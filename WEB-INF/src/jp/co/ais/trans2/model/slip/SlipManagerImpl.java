package jp.co.ais.trans2.model.slip;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.common.model.excel.*;
import jp.co.ais.trans2.common.model.report.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.bs.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.history.*;
import jp.co.ais.trans2.model.history.ApproveHistory.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.slip.SWK_DTL.*;
import jp.co.ais.trans2.model.slip.panel.*;
import jp.co.ais.trans2.model.user.*;

/**
 * 伝票マネージャ 伝票関連の操作（取得・登録・削除など）を制御するクラス実装
 */
@SuppressWarnings("unused")
public class SlipManagerImpl extends TModel implements SlipManager {

	/** true:BS勘定消込機能有効 */
	public static boolean isUseBS = ServerConfig.isFlagOn("trans.slip.use.bs");

	/** true:AP勘定消込機能無効 */
	public static boolean isNotUseAP = ServerConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** true:AR勘定消込機能無効 */
	public static boolean isNotUseAR = ServerConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** true:ファイル添付機能有効 */
	public static boolean isUseAttachment = ServerConfig.isFlagOn("trans.slip.use.attachment");

	/** true:承認履歴機能有効 */
	public static boolean isUseApproveHistory = ServerConfig.isFlagOn("trans.slip.use.approve.history");

	/** true:伝票番号を先頭付けてPDFファイル作成機能有効 */
	public static boolean isFileNameWithSlipNo = ServerConfig.isFlagOn("trans.slip.use.slipno.for.filename");

	/** 科目判定 */
	public Map<String, Item> itemMap = new HashMap<String, Item>();

	/** true:付箋機能有効 */
	public static boolean isUseTag = ServerConfig.isFlagOn("trans.slip.use.tag");

	/** true:CM_FUND_DTL登録を行うか */
	public static boolean isUseCmFund = ServerConfig.isFlagOn("trans.use.cm.fund.entry");

	/** true:CM_FUND_DTL登録でマッチングは口座番号を使用する */
	public static boolean isUseCmEntryAcctNo = ServerConfig.isFlagOn("trans.use.cm.entry.account.no");

	/** 計算ロジック */
	public TCalculator calculator = TCalculatorFactory.getCalculator();

	/** true:付替仕訳生成時に相手会社の勘定科目を表示する */
	public static boolean isUseTransferItemName = ServerConfig.isFlagOn("trans.slip.use.transfer.item.name");

	/** 承認グループリスト */
	protected List<AprvRoleGroup> grpList;

	/** 承認ロールリスト */
	protected List<AprvRole> roleList;

	/** 承認時オプション : 可能な限り先まで承認 */
	protected boolean isApproveAsMuchAsPossible = false;

	/**
	 * 伝票を構築する.<br>
	 * 付替、消費税仕訳、機能通貨仕訳
	 * 
	 * @param slip 伝票クラス
	 * @return 構築した伝票(付替先含め)
	 * @throws TException
	 */
	public List<Slip> buildSlip(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());

		// 登録伝票構築
		return logic.buildSlip(slip);
	}

	/**
	 * 伝票を起票する
	 * 
	 * @param slip 伝票クラス
	 * @throws TException
	 */
	public void entry(Slip slip) throws TException {

		if (slip != null) {
			restoreAPAR(slip.getSlipNo());
		}
		SWK_HDR h = slip.getHeader();
		if (getCompany().getAccountConfig().isUseWorkflowApprove() //
			&& Util.isNullOrEmpty(h.getSWK_APRV_GRP_CODE())) {
			// ワークフロー承認を利用 だが 伝票にグループ未設定
			String aprvGrpCode = getUser().getAprvRoleGroupCode();
			if (Util.isNullOrEmpty(aprvGrpCode)) {
				// ユーザーにも未設定
				throw new TException("現在ログイン中のユーザーに承認グループを設定してください。");
			}
			h.setSWK_APRV_GRP_CODE(aprvGrpCode);
		}

		// AP勘定消込機能有効の場合、AP勘定消込仕訳の消込区分の設定
		if (!isNotUseAP && slip != null && slip.getDetails() != null) {
			boolean isExist = false;
			boolean isAP = false;

			for (SWK_DTL dtl : slip.getDetails()) {

				if (dtl.isAPErasing()) {
					// 債務消込データの場合
					AP_ZAN ap = dtl.getAP_ZAN();
					dtl.setSWK_APAR_KESI_KBN(SWK_DTL.APAR_KESI_KBN.AP_FORWARD);
					dtl.setSWK_APAR_DEN_NO(ap.getZAN_DEN_NO()); // 消込元の伝票番号
					dtl.setSWK_APAR_GYO_NO(ap.getZAN_SWK_GYO_NO()); // 消込元の行番号
					isExist = true;

				} else if (!Util.isNullOrEmpty(dtl.getSWK_APAR_DEN_NO())) {
					// 自動伝票作成で消込情報設定済み
					isExist = true;
				}

				if (dtl.getSWK_APAR_KESI_KBN() == SWK_DTL.APAR_KESI_KBN.AP_FORWARD) {
					isAP = true;
				}
			}

			if (isExist && isAP) {
				updateAPARInfo(slip, SWK_DTL.APAR_KESI_KBN.AP_BASE);
			}
		}

		// AR勘定消込機能有効の場合、AP勘定消込仕訳の消込区分の設定
		if (!isNotUseAR && slip != null && slip.getDetails() != null) {
			boolean isExist = false;
			boolean isAR = false;

			for (SWK_DTL dtl : slip.getDetails()) {

				if (dtl.isARErasing()) {
					// 債権消込データの場合
					AR_ZAN ar = dtl.getAR_ZAN();
					dtl.setSWK_APAR_KESI_KBN(SWK_DTL.APAR_KESI_KBN.AR_FORWARD);
					dtl.setSWK_APAR_DEN_NO(ar.getZAN_SEI_DEN_NO()); // 消込元の伝票番号
					dtl.setSWK_APAR_GYO_NO(ar.getZAN_SEI_GYO_NO()); // 消込元の行番号
					isExist = true;

				} else if (!Util.isNullOrEmpty(dtl.getSWK_APAR_DEN_NO())) {
					// 自動伝票作成で消込情報設定済み
					isExist = true;
				}

				if (dtl.getSWK_APAR_KESI_KBN() == SWK_DTL.APAR_KESI_KBN.AR_FORWARD) {
					isAR = true;
				}
			}
			if (isExist && isAR) {
				updateAPARInfo(slip, SWK_DTL.APAR_KESI_KBN.AR_BASE);
			}
		}

		// BS勘定消込機能有効の場合、BS勘定消込仕訳の消込区分の設定
		if (isUseBS && slip != null && slip.getDetails() != null) {
			for (SWK_DTL dtl : slip.getDetails()) {

				SWK_DTL bs = dtl.getBsDetail();

				if (bs == null) {
					continue;
				}

				// 消込区分＝2:消込先
				dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.FORWARD);
				dtl.setSWK_SOUSAI_DATE(bs.getSWK_DEN_DATE()); // 消込元の伝票日付
				dtl.setSWK_SOUSAI_DEN_NO(bs.getSWK_DEN_NO()); // 消込元の伝票番号
				dtl.setSWK_SOUSAI_GYO_NO(bs.getSWK_GYO_NO()); // 消込元の行番号
			}
		}
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		if (isSlipAP(slip.getHeader().getSWK_DATA_KBN())) {
			logic = getSlipLogic(slip.getSlipType(), slip.getHeader().getSWK_DATA_KBN());
		}

		logic.entry(slip);

		// BS勘定消込機能有効の場合
		if (isUseBS) {
			// 登録後にBS勘定消込仕訳の更新処理を行う
			BSEraseManager bsManager = (BSEraseManager) getComponent(BSEraseManager.class);
			bsManager.updateBsBalance(slip);
		}

		// ファイル添付機能有効の場合
		if (isUseAttachment) {
			SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
			attachment.entry(slip);
		}

		if (isUseApproveHistory) {
			// 履歴登録
			try {
				ApproveHistoryManager appr = (ApproveHistoryManager) getComponent(ApproveHistoryManager.class);
				int syoFlag = (slip.getHeader().getSWK_UPD_CNT() != 0) ? SYO_FLG.MODIFY : SYO_FLG.ENTRY;
				appr.entry(appr.getApproveHistory(slip, getUser().getEmployee(), syoFlag));
			} catch (TException e) {
				throw new TRuntimeException(e);
			}
		}
		// 付箋機能有効の場合
		if (isUseTag) {
			SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
			tag.entry(slip);
		}

		// CM_FUND_DTLに登録を行うか
		if (isUseCmFund) {
			entryCmFundInfo(slip);
		}

	}

	/**
	 * 債務計上伝票かどうかをチェック
	 * 
	 * @param dataType
	 * @return boolean
	 */
	protected boolean isSlipAP(String dataType) {
		if (dataType.equals("23")) {
			return true;
		}
		return false;
	}

	/**
	 * 伝票内の船舶動静整合性のチェックを行う
	 * 
	 * @param slip
	 * @return エラーメッセージ一覧
	 * @throws TException
	 */
	protected List<Message> checkSlipFleetConsistency(Slip slip) throws TException {
		SlipFleetMovementChecker checker = (SlipFleetMovementChecker) getComponent(SlipFleetMovementChecker.class);
		return checker.checkFleetMovement(slip);
	}

	/**
	 * 伝票を構築して登録する.<br>
	 * 付替、消費税仕訳、機能通貨仕訳、削除履歴 あり
	 * 
	 * @param motoSlip 伝票クラス
	 * @return 構築した伝票(付替先含め)
	 * @throws TException
	 */
	public List<Slip> buildAndEntry(Slip motoSlip) throws TException {
		Slip slip = motoSlip.clone();

		// 先に削除。更新履歴も
		if (!slip.isNew()) {
			delete(slip, true, false);
		}

		// 伝票構築
		List<Slip> slipList = buildSlip(slip);

		for (Slip tslip : slipList) {
			entry(tslip); // 登録
		}

		return slipList;
	}

	/**
	 * 伝票番号を採番し、伝票にセットする
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setSlipNo(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		String slipNo = logic.newSlipNo(slip);
		slip.setSlipNo(slipNo);
		slip.synchDetails();
	}

	/**
	 * 伝票のチェック
	 * 
	 * @param slip 伝票
	 * @return エラーメッセージリスト
	 * @throws TException
	 */
	public List<Message> checkSlipError(Slip slip) throws TException {

		SlipLogic logic = getSlipLogic(slip.getSlipType());
		List<Message> messages = logic.checkSlipError(slip);
		if (slip.isChecksFleetMvmt()) {
			messages.addAll(checkSlipFleetConsistency(slip));
		}
		return messages;
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slip 対象伝票
	 */
	public void delete(Slip slip) {
		String dataKbn = slip.getHeader().getSWK_DATA_KBN();
		if (isSlipAP(dataKbn)) {
			this.delete(slip.getSlipNo(), slip.getSlipType(), dataKbn);
		}
		this.delete(slip.getSlipNo(), slip.getSlipType());
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slip 対象伝票
	 * @param isSaveHistory 履歴を保存するかどうか
	 * @param isSaveDelHistory 削除履歴を保存するかどうか
	 */
	public void delete(Slip slip, boolean isSaveHistory, boolean isSaveDelHistory) {
		this.delete(slip.getSlipNo(), slip.getSlipType(), isSaveHistory, isSaveDelHistory);
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 対象伝票番号
	 * @param slipType 対象伝票番号の伝票種別
	 */
	public void delete(String slipNo, String slipType) {
		this.delete(slipNo, slipType, true);
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 対象伝票番号
	 * @param slipType 対象伝票番号の伝票種別
	 * @param dataKbn
	 */
	public void delete(String slipNo, String slipType, String dataKbn) {
		this.delete(slipNo, slipType, dataKbn, true);
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 対象伝票番号
	 * @param slipType 対象伝票番号の伝票種別
	 * @param isSaveHistory 履歴を保存するかどうか
	 */
	public void delete(String slipNo, String slipType, boolean isSaveHistory) {

		if (!Util.isNullOrEmpty(slipNo)) {

			// AP/AR消込機能有効の場合
			if (!isNotUseAP || !isNotUseAR) {
				// 削除前にAP/AR消込仕訳の復元処理を行う
				try {
					restoreAPAR(slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}

			// BS勘定消込機能有効の場合
			if (isUseBS) {
				// 削除前にBS勘定消込仕訳の復元処理を行う
				restoreBsBalance(slipNo);
			}

			// ファイル添付機能有効の場合
			if (isUseAttachment) {
				SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				attachment.delete(companyCode, slipNo);
			}
			// 付箋機能有効の場合
			if (isUseTag) {
				SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				tag.delete(companyCode, slipNo);
			}

			SlipLogic logic = getSlipLogic(slipType);
			logic.delete(slipNo, isSaveHistory);

			// CM_FUND_DTLに処理を行うか
			if (isUseCmFund) {
				try {
					String companyCode = null;
					if (!getCompany().getAccountConfig().isUseGroupAccount()) {
						companyCode = getCompanyCode();
					}
					deleteCmFundInfo(companyCode, null, slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}

		}
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 対象伝票番号
	 * @param slipType 対象伝票番号の伝票種別
	 * @param dataKbn データ区分
	 * @param isSaveHistory 履歴を保存するかどうか
	 */
	public void delete(String slipNo, String slipType, String dataKbn, boolean isSaveHistory) {

		if (!Util.isNullOrEmpty(slipNo)) {

			// AP/AR消込機能有効の場合
			if (!isNotUseAP || !isNotUseAR) {
				// 削除前にAP/AR消込仕訳の復元処理を行う
				try {
					restoreAPAR(slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}

			// BS勘定消込機能有効の場合
			if (isUseBS) {
				// 削除前にBS勘定消込仕訳の復元処理を行う
				restoreBsBalance(slipNo);
			}

			// ファイル添付機能有効の場合
			if (isUseAttachment) {
				SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				attachment.delete(companyCode, slipNo);
			}

			SlipLogic logic = getSlipLogic(slipType, dataKbn);
			logic.delete(slipNo, isSaveHistory);

			// CM_FUND_DTLに処理を行うか
			if (isUseCmFund) {
				try {
					String companyCode = null;
					if (!getCompany().getAccountConfig().isUseGroupAccount()) {
						companyCode = getCompanyCode();
					}
					deleteCmFundInfo(companyCode, null, slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}
		}
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 対象伝票番号
	 * @param slipType 対象伝票番号の伝票種別
	 * @param isSaveHistory 履歴を保存するかどうか
	 * @param isSaveDelHistory 削除履歴を保存するかどうか
	 */
	public void delete(String slipNo, String slipType, boolean isSaveHistory, boolean isSaveDelHistory) {

		if (!Util.isNullOrEmpty(slipNo)) {

			// BS勘定消込機能有効の場合
			if (isUseBS) {
				// 削除前にBS勘定消込仕訳の復元処理を行う
				restoreBsBalance(slipNo);
			}

			// ファイル添付機能有効の場合
			if (isUseAttachment) {
				SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				attachment.delete(companyCode, slipNo);
			}
			// 付箋機能有効の場合
			if (isUseTag) {
				SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
				String companyCode = null;
				if (!getCompany().getAccountConfig().isUseGroupAccount()) {
					companyCode = getCompanyCode();
				}
				tag.delete(companyCode, slipNo);
			}

			SlipLogic logic = getSlipLogic(slipType);
			logic.delete(slipNo, isSaveHistory, isSaveDelHistory);

			// CM_FUND_DTLに処理を行うか
			if (isUseCmFund) {
				try {
					String companyCode = null;
					if (!getCompany().getAccountConfig().isUseGroupAccount()) {
						companyCode = getCompanyCode();
					}
					deleteCmFundInfo(companyCode, null, slipNo);

				} catch (TException e) {
					throw new TRuntimeException(e);
				}
			}
		}
	}

	/**
	 * 削除前にBS勘定消込仕訳の復元処理
	 * 
	 * @param slipNo 対象伝票番号
	 */
	protected void restoreBsBalance(String slipNo) {
		SlipCondition condition = createSlipCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSlipNo(slipNo);
		condition.setProgramCode(getProgramCode());
		condition.setUserCode(getUserCode());

		// 削除前にBS勘定消込仕訳の復元処理を行う
		BSEraseManager bsManager = (BSEraseManager) getComponent(BSEraseManager.class);
		bsManager.restoreBsBalance(condition);
	}

	/**
	 * 削除前にAP/AR消込仕訳の復元処理
	 * 
	 * @param slipNo 対象伝票番号
	 * @throws TException
	 */
	protected void restoreAPAR(String slipNo) throws TException {

		SlipCondition condition = createSlipCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSlipNo(slipNo);
		condition.setProgramCode(getProgramCode());
		condition.setUserCode(getUserCode());

		restoreAPAR(condition);
	}

	/**
	 * 伝票条件の作成
	 * 
	 * @return 伝票条件
	 */
	protected SlipCondition createSlipCondition() {
		return new SlipCondition();
	}

	/**
	 * 伝票排他
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void lock(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.lock(logic.getHeaderDao(), slip);
	}

	/**
	 * 伝票排他解除
	 * 
	 * @param slip 伝票
	 */
	public void unlock(Slip slip) {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.unlock(logic.getHeaderDao(), slip);
	}

	/**
	 * 伝票排他解除
	 * 
	 * @param slipType 伝票種別
	 */
	public void unlockAll(String slipType) {
		SlipLogic logic = getSlipLogic(slipType);
		logic.unlockAll(logic.getHeaderDao());
	}

	/**
	 * 伝票を構築する.<br>
	 * 各種値設定、消費税仕訳
	 * 
	 * @param slip 伝票クラス
	 * @return 構築した伝票パターン
	 * @throws TException
	 */
	public List<Slip> buildSlipPattern(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());

		// 伝票パターン構築
		return logic.buildSlipPattern(slip);
	}

	/**
	 * 伝票パターンを起票する
	 * 
	 * @param slip 伝票クラス
	 */
	public void entryPattern(Slip slip) {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.entryPattern(slip);
	}

	/**
	 * 伝票パターンを構築して登録する.<br>
	 * 消費税仕訳
	 * 
	 * @param motoSlip 伝票クラス
	 * @return 構築した伝票(付替先含め)
	 * @throws TException
	 */
	public List<Slip> buildAndEntryPattern(Slip motoSlip) throws TException {
		Slip slip = motoSlip.clone();

		// 先に削除
		if (!slip.isNew()) {
			deletePattern(slip);
		}

		// 伝票構築
		List<Slip> slipList = buildSlipPattern(slip);

		for (Slip tslip : slipList) {
			entryPattern(tslip); // 登録
		}

		return slipList;
	}

	/**
	 * 伝票パターンを削除する
	 * 
	 * @param slip 対象伝票
	 */
	public void deletePattern(Slip slip) {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.deletePattern(slip);
	}

	/**
	 * 伝票パターン排他
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void lockPattern(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.lock(logic.getPatternHeaderDao(), slip);
	}

	/**
	 * 伝票パターン排他解除
	 * 
	 * @param slip 伝票
	 */
	public void unlockPattern(Slip slip) {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.unlock(logic.getPatternHeaderDao(), slip);
	}

	/**
	 * 伝票パターン排他解除
	 * 
	 * @param slipType 伝票種別
	 */
	public void unlockPatternAll(String slipType) {
		SlipLogic logic = getSlipLogic(slipType);
		logic.unlockAll(logic.getPatternHeaderDao());
	}

	/**
	 * ヘッダデータ取得
	 * 
	 * @param slipType 伝票種別
	 * @param param 条件
	 * @return ヘッダリスト
	 */
	public List<SWK_HDR> getHeader(String slipType, SlipCondition param) {
		SlipLogic logic = getSlipLogic(slipType);
		return logic.getHeader(param);
	}

	/**
	 * ヘッダデータ取得
	 * 
	 * @param param 条件
	 * @return ヘッダリスト
	 */
	public List<SWK_HDR> getHeader(SlipCondition param) {
		// 複合取得用Dao
		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		return dao.findByCondition(param);
	}

	/**
	 * ヘッダデータのエクセル取得
	 * 
	 * @param param 条件
	 * @return ヘッダリストのエクセル
	 * @throws TException
	 */
	public byte[] getHeaderExcel(SlipCondition param) throws TException {
		List<SWK_HDR> list = getHeader(param);

		if (list == null || list.isEmpty()) {
			return null;
		}

		SlipHeaderExcel excel = new SlipHeaderExcel(getUser().getLanguage());

		byte[] data = excel.getExcel(list);
		return data;
	}

	/**
	 * 明細データ取得
	 * 
	 * @param param 条件
	 * @return 仕訳リスト
	 */
	public List<SWK_DTL> getDetails(SlipCondition param) {
		SWK_DTLDao dao = (SWK_DTLDao) getComponent("NEW-SWK_DTLDao");
		return dao.findByCondition(param.toSQL());
	}

	/**
	 * ヘッダデータ取得
	 * 
	 * @param param 条件
	 * @return ヘッダリスト
	 */
	public List<SWK_HDR> getPatternHeader(SlipCondition param) {
		// 複合取得用Dao
		SlipDao dao = (SlipDao) getComponent(SlipDao.class);

		return dao.findPatternByCondition(param);
	}

	/**
	 * 明細データ取得
	 * 
	 * @param param 条件
	 * @return 仕訳リスト
	 */
	public List<SWK_DTL> getPatternDetails(SlipCondition param) {
		SWK_PTN_DTLDao dao = (SWK_PTN_DTLDao) getComponent("NEW-SWK_PTN_DTLDao");
		return dao.findByCondition(param.toSQL());
	}

	/**
	 * ヘッダデータ取得
	 * 
	 * @param param 条件
	 * @return ヘッダリスト
	 */
	public List<SWK_HDR> getHistoryHeader(SlipCondition param) {
		// 複合取得用Dao
		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		return dao.findHistoryByCondition(param);
	}

	/**
	 * 明細データ取得
	 * 
	 * @param param 条件
	 * @return 仕訳リスト
	 */
	public List<SWK_DTL> getHistoryDetails(SlipCondition param) {
		SWK_DEL_DTLDao dao = (SWK_DEL_DTLDao) getComponent("NEW-SWK_DEL_DTLDao");
		return dao.findByCondition(param.toSQL());
	}

	/**
	 * 伝票取得
	 * 
	 * @param condition 条件
	 * @return 伝票リスト
	 */
	public List<Slip> getSlip(SlipCondition condition) {

		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		List<Slip> slips = dao.findSlipByCondition(condition);
		return slips;

	}

	/**
	 * 指定ヘッダより伝票を取得する.
	 * 
	 * @param hdr 伝票
	 * @param condition
	 * @return 伝票
	 * @throws TException
	 */
	public Slip getSlip(SWK_HDR hdr, SlipCondition condition) throws TException {

		// 伝票構築
		Slip slip = new Slip(hdr);
		slip.setDetails(getDetails(condition));

		// 内部保持Entityなどを設定.
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.setupSlip(slip);

		return slip;
	}

	/**
	 * 指定条件の帳簿を取得する.
	 * 
	 * @param condition 条件
	 * @return 帳簿リスト
	 */
	public List<SlipBooks> getSlipBooks(SlipCondition condition) {
		List<Slip> slipList = getSlip(condition);

		List<SlipBooks> list = new ArrayList<SlipBooks>(slipList.size());

		for (Slip slip : slipList) {
			list.add(new SlipBooks(slip));
		}

		return list;
	}

	/**
	 * 指定条件の帳簿を取得する.
	 * 
	 * @param condition 条件
	 * @return 帳簿リスト
	 */
	public List<SlipDen> getSlipDen(SlipCondition condition) {
		List<Slip> slipList = getSlip(condition);

		List<SlipDen> list = new ArrayList<SlipDen>(slipList.size());

		for (Slip slip : slipList) {
			SlipDen slipDen = new SlipDen();
			slipDen.setSWK_DEN_NO(slip.getSlipNo());
			list.add(slipDen);
		}

		return list;
	}

	/**
	 * SWK_HDR→SlipDen
	 * 
	 * @param hdr
	 * @return SlipDen
	 */
	protected SlipDen convertToSlipDen(SWK_HDR hdr) {
		SlipDen bean = new SlipDen();
		bean.setKAI_CODE(hdr.getKAI_CODE());
		bean.setSWK_DEN_DATE(hdr.getSWK_DEN_DATE());
		bean.setSWK_DEN_NO(hdr.getSWK_DEN_NO());
		bean.setSWK_BEFORE_DEN_NO(hdr.getSWK_BEFORE_DEN_NO());
		bean.setSWK_BEFORE_UPD_KBN(hdr.getSWK_UPD_KBN());
		bean.setSWK_DATA_KBN(hdr.getSWK_DATA_KBN());
		bean.setSWK_UPD_KBN(hdr.getSWK_UPD_KBN());
		bean.setSWK_DEN_DATE(hdr.getSWK_DEN_DATE());

		bean.setSWK_IRAI_EMP_CODE(hdr.getSWK_IRAI_EMP_CODE());
		bean.setSWK_TEK(hdr.getSWK_TEK());
		bean.setSWK_DEN_SYU(hdr.getSWK_DEN_SYU());
		bean.setSWK_CUR_CODE(hdr.getSWK_CUR_CODE());
		bean.setSWK_UPD_CNT(hdr.getSWK_UPD_CNT());
		bean.setSWK_KSN_KBN(hdr.getSWK_KSN_KBN());

		// KAI_CODE String 会社コード
		// SWK_DEN_NO String 伝票番号
		// SWK_DEN_DATE Date 伝票日付
		// SWK_BEFORE_DEN_NO String BEFORE伝票番号
		// SWK_BEFORE_UPD_KBN int 前回更新区分
		// SWK_DATA_KBN String データ区分
		// SWK_UPD_KBN int 更新区分
		// SWK_SYO_EMP_CODE String 承認者コード
		// SWK_SYO_EMP_NAME String 承認者名称
		// SWK_SYO_EMP_NAME_S String 承認者略称
		// SWK_SYO_DATE Date 承認日
		// SWK_IRAI_EMP_CODE String 依頼者
		// SWK_TEK String 伝票摘要
		// SWK_DEN_SYU String 伝票種別
		// SWK_CUR_CODE String 通貨コード
		// SWK_UPD_CNT int 修正回数

		return bean;
	}

	/**
	 * 指定番号の帳簿を取得する.
	 * 
	 * @param compCode 会社コード
	 * @param slipNo 伝票番号
	 * @return 帳簿
	 */
	public SlipBooks getSlipBooks(String compCode, String slipNo) {
		SlipCondition param = new SlipCondition();
		param.setCompanyCode(compCode);
		param.setSlipNo(slipNo);

		List<Slip> list = getSlip(param);

		if (list.isEmpty()) {
			return null;
		}

		return new SlipBooks(list.get(0));
	}

	/**
	 * 指定ヘッダより伝票パターンを取得する.
	 * 
	 * @param hdr 伝票
	 * @return 伝票
	 * @throws TException
	 */
	public Slip getPatternSlip(SWK_HDR hdr) throws TException {

		// 伝票構築
		Slip slip = new Slip(hdr);

		SlipCondition param = new SlipCondition();
		param.setCompanyCode(slip.getCompanyCode());
		param.setSlipNo(slip.getSlipNo());
		slip.setDetails(getPatternDetails(param));

		// 内部保持Entityなどを設定.
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.setupSlip(slip);

		// 添付ファイルをクリア
		slip.getHeader().setAttachments(null);
		// 付箋機能をクリア
		slip.getHeader().setSwkTags(null);

		// 明細の紐付け初期化
		for (SWK_DTL dtl : slip.getDetails()) {
			dtl.setSWK_DEN_NO(null);
			dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
			dtl.setSWK_KESI_DATE(null); // 消込伝票日付
			dtl.setSWK_KESI_DEN_NO(null); // 消込伝票番号
			dtl.setSWK_SOUSAI_DATE(null); // 相殺伝票日付
			dtl.setSWK_SOUSAI_DEN_NO(null); // 相殺伝票番号
			dtl.setSWK_SOUSAI_GYO_NO(null); // 相殺行番号

			dtl.setSWK_APAR_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);// AP/AR消込区分
			dtl.setSWK_APAR_DEN_NO(null);// AP/AR消込伝票番号
			dtl.setSWK_APAR_GYO_NO(null);// AP/AR消込行番号

			dtl.setAP_ZAN(null);
			dtl.setAR_ZAN(null);
			dtl.setBsDetail(null);
		}

		return slip;
	}

	/**
	 * 伝票パターン取得
	 * 
	 * @param condition 条件
	 * @return 伝票リスト
	 */
	public List<Slip> getPatternSlip(SlipCondition condition) {

		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		return dao.findSlipPatternByCondition(condition);

	}

	/**
	 * 伝票取得
	 * 
	 * @param condition 条件
	 * @return 伝票リスト
	 */
	public List<Slip> getHistorySlip(SlipCondition condition) {

		SlipDao dao = (SlipDao) getComponent(SlipDao.class);
		return dao.findSlipHistoryByCondition(condition);

	}

	/**
	 * 指定条件の帳簿を取得する.(履歴用)
	 * 
	 * @param condition 条件
	 * @return 帳簿リスト
	 */
	public List<SlipBooks> getHistorySlipBooks(SlipCondition condition) {
		List<Slip> slipList = getHistorySlip(condition);

		List<SlipBooks> list = new ArrayList<SlipBooks>(slipList.size());

		for (Slip slip : slipList) {
			list.add(new SlipBooks(slip));
		}

		return list;
	}

	/**
	 * 一時伝票帳票を返す
	 * 
	 * @param tempSlip 一時伝票
	 * @return 一時伝票帳票
	 * @throws TException
	 */
	public byte[] getTempSlipReport(Slip tempSlip) throws TException {

		// 一時伝票番号＝PREVIEW
		tempSlip.setSlipNo("PREVIEW");
		tempSlip.setAvoidReuiredItemNULL(true); // build時チェック不要

		// 消費税仕訳追加
		List<Slip> list = buildSlip(tempSlip);

		for (Slip slip : list) {
			// タイトルに(PREVIEW)印字追加
			slip.getHeader().setSWK_DEN_SYU_NAME_K(slip.getHeader().getSWK_DEN_SYU_NAME_K() + "(PREVIEW)");
			slip.setDetails(slip.getDetails(CurrencyType.KEY));
		}

		return getReport(list);
	}

	/**
	 * 伝票帳票を返す
	 * 
	 * @param slips 伝票(付替先も含めたデータ)
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReport(List<Slip> slips) throws TException {
		if (slips.isEmpty()) {
			return new byte[0];
		}

		// PDFに相手会社の勘定科目を表示するか
		if (isUseTransferItemName) {
			// 付替科目の取得
			Map<String, TransferConfig> confMap = new TreeMap<String, TransferConfig>();
			CompanyManager manager = (CompanyManager) getComponent(CompanyManager.class);

			// 処理マップ作成
			Map<String, SWK_DTL> dtlMap = new TreeMap<String, SWK_DTL>();

			for (Slip slip : slips) {
				for (SWK_DTL dtl : slip.getDetails()) {
					// 自動明細行か否か 消費税明細じゃない
					// ログイン会社 → 付替区分=0かつ消費税行じゃない
					// ログイン会社以外 → 自動仕訳行じゃない
					if ((dtl.getKAI_CODE().equals(getCompanyCode()) && dtl.getSWK_TUKE_KBN() == 0) //
						|| (!dtl.getKAI_CODE().equals(getCompanyCode()) && !dtl.isAutoDetail())) {
						// 親会社仕訳取得するため、マップに一時保存 // key :会社コード
						String key = dtl.getKAI_CODE();

						// 最小行番号のみセット＝既にマップに存在する場合はセットしない
						if (!dtlMap.containsKey(key)) {
							dtlMap.put(key, dtl);
						}
					}

					// 会社コードと計上会社コードが異なる場合、MAP生成
					if (!Util.equals(dtl.getKAI_CODE(), dtl.getSWK_K_KAI_CODE())) {
						// Key=会社コードと計上会社コード
						String key = dtl.getKAI_CODE() + "<>" + dtl.getSWK_K_KAI_CODE();
						// Mapに存在しない場合、データ取得
						if (!confMap.containsKey(key)) {
							List<TransferConfig> list = manager.getTransferConfig(dtl.getKAI_CODE(),
								dtl.getSWK_K_KAI_CODE());
							if (list == null || list.isEmpty()) {
								continue;
							}
							// 1行だけのはず
							for (TransferConfig bean : list) {
								String from = bean.getCompanyCode();
								String to = bean.getTransferCompanyCode();
								confMap.put(from + "<>" + to, bean);
							}
						}
					}
				}
			}

			// 処理マップと仕訳ジャーナルによって訂正処理を行う
			for (Slip slip : slips) {
				for (SWK_DTL dtl : slip.getDetails()) {
					// Key=会社コードと計上会社コード
					String mapKey = dtl.getKAI_CODE() + "<>" + dtl.getSWK_K_KAI_CODE();
					TransferConfig bean = confMap.get(mapKey);

					// 自動仕訳行で付替科目の場合は内訳に名称セット
					if (dtl.isAutoDetail() //
						&& bean != null //
						&& (Util.equals(dtl.getSWK_KMK_CODE(), bean.getTransferItemCode()) //
							&& Util.equals(dtl.getSWK_HKM_CODE(), bean.getTransferSubItemCode()) //
							&& Util.equals(dtl.getSWK_UKM_CODE(), bean.getTransferDetailItemCode()) //
						)) {

						String key = dtl.getSWK_K_KAI_CODE();
						// マップが存在する
						if (dtlMap.containsKey(key)) {
							// 対象明細を取得
							SWK_DTL baseDtl = dtlMap.get(key);
							// 相手会社の勘定科目名称(頭５文字)＋補助科目名称に上書き
							String name = "(" + StringUtil.leftBX(baseDtl.getSWK_KMK_NAME(), 10);
							// 補助名称が存在する場合「・」で 区切り
							if (!Util.isNullOrEmpty(baseDtl.getSWK_HKM_NAME())) {
								name += "・" + baseDtl.getSWK_HKM_NAME();
							}
							name += ")";

							// 内訳科目に名称セット＆伝票PDF表示の判定用にコードをNULL
							dtl.setSWK_UKM_CODE(null);
							dtl.setSWK_UKM_NAME(name);
						}
					}
				}
			}
		}

		List<LedgerBookAndLayout> books = new ArrayList<LedgerBookAndLayout>();
		SlipLayoutDefine define = (SlipLayoutDefine) getComponent(SlipLayoutDefine.class);

		for (Slip slip : slips) {
			// 帳票
			LedgerBook book = getReportBook(slip);
			// レイアウト
			SlipLogic slipLogic = getSlipLogic(slip.getSlipType());
			SlipLayout layout = slipLogic.getLayout(getUser().getLanguage());

			// 伝票種別情報設定
			define.setSlipType(slip.getSlipType()); // 伝票種別コード
			define.setSlipKind(slip.getSlipKind()); // データ区分Enum
			define.setDataDivision(slip.getHeader().getSWK_DATA_KBN()); // データ区分のコード

			// 伝票レイアウトの定義の設定
			layout.setDefine(define);

			books.add(new LedgerBookAndLayout(book, layout));
		}

		// ページ番号対応
		setupPageNo(books);

		// レイアウトを生成し、バイナリで返す
		DefautlSlipLayout layout = new DefautlSlipLayout(getUser().getLanguage());
		byte[] data = layout.getMultiReport(books);
		data = setupSlipNoBytes(slips.get(0), data);
		return data;
	}

	/**
	 * @param books
	 */
	protected void setupPageNo(List<LedgerBookAndLayout> books) {

		// ページ番号付替え会社単位かどうか(true:伝票番号単位)
		if (!ServerConfig.isFlagOn("trans.slip.transfer.page.no")) {
			return;
		}

		// 開始ページ番号訂正
		Map<String, Integer> countMap = new HashMap<String, Integer>();

		for (int i = 0; i < books.size(); i++) {

			SlipBook slipBook = (SlipBook) books.get(i).getBook();
			SlipBookHeader header = (SlipBookHeader) slipBook.getSheetAt(0).getHeader();
			String currentKey = header.getSlipNo();

			// 前回の番号で切り替え
			if (countMap.containsKey(currentKey)) {
				slipBook.setStartPageNo(countMap.get(currentKey));
			}

			// 総ページ数訂正ため、集める
			if (countMap.containsKey(currentKey)) {
				int totalSheetCount = countMap.get(currentKey) + slipBook.getSheetCount();
				countMap.put(currentKey, totalSheetCount);
			} else {
				countMap.put(currentKey, slipBook.getSheetCount());
			}

		}

		// 総ページ数訂正
		for (int i = 0; i < books.size(); i++) {
			SlipBook slipBook = (SlipBook) books.get(i).getBook();
			SlipBookHeader header = (SlipBookHeader) slipBook.getSheetAt(0).getHeader();
			String currentKey = header.getSlipNo();
			slipBook.setTotalPageCount(countMap.get(currentKey));
		}
	}

	/**
	 * 伝票番号設定
	 * 
	 * @param slip 伝票
	 * @param data 元バイト配列
	 * @return 新バイト配列
	 */
	protected byte[] setupSlipNoBytes(Slip slip, byte[] data) {
		if (!isFileNameWithSlipNo || data == null || slip == null || Util.isNullOrEmpty(slip.getSlipNo())) {
			return data;
		}

		byte[] slipNo = ("<SlipNo>" + StringUtil.fill(slip.getSlipNo(), 20, ' ') + "</SlipNo>").getBytes();
		byte[] newdata = new byte[data.length + slipNo.length];
		StringUtil.arraycopy(data, 0, newdata, 0, data.length);
		StringUtil.arraycopy(slipNo, 0, newdata, data.length, slipNo.length);
		return newdata;
	}

	/**
	 * 伝票Bookを返す
	 * 
	 * @param slip
	 * @return 伝票Book
	 * @throws TException
	 */
	public SlipBook getReportBook(Slip slip) throws TException {

		// 伝票ロジック取得
		SlipLogic slipLogic = getSlipLogic(slip.getSlipType());

		// 製本
		SlipBook book = slipLogic.toBook(slip);

		return book;

	}

	/**
	 * 伝票帳票を返す
	 * 
	 * @param slip 伝票
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReport(Slip slip) throws TException {

		// 製本
		SlipBook book = getReportBook(slip);

		// レイアウトを生成し、バイナリで返す
		SlipLogic slipLogic = getSlipLogic(slip.getSlipType());
		SlipLayout layout = slipLogic.getLayout(getUser().getLanguage());
		SlipLayoutDefine define = (SlipLayoutDefine) getComponent(SlipLayoutDefine.class);

		// 伝票種別情報設定
		define.setSlipType(slip.getSlipType()); // 伝票種別コード
		define.setSlipKind(slip.getSlipKind()); // データ区分Enum
		define.setDataDivision(slip.getHeader().getSWK_DATA_KBN()); // データ区分のコード

		// 伝票レイアウトの定義の設定
		layout.setDefine(define);

		byte[] data = layout.getReport(book);
		data = setupSlipNoBytes(slip, data);
		return data;
	}

	/**
	 * 指定会社、伝票番号の伝票帳票を返す。<BR>
	 * 原則自国帳簿を返すが、自国帳簿が無い場合はIFRS帳簿伝票を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param slipNo 伝票番号
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReport(String companyCode, String slipNo) throws TException {
		List<String> list = new ArrayList<String>(1);
		list.add(slipNo);

		return getReport(companyCode, list);
	}

	/**
	 * 指定会社、伝票番号の伝票帳票を返す。<BR>
	 * 原則自国帳簿を返すが、自国帳簿が無い場合はIFRS帳簿伝票を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param slipNoList 伝票番号リスト
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReport(String companyCode, List<String> slipNoList) throws TException {

		// 指定の伝票を抽出
		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(companyCode);
		condition.setSlipNoList(slipNoList.toArray(new String[slipNoList.size()]));

		List<SlipBooks> list = getSlipBooks(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		List<Slip> slipList = getSlipList(list);

		return getReport(slipList);
	}

	/**
	 * 指定伝票番号(複数)の伝票帳票を返す。<BR>
	 * 原則自国帳簿を返すが、自国帳簿が無い場合はIFRS帳簿伝票を返す。 <BR>
	 * 付替仕訳の場合、自社・相手会社両方の伝票を出力する。
	 * 
	 * @param slipNoList 伝票番号リスト
	 * @return 伝票帳票
	 * @throws TException
	 */
	public byte[] getReportBySlipNos(List<String> slipNoList) throws TException {

		// 指定の伝票を抽出
		SlipCondition condition = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			condition.setCompanyCode(getCompanyCode());
		}

		condition.setSlipNoList(slipNoList.toArray(new String[slipNoList.size()]));

		// 付替仕訳に対応
		condition.addOrder(SlipCondition.SORT_SLIP_DATE);
		condition.addOrder(SlipCondition.SORT_SLIP_NO);

		if (ServerConfig.isFlagOn("trans.slip.transfer.page.no")) {
			// ログイン会社は最優先
			SQLCreator sql = new SQLCreator();
			sql.add(" CASE WHEN hdr.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode());
			condition.addOrder(sql.toSQL());
		}

		condition.addOrder(SlipCondition.SORT_COMPANY_CODE);
		condition.addOrder(SlipCondition.SORT_LINE_NO);

		List<SlipBooks> list = getSlipBooks(condition);

		if (list == null || list.isEmpty()) {
			return new byte[0];
		}

		List<Slip> slipList = getSlipList(list);

		return getReport(slipList);
	}

	/**
	 * 両帳簿のデータから原則自国帳簿を返すが、自国帳簿が無い場合はIFRS帳簿伝票を返す。
	 * 
	 * @param list 両帳簿の伝票データのリスト
	 * @return 自国帳簿伝票（自国帳簿伝票がない場合はIFRS帳簿伝票）
	 */
	protected List<Slip> getSlipList(List<SlipBooks> list) {
		List<Slip> slipList = new ArrayList<Slip>(list.size());

		for (SlipBooks slipBook : list) {
			Slip slip = slipBook.getOwnBookSlip();

			if (slip == null || slip.getDetails() == null || slip.getDetails().isEmpty()) {
				slip = slipBook.getIFRSBookSlip();

				if (slip == null || slip.getDetails() == null || slip.getDetails().isEmpty()) {
					slip = slipBook.getIFRSFuncSlip();

					if (slip == null || slip.getDetails() == null || slip.getDetails().isEmpty()) {
						continue;
					}
				}
			}

			slipList.add(slip);
		}
		return slipList;
	}

	/**
	 * 伝票を承認する
	 * 
	 * @param slipList 承認する伝票のリスト
	 * @throws TException
	 */
	public List<SlipDen> approveSlip(List<SlipDen> slipList) throws TException {

		// 承認社員
		Employee employee = getUser().getEmployee();

		// 伝票関連テーブルロック
		lockSlipTable();

		// 否認後の伝票一覧
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// 対象伝票を処理する
		for (SlipDen dtl : slipList) {
			// 承認ビジネスロジック取得
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				// 承認
				if (dtl.isACCT_APRV_FLG()) {
					sa.approveSlip(dtl, employee);
				} else {
					sa.approveSlipForFieldState(dtl, employee);
				}
			} else {
				sa.approveSlip(dtl, employee);
			}

			// 付替がある場合、付替先の伝票も同時に承認する。
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.approveSlip(convertToSlipDen(hdr), employee);
					}
				}
			}
			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * 伝票を否認する
	 * 
	 * @param slipList 否認する伝票のリスト
	 * @return 否認後の伝票
	 * @throws TException
	 */
	public List<SlipDen> denySlip(List<SlipDen> slipList) throws TException {

		// 伝票関連テーブルロック
		lockSlipTable();

		// 否認後の伝票一覧
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// 対象伝票を処理する
		for (SlipDen dtl : slipList) {
			// 否認ビジネスロジック取得
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			// 否認
			sa.denySlip(dtl);

			// 付替がある場合、付替先の伝票も同時に否認する。
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.denySlip(convertToSlipDen(hdr));
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * 伝票承認を取り消す
	 * 
	 * @param slipList 承認を取り消す伝票のリスト
	 * @return 承認取消後の伝票
	 * @throws TException
	 */
	public List<SlipDen> cancelApproveSlip(List<SlipDen> slipList) throws TException {

		// 現場承認を使うか
		boolean isUseFieldApprove = getCompany().getAccountConfig().isUseFieldApprove();

		// 伝票関連テーブルロック
		lockSlipTable();

		// 承認取消後の伝票一覧
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// 対象伝票を処理する
		for (SlipDen dtl : slipList) {
			// 承認取消ビジネスロジック取得
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				// 承認
				if (dtl.isACCT_APRV_FLG()) {
					// 承認取消
					sa.cancelApproveSlip(dtl, isUseFieldApprove);
				} else {
					// 承認取消
					sa.cancelApproveSlipForFieldState(dtl, isUseFieldApprove);
				}
			} else {
				// 承認取消
				sa.cancelApproveSlip(dtl, isUseFieldApprove);
			}

			// 付替がある場合、付替先の伝票も同時に承認取消する。
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.cancelApproveSlip(convertToSlipDen(hdr), isUseFieldApprove);
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * 伝票を承認（現場承認）する
	 * 
	 * @param slipList 承認する伝票のリスト
	 * @throws TException
	 */
	public List<SlipDen> approveSlipForFieldState(List<SlipDen> slipList) throws TException {

		// 承認社員
		Employee employee = getUser().getEmployee();

		// 伝票関連テーブルロック
		lockSlipTable();

		// 承認後の伝票一覧
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// 対象伝票を処理する
		for (SlipDen dtl : slipList) {
			// 承認ビジネスロジック取得
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			// 承認
			sa.approveSlipForFieldState(dtl, employee);

			// 付替がある場合、付替先の伝票も同時に承認する。
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.approveSlipForFieldState(convertToSlipDen(hdr), employee);
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * 伝票を否認（現場否認）する
	 * 
	 * @param slipList 否認する伝票のリスト
	 * @return 否認後の伝票
	 * @throws TException
	 */
	public List<SlipDen> denySlipForFieldState(List<SlipDen> slipList) throws TException {

		// 伝票関連テーブルロック
		lockSlipTable();

		// 否認後の伝票一覧
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// 対象伝票を処理する
		for (SlipDen dtl : slipList) {
			// 否認ビジネスロジック取得
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			// 否認
			sa.denySlipForFieldState(dtl);

			// 付替がある場合、付替先の伝票も同時に否認する。
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.denySlipForFieldState(convertToSlipDen(hdr));
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * 伝票承認（現場承認）を取り消す
	 * 
	 * @param slipList 承認を取り消す伝票のリスト
	 * @return 承認取消後の伝票
	 * @throws TException
	 */
	public List<SlipDen> cancelApproveSlipForFieldState(List<SlipDen> slipList) throws TException {

		// 現場承認を使うか
		boolean isUseFieldApprove = getCompany().getAccountConfig().isUseFieldApprove();

		// 伝票関連テーブルロック
		lockSlipTable();

		// 承認取消後の伝票一覧
		List<SlipDen> afterSlipList = new ArrayList<SlipDen>();

		// 対象伝票を処理する
		for (SlipDen dtl : slipList) {
			// 承認取消ビジネスロジック取得
			SlipLogic slipLogic = getSlipLogic(dtl.getSWK_DEN_SYU());
			SlipApprove sa = slipLogic.getSlipApprove();

			// 承認取消
			sa.cancelApproveSlipForFieldState(dtl, isUseFieldApprove);

			// 付替がある場合、付替先の伝票も同時に承認取消する。
			if (getCompany().getAccountConfig().isUseGroupAccount()) {
				List<SWK_HDR> hdrList = getSubsidiaryCompanySlips(dtl.getSWK_DEN_NO(), dtl.getSWK_DEN_DATE());
				if (hdrList != null && !hdrList.isEmpty()) {
					for (SWK_HDR hdr : hdrList) {
						sa.cancelApproveSlipForFieldState(convertToSlipDen(hdr), isUseFieldApprove);
					}
				}

			}

			afterSlipList.add(dtl);
		}

		return afterSlipList;
	}

	/**
	 * 伝票関連テーブルロック
	 * 
	 * @throws TException
	 */
	public void lockSlipTable() throws TException {

		Connection conn = DBUtil.getConnection();

		// テーブルロックにより他トランザクションをブロック
		try {
			DBUtil.execute(conn, "LOCK TABLE GL_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			DBUtil.execute(conn, "LOCK TABLE AP_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			DBUtil.execute(conn, "LOCK TABLE AR_SWK_HDR IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			DBUtil.execute(conn, "LOCK TABLE SWK_DTL    IN SHARE ROW EXCLUSIVE MODE NOWAIT");
		} catch (TException e) {
			throw new TException("W01133");
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 伝票ロジックの取得
	 * 
	 * @param slipType 伝票種別
	 * @return 伝票ロジック
	 */
	public SlipLogic getSlipLogic(String slipType) {
		SlipLogicFactory slipLogicFactory = (SlipLogicFactory) getComponent(SlipLogicFactory.class);
		return slipLogicFactory.getLogic(slipType);
	}

	/**
	 * 伝票ロジックの取得
	 * 
	 * @param slipType 伝票種別
	 * @param dataType
	 * @return 伝票ロジック
	 */
	public SlipLogic getSlipLogic(String slipType, String dataType) {
		SlipLogicFactory slipLogicFactory = (SlipLogicFactory) getComponent(SlipLogicFactory.class);
		return slipLogicFactory.getLogic(slipType, dataType);
	}

	/**
	 * 子会社の伝票を返す
	 * 
	 * @param slipNo
	 * @param slipDate
	 * @return 子会社の伝票
	 */
	protected List<SWK_HDR> getSubsidiaryCompanySlips(String slipNo, Date slipDate) {

		SlipCondition condition = new SlipCondition();
		condition.setGroupAccountDivision(1);
		condition.setSlipDateFrom(slipDate);
		condition.setSlipDateTo(slipDate);
		condition.setSlipNo(slipNo);

		List<SWK_HDR> hdrList = getHeader(condition);
		return hdrList;
	}

	/**
	 * 伝票明細を構築する.
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public Slip setupDetails(Slip slip) throws TException {
		// 内部保持Entityなどを設定.
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.setupDetails(slip);

		return slip;
	}

	/**
	 * 伝票明細を構築する.
	 * 
	 * @param slipList 同じ伝票種別想定
	 * @param includeBalance true:AP/AR/BS残高セットアップ
	 * @throws TException
	 */
	public List<Slip> setupDetails(List<Slip> slipList, boolean includeBalance) throws TException {

		if (slipList == null || slipList.isEmpty()) {
			return slipList;
		}

		// 内部保持Entityなどを設定.
		SlipLogic logic = getSlipLogic(slipList.get(0).getSlipType());
		logic.setupDetails(slipList, includeBalance);

		return slipList;
	}

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip) throws TException {
		return setupDetailsOptional(slip, true);
	}

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、フラグによって消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slip
	 * @param recalc 必要な場合のみ再計算
	 * @return 伝票
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip, boolean recalc) throws TException {
		return setupDetailsOptional(slip, recalc, true);
	}

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、フラグによって消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slip
	 * @param recalc 必要な場合のみ再計算
	 * @param includeBalance true:AP/AR/BS残高セットアップ
	 * @return 伝票
	 * @throws TException
	 */
	public Slip setupDetailsOptional(Slip slip, boolean recalc, boolean includeBalance) throws TException {
		try {
			// 内部保持Entityなどを設定.
			SlipLogic logic = getSlipLogic(slip.getSlipType());
			logic.setupDetailsOptional(slip, recalc, includeBalance);

			return slip;
		} catch (TException e) {
			throw new TException(e);
		}
	}

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、フラグによって消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slipList 同じ伝票種別想定
	 * @param recalc 必要な場合のみ再計算
	 * @param includeBalance true:AP/AR/BS残高セットアップ
	 * @return 伝票
	 * @throws TException
	 */
	public List<Slip> setupDetailsOptional(List<Slip> slipList, boolean recalc, boolean includeBalance)
		throws TException {
		try {

			if (slipList == null || slipList.isEmpty()) {
				return slipList;
			}

			// 内部保持Entityなどを設定.
			// 注意：すべて同じ伝票種別想定
			SlipLogic logic = getSlipLogic(slipList.get(0).getSlipType());
			logic.setupDetailsOptional(slipList, recalc, includeBalance);

			return slipList;
		} catch (TException e) {
			throw new TException(e);
		}
	}

	/**
	 * 自動仕訳科目の取得
	 * 
	 * @param companyCode 会社コード
	 * @param types 科目制御区分
	 * @return 自動仕訳科目
	 * @throws TException
	 */
	public AutoJornalAccount getAutoJornalAccount(String companyCode, AutoJornalAccountType types) throws TException {

		AutoJornalAccount entity = getAutoJornalAccount(companyCode, types.value);
		return entity;
	}

	/**
	 * 自動仕訳科目の取得
	 * 
	 * @param companyCode 会社コード
	 * @param type 科目制御区分
	 * @return 自動仕訳科目
	 * @throws TException
	 */
	public AutoJornalAccount getAutoJornalAccount(String companyCode, int type) throws TException {

		List<AutoJornalAccount> list = getAutoJornalAccounts(companyCode, type);

		if (list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * 自動仕訳科目の取得(複数指定)
	 * 
	 * @param companyCode 会社コード
	 * @param types 科目制御区分
	 * @return 自動仕訳科目
	 * @throws TException
	 */
	public List<AutoJornalAccount> getAutoJornalAccounts(String companyCode, AutoJornalAccountType... types)
		throws TException {

		int[] kinds = new int[types.length];
		for (int i = 0; i < types.length; i++) {
			kinds[i] = types[i].value;
		}

		List<AutoJornalAccount> list = getAutoJornalAccounts(companyCode, kinds);

		return list;
	}

	/**
	 * 自動仕訳科目の取得(複数指定)
	 * 
	 * @param companyCode 会社コード
	 * @param types 科目制御区分
	 * @return 自動仕訳科目
	 * @throws TException
	 */
	public List<AutoJornalAccount> getAutoJornalAccounts(String companyCode, int... types) throws TException {

		Connection conn = DBUtil.getConnection();
		Statement statement = null;

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ").append("\n");
			sql.append("  skmk.KAI_CODE, ").append("\n");
			sql.append("  skmk.KMK_CNT, ").append("\n");
			sql.append("  skmk.KMK_CNT_NAME, ").append("\n");
			sql.append("  skmk.KMK_CODE, ").append("\n");
			sql.append("  skmk.HKM_CODE, ").append("\n");
			sql.append("  skmk.UKM_CODE, ").append("\n");
			sql.append("  skmk.DEP_CODE, ").append("\n");
			sql.append("  dep.DEP_NAME, ").append("\n");
			sql.append("  dep.DEP_NAME_S, ").append("\n");
			sql.append("  kmk.KMK_NAME, ").append("\n");
			sql.append("  kmk.KMK_NAME_S, ").append("\n");
			sql.append("  hkm.HKM_NAME, ").append("\n");
			sql.append("  hkm.HKM_NAME_S, ").append("\n");
			sql.append("  ukm.UKM_NAME, ").append("\n");
			sql.append("  ukm.UKM_NAME_S ").append("\n");
			sql.append("FROM SWK_KMK_MST skmk").append("\n");
			sql.append(" LEFT OUTER JOIN BMN_MST dep ").append("\n");
			sql.append("   ON skmk.KAI_CODE = dep.KAI_CODE ").append("\n");
			sql.append("  AND skmk.DEP_CODE = dep.DEP_CODE ").append("\n");
			sql.append(" LEFT OUTER JOIN KMK_MST kmk ").append("\n");
			sql.append("   ON skmk.KAI_CODE = kmk.KAI_CODE ").append("\n");
			sql.append("  AND skmk.KMK_CODE = kmk.KMK_CODE ").append("\n");
			sql.append(" LEFT OUTER JOIN HKM_MST hkm ").append("\n");
			sql.append("   ON skmk.KAI_CODE = hkm.KAI_CODE ").append("\n");
			sql.append("  AND skmk.KMK_CODE = hkm.KMK_CODE ").append("\n");
			sql.append("  AND skmk.HKM_CODE = hkm.HKM_CODE ").append("\n");
			sql.append(" LEFT OUTER JOIN UKM_MST ukm ").append("\n");
			sql.append("   ON skmk.KAI_CODE = ukm.KAI_CODE ").append("\n");
			sql.append("  AND skmk.KMK_CODE = ukm.KMK_CODE ").append("\n");
			sql.append("  AND skmk.HKM_CODE = ukm.HKM_CODE ").append("\n");
			sql.append("  AND skmk.UKM_CODE = ukm.UKM_CODE ").append("\n");
			sql.append("WHERE skmk.KAI_CODE = " + SQLUtil.getParam(companyCode)).append("\n");
			sql.append("  AND skmk.KMK_CNT IN " + SQLUtil.getIntInStatement(types));
			sql.append("  AND skmk.KMK_CODE IS NOT NULL ");

			statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql.toString());

			List<AutoJornalAccount> list = new ArrayList<AutoJornalAccount>();

			while (rs.next()) {
				list.add(mappingAutoJornalAccount(rs));
			}

			DBUtil.close(rs);

			return list;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(statement);
			DBUtil.close(conn);
		}

	}

	/**
	 * 自動仕訳科目 O/Rマッピング
	 * 
	 * @param rs
	 * @return 自動仕訳科目
	 * @throws Exception
	 */
	protected AutoJornalAccount mappingAutoJornalAccount(ResultSet rs) throws Exception {
		AutoJornalAccount bean = new AutoJornalAccount();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setKind(rs.getInt("KMK_CNT"));
		bean.setKindName(rs.getString("KMK_CNT_NAME"));
		bean.setDepertmentCode(rs.getString("DEP_CODE"));
		bean.setDepertmentName(rs.getString("DEP_NAME"));
		bean.setDepertmentNames(rs.getString("DEP_NAME_S"));
		bean.setItemCode(rs.getString("KMK_CODE"));
		bean.setItemName(rs.getString("KMK_NAME"));
		bean.setItemNames(rs.getString("KMK_NAME_S"));
		bean.setSubItemCode(rs.getString("HKM_CODE"));
		bean.setSubItemName(rs.getString("HKM_NAME"));
		bean.setSubItemNames(rs.getString("HKM_NAME_S"));
		bean.setDetailItemCode(rs.getString("UKM_CODE"));
		bean.setDetailItemName(rs.getString("UKM_NAME"));
		bean.setDetailItemNames(rs.getString("UKM_NAME_S"));

		return bean;
	}

	/**
	 * 削除伝票リストデータを返す
	 * 
	 * @param condition 検索条件
	 * @return DeleteSlipListGetterBook
	 * @throws TException
	 */
	public DeleteSlipListBook getDeletedSlipListBook(SlipCondition condition) throws TException {

		DeleteSlipListGetter dao = (DeleteSlipListGetter) getComponent(DeleteSlipListGetter.class);
		DeleteSlipListBook book = dao.get(condition);
		return book;

	}

	/**
	 * 削除伝票リストデータ(帳票)を返す
	 * 
	 * @param condition 検索条件
	 * @return byte
	 * @throws TException
	 */
	public byte[] getDeletedSlipListReport(SlipCondition condition) throws TException {

		DeleteSlipListBook book = getDeletedSlipListBook(condition);
		if (book == null || book.isEmpty()) {
			return null;
		}

		DeleteSlipListLayout layout = new DeleteSlipListLayout(getUser().getLanguage());
		return layout.getReport(book);

	}

	/**
	 * 削除伝票リストデータ(エクセル)を返す
	 * 
	 * @param condition 検索条件
	 * @return byte
	 * @throws TException
	 */
	public byte[] getDeletedSlipListExcel(SlipCondition condition) throws TException {

		DeleteSlipListBook book = getDeletedSlipListBook(condition);
		if (book == null || book.isEmpty()) {
			return null;
		}
		DeleteSlipListExcel layout = new DeleteSlipListExcel(getUser().getLanguage());
		return layout.getExcel(book);

	}

	/**
	 * 削除履歴の削除
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void deleteDelHistory(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());
		logic.deleteDelHistory(slip);
	}

	/**
	 * 削除履歴の登録
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void entryDelHistory(Slip slip) throws TException {
		SlipLogic logic = getSlipLogic(slip.getSlipType());

		List<Slip> list = new ArrayList<Slip>();
		list.add(slip);

		logic.entryDeleteHistory(list);
	}

	/**
	 * AP/AR消込情報の更新
	 * 
	 * @param slip
	 * @param kesiKbn
	 * @throws TException
	 */
	public void updateAPARInfo(Slip slip, int kesiKbn) throws TException {

		for (SWK_DTL dtl : slip.getDetails()) {

			if (Util.isNullOrEmpty(dtl.getSWK_APAR_DEN_NO())) {
				continue;
			}

			SQLCreator sql = new SQLCreator();
			sql.add(" UPDATE SWK_DTL ");
			sql.add(" SET    SWK_APAR_KESI_KBN = ? ", kesiKbn);
			sql.add("       ,SWK_APAR_DEN_NO   = ? ", dtl.getSWK_DEN_NO());
			sql.add("       ,SWK_APAR_GYO_NO   = ? ", dtl.getSWK_GYO_NO());
			sql.adt("       ,UPD_DATE          = ? ", getNow());
			sql.add("       ,PRG_ID            = ? ", getProgramCode());
			sql.add("       ,USR_ID            = ? ", getUserCode());
			sql.add(" WHERE  KAI_CODE   = ? ", dtl.getKAI_CODE());
			sql.add("   AND  SWK_DEN_NO = ? ", dtl.getSWK_APAR_DEN_NO());
			sql.add("   AND  SWK_GYO_NO = ? ", dtl.getSWK_APAR_GYO_NO());
			sql.add("   AND  SWK_APAR_DEN_NO IS NULL ");
			sql.add("   AND  SWK_BOOK_NO = 1 ");
			sql.add("   AND  SWK_ADJ_KBN IN (0, 1) ");
			DBUtil.execute(sql);

		}
	}

	/**
	 * AP/AR消込仕訳の復元処理
	 * 
	 * @param condition
	 * @throws TException
	 */
	public void restoreAPAR(SlipCondition condition) throws TException {

		SQLCreator sql = new SQLCreator();
		sql.add(" UPDATE SWK_DTL ");
		sql.add(" SET    SWK_APAR_KESI_KBN = ? ", SWK_DTL.APAR_KESI_KBN.NOMAL);
		sql.add("       ,SWK_APAR_DEN_NO   = NULL ");
		sql.add("       ,SWK_APAR_GYO_NO   = NULL ");
		sql.add(" WHERE  KAI_CODE        = ? ", condition.getCompanyCode());
		sql.add("   AND  SWK_APAR_DEN_NO = ? ", condition.getSlipNo());

		DBUtil.execute(sql);
	}

	/**
	 * @see jp.co.ais.trans2.model.slip.SlipManager#getDetailExcel(java.util.List)
	 */
	public byte[] getDetailExcel(List<SWK_DTL> list) throws TException {
		SlipDetailExternalExcel excel = getExcelClass();
		try {
			return excel.getExcel(list);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセル明細リストをbeanに変換して返却
	 * 
	 * @param file
	 * @param slipType
	 * @return 明細beanリスト
	 * @throws TException
	 */
	public List<SWK_DTL> convertExcelToDetails(File file, SlipType slipType) throws TException {
		SlipDetailExternalExcel excel = getExcelClass();
		try {
			CompanyManager comMana = get(CompanyManager.class);
			CompanySearchCondition comCon = new CompanySearchCondition();
			List<Company> companys = comMana.get(comCon);
			List<SWK_DTL> dtlList = excel.convertToEntityList(file, companys);
			for (SWK_DTL dtl : dtlList) {
				if (Util.isNullOrEmpty(dtl.getSWK_K_KAI_CODE())) {
					dtl.setSWK_K_KAI_CODE(getCompanyCode());
				}
				if (Util.isNullOrEmpty(dtl.getSWK_KMK_CODE())) {
					dtl.setSWK_HKM_CODE(null);
					dtl.setSWK_UKM_CODE(null);
				}
				if (Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
					dtl.setSWK_UKM_CODE(null);
				}
				if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
					// 消費税非存在時非課税
					dtl.setSWK_ZEI_KBN(ZEI_KBN.TAX_NONE);
					dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
					dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
				}
			}
			Slip slip = new Slip();
			slip.setSlipType(slipType);
			slip.setDetails(dtlList);
			slip = setupDetailsOptional(slip);
			if (slip == null) {
				return null;
			}
			return slip.getDetails();
		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			ServerLogger.error("error", e);
			// ファイルの読み込みに失敗しました。
			throw new TException("E00021");
		}
	}

	/**
	 * エクセル明細入出力クラスを取得する
	 * 
	 * @return エクセル明細入出力クラス
	 */
	public SlipDetailExternalExcel getExcelClass() {
		return new SlipDetailExternalExcel(getUser().getLanguage(), getCompany());
	}

	/**
	 * <<<<<<< HEAD 伝票エクスポートエクセルを生成＆取得(付替仕訳の場合、自社・相手会社両方の伝票を出力する。)
	 * 
	 * @param slipNoList
	 * @return 明細リストエクセル
	 * @throws TException
	 */
	public byte[] getExportSlipExcelBySlipNos(List<String> slipNoList) throws TException {
		// 指定の伝票を抽出
		SlipCondition condition = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			condition.setCompanyCode(getCompanyCode());
		}

		condition.setSlipNoList(slipNoList.toArray(new String[slipNoList.size()]));

		// 付替仕訳に対応
		condition.addOrder(SlipCondition.SORT_SLIP_DATE);
		condition.addOrder(SlipCondition.SORT_SLIP_NO);
		condition.addOrder(SlipCondition.SORT_COMPANY_CODE);
		condition.addOrder(SlipCondition.SORT_LINE_NO);

		List<SlipBooks> list = getSlipBooks(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		List<Slip> slipList = getSlipList(list);

		// エクスポート明細変換＆行番号の再生成
		List<SlipInstructionDtl> dtlList = convertToExportData(slipList);

		// エクスポートエクセル 生成
		SlipInstructionExternalExcel excel = getInstructionExcelClass();
		try {
			return excel.getExcel(dtlList);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 伝票エクスポートエクセルを生成＆取得
	 * 
	 * @param companyCode
	 * @param slipNoList
	 * @return 明細リストエクセル
	 * @throws TException
	 */
	public byte[] getExportSlipExcel(String companyCode, List<String> slipNoList) throws TException {

		// 指定の伝票を抽出
		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(companyCode);
		condition.setSlipNoList(slipNoList.toArray(new String[slipNoList.size()]));

		List<SlipBooks> list = getSlipBooks(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		List<Slip> slipList = getSlipList(list);

		// エクスポート明細変換＆行番号の再生成
		List<SlipInstructionDtl> dtlList = convertToExportData(slipList);

		// エクスポートエクセル 生成
		SlipInstructionExternalExcel excel = getInstructionExcelClass();
		try {
			return excel.getExcel(dtlList);
		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * @param slipList
	 * @return List<Slip>
	 * @throws TException
	 */
	public List<SlipInstructionDtl> convertToExportData(List<Slip> slipList) throws TException {

		List<SlipInstructionDtl> list = new ArrayList<SlipInstructionDtl>();
		try {

			// 自動仕訳区分（SWK_AUTO_KBN）＝「1」の場合
			// 科目コードで科目マスタマスタを参照し、下記条件の場合 行番号に「1」を設定
			// GL科目制御区分=「04」または、AP科目制御区分＝「01」または、AR科目制御区分＝「01」
			// 上記以外 行番号 ＋ 1 を設定
			for (Slip slip : slipList) {
				String dataKbn = slip.getHeader().getSWK_DATA_KBN(); // 伝票種別の判定用
				boolean isFirstRow = false; // 1行目の判定用

				for (SWK_DTL detail : slip.getDetails()) {

					if (SlipKind.get(dataKbn) == SlipKind.TRANSFER) {
						// 振替/振戻はそのままセット
						continue;
					}

					Item item = getItem(detail.getKAI_CODE(), detail.getSWK_KMK_CODE());
					// ブランクは処理終了
					if (item == null) {
						// 科目情報がありません
						new TException("I00484");
					}

					if (SlipKind.get(dataKbn) == SlipKind.INPUT_CASH_FLOW
						|| SlipKind.get(dataKbn) == SlipKind.OUTPUT_CASH_FLOW) {
						// 入金/出金は、自動仕訳区分（SWK_AUTO_KBN）＝「1」かつGL科目制御区分=04:資金科目の場合に行番号1
						isFirstRow = (detail.isAutoDetail() && GLType.FUND == item.getGlType());

					} else if (SlipKind.get(dataKbn) == SlipKind.PURCHASE) {
						// 債務計上は、自動仕訳区分（SWK_AUTO_KBN）＝「1」かつAP科目制御区分=01:債務科目の場合に行番号1
						isFirstRow = (detail.isAutoDetail() && APType.DEBIT == item.getApType());

					} else if (SlipKind.get(dataKbn) == SlipKind.SALES) {
						// 債権計上は、自動仕訳区分（SWK_AUTO_KBN）＝「1」かつAR科目制御区分=01:債権科目の場合に行番号1
						isFirstRow = (detail.isAutoDetail() && ARType.AR == item.getArType());

					}

					if (isFirstRow) {
						// 行番号1
						list.add(mappingSlipExportInstruction(detail, slip.getHeader(), true));

					}
				}

				for (SWK_DTL detail : slip.getDetails()) {
					if (SlipKind.get(dataKbn) == SlipKind.TRANSFER) {
						// 振替/振戻はそのままセット
						list.add(mappingSlipExportInstruction(detail, slip.getHeader(), false));
						continue;
					}

					Item item = getItem(detail.getKAI_CODE(), detail.getSWK_KMK_CODE());
					// ブランクは処理終了
					if (item == null) {
						// 科目情報がありません
						new TException("I00484");
					}

					if (SlipKind.get(dataKbn) == SlipKind.INPUT_CASH_FLOW
						|| SlipKind.get(dataKbn) == SlipKind.OUTPUT_CASH_FLOW) {
						// 入金/出金は、自動仕訳区分（SWK_AUTO_KBN）＝「1」かつGL科目制御区分=04:資金科目の場合に行番号1
						isFirstRow = (detail.isAutoDetail() && GLType.FUND == item.getGlType());

					} else if (SlipKind.get(dataKbn) == SlipKind.PURCHASE) {
						// 債務計上は、自動仕訳区分（SWK_AUTO_KBN）＝「1」かつAP科目制御区分=01:債務科目の場合に行番号1
						isFirstRow = (detail.isAutoDetail() && APType.DEBIT == item.getApType());

					} else if (SlipKind.get(dataKbn) == SlipKind.SALES) {
						// 債権計上は、自動仕訳区分（SWK_AUTO_KBN）＝「1」かつAR科目制御区分=01:債権科目の場合に行番号1
						isFirstRow = (detail.isAutoDetail() && ARType.AR == item.getArType());

					}

					if (isFirstRow) {
						continue;
					} else {
						list.add(mappingSlipExportInstruction(detail, slip.getHeader(), false));
					}

				}

			}

			// 行番号の振り直し
			String kaiCode = null;
			String denNo = null;
			int gyoNo = 0;
			for (SlipInstructionDtl dtl : list) {
				gyoNo++;
				// Keyが変わったら初期化
				if (!Util.equals(kaiCode, dtl.getKAI_CODE()) || !Util.equals(denNo, dtl.getSWK_DEN_NO())) {
					gyoNo = 1;
					kaiCode = dtl.getKAI_CODE();
					denNo = dtl.getSWK_DEN_NO();
				}
				dtl.setSWK_GYO_NO(gyoNo);

			}

		} catch (Exception e) {
			throw new TException(e);
		}

		return list;
	}

	/**
	 * 科目マスタを取得する
	 * 
	 * @param kaiCode
	 * @param kmkCode
	 * @return 科目マスタ
	 */
	public Item getItem(String kaiCode, String kmkCode) {

		String key = kaiCode + "<>" + kmkCode;

		if (itemMap.containsKey(key)) {
			return itemMap.get(key);
		}

		// 科目
		ItemManager itemMng = (ItemManager) getComponent(ItemManager.class);
		Item item = null;
		try {
			item = itemMng.getItem(kaiCode, kmkCode, null, null);
			itemMap.put(key, item);

		} catch (TException e) {
			throw new TRuntimeException(e);
		}
		return item;
	}

	/**
	 * 伝票エクスポートエクセル O/Rマッピング
	 * 
	 * @param hdr
	 * @param detail
	 * @param isFirst
	 * @return 伝票エクスポートエクセル
	 * @throws Exception
	 */
	public SlipInstructionDtl mappingSlipExportInstruction(SWK_DTL detail, SWK_HDR hdr, boolean isFirst)
		throws Exception {
		SlipInstructionDtl bean = new SlipInstructionDtl();

		bean.setKAI_CODE(detail.getKAI_CODE());
		bean.setSWK_DEN_DATE(DateUtil.toYMDPlainString(detail.getSWK_DEN_DATE()));
		bean.setSWK_DEN_NO(detail.getSWK_DEN_NO());
		if (isFirst) {
			// ヘッダー行明細は1行目
			bean.setSWK_GYO_NO(1);
		} else {
			bean.setSWK_GYO_NO(detail.getSWK_GYO_NO());
		}
		bean.setSWK_UKE_DEP_CODE(hdr.getSWK_UKE_DEP_CODE());
		bean.setSWK_TEK_CODE(hdr.getSWK_TEK_CODE());
		bean.setSWK_TEK(hdr.getSWK_TEK());
		bean.setSWK_SYO_EMP_CODE(hdr.getSWK_SYO_EMP_CODE());
		bean.setSWK_SYO_DATE(DateUtil.toYMDPlainString(hdr.getSWK_SYO_DATE()));
		bean.setSWK_IRAI_EMP_CODE(hdr.getSWK_IRAI_EMP_CODE());
		bean.setSWK_IRAI_DEP_CODE(hdr.getSWK_IRAI_DEP_CODE());
		bean.setSWK_IRAI_DATE(DateUtil.toYMDPlainString(hdr.getSWK_IRAI_DATE()));
		bean.setSWK_SYS_KBN(hdr.getSWK_SYS_KBN());
		bean.setSWK_DEN_SYU(hdr.getSWK_DEN_SYU());
		bean.setSWK_UPD_KBN(hdr.getSWK_UPD_KBN());
		bean.setSWK_KSN_KBN(hdr.getSWK_KSN_KBN());
		bean.setSWK_KMK_CODE(detail.getSWK_KMK_CODE());
		bean.setSWK_HKM_CODE(detail.getSWK_HKM_CODE());
		bean.setSWK_UKM_CODE(detail.getSWK_UKM_CODE());
		bean.setSWK_DEP_CODE(detail.getSWK_DEP_CODE());
		bean.setSWK_TRI_CODE(detail.getSWK_TRI_CODE());
		bean.setSWK_EMP_CODE(detail.getSWK_EMP_CODE());
		bean.setSWK_CUR_CODE(detail.getSWK_CUR_CODE());
		bean.setSWK_CUR_RATE(detail.getSWK_CUR_RATE());
		if (DecimalUtil.isNullOrZero(bean.getSWK_CUR_RATE())) {
			// ZERO or NULLだったら1をセット
			bean.setSWK_CUR_RATE(BigDecimal.ONE);
		}
		bean.setSWK_DC_KBN(detail.getSWK_DC_KBN());
		bean.setSWK_ZEI_KBN(detail.getSWK_ZEI_KBN());
		bean.setSWK_ZEI_KIN(detail.getSWK_ZEI_KIN());
		bean.setSWK_ZEI_CODE(detail.getSWK_ZEI_CODE());
		bean.setSWK_GYO_TEK_CODE(detail.getSWK_GYO_TEK_CODE());
		bean.setSWK_GYO_TEK(detail.getSWK_GYO_TEK());
		bean.setSWK_KNR_CODE_1(detail.getSWK_KNR_CODE_1());
		bean.setSWK_KNR_CODE_2(detail.getSWK_KNR_CODE_2());
		bean.setSWK_KNR_CODE_3(detail.getSWK_KNR_CODE_3());
		bean.setSWK_KNR_CODE_4(detail.getSWK_KNR_CODE_4());
		bean.setSWK_KNR_CODE_5(detail.getSWK_KNR_CODE_5());
		bean.setSWK_KNR_CODE_6(detail.getSWK_KNR_CODE_6());
		bean.setSWK_HM_1(detail.getSWK_HM_1());
		bean.setSWK_HM_2(detail.getSWK_HM_2());
		bean.setSWK_HM_3(detail.getSWK_HM_3());
		bean.setSWK_AUTO_KBN(detail.getSWK_AUTO_KBN());
		bean.setHAS_DATE(DateUtil.toYMDPlainString(detail.getHAS_DATE()));
		bean.setSWK_AT_KMK_CODE(detail.getSWK_AT_KMK_CODE());
		bean.setSWK_AT_HKM_CODE(detail.getSWK_AT_HKM_CODE());
		bean.setSWK_AT_UKM_CODE(detail.getSWK_AT_UKM_CODE());
		bean.setSWK_AT_DEP_CODE(detail.getSWK_AT_DEP_CODE());
		bean.setSWK_K_KAI_CODE(detail.getSWK_K_KAI_CODE());
		bean.setSWK_SEI_NO(detail.getSWK_SEI_NO());
		bean.setSWK_KIN(detail.getSWK_KIN());
		bean.setSWK_IN_KIN(detail.getSWK_IN_KIN());
		if (isFirst) {
			bean.setSWK_SIHA_KBN(hdr.getSWK_SIHA_KBN());
			bean.setSWK_SIHA_DATE(DateUtil.toYMDPlainString(hdr.getSWK_SIHA_DATE()));
			bean.setSWK_HOH_CODE(hdr.getSWK_HOH_CODE());
			bean.setSWK_HORYU_KBN(hdr.getSWK_HORYU_KBN());
			bean.setSWK_TJK_CODE(hdr.getSWK_TJK_CODE());
			bean.setSWK_AR_DATE(DateUtil.toYMDPlainString(hdr.getSWK_AR_DATE()));
			bean.setSWK_CBK_CODE(hdr.getSWK_CBK_CODE());
			bean.setSWK_TUKE_KBN(hdr.getSWK_TUKE_KBN());
		}
		bean.setSWK_IN_ZEI_KIN(detail.getSWK_IN_ZEI_KIN());

		return bean;

	}

	/**
	 * エクセル明細入出力クラスを取得する
	 * 
	 * @return エクセル明細入出力クラス
	 */
	public SlipInstructionExternalExcel getInstructionExcelClass() {
		return new SlipInstructionExternalExcel(getUser().getLanguage(), getCompany());
	}

	/**
	 * 伝票画面クラスを取得するする
	 */
	public Class getSlipPanel(String prgCode, String denSyuMst) {

		// ビジネスロジック取得
		SlipPanelLogic slipPanelLogic = getSlipPanelLogic(denSyuMst);
		Class sc = slipPanelLogic.getSlipPanelClass(prgCode);

		return sc;
	}

	/**
	 * 伝票ロジックの取得
	 * 
	 * @param slipType 伝票種別
	 * @return 伝票ロジック
	 */
	public SlipPanelLogic getSlipPanelLogic(String slipType) {
		SlipPanelLogicFactory SlipPanelLogicFactory = (SlipPanelLogicFactory) getComponent(SlipPanelLogicFactory.class);
		return SlipPanelLogicFactory.getLogic(slipType);
	}

	/**
	 * 伝票番号/修正回数で伝票が存在しているかチェック
	 * 
	 * @param kaiCode
	 * @param slipNo
	 * @param slipUpdCnt
	 * @throws TException
	 */
	public void checkSlipInfo(String kaiCode, String slipNo, int slipUpdCnt) throws TException {

		Connection conn = null;

		try {

			conn = DBUtil.getConnection();
			SQLCreator where = new SQLCreator();
			where.add("         AND    KAI_CODE = ? ", kaiCode);
			where.add("         AND    SWK_DEN_NO = ? ", slipNo);
			if (slipUpdCnt >= 0) {
				where.add("         AND    SWK_UPD_CNT = ? ", slipUpdCnt);
			}

			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT SUM(CNT) ");
			sql.add(" FROM   (SELECT COUNT(*) cnt ");
			sql.add("         FROM   GL_SWK_HDR ");
			sql.add("         WHERE  1 = 1 ");
			sql.add(where.toSQL());
			sql.add("         UNION ALL ");
			sql.add("         SELECT COUNT(*) cnt ");
			sql.add("         FROM   AP_SWK_HDR ");
			sql.add("         WHERE  1 = 1 ");
			sql.add(where.toSQL());
			sql.add("         UNION ALL ");
			sql.add("         SELECT COUNT(*) cnt ");
			sql.add("         FROM   AR_SWK_HDR ");
			sql.add("         WHERE  1 = 1 ");
			sql.add(where.toSQL());
			sql.add("        ) s ");
			int count = DBUtil.selectOneInt(conn, sql.toSQL());
			if (count == 0) {
				// 指定の伝票は他端末で更新されています。{0}
				throw new TRuntimeException("I00070", slipNo);
			}
		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 借入情報の更新
	 * 
	 * @param slip
	 * @param status
	 * @throws TException
	 */
	public void updateLMInfo(Slip slip, int status) throws TException {
		SWK_HDR hdr = slip.getHeader();
		SQLCreator sql = new SQLCreator();
		sql.add(" UPDATE GL_SWK_HDR");
		sql.add(" SET    SWK_CNF_STATUS = ? ", status);
		sql.add(" WHERE  KAI_CODE   = ? ", hdr.getKAI_CODE());
		sql.add("   AND  SWK_DEN_NO = ? ", hdr.getSWK_DEN_NO());
		sql.add("   AND  SWK_DEN_DATE = ? ", hdr.getSWK_DEN_DATE());

		DBUtil.execute(sql);

		sql = new SQLCreator();
		sql.add(" UPDATE AP_SWK_HDR");
		sql.add(" SET    SWK_CNF_STATUS = ? ", status);
		sql.add(" WHERE  KAI_CODE   = ? ", hdr.getKAI_CODE());
		sql.add("   AND  SWK_DEN_NO = ? ", hdr.getSWK_DEN_NO());
		sql.add("   AND  SWK_DEN_DATE = ? ", hdr.getSWK_DEN_DATE());

		DBUtil.execute(sql);

		sql = new SQLCreator();
		sql.add(" UPDATE AR_SWK_HDR");
		sql.add(" SET    SWK_CNF_STATUS = ? ", status);
		sql.add(" WHERE  KAI_CODE   = ? ", hdr.getKAI_CODE());
		sql.add("   AND  SWK_DEN_NO = ? ", hdr.getSWK_DEN_NO());
		sql.add("   AND  SWK_DEN_DATE = ? ", hdr.getSWK_DEN_DATE());

		DBUtil.execute(sql);

	}

	/**
	 * CM_FUND_DTL情報の削除
	 * 
	 * @param kaiCode
	 * @param slipDate
	 * @param slipNo
	 * @throws TException
	 */
	public void deleteCmFundInfo(String kaiCode, Date slipDate, String slipNo) throws TException {

		// 削除共通
		VCreator sql = new VCreator();
		sql.add(" DELETE FROM CM_FUND_DTL ");
		sql.add(" WHERE 1 = 1 ");
		if (kaiCode != null) {
			sql.add(" AND   KAI_CODE     = ? ", kaiCode);
		}
		if (slipDate != null) {
			sql.add(" AND   KEY_DEN_DATE = ? ", slipDate);
		}
		sql.add(" AND   KEY_DEN_NO   = ? ", slipNo);
		sql.add(" AND   DATA_TYPE    = 0 ");
		DBUtil.execute(sql);

		sql = new VCreator();
		sql.add(" DELETE FROM CM_FUND_DTL ");
		sql.add(" WHERE 1 = 1 ");
		if (kaiCode != null) {
			sql.add(" AND   KAI_CODE     = ? ", kaiCode);
		}
		if (slipDate != null) {
			sql.add(" AND   KEY_DEN_DATE = ? ", slipDate);
		}
		sql.add(" AND   KEY_DEN_NO   = ? ", slipNo);
		sql.add(" AND   DATA_TYPE    = 1 ");
		DBUtil.execute(sql);

	}

	/**
	 * CM_FUND_DTL情報の更新
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void entryCmFundInfo(Slip slip) throws TException {

		String kaiCode = slip.getCompanyCode();
		Date slipDate = slip.getSlipDate();
		String slipNo = slip.getSlipNo();

		SWK_HDR hdr = slip.getHeader();

		CompanyManager cmpMn = get(CompanyManager.class);
		Company cmp = cmpMn.get(kaiCode);
		String keyCurCode = cmp.getAccountConfig().getKeyCurrency().getCode();

		BigDecimal inKin = BigDecimal.ZERO;
		BigDecimal kin = BigDecimal.ZERO;
		String tek = hdr.getSWK_TEK();
		String curCode = null;
		Date baseDate = null;

		RateManager rateMn = get(RateManager.class);
		BankAccount bnkAcc = null;
		// 銀行口座取得
		BankAccountManager baMn = get(BankAccountManager.class);
		BankAccountSearchCondition baCondition = null;

		CurrencyManager curMn = get(CurrencyManager.class);
		CurrencySearchCondition curCondition = null;

		VCreator sql = null;

		String denSyuCode = hdr.getSWK_DEN_SYU();
		String denSyuName = hdr.getSWK_DEN_SYU_NAME();
		if (Util.isNullOrEmpty(denSyuName)) {
			SlipTypeManager stMn = get(SlipTypeManager.class);
			SlipType slipType = stMn.get(kaiCode, denSyuCode);
			denSyuName = slipType == null ? "" : slipType.getName();

		}
		String sysKbn = hdr.getSWK_SYS_KBN();

		CustomerManager cusMn = get(CustomerManager.class);
		CustomerSearchCondition cusCnd = null;
		DepartmentManager depMn = get(DepartmentManager.class);
		DepartmentSearchCondition depCnd = null;

		// AP/ARはヘッダー登録
		// 銀行口座コード<>NULL＆付替=0、データ区分23,31,32 → APAR対象
		if (!Util.isNullOrEmpty(hdr.getSWK_CBK_CODE()) && hdr.getSWK_TUKE_KBN() == 0 //
			&& (Util.equals(hdr.getSWK_DATA_KBN(), "23") || //
				Util.equals(hdr.getSWK_DATA_KBN(), "31") || //
				Util.equals(hdr.getSWK_DATA_KBN(), "32"))) {

			// 名称事前セット
			String triName = hdr.getSWK_TRI_NAME();
			if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE()) && Util.isNullOrEmpty(triName)) {
				cusCnd = new CustomerSearchCondition();
				cusCnd.setCompanyCode(kaiCode);
				cusCnd.setCode(hdr.getSWK_TRI_CODE());
				List<Customer> cusList = cusMn.get(cusCnd);
				if (cusList.size() > 0) {
					triName = cusList.get(0).getName();
				}
			}
			String knrName = hdr.getSWK_DEP_NAME();
			if (!Util.isNullOrEmpty(hdr.getSWK_DEP_CODE()) && Util.isNullOrEmpty(knrName)) {
				depCnd = new DepartmentSearchCondition();
				depCnd.setCompanyCode(kaiCode);
				depCnd.setCode(hdr.getSWK_DEP_CODE());
				List<Department> depList = depMn.get(depCnd);
				if (depList.size() > 0) {
					knrName = depList.get(0).getName();
				}
			}
			// ヘッダー口座通貨から通貨取得
			curCode = hdr.getSWK_CBK_CODE();
			// 銀行口座通貨取得
			if (!Util.isNullOrEmpty(curCode)) {
				baCondition = new BankAccountSearchCondition();
				baCondition.setCompanyCode(kaiCode);
				baCondition.setCode(curCode);
				List<BankAccount> list = baMn.get(baCondition);
				if (list.size() > 0) {
					bnkAcc = list.get(0);
				}
			}
			curCode = bnkAcc != null ? bnkAcc.getCurrencyCode() : hdr.getSWK_CUR_CODE();

			if (Util.equals(hdr.getSWK_DATA_KBN(), "31") || Util.equals(hdr.getSWK_DATA_KBN(), "32")) {
				// AR系は入金予定日
				baseDate = hdr.getSWK_AR_DATE() != null ? hdr.getSWK_AR_DATE() : slipDate;
				inKin = hdr.getSWK_IN_KIN();
				kin = hdr.getSWK_KIN();
			} else {
				// AP系は支払予定日
				baseDate = hdr.getSWK_SIHA_DATE() != null ? hdr.getSWK_SIHA_DATE() : slipDate;
				// 支払は金額符号反転
				inKin = DecimalUtil.avoidNull(hdr.getSWK_IN_SIHA_KIN()).negate();
				kin = DecimalUtil.avoidNull(hdr.getSWK_SIHA_KIN()).negate();
			}

			if (Util.equals(curCode, hdr.getSWK_CUR_CODE())) {
				// 口座通貨と取引通貨が同一 はそのまま

			} else if (Util.equals(curCode, keyCurCode)) {
				// 口座通貨と基軸通貨が同一
				inKin = kin;
			} else {
				// 以外は計算より求める
				BigDecimal rate = rateMn.getRate(curCode, baseDate);

				Currency currency = null;
				curCondition = new CurrencySearchCondition();
				curCondition.setCompanyCode(kaiCode);
				curCondition.setCode(curCode);
				List<Currency> curList = curMn.get(curCondition);
				if (curList.size() > 0) {
					currency = curList.get(0);
				}

				inKin = convertToForeign(kin, rate, currency.getRatePow(), currency.getDecimalPoint(), cmp);
			}

			// ヘッダー登録
			sql = new VCreator();
			sql.add(" INSERT INTO CM_FUND_DTL (");
			sql.add("     KAI_CODE ");
			sql.add("    ,DEN_DATE ");
			sql.add("    ,DEN_NO ");
			sql.add("    ,TRI_CODE ");
			sql.add("    ,TRI_NAME ");
			sql.add("    ,KNR_CODE ");
			sql.add("    ,KNR_NAME ");
			sql.add("    ,TEK ");
			sql.add("    ,DEN_SYU_CODE ");
			sql.add("    ,DEN_SYU_NAME ");
			sql.add("    ,CUR_CODE ");
			sql.add("    ,ZAN_KIN ");
			sql.add("    ,ZAN_IN_KIN ");
			sql.add("    ,CBK_CODE ");
			sql.add("    ,DATA_KBN ");
			sql.add("    ,SYS_KBN ");
			sql.add("    ,DATA_TYPE ");
			sql.add("    ,KEY_DEN_DATE ");
			sql.add("    ,KEY_DEN_NO ");
			sql.add("    ,INP_DATE ");
			sql.add("    ,UPD_DATE ");
			sql.add("    ,PRG_ID ");
			sql.add("    ,USR_ID ");
			sql.add(" ) VALUES ( ");
			sql.add("     ? ", kaiCode);
			sql.add("    ,? ", baseDate);
			sql.add("    ,? ", slipNo);
			sql.add("    ,? ", hdr.getSWK_TRI_CODE());
			sql.add("    ,? ", triName);
			sql.add("    ,? ", hdr.getSWK_DEP_CODE());
			sql.add("    ,? ", knrName);
			sql.add("    ,? ", tek);
			sql.add("    ,? ", denSyuCode);
			sql.add("    ,? ", denSyuName);
			sql.add("    ,? ", curCode);
			sql.add("    ,? ", kin);
			sql.add("    ,? ", inKin);
			sql.add("    ,? ", hdr.getSWK_CBK_CODE());
			sql.add("    ,0 "); // DATA_KBN
			sql.add("    ,? ", hdr.getSWK_SYS_KBN());
			sql.add("    ,0 "); // DATA_TYPE
			sql.add("    ,? ", slipDate);
			sql.add("    ,? ", slipNo);
			sql.adt("    ,? ", Util.isNullOrEmpty(hdr.getSWK_INP_DATE()) ? hdr.getINP_DATE() : hdr.getSWK_INP_DATE());
			sql.adt("    ,? ", hdr.getUPD_DATE());
			sql.add("    ,? ", hdr.getPRG_ID());
			sql.add("    ,? ", hdr.getUSR_ID());
			sql.add(" ) ");

			DBUtil.execute(sql);
		}

		// 明細共通部構築
		VCreator sqlIns = new VCreator();
		sqlIns.add(" INSERT INTO CM_FUND_DTL ( ");
		sqlIns.add("      KAI_CODE ");
		sqlIns.add("     ,DEN_DATE ");
		sqlIns.add("     ,DEN_NO ");
		sqlIns.add("     ,TRI_CODE ");
		sqlIns.add("     ,TRI_NAME ");
		sqlIns.add("     ,KNR_CODE ");
		sqlIns.add("     ,KNR_NAME ");
		sqlIns.add("     ,TEK ");
		sqlIns.add("     ,DEN_SYU_CODE ");
		sqlIns.add("     ,DEN_SYU_NAME ");
		sqlIns.add("     ,CUR_CODE ");
		sqlIns.add("     ,ZAN_KIN ");
		sqlIns.add("     ,ZAN_IN_KIN ");
		sqlIns.add("     ,CBK_CODE ");
		sqlIns.add("     ,DATA_KBN ");
		sqlIns.add("     ,SYS_KBN ");
		sqlIns.add("     ,DATA_TYPE ");
		sqlIns.add("     ,KEY_DEN_DATE ");
		sqlIns.add("     ,KEY_DEN_NO ");
		sqlIns.add("     ,KEY_GYO_NO ");
		sqlIns.add("     ,INP_DATE ");
		sqlIns.add("     ,UPD_DATE ");
		sqlIns.add("     ,PRG_ID ");
		sqlIns.add("     ,USR_ID ");
		sqlIns.add(" ) VALUES ( ");

		// 銀行口座取得
		Map<String, BankAccount> bnkAccMap = new HashMap<String, BankAccount>();

		// 科目Key
		String key = null;

		// 基準日
		baseDate = slipDate;
		for (SWK_DTL dtl : slip.getDetails()) {
			// BOOK_NO=2はスルー
			if (dtl.getSWK_BOOK_NO() == 2) {
				continue;
			}
			// 資金科目じゃなかったら戻る
			if (dtl.getItem() != null && dtl.getItem().getGlType() != GLType.FUND) {
				continue;
			}
			// 科目Key生成
			if (!isUseCmEntryAcctNo) {
				// 預金番号比較は毎回取得させる
				key = dtl.getSWK_KMK_CODE() + "<>" + Util.avoidNull(dtl.getSWK_HKM_CODE()) + "<>"
					+ Util.avoidNull(dtl.getSWK_UKM_CODE());
			}

			// 初期化
			bnkAcc = null;
			// 銀行口座取得
			if (key != null && bnkAccMap.containsKey(key)) {
				bnkAcc = bnkAccMap.get(key);

			} else {
				// 科目でマッチングさせる
				baCondition = new BankAccountSearchCondition();
				baCondition.setCompanyCode(dtl.getKAI_CODE());
				baCondition.setItemCode(dtl.getSWK_KMK_CODE());
				if (isUseCmEntryAcctNo) {
					baCondition.setAccountNo(StringUtil.leftBX(Util.avoidNull(dtl.getSWK_HKM_CODE()), 7));
				} else {
					baCondition.setSubItemCode(Util.avoidNull(dtl.getSWK_HKM_CODE()));
				}
				baCondition.setDetailItemCode(Util.avoidNull(dtl.getSWK_UKM_CODE()));
				bnkAcc = baMn.getBankAccount(baCondition);
				if (key != null && bnkAcc != null) {
					bnkAccMap.put(key, bnkAcc);
				}
			}
			// 口座が無ければスルー
			if (bnkAcc == null) {
				continue;
			}
			// 銀行口座 通貨
			curCode = bnkAcc.getCurrencyCode();

			inKin = dtl.getSWK_IN_KIN();
			kin = dtl.getSWK_KIN();

			if (Util.equals(curCode, dtl.getSWK_CUR_CODE())) {
				// 口座通貨と取引通貨が同一 → そのまま

			} else if (Util.equals(curCode, keyCurCode)) {
				// 取引通貨と基軸通貨が同一
				inKin = kin;
			} else {
				// 以外は計算より求める
				BigDecimal rate = rateMn.getRate(curCode, baseDate);

				inKin = convertToForeign(kin, rate, bnkAcc.getCurrency().getRatePow(),
					bnkAcc.getCurrency().getDecimalPoint(), cmp);
			}

			// 名称設定
			String triName = dtl.getSWK_TRI_NAME();
			if (!Util.isNullOrEmpty(dtl.getSWK_TRI_CODE()) && Util.isNullOrEmpty(triName)) {
				cusCnd = new CustomerSearchCondition();
				cusCnd.setCompanyCode(kaiCode);
				cusCnd.setCode(dtl.getSWK_TRI_CODE());
				List<Customer> cusList = cusMn.get(cusCnd);
				if (cusList.size() > 0) {
					triName = cusList.get(0).getName();
				}
			}
			String knrName = dtl.getSWK_DEP_NAME();
			if (!Util.isNullOrEmpty(dtl.getSWK_DEP_CODE()) && Util.isNullOrEmpty(knrName)) {
				depCnd = new DepartmentSearchCondition();
				depCnd.setCompanyCode(kaiCode);
				depCnd.setCode(dtl.getSWK_DEP_CODE());
				List<Department> depList = depMn.get(depCnd);
				if (depList.size() > 0) {
					knrName = depList.get(0).getName();
				}
			}

			// 登録共通
			sql = new VCreator();
			sql.add(sqlIns);

			sql.add("     ? ", kaiCode);
			sql.add("    ,? ", slipDate);
			sql.add("    ,? ", slipNo);
			sql.add("    ,? ", dtl.getSWK_TRI_CODE());
			sql.add("    ,? ", triName);
			sql.add("    ,? ", dtl.getSWK_DEP_CODE());
			sql.add("    ,? ", knrName);
			sql.add("    ,? ", dtl.getSWK_GYO_TEK() != null ? dtl.getSWK_GYO_TEK() : tek);
			sql.add("    ,? ", denSyuCode);
			sql.add("    ,? ", denSyuName);
			sql.add("    ,? ", bnkAcc.getCurrencyCode());
			if (dtl.isDR()) {
				sql.add("    ,? ", kin);
				sql.add("    ,? ", inKin);
			} else {
				sql.add("    ,? * -1 ", kin);
				sql.add("    ,? * -1 ", inKin);
			}
			sql.add("    , ? ", bnkAcc.getCode());
			sql.add("    , 1 "); // DATA_KBN
			sql.add("    , ?", sysKbn);
			sql.add("    , 1 "); // DATA_TYPE
			sql.add("    ,? ", slipDate);
			sql.add("    ,? ", slipNo);
			sql.add("    ,? ", dtl.getSWK_GYO_NO());
			sql.adt("    ,? ", dtl.getINP_DATE());
			sql.adt("    ,? ", dtl.getUPD_DATE());
			sql.add("    ,? ", dtl.getPRG_ID());
			sql.add("    ,? ", dtl.getUSR_ID());
			sql.add(" ) ");
			DBUtil.execute(sql);

		}

	}

	/**
	 * 基軸通貨→入力金額
	 * 
	 * @param keyAmount 基軸通貨金額
	 * @param rate レート
	 * @param ratePow 外貨通貨レート係数
	 * @param decimalPoints 外貨通貨小数点以下桁数
	 * @param company
	 * @return 入力金額
	 */
	public BigDecimal convertToForeign(BigDecimal keyAmount, BigDecimal rate, int ratePow, int decimalPoints,
		Company company) {

		if (rate == null) {
			return null;
		}

		if (keyAmount == null) {
			return null;
		}

		if (DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		if (DecimalUtil.isNullOrZero(keyAmount)) {
			return BigDecimal.ZERO;
		}

		AccountConfig conf = company.getAccountConfig();
		ExchangeFraction frac = conf.getExchangeFraction();

		// 換算
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(frac);
		param.setConvertType(conf.getConvertType());
		param.setDigit(decimalPoints);
		param.setKeyAmount(keyAmount);
		param.setRate(rate);
		param.setRatePow(ratePow);

		return calculator.exchangeForeignAmount(param);
	}

	/**
	 * SQL用
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * コンストラクター
		 */
		public VCreator() {
			crlf = " ";
		}
	}

	/**
	 * 伝票をチェックして、承認
	 * 
	 * @param den
	 * @param isAsMuchAsPossible
	 * @return 承認後伝票
	 * @throws TException
	 */
	public SlipDen checkAndApproveSlip(SlipDen den, Boolean isAsMuchAsPossible) throws TException {

		isApproveAsMuchAsPossible = isAsMuchAsPossible;
		// 伝票関連テーブルローク
		lockSlipTable();

		// サーバーでSWK_HDRを取得
		SlipCondition condition = createSlipCondition();
		condition.setSlipNo(den.getSWK_DEN_NO());
		condition.setCompanyCode(getCompanyCode());
		AccountConfig ac = getCompany().getAccountConfig();
		condition.setSearchWorkFlow(ac.isUseWorkflowApprove());
		List<SWK_HDR> lists = getHeader(condition);
		if (lists == null || lists.isEmpty()) {
			// 対象伝票無し : 他ユーザーが削除または承認により
			// 非存在もしくは権限非対象になっている
			// 伝票番号{0}は他ユーザーが編集中、または日次更新により承認できませんでした。
			throw new TException("I00191", den.getSWK_DEN_NO());
		}
		SWK_HDR hdr = lists.get(0);

		// 伝票区分もう更新(4)になっている。
		if (hdr.getSWK_UPD_KBN() == SlipState.UPDATE.value) {
			// 指定の伝票は更新されています。
			throw new TException("W00126");
		}
		// 画面伝票区分とサーバー側伝票区分が違っている。
		if (!(hdr.getSWK_UPD_KBN() == den.getSWK_UPD_KBN())) {
			// 他のユーザーにより更新されています。再度検索して画面を開き直してください。
			throw new TException("I01076");
		}

		SWK_SYO_CTL syo_CTL = hdr.getSyoCtl();

		// NEXT_APRV_ROLE_CODEはNULL
		if (syo_CTL.getNEXT_APRV_ROLE_CODE() == null) {
			throw new TException("I01076");
		}
		AprvRoleManager dao = (AprvRoleManager) getComponent(AprvRoleManager.class);
		AprvRoleSearchCondition aprvCon = new AprvRoleSearchCondition();
		aprvCon.setCompanyCode(getCompanyCode());
		aprvCon.setUserCode(getUserCode());
		aprvCon.setCode(syo_CTL.getNEXT_APRV_ROLE_CODE());
		List<AprvRole> role = dao.get(aprvCon);
		if (Util.isNullOrEmpty(role)) {
			throw new TException("I01076");
		}

		// 承認後状態取得
		SlipDen toApproveSlipDen = getDen(hdr, den, SYO_FLG.APPROVE);

		// 承認
		List<SlipDen> tempDens = new ArrayList<SlipDen>();
		tempDens.add(toApproveSlipDen);
		List<SlipDen> beans = approveSlip(tempDens);

		return beans.get(0);
	}

	/**
	 * 伝票をチェックして、取消
	 * 
	 * @param den
	 * @return 承認取消後の伝票
	 * @throws TException
	 */
	public SlipDen checkAndCancelApprovedSlip(SlipDen den) throws TException {
		// 伝票関連テーブルローク
		lockSlipTable();

		// サーバーでSWK_HDRを取得
		SlipCondition condition = createSlipCondition();
		condition.setSlipNo(den.getSWK_DEN_NO());
		condition.setCompany(getCompany());
		AccountConfig ac = getCompany().getAccountConfig();
		condition.setSearchWorkFlow(ac.isUseWorkflowApprove());
		List<SWK_HDR> lists = getHeader(condition);
		if (lists == null || lists.isEmpty()) {
			// 対象伝票無し : 他ユーザーが削除または承認により
			// 非存在もしくは権限非対象になっている
			// 指定の伝票は他端末で処理中のため更新できません。{0}
			throw new TException("I00069");
		}
		SWK_HDR hdr = lists.get(0);

		// 伝票区分もう更新(4)になっている
		if (hdr.getSWK_UPD_KBN() == SlipState.UPDATE.value) {
			// 指定の伝票は更新されています。
			throw new TException("W00126");
		}

		// 画面伝票区分とサーバー側伝票区分が違っている。
		if (!(hdr.getSWK_UPD_KBN() == den.getSWK_UPD_KBN())) {
			// 他のユーザーにより更新されています。再度検索して画面を開き直してください。
			throw new TException("I01076");
		}

		// 取消権限あるかどうか
		SWK_SYO_CTL syo_CTL = hdr.getSyoCtl();
		AprvRoleManager dao = (AprvRoleManager) getComponent(AprvRoleManager.class);
		AprvRoleSearchCondition aprvCon = new AprvRoleSearchCondition();
		aprvCon.setCompanyCode(getCompanyCode());
		aprvCon.setUserCode(getUserCode());
		aprvCon.setCode(syo_CTL.getAPRV_ROLE_CODE());
		List<AprvRole> role = dao.get(aprvCon);
		if (Util.isNullOrEmpty(role)) {
			throw new TException("I01076");
		}

		// 取消後状態を取得
		SlipDen tocancelApproveSlipDen = getDen(hdr, den, SYO_FLG.CANCEL);

		// 取消
		List<SlipDen> slipDens = new ArrayList<SlipDen>();
		slipDens.add(tocancelApproveSlipDen);
		List<SlipDen> beans = cancelApproveSlip(slipDens);

		return beans.get(0);

	}

	/**
	 * 指定行の伝票情報の取得<br>
	 * 対象伝票行に対して承認者情報を更新<br>
	 * 設定されている承認グループ内で、次の承認ロールをNEXT_ROLEに設定
	 * 
	 * @param hdr
	 * @param den
	 * @param flg
	 * @return 伝票情報
	 */
	protected SlipDen getDen(SWK_HDR hdr, SlipDen den, int flg) {

		SWK_SYO_CTL ctl = hdr.getSyoCtl();
		if (ctl == null) {
			return den;
		}
		if (flg == SYO_FLG.APPROVE) {
			String nextRoleCode = getNextAprvRoleCode(hdr.getSWK_APRV_GRP_CODE(), ctl.getNEXT_APRV_ROLE_CODE());
			String roleCode = getPrevAprvRoleCode(hdr.getSWK_APRV_GRP_CODE(), nextRoleCode);
			den.setAPRV_ROLE_CODE(roleCode);
			den.setNEXT_APRV_ROLE_CODE(nextRoleCode);
			if (Util.isNullOrEmpty(nextRoleCode)) {
				den.setACCT_APRV_FLG(true);
			}
		} else if (flg == SYO_FLG.CANCEL) {
			String roleCode;
			String nextRoleCode;
			if (getCompany().getAccountConfig().isBackFirstWhenWorkflowDeny()) {
				nextRoleCode = getFirstAprvRoleCode(hdr.getSWK_APRV_GRP_CODE());
				roleCode = null;
			} else {
				// 通常（承認を一つ戻す場合
				nextRoleCode = ctl.getAPRV_ROLE_CODE();
				roleCode = getPrevAprvRoleCode(hdr.getSWK_APRV_GRP_CODE(), nextRoleCode);
			}
			if (!Util.isNullOrEmpty(roleCode)) {
				// 承認途中の場合、経理承認→現場承認と制御させるため
				den.setACCT_APRV_FLG(true);
			}
			den.setAPRV_ROLE_CODE(roleCode);
			den.setNEXT_APRV_ROLE_CODE(nextRoleCode);
		}
		return den;
	}

	/**
	 * 指定グループ / 指定ロールコード内での次の承認ロールコードを取得する
	 * 
	 * @param grpCode
	 * @param roleCode
	 * @return 次のロールコード
	 */
	protected String getNextAprvRoleCode(String grpCode, String roleCode) {
		try {
			AprvRoleGroup grp = getAprvRoleGroup(grpCode);
			if (grp == null) {
				return null;
			}
			if (roleCode == null) {
				return null;
			}
			int nowLevel = 0;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (Util.equals(dtl.getAPRV_ROLE_CODE(), roleCode)) {
					nowLevel = dtl.getAPRV_LEVEL();
				}
			}
			AprvRoleGroupDetail nextRole = null;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (nowLevel >= dtl.getAPRV_LEVEL()) {
					// 現在レベルよりも手前：対象外
					continue;
				}
				nextRole = dtl;
				if (!isApproveAsMuchAsPossible) {
					// 通常の進め方の場合、最初に該当したロールコード
					break;
				}
				// 進められるところまで進む場合
				if (!hasPermission(dtl.getAPRV_ROLE_CODE())) {
					// 権限を保持しない場合終了
					break;
				}
				// 再帰処理
				return getNextAprvRoleCode(grpCode, nextRole.getAPRV_ROLE_CODE());
			}
			if (nextRole == null) {
				return null;
			}
			return nextRole.getAPRV_ROLE_CODE();
		} catch (TException e) {
			return null;
		}
	}

	/**
	 * コードで指定の承認グループを取得
	 * 
	 * @param code
	 * @return 承認グループ
	 * @throws TException
	 */
	protected AprvRoleGroup getAprvRoleGroup(String code) throws TException {
		List<AprvRoleGroup> list = getAllGroup();
		if (list == null) {
			return null;
		}
		for (AprvRoleGroup grp : list) {
			if (Util.equals(grp.getAPRV_ROLE_GRP_CODE(), code)) {
				return grp;
			}
		}
		return null;
	}

	/**
	 * 存在するすべての承認グループを取得
	 * 
	 * @return すべての承認グループ
	 * @throws TException
	 */
	protected List<AprvRoleGroup> getAllGroup() throws TException {
		if (grpList != null) {
			return grpList;
		}
		AprvRoleGroupSearchCondition con = new AprvRoleGroupSearchCondition();
		con.setCompanyCode(getCompanyCode());
		AprvRoleGroupManager dao = (AprvRoleGroupManager) getComponent(AprvRoleGroupManager.class);
		grpList = dao.get(con);

		return grpList;
	}

	/**
	 * 指定のロールに対して権限を保持するか
	 * 
	 * @param roleCode 承認ロールコード
	 * @return true:権限を保持している
	 */
	protected boolean hasPermission(String roleCode) {
		try {
			AprvRole role = getAprvRole(roleCode);
			if (role == null) {
				return false;
			}
			for (User usr : role.getUserList()) {
				// ロール保有のユーザーとログインユーザーが一致の場合権限保有
				if (Util.equals(getUserCode(), usr.getCode())) {
					return true;
				}
			}
		} catch (TException e) {
			//
			return false;
		}
		return false;
	}

	/**
	 * 存在するすべての承認ロールを取得
	 * 
	 * @return すべての承認ロール
	 * @throws TException
	 */
	protected List<AprvRole> getAllRole() throws TException {
		if (roleList != null) {
			return roleList;
		}
		AprvRoleSearchCondition con = new AprvRoleSearchCondition();
		con.setCompanyCode(getCompanyCode());
		AprvRoleManager dao = (AprvRoleManager) getComponent(AprvRoleManager.class);
		roleList = dao.get(con);
		return roleList;
	}

	/**
	 * コードで指定の承認ロールを取得
	 * 
	 * @param code
	 * @return 承認ロール
	 * @throws TException
	 */
	protected AprvRole getAprvRole(String code) throws TException {
		List<AprvRole> list = getAllRole();
		if (list == null) {
			return null;
		}
		for (AprvRole role : list) {
			if (Util.equals(role.getAPRV_ROLE_CODE(), code)) {
				return role;
			}
		}
		return null;
	}

	/**
	 * 指定グループ / 指定ロールコード内の１つ前の承認ロールコードを取得する
	 * 
	 * @param grpCode
	 * @param roleCode
	 * @return 次のロールコード
	 */
	protected String getPrevAprvRoleCode(String grpCode, String roleCode) {
		try {
			AprvRoleGroup grp = getAprvRoleGroup(grpCode);
			if (grp == null) {
				return null;
			}
			int nowLevel = 99;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (Util.equals(dtl.getAPRV_ROLE_CODE(), roleCode)) {
					nowLevel = dtl.getAPRV_LEVEL();
				}
			}
			AprvRoleGroupDetail prevRole = null;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (nowLevel <= dtl.getAPRV_LEVEL()) {
					// 現在レベルより大きい：対象外
					continue;
				}
				prevRole = dtl;
				if (prevRole.getAPRV_LEVEL() < dtl.getAPRV_LEVEL()) {
					prevRole = dtl;
				}
			}
			if (prevRole == null) {
				return null;
			}
			return prevRole.getAPRV_ROLE_CODE();
		} catch (TException e) {
			return null;
		}
	}

	/**
	 * 指定した承認グループの先頭のコードを取得
	 * 
	 * @param grpCode
	 * @return 指定した承認グループの先頭のコード
	 */
	protected String getFirstAprvRoleCode(String grpCode) {
		try {
			AprvRoleGroup grp = getAprvRoleGroup(grpCode);
			if (grp == null) {
				return null;
			}
			int nowLevel = 99;
			AprvRoleGroupDetail prevRole = null;
			for (AprvRoleGroupDetail dtl : grp.getDetailList()) {
				if (nowLevel <= dtl.getAPRV_LEVEL()) {
					// 現在レベルより大きい：対象外
					continue;
				}
				if (nowLevel > dtl.getAPRV_LEVEL()) {
					prevRole = dtl;
					nowLevel = dtl.getAPRV_LEVEL();
				}
			}
			if (prevRole == null) {
				return null;
			}
			return prevRole.getAPRV_ROLE_CODE();
		} catch (TException e) {
			return null;
		}
	}

}

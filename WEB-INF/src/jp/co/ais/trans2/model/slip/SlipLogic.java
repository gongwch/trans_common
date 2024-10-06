package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans2.common.model.report.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.bill.*;
import jp.co.ais.trans2.model.bs.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.management.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.slip.SWK_DTL.ZEI_KBN;
import jp.co.ais.trans2.model.slip.parts.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 伝票処理
 */
public abstract class SlipLogic extends TModel {

	/** true:グループ会計<Server> */
	public static boolean groupAccounting = ServerConfig.isFlagOn("trans.slip.group.accounting");

	/** true:BS勘定消込機能有効<Server> */
	public static boolean isUseBS = ServerConfig.isFlagOn("trans.slip.use.bs");

	/** true:ファイル添付機能有効<Server> */
	public static boolean isUseAttachment = ServerConfig.isFlagOn("trans.slip.use.attachment");

	/** true:BS勘定は計上会社コードの入力値を利用<Server> */
	public static boolean isBsUseKCompany = ServerConfig.isFlagOn("trans.slip.bs.use.kcompany");

	/** true:付箋機能有効 */
	public static boolean isUseTag = ServerConfig.isFlagOn("trans.slip.use.tag");

	/** true:付替勘定の通貨を相手会社の取引通貨にする<Server> */
	public static boolean isUseOppositCompanysCurrency = ServerConfig
		.isFlagOn("trans.slip.group.accounting.different.key.currency");

	/** 内部保持用セパレータ */
	protected static final String KEY_SEP = "<>";

	/** 伝票種別 */
	protected String slipType;

	/** データ区分 */
	protected String dataType;

	/**
	 * 伝票種別
	 * 
	 * @param slipType 伝票種別
	 */
	public void setSlipType(String slipType) {
		this.slipType = slipType;
	}

	/**
	 * 伝票種別取得
	 * 
	 * @return 伝票種別
	 * @throws TException
	 */
	public SlipType getSlipType() throws TException {
		return getSlipType(this.slipType);
	}

	/**
	 * 伝票種別
	 * 
	 * @param typeNo 伝票種別番号
	 * @return 伝票種別
	 * @throws TException
	 */
	public SlipType getSlipType(String typeNo) throws TException {
		SlipTypeManager slipTypeManager = (SlipTypeManager) getComponent(SlipTypeManager.class);
		SlipType type = slipTypeManager.get(getCompanyCode(), typeNo);

		if (type == null) {
			// 伝票種別[{0}]が設定されていません。
			throw new TException("I00128", typeNo);
		}

		return type;
	}

	/**
	 * データ区分
	 * 
	 * @param dataKind データ区分
	 */
	public void setDataType(String dataKind) {
		this.dataType = dataKind;
	}

	/**
	 * 伝票を構築する.<br>
	 * 伝票番号採番、各種値設定、会社間付替、消費税仕訳発生、機能通貨仕訳作成
	 * 
	 * @param slip 伝票クラス
	 * @return 構築した伝票(付替先含め)
	 * @throws TException
	 */
	public List<Slip> buildSlip(Slip slip) throws TException {

		// ベースとなる伝票情報を形成(伝票番号採番、各種値設定)
		buildBaseSlip(slip);

		// 付替がある場合 会社間付替仕訳を作成する
		List<Slip> slipList = transferCompanySlip(slip);

		// 付け替え会社跨いで行番号が連番なので、追加の消費税仕訳も連番に
		int maxRowNum = 0;
		for (Slip tslip : slipList) {
			maxRowNum += tslip.getDetails().size();
		}

		// 付替会社ごと
		for (Slip tslip : slipList) {

			// 機能通貨仕訳(伝票)作成
			List<SWK_DTL> funcList = createFunctionalJournal(tslip);

			// 自国 消費税仕訳
			if (!tslip.isJournalizedTax()) {
				maxRowNum = addTaxJournal(tslip, maxRowNum);

			} else if (slip.isFromOtherSystem()) {
				// 他システムの場合は経過措置判定追加
				maxRowNum = addTransferTaxJournal(tslip, maxRowNum);
			}

			// 機能通貨仕訳を追加
			tslip.getDetails().addAll(funcList);

			// 相手科目を設定
			tslip.matchingItem();
		}

		for (Slip tslip : slipList) {
			// 年度、月度
			int fiscalYear = BizUtil.getFiscalYear(tslip.getSlipDate(), tslip.getCompanyCode());
			int fiscalMonth = BizUtil.getFiscalMonth(tslip.getSlipDate(), tslip.getCompanyCode());

			// 明細との同期あわせ
			tslip.synchDetails(fiscalYear, fiscalMonth);
		}

		return slipList;
	}

	/**
	 * 伝票を構築する.<br>
	 * 伝票番号採番、各種値設定
	 * 
	 * @param slip 伝票クラス
	 * @throws TException
	 */
	public void buildBaseSlip(Slip slip) throws TException {

		// 伝票整形
		SWK_HDR hdr = slip.getHeader();
		boolean isNew = false;

		// 伝票番号がブランクの場合、伝票番号を採番
		if (Util.isNullOrEmpty(hdr.getSWK_DEN_NO())) {

			// TODO 暫定対応
			if (Util.isNullOrEmpty(hdr.getSWK_IRAI_DEP_CODE())) {
				slip.setIssuer(getUser());
			}

			String slipNo = this.newSlipNo(slip);

			// 伝票番号が20桁超えたらエラー
			if (slipNo.length() > 20) {
				throw new TException("W00159");
			}

			hdr.setSWK_DEN_NO(slipNo);// 伝票番号
			isNew = true;
		}

		slip.synchDetails();

		// 必須チェック
		if (!slip.isAvoidReuiredItemNULL()) {
			checkNull(slip);
		}

		// 新規かどうか
		if (isNew) {
			hdr.setINP_DATE(getNow()); // 登録日
			hdr.setSWK_INP_DATE(getNow()); // 登録日

			if (hdr.getSWK_IRAI_DATE() == null) {
				hdr.setSWK_IRAI_EMP_CODE(getUser().getEmployee().getCode()); // 依頼者
				hdr.setSWK_IRAI_DEP_CODE(getUser().getDepartment().getCode()); // 依頼部門コード
				hdr.setSWK_IRAI_DATE(getNow()); // 依頼日- 現在日付
			}

		} else {
			// 更新データ変更
			hdr.setSWK_UPD_CNT(hdr.getSWK_UPD_CNT() + 1); // 修正回数
			hdr.setUPD_DATE(getNow()); // 更新日
		}

		// 更新区分 = 承認状態以外は値を初期化
		if (!hdr.getSlipState().equals(SlipState.APPROVE)) {
			hdr.setSWK_SYO_EMP_CODE(null); // 承認者
			hdr.setSWK_SYO_DATE(null); // 承認日
		}

		hdr.setUSR_ID(getUserCode()); // ユーザID
		hdr.setPRG_ID(getProgramCode()); // プログラムID

		// 更新時クリア
		for (SWK_DTL dtl : slip.getDetails()) {
			dtl.setSWK_KESI_DATE(null); // 消込伝票日付
			dtl.setSWK_KESI_DEN_NO(null); // 消込伝票番号
			dtl.setSWK_SOUSAI_DATE(null); // 相殺伝票日付
			dtl.setSWK_SOUSAI_DEN_NO(null); // 相殺伝票番号
		}
	}

	/**
	 * 伝票を起票する
	 * 
	 * @param slip 伝票クラス
	 */
	public void entry(Slip slip) {
		setEntryInfo(slip);

		SlipEntry logic = getSlipEntry();
		logic.setHeaderDao(getHeaderDao());
		logic.setDetailDao(getDetailDao());
		logic.entry(slip);
	}

	/**
	 * SlipEntry
	 * 
	 * @return SlipEntry
	 */
	protected abstract SlipEntry getSlipEntry();

	/**
	 * 登録情報(タイムスタンプ、プログラムID、ユーザーID)をセット
	 * 
	 * @param slip
	 */
	protected void setEntryInfo(Slip slip) {
		SWK_HDR header = slip.getHeader();

		if (header.getINP_DATE() == null) {
			header.setINP_DATE(getNow());
		}

		if (Util.isNullOrEmpty(header.getPRG_ID())) {
			header.setPRG_ID(getProgramCode());
		}

		if (Util.isNullOrEmpty(header.getUSR_ID())) {
			header.setUSR_ID(getUserCode());
		}

		for (SWK_DTL detail : slip.getDetails()) {
			if (detail.getINP_DATE() == null) {
				detail.setINP_DATE(getNow());
			}

			if (Util.isNullOrEmpty(detail.getPRG_ID())) {
				detail.setPRG_ID(getProgramCode());
			}

			if (Util.isNullOrEmpty(detail.getUSR_ID())) {
				detail.setUSR_ID(getUserCode());
			}
		}
	}

	/**
	 * 伝票のチェック
	 * 
	 * @param slip 伝票
	 * @return エラーメッセージリスト
	 * @throws TException
	 */
	public List<Message> checkSlipError(Slip slip) throws TException {

		// ヘッダ
		List<Message> list = getSlipHeaderChecker().checkHeader(slip);

		// 明細
		list.addAll(getJournalDetailChecker().checkDetail(slip));

		return list;
	}

	/**
	 * ヘッダチェックロジック
	 * 
	 * @return ヘッダチェックロジック
	 */
	protected HeaderCheck getSlipHeaderChecker() {
		return (HeaderCheck) getComponent(HeaderCheck.class);
	}

	/**
	 * 明細チェックロジック
	 * 
	 * @return 明細チェックロジック
	 */
	public JournalDetailCheck getJournalDetailChecker() {
		return (JournalDetailCheck) getComponent(JournalDetailCheck.class);
	}

	/**
	 * NULL不可項目がnull/ブランクになっていないかどうかのチェック.<br>
	 * 不正の場合、Exceptionを発生.
	 * 
	 * @param slip 対象伝票
	 * @throws TException
	 */
	protected void checkNull(Slip slip) throws TException {
		{
			String column = slip.getHeader().isReuiredItemNULL();
			if (!Util.isNullOrEmpty(column)) {
				// 伝票の{0}が不足しています。
				throw new TException("I00164", column);
			}
		}

		for (SWK_DTL detail : slip.getDetails()) {
			String column = detail.isRequiredItemNULL();
			if (!Util.isNullOrEmpty(column)) {
				// 伝票明細の{0}が不足しています。
				throw new TException("I00165", column);
			}
		}
	}

	/**
	 * 伝票番号を取得する
	 * 
	 * @param slip 伝票情報
	 * @return 伝票番号
	 */
	protected String newSlipNo(Slip slip) {
		SlipNoCreator creator = (SlipNoCreator) getComponent(SlipNoCreator.class);
		return creator.newSlipNo(slip);
	}

	/**
	 * 履歴保存
	 * 
	 * @param slipList 対象伝票リスト
	 */
	public void entryHistory(List<Slip> slipList) {
		SWK_HDRDao dhdao = getDeleteHeaderDao();
		SWK_DTLDao dddao = getDeleteDetailDao();

		for (Slip slip : slipList) {
			dhdao.insert(slip.getHeader());

			for (SWK_DTL dtl : slip.getDetails()) {
				dtl.setSWK_UPD_CNT(slip.getHeader().getSWK_UPD_CNT());
			}

			dddao.insert(slip.getDetails());
		}
	}

	/**
	 * 削除履歴保存
	 * 
	 * @param slipList 対象伝票リスト
	 */
	public void entryDeleteHistory(List<Slip> slipList) {

		DEL_DTLDao dao = getDeleteSlipDao();

		for (Slip slip : slipList) {
			SWK_HDR hdr = slip.getHeader();

			if (slip.isTransferSlip()) {
				continue;
			}

			DEL_DTL dtl = new DEL_DTL();
			dtl.setKAI_CODE(hdr.getKAI_CODE());
			dtl.setDEL_DEN_NO(hdr.getSWK_DEN_NO());
			dtl.setDEL_DEN_DATE(hdr.getSWK_DEN_DATE());
			dtl.setINP_DATE(Util.getCurrentDate());
			dtl.setPRG_ID(getProgramCode());
			dtl.setUSR_ID(getUserCode());

			dao.insert(dtl);
		}
	}

	/**
	 * 削除履歴の削除
	 * 
	 * @param slip 対象伝票
	 */
	protected void deleteDelHistory(Slip slip) {

		DEL_DTLDao dao = getDeleteSlipDao();

		SWK_HDR hdr = slip.getHeader();

		DEL_DTL dtl = new DEL_DTL();
		dtl.setKAI_CODE(hdr.getKAI_CODE());
		dtl.setDEL_DEN_NO(hdr.getSWK_DEN_NO());
		dtl.setDEL_DEN_DATE(hdr.getSWK_DEN_DATE());
		dtl.setINP_DATE(Util.getCurrentDate());
		dtl.setPRG_ID(getProgramCode());
		dtl.setUSR_ID(getUserCode());

		dao.delete(dtl);
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 伝票番号
	 */
	public void delete(String slipNo) {
		delete(slipNo, true);
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 伝票番号
	 * @param isSaveHistory 履歴を保存するかどうか
	 */
	public void delete(String slipNo, boolean isSaveHistory) {
		delete(slipNo, isSaveHistory, isSaveHistory);
	}

	/**
	 * 伝票を削除する
	 * 
	 * @param slipNo 伝票番号
	 * @param isSaveHistory 履歴を保存するかどうか
	 * @param isSaveDelHistory 削除履歴を保存するかどうか
	 */
	public void delete(String slipNo, boolean isSaveHistory, boolean isSaveDelHistory) {

		// 履歴保存
		if (isSaveHistory) {

			// 削除データの取得
			List<Slip> list = getSlip(slipNo);

			// 履歴
			entryHistory(list);

			if (isSaveDelHistory) {
				// 削除履歴
				entryDeleteHistory(list);
			}
		}

		SWK_HDRDao hdao = getHeaderDao();
		SWK_DTLDao ddao = getDetailDao();

		// ヘッダ削除
		SlipCondition hparam = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			hparam.setCompanyCode(getCompanyCode());
		}

		hparam.setSlipNo(slipNo);
		hdao.deleteByCondition(hparam);

		// 該当明細の全削除
		SlipCondition dparam = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			dparam.setCompanyCode(getCompanyCode());
		}

		dparam.setSlipNo(slipNo);
		ddao.deleteByCondition(dparam);
	}

	/**
	 * 伝票を取得する.(付替含む)
	 * 
	 * @param denNo 伝票番号
	 * @return 伝票リスト
	 */
	public List<Slip> getSlip(String denNo) {

		SlipCondition hparam = new SlipCondition();

		if (!getCompany().getAccountConfig().isUseGroupAccount()) {
			hparam.setCompanyCode(getCompanyCode());
		}

		hparam.setSlipNo(denNo);

		List<SWK_HDR> hlist = getHeader(hparam);

		List<Slip> list = new ArrayList<Slip>(hlist.size());

		for (SWK_HDR hdr : hlist) {
			SlipCondition dparam = new SlipCondition();
			dparam.setCompanyCode(hdr.getKAI_CODE());
			dparam.setSlipNo(denNo);
			List<SWK_DTL> dlist = getDetailDao().findByCondition(dparam);

			Slip slip = createSlip(hdr);
			slip.setDetails(dlist);

			list.add(slip);
		}

		return list;
	}

	/**
	 * 新規伝票の作成
	 * 
	 * @return 伝票
	 */
	public abstract Slip createSlip();

	/**
	 * 新規伝票の作成
	 * 
	 * @param hdr 伝票ヘッダ
	 * @return 伝票
	 */
	public Slip createSlip(SWK_HDR hdr) {
		Slip slip = createSlip();
		slip.setHeader(hdr);
		return slip;
	}

	/**
	 * 伝票ヘッダを取得する
	 * 
	 * @param param
	 * @return 伝票ヘッダ
	 */
	public List<SWK_HDR> getHeader(SlipCondition param) {
		return getHeaderDao().findByCondition(param.toSQL());
	}

	/**
	 * 伝票ヘッダを取得する
	 * 
	 * @param param
	 * @return 伝票ヘッダ
	 */
	public List<SWK_HDR> getPatternHeader(SlipCondition param) {
		return getPatternHeaderDao().findByCondition(param.toSQL());
	}

	/**
	 * 伝票ヘッダを取得する
	 * 
	 * @param param
	 * @return 伝票ヘッダ
	 */
	public List<SWK_HDR> getHistoryHeader(SlipCondition param) {
		return getDeleteHeaderDao().findByCondition(param.toSQL());
	}

	/**
	 * ヘッダを元に伝票を構築する.
	 * 
	 * @param hdr 伝票ヘッダ
	 * @return 仕訳リスト
	 */
	public Slip getSlip(SWK_HDR hdr) {
		Slip slip = createSlip(hdr);

		SlipCondition param = new SlipCondition();
		param.setCompanyCode(hdr.getKAI_CODE());
		param.setSlipNo(hdr.getSWK_DEN_NO());
		slip.setDetails(getDetailDao().findByCondition(param));

		return slip;
	}

	/**
	 * @return true:グループ会計(基軸通貨異なる計上会社可)
	 */
	public static boolean isGroupAccounting() {
		return groupAccounting;
	}

	/**
	 * 会社間付替処理
	 * 
	 * @param slip 伝票
	 * @return 付替え後の伝票リスト
	 * @throws TException
	 */
	protected List<Slip> transferCompanySlip(Slip slip) throws TException {
		if (isGroupAccounting()) {
			if (isUseOppositCompanysCurrency) {
				// グループ会計で取引通貨が異なる場合
				GroupCompanyTransferDifferentCurrency compTransfer = (GroupCompanyTransferDifferentCurrency) getComponent(
					GroupCompanyTransferDifferentCurrency.class);
				return compTransfer.transfer(slip);
			} else {
				// グループ会計の場合
				GroupCompanyTransfer compTransfer = (GroupCompanyTransfer) getComponent(GroupCompanyTransfer.class);
				return compTransfer.transfer(slip);
			}
		} else {
			CompanyTransfer compTransfer = (CompanyTransfer) getComponent(CompanyTransfer.class);
			return compTransfer.transfer(slip);
		}
	}

	/**
	 * 機能通貨仕訳(伝票)の作成
	 * 
	 * @param slip 元伝票
	 * @return 作成された伝票
	 * @throws TException
	 */
	protected List<SWK_DTL> createFunctionalJournal(Slip slip) throws TException {
		if (!getCompany().getAccountConfig().isUseIfrs()) {
			return Collections.EMPTY_LIST; // IFRS無しの場合は、作らずに返す
		}

		FunctionalCurrency logic;

		if (slip.isClosingSlip()) {
			// 決算レートベース
			logic = (FunctionalCurrency) getComponent("FunctionalSettlementCurrency");

		} else {
			// 社定レートベース
			logic = (FunctionalCurrency) getComponent("FunctionalCurrency");
		}

		return logic.create(slip).getDetails();
	}

	/**
	 * 伝票に消費税仕訳を追加。<br>
	 * 本体勘定の消費税コード、消費税額をもとに消費税勘定を作成し、伝票にセット。
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void addTaxJournal(Slip slip) throws TException {
		this.addTaxJournal(slip, slip.getDetails().size());
	}

	/**
	 * 伝票に消費税仕訳を追加。<br>
	 * 本体勘定の消費税コード、消費税額をもとに消費税勘定を作成し、伝票にセット。<br>
	 * 会社を跨いで行番号を連番にする場合は、最大行番号をセットする.
	 * 
	 * @param slip 伝票
	 * @param maxRowNum 最大行番号
	 * @return 最大行番号
	 * @throws TException
	 */
	protected int addTaxJournal(Slip slip, int maxRowNum) throws TException {
		TaxJournalIssuer logic = (TaxJournalIssuer) getComponent(TaxJournalIssuer.class);
		return logic.addJournal(slip, maxRowNum);
	}

	/**
	 * 伝票に消費税仕訳(経過措置部分)を追加。<br>
	 * 本体勘定の消費税コード、消費税額をもとに消費税勘定を利用して、経過措置判定を行い、元費用へ按分し、伝票にセット。<br>
	 * 会社を跨いで行番号を連番にする場合は、最大行番号をセットする.
	 * 
	 * @param slip 伝票
	 * @param maxRowNum 最大行番号
	 * @return 最大行番号
	 * @throws TException
	 */
	protected int addTransferTaxJournal(Slip slip, int maxRowNum) throws TException {
		TaxJournalIssuer logic = (TaxJournalIssuer) getComponent(TaxJournalIssuer.class);
		return logic.addTransferJournal(slip, maxRowNum);
	}

	/**
	 * 伝票パターンを構築する.<br>
	 * 各種値設定
	 * 
	 * @param slip 伝票クラス
	 * @return 構築した伝票(付替先含め)
	 * @throws TException
	 */
	public List<Slip> buildSlipPattern(Slip slip) throws TException {

		// 伝票ヘッダ
		SWK_HDR hdr = slip.getHeader();

		// 必須チェック(例外用)
		if (Util.isNullOrEmpty(hdr.getKAI_CODE())) {
			// 必須項目[会社コード]が設定されていません。
			throw new TException("I00166", "C00596");
		}

		if (Util.isNullOrEmpty(hdr.getSWK_DEN_NO())) {
			// 必須項目[パターン番号]が設定されていません。
			throw new TException("I00166", "C00987");
		}

		if (slip.isNew()) {
			// 新規
			hdr.setINP_DATE(getNow()); // 登録日
			hdr.setSWK_INP_DATE(getNow()); // 登録日

		} else {
			// 修正
			hdr.setSWK_UPD_CNT(hdr.getSWK_UPD_CNT() + 1); // 修正回数
			hdr.setUPD_DATE(getNow()); // 更新日付
		}

		if (hdr.getSWK_IRAI_DATE() == null) {
			hdr.setSWK_IRAI_EMP_CODE(getUser().getEmployee().getCode()); // 依頼者
			hdr.setSWK_IRAI_DEP_CODE(getUser().getDepartment().getCode()); // 依頼部門コード
			hdr.setSWK_IRAI_DATE(getNow()); // 依頼日- 現在日付
		}
		hdr.setUSR_ID(getUserCode()); // ユーザID
		hdr.setPRG_ID(getProgramCode()); // プログラムID

		// 伝票→パターンの際に不要な項目はクリア
		// TODO 他に洗い出し
		hdr.setSWK_SYO_EMP_CODE(null); // 承認者
		hdr.setSWK_SYO_DATE(null); // 承認日

		for (SWK_DTL dtl : slip.getDetails()) {
			dtl.setSWK_KESI_DATE(null); // 消込伝票日付
			dtl.setSWK_KESI_DEN_NO(null); // 消込伝票番号
			dtl.setSWK_SOUSAI_DATE(null); // 相殺伝票日付
			dtl.setSWK_SOUSAI_DEN_NO(null); // 相殺伝票番号
		}

		// 行番号
		slip.setRowNo();

		// 明細との同期あわせ
		slip.synchDetails();

		List<Slip> slipList = new ArrayList<Slip>(1);
		slipList.add(slip);

		return slipList;
	}

	/**
	 * 伝票パターンを起票する
	 * 
	 * @param slip 伝票クラス
	 */
	public void entryPattern(Slip slip) {
		setEntryInfo(slip);

		SlipEntry logic = getSlipEntry();
		logic.setHeaderDao(getPatternHeaderDao());
		logic.setDetailDao(getPatternDetailDao());
		logic.entry(slip);
	}

	/**
	 * 伝票パターンを削除する
	 * 
	 * @param slip 伝票
	 */
	public void deletePattern(Slip slip) {
		SWK_HDRDao hdao = getPatternHeaderDao();
		SWK_DTLDao ddao = getPatternDetailDao();

		// ヘッダ削除
		SlipCondition hparam = new SlipCondition();
		hparam.setCompanyCode(slip.getCompanyCode());
		hparam.setSlipNo(slip.getSlipNo());
		hdao.deleteByCondition(hparam);

		// 該当明細の全削除
		SlipCondition dparam = new SlipCondition();
		dparam.setCompanyCode(slip.getCompanyCode());
		dparam.setSlipNo(slip.getSlipNo());
		ddao.deleteByCondition(dparam);
	}

	/**
	 * 排他
	 * 
	 * @param dao ヘッダーDao
	 * @param slip 伝票
	 * @throws TException
	 */
	public void lock(SWK_HDRDao dao, Slip slip) throws TException {

		// SWK_SHR_KBN と SWK_UPD_KBNのみ使うため、最適化する
		String companyCode = slip.getCompanyCode();
		if (Util.isNullOrEmpty(companyCode)) {
			companyCode = getCompanyCode();
		}
		SWK_HDR hdr = dao.getLockInfo(companyCode, slip.getSlipNo());

		if (hdr == null) {
			// 指定のデータは既に削除されています。
			throw new TException("I00167");
		}

		// 既にロックされている場合
		if (hdr.getSWK_SHR_KBN() == Slip.SHR_KBN.LOCKED) {
			throw new TException("W00123");
		}

		switch (hdr.getSlipState()) {
			case UPDATE:
				// 更新されてる場合
				throw new TException("W00126");

			case APPROVE:
			case FIELD_APPROVE:
				// 承認されてる場合
				throw new TException("W00127");
		}

		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(companyCode); // 会社コード
		condition.setSlipNo(slip.getSlipNo());
		condition.setLockState(Slip.SHR_KBN.LOCKED); // 排他フラグ １：更新中
		condition.setProgramCode(getProgramCode()); // プログラムＩＤ
		condition.setUserCode(getUserCode()); // ユーザーＩＤ

		dao.updateLockState(condition);
	}

	/**
	 * 排他解除
	 * 
	 * @param dao ヘッダーDao
	 * @param slip 伝票
	 */
	public void unlock(SWK_HDRDao dao, Slip slip) {
		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(slip.getCompanyCode()); // 会社コード
		condition.setSlipNo(slip.getSlipNo()); // 伝票番号
		condition.setLockState(Slip.SHR_KBN.NON_LOCKED); // 排他フラグ 0:未更新
		condition.setProgramCode(getProgramCode()); // プログラムＩＤ
		condition.setUserCode(getUserCode()); // ユーザーＩＤ

		dao.updateLockState(condition);
	}

	/**
	 * ユーザの全排他解除
	 * 
	 * @param dao ヘッダーDao
	 */
	public void unlockAll(SWK_HDRDao dao) {
		SlipCondition condition = new SlipCondition();
		condition.setLockState(Slip.SHR_KBN.NON_LOCKED); // 排他フラグ 0:未更新
		condition.setCompanyCode(getCompanyCode()); // 会社コード
		condition.setProgramCode(getProgramCode()); // プログラムＩＤ
		condition.setUserCode(getUserCode()); // ユーザーＩＤ

		dao.updateLockState(condition);
	}

	/**
	 * 伝票ヘッダDao
	 * 
	 * @return ヘッダDao
	 */
	public abstract SWK_HDRDao getHeaderDao();

	/**
	 * 削除伝票ヘッダDao
	 * 
	 * @return 削除伝票ヘッダDao
	 */
	public abstract SWK_HDRDao getDeleteHeaderDao();

	/**
	 * 伝票ヘッダパターンDao
	 * 
	 * @return 伝票ヘッダパターンDao
	 */
	public abstract SWK_HDRDao getPatternHeaderDao();

	/**
	 * 伝票明細Dao
	 * 
	 * @return 伝票明細Dao
	 */
	public SWK_DTLDao getDetailDao() {
		return (SWK_DTLDao) getComponent("NEW-SWK_DTLDao");
	}

	/**
	 * 削除伝票データDao
	 * 
	 * @return 削除伝票データDao
	 */
	public DEL_DTLDao getDeleteSlipDao() {
		return (DEL_DTLDao) getComponent("NEW-DEL_DTLDao");
	}

	/**
	 * 削除伝票明細Dao
	 * 
	 * @return 削除伝票明細Dao
	 */
	public SWK_DTLDao getDeleteDetailDao() {
		return (SWK_DTLDao) getComponent("NEW-SWK_DEL_DTLDao");
	}

	/**
	 * 伝票明細パターンDao
	 * 
	 * @return 伝票明細パターンDao
	 */
	public SWK_DTLDao getPatternDetailDao() {
		return (SWK_DTLDao) getComponent("NEW-SWK_PTN_DTLDao");
	}

	/**
	 * 伝票内部のEntityを構築する
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void setupSlip(Slip slip) throws TException {
		setupHeader(slip);
		setupDetails(slip);
	}

	/**
	 * 伝票ヘッダを構築する.
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void setupHeader(Slip slip) throws TException {

		SWK_HDR hdr = slip.getHeader();

		// 計上部門
		if (!Util.isNullOrEmpty(hdr.getSWK_DEP_CODE())) {

			// 条件の設定
			DepartmentSearchCondition condition = new DepartmentSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_DEP_CODE());

			// 対象データの取得
			DepartmentManager logic = (DepartmentManager) getComponent(DepartmentManager.class);
			List<Department> list = logic.get(condition);

			// 対象データが存在する場合、ヘッダーにエンティティをセット
			if (!list.isEmpty()) {
				hdr.setDepartment(list.get(0));
			}
		}

		// 科目
		ItemManager itemMng = (ItemManager) getComponent(ItemManager.class);
		String kmk = hdr.getSWK_KMK_CODE();
		String hkm = hdr.getSWK_HKM_CODE();
		String ukm = hdr.getSWK_UKM_CODE();

		if (!Util.isNullOrEmpty(ukm)) {// 内訳まである場合

			// 検索条件の設定
			DetailItemSearchCondition condition = new DetailItemSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setItemCode(kmk);
			condition.setSubItemCode(hkm);
			condition.setCode(ukm);

			// 対象データが存在する場合、ヘッダーにエンティティをセット
			List<Item> items = itemMng.get(condition);

			if (!items.isEmpty()) {
				hdr.setItem(items.get(0));
			}

		} else if (!Util.isNullOrEmpty(hkm)) {// 補助まである場合

			// 検索条件の設定
			SubItemSearchCondition hkmCon = new SubItemSearchCondition();
			hkmCon.setCompanyCode(hdr.getKAI_CODE());
			hkmCon.setItemCode(kmk);
			hkmCon.setCode(hkm);

			// 対象データが存在する場合、ヘッダーにエンティティをセット
			List<Item> items = itemMng.get(hkmCon);

			if (!items.isEmpty()) {
				hdr.setItem(items.get(0));
			}

		} else if (!Util.isNullOrEmpty(kmk)) {

			// 科目のみの場合
			if (hdr.getItem() == null && !Util.isNullOrEmpty(kmk)) {

				// 検索条件の設定
				ItemSearchCondition kmkCon = new ItemSearchCondition();
				kmkCon.setCompanyCode(hdr.getKAI_CODE());
				kmkCon.setCode(kmk);

				// 対象データが存在する場合、ヘッダーにエンティティをセット
				List<Item> items = itemMng.get(kmkCon);

				if (!items.isEmpty()) {
					hdr.setItem(items.get(0));
				}
			}
		}

		// 通貨
		if (!Util.isNullOrEmpty(hdr.getSWK_CUR_CODE())) {

			// 条件の設定
			CurrencySearchCondition condition = new CurrencySearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_CUR_CODE());

			// 対象データの取得
			CurrencyManager logic = (CurrencyManager) getComponent(CurrencyManager.class);// 通貨
			List<Currency> list = logic.get(condition);

			// 対象データが存在する場合、ヘッダーにエンティティをセット
			if (!list.isEmpty()) {
				hdr.setCurrency(list.get(0));
			}
		}

		// 取引先
		if (!Util.isNullOrEmpty(hdr.getSWK_TRI_CODE())) {

			// 条件の設定
			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_TRI_CODE());

			// 対象データの取得
			CustomerManager logic = (CustomerManager) getComponent(CustomerManager.class);
			List<Customer> list = logic.get(condition);

			// 対象データが存在する場合、ヘッダーにエンティティをセット
			if (!list.isEmpty()) {
				hdr.setCustomer(list.get(0));
			}
		}

		// 支払方法
		if (!Util.isNullOrEmpty(hdr.getSWK_HOH_CODE())) {

			// 条件の設定
			PaymentMethodSearchCondition condition = new PaymentMethodSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_HOH_CODE());

			// 対象データの取得
			PaymentMethodManager logic = (PaymentMethodManager) getComponent(PaymentMethodManager.class);
			List<PaymentMethod> list = logic.get(condition);

			// 対象データが存在する場合、ヘッダーにエンティティをセット
			if (!list.isEmpty()) {
				hdr.setPaymentMethod(list.get(0));
			}
		}

		// 銀行口座
		if (!Util.isNullOrEmpty(hdr.getSWK_CBK_CODE())) {

			// 条件の設定
			BankAccountSearchCondition condition = new BankAccountSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_CBK_CODE());

			// 対象データの取得
			BankAccountManager logic = (BankAccountManager) getComponent(BankAccountManager.class);
			List<BankAccount> list = logic.get(condition);

			// 対象データが存在する場合、ヘッダーにエンティティをセット
			if (!list.isEmpty()) {
				hdr.setBankAccount(list.get(0));
			}
		}

		// 取引先条件
		if (!Util.isNullOrEmpty(hdr.getSWK_TJK_CODE())) {

			// 条件の設定
			PaymentSettingSearchCondition condition = new PaymentSettingSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCustomerCode(hdr.getSWK_TRI_CODE());
			condition.setCode(hdr.getSWK_TJK_CODE());

			// 対象データの取得
			PaymentSettingManager logic = (PaymentSettingManager) getComponent(PaymentSettingManager.class);
			List<PaymentSetting> list = logic.get(condition);

			// 対象データが存在する場合、関連情報もセットし、ヘッダーにエンティティをセット
			if (!list.isEmpty()) {
				PaymentSetting bean = list.get(0);
				bean.setBankAccount(hdr.getBankAccount());
				bean.setPaymentMethod(hdr.getPaymentMethod());
				bean.setPaymentDateType(PaymentDateType.getPaymentDateType(hdr.getSWK_SIHA_KBN()));
				hdr.setPaymentSetting(bean);
				if (hdr.getCustomer() != null) {
					hdr.getCustomer().setPaymentSetting(bean);
				}
			}
		}

		// 請求区分
		if (!Util.isNullOrEmpty(hdr.getSWK_SEI_KBN())) {

			// 条件の設定
			BillTypeSearchCondition condition = new BillTypeSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_SEI_KBN());

			// 対象データの取得
			BillTypeManager logic = (BillTypeManager) getComponent(BillTypeManager.class);
			List<BillType> list = logic.get(condition);

			// 対象データが存在する場合、ヘッダーにエンティティをセット
			if (!list.isEmpty()) {
				hdr.setBillType(list.get(0));
			}
		}

		// 社員
		if (!Util.isNullOrEmpty(hdr.getSWK_EMP_CODE())) {

			// 条件の設定
			EmployeeSearchCondition condition = new EmployeeSearchCondition();
			condition.setCompanyCode(hdr.getKAI_CODE());
			condition.setCode(hdr.getSWK_EMP_CODE());

			// 対象データの取得
			EmployeeManager logic = (EmployeeManager) getComponent(EmployeeManager.class);
			List<Employee> list = logic.get(condition);

			// 対象データが存在する場合、ヘッダーにエンティティをセット
			if (!list.isEmpty()) {
				hdr.setEmployee(list.get(0));
			}
		}

		// ファイル添付機能有効の場合
		if (isUseAttachment) {
			// 伝票添付の設定
			setupAttachments(slip);
		}
		// 付箋機能有効の場合
		if (isUseTag) {
			setupTags(slip);
		}

	}

	/**
	 * 伝票添付の設定
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setupAttachments(Slip slip) throws TException {
		SlipAttachmentManager attachment = (SlipAttachmentManager) getComponent(SlipAttachmentManager.class);
		slip.getHeader().setAttachments(attachment.get(slip));
	}

	/**
	 * 伝票付箋の設定
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setupTags(Slip slip) throws TException {
		SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
		slip.getHeader().setSwkTags(tag.get(slip));
	}

	/**
	 * 伝票明細を構築する.
	 * 
	 * @param slip 伝票
	 * @throws TException
	 */
	public void setupDetails(Slip slip) throws TException {
		if (slip == null) {
			return;
		}

		List<Slip> slipList = new ArrayList<Slip>();
		slipList.add(slip);
		setupDetails(slipList, true);
	}

	/**
	 * 伝票明細を構築する.
	 * 
	 * @param slipList 伝票リスト
	 * @param includeBalance true:AP/AR/BS残高セットアップ
	 * @throws TException
	 */
	public void setupDetails(List<Slip> slipList, boolean includeBalance) throws TException {

		if (slipList == null || slipList.isEmpty()) {
			return;
		}

		CompanyManager compMng = (CompanyManager) getComponent(CompanyManager.class); // 会社
		DepartmentManager deptMng = (DepartmentManager) getComponent(DepartmentManager.class);// 部門
		ItemManager itemMng = (ItemManager) getComponent(ItemManager.class); // 科目
		ConsumptionTaxManager taxMng = (ConsumptionTaxManager) getComponent(ConsumptionTaxManager.class);// 消費税
		CurrencyManager curMng = (CurrencyManager) getComponent(CurrencyManager.class);// 通貨

		CompanySearchCondition compCon = new CompanySearchCondition();
		DepartmentSearchCondition deptCon = new DepartmentSearchCondition();
		ItemSearchCondition kmkCon = new ItemSearchCondition();
		SubItemSearchCondition hkmCon = new SubItemSearchCondition();
		DetailItemSearchCondition ukmCon = new DetailItemSearchCondition();
		ConsumptionTaxSearchCondition taxCon = new ConsumptionTaxSearchCondition();
		CurrencySearchCondition curCon = new CurrencySearchCondition();

		Map<String, Company> compMap = new TreeMap<String, Company>();
		Map<String, Department> deptMap = new TreeMap<String, Department>();
		Map<String, Item> itemMap = new TreeMap<String, Item>();
		Map<String, ConsumptionTax> taxMap = new TreeMap<String, ConsumptionTax>();
		Map<String, Currency> curMap = new TreeMap<String, Currency>();

		String keyCur = getCompany().getAccountConfig().getKeyCurrency().getCode();

		for (Slip slip : slipList) {

			List<SWK_DTL> dtlList = slip.getDetails();

			// 明細
			for (SWK_DTL dtl : dtlList) {

				// 計上会社
				String kCompany = dtl.getSWK_K_KAI_CODE();

				if (!Util.isNullOrEmpty(kCompany)) {
					Company company = compMap.get(kCompany);
					if (company == null) {
						compCon.setCode(kCompany);
						List<Company> companys = compMng.get(compCon);

						if (!companys.isEmpty()) {
							company = companys.get(0);
							compMap.put(kCompany, company);
						}
					}
					dtl.setAppropriateCompany(company);
				}

				if (isGroupAccounting()) {
					if (dtl.getSWK_TUKE_KBN() == 2) {
						// 2
					} else {
						// 0, 1
						kCompany = dtl.getKAI_CODE();
						if (Util.isNullOrEmpty(kCompany)) {
							kCompany = getCompanyCode();
							dtl.setKAI_CODE(getCompanyCode());
						}
					}
				}

				// 計上部門
				String dept = dtl.getSWK_DEP_CODE();

				if (!Util.isNullOrEmpty(dept)) {
					Department department = deptMap.get(kCompany + KEY_SEP + dept);
					if (department == null) {
						deptCon.setCompanyCode(kCompany);
						deptCon.setCode(dept);
						List<Department> depts = deptMng.get(deptCon);
						if (!depts.isEmpty()) {
							department = depts.get(0);
							deptMap.put(kCompany + KEY_SEP + dept, department);
						}
					}
					dtl.setDepartment(department);
				}

				// 科目
				String kmk = dtl.getSWK_KMK_CODE();
				String hkm = dtl.getSWK_HKM_CODE();
				String ukm = dtl.getSWK_UKM_CODE();

				if (!Util.isNullOrEmpty(kmk)) {
					Item item;
					if (!Util.isNullOrEmpty(ukm)) {
						// 内訳まである
						item = itemMap.get(kCompany + KEY_SEP + kmk + KEY_SEP + hkm + KEY_SEP + ukm);

						if (item == null) {
							ukmCon.setCompanyCode(kCompany);
							ukmCon.setItemCode(kmk);
							ukmCon.setSubItemCode(hkm);
							ukmCon.setCode(ukm);
							List<Item> items = itemMng.get(ukmCon);

							if (!items.isEmpty()) {
								item = items.get(0);
								itemMap.put(kCompany + KEY_SEP + kmk + KEY_SEP + hkm + KEY_SEP + ukm, item);
							}
						}

					} else if (!Util.isNullOrEmpty(hkm)) {
						// 補助まである
						item = itemMap.get(kCompany + KEY_SEP + kmk + KEY_SEP + hkm);

						if (item == null) {
							hkmCon.setCompanyCode(kCompany);
							hkmCon.setItemCode(kmk);
							hkmCon.setCode(hkm);
							List<Item> items = itemMng.get(hkmCon);

							if (!items.isEmpty()) {
								item = items.get(0);
								itemMap.put(kCompany + KEY_SEP + kmk + KEY_SEP + hkm, item);
							}
						}

					} else {
						// 科目のみ
						item = itemMap.get(kCompany + KEY_SEP + kmk);

						if (item == null) {
							kmkCon.setCompanyCode(kCompany);
							kmkCon.setCode(kmk);
							List<Item> items = itemMng.get(kmkCon);

							if (!items.isEmpty()) {
								item = items.get(0);
								itemMap.put(kCompany + KEY_SEP + kmk, item);
							}
						}
					}
					// 科目情報が無ければ抜ける
					if (Util.isNullOrEmpty(item)) {
						continue;
					}
					dtl.setItem(item);

					// 最適科目を取得
					Item pItem = item.getPreferedItem();

					// 消費税
					String zei = dtl.getSWK_ZEI_CODE();
					if (!Util.isNullOrEmpty(zei)) {
						ConsumptionTax tax = taxMap.get(kCompany + KEY_SEP + zei);
						if (tax == null) {
							taxCon.setCompanyCode(kCompany);
							taxCon.setCode(zei);
							List<ConsumptionTax> taxs = taxMng.get(taxCon);
							if (!taxs.isEmpty()) {
								tax = taxs.get(0);
								taxMap.put(kCompany + KEY_SEP + zei, tax);
							}
						}
						// 課税フラグチェック
						if (tax != null && (tax.getTaxType() == TaxType.NOT || tax.getTaxType() == TaxType.PURCHAESE)
							&& pItem.isUsePurchaseTaxation()) {
							dtl.setTax(tax);
						} else if (tax != null && (tax.getTaxType() == TaxType.NOT || tax.getTaxType() == TaxType.SALES)
							&& pItem.isUseSalesTaxation()) {
								dtl.setTax(tax);
							} else {
								dtl.setSWK_ZEI_CODE(null);
							}
					}

					// 通貨
					String cur = dtl.getSWK_CUR_CODE();

					if (!Util.isNullOrEmpty(cur)) {
						Currency currency = curMap.get(kCompany + KEY_SEP + cur);
						if (currency == null) {
							curCon.setCompanyCode(kCompany);
							curCon.setCode(cur);
							List<Currency> curs = curMng.get(curCon);
							if (!curs.isEmpty()) {
								currency = curs.get(0);
								curMap.put(kCompany + KEY_SEP + cur, currency);
							}
						}
						if (pItem.isUseForeignCurrency()) {
							dtl.setCurrency(currency);
						} else if (!pItem.isUseForeignCurrency() && Util.equals(keyCur, cur)) {
							dtl.setCurrency(currency);
						} else {
							dtl.setSWK_CUR_CODE(null);
						}

					}
				}
			}

			// 伝票番号が設定されていない場合、処理終了
			if (Util.isNullOrEmpty(slip.getSlipNo())) {
				return;
			}

			// 残高エンティティをセット
			BalanceManager blanceMng = (BalanceManager) getComponent(BalanceManager.class);

			BalanceCondition blCon = new BalanceCondition();
			blCon.setCompanyCode(slip.getCompanyCode()); // 会社コード
			blCon.setEraseSlipNo(slip.getSlipNo()); // 消込伝票番号

			// 行番号のKeySetを作成
			Map<Integer, SWK_DTL> indexMap = new HashMap<Integer, SWK_DTL>(slip.getDetailCount());

			for (SWK_DTL dtl : slip.getDetails()) {
				indexMap.put(dtl.getSWK_GYO_NO(), dtl);
			}

			if (includeBalance) {

				// 債務
				List<AP_ZAN> apList = blanceMng.getApBalance(blCon);

				for (AP_ZAN zan : apList) {
					int lineNo = zan.getZAN_SIHA_GYO_NO();
					if (indexMap.get(lineNo) != null) {
						indexMap.get(lineNo).setAP_ZAN(zan);
					}
				}

				// 債権
				List<AR_ZAN> arList = blanceMng.getArBalance(blCon);

				for (AR_ZAN zan : arList) {
					int lineNo = zan.getZAN_KESI_GYO_NO();
					if (indexMap.get(lineNo) != null) {
						indexMap.get(lineNo).setAR_ZAN(zan);
					}
				}

				if (isUseBS) {
					// BS勘定消込仕訳の設定
					setupBsDetails(slip);
				}
			}
		}

	}

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、フラグによって消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slip
	 * @param recalc 必要な場合のみ再計算
	 * @throws TException
	 */
	public void setupDetailsOptional(Slip slip, boolean recalc) throws TException {
		setupDetailsOptional(slip, recalc, true);
	}

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、フラグによって消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slip
	 * @param recalc 必要な場合のみ再計算
	 * @param includeBalance true:AP/AR/BS残高セットアップ
	 * @throws TException
	 */
	public void setupDetailsOptional(Slip slip, boolean recalc, boolean includeBalance) throws TException {
		if (slip == null) {
			return;
		}

		List<Slip> slipList = new ArrayList<Slip>();
		slipList.add(slip);
		setupDetailsOptional(slipList, recalc, includeBalance);
	}

	/**
	 * 伝票明細を構築する<br>
	 * 直接制御に関係のない管理１～管理６や、取引先情報をセット<br>
	 * また、フラグによって消費税額や邦貨金額が空の場合その値も補う
	 * 
	 * @param slipList
	 * @param recalc 必要な場合のみ再計算
	 * @param includeBalance true:AP/AR/BS残高セットアップ
	 * @throws TException
	 */
	public void setupDetailsOptional(List<Slip> slipList, boolean recalc, boolean includeBalance) throws TException {

		if (slipList == null || slipList.isEmpty()) {
			return;
		}

		Management1Manager knr1Mng = get(Management1Manager.class);
		Management2Manager knr2Mng = get(Management2Manager.class);
		Management3Manager knr3Mng = get(Management3Manager.class);
		Management4Manager knr4Mng = get(Management4Manager.class);
		Management5Manager knr5Mng = get(Management5Manager.class);
		Management6Manager knr6Mng = get(Management6Manager.class);
		RemarkManager remMng = get(RemarkManager.class);
		EmployeeManager empMana = get(EmployeeManager.class);
		CustomerManager cusMng = get(CustomerManager.class);
		RateManager rateMgr = get(RateManager.class);
		TCalculator calc = TCalculatorFactory.getCalculator();
		TExchangeAmount exc = TCalculatorFactory.createExchangeAmount();
		TTaxCalculation taxCalc = TCalculatorFactory.createTaxCalculation();

		Management1SearchCondition k1Con = new Management1SearchCondition();
		Management2SearchCondition k2Con = new Management2SearchCondition();
		Management3SearchCondition k3Con = new Management3SearchCondition();
		Management4SearchCondition k4Con = new Management4SearchCondition();
		Management5SearchCondition k5Con = new Management5SearchCondition();
		Management6SearchCondition k6Con = new Management6SearchCondition();
		RemarkSearchCondition remCon = new RemarkSearchCondition();
		EmployeeSearchCondition empCon = new EmployeeSearchCondition();
		CustomerSearchCondition cusCon = new CustomerSearchCondition();

		Map<String, Management1> k1Map = new TreeMap<String, Management1>();
		Map<String, Management2> k2Map = new TreeMap<String, Management2>();
		Map<String, Management3> k3Map = new TreeMap<String, Management3>();
		Map<String, Management4> k4Map = new TreeMap<String, Management4>();
		Map<String, Management5> k5Map = new TreeMap<String, Management5>();
		Map<String, Management6> k6Map = new TreeMap<String, Management6>();
		Map<String, Remark> remMap = new TreeMap<String, Remark>();
		Map<String, Employee> empMap = new TreeMap<String, Employee>();
		Map<String, Customer> cusMap = new TreeMap<String, Customer>();
		Map<String, BigDecimal> rateMap = new TreeMap<String, BigDecimal>();

		// 既存処理での構築も実施
		setupDetails(slipList, includeBalance);

		for (Slip slip : slipList) {

			List<SWK_DTL> dtlList = slip.getDetails();

			try {

				// 明細
				for (SWK_DTL dtl : dtlList) {
					// 会社 再構築nullの場合ログイン会社
					String kCompany = dtl.getSWK_K_KAI_CODE();
					if (Util.isNullOrEmpty(kCompany)) {
						dtl.setAppropriateCompany(getCompany());
						kCompany = getCompanyCode();
					}

					if (isGroupAccounting()) {
						if (dtl.getSWK_TUKE_KBN() == 2) {
							// 2
						} else {
							// 0, 1
							kCompany = dtl.getKAI_CODE();
						}
					}

					Company comp = dtl.getAppropriateCompany();
					AccountConfig ac = comp == null ? null : comp.getAccountConfig();
					Currency keyCur = ac == null ? null : ac.getKeyCurrency();
					Currency cur = dtl.getCurrency();
					int curDecPint = cur == null ? 0 : cur.getDecimalPoint();

					Item item = dtl.getItem();
					if (item != null) {
						item = item.getPreferedItem();
					}
					// 管理１
					String k1Code = dtl.getSWK_KNR_CODE_1();
					if (!Util.isNullOrEmpty(k1Code) && item != null && item.isUseManagement1()) {
						Management1 k1 = k1Map.get(kCompany + KEY_SEP + k1Code);
						if (k1 == null) {
							k1Con.setCompanyCode(kCompany);
							k1Con.setCode(k1Code);
							List<Management1> k1s = knr1Mng.get(k1Con);
							if (!k1s.isEmpty()) {
								k1 = k1s.get(0);
								k1Map.put(kCompany + KEY_SEP + k1Code, k1);
							}
						}
						dtl.setManagement1(k1);
					} else {
						dtl.setManagement1(null);
					}
					// 管理2
					String k2Code = dtl.getSWK_KNR_CODE_2();
					if (!Util.isNullOrEmpty(k2Code) && item != null && item.isUseManagement2()) {
						Management2 k2 = k2Map.get(kCompany + KEY_SEP + k2Code);
						if (k2 == null) {
							k2Con.setCompanyCode(kCompany);
							k2Con.setCode(k2Code);
							List<Management2> k2s = knr2Mng.get(k2Con);
							if (!k2s.isEmpty()) {
								k2 = k2s.get(0);
								k2Map.put(kCompany + KEY_SEP + k2Code, k2);
							}
						}
						dtl.setManagement2(k2);
					} else {
						dtl.setManagement2(null);
					}
					// 管理3
					String k3Code = dtl.getSWK_KNR_CODE_3();
					if (!Util.isNullOrEmpty(k3Code) && item != null && item.isUseManagement3()) {
						Management3 k3 = k3Map.get(kCompany + KEY_SEP + k3Code);
						if (k3 == null) {
							k3Con.setCompanyCode(kCompany);
							k3Con.setCode(k3Code);
							List<Management3> k3s = knr3Mng.get(k3Con);
							if (!k3s.isEmpty()) {
								k3 = k3s.get(0);
								k3Map.put(kCompany + KEY_SEP + k3Code, k3);
							}
						}
						dtl.setManagement3(k3);
					} else {
						dtl.setManagement3(null);
					}
					// 管理4
					String k4Code = dtl.getSWK_KNR_CODE_4();
					if (!Util.isNullOrEmpty(k4Code) && item != null && item.isUseManagement4()) {
						Management4 k4 = k4Map.get(kCompany + KEY_SEP + k4Code);
						if (k4 == null) {
							k4Con.setCompanyCode(kCompany);
							k4Con.setCode(k4Code);
							List<Management4> k4s = knr4Mng.get(k4Con);
							if (!k4s.isEmpty()) {
								k4 = k4s.get(0);
								k4Map.put(kCompany + KEY_SEP + k4Code, k4);
							}
						}
						dtl.setManagement4(k4);
					} else {
						dtl.setManagement4(null);
					}
					// 管理5
					String k5Code = dtl.getSWK_KNR_CODE_5();
					if (!Util.isNullOrEmpty(k5Code) && item != null && item.isUseManagement5()) {
						Management5 k5 = k5Map.get(kCompany + KEY_SEP + k5Code);
						if (k5 == null) {
							k5Con.setCompanyCode(kCompany);
							k5Con.setCode(k5Code);
							List<Management5> k5s = knr5Mng.get(k5Con);
							if (!k5s.isEmpty()) {
								k5 = k5s.get(0);
								k5Map.put(kCompany + KEY_SEP + k5Code, k5);
							}
						}
						dtl.setManagement5(k5);
					} else {
						dtl.setManagement5(null);
					}
					// 管理6
					String k6Code = dtl.getSWK_KNR_CODE_6();
					if (!Util.isNullOrEmpty(k6Code) && item != null && item.isUseManagement6()) {
						Management6 k6 = k6Map.get(kCompany + KEY_SEP + k6Code);
						if (k6 == null) {
							k6Con.setCompanyCode(kCompany);
							k6Con.setCode(k6Code);
							List<Management6> k6s = knr6Mng.get(k6Con);
							if (!k6s.isEmpty()) {
								k6 = k6s.get(0);
								k6Map.put(kCompany + KEY_SEP + k6Code, k6);
							}
						}
						dtl.setManagement6(k6);
					} else {
						dtl.setManagement6(null);
					}
					// 取引先
					String cusCode = dtl.getSWK_TRI_CODE();
					if (!Util.isNullOrEmpty(cusCode) && item != null && item.isUseCustomer()) {
						Customer cus = cusMap.get(kCompany + KEY_SEP + cusCode);
						if (cus == null) {
							cusCon.setCompanyCode(kCompany);
							cusCon.setCode(cusCode);
							List<Customer> customers = cusMng.get(cusCon);
							if (!customers.isEmpty()) {
								cus = customers.get(0);
								cusMap.put(kCompany + KEY_SEP + cusCode, cus);
							}
						}
						dtl.setCustomer(cus);
					} else {
						dtl.setCustomer(null);
					}
					// 社員
					String empCode = dtl.getSWK_EMP_CODE();
					if (!Util.isNullOrEmpty(empCode) && item != null && item.isUseEmployee()) {
						Employee emp = empMap.get(kCompany + KEY_SEP + empCode);
						if (emp == null) {
							empCon.setCompanyCode(kCompany);
							empCon.setCode(empCode);
							List<Employee> employees = empMana.get(empCon);
							if (!employees.isEmpty()) {
								emp = employees.get(0);
								empMap.put(kCompany + KEY_SEP + empCode, emp);
							}
						}
						dtl.setEmployee(emp);
					} else {
						dtl.setEmployee(null);
					}
					// 摘要
					String remCode = dtl.getSWK_GYO_TEK_CODE();
					if (!Util.isNullOrEmpty(remCode)) {
						Remark rem = remMap.get(kCompany + KEY_SEP + remCode);
						if (rem == null) {
							remCon.setCompanyCode(kCompany);
							remCon.setSlipRowRemark(true);
							remCon.setCode(remCode);
							List<Remark> remarks = remMng.get(remCon);
							if (!remarks.isEmpty()) {
								rem = remarks.get(0);
								remMap.put(kCompany + KEY_SEP + remCode, rem);
							}
						}
						if (rem == null) {
							dtl.setSWK_GYO_TEK_CODE(null);
						} else if (Util.isNullOrEmpty(dtl.getSWK_GYO_TEK())) {
							dtl.setSWK_GYO_TEK(rem.getName());
						}
					}
					// 税セットアップ
					ConsumptionTax tx = dtl.getTax();
					if (tx != null) {
						if (DecimalUtil.isNullOrZero(tx.getRate())) {
							dtl.setTaxCalcType(TaxCalcType.NONE);
						}
					} else {
						dtl.setTaxCalcType(TaxCalcType.NONE);
						dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
						dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
					}

					if (recalc) {
						// 必要な場合のみ再計算

						// レート系セットアップ
						if (DecimalUtil.isNullOrZero(dtl.getSWK_CUR_RATE())) {
							// レートが0
							if (keyCur != null && Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
								// 基軸通貨と同じ場合
								dtl.setSWK_CUR_RATE(BigDecimal.ONE);
							} else if (!Util.isNullOrEmpty(dtl.getSWK_CUR_CODE())) {
								// 基軸通貨以外の通貨が登録
								Date occurDate = dtl.getHAS_DATE() == null ? getNowYMD() : dtl.getHAS_DATE();
								String key = dtl.getKAI_CODE() + "<>" + dtl.getSWK_CUR_CODE() + "<>" + occurDate;
								BigDecimal rate = rateMap.get(key);
								if (rate == null && cur != null && occurDate != null) {
									rate = rateMgr.getRate(cur, occurDate);
									rateMap.put(key, rate);
								}
								dtl.setSWK_CUR_RATE(rate);
							}
						}

						int keyCurDigit = keyCur == null ? 0 : keyCur.getDecimalPoint();

						// 金額チェック：基軸通貨で端数が存在する場合は強制的にZERO
						if (keyCur != null && Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
							// 入力金額
							if (!DecimalUtil.isNullOrZero(dtl.getSWK_IN_KIN())) {
								BigDecimal amount = dtl.getSWK_IN_KIN();
								int keyScale = amount.scale();
								if (keyScale > keyCurDigit) {
									// 小数点以下のチェック：超過桁数分を取得し0以外ならエラー
									String valu = StringUtil.rightBX(amount.toPlainString(), keyScale - keyCurDigit);
									if (!DecimalUtil.isNullOrZero(valu)) {
										dtl.setSWK_IN_KIN(BigDecimal.ZERO);
										dtl.setSWK_KIN(BigDecimal.ZERO);
									}
								}
							}
						}
						// 金額チェック：外国通貨で端数が存在する場合は強制的にZERO
						if (keyCur != null && !Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
							// 入力金額
							if (!DecimalUtil.isNullOrZero(dtl.getSWK_IN_KIN())) {
								BigDecimal amount = dtl.getSWK_IN_KIN();
								int keyScale = amount.scale();
								if (keyScale > curDecPint) {
									// 小数点以下のチェック：超過桁数分を取得し0以外ならエラー
									String valu = StringUtil.rightBX(amount.toPlainString(), keyScale - curDecPint);
									if (!DecimalUtil.isNullOrZero(valu)) {
										dtl.setSWK_IN_KIN(BigDecimal.ZERO);
										dtl.setSWK_KIN(BigDecimal.ZERO);
									}
								}
							}
						}

						// 金額セットアップ
						if (DecimalUtil.isNullOrZero(dtl.getSWK_KIN())) {
							// 金額が0の場合
							if (keyCur != null && Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
								// 基軸通貨と同じ場合 入力金額と同額を入力
								dtl.setSWK_KIN(dtl.getSWK_IN_KIN());
							} else if (cur != null) {
								// 基軸通貨と異なる場合
								exc.setAccountConfig(ac);
								exc.setRatePow(cur.getRatePow());
								exc.setDigit(keyCurDigit);
								exc.setForeignAmount(dtl.getSWK_IN_KIN());
								exc.setRate(dtl.getSWK_CUR_RATE());
								BigDecimal kin = calc.exchangeKeyAmount(exc);
								dtl.setSWK_KIN(kin);
							}
						}

						// 税額セットアップ ：常に消費税は再計算する
						if (tx != null && tx.getTaxType() != TaxType.NOT) {
							taxCalc.setAmount(dtl.getSWK_IN_KIN());
							taxCalc.setDigit(curDecPint);
							taxCalc.setInside(dtl.getSWK_ZEI_KBN() == ZEI_KBN.TAX_IN);
							taxCalc.setPaymentFunction(ac.getPaymentFunction());
							taxCalc.setReceivingFunction(ac.getReceivingFunction());
							taxCalc.setTax(tx);
							BigDecimal taxAmt = calc.calculateTax(taxCalc);
							dtl.setSWK_IN_ZEI_KIN(taxAmt);
						} else {
							dtl.setSWK_ZEI_KBN(ZEI_KBN.TAX_NONE);
							dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
						}

						// 税額セットアップ
						if (keyCur != null && !Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
							// 外貨は常に再計算させる
							dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
						} else {
							// 基軸通貨はそのまま入力税額
							dtl.setSWK_ZEI_KIN(dtl.getSWK_IN_ZEI_KIN());
						}
						if (DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())) {
							if (keyCur != null && Util.equals(keyCur.getCode(), dtl.getSWK_CUR_CODE())) {
								// 基軸通貨と同じ場合 入力消費税金額と同額を入力
								dtl.setSWK_ZEI_KIN(dtl.getSWK_IN_ZEI_KIN());
							} else {
								// 金額が0の場合
								if (tx != null && tx.getTaxType() != TaxType.NOT) {
									taxCalc.setAmount(dtl.getSWK_KIN());
									taxCalc.setDigit(keyCurDigit);
									taxCalc.setInside(dtl.getSWK_ZEI_KBN() == ZEI_KBN.TAX_IN);
									taxCalc.setPaymentFunction(ac.getPaymentFunction());
									taxCalc.setReceivingFunction(ac.getReceivingFunction());
									taxCalc.setTax(tx);
									BigDecimal taxAmt = calc.calculateTax(taxCalc);
									dtl.setSWK_ZEI_KIN(taxAmt);
								} else {
									dtl.setSWK_ZEI_KBN(ZEI_KBN.TAX_NONE);
									dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
								}
							}
						}

						// 消費税計算区分の不整合を訂正する
						if (dtl.getSWK_ZEI_KBN() != ZEI_KBN.TAX_IN && dtl.getSWK_ZEI_KBN() != ZEI_KBN.TAX_OUT
							&& dtl.getSWK_ZEI_KBN() != ZEI_KBN.TAX_NONE) {
							dtl.setSWK_ZEI_KBN(ZEI_KBN.TAX_NONE);
							dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
							dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
						}

						// 非会計明細
						if (ac == null) {
							continue;
						}
						if (ac.getNonAccounting1() == NonAccountingDivision.NONE) {
							dtl.setSWK_HM_1(null);
						} else if (item != null && !item.isUseNonAccount1()) {
							dtl.setSWK_HM_1(null);
						} else if (ac.getNonAccounting1() == NonAccountingDivision.NUMBER) {
							if (!Util.isNullOrEmpty(dtl.getSWK_HM_1())) {
								dtl.setSWK_HM_1(NumberFormatUtil.formatNumber(dtl.getSWK_HM_1()));
							}
						} else if (ac.getNonAccounting1() == NonAccountingDivision.YMD_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_1());
							dtl.setSWK_HM_1(DateUtil.toYMDString(val));
						} else if (ac.getNonAccounting1() == NonAccountingDivision.YMDHM_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_1());
							dtl.setSWK_HM_1(DateUtil.toYMDHMString(val));
						}
						if (ac.getNonAccounting2() == NonAccountingDivision.NONE) {
							dtl.setSWK_HM_2(null);
						} else if (item != null && !item.isUseNonAccount2()) {
							dtl.setSWK_HM_2(null);
						} else if (ac.getNonAccounting2() == NonAccountingDivision.NUMBER) {
							if (!Util.isNullOrEmpty(dtl.getSWK_HM_2())) {
								dtl.setSWK_HM_2(NumberFormatUtil.formatNumber(dtl.getSWK_HM_2()));
							}
						} else if (ac.getNonAccounting2() == NonAccountingDivision.YMD_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_2());
							dtl.setSWK_HM_2(DateUtil.toYMDString(val));
						} else if (ac.getNonAccounting2() == NonAccountingDivision.YMDHM_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_2());
							dtl.setSWK_HM_2(DateUtil.toYMDHMString(val));
						}
						if (ac.getNonAccounting3() == NonAccountingDivision.NONE) {
							dtl.setSWK_HM_3(null);
						} else if (item != null && !item.isUseNonAccount3()) {
							dtl.setSWK_HM_3(null);
						} else if (ac.getNonAccounting3() == NonAccountingDivision.NUMBER) {
							if (!Util.isNullOrEmpty(dtl.getSWK_HM_3())) {
								dtl.setSWK_HM_3(NumberFormatUtil.formatNumber(dtl.getSWK_HM_3()));
							}
						} else if (ac.getNonAccounting3() == NonAccountingDivision.YMD_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_3());
							dtl.setSWK_HM_3(DateUtil.toYMDString(val));
						} else if (ac.getNonAccounting3() == NonAccountingDivision.YMDHM_DATE) {
							Date val = DateUtil.toDateNE(dtl.getSWK_HM_3());
							dtl.setSWK_HM_3(DateUtil.toYMDHMString(val));
						}
					}
				}
			} catch (Exception e) {
				//
				throw new TException(e);
			}

		}
	}

	/**
	 * BS勘定消込仕訳の設定
	 * 
	 * @param slip
	 * @throws TException
	 */
	public void setupBsDetails(Slip slip) throws TException {
		// BS勘定消込仕訳の設定
		BSEraseManager bsManager = (BSEraseManager) getComponent(BSEraseManager.class);
		BSEraseCondition condition = new BSEraseCondition();
		if (!isBsUseKCompany || !getCompany().getAccountConfig().isUseGroupAccount()) {
			// 計上会社利用してない、またはグループ会計ではない場合、会社コード条件付け
			condition.setCompanyCode(slip.getCompanyCode());
		}
		condition.setSlipNo(slip.getSlipNo()); // 消込先の伝票番号
		condition.setForSlipModify(true); // 消込元取得フラグ

		List<SWK_DTL> list = bsManager.get(condition);
		if (list == null || list.isEmpty()) {
			return;
		}

		for (SWK_DTL forward : slip.getDetails(CurrencyType.KEY)) {

			SWK_DTL base = null;
			for (int i = 0; i < list.size(); i++) {
				if (isPair(list.get(i), forward)) {
					base = list.get(i);
					break;
				}
			}

			if (base != null) {
				// BS仕訳
				forward.setBsDetail(base);
				list.remove(base);
			}
		}
	}

	/**
	 * 消込元と消込先が一致するかどうか
	 * 
	 * @param base 消込元
	 * @param forward 消込先
	 * @return true:相殺一致仕訳
	 */
	protected boolean isPair(SWK_DTL base, SWK_DTL forward) {
		if (base == null || forward == null) {
			return false;
		}

		if (Util.equals(base.getSWK_SOUSAI_DEN_NO(), forward.getSWK_DEN_NO())
			&& Util.avoidNullAsInt(base.getSWK_SOUSAI_GYO_NO()) == Util.avoidNullAsInt(forward.getSWK_GYO_NO())) {
			return true;
		}

		return false;
	}

	/**
	 * SlipをSlipBookに変換する
	 * 
	 * @param slip
	 * @return SlipBook
	 */
	public SlipBook toBook(Slip slip) {
		SlipToBookConverter converter = getSlipToBookConverter();
		if (converter != null) {
			converter.setCompany(getCompany());
			converter.setUser(getUser());
			converter.setProgramCode(getProgramCode());
			converter.setNow(getNow());
		}
		SlipBook book = converter.toBook(slip);
		return book;
	}

	/**
	 * SlipをSlipBookに変換するクラスを返す。
	 * 
	 * @return SlipをSlipBookに変換するクラス
	 */
	public abstract SlipToBookConverter getSlipToBookConverter();

	/**
	 * レイアウトクラスのファクトリメソッド
	 * 
	 * @param langCode
	 * @return レイアウトの実装クラス
	 * @throws TException
	 */
	public abstract SlipLayout getLayout(String langCode) throws TException;

	/**
	 * 伝票承認クラスのファクトリメソッド
	 * 
	 * @return 伝票承認の実装クラス
	 */
	public abstract SlipApprove getSlipApprove();
}

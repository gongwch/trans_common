package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 銀行口座フィールドのコントロール(銀行名に銀行マスタ.銀行名+銀行マスタ.支店名を表示する)
 * 
 * @author roh
 */
public class TBankAccountBFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String SEARCH_SERVLET = "MP0030BankAccountMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** フィールド */
	protected TBankAccountBField field;

	/** メッセージ用パネル取得 */
	public TPanelBusiness panel;

	/**
	 * コンストラクタ field
	 * 
	 * @param field
	 */
	public TBankAccountBFieldCtrl(TBankAccountBField field) {
		this.field = field;
	}

	/**
	 * 銀行口座への値セット
	 * 
	 * @return boolean
	 */
	public boolean setBankAccountNotice() {

		try {
			String code = field.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				field.setBean(null);
				return true;
			}

			// 口座コードで詳細リスト習得
			AP_CBK_MST cbkMst = getFieldData(code);

			// 結果無しの場合
			if (Util.isNullOrEmpty(cbkMst)) {
				showMessage(field, "W00081", "C01879");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				field.setBean(null);
				return false;
			}

			// 有効期間チェック
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				if (termDate.after(cbkMst.getEND_DATE()) || cbkMst.getSTR_DATE().after(termDate)) {
					if (!showConfirmMessage(field, "Q00025", "C00857")) {
						field.clearOldText();
						field.requestTextFocus();
						field.setBean(null);
						return false;
					}
				}
			}

			// 社員支払フラグ適用
			if (field.isEmpKbn()) {
				if (String.valueOf(cbkMst.getCBK_EMP_FB_KBN()).equals("0")) {
					if (!showConfirmMessage(field, "Q00052", "C01117")) {
						return false;
					}
				}
			}

			// 社外支払フラグ適用
			if (field.isOutKbn()) {
				if (String.valueOf(cbkMst.getCBK_OUT_FB_KBN()).equals("0")) {
					if (!showConfirmMessage(field, "Q00052", "C01122")) {
						return false;
					}
				}
			}

			// 名称セット
			field.setNoticeValue(cbkMst.getBNK_NAME_S() + " " + cbkMst.getBNK_STN_NAME_S());
			field.setBean(cbkMst);
			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * フィールドデータリスト取得
	 * 
	 * @param code 口座コード
	 * @return 詳細リスト
	 * @throws TException
	 */
	protected AP_CBK_MST getFieldData(String code) throws TException {

		try {
			// 送信するパラメータを設定
			addSendValues("flag", "getCbkData"); // 区分flag

			ApCbkMstParameter param = new ApCbkMstParameter();

			// 会社コード
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				param.setKaiCode(getLoginUserCompanyCode());
			} else {
				param.setKaiCode(field.getCompanyCode());
			}
			param.setCbkCode(code);

			if (field.getCbkCodes() != null && !field.getCbkCodes().isEmpty()) {
				param.setCbkCodes(field.getCbkCodes());
			}

			addSendObject(param);
			if (!request(SEARCH_SERVLET)) {
				// クライアント 受信解析エラー。
				errorHandler(field, "S00002");
			}

			return (AP_CBK_MST) getResultDto(AP_CBK_MST.class);

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * 預金種目の多言語対応
	 * 
	 * @param kbn 口座区分
	 * @return 単語
	 */
	public String getWordByDepositType(String kbn) {
		if ("1".equals(kbn)) {
			return getWord("C00463"); // 普通 C00463
		} else if ("2".equals(kbn)) {
			return getWord("C00397"); // 当座 C00397
		} else if ("3".equals(kbn)) {
			return getWord("C00045"); // 外貨 C00045
		} else if ("4".equals(kbn)) {
			return getWord("C00368"); // 貯蓄 C00368
		} else {
			return "";
		}
	}

	/**
	 * 略称フィールドへの値セット
	 */
	public void showSearchDialog() {
		try {
			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.BANK_ACCOUNT_B,
				initParam);

			searchdialog.setStartCode(""); // 開始コード
			searchdialog.setEndCode(""); // 終了コード
			searchdialog.setServerName("BankAccountB");
			Map<String, String> otherParam = new HashMap<String, String>();
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(super.getLoginUserCompanyCode());
			}
			otherParam.put("companyCode", field.getCompanyCode());

			// 社員、外部のフラグを設定
			otherParam.put("empKbn", BooleanUtil.toString(field.isEmpKbn()));
			otherParam.put("outKbn", BooleanUtil.toString(field.isOutKbn()));
			otherParam.put("termBasisDate", field.getTermBasisDate());
			// 銀行口座リスト
			if (field.getCbkCodes() != null && !field.getCbkCodes().isEmpty()) {
				searchdialog.setCbkCodes(field.getCbkCodes());
			}
			searchdialog.setParamMap(otherParam);

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// 確定の場合
			if (searchdialog.isSettle()) {
				String[] info = searchdialog.getCurrencyInfo();
				field.setValue(info[0]); // コードをセットした
				field.setNoticeValue(info[1]); // 名称をセットした
			}

			field.getField().requestFocus();

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}
	}

	/**
	 * 検索dialog初期値のセット
	 * 
	 * @return param タイトル名
	 */
	protected Map<String, String> getSearchDialogParam() {

		Map<String, String> param = new HashMap<String, String>();
		if (!Util.isNullOrEmpty(field.getRefTitle())) {
			param.put("top", field.getRefTitle()); // topのセット
		} else {
			param.put("top", "C02519"); // topのセット
		}
		param.put("code", "C01879"); // コードのセット
		param.put("nameS", "C10361"); // 略称のセット
		param.put("nameK", "C00794"); // 口座番号のセット

		return param;
	}

}

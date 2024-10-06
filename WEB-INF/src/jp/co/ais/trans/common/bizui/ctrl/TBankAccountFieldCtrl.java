package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 銀行口座フィールドのコントロール
 * 
 * @author roh
 */
public class TBankAccountFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String SEARCH_SERVLET = "MP0030BankAccountMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** フィールド */
	protected TBankAccountField field;

	/** メッセージ用パネル取得 */
	public TPanelBusiness panel;

	/**
	 * コンストラクタ field
	 * 
	 * @param field
	 */
	public TBankAccountFieldCtrl(TBankAccountField field) {
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
				return true;
			}

			// 口座コードで詳細リスト習得
			List list = getFieldDataList(code);

			// 結果無しの場合
			if (list.isEmpty()) {
				showMessage(field, "W00081", "C01879");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				return false;
			}

			// 有効期間チェック
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(6));
				Date endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(7));
				if (termDate.after(endDate) || strDate.after(termDate)) {
					if (!showConfirmMessage(field, "Q00025", "C00857")) {
						field.clearOldText();
						field.requestTextFocus();
						return false;
					}
				}
			}

			// 社員支払フラグ適用
			if (field.isEmpKbn()) {
				if (((String) ((List) list.get(0)).get(8)).equals("0")) {
					if (!showConfirmMessage(field, "Q00026", "C01119")) {
						return false;
					}
				}
			}

			// 社外支払フラグ適用
			if (field.isOutKbn()) {
				if (((String) ((List) list.get(0)).get(9)).equals("0")) {
					if (!showConfirmMessage(field, "Q00026", "C01124")) {
						return false;
					}
				}
			}

			// 名称セット
			field.setNoticeValue((String) ((List) list.get(0)).get(10));

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
	protected List getFieldDataList(String code) throws TException {

		try {
			// 送信するパラメータを設定
			addSendValues("FLAG", field.getName()); // 区分flag
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(getLoginUserCompanyCode());
			}
			addSendValues("kaiCode", field.getCompanyCode()); // 会社コード
			addSendValues("cbkCbkCode", code); // commonのビジネスロジックに対応
			if (!request(SEARCH_SERVLET)) {
				// クライアント 受信解析エラー。
				errorHandler(field, "S00002");
			}

			return getResultList();

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
			String buttonName = "BankAccount";

			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.BANK_ACCOUNT,
				initParam);

			searchdialog.setStartCode(""); // 開始コード
			searchdialog.setEndCode(""); // 終了コード
			searchdialog.setServerName(buttonName);
			Map<String, String> otherParam = new HashMap<String, String>();
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(super.getLoginUserCompanyCode());
			}
			otherParam.put("companyCode", field.getCompanyCode());

			// 社員、外部のフラグを設定
			otherParam.put("empKbn", BooleanUtil.toString(field.isEmpKbn()));
			otherParam.put("outKbn", BooleanUtil.toString(field.isOutKbn()));
			otherParam.put("termBasisDate", field.getTermBasisDate());
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

		param.put("top", "C02519"); // topのセット
		param.put("code", "C01879"); // コードのセット
		param.put("nameS", "C02656"); // 略称のセット
		param.put("nameK", "C00794"); // 口座番号のセット

		return param;
	}

}

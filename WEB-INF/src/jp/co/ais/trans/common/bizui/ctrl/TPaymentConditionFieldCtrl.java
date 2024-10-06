package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 支払条件フィールドのコントロール
 * 
 * @author roh
 */
public class TPaymentConditionFieldCtrl extends TAppletClientBase {

	/** フィールド */
	protected TPaymentConditionField field;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MG0155CustomerConditionMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * コンストラクタ
	 * 
	 * @param itemField 支払い条件フィールド
	 */
	public TPaymentConditionFieldCtrl(TPaymentConditionField itemField) {
		this.field = itemField;
	}

	/**
	 * ローストフォーカスの対応
	 * 
	 * @return boolean
	 */
	public boolean setPaymentConditionNotice() {

		try {
			String code = field.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// 支払い条件名称の習得
			List list = getFieldDataList(code);

			// 結果無しの場合
			if (list.isEmpty()) {
				showMessage(field, "W00081", "C00672");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				return false;
			}
			// 有効期間のフラグ
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(2));
				Date endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));
				if (termDate.after(endDate) || strDate.after(termDate)) {
					if (!showConfirmMessage(field, "Q00025", "C00238")) {
						return false;
					}
				}

			}

			StringBuilder bfieldName = new StringBuilder();
			bfieldName.append((String) ((List) list.get(0)).get(1));

			field.setNoticeValue(bfieldName.toString());

			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * フィールドデータリスト取得
	 * 
	 * @param code 支払い条件コード
	 * @return 詳細リスト
	 * @throws TException
	 */
	protected List getFieldDataList(String code) throws TException {

		try {
			// 送信するパラメータを設定
			addSendValues("flag", "refPaymentCondition"); // 区分flag
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}
			addSendValues("tjkCode", code);
			addSendValues("triCode", Util.avoidNull(field.getCustomerCode()));
			if (!request(TARGET_SERVLET)) {
				// クライアント 受信解析エラー。
				errorHandler(field, "S00002");
			}

			return getResultList();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * ダイアログ表示
	 */
	public void showSearchDialog() {
		try {

			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.PAYMENTCON_MST,
				initParam);

			searchdialog.setStartCode(""); // 開始コード
			searchdialog.setEndCode(""); // 終了コード
			searchdialog.setServerName("refPaymentCondition");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

			otherParam.put("termBasisDate", field.getTermBasisDate());
			otherParam.put("triCode", field.getCustomerCode());
			searchdialog.setParamMap(otherParam);

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			showDialog(searchdialog);

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}

	}

	/**
	 * 検索dialog初期値のセット
	 * 
	 * @return param タイトル
	 */
	protected Map<String, String> getSearchDialogParam() {
		Map<String, String> param = new HashMap<String, String>();

		param.put("top", "C02325"); // topのセット
		param.put("code", "C00672"); // コードのセット
		param.put("nameS", "C00779"); // 略称
		param.put("nameK", "C00866"); // 索引略称
		param.put("kbn", "2");// データ区分
		return param;
	}

	/**
	 * 検索ダイアログ表示<BR>
	 * 
	 * @param dialog ダイアログ
	 * @return boolean : true 確定の場合 false 取消の場合
	 */
	protected boolean showDialog(REFDialogMasterCtrl dialog) {
		// 確定の場合
		if (dialog.isSettle()) {
			String[] info = dialog.getCurrencyInfo();
			field.setValue(info[0]); // コードをセットした
			field.setNoticeValue(info[1]); // 名称をセットした
			field.getField().requestFocus();
			return true;
		}
		field.getField().requestFocus();
		return false;
	}
}

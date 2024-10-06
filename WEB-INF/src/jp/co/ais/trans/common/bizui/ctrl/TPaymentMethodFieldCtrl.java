package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 支払い方法フィールドのコントロール
 * 
 * @author roh
 */
public class TPaymentMethodFieldCtrl extends TAppletClientBase {

	/** Field */
	protected TPaymentMethodField field;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MP0040PaymentMethodMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * コンストラクタ
	 * 
	 * @param itemField
	 */
	public TPaymentMethodFieldCtrl(TPaymentMethodField itemField) {
		this.field = itemField;
	}

	/**
	 * ローストフォーカスの対応
	 * 
	 * @return boolean
	 */
	public boolean setPaymentNotice() {
		try {
			String code = field.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				field.setBean(null);
				return true;
			}

			// 支払い方法名称の習得
			AP_HOH_MST apHohMst = getFieldDataList(code);

			// 結果無しの場合
			if (Util.isNullOrEmpty(apHohMst)) {
				showMessage(field, "W00081", "C00864");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				// 支払内部コードをセット
				field.setNaiCode("");
				field.setBean(null);
				return false;
			}

			// 有効期間のフラグ
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = apHohMst.getSTR_DATE();
				Date endDate = apHohMst.getEND_DATE();
				if (termDate.after(endDate) || strDate.after(termDate)) {
					if (!showConfirmMessage(field, "Q00025", "C00233")) {
						return false;
					}
				}
			}

			field.setNoticeValue(Util.avoidNull(apHohMst.getHOH_HOH_NAME()));
			// 支払内部コードをセット
			field.setNaiCode(Util.avoidNull(apHohMst.getHOH_NAI_CODE()));
			field.setBean(apHohMst);
			return true;

		} catch (Exception ex) {
			errorHandler(field, ex);
			return false;
		}
	}

	/**
	 * フィールドデータリスト取得
	 * 
	 * @param code 支払い方法コード
	 * @return List 詳細リスト
	 * @throws TException
	 */
	protected AP_HOH_MST getFieldDataList(String code) throws TException {

		try {
			// 送信するパラメータを設定
			// 区分flag
			addSendValues("flag", "refPayment");

			ApHohMstParameter param = new ApHohMstParameter();

			// 会社コード
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				param.setKaiCode(field.getCompanyCode());
			} else {
				param.setKaiCode(getLoginUserCompanyCode());
			}
			// 支払方法コード
			param.setHohCode(code);
			// 支払対象区分
			param.setHohSihKbn(Util.avoidNull(field.getSerchSihKbn()));
			// 支払方法内部コード NOT条件
			param.setNotHohNaiCode(Util.avoidNull(field.getSerchNotNaiCode()));
			// 支払方法内部コード
			if (!Util.isNullOrEmpty(field.getSerchNaiCode())) {
				param.setHohNaiCode(new String[] { field.getSerchNaiCode() });
			}
			// 支払方法リスト
			if (field.getHohCodes() != null && !field.getHohCodes().isEmpty()) {
				param.setHohCodes(field.getHohCodes());
			}

			addSendObject(param);

			if (!request(TARGET_SERVLET)) {
				// 正常に処理されませんでした
				throw new TRequestException();
			}

			return (AP_HOH_MST) getResultDto(AP_HOH_MST.class);

		} catch (IOException ex) {
			throw new TException(ex, "E00009");
		}
	}

	/**
	 * ダイアログ表示
	 */
	public void showSearchDialog() {
		try {
			Map<String, String> param = new HashMap<String, String>();

			// topのセット
			param.put("top", "C02350");
			param.put("code", "C00864"); // コードのセット
			param.put("nameS", "C02657"); // 略称
			param.put("nameK", "C00866"); // 索引略称

			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.PAYMENT_MST, param);

			searchdialog.setStartCode(""); // 開始コード
			searchdialog.setEndCode(""); // 終了コード
			searchdialog.setServerName("refPayment");

			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

			// 支払対象区分
			otherParam.put("sihKbn", Util.avoidNull(field.getSerchSihKbn()));
			// 支払方法内部コード NOT条件
			otherParam.put("notNaiCode", Util.avoidNull(field.getSerchNotNaiCode()));
			// 支払方法内部コード
			otherParam.put("naiCode", Util.avoidNull(field.getSerchNaiCode()));

			otherParam.put("termBasisDate", field.getTermBasisDate());

			// 支払方法リスト
			if (field.getHohCodes() != null && !field.getHohCodes().isEmpty()) {
				searchdialog.setCodes(field.getHohCodes());
			}

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
	 * 検索ダイアログ表示<BR>
	 * 
	 * @param dialog ダイアログ
	 * @return boolean ： true 確定の場合 false 取消の場合
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

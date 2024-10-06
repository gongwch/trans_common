package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 消費税共通フィールドのコントロール
 * 
 * @author roh
 */
public class TTaxFieldCtrl extends TAppletClientBase {

	/** フィールド */
	private TTaxField field;

	/** 有効期間フラグ */
	public boolean termBasisDateFlag;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0130ConsumptionTaxMasterServlet";

	/**
	 * コンストラクタ
	 * 
	 * @param itemField <br>
	 *            消費税フィールド
	 */
	public TTaxFieldCtrl(TTaxField itemField) {
		this.field = itemField;
	}

	/**
	 * ローストフォーカス時の処理
	 * 
	 * @return boolean
	 */
	public boolean taxLostFocus() {
		try {
			// 入力コードを取得する
			String code = field.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.setUsKbnPurchase(false);
				field.setUsKbnSales(false);
				field.setRate(0.0f);
				field.clearOldText();
				return true;
			}

			// 消費税名称の取得
			List list = getFieldTaxDataList(code);

			// 結果無しの場合
			if (list.isEmpty()) {
				showMessage(field, "W00081", "C00573");
				field.setNoticeValue("");
				field.clearOldText();
				// ロストフォーカスを取得する。
				field.requestTextFocus();
				return false;
			}

			if (termBasisDateFlag) {
				// 有効期間内のデータか判定する
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {

					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
					Date strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));
					Date endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(4));

					if (termDate.after(endDate) || strDate.after(termDate)) {
						if (!showConfirmMessage(field, "Q00025", "C00286")) {
							field.requestTextFocus();
							return false;
						}
					}
				}
			}

			// 税レートをセット
			field.setRate(Util.isNullOrEmpty(((List) (list.get(0))).get(5)) ? null : Float.valueOf(Util
				.avoidNull(((List) (list.get(0))).get(5))));

			// 消費税区分
			String taxKbn = (String) ((List) (list.get(0))).get(2);

			// データ区分をセット
			if ("0".equals(taxKbn)) {
				field.setUsKbnPurchase(false);
				field.setUsKbnSales(false);
			} else if ("1".equals(taxKbn)) {
				field.setUsKbnPurchase(false);
				field.setUsKbnSales(true);
			} else {
				field.setUsKbnPurchase(true);
				field.setUsKbnSales(false);
			}

			// データ区分によりエラーを表示する。
			if (!checkKbn(taxKbn)) {
				showMessage(field, "W01033", "C00286");
				field.setNoticeValue("");
				field.clearOldText();
				// ロストフォーカスを取得する。
				field.requestTextFocus();
				return false;
			}
			// フィールドにセットする
			field.setNoticeValue((String) ((List) list.get(0)).get(1));

			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * 消費税区分メソッド 重複指定対応
	 * 
	 * @param taxKbn <br>
	 *            DBから習得した消費税データ区分
	 * @return boolean true：正常 false:失敗
	 */
	private boolean checkKbn(String taxKbn) {

		boolean isValid = false;
		// 対象外区分
		if ((!field.isSales()) && (!field.isPurchase()) && (!field.isElseTax())) {
			isValid = true;
		}

		if (field.isElseTax()) {
			if ("0".equals(taxKbn)) {
				isValid = true;
			}
		}
		// 売り上げ区分
		if (field.isSales()) {
			if ("1".equals(taxKbn)) {
				isValid = true;
			}
		}
		// 仕入れ区分
		if (field.isPurchase()) {
			if ("2".equals(taxKbn)) {
				isValid = true;
			}
		}
		return isValid;
	}

	/**
	 * フィールドデータリスト取得
	 * 
	 * @param code コード
	 * @return 詳細リスト
	 * @throws IOException
	 */
	protected List getFieldTaxDataList(String code) throws IOException {

		// 送信するパラメータを設定
		addSendValues("flag", "refTax"); // 区分flag

		if (!Util.isNullOrEmpty(field.getCompanyCode())) {
			addSendValues("kaiCode", field.getCompanyCode());
		} else {
			addSendValues("kaiCode", getLoginUserCompanyCode());
		}

		addSendValues("zeiCode", code);

		if (!request(TARGET_SERVLET)) {
			// クライアント 受信解析エラー。
			errorHandler(field, "S00002");
		}

		return getResultList();
	}

	/**
	 * ボタン押下時の処理
	 */
	public void btnActionPerformed() {
		try {
			Map<String, String> param = new HashMap<String, String>();

			param.put("top", "C01675"); // タイトルのセット
			param.put("code", "C00573"); // コードのセット
			param.put("nameS", "C00775"); // 略称
			param.put("nameK", "C00828"); // 索引略称

			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.TAX_MST, param);

			searchdialog.setStartCode(""); // 開始コード
			searchdialog.setEndCode(""); // 終了コード
			searchdialog.setServerName("refTax");

			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

			otherParam.put("sales", BooleanUtil.toString(field.isSales()));
			otherParam.put("purchase", BooleanUtil.toString(field.isPurchase()));
			otherParam.put("elseTax", BooleanUtil.toString(field.isElseTax()));
			otherParam.put("termBasisDate", field.getTermBasisDate());
			searchdialog.setParamMap(otherParam);

			// ｺｰﾄﾞ設定、自動検索
			if (!Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// 確定の場合
			if (searchdialog.isSettle()) {
				String[] info = searchdialog.getCurrencyInfo();
				// コードをセットする
				field.setValue(info[0]);
				// 名称をセットする
				field.setNoticeValue(info[1]);
			}

			field.getField().requestFocus();

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}
	}

}

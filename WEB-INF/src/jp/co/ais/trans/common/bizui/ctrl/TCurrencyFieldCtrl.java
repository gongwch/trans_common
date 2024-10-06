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
 * 通貨フィールドのコントロール
 * 
 * @author roh
 */
public class TCurrencyFieldCtrl extends TAppletClientBase {

	/** 通貨フィールド */
	protected TCurrencyField field;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MG0300CurrencyMasterServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * コンストラクタ
	 * 
	 * @param itemField
	 */
	public TCurrencyFieldCtrl(TCurrencyField itemField) {
		this.field = itemField;
	}

	/**
	 * 取引先への値セット
	 * 
	 * @return boolean
	 */
	public boolean setCurrencyInfo() {

		try {
			String code = field.getValue();

			// テキストフィールドに文字列が入力されていたときのみ有効
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// 通貨データ
			List<List<String>> list = getFieldDataList(code);

			// 結果無しの場合
			if (list.isEmpty()) {
				if (!field.isFocusOut()) {
					showMessage(field, "W00081", "C00665");
					field.setNoticeValue("");
					field.clearOldText();
					field.requestTextFocus();
				}
				return false;
			}

			// 有効期間チェック
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = DateUtil.toYMDDate(list.get(0).get(2));
				Date endDate = DateUtil.toYMDDate(list.get(0).get(3));
				if (termDate.after(endDate) || strDate.after(termDate)) {
					if (!field.isFocusOut()) {
						if (!showConfirmMessage(field, "Q00025", "C00371")) {
							field.clearOldText();
							field.requestTextFocus();
							return false;
						}
					} else {
						return false;
					}
				}
			}

			field.setNoticeValue(list.get(0).get(1));
			if (Util.isNullOrEmpty(list.get(0).get(4))) {
				field.setDecKeta(0);
			} else {
				field.setDecKeta((Integer.parseInt(list.get(0).get(4))));
			}

			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * フィールドデータリスト取得
	 * 
	 * @param code 通貨コード
	 * @return 詳細リスト
	 * @throws TException
	 */
	public List<List<String>> getFieldDataList(String code) throws TException {

		try {
			// 送信するパラメータを設定
			addSendValues("flag", "refCurrency"); // 区分flag
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}
			addSendValues("curCode", code);
			if (!request(TARGET_SERVLET)) {
				throw new TRequestException();
			}

			return getResultList();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * ダイアログ表示
	 * 
	 * @return boolean
	 */
	public boolean showSearchDialog() {
		try {

			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.CURRENCY_MST,
				initParam);

			searchdialog.setStartCode(""); // 開始コード
			searchdialog.setEndCode(""); // 終了コード
			searchdialog.setServerName("refCurrency");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

			otherParam.put("termBasisDate", field.getTermBasisDate());
			otherParam.put("beginCode", field.getBeginCode());// 開始コード
			otherParam.put("endCode", field.getEndCode()); // 終了コード
			searchdialog.setParamMap(otherParam);

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// 検索ダイアログを表示

			boolean isSettled = showDialog(searchdialog);

			return isSettled;

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
			return false;
		}

	}

	/**
	 * 検索dialog初期値のセット
	 * 
	 * @return param タイトル
	 */
	protected Map<String, String> getSearchDialogParam() {

		Map<String, String> param = new HashMap<String, String>();

		param.put("top", "C01674"); // topのセット
		param.put("code", "C00665"); // コードのセット
		param.put("nameS", "C00881"); // 略称
		param.put("nameK", "C00882"); // 索引略称

		return param;
	}

	/**
	 * 検索ダイアログ表示<BR>
	 * 
	 * @param dialog ダイアログ
	 * @return true 確定の場合 false 取消の場合
	 */
	protected boolean showDialog(REFDialogMasterCtrl dialog) {
		// 確定の場合
		if (dialog.isSettle()) {
			String[] info = dialog.getCurrencyInfo();
			field.setValue(info[0]);

			field.getField().requestFocus();
			return true;
		}
		field.getField().requestFocus();
		return false;
	}

}

package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 通貨マスタダイアログ コントロール
 */
public class MG0300EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 通貨マスタダイアログ */
	protected MG0300EditDisplayDialog dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0300CurrencyMasterServlet";
	}

	protected boolean isUpdate = false;

	/**
	 * コンストラクタ
	 */
	MG0300EditDisplayDialogCtrl() {
		// 処理なし
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0300EditDisplayDialogCtrl(Frame parent, String titleid) {
		dialog = new MG0300EditDisplayDialog(parent, true, this, titleid);

		dialog.ctrlCurrencyCode.getField().requestFocus();

		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		try {
			dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
			dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));
		} catch (Exception e) {
			errorHandler(dialog, e, "E00009");
		}
		dialog.ctrlCurrencyCode.getField().requestFocus();

	}

	/**
	 * 表示
	 */
	void show() {
		dialog.setVisible(true);
	}

	/**
	 * 値のセット
	 * 
	 * @param map セットする値
	 */

	void setValues(Map map) {
		dialog.ctrlCurrencyCode.setValue((String) map.get("curCode"));
		dialog.ctrlCurrencyName.setValue((String) map.get("curName"));
		dialog.ctrlCurrencyAbbreviatedName.setValue((String) map.get("curName_S"));
		dialog.ctrlCurrencyNameForSearch.setValue((String) map.get("curName_K"));
		dialog.numDecimalPoint.setValue((String) map.get("decKeta"));
		dialog.numRatecoefficient.setValue((String) map.get("ratePow"));
		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("convKbn")));
		dialog.rdoCalculateMultiplication.setSelected(!boo);
		dialog.rdoCalculateDivision.setSelected(boo);

		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは通貨コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlCurrencyCode.setEditable(!isUpdate);
		if (isUpdate) {
			dialog.ctrlCurrencyName.getField().requestFocus();
		}
	}

	boolean checkDialog() {
		try {
			// コード
			if (Util.isNullOrEmpty(dialog.ctrlCurrencyCode.getValue())) {
				showMessage(dialog, "I00002", "C00665");
				dialog.ctrlCurrencyCode.requestFocus();
				return false;
			}

			if (!isUpdate && checkCode(dialog.ctrlCurrencyCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlCurrencyCode.requestFocus();
				return false;
			}

			if (Util.isNullOrEmpty(dialog.ctrlCurrencyName.getValue())) {
				showMessage(dialog, "I00002", "C00880");
				dialog.ctrlCurrencyName.requestFocus();
				return false;
			}

			if (Util.isNullOrEmpty(dialog.ctrlCurrencyAbbreviatedName.getValue())) {
				showMessage(dialog, "I00002", "C00881");
				dialog.ctrlCurrencyAbbreviatedName.requestFocus();
				return false;
			}

			if (Util.isNullOrEmpty(dialog.ctrlCurrencyNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00882");
				dialog.ctrlCurrencyNameForSearch.requestFocus();
				return false;
			}

			if (Util.isNullOrEmpty(dialog.numRatecoefficient.getValue())) {
				showMessage(dialog, "I00002", "C00896");
				dialog.numRatecoefficient.requestFocus();
				return false;
			}
			if (Util.isNullOrEmpty(dialog.numDecimalPoint.getValue())) {
				showMessage(dialog, "I00002", "C00884");
				dialog.numDecimalPoint.requestFocus();
				return false;
			}
			String valueDP = dialog.numDecimalPoint.getValue();
			if (!Util.isNullOrEmpty(valueDP) && (Integer.parseInt(valueDP) < 0 || 4 < Integer.parseInt(valueDP))) {
				showMessage(dialog, "W00065", 0, 4);
				dialog.numDecimalPoint.requestFocus();
				return false;
			}
			String valueR = dialog.numRatecoefficient.getValue();
			if (!Util.isNullOrEmpty(valueR) && (Integer.parseInt(valueR) < -9 || 9 < Integer.parseInt(valueR))) {
				showMessage(dialog, "W00065", -9, 9);
				dialog.numRatecoefficient.requestFocus();
				return false;
			}

			// 開始年月日
			if (dialog.dtBeginDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00055");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}
			// 終了年月日
			if (dialog.dtEndDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00261");
				dialog.dtEndDate.getCalendar().requestFocus();
				return false;
			}

			// 開始年月日＜＝終了年月日にしてください
			if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
				showMessage(dialog, "W00035", "");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}

			return true;
		} catch (IOException e) {
			ClientErrorHandler.handledException(e);
			return false;
		}
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// 確定ボタン押下 チェックOKなら閉じる
			if (!this.checkDialog()) return;
			dialog.setVisible(!this.checkDialog());
		} else {
			dialog.setVisible(false);// 閉じる
		}
	}

	/**
	 * 確定ボタンが押されたかどうか
	 * 
	 * @return 確定の場合true
	 */
	boolean isSettle() {
		return dialog.isSettle;
	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */

	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("curCode", dialog.ctrlCurrencyCode.getValue());
		map.put("curName", dialog.ctrlCurrencyName.getValue());
		map.put("curName_S", dialog.ctrlCurrencyAbbreviatedName.getValue());
		map.put("curName_K", dialog.ctrlCurrencyNameForSearch.getValue());
		map.put("decKeta", dialog.numDecimalPoint.getValue());
		map.put("ratePow", dialog.numRatecoefficient.getValue());
		map.put("convKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoCalculateDivision.isSelected())));
		map.put("strDate", dialog.dtBeginDate.getValue());
		map.put("endDate", dialog.dtEndDate.getValue());

		return map;
	}

	boolean checkCode(String code) throws IOException {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("curCode", code);

		if (!request(getServletName())) {
			errorHandler(dialog);
			return true;
		}
		List result = super.getResultList();
		return (result.size() > 0);
	}
}

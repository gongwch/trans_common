package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * 環境設定マスタDialogCtrl
 */
public class MG0010EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 環境設定マスタダイアログ */
	protected MG0010EditDisplayDialog dialog;

	protected boolean isUpdate;

	private static final String TARGET_SERVLET = "MG0010EnvironmentalSettingMasterServlet";

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleID
	 */
	MG0010EditDisplayDialogCtrl(Frame parent, String titleID) {
		dialog = new MG0010EditDisplayDialog(parent, true, this, titleID);

		dialog.txtBackgroundColor.setEnabled(true);
		dialog.txtBackgroundColor.setEditable(false);

		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		dialog.ctrlCompanyCode.requestTextFocus();

		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));
	}

	/**
	 * 表示
	 * 
	 * @param isEnabledknrCode 会社コードエリア編集可(true)、付加(false)
	 */
	void show(boolean isEnabledknrCode) {
		dialog.setVisible(true);
		dialog.ctrlCompanyCode.setEditable(isEnabledknrCode);
	}

	/**
	 * 値のセット
	 * 
	 * @param map
	 */

	void setValues(Map map) {
		dialog.ctrlCompanyCode.setValue((String) map.get("kaiCode"));
		dialog.ctrlCompanyName.setValue((String) map.get("kaiName"));
		dialog.ctrlCompanyAbbreviatedName.setValue((String) map.get("kaiName_S"));
		dialog.ctrlAddress1.setValue((String) map.get("jyu1"));
		dialog.ctrlAddress2.setValue((String) map.get("jyu2"));
		dialog.ctrlAddressKana.setValue((String) map.get("jyuKana"));
		dialog.ctrlPostcode.setValue((String) map.get("zip"));
		dialog.ctrlTelephoneNumber.setValue((String) map.get("tel"));
		dialog.ctrlFaxNumber.setValue((String) map.get("fax"));
		if ((map.get("foreColor")).toString().trim().length() < 7) {
			showMessage(dialog, "W00188", (map.get("foreColor")).toString());
			String color1 = "#FFFFFF";
			dialog.txtBackgroundColor.setValue(color1);
			String r = color1.substring(1, 3);
			int red = Integer.parseInt(r, 16);
			String g = color1.substring(3, 5);
			int green = Integer.parseInt(g, 16);
			String b = color1.substring(5, 7);
			int blue = Integer.parseInt(b, 16);
			Color color = new Color(red, green, blue);
			dialog.txtBackgroundColor.setBackground(color);
		} else {
			dialog.txtBackgroundColor.setValue(map.get("foreColor"));
			String r = ((String) map.get("foreColor")).substring(1, 3);
			int red = Integer.parseInt(r, 16);
			String g = ((String) map.get("foreColor")).substring(3, 5);
			int green = Integer.parseInt(g, 16);
			String b = ((String) map.get("foreColor")).substring(5, 7);
			int blue = Integer.parseInt(b, 16);
			Color color = new Color(red, green, blue);
			dialog.txtBackgroundColor.setBackground(color);
		}
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは管理コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));

		dialog.ctrlCompanyCode.setEditable(isUpdate == false);

		if (isUpdate) {
			dialog.ctrlCompanyName.getField().requestFocus();
		}

	}

	boolean checkDialog() {

		// "会社コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlCompanyCode.getValue())) {
			showMessage(dialog, "I00002", "C00596");
			dialog.ctrlCompanyCode.requestFocus();
			return false;
		}

		if (!Util.isNullOrEmpty(dialog.ctrlCompanyCode.getValue())) {
			if (!isUpdate && checkCode(dialog.ctrlCompanyCode.getValue())) {
				// 警告メッセージ表示
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlCompanyCode.requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 会社名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlCompanyName.getValue())) {
			showMessage(dialog, "I00002", "C00685");
			dialog.ctrlCompanyName.requestFocus();

			return false;
		}

		// 会社略称チェック
		if (Util.isNullOrEmpty(dialog.ctrlCompanyAbbreviatedName.getValue())) {
			showMessage(dialog, "I00002", "C00686");
			dialog.ctrlCompanyAbbreviatedName.requestFocus();
			return false;
		}

		// 開始年月日
		if (Util.isNullOrEmpty(dialog.dtBeginDate.getValue())) {
			super.showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		// 終了年月日
		if (Util.isNullOrEmpty(dialog.dtEndDate.getValue())) {
			super.showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.requestFocus();
			return false;

		}

		// 開始年月日,終了年月日チェック
		if ((!Util.isNullOrEmpty(dialog.dtBeginDate.getValue()) && !Util.isNullOrEmpty(dialog.dtEndDate.getValue()))
			&& !Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue())) {
			super.showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		// 背景色チェック
		if (Util.isNullOrEmpty(dialog.txtBackgroundColor.getValue())) {
			showMessage(dialog, "I00002", "C00428");
			dialog.btnBackgroundColor.requestFocus();
			return false;
		}

		return true;

	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {
		// エラーがある場合にはダイアログを閉じない
		if (dialog.isSettle) {

			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} else {
			dialog.setVisible(false);
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

		map.put("kaiCode", dialog.ctrlCompanyCode.getValue());
		map.put("kaiName", dialog.ctrlCompanyName.getValue());
		map.put("kaiName_S", dialog.ctrlCompanyAbbreviatedName.getValue());
		map.put("jyu1", dialog.ctrlAddress1.getValue());
		map.put("jyu2", dialog.ctrlAddress2.getValue());
		map.put("jyuKana", dialog.ctrlAddressKana.getValue());
		map.put("zip", dialog.ctrlPostcode.getValue());
		map.put("tel", dialog.ctrlTelephoneNumber.getValue());
		map.put("fax", dialog.ctrlFaxNumber.getValue());
		map.put("foreColor", dialog.txtBackgroundColor.getValue());
		map.put("strDate", dialog.dtBeginDate.getValue());
		map.put("endDate", dialog.dtEndDate.getValue());

		return map;
	}

	boolean checkCode(String code) {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", code);

		// 送信
		try {
			request(TARGET_SERVLET);
		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(dialog, e, "E00009");
		}

		List result = super.getResultList();
		return (result.size() > 0);
	}
}

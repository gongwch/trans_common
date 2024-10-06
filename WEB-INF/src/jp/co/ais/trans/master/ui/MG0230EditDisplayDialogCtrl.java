package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 管理マスタダイアログ コントロール
 * 
 * @author ookawara
 */
public class MG0230EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 管理マスタダイアログ */
	protected MG0230EditDisplayDialog dialog;

	private boolean isUpdate;

	private static final String TARGET_SERVLET = "MG0230Management6MasterServlet";

	String knrName;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0230EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 管理マスタダイアログの初期化
		dialog = createDialog(parent, titleid);

		knrName = CompanyControlHelper230.getInstance(this.getLoginUserCompanyCode()).getKnrName6();
		dialog.ctrlManagementCode.getLabel().setText(knrName + this.getWord("C00174"));
		dialog.ctrlManagementName.getLabel().setText(knrName + this.getWord("C00518"));
		dialog.ctrlManagementAbbreviationName.getLabel().setText(knrName + this.getWord("C00548"));
		dialog.ctrlManagementNameForSearch.getLabel().setText(knrName + this.getWord("C00160"));

		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});

		dialog.ctrlManagementCode.getField().requestFocus();

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	/**
	 * @param parent 親コンテナー
	 * @param titleid タイトル
	 * @return ダイアログ
	 */
	protected MG0230EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0230EditDisplayDialog(parent, true, this, titleid);
	}

	/**
	 * 表示
	 * 
	 * @param isEnabledknrCode 管理コードエリア編集可(true)、付加(false)
	 */
	void show(boolean isEnabledknrCode) {
		// 画面を表示する
		dialog.setVisible(true);
		// 部門コードの設定
		dialog.ctrlManagementCode.setEditable(isEnabledknrCode);
	}

	/**
	 * 値のセット
	 * 
	 * @param map
	 */
	void setValues(Map map) {
		// 管理コードの設定
		dialog.ctrlManagementCode.setValue((String) map.get("knrCode"));
		// 管理名称の設定
		dialog.ctrlManagementName.setValue((String) map.get("knrName"));
		// 管理略称の設定
		dialog.ctrlManagementAbbreviationName.setValue((String) map.get("knrName_S"));
		// 管理検索名称の設定
		dialog.ctrlManagementNameForSearch.setValue((String) map.get("knrName_K"));
		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは管理コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		// 管理コードの設定
		dialog.ctrlManagementCode.setEditable(isUpdate == false);

		if (isUpdate) {
			dialog.ctrlManagementName.getField().requestFocus(isUpdate);
		}

	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// 確定ボタン押下 チェックOKなら閉じる
			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} else {
			// 戻るボタン押下
			// 画面を閉める
			dialog.setVisible(false);
		}
	}

	boolean checkDialog() {
		// 管理コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlManagementCode.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", knrName + getWord("C00174"));
			dialog.ctrlManagementCode.requestFocus();
			// エラーを返す
			return false;
		}
		// 管理コードが存在しています
		if (!isUpdate && checkCode(dialog.ctrlManagementCode.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlManagementCode.requestFocus();
			// エラーを返す
			return false;
		}
		// 管理名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlManagementName.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", knrName + getWord("C00518"));
			dialog.ctrlManagementName.requestFocus();
			// エラーを返す
			return false;
		}
		// 管理略称チェック
		if (Util.isNullOrEmpty(dialog.ctrlManagementAbbreviationName.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", knrName + getWord("C00548"));
			dialog.ctrlManagementAbbreviationName.requestFocus();
			// エラーを返す
			return false;
		}
		// 管理検索名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlManagementNameForSearch.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", knrName + getWord("C00160"));
			dialog.ctrlManagementNameForSearch.requestFocus();
			// エラーを返す
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

		// 年月日チェック
		if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
			// 警告メッセージ表示
			showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.getCalendar().requestFocus();
			// エラーを返す
			return false;
		}

		return true;
	}

	/**
	 * 確定ボタンが押されたかどうか
	 * 
	 * @return 確定の場合true
	 */
	boolean isSettle() {
		// 処理種別の設定
		return dialog.isSettle;
	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// 管理コードの設定
		map.put("knrCode", dialog.ctrlManagementCode.getValue());
		// 管理名称の設定
		map.put("knrName", dialog.ctrlManagementName.getValue());
		// 管理略称の設定
		map.put("knrName_K", dialog.ctrlManagementNameForSearch.getValue());
		// 管理検索名称の設定
		map.put("knrName_S", dialog.ctrlManagementAbbreviationName.getValue());
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 会社コードの設定
		map.put("kaiCode", getLoginUserCompanyCode());
		// 結果を返す
		return map;
	}

	boolean checkCode(String code) {
		// 管理コード未入力
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 管理コードの設定
		addSendValues("knrCode", code);

		try {
			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return true;
			}

			// 結果を取得
			List result = super.getResultList();
			// 結果を返す
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}
	}

}

class CompanyControlHelper230 extends TAppletClientBase {

	private static Map instances = Collections.synchronizedMap(new HashMap());

	/**
	 * @param companyCode
	 * @return CompanyControlHelper230
	 */
	public static CompanyControlHelper230 getInstance(String companyCode) {
		if (!instances.containsKey(companyCode)) {
			CompanyControlHelper230 instance = new CompanyControlHelper230(companyCode);
			instances.put(companyCode, instance);
		}
		return (CompanyControlHelper230) instances.get(companyCode);
	}

	/**
	 * @param companyCode
	 */
	public static void reload(String companyCode) {
		instances.remove(companyCode);
	}

	private static final String TARGET_SERVLET = "MG0030CompanyControlMasterServlet";

	private CompanyControlHelper230(String companyCode) {
		addSendValues("flag", "findone");
		addSendValues("kaiCode", companyCode);
		// try {
		// request(TARGET_SERVLET);
		// } catch (Exception ex) {
		// ClientErrorHandler.handledException(ex);
		// }
		//
		// // サーバ側のエラー場合
		// Map error = getError();
		// if (error.size() > 0) {
		// errorHandler(this.getPanel(), error);
		// }
		try {
			if (!request(TARGET_SERVLET)) {
				errorHandler();
			} else {
				List result = super.getResultList();
				if (result != null && result.size() > 0) {
					List inner = (List) result.get(0);

					knrKbn1 = (String) inner.get(5);
					knrKbn2 = (String) inner.get(6);
					knrKbn3 = (String) inner.get(7);
					knrKbn4 = (String) inner.get(8);
					knrKbn5 = (String) inner.get(9);
					knrKbn6 = (String) inner.get(10);
					knrName1 = (String) inner.get(11);
					knrName2 = (String) inner.get(12);
					knrName3 = (String) inner.get(13);
					knrName4 = (String) inner.get(14);
					knrName5 = (String) inner.get(15);
					knrName6 = (String) inner.get(16);

					cmpHmKbn1 = (String) inner.get(17);
					cmpHmKbn2 = (String) inner.get(18);
					cmpHmKbn3 = (String) inner.get(19);
					cmpHmName1 = (String) inner.get(20);
					cmpHmName2 = (String) inner.get(21);
					cmpHmName3 = (String) inner.get(22);

					kmkName = (String) inner.get(1);
					hkmName = (String) inner.get(2);
					ukmName = (String) inner.get(4);
					ukmKbn = (String) inner.get(3);
				}
			}

			if (Util.isNullOrEmpty(knrKbn1) || "0".equals(knrKbn1) || Util.isNullOrEmpty(knrName1)) {
				knrKbn1 = "0";
				knrName1 = "C01025";
			}
			if (Util.isNullOrEmpty(knrKbn2) || "0".equals(knrKbn2) || Util.isNullOrEmpty(knrName2)) {
				knrKbn2 = "0";
				knrName2 = "C01027";
			}
			if (Util.isNullOrEmpty(knrKbn3) || "0".equals(knrKbn3) || Util.isNullOrEmpty(knrName3)) {
				knrKbn3 = "0";
				knrName3 = "C01029";
			}
			if (Util.isNullOrEmpty(knrKbn4) || "0".equals(knrKbn4) || Util.isNullOrEmpty(knrName4)) {
				knrKbn4 = "0";
				knrName4 = "C01031";
			}
			if (Util.isNullOrEmpty(knrKbn5) || "0".equals(knrKbn5) || Util.isNullOrEmpty(knrName5)) {
				knrKbn5 = "0";
				knrName5 = "C01033";
			}
			if (Util.isNullOrEmpty(knrKbn6) || "0".equals(knrKbn6) || Util.isNullOrEmpty(knrName6)) {
				knrKbn6 = "0";
				knrName6 = "C01035";
			}

			if (Util.isNullOrEmpty(cmpHmKbn1) || "0".equals(cmpHmKbn1) || Util.isNullOrEmpty(cmpHmName1)) {
				cmpHmKbn1 = "0";
				cmpHmName1 = "C01291";
			}
			if (Util.isNullOrEmpty(cmpHmKbn2) || "0".equals(cmpHmKbn2) || Util.isNullOrEmpty(cmpHmName2)) {
				cmpHmKbn2 = "0";
				cmpHmName2 = "C01292";
			}
			if (Util.isNullOrEmpty(cmpHmKbn3) || "0".equals(cmpHmKbn3) || Util.isNullOrEmpty(cmpHmName3)) {
				cmpHmKbn3 = "0";
				cmpHmName3 = "C01293";
			}

			if (Util.isNullOrEmpty(kmkKbn) || "0".equals(kmkKbn) || Util.isNullOrEmpty(kmkName)) {
				kmkKbn = "0";
				kmkName = "C00077";
			}
			if (Util.isNullOrEmpty(hkmKbn) || "0".equals(hkmKbn) || Util.isNullOrEmpty(hkmName)) {
				hkmKbn = "0";
				hkmName = "C00488";
			}
			if (Util.isNullOrEmpty(ukmKbn) || "0".equals(ukmKbn) || Util.isNullOrEmpty(ukmName)) {
				ukmKbn = "0";
				ukmName = "C00025";
			}
		} catch (IOException e) {
			ClientErrorHandler.handledException(e);
		}
	}

	private String knrKbn1;

	private String knrKbn2;

	private String knrKbn3;

	private String knrKbn4;

	private String knrKbn5;

	private String knrKbn6;

	private String knrName1;

	private String knrName2;

	private String knrName3;

	private String knrName4;

	private String knrName5;

	private String knrName6;

	private String cmpHmKbn1;

	private String cmpHmKbn2;

	private String cmpHmKbn3;

	private String cmpHmName1;

	private String cmpHmName2;

	private String cmpHmName3;

	private String kmkName;

	private String hkmName;

	private String ukmName;

	private String kmkKbn = "1";

	private String hkmKbn = "1";

	private String ukmKbn;

	/**
	 * @return String
	 */
	public String getKnrName1() {
		if ("0".equals(knrKbn1)) {
			return this.getWord(knrName1);
		}
		return knrName1;
	}

	/**
	 * @return String
	 */
	public String getKnrName2() {
		if ("0".equals(knrKbn2)) {
			return this.getWord(knrName2);
		}
		return knrName2;
	}

	/**
	 * @return String
	 */
	public String getKnrName3() {
		if ("0".equals(knrKbn3)) {
			return this.getWord(knrName3);
		}
		return knrName3;
	}

	/**
	 * @return String
	 */
	public String getKnrName4() {
		if ("0".equals(knrKbn4)) {
			return this.getWord(knrName4);
		}
		return knrName4;
	}

	/**
	 * @return String
	 */
	public String getKnrName5() {
		if ("0".equals(knrKbn5)) {
			return this.getWord(knrName5);
		}
		return knrName5;
	}

	/**
	 * @return String
	 */
	public String getKnrName6() {
		if ("0".equals(knrKbn6)) {
			return this.getWord(knrName6);
		}
		return knrName6;
	}

	/**
	 * @return String
	 */
	public String getCmpHmName1() {
		if ("0".equals(cmpHmKbn1)) {
			return this.getWord(cmpHmName1);
		}
		return cmpHmName1;
	}

	/**
	 * @return String
	 */
	public String getCmpHmName2() {
		if ("0".equals(cmpHmKbn2)) {
			return this.getWord(cmpHmName2);
		}
		return cmpHmName2;
	}

	/**
	 * @return String
	 */
	public String getCmpHmName3() {
		if ("0".equals(cmpHmKbn3)) {
			return this.getWord(cmpHmName3);
		}
		return cmpHmName3;
	}

	/**
	 * @return String
	 */
	public String getKmkName() {
		if ("0".equals(kmkKbn)) {
			return this.getWord(kmkName);
		}
		return kmkName;
	}

	/**
	 * @return String
	 */
	public String getHkmName() {
		if ("0".equals(hkmKbn)) {
			return this.getWord(hkmName);
		}
		return hkmName;
	}

	/**
	 * @return String
	 */
	public String getUkmName() {
		if ("0".equals(ukmKbn)) {
			return this.getWord(ukmName);
		}
		return ukmName;
	}
}

package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * システム区分マスタダイアログ コントロール
 */
public class MG0320EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** システム区分マスタダイアログ */
	protected MG0320EditDisplayDialog dialog;

	/** 処理区分 */
	private boolean isUpdate;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0320SystemDivisionMasterServlet";
	}

	protected Map dataKbnMap;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0320EditDisplayDialogCtrl(Frame parent, String titleid) {
		// システム区分マスタダイアログの初期化
		dialog = createDialog(parent, titleid);
		// タイトルを設定する
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				// 処理種別の設定
				dialog.btnReturn.doClick();
			}
		});

		// fix bug: 確定ボタン押下して UI チェックが失敗の場合は、
		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		dialog.ctrlSystemDivision.getField().requestFocus();

	}

	/**
	 * @param parent 親コンテナー
	 * @param titleid タイトル
	 * @return ダイアログ
	 */
	protected MG0320EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0320EditDisplayDialog(parent, true, this, titleid);
	}

	/**
	 * 表示
	 * 
	 * @param isEnabledsysKbn システム区分コードエリア編集可(true)、付加(false)
	 */
	void show(boolean isEnabledsysKbn) {
		// 画面を表示する
		dialog.setVisible(true);
		// システム区分コードの設定
		dialog.ctrlSystemDivision.setEditable(isEnabledsysKbn);
	}

	/**
	 * 値のセット
	 * 
	 * @param map
	 */
	void setValues(Map map) {
		// システム区分コードの設定
		dialog.ctrlSystemDivision.setValue((String) map.get("sysKbn"));
		// システム区分名称の設定
		dialog.ctrlSystemDivisionName.setValue((String) map.get("sysName"));
		// システム区分略称の設定
		dialog.ctrlSystemDivisionAbbreviatedName.setValue((String) map.get("sysName_S"));
		// システム区分検索名称の設定
		dialog.ctrlSystemDivisionNameForSearch.setValue((String) map.get("sysName_K"));
		// 外部システム区分の設定
		this.setComboBoxSelectedItem(dialog.ctrlOutsideSystemDivision.getComboBox(), (String) map.get("osyKbn"));
		// 編集モードのときはシステム区分コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		// システム区分コードの設定
		dialog.ctrlSystemDivision.setEditable(!isUpdate);
		if (isUpdate) {
			dialog.ctrlSystemDivisionName.getField().requestFocus();
		}
	}

	boolean checkDialog() {
		// システム区分コード未入力チェック
		if (Util.isNullOrEmpty(dialog.ctrlSystemDivision.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00980");
			dialog.ctrlSystemDivision.requestFocus();
			// エラーを返す
			return false;
		}

		// システム区分コードが存在していますチェック
		if (!isUpdate && checkCode(dialog.ctrlSystemDivision.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlSystemDivision.requestFocus();
			// エラーを返す
			return false;
		}

		// システム区分名称未入力チェック
		if (Util.isNullOrEmpty(dialog.ctrlSystemDivisionName.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00832");
			dialog.ctrlSystemDivisionName.requestFocus();
			// エラーを返す
			return false;
		}

		// システム区分略称未入力チェック
		if (Util.isNullOrEmpty(dialog.ctrlSystemDivisionAbbreviatedName.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00833");
			dialog.ctrlSystemDivisionAbbreviatedName.requestFocus();
			// エラーを返す
			return false;
		}

		// システム区分検索名称未入力チェック
		if (Util.isNullOrEmpty(dialog.ctrlSystemDivisionNameForSearch.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00834");
			dialog.ctrlSystemDivisionNameForSearch.requestFocus();
			// エラーを返す
			return false;
		}

		// 外部システム区分未入力チェック
		if (Util.isNullOrEmpty(this.getComboBoxSelectedValue(dialog.ctrlOutsideSystemDivision.getComboBox()))) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C01018");
			dialog.ctrlOutsideSystemDivision.getComboBox().requestFocus();
			// エラーを返す
			return false;
		}

		// 成功を返
		return true;
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
			// 画面を閉める
			dialog.setVisible(false);
		}
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
	 * @param map
	 */
	public void setDataKbnMap(Map map) {
		this.dataKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlOutsideSystemDivision.getComboBox(), dataKbnMap);
	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("sysKbn", dialog.ctrlSystemDivision.getValue());
		map.put("sysName", dialog.ctrlSystemDivisionName.getValue());
		map.put("sysName_K", dialog.ctrlSystemDivisionNameForSearch.getValue());
		map.put("sysName_S", dialog.ctrlSystemDivisionAbbreviatedName.getValue());
		map.put("osyKbn", this.getComboBoxSelectedValue(dialog.ctrlOutsideSystemDivision.getComboBox()));
		map.put("kaiCode", getLoginUserCompanyCode());
		// 結果を返す
		return map;
	}

	boolean checkCode(String code) {
		try {
			if (Util.isNullOrEmpty(code)) {
				return false;
			}
			// 処理種別の設定
			addSendValues("flag", "checkcode");
			addSendValues("kaiCode", getLoginUserCompanyCode());
			addSendValues("sysKbn", code);

			if (!request(getServletName())) {
				// サーバ側のエラー場合
				errorHandler(dialog);
				return false;
			}

			// 結果を取得
			List result = super.getResultList();
			// 結果を返す
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(dialog);
			return false;
		}
	}

}

package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * 
 */
public class MG0120EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 摘要マスタダイアログ */
	protected MG0120EditDisplayDialog dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0120MemoMasterServlet";
	}

	/** 処理区分 */
	protected boolean isUpdate = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleID
	 */
	MG0120EditDisplayDialogCtrl(Frame parent, String titleID) {
		// 摘要マスタダイアログの初期化
		dialog = new MG0120EditDisplayDialog(parent, true, this, titleID);

		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});
		// 摘要区分の設定
		dialog.rdoSlipMemo.setSelected(true);
		dialog.rdoSlipMemo.requestFocus();

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	// mapの初期化
	private Map dataKbnMap;

	/**
	 * @param map
	 */
	public void setDataKbnMap(Map map) {
		this.dataKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlDataDivision.getComboBox(), dataKbnMap);
	}

	/**
	 * 表示
	 */
	void show() {
		// 画面を表示する
		dialog.setVisible(true);
	}

	/**
	 * 値のセット
	 * 
	 * @param map セットする値
	 */

	void setValues(Map map) {
		// 科目コードの設定
		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("tekKbn")));
		dialog.rdoSlipMemo.setSelected(!boo);
		dialog.rdoRowMemo.setSelected(boo);
		// ﾃﾞｰﾀ区分の設定
		this.setComboBoxSelectedItem(dialog.ctrlDataDivision.getComboBox(), (String) map.get("dataKbn"));
		// 摘要コードの設定
		dialog.ctrlMemoCode.setValue((String) map.get("tekCode"));
		// 摘要名称の設定
		dialog.ctrlMemoName.setValue((String) map.get("tekName"));
		// 摘要検索名称の設定
		dialog.ctrlMemoNameForSearch.setValue((String) map.get("tekName_K"));
		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは摘要コード、摘要区分とが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		if (isUpdate) {
			dialog.ctrlDataDivision.getComboBox().requestFocus();
		}

		dialog.ctrlMemoCode.setEditable(!isUpdate);
		dialog.rdoSlipMemo.setEnabled(!isUpdate);
		dialog.rdoRowMemo.setEnabled(!isUpdate);
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

		// 摘要区分チェック

		if (!dialog.rdoSlipMemo.isSelected() && !dialog.rdoRowMemo.isSelected()) {
			showMessage(dialog, "I00003", "C00568");
			dialog.rdoSlipMemo.requestFocus();
			return false;
		}

		// データ区分

		if (Util.isNullOrEmpty(dialog.ctrlDataDivision.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C00567");
			dialog.ctrlDataDivision.getComboBox().requestFocus();
			return false;
		}

		// 摘要コード

		if (Util.isNullOrEmpty(dialog.ctrlMemoCode.getValue())) {
			showMessage(dialog, "I00002", "C00564");
			dialog.ctrlMemoCode.requestFocus();
			return false;
		}

		if (!isUpdate
			&& checkCode(String.valueOf(BooleanUtil.toInt(dialog.rdoRowMemo.isSelected())), dialog.ctrlMemoCode
				.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlMemoCode.requestFocus();
			return false;
		}
		// 摘要名称チェック

		if (Util.isNullOrEmpty(dialog.ctrlMemoName.getValue())) {
			showMessage(dialog, "I00002", "C00565");
			dialog.ctrlMemoName.requestFocus();
			return false;
		}

		// 摘要検索名称チェック

		if (Util.isNullOrEmpty(dialog.ctrlMemoNameForSearch.getValue())) {
			showMessage(dialog, "I00002", "C00566");
			dialog.ctrlMemoNameForSearch.requestFocus();
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
		if (!Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue())) {
			showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.getCalendar().requestFocus();
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
		// 摘要区分の設定
		map.put("tekKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoRowMemo.isSelected())));
		// ﾃﾞｰﾀ区分の設定
		map.put("dataKbn", this.getComboBoxSelectedValue(dialog.ctrlDataDivision.getComboBox()));
		// 摘要コードの設定
		map.put("tekCode", dialog.ctrlMemoCode.getValue());
		// 摘要名称の設定
		map.put("tekName", dialog.ctrlMemoName.getValue());
		// 摘要検索名称の設定
		map.put("tekName_K", dialog.ctrlMemoNameForSearch.getValue());
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	boolean checkCode(String tekKbn, String tekCode) {
		try {
			// 摘要区分未入力
			if (Util.isNullOrEmpty(tekKbn) && Util.isNullOrEmpty(tekCode)) {
				return false;
			}
			// 処理種別の設定
			addSendValues("flag", "checkcode");
			// 会社コードの設定
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// 摘要区分の設定
			addSendValues("tekKbn", tekKbn);
			// 摘要コードの設定
			addSendValues("tekCode", tekCode);

			if (!request(getServletName())) {
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

package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * 部門マスタダイアログ コントロール
 * 
 * @author ookawara
 */
public class MG0060EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 部門マスタダイアログ */
	protected MG0060EditDisplayDialog dialog;

	/** 処理区分 */
	private boolean isUpdate;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0060DepartmentMasterServlet";

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0060EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 部門マスタダイアログの初期化
		dialog = createDialog(parent, titleid);

		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				// 処理種別の設定
				dialog.btnReturn.doClick();
			}
		});
		dialog.ctrlDepartmentCode.getField().requestFocus();

		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));

	}

	/**
	 * @param parent 親コンテナー
	 * @param titleid タイトル
	 * @return ダイアログ
	 */
	protected MG0060EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0060EditDisplayDialog(parent, true, this, titleid);
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

	boolean checkDialog() {

		if (Util.isNullOrEmpty(dialog.ctrlDepartmentCode.getValue())) {
			if (Util.isNullOrEmpty(dialog.ctrlDepartmentCode.getValue())) {
				// 警告メッセージ表示
				showMessage(dialog, "I00002", "C00698");
				dialog.ctrlDepartmentCode.requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 部門コードチェック
		if (!Util.isNullOrEmpty(dialog.ctrlDepartmentCode.getValue())) {
			if (!isUpdate && checkCode(dialog.ctrlDepartmentCode.getValue())) {
				// 警告メッセージ表示
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlDepartmentCode.requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 部門名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlDepartmentName.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00723");
			dialog.ctrlDepartmentName.requestFocus();
			// エラーを返す
			return false;
		}

		// 部門略称未入力
		if (Util.isNullOrEmpty(dialog.ctrlDepartmentAbbreviatedName.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00724");
			dialog.ctrlDepartmentAbbreviatedName.requestFocus();
			// エラーを返す
			return false;
		}
		// 部門検索名称未入力
		if (Util.isNullOrEmpty(dialog.ctrlDepartmentNameForSearch.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00725");
			dialog.ctrlDepartmentNameForSearch.requestFocus();
			// エラーを返す
			return false;
		}

		// 人員数１未入力
		if (Util.isNullOrEmpty(dialog.numPersonNumber1.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00726");
			dialog.numPersonNumber1.requestFocus();
			// エラーを返す
			return false;
		}

		String strNum1 = dialog.numPersonNumber1.getValue();
		if (!Util.isNullOrEmpty(strNum1)) {
			BigDecimal num = new BigDecimal(strNum1);
			// 人員数１がが0～9,999,999以外
			if (!Util.isNullOrEmpty(strNum1)
				&& ((num.compareTo(new BigDecimal(0)) < 0 || num.compareTo(new BigDecimal("99999999")) > 0))) {
				// 警告メッセージ表示
				showMessage(dialog, "W00065", "0", "99,999,999");
				dialog.numPersonNumber1.requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 人員数２未入力
		if (Util.isNullOrEmpty(dialog.numPersonNumber2.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00727");
			dialog.numPersonNumber2.requestFocus();
			// エラーを返す
			return false;
		}

		String strNum2 = dialog.numPersonNumber2.getValue();
		if (!Util.isNullOrEmpty(strNum2)) {
			BigDecimal num = new BigDecimal(strNum2);
			// 人員数２がが0～9,999,999以外
			if (num.compareTo(new BigDecimal(0)) < 0 || num.compareTo(new BigDecimal("99999999")) > 0) {
				// 警告メッセージ表示
				showMessage(dialog, "W00065", "0", "99,999,999");
				dialog.numPersonNumber2.requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 床面積未入力
		if (Util.isNullOrEmpty(dialog.numFloorArea.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00728");
			dialog.numFloorArea.requestFocus();
			// エラーを返す
			return false;
		}

		String strArea = dialog.numFloorArea.getValue();
		if (!Util.isNullOrEmpty(strArea)) {
			BigDecimal num = new BigDecimal(strArea);
			// 床面積がが0～9,999,999.99以外
			if (num.compareTo(new BigDecimal(0)) < 0 || num.compareTo(new BigDecimal("99999999.99")) > 0) {
				// 警告メッセージ表示
				showMessage(dialog, "W00065", "0", "99,999,999.99");
				dialog.numFloorArea.requestFocus();
				// エラーを返す
				return false;
			}
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

		return true;

	}

	/**
	 * 表示
	 * 
	 * @param isEnabledDeptCode 部門コードエリア編集可(true)、付加(false)
	 */
	void show(boolean isEnabledDeptCode) {
		// 画面を表示する
		dialog.setVisible(true);
		// 部門コードの設定
		dialog.ctrlDepartmentCode.setEditable(isEnabledDeptCode);
	}

	/**
	 * 値のセット
	 * 
	 * @param map
	 */

	void setValues(Map map) {
		// 部門コードの設定
		dialog.ctrlDepartmentCode.setValue((String) map.get("depCode"));
		// 部門名称の設定
		dialog.ctrlDepartmentName.setValue((String) map.get("depName"));
		// 部門略称の設定
		dialog.ctrlDepartmentAbbreviatedName.setValue((String) map.get("depName_S"));
		// 部門検索名称の設定
		dialog.ctrlDepartmentNameForSearch.setValue((String) map.get("depName_K"));

		// 人員数１の設定
		dialog.numPersonNumber1.setValue(((String) map.get("men1")).replace(",", ""));
		// 人員数２の設定
		dialog.numPersonNumber2.setValue(((String) map.get("men2")).replace(",", ""));
		// 床面積の設定
		dialog.numFloorArea.setValue(((String) map.get("space")).replace(",", ""));
		// 入力の設定
		dialog.rdoInput.setSelected(true);
		// 部門区分の設定
		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("depKbn")));
		// 入力の設定
		dialog.rdoInput.setSelected(!boo);
		// 集計の設定
		dialog.rdoSummary.setSelected(boo);
		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));
		// 編集モードのときは部門コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		if (isUpdate) {
			dialog.ctrlDepartmentName.getField().requestFocus();
		}
		// 部門コードの設定
		dialog.ctrlDepartmentCode.setEditable(isUpdate == false);
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
		// 部門コードの設定
		map.put("depCode", dialog.ctrlDepartmentCode.getValue());
		// 部門名称の設定
		map.put("depName", dialog.ctrlDepartmentName.getValue());
		// 部門略称の設定
		map.put("depName_K", dialog.ctrlDepartmentNameForSearch.getValue());
		// 部門検索名称の設定
		map.put("depName_S", dialog.ctrlDepartmentAbbreviatedName.getValue());
		// 人員数１の設定
		map.put("men1", (dialog.numPersonNumber1.getValue()).replace(",", ""));
		// 人員数２の設定
		map.put("men2", (dialog.numPersonNumber2.getValue()).replace(",", ""));
		// 床面積の設定
		map.put("space", (dialog.numFloorArea.getValue()).replace(",", ""));
		// 部門区分の設定
		map.put("depKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoInput.isSelected() == false)));
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 会社コードの設定
		map.put("kaiCode", getLoginUserCompanyCode());
		// 結果を返す
		return map;
	}

	/**
	 * コードのチェック
	 * 
	 * @param code
	 * @return チェック結果
	 */
	boolean checkCode(String code) {
		try {// 部門コード未入力
			if (Util.isNullOrEmpty(code)) return false;
			// 処理種別の設定
			addSendValues("flag", "checkcode");
			// 会社コードの設定
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// 部門コードの設定
			addSendValues("depCode", code);

			// サーバ側のエラー場合
			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return true;
			}

			// 結果を取得
			List result = super.getResultList();
			// 結果を返す
			return (result.size() > 0);
		} catch (IOException ex) {
			// 正常に処理されませんでした
			super.errorHandler(dialog, ex, "E00009");
			return false;
		}
	}
}

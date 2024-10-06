package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * 銀行マスタ
 */
public class MG0140EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 銀行マスタダイアログ */
	protected MG0140EditDisplayDialog dialog;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0140BankMasterServlet";

	/** 処理区分 */
	private boolean isUpdate = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0140EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 銀行マスタダイアログの初期化
		dialog = new MG0140EditDisplayDialog(parent, true, this, titleid);
		// 画面の設定
		dialog.setSize(500, 400);
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});
		dialog.ctrlBankCode.requestTextFocus();

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	/**
	 * 表示
	 */
	void show() {
		// 画面を表示する
		dialog.setVisible(true);
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

		try {
			// 銀行コードチェック
			if (Util.isNullOrEmpty(dialog.ctrlBankCode.getValue())) {
				showMessage(dialog, "I00002", "C00779");
				dialog.ctrlBankCode.requestFocus();
				return false;
			}

			// 銀行支店コードチェック

			if (Util.isNullOrEmpty(dialog.ctrlBankBranchCode.getValue())) {
				showMessage(dialog, "I00002", "C00780");
				dialog.ctrlBankBranchCode.requestFocus();
				return false;
			}

			if (!Util.isNullOrEmpty(dialog.ctrlBankBranchCode.getValue()) && !isUpdate
				&& checkCode(dialog.ctrlBankCode.getValue(), dialog.ctrlBankBranchCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlBankBranchCode.requestFocus();
				return false;
			}

			// 銀行名チェック

			if (Util.isNullOrEmpty(dialog.ctrlBankName.getValue())) {
				showMessage(dialog, "I00002", "C00781");
				dialog.ctrlBankName.requestFocus();
				return false;
			}

			// 銀行カナ名称チェック

			if (Util.isNullOrEmpty(dialog.ctrlBankKanaName.getValue())) {
				showMessage(dialog, "I00002", "C00782");
				dialog.ctrlBankKanaName.requestFocus();
				return false;
			}

			// 銀行検索名称チェック

			if (Util.isNullOrEmpty(dialog.ctrlBankNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00829");
				dialog.ctrlBankNameForSearch.requestFocus();
				return false;
			}

			// 銀行支店名チェック

			if (Util.isNullOrEmpty(dialog.ctrlBankBranchName.getValue())) {
				showMessage(dialog, "I00002", "C00783");
				dialog.ctrlBankBranchName.requestFocus();
				return false;
			}

			// 銀行支店カナ名称チェック

			if (Util.isNullOrEmpty(dialog.ctrlBankBranchKanaName.getValue())) {
				showMessage(dialog, "I00002", "C00784");
				dialog.ctrlBankBranchKanaName.requestFocus();
				return false;
			}

			// 銀行支店検索名称チェック

			if (Util.isNullOrEmpty(dialog.ctrlBankBranchNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00785");
				dialog.ctrlBankBranchNameForSearch.requestFocus();
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
			errorHandler(dialog, e, "E00009");
			return false;
		}
	}

	/**
	 * 値のセット
	 * 
	 * @param map セットする値
	 */
	void setValues(Map map) {
		// 銀行コードの設定
		dialog.ctrlBankCode.setValue((String) map.get("bnkCode"));
		// 銀行支店コードの設定
		dialog.ctrlBankBranchCode.setValue((String) map.get("bnkStnCode"));
		// 銀行名の設定
		dialog.ctrlBankName.setValue((String) map.get("bnkName_S"));
		// 銀行カナ名称の設定
		dialog.ctrlBankKanaName.setValue((String) map.get("bnkKana"));
		// 銀行検索名称の設定
		dialog.ctrlBankNameForSearch.setValue((String) map.get("bnkName_K"));
		// 銀行支店名の設定
		dialog.ctrlBankBranchName.setValue((String) map.get("bnkStnName_S"));
		// 銀行支店カナ名称の設定
		dialog.ctrlBankBranchKanaName.setValue((String) map.get("bnkStnKana"));
		// 銀行支店検索名称の設定
		dialog.ctrlBankBranchNameForSearch.setValue((String) map.get("bnkStnName_K"));
		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは銀行コード、銀行支店コードとが編集不可になる
		isUpdate = "update".equals(map.get("flag"));

		if (isUpdate) {
			dialog.ctrlBankName.requestFocus();
		}
		// 銀行コードの設定
		dialog.ctrlBankCode.setEditable(!isUpdate);
		// 銀行支店コードの設定
		dialog.ctrlBankBranchCode.setEditable(!isUpdate);

	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// 銀行コードの設定
		map.put("bnkCode", dialog.ctrlBankCode.getValue());
		// 銀行支店コードの設定
		map.put("bnkStnCode", dialog.ctrlBankBranchCode.getValue());
		// 銀行名の設定
		map.put("bnkName_S", dialog.ctrlBankName.getValue());
		// 銀行カナ名称の設定
		map.put("bnkKana", dialog.ctrlBankKanaName.getValue());
		// 銀行検索名称の設定
		map.put("bnkName_K", dialog.ctrlBankNameForSearch.getValue());
		// 銀行支店名の設定
		map.put("bnkStnName_S", dialog.ctrlBankBranchName.getValue());
		// 銀行支店カナ名称の設定
		map.put("bnkStnKana", dialog.ctrlBankBranchKanaName.getValue());
		// 銀行支店検索名称の設定
		map.put("bnkStnName_K", dialog.ctrlBankBranchNameForSearch.getValue());
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	boolean checkCode(String bnkCode, String bnkStnCode) throws IOException {
		// 銀行コード未入力
		if (Util.isNullOrEmpty(bnkCode)) {
			return false;
		}
		if (Util.isNullOrEmpty(bnkStnCode)) {
			return false;
		}
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 銀行コードの設定
		addSendValues("bnkCode", bnkCode);
		// 銀行支店コードの設定
		addSendValues("bnkStnCode", bnkStnCode);

		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return true;
		}
		// 結果を取得
		List result = super.getResultList();
		// 結果を返す
		return (result.size() > 0);
	}
}

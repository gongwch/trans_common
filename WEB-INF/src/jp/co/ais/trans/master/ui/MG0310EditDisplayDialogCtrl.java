package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 通貨レートマスタ ダイアログコントロール
 */
public class MG0310EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** レートマスタダイアログ */
	protected MG0310EditDisplayDialog dialog;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0310RateMasterServlet";

	/** 処理区分 */
	private boolean isUpdate;

	protected REFDialogCtrl ref1;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0310EditDisplayDialogCtrl(Frame parent, String titleid) {
		// レートマスタダイアログの初期化
		dialog = new MG0310EditDisplayDialog(parent, true, this, titleid);
		// フォーカスの設定
		dialog.ctrlCurrency.getField().requestFocus();

		ref1 = new REFDialogCtrl(dialog.ctrlCurrency, dialog.getParentFrame());
		ref1.setColumnLabels("C00665", "C00881", "C00882");
		ref1.setTargetServlet("MG0300CurrencyMasterServlet");
		ref1.setTitleID(getWord("C01985"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				// mapの初期化
				Map keys = new HashMap();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Currency");
				// データを返す
				return keys;
			}
		});

		dialog.ctrlCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlCurrency.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlCurrency.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlCurrency.getValue());
					dialog.ctrlCurrency.getField().clearOldText();
					dialog.ctrlCurrency.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		// fix bug: 確定ボタン押下して UI チェックが失敗の場合は、
		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		try {
			dialog.dtOutLineBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		} catch (Exception e) {
			errorHandler(dialog, e, "E00009");
		}
	}

	/**
	 * 表示
	 * 
	 * @param isEnabledCurCode 通貨コードエリア編集可(true)、付加(false)
	 */
	void show(boolean isEnabledCurCode) {
		// 画面を表示する
		dialog.setVisible(true);
		// 通貨コードの設定
		dialog.ctrlCurrency.setEnabled(isEnabledCurCode);
	}

	/**
	 * 値のセット
	 * 
	 * @param map
	 */
	void setValues(Map map) {
		// 通貨コードの設定
		dialog.ctrlCurrency.setValue((String) map.get("curCode"));
		// 摘要開始日付の設定
		dialog.dtOutLineBeginDate.setValue((Date) map.get("strDate"));
		// レートの設定
		dialog.numRate.setValue(((String) map.get("curRate")).replace(",", ""));
		// 編集モードのときは通貨コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));

		// 通貨ボタンの設定
		dialog.ctrlCurrency.getButton().setEnabled(!isUpdate);
		// 通貨コードの設定
		dialog.ctrlCurrency.getField().setEditable(!isUpdate);
		dialog.dtOutLineBeginDate.getCalendar().setEditable(true);
		ref1.refreshName();

		// 処理種別のチェック
		if (!isUpdate) return;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 通貨コードの設定
		addSendValues("beginCurCode", (String) map.get("curCode"));

		if (isUpdate) {
			dialog.dtOutLineBeginDate.setEditable(false);
			dialog.dtOutLineBeginDate.setEnabled(true);
			dialog.numRate.getField().requestFocus();
		}
	}

	boolean checkDialog() {
		// 通貨コード未入力チェック
		if (Util.isNullOrEmpty(dialog.ctrlCurrency.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C00665");
			dialog.ctrlCurrency.requestTextFocus();
			// エラーを返す
			return false;
		}

		// 摘要開始日付未入力チェック
		if (dialog.dtOutLineBeginDate.getValue() == null) {
			showMessage(dialog, "I00002", "C01247");
			dialog.dtOutLineBeginDate.getCalendar().requestFocus();
			// エラーを返す
			return false;
		}

		// レート未入力チェック
		if (Util.isNullOrEmpty(dialog.numRate.getValue())) {
			// 警告メッセージ表示
			showMessage(dialog, "I00002", "C01555");
			dialog.numRate.requestFocus();
			// エラーを返す
			return false;
		}

		// レートがが0.0001～99,999,999,999.9999以外チェック
		if (!Util.isNullOrEmpty(dialog.numRate.getValue())) {
			BigDecimal num = new BigDecimal(dialog.numRate.getValue());
			if (num.compareTo(new BigDecimal("0.0001")) < 0 || num.compareTo(new BigDecimal("99999999999.9999")) > 0) {
				// 警告メッセージ表示
				showMessage(dialog, "W00065", "0.0001", "99,999,999,999.9999");
				dialog.numRate.requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 摘要開始日付が存在していますチェック
		if (!isUpdate && checkCode()) {
			// 警告メッセージ表示
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlCurrency.getField().requestFocus();
			// エラーを返す
			return false;
		}
		// 成功を返す
		return true;
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {
		// エラーがある場合にはダイアログを閉じない
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
	 * コードのチェック
	 * 
	 * @return チェック結果
	 */
	boolean checkCode() {
		try { // 処理種別の設定
			addSendValues("flag", "checkcode");
			// 会社コードの設定
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// 通貨コードの設定
			addSendValues("curCode", dialog.ctrlCurrency.getValue());
			// 摘要開始日付の設定
			addSendValues("strDate", DateUtil.toYMDHMSString(dialog.dtOutLineBeginDate.getValue()));

			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return false;
			}

			// 結果を取得
			List result = super.getResultList();
			// 結果を返す
			return (result.size() > 0);
		} catch (IOException ex) {
			// 正常に処理されませんでした
			super.errorHandler(dialog, ex);
			return false;
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
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// 通貨コードの設定
		map.put("curCode", dialog.ctrlCurrency.getValue());
		// 摘要開始日付の設定
		map.put("strDate", dialog.dtOutLineBeginDate.getValue());
		// レートの設定
		map.put("curRate", dialog.numRate.getValue());
		// 会社コードの設定
		map.put("kaiCode", getLoginUserCompanyCode());
		// 結果を返す
		return map;
	}
}

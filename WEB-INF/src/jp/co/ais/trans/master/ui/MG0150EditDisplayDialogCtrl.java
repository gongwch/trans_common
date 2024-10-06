package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 取引先マスタダイアログ コントロール
 * 
 * @author mayongliang
 */
public class MG0150EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 取引先マスタダイアログ */
	protected MG0150EditDisplayDialog dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0150CustomerMasterServlet";
	}

	/** 処理区分 */
	protected boolean isUpdate = false;

	protected boolean isSpot = false;

	/** 取引コード */
	public String triCode = null;

	protected REFDialogCtrl refSummaryCode;

	private REFDialogCtrl refInputBankAccountCode;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0150EditDisplayDialogCtrl(Frame parent, String titleid) {

		// 取引先マスタダイアログの初期化
		dialog = new MG0150EditDisplayDialog(parent, true, this, titleid);

		dialog.rdoMyCompanyPay.setSelected(true);
		dialog.rdoNotVendor.setSelected(true);
		dialog.rdoNotCustomer.setSelected(true);
		dialog.rdoNormally.setSelected(true);

		init();

		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});

		dialog.ctrlCustomerCode.getField().requestFocus();
		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

		changedCustomerSelect(false);
	}

	protected void init() {

		refSummaryCode = new REFDialogCtrl(dialog.ctrlSummaryCode, dialog.getParentFrame());
		refSummaryCode.setTargetServlet(getServletName());
		refSummaryCode.setTitleID("C02326");
		refSummaryCode.setColumnLabels("C00786", "C00787", "C00836");
		refSummaryCode.setShowsMsgOnError(false);
		refSummaryCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Customer");
				return keys;
			}

		});

		dialog.ctrlSummaryCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refSummaryCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlSummaryCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlSummaryCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlSummaryCode.getValue());
					dialog.ctrlSummaryCode.getField().clearOldText();
					dialog.ctrlSummaryCode.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// 入金銀行口座ｺｰﾄﾞの設定
		refInputBankAccountCode = new REFDialogCtrl(dialog.ctrlInputBankAccountCode, dialog.getParentFrame());
		refInputBankAccountCode.setColumnLabels("C00857", "C00124", "C00794");
		refInputBankAccountCode.setTargetServlet("MP0030BankAccountMasterServlet");
		refInputBankAccountCode.setTitleID("C02322");
		refInputBankAccountCode.setShowsMsgOnError(false);

		dialog.ctrlInputBankAccountCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refInputBankAccountCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlInputBankAccountCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlInputBankAccountCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlInputBankAccountCode.getValue());
					dialog.ctrlInputBankAccountCode.getField().clearOldText();
					dialog.ctrlInputBankAccountCode.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		refInputBankAccountCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "BankAccount");

				return keys;
			}

			// 銀行コードにて銀行マスタに存在
			public void goodCodeInputted() {
				try {
					addSendValues("flag", "getrefitems");
					addSendValues("kaiCode", getLoginUserCompanyCode());
					addSendValues("cbkCbkCode", dialog.ctrlInputBankAccountCode.getField().getText());

					if (!request("MP0030BankAccountMasterServlet")) {
						errorHandler(dialog);
					}

					List result = getResultList().get(0);
					if (result != null) {
						String text1 = (String) result.get(1) + " " + (String) result.get(3);

						String rsText = (String) result.get(4);
						if ("1".equals(rsText)) {
							rsText = getWord("C00463");
						} else if ("2".equals(rsText)) {
							rsText = getWord("C00397");
						} else if ("3".equals(rsText)) {
							rsText = getWord("C00045");
						} else {
							rsText = getWord("C00368");
						}
						String text2 = rsText + " " + (String) result.get(5);

						dialog.ctrlInputBankAccountCode.getNotice().setEditable(true);
						dialog.ctrlInputBankAccountCode.getNotice().setText(text1);
						dialog.ctrlInputBankAccountCode.getNotice().setEditable(false);

						dialog.txtDepositTypeAccountNumber.setText(text2);
					}
				} catch (IOException ex) {
					errorHandler(dialog, ex, "E00009");
				}
			}

			// 銀行コードにて銀行マスタに存在しない
			public void badCodeInputted() {
				dialog.ctrlInputBankAccountCode.getNotice().setEditable(true);
				dialog.ctrlInputBankAccountCode.getNotice().setText("");
				dialog.ctrlInputBankAccountCode.getNotice().setEditable(false);

				dialog.txtDepositTypeAccountNumber.setEditable(true);
				dialog.txtDepositTypeAccountNumber.setText("");
				dialog.txtDepositTypeAccountNumber.setEditable(false);
			}

			// 銀行コードをクリア
			public void textCleared() {
				badCodeInputted();
			}
		});

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
		boolean boo;
		// 取引先コードの設定
		dialog.ctrlCustomerCode.setValue((String) map.get("triCode"));
		// 取引先名称の設定
		dialog.ctrlCustomerName.setValue((String) map.get("triName"));
		// 取引先略称の設定
		dialog.ctrlCustomerAbbreviationName.setValue((String) map.get("triName_S"));
		// 取引先検索名称の設定
		dialog.ctrlCustomerNameForSearch.setValue((String) map.get("triName_K"));
		// 郵便番号の設定
		dialog.ctrlPostcode.setValue((String) map.get("zip"));
		// 住所カナの設定
		dialog.ctrlAddressKana.setValue((String) map.get("jyuKana"));
		// 住所１の設定
		dialog.ctrlAddress1.setValue((String) map.get("jyu1"));
		// 住所２の設定
		dialog.ctrlAddress2.setValue((String) map.get("jyu2"));
		// 電話番号の設定
		dialog.ctrlTelephoneNumber.setValue((String) map.get("tel"));
		// FAX番号の設定
		dialog.ctrlFaxNumber.setValue((String) map.get("fax"));
		// 集計相手先コードの設定
		dialog.ctrlSummaryCode.setValue((String) map.get("sumCode"));
		// 仕入先区分の設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("siireKbn")));
		dialog.rdoNotVendor.setSelected(!boo);
		dialog.rdoVendor.setSelected(boo);
		// 得意先区分の設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("tokuiKbn")));
		dialog.rdoNotCustomer.setSelected(!boo);
		dialog.rdoCustomer.setSelected(boo);
		changedCustomerSelect(boo);

		// 入金条件締め日の設定
		dialog.numCashInConditionCutoffDay.setValue(map.get("njCDate"));
		// 入金条件締め後月の設定
		dialog.numCashInConditionAfterCutoffMonth.setValue(map.get("njRMon"));
		// 入金条件入金日の設定
		dialog.numCashInConditionCashInDay.setValue(map.get("njPDate"));
		// 入金銀行口座ｺｰﾄﾞの設定
		dialog.ctrlInputBankAccountCode.setValue((String) map.get("nknCbkCode"));
		// 取引形態区分の設定
		dialog.ctrlOfficeName.setValue((String) map.get("jigName"));
		// 振込依頼人名の設定
		dialog.ctrlTransferRequesterKanaName.setValue((String) map.get("iraiName"));
		// 入金手数料区分の設定
		String tesKbn = (String) map.get("nyuTesuKbn");

		if ("1".equals(tesKbn)) {
			dialog.rdoMyCompanyPay.setSelected(true);
		} else {
			dialog.rdoCustomerPay.setSelected(true);
		}

		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));
		// 編集モードのときは摘要コード、摘要区分とが編集不可になる
		isUpdate = "update".equals(map.get("flag"));

		// SPOT本登録
		isSpot = "spot".equals(map.get("flag"));
		dialog.ctrlCustomerCode.setEnabled(true);
		dialog.ctrlCustomerCode.setEditable(!isUpdate);

		// 取引先コード
		if (isUpdate) {
			// requestFocus
			dialog.ctrlCustomerName.getField().requestFocus();
			if ("00".equals(map.get("triKbn"))) {
				dialog.rdoNormally.setSelected(true);
				dialog.rdoSpot.setSelected(false);
				dialog.rdoSpot.setEnabled(false);
			} else {
				dialog.rdoNormally.setSelected(false);
				dialog.rdoNormally.setEnabled(false);
				dialog.rdoSpot.setSelected(true);
				dialog.rdoSpot.setEnabled(true);
			}
		}

		refSummaryCode.refreshName();
		refInputBankAccountCode.refreshName();
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {
		try {

			// 戻るボタン押下
			if (!dialog.isSettle) {
				// 画面を閉める
				dialog.setVisible(false);
				return;
			}

			// 確定ボタン押下 チェックOKなら閉じる
			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} catch (IOException ex) {
			errorHandler(dialog, ex, "E00009");
		}
	}

	protected boolean checkDialog() throws IOException {

		// 取引先コード
		if (dialog.ctrlCustomerCode.isEmpty()) {
			showMessage(dialog, "I00002", "C00786");
			dialog.ctrlCustomerCode.requestFocus();
			return false;
		}

		if (!isSpot && !isUpdate && checkCode(dialog.ctrlCustomerCode.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlCustomerCode.requestFocus();
			return false;
		}

		// 取引先名称
		if (dialog.ctrlCustomerName.isEmpty()) {
			showMessage(dialog, "I00002", "C00830");
			dialog.ctrlCustomerName.requestFocus();
			return false;
		}

		// 取引先略称チェック
		if (dialog.ctrlCustomerAbbreviationName.isEmpty()) {
			showMessage(dialog, "I00002", "C00787");
			dialog.ctrlCustomerAbbreviationName.requestFocus();
			return false;
		}

		// 取引先検索名称チェック
		if (dialog.ctrlCustomerNameForSearch.isEmpty()) {
			showMessage(dialog, "I00002", "C00836");
			dialog.ctrlCustomerNameForSearch.requestFocus();
			return false;
		}

		// 得意先が選択されている場合のみチェック
		if (dialog.rdoCustomer.isSelected()) {

			// 入金銀行口座ｺｰﾄﾞチェック
			if (dialog.ctrlInputBankAccountCode.isEmpty()) {
				showMessage(dialog, "I00002", "C00647");
				dialog.ctrlInputBankAccountCode.requestTextFocus();
				return false;
			}

			// 入金条件締め日チェック
			if (dialog.numCashInConditionCutoffDay.isEmpty()) {
				showMessage(dialog, "I00002", "C00868");
				dialog.numCashInConditionCutoffDay.requestFocus();
				return false;
			}

			int cutoffDay = dialog.numCashInConditionCutoffDay.getInt();

			// 入金条件 締め日 0～31,99
			if (cutoffDay != 99 && (cutoffDay < 1 || 31 < cutoffDay)) {
				showMessage(dialog, "W00065", "1", "31, 99");
				dialog.numCashInConditionCutoffDay.requestFocus();
				return false;
			}

			// 入金条件 締め後月チェック
			if (dialog.numCashInConditionAfterCutoffMonth.isEmpty()) {
				showMessage(dialog, "I00002", "C00869");
				dialog.numCashInConditionAfterCutoffMonth.requestFocus();
				return false;
			}

			int cutoffMonth = dialog.numCashInConditionAfterCutoffMonth.getInt();

			// 入金条件 締め後月 0～99
			if (cutoffMonth < 0 || 99 < cutoffMonth) {
				showMessage(dialog, "W00065", "0", "99");
				dialog.numCashInConditionAfterCutoffMonth.requestFocus();
				return false;
			}

			// 入金条件 入金日チェック
			if (dialog.numCashInConditionCashInDay.isEmpty()) {
				showMessage(dialog, "I00002", "C00870");
				dialog.numCashInConditionCashInDay.requestFocus();
				return false;
			}

			int cashInDay = dialog.numCashInConditionCashInDay.getInt();

			// 入金条件 入金日 0～31,99
			if (cashInDay != 99 && (cashInDay < 1 || 31 < cashInDay)) {
				showMessage(dialog, "W00065", "1", "31, 99");
				dialog.numCashInConditionCashInDay.requestFocus();
				return false;
			}
		}

		// 開始年月日
		if (dialog.dtBeginDate.isEmpty()) {
			showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.getCalendar().requestFocus();
			return false;
		}
		// 終了年月日
		if (dialog.dtEndDate.isEmpty()) {
			showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.getCalendar().requestFocus();
			return false;
		}

		// 開始年月日＜＝終了年月日
		if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
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
		return dialog.isSettle;
	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */

	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// 取引先コードの設定
		map.put("triCode", dialog.ctrlCustomerCode.getValue());
		// 過去の取引先コードの設定
		map.put("oldTriCode", this.triCode);
		// 取引先名称の設定
		map.put("triName", dialog.ctrlCustomerName.getValue());
		// 取引先略称の設定
		map.put("triName_S", dialog.ctrlCustomerAbbreviationName.getValue());
		// 取引先検索名称の設定
		map.put("triName_K", dialog.ctrlCustomerNameForSearch.getValue());
		// 郵便番号の設定
		map.put("zip", dialog.ctrlPostcode.getValue());
		// 住所カナの設定
		map.put("jyuKana", dialog.ctrlAddressKana.getValue());
		// 住所カナの設定
		map.put("jyu1", dialog.ctrlAddress1.getValue());
		// 住所２の設定
		map.put("jyu2", dialog.ctrlAddress2.getValue());
		// 電話番号の設定
		map.put("tel", dialog.ctrlTelephoneNumber.getValue());
		// FAX番号の設定
		map.put("fax", dialog.ctrlFaxNumber.getValue());
		// 集計相手先コードの設定
		map.put("sumCode", dialog.ctrlSummaryCode.getValue());
		// 仕入先区分の設定
		map.put("siireKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoVendor.isSelected())));
		// 得意先区分の設定
		map.put("tokuiKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoCustomer.isSelected())));
		// 入金条件締め日の設定
		map.put("njCDate", dialog.numCashInConditionCutoffDay.getValue());
		// 入金条件締め後月の設定
		map.put("njRMon", dialog.numCashInConditionAfterCutoffMonth.getValue());
		// 入金条件入金日の設定
		map.put("njPDate", dialog.numCashInConditionCashInDay.getValue());
		// 入金銀行口座ｺｰﾄﾞの設定
		map.put("nknCbkCode", dialog.ctrlInputBankAccountCode.getValue());
		// 事業所名称の設定
		map.put("jigName", dialog.ctrlOfficeName.getValue());
		// 振込依頼人名の設定
		map.put("iraiName", dialog.ctrlTransferRequesterKanaName.getValue());
		// 入金手数料区分の設定
		String tesKbn;
		if (dialog.rdoMyCompanyPay.isSelected()) {
			tesKbn = "1";
		} else {
			tesKbn = "2";
		}
		map.put("nyuTesuKbn", tesKbn);
		// 取引形態区分の設定
		boolean boo = dialog.rdoNormally.isSelected();
		if (boo) {
			map.put("triKbn", "00");
		} else {
			map.put("triKbn", "01");
		}
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	/**
	 * 取引先コード設定
	 * 
	 * @param triCode 取引先コード
	 */
	public void setTriCode(String triCode) {
		this.triCode = triCode;
	}

	/**
	 * 取引先コードチェック
	 * 
	 * @param triCode_ 取引先コード
	 * @return true:OK
	 * @throws IOException
	 */
	protected boolean checkCode(String triCode_) throws IOException {

		// 取引先コード未入力
		if (Util.isNullOrEmpty(triCode_)) {
			return false;
		}

		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 取引先コードの設定
		addSendValues("triCode", triCode_);

		// 送信
		if (!request(getServletName())) {
			errorHandler(dialog);
			return true;
		}

		// 結果を取得
		List result = super.getResultList();

		// 結果を返す
		return !result.isEmpty();
	}

	/**
	 * 得意先チェックが変更された際の処理
	 * 
	 * @param isSelect true:選択 false:非選択
	 */
	protected void changedCustomerSelect(boolean isSelect) {
		// コンポーネント制御
		dialog.ctrlInputBankAccountCode.setEnabled(isSelect);
		dialog.ctrlTransferRequesterKanaName.setEnabled(isSelect);
		dialog.numCashInConditionCutoffDay.setEnabled(isSelect);
		dialog.numCashInConditionAfterCutoffMonth.setEnabled(isSelect);
		dialog.numCashInConditionCashInDay.setEnabled(isSelect);
		dialog.rdoCustomerPay.setEnabled(isSelect);
		dialog.rdoMyCompanyPay.setEnabled(isSelect);

		if (!isSelect) {
			dialog.ctrlInputBankAccountCode.clear();
			dialog.txtDepositTypeAccountNumber.setValue("");
			dialog.ctrlTransferRequesterKanaName.setValue("");

			dialog.numCashInConditionCutoffDay.setNumber(0);
			dialog.numCashInConditionAfterCutoffMonth.setNumber(0);
			dialog.numCashInConditionCashInDay.setNumber(0);

			dialog.rdoMyCompanyPay.setSelected(true);
		}
	}
}
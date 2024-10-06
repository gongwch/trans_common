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
 * 取引先条件マスタ
 * 
 * @author mayongliang
 */
public class MG0155EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 取引先条件マスタダイアログ */
	protected MG0155EditDisplayDialog dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0155CustomerConditionMasterServlet";
	}

	/** 処理区分 */
	protected boolean isUpdate = false;

	protected REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	private REFDialogCtrl ref3;

	private REFDialogCtrl ref4;

	private REFDialogCtrl ref5;

	protected REFDialogCtrl ref6;

	/**
	 * コンストラクタ
	 */
	protected MG0155EditDisplayDialogCtrl() {
		// 処理なし
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0155EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 取引先条件マスタダイアログの初期化
		dialog = new MG0155EditDisplayDialog(parent, true, this, titleid);
		dialog.ctrlCustomerAbbreviationName.setEditable(false);
		dialog.ctrlCustomerAbbreviationName.setEnabled(false);
		dialog.rdoNormally.setSelected(true);
		dialog.rdoMyCompanyPay.setSelected(true);
		dialog.rdoRemittanceRecipient.setSelected(true);

		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});
		dialog.ctrlBranch.getField().setEditable(false);
		dialog.ctrlBranch.getButton().setEnabled(false);

		init();

		dialog.ctrlCustomer.getField().requestFocus();
		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	void init() {
		// 取引先
		ref1 = new REFDialogCtrl(dialog.ctrlCustomer, dialog.getParentFrame());
		ref1.setTargetServlet("MG0150CustomerMasterServlet");
		ref1.setTitleID("C02326");
		ref1.setColumnLabels("C00786", "C00787", "C00836");
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "Customer");
				keys.put("kaiCode", getLoginUserCompanyCode());
				return keys;
			}

			public void goodCodeInputted() {
				dialog.ctrlCustomerAbbreviationName.getField().setText(dialog.ctrlCustomer.getNotice().getText());
				dialog.ctrlCustomerAbbreviationName.getField().setEnabled(true);
			}

			public void badCodeInputted() {
				dialog.ctrlCustomerAbbreviationName.getField().setText("");
			}

		});

		dialog.ctrlCustomer.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();

				if (!Util.isNullOrEmpty(dialog.ctrlCustomer.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlCustomer.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlCustomer.getValue());
					dialog.ctrlCustomer.getField().clearOldText();
					dialog.ctrlCustomer.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// 支払方法

		ref2 = new REFDialogCtrl(dialog.ctrlPaymentMethod, dialog.getParentFrame());
		ref2.setTargetServlet("MP0040PaymentMethodMasterServlet");
		ref2.setTitleID("C02350");
		ref2.setColumnLabels("C00864", "C00865", "C00866");
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 銀行コードの設定
				keys.put("kind", "SelectPaymentMethod");
				keys.put("kaiCode", getLoginUserCompanyCode());
				// データを返す
				return keys;
			}
		});

		dialog.ctrlPaymentMethod.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlPaymentMethod.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlPaymentMethod.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlPaymentMethod.getValue());
					dialog.ctrlPaymentMethod.getField().clearOldText();
					dialog.ctrlPaymentMethod.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// 銀行
		ref3 = new REFDialogCtrl(dialog.ctrlBank, dialog.getParentFrame());
		ref3.setTargetServlet("MG0140BankMasterServlet");
		ref3.setTitleID("C02323");
		ref3.setColumnLabels("C00779", "C00781", "C00829");
		ref3.setShowsMsgOnError(false);
		ref3.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "Bank");
				return keys;
			}

			// 支店コード存在する
			public void goodCodeInputted() {
				dialog.ctrlBranch.getField().setEditableEnabled(true);
				dialog.ctrlBranch.getButton().setEnabled(true);
			}

			// 支店コード存在しない
			public void badCodeInputted() {
				dialog.ctrlBranch.getField().setText(null);
				dialog.ctrlBranch.getField().setEditableEnabled(false);
				dialog.ctrlBranch.getNotice().setEditable(true);
				dialog.ctrlBranch.getNotice().setText(null);
				dialog.ctrlBranch.getNotice().setEditable(false);
				dialog.ctrlBranch.getButton().setEnabled(false);
			}

			public void codeChanged() {
				dialog.ctrlBranch.getField().setText(null);
				dialog.ctrlBranch.getNotice().setText(null);
			}

		});

		dialog.ctrlBank.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref3.refreshName();
				if (dialog.ctrlBank.isValueChanged()) {
					if (!Util.isNullOrEmpty(dialog.ctrlBank.getValue().trim())
						&& !Util.isNullOrEmpty(dialog.ctrlBank.getNotice().getText().trim())) {
						dialog.ctrlBranch.getField().setText(null);
						dialog.ctrlBranch.getNotice().setText(null);

					}
				}

				if (!Util.isNullOrEmpty(dialog.ctrlBank.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlBank.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlBank.getValue());
					dialog.ctrlBank.getField().clearOldText();
					dialog.ctrlBank.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// 支店

		ref4 = new REFDialogCtrl(dialog.ctrlBranch, dialog.getParentFrame());
		ref4.setTargetServlet("MG0140BankMasterServlet");
		ref4.setTitleID("C00778");
		ref4.setColumnLabels("C00780", "C00783", "C00785");
		ref4.setShowsMsgOnError(false);
		ref4.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 銀行コードの設定
				keys.put("kind", "BankBranch");
				keys.put("bnkCode", dialog.ctrlBank.getField().getText());
				// データを返す
				return keys;
			}
		});

		dialog.ctrlBranch.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref4.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlBranch.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlBranch.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlBranch.getValue());
					dialog.ctrlBranch.getField().clearOldText();
					dialog.ctrlBranch.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// 送金目的
		ref5 = new REFDialogCtrl(dialog.ctrlRemittancePurpose, dialog.getParentFrame());
		ref5.setTargetServlet("ApMktMstServlet");
		ref5.setTitleID("C00947");
		ref5.setColumnLabels("C00793", "C02037", null);
		ref5.setShowsMsgOnError(false);
		ref5.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 銀行コードの設定
				keys.put("kind", "ApMktMst");
				// データを返す
				return keys;
			}
		});

		dialog.ctrlRemittancePurpose.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref5.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlRemittancePurpose.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlRemittancePurpose.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlRemittancePurpose.getValue());
					dialog.ctrlRemittancePurpose.getField().clearOldText();
					dialog.ctrlRemittancePurpose.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// 振込振出銀行
		ref6 = new REFDialogCtrl(dialog.ctrlTransferDrawingBank, dialog.getParentFrame());
		ref6.setColumnLabels("C00857", "C00124", "C00794");
		ref6.setTargetServlet("MP0030BankAccountMasterServlet");
		ref6.setTitleID("C02322");
		ref6.setShowsMsgOnError(false);

		dialog.ctrlTransferDrawingBank.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref6.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlTransferDrawingBank.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferDrawingBank.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferDrawingBank.getValue());
					dialog.ctrlTransferDrawingBank.getField().clearOldText();
					dialog.ctrlTransferDrawingBank.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		ref6.setREFListener(new REFAdapter() {

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
					addSendValues("cbkCbkCode", dialog.ctrlTransferDrawingBank.getField().getText());

					// サーバ側のエラー場合

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

						dialog.ctrlTransferDrawingBank.getNotice().setEditable(true);
						dialog.ctrlTransferDrawingBank.getNotice().setText(text1);
						dialog.ctrlTransferDrawingBank.getNotice().setEditable(false);

						dialog.txtDepositTypeAccountNumber.setEditable(true);
						dialog.txtDepositTypeAccountNumber.setText(text2);
						dialog.txtDepositTypeAccountNumber.setEditable(false);
					}
				} catch (IOException e) {
					errorHandler(dialog, e, "E00009");
				}
			}

			// 銀行コードにて銀行マスタに存在しない
			public void badCodeInputted() {
				dialog.ctrlTransferDrawingBank.getNotice().setEditable(true);
				dialog.ctrlTransferDrawingBank.getNotice().setText("");
				dialog.ctrlTransferDrawingBank.getNotice().setEditable(false);

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
		// 画面を表示する
		dialog.setVisible(true);
	}

	/**
	 * 値のセット
	 * 
	 * @param map セットする値
	 */

	void setValues(Map map) {
		String selectValue;

		// 取引先コードの設定
		dialog.ctrlCustomer.setValue((String) map.get("triCode"));
		// 取引先コードの設定
		dialog.ctrlCustomerConditionCode.setValue((String) map.get("tjkCode"));
		// 振込手数料区分の設定
		selectValue = (String) map.get("furiTesuKen");
		if ("1".equals(selectValue)) {
			dialog.rdoMyCompanyPay.setSelected(true);
			dialog.rdoCustomerPay.setSelected(false);
		} else {
			dialog.rdoMyCompanyPay.setSelected(false);
			dialog.rdoCustomerPay.setSelected(true);
		}
		// 支払条件締め後月の設定
		dialog.numPaymentTermsCutoffDate.setValue(map.get("sjcDate"));
		// 支払条件締め日の設定
		dialog.numPaymentTermsAfterCutoffMonth.setValue(map.get("sjrMon"));
		// 支払条件支払日の設定
		dialog.numPaymentTermsCashInDate.setValue(map.get("sjpDate"));
		// 支払方法の設定
		selectValue = (String) map.get("sihaKbn");
		if ("00".equals(selectValue)) {
			dialog.rdoTemporary.setSelected(true);
			dialog.rdoOntime.setSelected(false);
		} else {
			dialog.rdoTemporary.setSelected(false);
			dialog.rdoOntime.setSelected(true);
		}
		// 支払方法の設定
		dialog.ctrlPaymentMethod.setValue((String) map.get("sihaHouCode"));
		// 振込振出銀行口座ｺｰﾄﾞの設定
		dialog.ctrlTransferDrawingBank.setValue((String) map.get("furiCbkCode"));
		// 銀行コードの設定
		dialog.ctrlBank.setValue((String) map.get("bnkCode"));
		// 支店コードの設定
		dialog.ctrlBranch.getField().setEditable(true);
		dialog.ctrlBranch.getButton().setEnabled(true);
		dialog.ctrlBranch.setValue((String) map.get("bnkStnCode"));
		// 預金種目の設定
		selectValue = (String) map.get("yknKbn");
		if ("1".equals(selectValue)) {
			dialog.rdoNormally.setSelected(true);
			dialog.rdoInterim.setSelected(false);
			dialog.rdoForeignCurrency.setSelected(false);
			dialog.rdoSavings.setSelected(false);
		} else if ("2".equals(selectValue)) {
			dialog.rdoNormally.setSelected(false);
			dialog.rdoInterim.setSelected(true);
			dialog.rdoForeignCurrency.setSelected(false);
			dialog.rdoSavings.setSelected(false);
		} else if ("3".equals(selectValue)) {
			dialog.rdoNormally.setSelected(false);
			dialog.rdoInterim.setSelected(false);
			dialog.rdoForeignCurrency.setSelected(true);
			dialog.rdoSavings.setSelected(false);
		} else {
			dialog.rdoNormally.setSelected(false);
			dialog.rdoInterim.setSelected(false);
			dialog.rdoForeignCurrency.setSelected(false);
			dialog.rdoSavings.setSelected(true);
		}
		// 口座番号の設定
		dialog.ctrlAccountNumber.setValue((String) map.get("yknNo"));
		// 口座名義の設定
		dialog.ctrlEnglishBankAddress.setValue((String) map.get("yknName"));
		// 口座名義カナの設定
		dialog.ctrlAccountName.setValue((String) map.get("yknKana"));
		// 送金目的名の設定
		dialog.ctrlRemittancePurpose.setValue((String) map.get("gsMktCode"));
		// 被仕向支店名称の設定
		dialog.ctrlEnglishBankName.setValue((String) map.get("gsBnkName"));
		// 被仕向銀行名称の設定
		dialog.ctrlEnglishBranchName.setValue((String) map.get("gsStnName"));
		// 手数料区分の設定
		selectValue = (String) map.get("gsTesuKbn");
		if ("1".equals(selectValue)) {
			dialog.rdoRemittanceRecipient.setSelected(true);
			dialog.rdoRemittanceMaker.setSelected(false);
		} else {
			dialog.rdoRemittanceRecipient.setSelected(false);
			dialog.rdoRemittanceMaker.setSelected(true);
		}
		// 支払銀行名称の設定
		dialog.ctrlPaymentBankName.setValue((String) map.get("sihaBnkName"));
		// 支払支店名称の設定
		dialog.ctrlPaymentBranchName.setValue((String) map.get("sihaStnName"));
		// 支払支店名称の設定
		dialog.ctrlPaymentBankAddress.setValue((String) map.get("sihaBnkAdr"));
		// 経由銀行名称の設定
		dialog.ctrlViaBankName.setValue((String) map.get("keiyuBnkName"));
		// 経由支店名称の設定
		dialog.ctrlViaBranchName.setValue((String) map.get("keiyuStnName"));
		// 経由銀行住所の設定
		dialog.ctrlViaBankAddress.setValue((String) map.get("keiyuBnkAdr"));
		// 受取人住所の設定
		dialog.ctrlRecipientAddress.setValue((String) map.get("ukeAdr"));
		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 開始年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		ref1.refreshName();
		ref2.refreshName();
		ref3.refreshName();
		ref4.refreshName();
		ref5.refreshName();
		ref6.refreshName();
		// 編集モードのときは摘要コード、摘要区分とが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlCustomer.setEnabled(true);
		dialog.ctrlCustomer.setEditable(!isUpdate);
		dialog.ctrlCustomer.setButtonEnabled(!isUpdate);
		dialog.ctrlCustomerConditionCode.setEnabled(true);
		dialog.ctrlCustomerConditionCode.setEditable(!isUpdate);
		if (isUpdate) {
			dialog.ctrlPaymentMethod.getField().requestFocus();
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
		try {// 取引先コード
			if (Util.isNullOrEmpty(dialog.ctrlCustomer.getValue())) {
				showMessage(dialog, "I00002", "C00786");
				dialog.ctrlCustomer.requestTextFocus();
				return false;
			}

			// 取引先条件コード

			if (Util.isNullOrEmpty(dialog.ctrlCustomerConditionCode.getValue())) {
				showMessage(dialog, "I00002", "C00788");
				dialog.ctrlCustomerConditionCode.requestFocus();
				return false;
			}

			if (!isUpdate && checkCode(dialog.ctrlCustomer.getValue(), dialog.ctrlCustomerConditionCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlCustomerConditionCode.requestFocus();
				return false;
			}

			// 支払方法チェック
			if (Util.isNullOrEmpty(dialog.ctrlPaymentMethod.getValue())) {
				showMessage(dialog, "I00002", "C00233");
				dialog.ctrlPaymentMethod.requestTextFocus();
				return false;
			}
			// 日締めチェック
			if (Util.isNullOrEmpty(dialog.numPaymentTermsCutoffDate.getValue())) {
				showMessage(dialog, "I00002", "C01265");
				dialog.numPaymentTermsCutoffDate.requestFocus();
				return false;
			}

			String strNum2 = (String) dialog.numPaymentTermsCutoffDate.getValue();
			// 人入金条件締め日がが0～3199以外
			if (!Util.isNullOrEmpty(strNum2)
				&& ((Integer.parseInt(strNum2) < 1 || 31 < Integer.parseInt(strNum2)) && Integer.parseInt(strNum2) != 99)) {
				// 警告メッセージ表示
				showMessage(dialog, "W00065", "1", "31, 99");
				dialog.numPaymentTermsCutoffDate.requestFocus();
				// エラーを返す
				return false;
			}

			// 締後月チェック
			if (Util.isNullOrEmpty(dialog.numPaymentTermsAfterCutoffMonth.getValue())) {
				showMessage(dialog, "I00002", "C00979");
				dialog.numPaymentTermsAfterCutoffMonth.requestFocus();
				return false;
			}

			String strNum3 = (String) dialog.numPaymentTermsAfterCutoffMonth.getValue();
			if (!Util.isNullOrEmpty(strNum3) && (Integer.parseInt(strNum3) < 0 || 99 < Integer.parseInt(strNum3))) {
				// 警告メッセージ表示
				showMessage(dialog, "W00065", "0", "99");
				dialog.numPaymentTermsAfterCutoffMonth.requestFocus();
				// エラーを返す
				return false;
			}

			// 日払いチェック
			if (Util.isNullOrEmpty(dialog.numPaymentTermsCashInDate.getValue())) {
				showMessage(dialog, "I00002", "C00448");
				dialog.numPaymentTermsCashInDate.requestFocus();
				return false;
			}

			String strNum4 = (String) dialog.numPaymentTermsCashInDate.getValue();
			// 人入金条件締め日がが0～3199以外
			if (!Util.isNullOrEmpty(strNum4)
				&& ((Integer.parseInt(strNum4) < 1 || 31 < Integer.parseInt(strNum4)) && Integer.parseInt(strNum4) != 99)) {
				// 警告メッセージ表示
				showMessage(dialog, "W00065", "1", "31, 99");
				dialog.numPaymentTermsCashInDate.requestFocus();
				// エラーを返す
				return false;
			}

			// 振込振出銀行チェック
			// 内部コードチェック
			boolean bol;

			bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());

			// 内部コードが12,13,18,19の場合
			if (bol) {
				// 振込振出銀行チェック
				if (Util.isNullOrEmpty(dialog.ctrlTransferDrawingBank.getValue())) {
					showMessage(dialog, "I00002", "C00946");
					dialog.ctrlTransferDrawingBank.getField().requestFocus();
					return false;
				}
			}

			// 銀 行チェック
			// 内部コードチェック
			// boolean bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());
			// 内部コードが12,13,18,19の場合
			if (bol) {
				// 銀 行チェック
				if (Util.isNullOrEmpty(dialog.ctrlBank.getValue())) {
					showMessage(dialog, "I00002", "C00124");
					dialog.ctrlBank.requestTextFocus();
					return false;
				}
			}

			// 支 店１チェック
			// 内部コードチェック
			// boolean bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());
			// 内部コードが12,13,18,19の場合
			if (bol) {
				// 支 店１チェック
				if (Util.isNullOrEmpty(dialog.ctrlBranch.getValue())) {
					showMessage(dialog, "I00002", "C00222");
					dialog.ctrlBranch.getField().requestFocus();
					return false;
				}
			}

			// 口座番号チェック
			// 内部コードチェック
			// boolean bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());
			// 内部コードが12,13,18,19の場合
			if (bol) {
				// 口座番号チェック
				if (Util.isNullOrEmpty(dialog.ctrlAccountNumber.getValue())) {
					showMessage(dialog, "I00002", "C00794");
					dialog.ctrlAccountNumber.requestFocus();
					return false;
				}
			}

			// 口座名義カナチェック
			// 内部コードチェック
			// boolean bol = checkNaiCode(dialog.ctrlPaymentMethod.getValue());
			// 内部コードが12,13,18,19の場合
			if (bol) {
				// 口座名義カナチェック
				if (Util.isNullOrEmpty(dialog.ctrlAccountName.getValue())) {
					showMessage(dialog, "I00002", "C00168");
					dialog.ctrlAccountName.requestFocus();
					return false;
				}
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
		// 取引先コードの設定
		String selectedValue;
		map.put("triCode", dialog.ctrlCustomer.getField().getText());
		// 取引先条件コードの設定
		map.put("tjkCode", dialog.ctrlCustomerConditionCode.getField().getText());

		// 振込手数料区分の設定
		if (dialog.rdoMyCompanyPay.isSelected()) {
			selectedValue = "1";
		} else {
			selectedValue = "2";
		}
		map.put("furiTesuKen", selectedValue);
		// 支払条件締め日の設定
		map.put("sjcDate", dialog.numPaymentTermsCutoffDate.getText());
		// 支払条件締め後月の設定
		map.put("sjrMon", dialog.numPaymentTermsAfterCutoffMonth.getText());
		// 支払条件支払日の設定
		map.put("sjpDate", dialog.numPaymentTermsCashInDate.getText());
		// 支払区分の設定
		if (dialog.rdoTemporary.isSelected()) {
			selectedValue = "00";
		} else {
			selectedValue = "10";
		}
		map.put("sihaKbn", selectedValue);
		// 支払方法の設定
		map.put("sihaHouCode", dialog.ctrlPaymentMethod.getField().getText());
		// 振込振出銀行口座ｺｰﾄﾞの設定
		map.put("furiCbkCode", dialog.ctrlTransferDrawingBank.getField().getText());
		// 銀行コードの設定
		map.put("bnkCode", dialog.ctrlBank.getField().getText());
		// 銀行コードの設定
		map.put("bnkStnCode", dialog.ctrlBranch.getField().getText());
		// 預金種目の設定
		if (dialog.rdoNormally.isSelected()) {
			selectedValue = "1";
		} else if (dialog.rdoInterim.isSelected()) {
			selectedValue = "2";
		} else if (dialog.rdoForeignCurrency.isSelected()) {
			selectedValue = "3";
		} else {
			selectedValue = "4";
		}
		map.put("yknKbn", selectedValue);
		// 口座番号の設定
		map.put("yknNo", dialog.ctrlAccountNumber.getField().getText());
		// 口座名義の設定
		map.put("yknName", dialog.ctrlEnglishBankAddress.getField().getText());
		// 口座名義カナの設定
		map.put("yknKana", dialog.ctrlAccountName.getField().getText());
		// 送金目的名の設定
		map.put("gsMktCode", dialog.ctrlRemittancePurpose.getField().getText());
		// 被仕向支店名称の設定
		map.put("gsStnName", dialog.ctrlEnglishBranchName.getField().getText());
		// 被仕向銀行名称の設定
		map.put("gsBnkName", dialog.ctrlEnglishBankName.getField().getText());
		// 手数料区分の設定
		if (dialog.rdoRemittanceRecipient.isSelected()) {
			selectedValue = "1";
		} else {
			selectedValue = "2";
		}
		map.put("gsTesuKbn", selectedValue);
		// 支払銀行名称の設定
		map.put("sihaBnkName", dialog.ctrlPaymentBankName.getField().getText());
		// 支払支店名称の設定
		map.put("sihaStnName", dialog.ctrlPaymentBranchName.getField().getText());
		// 支払銀行住所の設定
		map.put("sihaBnkAdr", dialog.ctrlPaymentBankAddress.getField().getText());
		// 経由銀行名称の設定
		map.put("keiyuBnkName", dialog.ctrlViaBankName.getField().getText());
		// 経由銀行名称の設定
		map.put("keiyuStnName", dialog.ctrlViaBranchName.getField().getText());
		// 経由銀行名称の設定
		map.put("keiyuBnkAdr", dialog.ctrlViaBankAddress.getField().getText());
		// 受取人住所の設定
		map.put("ukeAdr", dialog.ctrlRecipientAddress.getField().getText());
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	// 支払内部コードチェック
	boolean checkNaiCode(String hohCode) throws IOException {
		// 処理種別の設定
		addSendValues("flag", "checkNaiCode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 支払コードの設定
		addSendValues("hohHohCode", hohCode);

		if (!request(getServletName())) {
			errorHandler(dialog);
			return false;
		}
		Map data = super.getResult();

		if ("12".equals(String.valueOf(data.get("hOH_NAI_CODE")))) {
			return true;
		} else if ("13".equals(String.valueOf(data.get("hOH_NAI_CODE")))) {
			return true;
		} else if ("18".equals(String.valueOf(data.get("hOH_NAI_CODE")))) {
			return true;
		} else if ("19".equals(String.valueOf(data.get("hOH_NAI_CODE")))) {
			return true;
		} else {
			return false;
		}
	}

	boolean checkCode(String triCode, String tjkCode) throws IOException {
		// 科目コード未入力
		if (Util.isNullOrEmpty(triCode) && Util.isNullOrEmpty(tjkCode)) {
			return false;
		}
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 取引先コードの設定
		addSendValues("triCode", triCode);
		// 取引先条件コードの設定
		addSendValues("tjkCode", tjkCode);

		if (!request(getServletName())) {
			errorHandler(dialog);
			return true;
		}
		// 結果を取得
		List result = super.getResultList();
		// 結果を返す
		return (result.size() > 0);
	}
}
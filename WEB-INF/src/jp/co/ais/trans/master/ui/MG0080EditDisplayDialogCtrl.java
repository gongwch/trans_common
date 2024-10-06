package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 科目マスタ
 */
public class MG0080EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 科目マスタダイアログ */
	public MG0080EditDisplayDialog dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0080ItemMasterServlet";
	}

	/** 処理区分 */
	private boolean isUpdate = false;

	/** 固定部門名称 */
	private REFDialogCtrl koteiDepCode;

	/** 消費税名称 */
	private REFDialogCtrl zeiCode;

	/** 借方資金名称 */
	private REFDialogCtrl sknCodeDr;

	/** 貸方資金名称 */
	private REFDialogCtrl sknCodeCr;

	/**
	 * コンストラクタ
	 */
	protected MG0080EditDisplayDialogCtrl() {
		// 処理なし
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0080EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 科目マスタダイアログの初期化
		dialog = new MG0080EditDisplayDialog(parent, true, this, titleid);
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		dialog.rdoInput.requestFocus();
		dialog.rdoInput.setSelected(true);
		dialog.rdoDebit.setSelected(true);

		CompanyControlHelper80 cc = CompanyControlHelper80.getInstance(this.getLoginUserCompanyCode());

		// 管理１-6入力フラグが言語の指定された名称を表示する。
		String knrName;
		knrName = cc.getKnrName1() + this.getWord("C02386");
		dialog.chkManagement1InputFlag.setText(knrName);

		knrName = cc.getKnrName2() + this.getWord("C02386");
		dialog.chkManagement2InputFlag.setText(knrName);

		knrName = cc.getKnrName3() + this.getWord("C02386");
		dialog.chkManagement3InputFlag.setText(knrName);

		knrName = cc.getKnrName4() + this.getWord("C02386");
		dialog.chkManagement4InputFlag.setText(knrName);

		knrName = cc.getKnrName5() + this.getWord("C02386");
		dialog.chkManagement5InputFlag.setText(knrName);

		knrName = cc.getKnrName6() + this.getWord("C02386");
		dialog.chkManagement6InputFlag.setText(knrName);

		// 非会計1-3入力フラグが言語の指定された名称を表示する。
		String hmName;
		hmName = cc.getCmpHmName1() + this.getWord("C02386");
		dialog.chkNotAccounting1InputFlag.setText(hmName);

		hmName = cc.getCmpHmName2() + this.getWord("C02386");
		dialog.chkNotAccounting2InputFlag.setText(hmName);

		hmName = cc.getCmpHmName3() + this.getWord("C02386");
		dialog.chkNotAccounting3InputFlag.setText(hmName);
		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));

		init();
	}

	/**
	 * 表示
	 */
	void show() {
		dialog.setVisible(true);
	}

	protected void init() {

		koteiDepCode = new REFDialogCtrl(dialog.ctrlFixedDepartment, dialog.getParentFrame());
		koteiDepCode.setTargetServlet("MG0060DepartmentMasterServlet");
		koteiDepCode.setTitleID(getWord("C02338"));
		koteiDepCode.setColumnLabelIDs("C00698", "C00724", "C00725");
		koteiDepCode.setShowsMsgOnError(false);
		koteiDepCode.addIgnoredButton(dialog.btnReturn);
		koteiDepCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定
				keys.put("kind", "Department");
				keys.put("kaiCode", getLoginUserCompanyCode());
				return keys;
			}
		});

		dialog.ctrlFixedDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				koteiDepCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlFixedDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlFixedDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlFixedDepartment.getValue());
					dialog.ctrlFixedDepartment.getField().clearOldText();
					dialog.ctrlFixedDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		// 消費税コードの設定
		zeiCode = new REFDialogCtrl(dialog.ctrlConsumptionTax, dialog.getParentFrame());
		zeiCode.setTargetServlet("MG0130ConsumptionTaxMasterServlet");
		zeiCode.setTitleID(getWord("C02324"));
		zeiCode.setColumnLabelIDs("C00573", "C00775", "C00828");
		zeiCode.setShowsMsgOnError(false);
		zeiCode.addIgnoredButton(dialog.btnReturn);
		zeiCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定

				keys.put("kind", "ConsumptionTax");
				keys.put("kaiCode", getLoginUserCompanyCode());
				return keys;
			}
		});

		dialog.ctrlConsumptionTax.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				zeiCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlConsumptionTax.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlConsumptionTax.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlConsumptionTax.getValue());
					dialog.ctrlConsumptionTax.getField().clearOldText();
					dialog.ctrlConsumptionTax.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		// 借方資金コードの設定
		sknCodeDr = new REFDialogCtrl(dialog.ctrlDebitFund, dialog.getParentFrame());
		sknCodeDr.setTargetServlet("FundMasterServlet");
		sknCodeDr.setTitleID(getWord("C02033"));
		sknCodeDr.setColumnLabelIDs("C02034", "C02035", "C02036");
		sknCodeDr.setShowsMsgOnError(false);
		sknCodeDr.addIgnoredButton(dialog.btnReturn);
		sknCodeDr.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定

				keys.put("kind", "InputFund");
				keys.put("kaiCode", getLoginUserCompanyCode());
				return keys;
			}
		});

		dialog.ctrlDebitFund.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				sknCodeDr.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlDebitFund.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlDebitFund.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlDebitFund.getValue());
					dialog.ctrlDebitFund.getField().clearOldText();
					dialog.ctrlDebitFund.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		// 貸方資金コードの設定

		sknCodeCr = new REFDialogCtrl(dialog.ctrlCreditFund, dialog.getParentFrame());
		sknCodeCr.setTargetServlet("FundMasterServlet");
		sknCodeCr.setTitleID(getWord("C02033"));
		sknCodeCr.setColumnLabelIDs("C02034", "C02035", "C02036");
		sknCodeCr.setShowsMsgOnError(false);
		sknCodeCr.addIgnoredButton(dialog.btnReturn);
		sknCodeCr.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定

				keys.put("kind", "InputFund");
				keys.put("kaiCode", getLoginUserCompanyCode());
				return keys;
			}
		});

		dialog.ctrlCreditFund.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				sknCodeCr.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlCreditFund.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlCreditFund.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlCreditFund.getValue());
					dialog.ctrlCreditFund.getField().clearOldText();
					dialog.ctrlCreditFund.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

	}

	protected boolean checkDialog() {
		try { // 科目種別チェック
			if (Util.isNullOrEmpty(dialog.ctrlItemType.getComboBox().getSelectedItem().toString())) {
				showMessage(dialog, "I00002", "C01007");
				dialog.ctrlItemType.getComboBox().requestFocus();
				return false;
			}
			// 科目コードチェックの設定
			if (Util.isNullOrEmpty(dialog.ctrlItemCode.getValue())) {
				showMessage(dialog, "I00002", "C00572");
				dialog.ctrlItemCode.requestFocus();
				return false;
			}
			if (!Util.isNullOrEmpty(dialog.ctrlItemCode.getValue())) {

				if (!isUpdate && checkCode(dialog.ctrlItemCode.getValue())) {
					// 警告メッセージ表示
					showMessage(dialog, "W00005", "C00174");
					dialog.ctrlItemCode.requestFocus();
					// エラーを返す
					return false;
				}

			}
			// 科目名称チェック
			if (Util.isNullOrEmpty(dialog.ctrlItemName.getValue())) {
				showMessage(dialog, "I00002", "C00700");
				dialog.ctrlItemName.requestFocus();
				return false;
			}

			// 科目略称チェック
			if (Util.isNullOrEmpty(dialog.ctrlItemAbbreviationName.getValue())) {
				showMessage(dialog, "I00002", "C00730");
				dialog.ctrlItemAbbreviationName.requestFocus();
				return false;
			}
			// 科目検索名称チェック
			if (Util.isNullOrEmpty(dialog.ctrlItemNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00731");
				dialog.ctrlItemNameForSearch.requestFocus();
				return false;
			}
			// GL科目制御区分チェック
			if (dialog.rdoInput.isSelected()) {
				if (Util
					.isNullOrEmpty(String.valueOf(dialog.ctrlGlItemControlDivision.getComboBox().getSelectedItem()))) {
					showMessage(dialog, "I00002", "C00968");
					dialog.ctrlGlItemControlDivision.getComboBox().requestFocus();
					return false;
				}
			}

			// AP科目制御区分チェック
			if (dialog.rdoInput.isSelected()) {
				if (Util
					.isNullOrEmpty(String.valueOf(dialog.ctrlApItemControlDivision.getComboBox().getSelectedItem()))) {
					showMessage(dialog, "I00002", "C00959");
					dialog.ctrlApItemControlDivision.getComboBox().requestFocus();
					return false;
				}
			}

			// AR科目制御区分チェック
			if (dialog.rdoInput.isSelected()) {
				if (Util
					.isNullOrEmpty(String.valueOf(dialog.ctrlArItemControlDivision.getComboBox().getSelectedItem()))) {
					showMessage(dialog, "I00002", "C00960");
					dialog.ctrlArItemControlDivision.getComboBox().requestFocus();
					return false;
				}
			}

			// BG科目制御区分チェック
			if (dialog.rdoInput.isSelected()) {
				if (Util
					.isNullOrEmpty(String.valueOf(dialog.ctrlBgItemControlDivision.getComboBox().getSelectedItem()))) {
					showMessage(dialog, "I00002", "C00962");
					dialog.ctrlBgItemControlDivision.getComboBox().requestFocus();
					return false;
				}
			}

			// 取引先入力フラグチェック
			if (dialog.rdoInput.isSelected()) {
				if (Util.isNullOrEmpty(String.valueOf(dialog.ctrlCustomerInputFlag.getComboBox().getSelectedItem()))) {
					showMessage(dialog, "I00002", "C01134");
					dialog.ctrlCustomerInputFlag.getComboBox().requestFocus();
					return false;
				}
			}

			// 相殺科目制御区分チェック
			if (dialog.rdoInput.isSelected()) {
				if (Util.isNullOrEmpty(String.valueOf(dialog.ctrlOffsettingItemControlDivision.getComboBox()
					.getSelectedItem()))) {
					showMessage(dialog, "I00002", "C01217");
					dialog.ctrlOffsettingItemControlDivision.getComboBox().requestFocus();
					return false;
				}
			}

			// BS勘定消込区分チェック
			if (dialog.rdoInput.isSelected()) {
				if (Util.isNullOrEmpty(String.valueOf(dialog.ctrlBsAccountErasingDivision.getComboBox()
					.getSelectedItem()))) {
					showMessage(dialog, "I00002", "C02078");
					dialog.ctrlBsAccountErasingDivision.getComboBox().requestFocus();
					return false;
				}
			}

			// 評価替対象フラグチェック
			if (dialog.rdoInput.isSelected()) {
				if (Util
					.isNullOrEmpty(String.valueOf(dialog.ctrlRevaluationObjectFlag.getComboBox().getSelectedItem()))) {
					showMessage(dialog, "I00002", "C01301");
					dialog.ctrlRevaluationObjectFlag.getComboBox().requestFocus();
					return false;
				}
			}

			// 補助区分チェック
			if (dialog.rdoInput.isSelected()) {
				if (Util.isNullOrEmpty(String.valueOf(dialog.ctrlSubDivision.getComboBox().getSelectedItem()))) {
					showMessage(dialog, "I00002", "C01314");
					dialog.ctrlSubDivision.getComboBox().requestFocus();
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

			// 消費税コードチェック
			MG0080InputCheckerShouhiZei checker = new MG0080InputCheckerShouhiZei(dialog);

			if (!checker.isValid(dialog.ctrlConsumptionTax.getValue(), dialog.chkSalesTaxInputFlag.isSelected(),
				dialog.chkPurchesesTaxInputFlag.isSelected())) {
				dialog.ctrlConsumptionTax.requestTextFocus();
				return false;
			}

			return true;
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}
	}

	/**
	 * 集計区分 「入力」以外が選択されている場合、科目ｺｰﾄﾞ,科目名称,科目略称,科目検索名称,貸借区分,科目種別,開始年月日,終了年月日以外の全ての項目を入力不能とする。
	 */
	public void changeInput() {
		if (!dialog.rdoInput.isSelected()) {
			this.setComboBoxSelectedItem(dialog.ctrlSubDivision.getComboBox(), "");
			dialog.ctrlSubDivision.setEnabled(false);
			dialog.ctrlFixedDepartment.getField().setValue("");
			dialog.ctrlFixedDepartment.getField().setEditable(false);
			dialog.ctrlFixedDepartment.getNotice().setEditable(true);
			dialog.ctrlFixedDepartment.getNotice().setValue("");
			dialog.ctrlFixedDepartment.getNotice().setEditable(false);
			dialog.ctrlFixedDepartment.getButton().setEnabled(false);
			dialog.ctrlConsumptionTax.getField().setValue("");
			dialog.ctrlConsumptionTax.getField().setEditable(false);
			dialog.ctrlConsumptionTax.getNotice().setEditable(true);
			dialog.ctrlConsumptionTax.getNotice().setValue("");
			dialog.ctrlConsumptionTax.getNotice().setEditable(false);
			dialog.ctrlConsumptionTax.getButton().setEnabled(false);
			dialog.ctrlDebitFund.getField().setValue("");
			dialog.ctrlDebitFund.getField().setEditable(false);
			dialog.ctrlDebitFund.getNotice().setEditable(true);
			dialog.ctrlDebitFund.getNotice().setValue("");
			dialog.ctrlDebitFund.getNotice().setEditable(false);
			dialog.ctrlDebitFund.getButton().setEnabled(false);
			dialog.ctrlCreditFund.getField().setValue("");
			dialog.ctrlCreditFund.getField().setEditable(false);
			dialog.ctrlCreditFund.getNotice().setEditable(true);
			dialog.ctrlCreditFund.getNotice().setValue("");
			dialog.ctrlCreditFund.getNotice().setEditable(false);
			dialog.ctrlCreditFund.getButton().setEnabled(false);
			this.setComboBoxSelectedItem(dialog.ctrlGlItemControlDivision.getComboBox(), "");
			dialog.ctrlGlItemControlDivision.getComboBox().setEnabled(false);
			this.setComboBoxSelectedItem(dialog.ctrlApItemControlDivision.getComboBox(), "");
			dialog.ctrlApItemControlDivision.getComboBox().setEnabled(false);
			this.setComboBoxSelectedItem(dialog.ctrlArItemControlDivision.getComboBox(), "");
			dialog.ctrlArItemControlDivision.getComboBox().setEnabled(false);
			this.setComboBoxSelectedItem(dialog.ctrlBgItemControlDivision.getComboBox(), "");
			dialog.ctrlBgItemControlDivision.getComboBox().setEnabled(false);
			this.setComboBoxSelectedItem(dialog.ctrlCustomerInputFlag.getComboBox(), "");
			dialog.ctrlCustomerInputFlag.getComboBox().setEnabled(false);
			this.setComboBoxSelectedItem(dialog.ctrlOffsettingItemControlDivision.getComboBox(), "");
			dialog.ctrlOffsettingItemControlDivision.getComboBox().setEnabled(false);
			// BS勘定消込区分
			this.setComboBoxSelectedItem(dialog.ctrlBsAccountErasingDivision.getComboBox(), "");
			dialog.ctrlBsAccountErasingDivision.getComboBox().setEnabled(false);

			this.setComboBoxSelectedItem(dialog.ctrlRevaluationObjectFlag.getComboBox(), "");
			dialog.ctrlRevaluationObjectFlag.getComboBox().setEnabled(false);

			dialog.chkReceivingSlipInputFlag.setSelected(false);
			dialog.chkReceivingSlipInputFlag.setEnabled(false);
			dialog.chkPaymentSlipInputFlag.setSelected(false);
			dialog.chkPaymentSlipInputFlag.setEnabled(false);
			dialog.chkTransferSlipInputFlag.setSelected(false);
			dialog.chkTransferSlipInputFlag.setEnabled(false);
			dialog.chkExpenseInputFlag.setSelected(false);
			dialog.chkExpenseInputFlag.setEnabled(false);
			dialog.chkAccountsPayableAppropriateSlipInputFlag.setSelected(false);
			dialog.chkAccountsPayableAppropriateSlipInputFlag.setEnabled(false);
			dialog.chkAccountsReceivableAppropriateSlipInputFlag.setSelected(false);
			dialog.chkAccountsReceivableAppropriateSlipInputFlag.setEnabled(false);
			dialog.chkAccountsReceivableErasingSlipInputFlag.setSelected(false);
			dialog.chkAccountsReceivableErasingSlipInputFlag.setEnabled(false);
			dialog.chkAssetsAppropriateSlipInputFlag.setSelected(false);
			dialog.chkAssetsAppropriateSlipInputFlag.setEnabled(false);
			dialog.chkPaymentRequestSlipInputFlag.setSelected(false);
			dialog.chkPaymentRequestSlipInputFlag.setEnabled(false);
			dialog.chkMultiCurrencyInputFlag.setSelected(false);
			dialog.chkMultiCurrencyInputFlag.setEnabled(false);
			dialog.chkEmployeeInputFlag.setSelected(false);
			dialog.chkEmployeeInputFlag.setEnabled(false);
			dialog.chkManagement1InputFlag.setSelected(false);
			dialog.chkManagement1InputFlag.setEnabled(false);
			dialog.chkManagement2InputFlag.setSelected(false);
			dialog.chkManagement2InputFlag.setEnabled(false);
			dialog.chkManagement3InputFlag.setSelected(false);
			dialog.chkManagement3InputFlag.setEnabled(false);
			dialog.chkManagement4InputFlag.setSelected(false);
			dialog.chkManagement4InputFlag.setEnabled(false);
			dialog.chkManagement5InputFlag.setSelected(false);
			dialog.chkManagement5InputFlag.setEnabled(false);
			dialog.chkManagement6InputFlag.setSelected(false);
			dialog.chkManagement6InputFlag.setEnabled(false);
			dialog.chkNotAccounting1InputFlag.setSelected(false);
			dialog.chkNotAccounting1InputFlag.setEnabled(false);
			dialog.chkNotAccounting2InputFlag.setSelected(false);
			dialog.chkNotAccounting2InputFlag.setEnabled(false);
			dialog.chkNotAccounting3InputFlag.setSelected(false);
			dialog.chkNotAccounting3InputFlag.setEnabled(false);
			dialog.chkSalesTaxInputFlag.setSelected(false);
			dialog.chkSalesTaxInputFlag.setEnabled(false);
			dialog.chkPurchesesTaxInputFlag.setSelected(false);
			dialog.chkPurchesesTaxInputFlag.setEnabled(false);
			dialog.chkHasFlg.setSelected(false);
			dialog.chkHasFlg.setEnabled(false);

		} else {
			dialog.ctrlSubDivision.getComboBox().setEnabled(true);
			dialog.ctrlSubDivision.getComboBox().setSelectedIndex(1);
			dialog.ctrlSubDivision.getLabel().setEnabled(true);
			dialog.ctrlFixedDepartment.getField().setEditable(true);
			dialog.ctrlFixedDepartment.getButton().setEnabled(true);
			dialog.ctrlFixedDepartment.getButton().setEnabled(true);
			dialog.ctrlConsumptionTax.getField().setEditable(true);
			dialog.ctrlConsumptionTax.getButton().setEnabled(true);
			dialog.ctrlDebitFund.getField().setEditable(true);
			dialog.ctrlDebitFund.getButton().setEnabled(true);
			dialog.ctrlCreditFund.getField().setEditable(true);
			dialog.ctrlCreditFund.getButton().setEnabled(true);
			dialog.ctrlGlItemControlDivision.getComboBox().setEnabled(true);
			dialog.ctrlGlItemControlDivision.getComboBox().setSelectedIndex(1);
			dialog.ctrlApItemControlDivision.getComboBox().setEnabled(true);
			dialog.ctrlApItemControlDivision.getComboBox().setSelectedIndex(1);
			dialog.ctrlArItemControlDivision.getComboBox().setEnabled(true);
			dialog.ctrlArItemControlDivision.getComboBox().setSelectedIndex(1);
			dialog.ctrlBgItemControlDivision.getComboBox().setEnabled(true);
			dialog.ctrlBgItemControlDivision.getComboBox().setSelectedIndex(1);
			dialog.ctrlCustomerInputFlag.getComboBox().setEnabled(true);
			dialog.ctrlCustomerInputFlag.getComboBox().setSelectedIndex(1);
			dialog.ctrlOffsettingItemControlDivision.getComboBox().setEnabled(true);
			dialog.ctrlOffsettingItemControlDivision.getComboBox().setSelectedIndex(1);

			dialog.ctrlBsAccountErasingDivision.getComboBox().setEnabled(true);
			dialog.ctrlBsAccountErasingDivision.getComboBox().setSelectedIndex(1);

			dialog.ctrlRevaluationObjectFlag.getComboBox().setEnabled(true);
			dialog.ctrlRevaluationObjectFlag.getComboBox().setSelectedIndex(1);

			dialog.chkReceivingSlipInputFlag.setEnabled(true);
			dialog.chkPaymentSlipInputFlag.setEnabled(true);
			dialog.chkTransferSlipInputFlag.setEnabled(true);
			dialog.chkExpenseInputFlag.setEnabled(true);
			dialog.chkAccountsPayableAppropriateSlipInputFlag.setEnabled(true);
			dialog.chkAccountsReceivableAppropriateSlipInputFlag.setEnabled(true);
			dialog.chkAccountsReceivableErasingSlipInputFlag.setEnabled(true);
			dialog.chkAssetsAppropriateSlipInputFlag.setEnabled(true);
			dialog.chkPaymentRequestSlipInputFlag.setEnabled(true);
			dialog.chkMultiCurrencyInputFlag.setEnabled(true);
			dialog.chkEmployeeInputFlag.setEnabled(true);
			dialog.chkManagement1InputFlag.setEnabled(true);
			dialog.chkManagement2InputFlag.setEnabled(true);
			dialog.chkManagement3InputFlag.setEnabled(true);
			dialog.chkManagement4InputFlag.setEnabled(true);
			dialog.chkManagement5InputFlag.setEnabled(true);
			dialog.chkManagement6InputFlag.setEnabled(true);
			dialog.chkNotAccounting1InputFlag.setEnabled(true);
			dialog.chkNotAccounting2InputFlag.setEnabled(true);
			dialog.chkNotAccounting3InputFlag.setEnabled(true);
			dialog.chkSalesTaxInputFlag.setEnabled(true);
			dialog.chkPurchesesTaxInputFlag.setEnabled(true);
			dialog.chkHasFlg.setEnabled(true);
		}
	}

	/**
	 * 値のセット
	 * 
	 * @param map セットする値
	 */

	void setValues(Map map) {
		boolean boo;
		// 科目コード
		dialog.ctrlItemCode.setValue((String) map.get("kmkCode"));
		// 科目名称
		dialog.ctrlItemName.setValue((String) map.get("kmkName"));
		// 科目略称
		dialog.ctrlItemAbbreviationName.setValue((String) map.get("kmkName_S"));
		// 科目検索名称
		dialog.ctrlItemNameForSearch.setValue((String) map.get("kmkName_K"));
		// 集計区分
		int sumKbn = Integer.parseInt((String) map.get("sumKbn"));
		if (sumKbn == 0) {
			dialog.rdoInput.setSelected(true);
			dialog.rdoSummary.setSelected(false);
			dialog.rdoCaption.setSelected(false);
		} else if (sumKbn == 1) {
			dialog.rdoInput.setSelected(false);
			dialog.rdoSummary.setSelected(true);
			dialog.rdoCaption.setSelected(false);
		} else {
			dialog.rdoInput.setSelected(false);
			dialog.rdoSummary.setSelected(false);
			dialog.rdoCaption.setSelected(true);
		}
		// 科目種別
		this.setComboBoxSelectedItem(dialog.ctrlItemType.getComboBox(), (String) map.get("kmkShu"));
		// 貸借区分
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("dcKbn")));
		dialog.rdoDebit.setSelected(!boo);
		dialog.rdoCredit.setSelected(boo);

		// 集計区分にて「入力科目」の場合科目ｺｰﾄﾞ,科目名称,科目略称,科目検索名称,貸借区分,科目種別,開始年月日,終了年月日が値がある
		if (sumKbn == 0) {
			// 補助区分
			this.setComboBoxSelectedItem(dialog.ctrlSubDivision.getComboBox(), (String) map.get("hkmKbn"));
			// ＧＬ科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlGlItemControlDivision.getComboBox(), (String) map.get("kmkCntGl"));
			// AP科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlApItemControlDivision.getComboBox(), (String) map.get("kmkCntAp"));
			// AR科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlArItemControlDivision.getComboBox(), (String) map.get("kmkCntAr"));
			// 固定部門ｺｰﾄﾞ
			dialog.ctrlFixedDepartment.setValue((String) map.get("koteiDepCode"));
			// 消費税コード
			dialog.ctrlConsumptionTax.setValue((String) map.get("zeiCode"));
			// 入金伝票入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("glFlg1")));
			dialog.chkReceivingSlipInputFlag.setSelected(boo);
			// 出金伝票入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("glFlg2")));
			dialog.chkPaymentSlipInputFlag.setSelected(boo);
			// 振替伝票入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("glFlg3")));
			dialog.chkTransferSlipInputFlag.setSelected(boo);
			// 経費精算伝票入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("apFlg1")));
			dialog.chkExpenseInputFlag.setSelected(boo);
			// 請求書伝票入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("apFlg2")));
			dialog.chkAccountsPayableAppropriateSlipInputFlag.setSelected(boo);
			// 債権計上伝票入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("arFlg1")));
			dialog.chkAccountsReceivableAppropriateSlipInputFlag.setSelected(boo);
			// 債権消込伝票入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("arFlg2")));
			dialog.chkAccountsReceivableErasingSlipInputFlag.setSelected(boo);
			// 資産計上伝票入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("faFlg1")));
			dialog.chkAssetsAppropriateSlipInputFlag.setSelected(boo);
			// 支払依頼伝票入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("faFlg2")));
			dialog.chkPaymentRequestSlipInputFlag.setSelected(boo);
			// 取引先入力フラグ
			this.setComboBoxSelectedItem(dialog.ctrlCustomerInputFlag.getComboBox(), (String) map.get("triCodeFlg"));
			// 発生日入力ﾌﾗｸﾞ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hasFlg")));
			dialog.chkHasFlg.setSelected(boo);
			// 社員入力フラグ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("empCodeFlg")));
			dialog.chkEmployeeInputFlag.setSelected(boo);
			// 管理１-6入力フラグ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg1")));
			dialog.chkManagement1InputFlag.setSelected(boo);
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg2")));
			dialog.chkManagement2InputFlag.setSelected(boo);
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg3")));
			dialog.chkManagement3InputFlag.setSelected(boo);
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg4")));
			dialog.chkManagement4InputFlag.setSelected(boo);
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg5")));
			dialog.chkManagement5InputFlag.setSelected(boo);
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg6")));
			dialog.chkManagement6InputFlag.setSelected(boo);
			// 非会計1-3入力フラグ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hmFlg1")));
			dialog.chkNotAccounting1InputFlag.setSelected(boo);
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hmFlg2")));
			dialog.chkNotAccounting2InputFlag.setSelected(boo);
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hmFlg3")));
			dialog.chkNotAccounting3InputFlag.setSelected(boo);
			// 売上課税入力フラグ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("uriZeiFlg")));
			dialog.chkSalesTaxInputFlag.setSelected(boo);
			// 仕入課税入力フラグ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("sirZeiFlg")));
			dialog.chkPurchesesTaxInputFlag.setSelected(boo);
			// 多通貨入力フラグ
			boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("mcrFlg")));
			dialog.chkMultiCurrencyInputFlag.setSelected(boo);
			// 評価替対象フラグ
			this.setComboBoxSelectedItem(dialog.ctrlRevaluationObjectFlag.getComboBox(), (String) map.get("excFlg"));
			// BG科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlBgItemControlDivision.getComboBox(), (String) map.get("kmkCntBg"));
			// 借方資金コード
			dialog.ctrlDebitFund.setValue((String) map.get("sknCodeDr"));
			// 貸方資金コード
			dialog.ctrlCreditFund.setValue((String) map.get("sknCodeCr"));
			// 相殺科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlOffsettingItemControlDivision.getComboBox(), (String) map
				.get("kmkCntSousai"));
			// BS勘定消込区分
			this
				.setComboBoxSelectedItem(dialog.ctrlBsAccountErasingDivision.getComboBox(), (String) map.get("kesiKbn"));

		} else {
			// 補助区分
			this.setComboBoxSelectedItem(dialog.ctrlSubDivision.getComboBox(), "");
			dialog.ctrlSubDivision.setEnabled(false);
			// 固定部門
			dialog.ctrlFixedDepartment.getField().setValue("");
			dialog.ctrlFixedDepartment.getField().setEditable(false);
			dialog.ctrlFixedDepartment.getNotice().setEditable(true);
			dialog.ctrlFixedDepartment.getNotice().setValue("");
			dialog.ctrlFixedDepartment.getNotice().setEditable(false);
			dialog.ctrlFixedDepartment.getButton().setEnabled(false);
			// 消 費 税
			dialog.ctrlConsumptionTax.getField().setValue("");
			dialog.ctrlConsumptionTax.getField().setEditable(false);
			dialog.ctrlConsumptionTax.getNotice().setEditable(true);
			dialog.ctrlConsumptionTax.getNotice().setValue("");
			dialog.ctrlConsumptionTax.getNotice().setEditable(false);
			dialog.ctrlConsumptionTax.getButton().setEnabled(false);
			// 借方資金コード
			dialog.ctrlDebitFund.getField().setValue("");
			dialog.ctrlDebitFund.getField().setEditable(false);
			dialog.ctrlDebitFund.getNotice().setEditable(true);
			dialog.ctrlDebitFund.getNotice().setValue("");
			dialog.ctrlDebitFund.getNotice().setEditable(false);
			dialog.ctrlDebitFund.getButton().setEnabled(false);
			// 貸方資金コード
			dialog.ctrlCreditFund.getField().setValue("");
			dialog.ctrlCreditFund.getField().setEditable(false);
			dialog.ctrlCreditFund.getNotice().setEditable(true);
			dialog.ctrlCreditFund.getNotice().setValue("");
			dialog.ctrlCreditFund.getNotice().setEditable(false);
			dialog.ctrlCreditFund.getButton().setEnabled(false);
			// ＧＬ科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlGlItemControlDivision.getComboBox(), "");
			dialog.ctrlGlItemControlDivision.getComboBox().setEnabled(false);
			// AP科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlApItemControlDivision.getComboBox(), "");
			dialog.ctrlApItemControlDivision.getComboBox().setEnabled(false);
			// AR科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlArItemControlDivision.getComboBox(), "");
			dialog.ctrlArItemControlDivision.getComboBox().setEnabled(false);
			// BG科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlBgItemControlDivision.getComboBox(), "");
			dialog.ctrlBgItemControlDivision.getComboBox().setEnabled(false);
			// 取引先入力フラグ
			this.setComboBoxSelectedItem(dialog.ctrlCustomerInputFlag.getComboBox(), "");
			dialog.ctrlCustomerInputFlag.getComboBox().setEnabled(false);
			// 相殺科目制御区分
			this.setComboBoxSelectedItem(dialog.ctrlOffsettingItemControlDivision.getComboBox(), "");
			dialog.ctrlOffsettingItemControlDivision.getComboBox().setEnabled(false);
			// BS勘定消込区分
			this.setComboBoxSelectedItem(dialog.ctrlBsAccountErasingDivision.getComboBox(), "");
			dialog.ctrlBsAccountErasingDivision.getComboBox().setEnabled(false);
			// 評価替対象フラグ
			this.setComboBoxSelectedItem(dialog.ctrlRevaluationObjectFlag.getComboBox(), "");
			dialog.ctrlRevaluationObjectFlag.getComboBox().setEnabled(false);
			// 入金伝票入力ﾌﾗｸﾞ
			dialog.chkReceivingSlipInputFlag.setSelected(false);
			dialog.chkReceivingSlipInputFlag.setEnabled(false);
			// 出金伝票入力フラグ
			dialog.chkPaymentSlipInputFlag.setSelected(false);
			dialog.chkPaymentSlipInputFlag.setEnabled(false);
			// 振替伝票入力フラグ
			dialog.chkTransferSlipInputFlag.setSelected(false);
			dialog.chkTransferSlipInputFlag.setEnabled(false);
			// 経費精算伝票入力フラグ
			dialog.chkExpenseInputFlag.setSelected(false);
			dialog.chkExpenseInputFlag.setEnabled(false);
			// 債務計上伝票入力フラグ
			dialog.chkAccountsPayableAppropriateSlipInputFlag.setSelected(false);
			dialog.chkAccountsPayableAppropriateSlipInputFlag.setEnabled(false);
			// 債権計上伝票入力フラグ
			dialog.chkAccountsReceivableAppropriateSlipInputFlag.setSelected(false);
			dialog.chkAccountsReceivableAppropriateSlipInputFlag.setEnabled(false);
			// 債権消込伝票入力フラグ
			dialog.chkAccountsReceivableErasingSlipInputFlag.setSelected(false);
			dialog.chkAccountsReceivableErasingSlipInputFlag.setEnabled(false);
			// 資産計上伝票入力フラグ
			dialog.chkAssetsAppropriateSlipInputFlag.setSelected(false);
			dialog.chkAssetsAppropriateSlipInputFlag.setEnabled(false);
			// 支払依頼伝票入力フラグ
			dialog.chkPaymentRequestSlipInputFlag.setSelected(false);
			dialog.chkPaymentRequestSlipInputFlag.setEnabled(false);
			// 多通貨入力フラグ
			dialog.chkMultiCurrencyInputFlag.setSelected(false);
			dialog.chkMultiCurrencyInputFlag.setEnabled(false);
			// 社員入力フラグ
			dialog.chkEmployeeInputFlag.setSelected(false);
			dialog.chkEmployeeInputFlag.setEnabled(false);
			// 管理１-6入力フラグ
			dialog.chkManagement1InputFlag.setSelected(false);
			dialog.chkManagement1InputFlag.setEnabled(false);
			dialog.chkManagement2InputFlag.setSelected(false);
			dialog.chkManagement2InputFlag.setEnabled(false);
			dialog.chkManagement3InputFlag.setSelected(false);
			dialog.chkManagement3InputFlag.setEnabled(false);
			dialog.chkManagement4InputFlag.setSelected(false);
			dialog.chkManagement4InputFlag.setEnabled(false);
			dialog.chkManagement5InputFlag.setSelected(false);
			dialog.chkManagement5InputFlag.setEnabled(false);
			dialog.chkManagement6InputFlag.setSelected(false);
			dialog.chkManagement6InputFlag.setEnabled(false);
			// 非会計1-3入力フラグ
			dialog.chkNotAccounting1InputFlag.setSelected(false);
			dialog.chkNotAccounting1InputFlag.setEnabled(false);
			dialog.chkNotAccounting2InputFlag.setSelected(false);
			dialog.chkNotAccounting2InputFlag.setEnabled(false);
			dialog.chkNotAccounting3InputFlag.setSelected(false);
			dialog.chkNotAccounting3InputFlag.setEnabled(false);
			// 売上課税入力フラグ
			dialog.chkSalesTaxInputFlag.setSelected(false);
			dialog.chkSalesTaxInputFlag.setEnabled(false);
			// 仕入課税入力フラグ
			dialog.chkPurchesesTaxInputFlag.setSelected(false);
			dialog.chkPurchesesTaxInputFlag.setEnabled(false);
			// 発生日入力ﾌﾗｸﾞ
			dialog.chkHasFlg.setSelected(false);
			dialog.chkHasFlg.setEnabled(false);
		}
		// 開始年月日
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは摘要コード、摘要区分とが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlItemCode.getField().setEditable(!isUpdate);
		if (isUpdate) {
			dialog.rdoInput.requestFocus();
		} else {
			dialog.rdoInput.requestFocus();
		}

		koteiDepCode.refreshName();
		zeiCode.refreshName();
		sknCodeDr.refreshName();
		sknCodeCr.refreshName();
		if (!isUpdate) {
			dialog.ctrlItemCode.getField().requestFocus();
		}
	}

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
		// 科目コードの設定
		map.put("kmkCode", dialog.ctrlItemCode.getValue());
		// 科目名称の設定
		map.put("kmkName", dialog.ctrlItemName.getValue());
		// 科目略称の設定
		map.put("kmkName_S", dialog.ctrlItemAbbreviationName.getValue());
		// 科目検索名称の設定
		map.put("kmkName_K", dialog.ctrlItemNameForSearch.getValue());
		// 集計区分の設定
		boolean boo;
		boo = dialog.rdoInput.isSelected();
		if (boo) {
			map.put("sumKbn", "0");
		} else if (dialog.rdoSummary.isSelected()) {
			map.put("sumKbn", "1");
		} else {
			map.put("sumKbn", "2");
		}
		// 科目種別の設定
		map.put("kmkShu", this.getComboBoxSelectedValue(dialog.ctrlItemType.getComboBox()));
		// 貸借区分の設定
		map.put("dcKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoCredit.isSelected())));
		// 集計区分にて「入力」以外が選択されている場合、
		// 科目ｺｰﾄﾞ,科目名称,科目略称,科目検索名称,貸借区分,科目種別,開始年月日,終了年月日以外の全ての項目を
		// 入力不能とする。
		if (boo) {
			// 補助区分の設定
			map.put("hkmKbn", this.getComboBoxSelectedValue(dialog.ctrlSubDivision.getComboBox()));
			// ＧＬ科目制御区分の設定
			map.put("kmkCntGl", this.getComboBoxSelectedValue(dialog.ctrlGlItemControlDivision.getComboBox()));
			// AP科目制御区分の設定
			map.put("kmkCntAp", this.getComboBoxSelectedValue(dialog.ctrlApItemControlDivision.getComboBox()));
			// AR科目制御区分の設定
			map.put("kmkCntAr", this.getComboBoxSelectedValue(dialog.ctrlArItemControlDivision.getComboBox()));
			// 固定部門ｺｰﾄﾞの設定
			map.put("koteiDepCode", dialog.ctrlFixedDepartment.getValue());
			// 消費税コードの設定
			map.put("zeiCode", dialog.ctrlConsumptionTax.getValue());
			// 入金伝票入力ﾌﾗｸﾞの設定
			map.put("glFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkReceivingSlipInputFlag.isSelected())));
			// 出金伝票入力ﾌﾗｸﾞの設定
			map.put("glFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkPaymentSlipInputFlag.isSelected())));
			// 振替伝票入力ﾌﾗｸﾞの設定
			map.put("glFlg3", String.valueOf(BooleanUtil.toInt(dialog.chkTransferSlipInputFlag.isSelected())));
			// 経費精算伝票入力ﾌﾗｸﾞの設定
			map.put("apFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkExpenseInputFlag.isSelected())));
			// 請求書伝票入力ﾌﾗｸﾞの設定
			map.put("apFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkAccountsPayableAppropriateSlipInputFlag
				.isSelected())));
			// 債権計上伝票入力ﾌﾗｸﾞの設定
			map.put("arFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkAccountsReceivableAppropriateSlipInputFlag
				.isSelected())));
			// 債権消込伝票入力ﾌﾗｸﾞの設定
			map.put("arFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkAccountsReceivableErasingSlipInputFlag
				.isSelected())));
			// 資産計上伝票入力ﾌﾗｸﾞの設定
			map.put("faFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkAssetsAppropriateSlipInputFlag.isSelected())));
			// 支払依頼伝票入力ﾌﾗｸﾞの設定
			map.put("faFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkPaymentRequestSlipInputFlag.isSelected())));
			// 取引先入力フラグの設定
			map.put("triCodeFlg", this.getComboBoxSelectedValue(dialog.ctrlCustomerInputFlag.getComboBox()));
			// 発生日入力ﾌﾗｸﾞの設定
			map.put("hasFlg", String.valueOf(BooleanUtil.toInt(dialog.chkHasFlg.isSelected())));
			// 社員入力フラグの設定
			map.put("empCodeFlg", String.valueOf(BooleanUtil.toInt(dialog.chkEmployeeInputFlag.isSelected())));
			// 管理１-6入力フラグの設定
			map.put("knrFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkManagement1InputFlag.isSelected())));
			map.put("knrFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkManagement2InputFlag.isSelected())));
			map.put("knrFlg3", String.valueOf(BooleanUtil.toInt(dialog.chkManagement3InputFlag.isSelected())));
			map.put("knrFlg4", String.valueOf(BooleanUtil.toInt(dialog.chkManagement4InputFlag.isSelected())));
			map.put("knrFlg5", String.valueOf(BooleanUtil.toInt(dialog.chkManagement5InputFlag.isSelected())));
			map.put("knrFlg6", String.valueOf(BooleanUtil.toInt(dialog.chkManagement6InputFlag.isSelected())));
			// 非会計1-3入力フラグの設定
			map.put("hmFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkNotAccounting1InputFlag.isSelected())));
			map.put("hmFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkNotAccounting2InputFlag.isSelected())));
			map.put("hmFlg3", String.valueOf(BooleanUtil.toInt(dialog.chkNotAccounting3InputFlag.isSelected())));
			// 売上課税入力フラグの設定
			map.put("uriZeiFlg", String.valueOf(BooleanUtil.toInt(dialog.chkSalesTaxInputFlag.isSelected())));
			// 仕入課税入力フラグの設定
			map.put("sirZeiFlg", String.valueOf(BooleanUtil.toInt(dialog.chkPurchesesTaxInputFlag.isSelected())));
			// 多通貨入力フラグの設定
			map.put("mcrFlg", String.valueOf(BooleanUtil.toInt(dialog.chkMultiCurrencyInputFlag.isSelected())));
			// 評価替対象フラグの設定
			map.put("excFlg", this.getComboBoxSelectedValue(dialog.ctrlRevaluationObjectFlag.getComboBox()));
			// BG科目制御区分の設定
			map.put("kmkCntBg", this.getComboBoxSelectedValue(dialog.ctrlBgItemControlDivision.getComboBox()));
			// 借方資金コードの設定
			map.put("sknCodeDr", dialog.ctrlDebitFund.getValue());
			// 貸方資金コードの設定
			map.put("sknCodeCr", dialog.ctrlCreditFund.getValue());
			// 相殺科目制御区分の設定
			map.put("kmkCntSousai", this.getComboBoxSelectedValue(dialog.ctrlOffsettingItemControlDivision
				.getComboBox()));
			// BS勘定消込区分の設定
			map.put("kesiKbn", this.getComboBoxSelectedValue(dialog.ctrlBsAccountErasingDivision.getComboBox()));
		} else {
			// リストボックス
			map.put("kmkCntGl", null);
			map.put("kmkCntAp", null);
			map.put("kmkCntAr", null);
			map.put("koteiDepCode", null);
			map.put("zeiCode", null);
			map.put("kmkCntBg", null);
			map.put("sknCodeDr", null);
			map.put("sknCodeCr", null);
			map.put("kmkCntSousai", null);
			// チェックボックス
			map.put("hkmKbn", "0");
			map.put("glFlg1", "0");
			map.put("glFlg2", "0");
			map.put("glFlg3", "0");
			map.put("apFlg1", "0");
			map.put("apFlg2", "0");
			map.put("arFlg1", "0");
			map.put("arFlg2", "0");
			map.put("faFlg1", "0");
			map.put("faFlg2", "0");
			map.put("triCodeFlg", "0");
			map.put("hasFlg", "0");
			map.put("empCodeFlg", "0");
			map.put("knrFlg1", "0");
			map.put("knrFlg2", "0");
			map.put("knrFlg3", "0");
			map.put("knrFlg4", "0");
			map.put("knrFlg5", "0");
			map.put("knrFlg6", "0");
			map.put("hmFlg1", "0");
			map.put("hmFlg2", "0");
			map.put("hmFlg3", "0");
			map.put("uriZeiFlg", "0");
			map.put("sirZeiFlg", "0");
			map.put("mcrFlg", "0");
			map.put("excFlg", "0");
			map.put("kesiKbn", "0");
		}
		// 開始年月日
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	/**
	 * リストボックスが値を作る
	 * 
	 * @param kmkShuMap
	 * @param hkmKbnMap
	 * @param kmkCntGlMap
	 * @param kmkCntApMap
	 * @param kmkCntArMap
	 * @param triCodeFlgMap
	 * @param excFlgMap
	 * @param kmkCntBgMap
	 * @param kmkCntSousaiMap
	 * @param kesiKbnMap
	 */
	public void setSelectedMap(Map kmkShuMap, Map hkmKbnMap, Map kmkCntGlMap, Map kmkCntApMap, Map kmkCntArMap,
		Map triCodeFlgMap, Map excFlgMap, Map kmkCntBgMap, Map kmkCntSousaiMap, Map kesiKbnMap) {
		// 科目種別
		this.fillItemsToComboBox(dialog.ctrlItemType.getComboBox(), kmkShuMap);

		// 補助区分
		this.fillItemsToComboBox(dialog.ctrlSubDivision.getComboBox(), hkmKbnMap);
		dialog.ctrlSubDivision.getComboBox().setSelectedIndex(1);
		// ＧＬ科目制御区分
		this.fillItemsToComboBox(dialog.ctrlGlItemControlDivision.getComboBox(), kmkCntGlMap);
		dialog.ctrlGlItemControlDivision.getComboBox().setSelectedIndex(1);
		// AP科目制御区分
		this.fillItemsToComboBox(dialog.ctrlApItemControlDivision.getComboBox(), kmkCntApMap);
		dialog.ctrlApItemControlDivision.getComboBox().setSelectedIndex(1);
		// AR科目制御区分
		this.fillItemsToComboBox(dialog.ctrlArItemControlDivision.getComboBox(), kmkCntArMap);
		dialog.ctrlArItemControlDivision.getComboBox().setSelectedIndex(1);
		// 取引先入力フラグ
		this.fillItemsToComboBox(dialog.ctrlCustomerInputFlag.getComboBox(), triCodeFlgMap);
		dialog.ctrlCustomerInputFlag.getComboBox().setSelectedIndex(1);
		// 評価替対象フラグ
		this.fillItemsToComboBox(dialog.ctrlRevaluationObjectFlag.getComboBox(), excFlgMap);
		dialog.ctrlRevaluationObjectFlag.getComboBox().setSelectedIndex(1);
		// BG科目制御区分
		this.fillItemsToComboBox(dialog.ctrlBgItemControlDivision.getComboBox(), kmkCntBgMap);
		dialog.ctrlBgItemControlDivision.getComboBox().setSelectedIndex(1);
		// 相殺科目制御区分
		this.fillItemsToComboBox(dialog.ctrlOffsettingItemControlDivision.getComboBox(), kmkCntSousaiMap);
		dialog.ctrlOffsettingItemControlDivision.getComboBox().setSelectedIndex(1);
		// BS勘定消込区分
		this.fillItemsToComboBox(dialog.ctrlBsAccountErasingDivision.getComboBox(), kesiKbnMap);
		dialog.ctrlBsAccountErasingDivision.getComboBox().setSelectedIndex(1);

	}

	boolean checkCode(String code) throws IOException {
		// コード未入力
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 科目コードの設定
		addSendValues("kmkCode", code);

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

class CompanyControlHelper80 extends TAppletClientBase {

	private static Map instances = Collections.synchronizedMap(new HashMap());

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
	 * @param companyCode
	 * @return CompanyControlHelper80
	 */
	public static CompanyControlHelper80 getInstance(String companyCode) {
		if (!instances.containsKey(companyCode)) {
			CompanyControlHelper80 instance = new CompanyControlHelper80(companyCode);
			instances.put(companyCode, instance);
		}
		return (CompanyControlHelper80) instances.get(companyCode);
	}

	/**
	 * @param companyCode
	 */
	public static void reload(String companyCode) {
		instances.remove(companyCode);
	}

	private static final String TARGET_SERVLET = "MG0030CompanyControlMasterServlet";

	private CompanyControlHelper80(String companyCode) {
		addSendValues("flag", "findone");
		addSendValues("kaiCode", companyCode);
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

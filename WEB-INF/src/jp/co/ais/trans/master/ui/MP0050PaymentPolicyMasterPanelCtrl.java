package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 支払方法マスタ ダイアログコントロール
 */
public class MP0050PaymentPolicyMasterPanelCtrl extends TPanelCtrlBase {

	/** 支払方法マスタダイアログ */
	protected MP0050PaymentPolicyMasterPanel panel;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MP0050PaymentPolicyMasterServlet";

	/** プログラムID */
	private static final String PROGRAM_ID = "MP0050";

	REFDialogCtrl ref4;

	REFDialogCtrl ref5;

	/**
	 * コンストラクタ
	 */
	public MP0050PaymentPolicyMasterPanelCtrl() {
		try {

			panel = new MP0050PaymentPolicyMasterPanel(this);

			panel.numRemittanceCommissionLowerValue.getField().requestFocus();

			// 入力制御
			setPanelEnable();

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});

	}

	/**
	 * 入力制御
	 */
	public void setPanelEnable() {
		panel.rdoDate1DayBefore.setEnabled(false);
		panel.rdoDate1DayAfter.setEnabled(false);
		panel.rdoDate5DayBefore.setEnabled(false);
		panel.rdoDate5DayAfter.setEnabled(false);
		panel.rdoDate10DayBefore.setEnabled(false);
		panel.rdoDate10DayAfter.setEnabled(false);
		panel.rdoDate15DayBefore.setEnabled(false);
		panel.rdoDate15DayAfter.setEnabled(false);
		panel.rdoDate20DayBefore.setEnabled(false);
		panel.rdoDate20DayAfter.setEnabled(false);
		panel.rdoDate25DayBefore.setEnabled(false);
		panel.rdoDate25DayAfter.setEnabled(false);
		panel.rdoDateLastDayBefore.setEnabled(false);
		panel.rdoDateLastDayAfter.setEnabled(false);
		panel.rdoDate1DayBefore.setSelected(true);
		panel.rdoDate5DayBefore.setSelected(true);
		panel.rdoDate10DayBefore.setSelected(true);
		panel.rdoDate15DayBefore.setSelected(true);
		panel.rdoDate20DayBefore.setSelected(true);
		panel.rdoDate25DayBefore.setSelected(true);
		panel.rdoDateLastDayBefore.setSelected(true);
	}

	/**
	 * パネル初期化
	 */
	private void init() {

		// 計上部門コード
		ref4 = new REFDialogCtrl(panel.ctrlAppropriateDepartment, panel.getParentFrame());
		ref4.setColumnLabels("C00698", "C00724", "C00725");
		ref4.setTargetServlet("MG0060DepartmentMasterServlet");
		ref4.setTitleID("C02338");
		ref4.setShowsMsgOnError(false);
		ref4.addIgnoredButton(panel.btnCancellation);

		panel.ctrlAppropriateDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref4.refreshName();
				if (!Util.isNullOrEmpty(panel.ctrlAppropriateDepartment.getValue().trim())
					&& Util.isNullOrEmpty(panel.ctrlAppropriateDepartment.getNotice().getText().trim())) {
					showMessage(panel, "W00081", panel.ctrlAppropriateDepartment.getValue());
					panel.ctrlAppropriateDepartment.getField().clearOldText();
					panel.ctrlAppropriateDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		ref4.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Department");
				return keys;
			}
		});

		// 消費税コード
		ref5 = new REFDialogCtrl(panel.ctrlCommissionConsumptionTaxCode, panel.getParentFrame());
		ref5.setColumnLabels("C00573", "C00775", "C00828");
		ref5.setTargetServlet("MG0130ConsumptionTaxMasterServlet");
		ref5.setTitleID("C02324");
		ref5.setShowsMsgOnError(false);
		ref5.addIgnoredButton(panel.btnCancellation);

		panel.ctrlCommissionConsumptionTaxCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref5.refreshName();
				if (!Util.isNullOrEmpty(panel.ctrlCommissionConsumptionTaxCode.getValue().trim())
					&& Util.isNullOrEmpty(panel.ctrlCommissionConsumptionTaxCode.getNotice().getText().trim())) {
					showMessage(panel, "W00081", panel.ctrlCommissionConsumptionTaxCode.getValue());
					panel.ctrlCommissionConsumptionTaxCode.getField().clearOldText();
					panel.ctrlCommissionConsumptionTaxCode.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		ref5.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "PurchaseConsumptionTax");
				return keys;
			}
		});

		/**
		 * データを取得し
		 */
		getKaiName();

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				panel.chkDate1.requestFocus();
			}
		});

	}

	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * 表示
	 */
	void show() {
		panel.setVisible(true);
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {

		if (panel.isSettle) {
			if (!checkPanel()) return;

			saveShh();
		} else {
			// 確認メッセージを表示する.
			if (!super.showConfirmMessage(panel.getParentFrame(), "Q00002", (Object) "")) {
				return;
			}
		}
		// getKaiName();
	}

	/**
	 * 閉じる(取消)
	 */
	void disposeDialogCancel() {

		if (panel.isSettle) {
			if (!checkPanel()) return;

			saveShh();
		} else {
			// 確認メッセージを表示する.
			if (!super.showConfirmMessage(panel.getParentFrame(), "Q00002", (Object) "")) {
				return;
			}
		}
		getKaiName();

		panel.chkDate1.requestFocus();

	}

	/**
	 * 獲得して得る
	 */

	void saveShh() {
		// 確認メッセージを表示する.
		if (!super.showConfirmMessage(panel.getParentFrame(), "Q00004", (Object) "")) {
			return;
		}

		addSendValues("flag", "save");
		addSendValues("kaiCode", this.getLoginUserCompanyCode());
		addSendValues("usrCode", this.getLoginUserID());
		addSendValues("shhSiha1", String.valueOf(BooleanUtil.toInt(panel.chkDate1.isSelected())));
		addSendValues("shhSiha5", String.valueOf(BooleanUtil.toInt(panel.chkDate5.isSelected())));
		addSendValues("shhSiha10", String.valueOf(BooleanUtil.toInt(panel.chkDate10.isSelected())));
		addSendValues("shhSiha15", String.valueOf(BooleanUtil.toInt(panel.chkDate15.isSelected())));
		addSendValues("shhSiha20", String.valueOf(BooleanUtil.toInt(panel.chkDate20.isSelected())));
		addSendValues("shhSiha25", String.valueOf(BooleanUtil.toInt(panel.chkDate25.isSelected())));
		addSendValues("shhSiha30", String.valueOf(BooleanUtil.toInt(panel.chkDateLast.isSelected())));

		addSendValues("shhBnkKbn1", String.valueOf(BooleanUtil.toInt(panel.rdoDate1DayAfter.isSelected())));
		addSendValues("shhBnkKbn5", String.valueOf(BooleanUtil.toInt(panel.rdoDate5DayAfter.isSelected())));
		addSendValues("shhBnkKbn10", String.valueOf(BooleanUtil.toInt(panel.rdoDate10DayAfter.isSelected())));
		addSendValues("shhBnkKbn15", String.valueOf(BooleanUtil.toInt(panel.rdoDate15DayAfter.isSelected())));
		addSendValues("shhBnkKbn20", String.valueOf(BooleanUtil.toInt(panel.rdoDate20DayAfter.isSelected())));
		addSendValues("shhBnkKbn25", String.valueOf(BooleanUtil.toInt(panel.rdoDate25DayAfter.isSelected())));
		addSendValues("shhBnkKbn30", String.valueOf(BooleanUtil.toInt(panel.rdoDateLastDayAfter.isSelected())));

		String value = panel.numPaymentLowerValue.getField().getText().replace(",", "");
		addSendValues("shhSihaMin", value);
		value = panel.numRemittanceCommissionLowerValue.getField().getText().replace(",", "");
		addSendValues("shhFuriMin", value);
		addSendValues("shhTesuKmkCode", panel.ctrlItemUnit.getTItemField().getField().getText());
		addSendValues("shhTesuHkmCode", panel.ctrlItemUnit.getTSubItemField().getField().getText());
		addSendValues("shhTesuUkmCode", panel.ctrlItemUnit.getTBreakDownItemField().getField().getText());
		addSendValues("shhTesuDepCode", panel.ctrlAppropriateDepartment.getField().getText());
		addSendValues("shhTesuZeiCode", panel.ctrlCommissionConsumptionTaxCode.getField().getText());

		addSendValues("shhGsPrintKbn", String
			.valueOf(BooleanUtil.toInt(panel.chkAbroadRemittanceMakeFlag.isSelected())));
		addSendValues("shhSeiNoKbn", String.valueOf(BooleanUtil.toInt(panel.chkInvoiceNumberInputFlag.isSelected())));

		// プログラムＩＤの設定
		addSendValues("prgID", PROGRAM_ID);

		try {
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}
			super.showMessage(panel.getParentFrame(), "I00008");
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * データを獲得する.
	 */

	void getKaiName() {
		addSendValues("flag", "findone");
		addSendValues("kaiCode", super.getLoginUserCompanyCode());

		try {
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}

			List result = super.getResultList();
			if (result != null && result.size() > 0) {
				List inner = (List) result.get(0);

				// 値のセット
				setPanelValues(inner);

			}
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * 値のセット
	 * 
	 * @param inner Panel Value
	 */
	public void setPanelValues(List inner) {
		boolean booDate1, booDate5, booDate10, booDate15, booDate20, booDate25, booDateLast;

		booDate1 = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(1)));
		panel.chkDate1.setSelected(booDate1);
		booDate5 = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(2)));
		panel.chkDate5.setSelected(booDate5);
		booDate10 = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(3)));
		panel.chkDate10.setSelected(booDate10);
		booDate15 = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(4)));
		panel.chkDate15.setSelected(booDate15);
		booDate20 = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(5)));
		panel.chkDate20.setSelected(booDate20);
		booDate25 = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(6)));
		panel.chkDate25.setSelected(booDate25);
		booDateLast = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(7)));
		panel.chkDateLast.setSelected(booDateLast);

		boolean boo;
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(8)));
		panel.rdoDate1DayBefore.setSelected(!boo);
		panel.rdoDate1DayAfter.setSelected(boo);
		panel.rdoDate1DayBefore.setEnabled(booDate1);
		panel.rdoDate1DayAfter.setEnabled(booDate1);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(9)));
		panel.rdoDate5DayBefore.setSelected(!boo);
		panel.rdoDate5DayAfter.setSelected(boo);
		panel.rdoDate5DayBefore.setEnabled(booDate5);
		panel.rdoDate5DayAfter.setEnabled(booDate5);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(10)));
		panel.rdoDate10DayBefore.setSelected(!boo);
		panel.rdoDate10DayAfter.setSelected(boo);
		panel.rdoDate10DayBefore.setEnabled(booDate10);
		panel.rdoDate10DayAfter.setEnabled(booDate10);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(11)));
		panel.rdoDate15DayBefore.setSelected(!boo);
		panel.rdoDate15DayAfter.setSelected(boo);
		panel.rdoDate15DayBefore.setEnabled(booDate15);
		panel.rdoDate15DayAfter.setEnabled(booDate15);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(12)));
		panel.rdoDate20DayBefore.setSelected(!boo);
		panel.rdoDate20DayAfter.setSelected(boo);
		panel.rdoDate20DayBefore.setEnabled(booDate20);
		panel.rdoDate20DayAfter.setEnabled(booDate20);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(13)));
		panel.rdoDate25DayBefore.setSelected(!boo);
		panel.rdoDate25DayAfter.setSelected(boo);
		panel.rdoDate25DayBefore.setEnabled(booDate25);
		panel.rdoDate25DayAfter.setEnabled(booDate25);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(14)));
		panel.rdoDateLastDayBefore.setSelected(!boo);
		panel.rdoDateLastDayAfter.setSelected(boo);
		panel.rdoDateLastDayBefore.setEnabled(booDateLast);
		panel.rdoDateLastDayAfter.setEnabled(booDateLast);

		panel.numPaymentLowerValue.getField().setText((String) inner.get(15));
		panel.numRemittanceCommissionLowerValue.getField().setText((String) inner.get(16));

		String itemCode = Util.avoidNull(inner.get(17));
		String subItemCode = Util.avoidNull(inner.get(18));
		String BreakDownItemCode = Util.avoidNull(inner.get(19));

		// 科目パラメータ
		panel.ctrlItemUnit.getInputParameter().setSummaryDivision("0");
		panel.ctrlItemUnit.getInputParameter().setItemCode(itemCode);
		panel.ctrlItemUnit.getInputParameter().setSubItemCode(subItemCode);

		panel.ctrlItemUnit.getTItemField().clearOldText();
		panel.ctrlItemUnit.getTSubItemField().clearOldText();
		panel.ctrlItemUnit.getTBreakDownItemField().clearOldText();

		panel.ctrlItemUnit.setItemCode(itemCode);
		panel.ctrlItemUnit.setSubItemCode(subItemCode);
		panel.ctrlItemUnit.setBreakDownItemCode(BreakDownItemCode);

		panel.ctrlAppropriateDepartment.clearOldText();
		panel.ctrlAppropriateDepartment.getField().setText((String) inner.get(20));
		ref4.refreshName();

		panel.ctrlCommissionConsumptionTaxCode.clearOldText();
		panel.ctrlCommissionConsumptionTaxCode.getField().setText((String) inner.get(21));
		ref5.refreshName();

		boo = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(22)));
		panel.chkAbroadRemittanceMakeFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) inner.get(23)));
		panel.chkInvoiceNumberInputFlag.setSelected(boo);
	}

	boolean checkCode(String code) {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", getLoginUserCompanyCode());

		try {
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return false;
			}

			List result = super.getResultList();
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
			return false;
		}
	}

	/**
	 * 
	 */
	public void selectedSet1() {
		boolean boo = panel.chkDate1.isSelected();
		if (boo == true) {
			panel.rdoDate1DayBefore.setEnabled(true);
			panel.rdoDate1DayAfter.setEnabled(true);
		} else {
			panel.rdoDate1DayBefore.setEnabled(false);
			panel.rdoDate1DayAfter.setEnabled(false);
		}
	}

	/**
	 * 
	 */
	public void selectedSet5() {
		boolean boo = panel.chkDate5.isSelected();
		if (boo == true) {
			panel.rdoDate5DayBefore.setEnabled(true);
			panel.rdoDate5DayAfter.setEnabled(true);
		} else {
			panel.rdoDate5DayBefore.setEnabled(false);
			panel.rdoDate5DayAfter.setEnabled(false);
		}
	}

	/**
	 * 
	 */
	public void selectedSet10() {
		boolean boo = panel.chkDate10.isSelected();
		if (boo == true) {
			panel.rdoDate10DayBefore.setEnabled(true);
			panel.rdoDate10DayAfter.setEnabled(true);
		} else {
			panel.rdoDate10DayBefore.setEnabled(false);
			panel.rdoDate10DayAfter.setEnabled(false);
		}
	}

	/**
	 * 
	 */
	public void selectedSet15() {
		boolean boo = panel.chkDate15.isSelected();
		if (boo == true) {
			panel.rdoDate15DayBefore.setEnabled(true);
			panel.rdoDate15DayAfter.setEnabled(true);
		} else {
			panel.rdoDate15DayBefore.setEnabled(false);
			panel.rdoDate15DayAfter.setEnabled(false);
		}
	}

	/**
	 * 
	 */
	public void selectedSet20() {
		boolean boo = panel.chkDate20.isSelected();
		if (boo == true) {
			panel.rdoDate20DayBefore.setEnabled(true);
			panel.rdoDate20DayAfter.setEnabled(true);
		} else {
			panel.rdoDate20DayBefore.setEnabled(false);
			panel.rdoDate20DayAfter.setEnabled(false);
		}
	}

	/**
	 * 
	 */
	public void selectedSet25() {
		boolean boo = panel.chkDate25.isSelected();
		if (boo == true) {
			panel.rdoDate25DayBefore.setEnabled(true);
			panel.rdoDate25DayAfter.setEnabled(true);
		} else {
			panel.rdoDate25DayBefore.setEnabled(false);
			panel.rdoDate25DayAfter.setEnabled(false);
		}
	}

	/**
	 * 
	 */
	public void selectedSetLast() {
		boolean boo = panel.chkDateLast.isSelected();
		if (boo == true) {
			panel.rdoDateLastDayBefore.setEnabled(true);
			panel.rdoDateLastDayAfter.setEnabled(true);
		} else {
			panel.rdoDateLastDayBefore.setEnabled(false);
			panel.rdoDateLastDayAfter.setEnabled(false);
		}
	}

	/**
	 * @return boolean
	 */
	public boolean checkPanel() {

		String strNumValue = "";
		BigDecimal numValue;

		// 銀行口座コードチェック
		if (Util.isNullOrEmpty(panel.numRemittanceCommissionLowerValue.getValue())) {
			// エラーメッセージ出力
			super.showMessage(panel, "I00002", "C00891");
			panel.numRemittanceCommissionLowerValue.requestFocus();
			return false;
		}

		strNumValue = panel.numRemittanceCommissionLowerValue.getValue();
		if (!Util.isNullOrEmpty(strNumValue)) {
			numValue = new BigDecimal(strNumValue);
			if (((numValue.compareTo(new BigDecimal("0")) < 0 || numValue.compareTo(new BigDecimal("9999999999999")) > 0))) {
				// 警告メッセージ表示
				showMessage(panel, "W00065", "0", "9999999999999");
				panel.numRemittanceCommissionLowerValue.requestFocus();
				// エラーを返す
				return false;
			}
		}

		TItemField itemField = panel.ctrlItemUnit.getTItemField();// 科目フィールド
		// 科 目チェック
		if (Util.isNullOrEmpty(itemField.getValue())) {
			showMessage(panel, "I00002", itemField.getButtonText() + getWord("C00174"));
			itemField.requestTextFocus();
			return false;
		}

		TSubItemField subItemField = panel.ctrlItemUnit.getTSubItemField();// 補助科目フィールド
		// 補 助チェック
		if (subItemField.getField().isEditable() && Util.isNullOrEmpty(subItemField.getValue())) {
			showMessage(panel, "I00002", subItemField.getButtonText() + getWord("C00174"));
			subItemField.requestTextFocus();
			return false;
		}

		TBreakDownItemField breakDownItemField = panel.ctrlItemUnit.getTBreakDownItemField();// 内訳科目フィールド
		// 内 訳チェック
		if (breakDownItemField.getField().isEditable() && Util.isNullOrEmpty(breakDownItemField.getValue())) {
			showMessage(panel, "I00002", breakDownItemField.getButtonText() + getWord("C00174"));
			breakDownItemField.requestTextFocus();
			return false;
		}

		// 計上部門チェック
		if (Util.isNullOrEmpty(panel.ctrlAppropriateDepartment.getValue())) {
			showMessage(panel, "I00002", "C00571");
			panel.ctrlAppropriateDepartment.requestTextFocus();
			return false;
		}

		// 手数料消費税コードチェック
		if (Util.isNullOrEmpty(panel.ctrlCommissionConsumptionTaxCode.getValue())) {
			showMessage(panel, "I00002", "C00889");
			panel.ctrlCommissionConsumptionTaxCode.requestTextFocus();
			return false;
		}

		// 支払下限額チェック
		if (Util.isNullOrEmpty(panel.numPaymentLowerValue.getValue())) {
			showMessage(panel, "I00002", "C00888");
			panel.numPaymentLowerValue.requestFocus();
			return false;
		}

		strNumValue = panel.numPaymentLowerValue.getValue();
		if (!Util.isNullOrEmpty(strNumValue)) {
			numValue = new BigDecimal(strNumValue);
			if (((numValue.compareTo(new BigDecimal("-9999999999999")) < 0 || numValue.compareTo(new BigDecimal(
				"9999999999999")) == 1))) {
				// 警告メッセージ表示
				showMessage(panel, "W00065", "-9,999,999,999,999", "9,999,999,999,999");
				panel.numPaymentLowerValue.requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 成功を返
		return true;

	}
}

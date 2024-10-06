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
 * ��ЃR���g���[���}�X�^DialogCtrl
 * 
 * @author yanwei
 */
public class MG0031EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ��ЃR���g���[���}�X�^�_�C�A���O */
	protected MG0031EditDisplayDialog dialog;

	private REFDialogCtrl refCurName;

	/** �����敪 */

	private boolean isUpdate;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0031EditDisplayDialogCtrl(Frame parent, String titleid) {
		// ��ЃR���g���[���}�X�^�_�C�A���O�̏�����
		dialog = new MG0031EditDisplayDialog(parent, true, this, titleid);
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		// ���[�g���Z�[�������̏�����
		dialog.rdoRateConversionRoundDown.setSelected(true);
		// �������Œ[�������̏�����
		dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.setSelected(true);
		// ��������Œ[�������̏�����
		dialog.rdoTemporaryPaymentConsumptionTaxRoundDown.setSelected(true);

		dialog.ctrlItemName.getField().requestFocus();
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				checkClicked();
				dialog.ctrlNotAccountingDetailDivision1.getComboBox().addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ev) {
						checkClicked();
					}
				});
				dialog.ctrlNotAccountingDetailDivision2.getComboBox().addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ev) {
						checkClicked();
					}
				});
				dialog.ctrlNotAccountingDetailDivision3.getComboBox().addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ev) {
						checkClicked();
					}
				});
			}
		});

		// �Ȗڃ_�C�A���O��\��

		refCurName = new REFDialogCtrl(dialog.ctrlKeyCurrency, dialog.getParentFrame());
		refCurName.setTargetServlet("MG0300CurrencyMasterServlet");
		refCurName.setTitleID(getWord("C01985"));
		refCurName.setColumnLabelIDs("C00665", "C00881", "C00882");
		refCurName.setShowsMsgOnError(false);
		refCurName.addIgnoredButton(dialog.btnReturn);
		refCurName.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��ЃR�[�h�̐ݒ�

				keys.put("kind", "Currency");
				keys.put("kaiCode", getLoginUserCompanyCode());
				return keys;
			}
		});

		dialog.ctrlKeyCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCurName.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlKeyCurrency.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlKeyCurrency.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlKeyCurrency.getValue());
					dialog.ctrlKeyCurrency.getField().clearOldText();
					dialog.ctrlKeyCurrency.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

	}

	/**
	 * @param cmpHmKbnMap
	 * @param jidMap
	 */
	public void setSelectMap(Map cmpHmKbnMap, Map jidMap) {
		// ���v���׋敪1-3�̏�����
		this.fillItemsToComboBox(dialog.ctrlNotAccountingDetailDivision1.getComboBox(), cmpHmKbnMap);
		this.fillItemsToComboBox(dialog.ctrlNotAccountingDetailDivision2.getComboBox(), cmpHmKbnMap);
		this.fillItemsToComboBox(dialog.ctrlNotAccountingDetailDivision3.getComboBox(), cmpHmKbnMap);
		// �����ݒ荀�ڂP-3�̏�����
		this.fillItemsToComboBox(dialog.ctrlAutomaticSettingItem1.getComboBox(), jidMap, false);
		this.fillItemsToComboBox(dialog.ctrlAutomaticSettingItem2.getComboBox(), jidMap, false);
		this.fillItemsToComboBox(dialog.ctrlAutomaticSettingItem3.getComboBox(), jidMap, false);
	}

	/**
	 * �\��
	 */
	void show() {
		// ��ʂ�\������
		dialog.setVisible(true);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */

	void setValues(Map map) {
		boolean boo;
		// ��ЃR�[�h�̐ݒ�
		dialog.ctrlCompanyCode.setEnabled(true);
		dialog.ctrlCompanyCode.getField().setEditable(true);
		dialog.ctrlCompanyCode.setValue((String) map.get("kaiCode"));
		dialog.ctrlCompanyCode.getField().setEditable(false);
		// ��Ж�
		getKaiName((String) map.get("kaiCode"));

		// �Ȗږ���
		dialog.ctrlItemName.setValue((String) map.get("cmpKmkName"));
		// �⏕�Ȗږ���
		dialog.ctrlSubItemName.setValue((String) map.get("cmpHkmName"));
		// ����ȖڊǗ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cmpUkmKbn")));
		dialog.chkBreakDownItemManagement.setSelected(boo);
		// ����Ȗږ���
		dialog.txtBreakDownItemManagement.setValue(map.get("cmpUkmName"));
		// �Ǘ��敪1-6
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn1")));
		dialog.chkManagementDivision1.setSelected(boo);
		dialog.txtManagementDivision1.setValue(map.get("knrName1"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn2")));
		dialog.chkManagementDivision2.setSelected(boo);
		dialog.txtManagementDivision2.setValue(map.get("knrName2"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn3")));
		dialog.chkManagementDivision3.setSelected(boo);
		dialog.txtManagementDivision3.setValue(map.get("knrName3"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn4")));
		dialog.chkManagementDivision4.setSelected(boo);
		dialog.txtManagementDivision4.setValue(map.get("knrName4"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn5")));
		dialog.chkManagementDivision5.setSelected(boo);
		dialog.txtManagementDivision5.setValue(map.get("knrName5"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn6")));
		dialog.chkManagementDivision6.setSelected(boo);
		dialog.txtManagementDivision6.setValue(map.get("knrName6"));
		// ���v���ז���1-3
		this.setComboBoxSelectedItem(dialog.ctrlNotAccountingDetailDivision1.getComboBox(), (String) map
			.get("cmpHmKbn1"));
		dialog.txtNotAccountingDetailDivision1.setValue(map.get("cmpHmName1"));
		this.setComboBoxSelectedItem(dialog.ctrlNotAccountingDetailDivision2.getComboBox(), (String) map
			.get("cmpHmKbn2"));
		dialog.txtNotAccountingDetailDivision2.setValue(map.get("cmpHmName2"));
		this.setComboBoxSelectedItem(dialog.ctrlNotAccountingDetailDivision3.getComboBox(), (String) map
			.get("cmpHmKbn3"));
		dialog.txtNotAccountingDetailDivision3.setValue(map.get("cmpHmName3"));
		// ����
		dialog.numBeginningOfPeriodMonth.setValue((String) map.get("cmpKisyu"));
		// �����ݒ荀�ڂP-3
		this.setComboBoxSelectedItem(dialog.ctrlAutomaticSettingItem1.getComboBox(), (String) map.get("jid1"));
		if (map.get("jid1").equals("")) {
			dialog.ctrlAutomaticSettingItem1.getComboBox().setSelectedIndex(0);
		}
		this.setComboBoxSelectedItem(dialog.ctrlAutomaticSettingItem2.getComboBox(), (String) map.get("jid2"));
		if (map.get("jid2").equals("")) {
			dialog.ctrlAutomaticSettingItem2.getComboBox().setSelectedIndex(0);
		}
		this.setComboBoxSelectedItem(dialog.ctrlAutomaticSettingItem3.getComboBox(), (String) map.get("jid3"));
		if (map.get("jid3").equals("")) {
			dialog.ctrlAutomaticSettingItem3.getComboBox().setSelectedIndex(0);
		}
		// �����̔ԕ�����
		dialog.numAutomaticNumberingDigits.setValue((String) map.get("autoNoKeta"));
		// �`�[����敪
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("printKbn")));
		dialog.chkSlipPrintDivision.setSelected(boo);
		// ������̏����l
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("printDef")));
		dialog.chkPrintTimeInitialValue.setSelected(boo);
		// ���ꏳ�F�׸�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cmpGShoninFlg")));
		dialog.chkSiteApproval.setSelected(boo);
		// �o�����F�׸�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cmpKShoninFlg")));
		dialog.chkAdministrationApproval.setSelected(boo);
		// �{�M�ʉ݃R�[�h
		dialog.ctrlKeyCurrency.setValue((String) map.get("curCode"));

		String value;
		// ���[�g���Z�[������
		value = (String) map.get("rateKbn");
		if ("0".equals(value)) {
			dialog.rdoRateConversionRoundDown.setSelected(true);
		} else {
			dialog.rdoRateConversionHalfAdjust.setSelected(true);
		}

		// �������Œ[������
		value = (String) map.get("kuKbn");
		if ("0".equals(value)) {
			dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.setSelected(true);
			dialog.rdoTemporaryReceivingConsumptionRoundUp.setSelected(false);
			dialog.rdoTemporaryReceivingConsumptionTaxHalfAdjust.setSelected(false);
		} else if ("1".equals(value)) {
			dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.setSelected(false);
			dialog.rdoTemporaryReceivingConsumptionRoundUp.setSelected(true);
			dialog.rdoTemporaryReceivingConsumptionTaxHalfAdjust.setSelected(false);
		} else {
			dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.setSelected(false);
			dialog.rdoTemporaryReceivingConsumptionRoundUp.setSelected(false);
			dialog.rdoTemporaryReceivingConsumptionTaxHalfAdjust.setSelected(true);
		}
		// �������Œ[������
		value = (String) map.get("kbKbn");
		if ("0".equals(value)) {
			dialog.rdoTemporaryPaymentConsumptionTaxRoundDown.setSelected(true);
		} else {
			dialog.rdoTemporaryPaymentConsumptionTaxHalfAdjust.setSelected(true);
		}

		// ���ڈ���敪
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("directKbn")));
		dialog.chkDirectPrintDivision.setSelected(boo);

		// �ҏW���[�h�̂Ƃ��͓E�v�R�[�h�A�E�v�敪�Ƃ��ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));

		refCurName.refreshName();
		if (isUpdate) {
			dialog.ctrlItemName.getField().requestFocus();
		}

	}

	boolean checkDialog() {
		// �Ȗږ���
		if (Util.isNullOrEmpty(dialog.ctrlItemName.getValue())) {
			showMessage(dialog, "I00002", "C00700");
			dialog.ctrlItemName.requestFocus();
			return false;
		}

		// �⏕�Ȗږ���
		if (Util.isNullOrEmpty(dialog.ctrlSubItemName.getValue())) {
			showMessage(dialog, "I00002", "C00701");
			dialog.ctrlSubItemName.requestFocus();
			return false;
		}
		// ����Ȗږ���
		if (dialog.chkBreakDownItemManagement.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtBreakDownItemManagement.getText())) {
			showMessage(dialog, "I00002", "C00702");
			dialog.txtBreakDownItemManagement.requestFocus();
			return false;
		}

		// �Ǘ����̂P
		if (dialog.chkManagementDivision1.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision1.getText())) {
			showMessage(dialog, "I00002", "C00703");
			dialog.txtManagementDivision1.requestFocus();
			return false;
		}

		// �Ǘ�����2
		if (dialog.chkManagementDivision2.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision2.getText())) {
			showMessage(dialog, "I00002", "C00704");
			dialog.txtManagementDivision2.requestFocus();
			return false;
		}

		// �Ǘ�����3
		if (dialog.chkManagementDivision3.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision3.getText())) {
			showMessage(dialog, "I00002", "C00705");
			dialog.txtManagementDivision3.requestFocus();
			return false;
		}
		// �Ǘ�����4
		if (dialog.chkManagementDivision4.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision4.getText())) {
			showMessage(dialog, "I00002", "C00706");
			dialog.txtManagementDivision4.requestFocus();
			return false;
		}
		// �Ǘ�����5
		if (dialog.chkManagementDivision5.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision5.getText())) {
			showMessage(dialog, "I00002", "C00707");
			dialog.txtManagementDivision5.requestFocus();
			return false;
		}

		// �Ǘ�����6
		if (dialog.chkManagementDivision6.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision6.getText())) {
			showMessage(dialog, "I00002", "C00708");
			dialog.txtManagementDivision6.requestFocus();
			return false;
		}

		// ���v���׋敪1
		if (Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision1.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C01294");
			dialog.ctrlNotAccountingDetailDivision1.requestFocus();
			return false;
		}

		// ���v���׋敪2
		if (Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision2.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C00944");
			dialog.ctrlNotAccountingDetailDivision2.requestFocus();
			return false;
		}

		// ���v���׋敪�R
		if (Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision3.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C00945");
			dialog.ctrlNotAccountingDetailDivision3.requestFocus();
			return false;
		}
		// ���v���ז���1
		if (!"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision1.getComboBox()))) {
			if (Util.isNullOrEmpty(dialog.txtNotAccountingDetailDivision1.getText())) {
				showMessage(dialog, "I00002", "C00709");
				dialog.txtNotAccountingDetailDivision1.requestFocus();
				return false;
			}
		}

		// ���v���ז��̂Q
		if (!"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision2.getComboBox()))) {
			if (Util.isNullOrEmpty(dialog.txtNotAccountingDetailDivision2.getText())) {
				showMessage(dialog, "I00002", "C00710");
				dialog.txtNotAccountingDetailDivision2.requestFocus();
				return false;
			}
		}

		// ���v���ז���3
		if (!"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision3.getComboBox()))) {
			if (Util.isNullOrEmpty(dialog.txtNotAccountingDetailDivision3.getText())) {
				showMessage(dialog, "I00002", "C00711");
				dialog.txtNotAccountingDetailDivision3.requestFocus();
				return false;
			}
		}
		// �����̔ԕ�����
		if (Util.isNullOrEmpty(dialog.numAutomaticNumberingDigits.getValue())) {
			showMessage(dialog, "I00002", "C00224");
			dialog.numAutomaticNumberingDigits.requestFocus();
			return false;
		}

		String strNum = dialog.numAutomaticNumberingDigits.getField().getText();
		if (!Util.isNullOrEmpty(strNum) && (20 < Integer.parseInt(strNum) || Integer.parseInt(strNum) < 1)) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "W00065", 1, 20);
			dialog.numAutomaticNumberingDigits.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// ����
		if (Util.isNullOrEmpty(dialog.numBeginningOfPeriodMonth.getValue())) {
			showMessage(dialog, "I00002", "C00105");
			dialog.numBeginningOfPeriodMonth.requestFocus();
			return false;
		}

		String strNumw = dialog.numBeginningOfPeriodMonth.getField().getText();
		if (!Util.isNullOrEmpty(strNumw) && (12 < Integer.parseInt(strNumw) || Integer.parseInt(strNumw) < 1)) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "W00065", 1, 12);
			dialog.numBeginningOfPeriodMonth.requestFocus();
			// �G���[��Ԃ�
			return false;
		}
		// ���ꏳ�F
		if (!dialog.chkAdministrationApproval.isSelected() && dialog.chkSiteApproval.isSelected()) {
			showMessage(dialog, "W00206");
			dialog.chkAdministrationApproval.requestFocus();
			return false;

		}
		// �{�M�ʉ݃R�[�h
		if (Util.isNullOrEmpty(dialog.ctrlKeyCurrency.getField().getText())) {
			showMessage(dialog, "I00002", "C00717");
			dialog.ctrlKeyCurrency.requestTextFocus();
			return false;
		}

		return true;

	}

	/**
	 * ����
	 */
	void disposeDialog() {
		// �G���[������ꍇ�ɂ̓_�C�A���O����Ȃ�
		if (dialog.isSettle) {

			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} else {
			dialog.setVisible(false);
		}
	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	boolean isSettle() {
		// ������ʂ̐ݒ�
		return dialog.isSettle;
	}

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */

	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", dialog.ctrlCompanyCode.getValue());
		// �Ȗږ���
		map.put("cmpKmkName", dialog.ctrlItemName.getValue());
		// �⏕�Ȗږ���
		map.put("cmpHkmName", dialog.ctrlSubItemName.getValue());
		// ����ȖڊǗ�
		map.put("cmpUkmKbn", String.valueOf(BooleanUtil.toInt(dialog.chkBreakDownItemManagement.isSelected())));
		// ����Ȗږ���
		map.put("cmpUkmName", dialog.txtBreakDownItemManagement.getValue());
		// �Ǘ��敪1-6
		map.put("knrKbn1", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision1.isSelected())));
		map.put("knrKbn2", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision2.isSelected())));
		map.put("knrKbn3", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision3.isSelected())));
		map.put("knrKbn4", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision4.isSelected())));
		map.put("knrKbn5", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision5.isSelected())));
		map.put("knrKbn6", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision6.isSelected())));
		// �Ǘ�����1-6
		map.put("knrName1", dialog.txtManagementDivision1.getText());
		map.put("knrName2", dialog.txtManagementDivision2.getText());
		map.put("knrName3", dialog.txtManagementDivision3.getText());
		map.put("knrName4", dialog.txtManagementDivision4.getText());
		map.put("knrName5", dialog.txtManagementDivision5.getText());
		map.put("knrName6", dialog.txtManagementDivision6.getText());
		// �Ǘ�����1-3
		map.put("cmpHmKbn1", this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision1.getComboBox()));
		map.put("cmpHmKbn2", this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision2.getComboBox()));
		map.put("cmpHmKbn3", this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision3.getComboBox()));
		// ���v���ז���1-3
		map.put("cmpHmName1", dialog.txtNotAccountingDetailDivision1.getText());
		map.put("cmpHmName2", dialog.txtNotAccountingDetailDivision2.getText());
		map.put("cmpHmName3", dialog.txtNotAccountingDetailDivision3.getText());
		// ����
		map.put("cmpKisyu", dialog.numBeginningOfPeriodMonth.getField().getText());
		// �����ݒ荀�ڂP-3
		map.put("jid1", this.getComboBoxSelectedValue(dialog.ctrlAutomaticSettingItem1.getComboBox()));
		map.put("jid2", this.getComboBoxSelectedValue(dialog.ctrlAutomaticSettingItem2.getComboBox()));
		map.put("jid3", this.getComboBoxSelectedValue(dialog.ctrlAutomaticSettingItem3.getComboBox()));
		// �����̔ԕ�����
		map.put("autoNoKeta", dialog.numAutomaticNumberingDigits.getField().getText());
		// �`�[����敪
		map.put("printKbn", String.valueOf(BooleanUtil.toInt(dialog.chkSlipPrintDivision.isSelected())));
		// ������̏����l
		map.put("printDef", String.valueOf(BooleanUtil.toInt(dialog.chkPrintTimeInitialValue.isSelected())));
		// ���ꏳ�F�׸�
		map.put("cmpGShoninFlg", String.valueOf(BooleanUtil.toInt(dialog.chkSiteApproval.isSelected())));
		// �o�����F�׸�
		map.put("cmpKShoninFlg", String.valueOf(BooleanUtil.toInt(dialog.chkAdministrationApproval.isSelected())));
		// �{�M�ʉ݃R�[�h
		map.put("curCode", dialog.ctrlKeyCurrency.getValue());

		// ���[�g���Z�[������
		if (dialog.rdoRateConversionRoundDown.isSelected()) {
			map.put("rateKbn", "0");
		} else {
			map.put("rateKbn", "2");
		}
		// �������Œ[������
		if (dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.isSelected()) {
			map.put("kuKbn", "0");
		} else if (dialog.rdoTemporaryReceivingConsumptionRoundUp.isSelected()) {
			map.put("kuKbn", "1");
		} else {
			map.put("kuKbn", "2");
		}
		// ��������Œ[������
		if (dialog.rdoTemporaryPaymentConsumptionTaxRoundDown.isSelected()) {
			map.put("kbKbn", "0");
		} else {
			map.put("kbKbn", "2");
		}

		// ���ڈ���敪
		map.put("directKbn", String.valueOf(BooleanUtil.toInt(dialog.chkDirectPrintDivision.isSelected())));

		// ���ʂ�Ԃ�
		return map;
	}

	void getKaiName(String kaiCode) {
		try {
			addSendValues("flag", "findone");
			addSendValues("kaiCode", kaiCode);

			if (!request("MG0010EnvironmentalSettingMasterServlet")) {
				// �T�[�o���̃G���[�ꍇ
				errorHandler(dialog);
			}

			List result = getResultList();
			if (result != null && result.size() > 0) {
				// ��З���
				String name_S = (String) ((List) result.get(0)).get(2);
				dialog.ctrlCompany.setText(name_S);
			}
		} catch (IOException e) {
			errorHandler(dialog);
		}
	}

	/**
	 * 
	 */
	public void checkClicked() {
		// �Ǘ��敪1-6
		dialog.txtBreakDownItemManagement.setEditable(dialog.chkBreakDownItemManagement.isSelected());
		dialog.txtManagementDivision1.setEditable(dialog.chkManagementDivision1.isSelected());
		dialog.txtManagementDivision2.setEditable(dialog.chkManagementDivision2.isSelected());
		dialog.txtManagementDivision3.setEditable(dialog.chkManagementDivision3.isSelected());
		dialog.txtManagementDivision4.setEditable(dialog.chkManagementDivision4.isSelected());
		dialog.txtManagementDivision5.setEditable(dialog.chkManagementDivision5.isSelected());
		dialog.txtManagementDivision6.setEditable(dialog.chkManagementDivision6.isSelected());

		// �Ǘ�����1-6
		if (!dialog.chkBreakDownItemManagement.isSelected()) {
			dialog.txtBreakDownItemManagement.setValue("");
		}

		if (!dialog.chkManagementDivision1.isSelected()) {
			dialog.txtManagementDivision1.setValue("");
		}

		if (!dialog.chkManagementDivision2.isSelected()) {
			dialog.txtManagementDivision2.setValue("");
		}

		if (!dialog.chkManagementDivision3.isSelected()) {
			dialog.txtManagementDivision3.setValue("");
		}

		if (!dialog.chkManagementDivision4.isSelected()) {
			dialog.txtManagementDivision4.setValue("");
		}

		if (!dialog.chkManagementDivision5.isSelected()) {
			dialog.txtManagementDivision5.setValue("");
		}

		if (!dialog.chkManagementDivision6.isSelected()) {
			dialog.txtManagementDivision6.setValue("");
		}

		// ���v���ז���1-3
		if (!Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision1.getComboBox().getSelectedItem().toString())
			&& !"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision1.getComboBox()))) {
			dialog.txtNotAccountingDetailDivision1.setEditable(true);
		} else {
			dialog.txtNotAccountingDetailDivision1.setValue("");
			dialog.txtNotAccountingDetailDivision1.setEditable(false);
		}

		if (!Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision2.getComboBox().getSelectedItem().toString())
			&& !"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision2.getComboBox()))) {
			dialog.txtNotAccountingDetailDivision2.setEditable(true);
		} else {
			dialog.txtNotAccountingDetailDivision2.setValue("");
			dialog.txtNotAccountingDetailDivision2.setEditable(false);
		}

		if (!Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision3.getComboBox().getSelectedItem().toString())
			&& !"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision3.getComboBox()))) {
			dialog.txtNotAccountingDetailDivision3.setEditable(true);
		} else {
			dialog.txtNotAccountingDetailDivision3.setValue("");
			dialog.txtNotAccountingDetailDivision3.setEditable(false);
		}
	}
}
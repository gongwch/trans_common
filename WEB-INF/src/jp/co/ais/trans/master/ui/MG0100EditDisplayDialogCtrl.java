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
 * ����Ȗڃ}�X�^�ҏW��ʃR���g���[��
 */
public class MG0100EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ����Ȗڃ}�X�^�_�C�A���O */
	protected MG0100EditDisplayDialog dialog;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0100BreakDownItemMasterServlet";

	/** �����敪 */
	protected boolean isUpdate = false;

	private Map triCodeFlgMap;

	REFDialogCtrl refKmkCode;

	REFDialogCtrl refHkmCode;

	REFDialogCtrl refZeiCode;

	/**
	 * �R���X�g���N�^
	 */
	MG0100EditDisplayDialogCtrl() {
		// �����Ȃ�
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0100EditDisplayDialogCtrl(Frame parent, String titleid) {
		// �⏕�Ȗڃ}�X�^�_�C�A���O�̏�����
		dialog = new MG0100EditDisplayDialog(parent, true, this, titleid);
		// �^�C�g����ݒ肷��
		dialog.ctrlItem.getField().requestFocus();
		dialog.ctrlItem.getField().setEnabled(true);

		CompanyControlHelper10 cc = CompanyControlHelper10.getInstance(this.getLoginUserCompanyCode());

		// �Ǘ��P-6���̓t���O������̎w�肳�ꂽ���̂�\������B
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

		// ���v1-3���̓t���O������̎w�肳�ꂽ���̂�\������B
		String hmName;
		hmName = cc.getCmpHmName1() + this.getWord("C02386");
		dialog.chkNotAccounting1InputFlag.setText(hmName);

		hmName = cc.getCmpHmName2() + this.getWord("C02386");
		dialog.chkNotAccounting2InputFlag.setText(hmName);

		hmName = cc.getCmpHmName3() + this.getWord("C02386");
		dialog.chkNotAccounting3InputFlag.setText(hmName);

		// �ȖڃR�[�h�̏�����
		dialog.ctrlSubItem.getField().setEditable(false);
		dialog.ctrlSubItem.getButton().setEnabled(false);
		init();
		// �_�C�A���O������Adialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));

	}

	protected void init() {

		// ����ŃR�[�h�`�F�b�N

		refZeiCode = new REFDialogCtrl(dialog.ctrlConsumptionTax, dialog.getParentFrame());
		refZeiCode.setTargetServlet("MG0130ConsumptionTaxMasterServlet");
		refZeiCode.setTitleID(getWord("C02324"));
		refZeiCode.setColumnLabelIDs("C00573", "C00775", "C00828");
		refZeiCode.setShowsMsgOnError(false);
		refZeiCode.addIgnoredButton(dialog.btnReturn);
		refZeiCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// ��ЃR�[�h�̐ݒ�

				keys.put("kind", "ConsumptionTax");
				keys.put("kaiCode", getLoginUserCompanyCode());

				return keys;
			}
		});

		dialog.ctrlConsumptionTax.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refZeiCode.refreshName();
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

	}

	/**
	 * (�Ȗ�)
	 */
	public void setItemData() {
		String itemCode = Util.avoidNull(dialog.ctrlItem.getValue());
		dialog.ctrlItem.getInputParameter().setSubItemDivision("1");
		dialog.ctrlItem.getInputParameter().setItemCode(itemCode);
	}

	/**
	 * (�⏕�Ȗ�)
	 */
	public void setSubItemData() {
		// ��ЃR�[�h
		dialog.ctrlSubItem.getInputParameter().setItemCode(dialog.ctrlItem.getValue());
		String subItemCode = Util.avoidNull(dialog.ctrlSubItem.getValue());
		dialog.ctrlSubItem.getInputParameter().setBreakDownItemDivision("1");
		dialog.ctrlSubItem.getInputParameter().setSubItemCode(subItemCode);
	}

	/**
	 * @param map
	 */
	// map�̏�����
	public void setTriCodeFlgMap(Map map) {
		this.triCodeFlgMap = map;

		this.fillItemsToComboBox(dialog.ctrlCustomerInputFlag.getComboBox(), triCodeFlgMap, false);
	}

	/**
	 * �\��
	 */
	void show() {
		dialog.setVisible(true);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */

	void setValues(Map map) {

		boolean boo;
		// �ȖڃR�[�h�̐ݒ�
		String itemCode = (String) map.get("kmkCode");
		dialog.ctrlItem.getInputParameter().setItemCode(itemCode);
		dialog.ctrlItem.setValue(itemCode);
		dialog.ctrlItem.getField().pushOldText();
		// �⏕�ȖڃR�[�h�̐ݒ�
		dialog.ctrlSubItem.getInputParameter().setItemCode(itemCode);
		String subItemCode = (String) map.get("hkmCode");
		dialog.ctrlSubItem.getInputParameter().setSubItemCode(subItemCode);
		dialog.ctrlSubItem.setValue(subItemCode);

		// ����ȖڃR�[�h�̐ݒ�
		dialog.ctrlBreakDownItemCode.setValue((String) map.get("ukmCode"));
		// ����Ȗږ��̂̐ݒ�
		dialog.ctrlBreakDownItemName.setValue((String) map.get("ukmName"));
		// ����Ȗڗ��̂̐ݒ�
		dialog.ctrlBreakDownItemAbbreviationName.setValue((String) map.get("ukmName_S"));
		// ����Ȗڌ������̂̐ݒ�
		dialog.ctrlBreakDownItemNameForSearch.setValue((String) map.get("ukmName_K"));
		// ����ŃR�[�h�̐ݒ�
		dialog.ctrlConsumptionTax.setValue((String) map.get("zeiCode"));
		// �������̓t���O�̐ݒ�
		this.setComboBoxSelectedItem(dialog.ctrlCustomerInputFlag.getComboBox(), (String) map.get("triCodeFlg"));
		// �����`�[�����׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("glFlg1")));
		dialog.chkReceivingSlipInputFlag.setSelected(boo);
		// �o���`�[�����׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("glFlg2")));
		dialog.chkPaymentSlipInputFlag.setSelected(boo);
		// �U�֓`�[�����׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("glFlg3")));
		dialog.chkTransferSlipInputFlag.setSelected(boo);
		// �o��Z�`�[�����׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("apFlg1")));
		dialog.chkExpenseInputFlag.setSelected(boo);
		// �������`�[�����׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("apFlg2")));
		dialog.chkAccountsPayableAppropriateSlipInputFlag.setSelected(boo);
		// ���v��`�[�����׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("arFlg1")));
		dialog.chkAccountsReceivableAppropriateSlipInputFlag.setSelected(boo);
		// �������`�[�����׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("arFlg2")));
		dialog.chkAccountsReceivableErasingSlipInputFlag.setSelected(boo);
		// ���Y�v��`�[�����׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("faFlg1")));
		dialog.chkAssetsAppropriateSlipInputFlag.setSelected(boo);
		// �x���˗��`�[�����׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("faFlg2")));
		dialog.chkPaymentRequestSlipInputFlag.setSelected(boo);
		// �����������׸ނ̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hasFlg")));
		dialog.chkOccurrenceDateInputFlag.setSelected(boo);
		// �Ј����̓t���O�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("empCodeFlg")));
		dialog.chkEmployeeInputFlag.setSelected(boo);
		// �Ǘ��P-6���̓t���O�̐ݒ�
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
		// ���v1-3���̓t���O�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hmFlg1")));
		dialog.chkNotAccounting1InputFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hmFlg2")));
		dialog.chkNotAccounting2InputFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hmFlg3")));
		dialog.chkNotAccounting3InputFlag.setSelected(boo);
		// ����ېœ��̓t���O�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("uriZeiFlg")));
		dialog.chkSalesTaxInputFlag.setSelected(boo);
		// �d���ېœ��̓t���O�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("sirZeiFlg")));
		dialog.chkPurchesesTaxInputFlag.setSelected(boo);
		// ���ʉݓ��̓t���O�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("mcrFlg")));
		dialog.chkMultiCurrencyInputFlag.setSelected(boo);
		// �]���֑Ώۃt���O�̐ݒ�
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("excFlg")));
		dialog.ctrlRevaluationObjectFlag.setSelected(boo);
		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// refKmkCode.refreshName();
		// refHkmCode.refreshName();
		refZeiCode.refreshName();
		// �ҏW���[�h�̂Ƃ��͓E�v�R�[�h�A�E�v�敪�Ƃ��ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		// �ȖڃR�[�h�̐ݒ�

		dialog.ctrlItem.getField().setEnabled(true);
		dialog.ctrlItem.getField().setEditable(!isUpdate);
		dialog.ctrlItem.getButton().setEnabled(!isUpdate);

		dialog.ctrlSubItem.getField().setEnabled(true);
		dialog.ctrlSubItem.getField().setEditable(false);
		dialog.ctrlSubItem.getButton().setEnabled(false);

		if (!isUpdate) {
			if (dialog.ctrlSubItem.getField() != null) {
				dialog.ctrlSubItem.getField().setEnabled(true);
				dialog.ctrlSubItem.getField().setEditable(true);
				dialog.ctrlSubItem.getButton().setEnabled(true);
			}
		}
		dialog.ctrlBreakDownItemCode.getField().setEnabled(true);
		dialog.ctrlBreakDownItemCode.getField().setEditable(!isUpdate);

		if (isUpdate) {
			dialog.ctrlBreakDownItemName.getField().requestFocus();
		}

	}

	/**
	 * ����
	 * 
	 * @return true:OK
	 * @throws IOException
	 */
	boolean checkDialog() throws IOException {
		// �ȖڃR�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlItem.getValue())) {
			showMessage(dialog, "I00002", "C00077");
			dialog.ctrlItem.requestTextFocus();
			return false;
		}

		if (!Util.isNullOrEmpty(dialog.ctrlItem.getValue())) {
			if (!isUpdate
				&& checkCode(dialog.ctrlItem.getValue(), dialog.ctrlSubItem.getValue(), dialog.ctrlBreakDownItemCode
					.getValue())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlBreakDownItemCode.requestTextFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// �⏕�ȖڃR�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlSubItem.getValue())) {
			showMessage(dialog, "I00002", "C00487");
			dialog.ctrlSubItem.requestTextFocus();
			return false;
		}

		// ����ȖڃR�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlBreakDownItemCode.getValue())) {
			showMessage(dialog, "I00002", "C00876");
			dialog.ctrlBreakDownItemCode.requestFocus();
			return false;
		}

		// ����Ȗږ��̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlBreakDownItemName.getValue())) {
			showMessage(dialog, "I00002", "C00877");
			dialog.ctrlBreakDownItemName.requestFocus();
			return false;
		}

		// ����Ȗڗ��̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlBreakDownItemAbbreviationName.getValue())) {
			showMessage(dialog, "I00002", "C00878");
			dialog.ctrlBreakDownItemAbbreviationName.requestFocus();
			return false;
		}

		// ����Ȗڌ������̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlBreakDownItemNameForSearch.getValue())) {
			showMessage(dialog, "I00002", "C00879");
			dialog.ctrlBreakDownItemNameForSearch.requestFocus();
			return false;
		}

		// �������̓t���O�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlCustomerInputFlag.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C01134");
			dialog.ctrlCustomerInputFlag.requestFocus();
			return false;
		}

		// �J�n�N����
		if (Util.isNullOrEmpty(dialog.dtBeginDate.getValue())) {
			super.showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		// �I���N����
		if (Util.isNullOrEmpty(dialog.dtEndDate.getValue())) {
			super.showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.requestFocus();
			return false;

		}

		// �J�n�N����,�I���N�����`�F�b�N
		if ((!Util.isNullOrEmpty(dialog.dtBeginDate.getValue()) && !Util.isNullOrEmpty(dialog.dtEndDate.getValue()))
			&& !Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue())) {
			super.showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		// ����ŃR�[�h�`�F�b�N
		MG0080InputCheckerShouhiZei checker = new MG0080InputCheckerShouhiZei(dialog);

		if (!checker.isValid(dialog.ctrlConsumptionTax.getValue(), dialog.chkSalesTaxInputFlag.isSelected(),
			dialog.chkPurchesesTaxInputFlag.isSelected())) {
			dialog.ctrlConsumptionTax.requestTextFocus();
			return false;
		}
		return true;

	}

	void disposeDialog() {
		try {
			// �G���[������ꍇ�ɂ̓_�C�A���O����Ȃ�
			if (dialog.isSettle) {
				if (checkDialog()) {
					dialog.setVisible(false);
				}
			} else {
				dialog.setVisible(false);
			}

		} catch (Exception ex) {
			errorHandler(dialog, ex);
		}
	}

	/**
	 * �m��{�^���������ꂽ���ǂ���
	 * 
	 * @return �m��̏ꍇtrue
	 */
	boolean isSettle() {
		return dialog.isSettle;
	}

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */

	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		// �ȖڃR�[�h�̐ݒ�
		map.put("kmkCode", dialog.ctrlItem.getValue());
		// �⏕�ȖڃR�[�h�̐ݒ�
		map.put("hkmCode", dialog.ctrlSubItem.getValue());
		// ����ȖڃR�[�h�̐ݒ�
		map.put("ukmCode", dialog.ctrlBreakDownItemCode.getValue());
		// ����Ȗږ��̂̐ݒ�
		map.put("ukmName", dialog.ctrlBreakDownItemName.getValue());
		// ����Ȗڗ��̂̐ݒ�
		map.put("ukmName_S", dialog.ctrlBreakDownItemAbbreviationName.getValue());
		// ����Ȗڌ������̂̐ݒ�
		map.put("ukmName_K", dialog.ctrlBreakDownItemNameForSearch.getValue());
		// ����ŃR�[�h�̐ݒ�
		map.put("zeiCode", dialog.ctrlConsumptionTax.getValue());
		// �������̓t���O�̐ݒ�
		map.put("triCodeFlg", this.getComboBoxSelectedValue(dialog.ctrlCustomerInputFlag.getComboBox()));
		// �����`�[�����׸ނ̐ݒ�
		map.put("glFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkReceivingSlipInputFlag.isSelected())));
		// �o���`�[�����׸ނ̐ݒ�
		map.put("glFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkPaymentSlipInputFlag.isSelected())));
		// �U�֓`�[�����׸ނ̐ݒ�
		map.put("glFlg3", String.valueOf(BooleanUtil.toInt(dialog.chkTransferSlipInputFlag.isSelected())));
		// �o��Z�`�[�����׸ނ̐ݒ�
		map.put("apFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkExpenseInputFlag.isSelected())));
		// �o��Z�`�[�����׸ނ̐ݒ�
		map.put("apFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkAccountsPayableAppropriateSlipInputFlag
			.isSelected())));
		// ���v��`�[�����׸ނ̐ݒ�
		map.put("arFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkAccountsReceivableAppropriateSlipInputFlag
			.isSelected())));
		// ���v��`�[�����׸ނ̐ݒ�
		map.put("arFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkAccountsReceivableErasingSlipInputFlag
			.isSelected())));
		// ���Y�v��`�[�����׸ނ̐ݒ�
		map.put("faFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkAssetsAppropriateSlipInputFlag.isSelected())));
		// ���Y�v��`�[�����׸ނ̐ݒ�
		map.put("faFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkPaymentRequestSlipInputFlag.isSelected())));
		// �����������׸�
		map.put("hasFlg", String.valueOf(BooleanUtil.toInt(dialog.chkOccurrenceDateInputFlag.isSelected())));
		// �Ј����̓t���O
		map.put("empCodeFlg", String.valueOf(BooleanUtil.toInt(dialog.chkEmployeeInputFlag.isSelected())));
		// �Ǘ��P-6���̓t���O
		map.put("knrFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkManagement1InputFlag.isSelected())));
		map.put("knrFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkManagement2InputFlag.isSelected())));
		map.put("knrFlg3", String.valueOf(BooleanUtil.toInt(dialog.chkManagement3InputFlag.isSelected())));
		map.put("knrFlg4", String.valueOf(BooleanUtil.toInt(dialog.chkManagement4InputFlag.isSelected())));
		map.put("knrFlg5", String.valueOf(BooleanUtil.toInt(dialog.chkManagement5InputFlag.isSelected())));
		map.put("knrFlg6", String.valueOf(BooleanUtil.toInt(dialog.chkManagement6InputFlag.isSelected())));
		// �Ǘ��P-3���̓t���O�̐ݒ�
		map.put("hmFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkNotAccounting1InputFlag.isSelected())));
		map.put("hmFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkNotAccounting2InputFlag.isSelected())));
		map.put("hmFlg3", String.valueOf(BooleanUtil.toInt(dialog.chkNotAccounting3InputFlag.isSelected())));
		// ���v1-3���̓t���O�̐ݒ�
		map.put("uriZeiFlg", String.valueOf(BooleanUtil.toInt(dialog.chkSalesTaxInputFlag.isSelected())));
		// �d���ېœ��̓t���O�̐ݒ�
		map.put("sirZeiFlg", String.valueOf(BooleanUtil.toInt(dialog.chkPurchesesTaxInputFlag.isSelected())));
		// ���ʉݓ��̓t���O�̐ݒ�
		map.put("mcrFlg", String.valueOf(BooleanUtil.toInt(dialog.chkMultiCurrencyInputFlag.isSelected())));
		// �]���֑Ώۃt���O�̐ݒ�
		map.put("excFlg", String.valueOf(BooleanUtil.toInt(dialog.ctrlRevaluationObjectFlag.isSelected())));
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String kmkCode, String hkmCode, String ukmCode) throws IOException {
		// �ȖڃR�[�h������
		if (Util.isNullOrEmpty(kmkCode) || Util.isNullOrEmpty(hkmCode) || Util.isNullOrEmpty(ukmCode)) {
			return false;
		}
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// �ȖڃR�[�h�̐ݒ�
		addSendValues("kmkCode", kmkCode);
		// �⏕�ȖڃR�[�h�̐ݒ�
		addSendValues("hkmCode", hkmCode);
		// ����ȖڃR�[�h�̐ݒ�
		addSendValues("ukmCode", ukmCode);

		// �T�[�o���̃G���[�ꍇ
		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return true;
		}

		// ���ʂ��擾
		List result = super.getResultList();

		// ���ʂ�Ԃ�
		return (result.size() > 0);
	}
}

class CompanyControlHelper10 extends TAppletClientBase {

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
	 * @return instance
	 */
	public static CompanyControlHelper10 getInstance(String companyCode) {
		if (!instances.containsKey(companyCode)) {
			CompanyControlHelper10 instance = new CompanyControlHelper10(companyCode);
			instances.put(companyCode, instance);
		}
		return (CompanyControlHelper10) instances.get(companyCode);
	}

	/**
	 * @param companyCode
	 */
	public static void reload(String companyCode) {
		instances.remove(companyCode);
	}

	private static final String TARGET_SERVLET = "MG0030CompanyControlMasterServlet";

	private CompanyControlHelper10(String companyCode) {
		try {
			addSendValues("flag", "findone");
			addSendValues("kaiCode", companyCode);

			if (!request(TARGET_SERVLET)) {
				errorHandler();
			}

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
		} catch (Exception ex) {
			// ����ɏ�������܂���ł���
			ClientErrorHandler.handledException(ex);
		}
	}

	/**
	 * @return ����
	 */
	public String getKnrName1() {
		if ("0".equals(knrKbn1)) {
			return this.getWord(knrName1);
		}
		return knrName1;
	}

	/**
	 * @return ����
	 */
	public String getKnrName2() {
		if ("0".equals(knrKbn2)) {
			return this.getWord(knrName2);
		}
		return knrName2;
	}

	/**
	 * @return ����
	 */
	public String getKnrName3() {
		if ("0".equals(knrKbn3)) {
			return this.getWord(knrName3);
		}
		return knrName3;
	}

	/**
	 * @return ����
	 */
	public String getKnrName4() {
		if ("0".equals(knrKbn4)) {
			return this.getWord(knrName4);
		}
		return knrName4;
	}

	/**
	 * @return ����
	 */
	public String getKnrName5() {
		if ("0".equals(knrKbn5)) {
			return this.getWord(knrName5);
		}
		return knrName5;
	}

	/**
	 * @return ����
	 */
	public String getKnrName6() {
		if ("0".equals(knrKbn6)) {
			return this.getWord(knrName6);
		}
		return knrName6;
	}

	/**
	 * @return ����
	 */
	public String getCmpHmName1() {
		if ("0".equals(cmpHmKbn1)) {
			return this.getWord(cmpHmName1);
		}
		return cmpHmName1;
	}

	/**
	 * @return ����
	 */
	public String getCmpHmName2() {
		if ("0".equals(cmpHmKbn2)) {
			return this.getWord(cmpHmName2);
		}
		return cmpHmName2;
	}

	/**
	 * @return ����
	 */
	public String getCmpHmName3() {
		if ("0".equals(cmpHmKbn3)) {
			return this.getWord(cmpHmName3);
		}
		return cmpHmName3;
	}

	/**
	 * @return ����
	 */
	public String getKmkName() {
		if ("0".equals(kmkKbn)) {
			return this.getWord(kmkName);
		}
		return kmkName;
	}

	/**
	 * @return ����
	 */
	public String getHkmName() {
		if ("0".equals(hkmKbn)) {
			return this.getWord(hkmName);
		}
		return hkmName;
	}

	/**
	 * @return ����
	 */
	public String getUkmName() {
		if ("0".equals(ukmKbn)) {
			return this.getWord(ukmName);
		}
		return ukmName;
	}
}

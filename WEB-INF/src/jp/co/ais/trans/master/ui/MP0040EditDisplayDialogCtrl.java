package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 
 */
public class MP0040EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** �x�����@�}�X�^�_�C�A���O */
	protected MP0040EditDisplayDialog dialog;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MP0040PaymentMethodMasterServlet";

	/** �����敪 */
	protected boolean isUpdate;

	/** �����敪 */
	protected boolean isInsert;

	/** �v�㕔�喼�� */
	REFDialogCtrl refHohDepCode;

	/** ����pFLAG */
	boolean flag1 = true;

	/** ����p�t���O */
	boolean flag2 = true;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MP0040EditDisplayDialogCtrl(Frame parent, String titleid) {
		// �x�����@�}�X�^�_�C�A���O�̏�����
		dialog = createDialog(parent, titleid);
		// �^�C�g����ݒ肷��
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		init();

		setItemCondition();
	}

	/**
	 * @param parent �e�R���e�i�[
	 * @param titleid �^�C�g��
	 * @return �_�C�A���O
	 */
	protected MP0040EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MP0040EditDisplayDialog(parent, true, this, titleid);
	}

	/**
	 * �ȖڃR���|�[�l���g �����ݒ�(�w�b�_)
	 */
	public void setItemCondition() {
		// ���O�C�����[�U�[�̉�ЃR�[�h
		dialog.ctrlItemUnit.getInputParameter().setCompanyCode(super.getLoginUserCompanyCode());
	}

	/**
	 * �_�C�A���O������
	 */
	private void init() {
		// �W�v�敪�̐ݒ�
		dialog.rdoExternalPayment.setSelected(true);

		// �⏕�ȖڃR�[�h�̏�����
		dialog.ctrlItemUnit.getTSubItemField().setEditable(false);
		dialog.ctrlItemUnit.getTSubItemField().setButtonEnabled(false);
		// ����ȖڃR�[�h�̏�����
		dialog.ctrlItemUnit.getTBreakDownItemField().setEditable(false);
		dialog.ctrlItemUnit.getTBreakDownItemField().setButtonEnabled(false);
		// �v�㕔��R�[�h�`�F�b�N
		refHohDepCode = new REFDialogCtrl(dialog.ctrlAppropriateDepartment, dialog.getParentFrame());
		refHohDepCode.setColumnLabels("C00698", "C00724", "C00725");
		refHohDepCode.setTargetServlet("MG0060DepartmentMasterServlet");
		refHohDepCode.setTitleID("C02338");
		refHohDepCode.setShowsMsgOnError(false);
		refHohDepCode.addIgnoredButton(dialog.btnReturn);

		dialog.ctrlAppropriateDepartment.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				refHohDepCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlAppropriateDepartment.getValue());
					dialog.ctrlAppropriateDepartment.getField().clearOldText();
					dialog.ctrlAppropriateDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		refHohDepCode.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Department");
				return keys;
			}

		});

		dialog.dtBeginDate.setValue(PanelAndDialogCtrlBase.minInputDate);
		dialog.dtEndDate.setValue(PanelAndDialogCtrlBase.maxInputDate);

		dialog.ctrlPaymentMethodCode.getField().requestFocus();
	}

	// map�̏�����
	private Map hohNaiCodeMap;

	/**
	 * @param map
	 */
	public void setHohNaiCodeMap(Map map) {
		this.hohNaiCodeMap = map;

		this.fillItemsToComboBox(dialog.ctrlPaymentInternalCode.getComboBox(), hohNaiCodeMap);
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
		// �x�����@�R�[�h�̐ݒ�
		dialog.ctrlPaymentMethodCode.setValue((String) map.get("hohHohCode"));
		// �x�����@���̐ݒ�
		dialog.ctrlPaymentMethodName.setValue((String) map.get("hohHohName"));
		// �x�����@�������̂̐ݒ�
		dialog.ctrlPaymentMethodNameForSearch.setValue((String) map.get("hohHohName_K"));

		String itemCode = Util.avoidNull(map.get("hohKmkCode"));
		String subItemCode = Util.avoidNull(map.get("hohHkmCode"));

		// �Ȗڃp�����[�^
		dialog.ctrlItemUnit.getInputParameter().setItemCode(itemCode);
		dialog.ctrlItemUnit.getInputParameter().setSubItemCode(subItemCode);

		// �Ȗ�
		dialog.ctrlItemUnit.setItemCode(itemCode);
		// �⏕�Ȗ�
		dialog.ctrlItemUnit.setSubItemCode(subItemCode);
		// ����Ȗ�
		dialog.ctrlItemUnit.setBreakDownItemCode(Util.avoidNull(map.get("hohUkmCode")));
		// �v�㕔��R�[�h�̐ݒ�
		dialog.ctrlAppropriateDepartment.setValue((String) map.get("hohDepCode"));
		// �x���Ώۋ敪�̐ݒ�
		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hohSihKbn")));
		dialog.rdoExternalPayment.setSelected(boo);
		dialog.rdoEmployeePayment.setSelected(!boo);
		// �x�������R�[�h�̐ݒ�
		this.setComboBoxSelectedItem(dialog.ctrlPaymentInternalCode.getComboBox(), (String) map.get("hohNaiCode"));
		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// �ҏW���[�h�̂Ƃ��͒ʉ݃R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlPaymentMethodCode.setEditable(isUpdate == false);

		isInsert = "insert".equals(map.get("flag"));
		// �x�����@�R�[�h
		if (isUpdate) {
			dialog.ctrlPaymentMethodName.getField().requestFocus();
		}
		refHohDepCode.refreshName();
	}

	/**
	 * ����
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// �m��{�^������ �`�F�b�NOK�Ȃ����
			if (!checkDialog()) return;
			dialog.setVisible(false);
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
		return dialog.isSettle;
	}

	/**
	 * ��ʏ�\���f�[�^�̎擾
	 * 
	 * @return �f�[�^
	 */

	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// �x�����@�R�[�h�̐ݒ�
		map.put("hohHohCode", dialog.ctrlPaymentMethodCode.getValue());
		// �x�����@���̐ݒ�
		map.put("hohHohName", dialog.ctrlPaymentMethodName.getValue());
		// �x�����@�������̂̐ݒ�
		map.put("hohHohName_K", dialog.ctrlPaymentMethodNameForSearch.getValue());
		// �ȖڃR�[�h�̐ݒ�
		map.put("hohKmkCode", dialog.ctrlItemUnit.getTItemField().getValue());
		// �⏕�ȖڃR�[�h�̐ݒ�
		map.put("hohHkmCode", dialog.ctrlItemUnit.getTSubItemField().getValue());
		// ����ȖڃR�[�h�̐ݒ�
		map.put("hohUkmCode", dialog.ctrlItemUnit.getTBreakDownItemField().getValue());
		// �v�㕔��R�[�h�̐ݒ�
		map.put("hohDepCode", dialog.ctrlAppropriateDepartment.getValue());
		// �x���Ώۋ敪�̐ݒ�
		map.put("hohSihKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoExternalPayment.isSelected())));
		// �x�������R�[�h�̐ݒ�
		map.put("hohNaiCode", this.getComboBoxSelectedValue(dialog.ctrlPaymentInternalCode.getComboBox()));
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String code) {
		try {
			// �x�����@�R�[�h������
			if (Util.isNullOrEmpty(code)) {
				return false;
			}
			// ������ʂ̐ݒ�
			addSendValues("flag", "checkcode");
			// ��ЃR�[�h�̐ݒ�
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// �x�����@�R�[�h�̐ݒ�
			addSendValues("hohHohCode", code);

			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return true;
			}

			// ���ʂ��擾
			List result = super.getResultList();
			// ���ʂ�Ԃ�
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}
	}

	/**
	 * �_�C�A���O�̓��͍��ڃ`�F�b�N
	 * 
	 * @return true �`�F�b�N���� false �G���[������
	 */
	public boolean checkDialog() {

		// �x�����@�R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlPaymentMethodCode.getValue())) {
			showMessage(dialog, "I00002", "C00864");
			dialog.ctrlPaymentMethodCode.requestFocus();
			return false;
		}

		if (!isUpdate && checkCode(dialog.ctrlPaymentMethodCode.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlPaymentMethodCode.requestFocus();
			return false;
		}

		// �x�����@���`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlPaymentMethodName.getValue())) {
			showMessage(dialog, "I00002", "C00865");
			dialog.ctrlPaymentMethodName.requestFocus();
			return false;
		}

		// �x�����@�������̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlPaymentMethodNameForSearch.getValue())) {
			showMessage(dialog, "I00002", "C00866");
			dialog.ctrlPaymentMethodNameForSearch.requestFocus();
			return false;
		}

		// �ȖڃR�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlItemUnit.getTItemField().getValue())) {
			showMessage(dialog, "I00002", "C00572");
			dialog.ctrlItemUnit.getTItemField().requestTextFocus();
			return false;
		}

		// �⏕�ȖڃR�[�h�`�F�b�N
		if (dialog.ctrlItemUnit.getTSubItemField().getField().isEditable()
			&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTSubItemField().getValue())) {
			showMessage(dialog, "I00002", "C00890");
			dialog.ctrlItemUnit.getTSubItemField().requestTextFocus();
			return false;
		}

		// ������g���ꍇ�̂݃`�F�b�N
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();
		if (compInfo.isUseBreakDownItem()) {
			// ����ȖڃR�[�h�`�F�b�N
			if (dialog.ctrlItemUnit.getTBreakDownItemField().getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTBreakDownItemField().getValue())) {
				showMessage(dialog, "I00002", "C00603");
				dialog.ctrlItemUnit.getTBreakDownItemField().requestTextFocus();
				return false;
			}
		}

		// �v�㕔��R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getValue())) {
			showMessage(dialog, "I00002", "C00863");
			dialog.ctrlAppropriateDepartment.requestTextFocus();
			return false;
		}

		// �x���Ώۋ敪�`�F�b�N
		if (!dialog.rdoEmployeePayment.isSelected() && !dialog.rdoExternalPayment.isSelected()) {
			showMessage(dialog, "I00002", "C01096");
			dialog.rdoEmployeePayment.requestFocus();
			return false;
		}

		// �x�������R�[�h�̐ݒ�
		if (Util.isNullOrEmpty(this.getComboBoxSelectedValue(dialog.ctrlPaymentInternalCode.getComboBox()))) {
			showMessage(dialog, "I00002", "C01097");
			dialog.ctrlPaymentInternalCode.getComboBox().requestFocus();
			return false;
		}

		// �x�������R�[�h�̐ݒ�i�ЊO�x�����̏ꍇ�A�����R�[�h<=10���ƃG���[�j
		if (dialog.rdoExternalPayment.isSelected()) {
			// �x���������R�[�h��10�ȉ��̂Ƃ��̓G���[
			if (dialog.ctrlPaymentInternalCode.getComboBox().getSelectedIndex() < 5) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00208");
				dialog.ctrlPaymentInternalCode.getComboBox().requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// �x�������R�[�h�̐ݒ�i�Ј��x�����̏ꍇ�A�����R�[�h>10���ƃG���[�j
		if (dialog.rdoEmployeePayment.isSelected()) {
			// �x���������R�[�h��10�ȉ��̂Ƃ��̓G���[
			if (dialog.ctrlPaymentInternalCode.getComboBox().getSelectedIndex() > 4) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00209");
				dialog.ctrlPaymentInternalCode.getComboBox().requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// �J�n�N����
		if (dialog.dtBeginDate.getValue() == null) {
			showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		// �I���N����
		if (dialog.dtEndDate.getValue() == null) {
			showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.requestFocus();
			return false;
		}

		// �J�n�N���������I���N�����ɂ��Ă��������`�F�b�N
		if (!Util.isNullOrEmpty(dialog.dtBeginDate.getValue()) && !Util.isNullOrEmpty(dialog.dtEndDate.getValue())) {
			if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
				showMessage(dialog, "W00035", "");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}
		}

		return true;
	}
}

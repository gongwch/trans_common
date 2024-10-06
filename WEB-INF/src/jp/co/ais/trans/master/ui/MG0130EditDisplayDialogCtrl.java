package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * ����Ń}�X�^�_�C�A���O�R���g���[��
 */
public class MG0130EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ����Ń}�X�^�_�C�A���O */
	protected MG0130EditDisplayDialog dialog;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0130ConsumptionTaxMasterServlet";

	/** �����敪 */
	private boolean isUpdate;

	/** ����d���敪 */
	private Map usKbnMap;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0130EditDisplayDialogCtrl(Frame parent, String titleid) {
		// ����Ń}�X�^�_�C�A���O�̏�����
		dialog = createDialog(parent, titleid);
		// �^�C�g����ݒ肷��

		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});

		// �W�v�敪�̐ݒ�
		dialog.rdoDisUse.setSelected(true);
		dialog.numOutputOrder.setEditable(false);

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);
		dialog.ctrlConsumptionTaxCode.getField().requestFocus();

	}

	/**
	 * @param parent �e�R���e�i�[
	 * @param titleid �^�C�g��
	 * @return �_�C�A���O
	 */
	protected MG0130EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0130EditDisplayDialog(parent, true, this, titleid);
	}

	/**
	 * @param map
	 */
	public void setUsKbnMap(Map map) {
		this.usKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlSalesPurchesesDivision.getComboBox(), usKbnMap, false);
	}

	/**
	 * �\��
	 * 
	 * @param isEnabledCurCode ����ŃR�[�h�G���A�ҏW��(true)�A�s��(false)
	 */
	void show(boolean isEnabledCurCode) {
		dialog.setVisible(true);
		dialog.ctrlConsumptionTaxCode.setEditable(isEnabledCurCode);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map �Z�b�g����l
	 */
	void setValues(Map map) {
		// ����ŃR�[�h�̐ݒ�
		dialog.ctrlConsumptionTaxCode.setValue((String) map.get("zeiCode"));
		// ����Ŗ��̂̐ݒ�
		dialog.ctrlConsumptionTaxName.setValue((String) map.get("zeiName"));
		// ����ŗ��̂̐ݒ�
		dialog.ctrlConsumptionTaxAbbreviationName.setValue((String) map.get("zeiName_S"));
		// ����Ō������̂̐ݒ�
		dialog.ctrlConsumptionTaxNameForSearch.setValue((String) map.get("zeiName_K"));
		// ����d���敪�̐ݒ�
		this.setComboBoxSelectedItem(dialog.ctrlSalesPurchesesDivision.getComboBox(), (String) map.get("usKbn"));
		// ����Ōv�Z���o�͏����̐ݒ�
		String boo = (String) map.get("szeiKeiKbn");
		if (Util.isNullOrEmpty(boo)) {
			dialog.rdoDisUse.setSelected(true);
			dialog.rdoUse.setSelected(false);
			dialog.numOutputOrder.getField().setEditable(false);
		} else {
			dialog.rdoDisUse.setSelected(false);
			dialog.rdoUse.setSelected(true);
			dialog.numOutputOrder.setEditable(true);
			dialog.numOutputOrder.setValue((String) map.get("szeiKeiKbn"));
		}
		// ����ŗ��̐ݒ�
		String numCon;
		DecimalFormat format = new DecimalFormat("##.0");
		numCon = format.format(Double.valueOf((String) map.get("zeiRate")));
		dialog.numConsumptionTaxRate.setValue(numCon);
		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// �ҏW���[�h�̂Ƃ��͒ʉ݃R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlConsumptionTaxCode.setEditable(!isUpdate); // 2006/12/29 Yanwei
		if (isUpdate) {
			dialog.ctrlConsumptionTaxName.getField().requestFocus();
		}

	}

	/**
	 * ����
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// �m��{�^������ �`�F�b�NOK�Ȃ����
			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} else {
			// �߂�{�^������
			// ��ʂ�߂�
			dialog.setVisible(false);
		}

	}

	boolean checkDialog() {
		try { // ����ŃR�[�h
			if (Util.isNullOrEmpty(dialog.ctrlConsumptionTaxCode.getValue())) {
				showMessage(dialog, "I00002", "C00573");
				dialog.ctrlConsumptionTaxCode.requestFocus();
				return false;
			}

			if (!isUpdate && checkCode(dialog.ctrlConsumptionTaxCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlConsumptionTaxCode.requestFocus();
				return false;
			}

			// ����Ŗ���
			if (Util.isNullOrEmpty(dialog.ctrlConsumptionTaxName.getValue())) {
				showMessage(dialog, "I00002", "C00774");
				dialog.ctrlConsumptionTaxName.requestFocus();
				return false;
			}

			// ����ŗ���
			if (Util.isNullOrEmpty(dialog.ctrlConsumptionTaxAbbreviationName.getValue())) {
				showMessage(dialog, "I00002", "C00775");
				dialog.ctrlConsumptionTaxAbbreviationName.requestFocus();
				return false;
			}

			// ����Ō�������
			if (Util.isNullOrEmpty(dialog.ctrlConsumptionTaxNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00828");
				dialog.ctrlConsumptionTaxNameForSearch.requestFocus();
				return false;
			}

			// ����d���敪
			if (Util.isNullOrEmpty(dialog.ctrlSalesPurchesesDivision.getComboBox().getSelectedItem().toString())) {
				showMessage(dialog, "I00002", "C01283");
				dialog.ctrlSalesPurchesesDivision.getComboBox().requestFocus();
				return false;
			}

			// ����Ōv�Z��
			if (!dialog.rdoDisUse.isSelected() && !dialog.rdoUse.isSelected()) {
				showMessage(dialog, "I00002", "C01176");
				dialog.rdoDisUse.requestFocus();
				return false;
			}

			if (!dialog.rdoDisUse.isSelected()) {
				if (Util.isNullOrEmpty(dialog.numOutputOrder.getValue())) {
					showMessage(dialog, "I00002", "C00776");
					dialog.numOutputOrder.requestFocus();
					return false;
				}
			}

			if (!dialog.rdoDisUse.isSelected()) {
				String value = dialog.numOutputOrder.getValue();
				if (!Util.isNullOrEmpty(value)) {
					if (Integer.parseInt(value) < 1 || 9 < Integer.parseInt(value)) {
						showMessage(dialog, "W00065", 1, 9);
						dialog.numOutputOrder.requestFocus();
						return false;
					}
				}
			}

			// ����ŗ�
			if (Util.isNullOrEmpty(dialog.numConsumptionTaxRate.getValue())) {
				showMessage(dialog, "I00002", "C00777");
				dialog.numConsumptionTaxRate.requestFocus();
				return false;
			}

			String value = dialog.numConsumptionTaxRate.getValue();
			if (!Util.isNullOrEmpty(value)
				&& (Float.parseFloat((dialog.numConsumptionTaxRate.getValue())) < 0 || 99.9f < Float
					.parseFloat(dialog.numConsumptionTaxRate.getValue().trim()))) {
				showMessage(dialog, "W00065", "0.0", "99.9");
				dialog.numConsumptionTaxRate.requestFocus();
				return false;
			}
			// �J�n�N����
			if (dialog.dtBeginDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00055");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}
			// �I���N����
			if (dialog.dtEndDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00261");
				dialog.dtEndDate.getCalendar().requestFocus();
				return false;
			}

			// �J�n�N���������I���N�����ɂ��Ă�������
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
		// ����ŃR�[�h�̐ݒ�
		map.put("zeiCode", dialog.ctrlConsumptionTaxCode.getValue());
		// ����Ŗ��̂̐ݒ�
		map.put("zeiName", dialog.ctrlConsumptionTaxName.getValue());
		// ����ŗ��̂̐ݒ�
		map.put("zeiName_S", dialog.ctrlConsumptionTaxAbbreviationName.getValue());
		// ����Ō������̂̐ݒ�
		map.put("zeiName_K", dialog.ctrlConsumptionTaxNameForSearch.getValue());
		// ����d���敪�̐ݒ�
		map.put("usKbn", this.getComboBoxSelectedValue(dialog.ctrlSalesPurchesesDivision.getComboBox()));
		// ����Ōv�Z���o�͏����̐ݒ�
		map.put("szeiKeiKbn", dialog.numOutputOrder.getValue());
		// ����ŗ��̐ݒ�
		map.put("zeiRate", dialog.numConsumptionTaxRate.getValue());
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ���ʂ�Ԃ�
		return map;
	}

	boolean checkCode(String code) throws IOException {
		// �ȖڃR�[�h������
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		// ������ʂ̐ݒ�
		addSendValues("flag", "checkcode");
		// ��ЃR�[�h�̐ݒ�
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// ����ŃR�[�h�̐ݒ�
		addSendValues("zeiCode", code);

		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return true;
		}
		// ���ʂ��擾
		List result = super.getResultList();
		// ���ʂ�Ԃ�
		return (result.size() > 0);
	}

	/**
	 * ����Ōv�Z���̏�����
	 */
	public void selectedSet() {
		boolean boo = dialog.rdoUse.isSelected();
		if (boo) {
			dialog.numOutputOrder.setEditable(boo);
		} else {
			dialog.numOutputOrder.getField().setText("");
			dialog.numOutputOrder.setEditable(boo);

		}
	}
}

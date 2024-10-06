package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * ����}�X�^�_�C�A���O �R���g���[��
 * 
 * @author ookawara
 */
public class MG0060EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ����}�X�^�_�C�A���O */
	protected MG0060EditDisplayDialog dialog;

	/** �����敪 */
	private boolean isUpdate;

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0060DepartmentMasterServlet";

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param titleid
	 */
	MG0060EditDisplayDialogCtrl(Frame parent, String titleid) {
		// ����}�X�^�_�C�A���O�̏�����
		dialog = createDialog(parent, titleid);

		// �_�C�A���O������Adialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				// ������ʂ̐ݒ�
				dialog.btnReturn.doClick();
			}
		});
		dialog.ctrlDepartmentCode.getField().requestFocus();

		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));

	}

	/**
	 * @param parent �e�R���e�i�[
	 * @param titleid �^�C�g��
	 * @return �_�C�A���O
	 */
	protected MG0060EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0060EditDisplayDialog(parent, true, this, titleid);
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

	boolean checkDialog() {

		if (Util.isNullOrEmpty(dialog.ctrlDepartmentCode.getValue())) {
			if (Util.isNullOrEmpty(dialog.ctrlDepartmentCode.getValue())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "I00002", "C00698");
				dialog.ctrlDepartmentCode.requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// ����R�[�h�`�F�b�N
		if (!Util.isNullOrEmpty(dialog.ctrlDepartmentCode.getValue())) {
			if (!isUpdate && checkCode(dialog.ctrlDepartmentCode.getValue())) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlDepartmentCode.requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// ���喼�̃`�F�b�N
		if (Util.isNullOrEmpty(dialog.ctrlDepartmentName.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00723");
			dialog.ctrlDepartmentName.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// ���嗪�̖�����
		if (Util.isNullOrEmpty(dialog.ctrlDepartmentAbbreviatedName.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00724");
			dialog.ctrlDepartmentAbbreviatedName.requestFocus();
			// �G���[��Ԃ�
			return false;
		}
		// ���匟�����̖�����
		if (Util.isNullOrEmpty(dialog.ctrlDepartmentNameForSearch.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00725");
			dialog.ctrlDepartmentNameForSearch.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		// �l�����P������
		if (Util.isNullOrEmpty(dialog.numPersonNumber1.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00726");
			dialog.numPersonNumber1.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		String strNum1 = dialog.numPersonNumber1.getValue();
		if (!Util.isNullOrEmpty(strNum1)) {
			BigDecimal num = new BigDecimal(strNum1);
			// �l�����P����0�`9,999,999�ȊO
			if (!Util.isNullOrEmpty(strNum1)
				&& ((num.compareTo(new BigDecimal(0)) < 0 || num.compareTo(new BigDecimal("99999999")) > 0))) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00065", "0", "99,999,999");
				dialog.numPersonNumber1.requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// �l�����Q������
		if (Util.isNullOrEmpty(dialog.numPersonNumber2.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00727");
			dialog.numPersonNumber2.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		String strNum2 = dialog.numPersonNumber2.getValue();
		if (!Util.isNullOrEmpty(strNum2)) {
			BigDecimal num = new BigDecimal(strNum2);
			// �l�����Q����0�`9,999,999�ȊO
			if (num.compareTo(new BigDecimal(0)) < 0 || num.compareTo(new BigDecimal("99999999")) > 0) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00065", "0", "99,999,999");
				dialog.numPersonNumber2.requestFocus();
				// �G���[��Ԃ�
				return false;
			}
		}

		// ���ʐϖ�����
		if (Util.isNullOrEmpty(dialog.numFloorArea.getValue())) {
			// �x�����b�Z�[�W�\��
			showMessage(dialog, "I00002", "C00728");
			dialog.numFloorArea.requestFocus();
			// �G���[��Ԃ�
			return false;
		}

		String strArea = dialog.numFloorArea.getValue();
		if (!Util.isNullOrEmpty(strArea)) {
			BigDecimal num = new BigDecimal(strArea);
			// ���ʐς���0�`9,999,999.99�ȊO
			if (num.compareTo(new BigDecimal(0)) < 0 || num.compareTo(new BigDecimal("99999999.99")) > 0) {
				// �x�����b�Z�[�W�\��
				showMessage(dialog, "W00065", "0", "99,999,999.99");
				dialog.numFloorArea.requestFocus();
				// �G���[��Ԃ�
				return false;
			}
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

		return true;

	}

	/**
	 * �\��
	 * 
	 * @param isEnabledDeptCode ����R�[�h�G���A�ҏW��(true)�A�t��(false)
	 */
	void show(boolean isEnabledDeptCode) {
		// ��ʂ�\������
		dialog.setVisible(true);
		// ����R�[�h�̐ݒ�
		dialog.ctrlDepartmentCode.setEditable(isEnabledDeptCode);
	}

	/**
	 * �l�̃Z�b�g
	 * 
	 * @param map
	 */

	void setValues(Map map) {
		// ����R�[�h�̐ݒ�
		dialog.ctrlDepartmentCode.setValue((String) map.get("depCode"));
		// ���喼�̂̐ݒ�
		dialog.ctrlDepartmentName.setValue((String) map.get("depName"));
		// ���嗪�̂̐ݒ�
		dialog.ctrlDepartmentAbbreviatedName.setValue((String) map.get("depName_S"));
		// ���匟�����̂̐ݒ�
		dialog.ctrlDepartmentNameForSearch.setValue((String) map.get("depName_K"));

		// �l�����P�̐ݒ�
		dialog.numPersonNumber1.setValue(((String) map.get("men1")).replace(",", ""));
		// �l�����Q�̐ݒ�
		dialog.numPersonNumber2.setValue(((String) map.get("men2")).replace(",", ""));
		// ���ʐς̐ݒ�
		dialog.numFloorArea.setValue(((String) map.get("space")).replace(",", ""));
		// ���͂̐ݒ�
		dialog.rdoInput.setSelected(true);
		// ����敪�̐ݒ�
		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("depKbn")));
		// ���͂̐ݒ�
		dialog.rdoInput.setSelected(!boo);
		// �W�v�̐ݒ�
		dialog.rdoSummary.setSelected(boo);
		// �J�n�N�����̐ݒ�
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// �I���N�����̐ݒ�
		dialog.dtEndDate.setValue((Date) map.get("endDate"));
		// �ҏW���[�h�̂Ƃ��͕���R�[�h���ҏW�s�ɂȂ�
		isUpdate = "update".equals(map.get("flag"));
		if (isUpdate) {
			dialog.ctrlDepartmentName.getField().requestFocus();
		}
		// ����R�[�h�̐ݒ�
		dialog.ctrlDepartmentCode.setEditable(isUpdate == false);
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
		// ����R�[�h�̐ݒ�
		map.put("depCode", dialog.ctrlDepartmentCode.getValue());
		// ���喼�̂̐ݒ�
		map.put("depName", dialog.ctrlDepartmentName.getValue());
		// ���嗪�̂̐ݒ�
		map.put("depName_K", dialog.ctrlDepartmentNameForSearch.getValue());
		// ���匟�����̂̐ݒ�
		map.put("depName_S", dialog.ctrlDepartmentAbbreviatedName.getValue());
		// �l�����P�̐ݒ�
		map.put("men1", (dialog.numPersonNumber1.getValue()).replace(",", ""));
		// �l�����Q�̐ݒ�
		map.put("men2", (dialog.numPersonNumber2.getValue()).replace(",", ""));
		// ���ʐς̐ݒ�
		map.put("space", (dialog.numFloorArea.getValue()).replace(",", ""));
		// ����敪�̐ݒ�
		map.put("depKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoInput.isSelected() == false)));
		// �J�n�N�����̐ݒ�
		map.put("strDate", dialog.dtBeginDate.getValue());
		// �I���N�����̐ݒ�
		map.put("endDate", dialog.dtEndDate.getValue());
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", getLoginUserCompanyCode());
		// ���ʂ�Ԃ�
		return map;
	}

	/**
	 * �R�[�h�̃`�F�b�N
	 * 
	 * @param code
	 * @return �`�F�b�N����
	 */
	boolean checkCode(String code) {
		try {// ����R�[�h������
			if (Util.isNullOrEmpty(code)) return false;
			// ������ʂ̐ݒ�
			addSendValues("flag", "checkcode");
			// ��ЃR�[�h�̐ݒ�
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// ����R�[�h�̐ݒ�
			addSendValues("depCode", code);

			// �T�[�o���̃G���[�ꍇ
			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return true;
			}

			// ���ʂ��擾
			List result = super.getResultList();
			// ���ʂ�Ԃ�
			return (result.size() > 0);
		} catch (IOException ex) {
			// ����ɏ�������܂���ł���
			super.errorHandler(dialog, ex, "E00009");
			return false;
		}
	}
}

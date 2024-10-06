package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ��I���F�Ǘ��I��+�͈͌���+�C�ӑI�� * 3�F�R���g���[��
 * 
 * @author AIS
 */
public class TManagementSelectMultiUnitController extends TController {

	/** true:�L�� */
	protected static boolean useSaveSetting = ClientConfig.isFlagOn("trans.report.management.unit.save");

	/** �t�B�[���h */
	protected TManagementSelectMultiUnit field;

	/**
	 * @param field �t�B�[���h
	 */
	public TManagementSelectMultiUnitController(TManagementSelectMultiUnit field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	protected void init() {
		//
	}

	/**
	 * @return true:�L��
	 */
	protected boolean isUseSaveSetting() {
		return useSaveSetting;
	}

	/**
	 * �Ǘ����ڒP�ʏ����ێ��̐ݒ�ɂ�蕜��
	 */
	protected void restoreSetting() {
		if (!isUseSaveSetting()) {
			// �L�����Ȃ��ꍇ�A�����s�v
			return;
		}

		// �ێ��L�[���g���ď���������
		if (!Util.isNullOrEmpty(getSaveKey())) {
			for (int i = 0; i < field.ctrlManagementSelectUnits.size(); i++) {
				TManagementSelectUnit unit = field.ctrlManagementSelectUnits.get(i);
				unit.setManagementAngleSearchCondition(getSaveCondition(i));
			}
		}
	}

	/**
	 * �ێ��L�[�̎擾
	 * 
	 * @return saveKey �ێ��L�[
	 */
	public String getSaveKey() {
		return field.getSaveKey();
	}

	/**
	 * �ێ��L�[�̎擾
	 * 
	 * @param i
	 * @return saveKey �ێ��L�[
	 */
	protected ManagementAngleSearchCondition getSaveCondition(int i) {
		return (ManagementAngleSearchCondition) FileUtil.getTemporaryObject(getSaveKey(i));
	}

	/**
	 * �ێ��L�[�̎擾
	 * 
	 * @param i
	 * @return saveKey �ێ��L�[
	 */
	protected String getSaveKey(int i) {
		StringBuilder sb = new StringBuilder();
		sb.append(TLoginCtrl.getClientSaveKey());
		sb.append("_");
		sb.append(field.getSaveKey());
		sb.append("_");
		sb.append(i);
		return sb.toString();
	}

	/**
	 * �Ǘ����ڒP�ʏ��� �ݒ�ێ�
	 */
	public void saveSetting() {
		if (!isUseSaveSetting()) {
			// �L�����Ȃ��ꍇ�A�����s�v
			return;
		}

		// ��������鎞�ɁA�ێ��L�[������΁A���Y�������N���C�A���g�Ɏ���
		if (Util.isNullOrEmpty(getSaveKey())) {
			return;
		}

		int size = field.ctrlManagementSelectUnits.size();

		List<ManagementAngleSearchCondition> list = getManagementAngleSearchConditions();
		if (list == null || list.isEmpty()) {
			// �����������N���A
			for (int i = 0; i < size; i++) {
				FileUtil.saveTemporaryObject(null, getSaveKey(i));
			}
		} else {
			for (int i = 0; i < size; i++) {
				ManagementAngleSearchCondition condition = list.get(i);
				FileUtil.saveTemporaryObject(condition, getSaveKey(i));
			}
		}

	}

	@Override
	public void start() {
		//

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �Ǘ�������Z�߂ĕԂ�
	 * 
	 * @return �Ǘ�������Z�߂ĕԂ�
	 */
	public List<ManagementAngleSearchCondition> getManagementAngleSearchConditions() {
		List<ManagementAngleSearchCondition> list = new ArrayList<ManagementAngleSearchCondition>();
		for (TManagementSelectUnit unit : field.ctrlManagementSelectUnits) {
			list.add(unit.getManagementAngleSearchCondition());
		}
		return list;
	}

	/**
	 * �Ǘ�������ݒ肷��
	 * 
	 * @param list �Ǘ�����
	 */
	public void setManagementAngleSearchConditions(List<ManagementAngleSearchCondition> list) {
		if (list == null) {
			return;
		}

		// TODO
	}

	/**
	 * �召�`�F�b�N�B�J�n�R�[�h > �I���R�[�h�̃t�B�[���h�������<br>
	 * ���̃C���f�b�N�X��Ԃ��B������Ε�����Ԃ��B
	 * 
	 * @return �J�n�R�[�h > �I���R�[�h�̃t�B�[���h�������<br>
	 *         ���̃C���f�b�N�X��Ԃ��B������Ε�����Ԃ��B
	 */
	public int isSmallerFrom() {
		int i = 0;
		for (TManagementSelectUnit unit : field.ctrlManagementSelectUnits) {
			if (!unit.isSmallerFrom()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * �ォ�珇�ɓ��͂���Ă��邩��Ԃ��B
	 * 
	 * @return �ォ�珇�ɓ��͂���Ă��邩
	 */
	public boolean isEnteredAtTheTop() {

		boolean entered = false;
		for (int i = field.ctrlManagementSelectUnits.size() - 1; i >= 0; i--) {
			if (entered && ManagementAngle.NONE == field.ctrlManagementSelectUnits.get(i).getManagementAngle()) {
				return false;
			}
			if (ManagementAngle.NONE != field.ctrlManagementSelectUnits.get(i).getManagementAngle()) {
				entered = true;
			}
		}

		return true;
	}

	/**
	 * �Ǘ��w��ɏd��������΁A�d�����Ă���t�B�[���h�̃C���f�b�N�X��Ԃ��B<br>
	 * �����̏ꍇ�A�d���͂Ȃ�
	 * 
	 * @return �����B����ȊO�͏d�����������t�B�[���h�̔ԍ�
	 */
	public int getSameManagementAngleIndex() {

		for (int i = 0; i < field.ctrlManagementSelectUnits.size(); i++) {

			TManagementSelectUnit unit = field.ctrlManagementSelectUnits.get(i);

			// �I������Ă���Ǘ�
			ManagementAngle managementAngle = unit.getManagementAngle();
			if (ManagementAngle.NONE != managementAngle) {
				int selectedCount = 0;
				for (TManagementSelectUnit unit2 : field.ctrlManagementSelectUnits) {
					if (unit2.getManagementAngle() == managementAngle) {
						selectedCount++;
					}
				}
				if (selectedCount != 1) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * true:�SSPC���[�h�̐ݒ�
	 * 
	 * @param allCompanyMode true:�SSPC���[�h
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {

		for (int i = 0; i < field.ctrlManagementSelectUnits.size(); i++) {

			TManagementSelectUnit unit = field.ctrlManagementSelectUnits.get(i);

			for (TReferenceRangeUnit unit2 : unit.ctrlReferenceRangeUnits) {

				if (unit2 instanceof TNoneReferenceRangeUnit) {
					continue;
				}

				if (unit2 instanceof TDepartmentReferenceRangeUnit) {
					// ����
					TDepartmentOptionalSelector selector = ((TDepartmentReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TDepartmentReferenceRange range = ((TDepartmentReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TCustomerReferenceRangeUnit) {
					// �����
					// �W�v�����
					TCustomerOptionalSelector selector = ((TCustomerReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TCustomerReferenceRange range = ((TCustomerReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TEmployeeReferenceRangeUnit) {
					// �Ј�
					TEmployeeOptionalSelector selector = ((TEmployeeReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TEmployeeReferenceRange range = ((TEmployeeReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement1ReferenceRangeUnit) {
					// �Ǘ�1
					TManagement1OptionalSelector selector = ((TManagement1ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement1ReferenceRange range = ((TManagement1ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement2ReferenceRangeUnit) {
					// �Ǘ�2
					TManagement2OptionalSelector selector = ((TManagement2ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement2ReferenceRange range = ((TManagement2ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement3ReferenceRangeUnit) {
					// �Ǘ�3
					TManagement3OptionalSelector selector = ((TManagement3ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement3ReferenceRange range = ((TManagement3ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement4ReferenceRangeUnit) {
					// �Ǘ�4
					TManagement4OptionalSelector selector = ((TManagement4ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement4ReferenceRange range = ((TManagement4ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement5ReferenceRangeUnit) {
					// �Ǘ�5
					TManagement5OptionalSelector selector = ((TManagement5ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement5ReferenceRange range = ((TManagement5ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement6ReferenceRangeUnit) {
					// �Ǘ�6
					TManagement6OptionalSelector selector = ((TManagement6ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement6ReferenceRange range = ((TManagement6ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);
				}

			}
		}

	}

	/**
	 * ��Џo�͒P�ʂ̐ݒ�
	 * 
	 * @param companyOrgUnit ��Џo�͒P��
	 */
	public void setCompanyOrgUnit(TCompanyOrganizationUnit companyOrgUnit) {

		// �֘A�Ǘ��I���ւ��ׂčX�V����
		for (int i = 0; i < field.ctrlManagementSelectUnits.size(); i++) {

			TManagementSelectUnit unit = field.ctrlManagementSelectUnits.get(i);

			for (TReferenceRangeUnit unit2 : unit.ctrlReferenceRangeUnits) {
				unit2.setCompanyOrgUnit(companyOrgUnit);
			}
		}
	}

}

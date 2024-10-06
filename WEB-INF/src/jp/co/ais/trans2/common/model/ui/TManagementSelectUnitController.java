package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ��I���F�Ǘ��I��+�͈͌���+�C�ӑI���F�R���g���[��
 * 
 * @author AIS
 */
public class TManagementSelectUnitController extends TController {

	/** �t�B�[���h */
	protected TManagementSelectUnit field;

	/**
	 * @param field �t�B�[���h
	 */
	public TManagementSelectUnitController(TManagementSelectUnit field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	protected void init() {
		setModel(getCompany());
		addEvent();
		cbo_itemStateChanged();
	}

	/**
	 * �R���{�{�b�N�X�A�����t�B�[���h�̏�����
	 * 
	 * @param company
	 */
	protected void setModel(Company company) {

		AccountConfig config = company.getAccountConfig();

		// �����Ȃ�
		field.cbo.addTextValueItem(ManagementAngle.NONE, getWord("C01748"));
		field.ctrlReferenceRangeUnits.add(new TNoneReferenceRangeUnit());

		// ����
		if (ClientConfig.isFlagOn("trans.management.use.department")) {
			field.cbo.addTextValueItem(ManagementAngle.DEPARTMENT, getWord("C00467"));
			field.ctrlReferenceRangeUnits.add(new TDepartmentReferenceRangeUnit());
		}

		// �����
		field.cbo.addTextValueItem(ManagementAngle.CUSTOMER, getWord("C00408"));
		field.ctrlReferenceRangeUnits.add(new TCustomerReferenceRangeUnit());

		if (ClientConfig.isFlagOn("trans.management.use.sum.tricode")) {
			// �W�v�����
			TCustomerReferenceRangeUnit sumRef = new TCustomerReferenceRangeUnit();
			sumRef.ctrlReferenceRange.ctrlCustomerReferenceFrom.getSearchCondition().setSearchSumCode(true);
			sumRef.ctrlReferenceRange.ctrlCustomerReferenceTo.getSearchCondition().setSearchSumCode(true);
			sumRef.ctrlOptionalSelector.getSearchCondition().setSearchSumCode(true);
			field.cbo.addTextValueItem(ManagementAngle.SUMCUSTOMER, getWord("C01149"));
			field.ctrlReferenceRangeUnits.add(sumRef);
		}
		// �Ј�
		field.cbo.addTextValueItem(ManagementAngle.EMPLOYEE, getWord("C00246"));
		field.ctrlReferenceRangeUnits.add(new TEmployeeReferenceRangeUnit());

		// �Ǘ�1
		if (config.isUseManagement1() && !ClientConfig.isFlagOn("trans.management.not.use.mng1")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT1, config.getManagement1Name());
			field.ctrlReferenceRangeUnits.add(new TManagement1ReferenceRangeUnit());
		}

		// �Ǘ�2
		if (config.isUseManagement2() && !ClientConfig.isFlagOn("trans.management.not.use.mng2")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT2, config.getManagement2Name());
			field.ctrlReferenceRangeUnits.add(new TManagement2ReferenceRangeUnit());
		}

		// �Ǘ�3
		if (config.isUseManagement3() && !ClientConfig.isFlagOn("trans.management.not.use.mng3")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT3, config.getManagement3Name());
			field.ctrlReferenceRangeUnits.add(new TManagement3ReferenceRangeUnit());
		}

		// �Ǘ�4
		if (config.isUseManagement4() && !ClientConfig.isFlagOn("trans.management.not.use.mng4")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT4, config.getManagement4Name());
			field.ctrlReferenceRangeUnits.add(new TManagement4ReferenceRangeUnit());
		}

		// �Ǘ�5
		if (config.isUseManagement5() && !ClientConfig.isFlagOn("trans.management.not.use.mng5")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT5, config.getManagement5Name());
			field.ctrlReferenceRangeUnits.add(new TManagement5ReferenceRangeUnit());
		}

		// �Ǘ�6
		if (config.isUseManagement6() && !ClientConfig.isFlagOn("trans.management.not.use.mng6")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT6, config.getManagement6Name());
			field.ctrlReferenceRangeUnits.add(new TManagement6ReferenceRangeUnit());
		}

		for (TReferenceRangeUnit rangeUnit : field.ctrlReferenceRangeUnits) {
			if (field.isBorder()) {
				rangeUnit.setLocation(15, 30);
			} else {
				rangeUnit.setLocation(0, 20);
			}
			field.add(rangeUnit);
		}

	}

	/**
	 * �C�x���g�ǉ�
	 */
	public void addEvent() {

		field.cbo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					cbo_itemStateChanged();
				}
			}
		});

	}

	/**
	 * �R���{�{�b�N�X�̃A�C�e���ύX
	 */
	protected void cbo_itemStateChanged() {
		for (TReferenceRangeUnit rangeUnit : field.ctrlReferenceRangeUnits) {
			rangeUnit.setVisible(false);
		}
		field.ctrlReferenceRangeUnits.get(field.cbo.getSelectedIndex()).setVisible(true);
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
	 * �I������Ă���Ǘ���Ԃ�
	 * 
	 * @return �I������Ă���Ǘ�
	 */
	public ManagementAngle getManagementAngle() {
		return (ManagementAngle) field.cbo.getSelectedItemValue();
	}

	/**
	 * �I������Ă���Ǘ�
	 * 
	 * @param angle �I������Ă���Ǘ�
	 */
	public void setManagementAngle(ManagementAngle angle) {
		field.cbo.setSelectedItemValue(angle);
	}

	/**
	 * �w�肳�ꂽ������Z�߂ĕԂ��B<br>
	 * �E�Ǘ��Ƃ��ĉ����I������Ă��邩<br>
	 * �E�J�n�R�[�h�A�I���R�[�h<br>
	 * �E�ʑI���R�[�h<br>
	 * ��Z�߂ĕԂ��B
	 * 
	 * @return �w�肳�ꂽ������Z�߂ĕԂ�
	 */
	public ManagementAngleSearchCondition getManagementAngleSearchCondition() {

		ManagementAngleSearchCondition condition = new ManagementAngleSearchCondition();

		// �����I������Ă��邩
		condition.setManagementAngle(getManagementAngle());

		if (ManagementAngle.NONE != condition.getManagementAngle()) {

			TReferenceRangeUnit unit = getReferenceRangeUnit();
			// �J�n�R�[�h
			condition.setCodeFrom(unit.getReferenceRange().getCodeFrom());

			// �I���R�[�h
			condition.setCodeTo(unit.getReferenceRange().getCodeTo());

			// �ʑI��
			condition.setOptionalCodes(unit.getOptionalSelector().getCodeList());

		}

		return condition;

	}

	/**
	 * �I������Ă���͈͌����t�B�[���h��Ԃ��B
	 * 
	 * @return �I������Ă���͈͌����t�B�[���h
	 */
	public TReferenceRangeUnit getReferenceRangeUnit() {
		return field.ctrlReferenceRangeUnits.get(field.cbo.getSelectedIndex());
	}

	/**
	 * �w�肳�ꂽ������ݒ肷��
	 * 
	 * @param condition �w�肳�ꂽ����
	 */
	public void setManagementAngleSearchCondition(ManagementAngleSearchCondition condition) {

		if (condition == null) {
			return;
		}

		try {

			// �����I������Ă��邩
			setManagementAngle(condition.getManagementAngle());

			if (ManagementAngle.NONE != condition.getManagementAngle()) {

				TReferenceRangeUnit unit = getReferenceRangeUnit();
				// �J�n�R�[�h
				unit.getReferenceRange().setCodeFrom(condition.getCodeFrom());
				unit.getReferenceRange().refleshEntityFrom();

				// �I���R�[�h
				unit.getReferenceRange().setCodeTo(condition.getCodeTo());
				unit.getReferenceRange().refleshEntityTo();

				// �ʑI��
				// unit.getOptionalSelector().setEntities(condition.getOptionalCodes()));
				unit.getOptionalSelector().setCodeList(condition.getOptionalCodes());

			}

		} catch (Exception ex) {
			// �G���[�Ȃ�
		}
	}

}

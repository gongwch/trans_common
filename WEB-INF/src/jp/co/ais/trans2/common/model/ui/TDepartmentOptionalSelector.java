package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * ����̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TDepartmentOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TDepartmentOptionalSelectorController controller;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^�[
	 */
	public TDepartmentOptionalSelector() {
		createController();
	}

	/**
	 * �R���g���[���쐬
	 */
	protected void createController() {
		controller = new TDepartmentOptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public DepartmentSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TDepartmentOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ����Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ����Entity�ꗗ
	 */
	public List<Department> getDepartmentEntities() {
		return controller.getDeptratmentEntities();
	}

	/**
	 * ����Entity�ꗗ��ݒ肷��
	 * 
	 * @param list ����Entity�ꗗ
	 */
	public void setDepartmentEntities(List<Department> list) {
		controller.setDeptratmentEntities(list);
	}

	/**
	 * true:�SSPC���[�h�̎擾
	 * 
	 * @return allCompanyMode true:�SSPC���[�h
	 */
	public boolean isAllCompanyMode() {
		return allCompanyMode;
	}

	/**
	 * true:�SSPC���[�h�̐ݒ�
	 * 
	 * @param allCompanyMode true:�SSPC���[�h
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {
		this.allCompanyMode = allCompanyMode;

		if (allCompanyMode) {
			getSearchCondition().setCompanyCode(null);
		} else {
			getSearchCondition().setCompanyCode(TLoginInfo.getCompany().getCode());
		}
	}

}

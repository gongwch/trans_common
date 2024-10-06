package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * �Ј��̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TEmployeeOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TEmployeeOptionalSelectorController controller;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^�[
	 */
	public TEmployeeOptionalSelector() {
		controller = createController();
	}

	/**
	 * @return �R���g���[���[
	 */
	protected TEmployeeOptionalSelectorController createController() {
		return new TEmployeeOptionalSelectorController(this);
	}

	@Override
	public TEmployeeOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public EmployeeSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �R�[�h���X�g�Z�b�g
	 * 
	 * @param codeList
	 */
	@Override
	public void setCodeList(List<String> codeList) {
		controller.setCodeList(codeList);
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

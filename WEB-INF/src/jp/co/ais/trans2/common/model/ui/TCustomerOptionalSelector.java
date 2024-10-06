package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * �����F�C�ӑI���F���C��
 * 
 * @author AIS
 */
public class TCustomerOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TCustomerOptionalSelectorController controller;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^
	 */
	public TCustomerOptionalSelector() {
		controller = createController();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return controller
	 */
	public TCustomerOptionalSelectorController createController() {
		return new TCustomerOptionalSelectorController(this);
	}

	/**
	 * ���������擾
	 * 
	 * @return ��������
	 */
	public CustomerSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �R���g���[���擾
	 * 
	 * @return controller
	 */
	@Override
	public TCustomerOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �G���e�B�e�B�擾
	 * 
	 * @return �G���e�B�e�B���X�g
	 */
	public List<Customer> getEntities() {
		return controller.getEntities();
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
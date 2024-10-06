package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * ��s�����̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TBankAccountOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TBankAccountOptionalSelectorController controller;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * 
	 */
	public TBankAccountOptionalSelector() {
		controller = createController();
	}

	/**
	 * @return TBankAccountOptionalSelectorController
	 */
	protected TBankAccountOptionalSelectorController createController() {
		return new TBankAccountOptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public BankAccountSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TBankAccountOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ��s����Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ��s����Entity�ꗗ
	 */
	public List<BankAccount> getEntities() {
		return controller.getEntities();
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

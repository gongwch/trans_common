package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ�1�̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TManagement1OptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TManagement1OptionalSelectorController controller;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^�[
	 */
	public TManagement1OptionalSelector() {
		controller = createController();
	}

	/**
	 * @return �R���g���[���[
	 */
	protected TManagement1OptionalSelectorController createController() {
		return new TManagement1OptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public Management1SearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TManagement1OptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�Ǘ�1Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ǘ�1Entity�ꗗ
	 */
	public List<Management1> getEntities() {
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

package jp.co.ais.trans2.common.model.ui;

import java.util.List;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.management.Management2;
import jp.co.ais.trans2.model.management.Management2SearchCondition;

/**
 * �Ǘ�2�̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TManagement2OptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TManagement2OptionalSelectorController controller;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^�[
	 */
	public TManagement2OptionalSelector() {
		controller = createController();
	}

	/**
	 * @return �R���g���[���[
	 */
	protected TManagement2OptionalSelectorController createController() {
		return new TManagement2OptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public Management2SearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TManagement2OptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�Ǘ�2Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ǘ�2Entity�ꗗ
	 */
	public List<Management2> getEntities() {
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

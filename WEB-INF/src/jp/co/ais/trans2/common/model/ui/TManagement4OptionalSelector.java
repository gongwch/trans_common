package jp.co.ais.trans2.common.model.ui;

import java.util.List;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.management.Management4;
import jp.co.ais.trans2.model.management.Management4SearchCondition;

/**
 * �Ǘ�4�̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TManagement4OptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TManagement4OptionalSelectorController controller;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^�[
	 */
	public TManagement4OptionalSelector() {
		controller = createController();
	}

	/**
	 * @return �R���g���[���[
	 */
	protected TManagement4OptionalSelectorController createController() {
		return new TManagement4OptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public Management4SearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TManagement4OptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�Ǘ�4Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ǘ�4Entity�ꗗ
	 */
	public List<Management4> getEntities() {
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

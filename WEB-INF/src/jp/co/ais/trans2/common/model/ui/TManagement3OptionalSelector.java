package jp.co.ais.trans2.common.model.ui;

import java.util.List;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.management.Management3;
import jp.co.ais.trans2.model.management.Management3SearchCondition;

/**
 * �Ǘ�3�̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TManagement3OptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TManagement3OptionalSelectorController controller;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^�[
	 */
	public TManagement3OptionalSelector() {
		controller = createController();
	}

	/**
	 * @return �R���g���[���[
	 */
	protected TManagement3OptionalSelectorController createController() {
		return new TManagement3OptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public Management3SearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TManagement3OptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�Ǘ�3Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ǘ�3Entity�ꗗ
	 */
	public List<Management3> getEntities() {
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

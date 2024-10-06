package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��Ђ̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TCompanyOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TCompanyOptionalSelectorController controller;

	/**
	 * �R���X�g���N�^�[
	 */
	public TCompanyOptionalSelector() {
		createController();
	}

	/**
	 * �R���g���[���쐬
	 */
	protected void createController() {
		controller = new TCompanyOptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public CompanySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TCompanyOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ���Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ���Entity�ꗗ
	 */
	public List<Company> getEntities() {
		return controller.getEntities();
	}

	/**
	 * ����Entity�ꗗ��ݒ肷��
	 * 
	 * @param list ����Entity�ꗗ
	 */
	@SuppressWarnings("unused")
	public void setEntities(List<Company> list) {
		// TODO
	}

}

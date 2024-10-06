package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��Ђ̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TCompanyReference extends TReference {

	/** �R���g���[�� */
	protected TCompanyReferenceController controller;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TCompanyReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^�[
	 */
	public TCompanyReference() {

		// �R���g���[������
		controller = createController();
	}

	/**
	 * �R���g���[���̍쐬
	 * 
	 * @return �R���g���[��
	 */
	protected TCompanyReferenceController createController() {
		return new TCompanyReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public CompanySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	public Company getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param company �G���e�B�e�B
	 */
	public void setEntity(Company company) {
		controller.setEntity(company);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.<br>
	 * �\�����X�V����
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}
}

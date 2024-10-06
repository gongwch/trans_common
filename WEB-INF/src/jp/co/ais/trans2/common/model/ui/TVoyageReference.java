package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.voyage.*;

/**
 * Voyage�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TVoyageReference extends TReference {

	/** �R���g���[�� */
	protected TVoyageReferenceController controller;

	/** ��ЃR�[�h */
	protected String companyCode;

	/**
	 * �R���X�g���N�^
	 */
	public TVoyageReference() {
		super();
		initController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public TVoyageReference(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �R���g���[������
	 */
	protected void initController() {
		// �R���g���[������
		if (controller == null) {
			controller = new TVoyageReferenceController(this);
		}
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
	public VoyageSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	public Voyage getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param voyage �G���e�B�e�B
	 */
	public void setEntity(Voyage voyage) {
		controller.setEntity(voyage);
	}

	/**
	 * ��ЃR�[�h��ݒ肵�܂��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * ��ЃR�[�h���擾���܂��B
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * ��ЃR�[�h���؂��邩�̎擾
	 * 
	 * @return verifyCompanyCode ��ЃR�[�h���؂��邩
	 */
	public boolean isVerifyCompanyCode() {
		return controller.isVerifyCompanyCode();
	}

	/**
	 * ��ЃR�[�h���؂��邩�̐ݒ�
	 * 
	 * @param verifyCompanyCode ��ЃR�[�h���؂��邩
	 */
	public void setVerifyCompanyCode(boolean verifyCompanyCode) {
		controller.setVerifyCompanyCode(verifyCompanyCode);
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

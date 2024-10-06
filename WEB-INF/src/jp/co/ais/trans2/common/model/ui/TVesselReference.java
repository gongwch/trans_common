package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * Vessel�̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class TVesselReference extends TReference {

	/** �R���g���[�� */
	protected TVesselReferenceController controller;

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ���D�_����ɓo�^�ς� true:���D�_����ɑ��݂���Vessel�̂݌����Ώ� */
	protected boolean isShipBuildEntry = false;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^
	 */
	public TVesselReference() {
		super();
		initController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public TVesselReference(String companyCode) {
		this(companyCode, false);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param isShipBuildEntry ���D�_��o�^�p��<br/>
	 *            true�F���D�_����ɑ��݂���Vessel�̂݌����̑ΏۂƂȂ�
	 */
	public TVesselReference(boolean isShipBuildEntry) {
		this(null, isShipBuildEntry);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param isShipBuildEntry ���D�_��o�^�p��<br/>
	 *            true�F���D�_����ɑ��݂���Vessel�̂݌����̑ΏۂƂȂ�
	 */
	public TVesselReference(String companyCode, boolean isShipBuildEntry) {
		this();
		this.isShipBuildEntry = isShipBuildEntry;
		this.companyCode = companyCode;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TVesselReference(TYPE type) {
		super(type);
		initController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public TVesselReference(TYPE type, String title) {
		super(type, title);
		initController();
	}

	/**
	 * �R���g���[������
	 */
	protected void initController() {
		// �R���g���[������
		if (controller == null) {
			controller = new TVesselReferenceController(this);
		}
	}

	/**
	 * �R���g���[���̃t�@�N�g��
	 */
	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public VesselSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I������Ă���G���e�B�e�B
	 * 
	 * @return �G���e�B�e�B
	 */
	public Vessel getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param vessel �G���e�B�e�B
	 */
	public void setEntity(Vessel vessel) {
		controller.setEntity(vessel);
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
	 * ���D�_����ɑ��݂���Vessel�̂݌����ΏۂƂ��邩
	 * 
	 * @return boolean
	 */
	public boolean isShipBuildEntry() {
		return this.isShipBuildEntry;
	}

	/**
	 * ���D�_����ɑ��݂���Vessel�̂݌����ΏۂƂ��邩
	 * 
	 * @param isShipBuildEntry true�F����
	 */
	public void setShipBuildEntry(boolean isShipBuildEntry) {
		this.isShipBuildEntry = isShipBuildEntry;
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

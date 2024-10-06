package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * ��Ќ����A�D�����O���[�v�R���|�[�l���g
 * 
 * @author AIS
 */
public class TCompanyVesselGroup extends TPanel {

	/** Company�t�B�[���h */
	public TCompanyReference ctrlCompany;

	/** Vessel�t�B�[���h */
	public TVesselReference ctrlVessel;

	/** �R���g���[�� */
	public TCompanyVesselGroupController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TCompanyVesselGroup() {
		this(false);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param isShipBuildEntry ���D�_��o�^�ςݑΏۂ��ǂ���<br>
	 *            true:���D�_��o�^�ς݂̑D�̂ݑΏہAfalse:�S�f�[�^�Ώ�
	 */
	public TCompanyVesselGroup(boolean isShipBuildEntry) {
		this(isShipBuildEntry, false);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param isShipBuildEntry ���D�_��o�^�ςݑΏۂ��ǂ���<br>
	 *            true:���D�_��o�^�ς݂̑D�̂ݑΏہAfalse:�S�f�[�^�Ώ�
	 * @param useDefaultSize true:�W���T�C�Y�����̂܂ܗ��p
	 */
	public TCompanyVesselGroup(boolean isShipBuildEntry, boolean useDefaultSize) {

		// �R���|�[�l���g������������
		initComponents(isShipBuildEntry);

		// �R���|�[�l���g��z�u����
		allocateComponents(useDefaultSize);
		controller = createController();
	}

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return TOwnerVesselGroupController
	 */
	public TCompanyVesselGroupController createController() {
		// �R���g���[������
		return new TCompanyVesselGroupController(this);
	}

	/**
	 * �R���|�[�l���g������������
	 * 
	 * @param isShipBuildEntry ���D�_��o�^�ς݂̂ݑΏۂ�
	 */
	protected void initComponents(boolean isShipBuildEntry) {
		ctrlCompany = new TCompanyReference();
		ctrlVessel = new TVesselReference(isShipBuildEntry);
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {
		allocateComponents(false);
	}

	/**
	 * �R���|�[�l���g��z�u����
	 * 
	 * @param useDefaultSize true:�W���T�C�Y�����̂܂ܗ��p
	 */
	protected void allocateComponents(boolean useDefaultSize) {

		setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();

		if (!useDefaultSize) {
			setButtonSize(125);
			setCodeSize(125);
			setNameSize(230);
		}

		// ���
		ctrlCompany.btn.setLangMessageID("C00053");
		gb.gridx = 0;
		gb.gridy = 0;
		gb.anchor = GridBagConstraints.WEST;
		gb.insets = new Insets(0, 0, 0, 0);
		add(ctrlCompany, gb);

		// Vessel
		ctrlVessel.btn.setLangMessageID("C00466");
		gb = new GridBagConstraints();
		gb.gridx = 0;
		gb.gridy = 1;
		gb.anchor = GridBagConstraints.WEST;
		gb.insets = new Insets(0, 0, 0, 0);
		add(ctrlVessel, gb);

		setSize(
			ctrlCompany.getWidth(),
			ctrlCompany.getHeight() + ctrlCompany.getInsets().top + ctrlCompany.getInsets().bottom
				+ ctrlVessel.getHeight() + ctrlVessel.getInsets().top + ctrlVessel.getInsets().bottom);

	}

	/**
	 * �{�^������ݒ肷��
	 * 
	 * @param width
	 */
	public void setButtonSize(int width) {
		ctrlCompany.setButtonSize(width);
		ctrlVessel.setButtonSize(width);
	}

	/**
	 * �R�[�h�̕���ݒ肷��
	 * 
	 * @param width
	 */
	public void setCodeSize(int width) {
		ctrlCompany.setCodeSize(width);
		ctrlVessel.setCodeSize(width);
	}

	/**
	 * ���̂̕���ݒ肷��
	 * 
	 * @param width
	 */
	public void setNameSize(int width) {
		ctrlCompany.setNameSize(width);
		ctrlVessel.setNameSize(width);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlCompany.setTabControlNo(tabControlNo);
		ctrlVessel.setTabControlNo(tabControlNo);
	}

	/**
	 * �I�����ꂽ�D��Ԃ�
	 * 
	 * @return �D���
	 */
	public Vessel getVesselEntity() {
		return controller.getVesselEntity();
	}

	/**
	 * �I�����ꂽCompany��Ԃ�
	 * 
	 * @return �I�����ꂽCompany
	 */
	public Company getCompanyEntity() {
		return controller.getCompanyEntity();
	}

	/**
	 * �D��ݒ肷��
	 * 
	 * @param bean
	 */
	public void setVesselEntity(Vessel bean) {
		controller.setVesselEntity(bean);
	}

	/**
	 * ��Ђ�ݒ肷��
	 * 
	 * @param bean Company
	 */
	public void setCompanyEntity(Company bean) {
		controller.setCompanyEntity(bean);
	}

	/**
	 * ����������getter
	 * 
	 * @return ��������
	 */
	public CompanySearchCondition getCompanySearchCondition() {
		return controller.getCompanySearchCondition();
	}

	/**
	 * �D�̌�������getter
	 * 
	 * @return ��������
	 */
	public VesselSearchCondition getVesselSearchCondition() {
		return controller.getVesselSearchCondition();
	}

	/**
	 * �N���A
	 */
	public void clear() {
		controller.clear();
	}

	/**
	 * �R�[���o�b�N���X�i�[�ݒ�
	 * 
	 * @param listener �R�[���o�b�N���X�i�[
	 */
	public void addCallBackListener(TCallBackListener listener) {
		ctrlCompany.addCallBackListener(listener);
		ctrlVessel.addCallBackListener(listener);
	}

	@Override
	public void requestFocus() {
		ctrlCompany.requestTextFocus();
	}

	/**
	 * ��ЃR�[�h���擾����
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return ctrlCompany.getCode();
	}

	/**
	 * �D�R�[�h���擾����
	 * 
	 * @return Vessel�R�[�h
	 */
	public String getVesselCode() {
		return ctrlVessel.getCode();
	}

	/**
	 * �D���̂��擾����
	 * 
	 * @return Vessel����
	 */
	public String getVesselName() {
		return ctrlVessel.getNames();
	}
}

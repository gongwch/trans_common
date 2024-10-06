package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;

/**
 * VESSEL�͈͌����t�B�[���h
 * 
 * @author AIS
 */
public class TVesselReferenceRange extends TReferenceRange {

	/** �J�n�t�B�[���h */
	public TVesselReference ctrlVesselReferenceFrom;

	/** �I���t�B�[���h */
	public TVesselReference ctrlVesselReferenceTo;

	/**
	 * �R���X�g���N�^
	 */
	public TVesselReferenceRange() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param isShipBuildEntry ���D�_��o�^�p��<br/>
	 *            true�F���D�_����ɑ��݂���Vessel�̂݌����̑ΏۂƂȂ�
	 */
	public TVesselReferenceRange(boolean isShipBuildEntry) {
		this();
		ctrlVesselReferenceFrom.setShipBuildEntry(isShipBuildEntry);
		ctrlVesselReferenceTo.setShipBuildEntry(isShipBuildEntry);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param isShipBuildEntry ���D�_��o�^�p��<br/>
	 *            true�F���D�_����ɑ��݂���Vessel�̂݌����̑ΏۂƂȂ�
	 */
	public TVesselReferenceRange(String companyCode, boolean isShipBuildEntry) {
		this();
		ctrlVesselReferenceFrom.setShipBuildEntry(isShipBuildEntry);
		ctrlVesselReferenceFrom.setCompanyCode(companyCode);
		ctrlVesselReferenceTo.setShipBuildEntry(isShipBuildEntry);
		ctrlVesselReferenceTo.setCompanyCode(companyCode);

	}

	@Override
	public void initComponents() {
		ctrlVesselReferenceFrom = new TVesselReference();
		ctrlVesselReferenceTo = new TVesselReference();
	}

	/**
	 * ������
	 */
	@Override
	protected void init() {

		ctrlVesselReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlVesselReferenceTo.getSearchCondition().setCodeFrom(ctrlVesselReferenceFrom.getCode());
			}
		});

		ctrlVesselReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlVesselReferenceFrom.getSearchCondition().setCodeTo(ctrlVesselReferenceTo.getCode());
			}
		});

	}

	@Override
	public TReference getFieldFrom() {
		return ctrlVesselReferenceFrom;
	}

	@Override
	public TReference getFieldTo() {
		return ctrlVesselReferenceTo;
	}

	/**
	 * �R�[�h���ݒ�
	 * 
	 * @param width
	 */
	public void setCodeSize(int width) {
		ctrlVesselReferenceFrom.setCodeSize(width);
		ctrlVesselReferenceTo.setCodeSize(width);
	}

	/**
	 * ���̃e�L�X�g���ݒ�
	 * 
	 * @param width
	 */
	public void setNameSize(int width) {
		ctrlVesselReferenceFrom.setNameSize(width);
		ctrlVesselReferenceTo.setNameSize(width);
	}

	/**
	 * �{�^�����ݒ�
	 * 
	 * @param width
	 */
	public void setButtonSize(int width) {
		ctrlVesselReferenceFrom.setButtonSize(width);
		ctrlVesselReferenceTo.setButtonSize(width);
	}

	/**
	 * ��ЃR�[�h��ݒ肷��
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		ctrlVesselReferenceFrom.setCompanyCode(companyCode);
		ctrlVesselReferenceTo.setCompanyCode(companyCode);
	}

}

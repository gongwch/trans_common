package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * ��Ќ����A�D�����̃O���[�v�R���|�[�l���g�R���g���[��
 * 
 * @author AIS
 */
public class TCompanyVesselGroupController extends TController {

	/** �t�B�[���h */
	protected TCompanyVesselGroup field;

	/**
	 * @param field �t�B�[���h
	 */
	public TCompanyVesselGroupController(TCompanyVesselGroup field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	protected void init() {

		// �C�x���g��`
		addEvent();

		clear();
	}

	/**
	 * CompanyVessel�̃C�x���g��`
	 */
	protected void addEvent() {

		// ��БI��
		field.ctrlCompany.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean isValid) {
				// �G���[������ꍇ�͑D���N���A����
				if (!isValid) {
					field.ctrlVessel.clear();
					field.ctrlVessel.setEditable(false);
					field.ctrlVessel.setCompanyCode(null);
					return;
				}
				// �l���ύX����Ă��Ȃ��ꍇ�͉������Ȃ�
				if (!field.ctrlCompany.isValueChanged()) {
					return;
				}
				if (Util.isNullOrEmpty(field.ctrlCompany.getCode())) {
					field.ctrlCompany.clear();
				}
				ctrlCompany_after();
			}
		});

	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �t�B�[���h���N���A����
	 */
	public void clear() {
		field.ctrlCompany.clear();
		ctrlCompany_after();
	}

	/**
	 * [��БI��]���̏���
	 */
	protected void ctrlCompany_after() {
		Company entity = field.ctrlCompany.getEntity();

		// ��Ђ��擾�����ꍇ�A�D�t�B�[���h����͉\�ɂ���B
		if (entity != null) {
			field.ctrlVessel.clear();
			field.ctrlVessel.setEditable(true);
			field.ctrlVessel.getSearchCondition().setCompanyCode(entity.getCode());
			field.ctrlVessel.setCompanyCode(entity.getCode());
		} else {
			field.ctrlVessel.clear();
			field.ctrlVessel.setEditable(false);
			field.ctrlVessel.setCompanyCode(null);
		}
	}

	/**
	 * �I�����ꂽ�D��Ԃ�
	 * 
	 * @return �I�����ꂽ�D
	 */
	public Vessel getVesselEntity() {

		// �I�����ꂽ�D���擾
		return field.ctrlVessel.getEntity();
	}

	/**
	 * �D��ݒ肷��
	 * 
	 * @param bean �D���
	 */
	public void setVesselEntity(Vessel bean) {
		field.ctrlVessel.setEntity(bean);
	}

	/**
	 * �I�����ꂽ��Ђ�Ԃ�
	 * 
	 * @return �I�����ꂽ���
	 */
	public Company getCompanyEntity() {
		return field.ctrlCompany.getEntity();
	}

	/**
	 * ��Ђ�ݒ肷��
	 * 
	 * @param bean ���
	 */
	public void setCompanyEntity(Company bean) {
		field.ctrlCompany.setEntity(bean);
		ctrlCompany_after();
	}

	/**
	 * Vessel����������getter
	 * 
	 * @return Vessel��������
	 */
	public VesselSearchCondition getVesselSearchCondition() {
		return field.ctrlVessel.getSearchCondition();
	}

	/**
	 * ��Ђ̌�������getter
	 * 
	 * @return ��Ђ̌�������
	 */
	public CompanySearchCondition getCompanySearchCondition() {
		return field.ctrlCompany.getSearchCondition();
	}

}

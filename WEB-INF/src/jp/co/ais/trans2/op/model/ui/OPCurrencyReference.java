package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * �ʉ݂̌����R���|�[�l���g
 * 
 * @author AIS
 */
public class OPCurrencyReference extends TCurrencyReference {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public OPCurrencyReference(String title) {
		super(title);
	}

	/**
	 * �R���X�g���N�^
	 */
	public OPCurrencyReference() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public OPCurrencyReference(TYPE type) {
		super(type);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public OPCurrencyReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	@Override
	protected void initComponents() {

		// �g���I�y���ʑΉ�
		if (isLabelMode()) {
			// �����I�Ƀ��x���ɂ���
			this.type = TYPE.LABEL;
		}

		super.initComponents();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	@Override
	protected void allocateComponents() {

		// �g���I�y���ʑΉ�
		if (isLabelMode()) {
			OPGuiUtil.allocateComponents(this);
		} else {
			super.allocateComponents();
		}
	}

	/**
	 * @return true:���x�����[�h
	 */
	protected boolean isLabelMode() {
		return OPGuiUtil.isLabelMode();
	}

	/**
	 * �R���g���[��
	 * 
	 * @return �R���g���[��
	 */
	@Override
	public OPCurrencyReferenceController createController() {
		return new OPCurrencyReferenceController(this);
	}

	@Override
	public OPCurrencyReferenceController getController() {
		return (OPCurrencyReferenceController) controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	@Override
	public CurrencySearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public Currency getEntity() {
		return getController().getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param bean �G���e�B�e�B
	 */
	@Override
	public void setEntity(Currency bean) {
		getController().setEntity(bean);
	}

	/**
	 * ���݂̓��͒l�ŃG���e�B�e�B���Đݒ肷��
	 */
	@Override
	public void refleshEntity() {
		getController().refleshEntity();
	}

	/**
	 * ����ݒ�
	 * 
	 * @param termDate
	 */
	public void setTermDate(Date termDate) {
		getController().setTermDate(termDate);
	}

}

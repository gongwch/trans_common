package jp.co.ais.trans2.op.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.payment.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �������������R���|�[�l���g
 * 
 * @author AIS
 */
public class OPPaymentSettingReference extends TPaymentSettingReference {

	/**
	 * �R���X�g���N�^.
	 */
	public OPPaymentSettingReference() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param isVisibleAccountNo �����ԍ���\�����邩�ǂ���
	 */
	public OPPaymentSettingReference(boolean isVisibleAccountNo) {
		super(isVisibleAccountNo);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public OPPaymentSettingReference(TYPE type) {
		super(type);
	}

	/**
	 * �R���|�[�l���g������������
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
			allocateOPComponents();
		} else {
			super.allocateComponents();
		}
	}

	/**
	 * 
	 */
	protected void allocateOPComponents() {

		OPGuiUtil.allocateComponents(this);

		GridBagConstraints gc = new GridBagConstraints();

		// �����ԍ�
		accountNo.setEditable(false);
		TGuiUtil.setComponentSize(accountNo, new Dimension(100, 20));
		gc.insets = new Insets(0, 0, 0, 0);
		add(accountNo, gc);

		resize();
	}

	/**
	 * @return true:���x�����[�h
	 */
	protected boolean isLabelMode() {
		return OPGuiUtil.isLabelMode();
	}

	/**
	 * �R���g���[���̍쐬
	 */
	@Override
	protected void createController() {
		controller = new OPPaymentSettingReferenceController(this);
	}

	@Override
	public OPPaymentSettingReferenceController getController() {
		return (OPPaymentSettingReferenceController) controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	@Override
	public PaymentSettingSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public PaymentSetting getEntity() {
		return getController().getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param bean �G���e�B�e�B
	 */
	@Override
	public void setEntity(PaymentSetting bean) {
		getController().setEntity(bean);
	}

	@Override
	public void refleshAndShowEntity() {
		getController().refleshEntity();
	}

	/**
	 * �T�C�Y�̍Ĕ��f
	 */
	@Override
	public void resize() {

		int width = (int) code.getPreferredSize().getWidth();
		if (btn.isVisible()) {
			width += (int) btn.getPreferredSize().getWidth();
		}
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}
		if (lbl.isVisible()) {
			width += (int) lbl.getPreferredSize().getWidth() + 5; // �E�]�����v�Z����ׂ�
		}
		if (accountNo.isVisible()) {
			width += (int) accountNo.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

}

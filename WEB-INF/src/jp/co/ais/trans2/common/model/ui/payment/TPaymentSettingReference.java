package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �x�����������R���|�[�l���g
 * 
 * @author AIS
 */
public class TPaymentSettingReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 5845285285425873551L;

	/** �R���g���[�� */
	protected TPaymentSettingReferenceController controller;

	/** ���� */
	public TTextField accountNo;

	/**
	 * �R���X�g���N�^.
	 */
	public TPaymentSettingReference() {
		this(false);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param isVisibleAccountNo �����ԍ���\�����邩�ǂ���
	 */
	public TPaymentSettingReference(boolean isVisibleAccountNo) {
		createController();
		accountNo.setVisible(isVisibleAccountNo);

		if (isVisibleAccountNo) {
			name.setMaximumSize(new Dimension(120, 20));
			name.setMinimumSize(new Dimension(120, 20));
			name.setPreferredSize(new Dimension(120, 20));
			this.resize();
		}

	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TPaymentSettingReference(TYPE type) {
		super(type);
		createController();
	}

	/**
	 * �R���g���[���̍쐬
	 */
	protected void createController() {
		controller = new TPaymentSettingReferenceController(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	@Override
	protected void initComponents() {

		super.initComponents();
		accountNo = new TTextField();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	@Override
	protected void allocateComponents() {

		super.allocateComponents();

		GridBagConstraints gc = new GridBagConstraints();

		// �����ԍ�
		accountNo.setEditable(false);
		TGuiUtil.setComponentSize(accountNo, new Dimension(100, 20));

		gc.insets = new Insets(0, 0, 0, 0);
		add(accountNo, gc);

		resize();
	}

	/**
	 * �T�C�Y�̍Ĕ��f
	 */
	@Override
	public void resize() {

		int width = (int) (btn.getPreferredSize().getWidth() + code.getPreferredSize().getWidth());
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}
		if (accountNo.isVisible()) {
			width += (int) accountNo.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public PaymentSettingSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public PaymentSetting getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param PaymentSetting �x������
	 */
	public void setEntity(PaymentSetting PaymentSetting) {
		controller.setEntity(PaymentSetting);
	}

	/**
	 * �����ԍ��̕��ύX
	 * 
	 * @param width ��
	 */
	public void setAccountNoSize(int width) {

		// �����ԍ�
		Dimension size = new Dimension(width, 20);
		accountNo.setSize(size);
		accountNo.setPreferredSize(size);
		accountNo.setMinimumSize(size);
		accountNo.setMaximumSize(size);
		resize();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.<br>
	 * �\�����X�V����
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
	}

}

package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.bank.*;

/**
 * ��s�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TBankReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 5845285285425873551L;

	/** �R���g���[�� */
	protected TBankReferenceController controller;

	/**
	 * �R���X�g���N�^.
	 */
	public TBankReference() {
		controller = new TBankReferenceController(this);

		this.resize();
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
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	@Override
	protected void allocateComponents() {
		super.allocateComponents();
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

		setSize(width, 20);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public BankSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Bank getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param BankAccount ��s
	 */
	public void setEntity(Bank BankAccount) {
		controller.setEntity(BankAccount);
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

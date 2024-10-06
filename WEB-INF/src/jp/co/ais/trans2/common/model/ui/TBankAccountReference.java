package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * ��s���������R���|�[�l���g
 * 
 * @author AIS
 */
public class TBankAccountReference extends TReference {

	/** �R���g���[�� */
	protected TBankAccountReferenceController controller;

	/** ���� */
	public TTextField accountNo;

	/** ����R�[�h */
	protected String deptCode;

	/** true:�SSPC���[�h */
	protected boolean allCompanyMode = false;

	/**
	 * �R���X�g���N�^.
	 */
	public TBankAccountReference() {
		this(false);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TBankAccountReference(TYPE type) {
		super(type);
		createController();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param isVisibleAccountNo �����ԍ���\�����邩�ǂ���
	 */
	public TBankAccountReference(boolean isVisibleAccountNo) {
		createController();
		accountNo.setVisible(isVisibleAccountNo);

		if (isVisibleAccountNo) {
			name.setMaximumSize(new Dimension(120, 20));
			name.setMinimumSize(new Dimension(120, 20));
			name.setPreferredSize(new Dimension(120, 20));
		}

		this.resize();
	}

	/**
	 * �R���g���[���̍쐬
	 */
	protected void createController() {
		controller = new TBankAccountReferenceController(this);
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
		accountNo.setMaximumSize(new Dimension(100, 20));
		accountNo.setMinimumSize(new Dimension(100, 20));
		accountNo.setPreferredSize(new Dimension(100, 20));
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
	public BankAccountSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public BankAccount getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param BankAccount ��s����
	 */
	public void setEntity(BankAccount BankAccount) {
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

	/**
	 * �v�㕔��R�[�h�̏����Z�b�g �A���J�[�J�X�^�}�C�Y
	 * 
	 * @param code ��s����
	 */
	public void setDepCode(String code) {
		if (controller == null) {
			return;
		}
		controller.setDepCode(code);
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

}

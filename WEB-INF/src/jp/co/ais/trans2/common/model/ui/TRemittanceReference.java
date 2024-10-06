package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * �����ړI�����R���|�[�l���g
 */
public class TRemittanceReference extends TReference {

	/** ����(���p�̂�) */
	public THalfAngleTextField halfName;

	/** ���ێ��x�R�[�h */
	public TTextField balanceCode;

	/** �R���g���[�� */
	protected TRemittanceReferenceController controller;

	/** �V�����ړI�}�X�^���g�����ǂ����Atrue:�g�� */
	protected static final boolean isUseNewRemittance = ClientConfig.isFlagOn("trans.new.mp0100.use");

	/**
	 * �R���X�g���N�^
	 */
	public TRemittanceReference() {
		this.controller = new TRemittanceReferenceController(this);
		this.resize();
	}

	/**
	 * �R���|�[�l���g������������
	 */
	@Override
	protected void initComponents() {
		super.initComponents();
		halfName = new THalfAngleTextField();
		balanceCode = new TTextField();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	@Override
	protected void allocateComponents() {

		GridBagConstraints gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setOpaque(false);

		// �{�^��
		TGuiUtil.setComponentSize(btn, new Dimension(80, 20));
		btn.setOpaque(false);

		// ���x��
		TGuiUtil.setComponentSize(lbl, new Dimension(80, 20));
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setOpaque(false);

		if (this.type == TYPE.BUTTON) {
			add(btn, gc);
			lbl.setVisible(false);
		} else {
			gc.insets = new Insets(0, 0, 0, 5);
			add(lbl, gc);
			btn.setVisible(false);
		}

		// �V�����ړI�R�[�h
		TGuiUtil.setComponentSize(code, new Dimension(80, 20));
		code.setMaxLength(getCodeLength());
		gc.insets = new Insets(0, 0, 0, 0);
		add(code, gc);
		code.setAllowedSpace(false);
		code.setImeMode(false);

		if (isUseNewRemittance) {
			// ���ێ��x�R�[�h
			balanceCode.setEditable(false);
			TGuiUtil.setComponentSize(balanceCode, new Dimension(80, 20));
			gc.insets = new Insets(0, 0, 0, 0);
			add(balanceCode, gc);

			name = halfName;
			name.setMaxLength(22);
		}

		// �V�����ړI����
		name.setEditable(false);
		TGuiUtil.setComponentSize(name, new Dimension(150, 20));
		gc.insets = new Insets(0, 0, 0, 0);
		add(name, gc);

		resize();
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	@Override
	public void setTabControlNo(int tabControlNo) {
		btn.setTabControlNo(tabControlNo);
		code.setTabControlNo(tabControlNo);
		balanceCode.setTabControlNo(tabControlNo);
		name.setTabControlNo(tabControlNo);
	}

	/**
	 * �\�����ꂽ���ێ��x�R�[�h��Ԃ�
	 * 
	 * @return ���ێ��x�R�[�h
	 */
	public String getBalanceCode() {
		return balanceCode.getText();
	}

	/**
	 * �\�����ꂽ���ێ��x�R�[�h���Z�b�g����
	 * 
	 * @param balance ���ێ��x�R�[�h
	 */
	public void setBalanceCode(String balance) {
		balanceCode.setText(balance);
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
		if (balanceCode.isVisible()) {
			width += (int) balanceCode.getPreferredSize().getWidth();
		}
		if (name.isVisible()) {
			width += (int) name.getPreferredSize().getWidth();
		}
		if (lbl.isVisible()) {
			width += (int) lbl.getPreferredSize().getWidth();
		}

		setSize(width, 20);
	}

	@Override
	public void setVisible(boolean bln) {
		btn.setVisible(bln);
		name.setVisible(bln);
		balanceCode.setVisible(bln);
		name.setVisible(bln);
		lbl.setVisible(bln);
		super.setVisible(bln);
	}

	/**
	 * ���ێ��x�R�[�h����ύX����
	 * 
	 * @param width ��
	 */
	public void setBalanceCodeSize(int width) {
		Dimension size = new Dimension(width, 20);
		balanceCode.setSize(size);
		balanceCode.setPreferredSize(size);
		balanceCode.setMinimumSize(size);
		balanceCode.setMaximumSize(size);
		resize();
	}

	/**
	 * �R�[�h�̒�����Ԃ��B�f�t�H���g���ƈقȂ錟���t�B�[���h�� ���Y���\�b�h��Override����B
	 * 
	 * @return �R�[�h��
	 */
	@Override
	protected int getCodeLength() {
		if (isUseNewRemittance) {
			return 10;
		}
		return 4;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public RemittanceSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Remittance getEntity() {
		return controller.getEntity();
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * Entity�Z�b�g
	 * 
	 * @param remittancePurpose Entity
	 */
	public void setEntity(Remittance remittancePurpose) {
		controller.setEntity(remittancePurpose);
	}
}

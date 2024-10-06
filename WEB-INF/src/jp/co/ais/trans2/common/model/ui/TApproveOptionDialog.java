package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;

/**
 * ���[�N�t���[���F��<br>
 * �����I���I�v�V����
 */
public class TApproveOptionDialog extends TDialog {

	/** �㕔�p�l�� */
	protected TPanel pnlTop;

	/** �����p�l�� */
	protected TPanel pnlMiddle;

	/** �{�^���p�l�� */
	protected TPanel pnlBottom;

	/** OK�{�^�� */
	protected TButton btnOK;

	/** �L�����Z���{�^�� */
	protected TButton btnCancel;

	/** �X�L�b�v�{�^�� */
	protected TCheckBox chkSkip;

	/** ��ʃR���g���[�� */
	protected TApproveOptionDialogCtrl controller;

	/** ���b�Z�[�W */
	TLabel lblCaption;

	/** ���� */
	public int option = JOptionPane.CANCEL_OPTION;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent
	 * @param mordal
	 */
	public TApproveOptionDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
		// �R���g���[��������
		initController();
	}

	@Override
	protected void init() {
		setLayout(new GridBagLayout());
		setResizable(true);
		setSize(450, 150);

	}

	/**
	 * �R���g���[��������
	 */
	protected void initController() {
		this.controller = new TApproveOptionDialogCtrl(this);
	}

	@Override
	public void initComponents() {
		pnlTop = new TPanel();
		pnlMiddle = new TPanel();
		pnlBottom = new TPanel();

		btnOK = new TButton();
		btnCancel = new TButton();

		lblCaption = new TLabel();
		chkSkip = new TCheckBox();

	}

	@Override
	public void allocateComponents() {
		// �L���v�V�����ݒ�
		initCaption();
		// �{�f�B��
		allocateBody();
		// �{�^����
		allocateButtons();
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		chkSkip.setTabControlNo(i++);
		btnOK.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
	}

	/**
	 * 
	 */
	protected void allocateButtons() {
		btnOK.setMinimumSize(new Dimension(100, 25));
		btnOK.setMaximumSize(new Dimension(100, 25));
		btnOK.setPreferredSize(new Dimension(100, 25));
		// �����{�^���������p�l���֔z�u
		gc.gridy = 0;
		gc.weightx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		pnlBottom.add(btnOK, gc);

		btnCancel.setMinimumSize(new Dimension(100, 25));
		btnCancel.setMaximumSize(new Dimension(100, 25));
		btnCancel.setPreferredSize(new Dimension(100, 25));
		btnCancel.setForClose(true);
		// �L�����Z���{�^���������p�l���֔z�u
		gc.gridx = 1;
		gc.insets = new Insets(0, 5, 0, 0);
		pnlBottom.add(btnCancel, gc);

	}

	/**
	 * �����L���v�V����������
	 */
	protected void initCaption() {
		lblCaption.setLangMessageID(getMessage("Q00004")); // �m�肵�܂��B��낵���ł����H
		chkSkip.setLangMessageID("�i�s�\�ȉӏ��܂ŏ��F����");

		btnOK.setText("<html>" + getWord("C10794") + "(<u>Y</u>)</html>"); // �͂�
		btnCancel.setText("<html>" + getWord("C10795") + "(<u>N</u>)</html>"); // ������
	}

	/**
	 * �{�f�B���z�u
	 */
	protected void allocateBody() {

		TGuiUtil.setComponentSize(pnlTop, 0, 30);
		pnlTop.setLayout(new GridBagLayout());

		// �㕔�p�l�������C���p�l���֔z�u
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1.0d;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(5, 0, 0, 0);
		add(pnlTop, gc);

		// �^�C�g�����x�����㕔�p�l���֔z�u
		gc.weightx = 0;
		pnlTop.add(lblCaption, gc);

		// �����p�l��
		gc.gridy += 1;
		TGuiUtil.setComponentSize(pnlMiddle, 0, 40);
		pnlMiddle.setLayout(null);
		add(pnlMiddle, gc);

		// �X�i�b�v�V���b�g�擾�v��
		TGuiUtil.setComponentSize(chkSkip, 200, 20);
		chkSkip.setLocation(80, 0);
		pnlMiddle.add(chkSkip);

		// ����
		pnlBottom.setLayout(new GridBagLayout());
		pnlBottom.setMinimumSize(new Dimension(0, 40));
		pnlBottom.setMaximumSize(new Dimension(0, 40));
		pnlBottom.setPreferredSize(new Dimension(0, 40));

		// �����p�l�������C���p�l���֔z�u
		gc.gridy += 1;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlBottom, gc);

	}

	/**
	 * OK�{�^������������
	 * 
	 * @return true:OK
	 */
	public boolean isOK() {
		return option == JOptionPane.OK_OPTION;
	}

	/**
	 * �\�Ȍ��菳�F��i�߂邩
	 * 
	 * @return true:�i�߂�
	 */
	public boolean isApproveAsMuchAsPossible() {
		return chkSkip.isSelected();
	}
}

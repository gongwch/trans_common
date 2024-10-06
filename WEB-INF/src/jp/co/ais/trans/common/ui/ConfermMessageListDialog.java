package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable.AutoSizeType;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * �m�F�t�����b�Z�[�W�ꗗ�\���_�C�A���O
 */
public class ConfermMessageListDialog extends TDialog {

	/** �L�����Z���I�� */
	public static int CANCEL_OPTION = 0;

	/** OK�I�� */
	public static int OK_OPTION = 1;

	/** �I�v�V���� */
	protected int option = CANCEL_OPTION;

	/** �\�����b�Z�[�W */
	protected List<String[]> messageList;

	/** �㕔�p�l�� */
	protected TPanel pnlTop;

	/** �^�C�g�����x�� */
	protected TLabel lblMessage;

	/** �����p�l�� */
	protected TPanel pnlMiddle;

	/** ���b�Z�[�W */
	protected TTable tblMessage;

	/** �{�^���p�l�� */
	protected TPanel pnlBottom;

	/** OK */
	protected TButton btnOK;

	/** Cancel */
	protected TButton btnCancel;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 */
	public ConfermMessageListDialog(Frame parent) {
		super(parent, true);
		init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�_�C�A���O
	 */
	public ConfermMessageListDialog(Dialog parent) {
		super(parent, true);
		init();
	}

	/**
	 * ���b�Z�[�W�̃Z�b�g
	 * 
	 * @param msgList
	 */
	public void setMessageList(List<String[]> msgList) {
		this.messageList = msgList;
	}

	/**
	 * ��������
	 */
	protected void init() {

		// ��ʍ\�z
		initComponents();

		// �e�[�u���̐ݒ�
		initTable();

		// �_�C�A���O������
		super.initDialog();

		// �t�H�[�J�X
		btnOK.requestFocusInWindow();

		// IME�֎~
		btnOK.enableInputMethods(false);
		btnCancel.enableInputMethods(false);
		enableInputMethods(false);
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {

		// �T�C�Y�����\
		setResizable(true);

		// ���C�A�E�g��`
		setLayout(new GridBagLayout());
		setSize(680, 360);

		// �����\��
		this.setLocationRelativeTo(null);

		// GridBagConstraints
		GridBagConstraints gc;

		// ������
		pnlTop = new TPanel();
		lblMessage = new TLabel();
		pnlMiddle = new TPanel();
		tblMessage = new TTable();
		pnlBottom = new TPanel();
		btnOK = new TButton();
		btnCancel = new TButton();

		// �㕔
		pnlTop.setLayout(new GridBagLayout());
		pnlTop.setMinimumSize(new Dimension(0, 40));
		pnlTop.setMaximumSize(new Dimension(0, 40));
		pnlTop.setPreferredSize(new Dimension(0, 40));

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
		pnlTop.add(lblMessage, gc);

		// ����
		pnlMiddle.setLayout(new GridBagLayout());
		pnlMiddle.setMinimumSize(new Dimension(0, 200));
		pnlMiddle.setMaximumSize(new Dimension(0, 200));
		pnlMiddle.setPreferredSize(new Dimension(0, 200));
		pnlMiddle.setFocusable(false);

		// �����p�l�������C���p�l���֔z�u
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, 40, 5, 5);
		add(pnlMiddle, gc);

		// �X�v���b�h�𒆕��p�l���֔z�u
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 0, 0);
		pnlMiddle.add(tblMessage, gc);

		// ����
		pnlBottom.setLayout(new GridBagLayout());
		pnlBottom.setMinimumSize(new Dimension(0, 40));
		pnlBottom.setMaximumSize(new Dimension(0, 40));
		pnlBottom.setPreferredSize(new Dimension(0, 40));

		// �����p�l�������C���p�l���֔z�u
		gc.gridy = 2;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlBottom, gc);

		// �����{�^��
		btnOK.setText("<html>" + getWord("C10794") + "(<u>Y</u>)</html>"); // �͂�
		btnOK.setMinimumSize(new Dimension(100, 25));
		btnOK.setMaximumSize(new Dimension(100, 25));
		btnOK.setPreferredSize(new Dimension(100, 25));
		btnOK.setTabControlNo(1);
		btnOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				option = OK_OPTION;
				setVisible(false);
			}
		});

		// �����{�^���������p�l���֔z�u
		gc.gridy = 0;
		gc.weightx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		pnlBottom.add(btnOK, gc);

		// �L�����Z���{�^��
		btnCancel.setText("<html>" + getWord("C10795") + "(<u>N</u>)</html>"); // ������
		btnCancel.setMinimumSize(new Dimension(100, 25));
		btnCancel.setMaximumSize(new Dimension(100, 25));
		btnCancel.setPreferredSize(new Dimension(100, 25));
		btnCancel.setTabControlNo(2);
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				option = CANCEL_OPTION;
				setVisible(false);
			}
		});

		// �L�����Z���{�^���������p�l���֔z�u
		gc.gridx = 1;
		gc.insets = new Insets(0, 5, 0, 0);
		pnlBottom.add(btnCancel, gc);

		// �{�^���C�ӕύX
		String[] btnWords = ClientConfig.getYesNoButtonWords();

		if (btnWords != null) {
			btnOK.setLangMessageID(btnWords[0]);
			btnCancel.setLangMessageID(btnWords[1]);
		}

		// �V���[�g�J�b�g
		btnOK.getActionMap().put("yes", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				option = OK_OPTION;
				setVisible(false);
			}
		});

		// �V���[�g�J�b�g
		btnCancel.getActionMap().put("no", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				option = CANCEL_OPTION;
				setVisible(false);
			}
		});

		InputMap yesMap = btnOK.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		yesMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.ALT_MASK), "yes");

		InputMap noMap = btnCancel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		noMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.ALT_MASK), "no");
	}

	/**
	 * �e�[�u���̏�����
	 */
	protected void initTable() {
		tblMessage.setFocusable(false);
		tblMessage.setRowColumnWidth(0);
		tblMessage.setHeaderRowHeight(0);

		tblMessage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * �\��
	 * 
	 * @param title �^�C�g�����b�Z�[�W
	 * @return �I��l
	 */
	public int show(String title) {
		this.lblMessage.setText(title);

		// �ꗗ�ɐݒ肷��f�[�^���쐬
		boolean hasSub = false;

		for (String[] msg : messageList) {
			hasSub |= !Util.isNullOrEmpty(msg[0]);
		}

		// ���̃X�^�C���ݒ�
		if (hasSub) {
			tblMessage.addColumn("", 75);
			tblMessage.addColumn("", 255);

		} else {
			tblMessage.addColumn("", 0);
			tblMessage.addColumn("", 310);
		}

		// �f�[�^���ꗗ�ɐݒ�
		for (String[] msg : messageList) {
			tblMessage.addRow(msg);
		}

		tblMessage.autosizeColumnWidth(AutoSizeType.HeaderAndContents);

		this.setVisible(true);

		return option;
	}
}
package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable.AutoSizeType;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * ���b�Z�[�W�ꗗ�\���_�C�A���O
 */
public class MessageListDialog extends TDialog {

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

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 */
	public MessageListDialog(Frame parent) {
		super(parent, true);

		init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�_�C�A���O
	 */
	public MessageListDialog(Dialog parent) {
		super(parent, true);

		init();
	}

	/**
	 * ���b�Z�[�W���X�g
	 * 
	 * @param msgList ���b�Z�[�W���X�g
	 */
	public void setMessageList(List<String> msgList) {

		List<String[]> list = new ArrayList<String[]>(msgList.size());

		for (String msg : msgList) {
			list.add(new String[] { "", msg });
		}

		this.messageList = list;
	}

	/**
	 * ���b�Z�[�W���X�g
	 * 
	 * @param msgList ���b�Z�[�W���X�g
	 */
	public void setMessagesList(List<String[]> msgList) {
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
		String btnWord = ClientConfig.getOkButtonText();
		if (Util.isNullOrEmpty(btnWord)) {
			btnWord = "C04284";
		}
		btnOK.setLangMessageID(btnWord);
		btnOK.setMinimumSize(new Dimension(100, 25));
		btnOK.setMaximumSize(new Dimension(100, 25));
		btnOK.setPreferredSize(new Dimension(100, 25));
		btnOK.setTabControlNo(1);
		btnOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				// ��ʂ����
				setVisible(false);
			}
		});

		// �����{�^���������p�l���֔z�u
		gc.gridy = 0;
		gc.weightx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		pnlBottom.add(btnOK, gc);
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
	 */
	public void show(String title) {
		if (Util.isNullOrEmpty(title)) {
			this.pnlTop.setVisible(false);
		} else {
			this.lblMessage.setText(title);
		}

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
	}
}
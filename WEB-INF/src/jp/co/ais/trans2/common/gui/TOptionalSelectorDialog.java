package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �C�ӑI��(�ʎw��)�R���|�[�l���g�̃_�C�A���O
 * 
 * @author AIS
 */
public abstract class TOptionalSelectorDialog extends jp.co.ais.trans.common.gui.TDialog {

	/** �I����⃊�X�g */
	public TTable tblList;

	/** �I�����ꂽ���X�g */
	public TTable tblSelectedList;

	/** �����p�l�� */
	public TPanel pnlCenter;

	/** �e�[�u���I���{�^�� */
	public TButton btnTableSelect;

	/** �e�[�u���L�����Z���{�^�� */
	public TButton btnTableCancel;

	/** �����p�l�� */
	public TPanel pnlSearch;

	/** ���� �R�[�h */
	public TTextField txtSearchCode;
	/** ���� ���� */
	public TTextField txtSearchName;

	/** �����p�l�� */
	public TPanel pnlBottom;

	/** �m��{�^�� */
	public TButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnBack;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent
	 * @param mordal
	 */
	public TOptionalSelectorDialog(Frame parent, boolean mordal) {

		super(parent, mordal);

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		initDialog();

	}

	/**
	 * �R���|�[�l���g�̏����ݒ�
	 */
	public void initComponents() {
		tblList = new TTable();
		pnlCenter = new TPanel();
		btnTableSelect = new TButton();
		btnTableCancel = new TButton();
		tblSelectedList = new TTable();
		pnlSearch = new TPanel();
		txtSearchCode = new TTextField();
		txtSearchName = new TTextField();
		pnlBottom = new TPanel();
		btnSettle = new TButton();
		btnBack = new TButton();
	}

	/**
	 * �R���|�[�l���g�̔z�u���s���܂��B
	 */
	public void allocateComponents() {

		setSize(840, 600);
		this.setResizable(true);

		setLayout(new GridBagLayout());

		// �I�����ꗓ
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 0.45d;
		gc.weighty = 0.95d;
		gc.insets = new Insets(10, 10, 10, 0);
		gc.fill = GridBagConstraints.BOTH;
		add(tblList, gc);

		// �����p�l��
		pnlCenter.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.weightx = 0.10d;
		gc.weighty = 0.95d;
		gc.gridx = 1;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlCenter, gc);

		// �I���{�^��
		btnTableSelect.setPreferredSize(new Dimension(60, 20));
		btnTableSelect.setText(getWord("C10139")); // ��
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 30, 0);
		pnlCenter.add(btnTableSelect, gc);

		// �I������{�^��
		btnTableCancel.setPreferredSize(new Dimension(60, 20));
		btnTableCancel.setText(getWord("C10140")); // ��
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.insets = new Insets(30, 0, 0, 0);
		pnlCenter.add(btnTableCancel, gc);

		// �I�����ꂽ�ꗗ
		gc = new GridBagConstraints();
		gc.weightx = 0.45d;
		gc.weighty = 0.95d;
		gc.gridx = 2;
		gc.insets = new Insets(10, 0, 10, 10);
		gc.fill = GridBagConstraints.BOTH;
		add(tblSelectedList, gc);

		// �����p�l��
		pnlSearch.setLayout(new GridBagLayout());
		pnlSearch.setPreferredSize(new Dimension(0, 20));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		gc.gridwidth = 3;
		add(pnlSearch, gc);

		// �R�[�h
		int codeLength = 100;
		txtSearchCode.setMinimumSize(new Dimension(codeLength, 20));
		txtSearchCode.setPreferredSize(new Dimension(codeLength, 20));
		txtSearchCode.setImeMode(false);
		int marginX = tblList.getRowColumnWidth() + 10;
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, marginX, 0, 0);
		
		pnlSearch.add(txtSearchCode, gc);

		// ����
		int namesLength = 200;
		txtSearchName.setMinimumSize(new Dimension(namesLength, 20));
		txtSearchName.setPreferredSize(new Dimension(namesLength, 20));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);

		gc.weightx = 0.1d;
		gc.weighty = 0.1d;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlSearch.add(txtSearchName, gc);

		// �����p�l��
		pnlBottom.setLayout(new GridBagLayout());
		pnlBottom.setPreferredSize(new Dimension(0, 30));
		gc = new GridBagConstraints();
		gc.gridy = 2;
		gc.gridwidth = 3;
		gc.weighty = 0.05d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBottom, gc);

		// �m��{�^��
		btnSettle.setPreferredSize(new Dimension(120, 25));
		btnSettle.setLangMessageID("C01019"); // �m��
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 10);
		pnlBottom.add(btnSettle, gc);

		// �߂�{�^��
		btnBack.setPreferredSize(new Dimension(120, 25));
		btnBack.setLangMessageID("C01747"); // �߂�
		btnBack.setShortcutKey(KeyEvent.VK_F12);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 10, 0, 0);
		pnlBottom.add(btnBack, gc);

		tblList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblSelectedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		tblList.addSpreadSheetSelectChange(btnTableSelect);
		tblSelectedList.addSpreadSheetSelectChange(btnTableCancel);

	}

}

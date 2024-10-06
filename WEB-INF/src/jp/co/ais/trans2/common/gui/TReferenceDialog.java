package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �����_�C�A���O�̊��
 * 
 * @author AIS
 */
public class TReferenceDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = -7281962661789452420L;

	/** �ꗗ */
	public TTable tbl;

	/** �R�[�h */
	public TTextField code;

	/** ���� */
	public TTextField names;

	/** �������� */
	public TTextField namek;

	/** �����{�^�� */
	public TButton btnSearch;

	/** �m��{�^�� */
	public TButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnBack;

	/** ��Џ�� */
	public Company company;

	/** �����p�l�� */
	public TPanel pnlSearch;

	/** �{�^���p�l�� */
	public TPanel pnlButton;

	/** �R���g���[�� */
	protected TReferenceController controller = null;

	/** 3�J�����̕\�� */
	protected boolean show3rdColumn = true;

	/** �����_�C�A���O�̗�ԍ���` */
	public enum SC {
		/** Bean */
		bean,
		/** �R�[�h */
		code,
		/** ���� */
		names,
		/** �������� */
		namek
	}

	/**
	 * 3�J�����̕\��
	 * 
	 * @return boolean
	 */
	public boolean isShow3rdColumn() {
		return show3rdColumn;
	}

	/**
	 * 3�J�����̕\��
	 * 
	 * @param show3rdColumn
	 */
	public void setShow3rdColumn(boolean show3rdColumn) {
		this.show3rdColumn = show3rdColumn;
	}

	/**
	 * @param controller �R���g���[��
	 */
	public TReferenceDialog(TReferenceController controller) {
		super(controller.field.getParentFrame(), true);
		this.controller = controller;

		setShow3rdColumn(controller.isShow3rdColumn());

		initComponents();

		try {
			// �e�[�u���̂ݐ�ɕ���
			Class c = this.getClass();
			tbl.restoreComponent(new TDefaultStorableKey(c, c.getField("tbl")));

		} catch (Exception ex) {
			ClientErrorHandler.handledException(ex);
		}

		allocateComponents();

		setTabIndex();
		initDialog();
	}

	/**
	 * �R���|�[�l���g������
	 */
	protected void initComponents() {
		tbl = new TTable();
		tbl.getTableHeader().setReorderingAllowed(false);
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.code, getWord(controller.getCodeCaption()), controller.getCodeColumnSize());

		if (this.show3rdColumn) {
			tbl.addColumn(SC.names, getWord(controller.getNamesCaption()), controller.getNamesColumnSize(),
				controller.getNamesColumnAlignment());
			tbl.addColumn(SC.namek, getWord(controller.getNamekCaption()), controller.getNamekColumnSize(),
				controller.getNamekColumnAlignment());

		} else {
			tbl.addColumn(SC.names, controller.getNameCaption(), controller.getNamesColumnSize(),
				controller.getNamesColumnAlignment());
			tbl.addColumn(SC.namek, controller.getNamekCaption(), 0, controller.getNamekColumnAlignment());
		}

		code = new TTextField();
		names = new TTextField();
		namek = new TTextField();
		btnSearch = new TButton();
		btnSettle = new TButton();
		btnBack = new TButton();

		pnlSearch = new TPanel();
		pnlButton = new TPanel();
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	protected void allocateComponents() {

		setLayout(new GridBagLayout());
		setResizable(true);

		setPreferredSize(new Dimension(650, 520));

		setTitle(controller.getDialogTitle());

		// �ꗗ
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		add(tbl, gc);
		tbl.addSpreadSheetSelectChange(btnSettle);

		// ���������w��t�B�[���h
		pnlSearch.setLayout(new GridBagLayout());
		pnlSearch.setMinimumSize(new Dimension(0, 20));
		pnlSearch.setPreferredSize(new Dimension(0, 20));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		add(pnlSearch, gc);

		// �R�[�h����
		TTableInformation ti = tbl.getTableInformation();
		int codeLength = ti.getWidthList().get(SC.code.ordinal());
		code.setMinimumSize(new Dimension(codeLength, 20));
		code.setPreferredSize(new Dimension(codeLength, 20));
		code.setImeMode(false);
		int marginX = tbl.getRowColumnWidth() + 10;
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, marginX, 0, 0);

		pnlSearch.add(code, gc);

		// ���̌���
		int namesLength = ti.getWidthList().get(SC.names.ordinal());
		names.setMinimumSize(new Dimension(namesLength, 20));
		names.setPreferredSize(new Dimension(namesLength, 20));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		pnlSearch.add(names, gc);

		// ��������
		int namekLength = ti.getWidthList().get(SC.namek.ordinal());
		namek.setMinimumSize(new Dimension(namekLength, 20));
		namek.setPreferredSize(new Dimension(namekLength, 20));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.NORTHWEST;

		pnlSearch.add(namek, gc);
		if (!show3rdColumn) {
			namek.setVisible(true);
		}

		// �{�^���t�B�[���h
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMinimumSize(new Dimension(0, 40));
		pnlButton.setPreferredSize(new Dimension(0, 40));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 2;
		add(pnlButton, gc);

		// �����{�^��
		btnSearch.setPreferredSize(new Dimension(120, 25));
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		pnlButton.add(btnSearch);
		gc = new GridBagConstraints();
		pnlButton.add(btnSearch, gc);

		// �m��{�^��
		btnSettle.setPreferredSize(new Dimension(120, 25));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		pnlButton.add(btnSettle, gc);

		// �߂�{�^��
		btnBack.setPreferredSize(new Dimension(120, 25));
		btnBack.setLangMessageID("C01747");
		btnBack.setShortcutKey(KeyEvent.VK_F12);
		gc = new GridBagConstraints();
		pnlButton.add(btnBack, gc);

		pack();
	}

	/**
	 * Tab���Z�b�g
	 */
	protected void setTabIndex() {
		int i = 0;
		code.setTabControlNo(i++);
		names.setTabControlNo(i++);
		namek.setTabControlNo(i++);
		if (ClientConfig.isFlagOn("trans.ref.table.focusable")) {
			tbl.setTabControlNo(i++);
			tbl.setEnterToButton(true);
		}
		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnBack.setTabControlNo(i++);
	}
}

package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TReference.*;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ����K�w�}�X�^�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0051DepartmentHierarchySelectionMasterPanel extends TMainPanel {

	/** �R���g���[���N���X */
	public MG0051DepartmentHierarchySelectionMasterPanelCtrl ctrl;

	/** �V�K�{�^�� */
	public TImageButton btnNew;

	/** ���ʃ{�^�� */
	public TImageButton btnCopy;

	/** �폜�{�^�� */
	public TImageButton btnDelete;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** �m�� */
	public TImageButton btnSettle;

	/** �g�D���̐ݒ� */
	public TImageButton btnOrganizationName;

	/** �V�K�쐬�p�l�� */
	public TPanel pnlDetail1;

	/** �g�D�R�[�h */
	public TDepartmentOrganizationCodeComboBox ctrlOrganizationCode;

	/** �g�D���� */
	public TTextField ctrlOrganizationName;

	/** ���x���O */
	public TDepartmentReference ctrlDepartment;

	/** ��ʕ���{�^�� */
	public TButton btnUpperDepartment;

	/** ���ʕ���{�^�� */
	public TButton btnLowerDepartment;

	/** ����ꗗ�p�l�� */
	public TPanel pnlDetail2;

	/** ���僊�X�g�p�l�� */
	public TPanel pnlDepartmentList;

	/** ����ꗗ�i���X�v���b�h�j */
	public TTable ssDepartmentList;

	/** ���x�����X�g�p�l�� */
	public TPanel pnlLabelList;

	/** ����{�^���p�l�� */
	public TPanel pnlButtonDepartment;

	/** ����ǉ��{�^�� */
	public TButton btnDepartmentAdd;

	/** ����폜�{�^�� */
	public TButton btnDepartmentCancellation;

	/** ����K�w�ꗗ�i�E�X�v���b�h�j */
	public TTable ssHierarchyList;

	/** ���X�g�^�u */
	public TTabbedPane listTab;

	/** BodyHeader�p�l�� */
	public TPanel pnlBodyHeader;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panelCtrl �R���g���[��
	 */
	public MG0051DepartmentHierarchySelectionMasterPanel(MG0051DepartmentHierarchySelectionMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * ����ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum DS1 {
		/** ����R�[�h */
		code,
		/** ���嗪�� */
		name,
		/** ����G���e�B�e�B */
		bean,
	}

	/**
	 * ����K�w�ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum DS2 {
		/** ����R�[�h */
		codeDep,
		/** ���x�� */
		level,
		/** ���x���P */
		level1,
		/** ���x��2 */
		level2,
		/** ���x��3 */
		level3,
		/** ���x��4 */
		level4,
		/** ���x��5 */
		level5,
		/** ���x��6 */
		level6,
		/** ���x��7 */
		level7,
		/** ���x��8 */
		level8,
		/** ���x��9 */
		level9,
		/** ����G���e�B�e�B */
		bean2,
	}

	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		btnSettle = new TImageButton(IconType.SETTLE);
		btnOrganizationName = new TImageButton();
		pnlDetail1 = new TPanel();
		ctrlOrganizationCode = new TDepartmentOrganizationCodeComboBox(true);
		ctrlOrganizationName = new TTextField();
		ctrlDepartment = new TDepartmentReference(TYPE.LABEL, "C00722");
		btnUpperDepartment = new TButton();
		btnLowerDepartment = new TButton();
		pnlDetail2 = new TPanel();
		pnlDepartmentList = new TPanel();
		ssDepartmentList = new TTable();
		pnlLabelList = new TPanel();
		ssHierarchyList = new TTable();
		pnlButtonDepartment = new TPanel();
		btnDepartmentAdd = new TButton();
		btnDepartmentCancellation = new TButton();
		pnlBodyHeader = new TPanel();

		// ����ꗗ�i���X�v���b�h�j
		ssDepartmentList = new TTable();
		ssDepartmentList.addColumn(DS1.code, "C00698", 100, SwingConstants.LEFT); // ����R�[�h
		ssDepartmentList.addColumn(DS1.name, "C00723", 250, SwingConstants.LEFT); // ���喼��
		ssDepartmentList.addColumn(DS1.bean, "", -1);

		// ����K�w�ꗗ�i�E�X�v���b�h�j
		ssHierarchyList = new TTable();
		ssHierarchyList.addColumn(DS2.codeDep, "C00698", 120, SwingConstants.LEFT); // ����R�[�h
		ssHierarchyList.addColumn(DS2.level, "C01739", 50, SwingConstants.RIGHT); // ���x��
		ssHierarchyList.addColumn(DS2.level1, "C02126", 90, SwingConstants.LEFT); // ���x��1
		ssHierarchyList.addColumn(DS2.level2, "C02127", 90, SwingConstants.LEFT); // ���x��2
		ssHierarchyList.addColumn(DS2.level3, "C02128", 90, SwingConstants.LEFT); // ���x��3
		ssHierarchyList.addColumn(DS2.level4, "C02129", 90, SwingConstants.LEFT); // ���x��4
		ssHierarchyList.addColumn(DS2.level5, "C02130", 90, SwingConstants.LEFT); // ���x��5
		ssHierarchyList.addColumn(DS2.level6, "C02131", 90, SwingConstants.LEFT); // ���x��6
		ssHierarchyList.addColumn(DS2.level7, "C02132", 90, SwingConstants.LEFT); // ���x��7
		ssHierarchyList.addColumn(DS2.level8, "C02133", 90, SwingConstants.LEFT); // ���x��8
		ssHierarchyList.addColumn(DS2.level9, "C02134", 90, SwingConstants.LEFT); // ���x��9
		ssHierarchyList.addColumn(DS2.bean2, "", -1);
	}

	@Override
	public void allocateComponents() {

		int x = 5;

		// �V�K�{�^��
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 130);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �폜�{�^��
		x = btnNew.getX() + btnNew.getWidth() + 5;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 130);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �G�N�Z���{�^��
		x = btnDelete.getX() + btnDelete.getWidth() + 5;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �m��{�^��
		x = btnExcel.getX() + btnExcel.getWidth() + 5;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setSize(25, 130);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �g�D���̐ݒ�{�^��
		x = btnSettle.getX() + btnSettle.getWidth() + 5;
		btnOrganizationName.setLangMessageID("C11968");
		btnOrganizationName.setSize(25, 130);
		btnOrganizationName.setLocation(x, HEADER_Y);
		pnlHeader.add(btnOrganizationName);

		// BodyHeader�p�l��
		pnlBodyHeader.setLayout(null);
		TGuiUtil.setComponentSize(pnlBodyHeader, 0, 45);
		gc = new GridBagConstraints();
		gc.weightx = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 3;
		gc.insets = new Insets(5, 10, 5, 0);
		pnlBody.add(pnlBodyHeader, gc);

		x = 0;
		int y = 0;

		gc = new GridBagConstraints();
		gc.weightx = 10d;
		gc.weighty = 5;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;

		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, 10, 5, 0);

		// �g�D�R�[�h
		ctrlOrganizationCode.setLangMessageID("C00335");
		ctrlOrganizationCode.setSize(210, 20);
		ctrlOrganizationCode.setLocation(x, y);
		pnlBodyHeader.add(ctrlOrganizationCode);

		// �g�D����
		ctrlOrganizationName.setLocation(x + 187, y);
		ctrlOrganizationName.setSize(250, 20);
		ctrlOrganizationName.setEditable(false);
		pnlBodyHeader.add(ctrlOrganizationName);

		y += ctrlOrganizationCode.getHeight();

		// ���x���O
		ctrlDepartment.setEditable(false);
		ctrlDepartment.setLocation(x + 2, y);
		ctrlDepartment.setNameSize(300);
		pnlBodyHeader.add(ctrlDepartment);

		// ��ʕ���{�^��
		btnUpperDepartment.setLangMessageID("C00719");
		btnUpperDepartment.setShortcutKey(KeyEvent.VK_F7);
		btnUpperDepartment.setSize(25, 130);
		btnUpperDepartment.setLocation(500, 10);
		pnlBodyHeader.add(btnUpperDepartment);

		// ���ʕ���{�^��
		btnLowerDepartment.setLangMessageID("C00720");
		btnLowerDepartment.setShortcutKey(KeyEvent.VK_F8);
		btnLowerDepartment.setSize(25, 130);
		btnLowerDepartment.setLocation(650, 10);
		pnlBodyHeader.add(btnLowerDepartment);

		// ����ꗗ�i���X�v���b�h�j
		ssDepartmentList.setMaximumSize(new Dimension(260, 0));
		ssDepartmentList.setMinimumSize(new Dimension(260, 0));
		ssDepartmentList.setPreferredSize(new Dimension(260, 0));

		gc = new GridBagConstraints();
		gc.weightx = 0.2d;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 10, 45, 0);
		pnlBody.add(ssDepartmentList, gc);
		ssDepartmentList.addSpreadSheetSelectChange(btnDepartmentAdd);

		// ����ǉ�
		btnDepartmentAdd.setLangMessageID("C03827");
		btnDepartmentAdd.setShortcutKey(KeyEvent.VK_F2);
		btnDepartmentAdd.setMaximumSize(new Dimension(100, 25));
		btnDepartmentAdd.setMinimumSize(new Dimension(100, 25));
		btnDepartmentAdd.setPreferredSize(new Dimension(100, 25));

		gc = new GridBagConstraints();
		gc.weightx = 0.1d;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 100, 10);
		pnlBody.add(btnDepartmentAdd, gc);

		// ����폜
		btnDepartmentCancellation.setLangMessageID("C03828");
		btnDepartmentCancellation.setShortcutKey(KeyEvent.VK_F3);
		btnDepartmentCancellation.setMaximumSize(new Dimension(100, 25));
		btnDepartmentCancellation.setMinimumSize(new Dimension(100, 25));
		btnDepartmentCancellation.setPreferredSize(new Dimension(100, 25));

		gc = new GridBagConstraints();
		gc.weightx = 0.1d;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 10, 10);
		pnlBody.add(btnDepartmentCancellation, gc);

		// ����K�w�ꗗ�i�E�X�v���b�h�j
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 0, 45, 10);
		pnlBody.add(ssHierarchyList, gc);
		ssHierarchyList.addSpreadSheetSelectChange(btnDepartmentCancellation);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlOrganizationCode.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		ctrlOrganizationCode.setTabControlNo(i++);
		btnUpperDepartment.setTabControlNo(i++);
		btnLowerDepartment.setTabControlNo(i++);
		btnDepartmentAdd.setTabControlNo(i++);
		btnDepartmentCancellation.setTabControlNo(i++);
		ssDepartmentList.setTabControlNo(i++);
		ssHierarchyList.setTabControlNo(i++);

	}

}

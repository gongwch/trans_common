package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TReference.TYPE;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ����K�w�}�X�^�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0050DepartmentHierarchyMasterPanel extends TMainPanel {

	/** �R���g���[���N���X */
	public MG0050DepartmentHierarchyMasterPanelCtrl ctrl;

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

	/** �V�K�쐬�p�l�� */
	public TPanel pnlNew;

	/** �ꗗ�p�l�� */
	public TPanel pnlTable;

	/** �ꗗ */
	public TDnDTable dndTable;

	/** �g�D�R�[�h */
	public TDepartmentOrganizationComboBox ctrlOrganizationCode;

	/** ���x���O */
	public TDepartmentReference ctrlDepartment;

	/** �R�����g�p */
	public TLabel ctrlComment;

	/** ���X�g�^�u */
	public TTabbedPane listTab;

	/** �c���[ */
	public TDnDTree tree;

	/** �c���[�p�l�� */
	public JScrollPane sp;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panelCtrl �R���g���[��
	 */
	public MG0050DepartmentHierarchyMasterPanel(MG0050DepartmentHierarchyMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * �ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** ����R�[�h */
		code,
		/** ���嗪�� */
		name,
		/** ����G���e�B�e�B */
		bean,
	}

	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		btnSettle = new TImageButton(IconType.SETTLE);
		pnlNew = new TPanel();
		ctrlOrganizationCode = new TDepartmentOrganizationComboBox(true);
		ctrlDepartment = new TDepartmentReference(TYPE.LABEL, "C00722");
		ctrlComment = new TLabel();
		listTab = new TTabbedPane();
		tree = new TDnDTree();
		sp = new JScrollPane();
		pnlTable = new TPanel();

		// TTable�̋@�\�g���N���X
		dndTable = new TDnDTable();
		dndTable.addColumn(SC.code, "C00698", 120); // ����R�[�h
		dndTable.addColumn(SC.name, "C00724", 260); // ���嗪��
		dndTable.addColumn(SC.bean, "", 0);
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

		// ���ʃ{�^��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 130);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜�{�^��
		x = btnCopy.getX() + btnCopy.getWidth() + 5;
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
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 130);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �㕔
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyTop, gc);

		// �V�K�쐬�p�l��
		pnlNew.setLayout(null);

		TGuiUtil.setComponentSize(pnlNew, 0, 80);
		gc = new GridBagConstraints();
		gc.weightx = 1.0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 2;

		pnlBodyTop.add(pnlNew, gc);

		x = 0;
		int y = 10;

		// �g�D�R�[�h
		ctrlOrganizationCode.setLangMessageID("C00335");
		ctrlOrganizationCode.setLabelSize(80);
		ctrlOrganizationCode.setLocation(x, y);
		pnlNew.add(ctrlOrganizationCode);

		y += ctrlOrganizationCode.getHeight();

		// ���x���O
		ctrlDepartment.resize();
		TGuiUtil.setComponentSize(ctrlDepartment, 315, 20);
		ctrlDepartment.setEditable(false);
		ctrlDepartment.setLocation(x, y);
		pnlNew.add(ctrlDepartment);

		// �R�����g
		TGuiUtil.setComponentSize(ctrlComment, 475, 20);
		ctrlComment.setLangMessageID("C11607");
		ctrlComment.setForeground(Color.blue);
		ctrlComment.setLocation(180, 60);
		pnlNew.add(ctrlComment);

		// ���X�g�^�u
		listTab.switchMode();
		listTab.setOpaque(false);

		TGuiUtil.setComponentSize(sp, 270, 400);
		sp.getViewport().add(tree, null);
		listTab.addTab(getWord("C02473"), sp);
		listTab.setSelectedIndex(0);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 30, 10, 30);

		pnlBodyTop.add(listTab, gc);

		// �ꗗ
		dndTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		TGuiUtil.setComponentSize(pnlTable, 274, 400);
		pnlTable.setLayout(new BoxLayout(pnlTable, BoxLayout.X_AXIS));

		pnlTable.add(dndTable);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);

		pnlBodyTop.add(pnlTable, gc);
	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlOrganizationCode.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		dndTable.setTabControlNo(i++);
	}

}

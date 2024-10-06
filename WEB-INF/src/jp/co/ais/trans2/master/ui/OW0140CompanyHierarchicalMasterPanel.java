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
 * ��ЊK�w�}�X�^�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class OW0140CompanyHierarchicalMasterPanel extends TMainPanel {

	/** �R���g���[���N���X */
	public OW0140CompanyHierarchicalMasterPanelCtrl ctrl;

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
	public TCompanyOrganizationCodeComboBox ctrlOrganizationCode;

	/** �g�D���� */
	public TTextField ctrlOrganizationName;

	/** ���x���O */
	public TCompanyReference ctrlCompany;

	/** ��ʉ�Ѓ{�^�� */
	public TButton btnUpperCompany;

	/** ���ʉ�Ѓ{�^�� */
	public TButton btnLowerCompany;

	/** ��Јꗗ�p�l�� */
	public TPanel pnlDetail2;

	/** ��Ѓ��X�g�p�l�� */
	public TPanel pnlCompanyList;

	/** ��Јꗗ�i���X�v���b�h�j */
	public TTable ssCompanyList;

	/** ���x�����X�g�p�l�� */
	public TPanel pnlLabelList;

	/** ��Ѓ{�^���p�l�� */
	public TPanel pnlButtonCompany;

	/** ��Вǉ��{�^�� */
	public TButton btnCompanyAdd;

	/** ��Ѝ폜�{�^�� */
	public TButton btnCompanyCancellation;

	/** ��ЊK�w�ꗗ�i�E�X�v���b�h�j */
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
	public OW0140CompanyHierarchicalMasterPanel(OW0140CompanyHierarchicalMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * ��Јꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum CS1 {
		/** ��ЃR�[�h */
		code,
		/** ��З��� */
		name,
		/** ��ЃG���e�B�e�B */
		bean,
	}

	/**
	 * ��ЊK�w�ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum CS2 {
		/** ��ЃR�[�h */
		codeCmp,
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
		/** ��ЃG���e�B�e�B */
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
		ctrlOrganizationCode = new TCompanyOrganizationCodeComboBox(true);
		ctrlOrganizationName = new TTextField();
		ctrlCompany = new TCompanyReference(TYPE.LABEL);
		btnUpperCompany = new TButton();
		btnLowerCompany = new TButton();
		pnlDetail2 = new TPanel();
		pnlCompanyList = new TPanel();
		ssCompanyList = new TTable();
		pnlLabelList = new TPanel();
		ssHierarchyList = new TTable();
		pnlButtonCompany = new TPanel();
		btnCompanyAdd = new TButton();
		btnCompanyCancellation = new TButton();
		pnlBodyHeader = new TPanel();

		// ��Јꗗ�i���X�v���b�h�j
		ssCompanyList = new TTable();
		ssCompanyList.addColumn(CS1.code, "COW042", 100, SwingConstants.LEFT); // ��ЃR�[�h
		ssCompanyList.addColumn(CS1.name, "C00685", 250, SwingConstants.LEFT); // ��Ж���
		ssCompanyList.addColumn(CS1.bean, "", -1);

		// ��ЊK�w�ꗗ�i�E�X�v���b�h�j
		ssHierarchyList = new TTable();
		ssHierarchyList.addColumn(CS2.codeCmp, "COW042", 120, SwingConstants.LEFT); // ��ЃR�[�h
		ssHierarchyList.addColumn(CS2.level, "C01739", 50, SwingConstants.RIGHT); // ���x��
		ssHierarchyList.addColumn(CS2.level1, "C02126", 90, SwingConstants.LEFT); // ���x��1
		ssHierarchyList.addColumn(CS2.level2, "C02127", 90, SwingConstants.LEFT); // ���x��2
		ssHierarchyList.addColumn(CS2.level3, "C02128", 90, SwingConstants.LEFT); // ���x��3
		ssHierarchyList.addColumn(CS2.level4, "C02129", 90, SwingConstants.LEFT); // ���x��4
		ssHierarchyList.addColumn(CS2.level5, "C02130", 90, SwingConstants.LEFT); // ���x��5
		ssHierarchyList.addColumn(CS2.level6, "C02131", 90, SwingConstants.LEFT); // ���x��6
		ssHierarchyList.addColumn(CS2.level7, "C02132", 90, SwingConstants.LEFT); // ���x��7
		ssHierarchyList.addColumn(CS2.level8, "C02133", 90, SwingConstants.LEFT); // ���x��8
		ssHierarchyList.addColumn(CS2.level9, "C02134", 90, SwingConstants.LEFT); // ���x��9
		ssHierarchyList.addColumn(CS2.bean2, "", -1);
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
		ctrlCompany.setEditable(false);
		ctrlCompany.setLocation(x + 2, y);
		ctrlCompany.setNameSize(300);
		pnlBodyHeader.add(ctrlCompany);

		// ��ʉ�Ѓ{�^��
		btnUpperCompany.setLangMessageID("C01487");
		btnUpperCompany.setShortcutKey(KeyEvent.VK_F7);
		btnUpperCompany.setSize(25, 130);
		btnUpperCompany.setLocation(500, 10);
		pnlBodyHeader.add(btnUpperCompany);

		// ���ʉ�Ѓ{�^��
		btnLowerCompany.setLangMessageID("C01488");
		btnLowerCompany.setShortcutKey(KeyEvent.VK_F8);
		btnLowerCompany.setSize(25, 130);
		btnLowerCompany.setLocation(650, 10);
		pnlBodyHeader.add(btnLowerCompany);

		// ��Јꗗ�i���X�v���b�h�j
		ssCompanyList.setMaximumSize(new Dimension(260, 0));
		ssCompanyList.setMinimumSize(new Dimension(260, 0));
		ssCompanyList.setPreferredSize(new Dimension(260, 0));

		gc = new GridBagConstraints();
		gc.weightx = 0.2d;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 10, 45, 0);
		pnlBody.add(ssCompanyList, gc);
		ssCompanyList.addSpreadSheetSelectChange(btnCompanyAdd);

		// ��Вǉ�
		btnCompanyAdd.setLangMessageID("C10543");
		btnCompanyAdd.setShortcutKey(KeyEvent.VK_F2);
		btnCompanyAdd.setMaximumSize(new Dimension(100, 25));
		btnCompanyAdd.setMinimumSize(new Dimension(100, 25));
		btnCompanyAdd.setPreferredSize(new Dimension(100, 25));

		gc = new GridBagConstraints();
		gc.weightx = 0.1d;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 100, 10);
		pnlBody.add(btnCompanyAdd, gc);

		// ��Ѝ폜
		btnCompanyCancellation.setLangMessageID("C10544");
		btnCompanyCancellation.setShortcutKey(KeyEvent.VK_F3);
		btnCompanyCancellation.setMaximumSize(new Dimension(100, 25));
		btnCompanyCancellation.setMinimumSize(new Dimension(100, 25));
		btnCompanyCancellation.setPreferredSize(new Dimension(100, 25));

		gc = new GridBagConstraints();
		gc.weightx = 0.1d;
		gc.weighty = 1;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 10, 10);
		pnlBody.add(btnCompanyCancellation, gc);

		// ��ЊK�w�ꗗ�i�E�X�v���b�h�j
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
		ssHierarchyList.addSpreadSheetSelectChange(btnCompanyCancellation);

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
		btnUpperCompany.setTabControlNo(i++);
		btnLowerCompany.setTabControlNo(i++);
		btnCompanyAdd.setTabControlNo(i++);
		btnCompanyCancellation.setTabControlNo(i++);
		ssCompanyList.setTabControlNo(i++);
		ssHierarchyList.setTabControlNo(i++);

	}

}

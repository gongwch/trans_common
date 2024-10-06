package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ���F�������[���}�X�^�̎w�����
 */
public class MG0510AprvRoleGroupMasterPanel extends TMainPanel {

	/** �㕔 */
	public TPanel pnlBodyTop;

	/** ���� */
	public TPanel pnlBodyButtom;

	/** �V�K�{�^�� */
	public TImageButton btnNew;

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �ҏW�{�^�� */
	public TImageButton btnModify;

	/** ���ʃ{�^�� */
	public TImageButton btnCopy;

	/** �폜�{�^�� */
	public TImageButton btnDelete;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** �͈͌����R���|�[�l���g */
	public TAprvRoleGroupReferenceRange ctrlSearch;

	/** ���������p�l�� */
	public TPanel pnlSearchCondition;

	/** �L�����Ԑ؂��\�����邩 */
	public TCheckBox ctrlOutputTermEnd;

	/** ���F�������[���}�X�^�ꗗ */
	public TTable tbl;

	/**
	 * �ꗗ�̃J������`
	 */
	public enum SC {

		/** BEAN */
		bean,

		/** ���[���R�[�h */
		roleCode,

		/** ���[������ */
		roleName,

		/** ���[������ */
		roleNames,

		/** ���[���������� */
		roleNamek,

		/** �J�n�N���� */
		strDate,

		/** �I���N���� */
		endDate;
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company
	 */
	public MG0510AprvRoleGroupMasterPanel(Company company) {
		super(company);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		// �V�K�{�^��
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �����{�^��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �ҏW�{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// ���ʃ{�^��
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜�{�^��
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �G�N�Z���{�^��
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �㕔
		pnlBodyTop.setLayout(null);
		TGuiUtil.setComponentSize(pnlBodyTop, 0, 100);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ���������p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// �͈͌����R���|�[�l���g
		ctrlSearch.setLocation(20, 20);
		// ctrlSearch
		pnlSearchCondition.add(ctrlSearch);

		// �L�����Ԑ؂�\��
		ctrlOutputTermEnd.setLangMessageID("C11089");
		ctrlOutputTermEnd.setSize(140, 20);
		ctrlOutputTermEnd.setLocation(360, 40);
		pnlSearchCondition.add(ctrlOutputTermEnd);

		// ����
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl, gc);
	}

	@Override
	public void initComponents() {
		pnlBodyTop = new TPanel();
		pnlBodyButtom = new TPanel();
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		ctrlSearch = new TAprvRoleGroupReferenceRange();
		ctrlOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.bean, "BEAN", -1);
		tbl.addColumn(SC.roleCode, "C12230", 100); // ���F�O���[�v�R�[�h
		tbl.addColumn(SC.roleName, "C12231", 200); // ���F�O���[�v����
		tbl.addColumn(SC.roleNames, "C12232", 200); // ���F�O���[�v����
		tbl.addColumn(SC.roleNamek, "C12233", 200); // ���F�O���[�v��������
		tbl.addColumn(SC.strDate, "C00055", 100, SwingConstants.CENTER); // �J�n�N����
		tbl.addColumn(SC.endDate, "C00261", 100, SwingConstants.CENTER); // �I���N����
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlSearch.setTabControlNo(i++);
		ctrlOutputTermEnd.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
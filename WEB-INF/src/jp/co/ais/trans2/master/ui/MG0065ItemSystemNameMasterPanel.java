package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * MG0065ItemSystemMaster - �Ȗڑ̌n���̃}�X�^ - Main Panel Class
 * 
 * @author AIS
 */
public class MG0065ItemSystemNameMasterPanel extends TMainPanel {

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

	/** �㕔 */
	public TPanel pnlBodyTop;

	/** �Ȗڑ̌n���͈̔͌����p�l�� */
	public TPanel pnlSearchCondition;

	/** �Ȗڑ̌n���͈̔͌��� */
	public TItemOrganizationReferenceRange ctrlItemOrgRan;

	/** �J�n���x�� */
	public TLabel lblStart;

	/** �I�����x�� */
	public TLabel lblEnd;

	/** ���� */
	public TPanel pnlBodyBottom;

	/** �Ȗڑ̌n���̃}�X�^�ꗗ */
	public TTable tbList;

	/** �ꗗ�̃J������` */
	public enum SC {
		/** Entity */
		ENTITY

		/** �Ȗڑ̌n�R�[�h */
		, CODE

		/** �Ȗڑ̌n���� */
		, NAME

		/** �Ȗڑ̌n���� */
		, NAME_S

		/** �Ȗڑ̌n�������� */
		, NAME_K

	}

	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlBodyTop = new TPanel();
		pnlSearchCondition = new TPanel();
		lblStart = new TLabel();
		lblEnd = new TLabel();
		ctrlItemOrgRan = new TItemOrganizationReferenceRange();
		pnlBodyBottom = new TPanel();

		tbList = new TTable();
		tbList.addColumn(SC.ENTITY, "", -1);
		tbList.addColumn(SC.CODE, getWord("C00617"), 120);
		tbList.addColumn(SC.NAME, getWord("C00618"), 200);
		tbList.addColumn(SC.NAME_S, getWord("C00619"), 200);
		tbList.addColumn(SC.NAME_K, getWord("C00620"), 250);

	}

	@Override
	public void allocateComponents() {

		// �V�K�{�^��
		int x = HEADER_LEFT_X;
		btnNew.setSize(25, 110);
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �����{�^��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setSize(25, 110);
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �ҏW�{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setSize(25, 110);
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbList.addSpreadSheetSelectChange(btnModify);

		// ���ʃ{�^��
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setSize(25, 110);
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜�{�^��
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setSize(25, 110);
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �G�N�Z���{�^��
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setSize(25, 130);
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �㕔
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// �Ȗڑ̌n���͈̔͌����p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setSize(430, 75);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlBodyTop.add(pnlSearchCondition);

		// �J�n���x��
		lblStart.setLangMessageID("C01012");// �J�n
		lblStart.setSize(60, 20);
		lblStart.setLocation(20, 20);
		pnlSearchCondition.add(lblStart);

		// �I�����x��
		lblEnd.setLangMessageID("C01143");// �I��
		lblEnd.setSize(60, 20);
		lblEnd.setLocation(20, 40);
		pnlSearchCondition.add(lblEnd);

		// �Ȗڑ̌n���͈̔�
		ctrlItemOrgRan.ctrlItemOrReferenceFrom.btn.setLangMessageID("C00609");// �Ȗڑ̌n
		ctrlItemOrgRan.ctrlItemOrgReferenceTo.btn.setLangMessageID("C00609");
		ctrlItemOrgRan.setLocation(70, 20);
		pnlSearchCondition.add(ctrlItemOrgRan);

		// ����
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbList, gc);
	}

	@Override
	// Tab����`
	public void setTabIndex() {
		int i = 1;
		ctrlItemOrgRan.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
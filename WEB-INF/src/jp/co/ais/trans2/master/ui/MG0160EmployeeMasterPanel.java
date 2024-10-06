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
 * MG0160EmployeeMaster - �Ј��}�X�^ - Main Panel Class
 * 
 * @author AIS
 */
public class MG0160EmployeeMasterPanel extends TMainPanel {

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

	/** �Ј��͈͌����p�l�� */
	public TPanel pnlEmpRefRan;

	/** �Ј��͈͌��� */
	public TEmployeeReferenceRange ctrlEmpRefRan;

	/** �L�����Ԑ؂� */
	public TCheckBox chkTerm;

	/** ���� */
	public TPanel pnlBodyBottom;

	/** �Ј��}�X�^�ꗗ */
	public TTable tbList;

	/** �ꗗ�̃J������` */
	public enum SC {

		/** �Ј��R�[�h */
		CODE,

		/** �Ј����� */
		NAME,

		/** �Ј����� */
		NAME_S,

		/** �Ј��������� */
		NAME_K,

		/** �U�o��s�����R�[�h */
		CBK_CODE,

		/** �U�o��s�������� */
		CBK_NAME,

		/** �U����s�R�[�h */
		BNK_CODE,

		/** �U����s���� */
		BNK_NAME,

		/** �U���x�X�R�[�h */
		STN_CODE,

		/** �U���x�X���� */
		STN_NAME,

		/** �U���������a����� */
		KOZA_KBN,

		/** �U�������ԍ� */
		YKN_NO,

		/** �������`�J�i */
		YKN_KANA,

		/** �J�n�N���� */
		DATE_FROM,

		/** �I���N���� */
		DATE_TO,

		/** Entity */
		ENTITY
	}

	@SuppressWarnings("static-access")
	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlBodyTop = new TPanel();
		pnlEmpRefRan = new TPanel();
		ctrlEmpRefRan = new TEmployeeReferenceRange();
		chkTerm = new TCheckBox();
		pnlBodyBottom = new TPanel();

		tbList = new TTable();
		tbList.addColumn(SC.CODE, getWord("C00697"), 120);
		tbList.addColumn(SC.NAME, getWord("C00807"), 200);
		tbList.addColumn(SC.NAME_S, getWord("C00808"), 200);
		tbList.addColumn(SC.NAME_K, getWord("C00809"), 250);
		tbList.addColumn(SC.CBK_CODE, getWord("C00810"), 120);
		tbList.addColumn(SC.CBK_NAME, getWord("C00475"), 200);
		tbList.addColumn(SC.BNK_CODE, getWord("C00811"), 120);
		tbList.addColumn(SC.BNK_NAME, getWord("C02470"), 200);
		tbList.addColumn(SC.STN_CODE, getWord("C00812"), 120);
		tbList.addColumn(SC.STN_NAME, getWord("C00473"), 200);
		tbList.addColumn(SC.KOZA_KBN, getWord("C00471"), 100, 0);
		tbList.addColumn(SC.YKN_NO, getWord("C00813"), 100);
		tbList.addColumn(SC.YKN_KANA, getWord("C01068"), 200);
		tbList.addColumn(SC.DATE_FROM, getWord("C00055"), 100, 0);
		tbList.addColumn(SC.DATE_TO, getWord("C00261"), 100, 0);
		tbList.addColumn(SC.ENTITY, "", -1, false);
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

		// �Ј��͈̓p�l��
		pnlEmpRefRan.setLayout(null);
		pnlEmpRefRan.setSize(600, 75);
		pnlEmpRefRan.setLangMessageID("C01060");
		pnlEmpRefRan.setLocation(30, 10);
		pnlBodyTop.add(pnlEmpRefRan);

		// �Ј��͈�
		ctrlEmpRefRan.setLocation(20, 20);
		pnlEmpRefRan.add(ctrlEmpRefRan);

		// ���b�Z�[�W�ꗗ�Ƀf�[�^���Ȃ��B�ǉ��K�v ##########
		// �L�����Ԑ؂�\��
		chkTerm.setLangMessageID("C11089");
		chkTerm.setSize(140, 20);
		chkTerm.setLocation(360, 40);
		pnlEmpRefRan.add(chkTerm);

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
		ctrlEmpRefRan.setTabControlNo(i++);
		chkTerm.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
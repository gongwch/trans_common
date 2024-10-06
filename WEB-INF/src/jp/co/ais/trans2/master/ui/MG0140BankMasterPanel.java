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

/**
 * ��s�}�X�^�̎w�����
 */
public class MG0140BankMasterPanel extends TMainPanel {

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

	/** ��s�����t�B�[���h */
	public TBankReference ctrlBank;

	/** �x�X�����t�B�[���h */
	public TBranchReferenceRange ctrlBranch;

	/** ���������p�l�� */
	public TPanel pnlSearchCondition;

	/** �ꗗ */
	public TTable tbl;

	/** �L�����Ԑ؂��\�����邩 */
	public TCheckBox chkOutputTermEnd;

	/**
	 * �ꗗ�̃J������`
	 */
	public enum SC {

		/** ��s�R�[�h */
		BnkCode,

		/** ��s�x�X�R�[�h */
		BranchCode,

		/** ��s���� */
		BnkName,

		/** ��s�J�i���� */
		BnkKana,

		/** ��s�������� */
		BnkNamek,

		/** ��s�x�X���� */
		BranchName,

		/** ��s�x�X�J�i���� */
		BranchKana,

		/** ��s�x�X�������� */
		BranchNamek,

		/** �J�n�N���� */
		DateFrom,

		/** �I���N���� */
		DateTo
	}

	/**
	 * �R���X�g���N�^
	 */
	public MG0140BankMasterPanel() {
		super();
	}

	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		ctrlBank = new TBankReference();
		ctrlBranch = new TBranchReferenceRange();
		pnlSearchCondition = new TPanel();
		chkOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.BnkCode, getWord("C00779"), 100);
		tbl.addColumn(SC.BranchCode, getWord("C00780"), 100);
		tbl.addColumn(SC.BnkName, getWord("C00781"), 300);
		tbl.addColumn(SC.BnkKana, getWord("C00782"), 200);
		tbl.addColumn(SC.BnkNamek, getWord("C00829"), 300);
		tbl.addColumn(SC.BranchName, getWord("C00783"), 100);
		tbl.addColumn(SC.BranchKana, getWord("C00784"), 200);
		tbl.addColumn(SC.BranchNamek, getWord("C00785"), 300);
		tbl.addColumn(SC.DateFrom, getWord("C00055"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.DateTo, getWord("C00261"), 100, SwingConstants.CENTER);

		tbl.setRowColumnWidth(38);
	}

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
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ��s�����t�B�[���h
		ctrlBank.setSize(320, 30);
		ctrlBank.setLocation(35, 30);
		pnlBodyTop.add(ctrlBank);

		// ���������p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C00433")));
		pnlSearchCondition.setLocation(370, 10);
		pnlSearchCondition.setSize(500, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// �x�X�����t�B�[���h
		ctrlBranch.setSize(420, 50);
		ctrlBranch.setLocation(30, 20);
		pnlSearchCondition.add(ctrlBranch);

		// �L�����Ԑ؂�\��
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(360, 40);
		pnlSearchCondition.add(chkOutputTermEnd);

		// �_�u���N���b�N����
		tbl.addSpreadSheetSelectChange(btnModify);

		// ����
		TPanel pnlBodyButtom = new TPanel();
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
	public void setTabIndex() {

		int i = 0;
		ctrlBank.setTabControlNo(i++);
		ctrlBranch.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��s�}�X�^�̕ҏW���
 */
public class MG0140BankMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** ��s�R�[�h */
	public TLabelField ctrlCode;

	/** ��s�x�X�R�[�h */
	public TLabelField ctrlBranchCode;

	/** ��s���� */
	public TLabelField ctrlName;

	/** ��s�J�i���� */
	public TLabelField ctrlKana;

	/** ��s�������� */
	public TLabelField ctrlNamek;

	/** ��s�x�X���� */
	public TLabelField ctrlBranchName;

	/** ��s�x�X�J�i���� */
	public TLabelField ctrlBranchKana;

	/** ��s�x�X�������� */
	public TLabelField ctrlBranchNamek;

	/** �J�n�N���� */
	public TLabelPopupCalendar dateFrom;

	/** �I���N���� */
	public TLabelPopupCalendar dateTo;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0140BankMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlCode = new TLabelField();
		ctrlBranchCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlKana = new TLabelField();
		ctrlNamek = new TLabelField();
		ctrlBranchName = new TLabelField();
		ctrlBranchKana = new TLabelField();
		ctrlBranchNamek = new TLabelField();
		dateFrom = new TLabelPopupCalendar();
		dateTo = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(480, 430);

		// �m��{�^��
		int x = 355 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 355;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// ��s�R�[�h
		ctrlCode.setLangMessageID("C00779");
		ctrlCode.setLabelSize(90);
		ctrlCode.setFieldSize(70);
		ctrlCode.setMaxLength(4);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		ctrlCode.setLocation(40, 20);
		pnlBody.add(ctrlCode);

		// �x�X�R�[�h
		ctrlBranchCode.setLangMessageID("C00780");
		ctrlBranchCode.setLabelSize(120);
		ctrlBranchCode.setFieldSize(60);
		ctrlBranchCode.setMaxLength(3);
		ctrlBranchCode.setImeMode(false);
		ctrlBranchCode.setAllowedSpace(false);
		ctrlBranchCode.setLocation(10, 50);
		pnlBody.add(ctrlBranchCode);

		// ��s����
		ctrlName.setLangMessageID("C00781");
		ctrlName.setLabelSize(95);
		ctrlName.setFieldSize(300);
		ctrlName.setMaxLength(30);
		ctrlName.setLocation(35, 80);
		pnlBody.add(ctrlName);

		// ��s�J�i����
		ctrlKana.setLangMessageID("C00782");
		ctrlKana.setLabelSize(120);
		ctrlKana.setFieldSize(150);
		ctrlKana.setMaxLength(30);
		ctrlKana.setLocation(10, 110);
		pnlBody.add(ctrlKana);

		// ��s��������
		ctrlNamek.setLangMessageID("C00829");
		ctrlNamek.setLabelSize(120);
		ctrlNamek.setFieldSize(300);
		ctrlNamek.setMaxLength(30);
		ctrlNamek.setLocation(10, 140);
		pnlBody.add(ctrlNamek);

		// ��s�x�X����
		ctrlBranchName.setLangMessageID("C00783");
		ctrlBranchName.setLabelSize(120);
		ctrlBranchName.setFieldSize(300);
		ctrlBranchName.setMaxLength(30);
		ctrlBranchName.setLocation(10, 170);
		pnlBody.add(ctrlBranchName);

		// ��s�x�X�J�i����
		ctrlBranchKana.setLangMessageID("C00784");
		ctrlBranchKana.setLabelSize(120);
		ctrlBranchKana.setFieldSize(150);
		ctrlBranchKana.setMaxLength(30);
		ctrlBranchKana.setLocation(10, 200);
		pnlBody.add(ctrlBranchKana);

		// ��s�x�X��������
		ctrlBranchNamek.setLangMessageID("C00785");
		ctrlBranchNamek.setLabelSize(125);
		ctrlBranchNamek.setFieldSize(300);
		ctrlBranchNamek.setMaxLength(30);
		ctrlBranchNamek.setLocation(5, 230);
		pnlBody.add(ctrlBranchNamek);

		// �J�n�N����
		dateFrom.setLabelSize(110);
		dateFrom.setSize(110 + dateFrom.getCalendarSize() + 5, 20);
		dateFrom.setLocation(20, 260);
		dateFrom.setLangMessageID("C00055");
		pnlBody.add(dateFrom);

		// �I���N����
		dateTo.setLabelSize(110);
		dateTo.setSize(110 + dateFrom.getCalendarSize() + 5, 20);
		dateTo.setLocation(20, 290);
		dateTo.setLangMessageID("C00261");
		pnlBody.add(dateTo);
	}

	@Override
	public void setTabIndex() {

		int i = 0;
		ctrlCode.setTabControlNo(i++);
		ctrlBranchCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlKana.setTabControlNo(i++);
		ctrlNamek.setTabControlNo(i++);
		ctrlBranchName.setTabControlNo(i++);
		ctrlBranchKana.setTabControlNo(i++);
		ctrlBranchNamek.setTabControlNo(i++);
		dateFrom.setTabControlNo(i++);
		dateTo.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
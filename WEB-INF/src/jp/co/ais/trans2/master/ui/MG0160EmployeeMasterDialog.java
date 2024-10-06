package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * MG0016-EmployeeMaster - �Ј��}�X�^ - �_�C�A���O���
 * 
 * @author AIS
 */
public class MG0160EmployeeMasterDialog extends TDialog {

	/** �c���Œ�l */
	protected final int BUTTON_HEIGHT = 25;

	/** �����Œ�l */
	protected final int BUTTON_WIDTH = 110;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �Ј��R�[�h */
	public TLabelField ctrlCode;

	/** �Ј����� */
	public TLabelField ctrlName;

	/** �Ј����� */
	public TLabelField ctrlNameS;

	/** �Ј��������� */
	public TLabelField ctrlNameK;

	/** �U�������ԍ� */
	public TLabelField ctrlYknNo;

	/** �������`�J�i */
	public TLabelField ctrlYknKana;

	/** �U�o��s���� */
	public TBankAccountReference ctrlCbkCode;

	/** �U����s�R�[�h */
	public TBankReference ctrlBnkCode;

	/** �U���x�X�R�[�h */
	public TBranchReference ctrlStnCode;

	/** �U���������a�����:�g */
	public TPanel pnlKozaKbn;

	/** �U���������a�����:Button Group */
	public ButtonGroup bgKozaKbn;

	/** �U���������a�����:1:���ʗa�� */
	public TRadioButton ctrlKozaKbnOrd;

	/** �U���������a�����:2:�����a�� */
	public TRadioButton ctrlKozaKbnCur;

	/** �J�n�N���� */
	public TLabelPopupCalendar ctrlDateFrom;

	/** �I���N���� */
	public TLabelPopupCalendar ctrlDateTo;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0160EmployeeMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlNameS = new TLabelField();
		ctrlNameK = new TLabelField();
		ctrlCbkCode = new TBankAccountReference();
		ctrlBnkCode = new TBankReference();
		ctrlStnCode = new TBranchReference();
		pnlKozaKbn = new TPanel();
		bgKozaKbn = new ButtonGroup();
		ctrlKozaKbnOrd = new TRadioButton();
		ctrlKozaKbnCur = new TRadioButton();
		ctrlYknNo = new TLabelField();
		ctrlYknKana = new TLabelField();
		ctrlDateFrom = new TLabelPopupCalendar();
		ctrlDateTo = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(600, 400);

		// Header������
		pnlHeader.setLayout(null);

		// �m��{�^��
		btnSettle.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		btnClose.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Body������
		pnlBody.setLayout(null);

		// �Ј��R�[�h
		ctrlCode.setFieldSize(150);
		ctrlCode.setLangMessageID("C00697");
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		ctrlCode.setLocation(20, 10);
		pnlBody.add(ctrlCode);

		// �Ј�����
		ctrlName.setFieldSize(400);
		ctrlName.setLangMessageID("C00807");
		ctrlName.setMaxLength(40);
		ctrlName.setLocation(20, 40);
		pnlBody.add(ctrlName);

		// �Ј�����
		ctrlNameS.setFieldSize(400);
		ctrlNameS.setLangMessageID("C00808");
		ctrlNameS.setMaxLength(20);
		ctrlNameS.setLocation(20, 70);
		pnlBody.add(ctrlNameS);

		// �Ј���������
		ctrlNameK.setFieldSize(400);
		ctrlNameK.setLabelSize(120);
		ctrlNameK.setLangMessageID("C00809");
		ctrlNameK.setMaxLength(40);
		ctrlNameK.setLocation(0, 100);
		pnlBody.add(ctrlNameK);

		// �U�o��s����
		ctrlCbkCode.setLocation(45, 130);
		pnlBody.add(ctrlCbkCode);

		// �U����s
		ctrlBnkCode.setLocation(45, 160);
		pnlBody.add(ctrlBnkCode);

		// �U����s�x�X
		ctrlStnCode.setLocation(45, 190);
		pnlBody.add(ctrlStnCode);

		// �U�������a�����
		pnlKozaKbn.setLayout(null);
		pnlKozaKbn.setSize(150, 70);
		pnlKozaKbn.setLangMessageID(getShortWord("C00471"));
		pnlKozaKbn.setLocation(400, 130);
		pnlBody.add(pnlKozaKbn);

		// �U�������a�����:����
		ctrlKozaKbnOrd.setSize(100, 20);
		ctrlKozaKbnOrd.setLangMessageID("C00463");
		ctrlKozaKbnOrd.setIndex(1);
		ctrlKozaKbnOrd.setLocation(20, 20);
		pnlKozaKbn.add(ctrlKozaKbnOrd);

		// �U�������a�����:����
		ctrlKozaKbnCur.setSize(100, 20);
		ctrlKozaKbnCur.setLangMessageID("C00397");
		ctrlKozaKbnCur.setIndex(2);
		ctrlKozaKbnCur.setLocation(20, 40);
		pnlKozaKbn.add(ctrlKozaKbnCur);

		// Radio Button Group ��
		bgKozaKbn.add(ctrlKozaKbnOrd);
		bgKozaKbn.add(ctrlKozaKbnCur);

		// �U�������ԍ�
		ctrlYknNo.setFieldSize(80);
		ctrlYknNo.setLangMessageID("C00813");
		ctrlYknNo.setMaxLength(7);
		ctrlYknNo.setImeMode(false);
		ctrlYknNo.setLocation(20, 220);
		pnlBody.add(ctrlYknNo);

		// �������`�J�i
		ctrlYknKana.setFieldSize(400);
		ctrlYknKana.setLangMessageID("C00168");
		ctrlYknKana.setMaxLength(30);
		ctrlYknKana.setLocation(20, 250);
		pnlBody.add(ctrlYknKana);

		// �J�n�N����
		ctrlDateFrom.setLabelSize(110);
		ctrlDateFrom.setSize(110 + ctrlDateFrom.getCalendarSize() + 5, 20);
		ctrlDateFrom.setLangMessageID("C00055");
		ctrlDateFrom.setLocation(10, 280);
		pnlBody.add(ctrlDateFrom);

		// �I���N����
		ctrlDateTo.setLabelSize(110);
		ctrlDateTo.setSize(110 + ctrlDateTo.getCalendarSize() + 5, 20);
		ctrlDateTo.setLangMessageID("C00261");
		ctrlDateTo.setLocation(200, 280);
		pnlBody.add(ctrlDateTo);
	}

	@Override
	/** Tab����` */
	public void setTabIndex() {
		int i = 1;
		ctrlCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlNameS.setTabControlNo(i++);
		ctrlNameK.setTabControlNo(i++);
		ctrlCbkCode.setTabControlNo(i++);
		ctrlBnkCode.setTabControlNo(i++);
		ctrlStnCode.setTabControlNo(i++);
		ctrlKozaKbnOrd.setTabControlNo(i++);
		ctrlKozaKbnCur.setTabControlNo(i++);
		ctrlYknNo.setTabControlNo(i++);
		ctrlYknKana.setTabControlNo(i++);
		ctrlDateFrom.setTabControlNo(i++);
		ctrlDateTo.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}
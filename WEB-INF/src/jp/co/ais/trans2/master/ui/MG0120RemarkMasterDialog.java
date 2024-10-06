package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * MG0120-RemarkMaster - �E�v�}�X�^ - �_�C�A���O���
 * 
 * @author AIS
 */
public class MG0120RemarkMasterDialog extends TDialog {

	/** �c���Œ�l */
	protected final int BUTTON_HEIGHT = 25;

	/** �����Œ�l */
	protected final int BUTTON_WIDTH = 110;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �E�v�敪�p�l�� */
	public TPanel pnlTek;

	/** �E�v�敪 ButtonGroup */
	public ButtonGroup bgTek;

	/** �`�[�E�v */
	public TRadioButton rdoSlipRemark;

	/** �s�E�v */
	public TRadioButton rdoSlipRowRemark;

	/** �f�[�^�敪:�R���{�{�b�N�X */
	public TLabelComboBox ctrlDataKbn;

	/** �E�v�R�[�h */
	public TLabelField ctrlTekCode;

	/** �E�v���� */
	public TLabelField ctrlTekName;

	/** �E�v�������� */
	public TLabelField ctrlTekNameK;

	/** �J�n�N���� */
	public TLabelPopupCalendar ctrlStrDate;

	/** �I���N���� */
	public TLabelPopupCalendar ctrlEndDate;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0120RemarkMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		pnlTek = new TPanel();
		rdoSlipRemark = new TRadioButton();
		rdoSlipRowRemark = new TRadioButton();
		ctrlDataKbn = new TLabelComboBox();
		ctrlTekCode = new TLabelField();
		ctrlTekName = new TLabelField();
		ctrlTekNameK = new TLabelField();
		ctrlStrDate = new TLabelPopupCalendar();
		ctrlEndDate = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(600, 320);

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

		// �E�v�敪
		pnlTek.setLangMessageID("C00568");
		pnlTek.setSize(250, 60);
		pnlTek.setLocation(80, 10);
		pnlBody.add(pnlTek);

		int x = 100;
		int y = 30;

		// �`�[�E�v
		rdoSlipRemark.setLangMessageID("C00569");
		rdoSlipRemark.setSize(100, 30);
		rdoSlipRemark.setLocation(x, y);
		rdoSlipRemark.setSelected(true);
		rdoSlipRemark.setIndex(0);
		pnlBody.add(rdoSlipRemark);

		x += rdoSlipRemark.getWidth();

		// �s�E�v
		rdoSlipRowRemark.setLangMessageID("C00119");
		rdoSlipRowRemark.setSize(100, 30);
		rdoSlipRowRemark.setLocation(x + 30, y);
		rdoSlipRowRemark.setIndex(1);
		pnlBody.add(rdoSlipRowRemark);

		bgTek = new ButtonGroup();
		bgTek.add(rdoSlipRemark);
		bgTek.add(rdoSlipRowRemark);

		/** �R���{�{�b�N�X */
		// �f�[�^�敪
		ctrlDataKbn.setComboSize(220);
		ctrlDataKbn.setLabelSize(140);
		ctrlDataKbn.setLocation(20, 75);
		ctrlDataKbn.setLangMessageID("C00567");
		setDataKbnItem(ctrlDataKbn.getComboBox());
		pnlBody.add(ctrlDataKbn);

		// �E�v�R�[�h
		ctrlTekCode.setLabelSize(110);
		ctrlTekCode.setFieldSize(100);
		ctrlTekCode.setSize(215, 20);
		ctrlTekCode.setLocation(50, 100);
		ctrlTekCode.setLabelText(getWord("C00564"));
		ctrlTekCode.setMaxLength(10);
		ctrlTekCode.setImeMode(false);
		ctrlTekCode.setAllowedSpace(false);
		pnlBody.add(ctrlTekCode);

		// �E�v����
		ctrlTekName.setLabelSize(110);
		ctrlTekName.setFieldSize(380);
		ctrlTekName.setSize(495, 20);
		ctrlTekName.setLocation(50, 125);
		ctrlTekName.setLabelText(getWord("C00565"));
		ctrlTekName.setMaxLength(80);
		pnlBody.add(ctrlTekName);

		// �E�v��������
		ctrlTekNameK.setLabelSize(110);
		ctrlTekNameK.setFieldSize(380);
		ctrlTekNameK.setSize(495, 20);
		ctrlTekNameK.setLocation(50, 150);
		ctrlTekNameK.setLabelText(getWord("C00566"));
		ctrlTekNameK.setMaxLength(40);
		pnlBody.add(ctrlTekNameK);

		// �J�n�N����
		ctrlStrDate.setLabelSize(110);
		ctrlStrDate.setSize(110 + ctrlStrDate.getCalendarSize() + 5, 20);
		ctrlStrDate.setLangMessageID("COP706");
		ctrlStrDate.setLocation(50, 175);
		pnlBody.add(ctrlStrDate);

		// �I���N����
		ctrlEndDate.setLabelSize(110);
		ctrlEndDate.setSize(110 + ctrlEndDate.getCalendarSize() + 5, 20);
		ctrlEndDate.setLangMessageID("COP707");
		ctrlEndDate.setLocation(50, 200);
		pnlBody.add(ctrlEndDate);
	}

	/**
	 * �f�[�^�敪�R���{�{�b�N�X�̒l�ݒ�
	 * 
	 * @param comboBox
	 */
	protected void setDataKbnItem(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(DataDivision.INPUT, getWord(DataDivision.getDataDivisionName(DataDivision.INPUT)));
		comboBox.addTextValueItem(DataDivision.OUTPUT, getWord(DataDivision.getDataDivisionName(DataDivision.OUTPUT)));
		comboBox.addTextValueItem(DataDivision.TRANSFER,
			getWord(DataDivision.getDataDivisionName(DataDivision.TRANSFER)));
		comboBox.addTextValueItem(DataDivision.ESTIMATE,
			getWord(DataDivision.getDataDivisionName(DataDivision.ESTIMATE)));
		comboBox.addTextValueItem(DataDivision.ESTIMATE_DEL,
			getWord(DataDivision.getDataDivisionName(DataDivision.ESTIMATE_DEL)));
		comboBox.addTextValueItem(DataDivision.LEDGER_IFRS,
			getWord(DataDivision.getDataDivisionName(DataDivision.LEDGER_IFRS)));
		comboBox.addTextValueItem(DataDivision.LEDGER_SELF,
			getWord(DataDivision.getDataDivisionName(DataDivision.LEDGER_SELF)));
		comboBox.addTextValueItem(DataDivision.EMP_KARI,
			getWord(DataDivision.getDataDivisionName(DataDivision.EMP_KARI)));
		comboBox
			.addTextValueItem(DataDivision.EXPENCE, getWord(DataDivision.getDataDivisionName(DataDivision.EXPENCE)));
		comboBox
			.addTextValueItem(DataDivision.ACCOUNT, getWord(DataDivision.getDataDivisionName(DataDivision.ACCOUNT)));
		comboBox.addTextValueItem(DataDivision.PAYMENT_EMP,
			getWord(DataDivision.getDataDivisionName(DataDivision.PAYMENT_EMP)));
		comboBox.addTextValueItem(DataDivision.ABROAD, getWord(DataDivision.getDataDivisionName(DataDivision.ABROAD)));
		comboBox.addTextValueItem(DataDivision.PAYMENT_COM,
			getWord(DataDivision.getDataDivisionName(DataDivision.PAYMENT_COM)));
		comboBox.addTextValueItem(DataDivision.CREDIT_SLIP,
			getWord(DataDivision.getDataDivisionName(DataDivision.CREDIT_SLIP)));
		comboBox.addTextValueItem(DataDivision.CREDIT_DEL,
			getWord(DataDivision.getDataDivisionName(DataDivision.CREDIT_DEL)));

	}

	@Override
	/** Tab����` */
	public void setTabIndex() {
		int i = 1;

		rdoSlipRemark.setTabControlNo(i++);
		rdoSlipRowRemark.setTabControlNo(i++);
		ctrlDataKbn.setTabControlNo(i++);
		ctrlTekCode.setTabControlNo(i++);
		ctrlTekName.setTabControlNo(i++);
		ctrlTekNameK.setTabControlNo(i++);
		ctrlStrDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}
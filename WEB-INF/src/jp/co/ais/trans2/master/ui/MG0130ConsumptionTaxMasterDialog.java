package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.math.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * MG0130-ConsumptionTaxMaster - ����Ń}�X�^ - �_�C�A���O���
 * 
 * @author AIS
 */
public class MG0130ConsumptionTaxMasterDialog extends TDialog {

	/** �c���Œ�l */
	protected final int BUTTON_HEIGHT = 25;

	/** �����Œ�l */
	protected final int BUTTON_WIDTH = 110;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** ����ŃR�[�h */
	public TLabelField ctrlTaxCode;

	/** ����Ŗ��� */
	public TLabelField ctrlTaxName;

	/** ����ŗ��� */
	public TLabelField ctrlTaxNameS;

	/** ����Ō������� */
	public TLabelField ctrlTaxNameK;

	/** ����d���敪:�R���{�{�b�N�X */
	public TLabelComboBox ctrlcboUsKbn;

	/** ����Ōv�Z�� ButtonGroup */
	public ButtonGroup bgTaxKei;

	/** ����Ōv�Z���p�l�� */
	public TPanel pnlTaxKei;

	/** �g�p���Ȃ� */
	public TRadioButton rdoDisUse;

	/** �g�p���� */
	public TRadioButton rdoUse;

	/** �o�͏��� */
	public TLabelNumericField numOutputOrder;

	/** ����ŗ� */
	public TLabelNumericField numConsumptionTaxRate;

	/** �J�n�N���� */
	public TLabelPopupCalendar ctrlStrDate;

	/** �I���N���� */
	public TLabelPopupCalendar ctrlEndDate;

	/** ������ % */
	public TLabel percent;

	/** �`�F�b�N�{�b�N�X��K�i���������s���Ǝ� */
	public TCheckBox ctrlNoInvReg;

	/** �o�ߑ[�u�T���\��(KEKA_SOTI_RATE) */
	public TLabelNumericField ctrlTransitMeasure;
	
	/** % */
	public TLabel lblTransitMeasure;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0130ConsumptionTaxMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlTaxCode = new TLabelField();
		ctrlTaxName = new TLabelField();
		ctrlTaxNameS = new TLabelField();
		ctrlTaxNameK = new TLabelField();
		ctrlcboUsKbn = new TLabelComboBox();
		pnlTaxKei = new TPanel();
		rdoDisUse = new TRadioButton();
		rdoUse = new TRadioButton();
		numOutputOrder = new TLabelNumericField();
		numConsumptionTaxRate = new TLabelNumericField();
		percent = new TLabel();
		ctrlStrDate = new TLabelPopupCalendar();
		ctrlEndDate = new TLabelPopupCalendar();
		ctrlNoInvReg = new TCheckBox();
		ctrlTransitMeasure = new TLabelNumericField();
		lblTransitMeasure = new TLabel();

	}

	@Override
	public void allocateComponents() {

		setSize(650, 450);

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

		// ����ŃR�[�h
		ctrlTaxCode.setLabelSize(110);
		ctrlTaxCode.setFieldSize(35);
		ctrlTaxCode.setSize(215, 20);
		ctrlTaxCode.setLocation(19, 20);
		ctrlTaxCode.setLabelText(getWord("C00573"));
		ctrlTaxCode.setMaxLength(2);
		ctrlTaxCode.setImeMode(false);
		ctrlTaxCode.setAllowedSpace(false);
		pnlBody.add(ctrlTaxCode);

		// ����Ŗ���
		ctrlTaxName.setLabelSize(110);
		ctrlTaxName.setFieldSize(380);
		ctrlTaxName.setSize(495, 20);
		ctrlTaxName.setLocation(50, 50);
		ctrlTaxName.setLabelText(getWord("C00774"));
		ctrlTaxName.setMaxLength(40);
		pnlBody.add(ctrlTaxName);

		// ����ŗ���
		ctrlTaxNameS.setLabelSize(110);
		ctrlTaxNameS.setFieldSize(220);
		ctrlTaxNameS.setSize(495, 20);
		ctrlTaxNameS.setLocation(-30, 80);
		ctrlTaxNameS.setLabelText(getWord("C00775"));
		ctrlTaxNameS.setMaxLength(20);
		pnlBody.add(ctrlTaxNameS);

		// ����Ō�������
		ctrlTaxNameK.setLabelSize(110);
		ctrlTaxNameK.setFieldSize(380);
		ctrlTaxNameK.setSize(495, 20);
		ctrlTaxNameK.setLocation(50, 110);
		ctrlTaxNameK.setLabelText(getWord("C00828"));
		ctrlTaxNameK.setMaxLength(40);
		pnlBody.add(ctrlTaxNameK);

		/** �R���{�{�b�N�X */
		/** ���X�g�{�b�N�X���p */
		// ����d���敪
		ctrlcboUsKbn.setComboSize(220);
		ctrlcboUsKbn.setLabelSize(140);
		ctrlcboUsKbn.setLocation(20, 140);
		ctrlcboUsKbn.setLangMessageID("C01283");
		setUSKbnItem(ctrlcboUsKbn.getComboBox());
		pnlBody.add(ctrlcboUsKbn);

		// ����Ōv�Z���i�p�l���j
		pnlTaxKei.setLangMessageID("C01176");
		pnlTaxKei.setSize(380, 55);
		pnlTaxKei.setLocation(165, 170);
		pnlBody.add(pnlTaxKei);

		int x = 190;
		int y = 190;

		// �g�p���Ȃ��i���W�I�{�^���j
		rdoDisUse.setLangMessageID("CLM0291");
		rdoDisUse.setSize(120, 30);
		rdoDisUse.setLocation(x, y);
		rdoDisUse.setSelected(true);
		rdoDisUse.setIndex(0);
		pnlBody.add(rdoDisUse);

		x += rdoDisUse.getWidth();

		// �g�p����i���W�I�{�^���j
		rdoUse.setLangMessageID("C00281");
		rdoUse.setSize(120, 30);
		rdoUse.setLocation(x, y);
		rdoUse.setIndex(1);
		pnlBody.add(rdoUse);

		bgTaxKei = new ButtonGroup();
		bgTaxKei.add(rdoDisUse);
		bgTaxKei.add(rdoUse);

		// �o�͏���
		numOutputOrder.setLabelSize(90);
		numOutputOrder.setFieldSize(20);
		numOutputOrder.setSize(450, 240);
		numOutputOrder.setLocation(210, 85);
		numOutputOrder.setPositiveOnly(true);
		numOutputOrder.setNumericFormat("###,###,##0");
		numOutputOrder.setLabelText(getWord("C00776"));
		numOutputOrder.setMaxLength(2);
		pnlBody.add(numOutputOrder);

		// ����ŗ�
		numConsumptionTaxRate.setLabelSize(90);
		numConsumptionTaxRate.setFieldSize(40);
		numConsumptionTaxRate.setSize(320, 320);
		numConsumptionTaxRate.setLocation(-20, 85);
		numConsumptionTaxRate.setPositiveOnly(true);
		numConsumptionTaxRate.setDecimalPoint(1);
		numConsumptionTaxRate.setNumericFormat("##,###,##0.0");
		numConsumptionTaxRate.setValue(("0.0"));
		numConsumptionTaxRate.setLabelText(getWord("C00777"));
		numConsumptionTaxRate.setMaxLength(3);
		pnlBody.add(numConsumptionTaxRate);

		// ������ ��
		percent.setLocation(215, 165);
		percent.setSize(20, 160);
		percent.setLangMessageID("COW242");
		pnlBody.add(percent);

		// �J�n�N����
		ctrlStrDate.setLabelSize(110);
		ctrlStrDate.setSize(110 + ctrlStrDate.getCalendarSize() + 5, 170);
		ctrlStrDate.setLangMessageID("COP706");
		ctrlStrDate.setLocation(52, 190);
		pnlBody.add(ctrlStrDate);

		// �I���N����
		ctrlEndDate.setLabelSize(110);
		ctrlEndDate.setSize(110 + ctrlEndDate.getCalendarSize() + 5, 170);
		ctrlEndDate.setLangMessageID("COP707");
		ctrlEndDate.setLocation(52, 220);
		pnlBody.add(ctrlEndDate);

		// �I���N����
		ctrlEndDate.setLabelSize(110);
		ctrlEndDate.setSize(110 + ctrlEndDate.getCalendarSize() + 5, 170);
		ctrlEndDate.setLangMessageID("COP707");
		ctrlEndDate.setLocation(52, 220);
		pnlBody.add(ctrlEndDate);

		x = ctrlTaxCode.getX() + ctrlTaxCode.getWidth() + 15;
		y = 240;

		// ��K�i���������s���Ǝ�
		ctrlNoInvReg.setLangMessageID("C12197");
		ctrlNoInvReg.setSize(160, 15);
		ctrlNoInvReg.setLocation(x, y);
		pnlBody.add(ctrlNoInvReg);

		x = ctrlNoInvReg.getX();
		y = ctrlNoInvReg.getY() + ctrlNoInvReg.getHeight() + 10;

		// �o�ߑ[�u�T���\��
		ctrlTransitMeasure.setLangMessageID("C12228"); // �o�ߑ[�u�T���\��
		ctrlTransitMeasure.setPositiveOnly();
		ctrlTransitMeasure.setMinValue(BigDecimal.ZERO);
		ctrlTransitMeasure.setMaxValue(new BigDecimal(100));
		ctrlTransitMeasure.setLabelSize(175);
		ctrlTransitMeasure.setFieldSize(40);
		ctrlTransitMeasure.setLocation(x, y);
		pnlBody.add(ctrlTransitMeasure);

		x += ctrlTransitMeasure.getWidth() + 2;

		// %
		TGuiUtil.setComponentSize(lblTransitMeasure, 55, 20);
		lblTransitMeasure.setLangMessageID("C01335"); // %
		lblTransitMeasure.setLocation(x, y);
		pnlBody.add(lblTransitMeasure);

	}

	/**
	 * ����d���敪�R���{�{�b�N�X�̒l�ݒ�
	 * 
	 * @param comboBox
	 */
	protected void setUSKbnItem(TComboBox comboBox) {
		comboBox.addTextValueItem(TaxType.NOT, getWord(TaxType.getName(TaxType.NOT)));
		comboBox.addTextValueItem(TaxType.SALES, getWord(TaxType.getName(TaxType.SALES)));
		comboBox.addTextValueItem(TaxType.PURCHAESE, getWord(TaxType.getName(TaxType.PURCHAESE)));
	}

	@Override
	/** Tab����` */
	public void setTabIndex() {
		int i = 1;

		ctrlTaxCode.setTabControlNo(i++);
		ctrlTaxName.setTabControlNo(i++);
		ctrlTaxNameS.setTabControlNo(i++);
		ctrlTaxNameK.setTabControlNo(i++);
		ctrlcboUsKbn.setTabControlNo(i++);
		rdoDisUse.setTabControlNo(i++);
		rdoUse.setTabControlNo(i++);
		numOutputOrder.setTabControlNo(i++);
		numConsumptionTaxRate.setTabControlNo(i++);
		ctrlStrDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		ctrlNoInvReg.setTabControlNo(i++);
		ctrlTransitMeasure.setTabControlNo(i++);

		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}
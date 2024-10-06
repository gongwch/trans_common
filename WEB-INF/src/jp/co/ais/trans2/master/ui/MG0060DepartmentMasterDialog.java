package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ����}�X�^�̕ҏW���
 */
public class MG0060DepartmentMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** ����R�[�h */
	public TLabelField ctrlDepartmentCode;

	/** ���喼�� */
	public TLabelField ctrlDepartmentName;

	/** ���嗪�� */
	public TLabelField ctrlDepartmentNames;

	/** ���匟������ */
	public TLabelField ctrlDepartmentNamek;

	/** �l����1 */
	public TLabelNumericField ctrlDepartmentMen1;

	/** �l����2 */
	public TLabelNumericField ctrlDepartmentMen2;

	/** ���ʐ� */
	public TLabelNumericField ctrlDepartmentArea;

	/** ���� */
	public TRadioButton rdoImput;

	/** �W�v */
	public TRadioButton rdoTotal;

	/** ����敪 ButtonGroup */
	public ButtonGroup bgDepartment;

	/** ����敪 */
	public TPanel pnlDepartmentkbn;

	/** �J�n�N���� */
	public TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar dtEndDate;

	/** ������ �l */
	public TLabel kazu;

	/** ������ �l */
	public TLabel kazu1;

	/** ������ M^2 */
	public TLabel kazu2;

	/** �A�g1�R�[�h */
	public TLabelField ctrlIf1Code;

	/** �A�g1���� */
	public TLabelField ctrlIf1Name;

	/** �A�g2�R�[�h */
	public TLabelField ctrlIf2Code;

	/** �A�g2���� */
	public TLabelField ctrlIf2Name;

	/** �A�g3�R�[�h */
	public TLabelField ctrlIf3Code;

	/** �A�g3���� */
	public TLabelField ctrlIf3Name;

	/** �A�g�R�[�h���� */
	public int if1CodeLength = -1;

	/** �A�g���̌��� */
	public int if1NameLength = -1;

	/** �A�g�R�[�h���� */
	public int if2CodeLength = -1;

	/** �A�g���̌��� */
	public int if2NameLength = -1;

	/** �A�g�R�[�h���� */
	public int if3CodeLength = -1;

	/** �A�g���̌��� */
	public int if3NameLength = -1;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0060DepartmentMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlDepartmentCode = new TLabelField();
		ctrlDepartmentName = new TLabelField();
		ctrlDepartmentNames = new TLabelField();
		ctrlDepartmentNamek = new TLabelField();
		ctrlDepartmentMen1 = new TLabelNumericField();
		ctrlDepartmentMen2 = new TLabelNumericField();
		ctrlDepartmentArea = new TLabelNumericField();
		rdoImput = new TRadioButton();
		rdoTotal = new TRadioButton();
		pnlDepartmentkbn = new TPanel();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		kazu = new TLabel();
		kazu1 = new TLabel();
		kazu2 = new TLabel();

		ctrlIf1Code = new TLabelField();
		ctrlIf1Name = new TLabelField();
		ctrlIf2Code = new TLabelField();
		ctrlIf2Name = new TLabelField();
		ctrlIf3Code = new TLabelField();
		ctrlIf3Name = new TLabelField();
	}

	@Override
	public void allocateComponents() {

		int addY = 0;

		// �O�H�D�D�J�X�^�}�C�Y�F�A�g�����Q�b�g
		boolean isUseIf1 = false;
		try {
			String if1Code = ClientConfig.getProperty("trans.MG0060.if.code.1.length");
			String if1Name = ClientConfig.getProperty("trans.MG0060.if.name.1.length");

			if1CodeLength = DecimalUtil.toInt(if1Code);
			if1NameLength = DecimalUtil.toInt(if1Name);

			isUseIf1 = if1CodeLength > 0 && if1NameLength > 0;

			if (isUseIf1) {
				addY += ctrlIf1Code.getHeight();
				addY += ctrlIf1Name.getHeight();
			}

		} catch (Exception e) {
			// �����Ȃ�
		}
		boolean isUseIf2 = false;
		try {
			String if2Code = ClientConfig.getProperty("trans.MG0060.if.code.2.length");
			String if2Name = ClientConfig.getProperty("trans.MG0060.if.name.2.length");

			if2CodeLength = DecimalUtil.toInt(if2Code);
			if2NameLength = DecimalUtil.toInt(if2Name);

			isUseIf2 = if2CodeLength > 0 && if2NameLength > 0;

			if (isUseIf2) {
				addY += ctrlIf2Code.getHeight();
				addY += ctrlIf2Name.getHeight();
			}

		} catch (Exception e) {
			// �����Ȃ�
		}
		boolean isUseIf3 = false;
		try {
			String if3Code = ClientConfig.getProperty("trans.MG0060.if.code.3.length");
			String if3Name = ClientConfig.getProperty("trans.MG0060.if.name.3.length");

			if3CodeLength = DecimalUtil.toInt(if3Code);
			if3NameLength = DecimalUtil.toInt(if3Name);

			isUseIf3 = if3CodeLength > 0 && if3NameLength > 0;

			if (isUseIf3) {
				addY += ctrlIf3Code.getHeight();
				addY += ctrlIf3Name.getHeight();
			}

		} catch (Exception e) {
			// �����Ȃ�
		}

		setSize(600, 380 + addY);

		// �m��{�^��
		int x = 355 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 355;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// ����R�[�h
		ctrlDepartmentCode.setLabelSize(110);
		ctrlDepartmentCode.setFieldSize(100);
		ctrlDepartmentCode.setSize(215, 20);
		ctrlDepartmentCode.setLocation(10, 10);
		ctrlDepartmentCode.setLangMessageID("C00698");
		ctrlDepartmentCode.setMaxLength(10);
		ctrlDepartmentCode.setImeMode(false);
		ctrlDepartmentCode.setAllowedSpace(false);
		pnlBody.add(ctrlDepartmentCode);

		// ���喼��
		ctrlDepartmentName.setLabelSize(110);
		ctrlDepartmentName.setFieldSize(380);
		ctrlDepartmentName.setSize(495, 20);
		ctrlDepartmentName.setLocation(10, 35);
		ctrlDepartmentName.setLangMessageID("C00723");
		ctrlDepartmentName.setMaxLength(40);
		pnlBody.add(ctrlDepartmentName);

		// ���嗪��
		ctrlDepartmentNames.setLabelSize(110);
		ctrlDepartmentNames.setFieldSize(150);
		ctrlDepartmentNames.setSize(265, 20);
		ctrlDepartmentNames.setLocation(10, 60);
		ctrlDepartmentNames.setLangMessageID("C00724");
		ctrlDepartmentNames.setMaxLength(20);
		pnlBody.add(ctrlDepartmentNames);

		// ���匟������
		ctrlDepartmentNamek.setLabelSize(110);
		ctrlDepartmentNamek.setFieldSize(380);
		ctrlDepartmentNamek.setSize(495, 20);
		ctrlDepartmentNamek.setLocation(10, 85);
		ctrlDepartmentNamek.setLangMessageID("C00725");
		ctrlDepartmentNamek.setMaxLength(40);
		pnlBody.add(ctrlDepartmentNamek);

		// �l����1
		ctrlDepartmentMen1.setLabelSize(110);
		ctrlDepartmentMen1.setFieldSize(75);
		ctrlDepartmentMen1.setSize(190, 20);
		ctrlDepartmentMen1.setLocation(10, 110);
		ctrlDepartmentMen1.setLangMessageID("C00726");
		ctrlDepartmentMen1.setMaxLength(8);
		ctrlDepartmentMen1.setNumericFormat("#,##0");
		ctrlDepartmentMen1.setValue("0");
		pnlBody.add(ctrlDepartmentMen1);

		// �l����2
		ctrlDepartmentMen2.setLabelSize(110);
		ctrlDepartmentMen2.setFieldSize(75);
		ctrlDepartmentMen2.setSize(190, 20);
		ctrlDepartmentMen2.setLocation(10, 135);
		ctrlDepartmentMen2.setLangMessageID("C00727");
		ctrlDepartmentMen2.setMaxLength(8);
		ctrlDepartmentMen2.setNumericFormat("#,##0");
		ctrlDepartmentMen2.setValue("0");
		pnlBody.add(ctrlDepartmentMen2);

		// ���ʐ�
		ctrlDepartmentArea.setLabelSize(110);
		ctrlDepartmentArea.setFieldSize(75);
		ctrlDepartmentArea.setSize(190, 20);
		ctrlDepartmentArea.setLocation(10, 160);
		ctrlDepartmentArea.setLangMessageID("C00728");
		ctrlDepartmentArea.setMaxLength(10, 2);
		ctrlDepartmentArea.setValue("0.00");
		pnlBody.add(ctrlDepartmentArea);

		x = 139;
		int y = 205;

		// ����
		rdoImput.setLangMessageID("C01275");
		rdoImput.setSize(95, 15);
		rdoImput.setSelected(true);
		rdoImput.setLocation(x, y);
		rdoImput.setIndex(0);
		pnlBody.add(rdoImput);

		x += rdoImput.getWidth();

		// �W�v
		rdoTotal.setLangMessageID("C00255");
		rdoTotal.setSize(95, 15);
		rdoTotal.setLocation(x, y);
		rdoTotal.setIndex(1);
		pnlBody.add(rdoTotal);

		bgDepartment = new ButtonGroup();
		bgDepartment.add(rdoImput);
		bgDepartment.add(rdoTotal);

		// ����敪
		pnlDepartmentkbn.setLangMessageID("C01303");
		pnlDepartmentkbn.setSize(225, 45);
		pnlDepartmentkbn.setLocation(123, 185);
		pnlBody.add(pnlDepartmentkbn);

		// �J�n�N����
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 240);
		dtBeginDate.setLangMessageID("C00055");
		pnlBody.add(dtBeginDate);

		// �I���N����
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 265);
		dtEndDate.setLangMessageID("C00261");
		pnlBody.add(dtEndDate);

		// ������ �l
		kazu.setLocation(205, 40);
		kazu.setSize(200, 160);
		kazu.setLangMessageID("C01195");
		pnlBody.add(kazu);

		// ������ �l
		kazu1.setLocation(205, 65);
		kazu1.setSize(200, 160);
		kazu1.setLangMessageID("C01195");
		pnlBody.add(kazu1);

		// ������ M^2
		kazu2.setLocation(205, 90);
		kazu2.setSize(200, 160);
		kazu2.setLangMessageID("C00969");
		pnlBody.add(kazu2);

		int adjX = ctrlDepartmentCode.getX();
		int adjY = dtEndDate.getY() + dtEndDate.getHeight() + 5;
		if (isUseIf1) {
			// �A�g�R�[�h1
			ctrlIf1Code.setLabelSize(110);
			ctrlIf1Code.setFieldSize(100);
			ctrlIf1Code.setSize(215, 20);
			ctrlIf1Code.setLocation(adjX, adjY);
			ctrlIf1Code.setLangMessageID("C12060");
			ctrlIf1Code.setMaxLength(if1CodeLength);
			ctrlIf1Code.setImeMode(false);
			ctrlIf1Code.setAllowedSpace(false);
			ctrlIf1Code.setRegex("[0-9]");
			pnlBody.add(ctrlIf1Code);

			adjY += ctrlIf1Code.getHeight();

			// �A�g����1
			ctrlIf1Name.setLabelSize(110);
			ctrlIf1Name.setFieldSize(380);
			ctrlIf1Name.setSize(495, 20);
			ctrlIf1Name.setLocation(adjX, adjY);
			ctrlIf1Name.setLangMessageID("C12061");
			ctrlIf1Name.setMaxLength(if1NameLength);
			pnlBody.add(ctrlIf1Name);

			adjY += ctrlIf1Name.getHeight();
		}
		if (isUseIf2) {
			// �A�g�R�[�h2
			ctrlIf2Code.setLabelSize(110);
			ctrlIf2Code.setFieldSize(100);
			ctrlIf2Code.setSize(215, 20);
			ctrlIf2Code.setLocation(adjX, adjY);
			ctrlIf2Code.setLangMessageID("C12062");
			ctrlIf2Code.setMaxLength(if2CodeLength);
			ctrlIf2Code.setImeMode(false);
			ctrlIf2Code.setAllowedSpace(false);
			ctrlIf2Code.setRegex("[0-9]");
			pnlBody.add(ctrlIf2Code);

			adjY += ctrlIf2Code.getHeight();

			// �A�g����2
			ctrlIf2Name.setLabelSize(110);
			ctrlIf2Name.setFieldSize(380);
			ctrlIf2Name.setSize(495, 20);
			ctrlIf2Name.setLocation(adjX, adjY);
			ctrlIf2Name.setLangMessageID("C12063");
			ctrlIf2Name.setMaxLength(if2NameLength);
			pnlBody.add(ctrlIf2Name);

			adjY += ctrlIf2Name.getHeight();
		}
		if (isUseIf3) {
			// �A�g�R�[�h3
			ctrlIf3Code.setLabelSize(110);
			ctrlIf3Code.setFieldSize(100);
			ctrlIf3Code.setSize(215, 20);
			ctrlIf3Code.setLocation(adjX, adjY);
			ctrlIf3Code.setLangMessageID("C12064");
			ctrlIf3Code.setMaxLength(if3CodeLength);
			ctrlIf3Code.setImeMode(false);
			ctrlIf3Code.setAllowedSpace(false);
			ctrlIf3Code.setRegex("[0-9]");
			pnlBody.add(ctrlIf3Code);

			adjY += ctrlIf2Code.getHeight();

			// �A�g����3
			ctrlIf3Name.setLabelSize(110);
			ctrlIf3Name.setFieldSize(380);
			ctrlIf3Name.setSize(495, 20);
			ctrlIf3Name.setLocation(adjX, adjY);
			ctrlIf3Name.setLangMessageID("C12065");
			ctrlIf3Name.setMaxLength(if3NameLength);
			pnlBody.add(ctrlIf3Name);

			adjY += ctrlIf3Name.getHeight();
		}

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlDepartmentCode.setTabControlNo(i++);
		ctrlDepartmentName.setTabControlNo(i++);
		ctrlDepartmentNames.setTabControlNo(i++);
		ctrlDepartmentNamek.setTabControlNo(i++);
		ctrlDepartmentMen1.setTabControlNo(i++);
		ctrlDepartmentMen2.setTabControlNo(i++);
		ctrlDepartmentArea.setTabControlNo(i++);
		rdoImput.setTabControlNo(i++);
		rdoTotal.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);

		ctrlIf1Code.setTabControlNo(i++);
		ctrlIf1Name.setTabControlNo(i++);
		ctrlIf2Code.setTabControlNo(i++);
		ctrlIf2Name.setTabControlNo(i++);
		ctrlIf3Code.setTabControlNo(i++);
		ctrlIf3Name.setTabControlNo(i++);

		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
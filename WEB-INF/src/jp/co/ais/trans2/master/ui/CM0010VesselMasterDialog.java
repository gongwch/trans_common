package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �D�}�X�^�̕ҏW���
 * 
 * @author AIS
 */
public class CM0010VesselMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �^�u */
	public TTabbedPane tab;

	/** �R���Ǘ��^�u */
	public TPanel pnlFuel;

	/** �D���p�l�� */
	public TPanel pnlVessel;

	/** �R�[�h */
	public TLabelField ctrlCode;

	/** ���� */
	public TLabelField ctrlName;

	/** ���� */
	public TLabelField ctrlNames;

	/** �������� */
	public TLabelField ctrlNamek;

	/** �J�n�N���� */
	public TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar dtEndDate;

	/** �����i����R�[�h */
	public TLabelField ctrlStock;

	/** �����i����R�[�h */
	public TDepartmentReference ctrlStockReference;

	/** �R�����R�[�h */
	public TLabelField ctrlFuel;

	/** �R�����R�[�h */
	public TDepartmentReference ctrlFuelReference;

	/** �R���Ǘ��^�u */
	public TScrollPane spFuel;

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public CM0010VesselMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		tab = new TTabbedPane();
		pnlFuel = new TPanel();
		pnlVessel = new TPanel();
		ctrlCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlNames = new TLabelField();
		ctrlNamek = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlStock = new TLabelField();
		ctrlStockReference = new TDepartmentReference();
		ctrlFuel = new TLabelField();
		ctrlFuelReference = new TDepartmentReference();
		spFuel = new TScrollPane(pnlFuel);

	}

	@Override
	public void allocateComponents() {

		setSize(600, 400);

		// �m��{�^��
		btnSettle.setLangMessageID("C01019"); // �m��
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(345, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		btnClose.setLangMessageID("C01747"); // �߂�
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(460, HEADER_Y);
		btnClose.setForClose(true);
		pnlHeader.add(btnClose);

		pnlBody.add(pnlVessel);

		// �R���Ǘ�
		pnlFuel.setLayout(null);
		TGuiUtil.setComponentSize(pnlFuel, new Dimension(500, 100));
		tab.addTab(getWord("C11783"), spFuel); // �R���Ǘ�

		// ��Џ��
		pnlVessel.setLayout(null);
		TGuiUtil.setComponentSize(pnlVessel, new Dimension(720, 150));

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(10, 0, 0, 0);
		gc.anchor = GridBagConstraints.WEST;
		pnlBody.add(pnlVessel, gc);
		TGuiUtil.setComponentSize(tab, new Dimension(720, 530));

		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(tab, gc);

		// �D�R�[�h
		ctrlCode.setLabelSize(110);
		ctrlCode.setFieldSize(75);
		ctrlCode.setSize(190, 20);
		ctrlCode.setLocation(10, 0);
		ctrlCode.setLabelText(getWord("C11758")); // �D�R�[�h
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		pnlVessel.add(ctrlCode);
		pnlVessel.setBackground(Color.red);

		// ����
		ctrlName.setLabelSize(110);
		ctrlName.setFieldSize(400);
		ctrlName.setSize(515, 20);
		ctrlName.setLocation(10, 25);
		ctrlName.setLabelText(getWord("C11773")); // �D����
		ctrlName.setMaxLength(40);
		pnlVessel.add(ctrlName);

		// ����
		ctrlNames.setLabelSize(110);
		ctrlNames.setFieldSize(150);
		ctrlNames.setSize(265, 20);
		ctrlNames.setLocation(10, 50);
		ctrlNames.setLabelText(getWord("C11759")); // �D����
		ctrlNames.setMaxLength(20);
		pnlVessel.add(ctrlNames);

		// ��������
		ctrlNamek.setLabelSize(110);
		ctrlNamek.setFieldSize(400);
		ctrlNamek.setSize(515, 20);
		ctrlNamek.setLocation(10, 75);
		ctrlNamek.setLabelText(getWord("C11774")); // �D��������
		ctrlNamek.setMaxLength(40);
		pnlVessel.add(ctrlNamek);

		// �J�n�N����
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 100);
		dtBeginDate.setLangMessageID("C00055");
		pnlVessel.add(dtBeginDate);

		// �I���N����
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 125);
		dtEndDate.setLangMessageID("C00261");
		pnlVessel.add(dtEndDate);

		// �����i����R�[�h
		ctrlStock.setLabelSize(120);
		ctrlStock.setFieldSize(0);
		ctrlStock.setSize(120, 20);
		ctrlStock.setLocation(0, 20);
		ctrlStock.setLangMessageID("C11775");
		pnlFuel.add(ctrlStock);

		// �����i����R�[�h
		ctrlStockReference.setLocation(130, 20);
		pnlFuel.add(ctrlStockReference);

		// �R�����R�[�h
		ctrlFuel.setLabelSize(120);
		ctrlFuel.setFieldSize(0);
		ctrlFuel.setSize(120, 20);
		ctrlFuel.setLocation(0, 45);
		ctrlFuel.setLangMessageID("C11777");
		pnlFuel.add(ctrlFuel);

		// �R�����R�[�h
		ctrlFuelReference.setLocation(130, 45);
		pnlFuel.add(ctrlFuelReference);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlNames.setTabControlNo(i++);
		ctrlNamek.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		tab.setTabControlNo(i++);
		ctrlStockReference.setTabControlNo(i++);
		ctrlFuelReference.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}

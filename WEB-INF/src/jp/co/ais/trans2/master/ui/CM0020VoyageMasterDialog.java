package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ���q�}�X�^�̕ҏW���
 * 
 * @author AIS
 */
public class CM0020VoyageMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �^�u */
	public TTabbedPane tab;

	/** ���q���ݒ�^�u */
	public TPanel pnlBase;

	/** ���q���p�l�� */
	public TPanel pnlVoyage;

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

	/**
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public CM0020VoyageMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		tab = new TTabbedPane();
		pnlBase = new TPanel();
		pnlVoyage = new TPanel();
		ctrlCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlNames = new TLabelField();
		ctrlNamek = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();

	}

	@Override
	public void allocateComponents() {

		setSize(520, 360);

		// �m��{�^��
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(getWidth() - 240 - HEADER_MARGIN_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 10, 10, 10);

		pnlBody.add(tab, gc);

		// ��{�ݒ�^�u
		pnlBase.setLayout(null);
		tab.addTab(getWord("C00111"), pnlBase);// ���q���

		// ���q���
		pnlVoyage.setLayout(null);
		pnlVoyage.setBorder(TBorderFactory.createTitledBorder(getWord("C11784")));// ���q���ݒ�
		pnlVoyage.setSize(750, 400);
		pnlVoyage.setLocation(10, 10);
		pnlBase.add(pnlVoyage);

		// ���q�R�[�h
		ctrlCode.setLabelSize(120);
		ctrlCode.setFieldSize(75);
		ctrlCode.setSize(200, 20);
		ctrlCode.setLocation(10, 20);
		ctrlCode.setLabelText(getWord("C03003"));
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		pnlVoyage.add(ctrlCode);

		// ���q����
		ctrlName.setLabelSize(120);
		ctrlName.setFieldSize(300);
		ctrlName.setSize(425, 20);
		ctrlName.setLocation(10, 50);
		ctrlName.setLabelText(getWord("C11780"));
		ctrlName.setMaxLength(40);
		pnlVoyage.add(ctrlName);

		// ���q����
		ctrlNames.setLabelSize(120);
		ctrlNames.setFieldSize(150);
		ctrlNames.setSize(275, 20);
		ctrlNames.setLocation(10, 80);
		ctrlNames.setLabelText(TModelUIUtil.getShortWord(("C11781")));
		ctrlNames.setMaxLength(20);
		pnlVoyage.add(ctrlNames);

		// ���q��������
		ctrlNamek.setLabelSize(120);
		ctrlNamek.setFieldSize(300);
		ctrlNamek.setSize(425, 20);
		ctrlNamek.setLocation(10, 110);
		ctrlNamek.setLabelText(getWord("C11782"));
		ctrlNamek.setMaxLength(40);
		pnlVoyage.add(ctrlNamek);

		// �J�n�N����
		dtBeginDate.setLabelSize(120);
		dtBeginDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 140);
		dtBeginDate.setLangMessageID("C00055");
		pnlVoyage.add(dtBeginDate);

		// �I���N����
		dtEndDate.setLabelSize(120);
		dtEndDate.setSize(120 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 170);
		dtEndDate.setLangMessageID("C00261");
		pnlVoyage.add(dtEndDate);

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
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}

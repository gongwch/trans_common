package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �A�����ђn��}�X�^�ҏW���
 * 
 * @author AIS
 */
public class CM0070MLITCountryMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** ���R�[�h */
	public TMlitRegionReference ctrlItem;

	/** �R�[�h */
	public TLabelField ctrlCode;

	/** ���� */
	public TLabelField ctrlName;

	/** Remark */
	public TLabelField ctrlRemark;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public CM0070MLITCountryMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();

		ctrlItem = new TMlitRegionReference();
		ctrlCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlRemark = new TLabelField();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		setSize(800, 225);

		int x = getWidth() - 300;
		int y = HEADER_Y;

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, y);

		pnlHeader.add(btnSettle);

		x = x + btnSettle.getWidth() + HEADER_MARGIN_X;

		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, y);
		btnClose.setForClose(true);
		pnlHeader.add(btnClose);

		// ����
		pnlBody.setLayout(null);

		x = 10;
		y = COMPONENT_MARGIN_Y + 10;

		// ��
		ctrlItem.btn.setLangMessageID("CBL363"); // ��
		ctrlItem.setLocation(x + 15, y);
		pnlBody.add(ctrlItem);

		y += 25;

		// �n��R�[�h
		ctrlCode.setLabelSize(90);
		ctrlCode.setFieldSize(80);
		ctrlCode.setLangMessageID("Country Code"); // Country Code
		ctrlCode.setLocation(x, y);
		pnlBody.add(ctrlCode);

		y += 25;

		// ����
		ctrlName.setLabelSize(90);
		ctrlName.setFieldSize(650);
		ctrlName.setLangMessageID("Country Name"); // Country Name
		ctrlName.setLocation(x, y);
		pnlBody.add(ctrlName);

		y += 25;

		// Remark
		ctrlRemark.setLabelSize(90);
		ctrlRemark.setFieldSize(650);
		ctrlRemark.setLangMessageID("CM0015"); // Remark
		ctrlRemark.setLocation(x, y);
		pnlBody.add(ctrlRemark);

		// ��{����
		controlSettings();
	}

	/**
	 * ����
	 */
	public void controlSettings() {
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);

		ctrlName.setMaxLength(40);
		ctrlRemark.setMaxLength(200);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlItem.setTabControlNo(i++);
		ctrlCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlRemark.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}

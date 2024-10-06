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
 * �A�����уT�u�A�C�e���}�X�^�ҏW���
 * 
 * @author AIS
 */
public class CM0050MLITSubItemMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �A�C�e���R�[�h */
	public TMlitItemReference ctrlItem;

	/** �R�[�h */
	public TLabelField ctrlSubCode;

	/** ���� */
	public TLabelField ctrlSubName;

	/** Remark */
	public TLabelField ctrlRemark;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public CM0050MLITSubItemMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();

		ctrlItem = new TMlitItemReference();
		ctrlSubCode = new TLabelField();
		ctrlSubName = new TLabelField();
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

		// �A�C�e��
		ctrlItem.btn.setLangMessageID("CBL363"); // �A�C�e��
		ctrlItem.setLocation(x + 15, y);
		pnlBody.add(ctrlItem);

		y += 25;

		// �T�u�A�C�e���R�[�h
		ctrlSubCode.setLabelSize(90);
		ctrlSubCode.setFieldSize(80);
		ctrlSubCode.setLangMessageID("CBL306"); // Sub Item Code
		ctrlSubCode.setLocation(x, y);
		pnlBody.add(ctrlSubCode);

		y += 25;

		// ����
		ctrlSubName.setLabelSize(90);
		ctrlSubName.setFieldSize(650);
		ctrlSubName.setLangMessageID("CBL307"); // Sub Item Name
		ctrlSubName.setLocation(x, y);
		pnlBody.add(ctrlSubName);

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
		ctrlSubCode.setMaxLength(10);
		ctrlSubCode.setImeMode(false);

		ctrlSubName.setMaxLength(40);
		ctrlRemark.setMaxLength(200);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlItem.setTabControlNo(i++);
		ctrlSubCode.setTabControlNo(i++);
		ctrlSubName.setTabControlNo(i++);
		ctrlRemark.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}

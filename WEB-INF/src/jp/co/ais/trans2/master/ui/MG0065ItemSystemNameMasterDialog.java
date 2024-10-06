package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * MG0065-ItemSystemMaster - �Ȗڑ̌n���̃}�X�^ - �_�C�A���O���
 * 
 * @author AIS
 */
public class MG0065ItemSystemNameMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �Ȗڑ̌n�R�[�h */
	public TLabelField ctrlCode;

	/** �Ȗڑ̌n���� */
	public TLabelField ctrlName;

	/** �Ȗڑ̌n���� */
	public TLabelField ctrlNameS;

	/** �Ȗڑ̌n�������� */
	public TLabelField ctrlNameK;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0065ItemSystemNameMasterDialog(Frame parent, boolean mordal) {
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
	}

	@Override
	public void allocateComponents() {

		setSize(600, 230);

		// Header������
		pnlHeader.setLayout(null);

		// �m��{�^��
		btnSettle.setSize(25, 110);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		btnClose.setSize(25, 110);
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Body������
		pnlBody.setLayout(null);

		// �Ȗڑ̌n�R�[�h
		ctrlCode.setFieldSize(40);
		ctrlCode.setLangMessageID("C00617");
		ctrlCode.setMaxLength(2);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		ctrlCode.setLocation(20, 10);
		pnlBody.add(ctrlCode);

		// �Ȗڑ̌n����
		ctrlName.setFieldSize(400);
		ctrlName.setLangMessageID("C00618");
		ctrlName.setMaxLength(40);
		ctrlName.setLocation(20, 40);
		pnlBody.add(ctrlName);

		// �Ȗڑ̌n����
		ctrlNameS.setFieldSize(200);
		ctrlNameS.setLangMessageID("C00619");
		ctrlNameS.setMaxLength(20);
		ctrlNameS.setLocation(20, 70);
		pnlBody.add(ctrlNameS);

		// �Ȗڑ̌n��������
		ctrlNameK.setFieldSize(400);
		ctrlNameK.setLabelSize(120);
		ctrlNameK.setLangMessageID("C00620");
		ctrlNameK.setMaxLength(40);
		ctrlNameK.setLocation(0, 100);
		pnlBody.add(ctrlNameK);

	}

	@Override
	/** Tab����` */
	public void setTabIndex() {
		int i = 1;
		ctrlCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlNameS.setTabControlNo(i++);
		ctrlNameK.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}
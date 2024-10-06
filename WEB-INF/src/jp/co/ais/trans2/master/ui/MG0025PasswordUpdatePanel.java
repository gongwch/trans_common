package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �p�X���[�h�ύX�̉�ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0025PasswordUpdatePanel extends TMainPanel {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** ���O�C�����[�U�[�R�[�h */
	public TLabelField ctrlUserCode;

	/** ���O�C�����[�U�[���� */
	public TTextField ctrlUserName;

	/** �V�����p�X���[�h */
	public TLabelPasswordField ctrlNewPassword;

	/** �V�����p�X���[�h�i�m�F�j */
	public TLabelPasswordField ctrlConfNewPassword;

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		ctrlUserCode = new TLabelField();
		ctrlUserName = new TTextField();
		ctrlNewPassword = new TLabelPasswordField();
		ctrlConfNewPassword = new TLabelPasswordField();
	}

	@Override
	public void allocateComponents() {

		// �m��{�^��
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(1000, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �㕔
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 800));
		pnlBodyTop.setMinimumSize(new Dimension(0, 800));
		pnlBodyTop.setPreferredSize(new Dimension(0, 800));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ���O�C�����[�U�[�R�[�h
		ctrlUserCode.setLangMessageID("C00993");
		ctrlUserCode.setLabelSize(110);
		ctrlUserCode.setEnabled(true);
		ctrlUserCode.setFieldSize(120);
		ctrlUserCode.setMaxLength(TransUtil.USER_CODE_LENGTH);
		ctrlUserCode.setLocation(80, 0);
		pnlBodyTop.add(ctrlUserCode);

		// ���O�C�����[�U�[����
		ctrlUserName.setEnabled(true);
		ctrlUserName.setSize(440, 20);
		ctrlUserName.setLocation(315, 0);
		ctrlUserName.setMaxLength(TransUtil.PROGRAM_NAME_LENGTH);
		pnlBodyTop.add(ctrlUserName);

		// �V�����p�X���[�h
		ctrlNewPassword.setLangMessageID("C00742");
		ctrlNewPassword.setLabelSize(110);
		ctrlNewPassword.setFieldSize(120);
		ctrlNewPassword.setMaxLength(TransUtil.PASSWORD_LENGTH);
		ctrlNewPassword.setLocation(ctrlUserCode.getX(), ctrlUserCode.getHeight() + 5);
		pnlBodyTop.add(ctrlNewPassword);

		// �V�����p�X���[�h�i�m�F�p�j
		ctrlConfNewPassword.setLangMessageID("C00305");
		ctrlConfNewPassword.setLabelSize(110);
		ctrlConfNewPassword.setFieldSize(120);
		ctrlConfNewPassword.setMaxLength(TransUtil.PASSWORD_LENGTH);
		ctrlConfNewPassword.setLocation(ctrlUserCode.getX(), ctrlUserCode.getHeight() + 30);
		pnlBodyTop.add(ctrlConfNewPassword);

		// ����
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlNewPassword.setTabControlNo(i++);
		ctrlConfNewPassword.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
	}
}

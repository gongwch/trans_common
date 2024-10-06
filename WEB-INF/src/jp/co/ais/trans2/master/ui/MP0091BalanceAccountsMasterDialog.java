package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ���ێ��x�R�[�h�}�X�^�̕ҏW���
 */
public class MP0091BalanceAccountsMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** ���ێ��x�R�[�h */
	public TLabelField ctrlBalanceCode;

	/** ���ێ��x���� */
	public TLabelHalfAngleTextField ctrlBalanceName;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MP0091BalanceAccountsMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlBalanceCode = new TLabelField();
		ctrlBalanceName = new TLabelHalfAngleTextField();
	}

	@Override
	public void allocateComponents() {
		setSize(500, 150);

		// �m��{�^��
		int x = 255 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 255;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);
		x = 10;
		int y = 10;

		// ���ێ��x�R�[�h
		ctrlBalanceCode.setSize(195, 20);
		ctrlBalanceCode.setLabelSize(110);
		ctrlBalanceCode.setFieldSize(80);
		ctrlBalanceCode.setLocation(x, y);
		ctrlBalanceCode.setLangMessageID("C11839"); // ���ێ��x�R�[�h
		ctrlBalanceCode.setMaxLength(4);
		ctrlBalanceCode.setImeMode(false);
		ctrlBalanceCode.setAllowedSpace(false);
		pnlBody.add(ctrlBalanceCode);

		y += ctrlBalanceCode.getHeight() + 5;

		// ���ێ��x����
		ctrlBalanceName.setLangMessageID("C11842"); // ���ێ��x
		ctrlBalanceName.setSize(265, 20);
		ctrlBalanceName.setLabelSize(110);
		ctrlBalanceName.setFieldSize(150);
		ctrlBalanceName.setLocation(x, y);
		ctrlBalanceName.setMaxLength(22);
		pnlBody.add(ctrlBalanceName);
	}

	@Override
	public void setTabIndex() {

		int i = 0;
		ctrlBalanceCode.setTabControlNo(i++);
		ctrlBalanceName.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}
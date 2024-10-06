package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * �����o�^�_�C�A���O
 */
public class TSlipPatternSaveDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �p�^�[���ԍ� */
	public TLabelField ctrlPatternNo;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent
	 * @param mordal
	 */
	public TSlipPatternSaveDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlPatternNo = new TLabelField();
	}

	@Override
	public void allocateComponents() {

		setSize(450, 150);

		// �m��{�^��
		int x = HEADER_MARGIN_X - 50;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 250, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 70;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 250, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// �p�^�[���ԍ�
		ctrlPatternNo.setFieldSize(200);
		ctrlPatternNo.setSize(350, 20);
		ctrlPatternNo.setLocation(0, 20);
		ctrlPatternNo.setLangMessageID(getWord("C00987"));
		ctrlPatternNo.setMaxLength(20);
		ctrlPatternNo.setImeMode(false);
		pnlBody.add(ctrlPatternNo);
	}

	@Override
	public void setTabIndex() {

		int i = 0;
		ctrlPatternNo.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}
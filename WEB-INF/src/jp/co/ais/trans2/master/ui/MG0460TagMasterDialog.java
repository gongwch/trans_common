package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * MG0460TagMaster - �tⳃ}�X�^ - �ҏW���
 * 
 * @author AIS
 */
public class MG0460TagMasterDialog extends TDialog {

	/** �c���Œ�l */
	protected final int BUTTON_HEIGHT = 25;

	/** �����Œ�l */
	protected final int BUTTON_WIDTH = 110;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �tⳃR�[�h */
	public TLabelField ctrlCode;

	/** �tⳐF */
	public TColor ctrlCompanyColor;

	/** �tⳃ^�C�g�� */
	public TLabelField ctrlTitle;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0460TagMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlCode = new TLabelField();
		ctrlCompanyColor = new TColor();
		ctrlTitle = new TLabelField();

	}

	@Override
	public void allocateComponents() {

		setSize(800, 200);

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

		int x = 20;
		int y = 10;

		// �tⳃR�[�h
		ctrlCode.setFieldSize(90);
		ctrlCode.setLangMessageID("CM0081");
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		ctrlCode.setLocation(x, y);
		pnlBody.add(ctrlCode);
		y += ctrlCode.getHeight() + 10;

		// �tⳐF
		ctrlCompanyColor.setCaption(TModelUIUtil.getShortWord("CM0082"));
		ctrlCompanyColor.setLocation(x + 25, y);
		pnlBody.add(ctrlCompanyColor);
		y += ctrlCompanyColor.getHeight() + 5;

		// �tⳃ^�C�g��
		ctrlTitle.setFieldSize(600);
		ctrlTitle.setLangMessageID("CM0083");
		ctrlTitle.setMaxLength(200);
		ctrlTitle.setLocation(20, 70);
		pnlBody.add(ctrlTitle);

	}

	@Override
	// Tab����`
	public void setTabIndex() {
		int i = 1;
		ctrlCode.setTabControlNo(i++);
		ctrlCompanyColor.setTabControlNo(i++);
		ctrlTitle.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}
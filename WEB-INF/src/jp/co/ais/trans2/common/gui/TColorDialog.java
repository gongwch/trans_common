package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;

/**
 * �F�I���_�C�A���O
 * 
 * @author AIS
 */
public class TColorDialog extends TDialog {

	/** colorChooser */
	public TColorChooser colorChooser;

	/** �{�^���p�l�� */
	public TPanel pnlButton;

	/** �m��{�^�� */
	public TButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnBack;

	/**
	 * �R���X�g���N�^�[
	 */
	public TColorDialog() {

		initComponents();
		allocateComponents();
		setTabIndex();
		initDialog();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param frame �e�_�C�A���O
	 * @param modal ���[�_���t���O
	 */
	public TColorDialog(Frame frame, boolean modal) {
		super(frame, modal);
		initComponents();
		allocateComponents();
		setTabIndex();
		initDialog();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param dialog �e�_�C�A���O
	 * @param modal ���[�_���t���O
	 */
	public TColorDialog(Dialog dialog, boolean modal) {
		super(dialog, modal);
		initComponents();
		allocateComponents();
		setTabIndex();
		initDialog();
	}

	/**
	 * �R���|�[�l���g������
	 */
	protected void initComponents() {
		colorChooser = new TColorChooser();
		btnSettle = new TButton();
		btnBack = new TButton();
		pnlButton = new TPanel();
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	protected void allocateComponents() {

		setResizable(true);

		setLayout(new GridBagLayout());
		setSize(555, 425);
		setMinimumSize(getSize());

		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;

		// colorChooser
		add(colorChooser, gc);

		pnlButton.setLayout(null);
		TGuiUtil.setComponentSize(pnlButton, 0, 30);

		// �m��{�^��
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(235, 0);
		pnlButton.add(btnSettle);

		// �߂�{�^��
		btnBack.setLangMessageID("C01747");
		btnBack.setShortcutKey(KeyEvent.VK_F12);
		btnBack.setSize(25, 110);
		btnBack.setLocation(350, 0);
		pnlButton.add(btnBack);

		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;

		add(pnlButton, gc);

	}

	/**
	 * Tab���Z�b�g
	 */
	protected void setTabIndex() {
		int i = 0;
		btnSettle.setTabControlNo(i++);
		btnBack.setTabControlNo(i++);
	}

}

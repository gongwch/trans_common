package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * TAB�p�l���\���p
 */
public class TTabbedTitle extends TPanel {

	/** �L�����Z���{�^���̃A�C�R�� */
	protected static Icon cancelButtonIcon;

	static {
		cancelButtonIcon = ResourceUtil.getImage(TTabbedPane.class, "images/btnCancel.gif");
	}

	/** �^�C�g���� */
	public String title;

	/** ���x�� */
	public JLabel lbl;

	/** �{�^�� */
	public JButton btn;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param title
	 */
	public TTabbedTitle(String title) {
		this.title = title;

		init();
	}

	/**
	 * ������
	 */
	protected void init() {

		setOpaque(false);

		lbl = new JLabel(title);
		lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		add(lbl);

		btn = new JButton(cancelButtonIcon);
		btn.setPreferredSize(new Dimension(cancelButtonIcon.getIconWidth(), cancelButtonIcon.getIconHeight()));

		add(btn);
	}

	/**
	 * �^�C�g���̎擾
	 * 
	 * @return title �^�C�g��
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * �^�C�g���̐ݒ�
	 * 
	 * @param title �^�C�g��
	 */
	public void setTitle(String title) {
		this.title = title;
		lbl.setText(title);
	}

}

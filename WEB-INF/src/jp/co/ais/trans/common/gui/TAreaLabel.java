package jp.co.ais.trans.common.gui;

import javax.swing.*;

/**
 * ���s����̃��x��
 */
public class TAreaLabel extends TTextArea {

	/**
	 * �R���X�g���N�^.
	 */
	public TAreaLabel() {
		super();

		initComponent();
	}

	/**
	 * �\�z
	 */
	protected void initComponent() {
		this.setBorder(BorderFactory.createEmptyBorder()); // ���Ȃ�
		this.setEditable(false); // �ҏW�s��
		this.setFocusable(false); // �t�H�[�J�X�֎~
		this.setOpaque(false);
	}
}

package jp.co.ais.trans.common.gui.event;

import java.awt.event.*;

import javax.swing.*;

/**
 * �V���[�g�J�b�g�L�[���s�̂��߃��X�i�[.<br>
 * �^�C�}�[�Œx�����s������.(�X�v���b�h�V�[�g�Ή�)
 */
public abstract class FunctionKeyListener extends Timer {

	/**
	 * �R���X�g���N�^
	 */
	public FunctionKeyListener() {
		super(200, null);

		ActionListener l = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				FunctionKeyListener.this.stop();
				FunctionKeyListener.this.actionCalled(e);
			}
		};

		addActionListener(l);
	}

	/**
	 * �L�[�C�x���g�������ɃR�[�������.
	 * 
	 * @param evt Action�C�x���g
	 */
	public abstract void actionCalled(ActionEvent evt);
}
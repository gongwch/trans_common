package jp.co.ais.trans.common.gui.event;

import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �V���[�g�J�b�g�L�[���s�̂��߂̃^�C�}�[�C�x���g �x�����s�N���X
 */
public class DoClickListener implements ActionListener {

	/** �����{�^�� */
	private JButton btn;

	/** �x�����s�̂��߂�Timer */
	private Timer tim;

	/**
	 * constructor.
	 * 
	 * @param tim
	 * @param btn
	 */
	public DoClickListener(Timer tim, JButton btn) {
		this.tim = tim;
		this.btn = btn;
	}

	/**
	 * �^�C�}�[�������smethod
	 * 
	 * @param evt
	 */
	public void actionPerformed(ActionEvent evt) {
		this.tim.stop();

		// ���앜��
		TGuiUtil.getParentFrameOrDialog(btn).setEnabled(true);

		// requestFocus()��A��莞�Ԃ������āA�܂�focus�������Ă����Ƃ�
		if (!this.btn.getVerifyInputWhenFocusTarget() || this.btn.hasFocus()) {
			this.btn.doClick();
		}
	}
}

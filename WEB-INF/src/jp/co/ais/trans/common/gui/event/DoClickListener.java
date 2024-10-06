package jp.co.ais.trans.common.gui.event;

import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ショートカットキー実行のためのタイマーイベント 遅延実行クラス
 */
public class DoClickListener implements ActionListener {

	/** 押下ボタン */
	private JButton btn;

	/** 遅延実行のためのTimer */
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
	 * タイマー時限実行method
	 * 
	 * @param evt
	 */
	public void actionPerformed(ActionEvent evt) {
		this.tim.stop();

		// 操作復活
		TGuiUtil.getParentFrameOrDialog(btn).setEnabled(true);

		// requestFocus()後、一定時間をおいて、まだfocusを持っていたとき
		if (!this.btn.getVerifyInputWhenFocusTarget() || this.btn.hasFocus()) {
			this.btn.doClick();
		}
	}
}

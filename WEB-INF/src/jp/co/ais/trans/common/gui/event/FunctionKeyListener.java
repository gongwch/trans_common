package jp.co.ais.trans.common.gui.event;

import java.awt.event.*;

import javax.swing.*;

/**
 * ショートカットキー実行のためリスナー.<br>
 * タイマーで遅延実行させる.(スプレッドシート対応)
 */
public abstract class FunctionKeyListener extends Timer {

	/**
	 * コンストラクタ
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
	 * キーイベント発生時にコールされる.
	 * 
	 * @param evt Actionイベント
	 */
	public abstract void actionCalled(ActionEvent evt);
}
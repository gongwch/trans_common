package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * 画面操作ブロック用GlassPane
 */
public class LockingGlassPane extends JComponent {

	/**
	 * コンストラクタ.
	 */
	public LockingGlassPane() {

		setOpaque(false);

		setFocusTraversalPolicy(new DefaultFocusTraversalPolicy() {

			@Override
			public boolean accept(Component c) {
				return false;
			}
		});
		Set<AWTKeyStroke> s = Collections.emptySet();
		setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, s);
		setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, s);

		addKeyListener(new KeyAdapter() {
			// なし
		});

		addMouseListener(new MouseAdapter() {
			// なし
		});

		requestFocusInWindow();
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	/**
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean flag) {
		super.setVisible(flag);
		setFocusTraversalPolicyProvider(flag);
	}
}

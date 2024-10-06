package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * ��ʑ���u���b�N�pGlassPane
 */
public class LockingGlassPane extends JComponent {

	/**
	 * �R���X�g���N�^.
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
			// �Ȃ�
		});

		addMouseListener(new MouseAdapter() {
			// �Ȃ�
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

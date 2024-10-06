package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * �^�u�p�l����TAB�ړ�����
 */
public class TabbedPaneAction extends AbstractAction {

	/** ���� */
	protected static final String NEXT = "navigateNextTab";

	/** �O�� */
	protected static final String PREV = "navigatePrevTab";

	/** ctrlTab */
	protected static final KeyStroke ctrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK);

	/** ctrlShiftTab */
	protected static final KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK
		| InputEvent.SHIFT_DOWN_MASK);

	/** tab */
	protected TTabbedPane tab;

	/** true:���ցAfalse:�O�� */
	protected boolean isForward = true;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param tab
	 * @param isForward true:���ցAfalse:�O��
	 */
	public TabbedPaneAction(TTabbedPane tab, boolean isForward) {
		this.tab = tab;
		this.isForward = isForward;
	}

	public void actionPerformed(ActionEvent e) {
		int selectedIndex = tab.getSelectedIndex();

		if (isForward) {
			// ����
			if (selectedIndex < tab.getTabCount() - 1) {
				tab.setSelectedIndex(selectedIndex + 1);
			} else {
				tab.setSelectedIndex(0);
			}
		} else {
			// �O��
			if (selectedIndex > 0) {
				tab.setSelectedIndex(selectedIndex - 1);
			} else {
				tab.setSelectedIndex(tab.getTabCount() - 1);
			}
		}
	}

	/**
	 * �S�ẴR���|�ɃC�x���g����<br>
	 * CTRL+TAB�����̃^�u<br>
	 * CTRL+SHIFT+TAB���O�̃^�u
	 * 
	 * @param tabPane
	 * @param cont
	 */
	public static void setTabTransfer(TTabbedPane tabPane, Container cont) {

		if (cont == null || tabPane == null) {
			return;
		}

		TabbedPaneAction nextAction = new TabbedPaneAction(tabPane, true);
		TabbedPaneAction prevAction = new TabbedPaneAction(tabPane, false);

		if (cont instanceof JComponent) {
			JComponent comp = (JComponent) cont;

			InputMap inputMap = comp.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			inputMap.put(ctrlTab, NEXT);
			inputMap.put(ctrlShiftTab, PREV);

			comp.getActionMap().put(NEXT, nextAction);
			comp.getActionMap().put(PREV, prevAction);
		}

		// �q�̃R���|�[�l���g������.
		Component[] arrComp = cont.getComponents();

		if (arrComp == null) {
			return;
		}

		// ���ׂĂ̎q�ɂ��āA�ċA�I�Ɍ�������.
		for (int i = 0; i < arrComp.length; i++) {
			if (arrComp[i] instanceof Container) {
				setTabTransfer(tabPane, (Container) arrComp[i]);
			}
		}
	}

}

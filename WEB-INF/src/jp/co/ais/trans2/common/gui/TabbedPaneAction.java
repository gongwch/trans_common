package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * タブパネルのTAB移動制御
 */
public class TabbedPaneAction extends AbstractAction {

	/** 次へ */
	protected static final String NEXT = "navigateNextTab";

	/** 前へ */
	protected static final String PREV = "navigatePrevTab";

	/** ctrlTab */
	protected static final KeyStroke ctrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK);

	/** ctrlShiftTab */
	protected static final KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK
		| InputEvent.SHIFT_DOWN_MASK);

	/** tab */
	protected TTabbedPane tab;

	/** true:次へ、false:前へ */
	protected boolean isForward = true;

	/**
	 * コンストラクター
	 * 
	 * @param tab
	 * @param isForward true:次へ、false:前へ
	 */
	public TabbedPaneAction(TTabbedPane tab, boolean isForward) {
		this.tab = tab;
		this.isForward = isForward;
	}

	public void actionPerformed(ActionEvent e) {
		int selectedIndex = tab.getSelectedIndex();

		if (isForward) {
			// 次へ
			if (selectedIndex < tab.getTabCount() - 1) {
				tab.setSelectedIndex(selectedIndex + 1);
			} else {
				tab.setSelectedIndex(0);
			}
		} else {
			// 前へ
			if (selectedIndex > 0) {
				tab.setSelectedIndex(selectedIndex - 1);
			} else {
				tab.setSelectedIndex(tab.getTabCount() - 1);
			}
		}
	}

	/**
	 * 全てのコンポにイベントつける<br>
	 * CTRL+TAB→次のタブ<br>
	 * CTRL+SHIFT+TAB→前のタブ
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

		// 子のコンポーネントを検索.
		Component[] arrComp = cont.getComponents();

		if (arrComp == null) {
			return;
		}

		// すべての子について、再帰的に検索する.
		for (int i = 0; i < arrComp.length; i++) {
			if (arrComp[i] instanceof Container) {
				setTabTransfer(tabPane, (Container) arrComp[i]);
			}
		}
	}

}

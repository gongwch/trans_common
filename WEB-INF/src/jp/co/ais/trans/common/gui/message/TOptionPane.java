package jp.co.ais.trans.common.gui.message;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * TOptionPane
 */
public class TOptionPane extends JOptionPane {

	/**
	 * コンストラクタ.
	 * 
	 * @param message
	 * @param messageType
	 * @param optionType
	 * @param icon
	 * @param options
	 * @param initialValue
	 */
	public TOptionPane(Object message, int messageType, int optionType, Icon icon, Object[] options, Object initialValue) {
		super(message, messageType, optionType, icon, options, initialValue);
	}

	/**
	 * 確認メッセージを表示する
	 * 
	 * @param parentComponent
	 * @param message
	 * @param title
	 * @param optionType
	 * @param messageType
	 * @param icon
	 * @param options
	 * @param initialValue
	 * @return selectOption
	 * @throws HeadlessException
	 */
	public static int showOptionDialog(Component parentComponent, Object message, String title, int optionType,
		int messageType, Icon icon, Object[] options, Object initialValue) throws HeadlessException {

		final TOptionPane pane = new TOptionPane(message, messageType, optionType, icon, options, initialValue);

		pane.setInitialValue(initialValue);
		pane.setComponentOrientation(((parentComponent == null) ? getRootFrame() : parentComponent)
			.getComponentOrientation());

		JDialog dialog = pane.createDialog(parentComponent, title);

		{
			// Y、Nのキー押下処理

			KeyStroke keyY = KeyStroke.getKeyStroke(KeyEvent.VK_Y, 0, false);
			Action keyYAction = new AbstractAction() {

				public void actionPerformed(ActionEvent e) {
					pane.setValue(YES_OPTION);
					pane.setVisible(false);
				}
			};
			KeyStroke keyN = KeyStroke.getKeyStroke(KeyEvent.VK_N, 0, false);
			Action keyNAction = new AbstractAction() {

				public void actionPerformed(ActionEvent e) {
					pane.setValue(NO_OPTION);
					pane.setVisible(false);
				}
			};
			dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyY, "Y");
			dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyN, "N");
			dialog.getRootPane().getActionMap().put("Y", keyYAction);
			dialog.getRootPane().getActionMap().put("N", keyNAction);
		}

		pane.selectInitialValue();
		dialog.setVisible(true);
		dialog.dispose();

		Object selectedValue = pane.getValue();

		if (selectedValue == null) return CLOSED_OPTION;
		if (options == null) {
			if (selectedValue instanceof Integer) return ((Integer) selectedValue).intValue();
			return CLOSED_OPTION;
		}
		for (int counter = 0, maxCounter = options.length; counter < maxCounter; counter++) {
			if (options[counter].equals(selectedValue)) return counter;
		}
		return CLOSED_OPTION;
	}

	/**
	 * 初期フォーカスを設定するために、TBasicOptionPaneUIを使用する
	 * 
	 * @see javax.swing.JOptionPane#updateUI()
	 */
	@Override
	public void updateUI() {
		setUI(new TBasicOptionPaneUI());
	}
}

package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * 貸借パネル。色が変わる
 */
public class TDrCrPanel extends TRadioPanel {

	/**
	 * コンストラクタ
	 */
	public TDrCrPanel() {
		super("C00344");

		TGuiUtil.setComponentSize(this, new Dimension(0, 40));

		initComponents();
	}

	/**
	 * コンポーネント生成
	 */
	protected void initComponents() {

		addRadioButton("C00080");
		addRadioButton("C00068");

		radios.get(0).addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				boolean isSelect = e.getStateChange() == ItemEvent.SELECTED;
				TRadioButton btn = (TRadioButton) e.getSource();
				btn.setForeground(isSelect ? Color.BLUE : TUIManager.getTextDefaultForeground());
				btn.setFont(btn.getFont().deriveFont(isSelect ? Font.BOLD : Font.PLAIN));
			}
		});

		radios.get(1).addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				boolean isSelect = e.getStateChange() == ItemEvent.SELECTED;
				TRadioButton btn = (TRadioButton) e.getSource();
				btn.setForeground(isSelect ? Color.RED : TUIManager.getTextDefaultForeground());
				btn.setFont(btn.getFont().deriveFont(isSelect ? Font.BOLD : Font.PLAIN));
			}
		});

		radios.get(0).addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					TRadioButton btn = radios.get(1);
					btn.setSelected(true);
					btn.setForeground(Color.RED);
					btn.setFont(btn.getFont().deriveFont(Font.BOLD));
					btn.requestFocus();
				}
			}
		});

		radios.get(1).addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					TRadioButton btn = radios.get(0);
					btn.setSelected(true);
					btn.setForeground(Color.BLUE);
					btn.setFont(btn.getFont().deriveFont(Font.BOLD));
					btn.requestFocus();
				}
			}
		});

		// 初期値
		TRadioButton btn = radios.get(0);
		btn.setSelected(true);
		btn.setForeground(Color.BLUE);
		btn.setFont(btn.getFont().deriveFont(Font.BOLD));
	}

	/**
	 * DR or CR
	 * 
	 * @param isDR 「DR」true,「CR」false
	 */
	public void setDR(boolean isDR) {
		setSelectON(isDR ? 0 : 1);
	}

	/**
	 * DR or CR
	 * 
	 * @return 「DR」true,「CR」false
	 */
	public boolean isDR() {
		return isSelected(0);
	}
}

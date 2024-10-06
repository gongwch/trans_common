package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

/**
 * 背景色設定DialogCtrl
 */
public class MG0010BackgroundColorDialogCtrl extends PanelAndDialogCtrlBase {

	protected MG0010BackgroundColorDialog dialog;

	protected int r = 0;

	protected int g = 0;

	protected int b = 0;

	protected boolean _isSettle = false;

	protected MG0010EditDisplayDialog parent;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 */
	public MG0010BackgroundColorDialogCtrl(MG0010EditDisplayDialog parent) {
		dialog = new MG0010BackgroundColorDialog(parent, true, this);
		this.parent = parent;
		dialog.setSize(540, 170);
		Color red = Color.RED;
		Color green = Color.GREEN;
		Color blue = Color.BLUE;
		dialog.txtBlue.setBackground(blue);
		dialog.txtGreen.setBackground(green);
		dialog.txtRed.setBackground(red);
		dialog.txtRed.setMaxLength(3);
		dialog.txtGreen.setMaxLength(3);
		dialog.txtBlue.setMaxLength(3);
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
	}

	/**
	 * 表示
	 */
	public void show() {
		dialog.setVisible(true);
	}

	/**
	 * 確定ボタンが押されたかどうか
	 * 
	 * @return boolean 確定の場合true
	 */
	public boolean isSettle() {
		return _isSettle;
	}

	/**
	 * @return Color
	 */
	public Color getColor() {
		return dialog.txtColorSample.getBackground();
	}

	/**
	 * @param color
	 */
	public void setColor(Color color) {
		dialog.txtColorSample.setBackground(color);
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
		dialog.jslRed.setValue(r);
		dialog.jslGreen.setValue(g);
		dialog.jslBlue.setValue(b);
	}

	void disposeDialog(boolean isSettle) {
		_isSettle = isSettle;
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				parent.btnBackgroundColor.requestFocus();
			}
		});

		dialog.setVisible(false);
	}

	void setRed(int value) {
		r = value;
		dialog.txtRed.setValue(value);

		freshSample();
	}

	void setGreen(int value) {
		g = value;
		dialog.txtGreen.setValue(value);

		freshSample();
	}

	void setBlue(int value) {
		b = value;
		dialog.txtBlue.setValue(value);

		freshSample();
	}

	void freshSample() {
		Color color = new Color(r, g, b);
		dialog.txtColorSample.setBackground(color);
		String text = String.format("#%1$02X%2$02X%3$02X", r, g, b);
		dialog.txtColorSample.setEditable(true);
		dialog.txtColorSample.setValue(text);
		dialog.txtColorSample.setEditable(false);
	}
}

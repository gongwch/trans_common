package jp.co.ais.trans2.common.gui;

import java.awt.*;

import jp.co.ais.trans.common.gui.TButton;
import jp.co.ais.trans.common.gui.TPanel;
import jp.co.ais.trans.common.gui.TTextField;

/**
 * 色選択コンポーネント
 * @author AIS
 *
 */
public class TColor extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -4547408225937169080L;

	/** ボタン */
	public TButton btn;

	/** 色表示フィールド */
	public TTextField ctrlColor;

	/** コントローラ */
	protected TColorController controller;

	/**
	 * 
	 *
	 */
	public TColor() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		// コントローラ
		controller = new TColorController(this);

	}

	/**
	 * コンポーネントを初期化する<BR>
	 *
	 */
	protected void initComponents() {
		btn = new TButton();
		ctrlColor = new TTextField();
	}

	/**
	 * コンポーネントを配置する
	 *
	 */
	protected void allocateComponents() {

		setLayout(null);

		// ボタン
		btn.setSize(20, 80);
		btn.setLocation(0, 0);
		add(btn);

		// 色
		ctrlColor.setSize(80, 20);
		ctrlColor.setLocation(80, 0);
		add(ctrlColor);

		int width = btn.getWidth() + ctrlColor.getWidth();

		setSize(width, 20);

	}

	/**
	 * ボタン、色選択ダイアログのキャプション設定
	 * @param caption キャプション
	 */
	public void setCaption(String caption) {
		controller.setCaption(caption);
	}

	/**
	 * 色をセットする
	 * @param color 色
	 */
	public void setColor(Color color) {
		controller.setColor(color);
	}

	/**
	 * 選択された色を返す
	 * @return 選択された色 
	 */
	public Color getColor() {
		return controller.getColor();
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		btn.setTabControlNo(tabControlNo);
	}

}

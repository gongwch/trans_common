package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * TABパネル表示用
 */
public class TTabbedTitle extends TPanel {

	/** キャンセルボタンのアイコン */
	protected static Icon cancelButtonIcon;

	static {
		cancelButtonIcon = ResourceUtil.getImage(TTabbedPane.class, "images/btnCancel.gif");
	}

	/** タイトル名 */
	public String title;

	/** ラベル */
	public JLabel lbl;

	/** ボタン */
	public JButton btn;

	/**
	 * コンストラクター
	 * 
	 * @param title
	 */
	public TTabbedTitle(String title) {
		this.title = title;

		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		setOpaque(false);

		lbl = new JLabel(title);
		lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		add(lbl);

		btn = new JButton(cancelButtonIcon);
		btn.setPreferredSize(new Dimension(cancelButtonIcon.getIconWidth(), cancelButtonIcon.getIconHeight()));

		add(btn);
	}

	/**
	 * タイトルの取得
	 * 
	 * @return title タイトル
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * タイトルの設定
	 * 
	 * @param title タイトル
	 */
	public void setTitle(String title) {
		this.title = title;
		lbl.setText(title);
	}

}

package jp.co.ais.trans.common.gui;

import javax.swing.*;

/**
 * 改行ありのラベル
 */
public class TAreaLabel extends TTextArea {

	/**
	 * コンストラクタ.
	 */
	public TAreaLabel() {
		super();

		initComponent();
	}

	/**
	 * 構築
	 */
	protected void initComponent() {
		this.setBorder(BorderFactory.createEmptyBorder()); // 線なし
		this.setEditable(false); // 編集不可
		this.setFocusable(false); // フォーカス禁止
		this.setOpaque(false);
	}
}

package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 横ラジオボタンパネル
 */
public class TRadioPanel extends TPanel {

	/** ラジオボタン */
	public List<TRadioButton> radios = new LinkedList<TRadioButton>();

	/** ボタングループ */
	protected ButtonGroup btngrpClass = new ButtonGroup();

	/** コンテンツ */
	protected GridBagConstraints gc = new GridBagConstraints();

	/**
	 * コンストラクタ
	 */
	public TRadioPanel() {
		TGuiUtil.setComponentSize(this, new Dimension(0, 45));

		this.setLayout(new GridBagLayout());

		gc.insets = new Insets(0, 0, 0, 0);
		gc.gridy = 0;
		gc.weightx = 1.0d;
		gc.anchor = GridBagConstraints.CENTER;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param title タイトル
	 */
	public TRadioPanel(String title) {
		this();

		// タイトル
		this.setLangMessageID(title);
	}

	/**
	 * ラジオボタン追加
	 * 
	 * @param word 表示文字
	 */
	public void addRadioButton(String word) {
		addRadioButton(word, 50);
	}

	/**
	 * ラジオボタン追加
	 * 
	 * @param word 表示文字
	 * @param width 幅サイズ
	 */
	public void addRadioButton(String word, int width) {

		TRadioButton rdo = new TRadioButton(word);
		TGuiUtil.setComponentSize(rdo, new Dimension(width, 16));
		rdo.setHorizontalAlignment(SwingConstants.CENTER);
		rdo.setMargin(new Insets(0, 0, 0, 0));

		if (radios.isEmpty()) {
			rdo.setSelected(true);
		}

		btngrpClass.add(rdo);

		gc.gridx = radios.size();
		this.add(rdo, gc);

		radios.add(rdo);

		int twidth = 0;
		for (TRadioButton inrdo : radios) {
			twidth += inrdo.getWidth();
		}

		setWidth(twidth + 10);
	}

	/**
	 * 土台パネルの幅変更
	 * 
	 * @param width 幅サイズ
	 */
	public void setWidth(int width) {
		TGuiUtil.setComponentWidth(this, width);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TRadioButton rdo : radios) {
			rdo.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * 指定Indexボタンの選択状態をONにする.
	 * 
	 * @param index 指定Indexボタン
	 */
	public void setSelectON(int index) {
		for (int i = 0; i < radios.size(); i++) {
			radios.get(i).setSelected(i == index);
		}
	}

	/**
	 * 指定Indexボタンの状態を取得する
	 * 
	 * @param index 指定Index
	 * @return true:選択状態
	 */
	public boolean isSelected(int index) {
		return radios.get(index).isSelected();
	}

	/**
	 * 0番目ラジオにItemListenerセット
	 * 
	 * @param listener リスナー
	 */
	public void addItemListener(ItemListener listener) {
		radios.get(0).addItemListener(listener);
	}

	/**
	 * ItemListenerセット
	 * 
	 * @param index 指定Index
	 * @param listener リスナー
	 */
	public void addItemListener(int index, ItemListener listener) {
		radios.get(index).addItemListener(listener);
	}

	/**
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		for (int i = 0; i < radios.size(); i++) {
			radios.get(i).setEnabled(enabled);
		}
	}
}

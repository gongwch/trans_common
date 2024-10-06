package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ラジオボタンパネル
 * 
 * @param <T> Enum
 */
public class TEnumRadioPanel<T extends TEnumRadio> extends TPanel {

	/** ラジオボタン */
	public Map<T, TRadioButton> radios = new LinkedHashMap<T, TRadioButton>();

	/** ボタングループ */
	protected ButtonGroup btngrpClass = new ButtonGroup();

	/** コンテンツ */
	protected GridBagConstraints gc = new GridBagConstraints();

	/** 描画方向 */
	public int alignment = SwingConstants.HORIZONTAL;

	/** 左余白 */
	public int leftMargin = 0;

	/** ラジオボタンの高さ(デフォルトは16で、16より不足分) */
	public int height = 0;

	/**
	 * コンストラクタ(横)
	 */
	public TEnumRadioPanel() {
		this(SwingConstants.HORIZONTAL);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param alignment 横:SwingConstants.HORIZONTAL 縦:SwingConstants.VERTICAL
	 */
	public TEnumRadioPanel(int alignment) {
		this(alignment, 0);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param alignment 横:SwingConstants.HORIZONTAL 縦:SwingConstants.VERTICAL
	 * @param leftMargin 左余白
	 */
	public TEnumRadioPanel(int alignment, int leftMargin) {
		this(alignment, leftMargin, 0);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param alignment 横:SwingConstants.HORIZONTAL 縦:SwingConstants.VERTICAL
	 * @param leftMargin 左余白
	 * @param height ラジオボタンの高さ(デフォルトは16で、16より不足分)
	 */
	public TEnumRadioPanel(int alignment, int leftMargin, int height) {
		TGuiUtil.setComponentSize(this, new Dimension(0, 45));

		this.alignment = alignment;
		this.leftMargin = leftMargin;
		this.height = height;

		this.setLayout(new GridBagLayout());

		gc.insets = new Insets(0, 0, 0, 0);
		if (alignment == SwingConstants.HORIZONTAL) {
			// 横
			gc.gridy = 0;
			gc.weightx = 1.0d;
			gc.anchor = GridBagConstraints.CENTER;
		} else {
			// 縦
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.NORTHWEST;
		}

	}

	/**
	 * コンストラクタ.
	 * 
	 * @param title タイトル
	 */
	public TEnumRadioPanel(String title) {
		this();

		// タイトル
		this.setLangMessageID(title);
	}

	/**
	 * ラジオボタン追加
	 * 
	 * @param e Enum
	 * @param width 幅サイズ
	 */
	public void addRadioButton(T e, int width) {

		TRadioButton rdo = new TRadioButton(e.getName());
		TGuiUtil.setComponentSize(rdo, new Dimension(width, 16 + height));
		rdo.setHorizontalAlignment(SwingConstants.LEFT);
		rdo.setVerticalAlignment(SwingConstants.CENTER);
		rdo.setMargin(new Insets(0, leftMargin, 0, 0));

		if (radios.isEmpty()) {
			rdo.setSelected(true);
		}

		btngrpClass.add(rdo);
		radios.put(e, rdo);

		if (alignment == SwingConstants.HORIZONTAL) {
			// 横
			gc.gridx = radios.size();
			this.add(rdo, gc);

			int twidth = 0;
			for (TRadioButton inrdo : radios.values()) {
				twidth += inrdo.getWidth();
			}

			setWidth(leftMargin + twidth + 10);
		} else {
			// 縦
			gc.gridy = radios.size();
			this.add(rdo, gc);

			int twidth = 0;
			int theight = 0;
			for (TRadioButton inrdo : radios.values()) {
				twidth = Math.max(inrdo.getWidth(), twidth);
				theight += inrdo.getHeight();
			}

			setWidth(leftMargin + twidth + 10);
			setHeight(theight + 30);
		}
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
	 * 土台パネルの高さ変更
	 * 
	 * @param height 高さサイズ
	 */
	public void setHeight(int height) {
		TGuiUtil.setComponentHeight(this, height);
	}

	/**
	 * 水平寄せの設定
	 * 
	 * @param alignment 寄せ
	 */
	public void setHorizontalAlignment(int alignment) {
		for (TRadioButton rdo : radios.values()) {
			rdo.setHorizontalAlignment(alignment);
		}
	}

	/**
	 * 表示文字個別指定
	 * 
	 * @param e Enum
	 * @param word 表示文字
	 */
	public void setLangMessageID(T e, String word) {
		radios.get(e).setLangMessageID(word);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TRadioButton rdo : radios.values()) {
			rdo.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * 指定Indexボタンの選択状態をONにする.
	 * 
	 * @param e 指定Enum
	 */
	public void setSelectON(T e) {
		for (Entry<T, TRadioButton> entry : radios.entrySet()) {
			entry.getValue().setSelected(entry.getKey().equals(e));
		}
	}

	/**
	 * 選択中Enumの戻す
	 * 
	 * @return 選択中Enum(null可能)
	 */
	public T getSelected() {
		for (Entry<T, TRadioButton> entry : radios.entrySet()) {
			if (entry.getValue().isSelected()) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * 指定Enumのラジオボタンの取得
	 * 
	 * @param e Enum
	 * @return 指定Enumのラジオボタン
	 */
	public TRadioButton getRadioButton(T e) {
		return radios.get(e);
	}

	/**
	 * ItemListenerセット<br>
	 * 全ラジオボタン実装
	 * 
	 * @param listener リスナー
	 */
	public void addItemListener(ItemListener listener) {
		for (TRadioButton rdo : radios.values()) {
			rdo.addItemListener(listener);
		}
	}

	/**
	 * ItemListenerセット
	 * 
	 * @param e 指定Enum
	 * @param listener リスナー
	 */
	public void addItemListener(T e, ItemListener listener) {
		radios.get(e).addItemListener(listener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (TRadioButton radio : radios.values()) {
			radio.setEnabled(enabled);
		}
	}
}

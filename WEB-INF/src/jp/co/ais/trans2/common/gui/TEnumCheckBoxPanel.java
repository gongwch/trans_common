package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * チェックボックスパネル
 * 
 * @param <T> Enum
 */
public class TEnumCheckBoxPanel<T extends TEnumRadio> extends TPanel {

	/** チェックボックス */
	public Map<T, TCheckBox> list = new LinkedHashMap<T, TCheckBox>();

	/** コンテンツ */
	protected GridBagConstraints gc = new GridBagConstraints();

	/** 描画方向 */
	public int alignment = SwingConstants.HORIZONTAL;

	/** 左余白 */
	public int leftMargin = 0;

	/** チェックボックスの高さ(デフォルトは16で、16より不足分) */
	public int height = 0;

	/**
	 * コンストラクタ(横)
	 */
	public TEnumCheckBoxPanel() {
		this(SwingConstants.HORIZONTAL);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param alignment 横:SwingConstants.HORIZONTAL 縦:SwingConstants.VERTICAL
	 */
	public TEnumCheckBoxPanel(int alignment) {
		this(alignment, 0);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param alignment 横:SwingConstants.HORIZONTAL 縦:SwingConstants.VERTICAL
	 * @param leftMargin 左余白
	 */
	public TEnumCheckBoxPanel(int alignment, int leftMargin) {
		this(alignment, leftMargin, 0);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param alignment 横:SwingConstants.HORIZONTAL 縦:SwingConstants.VERTICAL
	 * @param leftMargin 左余白
	 * @param height チェックボックスの高さ(デフォルトは16で、16より不足分)
	 */
	public TEnumCheckBoxPanel(int alignment, int leftMargin, int height) {
		TGuiUtil.setComponentSize(this, new Dimension(0, 20));

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
	public TEnumCheckBoxPanel(String title) {
		this();

		// タイトル
		this.setLangMessageID(title);
	}

	/**
	 * チェックボックス追加
	 * 
	 * @param e Enum
	 * @param width 幅サイズ
	 * @return TCheckBox
	 */
	public TCheckBox addCheckBox(T e, int width) {
		return addCheckBox(e, e.getName(), width);
	}

	/**
	 * チェックボックス追加
	 * 
	 * @param e Enum
	 * @param name 名称指定
	 * @param width 幅サイズ
	 * @return TCheckBox
	 */
	public TCheckBox addCheckBox(T e, String name, int width) {

		TCheckBox chk = new TCheckBox(name);
		TGuiUtil.setComponentSize(chk, new Dimension(width, 16 + height));
		chk.setHorizontalAlignment(SwingConstants.LEFT);
		chk.setVerticalAlignment(SwingConstants.CENTER);
		chk.setMargin(new Insets(0, leftMargin, 0, 0));

		if (list.isEmpty()) {
			chk.setSelected(true);
		}

		list.put(e, chk);

		if (alignment == SwingConstants.HORIZONTAL) {
			// 横
			gc.gridx = list.size();
			this.add(chk, gc);

			int twidth = 0;
			for (TCheckBox inrdo : list.values()) {
				twidth += inrdo.getWidth();
			}

			setWidth(leftMargin + twidth + 10);

		} else {
			// 縦
			gc.gridy = list.size();
			this.add(chk, gc);

			int twidth = 0;
			int theight = 0;
			for (TCheckBox inrdo : list.values()) {
				twidth = Math.max(inrdo.getWidth(), twidth);
				theight += inrdo.getHeight();
			}

			setWidth(leftMargin + twidth + 10);
			setHeight(theight + 30);
		}

		return chk;
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
		for (TCheckBox rdo : list.values()) {
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
		list.get(e).setLangMessageID(word);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TCheckBox chk : list.values()) {
			chk.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * 指定Indexボタンの選択状態をONにする.
	 * 
	 * @param arr 指定Enum
	 */
	public void setCheckON(T... arr) {
		for (Entry<T, TCheckBox> entry : list.entrySet()) {
			for (T e : arr) {
				if (entry.getKey().equals(e)) {
					entry.getValue().setSelected(true);
					break;
				}
			}
		}
	}

	/**
	 * 選択中Enumの戻す
	 * 
	 * @return 選択中Enum(null可能)
	 */
	public List<T> getChecked() {
		List<T> resultList = new ArrayList<T>();
		for (Entry<T, TCheckBox> entry : list.entrySet()) {
			if (entry.getValue().isSelected()) {
				resultList.add(entry.getKey());
			}
		}

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
	}

	/**
	 * 対象Enumが選択判定
	 * 
	 * @param e
	 * @return true:対象Enumが選択
	 */
	public boolean isChecked(T e) {
		for (Entry<T, TCheckBox> entry : list.entrySet()) {
			if (entry.getKey().equals(e) && entry.getValue().isSelected()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定Enumのチェックボックスの取得
	 * 
	 * @param e Enum
	 * @return 指定Enumのチェックボックス
	 */
	public TCheckBox getCheckBox(T e) {
		return list.get(e);
	}

	/**
	 * ItemListenerセット<br>
	 * 全チェックボックス実装
	 * 
	 * @param listener リスナー
	 */
	public void addItemListener(ItemListener listener) {
		for (TCheckBox rdo : list.values()) {
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
		list.get(e).addItemListener(listener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (TCheckBox chk : list.values()) {
			chk.setEnabled(enabled);
		}
	}
}

package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.gui.*;

/**
 * Tabコンポーネント
 * 
 * @author AIS
 */
public class TTabbedPane extends JTabbedPane implements TInterfaceTabControl {

	/** タブの高さ */
	protected int tabHeight = 0;

	/** タブの幅 */
	protected int tabWeight = 0;

	/** タブ順 */
	protected int tabControlNo = -1;

	/** タブ移動可・付加 */
	protected boolean isTabEnabled = true;

	/** [×]ボタン押下時のCallBackListener */
	protected List<CallBackListener> cancelButtonCloseCallBackListener;

	/**
	 * コンストラクタ.
	 */
	public TTabbedPane() {
		super();
		setOpaque(true);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param tabPlacement タブ位置
	 */
	public TTabbedPane(int tabPlacement) {
		super(tabPlacement);
		setOpaque(true);
	}

	/**
	 * [×]ボタン押下時のCallBackListenerを追加する
	 * 
	 * @param c CallBackListener
	 */
	public void addCancelButtonCloseCallBackListener(CallBackListener c) {
		if (cancelButtonCloseCallBackListener == null) {
			cancelButtonCloseCallBackListener = new ArrayList<CallBackListener>();
		}
		cancelButtonCloseCallBackListener.add(c);
	}

	/**
	 * タブを追加する
	 * 
	 * @param title タブのタイトル
	 * @param content タブにセットするコンポーネント
	 * @param isClosable 閉じるボタンを追加するか
	 */
	public void addTab(String title, final Component content, boolean isClosable) {

		if (!isClosable) {
			super.addTab(title, content);
			return;
		}

		TTabbedTitle tabPanel = new TTabbedTitle(title);
		tabPanel.btn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				if (cancelButtonCloseCallBackListener != null) {
					for (CallBackListener c : cancelButtonCloseCallBackListener) {
						c.before(content);
					}
				}

				// タブを閉じる
				removeTabAt(indexOfComponent(content));

				if (cancelButtonCloseCallBackListener != null) {
					for (CallBackListener c : cancelButtonCloseCallBackListener) {
						c.after(content);
					}
				}

			}
		});

		super.addTab("", content);
		setTabComponentAt(getTabCount() - 1, tabPanel);
	}

	/**
	 * @param tabIndex
	 * @return TAB
	 */
	public TTabbedTitle getTab(int tabIndex) {
		return (TTabbedTitle) getTabComponentAt(tabIndex);
	}

	/**
	 * InputVerifier対応なし版
	 * 
	 * @param index
	 */
	public void setSelectedIndexNI(int index) {
		super.setSelectedIndex(index);
	}

	/**
	 * InputVerifier対応
	 * 
	 * @see javax.swing.JTabbedPane#setSelectedIndex(int)
	 */
	@Override
	public void setSelectedIndex(int index) {
		Component comp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

		if (getSelectedIndex() == -1 || comp == this || requestFocus(false)) {
			if (comp != null) {
				comp.requestFocus();
			}

			super.setSelectedIndex(index);
		}
	}

	/**
	 * タブ順を設定する
	 * 
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * タブ順を取得する
	 * 
	 * @param no
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * タブ移動可・付加を取得する
	 * 
	 * @return boolean
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * タブ移動可・付加を設定する
	 * 
	 * @param bool
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	/**
	 * タブ切替可能にする
	 */
	public void switchMode() {
		TTabbedPainSwitchModel switchModel = new TTabbedPainSwitchModel();
		switchModel.accept(this);
	}
}

package jp.co.ais.trans.common.gui;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * GUIのためのユーティリティ.
 */
public class TGuiUtil {

	/** ショートカットキー無し */
	public static final int SKEY_NONE = -1;

	/** キーのみ */
	public static final int WITH_NO_KEY = 0;

	/** Shift + キー */
	public static final int WITH_SHIFT_KEY = KeyEvent.VK_SHIFT;

	/** Ctrl + キー */
	public static final int WITH_CTRL_KEY = KeyEvent.VK_CONTROL;

	/** Alt + キー */
	public static final int WITH_ALT_KEY = KeyEvent.VK_ALT;

	/** ボタンタイプ */
	public static final String TYPE_BUTTON = "button";

	/** テキストフィールドタイプ */
	public static final String TYPE_TEXTFIELD = "text";

	/** ラベルタイプ */
	public static final String TYPE_LABEL = "label";

	/** カレンダータイプ */
	public static final String TYPE_CALENDER = "calender";

	/** ボタンフィールドタイプ */
	public static final String TYPE_BUTTON_FIELD = "buttonfield";

	/** 無限ループ回避のための、再帰レベル限度 */
	protected static int LOOP_CHECK_NUM = 100;

	/** 右クリックメニューの単語(テキストフィールド用) */
	protected static String[] popupWords = { "cut", "copy", "paste", "select all" };

	/**
	 * JコンポーネントのsetHorizontalAlignment(int)を使うこと
	 * 
	 * @deprecated
	 * @param comp ボタン
	 * @param align 場所 0:左、1:中央、2:右
	 */
	public static void setButtonHorizonalAlignment(JButton comp, int align) {
		if (align == 0) {
			comp.setHorizontalAlignment(SwingConstants.LEFT);
		} else if (align == 1) {
			comp.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			comp.setHorizontalAlignment(SwingConstants.RIGHT);
		}
	}

	/**
	 * JコンポーネントのsetHorizontalAlignment(int)を使うこと
	 * 
	 * @deprecated
	 * @param comp テキスト
	 * @param align 場所 0:左、1:中央、2:右
	 */
	public static void setTextFieldHorizonalAlignment(JTextField comp, int align) {
		if (align == 0) {
			comp.setHorizontalAlignment(SwingConstants.LEFT);
		} else if (align == 1) {
			comp.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			comp.setHorizontalAlignment(SwingConstants.RIGHT);
		}
	}

	/**
	 * JコンポーネントのsetHorizontalAlignment(int)を使うこと
	 * 
	 * @deprecated
	 * @param comp ラベル
	 * @param align 場所 0:左、1:中央、2:右
	 */
	public static void setLabelHorizonalAlignment(JLabel comp, int align) {
		if (align == 0) {
			comp.setHorizontalAlignment(SwingConstants.LEFT);
		} else if (align == 1) {
			comp.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			comp.setHorizontalAlignment(SwingConstants.RIGHT);
		}
	}

	/**
	 * コンポーネントの横幅を設定する.
	 * 
	 * @param comp コンポーネント
	 * @param size 設定サイズ
	 */
	public static void setComponentSize(JComponent comp, Dimension size) {
		comp.setPreferredSize(size);
		comp.setMaximumSize(size);
		comp.setMinimumSize(size);
		comp.setSize(size);
	}

	/**
	 * 幅を指定する.MIN,MAX,PER
	 * 
	 * @param comp 対象コンポーネント
	 * @param width 幅
	 */
	public static void setComponentWidth(JComponent comp, int width) {
		setComponentSize(comp, new Dimension(width, comp.getPreferredSize().height));
	}

	/**
	 * 高さを指定する.MIN,MAX,PER
	 * 
	 * @param comp 対象コンポーネント
	 * @param height 高さ
	 */
	public static void setComponentHeight(JComponent comp, int height) {
		setComponentSize(comp, new Dimension(comp.getPreferredSize().width, height));
	}

	/**
	 * サイズを指定する.MIN,MAX,PER
	 * 
	 * @param comp 対象コンポーネント
	 * @param width 幅
	 * @param height 高さ
	 */
	public static void setComponentSize(JComponent comp, int width, int height) {
		setComponentSize(comp, new Dimension(width, height));
	}

	/**
	 * 親コンテナをたどり、java.awt.Window, java.awt.Appletを探す.
	 * 
	 * @param p 元コンテナ
	 * @return 親コンテナ
	 */
	public static Container getParentRoot(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof Window || p instanceof Applet) {
				return p;
			}
		}
		return p;
	}

	/**
	 * 親コンテナをたどり、java.awt.Windowを探す.
	 * 
	 * @param p 元コンテナ
	 * @return 親コンテナ
	 */
	public static Window getParentWindow(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof Window) {
				return (Window) p;
			}
		}
		return null;
	}

	/**
	 * 親コンテナをたどり、TDialogを探す.
	 * 
	 * @param p 元コンテナ
	 * @return 親コンテナ
	 */
	public static TDialog getParentTDialog(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof TDialog) {
				return (TDialog) p;
			}
		}
		return null;
	}

	/**
	 * 親コンテナをたどり、java.awt.Frame, java.awt.Dialogを探す.<br>
	 * （引数がContainer版）
	 * 
	 * @param p 元コンテナ
	 * @return 親コンテナ
	 */
	public static Container getParentFrameOrDialog(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof Frame || p instanceof Dialog) {
				return p;
			}
		}
		return p;
	}

	/**
	 * 親コンテナをたどり、java.awt.Frame, java.awt.Dialogを探す.<br>
	 * （引数がComponent版）
	 * 
	 * @param p 元コンテナ
	 * @return 親コンテナ
	 */
	public static Component getParentFrameOrDialog2(Component p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof Frame || p instanceof Dialog) {
				return p;
			}
		}
		return p;
	}

	/**
	 * 親コンテナをたどり、TPanelBusiness, TDialog, TFrameを探す.
	 * 
	 * @param p 元コンテナ
	 * @return 親コンテナ
	 */
	public static Container getPanelBusiness(Container p) {

		if (p == null) {
			return null;
		}

		for (; p != null; p = p.getParent()) {
			if (p instanceof TPanelBusiness || p instanceof TDialog || p instanceof TFrame) {
				return p;
			}
		}
		return null;
	}

	/**
	 * ファンクションキーの処理をTPanelBusinessに代行させる.
	 * 
	 * @param cont
	 * @param evt
	 */
	public static void dispatchPanelBusinessFunctionKey(Container cont, KeyEvent evt) {

		if (isDispatchPanelBusinessFunctionKey(cont, evt)) {
			Container busi = TGuiUtil.getPanelBusiness(cont);
			busi.dispatchEvent(evt);
		}
	}

	/**
	 * ファンクションキーの処理をTPanelBusinessに代行させることができるかチェックする.
	 * 
	 * @param cont
	 * @param evt
	 * @return true:正常実行
	 */
	public static boolean isDispatchPanelBusinessFunctionKey(Container cont, KeyEvent evt) {

		Container busi = TGuiUtil.getPanelBusiness(cont);
		if (busi == null) {
			return false;
		}

		int fkey = TGuiUtil.checkFunctionKeyEvent(evt);

		if (fkey <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * Enterキーの処理をする.
	 * 
	 * @param cont
	 * @param evt
	 */
	public static void transferFocusByEnterKey(Container cont, KeyEvent evt) {

		if (cont instanceof TTableComponent) {
			// テーブルセルエディタ利用はフォーカス無し
			if (((TTableComponent) cont).isTableCellEditor()) {
				return;
			}
		}

		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

			Container root = getPanelBusiness(cont);
			if (root == null) {
				cont.transferFocus();
				return;
			}

			// ダイアログがActiveでない場合、処理しない
			if (root instanceof TDialog && !((TDialog) root).isActive()) {
				return;
			}

			TInterfaceTabPolicy tp = (TInterfaceTabPolicy) root;

			boolean mode = tp.getModeEnter();
			tp.setModeEnter(true);

			if (evt.isShiftDown()) {
				cont.transferFocusBackward();
			} else {
				cont.transferFocus();
			}

			tp.setModeEnter(mode);

			// Enterキー再Eventでなければ設定
			if (evt.getKeyChar() != '0') {
				tp.setEventBefore(new KeyEvent((Component) evt.getSource(), evt.getID(), evt.getWhen(), evt
					.getModifiers(), evt.getKeyCode(), '0'));
			} else {
				tp.setEventBefore(null);
			}

		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {

			Container root = getPanelBusiness(cont);
			if (root == null) {
				cont.transferFocus();
				return;
			}

			// ダイアログがActiveでない場合、処理しない
			if (root instanceof TDialog && !((TDialog) root).isActive()) {
				return;
			}

			TInterfaceTabPolicy tp = (TInterfaceTabPolicy) root;

			boolean mode = tp.getModeEnter();
			tp.setModeEnter(false); // tabモード

			if (evt.isShiftDown()) {
				cont.transferFocusBackward();
			} else {
				cont.transferFocus();
			}

			tp.setModeEnter(mode);

			// Tabキー再Eventでなければ設定
			if (evt.getKeyChar() != '0') {
				tp.setEventBefore(new KeyEvent((Component) evt.getSource(), evt.getID(), evt.getWhen(), evt
					.getModifiers(), evt.getKeyCode(), '0'));
			} else {
				tp.setEventBefore(null);
			}

		} else {

			Container root = getPanelBusiness(cont);
			if (root == null) {
				return;
			}

			TInterfaceTabPolicy tp = (TInterfaceTabPolicy) root;
			tp.setEventBefore(null);
		}
	}

	/**
	 * Enterキーの処理を再度行う.
	 * 
	 * @param cont
	 */
	public static void transferBeforeFocusByEnterKey(Container cont) {

		Container root = getPanelBusiness(cont);
		if (root == null) {
			return;
		}

		TInterfaceTabPolicy tp = (TInterfaceTabPolicy) root;

		// 前回のイベント
		KeyEvent beforeEvent = tp.getEventBefore();
		if (beforeEvent == null) {
			return;
		}

		if (beforeEvent.getSource() instanceof JTextComponent) {

			SwingUtilities.invokeLater(new ExecLater(cont, beforeEvent));
			tp.setEventBefore(null);
		}
	}

	// message ID関連

	/**
	 * 再帰的に子のComponentにmessageIDに対応するメッセージを登録する.
	 * 
	 * @param cont カレントコンテナ.
	 * @param lang 言語コード
	 */
	public static void translateLangMessageID(Container cont, String lang) {

		TGuiUtil.recursiveTranslateLangMessageID(TGuiUtil.getParentRoot(cont), lang, 0);
	}

	/**
	 * 再帰的に子のComponentにmessageIDに対応するメッセージを登録する.
	 * 
	 * @param cont カレントコンテナ.
	 * @param lang 言語コード
	 * @param level ループ限界階層.
	 */
	public static void recursiveTranslateLangMessageID(Container cont, String lang, int level) {

		if (LOOP_CHECK_NUM < level) {
			return;
		}
		if (cont == null) {
			return;
		}

		// パネル.
		if (cont instanceof JPanel) {
			// borderありで、BorderがmessageIDを設定できるアイテムなら MessageIDをMapに登録.
			translatePanelLangMessageID((JPanel) cont, lang);

		} else if (cont instanceof TInterfaceLangMessageID) {
			// パネル以外で、MessegeIDを設定できるアイテム.
			translateContainerLangMessageID(cont, lang);

		} else if (cont instanceof TInterfaceTableLangMessageID) {
			// Tableアイテム.
			translateTableLangMessageID(cont, lang);
		}

		// 子のコンポーネントを検索.
		Component[] arrComp = cont.getComponents();

		if (arrComp == null) {
			return;
		}

		// すべての子について、再帰的に検索する.
		for (int i = 0; i < arrComp.length; i++) {
			if (arrComp[i] instanceof Container) {
				recursiveTranslateLangMessageID((Container) arrComp[i], lang, level + 1);
			}
		}

		return;
	}

	/**
	 * panelのmessage IDをmessageに変換する. <br>
	 * borderありで、BorderがTTitledBorder classなら、 Messageを設定する.
	 * 
	 * @param panel
	 * @param lang
	 */
	protected static void translatePanelLangMessageID(JPanel panel, String lang) {

		if (panel.getBorder() == null) {
			return;
		}

		// message IDのinterfaceを持っている.
		if (panel.getBorder() instanceof TTitledBorder) {
			TTitledBorder tborder = (TTitledBorder) panel.getBorder();

			if (tborder.getLangMessageID() != null) {
				tborder.setTitle(MessageUtil.getWord(lang, tborder.getLangMessageID()));
			}

		} else if (panel instanceof TPanel && panel.getBorder() instanceof TitledBorder) {
			TitledBorder tborder = (TitledBorder) panel.getBorder();

			TPanel tpanel = (TPanel) panel;
			if (tpanel.getLangMessageID() != null) {
				tborder.setTitle(MessageUtil.getWord(lang, tpanel.getLangMessageID()));
			}
		}
	}

	/**
	 * Containerのmessage IDをmessageに変換する.
	 * 
	 * @param cont
	 * @param lang
	 */
	protected static void translateContainerLangMessageID(Container cont, String lang) {

		TInterfaceLangMessageID ifMessage = (TInterfaceLangMessageID) cont;
		String wordId = ifMessage.getLangMessageID();

		if (Util.isNullOrEmpty(wordId)) {
			return;
		}

		boolean isShortWord = Util.isShortLanguage(lang);

		if (cont instanceof JTabbedPane) {
			// JTabbedPane
			JTabbedPane tab = (JTabbedPane) cont;

			// タブの分IDがあるので、分割
			String[] msgIDs = StringUtil.toArrayFromDelimitString(wordId);

			for (int i = 0; i < tab.getTabCount(); i++) {
				String wordID = (i < msgIDs.length) ? msgIDs[i] : "";

				if (isShortWord) {
					// 略語の場合
					tab.setTitleAt(i, MessageUtil.getShortWord(lang, wordID));
					tab.setToolTipTextAt(i, MessageUtil.getWord(lang, wordID));

				} else {
					tab.setTitleAt(i, MessageUtil.getWord(lang, wordID));
				}
			}

			return;

		} else if (cont instanceof JDialog) {
			// Dialog
			((JDialog) cont).setTitle(MessageUtil.getWord(lang, wordId));

			return;
		}

		// その他

		String text;
		String tip;

		if (isShortWord) {
			// 英語の場合
			text = MessageUtil.getShortWord(lang, wordId);
			tip = MessageUtil.getWord(lang, wordId);

		} else {
			text = MessageUtil.getWord(lang, wordId);
			tip = text;
		}

		if (cont instanceof JLabel) {
			// label.
			((JLabel) cont).setText(text);
			((JLabel) cont).setToolTipText(tip);

		} else if (cont instanceof AbstractButton) {
			// button, checkbutton, radiobutton.
			((AbstractButton) cont).setText(text);
			((AbstractButton) cont).setToolTipText(tip);

		} else if (cont instanceof JTextArea) {
			// TextArea
			((JTextArea) cont).setText(text);
			((JTextArea) cont).setToolTipText(tip);
		}
	}

	/**
	 * Tableのmessage IDをmessageに変換する.
	 * 
	 * @param cont
	 * @param lang
	 */
	protected static void translateTableLangMessageID(Container cont, String lang) {

		if (!(cont instanceof TTable)) {
			return;
		}

		TTable table = (TTable) cont;

		// 行タイトル.
		if (table.getRowTitleMessageID() != null) {
			String[] arr = table.getRowTitleMessageID();
			String[] title = new String[arr.length];

			for (int i = 0; i < arr.length; i++) {
				title[i] = MessageUtil.getWord(lang, arr[i]);
			}

			table.setRowLabels(title);

			if (table.getDataSource() instanceof JCVectorDataSource) {
				((JCVectorDataSource) table.getDataSource()).setRowLabels(title);
			}
		}

		// 列タイトル.
		if (table.getColumnTitleMessageID() != null) {
			String[] arr = table.getColumnTitleMessageID();
			String[] title = new String[arr.length];

			for (int i = 0; i < arr.length; i++) {
				title[i] = MessageUtil.getWord(lang, arr[i]);
			}

			table.setColumnLabels(title);

			if (table.getDataSource() instanceof JCVectorDataSource) {
				((JCVectorDataSource) table.getDataSource()).setColumnLabels(title);
			}

			if (table.isAutoColumnWidthSet()) {
				for (int i = 0; i < title.length; i++) {
					if (table.getCharWidth(i) <= 0) {
						continue;
					}

					int width = title[i].getBytes().length * 2 / 3;

					if (table.getCharWidth(i) >= width) {
						continue;
					}

					table.setCharWidth(i, width);
				}
			}
		}
	}

	// tab control No.関連

	/**
	 * ComponentからtabControlNoを収集する.
	 * 
	 * @param cont カレントコンテナ.
	 * @param lstTab Labelをkeyに設定
	 */
	static void collectTabControlNo(Container cont, List<TNameComponent> lstTab) {

		TGuiUtil.recursiveCollectTabControlNo(cont, lstTab, 0);
	}

	/**
	 * 再帰的に子のComponentからtabControlNoを収集する.
	 * 
	 * @param cont カレントコンテナ.
	 * @param lstTab Labelをkeyに設定
	 * @param level ループ限界階層.
	 */
	protected static void recursiveCollectTabControlNo(Container cont, List<TNameComponent> lstTab, int level) {

		if (LOOP_CHECK_NUM < level) {
			return;
		}
		if (cont == null) {
			return;
		}

		// パネル.

		if (cont instanceof JPanel) {
			// パネルのときなにもしない.

			// パネル以外で、tabNoを設定できるアイテム.
		} else if (cont instanceof TInterfaceTabControl) {
			// tabNoをListに登録.
			collectCompnentTabControlNo(cont, lstTab);
		}

		// 子のコンポーネントを検索.
		Component[] arrComp = cont.getComponents();

		if (arrComp == null) {
			return;
		}

		// すべての子について、再帰的に検索する.
		for (int i = 0; i < arrComp.length; i++) {
			if (arrComp[i] instanceof Container) {
				recursiveCollectTabControlNo((Container) arrComp[i], lstTab, level + 1);
			}
		}

		return;
	}

	/**
	 * 再帰的に子のComponent(TButton)からショートカットキーを収集する.
	 * 
	 * @param cont カレントコンテナ.
	 * @param fkyeMap ファンクションキー
	 * @param level ループ限界階層.
	 */
	static void recursiveCollectFunctionKey(Container cont, Map<Integer, JButton[]> fkyeMap, int level) {

		if (LOOP_CHECK_NUM < level) {
			return;
		}
		if (cont == null) {
			return;
		}

		if (cont instanceof TButton) {
			TButton button = (TButton) cont;
			int type = button.getShortcutType();

			if (type != SKEY_NONE) {

				int index = getFKeyNo(button.getShortcutKey());

				if (button.isAutoFkeyWord()) {
					// (ショートカットキー)文字を自動的に挿入
					StringBuilder keyStr = new StringBuilder("(");
					switch (type) {
						case KeyEvent.VK_SHIFT:
							keyStr.append("Shift+");
							break;
						case KeyEvent.VK_CONTROL:
							keyStr.append("Ctrl+");
							break;
						case KeyEvent.VK_ALT:
							keyStr.append("Alt+");
							break;
					}

					if (index == 13) {
						keyStr.append("PageUp");

					} else if (index == 14) {
						keyStr.append("PageDn");

					} else if (index == 15) {
						keyStr.append("Esc");

					} else {
						keyStr.append("F").append(index);
					}

					keyStr.append(")");

					button.setText(button.getText() + keyStr.toString());
				}

				JButton[] fbuttons = fkyeMap.get(type);
				fbuttons[index - 1] = button;
			}
		}

		// 子のコンポーネントを検索.
		Component[] arrComp = cont.getComponents();

		if (arrComp == null) {
			return;
		}

		// すべての子について、再帰的に検索する.
		for (int i = 0; i < arrComp.length; i++) {
			if (arrComp[i] instanceof Container) {
				recursiveCollectFunctionKey((Container) arrComp[i], fkyeMap, level + 1);
			}
		}
	}

	/**
	 * Fキーイベントに対応する番号を返す
	 * 
	 * @param eventKey
	 * @return Fキー対応数字
	 */
	protected static int getFKeyNo(int eventKey) {
		switch (eventKey) {
			case KeyEvent.VK_F1:
				return 1;
			case KeyEvent.VK_F2:
				return 2;
			case KeyEvent.VK_F3:
				return 3;
			case KeyEvent.VK_F4:
				return 4;
			case KeyEvent.VK_F5:
				return 5;
			case KeyEvent.VK_F6:
				return 6;
			case KeyEvent.VK_F7:
				return 7;
			case KeyEvent.VK_F8:
				return 8;
			case KeyEvent.VK_F9:
				return 9;
			case KeyEvent.VK_F10:
				return 10;
			case KeyEvent.VK_F11:
				return 11;
			case KeyEvent.VK_F12:
				return 12;
			case KeyEvent.VK_PAGE_UP:
				return 13;
			case KeyEvent.VK_PAGE_DOWN:
				return 14;
			case KeyEvent.VK_ESCAPE:
				return 15;
			default:
				return 0;
		}
	}

	/**
	 * tab順登録.
	 * 
	 * @param listTabControlNo タブ順番号とcomponentのリスト
	 * @param listTabComponent tab順componentリスト
	 */
	public static void changeTabControlNo(List<TNameComponent> listTabControlNo, List<Component> listTabComponent) {

		listTabComponent.clear();

		if (listTabControlNo == null) {
			return;
		}

		TNameComponent nv;
		while (0 < listTabControlNo.size()) {
			int minPos = 0;
			int minVal = -1;
			for (int i = 0; i < listTabControlNo.size(); i++) {
				nv = listTabControlNo.get(i);
				if (nv == null) continue;

				if (minVal <= nv.getName()) {
					minVal = nv.getName();
					minPos = i;
				}
			}
			nv = listTabControlNo.get(minPos);
			listTabComponent.add(0, nv.getValue());
			listTabControlNo.remove(minPos);
		}
	}

	/**
	 * panelのmessage IDを収集する. <br>
	 * borderありで、BorderがTTitledBorder classなら、 Messageを収集する.
	 * 
	 * @param cont
	 * @param list
	 */
	protected static void collectCompnentTabControlNo(Container cont, List<TNameComponent> list) {
		// tabNoをListに登録.
		TInterfaceTabControl ifTab = (TInterfaceTabControl) cont;
		if (0 <= ifTab.getTabControlNo()) {
			list.add(new TNameComponent(ifTab.getTabControlNo(), cont));
		}
	}

	/**
	 * key入力の判定.
	 * 
	 * @param e イベント
	 * @return 0:Function-keyでない >1:Function-keyである
	 */
	static public int checkFunctionKeyEvent(KeyEvent e) {

		if (e == null) {
			return 0;
		}

		return getFKeyNo(e.getKeyCode());
	}

	/**
	 * 同時押しkey入力の判定.
	 * 
	 * @param e イベント
	 * @return WITH_SHIFT_KEY | WITH_CTRL_KEY | WIDTH_ALT_KEYの組み合わせ
	 */
	static public int checkWithKeyEvent(KeyEvent e) {

		if (e == null) {
			return 0;
		}

		int code = e.getKeyCode();
		String name = KeyEvent.getKeyText(code);

		if (name == null) {
			return 0;
		}

		int mkey = 0;

		if (e.isShiftDown()) {
			mkey |= WITH_SHIFT_KEY;
		}

		if (e.isControlDown()) {
			mkey |= WITH_CTRL_KEY;
		}

		if (e.isAltDown()) {
			mkey |= WITH_ALT_KEY;
		}

		return mkey;
	}

	/**
	 * 同時に特殊キーが押されていたかのチェック
	 * 
	 * @param e キーイベント
	 * @return true:同時押し
	 */
	static public boolean isSpecialKeyDown(KeyEvent e) {
		if (e.isAltDown() || e.isAltGraphDown() || e.isControlDown() || e.isMetaDown() || e.isShiftDown()) {
			return true;
		}
		return false;
	}

	/**
	 * 入力可能な状態にあるかのチェック
	 * 
	 * @param comp 評価するコンポーネント
	 * @return true:入力可能
	 */
	static public boolean isActive(Component comp) {

		if (comp == null) {
			return false;
		}

		if (!comp.isShowing()) return false;
		if (!comp.isEnabled()) return false;

		if (comp instanceof JTextField && !((JTextField) comp).isEditable()) return false;
		if (comp instanceof JComboBox && !((JComboBox) comp).isEditable()) return false;

		return true;
	}

	/**
	 * コンポーネントの入力チェック
	 * 
	 * @param comp
	 * @return false:入力チェックNG true:入力チェックOKまたは入力アイテムでない
	 */
	static public boolean isInputVerifyOk(Object comp) {
		if (TGuiUtil.getInpVerifier(comp) != null) {
			if (!TGuiUtil.getInpVerifier(comp).verify((JComponent) comp)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * コンポーネントの入力チェッククラス取得
	 * 
	 * @param comp
	 * @return false:入力チェックNG true:入力チェックOKまたは入力アイテムでない
	 */
	static public InputVerifier getInpVerifier(Object comp) {
		if (comp instanceof JComponent) {
			JComponent jcomp = (JComponent) comp;
			return (jcomp.getInputVerifier());
		}
		return null;
	}

	/**
	 * コンポーネントの入力チェッククラス設定
	 * 
	 * @param comp
	 * @param veri
	 */
	static public void setInpVerifier(Object comp, InputVerifier veri) {
		if (comp instanceof JComponent) {
			JComponent jcomp = (JComponent) comp;
			jcomp.setInputVerifier(veri);
		}
	}

	/**
	 * コンポーネント階層の上位のTButtonFieldオブジェクトを返す
	 * 
	 * @param cont アイテムコンテナ
	 * @return ButtonField
	 */
	static TButtonField getUpperButtonField(Container cont) {

		for (; cont != null; cont = cont.getParent()) {

			if (cont instanceof TButtonField) {
				return (TButtonField) cont;
			}
		}

		return null;
	}

	/**
	 * 子コンポーネントのTInputVerifier有効／無効化
	 * 
	 * @param win
	 * @param flag
	 */
	static public void setComponentsVerifierEnabled(Window win, boolean flag) {
		if (win == null) {
			return;
		}
		// インスタンスを使ってロックをかけ、TInputVerifierとの競合をなくす。
		synchronized (win) {
			TInputVerifier.setActiveChildren(win, flag);
		}
	}

	/**
	 * 指定されたタイプに合わせ、縦横サイズ補正を行い返す.(フォントサイズ変更用)
	 * 
	 * @param type タイプ
	 * @param d 元サイズ
	 * @return 補正後のサイズ
	 */
	public static Dimension correctSize(String type, Dimension d) {

		double wdeff = ClientConfig.getDeffWidth(type);
		double hdeff = ClientConfig.getDeffHeight(type);

		if (wdeff == 0.0d && hdeff == 0.0d) {
			return d;
		}

		Dimension dim = new Dimension();
		dim.setSize(d.width + wdeff, d.height + hdeff);

		return dim;
	}

	/**
	 * UNDO/REDO登録
	 * 
	 * @param tc 対象テキストコンポーネント
	 */
	public static void initUndoRedo(final JTextComponent tc) {

		final UndoManager manager = new UndoManager();
		tc.getDocument().addUndoableEditListener(manager);
		tc.getActionMap().put("undo", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				if (manager.canUndo() && tc.isEditable()) {
					manager.undo();
				}
			}
		});

		tc.getActionMap().put("redo", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				if (manager.canRedo() && tc.isEditable()) {
					manager.redo();
				}
			}
		});

		InputMap imap = tc.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK), "undo");
		imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK), "redo");
	}

	/**
	 * 右クリックカット＆ペースト用マウスリスナーを生成
	 * 
	 * @param tc 対象テキストコンポーネント
	 * @return リスナー
	 */
	public static MouseListener createCnPListener(final JTextComponent tc) {

		MouseListener listener = new MouseAdapter() {

			/** メニュー */
			protected JPopupMenu popup = new JPopupMenu();

			/** TABキー用リスナ */
			protected FocusListener flistener = new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {
					popup.setVisible(false);
				}
			};

			/** クリックイベント */
			@Override
			public void mouseClicked(MouseEvent e) {

				if (!tc.isShowing() || !tc.isEnabled()) {
					return;
				}

				if (tc.isFocusOwner() && SwingUtilities.isRightMouseButton(e)) {
					popup.removeAll();

					boolean isEditable = tc.isEditable();

					if (Util.isNullOrEmpty(tc.getSelectedText())) {
						makeAnP(isEditable);

					} else {
						makeCnP(isEditable);
					}

					// TABキー対応
					popup.addPopupMenuListener(new PopupMenuListener() {

						@SuppressWarnings("hiding")
						public void popupMenuCanceled(PopupMenuEvent e) {
							// なし
						}

						@SuppressWarnings("hiding")
						public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
							tc.removeFocusListener(flistener);
						}

						@SuppressWarnings("hiding")
						public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
							tc.addFocusListener(flistener);
						}
					});

					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			/**
			 * カット、コピー、ペースト実装
			 */
			protected void makeCnP(boolean isEditable) {

				// カット
				JMenuItem cut = new JMenuItem(popupWords[0]);
				cut.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.cut();
					}
				});

				// コピー
				JMenuItem copy = new JMenuItem(popupWords[1]);
				copy.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.copy();
					}
				});

				// ペースト
				JMenuItem past = new JMenuItem(popupWords[2]);
				past.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.paste();
					}
				});

				popup.setFocusable(false);
				cut.setEnabled(isEditable);
				past.setEnabled(isEditable);
				popup.add(cut);
				popup.add(copy);
				popup.add(past);
			}

			/**
			 * 全て選択、ペースト実装
			 */
			protected void makeAnP(boolean isEditable) {

				// 全て選択
				JMenuItem select = new JMenuItem(popupWords[3]);
				select.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.selectAll();
					}
				});

				// ペースト
				JMenuItem past = new JMenuItem(popupWords[2]);
				past.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						tc.paste();
					}
				});

				popup.setFocusable(false);
				past.setEnabled(isEditable);
				popup.add(select);
				popup.add(past);
			}
		};

		return listener;
	}

	/**
	 * テキスト 右クリックメニューの単語
	 * 
	 * @param wordList 右クリックメニュー単語リスト
	 */
	public static void setLightPopupMenuWords(String[] wordList) {
		popupWords = wordList;
	}

	/**
	 *
	 */
	protected static class ExecLater implements Runnable {

		/***/
		protected KeyEvent evt;

		/***/
		protected Container cont;

		/**
		 * @param cont
		 * @param evt
		 */
		public ExecLater(Container cont, KeyEvent evt) {
			this.evt = evt;
			this.cont = cont;
		}

		public void run() {
			transferFocusByEnterKey(this.cont, this.evt);
		}
	}
}

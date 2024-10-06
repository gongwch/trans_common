package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.text.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 検索フィールド
 */
public class TSearchField extends TPanel {

	/** タイトル */
	public TLabel lbl;

	/** 検索入力 */
	public TTextField field;

	/** 検索ボタン */
	public TButton btn;

	/** 備考 */
	public TLabel comment;

	/** 親 */
	protected JComponent parent;

	/**
	 * コンストラクター
	 * 
	 * @param parent
	 */
	public TSearchField(JComponent parent) {
		this.parent = parent;

		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		initComponents();
		allocateComponents();
		initEvents();
	}

	/**
	 * 初期化
	 */
	protected void initComponents() {
		lbl = new TLabel();
		field = new TTextField();
		btn = new TButton();
		comment = new TLabel();
	}

	/**
	 * コンポ設定
	 */
	protected void allocateComponents() {
		setLayout(null);
		TGuiUtil.setComponentSize(this, 185, 40);

		int x = 0;
		int y = 0;

		// タイトル
		TGuiUtil.setComponentSize(lbl, 55, 20);
		lbl.setLangMessageID("SEARCH");
		lbl.setLocation(x, y);
		add(lbl);

		x += lbl.getWidth();

		// 入力
		TGuiUtil.setComponentSize(field, 95, 20);
		field.setLocation(x, y);
		add(field);

		x += field.getWidth();

		// ボタン
		btn.setSize(20, 35);
		btn.setLocation(x, y);
		add(btn);

		x = 0;
		y += btn.getHeight();

		// 備考
		TGuiUtil.setComponentSize(comment, 185, 20);
		comment.setLocation(x, y);
		add(comment);

	}

	/**
	 * イベント設定
	 */
	protected void initEvents() {
		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btn_Click();
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		field.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				highLighter();
			}
		});
	}

	/**
	 * ボタン押す
	 */
	protected void btn_Click() {

		removeAllHighlights(parent);
		field.clear();
	}

	/**
	 * @param comp
	 */
	protected void removeAllHighlights(Component comp) {

		if (comp == this) {
			return;
		} else if (comp instanceof JTextArea) {
			Highlighter l = ((JTextArea) comp).getHighlighter();
			l.removeAllHighlights();

		} else if (comp instanceof JTextField) {
			Highlighter l = ((JTextField) comp).getHighlighter();
			l.removeAllHighlights();

		} else if (comp instanceof TLabelField) {
			Highlighter l = ((TLabelField) comp).getField().getHighlighter();
			l.removeAllHighlights();

		} else if (comp instanceof TLabelNumericField) {
			Highlighter l = ((TLabelNumericField) comp).getField().getHighlighter();
			l.removeAllHighlights();

		} else if (comp instanceof TTable) {
			((TTable) comp).removeAllHighlights();

		} else if (comp instanceof Container) {
			for (Component c : ((Container) comp).getComponents()) {
				removeAllHighlights(c);
			}
		}

	}

	/**
	 * ボタン押す
	 */
	protected void highLighter() {
		removeAllHighlights(parent);

		if (Util.isNullOrEmpty(field.getText())) {
			return;
		}

		Pattern pattern = Pattern.compile(Pattern.quote(field.getText().toLowerCase()));
		addHighlights(parent, pattern);
	}

	/**
	 * @param comp
	 * @param pattern
	 */
	protected void addHighlights(Component comp, Pattern pattern) {

		if (comp == this) {
			return;
		} else if (comp instanceof JTextArea) {
			Highlighter l = ((JTextArea) comp).getHighlighter();
			match(((JTextArea) comp).getText(), pattern, l);

		} else if (comp instanceof JTextField) {
			Highlighter l = ((JTextField) comp).getHighlighter();
			match(((JTextField) comp).getText(), pattern, l);

		} else if (comp instanceof TLabelField) {
			Highlighter l = ((TLabelField) comp).getField().getHighlighter();
			match(((TLabelField) comp).getValue(), pattern, l);

		} else if (comp instanceof TLabelNumericField) {
			Highlighter l = ((TLabelNumericField) comp).getField().getHighlighter();
			match(((TLabelNumericField) comp).getValue(), pattern, l);

		} else if (comp instanceof TTable) {
			((TTable) comp).setHighlighterPattern(pattern);

		} else if (comp instanceof Container) {
			for (Component c : ((Container) comp).getComponents()) {
				addHighlights(c, pattern);
			}
		}

	}

	/**
	 * @param txt
	 * @param pattern
	 * @param l
	 */
	protected void match(String txt, Pattern pattern, Highlighter l) {

		Matcher matcher = pattern.matcher(txt.toLowerCase());

		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();

			try {
				l.addHighlight(start, end, DefaultPainter);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**  */
	public static final THighlightPainter DefaultPainter = new THighlightPainter();

}

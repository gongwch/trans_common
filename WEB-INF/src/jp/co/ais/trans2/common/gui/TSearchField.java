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
 * �����t�B�[���h
 */
public class TSearchField extends TPanel {

	/** �^�C�g�� */
	public TLabel lbl;

	/** �������� */
	public TTextField field;

	/** �����{�^�� */
	public TButton btn;

	/** ���l */
	public TLabel comment;

	/** �e */
	protected JComponent parent;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param parent
	 */
	public TSearchField(JComponent parent) {
		this.parent = parent;

		init();
	}

	/**
	 * ������
	 */
	protected void init() {
		initComponents();
		allocateComponents();
		initEvents();
	}

	/**
	 * ������
	 */
	protected void initComponents() {
		lbl = new TLabel();
		field = new TTextField();
		btn = new TButton();
		comment = new TLabel();
	}

	/**
	 * �R���|�ݒ�
	 */
	protected void allocateComponents() {
		setLayout(null);
		TGuiUtil.setComponentSize(this, 185, 40);

		int x = 0;
		int y = 0;

		// �^�C�g��
		TGuiUtil.setComponentSize(lbl, 55, 20);
		lbl.setLangMessageID("SEARCH");
		lbl.setLocation(x, y);
		add(lbl);

		x += lbl.getWidth();

		// ����
		TGuiUtil.setComponentSize(field, 95, 20);
		field.setLocation(x, y);
		add(field);

		x += field.getWidth();

		// �{�^��
		btn.setSize(20, 35);
		btn.setLocation(x, y);
		add(btn);

		x = 0;
		y += btn.getHeight();

		// ���l
		TGuiUtil.setComponentSize(comment, 185, 20);
		comment.setLocation(x, y);
		add(comment);

	}

	/**
	 * �C�x���g�ݒ�
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
	 * �{�^������
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
	 * �{�^������
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

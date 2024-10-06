package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import jp.co.ais.trans.common.gui.*;

/**
 * フッター
 */
public class MFooter extends TPanel {

	/**
	 * 列タイプ
	 */
	public static class TYPE {

		/** BLANK:空欄 */
		public static final int BLANK = 0;

		/** LABEL:ラベル表示用 */
		public static final int LABEL = 1;

		/** SUM:合計列 */
		public static final int SUM = 2;

		/** TXT:検索文字フィールド */
		public static final int TXT = 3;

		/** YMD:検索日付フィールド */
		public static final int YMD = 4;

		/** YMDHM:検索日時フィールド */
		public static final int YMDHM = 5;

		/** S_YMD:検索用日時フィールド */
		public static final int S_YMD = 7;
	}

	/** 固定ラベル */
	public TLabel lbl;

	/** 合計フィールドリスト */
	public List<JComponent> compList;

	/** 列リスト */
	public List<Enum> colList;

	/** 列タイプ */
	public List<Integer> typeList;

	/** 紐付くテーブル */
	public TTable tbl;

	/** フィールドClass */
	protected Class fieldClass;

	/** 文字フィールドClass */
	protected Class<? extends TTextField> textClass;

	/** 検索日付用フィールドClass */
	protected Class<? extends THalfwayDateField> searchDateClass;

	/** 日付フィールドClass */
	protected Class<? extends TCalendar> calClass;

	/** 位置X */
	protected int locationX = 0;

	/**
	 * コンストラクター
	 * 
	 * @param tbl
	 * @param fieldClass
	 */
	public MFooter(TTable tbl, Class fieldClass) {
		this(tbl, fieldClass, TTextField.class, THalfwayDateField.class, TCalendar.class);
	}

	/**
	 * コンストラクター
	 * 
	 * @param tbl
	 * @param fieldClass
	 * @param textClass
	 * @param searchDateClass
	 * @param calClass
	 */
	public MFooter(TTable tbl, Class fieldClass, Class textClass, Class searchDateClass, Class calClass) {
		// 紐付くテーブル
		this.tbl = tbl;
		this.fieldClass = fieldClass;
		this.textClass = textClass;
		this.searchDateClass = searchDateClass;
		this.calClass = calClass;

		// 初期化
		initFooter();
	}

	/**
	 * 初期化(コンストラクター)
	 */
	public void initFooter() {

		colList = new ArrayList<Enum>();
		typeList = new ArrayList<Integer>();

		ChangeListener cl = new ChangeListener() {

			boolean adjustFlag = false;

			public void stateChanged(ChangeEvent e) {
				if (!adjustFlag) {
					adjustFlag = true;
					syncStateChanged();
					adjustFlag = false;
				}
			}
		};

		tbl.getViewport().addChangeListener(cl);
	}

	/**
	 * 同期
	 */
	protected void syncStateChanged() {
		Point point = tbl.getViewport().getViewPosition();
		locationX = -point.x;

		resizeComponents();

		tbl.adapter.fireMTableFooterChanged();
	}

	/**
	 * 列指定
	 * 
	 * @param cols
	 * @param types
	 */
	public void setColumns(Enum[] cols, int[] types) {
		for (int i = 0; i < cols.length; i++) {
			Enum col = cols[i];
			int type = types[i];

			colList.add(col);
			typeList.add(type);
		}
	}

	/**
	 * 初期化
	 */
	public void init() {
		initComponents();
		resizeComponents();
		allocateComponents();
	}

	/**
	 * 初期化
	 */
	protected void initComponents() {
		setFocusable(false);
		setFocusCycleRoot(false);

		lbl = createLabel();
		compList = new ArrayList<JComponent>();

		for (int i = 0; i < colList.size(); i++) {
			JComponent comp = createField(typeList.get(i));
			compList.add(comp);
		}
	}

	/**
	 * @return ラベル
	 */
	public TLabel createLabel() {
		TLabel l = new TLabel();
		l.setOpaque(true);
		l.setHorizontalAlignment(SwingConstants.RIGHT);
		l.setBackground(TTable.columnColor);
		l.setForeground(TTable.columnFontColor);
		return l;
	}

	/**
	 * @param type
	 * @return 表示コンポ
	 */
	public JComponent createField(int type) {
		if (TYPE.TXT == type) {
			return createTextField();
		} else if (TYPE.YMD == type) {
			return createCalendarField(TCalendar.TYPE_YMD);
		} else if (TYPE.YMDHM == type) {
			return createCalendarField(TCalendar.TYPE_YMDHM);
		} else if (TYPE.S_YMD == type) {
			return createSearchDateField();
		} else {
			return createNumbericField();
		}
	}

	/**
	 * @return 検索フィールド
	 */
	public JComponent createTextField() {
		try {
			TTextField field = textClass.newInstance();
			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param calType
	 * @return 検索日付フィールド
	 */
	public JComponent createCalendarField(int calType) {
		try {
			TCalendar field = calClass.getDeclaredConstructor(Integer.class).newInstance(calType);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @return 検索日付用フィールド
	 */
	public JComponent createSearchDateField() {
		try {
			THalfwayDateField field = searchDateClass.newInstance();
			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @return 合計フィールド
	 */
	public JComponent createNumbericField() {
		try {
			TNumericField field = (TNumericField) fieldClass.newInstance();
			field.setEditable(false);
			field.setNumericFormat("#,##0");
			field.setMaxLength(17, 4);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 幅調整
	 */
	public void resizeComponents() {

		int x = locationX;
		int y = 0;

		// 行番号幅
		int rowColumnWidth = tbl.getRowColumnWidth();

		{
			// ラベル位置設定

			int width = rowColumnWidth;
			for (int i = 0; i < colList.size(); i++) {
				Enum col = colList.get(i);
				int type = typeList.get(i);

				if (type == TYPE.LABEL) {
					int w = tbl.getColumnWidth(col);
					w = Math.max(0, w);

					width += w;
				} else {
					break;
				}
			}

			lbl.setLocation(x, y);
			setWidth(lbl, width);
		}

		x = locationX + rowColumnWidth;

		// 合計列設定
		for (int i = 0; i < colList.size(); i++) {
			Enum col = colList.get(i);
			int type = typeList.get(i);

			int width = tbl.getColumnWidth(col);
			width = Math.max(0, width);

			if (type == TYPE.BLANK || type == TYPE.LABEL) {
				// 合計列以外
				compList.add(null);
			} else {
				// 合計列
				JComponent comp = compList.get(i);
				compList.add(comp);

				comp.setLocation(x, y);
				setWidth(comp, width);
			}

			x += width;
		}
	}

	/**
	 * コンポ設定
	 */
	protected void allocateComponents() {

		setLayout(null);

		lbl.setText("TOTAL ");
		add(lbl);

		for (int i = 0; i < compList.size(); i++) {
			JComponent comp = compList.get(i);
			if (comp == null) {
				continue;
			}

			add(comp);
		}

	}

	/**
	 * 幅設定<br>
	 * 高さは20固定
	 * 
	 * @param comp
	 * @param width
	 */
	protected static void setWidth(JComponent comp, int width) {
		TGuiUtil.setComponentSize(comp, width, 20);
	}

	/**
	 * @param labelCols
	 * @return 初期列タイプ
	 */
	public static int[] createDefaultType(Enum[] labelCols) {
		int[] types = new int[labelCols.length];
		types[0] = TYPE.LABEL;

		// 初期全部ラベルにする
		for (Enum col : labelCols) {
			types[col.ordinal()] = TYPE.LABEL;
		}

		return types;
	}

	/**
	 * 空欄設定<br>
	 * 最後の空欄以降のラベル表示欄は自動的に空欄を設定する<br>
	 * なので、連続空欄であれば、一つ目空欄だけでもOK
	 * 
	 * @param types
	 * @param blankCols
	 */
	public static void setBlankType(int[] types, Enum... blankCols) {
		int lastIndex = 0;
		for (Enum col : blankCols) {
			lastIndex = col.ordinal();
			types[col.ordinal()] = TYPE.BLANK;
		}

		if (lastIndex + 1 < types.length) {
			for (int i = lastIndex + 1; i < types.length; i++) {
				if (types[i] == TYPE.LABEL) {
					types[i] = TYPE.BLANK;
				}
			}
		}
	}

	/**
	 * 合計列設定
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setSumType(int[] types, Enum... cols) {
		setType(types, TYPE.SUM, cols);
	}

	/**
	 * 文字列設定
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setTextType(int[] types, Enum... cols) {
		setType(types, TYPE.TXT, cols);
	}

	/**
	 * YMD設定
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setYMDType(int[] types, Enum... cols) {
		setType(types, TYPE.YMD, cols);
	}

	/**
	 * YMDHM設定
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setYMDHMType(int[] types, Enum... cols) {
		setType(types, TYPE.YMDHM, cols);
	}

	/**
	 * 検索YMD設定
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setSearchDateType(int[] types, Enum... cols) {
		setType(types, TYPE.S_YMD, cols);
	}

	/**
	 * タイプ設定
	 * 
	 * @param types
	 * @param type
	 * @param cols
	 */
	public static void setType(int[] types, int type, Enum... cols) {
		for (Enum col : cols) {
			types[col.ordinal()] = type;
		}
	}
}

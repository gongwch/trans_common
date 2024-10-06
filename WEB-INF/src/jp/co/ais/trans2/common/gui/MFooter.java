package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �t�b�^�[
 */
public class MFooter extends TPanel {

	/**
	 * ��^�C�v
	 */
	public static class TYPE {

		/** BLANK:�� */
		public static final int BLANK = 0;

		/** LABEL:���x���\���p */
		public static final int LABEL = 1;

		/** SUM:���v�� */
		public static final int SUM = 2;

		/** TXT:���������t�B�[���h */
		public static final int TXT = 3;

		/** YMD:�������t�t�B�[���h */
		public static final int YMD = 4;

		/** YMDHM:���������t�B�[���h */
		public static final int YMDHM = 5;

		/** S_YMD:�����p�����t�B�[���h */
		public static final int S_YMD = 7;
	}

	/** �Œ胉�x�� */
	public TLabel lbl;

	/** ���v�t�B�[���h���X�g */
	public List<JComponent> compList;

	/** �񃊃X�g */
	public List<Enum> colList;

	/** ��^�C�v */
	public List<Integer> typeList;

	/** �R�t���e�[�u�� */
	public TTable tbl;

	/** �t�B�[���hClass */
	protected Class fieldClass;

	/** �����t�B�[���hClass */
	protected Class<? extends TTextField> textClass;

	/** �������t�p�t�B�[���hClass */
	protected Class<? extends THalfwayDateField> searchDateClass;

	/** ���t�t�B�[���hClass */
	protected Class<? extends TCalendar> calClass;

	/** �ʒuX */
	protected int locationX = 0;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param tbl
	 * @param fieldClass
	 */
	public MFooter(TTable tbl, Class fieldClass) {
		this(tbl, fieldClass, TTextField.class, THalfwayDateField.class, TCalendar.class);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param tbl
	 * @param fieldClass
	 * @param textClass
	 * @param searchDateClass
	 * @param calClass
	 */
	public MFooter(TTable tbl, Class fieldClass, Class textClass, Class searchDateClass, Class calClass) {
		// �R�t���e�[�u��
		this.tbl = tbl;
		this.fieldClass = fieldClass;
		this.textClass = textClass;
		this.searchDateClass = searchDateClass;
		this.calClass = calClass;

		// ������
		initFooter();
	}

	/**
	 * ������(�R���X�g���N�^�[)
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
	 * ����
	 */
	protected void syncStateChanged() {
		Point point = tbl.getViewport().getViewPosition();
		locationX = -point.x;

		resizeComponents();

		tbl.adapter.fireMTableFooterChanged();
	}

	/**
	 * ��w��
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
	 * ������
	 */
	public void init() {
		initComponents();
		resizeComponents();
		allocateComponents();
	}

	/**
	 * ������
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
	 * @return ���x��
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
	 * @return �\���R���|
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
	 * @return �����t�B�[���h
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
	 * @return �������t�t�B�[���h
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
	 * @return �������t�p�t�B�[���h
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
	 * @return ���v�t�B�[���h
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
	 * ������
	 */
	public void resizeComponents() {

		int x = locationX;
		int y = 0;

		// �s�ԍ���
		int rowColumnWidth = tbl.getRowColumnWidth();

		{
			// ���x���ʒu�ݒ�

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

		// ���v��ݒ�
		for (int i = 0; i < colList.size(); i++) {
			Enum col = colList.get(i);
			int type = typeList.get(i);

			int width = tbl.getColumnWidth(col);
			width = Math.max(0, width);

			if (type == TYPE.BLANK || type == TYPE.LABEL) {
				// ���v��ȊO
				compList.add(null);
			} else {
				// ���v��
				JComponent comp = compList.get(i);
				compList.add(comp);

				comp.setLocation(x, y);
				setWidth(comp, width);
			}

			x += width;
		}
	}

	/**
	 * �R���|�ݒ�
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
	 * ���ݒ�<br>
	 * ������20�Œ�
	 * 
	 * @param comp
	 * @param width
	 */
	protected static void setWidth(JComponent comp, int width) {
		TGuiUtil.setComponentSize(comp, width, 20);
	}

	/**
	 * @param labelCols
	 * @return ������^�C�v
	 */
	public static int[] createDefaultType(Enum[] labelCols) {
		int[] types = new int[labelCols.length];
		types[0] = TYPE.LABEL;

		// �����S�����x���ɂ���
		for (Enum col : labelCols) {
			types[col.ordinal()] = TYPE.LABEL;
		}

		return types;
	}

	/**
	 * �󗓐ݒ�<br>
	 * �Ō�̋󗓈ȍ~�̃��x���\�����͎����I�ɋ󗓂�ݒ肷��<br>
	 * �Ȃ̂ŁA�A���󗓂ł���΁A��ڋ󗓂����ł�OK
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
	 * ���v��ݒ�
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setSumType(int[] types, Enum... cols) {
		setType(types, TYPE.SUM, cols);
	}

	/**
	 * ������ݒ�
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setTextType(int[] types, Enum... cols) {
		setType(types, TYPE.TXT, cols);
	}

	/**
	 * YMD�ݒ�
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setYMDType(int[] types, Enum... cols) {
		setType(types, TYPE.YMD, cols);
	}

	/**
	 * YMDHM�ݒ�
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setYMDHMType(int[] types, Enum... cols) {
		setType(types, TYPE.YMDHM, cols);
	}

	/**
	 * ����YMD�ݒ�
	 * 
	 * @param types
	 * @param cols
	 */
	public static void setSearchDateType(int[] types, Enum... cols) {
		setType(types, TYPE.S_YMD, cols);
	}

	/**
	 * �^�C�v�ݒ�
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

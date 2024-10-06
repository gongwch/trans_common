package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TComboBox.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.table.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.user.*;

/**
 * �e�[�u���R���|�[�l���g
 * 
 * @author AIS
 */
@SuppressWarnings("unused")
public class TTable extends JScrollPane implements TStorable {

	/** �����������̃^�C�v */
	public enum AutoSizeType {
		/** �w�b�_�[�ƒl�̍ő啝 */
		HeaderAndContents,
		/** �w�b�_�[�̍ő啝 */
		HeaderOnly,
		/** �l�̍ő啝 */
		ContentsOnly,
		/** �w�b�_�[�̍ő啝�Ɛݒ�l�̔�r���ʂ̍ő啝 */
		HeaderAdjust;
	}

	/** �w�b�_�[�̃J���[ */
	public static Color columnColor = new Color(90, 90, 90);

	/** �w�b�_�[�̃t�H���g�J���[ */
	public static Color columnFontColor = Color.WHITE;

	/** ���I�����̃J���[ */
	public static Color notSelectedColor = new Color(255, 255, 255);

	/** ���I�����̃J���[(�ʐF) */
	public static Color notSelectedColor2 = new Color(230, 240, 250);

	/** �I�����̃J���[ */
	public static Color selectedColor = new Color(0, 102, 204);

	/** �I�����̃t�H���g�J���[ */
	public static Color selectedCellFontColor = Color.WHITE;

	/** ���I�����̃t�H���g�J���[ */
	public static Color cellFontColor = Color.BLACK;

	/** Enter�L�[���� */
	public static final String ACTION_KEY_ENTER = "ACTION_KEY_ENTER";

	/** Shift+Enter�L�[���� */
	public static final String ACTION_KEY_SHIFT_ENTER = "ACTION_KEY_SHIFT_ENTER";

	/** �s�̍��� */
	protected static int rowHeight = 25;

	/** �s�ԍ��̕� */
	protected static int rowNumberWidth = 30;

	/** �^�u�� �����ړ� */
	protected static Set<AWTKeyStroke> forwardSet = new HashSet<AWTKeyStroke>();

	/** �^�u�� �t���ړ� */
	protected static Set<AWTKeyStroke> backwardSet = new HashSet<AWTKeyStroke>();

	/** �^�u�� �����ړ�(�f�t�H���g) */
	protected static Set<AWTKeyStroke> defaltForwardSet = new HashSet<AWTKeyStroke>();

	/** �^�u�� �t���ړ�(�f�t�H���g) */
	protected static Set<AWTKeyStroke> defaultBackwardSet = new HashSet<AWTKeyStroke>();

	/** �E�N���b�N���j���[�̒P�� */
	protected static String[] popupWords = { "Initialization", "Column width to header and contents",
			"Column width to header", "Column width to contents" };

	/** ���l��r */
	protected static TNumberComparator numberComparator = new TNumberComparator();

	/** �t�H���g��` */
	protected FontMetrics fm = null;

	/**
	 * �w�b�_�[���N���b�N�����ۂɁA�`�F�b�N�{�b�N�X�̃J�����Ȃ��<br>
	 * �S�`�F�b�N�@�\��L���ɂ��邩
	 */
	protected boolean allCheckWhenHeaderClicked = true;

	/** JTable�ւ̂Ȃ� */
	protected TTableAdapter adapter = null;

	static {
		// �����l�擾
		Color columnColor_ = ClientConfig.getTableLabelColor();
		Color columnFontColor_ = ClientConfig.getTableLabelFontColor();
		Color notSelectedColor_ = ClientConfig.getTableCellBackColor1();
		Color notSelectedColor2_ = ClientConfig.getTableCellBackColor2();
		Color selectedColor_ = ClientConfig.getTableSelectColor();
		Color selectedCellFontColor_ = ClientConfig.getTableSelectCellFontColor();
		Color cellFontColor_ = ClientConfig.getTableCellFontColor();
		int rowHeight_ = ClientConfig.getTableCellHeight();

		if (columnColor_ != null) {
			columnColor = columnColor_;
		}

		if (columnFontColor_ != null) {
			columnFontColor = columnFontColor_;
		}

		if (notSelectedColor_ != null) {
			notSelectedColor = notSelectedColor_;
		}

		if (notSelectedColor2_ != null) {
			notSelectedColor2 = notSelectedColor2_;
		}

		if (selectedColor_ != null) {
			selectedColor = selectedColor_;
		}

		if (selectedCellFontColor_ != null) {
			selectedCellFontColor = selectedCellFontColor_;
		}

		if (cellFontColor_ != null) {
			cellFontColor = cellFontColor_;
		}

		if (rowHeight_ != 0) {
			rowHeight = rowHeight_;
		}

		// TAB�L�[�t�H�[�J�X�ړ�
		forwardSet.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0));
		backwardSet.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK));

		defaltForwardSet.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK));
		defaultBackwardSet
			.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
	}

	/** �e�[�u���{�� */
	public BaseTable table;

	/** �J���� */
	protected List<TTableColumn> columns = null;

	/** �e�[�u������ۑ�����ꍇ�A���̕����L�[ */
	protected String tableKeyName = null;

	/** �t�B���^�����O�̃|�b�v�A�b�v */
	protected JPopupMenu popupMenu = null;

	/** �t�B���^�����O�̃|�b�v�A�b�v�A�C�e�� */
	protected TTableFilterPopupMenuItem popupMenuItem = null;

	/** �_�u���N���b�N�����N�{�^�� */
	protected TButton doubleClickLinkButton;

	/** �s�ԍ��p�e�[�u�� */
	protected JComponent rowHeaderComp;

	/** �s�ԍ��p�e�[�u�� */
	protected JTable rowHeaderView;

	/** true:Enter�L�[�Ń{�^�����s */
	protected boolean isEnterToButton = false;

	/** �e�[�u���\�[�g */
	protected TableRowSorter<? extends TableModel> sorter = null;

	/** �\�[�g�\��(�S��) */
	protected boolean sortable = true;

	/** true:TAB�͊O�Ɉړ����Ȃ��Afalse:�S��ʈړ� */
	protected boolean tabTraverseCell = false;

	/** ���l��r�g�����ǂ��� */
	protected boolean useNumberComparator = true;

	/** ������� */
	protected TTableInformation initTableInformation;

	/** ��ǉ����̐ݒ� */
	protected ColumnSetting columnSetting = null;

	/** SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g */
	protected List<Integer> checkBoxColumns = null;

	/** �\��t���@�\�g�����Btrue:�g�� */
	protected boolean useTablePaste = false;

	/** �s�ԍ��J�n�ԍ� */
	protected int rowNumPlus = 1;

	/**  */
	protected Pattern highlightPattern = null;

	/** Enter���� */
	protected Object enterAct = null;

	/** Shift+Enter���� */
	protected Object shiftEnterAct = null;

	/** Enter�L�[�ŏ㉺�J�ڃ��[�h */
	protected boolean useEnterKeyUpDownAction = false;

	/** ���ݍs���� */
	protected int currentRowHeight = rowHeight;

	/**
	 * �R���X�g���N�^
	 */
	public TTable() {
		init();
	}

	/**
	 * ��������
	 */
	public void init() {

		// ScrollPane����
		this.setOpaque(false);
		this.getViewport().setOpaque(false);

		table = createBaseTable();
		setViewportTable(table);
		table.adapter = createTTableAdapter();
		adapter = table.adapter;
		adapter.setTable(table);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setSurrendersFocusOnKeystroke(true);

		table.setRowHeight(rowHeight);

		// �s�ԍ�
		rowHeaderComp = createRowHeaderTable();
		rowHeaderComp.setFocusable(false);
		rowHeaderComp.setFocusCycleRoot(false);
		rowHeaderView = getRowHeaderView();

		this.setRowHeaderView(rowHeaderComp);
		getRowHeader().setOpaque(false); // ����

		addKeyEvent();
		addKeyListener();
		addMouseListener();

		sorter = createTableRowSorter();

		table.setRowSorter(sorter);

		// �E�N���b�N���j���[������
		initPopupMenu();

	}

	/**
	 * @param tbl
	 */
	protected void setViewportTable(BaseTable tbl) {
		setViewportView(tbl);
	}

	/**
	 * @return JTable�Ƃ�Adapter
	 */
	protected TTableAdapter createTTableAdapter() {
		return new TTableAdapter();
	}

	/**
	 * @return �s�w�b�_�[�pTable
	 */
	protected JComponent createRowHeaderTable() {
		return new RowHeaderTable(this, rowNumberWidth, rowHeight);
	}

	/**
	 * @return �s�w�b�_�[�pTable
	 */
	public JTable getRowHeaderView() {
		return (JTable) rowHeaderComp;
	}

	/**
	 * @return �\�[�^�[
	 */
	protected TableRowSorter<TableModel> createTableRowSorter() {
		return new TableRowSorter<TableModel>(table.getModel()) {

			@Override
			public boolean isSortable(int column) {
				if (!sortable) {
					return false;
				}
				if (columns != null && !columns.isEmpty()) {
					if (columns.get(column).getComponent() != null) {
						return false;
					}
				}
				return true;
			}

			@Override
			public Comparator<?> getComparator(int column) {
				if (useNumberComparator && columns != null && !columns.isEmpty()) {
					if (columns.get(column).getHorizontalAlign() == SwingConstants.RIGHT) {
						return numberComparator;
					}
				}
				return super.getComparator(column);
			}
		};
	}

	/**
	 * �E�N���b�N���j���[������
	 */
	protected void initPopupMenu() {

		// �E�N���b�N���j���[������
		popupMenu = new JPopupMenu();

		JMenuItem clear = new JMenuItem(popupWords[0]);
		clear.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				clearTableInformation();
			}
		});

		popupMenu.add(clear);

		JMenuItem autosizeHeaderAndContents = new JMenuItem(popupWords[1]);
		autosizeHeaderAndContents.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// ����������
				adjustTableInformation(AutoSizeType.HeaderAndContents);

			}
		});

		popupMenu.add(autosizeHeaderAndContents);

		JMenuItem autosizeHeader = new JMenuItem(popupWords[2]);
		autosizeHeader.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// ����������
				adjustTableInformation(AutoSizeType.HeaderOnly);

			}
		});

		popupMenu.add(autosizeHeader);

		JMenuItem autosizeContents = new JMenuItem(popupWords[3]);
		autosizeContents.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// ����������
				adjustTableInformation(AutoSizeType.ContentsOnly);

			}
		});

		popupMenu.add(autosizeContents);
	}

	/**
	 * �L�[�n�C�x���g�o�^
	 */
	protected void addKeyEvent() {

		// ENTER�L�[���E�ړ�
		InputMap im = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

		// Enter�L�[�f�t�H���gActionMap������
		enterAct = im.get(enter);

		im.put(enter, im.get(tab));

		KeyStroke stab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK);
		KeyStroke senter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK);

		// Shift+Enter�L�[�f�t�H���gActionMap������
		shiftEnterAct = im.get(senter);

		im.put(senter, im.get(stab));

		// TAB�L�[�t�H�[�J�X�ړ�
		table.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardSet);
		table.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardSet);

		// �f�t�H���gF8�L�[������
		ActionMap actionMap = table.getActionMap();
		actionMap.remove("focusHeader");

		while ((actionMap = actionMap.getParent()) != null) {
			actionMap.remove("focusHeader");
		}

		// ----------------------------------------------------------------
		// �V���b�g�J�b�g�L�[����F1�`F12(���ځAShift�{�ACtrl�{)

		int[] vkeys = { 0, InputEvent.CTRL_DOWN_MASK, InputEvent.SHIFT_DOWN_MASK };

		for (int i : vkeys) {
			for (int j = KeyEvent.VK_F1; j <= KeyEvent.VK_F12; j++) {
				im.put(KeyStroke.getKeyStroke(j, i), "dummy_key");
			}
		}

		table.getActionMap().put("dummy_key", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				// �����Ȃ�
			}
		});
		// ----------------------------------------------------------------
	}

	/**
	 * �x�[�X�e�[�u��
	 * 
	 * @return �x�[�X�e�[�u��
	 */
	protected BaseTable createBaseTable() {
		return new BaseTable();
	}

	/**
	 * �\�[�g�@�\���O��
	 */
	public void setSortOff() {
		sortable = false;
	}

	/**
	 * true:TAB�͊O�Ɉړ����Ȃ��Afalse:�S��ʈړ��̎擾
	 * 
	 * @return tabTraverseCell true:TAB�͊O�Ɉړ����Ȃ��Afalse:�S��ʈړ�
	 */
	public boolean isTabTraverseCell() {
		return tabTraverseCell;
	}

	/**
	 * true:TAB�͊O�Ɉړ����Ȃ��Afalse:�S��ʈړ��̐ݒ�
	 * 
	 * @param tabTraverseCell true:TAB�͊O�Ɉړ����Ȃ��Afalse:�S��ʈړ�
	 */
	public void setTabTraverseCell(boolean tabTraverseCell) {
		this.tabTraverseCell = tabTraverseCell;

		if (this.tabTraverseCell) {
			// TAB�͊O�Ɉړ����Ȃ�
			table.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, defaltForwardSet);
			table.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, defaultBackwardSet);
		} else {
			// TAB�L�[�t�H�[�J�X�ړ�
			table.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardSet);
			table.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardSet);
		}
	}

	/**
	 * TAB�L�[�Ŏ��̃Z���Ɉړ�����悤�ɐݒ肷��.
	 */
	public void setTabTraverseCell() {
		setTabTraverseCell(true);
	}

	/**
	 * ��̃^�C�g���A�����w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 */
	public void addColumn(Enum e, String title, int width) {
		addColumn(e, title, width, SwingConstants.LEFT);
	}

	/**
	 * ��̃^�C�g���A���A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param visible �\��/��\��
	 */
	public void addColumn(Enum e, String title, int width, boolean visible) {
		addColumn(e, title, width, SwingConstants.LEFT, visible);
	}

	/**
	 * ��̃^�C�g���A�����w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 */
	public void addColumn(String title, int width) {
		addColumn(null, title, width, SwingConstants.LEFT);
	}

	/**
	 * ��̃^�C�g���A�����w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 */
	public void addColumn(String title, int width, int horizontalAlign) {
		addColumn(null, title, width, horizontalAlign);
	}

	/**
	 * ��̃^�C�g���A���A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param visible �\��/��\��
	 */
	public void addColumn(String title, int width, boolean visible) {
		addColumn(null, title, width, SwingConstants.LEFT, visible);
	}

	/**
	 * ��̃^�C�g���A���A���������\���ʒu���w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 */
	public void addColumn(Enum e, String title, int width, int horizontalAlign) {
		addColumn(new TTableColumn(e, title, width, horizontalAlign));
	}

	/**
	 * ��̃^�C�g���A���A���������\���ʒu�A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 * @param visible �\��/��\��
	 */
	public void addColumn(Enum e, String title, int width, int horizontalAlign, boolean visible) {
		addColumn(new TTableColumn(e, title, width, horizontalAlign, visible));
	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g���w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 */
	public void addColumn(Enum e, String title, int width, Class<? extends TTableComponent> component) {
		try {
			TTableComponent instance = component.newInstance();
			addColumn(
				new TTableColumn(e, title, width, instance.getDefaultCellRendererHorizontalAlignment(), instance));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g���w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 */
	public void addColumn(Enum e, String title, int width, TTableComponent component) {
		try {

			addColumn(
				new TTableColumn(e, title, width, component.getDefaultCellRendererHorizontalAlignment(), component));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g�A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 * @param visible �\��/��\��
	 */
	public void addColumn(Enum e, String title, int width, TTableComponent component, boolean visible) {
		try {
			addColumn(new TTableColumn(e, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component, visible));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g���w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 */
	public void addColumn(String title, int width, TTableComponent component) {
		try {
			addColumn(new TTableColumn(null, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component, true));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g�A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 * @param visible �\��/��\��
	 */
	public void addColumn(String title, int width, TTableComponent component, boolean visible) {
		try {
			addColumn(new TTableColumn(null, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component, visible));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ���ǉ�����
	 * 
	 * @param column ��
	 */
	public void addColumn(TTableColumn column) {

		if (columns == null) {
			columns = new ArrayList<TTableColumn>();
		}

		// ��ǉ��̐ݒ肪����΁A�񂹂Ȃǒ���
		if (columnSetting != null) {
			column.setAutoWordwrap(columnSetting.isAutoWordwrap());
			column.setTextAlignCenter(columnSetting.isTextAlignCenter());
			if (columnSetting.getHorizontalAlign() != -1) {
				column.setHorizontalAlign(columnSetting.getHorizontalAlign());
			}
			if (columnSetting.getVerticalAlign() != -1) {
				column.setVerticalAlign(columnSetting.getVerticalAlign());
			}
		}

		// TODO ����R���|�̎w���h��

		columns.add(column);

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		// ���[�U���
		User user = TLoginInfo.getUser();
		String lang = (user == null) ? "ja" : user.getLanguage();
		boolean isEn = (user == null) ? false : user.isEnglish();

		tableModel.addColumn(getColumnTitle(column, lang));

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();

		JTableHeader header = table.getTableHeader();
		TableCellRenderer hr = header.getDefaultRenderer();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {

			columns.get(i).setColumn(columnModel.getColumn(i));

			int width = columns.get(i).getWidth();
			boolean visible = columns.get(i).isVisible();

			if (width < 0 || !visible) {
				columnModel.getColumn(i).setMinWidth(0);
				columnModel.getColumn(i).setMaxWidth(0);
				columnModel.getColumn(i).setPreferredWidth(0);

			} else {
				columnModel.getColumn(i).setMinWidth(0);

				if (isEn && width != 0) {
					// �p�ꃆ�[�U�A�����̓[���łȂ��̏ꍇ�A�񕝎����v�Z
					width = getAdjustColumnWidth(columnModel.getColumn(i), width);
				}
				columnModel.getColumn(i).setPreferredWidth(width);
			}

			TableColumn col = table.getColumnModel().getColumn(i);
			TTableComponent colComponent = columns.get(i).getComponent();

			// �^�C�g��
			if (colComponent != null && colComponent instanceof TTableColumnHeader) {
				// �`���[�g�̏ꍇ�A�w�b�_�[�͓��ʑΉ�
				TTableColumnHeader colHeader = (TTableColumnHeader) colComponent;
				TableCellRenderer renderer = createTableCellRenderer(colHeader);
				col.setHeaderRenderer(renderer);
			} else {
				HeaderRenderer headerRenderer = createHeaderRenderer(hr);
				headerRenderer.setVerticalAlign(columns.get(i).getVerticalAlign());
				col.setHeaderRenderer(headerRenderer);
			}

			// �Z��
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(columns.get(i).getHorizontalAlign());
			columnModel.getColumn(i).setCellRenderer(createCellRenderer(r));
			if (colComponent != null) {
				if (colComponent.getCellRenderer(this) != null) {
					getColumnModel().getColumn(i).setCellRenderer(colComponent.getCellRenderer(this));
				}
				if (colComponent.getCellEditor(this) != null) {
					getColumnModel().getColumn(i).setCellEditor(colComponent.getCellEditor(this));
				}
			}
		}

		// ������Ԃ�ێ�
		this.initTableInformation = getTableInformation();
	}

	/**
	 * �w�b�_�[�\���̍쐬
	 * 
	 * @param colHeader
	 * @return �w�b�_�[�\��
	 */
	protected TableCellRenderer createTableCellRenderer(TTableColumnHeader colHeader) {
		return colHeader.createHeaderRenderer(this, columnColor, columnFontColor);
	}

	/**
	 * ��^�C�g���̎擾
	 * 
	 * @param column
	 * @param lang
	 * @return ��^�C�g��
	 */
	protected String getColumnTitle(TTableColumn column, String lang) {
		String word = MessageUtil.getWord(lang, column.getTitle());

		if (column.isAutoWordwrap()) {
			String align = column.isTextAlignCenter() ? "text-align:center; " : "";
			String htmlPrefix = "<html><div style='word-wrap: break-word; " + align + "'>";
			String htmlSuffix = "</div></html>";
			word = htmlPrefix + word + htmlSuffix;
		}

		return word;
	}

	/**
	 * @param hr
	 * @return �e�[�u���w�b�_�[�̃Z�������_��
	 */
	protected HeaderRenderer createHeaderRenderer(TableCellRenderer hr) {
		return new HeaderRenderer(hr);
	}

	/**
	 * @param r
	 * @return �Z�������_��
	 */
	protected CellRenderer createCellRenderer(TableCellRenderer r) {
		return new CellRenderer(r);
	}

	/**
	 * Enum�ɂ�����TableColumn�I�u�W�F�N�g�� �Ԃ��܂��B
	 * 
	 * @param e �v�������
	 * @return TableColumn
	 */
	public TableColumn getColumn(Enum e) {

		TableColumnModel model = getColumnModel();

		// �����ւ��A���̏ꍇ
		if (getTableHeader().getReorderingAllowed()) {

			for (TTableColumn tcolumn : columns) {
				if (tcolumn.getE() == e) {
					return tcolumn.getColumn();
				}
			}
		}

		return model.getColumn(e.ordinal());
	}

	/**
	 * �e�[�u�����ۑ��̂��߃}�E�X�C�x���g�ǉ�
	 */
	protected void addMouseListener() {

		// ScrollPane���X�i�[�o�^
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				onMouseRightClicked(e);
			}
		});

		// �e�[�u���s�ԍ��N���b�N���X�i�[�o�^
		rowHeaderView.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				onRowHeaderViewMouseClicked();
			}
		});

		// �e�[�u���w�b�_���X�i�[�o�^
		table.getTableHeader().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				onTableHeaderMouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (table.isEditing()) {
					table.getCellEditor().stopCellEditing();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					// �e�[�u�����ۑ�
					saveTableInformation();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	/**
	 * Mouse Right Clicked
	 * 
	 * @param e
	 */
	protected void onMouseRightClicked(MouseEvent e) {
		// ����]���̉E�N���b�N
		if (SwingUtilities.isRightMouseButton(e)) {
			if (!Util.isNullOrEmpty(getTableKeyName()) && e.getX() <= rowNumberWidth && e.getY() <= rowHeight) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * RowHeaderView MouseClicked
	 */
	protected void onRowHeaderViewMouseClicked() {
		// �s�ԍ��N���b�N�㏈�����s��
		adapter.afterRowHeaderClicked();
	}

	/**
	 * TableHeader MouseClicked
	 * 
	 * @param e
	 */
	protected void onTableHeaderMouseClicked(MouseEvent e) {
		// �E�{�^�����N���b�N���ꂽ�Ƃ��A�������_�C�A���O���J��
		if (SwingUtilities.isRightMouseButton(e)) {
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();

		// �`�F�b�N�{�b�N�X�̃w�b�_�[���N���b�N�����ꍇ�͑S�I��
		int col = table.getTableHeader().columnAtPoint(e.getPoint());

		// �w�b�_�[���N���b�N�O
		adapter.beforeHeaderClicked(col);

		if (col >= 0 && columnModel.getColumn(col).getCellEditor() instanceof TCheckBoxEditor && getRowCount() > 0
			&& allCheckWhenHeaderClicked) {

			// �ҏW���̏ꍇ�͈�U�m��
			if (table.isEditing()) {
				table.getCellEditor().stopCellEditing();
			}

			// 1�s�ڂ̃`�F�b�N��Ԃ𔽓]�B���̍s�����̏�Ԃɏ]���A�ݒ肷��
			boolean isSelected = getNextSwitchBoolean(col);

			for (int i = 0; i < getRowCount(); i++) {
				boolean isEditable = table.isCellEditable(i, col);

				TCheckBoxEditor editor = null;
				if (columnModel.getColumn(col).getCellEditor() instanceof TCheckBoxEditor) {
					editor = (TCheckBoxEditor) columnModel.getColumn(col).getCellEditor();
					isEditable &= editor.isCellEditable(i, col);
				}

				if (isEditable) {
					table.setValueAt(isSelected, table.convertRowIndexToModel(i), col);

					if (editor != null) {
						TCheckBox chk = (TCheckBox) editor.getComponent();
						chk.setSelected(isSelected);
						chk.setIndex(i);
						if (chk.getActionListeners() != null || chk.getActionListeners().length != 0) {
							for (int lc = 0; lc < chk.getActionListeners().length; lc++) {
								chk.getActionListeners()[lc].actionPerformed(null);
							}
						}
					}
				}
			}

			table.repaint();
		}

		// �w�b�_�[���N���b�N��
		adapter.afterHeaderClicked(col);
	}

	/**
	 * �w�b�_�N���b�N�ꊇ�ϊ��̐ؑւ��Boolean
	 * 
	 * @param col �ΏۃJ����
	 * @return �ؑւ��Boolean
	 */
	protected boolean getNextSwitchBoolean(int col) {
		return getNextSwitchBoolean(table, col);
	}

	/**
	 * �w�b�_�N���b�N�ꊇ�ϊ��̐ؑւ��Boolean
	 * 
	 * @param tbl BaseTable
	 * @param col �ΏۃJ����
	 * @return �ؑւ��Boolean
	 */
	public static boolean getNextSwitchBoolean(BaseTable tbl, int col) {
		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tbl.getColumnModel();
		TCheckBoxEditor editor = (TCheckBoxEditor) columnModel.getColumn(col).getCellEditor();

		for (int i = 0; i < tbl.getRowCount(); i++) {
			if (columnModel.getColumn(col).getCellEditor() instanceof TCheckBoxEditor) {
				if (editor.isCellEditable(i, col) && tbl.isCellEditable(i, col)) {
					return !(Boolean) tbl.getValueAt(i, col);
				}
			}
		}

		return false;
	}

	/**
	 * �L�[���X�i�[
	 */
	protected void addKeyListener() {

		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent ev) {
				onKeyPressed(ev);
			}

		});
	}

	/**
	 * key Pressed
	 * 
	 * @param ev
	 */
	protected void onKeyPressed(KeyEvent ev) {
		TGuiUtil.dispatchPanelBusinessFunctionKey(table, ev);

		switch (ev.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				// Enter�L�[�Ŋm��
				if (TTable.this.doubleClickLinkButton != null && isEnterToButton) {
					ev.consume();
					TTable.this.doubleClickLinkButton.doClick();
				}

				break;

			case KeyEvent.VK_SPACE:
				// SPACE�L�[�Ń`�F�b�N�{�b�N�XON/OFF�ؑ�

				if (checkBoxColumns != null) {

					// �`�F�b�N�{�b�N�X�񂪎w�肳�ꂽ�ꍇ�A�J��Ԃ�
					for (int col : checkBoxColumns) {

						Boolean isFirstRowSelected = null;

						for (int row : getSelectedRows()) {
							if (!(getRowValueAt(row, col) instanceof Boolean)) {
								// �񂪃`�F�b�N�{�b�N�X�ł͂Ȃ��ꍇ�A�S�I���s�����s�v
								break;
							}

							// 1�s�ڂ̃X�e�[�^�X�̔��]��S�I���s�֔��f����
							if (isFirstRowSelected == null) {
								isFirstRowSelected = (Boolean) getRowValueAt(row, col);
							}

							// ���f
							setRowValueAt(!isFirstRowSelected, row, col);
						}
					}
				}

				break;

			case KeyEvent.VK_C:
			case KeyEvent.VK_INSERT:

				// CTRL+C�ACTRL+INSERT�R�s�[���[�h
				if (useTablePaste && ev.isControlDown()) {
					int rowIndex = table.getSelectedRow();
					int columnIndex = table.getSelectedColumn();

					if (rowIndex != -1 || columnIndex != -1) {

						final StringBuilder sb = new StringBuilder();
						for (int i = 0; i < table.getRowCount(); i++) {
							sb.append(Util.avoidNull(getRowValueAt(i, columnIndex)));
							sb.append("\n");
						}

						EventQueue.invokeLater(new Runnable() {

							@Override
							public void run() {
								StringSelection ss = new StringSelection(sb.toString());
								Toolkit kit = Toolkit.getDefaultToolkit();
								Clipboard clip = kit.getSystemClipboard();
								clip.setContents(ss, ss);
							}
						});
					}
				}
				break;

			// TODO
			// case KeyEvent.VK_SPACE:
			// // SPACE�L�[�Ń`�F�b�N�{�b�N�XON/OFF�ؑ�
			// DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			// DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
			// int selectedColumn = table.getSelectedColumn();
			//
			// for (int col = 0; col < columnModel.getColumnCount(); col++) {
			// if (col == selectedColumn) {
			// continue; // �`�F�b�N�{�b�N�X����
			// }
			//
			// TableColumn column = columnModel.getColumn(col);
			// if (!(column.getCellEditor() instanceof TCheckBoxEditor)) {
			// continue; // CheckBox����Ȃ�
			// }
			//
			// TCheckBoxEditor editor = (TCheckBoxEditor) column.getCellEditor();
			//
			// for (int row : getSelectedRows()) {
			// boolean isEditable = editor.isCellEditable(row, col);
			//
			// if (isEditable) {
			// boolean isSelected = (Boolean) tableModel.getValueAt(row, col);
			// tableModel.setValueAt(!isSelected, table.convertRowIndexToModel(row), col);
			//
			// TCheckBox chk = (TCheckBox) editor.getComponent();
			// chk.setSelected(!isSelected);
			// chk.setIndex(row);
			// for (ActionListener listener : chk.getActionListeners()) {
			// listener.actionPerformed(null);
			// }
			// }
			// }
			// }
			//
			// break;

			default:
				break;
		}
	}

	/**
	 * �e�[�u���𕜌�����
	 */
	public void restoreTable() {
		//
	}

	/**
	 * �s�ǉ� List��ǉ�
	 * 
	 * @param list �f�[�^���X�g
	 */
	public void addRow(List list) {
		addRow(list.toArray(new Object[list.size()]));
	}

	/**
	 * �s�ǉ� Object�^�̔z���ǉ�
	 * 
	 * @param data �f�[�^
	 */
	public void addRow(Object[] data) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.addRow(data);
	}

	/**
	 * �I������Ă���s���C������
	 * 
	 * @param list �f�[�^���X�g
	 */
	public void modifySelectedRow(List list) {
		modifySelectedRow(list.toArray(new Object[list.size()]));
	}

	/**
	 * �I������Ă���s���C������
	 * 
	 * @param data �C���f�[�^
	 */
	public void modifySelectedRow(Object[] data) {

		// �I�𒆂̍s�ԍ���ޔ�
		int row = table.getSelectedRow();
		modifyRow(row, data);

		// �t�H�[�J�X
		table.setRowSelectionInterval(row, row);

		// �w��s�ɃX�N���[��
		scrollToRow(row);
	}

	/**
	 * �w��s���C������
	 * 
	 * @param row �s
	 * @param list �f�[�^���X�g
	 */
	public void modifyRow(int row, List list) {
		modifyRow(row, list.toArray(new Object[list.size()]));
	}

	/**
	 * �w��s���C������
	 * 
	 * @param row �s
	 * @param data �C���f�[�^
	 */
	public void modifyRow(int row, Object[] data) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		int num = table.convertRowIndexToModel(row);
		tableModel.removeRow(num); // �s�폜
		tableModel.insertRow(num, data); // �ǉ�

		// �w��s�ɃX�N���[��
		scrollToRow(num);
	}

	/**
	 * �w��̍s���폜����
	 * 
	 * @param row �s�폜
	 */
	public void removeRow(int row) {
		table.setRowSorter(sorter);
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.removeRow(table.convertRowIndexToModel(row));
	}

	/**
	 * �I������Ă���s���폜����
	 */
	public void removeSelectedRow() {

		// �I�𒆂̍s�ԍ���ޔ�
		int row = table.getSelectedRow();
		removeRow(row);

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		// �s�������Ȃ����ꍇ�͉������Ȃ�
		if (tableModel.getRowCount() > 0) {

			if (tableModel.getRowCount() <= row) {
				table.setRowSelectionInterval(tableModel.getRowCount() - 1, tableModel.getRowCount() - 1);
			} else {
				table.setRowSelectionInterval(row, row);
			}
		}
	}

	/**
	 * �I������Ă���s���폜����(�����s)
	 */
	public void removeSelectedRows() {
		// �I�𒆂̍s�ԍ���ޔ�
		removeRows(table.getSelectedRows());
	}

	/**
	 * �I������Ă���s���폜����(�����s)
	 * 
	 * @param rows �I�𒆂̍s�ԍ�
	 */
	public void removeRows(int[] rows) {

		// �s�폜
		for (int i = rows.length - 1; i >= 0; i--) {
			removeRow(rows[i]);
		}
	}

	/**
	 * �S�Ă̍s���폜����
	 */
	public void removeRow() {

		// �u*�v�N���A
		setShowRowHeaderStar(false);

		table.setRowSorter(sorter);
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
	}

	/**
	 * �e�[�u������Ԃ��B
	 * 
	 * @return �e�[�u�����
	 */
	public TTableInformation getTableInformation() {

		TTableInformation tableInformation = new TTableInformation();

		// �ۑ��L�[
		tableInformation.setTableKeyName(getTableKeyName());

		// ��
		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();

		List<Integer> widthList = new ArrayList<Integer>();
		List<Integer> dispOrderList = new ArrayList<Integer>();
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			// ��\���̏ꍇ�͑O��l
			if (!columns.get(i).isVisible()) {
				widthList.add(columns.get(i).getWidth());
			} else {
				widthList.add(columnModel.getColumn(i).getPreferredWidth());
			}
			dispOrderList.add(columnModel.getColumn(i).getModelIndex());
		}

		tableInformation.setWidthList(widthList);
		tableInformation.setDisplayOrder(dispOrderList);
		return tableInformation;
	}

	/**
	 * �O��ۑ������e�[�u������Ԃ��B
	 * 
	 * @return �O��ۑ������e�[�u�����
	 */
	public TTableInformation getPreviousTableInformation() {

		if (getTableKeyName() == null) {
			return null;
		}

		TTableInformation tableInformation = (TTableInformation) FileUtil
			.getTemporaryObject(getTableKeyName() + ".table");

		return tableInformation;
	}

	/**
	 * �e�[�u������ۑ�����
	 */
	public void saveTableInformation() {
		TTableInformation tableInformation = getTableInformation();
		saveTableInformation(tableInformation);
	}

	/**
	 * �w��̃e�[�u������ۑ�����
	 * 
	 * @param tableInformation �e�[�u�����
	 */
	protected void saveTableInformation(TTableInformation tableInformation) {
		if (tableInformation.tableKeyName != null) {
			FileUtil.saveTemporaryObject(tableInformation, tableInformation.tableKeyName + ".table");
		}

		// ��ύX�������Ăяo��
		fireTableColumnChanged();
	}

	/**
	 * �e�[�u�������N���A
	 */
	protected void clearTableInformation() {
		FileUtil.removeTempolaryFile(getTableKeyName() + ".table");

		// �񏇕���
		restoreColumns(initTableInformation);

		// �񕝕���
		restoreWidth(initTableInformation);

		// �e�[�u�����ۑ�
		saveTableInformation();

		table.repaint();
	}

	/**
	 * �e�[�u������������
	 * 
	 * @param type �����������̃^�C�v
	 */
	protected void adjustTableInformation(AutoSizeType type) {

		// �񏇕���
		restoreColumns(initTableInformation);

		// ����������
		autosizeColumnWidth(false, type);

		// �e�[�u�����ۑ�
		saveTableInformation();
	}

	/**
	 * @return tableKeyName��߂��܂��B
	 */
	public String getTableKeyName() {
		return tableKeyName;
	}

	/**
	 * @param tableKeyName tableKeyName��ݒ肵�܂��B
	 */
	public void setTableKeyName(String tableKeyName) {
		this.tableKeyName = tableKeyName;
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�
	 * @return �w��s�̎w��J�����̒l
	 */
	public BigDecimal getDecimalValueAt(int row, Enum column) {
		return getDecimalValueAt(row, column.ordinal());
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public BigDecimal getDecimalValueAt(int row, int column) {
		Object obj = getValueAt(row, column);
		if (obj == null || obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		return DecimalUtil.toBigDecimal((String) getValueAt(row, column));
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public Object getValueAt(int row, Enum column) {
		return getValueAt(row, column.ordinal());
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public Object getValueAt(int row, int column) {
		if (row < 0) {
			return null;
		}

		return table.getValueAt(row, column);
	}

	/**
	 * �I������Ă���s�̎w��J�����̒l��Ԃ��܂��B
	 * 
	 * @param column �l����肽���񎯕�enum
	 * @return �I������Ă���s�̎w��J�����̒l
	 */
	public Object getSelectedRowValueAt(Enum column) {
		return getRowValueAt(table.getSelectedRow(), column);
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public Object getRowValueAt(int row, Enum column) {
		return getRowValueAt(row, column.ordinal());
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B
	 * 
	 * @param row �s�ԍ�
	 * @param column ��ԍ�
	 * @return �w��s�̎w��J�����̒l
	 */
	public Object getRowValueAt(int row, int column) {
		if (row < 0) {
			return null;
		}

		return getModelValueAt(table.convertRowIndexToModel(row), column);
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����<BR>
	 * ���\�[�g�A�����ւ��Ή�
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�
	 * @return �w��s�̎w��J�����̒l
	 */
	public BigDecimal getRowDecimalValueAt(int row, Enum column) {
		return getRowDecimalValueAt(row, column.ordinal());
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����<BR>
	 * ���\�[�g�A�����ւ��Ή�
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public BigDecimal getRowDecimalValueAt(int row, int column) {
		Object obj = getRowValueAt(row, column);
		if (obj == null || obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		return DecimalUtil.toBigDecimal((String) getRowValueAt(row, column));
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public BigDecimal getModelDecimalValueAt(int row, Enum column) {
		return getModelDecimalValueAt(row, column.ordinal());
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public BigDecimal getModelDecimalValueAt(int row, int column) {
		Object obj = getModelValueAt(row, column);
		if (obj == null || obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}

		if (obj instanceof NumberCellValue) {
			return ((NumberCellValue) obj).getNumber();
		}

		return DecimalUtil.toBigDecimal((String) getModelValueAt(row, column));
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public Object getModelComboBoxValueAt(int row, Enum column) {
		return getModelComboBoxValueAt(row, column.ordinal());
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public Object getModelComboBoxValueAt(int row, int column) {
		Object obj = getModelValueAt(row, column);

		if (obj != null && obj instanceof TTextValue) {
			return ((TTextValue) obj).getValue();
		}

		return obj;
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public Object getModelValueAt(int row, Enum column) {
		return getModelValueAt(row, column.ordinal());
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public Object getModelValueAt(int row, int column) {
		if (row < 0) {
			return null;
		}

		return table.getModel().getValueAt(row, column);
	}

	/**
	 * �X�v���b�h�V�[�g�̃_�u���N���b�N�C�x���g�ƃ{�^���C�x���g�̕R�t��
	 * 
	 * @param btn
	 */
	public void addSpreadSheetSelectChange(TButton btn) {

		this.doubleClickLinkButton = btn;

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent me) {
				onMouseLeftClicked(me);
			}

		});
	}

	/**
	 * Mouse Left Clicked
	 * 
	 * @param me
	 */
	protected void onMouseLeftClicked(MouseEvent me) {
		if (!SwingUtilities.isLeftMouseButton(me)) {
			return;
		}

		// �`�F�b�N�{�b�N�X�̃_�u���N���b�N�͏����ΏۊO
		int col = table.getTableHeader().columnAtPoint(me.getPoint());
		if (columns.get(col).getComponent() instanceof JCheckBox) {
			return;
		}

		// �_�u���N���b�N����A���I���s������ꍇ
		if (me.getClickCount() == 2 && table.getSelectedRow() >= 0) {
			doubleClickLinkButton.doClick();
		}
	}

	/**
	 * �e�[�u���̍s���ړ�����B(�����s�ړ�)<br>
	 * start����end �܂ł�1�s�܂��͕����s���Ato�̈ʒu�Ɉړ�
	 * 
	 * @param start start����end �܂ł�1�s�܂��͕����s�����ւ�
	 * @param end start����end �܂ł�1�s�܂��͕����s�����ւ�
	 * @param to �ړ���̗�ԍ�
	 */
	public void moveRow(int start, int end, int to) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.moveRow(start, end, to);
	}

	/**
	 * �e�[�u���̍s���ړ�����B
	 * 
	 * @param row �ړ��Ώۍs
	 * @param to �ړ���̗�ԍ�
	 */
	public void moveRow(int row, int to) {

		// �Ώۍs�̑I����Ԃ�����
		removeRowSelection(row);

		// �Ώۍs���ړ�
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.moveRow(row, row, to);

		// �ړ���̑I����Ԃ�ݒ�
		setRowSelection(to);
	}

	/**
	 * �w�b�_�[�̍�����ݒ肷��
	 * 
	 * @param height
	 */
	public void setHeaderRowHeight(int height) {
		if (getColumnHeader() == null) {
			setColumnHeader(new JViewport());
		}

		Dimension dim = getColumnHeader().getPreferredSize();
		dim.height = height;
		getColumnHeader().setPreferredSize(dim);
	}

	/**
	 * �ΏۃZ����I������
	 * 
	 * @param index1
	 * @param index2
	 */
	public void setRowSelectionInterval(int index1, int index2) {
		table.setRowSelectionInterval(index1, index2);

		// �w��Z���ɃX�N���[��
		scrollToCell(index1, index2);
	}

	/**
	 * �ΏۃZ����ǉ��I������
	 * 
	 * @param index1
	 * @param index2
	 */
	public void addRowSelectionInterval(int index1, int index2) {
		table.addRowSelectionInterval(index1, index2);

		// �w��Z���ɃX�N���[��
		scrollToCell(index1, index2);
	}

	/**
	 * �ΏۃZ���̑I������������
	 * 
	 * @param index1
	 * @param index2
	 */
	public void removeRowSelectionInterval(int index1, int index2) {
		table.removeRowSelectionInterval(index1, index2);
	}

	/**
	 * ALL�I��
	 */
	public void selectAll() {
		table.selectAll();
	}

	/**
	 * �Ώۍs��I������
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	public void setRowSelection(int rowIndex) {
		table.setRowSelectionInterval(rowIndex, rowIndex);

		// �w��s�ɃX�N���[��
		scrollToRow(rowIndex);
	}

	/**
	 * �Ώۍs��ǉ��I������
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	public void addRowSelection(int rowIndex) {
		table.addRowSelectionInterval(rowIndex, rowIndex);

		// �w��s�ɃX�N���[��
		scrollToRow(rowIndex);
	}

	/**
	 * �Ώۍs�̑I������������
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	public void removeRowSelection(int rowIndex) {
		table.removeRowSelectionInterval(rowIndex, rowIndex);
	}

	/**
	 * �\������Ă���s�����擾����
	 * 
	 * @return �s��
	 */
	public int getRowCount() {
		return table.getRowCount();
	}

	/**
	 * �s����Ԃ�(��\���s���܂�)
	 * 
	 * @return �s��
	 */
	public int getModelRowCount() {
		return table.getModel().getRowCount();
	}

	/**
	 * �s�̍���
	 * 
	 * @param height ����
	 */
	public void setRowHeight(int height) {
		this.currentRowHeight = height;
		table.setRowHeight(height);

		if (rowHeaderView != null) {
			rowHeaderView.setRowHeight(height);
			rowHeaderView.repaint();
		}
	}

	/**
	 * @return ���ݍs����
	 */
	public int getCurrentRowHeight() {
		return this.currentRowHeight;
	}

	/**
	 * �s�̍���
	 * 
	 * @param row
	 * @param height ����
	 */
	public void setRowHeight(int row, int height) {
		table.setRowHeight(row, height);

		if (rowHeaderView != null) {
			rowHeaderView.setRowHeight(row, height);
			rowHeaderView.repaint();
		}
	}

	/**
	 * �s�J�����̕�
	 * 
	 * @param width ��
	 */
	public void setRowColumnWidth(int width) {
		if (rowHeaderView != null) {
			rowHeaderView.getColumnModel().getColumn(0).setPreferredWidth(width);
			rowHeaderView.getColumnModel().getColumn(0).setMinWidth(width);
			rowHeaderView.getColumnModel().getColumn(0).setMaxWidth(width);
			rowHeaderView.getColumnModel().getColumn(0).setWidth(width);
			rowHeaderView.setPreferredScrollableViewportSize(new Dimension(width, 0));
			rowHeaderView.repaint();
		}
	}

	/**
	 * �s�J�����̕�getter
	 * 
	 * @return �s�J�����̕�
	 */
	public int getRowColumnWidth() {
		if (rowHeaderView != null) {
			return rowHeaderView.getColumnModel().getColumn(0).getPreferredWidth();
		}
		return 0;
	}

	/**
	 * �w�肳���Enum�̗񕝂̐ݒ�
	 * 
	 * @param e �w�肳���Enum
	 * @param width ��
	 */
	public void setColumnWidth(Enum e, int width) {
		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) this.table.getColumnModel();
		if (columnModel == null) {
			return;
		}
		int col = this.table.convertColumnIndexToView(e.ordinal());
		if (columnModel.getColumn(col) != null) {
			columnModel.getColumn(col).setMinWidth(width);
			columnModel.getColumn(col).setMaxWidth(width);
			columnModel.getColumn(col).setPreferredWidth(width);
		}
	}

	/**
	 * �w�肳���Enum�̗񕝂̐ݒ�
	 * 
	 * @param colIndex
	 * @param width ��
	 */
	public void setColumnWidth(int colIndex, int width) {
		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) this.table.getColumnModel();
		if (columnModel == null) {
			return;
		}
		int col = this.table.convertColumnIndexToView(colIndex);
		if (columnModel.getColumn(col) != null) {
			columnModel.getColumn(col).setMinWidth(width);
			columnModel.getColumn(col).setMaxWidth(width);
			columnModel.getColumn(col).setPreferredWidth(width);
		}
	}

	/**
	 * �w�肳���Enum�̗񕝂̎擾
	 * 
	 * @param e �w�肳���Enum
	 * @return PreferredWidth ��
	 */
	public int getColumnWidth(Enum e) {
		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) this.table.getColumnModel();
		if (columnModel == null) {
			return 0;
		}
		int col = this.table.convertColumnIndexToView(e.ordinal());
		if (columnModel.getColumn(col) != null) {
			return columnModel.getColumn(col).getPreferredWidth();
		}
		return 0;
	}

	/**
	 * @param model
	 */
	public void setSelectionMode(int model) {
		table.setSelectionMode(model);
	}

	/**
	 * �I���s���擾����
	 * 
	 * @return �I���s
	 */
	public int getSelectedRow() {
		return table.getSelectedRow();
	}

	/**
	 * �I���s���擾����
	 * 
	 * @return �I���s
	 */
	public int[] getSelectedRows() {
		return table.getSelectedRows();
	}

	/**
	 * TableHeader���擾����
	 * 
	 * @return �w�b�_�[
	 */
	public JTableHeader getTableHeader() {
		return table.getTableHeader();
	}

	/**
	 * row��label�ɍ��Ԃ�U�邩�I�� ���f�t�H���g�ݒ�͍��Ԃ���
	 * 
	 * @param isView ���Ԃ���Ftrue /���ԂȂ��Ffalse
	 */
	public void setRowLabelNumber(boolean isView) {
		this.setRowHeaderView(isView ? rowHeaderView : null);
	}

	/**
	 * ColumnModel���擾����
	 * 
	 * @return ColumnModel
	 */
	public TableColumnModel getColumnModel() {
		return table.getColumnModel();
	}

	/**
	 * CellEditor���擾����
	 * 
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor() {
		return table.getCellEditor();
	}

	/**
	 * �t�H�[�J�X��
	 * 
	 * @return �t�H�[�J�X��
	 */
	public int getTabControlNo() {
		return table.getTabControlNo();
	}

	/**
	 * @param no
	 */
	public void setTabControlNo(int no) {
		table.setTabControlNo(no);
	}

	/**
	 * �t�H�[�J�X����
	 * 
	 * @return true:�t�H�[�J�X��
	 */
	public boolean isTabEnabled() {
		return table.isTabEnabled();
	}

	/**
	 * @param bool
	 */
	public void setTabEnabled(boolean bool) {
		table.setTabEnabled(bool);
	}

	@Override
	public void requestFocus() {
		table.requestFocus();
	}

	@Override
	public void setFocusTraversalPolicy(FocusTraversalPolicy policy) {
		table.setFocusTraversalPolicy(policy);
	}

	@Override
	public void transferFocus() {
		table.transferFocus();
	}

	@Override
	public void transferFocusBackward() {
		table.transferFocusBackward();
	}

	@Override
	public boolean requestFocusInWindow() {
		return table.requestFocusInWindow();
	}

	/**
	 * �����ւ��\���ǂ���
	 * 
	 * @param bln true:�\�Afalse:�Ȃ�
	 */
	public void setReorderingAllowed(boolean bln) {
		table.getTableHeader().setReorderingAllowed(bln);
	}

	/**
	 * �s�I���ł��邩�ǂ���
	 * 
	 * @param bln true:����Afalse:�Ȃ�
	 */
	public void setRowSelectionAllowed(boolean bln) {
		table.setRowSelectionAllowed(bln);
		rowHeaderView.setRowSelectionAllowed(bln);
	}

	/**
	 * �w�i�F���擾����
	 * 
	 * @param row
	 * @param isSelected
	 * @param hasFocus
	 * @return �w�i�F
	 */
	public Color getBackgroundColor(int row, boolean isSelected, boolean hasFocus) {

		// �w�i�F�̎擾
		if (row % 2 == 0) {
			if (isSelected) {
				return selectedColor;
			}
			return notSelectedColor2;

		}

		if (isSelected) {
			return selectedColor;
		}

		return notSelectedColor;
	}

	/**
	 * �t�H���g�J���[���擾����
	 * 
	 * @param isSelected
	 * @param hasFocus
	 * @return �t�H���g�J���[
	 */
	public Color getCellFontColor(boolean isSelected, boolean hasFocus) {

		// �t�H���g�J���[�̎擾
		if (isSelected) {
			return selectedCellFontColor;
		}
		return cellFontColor;

	}

	/**
	 * �s��I��1�̔w�i�F���擾����
	 * 
	 * @return �s��I��1�̔w�i�F
	 */
	public Color getNotSelectedColor() {
		return notSelectedColor;
	}

	/**
	 * �s��I��2�̔w�i�F���擾����
	 * 
	 * @return �s��I��2�̔w�i�F
	 */
	public Color getNotSelectedColor2() {
		return notSelectedColor2;
	}

	/**
	 * �s�I�����̔w�i�F���擾����
	 * 
	 * @return �s�I�����̔w�i�F
	 */
	public Color getSelectedColor() {
		return selectedColor;
	}

	/**
	 * �s�I�����̃t�H���g�J���[���擾����
	 * 
	 * @return �s�I�����̔w�i�F
	 */
	public Color getSelectedFontColor() {
		return selectedCellFontColor;
	}

	/**
	 * ListSelectionModel���擾����
	 * 
	 * @return ListSelectionModel
	 */
	public ListSelectionModel getSelectionModel() {
		return table.getSelectionModel();
	}

	/**
	 * ���I����Ԃɂ���
	 */
	public void clearSelection() {
		table.clearSelection();
	}

	/**
	 * ListSelectionListener��ݒ�
	 * 
	 * @param listener ListSelectionListener
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		table.getSelectionModel().addListSelectionListener(listener);
	}

	/**
	 * rowHeight���擾����B
	 * 
	 * @return int rowHeight
	 */
	public int getRowHeight() {
		return rowHeight;
	}

	/**
	 * Enter�L�[�Ń{�^�����s���s�����ǂ���
	 * 
	 * @param isEnterToButton true:�{�^�����s���s��(�A��������)
	 */
	public void setEnterToButton(final boolean isEnterToButton) {
		this.isEnterToButton = isEnterToButton;
	}

	/**
	 * �t�H�[�J�X���󂯓���邩�ǂ���
	 * 
	 * @param isFocusable
	 */
	@Override
	public void setFocusable(boolean isFocusable) {
		table.setFocusable(isFocusable);
		rowHeaderView.setFocusable(isFocusable);
	}

	/**
	 * �Z���I���������邩�ǂ���
	 * 
	 * @param cellSelectionEnabled
	 */
	public void setCellSelectionEnabled(boolean cellSelectionEnabled) {
		table.setCellSelectionEnabled(cellSelectionEnabled);
		rowHeaderView.setCellSelectionEnabled(cellSelectionEnabled);
	}

	/**
	 * �e�[�u����CELL���ҏW�����ǂ���
	 * 
	 * @return isEditing
	 */
	public boolean isEditing() {
		return table.isEditing();
	}

	/**
	 * �e�[�u���̕ҏW���m�肷��
	 */
	public void stopCellEditing() {

		if (table.getCellEditor() == null) {
			return;
		}

		table.getCellEditor().stopCellEditing();
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param strText �f�[�^
	 * @param rowIndex �s
	 * @param col �J����
	 * @deprecated �V�K�ł̎g�p�֎~<br>
	 *             setRowValueAt�𗘗p���邱��
	 */
	public void setValueAt(String strText, int rowIndex, int col) {
		table.setValueAt(strText, rowIndex, col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param obj �f�[�^
	 * @param rowIndex �s
	 * @param column �J����
	 * @deprecated �V�K�ł̎g�p�֎~<br>
	 *             setRowValueAt�𗘗p���邱��
	 */
	public void setValueAt(Object obj, int rowIndex, Enum column) {
		table.setValueAt(obj, rowIndex, column.ordinal());
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param obj �f�[�^
	 * @param rowIndex �s
	 * @param col �J����
	 * @deprecated �V�K�ł̎g�p�֎~<br>
	 *             setRowValueAt�𗘗p���邱��
	 */
	public void setValueAt(Object obj, int rowIndex, int col) {
		table.setValueAt(obj, rowIndex, col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param strText �f�[�^
	 * @param rowIndex �s
	 * @param col �J����
	 */
	public void setModelValueAt(String strText, int rowIndex, int col) {
		table.getModel().setValueAt(strText, rowIndex, col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param obj �f�[�^
	 * @param rowIndex �s
	 * @param column �J����
	 */
	public void setModelValueAt(Object obj, int rowIndex, Enum column) {
		setModelValueAt(obj, rowIndex, column.ordinal());
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param obj �f�[�^
	 * @param rowIndex �s
	 * @param col �J����
	 */
	public void setModelValueAt(Object obj, int rowIndex, int col) {
		table.getModel().setValueAt(obj, rowIndex, col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷�� ���s�\�[�g�A�����ւ��Ή�
	 * 
	 * @param strText �f�[�^
	 * @param row �s
	 * @param col �J����
	 */
	public void setRowValueAt(String strText, int row, int col) {
		table.getModel().setValueAt(strText, table.convertRowIndexToModel(row), col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷�� ���s�\�[�g�A�����ւ��Ή�
	 * 
	 * @param obj �f�[�^
	 * @param row �s
	 * @param column �J����
	 */
	public void setRowValueAt(Object obj, int row, Enum column) {
		setRowValueAt(obj, row, column.ordinal());
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷�� ���s�\�[�g�A�����ւ��Ή�
	 * 
	 * @param obj �f�[�^
	 * @param row �s
	 * @param col �J����
	 */
	public void setRowValueAt(Object obj, int row, int col) {
		table.getModel().setValueAt(obj, table.convertRowIndexToModel(row), col);
	}

	/**
	 * �ҏW�s/�J������ݒ肷��
	 * 
	 * @param row �s
	 * @param col �J����
	 */
	public void editCellAt(int row, int col) {
		table.editCellAt(row, col);
	}

	/**
	 * �w��̈ʒu�ɍs��ǉ�����
	 * 
	 * @param row �s
	 * @param data �f�[�^
	 */
	public void insertRow(int row, Object[] data) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.insertRow(row, data); // �ǉ�

		// �w��s�ɃX�N���[��
		scrollToRow(row);
	}

	/**
	 * �w��̈ʒu�ɍs��ǉ�����
	 * 
	 * @param row �s
	 * @param list �f�[�^���X�g
	 */
	public void insertRow(int row, List list) {
		insertRow(row, list.toArray(new Object[list.size()]));
	}

	/**
	 * CellEditor���擾����
	 * 
	 * @param row �s
	 * @param col �J����
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor(int row, int col) {
		return table.getCellEditor(row, col);
	}

	/**
	 * CellRenderer���擾����
	 * 
	 * @param col �J����
	 * @return CellRenderer
	 */
	public TableCellRenderer getComponentRenderer(int col) {
		if (columns.get(col).getComponent() != null) {
			return columns.get(col).getComponent().getCellRenderer(this);
		}
		return null;
	}

	/**
	 * �e�[�u�����f����Ԃ�
	 * 
	 * @return �e�[�u�����f��
	 */
	public TableModel getModel() {
		return table.getModel();
	}

	/**
	 * �w��J�������`�F�b�N�{�b�N�X�̏ꍇ�A�`�F�b�N����Ă���s����Ԃ�
	 * 
	 * @param e
	 * @return �`�F�b�N�s��
	 */
	public int getCheckedRowCount(Enum e) {

		// �`�F�b�N�s�̎擾
		int checkedCount = 0;
		for (int i = 0; i < table.getRowCount(); i++) {

			// �`�F�b�N�l�̎擾
			Boolean isChecked = (Boolean) getRowValueAt(i, e);

			// �`�F�b�N����Ă���ꍇ�A�J�E���g�𑝉�
			if (isChecked) {
				checkedCount += 1;
			}

		}

		return checkedCount;
	}

	/**
	 * �w��J�������`�F�b�N�{�b�N�X�̏ꍇ�A�`�F�b�N����Ă���ŏ��̍s�̃C���f�b�N�X��Ԃ�
	 * 
	 * @param e
	 * @return �w��J�������`�F�b�N�{�b�N�X�̏ꍇ�A�`�F�b�N����Ă���ŏ��̍s�̃C���f�b�N�X
	 */
	public int getFirstCheckedRowIndex(Enum e) {

		// �`�F�b�N�s�̎擾
		for (int i = 0; i < table.getRowCount(); i++) {

			// �`�F�b�N�l�̎擾
			Boolean isChecked = (Boolean) getRowValueAt(i, e);

			// �`�F�b�N����Ă���ꍇ�A�J�E���g�𑝉�
			if (isChecked) {
				return i;
			}

		}

		return 0;
	}

	/**
	 * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
	 */
	@Override
	public void addFocusListener(FocusListener listener) {
		table.addFocusListener(listener);
	}

	/**
	 * TTableColumn��Ԃ�
	 * 
	 * @return TTableColumn
	 */
	public List<TTableColumn> getTableColumn() {
		return columns;
	}

	/**
	 * �w��C���f�b�N�X��TTableColumn��Ԃ�
	 * 
	 * @param index �C���f�b�N�X
	 * @return TTableColumn
	 */
	public TTableColumn getTableColumnAt(int index) {
		return columns.get(index);
	}

	/**
	 * �w�b�_�[���N���b�N�����ۂɁA�`�F�b�N�{�b�N�X�̃J�����Ȃ��<br>
	 * �S�`�F�b�N�@�\��L���ɂ��邩
	 * 
	 * @return true:�L��
	 */
	public boolean isAllCheckWhenHeaderClicked() {
		return allCheckWhenHeaderClicked;
	}

	/**
	 * �w�b�_�[���N���b�N�����ۂɁA�`�F�b�N�{�b�N�X�̃J�����Ȃ��<br>
	 * �S�`�F�b�N�@�\��L���ɂ��邩
	 * 
	 * @param allCheckWhenHeaderClicked true:�L��
	 */
	public void setAllCheckWhenHeaderClicked(boolean allCheckWhenHeaderClicked) {
		this.allCheckWhenHeaderClicked = allCheckWhenHeaderClicked;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TStorable#saveComponent(jp.co.ais.trans2.common.gui.TStorableKey,
	 *      java.io.Serializable)
	 */
	public void saveComponent(TStorableKey key, Serializable obj) {
		//
	}

	/**
	 * �J������Ԃ̕���
	 */
	public void restoreComponent() {
		restoreComponent(getKey());
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TStorable#restoreComponent(jp.co.ais.trans2.common.gui.TStorableKey)
	 */
	public void restoreComponent(TStorableKey key) {
		try {
			if (Util.isNullOrEmpty(tableKeyName) && key != null) {
				tableKeyName = key.getKey();
			}

			if (Util.isNullOrEmpty(tableKeyName)) {
				return;
			}

			TTableInformation tableInformation = getPreviousTableInformation();

			if (tableInformation == null) {
				return;
			}

			// �J�����ʒu����
			restoreColumns(tableInformation);

			// �񕝕���
			restoreWidth(tableInformation);

			table.repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �J�����ʒu����
	 * 
	 * @param tableInformation �e�[�u�����
	 */
	protected void restoreColumns(TTableInformation tableInformation) {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		List<Integer> dispOrderList = tableInformation.getDisplayOrder();

		if (dispOrderList != null && !dispOrderList.isEmpty() && dispOrderList.size() == columnCount) {
			for (int i = 0; i < columnCount; i++) {
				int displayNo = dispOrderList.get(i);

				// ���ݒn����
				int j = 0;
				now: for (; j < columnCount; j++) {
					if (columnModel.getColumn(j).getModelIndex() == displayNo) {
						break now;
					}
				}

				table.moveColumn(j, i);
			}
		}
	}

	/**
	 * �񕝕���
	 * 
	 * @param tableInformation �e�[�u�����
	 */
	public void restoreWidth(TTableInformation tableInformation) {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		List<Integer> widthList = tableInformation.getWidthList();
		if (widthList != null && !widthList.isEmpty() && widthList.size() == columnCount) {

			for (int i = 0; i < columnCount; i++) {

				int width = widthList.get(i);

				if (width < 0 || !columns.get(i).isVisible()) {
					columnModel.getColumn(i).setMinWidth(0);
					columnModel.getColumn(i).setMaxWidth(0);
					columnModel.getColumn(i).setPreferredWidth(0);

				} else {
					columnModel.getColumn(i).setMinWidth(0);
					columnModel.getColumn(i).setPreferredWidth(width);
				}

				if (!columns.get(i).isVisible()) {
					columns.get(i).setWidth(width);
				}
			}
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TStorable#getKey()
	 */
	public TStorableKey getKey() {
		return null;
	}

	/**
	 * Sorter�擾
	 * 
	 * @return Sorter
	 */
	public TableRowSorter<? extends TableModel> getSorter() {
		return sorter;
	}

	/**
	 * Sorter�ݒ�
	 * 
	 * @param sorter Sorter
	 */
	public void setSorter(TableRowSorter<? extends TableModel> sorter) {
		this.sorter = sorter;
	}

	/**
	 * �\�[�g�\��
	 * 
	 * @return sortable true:�\
	 */
	public boolean isSortable() {
		return sortable;
	}

	/**
	 * �\�[�g�\��
	 * 
	 * @param sortable true:�\
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	/**
	 * ���l��r�g�����ǂ���
	 * 
	 * @return useNumberComparator ���l��r�g�����ǂ���
	 */
	public boolean isUseNumberComparator() {
		return useNumberComparator;
	}

	/**
	 * ���l��r�g�����ǂ����̐ݒ�
	 * 
	 * @param flag ���l��r�g�����ǂ��� true:�g��<b>�E�񂹂̗�͐��l�Ǝb��</b>
	 */
	public void setUseNumberComparator(boolean flag) {
		this.useNumberComparator = flag;
	}

	/**
	 * �A�_�v�^�[�擾
	 * 
	 * @return adapter �A�_�v�^�[
	 */
	public TTableAdapter getAdapter() {
		return adapter;
	}

	/**
	 * �A�_�v�^�[�ݒ�
	 * 
	 * @param adapter �A�_�v�^�[
	 */
	public void setAdapter(TTableAdapter adapter) {
		this.adapter = adapter;
		this.adapter.setTable(table);
	}

	/**
	 * �w��J�����̏����_�ȉ�������ݒ� �������ւ��������ꍇ�s�����B�����g�p�֎~�Ƃ���B
	 * 
	 * @param column �J����
	 * @param digits �����_�ȉ�����
	 */
	public void setDecimalPointAt(Enum column, int digits) {

		// ���l�^�̃t�B�[���h�Ȃ猅���Z�b�g
		if (columns.get(column.ordinal()).getComponent() != null
			&& columns.get(column.ordinal()).getComponent() instanceof TNumericField) {

			TNumericField component = (TNumericField) columns.get(column.ordinal()).getComponent();
			component.setDecimalPoint(digits);

			// �����_���[�Đݒ�
			getColumnModel().getColumn(column.ordinal())
				.setCellRenderer(columns.get(column.ordinal()).getComponent().getCellRenderer(this));
		}
	}

	/**
	 * �w��J�����̏����_�ȉ�������ݒ�
	 * 
	 * @param column �J����
	 * @param digits �����_�ȉ�����
	 */
	public void setDecimalPoint(Enum column, int digits) {

		// ���l�^�̃t�B�[���h�Ȃ猅���Z�b�g
		if (columns.get(column.ordinal()).getComponent() != null
			&& columns.get(column.ordinal()).getComponent() instanceof TNumericField) {

			TNumericField component = (TNumericField) columns.get(column.ordinal()).getComponent();
			component.setDecimalPoint(digits);

			// �����_���[�Đݒ�
			int index = table.convertColumnIndexToView(column.ordinal());
			getColumnModel().getColumn(index)
				.setCellRenderer(columns.get(column.ordinal()).getComponent().getCellRenderer(this));
		}
	}

	/**
	 * �s�I������Ă��邩�ǂ���
	 * 
	 * @return true:�I��
	 */
	public boolean isSelectedRow() {
		return getSelectedRow() >= 0;
	}

	/**
	 * �X�N���[���o�[���ŏ�s�Ɉړ�����B
	 */
	public void setScrollTop() {
		JScrollBar bar = this.getVerticalScrollBar();
		bar.setValue(0);
	}

	/**
	 * �X�N���[���o�[���ŉ��s�Ɉړ�����B
	 */
	public void setScrollUnder() {
		JScrollBar bar = this.getVerticalScrollBar();
		bar.setValue(bar.getMaximum() - bar.getVisibleAmount());
	}

	/**
	 * �X�N���[���o�[�����Ɉړ�����B
	 */
	public void setScrollLeft() {
		JScrollBar bar = this.getHorizontalScrollBar();
		bar.setValue(0);
	}

	/**
	 * �X�N���[���o�[�������ʒu�Ɉړ�����B
	 */
	public void setScrollDefault() {
		setScrollTop();
		setScrollLeft();
	}

	/**
	 * �`�F�b�N�{�b�N�X�̕\���ύX��ǉ�.
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);

		// �`�F�b�N�{�b�N�X�̑����/�s��(�\��)���ύX
		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();

		for (int col = 0; col < columnModel.getColumnCount(); col++) {
			TableCellRenderer renderer = columnModel.getColumn(col).getCellRenderer();
			if (renderer instanceof TCheckBoxRenderer) {
				((TCheckBoxRenderer) renderer).getRenderer().setEnabled(enable);
			}
		}

		table.repaint();
	}

	/**
	 * �R���|�[�l���g�u�����N�\���p�̃A�_�v�^�[(���`�F�b�N�{�b�N�X����)
	 * 
	 * @param e �Ώۗ�
	 * @param adapter �A�_�v�^�[
	 */
	public void setComponentViewAdapter(Enum e, TComponentViewAdapter adapter) {
		TableColumn col = getColumnModel().getColumn(e.ordinal());
		((TCheckBoxRenderer) col.getCellRenderer()).setViewAdapter(adapter);
		((TCheckBoxEditor) col.getCellEditor()).setViewAdapter(adapter);
	}

	/**
	 * �e�L�X�g �E�N���b�N���j���[�̒P��
	 * 
	 * @param wordList �E�N���b�N���j���[�P�ꃊ�X�g
	 */
	public static void setLightPopupMenuWords(String[] wordList) {
		popupWords = wordList;
	}

	/**
	 * �S�Ă̗���B��
	 */
	public void hideAllColumn() {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		for (int i = 0; i < columnCount; i++) {
			columnModel.getColumn(i).setMinWidth(0);
			columnModel.getColumn(i).setMaxWidth(0);
			columnModel.getColumn(i).setPreferredWidth(0);
		}
	}

	/**
	 * �S�Ă̗��\��
	 */
	public void showAllColumn() {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		for (int i = 0; i < columnCount; i++) {
			columnModel.getColumn(i).setMaxWidth(Integer.MAX_VALUE);
			columnModel.getColumn(i).setPreferredWidth(initTableInformation.getWidthList().get(i));
			columnModel.getColumn(i).setWidth(initTableInformation.getWidthList().get(i));
		}
	}

	/**
	 * ������Ԃ̎擾
	 * 
	 * @return �l
	 */
	public TTableInformation getInitTableInformation() {
		return initTableInformation;
	}

	/**
	 * �񕝎����v�Z�l�̎擾
	 * 
	 * @param column ��
	 * @param colWidth �ݒ��
	 * @return ��
	 */
	public int getAdjustColumnWidth(TableColumn column, int colWidth) {
		return getAdjustColumnWidth(column, colWidth, AutoSizeType.HeaderAdjust, -1);
	}

	/**
	 * �񕝎����v�Z�l�̎擾
	 * 
	 * @param column ��
	 * @param colWidth �ݒ��
	 * @param type �����������̃^�C�v
	 * @param colIndex �w���C���f�b�N�X
	 * @return ��
	 */
	public int getAdjustColumnWidth(TableColumn column, int colWidth, AutoSizeType type, int colIndex) {

		if (fm == null) {
			fm = table.getFontMetrics(table.getFont());
		}

		String title = null;
		if (AutoSizeType.HeaderAdjust.equals(type)) {
			// �p�ꃆ�[�U�̏ꍇ�A�񕝎����v�Z

			String header = Util.avoidNullNT(column.getHeaderValue());
			header = header.replaceAll("\\<html\\>", "");
			header = header.replaceAll("\\</html\\>", "");
			header = header.replaceAll("\\<center\\>", "");
			header = header.replaceAll("\\</center\\>", "");
			header = header.replaceAll("\\<br\\>", "");

			title = header;
			int width = getWidthWithHtml(title);

			// ����Ȃ��ꍇ�v�Z�l��߂�
			return Math.max(colWidth, width);

		} else {
			int width = getMaxWidth(column, colIndex, type);
			return width;

		}

	}

	/**
	 * �w���̍ő啶����̎擾
	 * 
	 * @param column �w���I�u�W�F�N�g
	 * @param col �w���C���f�b�N�X
	 * @param type �����������̃^�C�v
	 * @return �w���̍ő啶����
	 */
	public int getMaxWidth(TableColumn column, int col, AutoSizeType type) {

		int colIndex = table.convertColumnIndexToModel(col);// ����C���f�b�N�X
		int addonWidth = fm.stringWidth("W"); // �f�t�H���g��

		String value = "";
		if (AutoSizeType.HeaderAndContents.equals(type) || AutoSizeType.HeaderOnly.equals(type)) {
			// �w�b�_�[�܂񂾏ꍇ�̂�
			value = Util.avoidNullNT(column.getHeaderValue());
		}

		int valueWidth = getWidthWithHtml(value);

		if (AutoSizeType.HeaderOnly.equals(type)) {
			// �w�b�_�[�݂̂̏ꍇ�A�߂�(�l�͌v�Z���Ȃ�)
			return addonWidth + valueWidth;
		}

		for (int i = 0; i < getRowCount(); i++) {

			// �R���|���ǂ�������i�ݒ�l��String�ł͂Ȃ��ꍇ�A�ύX�Ȃ��j
			if (getCellEditor(i, colIndex) instanceof TBaseCellEditor
				|| getCellEditor(i, colIndex) instanceof TableCellRenderer
				|| !(getRowValueAt(i, colIndex) instanceof String)) {
				return initTableInformation.getWidthList().get(colIndex);
			}

			// �l
			String cellValue = Util.avoidNullNT(getRowValueAt(i, colIndex));
			int cellWidth = getWidthWithHtml(cellValue);

			// ��Ԓ������擾
			if (valueWidth < cellWidth) {
				valueWidth = cellWidth;
			}
		}

		return addonWidth + valueWidth;
	}

	/**
	 * HTML�܂ݕ����̕�����
	 * 
	 * @param cellValue
	 * @return HTML�܂ݕ����̕�
	 */
	protected int getWidthWithHtml(String cellValue) {

		// HTML�̏ꍇ�A�^�O���O���ĕ��v�Z
		if (cellValue.contains("<html>")) {

			// ���s�ˈꎞ
			cellValue = cellValue.replaceAll("<br>", "\n");
			// <[^>]*>
			cellValue = cellValue.replaceAll("<[^>]*>", "");
			// �ꎞ�ˉ��s
			cellValue = cellValue.replaceAll("\n", "<br>");

			// ���s����(��Ԓ������擾)
			String[] arr = StringUtil.split(cellValue, "<br>");

			int cellWidth = 0;

			for (String a : arr) {
				int aWidth = fm.stringWidth(a);
				if (cellWidth < aWidth) {
					cellWidth = aWidth;
				}
			}

			return cellWidth;

		} else {
			return fm.stringWidth(cellValue);
		}
	}

	/**
	 * �񕝎����v�Z<br>
	 * �����[���̏ꍇ�A�����v�Z���Ȃ�
	 */
	public void autosizeColumnWidth() {
		autosizeColumnWidth(AutoSizeType.HeaderAdjust);
	}

	/**
	 * �񕝎����v�Z<br>
	 * �����[���̏ꍇ�A�����v�Z���Ȃ�
	 * 
	 * @param type �����������̃^�C�v
	 */
	public void autosizeColumnWidth(AutoSizeType type) {
		autosizeColumnWidth(false, type);
	}

	/**
	 * �񕝎����v�Z<br>
	 * 
	 * @param isZeroAdjust <br>
	 *            true:�����[���̏ꍇ�A�����v�Z���� false:�����[���̏ꍇ�A�����v�Z���Ȃ�
	 * @param type �����������̃^�C�v
	 */
	public void autosizeColumnWidth(boolean isZeroAdjust, AutoSizeType type) {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		List<Integer> widthList = initTableInformation.getWidthList();
		if (widthList != null && !widthList.isEmpty() && widthList.size() == columnCount) {

			for (int i = 0; i < columnCount; i++) {

				int width = widthList.get(i);

				if (width < 0 || !columns.get(i).isVisible()) {
					columnModel.getColumn(i).setMinWidth(0);
					columnModel.getColumn(i).setMaxWidth(0);
					columnModel.getColumn(i).setPreferredWidth(0);

				} else {
					columnModel.getColumn(i).setMinWidth(0);

					if (isZeroAdjust || width != 0) {
						// �񕝎����v�Z(useTitle=true�̏ꍇ�A��^�C�g���̍ő啝��)
						width = getAdjustColumnWidth(columnModel.getColumn(i), width, type, i);
					}
					columnModel.getColumn(i).setPreferredWidth(width);
				}

				if (!columns.get(i).isVisible()) {
					columns.get(i).setWidth(width);
				}
			}
		}
	}

	/**
	 * TTable�̍ĕ`��
	 */
	@Override
	public JScrollBar createVerticalScrollBar() {

		// TTable�̍ĕ`��
		return new ScrollBar(SwingConstants.VERTICAL) {

			@Override
			public void repaint(long l, int i, int j, int k, int i1) {
				super.repaint(l, i, j, k, i1);
				TTable.this.repaint();
			}

		};
	}

	/**
	 * �s�w�b�_�[�́u*�v�ŕ\�����邩�ǂ���
	 * 
	 * @return true:�I���s�̍s�ԍ��́u*�v�ŕ\��
	 */
	public boolean isShowRowHeaderStar() {
		return table.getRowHeaderStarIndex() != -1;
	}

	/**
	 * �s�w�b�_�[�́u*�v�\���s�ԍ��̎擾
	 * 
	 * @return �s�w�b�_�[�́u*�v�\���s�ԍ�
	 */
	public int getRowHeaderStarIndex() {
		return table.getRowHeaderStarIndex();
	}

	/**
	 * �s�w�b�_�[�́u*�v�ŕ\�����邩�ǂ���
	 * 
	 * @param flag true:�I���s�̍s�ԍ��́u*�v�ŕ\��
	 */
	public void setShowRowHeaderStar(boolean flag) {
		if (flag) {
			setShowRowHeaderStar(table.getSelectedRow());
		} else {
			setShowRowHeaderStar(-1);
		}
	}

	/**
	 * �s�w�b�_�[�́u*�v�ŕ\�����邩�ǂ���
	 * 
	 * @param row �w��s�̍s�ԍ��́u*�v�ŕ\��
	 */
	public void setShowRowHeaderStar(int row) {
		table.setRowHeaderStarIndex(row);
		TTable.this.repaint();
	}

	/**
	 * �s�^�C�g�����X�g�̎擾
	 * 
	 * @return rowHeaderMessage �s�^�C�g�����X�g
	 */
	public List<String> getRowHeaderMessage() {
		return table.getRowHeaderMessage();
	}

	/**
	 * �s�^�C�g�����X�g�̐ݒ�
	 * 
	 * @param rowHeaderMessage �s�^�C�g�����X�g
	 */
	public void setRowHeaderMessage(List<String> rowHeaderMessage) {
		table.setRowHeaderMessage(rowHeaderMessage);
		TTable.this.repaint();
	}

	/**
	 * �w�i�F���X�g�̎擾
	 * 
	 * @return rowHeaderBackground �w�i�F���X�g
	 */
	public List<Color> getRowHeaderBackground() {
		return table.getRowHeaderBackground();
	}

	/**
	 * �w�i�F���X�g�̐ݒ�
	 * 
	 * @param rowHeaderBackground �w�i�F���X�g
	 */
	public void setRowHeaderBackground(List<Color> rowHeaderBackground) {
		table.setRowHeaderBackground(rowHeaderBackground);
		TTable.this.repaint();
	}

	/**
	 * �����F���X�g�̎擾
	 * 
	 * @return rowHeaderMessage �����F���X�g
	 */
	public List<Color> getRowHeaderForeground() {
		return table.getRowHeaderForeground();
	}

	/**
	 * �����F���X�g�̐ݒ�
	 * 
	 * @param rowHeaderForeground �����F���X�g
	 */
	public void setRowHeaderForeground(List<Color> rowHeaderForeground) {
		table.setRowHeaderForeground(rowHeaderForeground);
		TTable.this.repaint();
	}

	/**
	 * �w��s��̃Z���̃t�H�[�J�X�𓖂Ă�
	 * 
	 * @param row �s
	 * @param column ��
	 */
	public void requestFocus(int row, int column) {
		requestFocus();
		table.setRowSelectionInterval(row, row);
		int col = table.convertColumnIndexToView(column);
		table.setColumnSelectionInterval(col, col);

		// �w��Z���ɃX�N���[��
		scrollToCell(row, column);
	}

	/**
	 * �w��s�ɃX�N���[��
	 * 
	 * @param row
	 */
	public void scrollToRow(int row) {
		table.scrollRectToVisible(table.getCellRect(row, 0, true));
	}

	/**
	 * �w��Z���ɃX�N���[��
	 * 
	 * @param row
	 * @param column
	 */
	public void scrollToCell(int row, int column) {
		table.scrollRectToVisible(table.getCellRect(row, column, true));
	}

	/**
	 * �w��s��̃Z���̃t�H�[�J�X�𓖂Ă�
	 * 
	 * @param row �s
	 * @param e ��Enum
	 */
	public void requestFocus(int row, Enum e) {
		requestFocus(row, e.ordinal());
	}

	/**
	 * ��^�C�g���̎擾
	 * 
	 * @param e Enum
	 * @return ��^�C�g��
	 */
	public String getColumnTitle(Enum e) {
		return getTableColumnAt(e.ordinal()).getTitle();
	}

	/**
	 * ��ǉ����̐ݒ�̎擾
	 * 
	 * @return columnSetting ��ǉ����̐ݒ�
	 */
	public ColumnSetting getColumnSetting() {
		return columnSetting;
	}

	/**
	 * ��ǉ����̐ݒ�̐ݒ�
	 * 
	 * @param columnSetting ��ǉ����̐ݒ�
	 */
	public void setColumnSetting(ColumnSetting columnSetting) {
		this.columnSetting = columnSetting;
	}

	/**
	 * SPACE�����Ώۃ`�F�b�N�{�b�N�X��̒ǉ�
	 * 
	 * @param col SPACE�����Ώۃ`�F�b�N�{�b�N�X��
	 */
	public void addCheckBoxColumn(int col) {
		if (checkBoxColumns == null) {
			checkBoxColumns = new ArrayList<Integer>();
		}
		checkBoxColumns.add(col);
	}

	/**
	 * SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g�̎擾
	 * 
	 * @return checkBoxColumns SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g
	 */
	public List<Integer> getCheckBoxColumns() {
		return checkBoxColumns;
	}

	/**
	 * SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g�̐ݒ�
	 * 
	 * @param checkBoxColumns SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g
	 */
	public void setCheckBoxColumns(List<Integer> checkBoxColumns) {
		this.checkBoxColumns = checkBoxColumns;
	}

	/**
	 * SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g�̃N���A
	 */
	public void clearCheckBoxColumns() {
		if (checkBoxColumns == null) {
			this.checkBoxColumns.clear();
		}
	}

	/**
	 * �\��t���@�\�g�����Btrue:�g���̎擾
	 * 
	 * @return useTablePaste �\��t���@�\�g�����Btrue:�g��
	 */
	public boolean isUseTablePaste() {
		return useTablePaste;
	}

	/**
	 * �\��t���@�\�g�����Btrue:�g���̐ݒ�
	 * 
	 * @param useTablePaste �\��t���@�\�g�����Btrue:�g��
	 */
	public void setUseTablePaste(boolean useTablePaste) {
		this.useTablePaste = useTablePaste;
	}

	/**
	 * 
	 */
	public void removeAllHighlights() {
		this.highlightPattern = null;
		this.repaint();
	}

	/**
	 * @param pattern
	 */
	public void setHighlighterPattern(Pattern pattern) {
		this.highlightPattern = pattern;
		this.repaint();
	}

	/**
	 * @return pattern
	 */
	public Pattern getHighlighterPattern() {
		return this.highlightPattern;
	}

	/**
	 * Enter�L�[�ŏ㉺�J�ڃ��[�h�̎擾
	 * 
	 * @return useEnterKeyUpDownAction Enter�L�[�ŏ㉺�J�ڃ��[�h
	 */
	public boolean isUseEnterKeyUpDownAction() {
		return useEnterKeyUpDownAction;
	}

	/**
	 * Enter�L�[�ŏ㉺�J�ڃ��[�h�̐ݒ�
	 * 
	 * @param useEnterKeyUpDownAction Enter�L�[�ŏ㉺�J�ڃ��[�h
	 */
	public void setUseEnterKeyUpDownAction(boolean useEnterKeyUpDownAction) {
		this.useEnterKeyUpDownAction = useEnterKeyUpDownAction;

		if (this.useEnterKeyUpDownAction) {

			// ActionMap�̐ݒ菈�����g��
			InputMap im = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
			im.put(enter, enterAct);

			KeyStroke senter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK);
			im.put(senter, shiftEnterAct);

		} else {

			// ENTER�L�[���E�ړ�(TAB���Q��)
			InputMap im = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
			KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
			im.put(enter, im.get(tab));

			KeyStroke stab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK);
			KeyStroke senter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK);
			im.put(senter, im.get(stab));
		}

	}

	/**
	 * �e�[�u����ύX
	 */
	protected void fireTableColumnChanged() {
		// ��ύX����
		adapter.fireTableColumnChanged();
	}

}

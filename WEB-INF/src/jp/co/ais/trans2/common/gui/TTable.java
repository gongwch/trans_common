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
 * テーブルコンポーネント
 * 
 * @author AIS
 */
@SuppressWarnings("unused")
public class TTable extends JScrollPane implements TStorable {

	/** 幅自動調整のタイプ */
	public enum AutoSizeType {
		/** ヘッダーと値の最大幅 */
		HeaderAndContents,
		/** ヘッダーの最大幅 */
		HeaderOnly,
		/** 値の最大幅 */
		ContentsOnly,
		/** ヘッダーの最大幅と設定値の比較結果の最大幅 */
		HeaderAdjust;
	}

	/** ヘッダーのカラー */
	public static Color columnColor = new Color(90, 90, 90);

	/** ヘッダーのフォントカラー */
	public static Color columnFontColor = Color.WHITE;

	/** 未選択時のカラー */
	public static Color notSelectedColor = new Color(255, 255, 255);

	/** 未選択時のカラー(別色) */
	public static Color notSelectedColor2 = new Color(230, 240, 250);

	/** 選択時のカラー */
	public static Color selectedColor = new Color(0, 102, 204);

	/** 選択時のフォントカラー */
	public static Color selectedCellFontColor = Color.WHITE;

	/** 未選択時のフォントカラー */
	public static Color cellFontColor = Color.BLACK;

	/** Enterキー処理 */
	public static final String ACTION_KEY_ENTER = "ACTION_KEY_ENTER";

	/** Shift+Enterキー処理 */
	public static final String ACTION_KEY_SHIFT_ENTER = "ACTION_KEY_SHIFT_ENTER";

	/** 行の高さ */
	protected static int rowHeight = 25;

	/** 行番号の幅 */
	protected static int rowNumberWidth = 30;

	/** タブ順 順次移動 */
	protected static Set<AWTKeyStroke> forwardSet = new HashSet<AWTKeyStroke>();

	/** タブ順 逆順移動 */
	protected static Set<AWTKeyStroke> backwardSet = new HashSet<AWTKeyStroke>();

	/** タブ順 順次移動(デフォルト) */
	protected static Set<AWTKeyStroke> defaltForwardSet = new HashSet<AWTKeyStroke>();

	/** タブ順 逆順移動(デフォルト) */
	protected static Set<AWTKeyStroke> defaultBackwardSet = new HashSet<AWTKeyStroke>();

	/** 右クリックメニューの単語 */
	protected static String[] popupWords = { "Initialization", "Column width to header and contents",
			"Column width to header", "Column width to contents" };

	/** 数値比較 */
	protected static TNumberComparator numberComparator = new TNumberComparator();

	/** フォント定義 */
	protected FontMetrics fm = null;

	/**
	 * ヘッダーをクリックした際に、チェックボックスのカラムならば<br>
	 * 全チェック機能を有効にするか
	 */
	protected boolean allCheckWhenHeaderClicked = true;

	/** JTableへのつなぎ */
	protected TTableAdapter adapter = null;

	static {
		// 初期値取得
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

		// TABキーフォーカス移動
		forwardSet.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, 0));
		backwardSet.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK));

		defaltForwardSet.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK));
		defaultBackwardSet
			.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
	}

	/** テーブル本体 */
	public BaseTable table;

	/** カラム */
	protected List<TTableColumn> columns = null;

	/** テーブル情報を保存する場合、その復元キー */
	protected String tableKeyName = null;

	/** フィルタリングのポップアップ */
	protected JPopupMenu popupMenu = null;

	/** フィルタリングのポップアップアイテム */
	protected TTableFilterPopupMenuItem popupMenuItem = null;

	/** ダブルクリックリンクボタン */
	protected TButton doubleClickLinkButton;

	/** 行番号用テーブル */
	protected JComponent rowHeaderComp;

	/** 行番号用テーブル */
	protected JTable rowHeaderView;

	/** true:Enterキーでボタン実行 */
	protected boolean isEnterToButton = false;

	/** テーブルソート */
	protected TableRowSorter<? extends TableModel> sorter = null;

	/** ソート可能か(全列) */
	protected boolean sortable = true;

	/** true:TABは外に移動しない、false:全画面移動 */
	protected boolean tabTraverseCell = false;

	/** 数値比較使うかどうか */
	protected boolean useNumberComparator = true;

	/** 初期状態 */
	protected TTableInformation initTableInformation;

	/** 列追加時の設定 */
	protected ColumnSetting columnSetting = null;

	/** SPACE押下対象チェックボックス列リスト */
	protected List<Integer> checkBoxColumns = null;

	/** 貼り付け機能使うか。true:使う */
	protected boolean useTablePaste = false;

	/** 行番号開始番号 */
	protected int rowNumPlus = 1;

	/**  */
	protected Pattern highlightPattern = null;

	/** Enter動作 */
	protected Object enterAct = null;

	/** Shift+Enter動作 */
	protected Object shiftEnterAct = null;

	/** Enterキーで上下遷移モード */
	protected boolean useEnterKeyUpDownAction = false;

	/** 現在行高さ */
	protected int currentRowHeight = rowHeight;

	/**
	 * コンストラクタ
	 */
	public TTable() {
		init();
	}

	/**
	 * 初期処理
	 */
	public void init() {

		// ScrollPane透過
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

		// 行番号
		rowHeaderComp = createRowHeaderTable();
		rowHeaderComp.setFocusable(false);
		rowHeaderComp.setFocusCycleRoot(false);
		rowHeaderView = getRowHeaderView();

		this.setRowHeaderView(rowHeaderComp);
		getRowHeader().setOpaque(false); // 透過

		addKeyEvent();
		addKeyListener();
		addMouseListener();

		sorter = createTableRowSorter();

		table.setRowSorter(sorter);

		// 右クリックメニュー初期化
		initPopupMenu();

	}

	/**
	 * @param tbl
	 */
	protected void setViewportTable(BaseTable tbl) {
		setViewportView(tbl);
	}

	/**
	 * @return JTableとのAdapter
	 */
	protected TTableAdapter createTTableAdapter() {
		return new TTableAdapter();
	}

	/**
	 * @return 行ヘッダー用Table
	 */
	protected JComponent createRowHeaderTable() {
		return new RowHeaderTable(this, rowNumberWidth, rowHeight);
	}

	/**
	 * @return 行ヘッダー用Table
	 */
	public JTable getRowHeaderView() {
		return (JTable) rowHeaderComp;
	}

	/**
	 * @return ソーター
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
	 * 右クリックメニュー初期化
	 */
	protected void initPopupMenu() {

		// 右クリックメニュー初期化
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

				// 幅自動調整
				adjustTableInformation(AutoSizeType.HeaderAndContents);

			}
		});

		popupMenu.add(autosizeHeaderAndContents);

		JMenuItem autosizeHeader = new JMenuItem(popupWords[2]);
		autosizeHeader.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// 幅自動調整
				adjustTableInformation(AutoSizeType.HeaderOnly);

			}
		});

		popupMenu.add(autosizeHeader);

		JMenuItem autosizeContents = new JMenuItem(popupWords[3]);
		autosizeContents.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// 幅自動調整
				adjustTableInformation(AutoSizeType.ContentsOnly);

			}
		});

		popupMenu.add(autosizeContents);
	}

	/**
	 * キー系イベント登録
	 */
	protected void addKeyEvent() {

		// ENTERキー左右移動
		InputMap im = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

		// EnterキーデフォルトActionMapを持つ
		enterAct = im.get(enter);

		im.put(enter, im.get(tab));

		KeyStroke stab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK);
		KeyStroke senter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK);

		// Shift+EnterキーデフォルトActionMapを持つ
		shiftEnterAct = im.get(senter);

		im.put(senter, im.get(stab));

		// TABキーフォーカス移動
		table.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardSet);
		table.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardSet);

		// デフォルトF8キー無効化
		ActionMap actionMap = table.getActionMap();
		actionMap.remove("focusHeader");

		while ((actionMap = actionMap.getParent()) != null) {
			actionMap.remove("focusHeader");
		}

		// ----------------------------------------------------------------
		// ショットカットキー処理F1～F12(直接、Shift＋、Ctrl＋)

		int[] vkeys = { 0, InputEvent.CTRL_DOWN_MASK, InputEvent.SHIFT_DOWN_MASK };

		for (int i : vkeys) {
			for (int j = KeyEvent.VK_F1; j <= KeyEvent.VK_F12; j++) {
				im.put(KeyStroke.getKeyStroke(j, i), "dummy_key");
			}
		}

		table.getActionMap().put("dummy_key", new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				// 処理なし
			}
		});
		// ----------------------------------------------------------------
	}

	/**
	 * ベーステーブル
	 * 
	 * @return ベーステーブル
	 */
	protected BaseTable createBaseTable() {
		return new BaseTable();
	}

	/**
	 * ソート機能を外す
	 */
	public void setSortOff() {
		sortable = false;
	}

	/**
	 * true:TABは外に移動しない、false:全画面移動の取得
	 * 
	 * @return tabTraverseCell true:TABは外に移動しない、false:全画面移動
	 */
	public boolean isTabTraverseCell() {
		return tabTraverseCell;
	}

	/**
	 * true:TABは外に移動しない、false:全画面移動の設定
	 * 
	 * @param tabTraverseCell true:TABは外に移動しない、false:全画面移動
	 */
	public void setTabTraverseCell(boolean tabTraverseCell) {
		this.tabTraverseCell = tabTraverseCell;

		if (this.tabTraverseCell) {
			// TABは外に移動しない
			table.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, defaltForwardSet);
			table.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, defaultBackwardSet);
		} else {
			// TABキーフォーカス移動
			table.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardSet);
			table.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardSet);
		}
	}

	/**
	 * TABキーで次のセルに移動するように設定する.
	 */
	public void setTabTraverseCell() {
		setTabTraverseCell(true);
	}

	/**
	 * 列のタイトル、幅を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 */
	public void addColumn(Enum e, String title, int width) {
		addColumn(e, title, width, SwingConstants.LEFT);
	}

	/**
	 * 列のタイトル、幅、表示/非表示を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param visible 表示/非表示
	 */
	public void addColumn(Enum e, String title, int width, boolean visible) {
		addColumn(e, title, width, SwingConstants.LEFT, visible);
	}

	/**
	 * 列のタイトル、幅を指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 */
	public void addColumn(String title, int width) {
		addColumn(null, title, width, SwingConstants.LEFT);
	}

	/**
	 * 列のタイトル、幅を指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 */
	public void addColumn(String title, int width, int horizontalAlign) {
		addColumn(null, title, width, horizontalAlign);
	}

	/**
	 * 列のタイトル、幅、表示/非表示を指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param visible 表示/非表示
	 */
	public void addColumn(String title, int width, boolean visible) {
		addColumn(null, title, width, SwingConstants.LEFT, visible);
	}

	/**
	 * 列のタイトル、幅、水平方向表示位置を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 */
	public void addColumn(Enum e, String title, int width, int horizontalAlign) {
		addColumn(new TTableColumn(e, title, width, horizontalAlign));
	}

	/**
	 * 列のタイトル、幅、水平方向表示位置、表示/非表示を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 * @param visible 表示/非表示
	 */
	public void addColumn(Enum e, String title, int width, int horizontalAlign, boolean visible) {
		addColumn(new TTableColumn(e, title, width, horizontalAlign, visible));
	}

	/**
	 * 列のタイトル、幅、セルにセットするコンポーネントを指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
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
	 * 列のタイトル、幅、セルにセットするコンポーネントを指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
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
	 * 列のタイトル、幅、セルにセットするコンポーネント、表示/非表示を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
	 * @param visible 表示/非表示
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
	 * 列のタイトル、幅、セルにセットするコンポーネントを指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
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
	 * 列のタイトル、幅、セルにセットするコンポーネント、表示/非表示を指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
	 * @param visible 表示/非表示
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
	 * 列を追加する
	 * 
	 * @param column 列
	 */
	public void addColumn(TTableColumn column) {

		if (columns == null) {
			columns = new ArrayList<TTableColumn>();
		}

		// 列追加の設定があれば、寄せなど直す
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

		// TODO 同一コンポの指定を防ぐ

		columns.add(column);

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		// ユーザ情報
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
					// 英語ユーザ、且つ幅はゼロでないの場合、列幅自動計算
					width = getAdjustColumnWidth(columnModel.getColumn(i), width);
				}
				columnModel.getColumn(i).setPreferredWidth(width);
			}

			TableColumn col = table.getColumnModel().getColumn(i);
			TTableComponent colComponent = columns.get(i).getComponent();

			// タイトル
			if (colComponent != null && colComponent instanceof TTableColumnHeader) {
				// チャートの場合、ヘッダーは特別対応
				TTableColumnHeader colHeader = (TTableColumnHeader) colComponent;
				TableCellRenderer renderer = createTableCellRenderer(colHeader);
				col.setHeaderRenderer(renderer);
			} else {
				HeaderRenderer headerRenderer = createHeaderRenderer(hr);
				headerRenderer.setVerticalAlign(columns.get(i).getVerticalAlign());
				col.setHeaderRenderer(headerRenderer);
			}

			// セル
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

		// 初期状態を保持
		this.initTableInformation = getTableInformation();
	}

	/**
	 * ヘッダー表現の作成
	 * 
	 * @param colHeader
	 * @return ヘッダー表現
	 */
	protected TableCellRenderer createTableCellRenderer(TTableColumnHeader colHeader) {
		return colHeader.createHeaderRenderer(this, columnColor, columnFontColor);
	}

	/**
	 * 列タイトルの取得
	 * 
	 * @param column
	 * @param lang
	 * @return 列タイトル
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
	 * @return テーブルヘッダーのセルレンダラ
	 */
	protected HeaderRenderer createHeaderRenderer(TableCellRenderer hr) {
		return new HeaderRenderer(hr);
	}

	/**
	 * @param r
	 * @return セルレンダラ
	 */
	protected CellRenderer createCellRenderer(TableCellRenderer r) {
		return new CellRenderer(r);
	}

	/**
	 * Enumにある列のTableColumnオブジェクトを 返します。
	 * 
	 * @param e 要求する列
	 * @return TableColumn
	 */
	public TableColumn getColumn(Enum e) {

		TableColumnModel model = getColumnModel();

		// 列入れ替えアリの場合
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
	 * テーブル情報保存のためマウスイベント追加
	 */
	protected void addMouseListener() {

		// ScrollPaneリスナー登録
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				onMouseRightClicked(e);
			}
		});

		// テーブル行番号クリックリスナー登録
		rowHeaderView.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				onRowHeaderViewMouseClicked();
			}
		});

		// テーブルヘッダリスナー登録
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
					// テーブル情報保存
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
		// 左上余白の右クリック
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
		// 行番号クリック後処理を行う
		adapter.afterRowHeaderClicked();
	}

	/**
	 * TableHeader MouseClicked
	 * 
	 * @param e
	 */
	protected void onTableHeaderMouseClicked(MouseEvent e) {
		// 右ボタンがクリックされたとき、検索候補ダイアログを開く
		if (SwingUtilities.isRightMouseButton(e)) {
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();

		// チェックボックスのヘッダーをクリックした場合は全選択
		int col = table.getTableHeader().columnAtPoint(e.getPoint());

		// ヘッダーをクリック前
		adapter.beforeHeaderClicked(col);

		if (col >= 0 && columnModel.getColumn(col).getCellEditor() instanceof TCheckBoxEditor && getRowCount() > 0
			&& allCheckWhenHeaderClicked) {

			// 編集中の場合は一旦確定
			if (table.isEditing()) {
				table.getCellEditor().stopCellEditing();
			}

			// 1行目のチェック状態を反転。他の行もその状態に従い、設定する
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

		// ヘッダーをクリック後
		adapter.afterHeaderClicked(col);
	}

	/**
	 * ヘッダクリック一括変換の切替り後Boolean
	 * 
	 * @param col 対象カラム
	 * @return 切替り後Boolean
	 */
	protected boolean getNextSwitchBoolean(int col) {
		return getNextSwitchBoolean(table, col);
	}

	/**
	 * ヘッダクリック一括変換の切替り後Boolean
	 * 
	 * @param tbl BaseTable
	 * @param col 対象カラム
	 * @return 切替り後Boolean
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
	 * キーリスナー
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
				// Enterキーで確定
				if (TTable.this.doubleClickLinkButton != null && isEnterToButton) {
					ev.consume();
					TTable.this.doubleClickLinkButton.doClick();
				}

				break;

			case KeyEvent.VK_SPACE:
				// SPACEキーでチェックボックスON/OFF切替

				if (checkBoxColumns != null) {

					// チェックボックス列が指定された場合、繰り返し
					for (int col : checkBoxColumns) {

						Boolean isFirstRowSelected = null;

						for (int row : getSelectedRows()) {
							if (!(getRowValueAt(row, col) instanceof Boolean)) {
								// 列がチェックボックスではない場合、全選択行処理不要
								break;
							}

							// 1行目のステータスの反転を全選択行へ反映する
							if (isFirstRowSelected == null) {
								isFirstRowSelected = (Boolean) getRowValueAt(row, col);
							}

							// 反映
							setRowValueAt(!isFirstRowSelected, row, col);
						}
					}
				}

				break;

			case KeyEvent.VK_C:
			case KeyEvent.VK_INSERT:

				// CTRL+C、CTRL+INSERTコピーモード
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
			// // SPACEキーでチェックボックスON/OFF切替
			// DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			// DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
			// int selectedColumn = table.getSelectedColumn();
			//
			// for (int col = 0; col < columnModel.getColumnCount(); col++) {
			// if (col == selectedColumn) {
			// continue; // チェックボックス自体
			// }
			//
			// TableColumn column = columnModel.getColumn(col);
			// if (!(column.getCellEditor() instanceof TCheckBoxEditor)) {
			// continue; // CheckBoxじゃない
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
	 * テーブルを復元する
	 */
	public void restoreTable() {
		//
	}

	/**
	 * 行追加 Listを追加
	 * 
	 * @param list データリスト
	 */
	public void addRow(List list) {
		addRow(list.toArray(new Object[list.size()]));
	}

	/**
	 * 行追加 Object型の配列を追加
	 * 
	 * @param data データ
	 */
	public void addRow(Object[] data) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.addRow(data);
	}

	/**
	 * 選択されている行を修正する
	 * 
	 * @param list データリスト
	 */
	public void modifySelectedRow(List list) {
		modifySelectedRow(list.toArray(new Object[list.size()]));
	}

	/**
	 * 選択されている行を修正する
	 * 
	 * @param data 修正データ
	 */
	public void modifySelectedRow(Object[] data) {

		// 選択中の行番号を退避
		int row = table.getSelectedRow();
		modifyRow(row, data);

		// フォーカス
		table.setRowSelectionInterval(row, row);

		// 指定行にスクロール
		scrollToRow(row);
	}

	/**
	 * 指定行を修正する
	 * 
	 * @param row 行
	 * @param list データリスト
	 */
	public void modifyRow(int row, List list) {
		modifyRow(row, list.toArray(new Object[list.size()]));
	}

	/**
	 * 指定行を修正する
	 * 
	 * @param row 行
	 * @param data 修正データ
	 */
	public void modifyRow(int row, Object[] data) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		int num = table.convertRowIndexToModel(row);
		tableModel.removeRow(num); // 行削除
		tableModel.insertRow(num, data); // 追加

		// 指定行にスクロール
		scrollToRow(num);
	}

	/**
	 * 指定の行を削除する
	 * 
	 * @param row 行削除
	 */
	public void removeRow(int row) {
		table.setRowSorter(sorter);
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.removeRow(table.convertRowIndexToModel(row));
	}

	/**
	 * 選択されている行を削除する
	 */
	public void removeSelectedRow() {

		// 選択中の行番号を退避
		int row = table.getSelectedRow();
		removeRow(row);

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		// 行が無くなった場合は何もしない
		if (tableModel.getRowCount() > 0) {

			if (tableModel.getRowCount() <= row) {
				table.setRowSelectionInterval(tableModel.getRowCount() - 1, tableModel.getRowCount() - 1);
			} else {
				table.setRowSelectionInterval(row, row);
			}
		}
	}

	/**
	 * 選択されている行を削除する(複数行)
	 */
	public void removeSelectedRows() {
		// 選択中の行番号を退避
		removeRows(table.getSelectedRows());
	}

	/**
	 * 選択されている行を削除する(複数行)
	 * 
	 * @param rows 選択中の行番号
	 */
	public void removeRows(int[] rows) {

		// 行削除
		for (int i = rows.length - 1; i >= 0; i--) {
			removeRow(rows[i]);
		}
	}

	/**
	 * 全ての行を削除する
	 */
	public void removeRow() {

		// 「*」クリア
		setShowRowHeaderStar(false);

		table.setRowSorter(sorter);
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
	}

	/**
	 * テーブル情報を返す。
	 * 
	 * @return テーブル情報
	 */
	public TTableInformation getTableInformation() {

		TTableInformation tableInformation = new TTableInformation();

		// 保存キー
		tableInformation.setTableKeyName(getTableKeyName());

		// 列幅
		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();

		List<Integer> widthList = new ArrayList<Integer>();
		List<Integer> dispOrderList = new ArrayList<Integer>();
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			// 非表示の場合は前回値
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
	 * 前回保存したテーブル情報を返す。
	 * 
	 * @return 前回保存したテーブル情報
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
	 * テーブル情報を保存する
	 */
	public void saveTableInformation() {
		TTableInformation tableInformation = getTableInformation();
		saveTableInformation(tableInformation);
	}

	/**
	 * 指定のテーブル情報を保存する
	 * 
	 * @param tableInformation テーブル情報
	 */
	protected void saveTableInformation(TTableInformation tableInformation) {
		if (tableInformation.tableKeyName != null) {
			FileUtil.saveTemporaryObject(tableInformation, tableInformation.tableKeyName + ".table");
		}

		// 列変更処理を呼び出す
		fireTableColumnChanged();
	}

	/**
	 * テーブル情報をクリア
	 */
	protected void clearTableInformation() {
		FileUtil.removeTempolaryFile(getTableKeyName() + ".table");

		// 列順復元
		restoreColumns(initTableInformation);

		// 列幅復元
		restoreWidth(initTableInformation);

		// テーブル情報保存
		saveTableInformation();

		table.repaint();
	}

	/**
	 * テーブル幅自動調整
	 * 
	 * @param type 幅自動調整のタイプ
	 */
	protected void adjustTableInformation(AutoSizeType type) {

		// 列順復元
		restoreColumns(initTableInformation);

		// 幅自動調整
		autosizeColumnWidth(false, type);

		// テーブル情報保存
		saveTableInformation();
	}

	/**
	 * @return tableKeyNameを戻します。
	 */
	public String getTableKeyName() {
		return tableKeyName;
	}

	/**
	 * @param tableKeyName tableKeyNameを設定します。
	 */
	public void setTableKeyName(String tableKeyName) {
		this.tableKeyName = tableKeyName;
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別
	 * @return 指定行の指定カラムの値
	 */
	public BigDecimal getDecimalValueAt(int row, Enum column) {
		return getDecimalValueAt(row, column.ordinal());
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public BigDecimal getDecimalValueAt(int row, int column) {
		Object obj = getValueAt(row, column);
		if (obj == null || obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		return DecimalUtil.toBigDecimal((String) getValueAt(row, column));
	}

	/**
	 * 指定行の指定カラムの値を返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public Object getValueAt(int row, Enum column) {
		return getValueAt(row, column.ordinal());
	}

	/**
	 * 指定行の指定カラムの値を返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public Object getValueAt(int row, int column) {
		if (row < 0) {
			return null;
		}

		return table.getValueAt(row, column);
	}

	/**
	 * 選択されている行の指定カラムの値を返します。
	 * 
	 * @param column 値を取りたい列識別enum
	 * @return 選択されている行の指定カラムの値
	 */
	public Object getSelectedRowValueAt(Enum column) {
		return getRowValueAt(table.getSelectedRow(), column);
	}

	/**
	 * 指定行の指定カラムの値を返します。
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public Object getRowValueAt(int row, Enum column) {
		return getRowValueAt(row, column.ordinal());
	}

	/**
	 * 指定行の指定カラムの値を返します。
	 * 
	 * @param row 行番号
	 * @param column 列番号
	 * @return 指定行の指定カラムの値
	 */
	public Object getRowValueAt(int row, int column) {
		if (row < 0) {
			return null;
		}

		return getModelValueAt(table.convertRowIndexToModel(row), column);
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する<BR>
	 * ※ソート、列入れ替え対応
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別
	 * @return 指定行の指定カラムの値
	 */
	public BigDecimal getRowDecimalValueAt(int row, Enum column) {
		return getRowDecimalValueAt(row, column.ordinal());
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する<BR>
	 * ※ソート、列入れ替え対応
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public BigDecimal getRowDecimalValueAt(int row, int column) {
		Object obj = getRowValueAt(row, column);
		if (obj == null || obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		return DecimalUtil.toBigDecimal((String) getRowValueAt(row, column));
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public BigDecimal getModelDecimalValueAt(int row, Enum column) {
		return getModelDecimalValueAt(row, column.ordinal());
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
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
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public Object getModelComboBoxValueAt(int row, Enum column) {
		return getModelComboBoxValueAt(row, column.ordinal());
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
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
	 * 指定行の指定カラムの値を返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public Object getModelValueAt(int row, Enum column) {
		return getModelValueAt(row, column.ordinal());
	}

	/**
	 * 指定行の指定カラムの値を返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public Object getModelValueAt(int row, int column) {
		if (row < 0) {
			return null;
		}

		return table.getModel().getValueAt(row, column);
	}

	/**
	 * スプレッドシートのダブルクリックイベントとボタンイベントの紐付け
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

		// チェックボックスのダブルクリックは処理対象外
		int col = table.getTableHeader().columnAtPoint(me.getPoint());
		if (columns.get(col).getComponent() instanceof JCheckBox) {
			return;
		}

		// ダブルクリックされ、かつ選択行がある場合
		if (me.getClickCount() == 2 && table.getSelectedRow() >= 0) {
			doubleClickLinkButton.doClick();
		}
	}

	/**
	 * テーブルの行を移動する。(複数行移動)<br>
	 * startからend までの1行または複数行を、toの位置に移動
	 * 
	 * @param start startからend までの1行または複数行を入れ替え
	 * @param end startからend までの1行または複数行を入れ替え
	 * @param to 移動先の列番号
	 */
	public void moveRow(int start, int end, int to) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.moveRow(start, end, to);
	}

	/**
	 * テーブルの行を移動する。
	 * 
	 * @param row 移動対象行
	 * @param to 移動先の列番号
	 */
	public void moveRow(int row, int to) {

		// 対象行の選択状態を解除
		removeRowSelection(row);

		// 対象行を移動
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.moveRow(row, row, to);

		// 移動後の選択状態を設定
		setRowSelection(to);
	}

	/**
	 * ヘッダーの高さを設定する
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
	 * 対象セルを選択する
	 * 
	 * @param index1
	 * @param index2
	 */
	public void setRowSelectionInterval(int index1, int index2) {
		table.setRowSelectionInterval(index1, index2);

		// 指定セルにスクロール
		scrollToCell(index1, index2);
	}

	/**
	 * 対象セルを追加選択する
	 * 
	 * @param index1
	 * @param index2
	 */
	public void addRowSelectionInterval(int index1, int index2) {
		table.addRowSelectionInterval(index1, index2);

		// 指定セルにスクロール
		scrollToCell(index1, index2);
	}

	/**
	 * 対象セルの選択を解除する
	 * 
	 * @param index1
	 * @param index2
	 */
	public void removeRowSelectionInterval(int index1, int index2) {
		table.removeRowSelectionInterval(index1, index2);
	}

	/**
	 * ALL選択
	 */
	public void selectAll() {
		table.selectAll();
	}

	/**
	 * 対象行を選択する
	 * 
	 * @param rowIndex 行番号
	 */
	public void setRowSelection(int rowIndex) {
		table.setRowSelectionInterval(rowIndex, rowIndex);

		// 指定行にスクロール
		scrollToRow(rowIndex);
	}

	/**
	 * 対象行を追加選択する
	 * 
	 * @param rowIndex 行番号
	 */
	public void addRowSelection(int rowIndex) {
		table.addRowSelectionInterval(rowIndex, rowIndex);

		// 指定行にスクロール
		scrollToRow(rowIndex);
	}

	/**
	 * 対象行の選択を解除する
	 * 
	 * @param rowIndex 行番号
	 */
	public void removeRowSelection(int rowIndex) {
		table.removeRowSelectionInterval(rowIndex, rowIndex);
	}

	/**
	 * 表示されている行数を取得する
	 * 
	 * @return 行数
	 */
	public int getRowCount() {
		return table.getRowCount();
	}

	/**
	 * 行数を返す(非表示行も含む)
	 * 
	 * @return 行数
	 */
	public int getModelRowCount() {
		return table.getModel().getRowCount();
	}

	/**
	 * 行の高さ
	 * 
	 * @param height 高さ
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
	 * @return 現在行高さ
	 */
	public int getCurrentRowHeight() {
		return this.currentRowHeight;
	}

	/**
	 * 行の高さ
	 * 
	 * @param row
	 * @param height 高さ
	 */
	public void setRowHeight(int row, int height) {
		table.setRowHeight(row, height);

		if (rowHeaderView != null) {
			rowHeaderView.setRowHeight(row, height);
			rowHeaderView.repaint();
		}
	}

	/**
	 * 行カラムの幅
	 * 
	 * @param width 幅
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
	 * 行カラムの幅getter
	 * 
	 * @return 行カラムの幅
	 */
	public int getRowColumnWidth() {
		if (rowHeaderView != null) {
			return rowHeaderView.getColumnModel().getColumn(0).getPreferredWidth();
		}
		return 0;
	}

	/**
	 * 指定されるEnumの列幅の設定
	 * 
	 * @param e 指定されるEnum
	 * @param width 幅
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
	 * 指定されるEnumの列幅の設定
	 * 
	 * @param colIndex
	 * @param width 幅
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
	 * 指定されるEnumの列幅の取得
	 * 
	 * @param e 指定されるEnum
	 * @return PreferredWidth 幅
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
	 * 選択行を取得する
	 * 
	 * @return 選択行
	 */
	public int getSelectedRow() {
		return table.getSelectedRow();
	}

	/**
	 * 選択行を取得する
	 * 
	 * @return 選択行
	 */
	public int[] getSelectedRows() {
		return table.getSelectedRows();
	}

	/**
	 * TableHeaderを取得する
	 * 
	 * @return ヘッダー
	 */
	public JTableHeader getTableHeader() {
		return table.getTableHeader();
	}

	/**
	 * rowのlabelに項番を振るか選択 ※デフォルト設定は項番あり
	 * 
	 * @param isView 項番あり：true /項番なし：false
	 */
	public void setRowLabelNumber(boolean isView) {
		this.setRowHeaderView(isView ? rowHeaderView : null);
	}

	/**
	 * ColumnModelを取得する
	 * 
	 * @return ColumnModel
	 */
	public TableColumnModel getColumnModel() {
		return table.getColumnModel();
	}

	/**
	 * CellEditorを取得する
	 * 
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor() {
		return table.getCellEditor();
	}

	/**
	 * フォーカス順
	 * 
	 * @return フォーカス順
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
	 * フォーカス判定
	 * 
	 * @return true:フォーカス可
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
	 * 列入れ替え可能かどうか
	 * 
	 * @param bln true:可能、false:なし
	 */
	public void setReorderingAllowed(boolean bln) {
		table.getTableHeader().setReorderingAllowed(bln);
	}

	/**
	 * 行選択できるかどうか
	 * 
	 * @param bln true:あり、false:なし
	 */
	public void setRowSelectionAllowed(boolean bln) {
		table.setRowSelectionAllowed(bln);
		rowHeaderView.setRowSelectionAllowed(bln);
	}

	/**
	 * 背景色を取得する
	 * 
	 * @param row
	 * @param isSelected
	 * @param hasFocus
	 * @return 背景色
	 */
	public Color getBackgroundColor(int row, boolean isSelected, boolean hasFocus) {

		// 背景色の取得
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
	 * フォントカラーを取得する
	 * 
	 * @param isSelected
	 * @param hasFocus
	 * @return フォントカラー
	 */
	public Color getCellFontColor(boolean isSelected, boolean hasFocus) {

		// フォントカラーの取得
		if (isSelected) {
			return selectedCellFontColor;
		}
		return cellFontColor;

	}

	/**
	 * 行非選択1の背景色を取得する
	 * 
	 * @return 行非選択1の背景色
	 */
	public Color getNotSelectedColor() {
		return notSelectedColor;
	}

	/**
	 * 行非選択2の背景色を取得する
	 * 
	 * @return 行非選択2の背景色
	 */
	public Color getNotSelectedColor2() {
		return notSelectedColor2;
	}

	/**
	 * 行選択時の背景色を取得する
	 * 
	 * @return 行選択時の背景色
	 */
	public Color getSelectedColor() {
		return selectedColor;
	}

	/**
	 * 行選択時のフォントカラーを取得する
	 * 
	 * @return 行選択時の背景色
	 */
	public Color getSelectedFontColor() {
		return selectedCellFontColor;
	}

	/**
	 * ListSelectionModelを取得する
	 * 
	 * @return ListSelectionModel
	 */
	public ListSelectionModel getSelectionModel() {
		return table.getSelectionModel();
	}

	/**
	 * 未選択状態にする
	 */
	public void clearSelection() {
		table.clearSelection();
	}

	/**
	 * ListSelectionListenerを設定
	 * 
	 * @param listener ListSelectionListener
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		table.getSelectionModel().addListSelectionListener(listener);
	}

	/**
	 * rowHeightを取得する。
	 * 
	 * @return int rowHeight
	 */
	public int getRowHeight() {
		return rowHeight;
	}

	/**
	 * Enterキーでボタン実行を行うかどうか
	 * 
	 * @param isEnterToButton true:ボタン実行を行う(連動させる)
	 */
	public void setEnterToButton(final boolean isEnterToButton) {
		this.isEnterToButton = isEnterToButton;
	}

	/**
	 * フォーカスを受け入れるかどうか
	 * 
	 * @param isFocusable
	 */
	@Override
	public void setFocusable(boolean isFocusable) {
		table.setFocusable(isFocusable);
		rowHeaderView.setFocusable(isFocusable);
	}

	/**
	 * セル選択を許可するかどうか
	 * 
	 * @param cellSelectionEnabled
	 */
	public void setCellSelectionEnabled(boolean cellSelectionEnabled) {
		table.setCellSelectionEnabled(cellSelectionEnabled);
		rowHeaderView.setCellSelectionEnabled(cellSelectionEnabled);
	}

	/**
	 * テーブルのCELLが編集中かどうか
	 * 
	 * @return isEditing
	 */
	public boolean isEditing() {
		return table.isEditing();
	}

	/**
	 * テーブルの編集を確定する
	 */
	public void stopCellEditing() {

		if (table.getCellEditor() == null) {
			return;
		}

		table.getCellEditor().stopCellEditing();
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param strText データ
	 * @param rowIndex 行
	 * @param col カラム
	 * @deprecated 新規での使用禁止<br>
	 *             setRowValueAtを利用すること
	 */
	public void setValueAt(String strText, int rowIndex, int col) {
		table.setValueAt(strText, rowIndex, col);
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param obj データ
	 * @param rowIndex 行
	 * @param column カラム
	 * @deprecated 新規での使用禁止<br>
	 *             setRowValueAtを利用すること
	 */
	public void setValueAt(Object obj, int rowIndex, Enum column) {
		table.setValueAt(obj, rowIndex, column.ordinal());
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param obj データ
	 * @param rowIndex 行
	 * @param col カラム
	 * @deprecated 新規での使用禁止<br>
	 *             setRowValueAtを利用すること
	 */
	public void setValueAt(Object obj, int rowIndex, int col) {
		table.setValueAt(obj, rowIndex, col);
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param strText データ
	 * @param rowIndex 行
	 * @param col カラム
	 */
	public void setModelValueAt(String strText, int rowIndex, int col) {
		table.getModel().setValueAt(strText, rowIndex, col);
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param obj データ
	 * @param rowIndex 行
	 * @param column カラム
	 */
	public void setModelValueAt(Object obj, int rowIndex, Enum column) {
		setModelValueAt(obj, rowIndex, column.ordinal());
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param obj データ
	 * @param rowIndex 行
	 * @param col カラム
	 */
	public void setModelValueAt(Object obj, int rowIndex, int col) {
		table.getModel().setValueAt(obj, rowIndex, col);
	}

	/**
	 * 指定行/カラムデータを設定する ※行ソート、列入れ替え対応
	 * 
	 * @param strText データ
	 * @param row 行
	 * @param col カラム
	 */
	public void setRowValueAt(String strText, int row, int col) {
		table.getModel().setValueAt(strText, table.convertRowIndexToModel(row), col);
	}

	/**
	 * 指定行/カラムデータを設定する ※行ソート、列入れ替え対応
	 * 
	 * @param obj データ
	 * @param row 行
	 * @param column カラム
	 */
	public void setRowValueAt(Object obj, int row, Enum column) {
		setRowValueAt(obj, row, column.ordinal());
	}

	/**
	 * 指定行/カラムデータを設定する ※行ソート、列入れ替え対応
	 * 
	 * @param obj データ
	 * @param row 行
	 * @param col カラム
	 */
	public void setRowValueAt(Object obj, int row, int col) {
		table.getModel().setValueAt(obj, table.convertRowIndexToModel(row), col);
	}

	/**
	 * 編集行/カラムを設定する
	 * 
	 * @param row 行
	 * @param col カラム
	 */
	public void editCellAt(int row, int col) {
		table.editCellAt(row, col);
	}

	/**
	 * 指定の位置に行を追加する
	 * 
	 * @param row 行
	 * @param data データ
	 */
	public void insertRow(int row, Object[] data) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.insertRow(row, data); // 追加

		// 指定行にスクロール
		scrollToRow(row);
	}

	/**
	 * 指定の位置に行を追加する
	 * 
	 * @param row 行
	 * @param list データリスト
	 */
	public void insertRow(int row, List list) {
		insertRow(row, list.toArray(new Object[list.size()]));
	}

	/**
	 * CellEditorを取得する
	 * 
	 * @param row 行
	 * @param col カラム
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor(int row, int col) {
		return table.getCellEditor(row, col);
	}

	/**
	 * CellRendererを取得する
	 * 
	 * @param col カラム
	 * @return CellRenderer
	 */
	public TableCellRenderer getComponentRenderer(int col) {
		if (columns.get(col).getComponent() != null) {
			return columns.get(col).getComponent().getCellRenderer(this);
		}
		return null;
	}

	/**
	 * テーブルモデルを返す
	 * 
	 * @return テーブルモデル
	 */
	public TableModel getModel() {
		return table.getModel();
	}

	/**
	 * 指定カラムがチェックボックスの場合、チェックされている行数を返す
	 * 
	 * @param e
	 * @return チェック行数
	 */
	public int getCheckedRowCount(Enum e) {

		// チェック行の取得
		int checkedCount = 0;
		for (int i = 0; i < table.getRowCount(); i++) {

			// チェック値の取得
			Boolean isChecked = (Boolean) getRowValueAt(i, e);

			// チェックされている場合、カウントを増加
			if (isChecked) {
				checkedCount += 1;
			}

		}

		return checkedCount;
	}

	/**
	 * 指定カラムがチェックボックスの場合、チェックされている最初の行のインデックスを返す
	 * 
	 * @param e
	 * @return 指定カラムがチェックボックスの場合、チェックされている最初の行のインデックス
	 */
	public int getFirstCheckedRowIndex(Enum e) {

		// チェック行の取得
		for (int i = 0; i < table.getRowCount(); i++) {

			// チェック値の取得
			Boolean isChecked = (Boolean) getRowValueAt(i, e);

			// チェックされている場合、カウントを増加
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
	 * TTableColumnを返す
	 * 
	 * @return TTableColumn
	 */
	public List<TTableColumn> getTableColumn() {
		return columns;
	}

	/**
	 * 指定インデックスのTTableColumnを返す
	 * 
	 * @param index インデックス
	 * @return TTableColumn
	 */
	public TTableColumn getTableColumnAt(int index) {
		return columns.get(index);
	}

	/**
	 * ヘッダーをクリックした際に、チェックボックスのカラムならば<br>
	 * 全チェック機能を有効にするか
	 * 
	 * @return true:有効
	 */
	public boolean isAllCheckWhenHeaderClicked() {
		return allCheckWhenHeaderClicked;
	}

	/**
	 * ヘッダーをクリックした際に、チェックボックスのカラムならば<br>
	 * 全チェック機能を有効にするか
	 * 
	 * @param allCheckWhenHeaderClicked true:有効
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
	 * カラム状態の復元
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

			// カラム位置復元
			restoreColumns(tableInformation);

			// 列幅復元
			restoreWidth(tableInformation);

			table.repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * カラム位置復元
	 * 
	 * @param tableInformation テーブル情報
	 */
	protected void restoreColumns(TTableInformation tableInformation) {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		List<Integer> dispOrderList = tableInformation.getDisplayOrder();

		if (dispOrderList != null && !dispOrderList.isEmpty() && dispOrderList.size() == columnCount) {
			for (int i = 0; i < columnCount; i++) {
				int displayNo = dispOrderList.get(i);

				// 現在地特定
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
	 * 列幅復元
	 * 
	 * @param tableInformation テーブル情報
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
	 * Sorter取得
	 * 
	 * @return Sorter
	 */
	public TableRowSorter<? extends TableModel> getSorter() {
		return sorter;
	}

	/**
	 * Sorter設定
	 * 
	 * @param sorter Sorter
	 */
	public void setSorter(TableRowSorter<? extends TableModel> sorter) {
		this.sorter = sorter;
	}

	/**
	 * ソート可能か
	 * 
	 * @return sortable true:可能
	 */
	public boolean isSortable() {
		return sortable;
	}

	/**
	 * ソート可能か
	 * 
	 * @param sortable true:可能
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	/**
	 * 数値比較使うかどうか
	 * 
	 * @return useNumberComparator 数値比較使うかどうか
	 */
	public boolean isUseNumberComparator() {
		return useNumberComparator;
	}

	/**
	 * 数値比較使うかどうかの設定
	 * 
	 * @param flag 数値比較使うかどうか true:使う<b>右寄せの列は数値と暫定</b>
	 */
	public void setUseNumberComparator(boolean flag) {
		this.useNumberComparator = flag;
	}

	/**
	 * アダプター取得
	 * 
	 * @return adapter アダプター
	 */
	public TTableAdapter getAdapter() {
		return adapter;
	}

	/**
	 * アダプター設定
	 * 
	 * @param adapter アダプター
	 */
	public void setAdapter(TTableAdapter adapter) {
		this.adapter = adapter;
		this.adapter.setTable(table);
	}

	/**
	 * 指定カラムの小数点以下桁数を設定 ※列入れ替えをした場合不具合あり。原則使用禁止とする。
	 * 
	 * @param column カラム
	 * @param digits 小数点以下桁数
	 */
	public void setDecimalPointAt(Enum column, int digits) {

		// 数値型のフィールドなら桁数セット
		if (columns.get(column.ordinal()).getComponent() != null
			&& columns.get(column.ordinal()).getComponent() instanceof TNumericField) {

			TNumericField component = (TNumericField) columns.get(column.ordinal()).getComponent();
			component.setDecimalPoint(digits);

			// レンダラー再設定
			getColumnModel().getColumn(column.ordinal())
				.setCellRenderer(columns.get(column.ordinal()).getComponent().getCellRenderer(this));
		}
	}

	/**
	 * 指定カラムの小数点以下桁数を設定
	 * 
	 * @param column カラム
	 * @param digits 小数点以下桁数
	 */
	public void setDecimalPoint(Enum column, int digits) {

		// 数値型のフィールドなら桁数セット
		if (columns.get(column.ordinal()).getComponent() != null
			&& columns.get(column.ordinal()).getComponent() instanceof TNumericField) {

			TNumericField component = (TNumericField) columns.get(column.ordinal()).getComponent();
			component.setDecimalPoint(digits);

			// レンダラー再設定
			int index = table.convertColumnIndexToView(column.ordinal());
			getColumnModel().getColumn(index)
				.setCellRenderer(columns.get(column.ordinal()).getComponent().getCellRenderer(this));
		}
	}

	/**
	 * 行選択されているかどうか
	 * 
	 * @return true:選択
	 */
	public boolean isSelectedRow() {
		return getSelectedRow() >= 0;
	}

	/**
	 * スクロールバーを最上行に移動する。
	 */
	public void setScrollTop() {
		JScrollBar bar = this.getVerticalScrollBar();
		bar.setValue(0);
	}

	/**
	 * スクロールバーを最下行に移動する。
	 */
	public void setScrollUnder() {
		JScrollBar bar = this.getVerticalScrollBar();
		bar.setValue(bar.getMaximum() - bar.getVisibleAmount());
	}

	/**
	 * スクロールバーを左に移動する。
	 */
	public void setScrollLeft() {
		JScrollBar bar = this.getHorizontalScrollBar();
		bar.setValue(0);
	}

	/**
	 * スクロールバーを初期位置に移動する。
	 */
	public void setScrollDefault() {
		setScrollTop();
		setScrollLeft();
	}

	/**
	 * チェックボックスの表示変更を追加.
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);

		// チェックボックスの操作可/不可(表示)も変更
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
	 * コンポーネントブランク表示用のアダプター(※チェックボックス限定)
	 * 
	 * @param e 対象列
	 * @param adapter アダプター
	 */
	public void setComponentViewAdapter(Enum e, TComponentViewAdapter adapter) {
		TableColumn col = getColumnModel().getColumn(e.ordinal());
		((TCheckBoxRenderer) col.getCellRenderer()).setViewAdapter(adapter);
		((TCheckBoxEditor) col.getCellEditor()).setViewAdapter(adapter);
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
	 * 全ての列を隠し
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
	 * 全ての列を表示
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
	 * 初期状態の取得
	 * 
	 * @return 値
	 */
	public TTableInformation getInitTableInformation() {
		return initTableInformation;
	}

	/**
	 * 列幅自動計算値の取得
	 * 
	 * @param column 列
	 * @param colWidth 設定列幅
	 * @return 列幅
	 */
	public int getAdjustColumnWidth(TableColumn column, int colWidth) {
		return getAdjustColumnWidth(column, colWidth, AutoSizeType.HeaderAdjust, -1);
	}

	/**
	 * 列幅自動計算値の取得
	 * 
	 * @param column 列
	 * @param colWidth 設定列幅
	 * @param type 幅自動調整のタイプ
	 * @param colIndex 指定列インデックス
	 * @return 列幅
	 */
	public int getAdjustColumnWidth(TableColumn column, int colWidth, AutoSizeType type, int colIndex) {

		if (fm == null) {
			fm = table.getFontMetrics(table.getFont());
		}

		String title = null;
		if (AutoSizeType.HeaderAdjust.equals(type)) {
			// 英語ユーザの場合、列幅自動計算

			String header = Util.avoidNullNT(column.getHeaderValue());
			header = header.replaceAll("\\<html\\>", "");
			header = header.replaceAll("\\</html\\>", "");
			header = header.replaceAll("\\<center\\>", "");
			header = header.replaceAll("\\</center\\>", "");
			header = header.replaceAll("\\<br\\>", "");

			title = header;
			int width = getWidthWithHtml(title);

			// 足りない場合計算値を戻る
			return Math.max(colWidth, width);

		} else {
			int width = getMaxWidth(column, colIndex, type);
			return width;

		}

	}

	/**
	 * 指定列の最大文字列の取得
	 * 
	 * @param column 指定列オブジェクト
	 * @param col 指定列インデックス
	 * @param type 幅自動調整のタイプ
	 * @return 指定列の最大文字列
	 */
	public int getMaxWidth(TableColumn column, int col, AutoSizeType type) {

		int colIndex = table.convertColumnIndexToModel(col);// 実列インデックス
		int addonWidth = fm.stringWidth("W"); // デフォルト幅

		String value = "";
		if (AutoSizeType.HeaderAndContents.equals(type) || AutoSizeType.HeaderOnly.equals(type)) {
			// ヘッダー含んだ場合のみ
			value = Util.avoidNullNT(column.getHeaderValue());
		}

		int valueWidth = getWidthWithHtml(value);

		if (AutoSizeType.HeaderOnly.equals(type)) {
			// ヘッダーのみの場合、戻る(値は計算しない)
			return addonWidth + valueWidth;
		}

		for (int i = 0; i < getRowCount(); i++) {

			// コンポかどうか判定（設定値がStringではない場合、変更なし）
			if (getCellEditor(i, colIndex) instanceof TBaseCellEditor
				|| getCellEditor(i, colIndex) instanceof TableCellRenderer
				|| !(getRowValueAt(i, colIndex) instanceof String)) {
				return initTableInformation.getWidthList().get(colIndex);
			}

			// 値
			String cellValue = Util.avoidNullNT(getRowValueAt(i, colIndex));
			int cellWidth = getWidthWithHtml(cellValue);

			// 一番長い幅取得
			if (valueWidth < cellWidth) {
				valueWidth = cellWidth;
			}
		}

		return addonWidth + valueWidth;
	}

	/**
	 * HTML含み文字の幅分析
	 * 
	 * @param cellValue
	 * @return HTML含み文字の幅
	 */
	protected int getWidthWithHtml(String cellValue) {

		// HTMLの場合、タグを外して幅計算
		if (cellValue.contains("<html>")) {

			// 改行⇒一時
			cellValue = cellValue.replaceAll("<br>", "\n");
			// <[^>]*>
			cellValue = cellValue.replaceAll("<[^>]*>", "");
			// 一時⇒改行
			cellValue = cellValue.replaceAll("\n", "<br>");

			// 改行分析(一番長い幅取得)
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
	 * 列幅自動計算<br>
	 * 幅がゼロの場合、自動計算しない
	 */
	public void autosizeColumnWidth() {
		autosizeColumnWidth(AutoSizeType.HeaderAdjust);
	}

	/**
	 * 列幅自動計算<br>
	 * 幅がゼロの場合、自動計算しない
	 * 
	 * @param type 幅自動調整のタイプ
	 */
	public void autosizeColumnWidth(AutoSizeType type) {
		autosizeColumnWidth(false, type);
	}

	/**
	 * 列幅自動計算<br>
	 * 
	 * @param isZeroAdjust <br>
	 *            true:幅がゼロの場合、自動計算する false:幅がゼロの場合、自動計算しない
	 * @param type 幅自動調整のタイプ
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
						// 列幅自動計算(useTitle=trueの場合、列タイトルの最大幅で)
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
	 * TTableの再描画
	 */
	@Override
	public JScrollBar createVerticalScrollBar() {

		// TTableの再描画
		return new ScrollBar(SwingConstants.VERTICAL) {

			@Override
			public void repaint(long l, int i, int j, int k, int i1) {
				super.repaint(l, i, j, k, i1);
				TTable.this.repaint();
			}

		};
	}

	/**
	 * 行ヘッダーは「*」で表示するかどうか
	 * 
	 * @return true:選択行の行番号は「*」で表示
	 */
	public boolean isShowRowHeaderStar() {
		return table.getRowHeaderStarIndex() != -1;
	}

	/**
	 * 行ヘッダーの「*」表示行番号の取得
	 * 
	 * @return 行ヘッダーの「*」表示行番号
	 */
	public int getRowHeaderStarIndex() {
		return table.getRowHeaderStarIndex();
	}

	/**
	 * 行ヘッダーは「*」で表示するかどうか
	 * 
	 * @param flag true:選択行の行番号は「*」で表示
	 */
	public void setShowRowHeaderStar(boolean flag) {
		if (flag) {
			setShowRowHeaderStar(table.getSelectedRow());
		} else {
			setShowRowHeaderStar(-1);
		}
	}

	/**
	 * 行ヘッダーは「*」で表示するかどうか
	 * 
	 * @param row 指定行の行番号は「*」で表示
	 */
	public void setShowRowHeaderStar(int row) {
		table.setRowHeaderStarIndex(row);
		TTable.this.repaint();
	}

	/**
	 * 行タイトルリストの取得
	 * 
	 * @return rowHeaderMessage 行タイトルリスト
	 */
	public List<String> getRowHeaderMessage() {
		return table.getRowHeaderMessage();
	}

	/**
	 * 行タイトルリストの設定
	 * 
	 * @param rowHeaderMessage 行タイトルリスト
	 */
	public void setRowHeaderMessage(List<String> rowHeaderMessage) {
		table.setRowHeaderMessage(rowHeaderMessage);
		TTable.this.repaint();
	}

	/**
	 * 背景色リストの取得
	 * 
	 * @return rowHeaderBackground 背景色リスト
	 */
	public List<Color> getRowHeaderBackground() {
		return table.getRowHeaderBackground();
	}

	/**
	 * 背景色リストの設定
	 * 
	 * @param rowHeaderBackground 背景色リスト
	 */
	public void setRowHeaderBackground(List<Color> rowHeaderBackground) {
		table.setRowHeaderBackground(rowHeaderBackground);
		TTable.this.repaint();
	}

	/**
	 * 文字色リストの取得
	 * 
	 * @return rowHeaderMessage 文字色リスト
	 */
	public List<Color> getRowHeaderForeground() {
		return table.getRowHeaderForeground();
	}

	/**
	 * 文字色リストの設定
	 * 
	 * @param rowHeaderForeground 文字色リスト
	 */
	public void setRowHeaderForeground(List<Color> rowHeaderForeground) {
		table.setRowHeaderForeground(rowHeaderForeground);
		TTable.this.repaint();
	}

	/**
	 * 指定行列のセルのフォーカスを当てる
	 * 
	 * @param row 行
	 * @param column 列
	 */
	public void requestFocus(int row, int column) {
		requestFocus();
		table.setRowSelectionInterval(row, row);
		int col = table.convertColumnIndexToView(column);
		table.setColumnSelectionInterval(col, col);

		// 指定セルにスクロール
		scrollToCell(row, column);
	}

	/**
	 * 指定行にスクロール
	 * 
	 * @param row
	 */
	public void scrollToRow(int row) {
		table.scrollRectToVisible(table.getCellRect(row, 0, true));
	}

	/**
	 * 指定セルにスクロール
	 * 
	 * @param row
	 * @param column
	 */
	public void scrollToCell(int row, int column) {
		table.scrollRectToVisible(table.getCellRect(row, column, true));
	}

	/**
	 * 指定行列のセルのフォーカスを当てる
	 * 
	 * @param row 行
	 * @param e 列Enum
	 */
	public void requestFocus(int row, Enum e) {
		requestFocus(row, e.ordinal());
	}

	/**
	 * 列タイトルの取得
	 * 
	 * @param e Enum
	 * @return 列タイトル
	 */
	public String getColumnTitle(Enum e) {
		return getTableColumnAt(e.ordinal()).getTitle();
	}

	/**
	 * 列追加時の設定の取得
	 * 
	 * @return columnSetting 列追加時の設定
	 */
	public ColumnSetting getColumnSetting() {
		return columnSetting;
	}

	/**
	 * 列追加時の設定の設定
	 * 
	 * @param columnSetting 列追加時の設定
	 */
	public void setColumnSetting(ColumnSetting columnSetting) {
		this.columnSetting = columnSetting;
	}

	/**
	 * SPACE押下対象チェックボックス列の追加
	 * 
	 * @param col SPACE押下対象チェックボックス列
	 */
	public void addCheckBoxColumn(int col) {
		if (checkBoxColumns == null) {
			checkBoxColumns = new ArrayList<Integer>();
		}
		checkBoxColumns.add(col);
	}

	/**
	 * SPACE押下対象チェックボックス列リストの取得
	 * 
	 * @return checkBoxColumns SPACE押下対象チェックボックス列リスト
	 */
	public List<Integer> getCheckBoxColumns() {
		return checkBoxColumns;
	}

	/**
	 * SPACE押下対象チェックボックス列リストの設定
	 * 
	 * @param checkBoxColumns SPACE押下対象チェックボックス列リスト
	 */
	public void setCheckBoxColumns(List<Integer> checkBoxColumns) {
		this.checkBoxColumns = checkBoxColumns;
	}

	/**
	 * SPACE押下対象チェックボックス列リストのクリア
	 */
	public void clearCheckBoxColumns() {
		if (checkBoxColumns == null) {
			this.checkBoxColumns.clear();
		}
	}

	/**
	 * 貼り付け機能使うか。true:使うの取得
	 * 
	 * @return useTablePaste 貼り付け機能使うか。true:使う
	 */
	public boolean isUseTablePaste() {
		return useTablePaste;
	}

	/**
	 * 貼り付け機能使うか。true:使うの設定
	 * 
	 * @param useTablePaste 貼り付け機能使うか。true:使う
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
	 * Enterキーで上下遷移モードの取得
	 * 
	 * @return useEnterKeyUpDownAction Enterキーで上下遷移モード
	 */
	public boolean isUseEnterKeyUpDownAction() {
		return useEnterKeyUpDownAction;
	}

	/**
	 * Enterキーで上下遷移モードの設定
	 * 
	 * @param useEnterKeyUpDownAction Enterキーで上下遷移モード
	 */
	public void setUseEnterKeyUpDownAction(boolean useEnterKeyUpDownAction) {
		this.useEnterKeyUpDownAction = useEnterKeyUpDownAction;

		if (this.useEnterKeyUpDownAction) {

			// ActionMapの設定処理を使う
			InputMap im = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
			im.put(enter, enterAct);

			KeyStroke senter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK);
			im.put(senter, shiftEnterAct);

		} else {

			// ENTERキー左右移動(TABを参照)
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
	 * テーブル列変更
	 */
	protected void fireTableColumnChanged() {
		// 列変更処理
		adapter.fireTableColumnChanged();
	}

}

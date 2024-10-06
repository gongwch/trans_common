package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.table.*;
import jp.co.ais.trans.common.util.*;

/**
 * JCListTableに、タブ順、行列ラベルのMessageIDインターフェイスを追加したTable.
 */
public class TTable extends JCListTable implements TInterfaceTableLangMessageID, TInterfaceTabControl, JCScrollListener {

	/** 垂直スクロールバー */
	private static final int VERTSB = 1;

	/** 未選択の場合のセル（行or列）数値 */
	private static final int CELL_UNSELECTION = -999;

	/** Enterキー押下時の遷移先を指定する */
	private static int enterTraverseDefault = JCTableEnum.TRAVERSE_DOWN;

	/** SHIFT+Enterキー押下時の遷移先を指定する */
	private static int enterReverseDefault = JCTableEnum.TRAVERSE_UP;

	/** 表題フォントカラー */
	private static Color foreground = ClientConfig.getTableLabelFontColor();

	/** 表題バックカラー */
	private static Color background = ClientConfig.getTableLabelColor();

	/** セル1カラー */
	private static Color cellBackground1 = ClientConfig.getTableCellBackColor1();

	/** セル2カラー */
	private static Color cellBackground2 = ClientConfig.getTableCellBackColor2();

	/** セルフォントカラー */
	private static Color cellFont = ClientConfig.getTableCellFontColor();

	static {
		// Enterキー押下時の遷移先を指定する
		String type = ClientConfig.getTableEnterTraverse();

		if ("TRAVERSE_RIGHT".equals(type)) {

			enterTraverseDefault = JCTableEnum.TRAVERSE_RIGHT;
			enterReverseDefault = JCTableEnum.TRAVERSE_LEFT;

		} else if ("TRAVERSE_LEFT".equals(type)) {

			enterTraverseDefault = JCTableEnum.TRAVERSE_LEFT;
			enterReverseDefault = JCTableEnum.TRAVERSE_RIGHT;

		} else if ("TRAVERSE_UP".equals(type)) {

			enterTraverseDefault = JCTableEnum.TRAVERSE_UP;
			enterReverseDefault = JCTableEnum.TRAVERSE_DOWN;
		}

		// 選択時カラー
		UIManager.put("Table.selectionBackground", ClientConfig.getTableSelectColor());
		UIManager.put("Table.selectionForeground", ClientConfig.getTableSelectCellFontColor());
	}

	/** タブNo */
	protected int tabControlNo = -1;

	/** タブ遷移 */
	protected boolean isTabEnabled = true;

	/** 複数選択 */
	protected boolean isSelectMultiLine = false;

	/** 行番号表示/非表示 */
	protected boolean isRowLabelNumber = true;

	/** 全行チェックON/OFFモード */
	protected boolean isAllCheck = true;

	/** 行タイトル */
	protected String[] rowTitleMessageID = null;

	/** 列タイトル */
	protected String[] colTitleMessageID = null;

	/** チェックボックスカラム番号 */
	protected int checkBoxColumnNumber = 0;

	/** 番号付けを始める行番号 */
	protected int startLabelNumberRow = 0;

	/** 行ラベル */
	protected Vector rowLabels;

	/** 列ラベル */
	protected Vector columnLabels;

	/** セルの縦サイズ */
	protected CellSize cellSize;

	/** 処理連動ボタン */
	protected TButton btn = null;

	/** true:Enterキーでボタン実行 */
	protected boolean isEnterToButton = false;

	/** スクロールバーの垂直値を保持 */
	protected int currentVValue;

	/** スクロールバーの横値を保持 */
	protected int currentHValue;

	/** ソート機能 */
	protected boolean sort;

	/** ソート押下履歴リスト */
	protected List<Boolean> sortList;

	/** 日付型カラムインデックス */
	protected List<Integer> dateSortIndexList = new LinkedList<Integer>();

	/** 金額型カラムインデックス */
	protected List<Integer> numberSortIndexList = new LinkedList<Integer>();

	/** Vector型データソース（ソート用） */
	private JCVectorDataSource ds;

	/** ENTERでどこに移動するか */
	protected int enterTraverseType = enterTraverseDefault;

	/** SHIFT+ENTERでどこに移動するか */
	protected int enterReverseType = enterReverseDefault;

	/** テーブル列サイズを自動設定するかどうか true:自動設定 */
	protected boolean autoColumnWidthSet = false;

	/**
	 * Constructor.
	 */
	public TTable() {
		super();
		initComponents();
		// スクロールバー用のリスナーの追加
		super.addScrollListener(this);
	}

	/**
	 * 色リセット
	 */
	public void resetColor() {
		CellStyleModel labelStyle = this.getDefaultLabelStyle();
		labelStyle.setForeground(foreground);
		labelStyle.setBackground(background);

		CellStyleModel cellStyle = this.getDefaultCellStyle();
		cellStyle.setForeground(cellFont);
		cellStyle.setRepeatBackgroundColors(new Color[] { cellBackground1, cellBackground2 });

		repaint();
	}

	/**
	 * TTableデフォルト設定
	 */
	private void initComponents() {

		// 各種表示設定
		this.setFocusIndicator(JCTableEnum.FOCUS_NONE);
		this.setSelectionType(JCListTable.SELECT_ROW);
		this.setResizeInteractive(false);

		// 行の表題
		this.setRowLabelDisplay(true);
		this.setCharWidth(JCTableEnum.LABEL, 3);

		// 列の表題
		this.setColumnLabelDisplay(true);

		// フォーカス
		this.setFocusable(true);

		// ラベル含みの選択
		this.setSelectIncludeLabels(true);

		// 行列サイズの変更許可
		this.setAllowCellResize(JCTableEnum.RESIZE_COLUMN);

		// Scrollバー表示
		this.setVertSBDisplay(JCTableEnum.SCROLLBAR_AS_NEEDED);
		this.setHorizSBDisplay(JCTableEnum.SCROLLBAR_AS_NEEDED);

		// AutoEditを標準に
		this.setAutoEdit(true);

		// 表題スタイル
		CellStyleModel labelStyle = this.getDefaultLabelStyle();
		labelStyle.setClipHints(CellStyleModel.SHOW_NONE);
		labelStyle.setFont(labelStyle.getFont().deriveFont(Font.BOLD));
		labelStyle.setHorizontalAlignment(JCTableEnum.CENTER);
		labelStyle.setVerticalAlignment(JCTableEnum.BOTTOM);
		labelStyle.setForeground(foreground);
		labelStyle.setBackground(background);

		// セルスタイル
		CellStyleModel cellStyle = this.getDefaultCellStyle();
		cellStyle.setVerticalAlignment(JCTableEnum.BOTTOM);
		cellStyle.setClipHints(CellStyleModel.SHOW_NONE);

		Color[] cellBackColor = { cellBackground1, cellBackground2 };
		cellStyle.setForeground(cellFont);
		cellStyle.setRepeatBackgroundColors(cellBackColor);
		cellStyle.setRepeatBackground(JCTableEnum.REPEAT_ROW);

		// セルボーダー
		cellStyle.setCellBorder(new JCCellBorder(JCTableEnum.BORDER_PLAIN));

		// 行の高さ
		this.setPixelHeight(JCTableEnum.ALL, 20);

		// 各種リスナ

		// マウスリスナ（ボタン実行）
		this.addMouseListener(new MouseButtonConnectedListener());

		// マウスリスナ（チェックボックス）
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent ev) {

				// イベント発生位置にあるセルの位置（行・列）を取得
				JCCellPosition cp = TTable.this.XYToCell(ev.getX(), ev.getY());

				if (cp == null) {
					return;
				}

				// データ、ヘッダが存在しない位置をクリックした場合はクリック処理は行なわない。
				if (cp.row == CELL_UNSELECTION && cp.column == CELL_UNSELECTION) {
					return;
				}

				// 行ラベルクリック
				if (cp.column == JCTableEnum.LABEL) {
					TTable.this.traverse(cp.row, 0, true, true);
					TTable.this.setRowSelection(getCurrentRow(), getCurrentRow());
				}

				// 右/中クリック対応
				if (ev.getButton() == MouseEvent.BUTTON2 || ev.getButton() == MouseEvent.BUTTON3) {

					// ラベルの場合は対象外
					if (cp.column != JCTableEnum.LABEL && cp.row != JCTableEnum.LABEL) {
						TTable.this.setCurrentCell(cp.row, 0);
					}

					TTable.this.setRowSelection(getCurrentRow(), getCurrentRow());
				}

				// ヘッダー左クリック
				if (ev.getButton() == MouseEvent.BUTTON1 && cp.row == JCTableEnum.LABEL) {

					int type = -1; // 列のコンポーネントタイプ(-1=なし)
					Component comp = null; // コンポーネントが存在する最上位行のオブジェクト

					if (sort || (isAllCheck && cp.column == checkBoxColumnNumber)) {

						// 一番先頭のコンポーネントを認識させる.(空カラムとソートを考慮)
						for (int i = 0; i < TTable.this.getNumRows(); i++) {
							comp = TTable.this.getComponent(i, cp.column);

							if (comp == null || !comp.isEnabled()) {
								continue;
							}

							if (comp instanceof TCheckBox) {
								type = 1;
								break;

							} else if (comp instanceof TTableRadioGroup) {
								type = 2;
								break;
							}
						}
					}

					// チェックボックス全チェック制御
					if (isAllCheck && cp.column == checkBoxColumnNumber) {

						try {
							JCVectorDataSource ds_ = (JCVectorDataSource) TTable.this.getDataSource();

							switch (type) {
								case 1: // チェックボックス
									boolean isCheck = ((TCheckBox) comp).isSelected();

									for (int i = 0; i < ds_.getNumRows(); i++) {
										TCheckBox checkBox = (TCheckBox) TTable.this.getComponent(i,
											checkBoxColumnNumber);
										if (checkBox != null && checkBox.isEnabled()) {
											checkBox.setSelected(!isCheck);
										}
									}

									setCurrentCell(0, checkBoxColumnNumber);
									setRowSelection(0, 0);

									break;

								// 保留
								// case 2: // ラジオグループ
								// // 先頭のINDEXに合わせる
								// int index = ((TTableRadioGroup) comp).getSelectedIndex();
								//
								// for (int i = 0; i < ds_.getNumRows(); i++) {
								// TTableRadioGroup radio = (TTableRadioGroup) TTable.this.getComponent(i,
								// checkBoxColumnNumber);
								// if (radio != null) {
								// radio.setSelectedIndex(index + 1);
								// }
								// }
								//
								// setCurrentCell(0, 0);
								// setRowSelection(0, 0);
								//
								// break;
							}

							// データの方
							if (!(comp instanceof TTableCheckBox)) {
								Object obj = TTable.this.getDataSource().getTableDataItem(0, checkBoxColumnNumber);

								if (obj instanceof Boolean) {
									ds_.setCell(JCTableEnum.ALLCELLS, checkBoxColumnNumber, !((Boolean) obj));
								}
							}

						} catch (Exception e) {
							ev.consume();
						}
					}

					// ソート処理(コンポーネント有りの場合は行なわない)
					if (sort && type == -1) {

						boolean isAsc = sortList.get(cp.column);

						Sort.sortByColumn(TTable.this, cp.column, isAsc ? Sort.ASCENDING : Sort.DESCENDING,
							new ObjectComparator(cp.column));

						// 連番振りなおし
						if (isRowLabelNumber) {
							TableDataView tdv = (TableDataView) TTable.this.getDataView();
							for (int i = 0; i < ds.getNumRows(); i++) {
								ds.setRowLabel(tdv.getDataRow(i), i + 1);
							}
						}

						sortList.set(cp.column, !isAsc);
					}
				}
			}
		});

		// キーリスナー設定
		setKeyListener();

		// セル選択リスナ
		this.addTraverseCellListener(new JCTraverseCellAdapter() {

			@Override
			public void traverseCell(JCTraverseCellEvent ev) {

				// マルチライン設定以外は、一行選択限定
				if (!isSelectMultiLine) {
					TTable.this.setRowSelection(TTable.this.getCurrentRow(), TTable.this.getCurrentRow());
				}
			}
		});

		// row選択制御リスナ
		this.addSelectListener(new JCSelectAdapter() {

			@Override
			public void select(JCSelectEvent ev) {

				if (!TTable.this.isSelectMultiLine) {
					// 1行しか選択できないようにする
					if (ev.getAction() == JCSelectEvent.EXTEND && Math.abs(ev.getStartRow() - ev.getEndRow()) > 0) {
						ev.setCancelled(true);
					}
				}
			}
		});
	}

	/**
	 * キーリスナー
	 */
	protected void setKeyListener() {
		// キー用リスナ
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent ev) {

				// ファンクションキー用ハンドラ
				handleKeyPressed(ev);

				switch (ev.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						// Enterキー押下（データ呼出がない場合は次行選択）
						ev.consume();

						if (TTable.this.btn != null && isEnterToButton) {
							TTable.this.btn.doClick();

						} else {
							TTable.this.traverse(ev.isShiftDown() ? enterReverseType : enterTraverseType);
							TTable.this.requestFocus();// フォーカスをシートに戻す
						}

						TTable.this.setRowSelection(TTable.this.getCurrentRow(), TTable.this.getCurrentRow());

						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent ev) {

				switch (ev.getKeyCode()) {
					case KeyEvent.VK_UP:
					case KeyEvent.VK_DOWN:
						if (ev.isShiftDown()) {
							return;
						}

					case KeyEvent.VK_PAGE_UP:
					case KeyEvent.VK_PAGE_DOWN:
					case KeyEvent.VK_TAB:
					case KeyEvent.VK_ESCAPE:

						if (TTable.this.getSelectionPolicy() != JCTableEnum.SELECT_NONE) {
							// テーブルが選択可能の際は現在行を再選択
							TTable.this.setRowSelection(TTable.this.getCurrentRow(), TTable.this.getCurrentRow());
						}

						break;

					case KeyEvent.VK_SPACE:
						// スペースキー押下(チェックボックスがある場合反応)
						try {
							JCVectorDataSource ds_ = (JCVectorDataSource) TTable.this.getDataSource();

							if (getSelectionPolicy() == JCTableEnum.SELECT_NONE) {
								// 選択なし状態の場合は、カーソル主体
								int row = getCurrentRow();
								int column = getCurrentColumn();
								Component comp = TTable.this.getComponent(row, column);

								if (comp == null || !comp.isEnabled()) {
									return;
								}

								if (comp instanceof TCheckBox) {
									TCheckBox checkBox = (TCheckBox) comp;
									checkBox.setSelected(!checkBox.isSelected());

								} else if (comp instanceof TTableRadioGroup) {
									TTableRadioGroup radio = (TTableRadioGroup) comp;
									radio.nextSelected();
								}

								// データの方
								if (!(comp instanceof TTableCheckBox)) {
									Object obj = TTable.this.getDataSource().getTableDataItem(row, column);
									if (obj instanceof Boolean) {
										ds_.setCell(row, column, !((Boolean) obj));
									}
								}

							} else {
								// 選択ありの場合は、選択行全て
								Collection<JCCellRange> selectRanges = getSelectedCells();

								int[] rows = TTable.this.getSelectedRows();

								for (int row : rows) {
									// コンポーネントの方
									Component comp = TTable.this.getComponent(row, checkBoxColumnNumber);

									if (comp == null || !comp.isEnabled()) {
										continue;
									}

									if (comp instanceof TCheckBox) {
										TCheckBox checkBox = (TCheckBox) comp;
										checkBox.setSelected(!checkBox.isSelected());

									} else if (comp instanceof TTableRadioGroup) {
										TTableRadioGroup radio = (TTableRadioGroup) comp;
										radio.nextSelected();
									}

									// データの方
									if (!(comp instanceof TTableCheckBox)) {
										Object obj = TTable.this.getDataSource().getTableDataItem(row,
											checkBoxColumnNumber);

										if (obj instanceof Boolean) {
											ds_.setCell(row, checkBoxColumnNumber, !((Boolean) obj));
										}
									}
								}

								setSelectedCells(selectRanges);
							}

						} catch (Exception e) {
							ev.consume();
						}

						break;
				}
			}
		});
	}

	/**
	 * 親コンテナをたどり、最初のFrameを返す.
	 * 
	 * @return 元フレーム
	 */
	public Frame getParentFrame() {
		for (Container p = this.getParent(); p != null; p = p.getParent()) {
			if (p instanceof Frame) {
				return (Frame) p;
			}
		}
		return null;
	}

	/**
	 * 行タイトルmessageID実装.
	 */
	public void setRowTitleMessageID(List<String> list) {
		if (list == null) {
			this.rowTitleMessageID = null;
			return;
		}
		this.rowTitleMessageID = (String[]) list.toArray();
	}

	/**
	 * 列タイトルmessageID実装.
	 */
	public void setColumnTitleMessageID(List<String> list) {
		if (list == null) {
			this.colTitleMessageID = null;
			return;
		}
		this.colTitleMessageID = (String[]) list.toArray();
	}

	/**
	 * 行タイトルmessageID実装.
	 */
	public void setRowTitleMessageID(String[] messageIDs) {
		this.rowTitleMessageID = messageIDs;
	}

	/**
	 * 列タイトルmessageID実装.
	 */
	public void setColumnTitleMessageID(String[] messageIDs) {
		this.colTitleMessageID = messageIDs;
	}

	/**
	 * 行タイトルmessageID実装.
	 */
	public String[] getRowTitleMessageID() {
		return this.rowTitleMessageID;
	}

	/**
	 * 列タイトルmessageID実装.
	 */
	public String[] getColumnTitleMessageID() {
		return this.colTitleMessageID;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * 
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	/**
	 * テーブル行を複数行選択(飛ばし選択は不可)<br>
	 * setSelectMultiRange()との併用は不可<br>
	 * falseを指定された場合、デフォルトの単行選択に戻る
	 * 
	 * @param bool 複数行：true /単行：false
	 */
	public void setSelectMaltiLine(boolean bool) {
		this.isSelectMultiLine = bool;

		setSelectionPolicy(SelectionModel.SELECT_RANGE);
	}

	/**
	 * テーブル行を複数行の範囲選択(飛ばし選択が可能)<br>
	 * setSelectMaltiLine()との併用は不可<br>
	 * falseを指定された場合、デフォルトの単行選択に戻る
	 * 
	 * @param bool 複数範囲：true /単行：false
	 */
	public void setSelectMultiRange(boolean bool) {
		this.isSelectMultiLine = bool;

		setSelectionPolicy(bool ? SelectionModel.SELECT_MULTIRANGE : SelectionModel.SELECT_RANGE);
	}

	/**
	 * rowのlabelに項番を振るか選択 ※デフォルト設定は項番あり
	 * 
	 * @param bool 項番あり：true /項番なし：false
	 */
	public void setRowLabelNumber(boolean bool) {
		this.isRowLabelNumber = bool;
	}

	/**
	 * 番号付けを始める行番号
	 * 
	 * @param startLabelNumberRow 行番号
	 */
	public void setStartLabelNumberRow(int startLabelNumberRow) {
		this.startLabelNumberRow = startLabelNumberRow;
	}

	/**
	 * チェックボックスのある列番号 ※デフォルトは0(一番左)
	 * 
	 * @param number 列番号
	 */
	public void setCheckBoxColumnNumber(int number) {
		this.checkBoxColumnNumber = number;
	}

	/**
	 * チェックボックスので全チェックできるか ※デフォルトはできる
	 * 
	 * @param bool できる：true /できない：false
	 */
	public void setAllCheck(boolean bool) {
		this.isAllCheck = bool;
	}

	/**
	 * データソース取得
	 * 
	 * @return TableDataModel
	 */
	@Override
	public TableDataModel getDataSource() {
		return super.getDataSource();
	}

	/**
	 * テーブルにデータソースをセット
	 * 
	 * @param ds データソース
	 */
	public void setDataSource(JCVectorDataSource ds) {
		super.setDataSource(ds);

		resetDataSource(ds);
	}

	/**
	 * 既にセットしたデータソースを再設定する.<br>
	 * ※DataSource自体が変わっている場合は変更されないので注意.<br>
	 * ※CellEditorの中ではsetDataSource()はNG.<br>
	 * 
	 * @param ds_ データソース
	 */
	public void resetDataSource(JCVectorDataSource ds_) {
		this.ds = ds_;

		// ソートのテーブル押下履歴設定

		sortList = new LinkedList<Boolean>();
		for (int i = 0; i < ds_.getNumColumns(); i++) {
			sortList.add(false);
		}

		// rowのlabelに項番を振る
		if (isRowLabelNumber) {

			for (int i = 0, row = startLabelNumberRow; row <= this.getNumRows(); i++, row++) {
				ds_.setRowLabel(row, String.valueOf(i + 1));
			}
		} else {

			// カラムラベルの再設定
			Vector dsRLabels = ds_.getRowLabels();
			if (dsRLabels != null && !dsRLabels.isEmpty()) {
				rowLabels = dsRLabels;
			}

			if (rowLabels != null) {
				ds_.setRowLabels(rowLabels);
			}
		}

		// カラムラベルの再設定
		Vector dsCLabels = ds_.getColumnLabels();
		if (dsCLabels != null && !dsCLabels.isEmpty()) {
			columnLabels = dsCLabels;
		}

		// カラムラベル設定
		if (columnLabels != null) {
			ds_.setColumnLabels(columnLabels);
		}

		// サイズ指定があれば、行の縦サイズを全行に設定
		if (cellSize != null) {
			for (int i = 0, row = startLabelNumberRow; row <= this.getNumRows(); i++, row++) {
				setRowCellSize(row, cellSize);
			}
		}

		// 保持しておいた値をスクロールバーにセット
		super.getVertSB().setValue(currentVValue);
		super.getHorizSB().setValue(currentHValue);
	}

	/**
	 * ファンクションキー処理.
	 * 
	 * @param ev キーイベント
	 */
	protected void handleKeyPressed(KeyEvent ev) {

		TGuiUtil.dispatchPanelBusinessFunctionKey(this, ev);
	}

	// 便利用メソッド

	/**
	 * 初期化補助. スプレッドのカラム設定
	 * 
	 * @param columnLabelMessageIDs ヘッダータイトルID
	 * @param columnCharWidths セル幅
	 */
	public void initSpreadSheet(String[] columnLabelMessageIDs, int[] columnCharWidths) {
		// タイトルにWordIDのセット
		if (columnLabelMessageIDs != null) {
			this.setColumnTitleMessageID(columnLabelMessageIDs);
			this.setColumnLabels(columnLabelMessageIDs);
		}

		this.setColumnWidths(columnCharWidths);
	}

	/**
	 * セル幅のセット
	 * 
	 * @param widths 幅リスト
	 */
	public void setColumnWidths(int... widths) {
		// セル幅のセット
		for (int i = 0; i < widths.length; i++) {
			if (widths[i] == 0) {
				this.setColumnHidden(i, true);
			} else {
				this.setCharWidth(i, widths[i]);
			}

			this.setMinWidth(i, -1);
		}
	}

	/**
	 * @see com.klg.jclass.table.JCTable#setCharWidth(int, int)
	 */
	@Override
	public void setCharWidth(int i, int j) {
		super.setCharWidth(i, (int) (j * 1.2));
	}

	/**
	 * ピクセルでのセル幅のセット
	 * 
	 * @param widths 幅リスト
	 */
	public void setColumnPixelWidths(int... widths) {

		// セル幅のセット
		for (int i = 0; i < widths.length; i++) {
			this.setPixelWidth(i, widths[i]);
		}
	}

	/**
	 * 現状の列幅(ピクセル)を取得する(TTableラベル設定が前提)
	 * 
	 * @return 列幅リスト
	 */
	public int[] getColumnPixelWidths() {
		getColumnWidthValues();

		int[] widths = new int[columnLabels.size()];
		for (int i = 0; i < columnLabels.size(); i++) {
			widths[i] = getPixelWidth(i);
		}

		return widths;
	}

	/**
	 * ヘッダの高さ設定
	 * 
	 * @param size 高さ
	 */
	public void setHeaderHeight(int size) {
		this.setPixelHeight(JCTableEnum.LABEL, size);
	}

	/**
	 * データ行の高さ設定.<br>
	 * （ヘッダは変更しない）
	 * 
	 * @param size 高さ
	 */
	public void setRowHeight(int size) {
		int headSize = this.getPixelHeight(JCTableEnum.LABEL);
		this.setPixelHeight(JCTableEnum.ALL, size);
		setHeaderHeight(headSize);
	}

	/**
	 * 行ラベルの設定
	 * 
	 * @param titles 行ラベル
	 */
	public void setRowLabels(String[] titles) {

		this.rowLabels = new Vector(titles.length);

		for (String title : titles) {
			this.rowLabels.add(title);
		}
	}

	/**
	 * 列(カラム)ラベルの設定
	 * 
	 * @param titles 列ラベル
	 */
	public void setColumnLabels(String[] titles) {

		this.columnLabels = new Vector(titles.length);

		for (String title : titles) {
			this.columnLabels.add(title);
		}
	}

	/**
	 * 行の縦サイズ(高さ)を指定.<br>
	 * サイズは、何行分かを指定する.
	 * 
	 * @param size サイズ(行数)
	 */
	public void setRowHeightSize(int size) {
		this.cellSize = new CellSize(size);
	}

	/**
	 * Enterキーでボタン実行を行うかどうか
	 * 
	 * @param isConnected true:ボタン実行を行う(連動させる)
	 */
	public void setEnterToButton(boolean isConnected) {
		this.isEnterToButton = isConnected;
	}

	/**
	 * Enterキーでボタン実行を行うかどうか
	 * 
	 * @return true:ボタン実行を行う
	 */
	public boolean isEnterToButton() {
		return this.isEnterToButton;
	}

	/**
	 * 特定ラベルの色を変更する
	 * 
	 * @param labelRowNum 対象ラベルIndex
	 * @param color 変更色
	 */
	public void setLabelColor(int labelRowNum, Color color) {

		CellStyleModel model = new JCCellStyle(getDefaultLabelStyle());
		model.setForeground(color);

		setCellStyle(JCTableEnum.LABEL, labelRowNum, model);
	}

	/**
	 * 指定列を中央寄せスタイルに変更する
	 * 
	 * @param rowNums 列Indexリスト
	 */
	public void setCenterStyle(int... rowNums) {

		CellStyleModel model = new JCCellStyle(getDefaultCellStyle());
		model.setHorizontalAlignment(SwingConstants.CENTER);

		for (int rowNum : rowNums) {
			setCellStyle(JCTableEnum.ALLCELLS, rowNum, model);
		}
	}

	/**
	 * 指定列を右寄せスタイルに変更する
	 * 
	 * @param rowNums 列Indexリスト
	 */
	public void setRightStyle(int... rowNums) {

		CellStyleModel model = new JCCellStyle(getDefaultCellStyle());
		model.setHorizontalAlignment(SwingConstants.RIGHT);

		for (int rowNum : rowNums) {
			setCellStyle(JCTableEnum.ALLCELLS, rowNum, model);
		}
	}

	/**
	 * スプレッド内イベント登録. 指定されたボタンの押下イベントを起こす.
	 * 
	 * @param btn_ 対象のボタン
	 */
	public void addSpreadSheetSelectChange(TButton btn_) {
		this.btn = btn_;
	}

	/**
	 * マウス動作とボタンを連動させる為のリスナー
	 */
	protected class MouseButtonConnectedListener extends MouseAdapter {

		// マウス
		@Override
		public void mouseClicked(MouseEvent ev) {
			// 左クリック
			JCCellPosition cp = TTable.this.XYToCell(ev.getX(), ev.getY());

			if (ev.getButton() == MouseEvent.BUTTON1 && cp.row != JCTableEnum.LABEL && cp.column != JCTableEnum.LABEL) {

				// スプレッドシートでダブルクリック時、btnを押下する
				if ((ev.getClickCount() >= 2) && TTable.this.btn != null) {

					if (!TTable.this.isFocusOwner()) {
						ev.consume();
						return;
					}

					TTable.this.btn.doClick();

					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						// 連続実行防止用のタイムラグ
					}
				}
			}
		}
	}

	/**
	 * スクロール後の処理Event
	 * 
	 * @see com.klg.jclass.table.JCScrollListener#afterScroll(com.klg.jclass.table.JCScrollEvent)
	 */
	public void afterScroll(JCScrollEvent e) {
		// 垂直スクロールバーのときのみ、値を保持
		if (e.getDirection() == VERTSB) {
			// 現在のスクロールバーの値を変数に保持
			currentVValue = e.getValue();
		}

		if (e.getDirection() == Adjustable.HORIZONTAL) {
			currentHValue = e.getValue();
		}
	}

	/**
	 * スクロール時の処理Event
	 * 
	 * @see com.klg.jclass.table.JCScrollListener#scroll(com.klg.jclass.table.JCScrollEvent)
	 */
	public void scroll(JCScrollEvent arg0) {
		// 処理無し(Override用)
	}

	/**
	 * ソート機能フラグ設定<br>
	 * コンポーネント付きのテーブル（コンポボックスなど）は設定できない。
	 * 
	 * @param isSort true : ソート機能付き
	 */
	public void setSort(boolean isSort) {
		this.sort = isSort;
	}

	/**
	 * ソート機能フラグ設定 可/非
	 * 
	 * @return true:可
	 */
	public boolean isSort() {
		return this.sort;
	}

	/**
	 * 日付型のカラムインデックス指定 <br>
	 * YYYY/MM/DDの形式のString変換し、表示した場合。
	 * 
	 * @param index
	 */
	public void setDateStringColumn(int index) {
		dateSortIndexList.add(index);
	}

	/**
	 * Number型のカラムインデックス指定<br>
	 * 数字をStringに変換し、表示したカラムの場合。
	 * 
	 * @param index
	 */
	public void setNumberStringColumn(int index) {
		numberSortIndexList.add(index);
	}

	/**
	 * Number型のカラムListをクリアする
	 */
	public void clearNumberStringColumn() {
		numberSortIndexList.clear();
	}

	/**
	 * 日付型のカラムListをクリアする
	 */
	public void clearDateStringColumn() {
		dateSortIndexList.clear();
	}

	/**
	 * チェックボックスコンポーネントを指定カラム(全行)へ設定する.<br>
	 * カラムは0番目へ設定.
	 */
	public void setCheckBoxComponents() {
		this.setCheckBoxComponents(0);
	}

	/**
	 * チェックボックスコンポーネントを指定カラム(全行)へ設定する.<br>
	 * 
	 * @param column カラム
	 */
	public void setCheckBoxComponents(int column) {
		this.setCheckBoxColumnNumber(column);

		for (int i = 0; i < this.getNumRows(); i++) {
			this.setCheckBoxComponent(i, column);
		}
	}

	/**
	 * チェックボックスコンポーネントを指定行、カラムへ設定する.<br>
	 * 選択状態は非選択.
	 * 
	 * @param row 行
	 * @param column カラム
	 */
	public void setCheckBoxComponent(int row, int column) {

		this.setCheckBoxComponent(row, column, false);
	}

	/**
	 * チェックボックスコンポーネントを指定行、カラムへ設定する.<br>
	 * 
	 * @param row 行
	 * @param column カラム
	 * @param isSelected true:選択
	 */
	public void setCheckBoxComponent(int row, int column, boolean isSelected) {
		TCheckBox checkBox = new TTableCheckBox(this, row, column);
		checkBox.setSelected(isSelected);

		this.setComponent(row, column, checkBox);
	}

	/**
	 * 最左列(0列)のチェックボックスコンポーネントの選択状態を変更する.
	 * 
	 * @param row 行
	 * @param isSelected true:選択
	 */
	public void setSelectedCheckBox(int row, boolean isSelected) {
		setSelectedCheckBox(row, 0, isSelected);
	}

	/**
	 * チェックボックスコンポーネントの選択状態を変更する.
	 * 
	 * @param row 行
	 * @param column カラム
	 * @param isSelected true:選択
	 */
	public void setSelectedCheckBox(int row, int column, boolean isSelected) {
		TCheckBox checkBox = (TCheckBox) this.getComponent(row, column);
		checkBox.setSelected(isSelected);
	}

	/**
	 * 最左列(0列)のチェックボックスコンポーネントの指定行、カラムの選択状態を返す.
	 * 
	 * @param row 行
	 * @return true:選択
	 */
	public boolean isCheckedCheckBox(int row) {
		return isCheckedCheckBox(row, 0);
	}

	/**
	 * チェックボックスコンポーネントの指定行、カラムの選択状態を返す.
	 * 
	 * @param row 行
	 * @param column カラム
	 * @return true:選択
	 */
	public boolean isCheckedCheckBox(int row, int column) {
		TCheckBox checkBox = (TCheckBox) this.getComponent(row, column);

		if (checkBox == null) {
			return false;
		}

		return checkBox.isSelected();
	}

	/**
	 * 最左列(0列)のチェックボックスコンポーネントの操作状態を変更する.
	 * 
	 * @param row 行
	 * @param isEnabled true:編集可
	 */
	public void setEnabledCheckBox(int row, boolean isEnabled) {
		setEnabledCheckBox(row, 0, isEnabled);
	}

	/**
	 * チェックボックスコンポーネントの操作状態を変更する.
	 * 
	 * @param row 行
	 * @param column カラム
	 * @param isEnabled true:編集可
	 */
	public void setEnabledCheckBox(int row, int column, boolean isEnabled) {
		TCheckBox checkBox = (TCheckBox) this.getComponent(row, column);
		checkBox.setEnabled(isEnabled);
	}

	/**
	 * ラジオボタングループコンポーネントを指定カラム(全行)へ設定する.<br>
	 * カラムは0番目へ設定.
	 */
	public void setRadioGroupComponents() {
		this.setRadioGroupComponents(0);
	}

	/**
	 * ラジオボタングループコンポーネントを指定カラム(全行)へ設定する.
	 * 
	 * @param column カラム
	 */
	public void setRadioGroupComponents(int column) {
		this.setCheckBoxColumnNumber(column);

		for (int i = 0; i < this.getNumRows(); i++) {
			this.setRadioGroupComponent(i, column);
		}
	}

	/**
	 * ラジオボタングループコンポーネントを指定カラム(全行)へ設定する.
	 * 
	 * @param column カラム
	 * @param index 選択ラジオボタンIndex
	 */
	public void setRadioGroupComponents(int column, int index) {
		this.setCheckBoxColumnNumber(column);

		for (int i = 0; i < this.getNumRows(); i++) {
			this.setRadioGroupComponent(i, column, index);
		}
	}

	/**
	 * ラジオボタングループコンポーネントを指定行、カラムへ設定する.<br>
	 * Indexは 0（左端）.
	 * 
	 * @param row 行
	 * @param column カラム
	 */
	public void setRadioGroupComponent(int row, int column) {

		this.setRadioGroupComponent(row, column, 0);
	}

	/**
	 * ラジオボタングループコンポーネントを指定行、カラムへ設定する.
	 * 
	 * @param row 行
	 * @param column カラム
	 * @param index 選択ラジオボタンIndex
	 */
	public void setRadioGroupComponent(int row, int column, int index) {
		TTableRadioGroup radio = new TTableRadioGroup(this, row);
		radio.setSelectedIndex(index);

		this.setComponent(row, column, radio);
	}

	/**
	 * ラジオボタングループコンポーネントの選択状態を変更する.
	 * 
	 * @param row 行
	 * @param column カラム
	 * @param index 選択ラジオボタンIndex
	 */
	public void setSelectedRadioGroup(int row, int column, int index) {
		TTableRadioGroup radio = (TTableRadioGroup) this.getComponent(row, column);
		radio.setSelectedIndex(index);
	}

	/**
	 * ラジオボタングループコンポーネントの指定行、カラムの選択状態を返す.
	 * 
	 * @param row 行
	 * @param column カラム
	 * @return 選択ラジオボタンIndex
	 */
	public int getRadioGroupIndex(int row, int column) {
		TTableRadioGroup radio = (TTableRadioGroup) this.getComponent(row, column);
		if (radio == null) {
			return -1;
		}

		return radio.getSelectedIndex();
	}

	/**
	 * リピート配色を無効にする
	 */
	public void setRepeatBackgroundOff() {
		this.getDefaultCellStyle().setRepeatBackground(JCTableEnum.REPEAT_NONE);
	}

	/**
	 * 選択行を取得する.
	 * 
	 * @return 選択行
	 */
	public int[] getSelectedRows() {
		Set<Integer> list = new TreeSet<Integer>();

		Collection<JCCellRange> collection = getSelectedCells();

		if (collection == null) {
			return new int[0];
		}

		for (JCCellRange vtr : collection) {

			// 選択のは行を終える
			int start = vtr.start_row;
			int end = vtr.end_row;

			if (start > end) {
				// startとend入れ替え
				start = start ^ end;
				end = start ^ end;
				start = start ^ end;
			}

			while (start <= end) {
				list.add(start++);
			}
		}

		int[] rtArray = new int[list.size()];
		int i = 0;
		for (Integer num : list) {
			rtArray[i++] = num;
		}

		return rtArray;
	}

	/**
	 * 最終選択行を取得する.(フォーカス行とは別)
	 * 
	 * @return 最終選択行
	 */
	public int getLastSelectedRow() {
		Collection<JCCellRange> collection = getSelectedCells();

		if (collection == null) {
			return -999;
		}

		int endRow = -999;
		for (JCCellRange vtr : collection) {

			// 選択のは行を終える
			endRow = vtr.end_row;
		}

		return endRow;
	}

	/**
	 * ENTERキーでどのセルに移動するかを指定する.<br>
	 * JCTableEnum.TRAVERSE_DOWN<br>
	 * JCTableEnum.TRAVERSE_RIGHT<br>
	 * JCTableEnum.TRAVERSE_LEFT<br>
	 * JCTableEnum.TRAVERSE_UP
	 * 
	 * @param type 移動タイプ
	 */
	public void setEnterTraverseType(int type) {
		enterTraverseType = type;

		switch (type) {
			case JCTableEnum.TRAVERSE_RIGHT:
				enterReverseType = JCTableEnum.TRAVERSE_LEFT;

				break;

			case JCTableEnum.TRAVERSE_LEFT:
				enterReverseType = JCTableEnum.TRAVERSE_RIGHT;

				break;

			case JCTableEnum.TRAVERSE_UP:
				enterReverseType = JCTableEnum.TRAVERSE_DOWN;

				break;
		}
	}

	/**
	 * スクロールバーを最上行に移動する。
	 */
	public void setScrollTop() {
		JScrollBar bar = this.getVertSB();
		bar.setValue(0);
	}

	/**
	 * スクロールバーを最下行に移動する。
	 */
	public void setScrollUnder() {
		JScrollBar bar = this.getVertSB();
		bar.setValue(bar.getMaximum() - bar.getVisibleAmount());
	}

	/**
	 * スクロールバーを左に移動する。
	 */
	public void setScrollLeft() {
		JScrollBar bar = this.getHorizSB();
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
	 * Objectの比較クラス
	 */
	private class ObjectComparator implements Comparator {

		/**  */
		private static final int TYPE_DATE = 1;

		/**  */
		private static final int TYPE_NUMBER = 2;

		/**  */
		private int type = 0;

		/**
		 * コンストラクタ
		 * 
		 * @param index カラムIndex
		 */
		public ObjectComparator(int index) {

			if (dateSortIndexList.contains(index)) {
				type = TYPE_DATE;

			} else if (numberSortIndexList.contains(index)) {
				type = TYPE_NUMBER;
			}
		}

		/**
		 * 日付比較条件
		 * 
		 * @param o1 比較対象の最初のオブジェクト
		 * @param o2 比較対象の 2 番目のオブジェクト
		 * @return boolean 比較結果
		 */
		public int compare(Object o1, Object o2) {

			// 両方nullの場合が比較する必要なし。
			if (Util.isNullOrEmpty(o1) && Util.isNullOrEmpty(o2)) {
				return 0;
			}
			// nullが入力された場合、nullを持つ方が小さい
			else if (Util.isNullOrEmpty(o1) && !Util.isNullOrEmpty(o2)) {
				return -1;
			}
			// nullが入力された場合、nullを持つ方が小さい
			else if (!Util.isNullOrEmpty(o1) && Util.isNullOrEmpty(o2)) {
				return 1;
			}

			switch (type) {
				case TYPE_DATE: // 日付
					return dateCompare(o1, o2);

				case TYPE_NUMBER: // 数値
					return numberCompare(o1, o2);

				default:
					return objectCompare(o1, o2);
			}
		}

		/**
		 * Object比較 <br>
		 * 指定のObjectがString,BigDecimal,Date,int型でない場合は比較なし。
		 * 
		 * @param o1
		 * @param o2
		 * @return 比較結果
		 */
		private int objectCompare(Object o1, Object o2) {

			// Stringの場合
			if (o1.getClass() == String.class) {
				String o1String = (String) o1;
				String o2String = (String) o2;
				return o1String.compareTo(o2String);
			}

			// BigDecimalの場合
			if (o1.getClass() == BigDecimal.class) {
				BigDecimal o1Decimal = (BigDecimal) o1;
				BigDecimal o2Decimal = (BigDecimal) o2;
				return o1Decimal.compareTo(o2Decimal);
			}

			// Dateの場合
			if (o1.getClass() == Date.class) {
				Date o1Date = (Date) o1;
				Date o2Date = (Date) o2;
				return o1Date.compareTo(o2Date);
			}

			// intの場合
			if (o1.getClass() == Integer.class) {
				Integer o1Date = (Integer) o1;
				Integer o2Date = (Integer) o2;
				return o1Date.compareTo(o2Date);
			}

			return 0;
		}

		/**
		 * 日付比較
		 * 
		 * @param o1
		 * @param o2
		 * @return 比較結果
		 */
		private int dateCompare(Object o1, Object o2) {

			try {
				String o1String = (String) o1;
				String o2String = (String) o2;

				Date o1Date = DateUtil.toYMDDate(o1String);
				Date o2Date = DateUtil.toYMDDate(o2String);

				return o1Date.compareTo(o2Date);

			} catch (ParseException ex) {
				// 日付型変換でエラーが発生した場合、比較結果なし。
				return 0;
			}
		}

		/**
		 * 数字型をStringに変換したカラムの場合
		 * 
		 * @param o1
		 * @param o2
		 * @return 比較結果
		 */
		private int numberCompare(Object o1, Object o2) {
			String o1String = (String) o1;
			String o2String = (String) o2;

			// カンマを除去し、BigDecimalとして比較する。
			BigDecimal o1Number = new BigDecimal(o1String.replace(",", ""));
			BigDecimal o2Number = new BigDecimal(o2String.replace(",", ""));

			return o1Number.compareTo(o2Number);
		}
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public void setComponent(int arg0, int arg1, TTextField arg2) {

		arg2.setForeground(Color.BLACK);
		super.setComponent(arg0, arg1, arg2);

	}

	/**
	 * テーブル列サイズを自動設定するかどうか
	 * 
	 * @return true:自動設定
	 */
	public boolean isAutoColumnWidthSet() {
		return autoColumnWidthSet;
	}

	/**
	 * テーブル列サイズを自動設定するかどうか
	 * 
	 * @param autoColumnWidthSet true:自動設定
	 */
	public void setAutoColumnWidthSet(boolean autoColumnWidthSet) {
		this.autoColumnWidthSet = autoColumnWidthSet;
	}

}

package jp.co.ais.trans.common.gui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * JRadioButtonに、タブ順、メッセージIDインターフェイスを追加したRadioButton. TTableのセルにセットできるように追加
 */
public class TRadioButton extends JRadioButton implements TInterfaceLangMessageID, TInterfaceTabControl,
	TTableComponent {

	/** シリアルUID */
	private static final long serialVersionUID = -2066831109211249798L;

	private String langMessageID = null;

	private int tabControlNo = -1;

	private boolean isTabEnabled = true;

	/** 設定Index番号 */
	private int index = -1;

	/** 汎用INDEXキー */
	protected int rowIndex;

	/** Columnキー */
	protected int colIndex;

	/** ボタングループ */
	public List<Integer> groupList;

	/**
	 * コンストラクター
	 */
	public TRadioButton() {
		this("");

		groupList = new ArrayList<Integer>();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param index インデックス番号
	 */
	public TRadioButton(int index) {
		this("");

		this.index = index;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param langMessageID 単語ID
	 */
	public TRadioButton(String langMessageID) {
		this(langMessageID, false);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param langMessageID 単語ID
	 * @param selected 選択状態
	 */
	public TRadioButton(String langMessageID, boolean selected) {
		super(langMessageID, selected);

		this.langMessageID = langMessageID;

		// ファンクションキー用リスナ
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				handleKeyPressed(evt);
			}
		});

		setOpaque(false);
	}

	/**
	 * ファンクションキー処理.
	 * 
	 * @param evt キーイベント
	 */
	private void handleKeyPressed(KeyEvent evt) {

		TGuiUtil.transferFocusByEnterKey(this, evt);
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#setLangMessageID(String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.langMessageID = langMessageID;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.langMessageID;
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
	 * 設定されたIndexを返す.<br>
	 * 設定されていない場合は-1.
	 * 
	 * @return インデックス番号
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * インデックス番号を設定する.
	 * 
	 * @param index インデックス番号
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @param e
	 */
	public void addGroup(Enum e) {
		groupList.add(e.ordinal());
	}

	/**
	 * クリア
	 */
	public void clearGroup() {
		groupList.clear();
	}

	/**
	 * @return グループ
	 */
	public List<Integer> getGroup() {
		return groupList;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new RadioButtonRenderer();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {
		return new RadioButtonEditor(this);
	}

	/**
	 * @return 列番号
	 */
	public int getColIndex() {
		return colIndex;
	}

	/**
	 * 列番号
	 * 
	 * @param index
	 */
	public void setColIndex(int index) {
		colIndex = index;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getRowIndex()
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setRowIndex(int)
	 */
	public void setRowIndex(int index) {
		rowIndex = index;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getDefaultCellRendererHorizontalAlignment()
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#isTableCellEditor()
	 */
	public boolean isTableCellEditor() {
		return false;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setTableCellEditor(boolean)
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		//
	}

}

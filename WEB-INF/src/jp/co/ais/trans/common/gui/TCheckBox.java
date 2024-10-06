package jp.co.ais.trans.common.gui;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * JCheckBoxに、タブ順、メッセージIDインターフェイスを追加したCheckBox.
 */
public class TCheckBox extends JCheckBox implements TInterfaceLangMessageID, TInterfaceTabControl, TTableComponent {

	/**  */
	protected String langMessageID = null;

	/**  */
	protected int tabControlNo = -1;

	/**  */
	protected boolean isTabEnabled = true;

	/** 設定Index番号 */
	protected int index = -1;

	/** Enterでのフォーカス移動が可能か */
	protected boolean isEnterFocusable = true;

	/** TTableのindex */
	protected int modelIndex = -1;

	/** TableCellEditor利用か */
	protected boolean tableCellEditor = false;

	/**
	 * コンストラクタ.<br>
	 * 未選択状態で構築.
	 */
	public TCheckBox() {
		this("");
	}

	/**
	 * コンストラクタ.<br>
	 * 未選択状態で構築.
	 * 
	 * @param langMessageID 単語ID
	 */
	public TCheckBox(String langMessageID) {
		this(langMessageID, false);
	}

	/**
	 * コンストラクタ.<br>
	 * 未選択状態で構築.
	 * 
	 * @param index インデックス番号
	 */
	public TCheckBox(int index) {
		this(false, index);
	}

	/**
	 * コンストラクタ.<br>
	 * 未選択状態で構築.
	 * 
	 * @param selected 選択状態
	 * @param index インデックス番号
	 */
	public TCheckBox(boolean selected, int index) {
		this(selected);

		this.index = index;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param selected 選択状態
	 */
	public TCheckBox(boolean selected) {
		this("", selected);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param selected 選択状態
	 * @param langMessageID 単語ID
	 */
	public TCheckBox(String langMessageID, boolean selected) {
		super(langMessageID, selected);

		this.langMessageID = langMessageID;

		enableInputMethods(false);

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
	protected void handleKeyPressed(KeyEvent evt) {

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
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TCheckBoxRenderer(tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TCheckBoxEditor(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getRowIndex()
	 */
	public int getRowIndex() {
		return getIndex();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setRowIndex(int)
	 */
	public void setRowIndex(int rowIndex) {
		setIndex(rowIndex);

	}

	/**
	 * Enterでフォーカス移動可能かどうか
	 * 
	 * @return true:フォーカス移動可能
	 */
	public boolean isEnterFocusable() {
		return isEnterFocusable;
	}

	/**
	 * Enterでフォーカス移動可能かどうか
	 * 
	 * @param isEnterFocusable true:フォーカス移動可能
	 */
	public void setEnterFocusable(boolean isEnterFocusable) {
		this.isEnterFocusable = isEnterFocusable;
	}

	/**
	 * 水平方向のalign getter
	 * 
	 * @return 水平方向のalign
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	/**
	 * TTableのindex
	 * 
	 * @return TTableのindex
	 */
	public int getModelIndex() {
		return modelIndex;
	}

	/**
	 * TTableのindex
	 * 
	 * @param modelIndex TTableのindex
	 */
	public void setModelIndex(int modelIndex) {
		this.modelIndex = modelIndex;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#isTableCellEditor()
	 */
	public boolean isTableCellEditor() {
		return tableCellEditor;
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#setTableCellEditor(boolean)
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		this.tableCellEditor = tableCellEditor;
	}
}

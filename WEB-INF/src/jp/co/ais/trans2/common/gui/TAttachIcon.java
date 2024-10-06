package jp.co.ais.trans2.common.gui;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.gui.table.*;
import jp.co.ais.trans2.define.*;

/**
 * スプレッド用添付表示マーク
 */
public class TAttachIcon extends JLabel implements TTableComponent {

	/** 汎用INDEXキー */
	protected int rowIndex;

	/** Columnキー */
	protected int colIndex;

	/** TCallBackListener */
	protected List<TCallBackListener> callBackListenerList = null;

	/**
	 * コンストラクタ
	 */
	public TAttachIcon() {
		super();
		this.setIcon(new TImageButton(IconType.ATTACHMENT_COMPLETE).getIcon());
	}

	/**
	 * @return callBackListenerを戻します。
	 */
	public List<TCallBackListener> getCallBackListenerList() {
		return callBackListenerList;
	}

	/**
	 * @param callBackListenerList callBackListenerを設定します。
	 */
	public void setCallBackListenerList(List<TCallBackListener> callBackListenerList) {
		this.callBackListenerList = callBackListenerList;
	}

	/**
	 * @param callBackListener callBackListenerを追加する
	 */
	public void addCallBackListener(TCallBackListener callBackListener) {
		if (callBackListenerList == null) {
			callBackListenerList = new ArrayList<TCallBackListener>();
		}
		callBackListenerList.add(callBackListener);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TAttachIconCellRenderer(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	public TableCellEditor getCellEditor(TTable tbl) {
		return null;
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
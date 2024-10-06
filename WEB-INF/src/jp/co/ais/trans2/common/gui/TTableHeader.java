package jp.co.ais.trans2.common.gui;

import java.awt.event.*;

import javax.swing.table.*;

/**
 * TTable描画用ヘッダー
 */
public class TTableHeader extends JTableHeader {

	/** 基礎スプレッド */
	public BaseTable base;

	/**
	 * コンストラクター
	 * 
	 * @param base 基礎スプレッド
	 */
	@Deprecated
	public TTableHeader(BaseTable base) {
		this.base = base;
	}

	/**
	 * コンストラクター
	 * 
	 * @param cm
	 * @param base 基礎スプレッド
	 */
	public TTableHeader(TableColumnModel cm, BaseTable base) {
		super(cm);

		this.base = base;
	}

	/**
	 * @see javax.swing.table.JTableHeader#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent event) {

		if (base != null && base.adapter != null) {
			return base.adapter.getHeaderToolTipText(event);
		}

		return super.getToolTipText(event);
	}

}

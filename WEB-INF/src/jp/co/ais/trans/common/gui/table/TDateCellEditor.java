package jp.co.ais.trans.common.gui.table;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTable用 日付セルEditor(簡易版)
 */
public class TDateCellEditor extends TCellEditor {

	/** 年(YYYY) */
	public static final int TYPE_Y = TDatePlainDocument.TYPE_Y;

	/** 年月(YYYY/MM) */
	public static final int TYPE_YM = TDatePlainDocument.TYPE_YM;

	/** 年月日(YYYY/MM/DD) */
	public static final int TYPE_YMD = TDatePlainDocument.TYPE_YMD;

	/**
	 * コンストラクタ.<br>
	 * 日付タイプ YYYY/MM/DD
	 * 
	 * @param table 対象テーブル
	 */
	public TDateCellEditor(final TTable table) {
		this(table, TYPE_YMD);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param table 対象テーブル
	 * @param type
	 */
	public TDateCellEditor(final TTable table, int type) {
		super(table, 0);

		setHorizontalAlignment(CellStyleModel.CENTER);

		// 日付ドキュメント
		setDocument(new TDatePlainDocument(this, type));
	}

	/**
	 * 全角入力は無し(セットされても無効)
	 * 
	 * @param ime 指定意味なし
	 */
	@Override
	public void setImeMode(boolean ime) {
		enableInputMethods(false);
	}
}

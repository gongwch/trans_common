package jp.co.ais.trans.common.gui.table;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * TTable用 金額セルEditor
 */
public class TAmountCellEditor extends TCellEditor {

	/** 小数点桁数 */
	private int digit;

	/**
	 * コンストラクタ.<br>
	 * 最大入力数17、小数点桁数0、マイナス可
	 * 
	 * @param table 対象テーブル
	 */
	public TAmountCellEditor(final TTable table) {
		this(table, 17);
	}

	/**
	 * コンストラクタ.<br>
	 * 小数点桁数0、マイナス可
	 * 
	 * @param table 対象テーブル
	 * @param maxLength 最大入力数
	 */
	public TAmountCellEditor(final TTable table, int maxLength) {
		this(table, maxLength, 0);
	}

	/**
	 * コンストラクタ.<br>
	 * マイナス可
	 * 
	 * @param table 対象テーブル
	 * @param maxLength 最大入力数
	 * @param digit 小数点桁数
	 */
	public TAmountCellEditor(final TTable table, int maxLength, int digit) {
		this(table, maxLength, digit, false);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param table 対象テーブル
	 * @param maxLength 最大入力数
	 * @param digit 小数点桁数
	 * @param isPositive true:マイナス不可
	 */
	public TAmountCellEditor(final TTable table, int maxLength, int digit, boolean isPositive) {
		super(table, maxLength);

		this.digit = digit;

		setHorizontalAlignment(CellStyleModel.RIGHT);

		// スプッド数値入力チェック 入力12桁
		setDocument(new TNumericPlainDocument(this, maxLength, digit, isPositive));
	}

	/**
	 * @see com.klg.jclass.cell.editors.BaseCellEditor#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);

		if (isVisible) {
			setCellEditorText(getCellEditorText().replace(",", ""));
			selectAll();
		}
	}

	/**
	 * ロストフォーカス時処理
	 */
	@Override
	protected void verifyCommit() {

		String txt = getCellEditorText();

		if (!Util.isNumber(txt)) {
			setCellEditorText("");
		}

		super.verifyCommit();
	}

	/**
	 * 小数点桁数の設定
	 * 
	 * @param digit 小数点桁数
	 */
	public void setDecimalPoint(int digit) {
		this.digit = digit;

		TNumericPlainDocument doc = (TNumericPlainDocument) getDocument();
		doc.setDecimalPoint(digit);
	}

	/**
	 * 小数点桁数の取得
	 * 
	 * @return 小数点桁数
	 */
	public int getDecimalPoint() {
		return this.digit;
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

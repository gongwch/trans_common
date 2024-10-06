package jp.co.ais.trans2.common.ledger;

import java.math.*;
import java.util.*;

import com.klg.jclass.page.*;

import jp.co.ais.trans.common.util.*;

/**
 * 帳票のセルテーブル
 * 
 * @author AIS
 */
public class LedgerCellTable {

	/** 右 */
	public static int ALIGN_RIGHT = 0;

	/** 中央 */
	public static int ALIGN_CENTER = 1;

	/** 左 */
	public static int ALIGN_LEFT = 2;

	/** セルリスト */
	protected List<LedgerCell> cellList;

	/** アライン */
	protected int align;

	/**
	 * コンストラクタ.
	 */
	public LedgerCellTable() {
		cellList = new ArrayList<LedgerCell>();
		align = ALIGN_CENTER;
	}

	/**
	 * セルを追加
	 * 
	 * @param cell
	 */
	public void addCell(LedgerCell cell) {
		cellList.add(cell);
	}

	/**
	 * セルを追加(値ブランク)
	 * 
	 * @param style
	 * @param width
	 */
	public void addCell(JCTextStyle style, double width) {
		cellList.add(new LedgerCell(style, width, ""));
	}

	/**
	 * セルを追加
	 * 
	 * @param style
	 * @param width
	 * @param value
	 */
	public void addCell(JCTextStyle style, double width, String value) {
		cellList.add(new LedgerCell(style, width, value));
	}

	/**
	 * セルを追加(指定バイト数で切り捨て)
	 * 
	 * @param style
	 * @param width
	 * @param value
	 * @param bytes
	 */
	public void addCell(JCTextStyle style, double width, String value, int bytes) {
		cellList.add(new LedgerCell(style, width, StringUtil.leftBX(value, bytes)));
	}

	/**
	 * セルを追加
	 * 
	 * @param style
	 * @param width
	 * @param value
	 * @param decimalPoint
	 */
	public void addCell(JCTextStyle style, double width, BigDecimal value, int decimalPoint) {
		cellList.add(new LedgerCell(style, width, value, decimalPoint));
	}

	/**
	 * セルの要素数を返す
	 * 
	 * @return 要素数
	 */
	public int getSize() {
		return cellList.size();
	}

	/**
	 * セルのリストを返す
	 * 
	 * @return セルのリスト
	 */
	public List<LedgerCell> getCellList() {
		return cellList;
	}

	/**
	 * 位置
	 * 
	 * @return 位置
	 */
	public int getAlign() {
		return align;
	}

	/**
	 * 位置
	 * 
	 * @param align 位置
	 */
	public void setAlign(int align) {
		this.align = align;
	}

	/**
	 * 残り分を除いた幅トータルを返す
	 * 
	 * @return 幅トータル
	 */
	public double getTotalWidthWithoutRest() {
		double rt = 0.0;
		for (LedgerCell bean : cellList) {
			if (bean.getWidth() != LedgerCell.WIDTH_REST) {
				rt = rt + bean.getWidth();
			}
		}
		return rt;
	}
}

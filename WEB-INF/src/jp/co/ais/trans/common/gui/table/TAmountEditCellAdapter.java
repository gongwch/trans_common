package jp.co.ais.trans.common.gui.table;

import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 金額用EditCellListener実装.<br>
 * セル値を変更時（直接入力）、指定小数点桁数で数値フォーマットする.<br>
 * カラムIndexを指定している場合、小数点桁数を変更した場合に内部の値を全て再フォーマットする.
 */
public class TAmountEditCellAdapter extends TEditCellAdapter {

	/** 小数点桁数 */
	protected int digit;

	/**
	 * コンストラクタ.
	 * 
	 * @param table 対象スプレッド
	 * @param digit 小数点桁数
	 * @param column 対象カラムIndex
	 */
	public TAmountEditCellAdapter(TTable table, int digit, int... column) {
		super(table, column);

		this.digit = digit;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.table.TEditCellAdapter#after(int, int)
	 */
	@Override
	protected void after(int row, int column) {
		JCVectorDataSource ds = (JCVectorDataSource) table.getDataSource();

		if (ds.getCells().size() <= row) {
			return;
		}

		Vector rowVec = (Vector) ds.getCells().get(row);

		if (rowVec == null || rowVec.size() <= column) {
			return;
		}

		String num = (String) rowVec.get(column);

		if (!Util.isNullOrEmpty(num)) {
			// セル表示変更
			num = num.replace(",", "");
			ds.setCell(row, column, NumberFormatUtil.formatNumber(num, digit));
		}
	}

	/**
	 * 小数点の設定.<br>
	 * 対象カラムIndexが設定されている場合、該当セルの値を全て再フォーマットする.
	 * 
	 * @param digit 小数点桁数
	 */
	public void setDecimalPoint(int digit) {
		this.digit = digit;

		// 内部数値の再フォーマット(カラムIndex指定時のみ)
		JCVectorDataSource ds = (JCVectorDataSource) table.getDataSource();

		for (int row = 0; row < ds.getCells().size(); row++) {
			Vector rowVec = (Vector) ds.getCells().get(row);
			if (rowVec == null) continue;

			for (int i = 0; i < columns.size(); i++) {
				if (rowVec.size() <= columns.get(i)) continue;

				String num = (String) rowVec.get(columns.get(i));
				if (Util.isNullOrEmpty(num)) continue;

				// セル表示変更
				num = num.replace(",", "");
				ds.setCell(row, columns.get(i), NumberFormatUtil.formatNumber(num, digit));
			}
		}
	}
}
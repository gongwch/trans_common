package jp.co.ais.trans2.common.gui.table;

import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * 数値比較
 */
public class TNumberComparator implements Comparator {

	/**
	 * 比較<br>
	 * ひとつが数値以外の場合、文字列で比較
	 * 
	 * @return a ＜ b、-1<br>
	 *         a ＝ b、 0<br>
	 *         a ＞ b、 1
	 */
	@Override
	public int compare(Object a, Object b) {

		String a1 = Util.avoidNull(a).replaceAll(",", "").replaceAll("%", "").trim();
		String b1 = Util.avoidNull(b).replaceAll(",", "").replaceAll("%", "").trim();

		if (!Util.isNumber(a1) || !Util.isNumber(b1)) {
			return a1.compareTo(b1);
		}

		// 数値比較
		return DecimalUtil.toBigDecimal(a1).compareTo(DecimalUtil.toBigDecimal(b1));
	}

}

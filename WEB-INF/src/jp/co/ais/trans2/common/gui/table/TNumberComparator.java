package jp.co.ais.trans2.common.gui.table;

import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * ���l��r
 */
public class TNumberComparator implements Comparator {

	/**
	 * ��r<br>
	 * �ЂƂ����l�ȊO�̏ꍇ�A������Ŕ�r
	 * 
	 * @return a �� b�A-1<br>
	 *         a �� b�A 0<br>
	 *         a �� b�A 1
	 */
	@Override
	public int compare(Object a, Object b) {

		String a1 = Util.avoidNull(a).replaceAll(",", "").replaceAll("%", "").trim();
		String b1 = Util.avoidNull(b).replaceAll(",", "").replaceAll("%", "").trim();

		if (!Util.isNumber(a1) || !Util.isNumber(b1)) {
			return a1.compareTo(b1);
		}

		// ���l��r
		return DecimalUtil.toBigDecimal(a1).compareTo(DecimalUtil.toBigDecimal(b1));
	}

}

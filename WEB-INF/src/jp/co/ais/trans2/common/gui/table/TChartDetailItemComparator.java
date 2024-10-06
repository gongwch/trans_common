package jp.co.ais.trans2.common.gui.table;

import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * 明細アイテム並び順整備
 */
public class TChartDetailItemComparator implements Comparator<TChartDetailItem> {

	/**
	 * ソート
	 */
	public int compare(TChartDetailItem o1, TChartDetailItem o2) {

		Date from1 = o1.getFrom();
		Date from2 = o2.getFrom();

		if (from1 == null) {
			return -1;
		} else if (from2 == null) {
			return 1;
		} else {
			int result = from1.compareTo(from2);
			if (result != 0) {
				return result;
			}
		}

		Date to1 = o1.getTo();
		Date to2 = o2.getTo();

		if (to1 == null) {
			return -1;
		} else if (to2 == null) {
			return 1;
		} else {
			int result = to1.compareTo(to2);
			if (result != 0) {
				return result;
			}
		}

		String label1 = Util.avoidNull(o1.getLabel());
		String label2 = Util.avoidNull(o2.getLabel());

		return label1.compareTo(label2);
	}

}

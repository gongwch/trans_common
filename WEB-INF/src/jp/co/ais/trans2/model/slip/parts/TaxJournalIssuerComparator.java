package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * åoâﬂë[íuå≥édñÛÇÃSWK_DTLÇÃÉ\Å[Ég
 */
public class TaxJournalIssuerComparator implements Comparator<SWK_DTL> {

	/** true:ã‡äzç~èá */
	protected boolean isDesc;

	/** true:å≥çsî‘çÜèá */
	protected boolean isUseOriginalRowNo = false;

	/**
	 * @param desc true:ã‡äzç~èá
	 */
	public TaxJournalIssuerComparator(boolean desc) {
		this.isDesc = desc;
	}

	/**
	 * å≥çsî‘çÜèá(ç≈óDêÊ)Ç…Ç∑ÇÈ
	 */
	public void useOriginalRowNo() {
		this.isUseOriginalRowNo = true;
	}

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(SWK_DTL o1, SWK_DTL o2) {

		if (o1 == null) {
			return -1;
		}

		if (o2 == null) {
			return 1;
		}

		if (!isUseOriginalRowNo) {
			// É\Å[ÉgÇÇ©ÇØÇÈ

			// ã‡äzè∏èá
			BigDecimal num1 = DecimalUtil.toBigDecimalNVL(o1.getAddonInfo().getValue("ORI_SWK_KIN"));
			BigDecimal num2 = DecimalUtil.toBigDecimalNVL(o2.getAddonInfo().getValue("ORI_SWK_KIN"));

			int comp = num1.compareTo(num2);

			if (comp != 0) {
				if (isDesc) {
					// ã‡äzç~èáÇÃèÍçáÇÕîΩì]
					if (comp == 1) {
						return -1;
					} else {
						return 1;
					}
				} else {
					// ã‡äzè∏èáÇÃèÍçáÇÕÇªÇÃÇ‹Ç‹
					return comp;
				}
			}

			// çsî‘çÜè∏èá
			if (o1.getSWK_GYO_NO() < o2.getSWK_GYO_NO()) {
				return -1;
			} else if (o1.getSWK_GYO_NO() > o2.getSWK_GYO_NO()) {
				return 1;
			}

			return 0;

		} else {
			// å≥ÇÃçsî‘çÜèá

			int rowNo1 = o1.getAddonInfo() != null ? Util.avoidNullAsInt(o1.getAddonInfo().getValue("ORI_GYO_NO")) : 0;
			int rowNo2 = o2.getAddonInfo() != null ? Util.avoidNullAsInt(o2.getAddonInfo().getValue("ORI_GYO_NO")) : 0;

			if (rowNo1 != rowNo2) {
				if (rowNo1 < rowNo2) {
					return -1;
				} else {
					return 1;
				}
			}

			// çsî‘çÜè∏èá
			if (o1.getSWK_GYO_NO() < o2.getSWK_GYO_NO()) {
				return -1;
			} else if (o1.getSWK_GYO_NO() > o2.getSWK_GYO_NO()) {
				return 1;
			}

			return 0;
		}

	}

}

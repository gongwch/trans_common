package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 経過措置元仕訳のSWK_DTLのソート
 */
public class TaxJournalIssuerComparator implements Comparator<SWK_DTL> {

	/** true:金額降順 */
	protected boolean isDesc;

	/** true:元行番号順 */
	protected boolean isUseOriginalRowNo = false;

	/**
	 * @param desc true:金額降順
	 */
	public TaxJournalIssuerComparator(boolean desc) {
		this.isDesc = desc;
	}

	/**
	 * 元行番号順(最優先)にする
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
			// ソートをかける

			// 金額昇順
			BigDecimal num1 = DecimalUtil.toBigDecimalNVL(o1.getAddonInfo().getValue("ORI_SWK_KIN"));
			BigDecimal num2 = DecimalUtil.toBigDecimalNVL(o2.getAddonInfo().getValue("ORI_SWK_KIN"));

			int comp = num1.compareTo(num2);

			if (comp != 0) {
				if (isDesc) {
					// 金額降順の場合は反転
					if (comp == 1) {
						return -1;
					} else {
						return 1;
					}
				} else {
					// 金額昇順の場合はそのまま
					return comp;
				}
			}

			// 行番号昇順
			if (o1.getSWK_GYO_NO() < o2.getSWK_GYO_NO()) {
				return -1;
			} else if (o1.getSWK_GYO_NO() > o2.getSWK_GYO_NO()) {
				return 1;
			}

			return 0;

		} else {
			// 元の行番号順

			int rowNo1 = o1.getAddonInfo() != null ? Util.avoidNullAsInt(o1.getAddonInfo().getValue("ORI_GYO_NO")) : 0;
			int rowNo2 = o2.getAddonInfo() != null ? Util.avoidNullAsInt(o2.getAddonInfo().getValue("ORI_GYO_NO")) : 0;

			if (rowNo1 != rowNo2) {
				if (rowNo1 < rowNo2) {
					return -1;
				} else {
					return 1;
				}
			}

			// 行番号昇順
			if (o1.getSWK_GYO_NO() < o2.getSWK_GYO_NO()) {
				return -1;
			} else if (o1.getSWK_GYO_NO() > o2.getSWK_GYO_NO()) {
				return 1;
			}

			return 0;
		}

	}

}

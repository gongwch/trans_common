package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.bunkertype.*;
import jp.co.ais.trans2.model.code.*;

/**
 * ñæç◊î‰äróp(É\Å[ÉgÇÃÇΩÇﬂ)
 * 
 * @param <T>
 */
public class OPLoginMergeComparator<T> implements Comparator<T> {

	/**
	 * î‰är
	 * 
	 * @param o1
	 * @param o2
	 * @return 0:ìØÇ∂
	 */
	public int compare(T o1, T o2) {

		if (o1 == null) {
			return -1;
		}

		if (o2 == null) {
			return 1;
		}

		if (o1 instanceof OP_CODE_MST) {
			OP_CODE_MST bean1 = (OP_CODE_MST) o1;
			OP_CODE_MST bean2 = (OP_CODE_MST) o2;

			if (bean1.getLCL_KBN() < bean2.getLCL_KBN()) {
				return -1;
			} else if (bean1.getLCL_KBN() > bean2.getLCL_KBN()) {
				return 1;
			}

			if (bean1.getDISP_ODR() < bean2.getDISP_ODR()) {
				return -1;
			} else if (bean1.getDISP_ODR() > bean2.getDISP_ODR()) {
				return 1;
			}

			String codeDiv1 = Util.avoidNull(bean1.getCODE_DIV());
			String codeDiv2 = Util.avoidNull(bean2.getCODE_DIV());
			int i = codeDiv1.compareTo(codeDiv2);
			if (i != 0) {
				return i;
			}

			String code1 = Util.avoidNull(bean1.getCODE());
			String code2 = Util.avoidNull(bean2.getCODE());
			return code1.compareTo(code2);

		} else if (o1 instanceof CM_BNKR_TYPE_MST) {

			CM_BNKR_TYPE_MST bean1 = (CM_BNKR_TYPE_MST) o1;
			CM_BNKR_TYPE_MST bean2 = (CM_BNKR_TYPE_MST) o2;

			if (bean1.getDISP_ODR() < bean2.getDISP_ODR()) {
				return -1;
			} else if (bean1.getDISP_ODR() > bean2.getDISP_ODR()) {
				return 1;
			}

			String code1 = Util.avoidNull(bean1.getBNKR_TYPE_CODE());
			String code2 = Util.avoidNull(bean2.getBNKR_TYPE_CODE());
			return code1.compareTo(code2);
		}

		return Util.avoidNull(o1).compareTo(Util.avoidNull(o2));
	}
}

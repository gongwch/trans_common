package jp.co.ais.trans2.model.payment;

import java.util.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * x•¥ˆ—ƒ}ƒl[ƒWƒƒ[‚ÌÀ‘•ƒNƒ‰ƒX‚Å‚·B
 * 
 * @author AIS
 */
public class PaymentManagerImpl extends TModel implements PaymentManager {

	/**
	 * x•¥‰Â”\“ú‚©‚Ç‚¤‚©
	 * 
	 * @param paymentDateType x•¥‹æ•ª
	 * @param payDate x•¥“ú
	 * @return trueFx•¥‰Â”\“ú
	 */
	public boolean isPaymentDate(PaymentDateType paymentDateType, Date payDate) {

		int div = paymentDateType.equals(PaymentDateType.REGULAR) ? 0 : 1;
		return BizUtil.isPayBusinessDate(getCompanyCode(), payDate, div);
	}

	/**
	 * x•¥“ú‚Ìæ“¾
	 * 
	 * @param bean x•¥ğŒƒ}ƒXƒ^
	 * @param baseDate Šî€“ú
	 * @return x•¥“ú
	 */
	public Date getPaymentDate(PaymentSetting bean, Date baseDate) {

		Date paymentDate = null;
		PaymentDateType paymentDateType = bean.getPaymentDateType();
		String companyCode = bean.getCompanyCode();

		if (paymentDateType.equals(PaymentDateType.REGULAR)) {
			// ’èx•¥—\’è“úæ“¾
			paymentDate = bean.getRegularPaymentDate(baseDate);
		} else {
			// —Õx•¥—\’è“úæ“¾
			paymentDate = BizUtil.getNextBusinessDay(companyCode, baseDate, 1);
		}

		// x•¥•ûjƒ}ƒXƒ^l—¶
		if (!Util.isNullOrEmpty(paymentDate)) {
			paymentDate = BizUtil.getDateTransferToAccount(companyCode, paymentDate, paymentDateType
				.getPaymentDateType());
		}

		return paymentDate;
	}

}

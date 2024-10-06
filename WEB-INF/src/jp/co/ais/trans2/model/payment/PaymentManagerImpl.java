package jp.co.ais.trans2.model.payment;

import java.util.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * �x�������}�l�[�W���[�̎����N���X�ł��B
 * 
 * @author AIS
 */
public class PaymentManagerImpl extends TModel implements PaymentManager {

	/**
	 * �x���\�����ǂ���
	 * 
	 * @param paymentDateType �x���敪
	 * @param payDate �x����
	 * @return true�F�x���\��
	 */
	public boolean isPaymentDate(PaymentDateType paymentDateType, Date payDate) {

		int div = paymentDateType.equals(PaymentDateType.REGULAR) ? 0 : 1;
		return BizUtil.isPayBusinessDate(getCompanyCode(), payDate, div);
	}

	/**
	 * �x�����̎擾
	 * 
	 * @param bean �x�������}�X�^
	 * @param baseDate ���
	 * @return �x����
	 */
	public Date getPaymentDate(PaymentSetting bean, Date baseDate) {

		Date paymentDate = null;
		PaymentDateType paymentDateType = bean.getPaymentDateType();
		String companyCode = bean.getCompanyCode();

		if (paymentDateType.equals(PaymentDateType.REGULAR)) {
			// �莞�x���\����擾
			paymentDate = bean.getRegularPaymentDate(baseDate);
		} else {
			// �Վ��x���\����擾
			paymentDate = BizUtil.getNextBusinessDay(companyCode, baseDate, 1);
		}

		// �x�����j�}�X�^�l��
		if (!Util.isNullOrEmpty(paymentDate)) {
			paymentDate = BizUtil.getDateTransferToAccount(companyCode, paymentDate, paymentDateType
				.getPaymentDateType());
		}

		return paymentDate;
	}

}

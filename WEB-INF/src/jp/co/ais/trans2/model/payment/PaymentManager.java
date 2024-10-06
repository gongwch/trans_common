package jp.co.ais.trans2.model.payment;

import java.util.*;
import jp.co.ais.trans2.define.*;

/**
 * �x�������}�l�[�W���[
 * 
 * @author AIS
 */
public interface PaymentManager {

	/**
	 * �x���\�����ǂ���
	 * 
	 * @param paymentDateType �x���敪
	 * @param payDate �x����
	 * @return true�F�x���\��
	 */
	public boolean isPaymentDate(PaymentDateType paymentDateType, Date payDate);

	/**
	 * �x�����̎擾
	 * 
	 * @param bean �x�������}�X�^
	 * @param baseDate ���
	 * @return �x����
	 */
	public Date getPaymentDate(PaymentSetting bean, Date baseDate);

}

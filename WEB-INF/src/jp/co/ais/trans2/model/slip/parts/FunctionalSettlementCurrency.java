package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �@�\�ʉ݊֘A(���Z���[�g�p)
 */
public class FunctionalSettlementCurrency extends FunctionalCurrency {

	/**
	 * �@�\�ʉ݃��[�g�擾
	 * 
	 * @param date �Ώۓ�
	 * @param curCode �ʉ݃R�[�h
	 * @return ���[�g
	 * @throws TException
	 */
	@Override
	protected BigDecimal getRate(Date date, String curCode) throws TException {
		Map<String, BigDecimal> map = rateMap.get(curCode);

		if (map == null) {
			map = new TreeMap<String, BigDecimal>();
			rateMap.put(curCode, map);
		}

		BigDecimal rate = map.get(DateUtil.toYMDString(date));

		if (rate == null) {
			// �Ώۓ��t����ŐV�̌��Z���[�g�擾
			rateManager.setCompany(kcompany);
			rate = rateManager.getFunctionalSettlementRate(curCode, date);

			if (rate == null) {
				throw new TException(getMessage("I00159", kcompany.getCode(), curCode, DateUtil.toYMDString(date)));
				// ���[{0}] �ʉ�[{1}] ������[{2}] �̋@�\�ʉ݌��Z���[�g���ݒ肳��Ă��܂���B
			}

			map.put(DateUtil.toYMDString(date), rate);
		}

		return rate;
	}
}

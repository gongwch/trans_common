package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 機能通貨関連(決算レート用)
 */
public class FunctionalSettlementCurrency extends FunctionalCurrency {

	/**
	 * 機能通貨レート取得
	 * 
	 * @param date 対象日
	 * @param curCode 通貨コード
	 * @return レート
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
			// 対象日付から最新の決算レート取得
			rateManager.setCompany(kcompany);
			rate = rateManager.getFunctionalSettlementRate(curCode, date);

			if (rate == null) {
				throw new TException(getMessage("I00159", kcompany.getCode(), curCode, DateUtil.toYMDString(date)));
				// 会社[{0}] 通貨[{1}] 発生日[{2}] の機能通貨決算レートが設定されていません。
			}

			map.put(DateUtil.toYMDString(date), rate);
		}

		return rate;
	}
}

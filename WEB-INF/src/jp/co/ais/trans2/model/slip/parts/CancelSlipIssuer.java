package jp.co.ais.trans2.model.slip.parts;

import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 赤伝関連のビジネスロジックの実装クラスです。
 * 
 * @author AIS
 */
public class CancelSlipIssuer extends TModel {

	/**
	 * 取消仕訳にする(金額反転)
	 * 
	 * @param slip 伝票
	 * @throws Exception
	 */
	public void toCancelJournal(Slip slip) throws Exception {

		for (SWK_DTL detail : slip.getDetails()) {

			// 金額反転
			detail.setSWK_KIN(detail.getSWK_KIN().negate());
			detail.setSWK_IN_KIN(detail.getSWK_IN_KIN().negate());

			if (detail.getSWK_ZEI_KIN() != null) {
				detail.setSWK_ZEI_KIN(detail.getSWK_ZEI_KIN().negate());
			}
			if (detail.getSWK_IN_ZEI_KIN() != null) {
				detail.setSWK_IN_ZEI_KIN(detail.getSWK_IN_ZEI_KIN().negate());
			}
		}
	}

}

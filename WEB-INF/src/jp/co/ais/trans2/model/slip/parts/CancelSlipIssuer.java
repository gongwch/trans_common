package jp.co.ais.trans2.model.slip.parts;

import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �ԓ`�֘A�̃r�W�l�X���W�b�N�̎����N���X�ł��B
 * 
 * @author AIS
 */
public class CancelSlipIssuer extends TModel {

	/**
	 * ����d��ɂ���(���z���])
	 * 
	 * @param slip �`�[
	 * @throws Exception
	 */
	public void toCancelJournal(Slip slip) throws Exception {

		for (SWK_DTL detail : slip.getDetails()) {

			// ���z���]
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

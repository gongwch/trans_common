package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * 船舶動静チェッカーダミー実装クラス
 */
public class SlipFleetMovementCheckerDummyImpl extends TModel implements SlipFleetMovementChecker {

	/**
	 * @return エラーメッセージ
	 * @see jp.co.ais.trans2.model.slip.SlipFleetMovementChecker#checkFleetMovement(jp.co.ais.trans2.model.slip.Slip)
	 */
	public List<Message> checkFleetMovement(Slip slip) throws TException {
		// 動作無し
		return new ArrayList<Message>();
	}

}

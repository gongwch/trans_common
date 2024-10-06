package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * 船舶動静チェッカー
 */
public interface SlipFleetMovementChecker {

	/**
	 * 伝票内項目と船舶動静の整合性チェックを実施する
	 * 
	 * @param slip
	 * @return エラーリスト
	 * @throws TException
	 */
	public List<Message> checkFleetMovement(Slip slip) throws TException;

}

package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * �D�����Ã`�F�b�J�[�_�~�[�����N���X
 */
public class SlipFleetMovementCheckerDummyImpl extends TModel implements SlipFleetMovementChecker {

	/**
	 * @return �G���[���b�Z�[�W
	 * @see jp.co.ais.trans2.model.slip.SlipFleetMovementChecker#checkFleetMovement(jp.co.ais.trans2.model.slip.Slip)
	 */
	public List<Message> checkFleetMovement(Slip slip) throws TException {
		// ���얳��
		return new ArrayList<Message>();
	}

}

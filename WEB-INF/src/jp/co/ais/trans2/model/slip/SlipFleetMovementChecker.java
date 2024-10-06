package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * �D�����Ã`�F�b�J�[
 */
public interface SlipFleetMovementChecker {

	/**
	 * �`�[�����ڂƑD�����Â̐������`�F�b�N�����{����
	 * 
	 * @param slip
	 * @return �G���[���X�g
	 * @throws TException
	 */
	public List<Message> checkFleetMovement(Slip slip) throws TException;

}

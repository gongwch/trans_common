package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.TModel;
import jp.co.ais.trans2.model.security.*;

/**
 * �`�[�r���̎���
 * 
 * @author AIS
 */
public class SlipExclusiveManagerImpl extends TModel implements SlipExclusiveManager {

	/**
	 * �r����������
	 * 
	 * @param slipTypeList �`�[���
	 * @throws TException
	 */
	public void unLockAll(List<String> slipTypeList) throws TException {

		for (String slipType : slipTypeList) {
			// �`�[�r������
			SlipManager slipManager = (SlipManager) getComponent(SlipManager.class);
			slipManager.unlockAll(slipType);
		}

		// �c���s�̔r��������
		CodeExclusiveManager exclusiveManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		exclusiveManager.cancelExclude();
	}

	/**
	 * �p�^�[���r����������
	 * 
	 * @param slipTypeList �`�[���
	 */
	public void unLockPatternAll(List<String> slipTypeList) throws TException {

		for (String slipType : slipTypeList) {
			// �`�[�p�^�[���r������
			SlipManager slipManager = (SlipManager) getComponent(SlipManager.class);
			slipManager.unlockPatternAll(slipType);
		}
	}

}

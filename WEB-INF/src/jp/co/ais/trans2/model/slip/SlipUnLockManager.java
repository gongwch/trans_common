package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �`�[�r������𐧌䂷��N���X
 */
public interface SlipUnLockManager {

	/**
	 * �`�[����������.
	 * 
	 * @return List<SlipUnLock>
	 * @throws TException
	 */
	public List<SlipUnLock> get() throws TException;

	/**
	 * �`�[���X�V����.<br>
	 * 
	 * @param list �`�[�N���X
	 * @throws TException
	 */
	public void update(List<SlipUnLock> list) throws TException;

}

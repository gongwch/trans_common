package jp.co.ais.trans2.model.security;

import java.util.*;
import jp.co.ais.trans.common.except.*;

/**
 * �o�b�`�r������𐧌䂷��N���X
 */
public interface BatchUnLockManager {

	/**
	 * �o�b�`����������.
	 * 
	 * @return list �o�b�`�N���X
	 * @throws TException
	 */
	public List<BatchUnLock> get() throws TException;

	/**
	 * �o�b�`�r�����폜����.<br>
	 * 
	 * @param list �o�b�`�N���X
	 * @throws TException
	 */
	public void delete(List<BatchUnLock> list) throws TException;

}

package jp.co.ais.trans2.model.exclusive;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �r�����׌����𐧌䂷��N���X
 */
public interface ExclusiveDetailManager {

	/**
	 * �r�����ׂ���������.
	 * 
	 * @return �r�����׈ꗗ
	 * @throws TException
	 */
	public List<ExclusiveDetail> get() throws TException;

	/**
	 * �r�����׈ꗗ(�G�N�Z��)��Ԃ�
	 * 
	 * @return �r�����׈ꗗ
	 * @throws TException
	 */
	public byte[] getExcel() throws TException;

}

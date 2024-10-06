package jp.co.ais.trans2.common.gui;

import java.io.*;

/**
 * �ۑ��̕����\�ȃR���|�[�l���g
 * 
 * @author AIS
 */
public interface TStorable {

	/**
	 * �R���|�[�l���g����ۑ�����
	 * 
	 * @param key
	 * @param obj
	 */
	public void saveComponent(TStorableKey key, Serializable obj);

	/**
	 * �R���|�[�l���g���𕜌�����
	 * 
	 * @param key
	 */
	public void restoreComponent(TStorableKey key);

	/**
	 * �ۑ��̕����L�[��Ԃ�
	 * 
	 * @return �ۑ��̕����L�[
	 */
	public TStorableKey getKey();

}

package jp.co.ais.trans2.common.objsave;

import java.io.*;

/**
 * �I�u�W�F�N�g�ۑ��G�������g
 */
public class SaveElement implements Serializable {

	/** �\���L�[ */
	protected String key = null;

	/** �l */
	protected Object value = null;

	/**
	 * �\���L�[�̎擾
	 * 
	 * @return key �\���L�[
	 */
	public String getKey() {
		return key;
	}

	/**
	 * �\���L�[�̐ݒ�
	 * 
	 * @param key �\���L�[
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * �l�̎擾
	 * 
	 * @return value �l
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * �l�̐ݒ�
	 * 
	 * @param value �l
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return key;
	}
}

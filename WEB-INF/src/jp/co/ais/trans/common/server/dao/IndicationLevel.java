package jp.co.ais.trans.common.server.dao;

import java.util.*;

/**
 * �J�����x�����
 */
public class IndicationLevel {

	/** �K�w���x�� */
	private int kJL_LVL;

	/** ��ʕ���R�[�h */
	private String kJL_UP_DEP_CODE;

	/** ��ʕ��嗪�� */
	private String kJL_UP_DEP_NAME_S;

	/** ����R�[�h */
	private String kJL_DEP_CODE;

	/** ���嗪�� */
	private String kJL_DEP_NAME_S;

	/**
	 * �K�w���x��
	 * 
	 * @param kJL_LVL �K�w���x��
	 */
	public void setKJL_LVL(int kJL_LVL) {
		this.kJL_LVL = kJL_LVL;
	}

	/**
	 * ��ʕ���R�[�h
	 * 
	 * @param kJL_UP_DEP_CODE ��ʕ���R�[�h
	 */
	public void setKJL_UP_DEP_CODE(String kJL_UP_DEP_CODE) {
		this.kJL_UP_DEP_CODE = kJL_UP_DEP_CODE;
	}

	/**
	 * ��ʕ��嗪��
	 * 
	 * @param kJL_UP_DEP_NAME_S ��ʕ��嗪��
	 */
	public void setKJL_UP_DEP_NAME_S(String kJL_UP_DEP_NAME_S) {
		this.kJL_UP_DEP_NAME_S = kJL_UP_DEP_NAME_S;
	}

	/**
	 * ����R�[�h
	 * 
	 * @param kJL_DEP_CODE ����R�[�h
	 */
	public void setKJL_DEP_CODE(String kJL_DEP_CODE) {
		this.kJL_DEP_CODE = kJL_DEP_CODE;
	}

	/**
	 * ���嗪�̂��܂��B
	 * 
	 * @param kJL_DEP_NAME_S ���嗪��
	 */
	public void setKJL_DEP_NAME_S(String kJL_DEP_NAME_S) {
		this.kJL_DEP_NAME_S = kJL_DEP_NAME_S;
	}

	/**
	 * �K�w���x�����擾���܂��B
	 * 
	 * @return �K�w���x��
	 */
	public int getKJL_LVL() {
		return this.kJL_LVL;

	}

	/**
	 * ��ʕ���R�[�h���擾���܂��B
	 * 
	 * @return ��ʕ���R�[�h
	 */
	public String getKJL_UP_DEP_CODE() {
		return this.kJL_UP_DEP_CODE;

	}

	/**
	 * ��ʕ��嗪��
	 * 
	 * @return ��ʕ��嗪��
	 */
	public String getKJL_UP_DEP_NAME_S() {
		return kJL_UP_DEP_NAME_S;
	}

	/**
	 * ����R�[�h���擾���܂��B
	 * 
	 * @return ����R�[�h
	 */
	public String getKJL_DEP_CODE() {
		return this.kJL_DEP_CODE;

	}

	/**
	 * ���嗪�̂��擾���܂��B
	 * 
	 * @return ���嗪��
	 */
	public String getKJL_DEP_NAME_S() {
		return this.kJL_DEP_NAME_S;

	}

	/**
	 * ToString���\�b�h���쐬����B
	 * 
	 * @return �X�g�����O
	 */
	public String toString() {
		StringBuffer strBuff = new StringBuffer("");
		strBuff.append(kJL_LVL).append("/");
		strBuff.append(kJL_UP_DEP_CODE).append("/");
		strBuff.append(kJL_UP_DEP_NAME_S).append("/");
		strBuff.append(kJL_DEP_CODE).append("/");
		strBuff.append(kJL_DEP_NAME_S).append("/");

		return strBuff.toString();
	}

	/**
	 * �z��ϊ�
	 * 
	 * @return �z��
	 */
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kJL_LVL);
		list.add(kJL_UP_DEP_CODE);
		list.add(kJL_UP_DEP_NAME_S);
		list.add(kJL_DEP_CODE);
		list.add(kJL_DEP_NAME_S);

		return list;
	}

	/**
	 * �}�b�v�ϊ�
	 * 
	 * @return �}�b�v
	 */
	public Map<String, Object> toObjectMap() {

		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("KJL_LVL", kJL_LVL); // �K�w���x��
		map.put("KJL_UP_DEP_CODE", kJL_UP_DEP_CODE); // ��ʕ���R�[�h
		map.put("KJL_UP_DEP_NAME_S", kJL_UP_DEP_NAME_S); // ��ʕ���R�[�h
		map.put("KJL_DEP_CODE", kJL_DEP_CODE); // ����R�[�h
		map.put("KJL_DEP_NAME_S", kJL_DEP_NAME_S); // ���嗪��

		return map;
	}
}

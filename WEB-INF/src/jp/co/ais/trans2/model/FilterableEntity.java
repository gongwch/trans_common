package jp.co.ais.trans2.model;

import java.util.*;

/**
 * 
 */
public interface FilterableEntity {

	/**
	 * �R�[�h��߂��܂��B
	 * 
	 * @return �R�[�h
	 */
	public String getCode();

	/**
	 * �������̂�߂��܂��B
	 * 
	 * @return ��������
	 */
	public String getNamek();

	/**
	 * ���̂�߂��܂��B
	 * 
	 * @return ����
	 */
	public String getNames();

	/**
	 * �J�n����߂��܂��B
	 * 
	 * @return �J�n��
	 */
	public Date getDateFrom();

	/**
	 * �I������߂��܂��B
	 * 
	 * @return �I����
	 */
	public Date getDateTo();

}

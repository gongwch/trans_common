package jp.co.ais.trans2.model;

import java.util.*;

/**
 * �t�B���^�[�\��������
 */
public interface FilterableCondition {

	/**
	 * �R�[�h��߂��܂��B
	 * 
	 * @return �R�[�h
	 */
	public String getCode();

	/**
	 * �R�[�h�J�n��߂��܂��B
	 * 
	 * @return �R�[�h�J�n
	 */
	public String getCodeFrom();

	/**
	 * �R�[�h�I����߂��܂��B
	 * 
	 * @return �R�[�h�I��
	 */
	public String getCodeTo();

	/**
	 * �R�[�h�O����v��߂��܂��B
	 * 
	 * @return �R�[�h�O����v
	 */
	public String getCodeLike();

	/**
	 * ��������like��߂��܂��B
	 * 
	 * @return ��������like
	 */
	public String getNamekLike();

	/**
	 * ����like��߂��܂��B
	 * 
	 * @return ����like
	 */
	public String getNamesLike();

	/**
	 * �R�[�h���X�g��߂��܂��B
	 * 
	 * @return �R�[�h���X�g
	 */
	public List<String> getCodeList();

	/**
	 * �������߂��܂��B
	 * 
	 * @return �����
	 */
	public Date getValidTerm();
}

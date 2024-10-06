package jp.co.ais.trans2.model.country;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �����C���^�[�t�F�[�X�B
 */
public interface CountryDao {

	/**
	 * �w������ɊY�����鍑����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����鍑���
	 * @throws TException
	 */
	public List<Country> get(CountrySearchCondition condition) throws TException;

	/**
	 * ����o�^����B
	 * 
	 * @param customer ��
	 * @throws TException
	 */
	public void entry(Country customer) throws TException;

	/**
	 * �����C������B
	 * 
	 * @param customer ��
	 * @throws TException
	 */
	public void modify(Country customer) throws TException;

	/**
	 * �����폜����B
	 * 
	 * @param customer ��
	 * @throws TException
	 */
	public void delete(Country customer) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CountrySearchCondition condition) throws TException;
}

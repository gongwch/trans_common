package jp.co.ais.trans2.model.country;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �����}�l�[�W��
 */
public interface CountryManager {

	/**
	 * �w������ɊY�����鍑����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����鍑���
	 * @throws TException
	 */
	public List<Country> get(CountrySearchCondition condition) throws TException;

	/**
	 * ������o�^����B
	 * 
	 * @param entity
	 * @throws TException
	 */
	public void entry(Country entity) throws TException;

	/**
	 * �������C������B
	 * 
	 * @param entity
	 * @throws TException
	 */
	public void modify(Country entity) throws TException;

	/**
	 * �������폜����B
	 * 
	 * @param entity
	 * @throws TException
	 */
	public void delete(Country entity) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CountrySearchCondition condition) throws TException;

}

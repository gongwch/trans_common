package jp.co.ais.trans2.model.currency;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �ʉݏ��C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface CurrencyManager {

	/**
	 * �w������ɊY������ʉݏ���Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������ʉݏ��
	 * @throws TException
	 */
	public List<Currency> get(CurrencySearchCondition condition) throws TException;

	/**
	 * �ʉݏ���o�^����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(Currency bean) throws TException;

	/**
	 * �ʉݏ����C������B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(Currency bean) throws TException;

	/**
	 * �ʉݏ����폜����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(Currency bean) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CurrencySearchCondition condition) throws TException;

	/**
	 * �G�N�Z��
	 * 
	 * @param condition
	 * @return �ʉݏ��
	 * @throws TException
	 */
	public byte[] getExcel(CurrencySearchCondition condition) throws TException;

}

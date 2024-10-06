package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ��s�����C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface BankAccountManager {

	/**
	 * �w������ɊY�������s��������Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������s�������
	 * @throws TException
	 */
	public List<BankAccount> get(BankAccountSearchCondition condition) throws TException;

	/**
	 * �w������ɊY�������s��������Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������s�������
	 * @throws TException
	 */
	public List<BankAccount> getRef(BankAccountSearchCondition condition) throws TException;

	/**
	 * �w������ɊY�������s�����R�[�h��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������s�����R�[�h
	 * @throws TException
	 */
	public BankAccount getBankAccount(BankAccountSearchCondition condition) throws TException;

	/**
	 * ��s������o�^����B
	 * 
	 * @param bean ��s����
	 * @throws TException
	 */
	public void entry(BankAccount bean) throws TException;

	/**
	 * ��s�������C������B
	 * 
	 * @param bean ��s����
	 * @throws TException
	 */
	public void modify(BankAccount bean) throws TException;

	/**
	 * ��s�������폜����B
	 * 
	 * @param bean ��s����
	 * @throws TException
	 */
	public void delete(BankAccount bean) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcel(BankAccountSearchCondition condition) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(BankAccountSearchCondition condition) throws TException;

}

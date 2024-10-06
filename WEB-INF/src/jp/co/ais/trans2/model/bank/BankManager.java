package jp.co.ais.trans2.model.bank;

import java.util.*;
import jp.co.ais.trans.common.except.*;

/**
 * ��s���C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface BankManager {

	/**
	 * �w������ɊY�������s����Ԃ�
	 * �x�X���͊܂܂�Ȃ��B
	 * @param condition ��������
	 * @return �w������ɊY�������s���
	 * @throws TException
	 */
	public List<Bank> get(BankSearchCondition condition) throws TException;

	/**
	 * �w������ɊY�������s�E�x�X����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������s�E�x�X���
	 * @throws TException
	 */
	public List<Bank> get(BranchSearchCondition condition) throws TException;

	/**
	 * ��s��o�^����B
	 * 
	 * @param bean ��s
	 * @throws TException
	 */
	public void entry(Bank bean) throws TException;

	/**
	 * ��s���C������B
	 * 
	 * @param bean ��s
	 * @throws TException
	 */
	public void modify(Bank bean) throws TException;

	/**
	 * ��s���폜����B
	 * 
	 * @param bean ��s
	 * @throws TException
	 */
	public void delete(Bank bean) throws TException;
	
	/**
	 * �G�N�Z��
	 * 
	 * @param condition
	 * @return �G�N�Z���`���̋�s�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(BranchSearchCondition condition) throws TException;


}

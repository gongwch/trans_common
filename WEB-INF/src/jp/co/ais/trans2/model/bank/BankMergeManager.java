package jp.co.ais.trans2.model.bank;

import java.util.*;

import jp.co.ais.trans.common.except.*;



/**
 * ��s���p���̃C���^�[�t�F�[�X
 */
public interface BankMergeManager {

	/**
	 * ��s���p���̃f�[�^������
	 * @return ��s���p���̃f�[�^
	 * 
	 * @throws TException
	 */
	public List<BankMerge> get() throws TException;
	
	/**
	 * ��s���p���̃f�[�^��o�^����B
	 * 
	 * @param bankMe
	 * @param i 
	 * @throws TException
	 */
	public void entry(BankMerge bankMe, int i) throws TException;

	/**
	 * @param bankMe
	 * @return �ǉ��A�X�V�s��
	 * @throws TException
	 */
	public List<BankMerge> seachcount(List<BankMerge> bankMe) throws TException;
	
	/**
	 * �f�[�^�X�V�B
	 * 
	 * @param bankMe
	 * @throws TException
	 */
	public void updata(List<BankMerge> bankMe) throws TException;
	
}

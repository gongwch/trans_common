package jp.co.ais.trans2.model.balance;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �c���}�l�[�W��
 */
public interface BalanceManager {

	/**
	 * AP/AR�c���X�V
	 * 
	 * @param slip �`�[
	 */
	public void updateBalance(Slip slip);

	/**
	 * AP�c���Ƀ��b�N��������
	 * 
	 * @param list �Ώێc��(����)���X�g
	 * @throws TException �r�����s
	 */
	public void lockApBalance(List<BalanceCondition> list) throws TException;

	/**
	 * AR�c���Ƀ��b�N��������
	 * 
	 * @param list �Ώێc��(����)���X�g
	 * @throws TException �r�����s
	 */
	public void lockArBalance(List<BalanceCondition> list) throws TException;

	/**
	 * AP�c���̃��b�N����
	 * 
	 * @param condition �Ώێc��(����)
	 * @throws TException �r�����s
	 */
	public void unlockApBalance(BalanceCondition condition) throws TException;

	/**
	 * AP�c���̃��b�N�����i�����j
	 * 
	 * @param condition �Ώێc��(����)
	 * @throws TException �r�����s
	 */
	public void unlockApBalanceList(BalanceCondition condition) throws TException;

	/**
	 * AR�c���̃��b�N����
	 * 
	 * @param condition �Ώێc��(����)
	 * @throws TException �r�����s
	 */
	public void unlockArBalance(BalanceCondition condition) throws TException;

	/**
	 * AR�c���̃��b�N�����i�����j
	 * 
	 * @param condition �Ώێc��(����)
	 * @throws TException �r�����s
	 */
	public void unlockArBalanceList(BalanceCondition condition) throws TException;

	/**
	 * ���c���f�[�^�擾
	 * 
	 * @param conditon ����
	 * @return �c���f�[�^
	 */
	public List<AP_ZAN> getApBalance(BalanceCondition conditon);

	/**
	 * ���c���폜(�������܂�Ă��Ȃ���Ԃɖ߂�)
	 * 
	 * @param condition ����
	 */
	public void clearApBalance(BalanceCondition condition);

	/**
	 * ���c���X�V(�x���ς݂̏�Ԃɂ���)
	 * 
	 * @param list �c�����X�g
	 */
	public void updateApBalance(List<AP_ZAN> list);

	/**
	 * ���c���f�[�^�擾
	 * 
	 * @param conditon ����
	 * @return List �f�[�^
	 */
	public List<AR_ZAN> getSummaryArBalance(BalanceCondition conditon);

	/**
	 * ���c���f�[�^�擾
	 * 
	 * @param conditon ����
	 * @return List �f�[�^
	 */
	public List<AR_ZAN> getArBalance(BalanceCondition conditon);

	/**
	 * ���c���폜(�������݃f�[�^�̍폜)
	 * 
	 * @param condition ����
	 */
	public void clearArBalance(BalanceCondition condition);

	/**
	 * ���c���X�V(�������݃f�[�^�̒ǉ�)<br>
	 * swkDtl��Beans�œn���A�c�����X�V
	 * 
	 * @param list �c�����X�g
	 */
	public void insertArBalance(List<AR_ZAN> list);

	/**
	 * ���c���X�V(�������݃f�[�^�̒ǉ�)<br>
	 * swkDtl��Beans�œn���A�c�����X�V
	 * 
	 * @param list �c�����X�g
	 */
	public void insertApBalance(List<AP_ZAN> list);

	/**
	 * �o�^
	 * 
	 * @param bean �G���e�B�e�B
	 */
	public void insertByUpdateDate(AP_ZAN bean);

	/**
	 * �X�V
	 * 
	 * @param bean �G���e�B�e�B
	 * @return int
	 */
	public int updateByUpdateDate(AP_ZAN bean);

	/**
	 * �폜
	 * 
	 * @param bean �c�����X�g
	 * @return int
	 */
	public int deleteByUpdateDate(AP_ZAN bean);

	/**
	 * ���c�� 1������(���̓L�[�̂�)
	 * 
	 * @param condition ����
	 * @return ���c��
	 */
	public AP_ZAN getAP(BalanceCondition condition);

	/**
	 * ���c���c�� 1������(���̓L�[�̂�)
	 * 
	 * @param condition ����
	 * @return ���c��
	 */
	public AR_ZAN getAR(BalanceCondition condition);

	/**
	 * CM_FUND_DTL���̍폜
	 * 
	 * @param condition
	 * @param isAP
	 * @return List<String>
	 * @throws TException
	 */
	public List<String> deleteCmFundInfoAPAR(BalanceCondition condition, boolean isAP) throws TException;

	/**
	 * CM_FUND_DTL���̍X�V
	 * 
	 * @param kaiCode
	 * @param slipNoList
	 * @param isAP true:AP false:AR
	 * @throws TException
	 */
	public void updateCmFundDtl(String kaiCode, List<String> slipNoList, boolean isAP) throws TException;
}
package jp.co.ais.trans2.model.bs;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * B/S��������}�l�[�W��
 */
public interface BSEraseManager {

	/**
	 * �r�����|����
	 * 
	 * @param condition �Ώ�BS����(����)
	 * @throws TException �r�����s
	 */
	public void lock(BSEraseCondition condition) throws TException;

	/**
	 * �r������
	 * 
	 * @param condition �Ώ�BS����(����)
	 * @throws TException �r�����s
	 */
	public void unlock(BSEraseCondition condition) throws TException;

	/**
	 * B/S����f�[�^�̎擾
	 * 
	 * @param condition ����
	 * @return List �f�[�^
	 * @throws TException �擾���s
	 */
	public List<SWK_DTL> get(BSEraseCondition condition) throws TException;

	/**
	 * B/S��������̕���
	 * 
	 * @param condition ����(��ЃR�[�h, �`�[�ԍ�)
	 * @throws TRuntimeException �������s
	 */
	public void restoreBsBalance(SlipCondition condition);

	/**
	 * B/S��������̍X�V
	 * 
	 * @param slip �`�[
	 */
	public void updateBsBalance(Slip slip);

}
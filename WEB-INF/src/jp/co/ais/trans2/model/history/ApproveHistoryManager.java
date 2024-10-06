package jp.co.ais.trans2.model.history;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * ���F�����}�l�[�W��
 */
public interface ApproveHistoryManager {

	/**
	 * ���F�����f�[�^�̎擾
	 * 
	 * @param condition ����
	 * @return List �f�[�^
	 * @throws TException �擾���s
	 */
	public List<ApproveHistory> get(ApproveHistoryCondition condition) throws TException;

	/**
	 * ���F�R���g���[���̎擾
	 * 
	 * @param condition ����
	 * @return List �f�[�^
	 * @throws TException �擾���s
	 */
	public List<ApproveHistory> getCtl(ApproveHistoryCondition condition) throws TException;

	/**
	 * ���F�������̓o�^
	 * 
	 * @param bean ���F�������
	 * @throws TException
	 */
	public void entry(ApproveHistory bean) throws TException;

	/**
	 * ���F�����̍쐬
	 * 
	 * @param employee
	 * @param slip
	 * @param flag ���F�t���O(0:���F�A1:�L�����Z���A2:�۔F�A3:�o�^�A4:�C��)
	 * @return ���F����
	 */
	public ApproveHistory getApproveHistory(Slip slip, Employee employee, int flag);

	/**
	 * ���F�����̍쐬
	 * 
	 * @param employee
	 * @param dtl
	 * @param flag ���F�t���O(0:���F�A1:�L�����Z���A2:�۔F�A3:�o�^�A4:�C��)
	 * @return ���F����
	 */
	public ApproveHistory getApproveHistory(SlipDen dtl, Employee employee, int flag);

}
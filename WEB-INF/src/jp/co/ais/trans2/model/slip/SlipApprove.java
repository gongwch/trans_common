package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.model.employee.Employee;

/**
 * �`�[���F�C���^�[�t�F�[�X
 * @author AIS
 *
 */
public interface SlipApprove {

	/**
	 * �`�[�����F����
	 * @param dtl ���F����`�[
	 * @param employee ���F�Ј�
	 * @throws TException
	 */
	public void approveSlip(SlipDen dtl, Employee employee) throws TException;

	/**
	 * �`�[��۔F����
	 * @param dtl �۔F����`�[
	 * @throws TException
	 */
	public void denySlip(SlipDen dtl) throws TException;

	/**
	 * �`�[���F��������
	 * @param dtl
	 * @param isUseFieldApprove
	 * @throws TException
	 */
	public void cancelApproveSlip(SlipDen dtl, boolean isUseFieldApprove) throws TException;

	/**
	 * �`�[�����F�i���ꏳ�F�j����
	 * 
	 * @param dtl ���F����`�[
	 * @param employee ���F�Ј�
	 * @throws TException
	 */
	public void approveSlipForFieldState(SlipDen dtl, Employee employee) throws TException;

	/**
	 * �`�[��۔F�i����۔F�j����
	 * 
	 * @param dtl �۔F����`�[
	 * @throws TException
	 */
	public void denySlipForFieldState(SlipDen dtl) throws TException;

	/**
	 * �`�[���F�i���ꏳ�F�j��������
	 * 
	 * @param dtl
	 * @param isUseFieldApprove
	 * @throws TException
	 */
	public void cancelApproveSlipForFieldState(SlipDen dtl, boolean isUseFieldApprove) throws TException;
}

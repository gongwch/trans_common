package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.model.employee.Employee;

/**
 * 伝票承認インターフェース
 * @author AIS
 *
 */
public interface SlipApprove {

	/**
	 * 伝票を承認する
	 * @param dtl 承認する伝票
	 * @param employee 承認社員
	 * @throws TException
	 */
	public void approveSlip(SlipDen dtl, Employee employee) throws TException;

	/**
	 * 伝票を否認する
	 * @param dtl 否認する伝票
	 * @throws TException
	 */
	public void denySlip(SlipDen dtl) throws TException;

	/**
	 * 伝票承認を取り消す
	 * @param dtl
	 * @param isUseFieldApprove
	 * @throws TException
	 */
	public void cancelApproveSlip(SlipDen dtl, boolean isUseFieldApprove) throws TException;

	/**
	 * 伝票を承認（現場承認）する
	 * 
	 * @param dtl 承認する伝票
	 * @param employee 承認社員
	 * @throws TException
	 */
	public void approveSlipForFieldState(SlipDen dtl, Employee employee) throws TException;

	/**
	 * 伝票を否認（現場否認）する
	 * 
	 * @param dtl 否認する伝票
	 * @throws TException
	 */
	public void denySlipForFieldState(SlipDen dtl) throws TException;

	/**
	 * 伝票承認（現場承認）を取り消す
	 * 
	 * @param dtl
	 * @param isUseFieldApprove
	 * @throws TException
	 */
	public void cancelApproveSlipForFieldState(SlipDen dtl, boolean isUseFieldApprove) throws TException;
}

package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.model.company.Company;

/**
 * �������ߏ���
 * 
 * @author AIS
 */
public interface DailyClose {

	/**
	 * �����̒��߂��s��
	 * 
	 * @param date �`�[���t
	 * @param company ���
	 * @throws TException
	 */
	public void closeDaily(Date date, Company company) throws TException;

	/**
	 * �Ō�ɓ����X�V�������t��Ԃ�
	 * 
	 * @param company
	 * @return Date
	 * @throws TException
	 */
	public Date getLastDailyClosedDate(Company company) throws TException;

	/**
	 * �����X�V�f�[�^���擾����i������ДŁj
	 * 
	 * @return �����X�V�f�[�^
	 * @throws TException
	 */
	public List<DailyData> getDailyData() throws TException;

	/**
	 * �u REVALUATION_CTL�v��STATUS_KBN���X�V����
	 * 
	 * @param date
	 * @param companyCode
	 * @throws TException
	 */
	public void checkStatus(Date date, String companyCode) throws TException;

	/**
	 * �O�ݕ]���֓`�[�̑��݃`�F�b�N
	 * 
	 * @param companyCode
	 * @return PROC_YM
	 * @throws TException
	 */
	public List<Date> existSlip(String companyCode) throws TException;
}

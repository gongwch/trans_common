package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ���ߊǗ��C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface CloseManager {

	/**
	 * �w���Ђ̉�v���ߏ���Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return �w���Ђ̉�v���ߏ��
	 * @throws TException
	 */
	public FiscalPeriod getFiscalPeriod(String companyCode) throws TException;

	/**
	 * �w����t�̔N�x��Ԃ�
	 * 
	 * @param date ���t
	 * @param company ��Џ��(���񌎂��Z�b�g����Ă��邱�Ƃ��O��)
	 * @return �w����t�̔N�x
	 */
	public int getFiscalYear(Date date, Company company);

	/**
	 * �w����t�̌��x��Ԃ�
	 * 
	 * @param date ���t
	 * @param company ��Џ��(���񌎂��Z�b�g����Ă��邱�Ƃ��O��)
	 * @return �w����t�̌��x
	 */
	public int getFiscalMonth(Date date, Company company);

	/**
	 * �w����t�̔N�x�̊������Ԃ�
	 * 
	 * @param date ���t
	 * @param company ��Џ��(���񌎂��Z�b�g����Ă��邱�Ƃ��O��)
	 * @return �w����t�̔N�x�̊����
	 */
	public Date getDateBeginningOfPeriod(Date date, Company company);

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
	 * @return �Ō�ɓ����X�V�������t
	 * @throws TException
	 */
	public Date getLastDailyClosedDate(Company company) throws TException;

	/**
	 * �����X�V����B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v���ԏ��
	 * @return ��v���ԏ��
	 * @throws TWarningException
	 * @throws TException
	 */
	public FiscalPeriod closeMonthly(String companyCode, FiscalPeriod fp) throws TWarningException, TException;

	/**
	 * �����X�V�̎��������B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v���ԏ��
	 * @return ��v���ԏ��
	 * @throws TWarningException
	 * @throws TException
	 */
	public FiscalPeriod cancelMonthly(String companyCode, FiscalPeriod fp) throws TWarningException, TException;
	
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

	/****************************************************************************************************************
	 * �ȉ�������ДŃ��\�b�h
	 ****************************************************************************************************************/

	/**
	 * �����X�V����B
	 * 
	 * @param baseDate �����N����
	 * @param list �����X�V���
	 * @return ������f�[�^���X�g
	 * @throws TWarningException
	 * @throws TException
	 */
	public List<Message> closeAllDaily(Date baseDate, List<DailyData> list) throws TWarningException, TException;

	/**
	 * �����X�V�f�[�^���擾����
	 * 
	 * @return �����X�V�f�[�^
	 * @throws TException
	 */
	public List<DailyData> getDailyData() throws TException;

	/**
	 * �����X�V����B
	 * 
	 * @param list �����X�V���
	 * @return list ������f�[�^���X�g
	 * @throws TWarningException
	 * @throws TException
	 */
	public List<Message> closeAllMonthly(List<MonthData> list) throws TWarningException, TException;

	/**
	 * �����X�V�f�[�^���擾����
	 * 
	 * @param condition ��������
	 * @return �����X�V�f�[�^
	 * @throws TException
	 */
	public List<MonthData> getMonthData(MonthDataSearchCondition condition) throws TException;

	/**
	 * �����X�V�̎��������B
	 * 
	 * @param list �����X�V���
	 * @throws TWarningException
	 * @throws TException
	 */
	public void cancelAllMonthly(List<MonthData> list) throws TWarningException, TException;

}

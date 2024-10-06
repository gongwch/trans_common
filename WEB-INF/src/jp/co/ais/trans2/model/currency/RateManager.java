package jp.co.ais.trans2.model.currency;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �ʉ݃��[�g���C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface RateManager {

	/**
	 * �w������ɊY������ʉ݃��[�g����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������ʉݏ��
	 * @throws TException
	 */
	public List<Rate> get(RateSearchCondition condition) throws TException;

	/**
	 * �ʉ݃��[�g����o�^����B
	 * 
	 * @param rate
	 * @throws TException
	 */
	public void entry(Rate rate) throws TException;

	/**
	 * �ʉ݃��[�g����o�^����B
	 * 
	 * @param rate
	 * @throws TException
	 */
	public void entry(List<Rate> rate) throws TException;

	/**
	 * �ʉ݃��[�g�����C������B
	 * 
	 * @param rate
	 * @throws TException
	 */
	public void modify(Rate rate) throws TException;

	/**
	 * �ʉ݃��[�g�����폜����B
	 * 
	 * @param rate
	 * @throws TException
	 */
	public void delete(Rate rate) throws TException;

	/**
	 * ��Аݒ�
	 * 
	 * @param company ���
	 */
	public void setCompany(Company company);

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getRate(String currencyCode, Date date) throws TException;

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param currency �ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getRate(Currency currency, Date date) throws TException;

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param rate �ʉ݃��[�g
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getRate(Rate rate, Date date) throws TException;

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param companyCode
	 * @param currencyCode
	 * @param date
	 * @param tableName
	 * @return ret
	 * @throws TException
	 */
	public BigDecimal getRate(String companyCode, String currencyCode, Date date, String tableName) throws TException;

	/**
	 * �@�\�ʉ݃��[�g�擾
	 * 
	 * @param currency �ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(Currency currency, Date date) throws TException;

	/**
	 * �@�\�ʉ݃��[�g�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(String currencyCode, Date date) throws TException;

	/**
	 * �@�\�ʉ݃��[�g�擾
	 * 
	 * @param rate �ʉ݃��[�g
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalRate(Rate rate, Date date) throws TException;

	/**
	 * �ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(String currencyCode, Date date) throws TException;

	/**
	 * �ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currency �ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(Currency currency, Date date) throws TException;

	/**
	 * �ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param rate �ʉ݃��[�g
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getSettlementRate(Rate rate, Date date) throws TException;

	/**
	 * �@�\�ʉݒʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(String currencyCode, Date date) throws TException;

	/**
	 * �@�\�ʉݒʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param rate �ʉ݃��[�g
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(Rate rate, Date date) throws TException;

	/**
	 * �@�\�ʉݒʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currency �ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getFunctionalSettlementRate(Currency currency, Date date) throws TException;

	/**
	 * �ʉ�CROSS���[�g�擾
	 * 
	 * @param fromCurCode ���ʉ݃R�[�h
	 * @param toCurCode ��ʉ݃R�[�h
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String fromCurCode, String toCurCode, Date date) throws TException;

	/**
	 * �ʉ�CROSS���[�g�擾
	 * 
	 * @param fromCurrency ���ʉ�
	 * @param toCurrency ��ʉ�
	 * @param date ���
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getCrossRate(Currency fromCurrency, Currency toCurrency, Date date) throws TException;

	/**
	 * �ʉ�CROSS���[�g�擾(���[�g���x��C�ӎw�肷��)
	 * 
	 * @param fromCurCode ���ʉ݃R�[�h
	 * @param toCurCode ��ʉ݃R�[�h
	 * @param date ���
	 * @param digits �����_�ȉ�����
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String fromCurCode, String toCurCode, Date date, int digits) throws TException;

	/**
	 * �ʉ�CROSS���[�g�擾
	 * 
	 * @param companyCode
	 * @param fromCurCode ���ʉ݃R�[�h
	 * @param toCurCode ��ʉ݃R�[�h
	 * @param keyCurCode ��ʉ݃R�[�h
	 * @param date ���
	 * @param tableName
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getCrossRate(String companyCode, String fromCurCode, String toCurCode, String keyCurCode,
		Date date, String tableName) throws TException;

	/**
	 * �������ɑ΂���ʉ݃��[�g�擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @param occurDate    ������
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getRateByOccurDate(String currencyCode, Date occurDate) throws TException;

	/**
	 * �������ɑ΂���ʉ݃��[�g�i�������Z�j�擾
	 * 
	 * @param currencyCode �ʉ݃��[�g
	 * @param occurDate ������
	 * @return ���[�g
	 * @throws TException
	 */
	public BigDecimal getSettlementRateByOccurDate(String currencyCode, Date occurDate) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcel(RateSearchCondition condition) throws TException;
}
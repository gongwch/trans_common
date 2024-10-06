package jp.co.ais.trans2.model.close;

import jp.co.ais.trans.common.except.TException;

/**
 * ���ߊ֘A��Dao
 * @author AIS
 *
 */
public interface CloseDao {

	/**
	 * �w���Ђ̉�v���ߏ���Ԃ�
	 * @param companyCode ��ЃR�[�h
	 * @return �w���Ђ̉�v���ߏ��
	 */
	public FiscalPeriod getFiscalPeriod(String companyCode) throws TException;

	/**
	 * �w���Ђ̉�v���ߏ����X�V����
	 * @param companyCode ��ЃR�[�h
	 * @param fp ��v���ߏ��
	 * @throws TException
	 */
	public void update(String companyCode, FiscalPeriod fp) throws TException;

}

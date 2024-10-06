package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * Port�}�X�^Dao
 * 
 * @author AIS
 */
public interface PortDao {

	/**
	 * �w������ɊY������Port����Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Port���
	 * @throws TException
	 */
	public List<Port> getByCondition(PortSearchCondition condition) throws TException;

	/**
	 * �w��R�[�h�ɊY������Port����Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param voyageCode Port�R�[�h
	 * @return �w��R�[�h�ɊY������Port���
	 * @throws TException
	 */
	public Port get(String companyCode, String voyageCode) throws TException;

	/**
	 * Port��o�^����B
	 * 
	 * @param dto Port
	 * @throws TException
	 */
	public void insert(Port dto) throws TException;

	/**
	 * Port���C������B
	 * 
	 * @param dto Port
	 * @throws TException
	 */
	public void update(Port dto) throws TException;

	/**
	 * Port���폜����B
	 * 
	 * @param dto Port
	 * @throws TException
	 */
	public void delete(Port dto) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(PortSearchCondition condition) throws TException;

}

package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * Port�Ǘ��C���^�[�t�F�[�X
 * 
 * @author AIS
 */
public interface PortManager {

	/**
	 * �w������ɊY������Port����Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Port���
	 * @throws TException
	 */
	public List<Port> get(PortSearchCondition condition) throws TException;

	/**
	 * �w��R�[�h�ɕR�t��Port����Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param portCode Port�R�[�h
	 * @return �w��R�[�h�ɕR�t��Port���
	 * @throws TException
	 */
	public Port get(String companyCode, String portCode) throws TException;

	/**
	 * Port��o�^����B
	 * 
	 * @param port Port
	 * @throws TException
	 */
	public void entry(Port port) throws TException;

	/**
	 * Port���C������B
	 * 
	 * @param port Port
	 * @throws TException
	 */
	public void modify(Port port) throws TException;

	/**
	 * Port���폜����B
	 * 
	 * @param port Port
	 * @throws TException
	 */
	public void delete(Port port) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcel(PortSearchCondition condition) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(PortSearchCondition condition) throws TException;

}

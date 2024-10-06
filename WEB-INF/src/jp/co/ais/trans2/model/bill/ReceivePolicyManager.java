package jp.co.ais.trans2.model.bill;

import jp.co.ais.trans.common.except.*;

/**
 * �������j�}�X�^�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface ReceivePolicyManager {

	/**
	 * �Ώۉ�ЃR�[�h�̓������j�}�X�^����Ԃ�
	 * 
	 * @param companyCode �Ώۉ�ЃR�[�h
	 * @return �w������ɊY������������j�}�X�^���
	 * @throws TException
	 */
	public ReceivePolicy get(String companyCode) throws TException;

	/**
	 * ���O�C����ЃR�[�h�̓������j�}�X�^����Ԃ�
	 * 
	 * @return �������j�}�X�^���
	 * @throws TException
	 */
	public ReceivePolicy get() throws TException;
	
	/**
	 * �������j�}�X�^��o�^����B
	 * 
	 * @param bean �������j�}�X�^
	 * @throws TException
	 */
	public void entry(ReceivePolicy bean) throws TException;

	/**
	 * �������j�}�X�^���C������B
	 * 
	 * @param bean �������j�}�X�^
	 * @throws TException
	 */
	public void modify(ReceivePolicy bean) throws TException;

	/**
	 * �������j�}�X�^���폜����B
	 * 
	 * @param bean �������j�}�X�^
	 * @throws TException
	 */
	public void delete(ReceivePolicy bean) throws TException;

}

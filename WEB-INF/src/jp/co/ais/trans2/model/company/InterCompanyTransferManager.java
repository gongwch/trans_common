package jp.co.ais.trans2.model.company;

import java.util.List;
import jp.co.ais.trans.common.except.TException;

/**
 * �t�։�Ѓ}�l�[�W��
 * 
 * @author AIS
 */
public interface InterCompanyTransferManager {

	/**
	 * �w������ɊY�����镔�����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����镔����
	 * @throws TException
	 */
	public List<InterCompanyTransfer> get(InterCompanyTransferSearchCondition condition) throws TException;

	/**
	 * �f�[�^���ꗗ�ɓo�^����B
	 * 
	 * @param interCompanyTransfer
	 * @throws TException
	 */
	public void entry(InterCompanyTransfer interCompanyTransfer) throws TException;

	/**
	 * �ꗗ���̑I���s�̃f�[�^���C������B
	 * 
	 * @param interCompanyTransfer
	 * @throws TException
	 */
	public void modify(InterCompanyTransfer interCompanyTransfer) throws TException;

	/**
	 * �ꗗ���̑I���s�̃f�[�^���폜����B
	 * 
	 * @param interCompanyTransfer
	 * @throws TException
	 */
	public void delete(InterCompanyTransfer interCompanyTransfer) throws TException;

	/**
	 * @param transferCompanyCode
	 * @return ��Њԕt�փ}�X�^�̃f�[�^
	 * @throws TException
	 */
	public InterCompanyTransfer getOne(String companyCode, String transferCompanyCode) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcel(InterCompanyTransferSearchCondition condition) throws TException;

}

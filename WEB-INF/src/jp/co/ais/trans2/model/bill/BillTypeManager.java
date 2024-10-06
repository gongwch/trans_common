package jp.co.ais.trans2.model.bill;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �����敪�}�X�^�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface BillTypeManager {

	/**
	 * �w������ɊY�����鐿���敪�}�X�^����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����鐿���敪�}�X�^���
	 * @throws TException
	 */
	public List<BillType> get(BillTypeSearchCondition condition) throws TException;

	/**
	 * �����敪�}�X�^��o�^����B
	 * 
	 * @param bean �����敪�}�X�^
	 * @throws TException
	 */
	public void entry(BillType bean) throws TException;

	/**
	 * �����敪�}�X�^���폜����B
	 * 
	 * @param bean �����敪�}�X�^
	 * @throws TException
	 */
	public void delete(BillType bean) throws TException;

	/**
	 * �����敪�}�X�^��o�^����B
	 * 
	 * @param bean �����敪�}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public BillType entryMaster(BillType bean) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcel(BillTypeSearchCondition condition) throws TException;

}

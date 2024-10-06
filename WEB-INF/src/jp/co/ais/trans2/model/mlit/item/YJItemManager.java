package jp.co.ais.trans2.model.mlit.item;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �A�����їp�A�C�e���}�l�[�W��
 */
public interface YJItemManager {

	/**
	 * �A�����уA�C�e�����擾����
	 * 
	 * @param condition
	 * @return �A�����уA�C�e�����X�g
	 * @throws TException
	 */
	public List<YJItem> getItems(YJItemSearchCondition condition) throws TException;

	/**
	 * �A�����уA�C�e���}�X�^���폜����
	 * 
	 * @param bean �A�����уA�C�e���}�X�^
	 * @throws TException
	 */
	public void delete(YJItem bean) throws TException;

	/**
	 * �A�����уA�C�e���}�X�^��o�^����
	 * 
	 * @param bean �A�����уA�C�e���}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public YJItem entryMaster(YJItem bean) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcel(YJItemSearchCondition condition) throws TException;

	/**
	 * �A�����уT�u�A�C�e���}�X�^���폜����
	 * 
	 * @param bean �A�����уT�u�A�C�e���}�X�^
	 * @throws TException
	 */
	public void deleteSub(YJItem bean) throws TException;

	/**
	 * �A�����уT�u�A�C�e���}�X�^��o�^����
	 * 
	 * @param bean �A�����уT�u�A�C�e���}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public YJItem entrySubMaster(YJItem bean) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcelSub(YJItemSearchCondition condition) throws TException;

	/**
	 * �T�u�A�C�e�����擾����
	 * 
	 * @param condition
	 * @return �A�����уA�C�e�����X�g
	 * @throws TException
	 */
	public List<YJItem> getSubItems(YJItemSearchCondition condition) throws TException;

}

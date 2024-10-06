package jp.co.ais.trans2.model.mlit.region;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �A�����э��E�n��p�}�l�[�W��
 */
public interface YJRegionManager {

	/**
	 * �A�����э����擾����
	 * 
	 * @param condition
	 * @return �A�����э����X�g
	 * @throws TException
	 */
	public List<YJRegion> getRegion(YJRegionSearchCondition condition) throws TException;

	/**
	 * �A�����э��}�X�^���폜����
	 * 
	 * @param bean �A�����э��}�X�^
	 * @throws TException
	 */
	public void deleteRegion(YJRegion bean) throws TException;

	/**
	 * �A�����э��}�X�^��o�^����
	 * 
	 * @param bean �A�����э��}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public YJRegion entryRegionMaster(YJRegion bean) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcelRegion(YJRegionSearchCondition condition) throws TException;

	/**
	 * �A�����ђn����擾����
	 * 
	 * @param condition
	 * @return �A�����ђn�惊�X�g
	 * @throws TException
	 */
	public List<YJRegion> getCountry(YJRegionSearchCondition condition) throws TException;

	/**
	 * �A�����ђn��}�X�^���폜����
	 * 
	 * @param bean �A�����ђn��}�X�^
	 * @throws TException
	 */
	public void deleteCountry(YJRegion bean) throws TException;

	/**
	 * �A�����ђn��}�X�^��o�^����
	 * 
	 * @param bean �A�����ђn��}�X�^
	 * @return �o�^���ԂȂǐݒ��̃G���e�B�e�B
	 * @throws TException
	 */
	public YJRegion entryCountryMaster(YJRegion bean) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcelCountry(YJRegionSearchCondition condition) throws TException;
}

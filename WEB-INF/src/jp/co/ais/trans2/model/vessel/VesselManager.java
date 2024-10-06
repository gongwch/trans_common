package jp.co.ais.trans2.model.vessel;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * Vessel�Ǘ��C���^�[�t�F�[�X
 * 
 * @author AIS
 */
public interface VesselManager {

	/**
	 * �w������ɊY������Vessel����Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Vessel���
	 * @throws TException
	 */
	public List<Vessel> get(VesselSearchCondition condition) throws TException;

	/**
	 * �w������ɊY������Vessel����Ԃ��B(���ӁF�C���N�������g�T�[�`�p���̂�)
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Vessel���
	 * @throws TException
	 */
	public List<Vessel> getVesselForSearch(VesselSearchCondition condition) throws TException;

	/**
	 * �w��R�[�h�ɕR�t��Vessel����Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param beanCode Vessel�R�[�h
	 * @return �w��R�[�h�ɕR�t��Vessel���
	 * @throws TException
	 */
	public Vessel get(String companyCode, String beanCode) throws TException;

	/**
	 * �w������ɊY������Vessel����Ԃ� �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Vessel
	 * @throws TException
	 */
	public List<Vessel> getRef(VesselSearchCondition condition) throws TException;

	/**
	 * Register Vessel.
	 * 
	 * @param bean
	 * @param oldBean
	 * @return the {@link Vessel}
	 * @throws TException
	 */
	public Vessel entry(Vessel bean, Vessel oldBean) throws TException;

	/**
	 * Insert vessel.
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void insert(Vessel bean) throws TException;

	/**
	 * Vessel���C������B
	 * 
	 * @param bean Vessel
	 * @throws TException
	 */
	public void modify(Vessel bean) throws TException;

	/**
	 * Vessel���폜����B
	 * 
	 * @param bean Vessel
	 * @throws TException
	 */
	public void delete(Vessel bean) throws TException;

	/**
	 * �R���Ǘ��e�[�u�����g�����ǂ���
	 * 
	 * @return true:���݂���false:���݂��Ȃ�
	 * @throws TException
	 */
	public boolean isUseBM() throws TException;

	/**
	 * VESSEL�X�s�[�h�}�X�^�̎擾
	 * 
	 * @param condition
	 * @return VESSEL�X�s�[�h�}�X�^
	 * @throws TException
	 */
	public List<VesselSpeed> getSpeedList(VesselSearchCondition condition) throws TException;

	/**
	 * PORT�R������ʃ}�X�^�̎擾
	 * 
	 * @param condition
	 * @return PORT�R������ʃ}�X�^
	 * @throws TException
	 */
	public List<VesselPortCons> getPortConsList(VesselSearchCondition condition) throws TException;

	/**
	 * �C��R������ʃ}�X�^�̎擾
	 * 
	 * @param condition
	 * @return �C��R������ʃ}�X�^
	 * @throws TException
	 */
	public List<VesselSeaCons> getSeaConsList(VesselSearchCondition condition) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcel(VesselSearchCondition condition) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(VesselSearchCondition condition) throws TException;
}

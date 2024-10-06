package jp.co.ais.trans.master.dao;

import java.util.List;
import jp.co.ais.trans.master.entity.*;

/**
 * AP�w�b�_ + ���׌���Dao
 */
public interface ApSlipUnitDao {

	/**
	 * �f�[�^�߂�l
	 */
	public Class BEAN = ApSlipUnit.class;

	/**
	 * AP�d��W���[�i���w�b�_���擾����
	 * 
	 * @param param
	 * @return List<ApSlipUnit> ���A���w�b�_���̂ݎ擾
	 */
	public List<ApSlipUnit> getApHdr(ApSlipParameter param);

	/**
	 * AP�d��W���[�i���w�b�_ + ���׏����擾����
	 * 
	 * @param param
	 * @return List<ApSlipUnit>
	 */
	public List<ApSlipUnit> getApSlip(ApSlipParameter param);

	/**
	 * AP�p�^�[���W���[�i���w�b�_���擾����
	 * 
	 * @param param
	 * @return List<ApSlipUnit> ���A���w�b�_���̂ݎ擾
	 */
	public List<ApSlipUnit> getApPtnHdr(ApSlipParameter param);

	/**
	 * AP�p�^�[���W���[�i���w�b�_ + ���׏����擾����
	 * 
	 * @param param
	 * @return List<ApSlipUnit>
	 */
	public List<ApSlipUnit> getApPtnSlip(ApSlipParameter param);

	/**
	 * �Ј��ɕR�t��AP�d��W���[�i���w�b�_�̂��擾����
	 * 
	 * @param param
	 * @return List<ApSlipUnit>
	 */
	public List<ApSlipUnit> getApEmpData(ApSlipParameter param);

}

package jp.co.ais.trans2.model.voyage;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * Voyage�}�X�^Dao
 * 
 * @author AIS
 */
public interface VoyageDao {

	/**
	 * �w������ɊY������Voyage����Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Voyage���
	 * @throws TException
	 */
	public List<Voyage> getByCondition(VoyageSearchCondition condition) throws TException;

	/**
	 * �w��R�[�h�ɊY������Voyage����Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param voyageCode Voyage�R�[�h
	 * @return �w��R�[�h�ɊY������Voyage���
	 * @throws TException
	 */
	public Voyage get(String companyCode, String voyageCode) throws TException;

	/**
	 * Voyage��o�^����B
	 * 
	 * @param dto Voyage
	 * @throws TException
	 */
	public void insert(Voyage dto) throws TException;

	/**
	 * Voyage���C������B
	 * 
	 * @param dto Voyage
	 * @throws TException
	 */
	public void update(Voyage dto) throws TException;

	/**
	 * Voyage���폜����B
	 * 
	 * @param dto Voyage
	 * @throws TException
	 */
	public void delete(Voyage dto) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(VoyageSearchCondition condition) throws TException;

}

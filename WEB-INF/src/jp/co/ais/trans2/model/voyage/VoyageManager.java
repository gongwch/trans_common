package jp.co.ais.trans2.model.voyage;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * Voyage�Ǘ��C���^�[�t�F�[�X
 * 
 * @author AIS
 */
public interface VoyageManager {

	/**
	 * �w������ɊY������Voyage����Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������Voyage���
	 * @throws TException
	 */
	public List<Voyage> get(VoyageSearchCondition condition) throws TException;

	/**
	 * �w��R�[�h�ɕR�t��Voyage����Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param voyageCode Voyage�R�[�h
	 * @return �w��R�[�h�ɕR�t��Voyage���
	 * @throws TException
	 */
	public Voyage get(String companyCode, String voyageCode) throws TException;

	/**
	 * Voyage��o�^����B
	 * 
	 * @param voyage Voyage
	 * @throws TException
	 */
	public void entry(Voyage voyage) throws TException;

	/**
	 * Voyage���C������B
	 * 
	 * @param voyage Voyage
	 * @throws TException
	 */
	public void modify(Voyage voyage) throws TException;

	/**
	 * Voyage���폜����B
	 * 
	 * @param voyage Voyage
	 * @throws TException
	 */
	public void delete(Voyage voyage) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcel(VoyageSearchCondition condition) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(VoyageSearchCondition condition) throws TException;

}

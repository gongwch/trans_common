package jp.co.ais.trans2.model.save;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �I�u�W�F�N�g�ۑ��p
 * 
 * @author AIS
 */
public interface SaveManager {

	/**
	 * �폜����
	 * 
	 * @param base �폜�Ώ�
	 * @throws Exception
	 */
	public void deleteObject(OBJ_SAVE base) throws Exception;

	/**
	 * �I�u�W�F�N�g�ۑ�
	 * 
	 * @param list �I�u�W�F�N�g���X�g
	 * @throws Exception
	 */
	public void saveObject(List<OBJ_SAVE> list) throws Exception;

	/**
	 * �ۑ��I�u�W�F�N�g�Ăяo��
	 * 
	 * @param compCode ��ЃR�[�h
	 * @param userID ���[�UID
	 * @param prgID �v���O����ID
	 * @param seq �V�[�P���XNo.
	 * @return �I�u�W�F�N�g���X�g
	 * @throws Exception
	 */
	public List<OBJ_SAVE> readObject(String compCode, String userID, String prgID, Integer seq) throws Exception;

	/**
	 * �}�j���A���̎擾
	 * 
	 * @return �}�j���A���̃��X�g
	 * @throws TException
	 */
	public List<MANUAL_ATTACH> getManual() throws TException;

	/**
	 * �}�j���A���o�^
	 * 
	 * @param list
	 * @throws TException
	 * @return �}�j���A���̃��X�g
	 */
	public List<MANUAL_ATTACH> entryManual(List<MANUAL_ATTACH> list) throws TException;

	/**
	 * �}�j���A���폜
	 * 
	 * @param list
	 * @throws TException
	 */
	public void deleteManual(List<MANUAL_ATTACH> list) throws TException;

	/**
	 * �}�j���A���Ɖ�
	 * 
	 * @param bean
	 * @return �}�j���A��
	 * @throws TException
	 */
	public byte[] drilldownManual(MANUAL_ATTACH bean) throws TException;

}

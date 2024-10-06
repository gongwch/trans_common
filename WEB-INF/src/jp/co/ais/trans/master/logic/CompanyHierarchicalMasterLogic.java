package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ��ЊK�w�}�X�^�r�W�l�X���W�b�N
 */
public interface CompanyHierarchicalMasterLogic {

	/**
	 * �f�[�^�o�^
	 * 
	 * @param dto
	 * @throws TException
	 */
	public void insert(EVK_MST dto) throws TException;

	/**
	 * �f�[�^�폜
	 * 
	 * @param dto
	 * @throws TException
	 */
	public void delete(EVK_MST dto) throws TException;

	/**
	 * �f�[�^�폜
	 * 
	 * @param kaiCode
	 * @param sskCode
	 */
	public void delete(String kaiCode, String sskCode);

	/**
	 * �f�[�^�ҏW
	 * 
	 * @param list
	 * @throws TException
	 */
	public void update(List list) throws TException;

	/**
	 * @param kaiCode
	 * @param sskCode
	 * @return List<Object
	 */
	public List<Object> getWithOutCom(String kaiCode, String sskCode);

	/**
	 * @param kaiCode
	 * @param sskCode
	 * @return List<Object
	 */
	public List<Object> getComLvl(String kaiCode, String sskCode);

	/**
	 * ��ЃR���g���[���f�[�^�擾
	 * 
	 * @param string ��ЃR�[�h
	 * @return ��ЃR���g���[���f�[�^
	 */
	public ENV_MST getENV_MST(String string);

	/**
	 * @param kaiCode
	 * @return List<Object>
	 */
	public List<Object> getOrgCode(String kaiCode);

	/**
	 * @param kaiCode
	 * @return List<Object>
	 */
	public List<Object> getENVListNewSsk(String kaiCode);

	/**
	 * @param kaiCode
	 * @param sskCode
	 * @throws TException
	 */
	public void getSskCode(String kaiCode, String sskCode) throws TException;
}

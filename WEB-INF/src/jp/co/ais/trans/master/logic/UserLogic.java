package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ���[�U�}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface UserLogic extends CommonLogic {

	/**
	 * vertifier�Ή� ���[�U�o�^�m�F�ƕ�����K��
	 * 
	 * @param keys
	 * @return List
	 */
	public List getREFItems(Map keys);

	/**
	 * ��ЃR�[�h�Ń��[�U���X�g���K��
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return List<USR_MST> ���[�Ulist
	 */
	public List<USR_MST> searchUsrList(String kaiCode);

	/**
	 * �X�V���[�U�}�X�^
	 * 
	 * @param entity ���[�U�}�X�^
	 * @param oldKaiCode old��ЃR�[�h
	 */
	public void updateUsrMst(USR_MST entity,String oldKaiCode);

}

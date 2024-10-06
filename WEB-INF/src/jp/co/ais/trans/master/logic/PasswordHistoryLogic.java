package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �p�X���[�h�����r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface PasswordHistoryLogic {

	/**
	 * �����f�[�^����.<br>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrCode ���[�U�R�[�h
	 * @return �����f�[�^
	 */
	public List<PWD_HST_TBL> findPassword(String kaiCode, String usrCode);

	/**
	 * ����V�K
	 * 
	 * @param pwdDto
	 */
	public void insert(PWD_HST_TBL pwdDto);

}

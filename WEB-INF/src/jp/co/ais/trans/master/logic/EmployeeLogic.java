package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

/**
 * �Ј��}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface EmployeeLogic extends CommonLogic {

	/**
	 * �Ј����擾
	 * 
	 * @param keys ���[�U�R�[�h
	 * @return List ���[�U���
	 */
	public abstract List getREFItems(Map keys);

	/**
	 * �_�C�A���O�ɕ\������郆�[�U���X�g�擾
	 * 
	 * @param kaiCode
	 * @param empCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param user
	 * @param depCode
	 * @param beginCode
	 * @param endCode
	 * @return ���[�U���X�g
	 */
	public abstract List<Object> refDailog(String kaiCode, String empCode, String sName, String kName,
		Timestamp termBasisDate, String user, String depCode, String beginCode, String endCode);

}

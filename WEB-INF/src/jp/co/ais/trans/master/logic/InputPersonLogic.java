package jp.co.ais.trans.master.logic;

import java.util.*;

/**
 * ���͎҃}�X�^�r�W�l�X���W�b�N
 */
public interface InputPersonLogic {

	/**
	 * �Ј��}�X�^����(���͎�)
	 * 
	 * @param strKaiCode ��ЃR�[
	 * @param strEmpCode �Ј��R�[�h
	 * @param strEmpName �Ј�����
	 * @param strEmpName_K �Ј�������
	 * @param strSlipDate �`�[���t
	 * @param strDepCodeKbn
	 * @param strDepCode
	 * @return �Ј��}�X�^���X�g
	 */
	public abstract List searchEmpMstData(String strKaiCode, String strEmpCode, String strEmpName, String strEmpName_K,
		String strSlipDate, String strDepCodeKbn, String strDepCode);
}

package jp.co.ais.trans.master.logic.impl;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ���͎҃}�X�^�r�W�l�X���W�b�N����
 */
public class InputPersonLogicImpl implements InputPersonLogic {

	/** �Ј��}�X�^�ꗗ */
	private EMP_MSTDao empMstDao = null;

	/**
	 * �Ј��}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param empMstDao �Ј��}�X�^�ꗗ
	 */
	public void setEmpMstDao(EMP_MSTDao empMstDao) {
		this.empMstDao = empMstDao;
	}

	/**
	 * �Ј��}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[
	 * @param strEmpCode �Ј��R�[�h
	 * @param strEmpName �Ј�����
	 * @param strEmpName_K �Ј�������
	 * @param strSlipDate �`�[���t
	 * @return �Ј��}�X�^���X�g
	 */
	public List searchEmpMstData(String strKaiCode, String strEmpCode, String strEmpName, String strEmpName_K,
		String strSlipDate, String strDepCodeKbn, String strDepCode) {
		if ("1".equals(strDepCodeKbn)) {
			// �f�[�^���擾����B
			List lstResult = empMstDao.searchEmpMstDataByUser(strKaiCode, strEmpCode, strEmpName, strEmpName_K,
				strDepCode);
			return lstResult;
		}
		Date slipDate = null;
		try {
			// �`�[���t
			if (!"".equals(strSlipDate)) {
				// �`�[���t
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}
		} catch (ParseException e) {
			// ignore
		}
		// �f�[�^���擾����B
		List lstResult = empMstDao.searchEmpMstData(strKaiCode, strEmpCode, strEmpName, strEmpName_K, slipDate, "", "");

		return lstResult;
	}
}

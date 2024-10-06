package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ����}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface DepartmentLogic extends CommonLogic {

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	void setCompanyCode(String companyCode);

	/**
	 * ����}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[�h
	 * @param strDepCode ����R�[�h
	 * @param strDepName ���嗪��
	 * @param strDepName_K ���匟����
	 * @param strSlipDate �`�[���t
	 * @param strDpkSsk �g�D�R�[�h
	 * @param strBmnKbn �z������(0:�܂� 1:�܂܂Ȃ�)
	 * @param strUpBmnCode ��ʕ��庰��
	 * @param strDpkLvl �K�w����
	 * @param strBaseDpkLvl �����K�w����
	 * @param strBaseBmnCode �������庰��
	 * @return ����}�X�^���X�g
	 */
	List searchBmnMstData(String strKaiCode, String strDepCode, String strDepName, String strDepName_K,
		String strSlipDate, String strDpkSsk, String strBmnKbn, String strUpBmnCode, String strDpkLvl,
		String strBaseDpkLvl, String strBaseBmnCode);

	/**
	 * �_�C�A���O�ɕ\�������f�[�^�̎擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param depCode ����R�[�h
	 * @param sName ����
	 * @param kName ��������
	 * @param termBasisDate �L������
	 * @param organization �g�D
	 * @param level ��ʃ��x��
	 * @param upperDepart ��ʕ���
	 * @param sumDepart �W�v����
	 * @param inputDepart ���͕���
	 * @param beginCode �J�n�R�[�h
	 * @param endCode �I���R�[�h
	 * @return �����񃊃X�g
	 */
	List<Object> refSearchDepartment(String kaiCode, String depCode, String sName, String kName,
		Timestamp termBasisDate, String organization, int level, String upperDepart, boolean sumDepart,
		boolean inputDepart, String beginCode, String endCode);

	/**
	 * ����f�[�^����
	 * 
	 * @param deptCode ����R�[�h
	 * @param beginCode �J�n�R�[�h
	 * @param endCode �I���R�[�h
	 * @return ����f�[�^
	 */
	BMN_MST findDepartment(String deptCode, String beginCode, String endCode);

	/**
	 * �w�蕔��̒����̕��僊�X�g�������K������B
	 * 
	 * @param comCode
	 * @param dpkCode
	 * @param DpkLvl
	 * @param inDepCode
	 * @param UpperCode
	 * @return ���ʕ��僊�X�g
	 */
	List<BMN_MST> getBmnList(String comCode, String dpkCode, int DpkLvl, String inDepCode, String UpperCode);
}

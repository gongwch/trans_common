package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �����}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface CustomerLogic extends CommonLogic {

	/**
	 * �����̃{�^���t�B�[���h�̏������ɑΉ�����悤�ɐݒ�B
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param siire
	 * @param tokui
	 * @param beginCode
	 * @param endCode
	 * @return ������񃊃X�g
	 */
	List<Object> refSearchCustomer(String kaiCode, String triCode, String sName, String kName, Timestamp termBasisDate,
		boolean siire, boolean tokui, String beginCode, String endCode);

	/**
	 * �����f�[�^����
	 * 
	 * @param customerCode �����R�[�h
	 * @param beginCode �J�n�R�[�h
	 * @param endCode �I���R�[�h
	 * @return �����f�[�^
	 */
	TRI_MST findCustomer(String customerCode, String beginCode, String endCode);

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	void setCompanyCode(String companyCode);

	/**
	 * �˗����̍X�V
	 * 
	 * @param dto
	 */
	public void updateIraiName(TRI_MST dto);
}

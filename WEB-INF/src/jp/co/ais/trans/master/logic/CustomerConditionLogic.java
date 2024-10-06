package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.text.*;
import java.util.*;

/**
 * �x���������}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface CustomerConditionLogic extends CommonLogic {

	/**
	 * �_�C�A���O�ɕ\����������惊�X�g�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param triCode �����R�[�h
	 * @param tjkCode ���������R�[�h
	 * @param termBasisDate �L������
	 * @return ����惊�X�g
	 */
	public abstract List<Object> refDailog(String kaiCode, String triCode, String tjkCode, Timestamp termBasisDate);

	/**
	 * ���������R�[�h�R�[�h�ɂ��A�������擾
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param tjkCode
	 * @return �������
	 */
	public abstract List<Object> searchName(String kaiCode, String triCode, String tjkCode);

	/**
	 * �f�t�H���g�̌��������擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param triCode �����R�[�h
	 * @param curCode �ʉ݃R�[�h
	 * @return map ���������R�[�h�E�x����s��
	 */

	public abstract Map<String, Object> getDefaultPaymentCondition(String kaiCode, String triCode, String curCode);

	/**
	 * �x����������͎��� �x���擝�������擾
	 * 
	 * @param param
	 * @return map ����擝�����
	 * @throws ParseException
	 */
	public abstract Map<String, Object> getPaymentConditionInfo(Map<String, String> param) throws ParseException;

	/**
	 * ����������擾
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param triSjCode
	 * @return TRI_SJ_MST
	 */
	public abstract Object findOneInfo(String kaiCode, String triCode, String triSjCode);
}

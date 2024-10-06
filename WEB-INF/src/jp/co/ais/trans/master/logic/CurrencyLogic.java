package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

/**
 * �ʉ݃}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface CurrencyLogic extends CommonLogic {

	/**
	 * ���������ɂ��ʉ݃��X�g�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param curCode �ʉ݃R�[�h
	 * @param sName ����
	 * @param kName ��������
	 * @param termBasisDate �L������
	 * @param beginCode
	 * @param endCode
	 * @return �ʉ݃��X�g
	 */
	public abstract List<Object> refDailogCurrency(String kaiCode, String curCode, String sName, String kName,
		Timestamp termBasisDate, String beginCode, String endCode);

	/**
	 * �ʉ݃R�[�h�ɂ��A�ʉݏ��擾
	 * 
	 * @param keys
	 * @return �ʉݏ��
	 */
	public abstract List getREFItems(Map keys);

}

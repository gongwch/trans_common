package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �x�������@�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface PaymentMethodLogic extends CommonLogic {

	/**
	 * �x�������@�R�[�h�Ŏx�������擾
	 * 
	 * @param keys
	 * @return �x�������@���
	 */
	public abstract AP_HOH_MST getREFItems(Map keys);

	/**
	 * �x�������@���X�g�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param hohCode �x�������@�R�[�h
	 * @param sName ����
	 * @param kName ��������
	 * @param termBasisDate �L������
	 * @return �x�������@���X�g
	 */
	public abstract List<Object> refDailog(String kaiCode, String hohCode, String sName, String kName,
		Timestamp termBasisDate);

}

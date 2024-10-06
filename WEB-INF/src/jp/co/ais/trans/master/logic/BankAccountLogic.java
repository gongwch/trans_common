package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

/**
 * ��s�����}�X�^�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface BankAccountLogic extends CommonLogic {

	/**
	 * �����R�[�h�ŋ�s�������擾
	 * 
	 * @param keys
	 * @return ��s�������
	 */
	public abstract List getREFItems(Map keys);

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions
	 */
	public abstract List findREF(Map conditions);

	/**
	 * ��s��������
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param code ��s�R�[�h
	 * @param nameS ����
	 * @param nameK ��������
	 * @param outkbn �ЊO�敪
	 * @param empkbn �Ј��敪
	 * @param termBasisDate �L������
	 * @return ��s�������X�g
	 */
	public abstract List<Object> searchReservationBankAccount(String kaiCode, String code, String nameS, String nameK,
		boolean outkbn, boolean empkbn, Timestamp termBasisDate);

	/**
	 * �f�t�H���g�̓�����s�������擾
	 * 
	 * @param kaiCode
	 * @return Map<String, Object>
	 */
	public abstract Map<String, Object> getDefaultReceivedAccount(String kaiCode);

}

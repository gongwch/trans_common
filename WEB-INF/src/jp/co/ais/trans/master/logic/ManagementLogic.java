package jp.co.ais.trans.master.logic;

import java.util.*;

/**
 * 
 */
public interface ManagementLogic {

	/**
	 * �Ǘ����擾<BR>
	 * �Ǘ����擾�B
	 * 
	 * @param conditonMap �p�����[�^�[Map
	 * @return triName �Ǘ����
	 */
	public Map getKnrName(Map conditonMap);

	/**
	 * �Ǘ�1�}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[
	 * @param strKnrCode �Ј��R�[�h
	 * @param strKnrName �Ј�����
	 * @param strKnrName_K �Ј�������
	 * @param strSlipDate �`�[���t
	 * @param args ���̑��p�����[�^
	 * @return �Ǘ�1�}�X�^���X�g
	 */
	public List searchKnr1MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * �Ǘ�2�}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[
	 * @param strKnrCode �Ј��R�[�h
	 * @param strKnrName �Ј�����
	 * @param strKnrName_K �Ј�������
	 * @param strSlipDate �`�[���t
	 * @param args ���̑��p�����[�^
	 * @return �Ǘ�2�}�X�^���X�g
	 */
	public List searchKnr2MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * �Ǘ�3�}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[
	 * @param strKnrCode �Ј��R�[�h
	 * @param strKnrName �Ј�����
	 * @param strKnrName_K �Ј�������
	 * @param strSlipDate �`�[���t
	 * @param args ���̑��p�����[�^
	 * @return �Ǘ�3�}�X�^���X�g
	 */
	public List searchKnr3MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * �Ǘ�4�}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[
	 * @param strKnrCode �Ј��R�[�h
	 * @param strKnrName �Ј�����
	 * @param strKnrName_K �Ј�������
	 * @param strSlipDate �`�[���t
	 * @param args ���̑��p�����[�^
	 * @return �Ǘ�4�}�X�^���X�g
	 */
	public List searchKnr4MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * �Ǘ�5�}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[
	 * @param strKnrCode �Ј��R�[�h
	 * @param strKnrName �Ј�����
	 * @param strKnrName_K �Ј�������
	 * @param strSlipDate �`�[���t
	 * @param args ���̑��p�����[�^
	 * @return �Ǘ�5�}�X�^���X�g
	 */
	public List searchKnr5MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

	/**
	 * �Ǘ�6�}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[
	 * @param strKnrCode �Ј��R�[�h
	 * @param strKnrName �Ј�����
	 * @param strKnrName_K �Ј�������
	 * @param strSlipDate �`�[���t
	 * @param args ���̑��p�����[�^
	 * @return �Ǘ�6�}�X�^���X�g
	 */
	public List searchKnr6MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args);

}

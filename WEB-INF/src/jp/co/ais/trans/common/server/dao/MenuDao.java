package jp.co.ais.trans.common.server.dao;

import java.util.*;

/**
 * ���j���[�f�[�^�擾�pDao
 */
public interface MenuDao {

	/** �Ώ�Bean */
	public Class BEAN = MenuBean.class;

	/** ARGS */
	public static final String getPRG_MSTByKaicodeSyscodePrcken_ARGS = "kaiCode, sysCode, prcKen, targetDate, langPrefix, userCode";

	/**
	 * �f�[�^�擾�i�w��j
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param sysCode �V�X�e���R�[�h
	 * @param prcKen �v���O�����敪
	 * @param targetDate ���t
	 * @param langPrefix ����v���t�B�b�N�X
	 * @param userCode ���[�U�[�R�[�h
	 * @return ���j���[�f�[�^
	 */
	public List getPRG_MSTByKaicodeSyscodePrcken(String kaiCode, String sysCode, int prcKen, Date targetDate,
		String langPrefix, String userCode);

	/** ARGS */
	public static final String getPRG_MSTByKaicodeSyscodePrcken2_ARGS = "kaiCode, sysCode, prcKen, targetDate, langPrefix, userCode";

	/**
	 * �f�[�^�擾�i�w��j ���ꏳ�F���j���[���O
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param sysCode �V�X�e���R�[�h
	 * @param prcKen �v���O�����敪
	 * @param targetDate ���t
	 * @param langPrefix ����v���t�B�b�N�X
	 * @param userCode ���[�U�[�R�[�h
	 * @return ���j���[�f�[�^
	 */
	public List getPRG_MSTByKaicodeSyscodePrcken2(String kaiCode, String sysCode, int prcKen, Date targetDate,
		String langPrefix, String userCode);

	/** getCount() SQL */
	public static final String getCount_SQL = "SELECT count(*) FROM PRG_MST/*$langPrefix*/";

	/**
	 * �f�[�^�J�E���g
	 * 
	 * @param langPrefix ����R�[�h
	 * @return �f�[�^��
	 */
	public int getCount(String langPrefix);
}

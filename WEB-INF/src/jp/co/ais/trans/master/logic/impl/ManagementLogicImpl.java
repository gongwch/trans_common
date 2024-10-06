package jp.co.ais.trans.master.logic.impl;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �Ǘ��}�X�^
 */
public class ManagementLogicImpl implements ManagementLogic {

	/** �Ǘ�1�}�X�^�ꗗ */
	private KNR1_MSTDao KNR1_MSTDao = null;

	/** �Ǘ�2�}�X�^�ꗗ */
	private KNR2_MSTDao KNR2_MSTDao = null;

	/** �Ǘ�3�}�X�^�ꗗ */
	private KNR3_MSTDao KNR3_MSTDao = null;

	/** �Ǘ�4�}�X�^�ꗗ */
	private KNR4_MSTDao KNR4_MSTDao = null;

	/** �Ǘ�5�}�X�^�ꗗ */
	private KNR5_MSTDao KNR5_MSTDao = null;

	/** �Ǘ�6�}�X�^�ꗗ */
	private KNR6_MSTDao KNR6_MSTDao = null;

	/**
	 * �Ǘ����擾<BR>
	 * �Ǘ����擾�B
	 * 
	 * @param conditonMap �p�����[�^�[Map
	 * @return triName �Ǘ����
	 */
	public Map<String, Object> getKnrName(Map conditonMap) {

		Map<String, Object> map = new HashMap<String, Object>();

		String strManagementFlag = (String) conditonMap.get("managementFlag"); // �t���O
		String strKaiCode = (String) conditonMap.get("kaiCode"); // ��ЃR�[�h
		String strSlipDate = (String) conditonMap.get("slipDate"); // �`�[���t
		String strManagementCode = (String) conditonMap.get("managementCode"); // �Ǘ�1�R�[�h
		String beginCode = (String) conditonMap.get("beginCode"); // �J�n�R�[�h
		String endCode = (String) conditonMap.get("endCode"); // �I���R�[�h
		String strKnrName = "";
		String strStrDate = "";
		String strEndDate = "";

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

		// �Ǘ�1���擾
		if ("1".equals(strManagementFlag)) {
			KNR1_MST knr1Mst = KNR1_MSTDao.searchKnrNameS1(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr1Mst != null) {
				strKnrName = knr1Mst.getKNR_NAME_S_1();
				strStrDate = Util.avoidNull(knr1Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr1Mst.getEND_DATE());
			}
		}
		// �Ǘ�2���擾
		else if ("2".equals(strManagementFlag)) {
			KNR2_MST knr2Mst = KNR2_MSTDao.searchKnrNameS2(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr2Mst != null) {
				strKnrName = knr2Mst.getKNR_NAME_S_2();
				strStrDate = Util.avoidNull(knr2Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr2Mst.getEND_DATE());
			}
		}
		// �Ǘ�3���擾
		else if ("3".equals(strManagementFlag)) {
			KNR3_MST knr3Mst = KNR3_MSTDao.searchKnrNameS3(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr3Mst != null) {
				strKnrName = knr3Mst.getKNR_NAME_S_3();
				strStrDate = Util.avoidNull(knr3Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr3Mst.getEND_DATE());
			}
		}
		// �Ǘ�4���擾
		else if ("4".equals(strManagementFlag)) {
			KNR4_MST knr4Mst = KNR4_MSTDao.searchKnrNameS4(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr4Mst != null) {
				strKnrName = knr4Mst.getKNR_NAME_S_4();
				strStrDate = Util.avoidNull(knr4Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr4Mst.getEND_DATE());
			}
		}
		// �Ǘ�5���擾
		else if ("5".equals(strManagementFlag)) {
			KNR5_MST knr5Mst = KNR5_MSTDao.searchKnrNameS5(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr5Mst != null) {
				strKnrName = knr5Mst.getKNR_NAME_S_5();
				strStrDate = Util.avoidNull(knr5Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr5Mst.getEND_DATE());
			}
		}
		// �Ǘ�6���擾
		else if ("6".equals(strManagementFlag)) {
			KNR6_MST knr6Mst = KNR6_MSTDao.searchKnrNameS6(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr6Mst != null) {
				strKnrName = knr6Mst.getKNR_NAME_S_6();
				strStrDate = Util.avoidNull(knr6Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr6Mst.getEND_DATE());
			}
		}
		map.put("strKnrName", strKnrName); // ����
		map.put("strStrDate", strStrDate); // �J�n���t
		map.put("strEndDate", strEndDate); // �I�����t

		return map;
	}

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
		String strSlipDate, String... args) {

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
		List lstResult = KNR1_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

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
		String strSlipDate, String... args) {

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
		List lstResult = KNR2_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

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
		String strSlipDate, String... args) {

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
		List lstResult = KNR3_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

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
		String strSlipDate, String... args) {

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
		List lstResult = KNR4_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

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
		String strSlipDate, String... args) {

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
		List lstResult = KNR5_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

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
		String strSlipDate, String... args) {

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
		List lstResult = KNR6_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

	/**
	 * �Ǘ�1�}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param KNR1_MSTDao �Ǘ�1�}�X�^�ꗗ
	 */
	public void setKNR1_MSTDao(KNR1_MSTDao KNR1_MSTDao) {
		this.KNR1_MSTDao = KNR1_MSTDao;
	}

	/**
	 * �Ǘ�2�}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param KNR2_MSTDao �Ǘ�2�}�X�^�ꗗ
	 */
	public void setKNR2_MSTDao(KNR2_MSTDao KNR2_MSTDao) {
		this.KNR2_MSTDao = KNR2_MSTDao;
	}

	/**
	 * �Ǘ�3�}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param KNR3_MSTDao �Ǘ�3�}�X�^�ꗗ
	 */
	public void setKNR3_MSTDao(KNR3_MSTDao KNR3_MSTDao) {
		this.KNR3_MSTDao = KNR3_MSTDao;
	}

	/**
	 * �Ǘ�4�}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param KNR4_MSTDao �Ǘ�4�}�X�^�ꗗ
	 */
	public void setKNR4_MSTDao(KNR4_MSTDao KNR4_MSTDao) {
		this.KNR4_MSTDao = KNR4_MSTDao;
	}

	/**
	 * �Ǘ�5�}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param KNR5_MSTDao �Ǘ�5�}�X�^�ꗗ
	 */
	public void setKNR5_MSTDao(KNR5_MSTDao KNR5_MSTDao) {
		this.KNR5_MSTDao = KNR5_MSTDao;
	}

	/**
	 * �Ǘ�6�}�X�^�ꗗ�ݒ肵�܂��B
	 * 
	 * @param KNR6_MSTDao �Ǘ�6�}�X�^�ꗗ
	 */
	public void setKNR6_MSTDao(KNR6_MSTDao KNR6_MSTDao) {
		this.KNR6_MSTDao = KNR6_MSTDao;
	}

}

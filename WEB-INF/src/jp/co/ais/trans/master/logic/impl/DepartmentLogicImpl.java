package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ����}�X�^����
 */
public class DepartmentLogicImpl extends CommonLogicBaseImpl implements DepartmentLogic {

	/** ����}�X�^Dao */
	protected BMN_MSTDao dao;

	/** ����}�X�^���� */
	protected BMN_MST entity;

	/** ��ЃR�[�h */
	private String companyCode;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao BMN_MSTDao
	 */
	public DepartmentLogicImpl(BMN_MSTDao dao) {
		// ����}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * @see jp.co.ais.trans.master.logic.DepartmentLogic#setCompanyCode(java.lang.String)
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * BMN_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity BMN_MST
	 */
	public void setEntity(BMN_MST entity) {
		// ����}�X�^���̂�Ԃ�
		this.entity = entity;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �J�n�R�[�h�̎擾
		String beginDepCode = (String) conditions.get("beginDepCode");
		// �I���R�[�h�̎擾
		String endDepCode = (String) conditions.get("endDepCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginDepCode) && Util.isNullOrEmpty(endDepCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllBMN_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginDepCode)) {
			// ���ʂ�Ԃ�
			return dao.getBmnInfoTo(kaiCode, endDepCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endDepCode)) {
			// ���ʂ�Ԃ�
			return dao.getBmnInfoFrom(kaiCode, beginDepCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getBmnInfo(kaiCode, beginDepCode, endDepCode);
		}
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ����R�[�h�̎擾
		String depCode = (String) keys.get("depCode");
		// ���ʂ�Ԃ�
		return dao.getBMN_MSTByKaicodeDepcode(kaiCode, depCode);
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		// �R�[�h
		String code = (String) conditions.get("code");
		// ����
		String name_S = (String) conditions.get("name_S");
		// ��������
		String name_K = (String) conditions.get("name_K");

		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");

		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");

		String kind = (String) conditions.get("kind");

		// ���ʂ̎擾
		List list = null;

		if ("DepartmentForMG0340".equals(kind)) {
			String dpkSsk = (String) conditions.get("dpkSsk");
			int level = Integer.parseInt((String) conditions.get("level"));
			String parentDepCode = (String) conditions.get("parentDepCode");

			list = dao.getDepartmentsForMG0340(kaiCode, dpkSsk, level, parentDepCode, code, name_S, name_K);
		} else if ("UpperDepartmentForMG0340".equals(kind)) {
			String dpkSsk = (String) conditions.get("dpkSsk");
			int level = Integer.parseInt((String) conditions.get("level"));

			list = dao.getUpperDepartmentsForMG0340(kaiCode, dpkSsk, level - 1, code, name_S, name_K);
		} else {
			list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, kind);
		}

		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			BMN_MST e = (BMN_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// ����R�[�h�̐ݒ�
			e2.setCode(e.getDEP_CODE());
			// ���嗪�̂̐ݒ�
			e2.setName_S(e.getDEP_NAME_S());
			// ���匟�����̂̐ݒ�
			e2.setName_K(e.getDEP_NAME_K());
			// �f�[�^�W�̐ݒ�
			list2.add(e2);
		}
		// ���ʂ�Ԃ�
		return list2;
	}

	/**
	 * ���ʂ���������
	 */
	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");

		String kind = (String) keys.get("kind");

		BMN_MST entity1 = null;

		if ("DepartmentAll".equals(kind)) {
			entity1 = dao.getBMN_MSTByKaicodeDepcode(kaiCode, code);
		} else if ("DepartmentForMG0340".equals(kind)) {
			String dpkSsk = (String) keys.get("dpkSsk");
			int level = Integer.parseInt((String) keys.get("level"));
			String parentDepCode = (String) keys.get("parentDepCode");

			entity1 = dao.getDepartmentForMG0340(kaiCode, dpkSsk, level, parentDepCode, code);
		} else if ("UpperDepartmentForMG0340".equals(kind)) {
			String dpkSsk = (String) keys.get("dpkSsk");
			int level = Integer.parseInt((String) keys.get("level"));

			entity1 = dao.getUpperDepartmentForMG0340(kaiCode, dpkSsk, level - 1, code);
		} else {
			entity1 = dao.getBMN_MSTByKaicodeDepcode(kaiCode, code);
			// DEP_KBN���P�̂Ƃ���null��Ԃ�
			if (entity1 != null && entity1.getDEP_KBN() != 0) {
				entity1 = null;
			}

			if ("InputDepartment".equals(kind)) {
				if (entity1 != null && entity1.getDEP_KBN() != 0) {
					entity1 = null;
				}
			}
		}
		return (entity1 == null ? null : Util.avoidNull(entity1.getDEP_NAME_S()));
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// ����R�[�h�̎擾
		String depCode = (String) conditions.get("depCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// ����R�[�h�̐ݒ�
		entity.setDEP_CODE(depCode);
		// �f�[�^���폜����
		dao.delete(entity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		BMN_MST entity_bmn = (BMN_MST) dto;

		// �f�[�^��o�^����
		dao.insert(entity_bmn);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// ���̂̏�����
		BMN_MST entity_bmn = (BMN_MST) dto;

		// �f�[�^���X�V����
		dao.update(entity_bmn);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		BMN_MST entity_bmn = (BMN_MST) dto;
		String kaiCode = entity_bmn.getKAI_CODE();
		String depCode = entity_bmn.getDEP_CODE();

		return dao.getBMN_MSTByKaicodeDepcode(kaiCode, depCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		BMN_MST entity_bmn = (BMN_MST) dto;
		return entity_bmn.getDEP_CODE();
	}

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
	public List searchBmnMstData(String strKaiCode, String strDepCode, String strDepName, String strDepName_K,
		String strSlipDate, String strDpkSsk, String strBmnKbn, String strUpBmnCode, String strDpkLvl,
		String strBaseDpkLvl, String strBaseBmnCode) {

		Date slipDate = null;
		try {
			// �`�[���t
			if (!Util.isNullOrEmpty(strSlipDate)) {
				// �`�[���t
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}

		} catch (ParseException e) {
			throw new TRuntimeException(e);
		}

		List lstResult = null;
		if (strDpkSsk != null && !"".equals(strDpkSsk)) {
			// �f�[�^���擾����B
			lstResult = dao.searchBmnMstLvlData(strKaiCode, strDepCode, strDepName, strDepName_K, strDpkSsk, strBmnKbn,
				strUpBmnCode, strDpkLvl, strBaseDpkLvl, strBaseBmnCode);
		} else {
			// �f�[�^���擾����B
			lstResult = dao.searchBmnMstData(strKaiCode, strDepCode, strDepName, strDepName_K, slipDate);
		}

		return lstResult;
	}

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
	 * @return �����񃊃X�g
	 */
	public List<Object> refSearchDepartment(String kaiCode, String depCode, String sName, String kName,
		Timestamp termBasisDate, String organization, int level, String upperDepart, boolean sumDepart,
		boolean inputDepart, String beginCode, String endCode) {

		return dao.searchDepDpkData(kaiCode, depCode, sName, kName, termBasisDate, organization, level, upperDepart,
			sumDepart, inputDepart, beginCode, endCode);
	}

	/**
	 * @see jp.co.ais.trans.master.logic.DepartmentLogic#findDepartment(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public BMN_MST findDepartment(String deptCode, String beginCode, String endCode) {
		return dao.getBMN_MSTByBeginEnd(this.companyCode, deptCode, beginCode, endCode);
	}

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
	public List<BMN_MST> getBmnList(String comCode, String dpkCode, int DpkLvl, String inDepCode, String UpperCode) {
		// �R�[�h
		String code = "";
		// ����
		String name_S = ""; // ��������
		String name_K = "";

		// ��ЃR�[�h,�g�D�R�[�h�̎擾
		String kaiCode = comCode;
		String dpkSsk = dpkCode;
		int level = DpkLvl + 1;
		String parentDepCode = inDepCode;
		if (level == 10) {
			level = 9;
			parentDepCode = UpperCode;
		}

		List list = dao.getDepartmentsForMG0340(kaiCode, dpkSsk, level, parentDepCode, code, name_S, name_K);

		List<BMN_MST> bmnList = new LinkedList<BMN_MST>();

		for (Object bmn : list) {
			bmnList.add((BMN_MST) bmn);
		}
		return bmnList;
	}
}

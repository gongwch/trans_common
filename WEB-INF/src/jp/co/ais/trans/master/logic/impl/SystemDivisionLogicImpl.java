package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �V�X�e���敪�f�[�^����
 */
public class SystemDivisionLogicImpl extends CommonLogicBaseImpl implements SystemDivisionLogic {

	protected SYS_MSTDao dao;

	protected SYS_MST entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao SYS_MSTDao
	 */
	public SystemDivisionLogicImpl(SYS_MSTDao dao) {
		this.dao = dao;
	}

	/**
	 * SYS_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity SYS_MST
	 */
	public void setEntity(SYS_MST entity) {
		this.entity = entity;
	}

	public List find(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String beginSysKbn = (String) conditions.get("beginSysKbn");
		String endSysKbn = (String) conditions.get("endSysKbn");

		if (Util.isNullOrEmpty(beginSysKbn) && Util.isNullOrEmpty(endSysKbn)) {
			return dao.getAllSYS_MST2(kaiCode);
		} else if (Util.isNullOrEmpty(beginSysKbn)) {
			return dao.getSysInfoTo(kaiCode, endSysKbn);
		} else if (Util.isNullOrEmpty(endSysKbn)) {
			return dao.getSysInfoFrom(kaiCode, beginSysKbn);
		} else {
			return dao.getSysInfo(kaiCode, beginSysKbn, endSysKbn);
		}
	}

	public Object findOne(Map keys) {
		String kaiCode = (String) keys.get("kaiCode");
		String sysKbn = (String) keys.get("sysKbn");

		return dao.getSYS_MSTByKaicodeSyskbn(kaiCode, sysKbn);
	}

	public SYS_MST getSystemKbn(String kaiCode, String syskbn) {
		return dao.getSYS_MSTByKaicodeSyskbn(kaiCode, syskbn);
	}

	public List findREF(Map conditions) {
		// �R�[�h
		String sysKbn = (String) conditions.get("code");
		// ����
		String name_S = (String) conditions.get("name_S");
		// ��������
		String name_K = (String) conditions.get("name_K");
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		List list = dao.conditionSearch(kaiCode, sysKbn, name_S, name_K, beginCode, endCode);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			SYS_MST e = (SYS_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getSYS_KBN());
			e2.setName_S(e.getSYS_KBN_NAME_S());
			e2.setName_K(e.getSYS_KBN_NAME_K());
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// �R�[�h
		String sysKbn = (String) keys.get("code");
		String kaiCode = (String) keys.get("kaiCode");

		SYS_MST localentity = dao.getSYS_MSTByKaicodeSyskbn(kaiCode, sysKbn);

		return (localentity == null ? null : localentity.getSYS_KBN_NAME_S());
	}

	public void delete(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String sysKbn = (String) conditions.get("sysKbn");

		entity.setKAI_CODE(kaiCode);
		entity.setSYS_KBN(sysKbn);

		dao.delete(entity);
	}

	protected void insertImpl(Object dto) {
		SYS_MST localentity = (SYS_MST) dto;

		dao.insert(localentity);
	}

	protected void updateImpl(Object dto, Object oldDto) {
		SYS_MST localentity = (SYS_MST) dto;

		dao.update(localentity);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		SYS_MST localentity = (SYS_MST) dto;
		String kaiCode = localentity.getKAI_CODE();
		String sysKBN = localentity.getSYS_KBN();

		return dao.getSYS_MSTByKaicodeSyskbn(kaiCode, sysKBN);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		SYS_MST localentity = (SYS_MST) dto;
		return localentity.getSYS_KBN_NAME();
	}

	/**
	 * @see jp.co.ais.trans.master.logic.SystemDivisionLogic#getSystemList(java.lang.String)
	 */
	public List<SYS_MST> getSystemList(String companyCode) {
		return dao.getAllSYS_MST2(companyCode);
	}

}

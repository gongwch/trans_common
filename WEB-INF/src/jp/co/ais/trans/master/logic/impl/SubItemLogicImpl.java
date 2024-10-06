package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �⏕�Ȗڃ}�X�^���W�b�N
 */
public class SubItemLogicImpl extends CommonLogicBaseImpl {

	private HKM_MSTDao dao;

	private HKM_MST hkmentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao HKM_MSTDao
	 */
	public SubItemLogicImpl(HKM_MSTDao dao) {
		this.dao = dao;
	}

	/**
	 * HKM_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity HKM_MST
	 */
	public void setEntity(HKM_MST entity) {
		this.hkmentity = entity;
	}

	public List find(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String kmkCode = (String) conditions.get("kmkCode");
		String beginHkmCode = (String) conditions.get("beginHkmCode");
		String endHkmCode = (String) conditions.get("endHkmCode");

		if (Util.isNullOrEmpty(kmkCode)) {
			return dao.getAllHKM_MST1(kaiCode);
		} else if (Util.isNullOrEmpty(beginHkmCode) && Util.isNullOrEmpty(endHkmCode)) {
			return dao.getAllHKM_MST2(kaiCode, kmkCode);
		} else if (Util.isNullOrEmpty(beginHkmCode)) {
			return dao.getHkmInfoTo(kaiCode, kmkCode, endHkmCode);
		} else if (Util.isNullOrEmpty(endHkmCode)) {
			return dao.getHkmInfoFrom(kaiCode, kmkCode, beginHkmCode);
		} else {
			return dao.getHkmInfo(kaiCode, kmkCode, beginHkmCode, endHkmCode);
		}

	}

	public Object findOne(Map keys) {
		String kaiCode = (String) keys.get("kaiCode");
		String kmkCode = (String) keys.get("kmkCode");
		String hkmCode = (String) keys.get("hkmCode");

		return dao.getHKM_MSTByKaicodeKmkcodeHkmcode(kaiCode, kmkCode, hkmCode);
	}

	public List findREF(Map conditions) {
		// �R�[�h
		String code = (String) conditions.get("code");
		// ����
		String name_S = (String) conditions.get("name_S");
		// ��������
		String name_K = (String) conditions.get("name_K");
		String kaiCode = (String) conditions.get("kaiCode");
		String kmkCode = (String) conditions.get("kmkCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		String kind = (String) conditions.get("kind");

		List list = dao.conditionSearch(kaiCode, kmkCode, code, name_S, name_K, beginCode, endCode, kind);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			HKM_MST e = (HKM_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getHKM_CODE());
			e2.setName_S(e.getHKM_NAME_S());
			e2.setName_K(e.getHKM_NAME_K());
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		String kaiCode = (String) keys.get("kaiCode");
		String kmkCode = (String) keys.get("kmkCode");
		String kind = (String) keys.get("kind");

		HKM_MST entity = dao.getHKM_MSTByKaicodeKmkcodeHkmcode(kaiCode, kmkCode, code);

		if ("SubItemWithChild".equals(kind)) {
			if (entity != null && entity.getUKM_KBN() != 1) {
				entity = null;
			}
		}

		return (entity == null ? null : entity.getHKM_NAME_S());
	}

	public void delete(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String kmkCode = (String) conditions.get("kmkCode");
		String hkmCode = (String) conditions.get("hkmCode");

		hkmentity.setKAI_CODE(kaiCode);
		hkmentity.setKMK_CODE(kmkCode);
		hkmentity.setHKM_CODE(hkmCode);

		dao.delete(hkmentity);
	}

	protected void insertImpl(Object dto) {
		HKM_MST entity = (HKM_MST) dto;

		dao.insert(entity);
	}

	protected void updateImpl(Object dto, Object oldDto) {
		HKM_MST entity = (HKM_MST) dto;

		dao.update(entity);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		HKM_MST entity = (HKM_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String kmkCode = entity.getKMK_CODE();
		String hkmCode = entity.getHKM_CODE();

		return dao.getHKM_MSTByKaicodeKmkcodeHkmcode(kaiCode, kmkCode, hkmCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		HKM_MST entity = (HKM_MST) dto;
		return entity.getHKM_CODE();
	}
}

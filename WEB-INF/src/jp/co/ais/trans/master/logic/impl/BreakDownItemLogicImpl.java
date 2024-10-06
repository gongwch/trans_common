package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ����Ȗڃ}�X�^����
 */
public class BreakDownItemLogicImpl extends CommonLogicBaseImpl {

	/** ����Ȗڃ}�X�^Dao */
	private UKM_MSTDao dao;

	/** ����Ȗڃ}�X�^���� */
	private UKM_MST entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao UKM_MSTDao
	 */
	public BreakDownItemLogicImpl(UKM_MSTDao dao) {
		// ����Ȗڃ}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * UKM_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity UKM_MST
	 */
	public void setEntity(UKM_MST entity) {
		// ����Ȗڃ}�X�^���̂�Ԃ�
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
		String kmkCode = (String) conditions.get("kmkCode");
		//
		String hkmCode = (String) conditions.get("hkmCode");
		//
		String beginUkmCode = (String) conditions.get("beginUkmCode");
		// �I���R�[�h�̎擾
		String endUkmCode = (String) conditions.get("endUkmCode");

		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(kmkCode)) {
			return dao.getAllUKM_MST1(kaiCode);
		} else if (Util.isNullOrEmpty(hkmCode)) {
			return dao.getAllUKM_MST2(kaiCode, kmkCode);
		} else if (Util.isNullOrEmpty(beginUkmCode) && Util.isNullOrEmpty(endUkmCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllUKM_MST3(kaiCode, kmkCode, hkmCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginUkmCode)) {
			// ���ʂ�Ԃ�
			return dao.getUkmInfoTo(kaiCode, kmkCode, hkmCode, endUkmCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endUkmCode)) {
			// ���ʂ�Ԃ�
			return dao.getUkmInfoFrom(kaiCode, kmkCode, hkmCode, beginUkmCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getUkmInfo(kaiCode, kmkCode, hkmCode, beginUkmCode, endUkmCode);
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
		// �ȖڃR�[�h�̎擾
		String kmkCode = (String) keys.get("kmkCode");
		// �⏕�ȖڃR�[�h�̎擾
		String hkmCode = (String) keys.get("hkmCode");
		// ����ȖڃR�[�h�̎擾
		String ukmCode = (String) keys.get("ukmCode");
		// ���ʂ�Ԃ�
		return dao.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(kaiCode, kmkCode, hkmCode, ukmCode);
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
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �ȖڃR�[�h�̎擾
		String kmkCode = (String) conditions.get("kmkCode");
		// �⏕�ȖڃR�[�h�̎擾
		String hkmCode = (String) conditions.get("hkmCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		// ���ʂ̎擾
		List list = dao.conditionSearch(kaiCode, kmkCode, hkmCode, code, name_S, name_K, beginCode, endCode);
		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			UKM_MST e = (UKM_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// ����ȖڃR�[�h�̐ݒ�
			e2.setCode(e.getUKM_CODE());
			// ����Ȗڗ��̂̐ݒ�
			e2.setName_S(e.getUKM_NAME_S());
			// ����Ȗڌ������̂̐ݒ�
			e2.setName_K(e.getUKM_NAME_K());
			// �f�[�^�W�̐ݒ�
			list2.add(e2);
		}
		// ���ʂ�Ԃ�
		return list2;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys
	 */
	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// �ȖڃR�[�h�̎擾
		String kmkCode = (String) keys.get("kmkCode");
		// �⏕�ȖڃR�[�h�̎擾
		String hkmCode = (String) keys.get("hkmCode");
		// ���̂̏�����
		UKM_MST entity1 = dao.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(kaiCode, kmkCode, hkmCode, code);
		// ���̂�Ԃ�
		return (entity1 == null ? null : entity1.getUKM_NAME_S());
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �ȖڃR�[�h�̎擾
		String kmkCode = (String) conditions.get("kmkCode");
		// �⏕�ȖڃR�[�h�̎擾
		String hkmCode = (String) conditions.get("hkmCode");
		// ����ȖڃR�[�h�̎擾
		String ukmCode = (String) conditions.get("ukmCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// �ȖڃR�[�h�̐ݒ�
		entity.setKMK_CODE(kmkCode);
		// �⏕�ȖڃR�[�h�̐ݒ�
		entity.setHKM_CODE(hkmCode);
		// ����ȖڃR�[�h�̐ݒ�
		entity.setUKM_CODE(ukmCode);
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
		UKM_MST entity1 = (UKM_MST) dto;

		// �f�[�^��o�^����
		dao.insert(entity1);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// ���̂̏�����
		UKM_MST entity1 = (UKM_MST) dto;

		// �f�[�^���X�V����
		dao.update(entity1);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		UKM_MST entity1 = (UKM_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String kmkCode = entity1.getKMK_CODE();
		String hkmCode = entity1.getHKM_CODE();
		String ukmCode = entity1.getUKM_CODE();

		return dao.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(kaiCode, kmkCode, hkmCode, ukmCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		UKM_MST entity1 = (UKM_MST) dto;
		return entity1.getUKM_CODE();
	}
}

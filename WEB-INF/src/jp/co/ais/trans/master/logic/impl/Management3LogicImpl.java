package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �Ǘ�3�}�X�^
 */
public class Management3LogicImpl extends CommonLogicBaseImpl {

	/** �Ǘ�3�}�X�^Dao */
	protected KNR3_MSTDao dao;

	/** �Ǘ�3�}�X�^���� */
	protected KNR3_MST knr3entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao KNR3_MSTDao
	 */
	public Management3LogicImpl(KNR3_MSTDao dao) {
		// �Ǘ��}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * KNR3_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity KNR3_MST
	 */
	public void setEntity(KNR3_MST entity) {
		// �Ǘ��}�X�^���̂�Ԃ�
		this.knr3entity = entity;
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
		String beginknrCode = (String) conditions.get("beginknrCode");
		// �I���R�[�h�̎擾
		String endknrCode = (String) conditions.get("endknrCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginknrCode) && Util.isNullOrEmpty(endknrCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllKNR3_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginknrCode)) {
			// ���ʂ�Ԃ�
			return dao.getKnr3InfoTo(kaiCode, endknrCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endknrCode)) {
			// ���ʂ�Ԃ�
			return dao.getKnr3InfoFrom(kaiCode, beginknrCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getKnr3Info(kaiCode, beginknrCode, endknrCode);
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
		// �Ǘ��R�[�h�̎擾
		String knrCode = (String) keys.get("knrCode");
		// ���ʂ�Ԃ�
		return dao.getKNR3_MSTByKaicodeKnrcode3(kaiCode, knrCode);
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
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		// ���ʂ̎擾
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode);
		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			KNR3_MST e = (KNR3_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// �Ǘ��R�[�h�̐ݒ�
			e2.setCode(e.getKNR_CODE_3());
			// �Ǘ����̂̐ݒ�
			e2.setName_S(e.getKNR_NAME_S_3());
			// �Ǘ��������̂̐ݒ�
			e2.setName_K(e.getKNR_NAME_K_3());
			// �f�[�^�W�̐ݒ�
			list2.add(e2);
		}
		// ���ʂ�Ԃ�
		return list2;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ���̂̏�����
		KNR3_MST entity = dao.getKNR3_MSTByKaicodeKnrcode3(kaiCode, code);
		// ���̂�Ԃ�
		return (entity == null ? null : entity.getKNR_NAME_S_3());
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �Ǘ��R�[�h�̎擾
		String knrCode = (String) conditions.get("knrCode");
		// ��ЃR�[�h�̐ݒ�
		knr3entity.setKAI_CODE(kaiCode);
		// �Ǘ��R�[�h�̐ݒ�
		knr3entity.setKNR_CODE_3(knrCode);
		// �f�[�^���폜����
		dao.delete(knr3entity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		KNR3_MST entity = (KNR3_MST) dto;

		// �f�[�^��o�^����
		dao.insert(entity);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// ���̂̏�����
		KNR3_MST entity = (KNR3_MST) dto;

		// �f�[�^���X�V����
		dao.update(entity);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		KNR3_MST entity = (KNR3_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String knrCode3 = entity.getKNR_CODE_3();

		return dao.getKNR3_MSTByKaicodeKnrcode3(kaiCode, knrCode3);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		KNR3_MST entity = (KNR3_MST) dto;
		return entity.getKNR_CODE_3();
	}
}

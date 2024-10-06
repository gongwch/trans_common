package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �Ǘ�5�}�X�^����
 */
public class Management5LogicImpl extends CommonLogicBaseImpl {

	/** �Ǘ��}�X�^Dao */
	private KNR5_MSTDao dao;

	/** �Ǘ��}�X�^���� */
	private KNR5_MST knr5entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao KNR5_MSTDao
	 */
	public Management5LogicImpl(KNR5_MSTDao dao) {
		// �Ǘ��}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * KNR5_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity KNR5_MST
	 */
	public void setEntity(KNR5_MST entity) {
		// �Ǘ��}�X�^���̂�Ԃ�
		this.knr5entity = entity;
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
			return dao.getAllKNR5_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginknrCode)) {
			// ���ʂ�Ԃ�
			return dao.getKnr5InfoTo(kaiCode, endknrCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endknrCode)) {
			// ���ʂ�Ԃ�
			return dao.getKnr5InfoFrom(kaiCode, beginknrCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getKnr5Info(kaiCode, beginknrCode, endknrCode);
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
		return dao.getKNR5_MSTByKaicodeKnrcode5(kaiCode, knrCode);
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
			KNR5_MST e = (KNR5_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// �Ǘ��R�[�h�̐ݒ�
			e2.setCode(e.getKNR_CODE_5());
			// �Ǘ����̂̐ݒ�
			e2.setName_S(e.getKNR_NAME_S_5());
			// �Ǘ��������̂̐ݒ�
			e2.setName_K(e.getKNR_NAME_K_5());
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
		KNR5_MST entity = dao.getKNR5_MSTByKaicodeKnrcode5(kaiCode, code);
		// ���̂�Ԃ�
		return (entity == null ? null : entity.getKNR_NAME_S_5());
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
		knr5entity.setKAI_CODE(kaiCode);
		// �Ǘ��R�[�h�̐ݒ�
		knr5entity.setKNR_CODE_5(knrCode);
		// �f�[�^���폜����
		dao.delete(knr5entity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		KNR5_MST entity = (KNR5_MST) dto;

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
		KNR5_MST entity = (KNR5_MST) dto;

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
		KNR5_MST entity = (KNR5_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String knrCode5 = entity.getKNR_CODE_5();

		return dao.getKNR5_MSTByKaicodeKnrcode5(kaiCode, knrCode5);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		KNR5_MST entity = (KNR5_MST) dto;
		return entity.getKNR_CODE_5();
	}
}

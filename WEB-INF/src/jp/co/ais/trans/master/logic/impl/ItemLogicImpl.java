package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �Ȗڃ}�X�^����
 */
public class ItemLogicImpl extends CommonLogicBaseImpl {

	/** �Ȗڃ}�X�^Dao */
	private KMK_MSTDao dao;

	/** �Ȗڃ}�X�^���� */
	private KMK_MST kmkMstentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao KMK_MSTDao
	 */
	public ItemLogicImpl(KMK_MSTDao dao) {
		// �Ȗڃ}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * KMK_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity KMK_MST
	 */
	public void setEntity(KMK_MST entity) {
		// �Ȗڃ}�X�^���̂�Ԃ�
		this.kmkMstentity = entity;
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
		String beginKmkCode = (String) conditions.get("beginKmkCode");
		// �I���R�[�h�̎擾
		String endKmkCode = (String) conditions.get("endKmkCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginKmkCode) && Util.isNullOrEmpty(endKmkCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllKMK_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginKmkCode)) {
			// ���ʂ�Ԃ�
			return dao.getKmkInfoTo(kaiCode, endKmkCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endKmkCode)) {
			// ���ʂ�Ԃ�
			return dao.getKmkInfoFrom(kaiCode, beginKmkCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getKmkInfo(kaiCode, beginKmkCode, endKmkCode);
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
		// ���ʂ�Ԃ�
		return dao.getKMK_MSTByKaicodeKmkcode(kaiCode, kmkCode);
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
		String kind = (String) conditions.get("kind");

		// ���ʂ̎擾
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, kind);
		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			KMK_MST e = (KMK_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// �ȖڃR�[�h�̐ݒ�
			e2.setCode(e.getKMK_CODE());
			// �Ȗڗ��̂̐ݒ�
			e2.setName_S(e.getKMK_NAME_S());
			// �Ȗڌ������̂̐ݒ�
			e2.setName_K(e.getKMK_NAME_K());
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

		String kind = (String) keys.get("kind");

		// ���̂̏�����
		KMK_MST entity = dao.getKMK_MSTByKaicodeKmkcode(kaiCode, code);

		// ���͂��ꂽ�l�������ꍇ
		if (entity == null) {
			// ���̂�Ԃ�
			return null;
		}

		// �Ȗڃ}�X�^���g�̏ꍇ
		else if (kind.equals("ItemAll")) {
			// ���̂�Ԃ�
			return (entity == null ? null : entity.getKMK_NAME_S());
		}
		// �Ȗڃ}�X�^�ȊO
		else {
			// �W�v�敪�E���o���敪�̂Ƃ��͌��ʂ�Ԃ��Ȃ�
			if ("Item".equals(kind) && entity.getSUM_KBN() != 0) {
				entity = null;
			}

			else if ("InputItem".equals(kind)) {
				if (entity != null && entity.getSUM_KBN() != 0) {
					entity = null;
				}
			} else if ("ItemWithChild".equals(kind)) {
				if (entity != null && entity.getHKM_KBN() != 1) {
					entity = null;
				}
			}

			// ���̂�Ԃ�
			return (entity == null ? null : entity.getKMK_NAME_S());
		}

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
		// ��ЃR�[�h�̐ݒ�
		kmkMstentity.setKAI_CODE(kaiCode);
		// �ȖڃR�[�h�̐ݒ�
		kmkMstentity.setKMK_CODE(kmkCode);
		// �f�[�^���폜����
		dao.delete(kmkMstentity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		KMK_MST entity = (KMK_MST) dto;

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
		KMK_MST entity = (KMK_MST) dto;

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
		KMK_MST entity = (KMK_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String kmkCode = entity.getKMK_CODE();

		return dao.getKMK_MSTByKaicodeKmkcode(kaiCode, kmkCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		KMK_MST entity = (KMK_MST) dto;
		return entity.getKMK_CODE();
	}
}

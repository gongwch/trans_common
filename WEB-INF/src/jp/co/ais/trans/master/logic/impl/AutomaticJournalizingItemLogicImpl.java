package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �����d��Ȗڃf�[�^����
 */
public class AutomaticJournalizingItemLogicImpl extends CommonLogicBaseImpl {

	/** �����d��Ȗڃ}�X�^Dao */
	private SWK_KMK_MSTDao dao;

	/** �����d��Ȗڃ}�X�^���� */
	private SWK_KMK_MST entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao SWK_KMK_MSTDao
	 */
	public AutomaticJournalizingItemLogicImpl(SWK_KMK_MSTDao dao) {
		// �����d��Ȗڃ}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * SWK_KMK_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity SWK_KMK_MST
	 */
	public void setEntity(SWK_KMK_MST entity) {
		// �����d��Ȗڃ}�X�^���̂�Ԃ�
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

		// ���ʂ�Ԃ�
		return dao.getSWK_KMK_MSTByKaicode(kaiCode);

	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// �Ȗڐ���敪�̎擾
		int kmkCnt = Integer.parseInt((String) keys.get("kmkCnt"));
		// ���ʂ�Ԃ�
		return dao.getSWK_KMK_MSTByKaicodeKmkcnt(kaiCode, kmkCnt);
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �Ȗڐ���敪�̎擾
		int kmkCnt = Integer.parseInt((String) conditions.get("kmkCnt"));
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// �Ȗڐ���敪�̐ݒ�
		entity.setKMK_CNT(kmkCnt);
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
		SWK_KMK_MST entity1 = (SWK_KMK_MST) dto;
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
		SWK_KMK_MST entity1 = (SWK_KMK_MST) dto;

		// mysql �ł͋󔒂�NULL�͕ʕ��ɂȂ�ׁA�󔒂�NULL�ɐݒ肵�Ȃ����B
		if (Util.isNullOrEmpty(entity1.getHKM_CODE())) {
			entity1.setHKM_CODE(null);
		}
		if (Util.isNullOrEmpty(entity1.getUKM_CODE())) {
			entity1.setUKM_CODE(null);
		}

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
		SWK_KMK_MST entity1 = (SWK_KMK_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		int kmkCnt = entity1.getKMK_CNT();

		return dao.getSWK_KMK_MSTByKaicodeKmkcnt(kaiCode, kmkCnt);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		SWK_KMK_MST entity1 = (SWK_KMK_MST) dto;
		return entity1.getKMK_CNT() + "";
	}
}

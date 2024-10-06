package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���[�g�}�X�^����
 */
public class RateLogicImpl extends CommonLogicBaseImpl {

	/** ���[�g�}�X�^Dao */
	private RATE_MSTDao dao;

	/** ���[�g�}�X�^���� */
	private RATE_MST rateentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao RATE_MSTDao
	 */
	public RateLogicImpl(RATE_MSTDao dao) {
		// ���[�g�}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * RATE_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity RATE_MST
	 */
	public void setEntity(RATE_MST entity) {
		// ���[�g�}�X�^���̂�Ԃ�
		this.rateentity = entity;
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
		String beginCurCode = (String) conditions.get("beginCurCode");
		// �I���R�[�h�̎擾
		String endCurCode = (String) conditions.get("endCurCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginCurCode) && Util.isNullOrEmpty(endCurCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllRATE_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginCurCode)) {
			// ���ʂ�Ԃ�
			return dao.getRateInfoTo2(kaiCode, endCurCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endCurCode)) {
			// ���ʂ�Ԃ�
			return dao.getRateInfoFrom2(kaiCode, beginCurCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getRateInfo(kaiCode, beginCurCode, endCurCode);
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
		// �ʉ݃R�[�h�̎擾
		String curCode = (String) keys.get("curCode");
		// �K�p�J�n���t�̎擾
		Date strDate = (Date) keys.get("strDate");
		// ���ʂ�Ԃ�
		return dao.getRATE_MST(kaiCode, curCode, strDate);
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �ʉ݃R�[�h�̎擾
		String curCode = (String) conditions.get("curCode");
		// �K�p�J�n���t�̎擾
		Date strDate = (Date) conditions.get("strDate");
		// ��ЃR�[�h�̐ݒ�
		rateentity.setKAI_CODE(kaiCode);
		// �ʉ݃R�[�h�̐ݒ�
		rateentity.setCUR_CODE(curCode);
		// �K�p�J�n���t�̐ݒ�
		rateentity.setSTR_DATE(strDate);
		// �f�[�^���폜����
		dao.delete(rateentity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		RATE_MST entity = (RATE_MST) dto;

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
		RATE_MST entity = (RATE_MST) dto;

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
		RATE_MST entity = (RATE_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String curCode = entity.getCUR_CODE();
		Date strDate = entity.getSTR_DATE();

		return dao.getRATE_MST(kaiCode, curCode, strDate);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B *
	 * 
	 * @return String
	 */
	protected String getPrimaryCode() {
		// RATE_MST entity = (RATE_MST)dto;
		return null;
	}
}

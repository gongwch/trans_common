package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �x�����j�f�[�^����
 */
public class PaymentPolicyLogicImpl extends CommonLogicBaseImpl {

	/** �x�����j�}�X�^Dao */
	private AP_SHH_MSTDao dao;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao AP_SHH_MSTDao
	 */
	public PaymentPolicyLogicImpl(AP_SHH_MSTDao dao) {
		// �x�����j�}�X�^Dao��Ԃ�
		this.dao = dao;
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
		return dao.getAllAP_SHH_MST2(kaiCode);
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ���ʂ�Ԃ�
		return dao.getAP_SHH_MST(kaiCode);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		AP_SHH_MST entity = (AP_SHH_MST) dto;
		// �o�^���t�̐ݒ�
		entity.setSHH_INP_DATE(Util.getCurrentDate());
		// �f�[�^��o�^����
		dao.insert(entity);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 * @param oldDto
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// ���̂̏�����
		AP_SHH_MST entity = (AP_SHH_MST) dto;
		AP_SHH_MST oldEntity = (AP_SHH_MST) oldDto;

		entity.setSHH_INP_DATE(oldEntity.getSHH_INP_DATE());

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
		AP_SHH_MST entity = (AP_SHH_MST) dto;
		String kaiCode = entity.getKAI_CODE();

		return dao.getAP_SHH_MST(kaiCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		AP_SHH_MST entity = (AP_SHH_MST) dto;
		return entity.getKAI_CODE();
	}
}

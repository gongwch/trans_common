package jp.co.ais.trans.master.logic.impl;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �J�����_�[�f�[�^����
 */
public class CalendarLogicImpl extends CommonLogicBaseImpl {

	/** �J�����_�[�}�X�^Dao */
	private AP_CAL_MSTDao dao;

	/** �J�����_�[�}�X�^���� */
	private AP_CAL_MST entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao AP_CAL_MSTDao
	 */
	public CalendarLogicImpl(AP_CAL_MSTDao dao) {
		// �ʉ݃}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * AP_CAL_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity AP_CAL_MST
	 */
	public void setEntity(AP_CAL_MST entity) {
		// �ʉ݃}�X�^���̂�Ԃ�
		this.entity = entity;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map
	 * @throws ParseException
	 */
	public List find(Map conditions) throws ParseException {

		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �J�n���t�̏�����
		Date beginCalDate = null;
		// �I�����t�̏�����
		Date endCalDate = null;
		// �J�n���t�̎擾
		beginCalDate = DateUtil.toYMDDate(conditions.get("beginCalDate").toString());
		// �I�����t�̎擾
		endCalDate = DateUtil.toYMDDate(conditions.get("endCalDate").toString());
		// ���ʂ�Ԃ�
		return dao.getCalendarInfo(kaiCode, beginCalDate, endCalDate);

	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 * @throws ParseException
	 */
	public Object findOne(Map keys) throws ParseException {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ���t�̎擾
		String calDate = (String) keys.get("calDate");
		// ���ʂ�Ԃ�
		return dao.getAP_CAL_MST(kaiCode, DateUtil.toYMDDate(calDate));
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) throws ParseException {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �����̎擾
		int intLastDay = Integer.parseInt((String) conditions.get("intLastDay"));
		// ���̎擾
		String calMonth = (String) conditions.get("calMonth");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		for (int i = 0; i < intLastDay; i++) {
			calMonth = calMonth.substring(0, 7) + "/" + String.valueOf(i + 1);
			// ���t�̎擾
			Date calDate = DateUtil.toYMDDate(calMonth);
			// ���t�̐ݒ�
			entity.setCAL_DATE(calDate);
			try {
				// �f�[�^���폜����
				dao.delete(entity);
			} catch (Exception e) {
				// ����ɏ�������܂���ł���
				continue;
			}
		}
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		AP_CAL_MST entity1 = (AP_CAL_MST) dto;
		// �o�^���t�̐ݒ�
		entity1.setCAL_INP_DATE(Util.getCurrentDate());
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
		AP_CAL_MST entity1 = (AP_CAL_MST) dto;
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
		AP_CAL_MST entity1 = (AP_CAL_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		Date calDATE = entity1.getCAL_DATE();

		return dao.getAP_CAL_MSTByKaicodeCalcode(kaiCode, calDATE);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		AP_CAL_MST entity1 = (AP_CAL_MST) dto;
		return entity1.getCAL_DATE() + "";
	}
}

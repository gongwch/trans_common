package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �x�������@�f�[�^����
 */
public class PaymentMethodLogicImpl extends CommonLogicBaseImpl implements PaymentMethodLogic {

	private AP_HOH_MSTDao dao;

	private AP_HOH_MST apHohentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao AP_HOH_MSTDao
	 */
	public PaymentMethodLogicImpl(AP_HOH_MSTDao dao) {
		this.dao = dao;
	}

	/**
	 * AP_HOH_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity AP_HOH_MST
	 */
	public void setEntity(AP_HOH_MST entity) {
		this.apHohentity = entity;
	}

	public List find(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String beginHohHohCode = (String) conditions.get("beginHohHohCode");
		String endHohHohCode = (String) conditions.get("endHohHohCode");
		String includeEmployeePayment = (String) conditions.get("includeEmployeePayment");
		String includeExternalPayment = (String) conditions.get("includeExternalPayment");

		return dao.query(kaiCode, beginHohHohCode, endHohHohCode, includeEmployeePayment, includeExternalPayment);
	}

	public AP_HOH_MST findOne(Map keys) {
		String kaiCode = (String) keys.get("kaiCode");
		String hohHohCode = (String) keys.get("hohHohCode");

		return dao.getAP_HOH_MSTByKaicodehohhohcode(kaiCode, hohHohCode);
	}

	/**
	 * �x�������@�R�[�h�Ŏx�������擾
	 * 
	 * @param conditions
	 * @return �x�������@���
	 */
	public List findREF(Map conditions) {
		// �R�[�h
		String code = (String) conditions.get("code");
		// ����
		String name_S = (String) conditions.get("name_S");
		// ��������
		String name_K = (String) conditions.get("name_K");
		// �����擾
		String kind = (String) conditions.get("kind");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		// ���ʂ̎擾
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, kind);
		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			AP_HOH_MST e = (AP_HOH_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// �x�����@�R�[�h�̐ݒ�
			e2.setCode(e.getHOH_HOH_CODE());
			// �x�����@���̂̐ݒ�
			e2.setName_S(e.getHOH_HOH_NAME());
			// �x�����@�������̂̐ݒ�
			e2.setName_K(e.getHOH_HOH_NAME_K());
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
		// �����̎擾
		String kind = (String) keys.get("kind");
		// ���̂̏�����
		AP_HOH_MST entity = dao.getHohHohName(kaiCode, code, kind);
		// ���̂�Ԃ�
		return (entity == null ? null : entity.getHOH_HOH_NAME());
	}

	public void delete(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String hohHohCode = (String) conditions.get("hohHohCode");

		apHohentity.setKAI_CODE(kaiCode);
		apHohentity.setHOH_HOH_CODE(hohHohCode);

		dao.delete(apHohentity);
	}

	protected void insertImpl(Object dto) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;

		entity.setHOH_INP_DATE(Util.getCurrentDate()); // �o�^���t

		dao.insert(entity);
	}

	protected void updateImpl(Object dto, Object oldDto) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;
		AP_HOH_MST oldEntity = (AP_HOH_MST) oldDto;

		entity.setHOH_INP_DATE(oldEntity.getHOH_INP_DATE());

		dao.update(entity);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String hohhohCode = entity.getHOH_HOH_CODE();

		return dao.getAP_HOH_MSTByKaicodehohhohcode(kaiCode, hohhohCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;
		return entity.getHOH_HOH_CODE();
	}

	public AP_HOH_MST getREFItems(Map keys) {
		// ���ʂ�Ԃ�
		return this.findOne(keys);
	}

	/**
	 * �x�������@���X�g�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param hohCode �x�������@�R�[�h
	 * @param sName ����
	 * @param kName ��������
	 * @param termBasisDate �L������
	 * @return �x�������@���X�g
	 */
	public List<Object> refDailog(String kaiCode, String hohCode, String sName, String kName, Timestamp termBasisDate) {

		return dao.refDialogSearch(kaiCode, hohCode, sName, kName, termBasisDate);
	}
}

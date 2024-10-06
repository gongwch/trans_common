package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �ʉ݃}�X�^����
 */
public class CurrencyLogicImpl extends CommonLogicBaseImpl implements CurrencyLogic {

	/** �ʉ݃}�X�^Dao */
	private CUR_MSTDao dao;

	/** �ʉ݃}�X�^���� */
	private CUR_MST entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao CUR_MSTDao
	 */
	public CurrencyLogicImpl(CUR_MSTDao dao) {
		// �ʉ݃}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * CUR_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity CUR_MST
	 */
	public void setEntity(CUR_MST entity) {
		// �ʉ݃}�X�^���̂�Ԃ�
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
		String beginCurCode = (String) conditions.get("beginCurCode");
		// �I���R�[�h�̎擾
		String endCurCode = (String) conditions.get("endCurCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginCurCode) && Util.isNullOrEmpty(endCurCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllCUR_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginCurCode)) {
			// ���ʂ�Ԃ�
			return dao.getCurInfoTo(kaiCode, endCurCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endCurCode)) {
			// ���ʂ�Ԃ�
			return dao.getCurInfoFrom(kaiCode, beginCurCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getCurInfo(kaiCode, beginCurCode, endCurCode);
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
		// ���ʂ�Ԃ�
		return dao.getCUR_MST(kaiCode, curCode);
	}

	/**
	 * REF�_�C�A���O�p�̌���
	 * 
	 * @conditions ���������iMap�`���A���Ȃ��Ƃ�code/name_S/name_K�O�������܂ށj
	 */
	public List findREF(Map conditions) {
		// �R�[�h
		String code = (String) conditions.get("code");
		// ����
		String name_S = (String) conditions.get("name_S");
		// ��������
		String name_K = (String) conditions.get("name_K");

		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");

		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// ���ʂ̎擾
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode);
		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			CUR_MST e = (CUR_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// �ʉ݃R�[�h�̐ݒ�
			e2.setCode(e.getCUR_CODE());
			// �ʉݗ��̂̐ݒ�
			e2.setName_S(e.getCUR_NAME_S());
			// �ʉ݌������̂̐ݒ�
			e2.setName_K(e.getCUR_NAME_K());
			// �f�[�^�W�̐ݒ�
			list2.add(e2);
		}
		// ���ʂ�Ԃ�
		return list2;
	}

	/**
	 * ButtonField�ŃR�[�h����͎��A���̂̌���
	 * 
	 * @conditions ���������iMap�`���AfindREF�Ɠ��������j
	 */
	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");

		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ���̂̏�����
		CUR_MST entity1 = dao.getCUR_MST(kaiCode, code);
		// ���̂�Ԃ�
		return (entity1 == null ? null : entity1.getCUR_NAME_S());
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
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// �ʉ݃R�[�h�̐ݒ�
		entity.setCUR_CODE(curCode);
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
		CUR_MST entity1 = (CUR_MST) dto;

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
		CUR_MST entity1 = (CUR_MST) dto;

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
		CUR_MST entity1 = (CUR_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String curCode = entity1.getCUR_CODE();

		return dao.getCUR_MST(kaiCode, curCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		CUR_MST entity1 = (CUR_MST) dto;
		return entity1.getCUR_CODE();
	}

	/**
	 * ���������ɂ��ʉ݃��X�g�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param curCode �ʉ݃R�[�h
	 * @param sName ����
	 * @param kName ��������
	 * @param termBasisDate �L������
	 * @return �ʉ݃��X�g
	 */
	public List<Object> refDailogCurrency(String kaiCode, String curCode, String sName, String kName,
		Timestamp termBasisDate, String beginCode, String endCode) {

		return dao.searchRefCurrency(kaiCode, curCode, sName, kName, termBasisDate, beginCode, endCode);
	}

	/**
	 * �ʉ݃R�[�h�ɂ��A�ʉݏ��擾
	 * 
	 * @param keys
	 * @return �ʉݏ��
	 */
	public List getREFItems(Map keys) {

		CUR_MST entity1 = (CUR_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity1 != null) {
			// ���ʂ̏�����
			List<Object> result = new ArrayList<Object>(7);

			String curCode = entity1.getCUR_CODE();
			String triName = entity1.getCUR_NAME();

			result.add(curCode);
			// ��s���̐ݒ�
			result.add(triName != null ? entity1.getCUR_NAME() : "");

			result.add(entity1.getSTR_DATE());
			// �I��
			result.add(entity1.getEND_DATE());
			// �����_�ȉ�����
			result.add(entity1.getDEC_KETA());
			// ���ʂ̐ݒ�
			list.add(result);
		} else {
			// ���ʂ̍폜
			list.add(null);
		}
		// ���ʂ�Ԃ�
		return list;
	}
}

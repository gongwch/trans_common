package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �Ј��}�X�^����
 */
public class EmployeeLogicImpl extends CommonLogicBaseImpl implements EmployeeLogic {

	/** �Ј��}�X�^Dao */
	protected EMP_MSTDao dao;

	/** �Ј��}�X�^���� */
	protected EMP_MST empMstentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao EMP_MSTDao
	 */
	public EmployeeLogicImpl(EMP_MSTDao dao) {
		// �Ј��}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * EMP_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity EMP_MST
	 */
	public void setEntity(EMP_MST entity) {
		// �Ј��}�X�^���̂�Ԃ�
		this.empMstentity = entity;
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
		String beginEmpCode = (String) conditions.get("beginEmpCode");
		// �I���R�[�h�̎擾
		String endEmpCode = (String) conditions.get("endEmpCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginEmpCode) && Util.isNullOrEmpty(endEmpCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllEMP_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginEmpCode)) {
			// ���ʂ�Ԃ�
			return dao.getEmpInfoTo(kaiCode, endEmpCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endEmpCode)) {
			// ���ʂ�Ԃ�
			return dao.getEmpInfoFrom(kaiCode, beginEmpCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getEmpInfo(kaiCode, beginEmpCode, endEmpCode);
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
		// �Ј��R�[�h�̎擾
		String empCode = (String) keys.get("empCode");
		// ���ʂ�Ԃ�
		return dao.getEMP_MSTByKaicodeEmpcode(kaiCode, empCode);
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
			EMP_MST e = (EMP_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// �Ј��R�[�h�̐ݒ�
			e2.setCode(e.getEMP_CODE());
			// �Ј����̂̐ݒ�
			e2.setName_S(e.getEMP_NAME_S());
			// �Ј��������̂̐ݒ�
			e2.setName_K(e.getEMP_NAME_K());
			// �f�[�^�W�̐ݒ�
			list2.add(e2);
		}
		// ���ʂ�Ԃ�
		return list2;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys String,keys Map
	 */
	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ���̂̏�����
		EMP_MST entity = dao.getEMP_MSTByKaicodeEmpcode(kaiCode, code);
		// ���̂�Ԃ�
		return (entity == null ? null : entity.getEMP_NAME_S());
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �Ј��R�[�h�̎擾
		String empCode = (String) conditions.get("empCode");
		// ��ЃR�[�h�̐ݒ�
		empMstentity.setKAI_CODE(kaiCode);
		// �Ј��R�[�h�̐ݒ�
		empMstentity.setEMP_CODE(empCode);
		// �f�[�^���폜����
		dao.delete(empMstentity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		EMP_MST entity = (EMP_MST) dto;

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
		EMP_MST entity = (EMP_MST) dto;

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
		EMP_MST entity = (EMP_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String empCode = entity.getEMP_CODE();

		return dao.getEMP_MSTByKaicodeEmpcode(kaiCode, empCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		EMP_MST entity = (EMP_MST) dto;
		return entity.getEMP_CODE();
	}

	/**
	 * ���[�U���擾
	 * 
	 * @param keys ���[�U�R�[�h
	 * @return List ���[�U���
	 */
	public List getREFItems(Map keys) {
		EMP_MST entity = (EMP_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity != null) {
			// ���ʂ̏�����
			List<Object> result = new ArrayList<Object>(6);

			String empCode = entity.getEMP_CODE();
			String empName = entity.getEMP_NAME_S();

			result.add(empCode);
			// ��s���̐ݒ�
			result.add(empName != null ? entity.getEMP_NAME_S() : "");

			result.add(entity.getSTR_DATE());
			// �I��
			result.add(entity.getEND_DATE());
			// ���ʂ̐ݒ�
			list.add(result);
		} else {
			// ���ʂ̍폜
			list.add(null);
		}
		// ���ʂ�Ԃ�
		return list;
	}

	/**
	 * �_�C�A���O�ɕ\������郆�[�U���X�g�擾
	 * 
	 * @param kaiCode
	 * @param empCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param user
	 * @param depCode
	 * @param beginCode
	 * @param endCode
	 * @return ���[�U���X�g
	 */
	public List<Object> refDailog(String kaiCode, String empCode, String sName, String kName, Timestamp termBasisDate,
		String user, String depCode, String beginCode, String endCode) {

		return dao.refDialogSearch(kaiCode, empCode, sName, kName, termBasisDate, user, depCode, beginCode, endCode);
	}
}

package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �v���O�����}�X�^����
 */
public class ProgramLogicImpl extends CommonLogicBaseImpl implements ProgramLogic {

	/** �v���O�����}�X�^Dao */
	protected PRG_MSTDao dao;

	/** �v���O�����}�X�^���� */
	protected PRG_MST prgentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao PRG_MSTDao
	 */
	public ProgramLogicImpl(PRG_MSTDao dao) {
		// �v���O�����}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * PRG_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity PRG_MST
	 */
	public void setEntity(PRG_MST entity) {
		// �v���O�����}�X�^���̂�Ԃ�
		this.prgentity = entity;
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
		String beginPrgCode = (String) conditions.get("beginPrgCode");
		// �I���R�[�h�̎擾
		String endPrgCode = (String) conditions.get("endPrgCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginPrgCode) && Util.isNullOrEmpty(endPrgCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllPRG_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginPrgCode)) {
			// ���ʂ�Ԃ�
			return dao.getPrgInfoTo(kaiCode, endPrgCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endPrgCode)) {
			// ���ʂ�Ԃ�
			return dao.getPrgInfoFrom(kaiCode, beginPrgCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getPrgInfo2(kaiCode, beginPrgCode, endPrgCode);
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
		// �V�X�e���R�[�h�̎擾
		String sysCode = (String) keys.get("sysCode");
		// �v���O�����R�[�h�̎擾
		String prgCode = (String) keys.get("prgCode");
		// ���ʂ�Ԃ�
		return dao.getPRG_MSTByKaicodeSyscodePrgcode(kaiCode, sysCode, prgCode);
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
			PRG_MST e = (PRG_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// �v���O�����R�[�h�̐ݒ�
			e2.setCode(e.getPRG_CODE());
			// �v���O�������̂̐ݒ�
			e2.setName_S(e.getPRG_NAME_S());
			// �v���O�����������̂̐ݒ�
			e2.setName_K(e.getPRG_NAME_K());
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
		PRG_MST entity = dao.getPRG_MSTByKaicodePrgcode(kaiCode, code);
		// ���̂�Ԃ�
		return (entity == null ? null : entity.getPRG_NAME_S());
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �V�X�e���R�[�h�̎擾
		String sysCode = (String) conditions.get("sysCode");
		// �v���O�����R�[�h�̎擾
		String prgCode = (String) conditions.get("prgCode");
		// ��ЃR�[�h�̐ݒ�
		prgentity.setKAI_CODE(kaiCode);
		// �V�X�e���R�[�h�̐ݒ�
		prgentity.setSYS_CODE(sysCode);
		// �v���O�����R�[�h�̐ݒ�
		prgentity.setPRG_CODE(prgCode);
		// �f�[�^���폜����
		dao.delete(prgentity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		PRG_MST entity = (PRG_MST) dto;

		dao.insert(entity);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// ���̂̏�����
		PRG_MST entity = (PRG_MST) dto;

		dao.update(entity);
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		PRG_MST entity = (PRG_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String sysCode = entity.getSYS_CODE();
		String prgCode = entity.getPRG_CODE();

		return dao.getPRG_MSTByKaicodeSyscodePrgcode(kaiCode, sysCode, prgCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		PRG_MST entity = (PRG_MST) dto;
		return entity.getPRG_CODE();
	}

	/**
	 * @see jp.co.ais.trans.master.logic.ProgramLogic#searchProgramList(java.lang.String)
	 */
	public List<PRG_MST> searchProgramList(String kaiCode) {
		return dao.getAllPRG_MST2(kaiCode);
	}
}

package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �E�v�}�X�^����
 */
public class MemoLogicImpl extends CommonLogicBaseImpl implements MemoLogic {

	/** �E�v�}�X�^Dao */
	protected TEK_MSTDao dao;

	/** �E�v�}�X�^���� */
	protected TEK_MST knr5entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao TEK_MSTDao
	 */
	public MemoLogicImpl(TEK_MSTDao dao) {
		// �E�v�}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * TEK_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity TEK_MST
	 */
	public void setEntity(TEK_MST entity) {
		// �E�v�}�X�^���̂�Ԃ�
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

		String tekKbn = (String) conditions.get("tekKbn");
		// �J�n�R�[�h�̎擾
		String beginTekCode = (String) conditions.get("beginTekCode");
		// �I���R�[�h�̎擾
		String endTekCode = (String) conditions.get("endTekCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginTekCode) && Util.isNullOrEmpty(endTekCode) && Util.isNullOrEmpty(tekKbn)) {
			// ���ʂ�Ԃ�
			return dao.getAllTEK_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginTekCode) && Util.isNullOrEmpty(tekKbn)) {
			// ���ʂ�Ԃ�
			return dao.getTekInfoTo(kaiCode, endTekCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endTekCode) && Util.isNullOrEmpty(tekKbn)) {
			// ���ʂ�Ԃ�
			return dao.getTekInfoFrom(kaiCode, beginTekCode);
		} else if (Util.isNullOrEmpty(beginTekCode) && Util.isNullOrEmpty(endTekCode)) {

			return dao.getAllTEK_MST3(kaiCode, tekKbn);

		} else if (Util.isNullOrEmpty(beginTekCode)) {

			return dao.getTekInfoTo2(kaiCode, endTekCode, tekKbn);

		} else if (Util.isNullOrEmpty(endTekCode)) {

			return dao.getTekInfoFrom2(kaiCode, beginTekCode, tekKbn);

			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else if (Util.isNullOrEmpty(tekKbn)) {
			// ���ʂ�Ԃ�
			return dao.getTekInfo2(kaiCode, beginTekCode, endTekCode);
		} else {
			return dao.getAllCode_MST2(kaiCode, beginTekCode, endTekCode, tekKbn);
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
		// �E�v�R�[�h�̎擾
		String tekCode = (String) keys.get("tekCode");
		// �E�v�敪�̎擾
		int tekKbn = Integer.parseInt((String) keys.get("tekKbn"));
		// ���ʂ�Ԃ�
		return dao.getTEK_MST(kaiCode, tekKbn, tekCode);
	}

	public List findREF(Map conditions) {
		// �R�[�h
		String code = (String) conditions.get("code");
		// ����
		String name_S = (String) conditions.get("name_S");
		// ��������
		String name_K = (String) conditions.get("name_K");
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			TEK_MST e = (TEK_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getTEK_CODE());
			e2.setName_S(e.getTEK_NAME());
			e2.setName_K(e.getTEK_NAME_K());
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		String kaiCode = (String) keys.get("kaiCode");
		// int tekKbn = Integer.parseInt((String)keys.get("tekKbn"));
		List result = dao.conditionSearch2(kaiCode, code);

		if (result.size() > 0) {
			TEK_MST entity = (TEK_MST) result.get(0);
			return entity.getTEK_NAME();
		}

		return null;
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �E�v�R�[�h�̎擾
		String tekCode = (String) conditions.get("tekCode");
		// �E�v�敪�̎擾
		int tekKbn = Integer.parseInt((String) conditions.get("tekKbn"));
		// ��ЃR�[�h�̐ݒ�
		knr5entity.setKAI_CODE(kaiCode);
		// �E�v�R�[�h�̐ݒ�
		knr5entity.setTEK_CODE(tekCode);
		// �E�v�敪�̐ݒ�
		knr5entity.setTEK_KBN(tekKbn);
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
		TEK_MST entity = (TEK_MST) dto;

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
		TEK_MST entity = (TEK_MST) dto;

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
		TEK_MST entity = (TEK_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		int tekKbn = entity.getTEK_KBN();
		String tekCode = entity.getTEK_CODE();

		return dao.getTEK_MST(kaiCode, tekKbn, tekCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		TEK_MST entity = (TEK_MST) dto;
		return entity.getTEK_CODE();
	}

	/**
	 * �E�v�R�[�h�ŏڍׂ��擾
	 * 
	 * @param keys
	 * @return �E�v���
	 */
	public List getREFItems(Map keys) {
		TEK_MST entity = (TEK_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity != null) {
			// ���ʂ̏�����
			List<Object> result = new ArrayList<Object>(6);

			String tekCode = entity.getTEK_CODE();
			String tekName = entity.getTEK_NAME();

			result.add(tekCode);
			// ��s���̐ݒ�
			result.add(tekName != null ? entity.getTEK_NAME() : "");

			result.add(entity.getDATA_KBN());

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
	 * �p�����[�^�����œE�v��񃊃X�g�K��
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param tekCode �E�v�R�[�h
	 * @param sName ����
	 * @param kName ��������
	 * @param memoType �s�E�v�^�C�v
	 * @param slipType �`�[�E�v�^�C�v
	 * @param termBasisDate �L������
	 * @return �E�v���X�g
	 */
	public List<Object> refDailog(String kaiCode, String tekCode, String sName, String kName, int memoType,
		String slipType, Timestamp termBasisDate) {

		return dao.refDialogSearch(kaiCode, tekCode, sName, kName, memoType, slipType, termBasisDate);
	}
}

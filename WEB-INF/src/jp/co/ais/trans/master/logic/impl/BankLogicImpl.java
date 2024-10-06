package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ��s�}�X�^����
 */
public class BankLogicImpl extends CommonLogicBaseImpl implements BankLogic {

	/** ��s�}�X�^Dao */
	private BNK_MSTDao dao;

	/** ��s�}�X�^���� */
	private BNK_MST entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao BNK_MSTDao
	 */
	public BankLogicImpl(BNK_MSTDao dao) {
		// ��s�}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * BNK_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity BNK_MST
	 */
	public void setEntity(BNK_MST entity) {
		// ��s�}�X�^���̂�Ԃ�
		this.entity = entity;
	}

	public List find(Map conditions) {
		// ��s�R�[�h
		String bnkCode = (String) conditions.get("bnkCode");
		// �J�n�R�[�h�̎擾
		String beginBnkStnCode = (String) conditions.get("beginBnkStnCode");
		// �I���R�[�h�̎擾
		String endBnkStnCode = (String) conditions.get("endBnkStnCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(bnkCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllBNK_MST2();
			// ��s�R�[�h�Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginBnkStnCode) && Util.isNullOrEmpty(endBnkStnCode)) {
			// ���ʂ�Ԃ�
			return dao.getBnkInfo1(bnkCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginBnkStnCode)) {
			// ���ʂ�Ԃ�
			return dao.getBnkInfo2To(bnkCode, endBnkStnCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endBnkStnCode)) {
			// ���ʂ�Ԃ�
			return dao.getBnkInfo2From(bnkCode, beginBnkStnCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getBnkInfo2FromTo(bnkCode, beginBnkStnCode, endBnkStnCode);
		}
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// ��ЃR�[�h�̎擾
		String bnkCode = (String) keys.get("bnkCode");
		// ��s�x�X�R�[�h�̎擾
		String bnkStnCode = (String) keys.get("bnkStnCode");
		// ���ʂ�Ԃ�
		return dao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, bnkStnCode);
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

		String kind = (String) conditions.get("kind");

		if ("Bank".equals(kind)) {
			// ���ʂ̎擾
			List list = dao.conditionSearch1(code, name_S, name_K);
			// �f�[�^�W�̏�����
			List list2 = new ArrayList(list.size());
			// ���ʂ̐ݒ�
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				// ���ʂ̎擾
				BNK_MST e = (BNK_MST) iterator.next();
				// ���̂̏�����
				REFEntity e2 = new REFEntity();
				// ��s�R�[�h�̐ݒ�
				e2.setCode(e.getBNK_CODE());
				// ��s���̂̐ݒ�
				e2.setName_S(e.getBNK_NAME_S());
				// ��s�������̂̐ݒ�
				e2.setName_K(e.getBNK_NAME_K());
				// �f�[�^�W�̐ݒ�
				list2.add(e2);
			}
			// ���ʂ�Ԃ�
			return list2;
		} else { // BankBranch
			String bnkCode = (String) conditions.get("bnkCode");

			String beginCode = (String) conditions.get("beginCode");
			String endCode = (String) conditions.get("endCode");

			List list = dao.conditionSearch2(bnkCode, code, name_S, name_K, beginCode, endCode);
			List list2 = new ArrayList(list.size());

			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				BNK_MST e = (BNK_MST) iterator.next();
				REFEntity e2 = new REFEntity();
				e2.setCode(e.getBNK_STN_CODE());
				e2.setName_S(e.getBNK_STN_NAME_S());
				e2.setName_K(e.getBNK_STN_NAME_K());
				list2.add(e2);
			}

			return list2;
		}
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys
	 */
	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");

		String kind = (String) keys.get("kind");

		if ("Bank".equals(kind)) {
			// ���̂̏�����
			BNK_MST entity1 = dao.getBNK_MST2(code);
			// ���̂�Ԃ�
			return (entity1 == null ? null : entity1.getBNK_NAME_S());
		} else { // BankBranch
			String bnkCode = (String) keys.get("bnkCode");
			BNK_MST entity1 = dao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, code);
			return (entity1 == null ? null : entity1.getBNK_STN_NAME_S());
		}

	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String bnkCode = (String) conditions.get("bnkCode");
		// ��s�x�X�R�[�h�̎擾
		String bnkStnCode = (String) conditions.get("bnkStnCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setBNK_CODE(bnkCode);
		// ��s�x�X�R�[�h�̐ݒ�
		entity.setBNK_STN_CODE(bnkStnCode);
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
		BNK_MST entity1 = (BNK_MST) dto;

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
		BNK_MST entity1 = (BNK_MST) dto;

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
		BNK_MST entity1 = (BNK_MST) dto;
		String bnkCode = entity1.getBNK_CODE();
		String BnkStnCode = entity1.getBNK_STN_CODE();

		return dao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, BnkStnCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @return String
	 */
	protected String getPrimaryCode() {
		// BNK_MST entity = (BNK_MST)dto;
		return null;
	}

	/**
	 * ��s�������擾 PK�����i�x�X�R�[�h���O�j
	 * 
	 * @param param
	 * @return ��s���
	 */
	public List<BNK_MST> getBankList(BnkMstParameter param) {
		return dao.getBankList(param);
	}

	/**
	 * ��s������񃊃X�g�擾 �i�x�X�R�[�h���O�j
	 * 
	 * @param param
	 * @return ��s��񃊃X�g
	 */
	public BNK_MST getBankName(BnkMstParameter param) {
		List<BNK_MST> bnkList = dao.getBankList(param);

		if (bnkList.isEmpty()) {
			return null;
		}

		return bnkList.get(0);
	}

	/**
	 * ��s�x�X���擾 PK����
	 * 
	 * @param param
	 * @return ��s�x�X��񃊃X�g
	 */
	public List<BNK_MST> getStnList(BnkMstParameter param) {
		return dao.getStnList(param);
	}

	/**
	 * ��s�x�X��񃊃X�g�擾
	 * 
	 * @param param
	 * @return ��s�x�X��񃊃X�g
	 */
	public BNK_MST getStnName(BnkMstParameter param) {
		List<BNK_MST> stnList = dao.getStnList(param);

		if (stnList.isEmpty()) {
			return null;
		}

		return stnList.get(0);
	}
}

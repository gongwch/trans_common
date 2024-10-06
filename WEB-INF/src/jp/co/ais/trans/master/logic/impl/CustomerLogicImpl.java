package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �����f�[�^���W�b�N
 */
public class CustomerLogicImpl extends CommonLogicBaseImpl implements CustomerLogic {

	/** �����}�X�^Dao */
	private TRI_MSTDao dao;

	/** �����}�X�^���� */
	private TRI_MST entity;

	/** ��ЃR�[�h */
	private String companyCode;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao TRI_MSTDao
	 */
	public CustomerLogicImpl(TRI_MSTDao dao) {
		// �����}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * TRI_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity TRI_MST
	 */
	public void setEntity(TRI_MST entity) {
		// �����}�X�^���̂�Ԃ�
		this.entity = entity;
	}

	/**
	 * @see jp.co.ais.trans.master.logic.CustomerLogic#setCompanyCode(java.lang.String)
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
		String beginTriCode = (String) conditions.get("beginTriCode");
		// �I���R�[�h�̎擾
		String endTriCode = (String) conditions.get("endTriCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginTriCode) && Util.isNullOrEmpty(endTriCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllTRI_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginTriCode)) {
			// ���ʂ�Ԃ�
			return dao.getTriInfoTo(kaiCode, endTriCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endTriCode)) {
			// ���ʂ�Ԃ�
			return dao.getTriInfoFrom(kaiCode, beginTriCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getTriInfo(kaiCode, beginTriCode, endTriCode);
		}
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		String kaiCode = (String) keys.get("kaiCode");// ��ЃR�[�h
		String triCode = (String) keys.get("triCode"); // �����R�[�h

		// ���ʂ�Ԃ�
		return dao.getTRI_MSTByKaicodeTricode(kaiCode, triCode);
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		String kind = (String) conditions.get("kind");
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
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, kind);
		// �f�[�^�W�̏�����
		List<Object> list2 = new ArrayList<Object>(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			TRI_MST e = (TRI_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// �����R�[�h�̐ݒ�
			e2.setCode(e.getTRI_CODE());
			// ����旪�̂̐ݒ�
			e2.setName_S(e.getTRI_NAME_S());
			// ����挟�����̂̐ݒ�
			e2.setName_K(e.getTRI_NAME_K());

			// �f�[�^�W�̐ݒ�
			list2.add(e2);
		}
		// ���ʂ�Ԃ�
		return list2;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys key Map
	 */
	public String findName(Map keys) {
		String kind = (String) keys.get("kind");
		// �R�[�h
		String code = (String) keys.get("code");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ���̂̏�����
		TRI_MST entity1 = dao.getTRI_MSTByKaicodeTricode(kaiCode, code);

		if (kind.equals("CustomerWithoutSumCode")) {
			if (entity1 != null && entity1.getSUM_CODE() != null) {
				entity1 = null;
			}
		}

		// ���̂�Ԃ�
		return (entity1 == null ? null : entity1.getTRI_NAME_S());
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �����R�[�h�̎擾
		String triCode = (String) conditions.get("triCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// �����R�[�h�̐ݒ�
		entity.setTRI_CODE(triCode);
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
		TRI_MST entity1 = (TRI_MST) dto;

		// �f�[�^��o�^����
		dao.insert(entity1);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 * @param oldDto
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// ���̂̏�����
		TRI_MST entity1 = (TRI_MST) dto;
		TRI_MST oldEntity = (TRI_MST) oldDto;

		// �ς��Ȃ����ڂ̒l��ݒ肷��
		entity1.setSPOT_DEN_NO(oldEntity.getSPOT_DEN_NO());

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
		TRI_MST entity1 = (TRI_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String triCode = entity1.getTRI_CODE();

		return dao.getTRI_MSTByKaicodeTricode(kaiCode, triCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		TRI_MST entity1 = (TRI_MST) dto;
		return entity1.getTRI_CODE();
	}

	/**
	 * ����挟��<br>
	 * �����̃{�^���t�B�[���h�̏������ɑΉ�����悤�ɐݒ�B
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param siire
	 * @param tokui
	 * @param beginCode
	 * @param endCode
	 * @return ������񃊃X�g
	 */
	public List<Object> refSearchCustomer(String kaiCode, String triCode, String sName, String kName,
		Timestamp termBasisDate, boolean siire, boolean tokui, String beginCode, String endCode) {
		return dao.searchRefCustomer(kaiCode, triCode, sName, kName, termBasisDate, siire, tokui, beginCode, endCode);
	}

	/**
	 * @see jp.co.ais.trans.master.logic.CustomerLogic#findCustomer(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public TRI_MST findCustomer(String customerCode, String beginCode, String endCode) {
		return dao.getTRI_MSTByBeginEnd(this.companyCode, customerCode, beginCode, endCode);
	}

	/**
	 * �˗����̍X�V
	 * 
	 * @param dto
	 */
	public void updateIraiName(TRI_MST dto) {
		dao.updateIraiName(dto);
	}
}

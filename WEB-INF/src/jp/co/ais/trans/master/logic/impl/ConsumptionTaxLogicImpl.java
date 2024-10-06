package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ����Ń}�X�^����
 */
public class ConsumptionTaxLogicImpl extends CommonLogicBaseImpl implements ConsumptionTaxLogic {

	/** ����Ń}�X�^Dao */
	protected SZEI_MSTDao dao;

	/** ����Ń}�X�^���� */
	protected SZEI_MST entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao SZEI_MSTDao
	 */
	public ConsumptionTaxLogicImpl(SZEI_MSTDao dao) {
		// ����Ń}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * SZEI_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity SZEI_MST
	 */
	public void setEntity(SZEI_MST entity) {
		// ����Ń}�X�^���̂�Ԃ�
		this.entity = entity;
	}

	public List find(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �J�n�R�[�h�̎擾
		String beginZeiCode = (String) conditions.get("beginZeiCode");
		// �I���R�[�h�̎擾
		String endZeiCode = (String) conditions.get("endZeiCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginZeiCode) && Util.isNullOrEmpty(endZeiCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllSZEI_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginZeiCode)) {
			// ���ʂ�Ԃ�
			return dao.getSzeiInfoTo(kaiCode, endZeiCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endZeiCode)) {
			// ���ʂ�Ԃ�
			return dao.getSzeiInfoFrom(kaiCode, beginZeiCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getSzeiInfo(kaiCode, beginZeiCode, endZeiCode);
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
		// ����ŃR�[�h�̎擾
		String zeiCode = (String) keys.get("zeiCode");
		// ���ʂ�Ԃ�
		return dao.getSZEI_MSTByKaicodeSzeicode(kaiCode, zeiCode);
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

		String kind = (String) conditions.get("kind");

		// ���ʂ̎擾
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, kind);
		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			SZEI_MST e = (SZEI_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// ����ŃR�[�h�̐ݒ�
			e2.setCode(e.getZEI_CODE());
			// ����ŗ��̂̐ݒ�
			e2.setName_S(e.getZEI_NAME_S());
			// ����Ō������̂̐ݒ�
			e2.setName_K(e.getZEI_NAME_K());
			// �f�[�^�W�̐ݒ�
			list2.add(e2);
		}
		// ���ʂ�Ԃ�
		return list2;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys
	 */
	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");

		String kind = (String) keys.get("kind");

		// ���̂̏�����
		SZEI_MST entity1 = dao.getSZEI_MSTByKaicodeSzeicode(kaiCode, code);

		if ("PurchaseConsumptionTax".equals(kind)) {
			if (entity1 != null && entity1.getUS_KBN() != 2) {
				entity1 = null;
			}
		}
		// ���̂�Ԃ�
		return (entity1 == null ? null : entity1.getZEI_NAME_S());
	}

	/**
	 * F �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// ����ŃR�[�h�̎擾
		String zeiCode = (String) conditions.get("zeiCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// ����ŃR�[�h�̐ݒ�
		entity.setZEI_CODE(zeiCode);
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
		SZEI_MST entity1 = (SZEI_MST) dto;

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
		SZEI_MST entity1 = (SZEI_MST) dto;
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
		SZEI_MST entity1 = (SZEI_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String zeiCode = entity1.getZEI_CODE();

		return dao.getSZEI_MSTByKaicodeSzeicode(kaiCode, zeiCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		SZEI_MST entity1 = (SZEI_MST) dto;
		return entity1.getZEI_CODE();
	}

	public List getREFItems(Map keys) {
		SZEI_MST entity1 = (SZEI_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity1 != null) {
			// ���ʂ̏�����
			List<Object> result = new ArrayList<Object>(7);

			String zeiCode = entity1.getZEI_CODE();
			String zeiName = entity1.getZEI_NAME_S();

			result.add(zeiCode);

			result.add(zeiName != null ? entity1.getZEI_NAME_S() : "");

			result.add(entity1.getUS_KBN());

			result.add(entity1.getSTR_DATE());
			// �I��
			result.add(entity1.getEND_DATE());
			// �ŗ�
			result.add(Util.isNullOrEmpty(entity1.getZEI_RATE()) ? "" : String.valueOf(entity1.getZEI_RATE()));
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
	 * ����Ń��X�g����
	 * 
	 * @return ����Ń��X�g
	 * @see jp.co.ais.trans.master.logic.ConsumptionTaxLogic#refDailog(java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 *      java.sql.Timestamp)
	 */
	public List<Object> refDailog(String kaiCode, String zeiCode, String sName, String kName, String sales,
		String purchase, String elseTax, Timestamp termBasisDate) {

		return dao.refDialogSearch(kaiCode, zeiCode, sName, kName, sales, purchase, elseTax, termBasisDate);
	}
}

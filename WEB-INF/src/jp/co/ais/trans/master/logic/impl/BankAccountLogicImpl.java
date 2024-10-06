package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ��s�����f�[�^����
 */
public class BankAccountLogicImpl extends CommonLogicBaseImpl implements BankAccountLogic {

	/** ��s�����}�X�^Dao */
	private AP_CBK_MSTDao dao;

	/** ��s�����}�X�^ */
	private AP_CBK_MST entity;

	/** ��s�}�X�^Dao */
	protected BNK_MSTDao bNK_MSTDao;

	private AP_CBK_MSTDao2 aP_CBK_MSTDao2;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao AP_CBK_MSTDao
	 */
	public BankAccountLogicImpl(AP_CBK_MSTDao dao) {
		// ��s����Ͻ�Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * AP_CBK_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity AP_CBK_MST
	 */
	public void setEntity(AP_CBK_MST entity) {
		// ��s�����}�X�^���̂�Ԃ�
		this.entity = entity;
	}

	/**
	 * �f�t�H���g�R���X�g���N�^. dicon�t�@�C����Dao�̃R���|�[�l���g���`���A �R���X�g���N�^�̈����Ƃ��ĊY��Dao���`����ƁA �����I�Ɉ����Ƃ��Ă킽���Ă���.
	 * 
	 * @param dao BNK_MSTDao
	 */
	public void setBNK_MSTDao(BNK_MSTDao dao) {
		// ��s�}�X�^Dao��Ԃ�
		this.bNK_MSTDao = dao;
	}

	/**
	 * �f�t�H���g�R���X�g���N�^. dicon�t�@�C����Dao�̃R���|�[�l���g���`���A �R���X�g���N�^�̈����Ƃ��ĊY��Dao���`����ƁA �����I�Ɉ����Ƃ��Ă킽���Ă���.
	 * 
	 * @param dao AP_CBK_MSTDao2
	 */
	public void setAP_CBK_MSTDao2(AP_CBK_MSTDao2 dao) {
		// ��s�}�X�^Dao��Ԃ�
		this.aP_CBK_MSTDao2 = dao;
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
		String beginCbkCbkCode = (String) conditions.get("beginCbkCbkCode");
		// �I���R�[�h�̎擾
		String endCbkCbkCode = (String) conditions.get("endCbkCbkCode");

		return aP_CBK_MSTDao2.query(kaiCode, beginCbkCbkCode, endCbkCbkCode);
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ��s�����R�[�h�̎擾
		String cbkCbkCode = (String) keys.get("cbkCbkCode");
		// ���ʂ�Ԃ�
		return dao.getAP_CBK_MSTByKaicodecbkcbkcode(kaiCode, cbkCbkCode);
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

		String langCode = (String) conditions.get("langCode");
		String word1 = MessageUtil.getWord(langCode, "C00463");
		String word2 = MessageUtil.getWord(langCode, "C00397");
		String word3 = MessageUtil.getWord(langCode, "C00045");
		String word4 = MessageUtil.getWord(langCode, "C00368");
		String wordUnknown = "(" + MessageUtil.getWord(langCode, "C00035") + ")";

		// ���ʂ̎擾
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, word1, word2, word3, word4,
			wordUnknown, ServerConfig.getTableSpaceName());
		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			AP_CBK_MST e = (AP_CBK_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// ��s�����R�[�h�̐ݒ�
			e2.setCode(e.getCBK_CBK_CODE());
			// ��s�������̂̐ݒ�
			e2.setName_S(e.getCBK_NAME());
			// ��s�������̌����̐ݒ�
			e2.setName_K(e.getCBK_YKN_NO());
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
		// ���̂̏�����
		AP_CBK_MST entity1 = dao.getAP_CBK_MSTByKaicodecbkcbkcode(kaiCode, code);
		// ���̂�Ԃ�
		return (entity1 == null ? null : entity1.getCBK_NAME());
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// ��s�����R�[�h�̎擾
		String cbkCbkCode = (String) conditions.get("cbkCbkCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
		// ��s�����R�[�h�̐ݒ�
		entity.setCBK_CBK_CODE(cbkCbkCode);
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
		AP_CBK_MST entity1 = (AP_CBK_MST) dto;
		// �o�^���t�̐ݒ�
		entity1.setCBK_INP_DATE(Util.getCurrentDate());

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
		AP_CBK_MST entity1 = (AP_CBK_MST) dto;
		AP_CBK_MST oldEntity = (AP_CBK_MST) oldDto;

		// �o�^���t�̐ݒ�
		entity1.setCBK_INP_DATE(oldEntity.getCBK_INP_DATE());

		// �f�[�^���X�V����
		dao.update(entity1);
	}

	public List getREFItems(Map keys) {
		AP_CBK_MST entity1 = (AP_CBK_MST) this.findOne(keys);
		List list = new ArrayList(1);

		if (entity1 != null) {
			// ���ʂ̏�����
			List result = new ArrayList(10);
			// ��s�R�[�h�̎擾
			String bnkCode = entity1.getCBK_BNK_CODE();
			// �x�X�R�[�h�̎擾
			String stnCode = entity1.getCBK_STN_CODE();
			// Dao�̏�����
			BNK_MST bnkMst = bNK_MSTDao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, stnCode);
			// ��s�R�[�h�̐ݒ�
			result.add(bnkCode);
			// ��s���̐ݒ�
			result.add(bnkMst != null ? bnkMst.getBNK_NAME_S() : "");
			// �x�X�R�[�h�̐ݒ�
			result.add(stnCode);
			// �x�X���̐ݒ�
			result.add(bnkMst != null ? bnkMst.getBNK_STN_NAME_S() : "");
			// �a����ڂ̐ݒ�
			result.add(entity1.getCBK_YKN_KBN());
			// �����ԍ��̐ݒ�
			result.add(entity1.getCBK_YKN_NO());
			// �J�n���t��
			result.add(entity1.getSTR_DATE());
			// �I��
			result.add(entity1.getEND_DATE());
			// �Ј��x���敪
			result.add(entity1.getCBK_EMP_FB_KBN());
			// �ЊO�x���敪
			result.add(entity1.getCBK_OUT_FB_KBN());
			// ��s������
			result.add(entity1.getCBK_NAME());
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
	 * �ҏW���̃��R�[�h�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		AP_CBK_MST entity1 = (AP_CBK_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String cbkcbkCode = entity1.getCBK_CBK_CODE();

		return dao.getAP_CBK_MSTByKaicodecbkcbkcode(kaiCode, cbkcbkCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		AP_CBK_MST entity1 = (AP_CBK_MST) dto;
		return entity1.getCBK_CBK_CODE();
	}

	/**
	 * ��s��������
	 * 
	 * @param kaiCode
	 * @param code
	 * @param nameS
	 * @param nameK
	 * @param outkbn
	 * @param empkbn
	 * @param termBasisDate
	 * @return ��s�������X�g
	 */
	public List<Object> searchReservationBankAccount(String kaiCode, String code, String nameS, String nameK,
		boolean outkbn, boolean empkbn, Timestamp termBasisDate) {
		return dao.searchCommonCbkMstData(kaiCode, code, nameS, nameK, outkbn, empkbn, termBasisDate);
	}

	/**
	 * �f�t�H���g�̌��������擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return ��s�������
	 */
	public Map<String, Object> getDefaultReceivedAccount(String kaiCode) {
		AP_CBK_MST cbkMst = dao.getDefaultReceivedAccount(kaiCode, ServerConfig.getProperty("nss.default.cbkcode"));

		Map<String, Object> map = new HashMap<String, Object>();

		if (Util.isNullOrEmpty(cbkMst)) {
			return map;
		}

		map.put("cbkCode", Util.avoidNull(cbkMst.getCBK_CBK_CODE()));
		map.put("cbkName", Util.avoidNull(cbkMst.getCBK_NAME()));
		return map;
	}
}

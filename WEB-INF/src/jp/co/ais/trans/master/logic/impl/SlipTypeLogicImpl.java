package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �`�[��ʃf�[�^����
 */
public class SlipTypeLogicImpl extends CommonLogicBaseImpl implements SlipTypeLogic {

	/** �`�[��ʃ}�X�^Dao */
	protected  DEN_SYU_MSTDao dao;

	/** �`�[��ʃ}�X�^���� */
	protected DEN_SYU_MST densyuentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao DEN_SYU_MSTDao
	 */
	public SlipTypeLogicImpl(DEN_SYU_MSTDao dao) {
		// �`�[��ʃ}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * DEN_SYU_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity DEN_SYU_MST
	 */
	public void setEntity(DEN_SYU_MST entity) {
		// �`�[��ʃ}�X�^���̂�Ԃ�
		this.densyuentity = entity;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// String strDate=(String)conditions.get("strDate");
		// �J�n�R�[�h�̎擾
		String beginDenSyuCode = (String) conditions.get("beginDenSyuCode");
		// �I���R�[�h�̎擾
		String endDenSyuCode = (String) conditions.get("endDenSyuCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginDenSyuCode) && Util.isNullOrEmpty(endDenSyuCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllDEN_SYU_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginDenSyuCode)) {
			// ���ʂ�Ԃ�
			return dao.getDenSyuInfoTo(kaiCode, endDenSyuCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endDenSyuCode)) {
			// ���ʂ�Ԃ�
			return dao.getDenSyuInfoFrom(kaiCode, beginDenSyuCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getDenSyuInfo(kaiCode, beginDenSyuCode, endDenSyuCode);
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
		// �`�[��ʺ��� �̎擾
		String denSyuCode = (String) keys.get("denSyuCode");
		// String strDate =Util.convertToString((Date)keys.get("strDate")) ;
		// Date strDate =(Date)keys.get("strDate");
		// ���ʂ�Ԃ�
		return dao.getDEN_SYU_MSTByKaicodeDensyucode(kaiCode, denSyuCode);
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		// �R�[�h
		String denSyuCode = (String) conditions.get("code");
		// ����
		String name_S = (String) conditions.get("name_S");
		// ��������
		String name_K = (String) conditions.get("name_K");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		// ���ʂ̎擾
		List list = dao.conditionSearch(kaiCode, denSyuCode, name_S, name_K, beginCode, endCode);
		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			DEN_SYU_MST e = (DEN_SYU_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// �`�[��ʺ��� �̐ݒ�
			e2.setCode(e.getDEN_SYU_CODE());
			// �`�[��ʖ��̂̐ݒ�
			e2.setName_S(e.getDEN_SYU_NAME_S());
			// �`�[��ʗ��̂̐ݒ�
			e2.setName_K(e.getDEN_SYU_NAME_K());
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
		String denSyuCode = (String) keys.get("code");
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) keys.get("kaiCode");
		// ���̂̏�����
		DEN_SYU_MST entity = dao.getDEN_SYU_MSTByKaicodeDensyucode(kaiCode, denSyuCode);
		// ���̂�Ԃ�
		return (entity == null ? null : entity.getDEN_SYU_NAME_S());
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �`�[��ʺ��� �̎擾
		String denSyuCode = (String) conditions.get("denSyuCode");
		// Date strDate = (Date) conditions.get("strDate");
		// ��ЃR�[�h�̐ݒ�
		densyuentity.setKAI_CODE(kaiCode);
		// �`�[��ʺ��� �̐ݒ�
		densyuentity.setDEN_SYU_CODE(denSyuCode);
		// entity.setSTR_DATE(strDate);
		// �f�[�^���폜����
		dao.delete(densyuentity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;

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
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;

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
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String densyuCode = entity.getDEN_SYU_CODE();

		return dao.getDEN_SYU_MSTByKaicodeDensyucode(kaiCode, densyuCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;
		return entity.getDEN_SYU_CODE();
	}

	/**
	 * �`�[��ʗ��̎擾<BR>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param isIncludeSystemEls true:���V�X�e�������荞�񂾓`�[��ʂ�ΏۂƂ���
	 * @return �`�[��ʗ��̃��X�g
	 */
	public List<DEN_SYU_MST> getDenSyuNames(String kaiCode, boolean isIncludeSystemEls) {

		// �`�[��ʗ��̃��X�g
		return dao.getDenSyuList(kaiCode, isIncludeSystemEls);
	}
}

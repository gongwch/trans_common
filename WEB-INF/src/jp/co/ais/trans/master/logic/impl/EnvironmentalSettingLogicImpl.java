package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���ݒ�}�X�^����
 */
public class EnvironmentalSettingLogicImpl extends CommonLogicBaseImpl {

	/** ���ݒ�}�X�^Dao */
	private ENV_MSTDao dao;

	/** ���ݒ�}�X�^���� */
	private ENV_MST envMstentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao ENV_MSTDao
	 */
	public EnvironmentalSettingLogicImpl(ENV_MSTDao dao) {
		// ���ݒ�}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * ENV_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity ENV_MST
	 */
	public void setEntity(ENV_MST entity) {
		// ���ݒ�}�X�^���̂�Ԃ�
		this.envMstentity = entity;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");

		if (Util.isNullOrEmpty(kaiCode)) {
			// ���ʂ�Ԃ�
			return dao.getENV_MSTByKaicode1();
		} else {
			List list = new ArrayList();
			ENV_MST envMst = dao.getENV_MSTByKaicode(kaiCode);
			list.add(envMst);
			// ���ʂ�Ԃ�
			return list;
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

		// ���ʂ�Ԃ�
		return dao.getENV_MSTByKaicode(kaiCode);
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		// ���O�C����ЃR�[�h
		String loginCompCode = Util.avoidNull(conditions.get("LOGIN_KAI_CODE"));
		// ���͉�ЃR�[�h
		String inCompCode = "";
		String inCompName_S = "";
		if (Util.avoidNull(conditions.get("refFlg")).equals("1")) {
			// ���͉�ЃR�[�h
			inCompCode = Util.avoidNull(conditions.get("code"));
			// ���͉�З���
			inCompName_S = Util.avoidNull(conditions.get("name_S"));
		} else {
			// ���͉�ЃR�[�h
			inCompCode = Util.avoidNull(conditions.get("KAI_CODE"));
			// ���͉�З���
			inCompName_S = Util.avoidNull(conditions.get("KAI_NAME_S"));
		}

		// �g�D�R�[�h
		String dpkSsk = Util.avoidNull(conditions.get("DPK_SSK"));
		// �z�����(0:�܂� 1:�܂܂Ȃ�)
		String cmpKbn = Util.avoidNull(conditions.get("CMP_KBN"));
		// ��ʉ�к���
		String upCmpCode = Util.avoidNull(conditions.get("UP_CMP_CODE"));
		// �K�w����
		String dpkLvl = Util.avoidNull(conditions.get("DPK_LVL"));
		// ������к���
		String baseCmpCode = Util.avoidNull(conditions.get("BASE_CMP_CODE"));
		// �����K�w����
		String baseDpkLvl = Util.avoidNull(conditions.get("BASE_DPK_LVL"));

		List list = null;
		if (dpkSsk != null && !"".equals(dpkSsk)) {
			// �f�[�^���擾����B
			list = dao.conditionLvlSearch(loginCompCode, inCompCode, inCompName_S, dpkSsk, cmpKbn, upCmpCode, dpkLvl,
				baseCmpCode, baseDpkLvl);
		} else {
			// �f�[�^���擾����B
			list = dao.conditionSearch(inCompCode, inCompName_S);
		}

		// �f�[�^�W�̏�����
		List list2 = new ArrayList(list.size());
		// ���ʂ̐ݒ�
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// ���ʂ̎擾
			ENV_MST e = (ENV_MST) iterator.next();
			// ���̂̏�����
			REFEntity e2 = new REFEntity();
			// ��ЃR�[�h�̐ݒ�
			e2.setCode(e.getKAI_CODE());
			// ��З��̂̐ݒ�
			e2.setName_S(e.getKAI_NAME_S());
			// ��Ќ������̂̐ݒ�
			e2.setName_K("");
			// �f�[�^�W�̐ݒ�
			list2.add(e2);
		}
		// ���ʂ�Ԃ�
		return list2;
	}

	/**
	 * ���̂��擾����
	 * 
	 * @param keys Map
	 */
	public String findName(Map keys) {
		// �R�[�h
		String code = (String) keys.get("code");
		// ���̂̏�����
		ENV_MST entity = dao.getENV_MSTByKaicode(code);
		// ���̂�Ԃ�
		return (entity == null ? null : entity.getKAI_NAME_S());
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// ��Ж��̂̎擾
		String kaiName = (String) conditions.get("kaiName");
		// ��ЃR�[�h�̐ݒ�
		envMstentity.setKAI_CODE(kaiCode);
		// ��Ж��̂̐ݒ�
		envMstentity.setKAI_NAME(kaiName);
		// �f�[�^���폜����
		dao.delete(envMstentity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		ENV_MST entity = (ENV_MST) dto;

		// �f�[�^��o�^����
		dao.insert(entity);
	}

	/**
	 * �f�[�^���X�V����
	 * 
	 * @param dto Object
	 * @param oldDto
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// ���̂̏�����
		ENV_MST entity = (ENV_MST) dto;
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
		ENV_MST entity = (ENV_MST) dto;
		String kaiCode = entity.getKAI_CODE();

		return dao.getENV_MSTByKaicode(kaiCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		ENV_MST entity = (ENV_MST) dto;
		return entity.getKAI_CODE();
	}
}

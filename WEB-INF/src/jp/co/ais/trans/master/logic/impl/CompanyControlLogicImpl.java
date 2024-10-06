package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ��ЃR���g���[���}�X�^����
 */
public class CompanyControlLogicImpl extends CommonLogicBaseImpl implements CompanyControlLogic {

	/** ��ЃR���g���[���}�X�^Dao */
	private CMP_MSTDao dao;

	/** ��ЃR���g���[���}�X�^���� */
	private CMP_MST entity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao CMP_MSTDao
	 */
	public CompanyControlLogicImpl(CMP_MSTDao dao) {
		// ��ЃR���g���[���}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * CMP_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity CMP_MST
	 */
	public void setEntity(CMP_MST entity) {
		// ��ЃR���g���[���}�X�^���̂�Ԃ�
		this.entity = entity;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {

		// ��ЃR�[�h�̎擾
		String kaiCode = Util.avoidNull(conditions.get("kaiCode"));
		String prgCode = Util.avoidNull(conditions.get("prgCode"));

		// ���ʂ�Ԃ�
		return dao.getAllCMP_MST(kaiCode,prgCode);
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
		return dao.getCMP_MST_ByKaicode(kaiCode);
	}

	public Object findOne(String kaiCode) {
		// ���ʂ�Ԃ�
		return dao.getCMP_MST_ByKaicode(kaiCode);
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// ��ЃR�[�h�̐ݒ�
		entity.setKAI_CODE(kaiCode);
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
		CMP_MST entity1 = (CMP_MST) dto;

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
		CMP_MST entity1 = (CMP_MST) dto;

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
		CMP_MST entity1 = (CMP_MST) dto;
		String kaiCode = entity1.getKAI_CODE();

		return dao.getCMP_MST_ByKaicode(kaiCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		CMP_MST entity1 = (CMP_MST) dto;
		return entity1.getKAI_CODE();
	}

	public List getREFItems(Map keys) {
		CMP_MST entity1 = (CMP_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity1 != null) {
			// ���ʂ̏�����
			List<Object> result = new ArrayList<Object>(6);

			result.add(entity1.getCMP_KMK_NAME());

			result.add(entity1.getCMP_HKM_NAME());
			// �I��
			result.add(entity1.getCMP_UKM_KBN());

			result.add(entity1.getCMP_UKM_NAME());
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

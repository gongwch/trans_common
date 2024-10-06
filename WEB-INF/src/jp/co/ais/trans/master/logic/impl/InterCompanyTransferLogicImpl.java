package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ��Њԕt�փf�[�^����
 */
public class InterCompanyTransferLogicImpl extends CommonLogicBaseImpl {

	/** ��Њԕt�փ}�X�^Dao */
	private KTK_MSTDao dao;

	/** ��Њԕt�փ}�X�^���� */
	private KTK_MST ktkMstentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao KTK_MSTDao
	 */
	public InterCompanyTransferLogicImpl(KTK_MSTDao dao) {
		// ��Њԕt�փ}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * KTK_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity KTK_MST
	 */
	public void setEntity(KTK_MST entity) {
		// ��Њԕt�փ}�X�^���̂�Ԃ�
		this.ktkMstentity = entity;
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
		String beginKtkKaiCode = (String) conditions.get("beginKtkKaiCode");
		// �I���R�[�h�̎擾
		String endKtkKaiCode = (String) conditions.get("endKtkKaiCode");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginKtkKaiCode) && Util.isNullOrEmpty(endKtkKaiCode)) {
			// ���ʂ�Ԃ�
			return dao.getAllKTK_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginKtkKaiCode)) {
			// ���ʂ�Ԃ�
			return dao.getKtkInfoTo(kaiCode, endKtkKaiCode);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endKtkKaiCode)) {
			// ���ʂ�Ԃ�
			return dao.getKtkInfoFrom(kaiCode, beginKtkKaiCode);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getKtkInfo(kaiCode, beginKtkKaiCode, endKtkKaiCode);
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
		// �t�։�ЃR�[�h�̎擾
		String ktkKaiCode = (String) keys.get("ktkKaiCode");
		// ���ʂ�Ԃ�
		return dao.getKTK_MSTByKaicodeKtkkaicode(kaiCode, ktkKaiCode);
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// �t�։�ЃR�[�h�̎擾
		String ktkKaiCode = (String) conditions.get("ktkKaiCode");
		// ��ЃR�[�h�̐ݒ�
		ktkMstentity.setKAI_CODE(kaiCode);
		// �t�։�ЃR�[�h�̐ݒ�
		ktkMstentity.setKTK_KAI_CODE(ktkKaiCode);
		// �f�[�^���폜����
		dao.delete(ktkMstentity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		KTK_MST entity = (KTK_MST) dto;

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
		KTK_MST entity = (KTK_MST) dto;

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
		KTK_MST entity = (KTK_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String ktkkaiCode = entity.getKTK_KAI_CODE();

		return dao.getKTK_MSTByKaicodeKtkkaicode(kaiCode, ktkkaiCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		KTK_MST entity = (KTK_MST) dto;
		return entity.getKTK_KAI_CODE();
	}
}

package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �J�����x���f�[�^����
 */
public class IndicationLevelLogicImpl extends CommonLogicBaseImpl {

	/** �J�����x���}�X�^Dao */
	protected KJL_MSTDao dao;

	/** �J�����x���}�X�^���� */
	protected KJL_MST kjlMstentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao KJL_MSTDao
	 */
	public IndicationLevelLogicImpl(KJL_MSTDao dao) {
		// �J�����x���}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * KJL_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity KJL_MST
	 */
	public void setEntity(KJL_MST entity) {
		// �J�����x���}�X�^���̂�Ԃ�
		this.kjlMstentity = entity;
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
		String beginKjlUsrId = (String) conditions.get("beginKjlUsrId");
		// �I���R�[�h�̎擾
		String endKjlUsrId = (String) conditions.get("endKjlUsrId");
		// �J�n�R�[�h���I���R�[�h���Ȃ��ꍇ
		if (Util.isNullOrEmpty(beginKjlUsrId) && Util.isNullOrEmpty(endKjlUsrId)) {
			// ���ʂ�Ԃ�
			return dao.getAllKJL_MST2(kaiCode);
			// �J�n�R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(beginKjlUsrId)) {
			// ���ʂ�Ԃ�
			return dao.getIndicationLevelInfoTo(kaiCode, endKjlUsrId);
			// �I���R�[�h�����Ȃ��ꍇ
		} else if (Util.isNullOrEmpty(endKjlUsrId)) {
			// ���ʂ�Ԃ�
			return dao.getIndicationLevelInfoFrom(kaiCode, beginKjlUsrId);
			// �J�n�R�[�h�ƏI���R�[�h�S������ꍇ
		} else {
			// ���ʂ�Ԃ�
			return dao.getIndicationLevelInfo(kaiCode, beginKjlUsrId, endKjlUsrId);
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
		// ���[�U�[�R�[�h�̎擾
		String kjlUsrId = (String) keys.get("kjlUsrId");
		// �Ȗڑ̌n�R�[�h
		String kjlKmtCode = (String) keys.get("kjlKmtCode");
		// �g�D�R�[�h
		String kjlDpkSsk = (String) keys.get("kjlDpkSsk");

		// ���ʂ�Ԃ�
		return dao.getKJL_MSTByKaicodeKjlusridKjlkmtcodeKjldpkssk(kaiCode, kjlUsrId, kjlKmtCode, kjlDpkSsk);
	}

	/**
	 * �f�[�^���폜����
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = (String) conditions.get("kaiCode");
		// ���[�U�[�R�[�h�̎擾
		String kjlUsrId = (String) conditions.get("kjlUsrId");
		String kjlKmtCode = (String) conditions.get("kjlKmtCode");
		String kjlDpkSsk = (String) conditions.get("kjlDpkSsk");
		// ��ЃR�[�h�̐ݒ�
		kjlMstentity.setKAI_CODE(kaiCode);
		kjlMstentity.setKJL_USR_ID(kjlUsrId);
		kjlMstentity.setKJL_KMT_CODE(kjlKmtCode);
		kjlMstentity.setKJL_DPK_SSK(kjlDpkSsk);
		// ���[�U�[�R�[�h�̐ݒ�

		// �f�[�^���폜����
		dao.delete(kjlMstentity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		KJL_MST entity = (KJL_MST) dto;

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
		KJL_MST entity = (KJL_MST) dto;

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
		KJL_MST entity = (KJL_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String kjlusrID = entity.getKJL_USR_ID();
		String kjlkmtCode = entity.getKJL_KMT_CODE();
		String kjldpkSSK = entity.getKJL_DPK_SSK();

		return dao.getKJL_MSTByKaicodeKjlusridKjlkmtcodeKjldpkssk(kaiCode, kjlusrID, kjlkmtCode, kjldpkSSK);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @return String
	 */
	protected String getPrimaryCode() {
		// KJL_MST entity = (KJL_MST)dto;
		return null;
	}
}

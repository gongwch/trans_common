package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * GL�R���g���[���}�X�^����
 */
public class GLControlLogicImpl extends CommonLogicBaseImpl {

	/** GL�R���g���[���}�X�^Dao */
	private GL_CTL_MSTDao dao;

	/** GL�R���g���[���}�X�^���� */
	private GL_CTL_MST glCtlMstentity;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao GL_CTL_MSTDao
	 */
	public GLControlLogicImpl(GL_CTL_MSTDao dao) {
		// GL�R���g���[���}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * GL_CTL_MST�C���X�^���X�̐ݒ�.
	 * 
	 * @param entity GL_CTL_MST
	 */
	public void setEntity(GL_CTL_MST entity) {
		// GL�R���g���[���}�X�^���̂�Ԃ�
		this.glCtlMstentity = entity;
	}

	/**
	 * ���ʂ���������
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// ��ЃR�[�h�̎擾
		String kaiCode = Util.avoidNull(conditions.get("kaiCode"));

		// ���ʂ�Ԃ�
		return dao.getAllGL_CTL_MST(kaiCode);
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
		return dao.getGL_CTL_MST(kaiCode);
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
		glCtlMstentity.setKAI_CODE(kaiCode);
		// ���ʂ�Ԃ�
		dao.delete(glCtlMstentity);
	}

	/**
	 * �f�[�^��o�^����
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// ���̂̏�����
		GL_CTL_MST entity = (GL_CTL_MST) dto;

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
		GL_CTL_MST entity = (GL_CTL_MST) dto;

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
		GL_CTL_MST entity = (GL_CTL_MST) dto;
		String kaiCode = entity.getKAI_CODE();

		return dao.getGL_CTL_MSTByIKaicode(kaiCode);
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B �v���p�[�e�B�uEntity�v����A��L�[���擾���A �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		GL_CTL_MST entity = (GL_CTL_MST) dto;
		return entity.getKAI_CODE();
	}
}

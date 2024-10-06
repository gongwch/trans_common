package jp.co.ais.trans.master.logic.impl;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �}�X�^�x�[�X���W�b�N
 */
public abstract class CommonLogicBaseImpl implements CommonLogic {

	/** ���[�U�[ID */
	String userID = null;

	/**
	 * �ꗗ��ʂł̌���
	 * 
	 * @conditions ���������iMap�`���j
	 */
	@SuppressWarnings("unused")
	public List find(Map conditions) throws ParseException {
		return null;
	}

	/**
	 * ����̃��R�[�h�̌���
	 * 
	 * @keys ���R�[�h�̎�L�[�iMap�`���j
	 */
	@SuppressWarnings("unused")
	public Object findOne(Map keys) throws ParseException {
		return null;
	}

	/**
	 * REF�_�C�A���O�p�̌���
	 * 
	 * @conditions ���������iMap�`���A���Ȃ��Ƃ�code/name_S/name_K�O�������܂ށj
	 */
	@SuppressWarnings("unused")
	public List findREF(Map conditions) throws ParseException {
		return null;
	}

	/**
	 * ButtonField�ŃR�[�h����͎��A���̂̌���
	 * 
	 * @keys ���������iMap�`���AfindREF�Ɠ��������j
	 */
	@SuppressWarnings("unused")
	public String findName(Map keys) throws ParseException {
		return null;
	}

	/**
	 * �V�K�ꍇ�̎�L�[�d���̉���`�F�b�N
	 * 
	 * @keys ���R�[�h�̎�L�[�iMap�`���j
	 */
	public boolean checkCode(Map keys) throws ParseException {
		Object entity = findOne(keys);
		return (entity != null);
	}

	/**
	 * ���R�[�h�̍폜
	 * 
	 * @keys ���R�[�h�̎�L�[�iMap�`���j
	 */
	@SuppressWarnings("unused")
	public void delete(Map keys) throws ParseException {
		//
	}

	/**
	 * ���R�[�h�̐V�K�i�Y�����R�[�h���ɑ��݂���ꍇ�́A�G���[���X���[�j<br>
	 * ���ʃN���X�ɂ́AinsertImpl���\�b�h���������Ă��������B
	 * 
	 * @dto ���R�[�h
	 */
	public void insert(Object dto) throws TException {
		// ���݃`�F�b�N
		Object oldDto = getOldEntity(dto);

		if (oldDto != null) {
			// �G���[�ʒm(���łɑ��݂��܂�)
			String code = getPrimaryCode(dto);

			if (code == null) {
				throw new TException("W00005");
			} else {
				throw new TException("W00005", code);
			}
		}

		MasterBase entity = (MasterBase) dto;

		// ���[�U�[ID�̐ݒ�
		entity.setUSR_ID(userID);

		// �o�^���t�̐ݒ�
		entity.setINP_DATE(Util.getCurrentDate());

		// �X�V���t��NULL�ɂ���
		entity.setUPD_DATE(null);

		insertImpl(entity);
	}

	/**
	 * ���R�[�h�̕ύX�i�Y�����R�[�h�͑��݂��Ȃ��ꍇ�́A�G���[���X���[�j<br>
	 * ���ʃN���X�ɂ́AupdateImpl���\�b�h���������Ă��������B
	 * 
	 * @dto ���R�[�h
	 */
	public void update(Object dto) throws TException {
		// ���݃`�F�b�N
		MasterBase oldDto = (MasterBase) getOldEntity(dto);

		if (oldDto == null) {
			// �G���[�ʒm�i�Y���R�[�h�͑��݂��܂���j
			String code = getPrimaryCode(dto);

			if (code == null) {
				throw new TException("W00081");
			} else {
				throw new TException("W00081", code);
			}
		}

		MasterBase entity = (MasterBase) dto;

		// ���[�U�[ID�̐ݒ�
		entity.setUSR_ID(userID);
		// �ς��Ȃ����ڂ̒l��ݒ肷��
		entity.setINP_DATE(oldDto.getINP_DATE());
		// �X�V���t�̐ݒ�
		entity.setUPD_DATE(Util.getCurrentDate());

		updateImpl(entity, oldDto);
	}

	/**
	 * ���[�U�[ID���Z�b�g
	 * 
	 * @param userID
	 */
	public void setUserCode(String userID) {
		this.userID = userID;
	}

	/**
	 * ���R�[�h�̓o�^�i�Y�����R�[�h�͑��݂���ꍇ�́A�ύX���s���G���݂��Ȃ��ꍇ�́A�V�K���s���j<br>
	 * ���ʃN���X�ɂ́AinsertImpl��updateImpl���\�b�h���������Ă��������B
	 * 
	 * @dto ���R�[�h
	 */
	public void save(Object dto) {
		// ���݃`�F�b�N
		MasterBase oldDto = (MasterBase) getOldEntity(dto);

		if (oldDto == null) {
			MasterBase entity = (MasterBase) dto;
			// ���[�U�[ID�̐ݒ�
			entity.setUSR_ID(userID);
			// �o�^���t�̐ݒ�
			entity.setINP_DATE(Util.getCurrentDate());
			// �X�V���t��NULL�ɂ���
			entity.setUPD_DATE(null);
			insertImpl(dto);
		} else {
			MasterBase entity = (MasterBase) dto;
			// ���[�U�[ID�̐ݒ�
			entity.setUSR_ID(userID);
			// �ς��Ȃ����ڂ̒l��ݒ肷��
			entity.setINP_DATE(oldDto.getINP_DATE());
			// �X�V���t�̐ݒ�
			entity.setUPD_DATE(Util.getCurrentDate());
			updateImpl(entity, oldDto);
		}
	}

	/**
	 * ���R�[�h�̐V�K�̎���
	 * 
	 * @param dto ���R�[�h
	 */
	@SuppressWarnings("unused")
	protected void insertImpl(Object dto) {
		//
	}

	/**
	 * ���R�[�h�̐V�K�̎���
	 * 
	 * @param dto ���R�[�h
	 * @param oldDto DB����ҏW���̃��R�[�h
	 */
	@SuppressWarnings("unused")
	protected void updateImpl(Object dto, Object oldDto) {
		//
	}

	/**
	 * �ҏW���̃��R�[�h�̎擾�B<br>
	 * �v���p�[�e�B�uEntity�v����A��L�[���擾���A<br>
	 * dao�̃��\�b�h���ďo���ADB����ҏW���̃��R�[�h���擾����
	 * 
	 * @param dto ���R�[�h
	 * @return ����
	 */
	@SuppressWarnings("unused")
	protected Object getOldEntity(Object dto) {
		return null;
	}

	/**
	 * �ҏW���̃��R�[�h�̎�L�[�̎擾�B<br>
	 * ��L�[���擾����B<br>
	 * �ʏ�ꍇ�́A��ЃR�[�h�ȊO�̎�L�[��߂�B
	 * 
	 * @param dto
	 * @return PrimaryCode
	 */
	@SuppressWarnings("unused")
	protected String getPrimaryCode(Object dto) {
		return null;
	}
}

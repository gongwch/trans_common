package jp.co.ais.trans.master.logic.impl;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ���[�U�F�؃}�X�^���W�b�N
 */
public class UserAuthLogicImpl implements UserAuthLogic {

	/** �p�X���[�h�Ǘ��}�X�^Dao */
	private USR_AUTH_MSTDao dao;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao USR_AUTH_MSTDao
	 */
	public UserAuthLogicImpl(USR_AUTH_MSTDao dao) {
		this.dao = dao;
	}

	/**
	 * �f�[�^����.<br>
	 * �Y���f�[�^�����݂��Ȃ��ꍇ�́Anull���Ԃ�.
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return �f�[�^.�����ꍇ��null
	 */
	public USR_AUTH_MST find(String kaiCode) {

		return dao.findByKaiCode(kaiCode);
	}

	/**
	 * �f�[�^���X�V����.<br>
	 * ���Ƀf�[�^�����݂���ꍇ�́A�X�V���������čX�V.<br>
	 * �f�[�^�����݂��Ȃ��ꍇ�́A�쐬���������ĐV�K�ǉ�.
	 * 
	 * @param authDto �Ώۃf�[�^
	 */
	public void update(USR_AUTH_MST authDto) {

		// ���݃`�F�b�N
		USR_AUTH_MST oldDto = find(authDto.getKAI_CODE());

		if (oldDto == null) {

			// �o�^���t�̐ݒ�
			authDto.setINP_DATE(Util.getCurrentDate());

			// �X�V���t��NULL�ɂ���
			authDto.setUPD_DATE(null);

			dao.insert(authDto);

		} else {
			// �ς��Ȃ����ڂ̒l��ݒ肷��
			authDto.setINP_DATE(oldDto.getINP_DATE());

			// �X�V���t�̐ݒ�
			authDto.setUPD_DATE(Util.getCurrentDate());

			dao.update(authDto);
		}
	}
}

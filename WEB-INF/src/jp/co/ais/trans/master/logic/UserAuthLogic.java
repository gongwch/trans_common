package jp.co.ais.trans.master.logic;

import jp.co.ais.trans.master.entity.*;

/**
 * ���[�U�F�؃r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface UserAuthLogic {

	/**
	 * �f�[�^����.<br>
	 * �Y���f�[�^�����݂��Ȃ��ꍇ�́Anull���Ԃ�.
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return �f�[�^.�����ꍇ��null
	 */
	public abstract USR_AUTH_MST find(String kaiCode);

	/**
	 * �f�[�^���X�V����.<br>
	 * ���Ƀf�[�^�����݂���ꍇ�́A�X�V���������čX�V.<br>
	 * �f�[�^�����݂��Ȃ��ꍇ�́A�쐬���������ĐV�K�ǉ�.
	 * 
	 * @param authDto �Ώۃf�[�^
	 */
	public abstract void update(USR_AUTH_MST authDto);
}

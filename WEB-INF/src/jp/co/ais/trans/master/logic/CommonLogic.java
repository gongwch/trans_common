package jp.co.ais.trans.master.logic;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 
 */
public interface CommonLogic {

	/**
	 * �ꗗ��ʂł̌���
	 * 
	 * @param conditions
	 * @return List
	 * @throws ParseException
	 * @conditions ���������iMap�`���j
	 */
	List find(Map conditions) throws ParseException;

	/**
	 * ����̃��R�[�h�̌���
	 * 
	 * @param keys
	 * @return Object
	 * @throws ParseException
	 * @keys ���R�[�h�̎�L�[�iMap�`���j
	 */
	Object findOne(Map keys) throws ParseException;

	/**
	 * REF�_�C�A���O�p�̌���
	 * 
	 * @conditions ���������iMap�`���A���Ȃ��Ƃ�code/name_S/name_K�O�������܂ށj
	 * @param conditions
	 * @return List
	 * @throws ParseException
	 */
	List findREF(Map conditions) throws ParseException;

	/**
	 * ButtonField�ŃR�[�h����͎��A���̂̌���
	 * 
	 * @keys ���������iMap�`���AfindREF�Ɠ��������j
	 * @param keys
	 * @return String
	 * @throws ParseException
	 */
	String findName(Map keys) throws ParseException;

	/**
	 * �V�K�ꍇ�̎�L�[�d���̉���`�F�b�N
	 * 
	 * @param keys
	 * @return boolean
	 * @throws ParseException
	 * @keys ���R�[�h�̎�L�[�iMap�`���j
	 */
	boolean checkCode(Map keys) throws ParseException;

	/**
	 * ���R�[�h�̍폜
	 * 
	 * @param keys
	 * @throws ParseException
	 * @keys ���R�[�h�̎�L�[�iMap�`���j
	 */
	void delete(Map keys) throws ParseException;

	/**
	 * ���R�[�h�̐V�K�i�Y�����R�[�h���ɑ��݂���ꍇ�́A�G���[���X���[�j
	 * 
	 * @dto ���R�[�h
	 * @param dto
	 * @throws TException
	 */
	void insert(Object dto) throws TException;

	/**
	 * ���R�[�h�̕ύX�i�Y�����R�[�h�͑��݂��Ȃ��ꍇ�́A�G���[���X���[�j
	 * 
	 * @param dto
	 * @throws TException
	 * @dto ���R�[�h
	 */
	void update(Object dto) throws TException;

	/**
	 * ���R�[�h�̓o�^�i�Y�����R�[�h�͑��݂���ꍇ�́A�ύX���s���G���݂��Ȃ��ꍇ�́A�V�K���s���j
	 * 
	 * @dto ���R�[�h
	 * @param dto
	 */
	void save(Object dto);

	/**
	 * ���[�U�[ID���Z�b�g
	 * 
	 * @param userID
	 * @throws TException
	 */
	void setUserCode(String userID) throws TException;
}

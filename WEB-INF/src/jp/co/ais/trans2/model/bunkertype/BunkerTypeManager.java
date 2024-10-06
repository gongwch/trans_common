package jp.co.ais.trans2.model.bunkertype;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���틤�ʃ}�l�[�W��
 */
public interface BunkerTypeManager {

	/**
	 * ���탊�X�g�̎擾
	 * 
	 * @param condition
	 * @return ���탊�X�g
	 * @throws TException
	 */
	public List<CM_BNKR_TYPE_MST> get(BunkerTypeSearchCondition condition) throws TException;

	/**
	 * Insert
	 * 
	 * @param list
	 * @throws TException
	 */
	public void insert(List<CM_BNKR_TYPE_MST> list) throws TException;

	/**
	 * Get excel
	 * 
	 * @param condition
	 * @return byte excel
	 * @throws TException
	 */
	public byte[] getExcel(BunkerTypeSearchCondition condition) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(BunkerTypeSearchCondition condition) throws TException;
}

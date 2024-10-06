package jp.co.ais.trans2.model.code;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �R�[�h�}�X�^���ʃ}�l�[�W��
 */
public interface CodeManager {

	/**
	 * �R�[�h�}�X�^���X�g�̎擾
	 * 
	 * @param condition
	 * @return �R�[�h�}�X�^���X�g
	 * @throws TException
	 */
	public List<OP_CODE_MST> get(CodeSearchCondition condition) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CodeSearchCondition condition) throws TException;

}

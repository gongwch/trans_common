package jp.co.ais.trans.logic.util;

import jp.co.ais.trans.common.except.*;

/**
 * �g���I�y�p�����̔�Dao�C���^�[�t�F�[�X
 * 
 * @author AIS
 */
public interface OPAutoControlDao {

	/**
	 * �g���I�y�p�F�����̔Ԃ��ꂽ�ԍ����擾<br>
	 * �����̔ԃR���g���[���̍X�V���s��
	 * 
	 * @param companyCode
	 * @param usrCode
	 * @param prgCode
	 * @param prifix
	 * @param increase
	 * @return �g���I�y�p�����̔�ID
	 * @throws TException
	 */
	public int getAutoId(String companyCode, String usrCode, String prgCode, String prifix, int increase)
		throws TException;
}
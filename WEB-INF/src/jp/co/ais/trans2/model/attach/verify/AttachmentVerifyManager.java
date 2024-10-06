package jp.co.ais.trans2.model.attach.verify;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �Y�t�t�@�C�����؃}�l�[�W��
 */
public interface AttachmentVerifyManager {

	/**
	 * �G���[���X�g���擾����
	 * 
	 * @return �G���[���X�g
	 * @throws TException
	 */
	public List get() throws TException;

	/**
	 * �G���[���X�g�G�N�Z�����擾����
	 * 
	 * @return �G���[���X�g�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel() throws TException;

}

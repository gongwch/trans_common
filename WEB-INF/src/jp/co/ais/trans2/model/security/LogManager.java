package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;

/**
 * ���s���O�Ǘ��C���^�[�t�F�[�X
 * @author AIS
 *
 */
public interface LogManager {

	/**
	 * ���O�����
	 * @param log
	 * @throws TException
	 */
	public void log(Log log) throws TException;

}

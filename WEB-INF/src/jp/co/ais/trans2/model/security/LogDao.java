package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;

/**
 * ���O�Ɋւ���Dao
 * @author AIS
 *
 */
public interface LogDao {

	/**
	 * ���O��o�^����
	 * @param log
	 * @throws TException
	 */
	public void entry(Log log) throws TException;

}

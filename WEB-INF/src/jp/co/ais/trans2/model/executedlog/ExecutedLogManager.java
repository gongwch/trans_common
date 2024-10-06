package jp.co.ais.trans2.model.executedlog;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���s���O�Q��Manager
 */
public interface ExecutedLogManager {

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return ���s���O�Q�Ə��
	 * @throws TException
	 */
	public List<ExecutedLog> get(ExecutedLogSearchCondition condition) throws TException;

	/**
	 * Excel�o��
	 * 
	 * @param condition �o�͏���
	 * @return ���s���O�Q�Ə��
	 * @throws TException
	 */
	public byte[] getExcel(ExecutedLogSearchCondition condition) throws TException;

}

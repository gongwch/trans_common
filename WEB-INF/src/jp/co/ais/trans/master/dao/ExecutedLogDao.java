package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.logic.system.impl.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���s���ODao
 */
public interface ExecutedLogDao {

	/** BEAN */
	public Class BEAN = ExecutedLog.class;

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insertLog(ExecutedLog dto);
	

	/**
	 * ���s���O�ꗗ�擾
	 * 
	 * @param param �����p�����[�^
	 * @return ���s���O�ꗗ
	 */
	public List<ExecutedLog> getExecutedLogList(ExecutedLogSearchParam param);

}

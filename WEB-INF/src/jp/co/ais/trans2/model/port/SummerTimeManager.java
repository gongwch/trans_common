package jp.co.ais.trans2.model.port;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �T�}�[�^�C���}�X�^Manager
 */
public interface SummerTimeManager {

	/**
	 * �擾����
	 * 
	 * @param condition
	 * @return list
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> get(PortSearchCondition condition) throws TException;

	/**
	 * �o�^����
	 * 
	 * @param list
	 * @return �X�V�ナ�X�g�f�[�^
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> entry(List<OP_SMR_TIME_MST> list) throws TException;

	/**
	 * �G�N�Z���o��
	 * 
	 * @param condition
	 * @return byte[]
	 * @throws TException
	 */
	public byte[] getExcel(PortSearchCondition condition) throws TException;

	/**
	 * �G�N�Z���C���|�[�g����
	 * 
	 * @param file
	 * @param condition
	 * @return list �捞�ナ�X�g
	 * @throws TErroneousSummerTimeException
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> importExcel(File file, PortSearchCondition condition)
		throws TErroneousSummerTimeException, TException;
}

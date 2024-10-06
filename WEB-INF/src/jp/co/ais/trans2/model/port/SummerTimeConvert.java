package jp.co.ais.trans2.model.port;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �T�}�[�^�C���}�X�^�ϊ�
 */
public interface SummerTimeConvert {

	/**
	 * �ϊ�����
	 * 
	 * @param file
	 * @param condition
	 * @return list
	 * @throws TErroneousSummerTimeException
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> convert(File file, PortSearchCondition condition)
		throws TErroneousSummerTimeException, TException;

}

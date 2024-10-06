package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �`�[�r���̔r���Ǘ�
 * 
 * @author AIS
 */
public interface SlipExclusiveManager {

	/**
	 * �r����������
	 * 
	 * @param slipTypeList �`�[���
	 * @throws TException
	 */
	public void unLockAll(List<String> slipTypeList) throws TException;

	/**
	 * �p�^�[���r����������
	 * 
	 * @param slipTypeList �`�[���
	 * @throws TException
	 */
	public void unLockPatternAll(List<String> slipTypeList) throws TException;
}

package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �r������e�[�u���̃}�l�[�W��
 * 
 * @author AIS
 */
public interface CodeExclusiveManager {

	/**
	 * �r������
	 * 
	 * @param processType
	 * @param code
	 * @param rowNo
	 * @throws TException
	 */
	public void exclude(String processType, String code, String rowNo) throws TException;

	/**
	 * �r����������
	 * 
	 * @throws TException
	 */
	public void cancelExclude() throws TException;

	/**
	 * �r����������
	 * 
	 * @param processType
	 * @param code
	 * @param rowNo
	 * @throws TException
	 */
	public void cancelExclude(String processType, String code, String rowNo) throws TException;

	/**
	 * �r���|����<br>
	 * �����ꊇ�r���ucodeList��rowNoList�̌��͈�v����K�v�v
	 * 
	 * @param processType
	 * @param codeList
	 * @param rowNoList
	 * @throws TException
	 */
	public void exclude(String processType, List<String> codeList, List<Integer> rowNoList) throws TException;

	/**
	 * �r������<br>
	 * �����ꊇ�r���ucodeList��rowNoList�̌��͈�v����K�v�v
	 * 
	 * @param processType
	 * @param codeList
	 * @param rowNoList
	 * @throws TException
	 */
	public void cancelExclude(String processType, List<String> codeList, List<Integer> rowNoList) throws TException;
}

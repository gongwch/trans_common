package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * プログラムマネージャ
 * 
 * @author AIS
 */
public interface ProgramManager {

	/**
	 * 指定条件に該当するプログラム体系を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するプログラム体系
	 * @throws TException
	 */
	public List<SystemizedProgram> getSystemizedProgram(ProgramSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当するプログラムを返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するプログラム
	 * @throws TException
	 */
	public List<Program> get(ProgramSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当するシステムを返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するシステム
	 * @throws TException
	 */
	public List<SystemClassification> getSystem(SystemClassificationSearchCondition condition) throws TException;

	/**
	 * プログラムを登録する。
	 * 
	 * @param program プログラム
	 * @throws TException
	 */
	public void entry(Program program) throws TException;

	/**
	 * プログラムを修正する。
	 * 
	 * @param program プログラム
	 * @throws TException
	 */
	public void modify(Program program) throws TException;

	/**
	 * プログラムを削除する。
	 * 
	 * @param program プログラム
	 * @throws TException
	 */
	public void delete(Program program) throws TException;

	/**
	 * プログラム(エクセル)を返す
	 * 
	 * @param condition 検索条件
	 * @return プログラム
	 * @throws TException
	 */
	public byte[] getExcel(ProgramSearchCondition condition) throws TException;

}

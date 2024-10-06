package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * メニュー表示マネージャ
 * 
 * @author AIS
 */
public interface MenuManager {

	/**
	 * 指定条件に該当するプログラム体系を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するプログラム体系
	 * @throws TException
	 */
	public List<SystemizedProgram> getSystemizedProgram(MenuSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当するプログラム名称を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するプログラム名称
	 * @throws TException
	 */
	public String getProgramName(MenuSearchCondition condition) throws TException;

	/**
	 * メニュー表示を登録する。
	 * 
	 * @param systemList メニュー表示
	 * @throws TException
	 */
	public void entry(List<SystemizedProgram> systemList) throws TException;

}

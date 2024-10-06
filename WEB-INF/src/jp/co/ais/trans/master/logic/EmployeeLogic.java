package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

/**
 * 社員マスタビジネスロジック
 * 
 * @author roh
 */
public interface EmployeeLogic extends CommonLogic {

	/**
	 * 社員情報取得
	 * 
	 * @param keys ユーザコード
	 * @return List ユーザ情報
	 */
	public abstract List getREFItems(Map keys);

	/**
	 * ダイアログに表示されるユーザリスト取得
	 * 
	 * @param kaiCode
	 * @param empCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param user
	 * @param depCode
	 * @param beginCode
	 * @param endCode
	 * @return ユーザリスト
	 */
	public abstract List<Object> refDailog(String kaiCode, String empCode, String sName, String kName,
		Timestamp termBasisDate, String user, String depCode, String beginCode, String endCode);

}

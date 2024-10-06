package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 計上会社フィールドDao
 * 
 * @author zhangbo
 */
public interface AppropriateCompanyDao {

	/** Bean定義 */
	public static Class BEAN = AppropriateCompany.class;

	/**
	 * 指定されたデータの取得
	 */
	public String conditionSearch_ARGS = "param";

	/**
	 * @param param 計上会社フィールドエンティティ
	 * @return List 計上会社フィールドエンティティ
	 */
	public List<AppropriateCompany> conditionSearch(AppropriateCompany param);

}

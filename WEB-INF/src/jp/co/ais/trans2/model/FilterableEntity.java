package jp.co.ais.trans2.model;

import java.util.*;

/**
 * 
 */
public interface FilterableEntity {

	/**
	 * コードを戻します。
	 * 
	 * @return コード
	 */
	public String getCode();

	/**
	 * 検索名称を戻します。
	 * 
	 * @return 検索名称
	 */
	public String getNamek();

	/**
	 * 略称を戻します。
	 * 
	 * @return 略称
	 */
	public String getNames();

	/**
	 * 開始日を戻します。
	 * 
	 * @return 開始日
	 */
	public Date getDateFrom();

	/**
	 * 終了日を戻します。
	 * 
	 * @return 終了日
	 */
	public Date getDateTo();

}

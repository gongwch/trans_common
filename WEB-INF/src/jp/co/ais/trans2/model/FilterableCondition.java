package jp.co.ais.trans2.model;

import java.util.*;

/**
 * フィルター可能検索条件
 */
public interface FilterableCondition {

	/**
	 * コードを戻します。
	 * 
	 * @return コード
	 */
	public String getCode();

	/**
	 * コード開始を戻します。
	 * 
	 * @return コード開始
	 */
	public String getCodeFrom();

	/**
	 * コード終了を戻します。
	 * 
	 * @return コード終了
	 */
	public String getCodeTo();

	/**
	 * コード前方一致を戻します。
	 * 
	 * @return コード前方一致
	 */
	public String getCodeLike();

	/**
	 * 検索名称likeを戻します。
	 * 
	 * @return 検索名称like
	 */
	public String getNamekLike();

	/**
	 * 略称likeを戻します。
	 * 
	 * @return 略称like
	 */
	public String getNamesLike();

	/**
	 * コードリストを戻します。
	 * 
	 * @return コードリスト
	 */
	public List<String> getCodeList();

	/**
	 * 基準日時を戻します。
	 * 
	 * @return 基準日時
	 */
	public Date getValidTerm();
}

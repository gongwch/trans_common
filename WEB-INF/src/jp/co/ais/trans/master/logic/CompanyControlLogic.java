package jp.co.ais.trans.master.logic;

import java.util.*;

/**
 * 会社コントロールビジネスロジック
 */
public interface CompanyControlLogic extends CommonLogic {

	/**
	 * 会社コードでコントロールエンティティを取得
	 * 
	 * @param kaiCode
	 * @return 会社コントロール情報
	 */
	public abstract Object findOne(String kaiCode);

	/**
	 * 条件検索による会社コントロールリスト取得
	 * 
	 * @param keys
	 * @return 会社コントロールリスト
	 */
	public abstract List getREFItems(Map keys);

}

package jp.co.ais.trans2.model.language;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 言語コードインターフェース。
 * 
 * @author AIS
 */
public interface LanguageManager {

	/**
	 * 指定条件に該当する言語を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する部門組織情報
	 * @throws TException
	 */
	public List<Language> getLanguage(LanguageSearchCondition condition) throws TException;

}

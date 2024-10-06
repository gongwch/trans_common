package jp.co.ais.trans2.model.country;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * 国情報の実装クラス
 */
public class CountryManagerImpl extends TModel implements CountryManager {

	/**
	 * @see jp.co.ais.trans2.model.country.CountryManager#get(jp.co.ais.trans2.model.country.CountrySearchCondition)
	 */
	public List<Country> get(CountrySearchCondition condition) throws TException {
		CountryDao dao = (CountryDao) getComponent(CountryDao.class);
		return dao.get(condition);
	}

	/**
	 * @see jp.co.ais.trans2.model.country.CountryManager#entry(jp.co.ais.trans2.model.country.Country)
	 */
	public void entry(Country bean) throws TException {
		CountryDao dao = (CountryDao) getComponent(CountryDao.class);
		dao.entry(bean);
	}

	/**
	 * @see jp.co.ais.trans2.model.country.CountryManager#modify(jp.co.ais.trans2.model.country.Country)
	 */
	public void modify(Country bean) throws TException {
		CountryDao dao = (CountryDao) getComponent(CountryDao.class);
		dao.modify(bean);
	}

	/**
	 * @see jp.co.ais.trans2.model.country.CountryManager#delete(jp.co.ais.trans2.model.country.Country)
	 */
	public void delete(Country bean) throws TException {
		CountryDao dao = (CountryDao) getComponent(CountryDao.class);
		dao.delete(bean);
	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(CountrySearchCondition condition) throws TException {
		CountryDao dao = (CountryDao) getComponent(CountryDao.class);
		return dao.hasDelete(condition);
	}

}

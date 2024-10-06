package jp.co.ais.trans2.model.voyage;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * Voyage管理の実装クラス
 * 
 * @author AIS
 */
public class VoyageManagerImpl extends TModel implements VoyageManager {

	/**
	 * @return Dao
	 */
	protected VoyageDao getDao() {
		return (VoyageDao) getComponent(VoyageDao.class);
	}

	@Override
	public Voyage get(String companyCode, String voyageCode) throws TException {
		VoyageDao dao = getDao();
		return dao.get(companyCode, voyageCode);
	}

	@Override
	public List<Voyage> get(VoyageSearchCondition condition) throws TException {
		VoyageDao dao = getDao();
		return dao.getByCondition(condition);
	}

	@Override
	public void entry(Voyage voyage) throws TException {
		VoyageDao dao = getDao();
		dao.insert(voyage);
	}

	@Override
	public void modify(Voyage voyage) throws TException {
		VoyageDao dao = getDao();
		dao.update(voyage);
	}

	@Override
	public void delete(Voyage voyage) throws TException {
		VoyageDao dao = getDao();
		dao.delete(voyage);
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */
	// TODO 一時的にコメントアウト
	public byte[] getExcel(VoyageSearchCondition condition) throws TException {

		try {

			// 次航情報データを抽出
			List<Voyage> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			VoyageExcel exl = new VoyageExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(VoyageSearchCondition condition) throws TException {
		VoyageDao dao = getDao();
		return dao.hasDelete(condition);
	}

}

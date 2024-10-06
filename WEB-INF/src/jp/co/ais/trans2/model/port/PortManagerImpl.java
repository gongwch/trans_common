package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * Port管理の実装クラス
 * 
 * @author AIS
 */
public class PortManagerImpl extends TModel implements PortManager {

	/**
	 * @return Dao
	 */
	protected PortDao getDao() {
		return (PortDao) getComponent(PortDao.class);
	}

	/**
	 * 指定コードに紐付くPort情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param voyageCode Portコード
	 * @return 指定コードに紐付くPort情報
	 * @throws TException
	 */
	@Override
	public Port get(String companyCode, String voyageCode) throws TException {
		PortDao dao = getDao();
		return dao.get(companyCode, voyageCode);
	}

	/**
	 * 指定条件に該当するPort情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するPort情報
	 * @throws TException
	 */
	@Override
	public List<Port> get(PortSearchCondition condition) throws TException {
		PortDao dao = getDao();
		return dao.getByCondition(condition);
	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(PortSearchCondition condition) throws TException {
		PortDao dao = getDao();
		return dao.hasDelete(condition);
	}

	/**
	 * Portを登録する。
	 * 
	 * @param voyage Port
	 * @throws TException
	 */
	@Override
	public void entry(Port voyage) throws TException {
		PortDao dao = getDao();
		dao.insert(voyage);
	}

	/**
	 * Portを修正する。
	 * 
	 * @param voyage Port
	 * @throws TException
	 */
	@Override
	public void modify(Port voyage) throws TException {
		PortDao dao = getDao();
		dao.update(voyage);
	}

	/**
	 * Portを削除する。
	 * 
	 * @param voyage Port
	 * @throws TException
	 */
	@Override
	public void delete(Port voyage) throws TException {
		PortDao dao = getDao();
		dao.delete(voyage);
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */

	public byte[] getExcel(PortSearchCondition condition) throws TException {

		try {

			// 港情報データを抽出
			List<Port> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			PortExcel exl = new PortExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}
}

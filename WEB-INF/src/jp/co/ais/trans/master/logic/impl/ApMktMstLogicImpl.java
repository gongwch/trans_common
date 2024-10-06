package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 送金目的マスタ操作
 */
public class ApMktMstLogicImpl extends CommonLogicBaseImpl {

	private AP_MKT_MSTDao dao;

	/**
	 * @param dao
	 */
	public ApMktMstLogicImpl(AP_MKT_MSTDao dao) {
		this.dao = dao;
	}

	/**
	 * @see jp.co.ais.trans.master.logic.impl.CommonLogicBaseImpl#findREF(java.util.Map)
	 */
	public List findREF(Map conditions) {
		// コード
		String code = (String) conditions.get("code");
		// 略称
		String name_S = (String) conditions.get("name_S");
		// 検索名称
		// String name_K = (String)conditions.get("name_K");
		List list = dao.conditionSearch(code, name_S);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			AP_MKT_MST e = (AP_MKT_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getMKT_CODE());
			e2.setName_S(e.getMKT_NAME());
			e2.setName_K("");
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		AP_MKT_MST entity = dao.getAP_MKT_MST(code);

		return (entity == null ? null : entity.getMKT_NAME());
	}
}

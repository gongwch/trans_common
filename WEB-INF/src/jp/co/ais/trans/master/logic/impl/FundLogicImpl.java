package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 資金コードマスタ操作
 */
public class FundLogicImpl extends CommonLogicBaseImpl {

	private CM_SKN_MSTDao dao;

	/**
	 * @param dao
	 */
	public FundLogicImpl(CM_SKN_MSTDao dao) {
		this.dao = dao;
	}

	public List findREF(Map conditions) {
		// コード
		String code = (String) conditions.get("code");
		// 略称
		String name_S = (String) conditions.get("name_S");
		// 検索名称
		String name_K = (String) conditions.get("name_K");
		String kaiCode = (String) conditions.get("kaiCode");
		String kind = (String) conditions.get("kind");

		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, kind);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			CM_SKN_MST e = (CM_SKN_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getSKN_CODE());
			e2.setName_S(e.getSKN_NAME_S());
			e2.setName_K(e.getSKN_NAME_K());
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		String kaiCode = (String) keys.get("kaiCode");
		String kind = (String) keys.get("kind");

		CM_SKN_MST entity = dao.getCM_SKN_MST(kaiCode, code);

		if ("InputFund".equals(kind)) {
			if (entity != null && entity.getSKN_SUM_KBN() != 0) {
				entity = null;
			}
		}

		return (entity == null ? null : entity.getSKN_NAME_S());
	}
}

package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 言語マスタ操作
 */
public class LanguageLogicImpl extends CommonLogicBaseImpl {

	private LNG_MSTDao dao;

	/**
	 * @param dao
	 */
	public LanguageLogicImpl(LNG_MSTDao dao) {
		this.dao = dao;
	}

	public List findREF(Map conditions) {
		// コード
		String code = (String) conditions.get("code");
		// 略称
		// String name_S = (String)conditions.get("name_S");
		// 検索名称
		// String name_K = (String)conditions.get("name_K");
		String kaiCode = (String) conditions.get("kaiCode");
		List list = dao.conditionSearch(kaiCode, code);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			LNG_MST e = (LNG_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getLNG_CODE());
			e2.setName_S("");
			e2.setName_K("");
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		String kaiCode = (String) keys.get("kaiCode");

		LNG_MST entity = dao.getLNG_MSTByKaicodeLngcode(kaiCode, code);

		return (entity == null ? null : entity.getLNG_CODE());
	}
}

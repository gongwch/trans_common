package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 科目体系名称マスタ操作
 */
public class KmkTkMstLogicImpl extends CommonLogicBaseImpl implements KmkTkMstLogic {

	private KMK_TK_MSTDao dao;

	/**
	 * @param dao
	 */
	public KmkTkMstLogicImpl(KMK_TK_MSTDao dao) {
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
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			KMK_TK_MST e = (KMK_TK_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getKMT_CODE());
			e2.setName_S(e.getKMT_NAME_S());
			e2.setName_K(e.getKMT_NAME_K());
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		String kaiCode = (String) keys.get("kaiCode");

		KMK_TK_MST entity = dao.getKMK_TK_MST(kaiCode, code);

		return (entity == null ? null : entity.getKMT_NAME_S());
	}

	/**
	 * 科目体系マスタ検索
	 * 
	 * @param strKaiCode 会社コード
	 * @param strKmtCode 科目体系コード
	 * @param strKmtName 科目体系略称
	 * @param strKmtName_K 科目体系検索名
	 * @param args その他パラメータ
	 * @return 科目体系マスタリスト
	 */
	public List searchKmtMstData(String strKaiCode, String strKmtCode, String strKmtName, String strKmtName_K,
		String... args) {

		// データを取得する。
		List lstResult = dao.searchKmtMstData(strKaiCode, strKmtCode, strKmtName, strKmtName_K, args[0], args[1]);

		return lstResult;
	}

	/**
	 * 基本科目体系を取得する
	 * 
	 * @param conditionMap パラメーター
	 * @return Map
	 */
	public Map getItemSystemValue(Map conditionMap) {
		// 戻るMap
		Map resultMap = new HashMap();
		// 会社コードを取得する
		String strKaiCode = Util.avoidNull(conditionMap.get("KAI_CODE"));
		// 基本科目体系コードを取得する
		String strItemSystemCode = Util.avoidNull(conditionMap.get("ITEM_SYSTEM_CODE"));

		// 基本科目体系Listを取得する
		KMK_TK_MST kmtMst = dao.getKmkTkMst(strKaiCode, strItemSystemCode);
		if (kmtMst != null) {
			// 基本科目体系コードを取得する
			resultMap.put("ITEM_SYSTEM_CODE", kmtMst.getKMT_CODE());
			// 基本科目体系名を取得する
			resultMap.put("ITEM_SYSTEM_NAME", kmtMst.getKMT_NAME_S());
		}
		// 戻る
		return resultMap;
	}
}

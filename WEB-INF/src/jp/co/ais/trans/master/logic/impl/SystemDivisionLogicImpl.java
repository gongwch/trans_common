package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * システム区分データ操作
 */
public class SystemDivisionLogicImpl extends CommonLogicBaseImpl implements SystemDivisionLogic {

	protected SYS_MSTDao dao;

	protected SYS_MST entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao SYS_MSTDao
	 */
	public SystemDivisionLogicImpl(SYS_MSTDao dao) {
		this.dao = dao;
	}

	/**
	 * SYS_MSTインスタンスの設定.
	 * 
	 * @param entity SYS_MST
	 */
	public void setEntity(SYS_MST entity) {
		this.entity = entity;
	}

	public List find(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String beginSysKbn = (String) conditions.get("beginSysKbn");
		String endSysKbn = (String) conditions.get("endSysKbn");

		if (Util.isNullOrEmpty(beginSysKbn) && Util.isNullOrEmpty(endSysKbn)) {
			return dao.getAllSYS_MST2(kaiCode);
		} else if (Util.isNullOrEmpty(beginSysKbn)) {
			return dao.getSysInfoTo(kaiCode, endSysKbn);
		} else if (Util.isNullOrEmpty(endSysKbn)) {
			return dao.getSysInfoFrom(kaiCode, beginSysKbn);
		} else {
			return dao.getSysInfo(kaiCode, beginSysKbn, endSysKbn);
		}
	}

	public Object findOne(Map keys) {
		String kaiCode = (String) keys.get("kaiCode");
		String sysKbn = (String) keys.get("sysKbn");

		return dao.getSYS_MSTByKaicodeSyskbn(kaiCode, sysKbn);
	}

	public SYS_MST getSystemKbn(String kaiCode, String syskbn) {
		return dao.getSYS_MSTByKaicodeSyskbn(kaiCode, syskbn);
	}

	public List findREF(Map conditions) {
		// コード
		String sysKbn = (String) conditions.get("code");
		// 略称
		String name_S = (String) conditions.get("name_S");
		// 検索名称
		String name_K = (String) conditions.get("name_K");
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		List list = dao.conditionSearch(kaiCode, sysKbn, name_S, name_K, beginCode, endCode);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			SYS_MST e = (SYS_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getSYS_KBN());
			e2.setName_S(e.getSYS_KBN_NAME_S());
			e2.setName_K(e.getSYS_KBN_NAME_K());
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// コード
		String sysKbn = (String) keys.get("code");
		String kaiCode = (String) keys.get("kaiCode");

		SYS_MST localentity = dao.getSYS_MSTByKaicodeSyskbn(kaiCode, sysKbn);

		return (localentity == null ? null : localentity.getSYS_KBN_NAME_S());
	}

	public void delete(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String sysKbn = (String) conditions.get("sysKbn");

		entity.setKAI_CODE(kaiCode);
		entity.setSYS_KBN(sysKbn);

		dao.delete(entity);
	}

	protected void insertImpl(Object dto) {
		SYS_MST localentity = (SYS_MST) dto;

		dao.insert(localentity);
	}

	protected void updateImpl(Object dto, Object oldDto) {
		SYS_MST localentity = (SYS_MST) dto;

		dao.update(localentity);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		SYS_MST localentity = (SYS_MST) dto;
		String kaiCode = localentity.getKAI_CODE();
		String sysKBN = localentity.getSYS_KBN();

		return dao.getSYS_MSTByKaicodeSyskbn(kaiCode, sysKBN);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。 *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		SYS_MST localentity = (SYS_MST) dto;
		return localentity.getSYS_KBN_NAME();
	}

	/**
	 * @see jp.co.ais.trans.master.logic.SystemDivisionLogic#getSystemList(java.lang.String)
	 */
	public List<SYS_MST> getSystemList(String companyCode) {
		return dao.getAllSYS_MST2(companyCode);
	}

}

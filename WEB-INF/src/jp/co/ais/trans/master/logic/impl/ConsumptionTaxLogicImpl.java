package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 消費税マスタ操作
 */
public class ConsumptionTaxLogicImpl extends CommonLogicBaseImpl implements ConsumptionTaxLogic {

	/** 消費税マスタDao */
	protected SZEI_MSTDao dao;

	/** 消費税マスタ実体 */
	protected SZEI_MST entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao SZEI_MSTDao
	 */
	public ConsumptionTaxLogicImpl(SZEI_MSTDao dao) {
		// 消費税マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * SZEI_MSTインスタンスの設定.
	 * 
	 * @param entity SZEI_MST
	 */
	public void setEntity(SZEI_MST entity) {
		// 消費税マスタ実体を返す
		this.entity = entity;
	}

	public List find(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 開始コードの取得
		String beginZeiCode = (String) conditions.get("beginZeiCode");
		// 終了コードの取得
		String endZeiCode = (String) conditions.get("endZeiCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginZeiCode) && Util.isNullOrEmpty(endZeiCode)) {
			// 結果を返す
			return dao.getAllSZEI_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginZeiCode)) {
			// 結果を返す
			return dao.getSzeiInfoTo(kaiCode, endZeiCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endZeiCode)) {
			// 結果を返す
			return dao.getSzeiInfoFrom(kaiCode, beginZeiCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getSzeiInfo(kaiCode, beginZeiCode, endZeiCode);
		}
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 消費税コードの取得
		String zeiCode = (String) keys.get("zeiCode");
		// 結果を返す
		return dao.getSZEI_MSTByKaicodeSzeicode(kaiCode, zeiCode);
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		// コード
		String code = (String) conditions.get("code");
		// 略称
		String name_S = (String) conditions.get("name_S");
		// 検索名称
		String name_K = (String) conditions.get("name_K");

		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");

		String kind = (String) conditions.get("kind");

		// 結果の取得
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, kind);
		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			SZEI_MST e = (SZEI_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 消費税コードの設定
			e2.setCode(e.getZEI_CODE());
			// 消費税略称の設定
			e2.setName_S(e.getZEI_NAME_S());
			// 消費税検索名称の設定
			e2.setName_K(e.getZEI_NAME_K());
			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");

		String kind = (String) keys.get("kind");

		// 実体の初期化
		SZEI_MST entity1 = dao.getSZEI_MSTByKaicodeSzeicode(kaiCode, code);

		if ("PurchaseConsumptionTax".equals(kind)) {
			if (entity1 != null && entity1.getUS_KBN() != 2) {
				entity1 = null;
			}
		}
		// 実体を返す
		return (entity1 == null ? null : entity1.getZEI_NAME_S());
	}

	/**
	 * F データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 消費税コードの取得
		String zeiCode = (String) conditions.get("zeiCode");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// 消費税コードの設定
		entity.setZEI_CODE(zeiCode);
		// データを削除する
		dao.delete(entity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		SZEI_MST entity1 = (SZEI_MST) dto;

		// データを登録する
		dao.insert(entity1);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// 実体の初期化
		SZEI_MST entity1 = (SZEI_MST) dto;
		// データを更新する
		dao.update(entity1);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		SZEI_MST entity1 = (SZEI_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String zeiCode = entity1.getZEI_CODE();

		return dao.getSZEI_MSTByKaicodeSzeicode(kaiCode, zeiCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		SZEI_MST entity1 = (SZEI_MST) dto;
		return entity1.getZEI_CODE();
	}

	public List getREFItems(Map keys) {
		SZEI_MST entity1 = (SZEI_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity1 != null) {
			// 結果の初期化
			List<Object> result = new ArrayList<Object>(7);

			String zeiCode = entity1.getZEI_CODE();
			String zeiName = entity1.getZEI_NAME_S();

			result.add(zeiCode);

			result.add(zeiName != null ? entity1.getZEI_NAME_S() : "");

			result.add(entity1.getUS_KBN());

			result.add(entity1.getSTR_DATE());
			// 終了
			result.add(entity1.getEND_DATE());
			// 税率
			result.add(Util.isNullOrEmpty(entity1.getZEI_RATE()) ? "" : String.valueOf(entity1.getZEI_RATE()));
			// 結果の設定
			list.add(result);
		} else {
			// 結果の削除
			list.add(null);
		}
		// 結果を返す
		return list;
	}

	/**
	 * 消費税リスト検索
	 * 
	 * @return 消費税リスト
	 * @see jp.co.ais.trans.master.logic.ConsumptionTaxLogic#refDailog(java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 *      java.sql.Timestamp)
	 */
	public List<Object> refDailog(String kaiCode, String zeiCode, String sName, String kName, String sales,
		String purchase, String elseTax, Timestamp termBasisDate) {

		return dao.refDialogSearch(kaiCode, zeiCode, sName, kName, sales, purchase, elseTax, termBasisDate);
	}
}

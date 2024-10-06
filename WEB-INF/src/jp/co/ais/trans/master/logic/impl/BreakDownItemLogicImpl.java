package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 内訳科目マスタ操作
 */
public class BreakDownItemLogicImpl extends CommonLogicBaseImpl {

	/** 内訳科目マスタDao */
	private UKM_MSTDao dao;

	/** 内訳科目マスタ実体 */
	private UKM_MST entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao UKM_MSTDao
	 */
	public BreakDownItemLogicImpl(UKM_MSTDao dao) {
		// 内訳科目マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * UKM_MSTインスタンスの設定.
	 * 
	 * @param entity UKM_MST
	 */
	public void setEntity(UKM_MST entity) {
		// 内訳科目マスタ実体を返す
		this.entity = entity;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 開始コードの取得
		String kmkCode = (String) conditions.get("kmkCode");
		//
		String hkmCode = (String) conditions.get("hkmCode");
		//
		String beginUkmCode = (String) conditions.get("beginUkmCode");
		// 終了コードの取得
		String endUkmCode = (String) conditions.get("endUkmCode");

		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(kmkCode)) {
			return dao.getAllUKM_MST1(kaiCode);
		} else if (Util.isNullOrEmpty(hkmCode)) {
			return dao.getAllUKM_MST2(kaiCode, kmkCode);
		} else if (Util.isNullOrEmpty(beginUkmCode) && Util.isNullOrEmpty(endUkmCode)) {
			// 結果を返す
			return dao.getAllUKM_MST3(kaiCode, kmkCode, hkmCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginUkmCode)) {
			// 結果を返す
			return dao.getUkmInfoTo(kaiCode, kmkCode, hkmCode, endUkmCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endUkmCode)) {
			// 結果を返す
			return dao.getUkmInfoFrom(kaiCode, kmkCode, hkmCode, beginUkmCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getUkmInfo(kaiCode, kmkCode, hkmCode, beginUkmCode, endUkmCode);
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
		// 科目コードの取得
		String kmkCode = (String) keys.get("kmkCode");
		// 補助科目コードの取得
		String hkmCode = (String) keys.get("hkmCode");
		// 内訳科目コードの取得
		String ukmCode = (String) keys.get("ukmCode");
		// 結果を返す
		return dao.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(kaiCode, kmkCode, hkmCode, ukmCode);
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
		// 科目コードの取得
		String kmkCode = (String) conditions.get("kmkCode");
		// 補助科目コードの取得
		String hkmCode = (String) conditions.get("hkmCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		// 結果の取得
		List list = dao.conditionSearch(kaiCode, kmkCode, hkmCode, code, name_S, name_K, beginCode, endCode);
		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			UKM_MST e = (UKM_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 内訳科目コードの設定
			e2.setCode(e.getUKM_CODE());
			// 内訳科目略称の設定
			e2.setName_S(e.getUKM_NAME_S());
			// 内訳科目検索名称の設定
			e2.setName_K(e.getUKM_NAME_K());
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
		// 科目コードの取得
		String kmkCode = (String) keys.get("kmkCode");
		// 補助科目コードの取得
		String hkmCode = (String) keys.get("hkmCode");
		// 実体の初期化
		UKM_MST entity1 = dao.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(kaiCode, kmkCode, hkmCode, code);
		// 実体を返す
		return (entity1 == null ? null : entity1.getUKM_NAME_S());
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 科目コードの取得
		String kmkCode = (String) conditions.get("kmkCode");
		// 補助科目コードの取得
		String hkmCode = (String) conditions.get("hkmCode");
		// 内訳科目コードの取得
		String ukmCode = (String) conditions.get("ukmCode");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// 科目コードの設定
		entity.setKMK_CODE(kmkCode);
		// 補助科目コードの設定
		entity.setHKM_CODE(hkmCode);
		// 内訳科目コードの設定
		entity.setUKM_CODE(ukmCode);
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
		UKM_MST entity1 = (UKM_MST) dto;

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
		UKM_MST entity1 = (UKM_MST) dto;

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
		UKM_MST entity1 = (UKM_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String kmkCode = entity1.getKMK_CODE();
		String hkmCode = entity1.getHKM_CODE();
		String ukmCode = entity1.getUKM_CODE();

		return dao.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(kaiCode, kmkCode, hkmCode, ukmCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		UKM_MST entity1 = (UKM_MST) dto;
		return entity1.getUKM_CODE();
	}
}

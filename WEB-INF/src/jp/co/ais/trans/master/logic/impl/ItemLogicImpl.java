package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 科目マスタ操作
 */
public class ItemLogicImpl extends CommonLogicBaseImpl {

	/** 科目マスタDao */
	private KMK_MSTDao dao;

	/** 科目マスタ実体 */
	private KMK_MST kmkMstentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao KMK_MSTDao
	 */
	public ItemLogicImpl(KMK_MSTDao dao) {
		// 科目マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * KMK_MSTインスタンスの設定.
	 * 
	 * @param entity KMK_MST
	 */
	public void setEntity(KMK_MST entity) {
		// 科目マスタ実体を返す
		this.kmkMstentity = entity;
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
		String beginKmkCode = (String) conditions.get("beginKmkCode");
		// 終了コードの取得
		String endKmkCode = (String) conditions.get("endKmkCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginKmkCode) && Util.isNullOrEmpty(endKmkCode)) {
			// 結果を返す
			return dao.getAllKMK_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginKmkCode)) {
			// 結果を返す
			return dao.getKmkInfoTo(kaiCode, endKmkCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endKmkCode)) {
			// 結果を返す
			return dao.getKmkInfoFrom(kaiCode, beginKmkCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getKmkInfo(kaiCode, beginKmkCode, endKmkCode);
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
		// 結果を返す
		return dao.getKMK_MSTByKaicodeKmkcode(kaiCode, kmkCode);
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
			KMK_MST e = (KMK_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 科目コードの設定
			e2.setCode(e.getKMK_CODE());
			// 科目略称の設定
			e2.setName_S(e.getKMK_NAME_S());
			// 科目検索名称の設定
			e2.setName_K(e.getKMK_NAME_K());
			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");

		String kind = (String) keys.get("kind");

		// 実体の初期化
		KMK_MST entity = dao.getKMK_MSTByKaicodeKmkcode(kaiCode, code);

		// 入力された値が無い場合
		if (entity == null) {
			// 実体を返す
			return null;
		}

		// 科目マスタ自身の場合
		else if (kind.equals("ItemAll")) {
			// 実体を返す
			return (entity == null ? null : entity.getKMK_NAME_S());
		}
		// 科目マスタ以外
		else {
			// 集計区分・見出し区分のときは結果を返さない
			if ("Item".equals(kind) && entity.getSUM_KBN() != 0) {
				entity = null;
			}

			else if ("InputItem".equals(kind)) {
				if (entity != null && entity.getSUM_KBN() != 0) {
					entity = null;
				}
			} else if ("ItemWithChild".equals(kind)) {
				if (entity != null && entity.getHKM_KBN() != 1) {
					entity = null;
				}
			}

			// 実体を返す
			return (entity == null ? null : entity.getKMK_NAME_S());
		}

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
		// 会社コードの設定
		kmkMstentity.setKAI_CODE(kaiCode);
		// 科目コードの設定
		kmkMstentity.setKMK_CODE(kmkCode);
		// データを削除する
		dao.delete(kmkMstentity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		KMK_MST entity = (KMK_MST) dto;

		// データを登録する
		dao.insert(entity);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// 実体の初期化
		KMK_MST entity = (KMK_MST) dto;

		// データを更新する
		dao.update(entity);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		KMK_MST entity = (KMK_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String kmkCode = entity.getKMK_CODE();

		return dao.getKMK_MSTByKaicodeKmkcode(kaiCode, kmkCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		KMK_MST entity = (KMK_MST) dto;
		return entity.getKMK_CODE();
	}
}

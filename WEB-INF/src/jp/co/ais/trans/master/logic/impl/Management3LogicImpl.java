package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 管理3マスタ
 */
public class Management3LogicImpl extends CommonLogicBaseImpl {

	/** 管理3マスタDao */
	protected KNR3_MSTDao dao;

	/** 管理3マスタ実体 */
	protected KNR3_MST knr3entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao KNR3_MSTDao
	 */
	public Management3LogicImpl(KNR3_MSTDao dao) {
		// 管理マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * KNR3_MSTインスタンスの設定.
	 * 
	 * @param entity KNR3_MST
	 */
	public void setEntity(KNR3_MST entity) {
		// 管理マスタ実体を返す
		this.knr3entity = entity;
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
		String beginknrCode = (String) conditions.get("beginknrCode");
		// 終了コードの取得
		String endknrCode = (String) conditions.get("endknrCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginknrCode) && Util.isNullOrEmpty(endknrCode)) {
			// 結果を返す
			return dao.getAllKNR3_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginknrCode)) {
			// 結果を返す
			return dao.getKnr3InfoTo(kaiCode, endknrCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endknrCode)) {
			// 結果を返す
			return dao.getKnr3InfoFrom(kaiCode, beginknrCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getKnr3Info(kaiCode, beginknrCode, endknrCode);
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
		// 管理コードの取得
		String knrCode = (String) keys.get("knrCode");
		// 結果を返す
		return dao.getKNR3_MSTByKaicodeKnrcode3(kaiCode, knrCode);
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
		// 結果の取得
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode);
		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			KNR3_MST e = (KNR3_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 管理コードの設定
			e2.setCode(e.getKNR_CODE_3());
			// 管理略称の設定
			e2.setName_S(e.getKNR_NAME_S_3());
			// 管理検索名称の設定
			e2.setName_K(e.getKNR_NAME_K_3());
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
		// 実体の初期化
		KNR3_MST entity = dao.getKNR3_MSTByKaicodeKnrcode3(kaiCode, code);
		// 実体を返す
		return (entity == null ? null : entity.getKNR_NAME_S_3());
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 管理コードの取得
		String knrCode = (String) conditions.get("knrCode");
		// 会社コードの設定
		knr3entity.setKAI_CODE(kaiCode);
		// 管理コードの設定
		knr3entity.setKNR_CODE_3(knrCode);
		// データを削除する
		dao.delete(knr3entity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		KNR3_MST entity = (KNR3_MST) dto;

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
		KNR3_MST entity = (KNR3_MST) dto;

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
		KNR3_MST entity = (KNR3_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String knrCode3 = entity.getKNR_CODE_3();

		return dao.getKNR3_MSTByKaicodeKnrcode3(kaiCode, knrCode3);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。 *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		KNR3_MST entity = (KNR3_MST) dto;
		return entity.getKNR_CODE_3();
	}
}

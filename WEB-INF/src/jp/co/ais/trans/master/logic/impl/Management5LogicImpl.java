package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 管理5マスタ操作
 */
public class Management5LogicImpl extends CommonLogicBaseImpl {

	/** 管理マスタDao */
	private KNR5_MSTDao dao;

	/** 管理マスタ実体 */
	private KNR5_MST knr5entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao KNR5_MSTDao
	 */
	public Management5LogicImpl(KNR5_MSTDao dao) {
		// 管理マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * KNR5_MSTインスタンスの設定.
	 * 
	 * @param entity KNR5_MST
	 */
	public void setEntity(KNR5_MST entity) {
		// 管理マスタ実体を返す
		this.knr5entity = entity;
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
			return dao.getAllKNR5_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginknrCode)) {
			// 結果を返す
			return dao.getKnr5InfoTo(kaiCode, endknrCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endknrCode)) {
			// 結果を返す
			return dao.getKnr5InfoFrom(kaiCode, beginknrCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getKnr5Info(kaiCode, beginknrCode, endknrCode);
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
		return dao.getKNR5_MSTByKaicodeKnrcode5(kaiCode, knrCode);
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
			KNR5_MST e = (KNR5_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 管理コードの設定
			e2.setCode(e.getKNR_CODE_5());
			// 管理略称の設定
			e2.setName_S(e.getKNR_NAME_S_5());
			// 管理検索名称の設定
			e2.setName_K(e.getKNR_NAME_K_5());
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
		KNR5_MST entity = dao.getKNR5_MSTByKaicodeKnrcode5(kaiCode, code);
		// 実体を返す
		return (entity == null ? null : entity.getKNR_NAME_S_5());
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
		knr5entity.setKAI_CODE(kaiCode);
		// 管理コードの設定
		knr5entity.setKNR_CODE_5(knrCode);
		// データを削除する
		dao.delete(knr5entity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		KNR5_MST entity = (KNR5_MST) dto;

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
		KNR5_MST entity = (KNR5_MST) dto;

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
		KNR5_MST entity = (KNR5_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String knrCode5 = entity.getKNR_CODE_5();

		return dao.getKNR5_MSTByKaicodeKnrcode5(kaiCode, knrCode5);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。 *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		KNR5_MST entity = (KNR5_MST) dto;
		return entity.getKNR_CODE_5();
	}
}

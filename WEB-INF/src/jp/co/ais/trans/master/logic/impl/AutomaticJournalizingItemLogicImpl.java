package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 自動仕訳科目データ操作
 */
public class AutomaticJournalizingItemLogicImpl extends CommonLogicBaseImpl {

	/** 自動仕訳科目マスタDao */
	private SWK_KMK_MSTDao dao;

	/** 自動仕訳科目マスタ実体 */
	private SWK_KMK_MST entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao SWK_KMK_MSTDao
	 */
	public AutomaticJournalizingItemLogicImpl(SWK_KMK_MSTDao dao) {
		// 自動仕訳科目マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * SWK_KMK_MSTインスタンスの設定.
	 * 
	 * @param entity SWK_KMK_MST
	 */
	public void setEntity(SWK_KMK_MST entity) {
		// 自動仕訳科目マスタ実体を返す
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

		// 結果を返す
		return dao.getSWK_KMK_MSTByKaicode(kaiCode);

	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 科目制御区分の取得
		int kmkCnt = Integer.parseInt((String) keys.get("kmkCnt"));
		// 結果を返す
		return dao.getSWK_KMK_MSTByKaicodeKmkcnt(kaiCode, kmkCnt);
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 科目制御区分の取得
		int kmkCnt = Integer.parseInt((String) conditions.get("kmkCnt"));
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// 科目制御区分の設定
		entity.setKMK_CNT(kmkCnt);
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
		SWK_KMK_MST entity1 = (SWK_KMK_MST) dto;
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
		SWK_KMK_MST entity1 = (SWK_KMK_MST) dto;

		// mysql では空白とNULLは別物になる為、空白をNULLに設定しなおす。
		if (Util.isNullOrEmpty(entity1.getHKM_CODE())) {
			entity1.setHKM_CODE(null);
		}
		if (Util.isNullOrEmpty(entity1.getUKM_CODE())) {
			entity1.setUKM_CODE(null);
		}

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
		SWK_KMK_MST entity1 = (SWK_KMK_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		int kmkCnt = entity1.getKMK_CNT();

		return dao.getSWK_KMK_MSTByKaicodeKmkcnt(kaiCode, kmkCnt);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		SWK_KMK_MST entity1 = (SWK_KMK_MST) dto;
		return entity1.getKMK_CNT() + "";
	}
}

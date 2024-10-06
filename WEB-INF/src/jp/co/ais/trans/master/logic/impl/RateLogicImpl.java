package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * レートマスタ操作
 */
public class RateLogicImpl extends CommonLogicBaseImpl {

	/** レートマスタDao */
	private RATE_MSTDao dao;

	/** レートマスタ実体 */
	private RATE_MST rateentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao RATE_MSTDao
	 */
	public RateLogicImpl(RATE_MSTDao dao) {
		// レートマスタDaoを返す
		this.dao = dao;
	}

	/**
	 * RATE_MSTインスタンスの設定.
	 * 
	 * @param entity RATE_MST
	 */
	public void setEntity(RATE_MST entity) {
		// レートマスタ実体を返す
		this.rateentity = entity;
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
		String beginCurCode = (String) conditions.get("beginCurCode");
		// 終了コードの取得
		String endCurCode = (String) conditions.get("endCurCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginCurCode) && Util.isNullOrEmpty(endCurCode)) {
			// 結果を返す
			return dao.getAllRATE_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginCurCode)) {
			// 結果を返す
			return dao.getRateInfoTo2(kaiCode, endCurCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endCurCode)) {
			// 結果を返す
			return dao.getRateInfoFrom2(kaiCode, beginCurCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getRateInfo(kaiCode, beginCurCode, endCurCode);
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
		// 通貨コードの取得
		String curCode = (String) keys.get("curCode");
		// 適用開始日付の取得
		Date strDate = (Date) keys.get("strDate");
		// 結果を返す
		return dao.getRATE_MST(kaiCode, curCode, strDate);
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 通貨コードの取得
		String curCode = (String) conditions.get("curCode");
		// 適用開始日付の取得
		Date strDate = (Date) conditions.get("strDate");
		// 会社コードの設定
		rateentity.setKAI_CODE(kaiCode);
		// 通貨コードの設定
		rateentity.setCUR_CODE(curCode);
		// 適用開始日付の設定
		rateentity.setSTR_DATE(strDate);
		// データを削除する
		dao.delete(rateentity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		RATE_MST entity = (RATE_MST) dto;

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
		RATE_MST entity = (RATE_MST) dto;

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
		RATE_MST entity = (RATE_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String curCode = entity.getCUR_CODE();
		Date strDate = entity.getSTR_DATE();

		return dao.getRATE_MST(kaiCode, curCode, strDate);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。 *
	 * 
	 * @return String
	 */
	protected String getPrimaryCode() {
		// RATE_MST entity = (RATE_MST)dto;
		return null;
	}
}

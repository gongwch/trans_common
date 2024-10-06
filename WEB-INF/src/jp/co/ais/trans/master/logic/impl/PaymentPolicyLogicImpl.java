package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 支払方針データ操作
 */
public class PaymentPolicyLogicImpl extends CommonLogicBaseImpl {

	/** 支払方針マスタDao */
	private AP_SHH_MSTDao dao;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao AP_SHH_MSTDao
	 */
	public PaymentPolicyLogicImpl(AP_SHH_MSTDao dao) {
		// 支払方針マスタDaoを返す
		this.dao = dao;
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
		return dao.getAllAP_SHH_MST2(kaiCode);
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 結果を返す
		return dao.getAP_SHH_MST(kaiCode);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		AP_SHH_MST entity = (AP_SHH_MST) dto;
		// 登録日付の設定
		entity.setSHH_INP_DATE(Util.getCurrentDate());
		// データを登録する
		dao.insert(entity);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 * @param oldDto
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// 実体の初期化
		AP_SHH_MST entity = (AP_SHH_MST) dto;
		AP_SHH_MST oldEntity = (AP_SHH_MST) oldDto;

		entity.setSHH_INP_DATE(oldEntity.getSHH_INP_DATE());

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
		AP_SHH_MST entity = (AP_SHH_MST) dto;
		String kaiCode = entity.getKAI_CODE();

		return dao.getAP_SHH_MST(kaiCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。 *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		AP_SHH_MST entity = (AP_SHH_MST) dto;
		return entity.getKAI_CODE();
	}
}

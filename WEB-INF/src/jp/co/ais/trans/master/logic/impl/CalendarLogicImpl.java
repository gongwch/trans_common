package jp.co.ais.trans.master.logic.impl;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * カレンダーデータ操作
 */
public class CalendarLogicImpl extends CommonLogicBaseImpl {

	/** カレンダーマスタDao */
	private AP_CAL_MSTDao dao;

	/** カレンダーマスタ実体 */
	private AP_CAL_MST entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao AP_CAL_MSTDao
	 */
	public CalendarLogicImpl(AP_CAL_MSTDao dao) {
		// 通貨マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * AP_CAL_MSTインスタンスの設定.
	 * 
	 * @param entity AP_CAL_MST
	 */
	public void setEntity(AP_CAL_MST entity) {
		// 通貨マスタ実体を返す
		this.entity = entity;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map
	 * @throws ParseException
	 */
	public List find(Map conditions) throws ParseException {

		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 開始日付の初期化
		Date beginCalDate = null;
		// 終了日付の初期化
		Date endCalDate = null;
		// 開始日付の取得
		beginCalDate = DateUtil.toYMDDate(conditions.get("beginCalDate").toString());
		// 終了日付の取得
		endCalDate = DateUtil.toYMDDate(conditions.get("endCalDate").toString());
		// 結果を返す
		return dao.getCalendarInfo(kaiCode, beginCalDate, endCalDate);

	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 * @throws ParseException
	 */
	public Object findOne(Map keys) throws ParseException {
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 日付の取得
		String calDate = (String) keys.get("calDate");
		// 結果を返す
		return dao.getAP_CAL_MST(kaiCode, DateUtil.toYMDDate(calDate));
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) throws ParseException {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 日数の取得
		int intLastDay = Integer.parseInt((String) conditions.get("intLastDay"));
		// 月の取得
		String calMonth = (String) conditions.get("calMonth");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		for (int i = 0; i < intLastDay; i++) {
			calMonth = calMonth.substring(0, 7) + "/" + String.valueOf(i + 1);
			// 日付の取得
			Date calDate = DateUtil.toYMDDate(calMonth);
			// 日付の設定
			entity.setCAL_DATE(calDate);
			try {
				// データを削除する
				dao.delete(entity);
			} catch (Exception e) {
				// 正常に処理されませんでした
				continue;
			}
		}
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		AP_CAL_MST entity1 = (AP_CAL_MST) dto;
		// 登録日付の設定
		entity1.setCAL_INP_DATE(Util.getCurrentDate());
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
		AP_CAL_MST entity1 = (AP_CAL_MST) dto;
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
		AP_CAL_MST entity1 = (AP_CAL_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		Date calDATE = entity1.getCAL_DATE();

		return dao.getAP_CAL_MSTByKaicodeCalcode(kaiCode, calDATE);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		AP_CAL_MST entity1 = (AP_CAL_MST) dto;
		return entity1.getCAL_DATE() + "";
	}
}

package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 支払い方法データ操作
 */
public class PaymentMethodLogicImpl extends CommonLogicBaseImpl implements PaymentMethodLogic {

	private AP_HOH_MSTDao dao;

	private AP_HOH_MST apHohentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao AP_HOH_MSTDao
	 */
	public PaymentMethodLogicImpl(AP_HOH_MSTDao dao) {
		this.dao = dao;
	}

	/**
	 * AP_HOH_MSTインスタンスの設定.
	 * 
	 * @param entity AP_HOH_MST
	 */
	public void setEntity(AP_HOH_MST entity) {
		this.apHohentity = entity;
	}

	public List find(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String beginHohHohCode = (String) conditions.get("beginHohHohCode");
		String endHohHohCode = (String) conditions.get("endHohHohCode");
		String includeEmployeePayment = (String) conditions.get("includeEmployeePayment");
		String includeExternalPayment = (String) conditions.get("includeExternalPayment");

		return dao.query(kaiCode, beginHohHohCode, endHohHohCode, includeEmployeePayment, includeExternalPayment);
	}

	public AP_HOH_MST findOne(Map keys) {
		String kaiCode = (String) keys.get("kaiCode");
		String hohHohCode = (String) keys.get("hohHohCode");

		return dao.getAP_HOH_MSTByKaicodehohhohcode(kaiCode, hohHohCode);
	}

	/**
	 * 支払い方法コードで支払い情報取得
	 * 
	 * @param conditions
	 * @return 支払い方法情報
	 */
	public List findREF(Map conditions) {
		// コード
		String code = (String) conditions.get("code");
		// 略称
		String name_S = (String) conditions.get("name_S");
		// 検索名称
		String name_K = (String) conditions.get("name_K");
		// 条件取得
		String kind = (String) conditions.get("kind");
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		// 結果の取得
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, kind);
		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			AP_HOH_MST e = (AP_HOH_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 支払方法コードの設定
			e2.setCode(e.getHOH_HOH_CODE());
			// 支払方法略称の設定
			e2.setName_S(e.getHOH_HOH_NAME());
			// 支払方法検索名称の設定
			e2.setName_K(e.getHOH_HOH_NAME_K());
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
		// 条件の取得
		String kind = (String) keys.get("kind");
		// 実体の初期化
		AP_HOH_MST entity = dao.getHohHohName(kaiCode, code, kind);
		// 実体を返す
		return (entity == null ? null : entity.getHOH_HOH_NAME());
	}

	public void delete(Map conditions) {
		String kaiCode = (String) conditions.get("kaiCode");
		String hohHohCode = (String) conditions.get("hohHohCode");

		apHohentity.setKAI_CODE(kaiCode);
		apHohentity.setHOH_HOH_CODE(hohHohCode);

		dao.delete(apHohentity);
	}

	protected void insertImpl(Object dto) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;

		entity.setHOH_INP_DATE(Util.getCurrentDate()); // 登録日付

		dao.insert(entity);
	}

	protected void updateImpl(Object dto, Object oldDto) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;
		AP_HOH_MST oldEntity = (AP_HOH_MST) oldDto;

		entity.setHOH_INP_DATE(oldEntity.getHOH_INP_DATE());

		dao.update(entity);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String hohhohCode = entity.getHOH_HOH_CODE();

		return dao.getAP_HOH_MSTByKaicodehohhohcode(kaiCode, hohhohCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。 *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		AP_HOH_MST entity = (AP_HOH_MST) dto;
		return entity.getHOH_HOH_CODE();
	}

	public AP_HOH_MST getREFItems(Map keys) {
		// 結果を返す
		return this.findOne(keys);
	}

	/**
	 * 支払い方法リスト取得
	 * 
	 * @param kaiCode 会社コード
	 * @param hohCode 支払い方法コード
	 * @param sName 略称
	 * @param kName 索引略称
	 * @param termBasisDate 有効期間
	 * @return 支払い方法リスト
	 */
	public List<Object> refDailog(String kaiCode, String hohCode, String sName, String kName, Timestamp termBasisDate) {

		return dao.refDialogSearch(kaiCode, hohCode, sName, kName, termBasisDate);
	}
}

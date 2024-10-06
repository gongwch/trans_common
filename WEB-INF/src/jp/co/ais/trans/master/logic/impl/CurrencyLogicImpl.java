package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 通貨マスタ操作
 */
public class CurrencyLogicImpl extends CommonLogicBaseImpl implements CurrencyLogic {

	/** 通貨マスタDao */
	private CUR_MSTDao dao;

	/** 通貨マスタ実体 */
	private CUR_MST entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao CUR_MSTDao
	 */
	public CurrencyLogicImpl(CUR_MSTDao dao) {
		// 通貨マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * CUR_MSTインスタンスの設定.
	 * 
	 * @param entity CUR_MST
	 */
	public void setEntity(CUR_MST entity) {
		// 通貨マスタ実体を返す
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
		String beginCurCode = (String) conditions.get("beginCurCode");
		// 終了コードの取得
		String endCurCode = (String) conditions.get("endCurCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginCurCode) && Util.isNullOrEmpty(endCurCode)) {
			// 結果を返す
			return dao.getAllCUR_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginCurCode)) {
			// 結果を返す
			return dao.getCurInfoTo(kaiCode, endCurCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endCurCode)) {
			// 結果を返す
			return dao.getCurInfoFrom(kaiCode, beginCurCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getCurInfo(kaiCode, beginCurCode, endCurCode);
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
		// 結果を返す
		return dao.getCUR_MST(kaiCode, curCode);
	}

	/**
	 * REFダイアログ用の検索
	 * 
	 * @conditions 検索条件（Map形式、少なくともcode/name_S/name_K三つ条件を含む）
	 */
	public List findREF(Map conditions) {
		// コード
		String code = (String) conditions.get("code");
		// 略称
		String name_S = (String) conditions.get("name_S");
		// 検索名称
		String name_K = (String) conditions.get("name_K");

		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");

		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 結果の取得
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode);
		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			CUR_MST e = (CUR_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 通貨コードの設定
			e2.setCode(e.getCUR_CODE());
			// 通貨略称の設定
			e2.setName_S(e.getCUR_NAME_S());
			// 通貨検索名称の設定
			e2.setName_K(e.getCUR_NAME_K());
			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * ButtonFieldでコードを入力時、略称の検索
	 * 
	 * @conditions 検索条件（Map形式、findREFと同じ条件）
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");

		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 実体の初期化
		CUR_MST entity1 = dao.getCUR_MST(kaiCode, code);
		// 実体を返す
		return (entity1 == null ? null : entity1.getCUR_NAME_S());
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
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// 通貨コードの設定
		entity.setCUR_CODE(curCode);
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
		CUR_MST entity1 = (CUR_MST) dto;

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
		CUR_MST entity1 = (CUR_MST) dto;

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
		CUR_MST entity1 = (CUR_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String curCode = entity1.getCUR_CODE();

		return dao.getCUR_MST(kaiCode, curCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		CUR_MST entity1 = (CUR_MST) dto;
		return entity1.getCUR_CODE();
	}

	/**
	 * 検索条件による通貨リスト取得
	 * 
	 * @param kaiCode 会社コード
	 * @param curCode 通貨コード
	 * @param sName 略称
	 * @param kName 索引略称
	 * @param termBasisDate 有効期間
	 * @return 通貨リスト
	 */
	public List<Object> refDailogCurrency(String kaiCode, String curCode, String sName, String kName,
		Timestamp termBasisDate, String beginCode, String endCode) {

		return dao.searchRefCurrency(kaiCode, curCode, sName, kName, termBasisDate, beginCode, endCode);
	}

	/**
	 * 通貨コードによる、通貨情報取得
	 * 
	 * @param keys
	 * @return 通貨情報
	 */
	public List getREFItems(Map keys) {

		CUR_MST entity1 = (CUR_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity1 != null) {
			// 結果の初期化
			List<Object> result = new ArrayList<Object>(7);

			String curCode = entity1.getCUR_CODE();
			String triName = entity1.getCUR_NAME();

			result.add(curCode);
			// 銀行名の設定
			result.add(triName != null ? entity1.getCUR_NAME() : "");

			result.add(entity1.getSTR_DATE());
			// 終了
			result.add(entity1.getEND_DATE());
			// 小数点以下桁数
			result.add(entity1.getDEC_KETA());
			// 結果の設定
			list.add(result);
		} else {
			// 結果の削除
			list.add(null);
		}
		// 結果を返す
		return list;
	}
}

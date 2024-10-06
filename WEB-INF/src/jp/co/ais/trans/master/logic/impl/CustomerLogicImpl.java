package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 取引先データロジック
 */
public class CustomerLogicImpl extends CommonLogicBaseImpl implements CustomerLogic {

	/** 取引先マスタDao */
	private TRI_MSTDao dao;

	/** 取引先マスタ実体 */
	private TRI_MST entity;

	/** 会社コード */
	private String companyCode;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao TRI_MSTDao
	 */
	public CustomerLogicImpl(TRI_MSTDao dao) {
		// 取引先マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * TRI_MSTインスタンスの設定.
	 * 
	 * @param entity TRI_MST
	 */
	public void setEntity(TRI_MST entity) {
		// 取引先マスタ実体を返す
		this.entity = entity;
	}

	/**
	 * @see jp.co.ais.trans.master.logic.CustomerLogic#setCompanyCode(java.lang.String)
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
		String beginTriCode = (String) conditions.get("beginTriCode");
		// 終了コードの取得
		String endTriCode = (String) conditions.get("endTriCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginTriCode) && Util.isNullOrEmpty(endTriCode)) {
			// 結果を返す
			return dao.getAllTRI_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginTriCode)) {
			// 結果を返す
			return dao.getTriInfoTo(kaiCode, endTriCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endTriCode)) {
			// 結果を返す
			return dao.getTriInfoFrom(kaiCode, beginTriCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getTriInfo(kaiCode, beginTriCode, endTriCode);
		}
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		String kaiCode = (String) keys.get("kaiCode");// 会社コード
		String triCode = (String) keys.get("triCode"); // 取引先コード

		// 結果を返す
		return dao.getTRI_MSTByKaicodeTricode(kaiCode, triCode);
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		String kind = (String) conditions.get("kind");
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
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, kind);
		// データ集の初期化
		List<Object> list2 = new ArrayList<Object>(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			TRI_MST e = (TRI_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 取引先コードの設定
			e2.setCode(e.getTRI_CODE());
			// 取引先略称の設定
			e2.setName_S(e.getTRI_NAME_S());
			// 取引先検索名称の設定
			e2.setName_K(e.getTRI_NAME_K());

			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys key Map
	 */
	public String findName(Map keys) {
		String kind = (String) keys.get("kind");
		// コード
		String code = (String) keys.get("code");
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 実体の初期化
		TRI_MST entity1 = dao.getTRI_MSTByKaicodeTricode(kaiCode, code);

		if (kind.equals("CustomerWithoutSumCode")) {
			if (entity1 != null && entity1.getSUM_CODE() != null) {
				entity1 = null;
			}
		}

		// 実体を返す
		return (entity1 == null ? null : entity1.getTRI_NAME_S());
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 取引先コードの取得
		String triCode = (String) conditions.get("triCode");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// 取引先コードの設定
		entity.setTRI_CODE(triCode);
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
		TRI_MST entity1 = (TRI_MST) dto;

		// データを登録する
		dao.insert(entity1);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 * @param oldDto
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// 実体の初期化
		TRI_MST entity1 = (TRI_MST) dto;
		TRI_MST oldEntity = (TRI_MST) oldDto;

		// 変わらない項目の値を設定する
		entity1.setSPOT_DEN_NO(oldEntity.getSPOT_DEN_NO());

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
		TRI_MST entity1 = (TRI_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String triCode = entity1.getTRI_CODE();

		return dao.getTRI_MSTByKaicodeTricode(kaiCode, triCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		TRI_MST entity1 = (TRI_MST) dto;
		return entity1.getTRI_CODE();
	}

	/**
	 * 取引先検索<br>
	 * 取引先のボタンフィールドの条件文に対応するように設定。
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param siire
	 * @param tokui
	 * @param beginCode
	 * @param endCode
	 * @return 取引先情報リスト
	 */
	public List<Object> refSearchCustomer(String kaiCode, String triCode, String sName, String kName,
		Timestamp termBasisDate, boolean siire, boolean tokui, String beginCode, String endCode) {
		return dao.searchRefCustomer(kaiCode, triCode, sName, kName, termBasisDate, siire, tokui, beginCode, endCode);
	}

	/**
	 * @see jp.co.ais.trans.master.logic.CustomerLogic#findCustomer(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public TRI_MST findCustomer(String customerCode, String beginCode, String endCode) {
		return dao.getTRI_MSTByBeginEnd(this.companyCode, customerCode, beginCode, endCode);
	}

	/**
	 * 依頼名称更新
	 * 
	 * @param dto
	 */
	public void updateIraiName(TRI_MST dto) {
		dao.updateIraiName(dto);
	}
}

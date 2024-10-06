package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 伝票種別データ操作
 */
public class SlipTypeLogicImpl extends CommonLogicBaseImpl implements SlipTypeLogic {

	/** 伝票種別マスタDao */
	protected  DEN_SYU_MSTDao dao;

	/** 伝票種別マスタ実体 */
	protected DEN_SYU_MST densyuentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao DEN_SYU_MSTDao
	 */
	public SlipTypeLogicImpl(DEN_SYU_MSTDao dao) {
		// 伝票種別マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * DEN_SYU_MSTインスタンスの設定.
	 * 
	 * @param entity DEN_SYU_MST
	 */
	public void setEntity(DEN_SYU_MST entity) {
		// 伝票種別マスタ実体を返す
		this.densyuentity = entity;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// String strDate=(String)conditions.get("strDate");
		// 開始コードの取得
		String beginDenSyuCode = (String) conditions.get("beginDenSyuCode");
		// 終了コードの取得
		String endDenSyuCode = (String) conditions.get("endDenSyuCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginDenSyuCode) && Util.isNullOrEmpty(endDenSyuCode)) {
			// 結果を返す
			return dao.getAllDEN_SYU_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginDenSyuCode)) {
			// 結果を返す
			return dao.getDenSyuInfoTo(kaiCode, endDenSyuCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endDenSyuCode)) {
			// 結果を返す
			return dao.getDenSyuInfoFrom(kaiCode, beginDenSyuCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getDenSyuInfo(kaiCode, beginDenSyuCode, endDenSyuCode);
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
		// 伝票種別ｺｰﾄﾞ の取得
		String denSyuCode = (String) keys.get("denSyuCode");
		// String strDate =Util.convertToString((Date)keys.get("strDate")) ;
		// Date strDate =(Date)keys.get("strDate");
		// 結果を返す
		return dao.getDEN_SYU_MSTByKaicodeDensyucode(kaiCode, denSyuCode);
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		// コード
		String denSyuCode = (String) conditions.get("code");
		// 略称
		String name_S = (String) conditions.get("name_S");
		// 検索名称
		String name_K = (String) conditions.get("name_K");
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		// 結果の取得
		List list = dao.conditionSearch(kaiCode, denSyuCode, name_S, name_K, beginCode, endCode);
		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			DEN_SYU_MST e = (DEN_SYU_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 伝票種別ｺｰﾄﾞ の設定
			e2.setCode(e.getDEN_SYU_CODE());
			// 伝票種別名称の設定
			e2.setName_S(e.getDEN_SYU_NAME_S());
			// 伝票種別略称の設定
			e2.setName_K(e.getDEN_SYU_NAME_K());
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
		String denSyuCode = (String) keys.get("code");
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 実体の初期化
		DEN_SYU_MST entity = dao.getDEN_SYU_MSTByKaicodeDensyucode(kaiCode, denSyuCode);
		// 実体を返す
		return (entity == null ? null : entity.getDEN_SYU_NAME_S());
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 伝票種別ｺｰﾄﾞ の取得
		String denSyuCode = (String) conditions.get("denSyuCode");
		// Date strDate = (Date) conditions.get("strDate");
		// 会社コードの設定
		densyuentity.setKAI_CODE(kaiCode);
		// 伝票種別ｺｰﾄﾞ の設定
		densyuentity.setDEN_SYU_CODE(denSyuCode);
		// entity.setSTR_DATE(strDate);
		// データを削除する
		dao.delete(densyuentity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;

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
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;

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
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String densyuCode = entity.getDEN_SYU_CODE();

		return dao.getDEN_SYU_MSTByKaicodeDensyucode(kaiCode, densyuCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		DEN_SYU_MST entity = (DEN_SYU_MST) dto;
		return entity.getDEN_SYU_CODE();
	}

	/**
	 * 伝票種別略称取得<BR>
	 * 
	 * @param kaiCode 会社コード
	 * @param isIncludeSystemEls true:他システムから取り込んだ伝票種別を対象とする
	 * @return 伝票種別略称リスト
	 */
	public List<DEN_SYU_MST> getDenSyuNames(String kaiCode, boolean isIncludeSystemEls) {

		// 伝票種別略称リスト
		return dao.getDenSyuList(kaiCode, isIncludeSystemEls);
	}
}

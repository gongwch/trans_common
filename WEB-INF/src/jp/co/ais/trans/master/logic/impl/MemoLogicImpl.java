package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 摘要マスタ操作
 */
public class MemoLogicImpl extends CommonLogicBaseImpl implements MemoLogic {

	/** 摘要マスタDao */
	protected TEK_MSTDao dao;

	/** 摘要マスタ実体 */
	protected TEK_MST knr5entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao TEK_MSTDao
	 */
	public MemoLogicImpl(TEK_MSTDao dao) {
		// 摘要マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * TEK_MSTインスタンスの設定.
	 * 
	 * @param entity TEK_MST
	 */
	public void setEntity(TEK_MST entity) {
		// 摘要マスタ実体を返す
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

		String tekKbn = (String) conditions.get("tekKbn");
		// 開始コードの取得
		String beginTekCode = (String) conditions.get("beginTekCode");
		// 終了コードの取得
		String endTekCode = (String) conditions.get("endTekCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginTekCode) && Util.isNullOrEmpty(endTekCode) && Util.isNullOrEmpty(tekKbn)) {
			// 結果を返す
			return dao.getAllTEK_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginTekCode) && Util.isNullOrEmpty(tekKbn)) {
			// 結果を返す
			return dao.getTekInfoTo(kaiCode, endTekCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endTekCode) && Util.isNullOrEmpty(tekKbn)) {
			// 結果を返す
			return dao.getTekInfoFrom(kaiCode, beginTekCode);
		} else if (Util.isNullOrEmpty(beginTekCode) && Util.isNullOrEmpty(endTekCode)) {

			return dao.getAllTEK_MST3(kaiCode, tekKbn);

		} else if (Util.isNullOrEmpty(beginTekCode)) {

			return dao.getTekInfoTo2(kaiCode, endTekCode, tekKbn);

		} else if (Util.isNullOrEmpty(endTekCode)) {

			return dao.getTekInfoFrom2(kaiCode, beginTekCode, tekKbn);

			// 開始コードと終了コード全部ある場合
		} else if (Util.isNullOrEmpty(tekKbn)) {
			// 結果を返す
			return dao.getTekInfo2(kaiCode, beginTekCode, endTekCode);
		} else {
			return dao.getAllCode_MST2(kaiCode, beginTekCode, endTekCode, tekKbn);
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
		// 摘要コードの取得
		String tekCode = (String) keys.get("tekCode");
		// 摘要区分の取得
		int tekKbn = Integer.parseInt((String) keys.get("tekKbn"));
		// 結果を返す
		return dao.getTEK_MST(kaiCode, tekKbn, tekCode);
	}

	public List findREF(Map conditions) {
		// コード
		String code = (String) conditions.get("code");
		// 略称
		String name_S = (String) conditions.get("name_S");
		// 検索名称
		String name_K = (String) conditions.get("name_K");
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode);
		List list2 = new ArrayList(list.size());

		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			TEK_MST e = (TEK_MST) iterator.next();
			REFEntity e2 = new REFEntity();
			e2.setCode(e.getTEK_CODE());
			e2.setName_S(e.getTEK_NAME());
			e2.setName_K(e.getTEK_NAME_K());
			list2.add(e2);
		}

		return list2;
	}

	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		String kaiCode = (String) keys.get("kaiCode");
		// int tekKbn = Integer.parseInt((String)keys.get("tekKbn"));
		List result = dao.conditionSearch2(kaiCode, code);

		if (result.size() > 0) {
			TEK_MST entity = (TEK_MST) result.get(0);
			return entity.getTEK_NAME();
		}

		return null;
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 摘要コードの取得
		String tekCode = (String) conditions.get("tekCode");
		// 摘要区分の取得
		int tekKbn = Integer.parseInt((String) conditions.get("tekKbn"));
		// 会社コードの設定
		knr5entity.setKAI_CODE(kaiCode);
		// 摘要コードの設定
		knr5entity.setTEK_CODE(tekCode);
		// 摘要区分の設定
		knr5entity.setTEK_KBN(tekKbn);
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
		TEK_MST entity = (TEK_MST) dto;

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
		TEK_MST entity = (TEK_MST) dto;

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
		TEK_MST entity = (TEK_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		int tekKbn = entity.getTEK_KBN();
		String tekCode = entity.getTEK_CODE();

		return dao.getTEK_MST(kaiCode, tekKbn, tekCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。 *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		TEK_MST entity = (TEK_MST) dto;
		return entity.getTEK_CODE();
	}

	/**
	 * 摘要コードで詳細を取得
	 * 
	 * @param keys
	 * @return 摘要情報
	 */
	public List getREFItems(Map keys) {
		TEK_MST entity = (TEK_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity != null) {
			// 結果の初期化
			List<Object> result = new ArrayList<Object>(6);

			String tekCode = entity.getTEK_CODE();
			String tekName = entity.getTEK_NAME();

			result.add(tekCode);
			// 銀行名の設定
			result.add(tekName != null ? entity.getTEK_NAME() : "");

			result.add(entity.getDATA_KBN());

			result.add(entity.getSTR_DATE());
			// 終了
			result.add(entity.getEND_DATE());
			// 結果の設定
			list.add(result);
		} else {
			// 結果の削除
			list.add(null);
		}
		// 結果を返す
		return list;
	}

	/**
	 * パラメータ条件で摘要情報リスト習得
	 * 
	 * @param kaiCode 会社コード
	 * @param tekCode 摘要コード
	 * @param sName 略称
	 * @param kName 索引略称
	 * @param memoType 行摘要タイプ
	 * @param slipType 伝票摘要タイプ
	 * @param termBasisDate 有効期間
	 * @return 摘要リスト
	 */
	public List<Object> refDailog(String kaiCode, String tekCode, String sName, String kName, int memoType,
		String slipType, Timestamp termBasisDate) {

		return dao.refDialogSearch(kaiCode, tekCode, sName, kName, memoType, slipType, termBasisDate);
	}
}

package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 銀行マスタ操作
 */
public class BankLogicImpl extends CommonLogicBaseImpl implements BankLogic {

	/** 銀行マスタDao */
	private BNK_MSTDao dao;

	/** 銀行マスタ実体 */
	private BNK_MST entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao BNK_MSTDao
	 */
	public BankLogicImpl(BNK_MSTDao dao) {
		// 銀行マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * BNK_MSTインスタンスの設定.
	 * 
	 * @param entity BNK_MST
	 */
	public void setEntity(BNK_MST entity) {
		// 銀行マスタ実体を返す
		this.entity = entity;
	}

	public List find(Map conditions) {
		// 銀行コード
		String bnkCode = (String) conditions.get("bnkCode");
		// 開始コードの取得
		String beginBnkStnCode = (String) conditions.get("beginBnkStnCode");
		// 終了コードの取得
		String endBnkStnCode = (String) conditions.get("endBnkStnCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(bnkCode)) {
			// 結果を返す
			return dao.getAllBNK_MST2();
			// 銀行コードない場合
		} else if (Util.isNullOrEmpty(beginBnkStnCode) && Util.isNullOrEmpty(endBnkStnCode)) {
			// 結果を返す
			return dao.getBnkInfo1(bnkCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginBnkStnCode)) {
			// 結果を返す
			return dao.getBnkInfo2To(bnkCode, endBnkStnCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endBnkStnCode)) {
			// 結果を返す
			return dao.getBnkInfo2From(bnkCode, beginBnkStnCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getBnkInfo2FromTo(bnkCode, beginBnkStnCode, endBnkStnCode);
		}
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// 会社コードの取得
		String bnkCode = (String) keys.get("bnkCode");
		// 銀行支店コードの取得
		String bnkStnCode = (String) keys.get("bnkStnCode");
		// 結果を返す
		return dao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, bnkStnCode);
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

		String kind = (String) conditions.get("kind");

		if ("Bank".equals(kind)) {
			// 結果の取得
			List list = dao.conditionSearch1(code, name_S, name_K);
			// データ集の初期化
			List list2 = new ArrayList(list.size());
			// 結果の設定
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				// 結果の取得
				BNK_MST e = (BNK_MST) iterator.next();
				// 実体の初期化
				REFEntity e2 = new REFEntity();
				// 銀行コードの設定
				e2.setCode(e.getBNK_CODE());
				// 銀行略称の設定
				e2.setName_S(e.getBNK_NAME_S());
				// 銀行検索名称の設定
				e2.setName_K(e.getBNK_NAME_K());
				// データ集の設定
				list2.add(e2);
			}
			// 結果を返す
			return list2;
		} else { // BankBranch
			String bnkCode = (String) conditions.get("bnkCode");

			String beginCode = (String) conditions.get("beginCode");
			String endCode = (String) conditions.get("endCode");

			List list = dao.conditionSearch2(bnkCode, code, name_S, name_K, beginCode, endCode);
			List list2 = new ArrayList(list.size());

			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				BNK_MST e = (BNK_MST) iterator.next();
				REFEntity e2 = new REFEntity();
				e2.setCode(e.getBNK_STN_CODE());
				e2.setName_S(e.getBNK_STN_NAME_S());
				e2.setName_K(e.getBNK_STN_NAME_K());
				list2.add(e2);
			}

			return list2;
		}
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");

		String kind = (String) keys.get("kind");

		if ("Bank".equals(kind)) {
			// 実体の初期化
			BNK_MST entity1 = dao.getBNK_MST2(code);
			// 実体を返す
			return (entity1 == null ? null : entity1.getBNK_NAME_S());
		} else { // BankBranch
			String bnkCode = (String) keys.get("bnkCode");
			BNK_MST entity1 = dao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, code);
			return (entity1 == null ? null : entity1.getBNK_STN_NAME_S());
		}

	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String bnkCode = (String) conditions.get("bnkCode");
		// 銀行支店コードの取得
		String bnkStnCode = (String) conditions.get("bnkStnCode");
		// 会社コードの設定
		entity.setBNK_CODE(bnkCode);
		// 銀行支店コードの設定
		entity.setBNK_STN_CODE(bnkStnCode);
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
		BNK_MST entity1 = (BNK_MST) dto;

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
		BNK_MST entity1 = (BNK_MST) dto;

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
		BNK_MST entity1 = (BNK_MST) dto;
		String bnkCode = entity1.getBNK_CODE();
		String BnkStnCode = entity1.getBNK_STN_CODE();

		return dao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, BnkStnCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @return String
	 */
	protected String getPrimaryCode() {
		// BNK_MST entity = (BNK_MST)dto;
		return null;
	}

	/**
	 * 銀行口座情報取得 PK検索（支店コード除外）
	 * 
	 * @param param
	 * @return 銀行情報
	 */
	public List<BNK_MST> getBankList(BnkMstParameter param) {
		return dao.getBankList(param);
	}

	/**
	 * 銀行口座情報リスト取得 （支店コード除外）
	 * 
	 * @param param
	 * @return 銀行情報リスト
	 */
	public BNK_MST getBankName(BnkMstParameter param) {
		List<BNK_MST> bnkList = dao.getBankList(param);

		if (bnkList.isEmpty()) {
			return null;
		}

		return bnkList.get(0);
	}

	/**
	 * 銀行支店情報取得 PK検索
	 * 
	 * @param param
	 * @return 銀行支店情報リスト
	 */
	public List<BNK_MST> getStnList(BnkMstParameter param) {
		return dao.getStnList(param);
	}

	/**
	 * 銀行支店情報リスト取得
	 * 
	 * @param param
	 * @return 銀行支店情報リスト
	 */
	public BNK_MST getStnName(BnkMstParameter param) {
		List<BNK_MST> stnList = dao.getStnList(param);

		if (stnList.isEmpty()) {
			return null;
		}

		return stnList.get(0);
	}
}

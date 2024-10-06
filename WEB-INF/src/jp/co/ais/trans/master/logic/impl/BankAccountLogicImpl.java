package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 銀行口座データ操作
 */
public class BankAccountLogicImpl extends CommonLogicBaseImpl implements BankAccountLogic {

	/** 銀行口座マスタDao */
	private AP_CBK_MSTDao dao;

	/** 銀行口座マスタ */
	private AP_CBK_MST entity;

	/** 銀行マスタDao */
	protected BNK_MSTDao bNK_MSTDao;

	private AP_CBK_MSTDao2 aP_CBK_MSTDao2;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao AP_CBK_MSTDao
	 */
	public BankAccountLogicImpl(AP_CBK_MSTDao dao) {
		// 銀行口座ﾏｽﾀDaoを返す
		this.dao = dao;
	}

	/**
	 * AP_CBK_MSTインスタンスの設定.
	 * 
	 * @param entity AP_CBK_MST
	 */
	public void setEntity(AP_CBK_MST entity) {
		// 銀行口座マスタ実体を返す
		this.entity = entity;
	}

	/**
	 * デフォルトコンストラクタ. diconファイルへDaoのコンポーネントを定義し、 コンストラクタの引数として該当Daoを定義すると、 自動的に引数としてわたってくる.
	 * 
	 * @param dao BNK_MSTDao
	 */
	public void setBNK_MSTDao(BNK_MSTDao dao) {
		// 銀行マスタDaoを返す
		this.bNK_MSTDao = dao;
	}

	/**
	 * デフォルトコンストラクタ. diconファイルへDaoのコンポーネントを定義し、 コンストラクタの引数として該当Daoを定義すると、 自動的に引数としてわたってくる.
	 * 
	 * @param dao AP_CBK_MSTDao2
	 */
	public void setAP_CBK_MSTDao2(AP_CBK_MSTDao2 dao) {
		// 銀行マスタDaoを返す
		this.aP_CBK_MSTDao2 = dao;
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
		String beginCbkCbkCode = (String) conditions.get("beginCbkCbkCode");
		// 終了コードの取得
		String endCbkCbkCode = (String) conditions.get("endCbkCbkCode");

		return aP_CBK_MSTDao2.query(kaiCode, beginCbkCbkCode, endCbkCbkCode);
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 銀行口座コードの取得
		String cbkCbkCode = (String) keys.get("cbkCbkCode");
		// 結果を返す
		return dao.getAP_CBK_MSTByKaicodecbkcbkcode(kaiCode, cbkCbkCode);
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

		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");

		String langCode = (String) conditions.get("langCode");
		String word1 = MessageUtil.getWord(langCode, "C00463");
		String word2 = MessageUtil.getWord(langCode, "C00397");
		String word3 = MessageUtil.getWord(langCode, "C00045");
		String word4 = MessageUtil.getWord(langCode, "C00368");
		String wordUnknown = "(" + MessageUtil.getWord(langCode, "C00035") + ")";

		// 結果の取得
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, word1, word2, word3, word4,
			wordUnknown, ServerConfig.getTableSpaceName());
		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			AP_CBK_MST e = (AP_CBK_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 銀行口座コードの設定
			e2.setCode(e.getCBK_CBK_CODE());
			// 銀行口座名称の設定
			e2.setName_S(e.getCBK_NAME());
			// 銀行口座名称検索の設定
			e2.setName_K(e.getCBK_YKN_NO());
			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");

		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 実体の初期化
		AP_CBK_MST entity1 = dao.getAP_CBK_MSTByKaicodecbkcbkcode(kaiCode, code);
		// 実体を返す
		return (entity1 == null ? null : entity1.getCBK_NAME());
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 銀行口座コードの取得
		String cbkCbkCode = (String) conditions.get("cbkCbkCode");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// 銀行口座コードの設定
		entity.setCBK_CBK_CODE(cbkCbkCode);
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
		AP_CBK_MST entity1 = (AP_CBK_MST) dto;
		// 登録日付の設定
		entity1.setCBK_INP_DATE(Util.getCurrentDate());

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
		AP_CBK_MST entity1 = (AP_CBK_MST) dto;
		AP_CBK_MST oldEntity = (AP_CBK_MST) oldDto;

		// 登録日付の設定
		entity1.setCBK_INP_DATE(oldEntity.getCBK_INP_DATE());

		// データを更新する
		dao.update(entity1);
	}

	public List getREFItems(Map keys) {
		AP_CBK_MST entity1 = (AP_CBK_MST) this.findOne(keys);
		List list = new ArrayList(1);

		if (entity1 != null) {
			// 結果の初期化
			List result = new ArrayList(10);
			// 銀行コードの取得
			String bnkCode = entity1.getCBK_BNK_CODE();
			// 支店コードの取得
			String stnCode = entity1.getCBK_STN_CODE();
			// Daoの初期化
			BNK_MST bnkMst = bNK_MSTDao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, stnCode);
			// 銀行コードの設定
			result.add(bnkCode);
			// 銀行名の設定
			result.add(bnkMst != null ? bnkMst.getBNK_NAME_S() : "");
			// 支店コードの設定
			result.add(stnCode);
			// 支店名の設定
			result.add(bnkMst != null ? bnkMst.getBNK_STN_NAME_S() : "");
			// 預金種目の設定
			result.add(entity1.getCBK_YKN_KBN());
			// 口座番号の設定
			result.add(entity1.getCBK_YKN_NO());
			// 開始日付け
			result.add(entity1.getSTR_DATE());
			// 終了
			result.add(entity1.getEND_DATE());
			// 社員支払区分
			result.add(entity1.getCBK_EMP_FB_KBN());
			// 社外支払区分
			result.add(entity1.getCBK_OUT_FB_KBN());
			// 銀行口座名
			result.add(entity1.getCBK_NAME());
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
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		AP_CBK_MST entity1 = (AP_CBK_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String cbkcbkCode = entity1.getCBK_CBK_CODE();

		return dao.getAP_CBK_MSTByKaicodecbkcbkcode(kaiCode, cbkcbkCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		AP_CBK_MST entity1 = (AP_CBK_MST) dto;
		return entity1.getCBK_CBK_CODE();
	}

	/**
	 * 銀行口座検索
	 * 
	 * @param kaiCode
	 * @param code
	 * @param nameS
	 * @param nameK
	 * @param outkbn
	 * @param empkbn
	 * @param termBasisDate
	 * @return 銀行口座リスト
	 */
	public List<Object> searchReservationBankAccount(String kaiCode, String code, String nameS, String nameK,
		boolean outkbn, boolean empkbn, Timestamp termBasisDate) {
		return dao.searchCommonCbkMstData(kaiCode, code, nameS, nameK, outkbn, empkbn, termBasisDate);
	}

	/**
	 * デフォルトの検索条件取得
	 * 
	 * @param kaiCode 会社コード
	 * @return 銀行口座情報
	 */
	public Map<String, Object> getDefaultReceivedAccount(String kaiCode) {
		AP_CBK_MST cbkMst = dao.getDefaultReceivedAccount(kaiCode, ServerConfig.getProperty("nss.default.cbkcode"));

		Map<String, Object> map = new HashMap<String, Object>();

		if (Util.isNullOrEmpty(cbkMst)) {
			return map;
		}

		map.put("cbkCode", Util.avoidNull(cbkMst.getCBK_CBK_CODE()));
		map.put("cbkName", Util.avoidNull(cbkMst.getCBK_NAME()));
		return map;
	}
}

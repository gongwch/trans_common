package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 部門マスタ操作
 */
public class DepartmentLogicImpl extends CommonLogicBaseImpl implements DepartmentLogic {

	/** 部門マスタDao */
	protected BMN_MSTDao dao;

	/** 部門マスタ実体 */
	protected BMN_MST entity;

	/** 会社コード */
	private String companyCode;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao BMN_MSTDao
	 */
	public DepartmentLogicImpl(BMN_MSTDao dao) {
		// 部門マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * @see jp.co.ais.trans.master.logic.DepartmentLogic#setCompanyCode(java.lang.String)
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * BMN_MSTインスタンスの設定.
	 * 
	 * @param entity BMN_MST
	 */
	public void setEntity(BMN_MST entity) {
		// 部門マスタ実体を返す
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
		String beginDepCode = (String) conditions.get("beginDepCode");
		// 終了コードの取得
		String endDepCode = (String) conditions.get("endDepCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginDepCode) && Util.isNullOrEmpty(endDepCode)) {
			// 結果を返す
			return dao.getAllBMN_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginDepCode)) {
			// 結果を返す
			return dao.getBmnInfoTo(kaiCode, endDepCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endDepCode)) {
			// 結果を返す
			return dao.getBmnInfoFrom(kaiCode, beginDepCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getBmnInfo(kaiCode, beginDepCode, endDepCode);
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
		// 部門コードの取得
		String depCode = (String) keys.get("depCode");
		// 結果を返す
		return dao.getBMN_MSTByKaicodeDepcode(kaiCode, depCode);
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

		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");

		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");

		String kind = (String) conditions.get("kind");

		// 結果の取得
		List list = null;

		if ("DepartmentForMG0340".equals(kind)) {
			String dpkSsk = (String) conditions.get("dpkSsk");
			int level = Integer.parseInt((String) conditions.get("level"));
			String parentDepCode = (String) conditions.get("parentDepCode");

			list = dao.getDepartmentsForMG0340(kaiCode, dpkSsk, level, parentDepCode, code, name_S, name_K);
		} else if ("UpperDepartmentForMG0340".equals(kind)) {
			String dpkSsk = (String) conditions.get("dpkSsk");
			int level = Integer.parseInt((String) conditions.get("level"));

			list = dao.getUpperDepartmentsForMG0340(kaiCode, dpkSsk, level - 1, code, name_S, name_K);
		} else {
			list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode, kind);
		}

		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			BMN_MST e = (BMN_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 部門コードの設定
			e2.setCode(e.getDEP_CODE());
			// 部門略称の設定
			e2.setName_S(e.getDEP_NAME_S());
			// 部門検索名称の設定
			e2.setName_K(e.getDEP_NAME_K());
			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * 結果を検索する
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");

		String kind = (String) keys.get("kind");

		BMN_MST entity1 = null;

		if ("DepartmentAll".equals(kind)) {
			entity1 = dao.getBMN_MSTByKaicodeDepcode(kaiCode, code);
		} else if ("DepartmentForMG0340".equals(kind)) {
			String dpkSsk = (String) keys.get("dpkSsk");
			int level = Integer.parseInt((String) keys.get("level"));
			String parentDepCode = (String) keys.get("parentDepCode");

			entity1 = dao.getDepartmentForMG0340(kaiCode, dpkSsk, level, parentDepCode, code);
		} else if ("UpperDepartmentForMG0340".equals(kind)) {
			String dpkSsk = (String) keys.get("dpkSsk");
			int level = Integer.parseInt((String) keys.get("level"));

			entity1 = dao.getUpperDepartmentForMG0340(kaiCode, dpkSsk, level - 1, code);
		} else {
			entity1 = dao.getBMN_MSTByKaicodeDepcode(kaiCode, code);
			// DEP_KBNが１のときはnullを返す
			if (entity1 != null && entity1.getDEP_KBN() != 0) {
				entity1 = null;
			}

			if ("InputDepartment".equals(kind)) {
				if (entity1 != null && entity1.getDEP_KBN() != 0) {
					entity1 = null;
				}
			}
		}
		return (entity1 == null ? null : Util.avoidNull(entity1.getDEP_NAME_S()));
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 部門コードの取得
		String depCode = (String) conditions.get("depCode");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// 部門コードの設定
		entity.setDEP_CODE(depCode);
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
		BMN_MST entity_bmn = (BMN_MST) dto;

		// データを登録する
		dao.insert(entity_bmn);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// 実体の初期化
		BMN_MST entity_bmn = (BMN_MST) dto;

		// データを更新する
		dao.update(entity_bmn);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		BMN_MST entity_bmn = (BMN_MST) dto;
		String kaiCode = entity_bmn.getKAI_CODE();
		String depCode = entity_bmn.getDEP_CODE();

		return dao.getBMN_MSTByKaicodeDepcode(kaiCode, depCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		BMN_MST entity_bmn = (BMN_MST) dto;
		return entity_bmn.getDEP_CODE();
	}

	/**
	 * 部門マスタ検索
	 * 
	 * @param strKaiCode 会社コード
	 * @param strDepCode 部門コード
	 * @param strDepName 部門略称
	 * @param strDepName_K 部門検索名
	 * @param strSlipDate 伝票日付
	 * @param strDpkSsk 組織コード
	 * @param strBmnKbn 配下部門(0:含む 1:含まない)
	 * @param strUpBmnCode 上位部門ｺｰﾄﾞ
	 * @param strDpkLvl 階層ﾚﾍﾞﾙ
	 * @param strBaseDpkLvl 初期階層ﾚﾍﾞﾙ
	 * @param strBaseBmnCode 初期部門ｺｰﾄﾞ
	 * @return 部門マスタリスト
	 */
	public List searchBmnMstData(String strKaiCode, String strDepCode, String strDepName, String strDepName_K,
		String strSlipDate, String strDpkSsk, String strBmnKbn, String strUpBmnCode, String strDpkLvl,
		String strBaseDpkLvl, String strBaseBmnCode) {

		Date slipDate = null;
		try {
			// 伝票日付
			if (!Util.isNullOrEmpty(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}

		} catch (ParseException e) {
			throw new TRuntimeException(e);
		}

		List lstResult = null;
		if (strDpkSsk != null && !"".equals(strDpkSsk)) {
			// データを取得する。
			lstResult = dao.searchBmnMstLvlData(strKaiCode, strDepCode, strDepName, strDepName_K, strDpkSsk, strBmnKbn,
				strUpBmnCode, strDpkLvl, strBaseDpkLvl, strBaseBmnCode);
		} else {
			// データを取得する。
			lstResult = dao.searchBmnMstData(strKaiCode, strDepCode, strDepName, strDepName_K, slipDate);
		}

		return lstResult;
	}

	/**
	 * ダイアログに表示されるデータの取得
	 * 
	 * @param kaiCode 会社コード
	 * @param depCode 部門コード
	 * @param sName 略称
	 * @param kName 検索略称
	 * @param termBasisDate 有効期間
	 * @param organization 組織
	 * @param level 上位レベル
	 * @param upperDepart 上位部門
	 * @param sumDepart 集計部門
	 * @param inputDepart 入力部門
	 * @return 部門情報リスト
	 */
	public List<Object> refSearchDepartment(String kaiCode, String depCode, String sName, String kName,
		Timestamp termBasisDate, String organization, int level, String upperDepart, boolean sumDepart,
		boolean inputDepart, String beginCode, String endCode) {

		return dao.searchDepDpkData(kaiCode, depCode, sName, kName, termBasisDate, organization, level, upperDepart,
			sumDepart, inputDepart, beginCode, endCode);
	}

	/**
	 * @see jp.co.ais.trans.master.logic.DepartmentLogic#findDepartment(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public BMN_MST findDepartment(String deptCode, String beginCode, String endCode) {
		return dao.getBMN_MSTByBeginEnd(this.companyCode, deptCode, beginCode, endCode);
	}

	/**
	 * 指定部門の直下の部門リストだけを習得する。
	 * 
	 * @param comCode
	 * @param dpkCode
	 * @param DpkLvl
	 * @param inDepCode
	 * @param UpperCode
	 * @return 下位部門リスト
	 */
	public List<BMN_MST> getBmnList(String comCode, String dpkCode, int DpkLvl, String inDepCode, String UpperCode) {
		// コード
		String code = "";
		// 略称
		String name_S = ""; // 検索名称
		String name_K = "";

		// 会社コード,組織コードの取得
		String kaiCode = comCode;
		String dpkSsk = dpkCode;
		int level = DpkLvl + 1;
		String parentDepCode = inDepCode;
		if (level == 10) {
			level = 9;
			parentDepCode = UpperCode;
		}

		List list = dao.getDepartmentsForMG0340(kaiCode, dpkSsk, level, parentDepCode, code, name_S, name_K);

		List<BMN_MST> bmnList = new LinkedList<BMN_MST>();

		for (Object bmn : list) {
			bmnList.add((BMN_MST) bmn);
		}
		return bmnList;
	}
}

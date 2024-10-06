package jp.co.ais.trans.master.logic.impl;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 管理マスタ
 */
public class ManagementLogicImpl implements ManagementLogic {

	/** 管理1マスタ一覧 */
	private KNR1_MSTDao KNR1_MSTDao = null;

	/** 管理2マスタ一覧 */
	private KNR2_MSTDao KNR2_MSTDao = null;

	/** 管理3マスタ一覧 */
	private KNR3_MSTDao KNR3_MSTDao = null;

	/** 管理4マスタ一覧 */
	private KNR4_MSTDao KNR4_MSTDao = null;

	/** 管理5マスタ一覧 */
	private KNR5_MSTDao KNR5_MSTDao = null;

	/** 管理6マスタ一覧 */
	private KNR6_MSTDao KNR6_MSTDao = null;

	/**
	 * 管理情報取得<BR>
	 * 管理情報取得。
	 * 
	 * @param conditonMap パラメーターMap
	 * @return triName 管理情報
	 */
	public Map<String, Object> getKnrName(Map conditonMap) {

		Map<String, Object> map = new HashMap<String, Object>();

		String strManagementFlag = (String) conditonMap.get("managementFlag"); // フラグ
		String strKaiCode = (String) conditonMap.get("kaiCode"); // 会社コード
		String strSlipDate = (String) conditonMap.get("slipDate"); // 伝票日付
		String strManagementCode = (String) conditonMap.get("managementCode"); // 管理1コード
		String beginCode = (String) conditonMap.get("beginCode"); // 開始コード
		String endCode = (String) conditonMap.get("endCode"); // 終了コード
		String strKnrName = "";
		String strStrDate = "";
		String strEndDate = "";

		Date slipDate = null;
		try {
			// 伝票日付
			if (!"".equals(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}
		} catch (ParseException e) {
			// ignore
		}

		// 管理1情報取得
		if ("1".equals(strManagementFlag)) {
			KNR1_MST knr1Mst = KNR1_MSTDao.searchKnrNameS1(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr1Mst != null) {
				strKnrName = knr1Mst.getKNR_NAME_S_1();
				strStrDate = Util.avoidNull(knr1Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr1Mst.getEND_DATE());
			}
		}
		// 管理2情報取得
		else if ("2".equals(strManagementFlag)) {
			KNR2_MST knr2Mst = KNR2_MSTDao.searchKnrNameS2(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr2Mst != null) {
				strKnrName = knr2Mst.getKNR_NAME_S_2();
				strStrDate = Util.avoidNull(knr2Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr2Mst.getEND_DATE());
			}
		}
		// 管理3情報取得
		else if ("3".equals(strManagementFlag)) {
			KNR3_MST knr3Mst = KNR3_MSTDao.searchKnrNameS3(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr3Mst != null) {
				strKnrName = knr3Mst.getKNR_NAME_S_3();
				strStrDate = Util.avoidNull(knr3Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr3Mst.getEND_DATE());
			}
		}
		// 管理4情報取得
		else if ("4".equals(strManagementFlag)) {
			KNR4_MST knr4Mst = KNR4_MSTDao.searchKnrNameS4(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr4Mst != null) {
				strKnrName = knr4Mst.getKNR_NAME_S_4();
				strStrDate = Util.avoidNull(knr4Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr4Mst.getEND_DATE());
			}
		}
		// 管理5情報取得
		else if ("5".equals(strManagementFlag)) {
			KNR5_MST knr5Mst = KNR5_MSTDao.searchKnrNameS5(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr5Mst != null) {
				strKnrName = knr5Mst.getKNR_NAME_S_5();
				strStrDate = Util.avoidNull(knr5Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr5Mst.getEND_DATE());
			}
		}
		// 管理6情報取得
		else if ("6".equals(strManagementFlag)) {
			KNR6_MST knr6Mst = KNR6_MSTDao.searchKnrNameS6(slipDate, strKaiCode, strManagementCode, beginCode, endCode);
			if (knr6Mst != null) {
				strKnrName = knr6Mst.getKNR_NAME_S_6();
				strStrDate = Util.avoidNull(knr6Mst.getSTR_DATE());
				strEndDate = Util.avoidNull(knr6Mst.getEND_DATE());
			}
		}
		map.put("strKnrName", strKnrName); // 略称
		map.put("strStrDate", strStrDate); // 開始日付
		map.put("strEndDate", strEndDate); // 終了日付

		return map;
	}

	/**
	 * 管理1マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理1マスタリスト
	 */
	public List searchKnr1MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args) {

		Date slipDate = null;
		try {
			// 伝票日付
			if (!"".equals(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}
		} catch (ParseException e) {
			// ignore
		}

		// データを取得する。
		List lstResult = KNR1_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

	/**
	 * 管理2マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理2マスタリスト
	 */
	public List searchKnr2MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args) {

		Date slipDate = null;
		try {
			// 伝票日付
			if (!"".equals(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}
		} catch (ParseException e) {
			// ignore
		}
		// データを取得する。
		List lstResult = KNR2_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

	/**
	 * 管理3マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理3マスタリスト
	 */
	public List searchKnr3MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args) {

		Date slipDate = null;
		try {
			// 伝票日付
			if (!"".equals(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}
		} catch (ParseException e) {
			// ignore
		}
		// データを取得する。
		List lstResult = KNR3_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

	/**
	 * 管理4マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理4マスタリスト
	 */
	public List searchKnr4MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args) {

		Date slipDate = null;
		try {
			// 伝票日付
			if (!"".equals(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}
		} catch (ParseException e) {
			// ignore
		}
		// データを取得する。
		List lstResult = KNR4_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

	/**
	 * 管理5マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理5マスタリスト
	 */
	public List searchKnr5MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args) {

		Date slipDate = null;
		try {
			// 伝票日付
			if (!"".equals(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}
		} catch (ParseException e) {
			// ignore
		}
		// データを取得する。
		List lstResult = KNR5_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

	/**
	 * 管理6マスタ検索
	 * 
	 * @param strKaiCode 会社コー
	 * @param strKnrCode 社員コード
	 * @param strKnrName 社員略称
	 * @param strKnrName_K 社員検索名
	 * @param strSlipDate 伝票日付
	 * @param args その他パラメータ
	 * @return 管理6マスタリスト
	 */
	public List searchKnr6MstData(String strKaiCode, String strKnrCode, String strKnrName, String strKnrName_K,
		String strSlipDate, String... args) {

		Date slipDate = null;
		try {
			// 伝票日付
			if (!"".equals(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}
		} catch (ParseException e) {
			// ignore
		}
		// データを取得する。
		List lstResult = KNR6_MSTDao.searchKnrMstData(strKaiCode, strKnrCode, strKnrName, strKnrName_K, slipDate,
			args[0], args[1]);

		return lstResult;
	}

	/**
	 * 管理1マスタ一覧設定します。
	 * 
	 * @param KNR1_MSTDao 管理1マスタ一覧
	 */
	public void setKNR1_MSTDao(KNR1_MSTDao KNR1_MSTDao) {
		this.KNR1_MSTDao = KNR1_MSTDao;
	}

	/**
	 * 管理2マスタ一覧設定します。
	 * 
	 * @param KNR2_MSTDao 管理2マスタ一覧
	 */
	public void setKNR2_MSTDao(KNR2_MSTDao KNR2_MSTDao) {
		this.KNR2_MSTDao = KNR2_MSTDao;
	}

	/**
	 * 管理3マスタ一覧設定します。
	 * 
	 * @param KNR3_MSTDao 管理3マスタ一覧
	 */
	public void setKNR3_MSTDao(KNR3_MSTDao KNR3_MSTDao) {
		this.KNR3_MSTDao = KNR3_MSTDao;
	}

	/**
	 * 管理4マスタ一覧設定します。
	 * 
	 * @param KNR4_MSTDao 管理4マスタ一覧
	 */
	public void setKNR4_MSTDao(KNR4_MSTDao KNR4_MSTDao) {
		this.KNR4_MSTDao = KNR4_MSTDao;
	}

	/**
	 * 管理5マスタ一覧設定します。
	 * 
	 * @param KNR5_MSTDao 管理5マスタ一覧
	 */
	public void setKNR5_MSTDao(KNR5_MSTDao KNR5_MSTDao) {
		this.KNR5_MSTDao = KNR5_MSTDao;
	}

	/**
	 * 管理6マスタ一覧設定します。
	 * 
	 * @param KNR6_MSTDao 管理6マスタ一覧
	 */
	public void setKNR6_MSTDao(KNR6_MSTDao KNR6_MSTDao) {
		this.KNR6_MSTDao = KNR6_MSTDao;
	}

}

package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 取引先支払条件マスタ操作
 */
public class CustomerConditionLogicImpl extends CommonLogicBaseImpl implements CustomerConditionLogic {

	/** 取引先支払条件マスタDao */
	private TRI_SJ_MSTDao dao;

	/** 取引先支払条件マスタ実体 */
	private TRI_SJ_MST entity;

	/** 銀行マスタDao */
	private BNK_MSTDao bnkDao;

	/** 支払方法マスタDAO */
	private AP_HOH_MSTDao aphohMstDao;

	/** 銀行口座マスタDAO */
	private AP_CBK_MSTDao apcbkMstDao;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao TRI_SJ_MSTDao
	 */
	public CustomerConditionLogicImpl(TRI_SJ_MSTDao dao) {
		// 取引先支払条件マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * TRI_SJ_MSTインスタンスの設定.
	 * 
	 * @param entity TRI_SJ_MST
	 */
	public void setEntity(TRI_SJ_MST entity) {
		// 取引先支払条件マスタ実体を返す
		this.entity = entity;
	}

	/**
	 * 銀行マスタ一覧設定します。
	 * 
	 * @param dao 銀行マスタ一覧
	 */
	public void setBNK_MSTDao(BNK_MSTDao dao) {
		// 銀行マスタDaoを返す
		this.bnkDao = dao;
	}

	/**
	 * 支払方法マスタ一覧設定します。
	 * 
	 * @param aphohMstDao 支払方法マスタ一覧
	 */
	public void setAphohMstDao(AP_HOH_MSTDao aphohMstDao) {
		this.aphohMstDao = aphohMstDao;
	}

	/**
	 * 銀行口座マスタ一覧設定します。
	 * 
	 * @param apcbkMstDao 銀行口座マスタ一覧
	 */
	public void setApcbkMstDao(AP_CBK_MSTDao apcbkMstDao) {
		this.apcbkMstDao = apcbkMstDao;
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
		String beginTriSjCode = (String) conditions.get("beginTriSjCode");
		// 終了コードの取得
		String endTriSjCode = (String) conditions.get("endTriSjCode");
		// 結果を返す
		return dao.getAllTRI_SJ_MST2(kaiCode, beginTriSjCode, endTriSjCode);

	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 取引先コードの設定
		String triCode = (String) keys.get("triCode");
		// 取引先条件コードの設定
		String tjkCode = (String) keys.get("tjkCode");
		// 結果を返す
		return dao.getTRI_SJ_MSTByKaicodeTricodeTjkcode(kaiCode, triCode, tjkCode);
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
		// 取引先条件コードの取得
		String tjkCode = (String) conditions.get("tjkCode");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// 取引先コードの設定
		entity.setTRI_CODE(triCode);
		// 取引先条件コードの設定
		entity.setTJK_CODE(tjkCode);
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
		TRI_SJ_MST entity1 = (TRI_SJ_MST) dto;

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
		TRI_SJ_MST entity1 = (TRI_SJ_MST) dto;

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
		TRI_SJ_MST entity1 = (TRI_SJ_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String triCode = entity1.getTRI_CODE();
		String tjkCode = entity1.getTJK_CODE();

		return dao.getTRI_SJ_MSTByKaicodeTricodeTjkcode(kaiCode, triCode, tjkCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		TRI_SJ_MST entity1 = (TRI_SJ_MST) dto;
		return entity1.getTJK_CODE();
	}

	/**
	 * ダイアログに表示される取引先リスト取得
	 * 
	 * @param kaiCode 会社コード
	 * @param triCode 取引先コード
	 * @param tjkCode 取引先条件コード
	 * @param termBasisDate 有効期間
	 * @return 取引先リスト
	 */
	public List<Object> refDailog(String kaiCode, String triCode, String tjkCode, Timestamp termBasisDate) {
		// 戻り値のリスト
		List<Object> addedList = new ArrayList<Object>();
		// 条件に当たる支払条件コード達を習得
		List<Object> tjkList = dao.refDialogSearch(kaiCode, triCode, tjkCode, termBasisDate);
		// コードが存在すると
		if (!tjkList.isEmpty()) {
			Iterator i = tjkList.iterator();
			// 条件コード別に支払銀行名を習得する。
			while (i.hasNext()) {
				TRI_SJ_MST triSj = (TRI_SJ_MST) i.next();
				String tjkCodes = triSj.getTJK_CODE();
				String bankAccout = "";
				String yknNo = triSj.getYKN_NO();
				StringBuilder fieldName = new StringBuilder();
				String bankName = triSj.getYKN_NAME();
				String bankStnName = triSj.getYKN_KANA();
				fieldName.append(bankName);
				fieldName.append(" ");
				fieldName.append(Util.avoidNull(bankStnName));
				fieldName.append(" ");
				fieldName.append(Util.avoidNull(yknNo));
				bankAccout = fieldName.toString();
				List<String> added = new ArrayList<String>();
				added.add(tjkCodes);
				added.add(bankAccout);
				// 条件テーブルの支払銀行コードが銀行テーブルに存在する場合、
				// 条件こードと銀行名をリストに追加。
				addedList.add(added);

			}
		}
		return addedList;
	}

	/**
	 * 取引先条件コードコードによる、取引先情報取得
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param tjkCode
	 * @return 取引先情報
	 */
	public List<Object> searchName(String kaiCode, String triCode, String tjkCode) {
		// 戻り値のリスト
		List<Object> addedList = new ArrayList<Object>();
		List<Object> tjkRows = dao.searchBnkName(kaiCode, triCode, tjkCode);

		if (!tjkRows.isEmpty()) {
			TRI_SJ_MST tjkRow = (TRI_SJ_MST) tjkRows.get(0);
			String tjkCodes = tjkRow.getTJK_CODE();
			Date strDate = tjkRow.getSTR_DATE();
			Date endDate = tjkRow.getEND_DATE();
			String yknNo = tjkRow.getYKN_NO();
			// 銀行コード
			String bankAccout = "";
			// 支払銀行コード
			String bnkStnCode = tjkRow.getBNK_STN_CODE();
			// 条件テーブルの銀行コード
			String bnkCode = tjkRow.getBNK_CODE();
			// 銀行コードと支払銀行コードで銀行名を習得
			BNK_MST bnk = bnkDao.getBNK_MSTByBnkcodeBnkStncode(bnkCode, bnkStnCode);
			StringBuilder fieldName = new StringBuilder();
			String bankName = bnk.getBNK_NAME_S();
			String bankStnName = bnk.getBNK_STN_NAME_S();
			fieldName.append(bankName);
			fieldName.append(" ");
			fieldName.append(bankStnName);
			fieldName.append(" ");
			fieldName.append(yknNo);
			bankAccout = fieldName.toString();

			// 条件と銀行名を調合したデータを構成
			List<Object> added = new ArrayList<Object>();
			added.add(tjkCodes);
			added.add(bankAccout);
			added.add(strDate);
			added.add(endDate);

			// 習得した銀行名リストを戻り値リストに載せる。
			addedList.add(added);
		}
		return addedList;
	}

	/**
	 * デフォルトの検索条件取得
	 * 
	 * @param kaiCode 会社コード
	 * @param triCode 取引先コード
	 * @param curCode 通貨コード
	 * @return map 取引先条件コード・支払銀行名
	 */
	public Map<String, Object> getDefaultPaymentCondition(String kaiCode, String triCode, String curCode) {

		TRI_SJ_MST tjkMst = dao.getDefaultPaymentCondition(kaiCode, triCode, curCode);

		// 取引先条件コード
		String tjkCode = "";
		// 銀行コード
		String bnkcode = "";
		// 銀行支店名
		String stncode = "";
		// 口座番号
		String yknNo = "";

		if (!Util.isNullOrEmpty(tjkMst)) {
			tjkCode = Util.avoidNull(tjkMst.getTJK_CODE());
			bnkcode = Util.avoidNull(tjkMst.getBNK_CODE());
			stncode = Util.avoidNull(tjkMst.getBNK_STN_CODE());
			yknNo = Util.avoidNull(tjkMst.getYKN_NO());
		}

		// 支払銀行名
		String bnkname = "";

		// 支払銀行名、口座番号を取得
		BNK_MST bnk = bnkDao.getBNK_MSTByBnkcodeBnkStncode(bnkcode, stncode);
		if (!Util.isNullOrEmpty(bnk)) {
			bnkname = bnk.getBNK_NAME_S() + ' ' + bnk.getBNK_STN_NAME_S() + ' ' + yknNo;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tjkCode", Util.avoidNull(tjkCode));
		map.put("bnkname", Util.avoidNull(bnkname));

		return map;
	}

	/**
	 * 支払先条件入力時に 支払先統合情報を取得
	 * 
	 * @param param
	 * @return map 取引先統合情報
	 * @throws ParseException
	 */
	public Map<String, Object> getPaymentConditionInfo(Map<String, String> param) throws ParseException {
		String kaiCode = param.get("kaiCode");
		String triCode = param.get("triCode");
		String tjkCode = param.get("tjkCode");
		String slipDate = param.get("slipDate");
		TRI_SJ_MST tjkMst = dao.getTRI_SJ_MSTByKaicodeTricodeTjkcode(kaiCode, triCode, tjkCode);
		// 支払先コード
		String tjkcode = "";
		// 支払銀行名
		String bnkname = "";
		// 銀行コード
		String bnkcode = "";
		// 銀行支店名
		String stncode = "";
		// 支払区分
		String sihakbn = "";
		// 支払条件支払日
		String pDate = "";
		// 支払条件締め日
		String cDate = "";
		// 支払条件締め後月
		String mon = "";
		// 振出銀行コード
		String huricode = "";
		// 開始日
		String strDate = "";
		// 終了日
		String endDate = "";
		// 支払方法コード
		String sihahouCode = "";
		// 支払方法名称
		String sihahouName = "";
		// 支払内部コード
		String naicode = "";
		// 振出銀行名称
		String huriname = "";
		// フラグ 0:データなし 1:データ有り
		String flag = "0";
		// 支払日
		String sihaDate = "";

		if (tjkMst != null) {
			flag = "1";
			// 取引先条件コード
			tjkcode = Util.avoidNull(tjkMst.getTJK_CODE());
			// 銀行コード
			bnkcode = Util.avoidNull(tjkMst.getBNK_CODE());
			// 銀行支店名
			stncode = Util.avoidNull(tjkMst.getBNK_STN_CODE());
			// 支払区分
			sihakbn = Util.avoidNull(tjkMst.getSIHA_KBN());
			// 支払条件支払日
			pDate = Util.avoidNull(tjkMst.getSJP_DATE());
			// 支払条件締め日
			cDate = Util.avoidNull(tjkMst.getSJC_DATE());
			// 支払条件締め後月
			mon = Util.avoidNull(tjkMst.getSJR_MON());
			// 振出銀行コード
			huricode = Util.avoidNull(tjkMst.getFURI_CBK_CODE());
			// 開始日
			strDate = DateUtil.toYMDString(tjkMst.getSTR_DATE());
			// 終了日
			endDate = DateUtil.toYMDString(tjkMst.getEND_DATE());
			// 口座番号
			String yknNo = Util.avoidNull(tjkMst.getYKN_NO());

			// 支払銀行名、口座番号を取得
			BNK_MST bnk = bnkDao.getBNK_MSTByBnkcodeBnkStncode(bnkcode, stncode);
			if (!Util.isNullOrEmpty(bnk)) {
				bnkname = Util.avoidNull(bnk.getBNK_NAME_S()) + ' '
					+ Util.avoidNull(bnk.getBNK_STN_NAME_S() + ' ' + yknNo);
			}

			// 支払方法コードの取得
			TRI_SJ_MST tjkMst2 = dao.getSihhoh(kaiCode, triCode, tjkCode);
			if (!Util.isNullOrEmpty(tjkMst2)) {
				sihahouCode = Util.avoidNull(tjkMst2.getSIHA_HOU_CODE());
			}

			// 支払方法名称取得
			AP_HOH_MST aphoh = aphohMstDao.getAP_HOH_MSTByKaicodehohhohcode(kaiCode, sihahouCode);
			if (!Util.isNullOrEmpty(aphoh)) {
				sihahouName = Util.avoidNull(aphoh.getHOH_HOH_NAME());
				naicode = Util.avoidNull(aphoh.getHOH_NAI_CODE());
			}

			// 振出銀行名称取得
			AP_CBK_MST apcbk = apcbkMstDao.getAP_CBK_MSTByKaicodecbkcbkcodeoutfbkbn(kaiCode, huricode);
			String huribnkcode = "";
			String huristncode = "";
			if (!Util.isNullOrEmpty(apcbk)) {
				huribnkcode = Util.avoidNull(apcbk.getCBK_BNK_CODE());
				huristncode = Util.avoidNull(apcbk.getCBK_STN_CODE());

			}

			// 振出銀行名称取得
			BNK_MST huribnk = bnkDao.getBNK_MSTByBnkcodeBnkStncode(huribnkcode, huristncode);
			if (!Util.isNullOrEmpty(huribnk)) {
				huriname = Util.avoidNull(huribnk.getBNK_NAME_S()) + ' ' + Util.avoidNull(huribnk.getBNK_STN_NAME_S());
			}

			// 日付を設定していない場合、支払予定日を求めない
			if (!Util.isNullOrEmpty(slipDate)) {
				// 支払日
				boolean bol = !Util.isNullOrEmpty(cDate) && !Util.isNullOrEmpty(mon) && !Util.isNullOrEmpty(pDate);

				if (bol) {
					sihaDate = BizUtil.getInitialBalanceDate(kaiCode, triCode, tjkCode, slipDate, 0);
				} else {
					sihaDate = slipDate;
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tjkcode", tjkcode); // 取引先条件コード
		map.put("bnkcode", bnkcode); // 支払銀行コード
		map.put("stncode", stncode); // 支店コード
		map.put("bnkname", bnkname); // 支払銀行名
		map.put("sihahouCode", sihahouCode); // 支払方法コード
		map.put("sihahouName", sihahouName); // 支払方法名称
		map.put("huricode", huricode); // 振出銀行コード
		map.put("huriname", huriname); // 振出銀行名称
		map.put("sihakbn", sihakbn); // 支払区分
		map.put("naicode", naicode); // 支払内部コード
		map.put("strDate", strDate); // 開始日付
		map.put("endDate", endDate); // 終了日付
		map.put("flag", flag); // フラグ
		map.put("sihaDate", sihaDate); // 支払予定日

		return map;
	}

	/**
	 * @see jp.co.ais.trans.master.logic.CustomerConditionLogic#findOneInfo(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public Object findOneInfo(String kaiCode, String triCode, String triSjCode) {
		return dao.getOneTRI_SJ_MST2(kaiCode, triCode, triSjCode);
	}
}

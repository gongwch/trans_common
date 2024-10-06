package jp.co.ais.trans.common.server.di;

import java.text.*;
import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 検索ロジッククラス
 */
public class TAccountItemUnitLogicImpl implements TAccountItemUnitLogic {

	/** 科目検索用DAO */
	protected KMK_MSTDao kmkMstDao = null;

	/** 補助科目検索用DAO */
	protected HKM_MSTDao hkmMstDao = null;

	/** 内訳科目検索用DAO */
	protected UKM_MSTDao ukmMstDao = null;

	/**
	 * デフォルトコンストラクタ
	 * 
	 * @param dao 科目検索用DAO
	 */
	public TAccountItemUnitLogicImpl(KMK_MSTDao dao) {
		kmkMstDao = dao;
	}

	/**
	 * 補助科目検索用DAOを設定する。
	 * 
	 * @param hkmMstDao 補助科目検索用DAO
	 */
	public void setHkmMstDao(HKM_MSTDao hkmMstDao) {
		this.hkmMstDao = hkmMstDao;
	}

	/**
	 * 内訳科目検索用DAOを設定する。
	 * 
	 * @param ukmMstDao 内訳科目検索用DAO
	 */
	public void setUkmMstDao(UKM_MSTDao ukmMstDao) {
		this.ukmMstDao = ukmMstDao;
	}

	/**
	 * 科目コード選択時表示データ取得。
	 * 
	 * @param inputParameter パラメーターMap
	 * @return map データマップ
	 * @throws TException
	 */
	public Map<String, Object> getItemInfo(AccountItemInputParameter inputParameter) throws TException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 伝票日付
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// 伝票日付
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// 伝票日付
				inputParameter.setDateSlipDate(null);
			}

			KMK_MST kmkMst = kmkMstDao.findByParameter(inputParameter);

			if (kmkMst != null) {
				map.put("existFlag", "1");
				// 科目略称
				map.put("KMK_NAME_S", String.valueOf(kmkMst.getKMK_NAME_S()));
				// 補助区分
				map.put("HKM_KBN", String.valueOf(kmkMst.getHKM_KBN()));
				// 売上課税入力フラグ
				map.put("URI_ZEI_FLG", String.valueOf(kmkMst.getURI_ZEI_FLG()));
				// 仕入課税入力フラグ
				map.put("SIR_ZEI_FLG", String.valueOf(kmkMst.getSIR_ZEI_FLG()));
				// 消費税コード
				map.put("ZEI_CODE", kmkMst.getZEI_CODE());
				// 消費税
				map.putAll(getSzeiMst(inputParameter.getCompanyCode(), kmkMst.getZEI_CODE()));
				// 社員略称
				map.put("EMP_NAME_S", getEmpName(inputParameter.getCompanyCode(), inputParameter.getEmployeeCode()));
				// 取引先入力フラグ
				map.put("TRI_CODE_FLG", String.valueOf(kmkMst.getTRI_CODE_FLG()));
				// 社員入力フラグ
				map.put("EMP_CODE_FLG", String.valueOf(kmkMst.getEMP_CODE_FLG()));
				// 管理1入力フラグ
				map.put("KNR_FLG_1", String.valueOf(kmkMst.getKNR_FLG_1()));
				// 管理2入力フラグ
				map.put("KNR_FLG_2", String.valueOf(kmkMst.getKNR_FLG_2()));
				// 管理3入力フラグ
				map.put("KNR_FLG_3", String.valueOf(kmkMst.getKNR_FLG_3()));
				// 管理4入力フラグ
				map.put("KNR_FLG_4", String.valueOf(kmkMst.getKNR_FLG_4()));
				// 管理5入力フラグ
				map.put("KNR_FLG_5", String.valueOf(kmkMst.getKNR_FLG_5()));
				// 管理6入力フラグ
				map.put("KNR_FLG_6", String.valueOf(kmkMst.getKNR_FLG_6()));
				// 非会計明細1入力フラグ
				map.put("HM_FLG_1", String.valueOf(kmkMst.getHM_FLG_1()));
				// 非会計明細2入力フラグ
				map.put("HM_FLG_2", String.valueOf(kmkMst.getHM_FLG_2()));
				// 非会計明細3入力フラグ
				map.put("HM_FLG_3", String.valueOf(kmkMst.getHM_FLG_3()));
				// 発生日入力フラグ
				map.put("HAS_FLG", String.valueOf(kmkMst.getHAS_FLG()));
				// 多通貨入力フラグ
				map.put("MCR_FLG", String.valueOf(kmkMst.getMCR_FLG()));
				// 社員コード
				map.put("EMP_CODE", inputParameter.getEmployeeCode());
				// GL科目制御区分
				map.put("KMK_CNT_GL", String.valueOf(kmkMst.getKMK_CNT_GL()));
				// AP科目制御区分
				map.put("KMK_CNT_AP", String.valueOf(kmkMst.getKMK_CNT_AP()));
				// AR科目制御区分
				map.put("KMK_CNT_AR", String.valueOf(kmkMst.getKMK_CNT_AR()));

			} else {
				map.put("existFlag", "0");
				// 科目略称
				map.put("KMK_NAME_S", "");
			}

			return map;
		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * 科目一覧検索
	 * 
	 * @param inputParameter パラメータ
	 * @return 科目データ
	 * @throws TException
	 */
	public List getItemInfoAll(AccountItemInputParameter inputParameter) throws TException {
		try {
			// 伝票日付
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// 伝票日付
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// 伝票日付
				inputParameter.setDateSlipDate(null);
			}

			// データを取得する。
			return kmkMstDao.findListByParameter(inputParameter);
		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * 補助科目コード選択時表示データ取得。
	 * 
	 * @param inputParameter パラメーターMap
	 * @return map データマップ
	 * @throws TException
	 */
	public Map<String, Object> getSubItemInfo(AccountItemInputParameter inputParameter) throws TException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 伝票日付
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// 伝票日付
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// 伝票日付
				inputParameter.setDateSlipDate(null);
			}

			HKM_MST hkmMst = hkmMstDao.findByParameter(inputParameter);

			if (hkmMst != null) {
				map.put("existFlag", "1");
				// 科目略称
				map.put("HKM_NAME_S", String.valueOf(hkmMst.getHKM_NAME_S()));
				// 補助区分
				map.put("UKM_KBN", String.valueOf(hkmMst.getUKM_KBN()));
				// 売上課税入力フラグ
				map.put("URI_ZEI_FLG", String.valueOf(hkmMst.getURI_ZEI_FLG()));
				// 仕入課税入力フラグ
				map.put("SIR_ZEI_FLG", String.valueOf(hkmMst.getSIR_ZEI_FLG()));
				// 消費税コード
				map.put("ZEI_CODE", hkmMst.getZEI_CODE());
				// 消費税コード
				map.putAll(getSzeiMst(inputParameter.getCompanyCode(), hkmMst.getZEI_CODE()));
				// 社員略称
				map.put("EMP_NAME_S", getEmpName(inputParameter.getCompanyCode(), inputParameter.getEmployeeCode()));
				// 取引先入力フラグ
				map.put("TRI_CODE_FLG", String.valueOf(hkmMst.getTRI_CODE_FLG()));
				// 社員入力フラグ
				map.put("EMP_CODE_FLG", String.valueOf(hkmMst.getEMP_CODE_FLG()));
				// 管理1入力フラグ
				map.put("KNR_FLG_1", String.valueOf(hkmMst.getKNR_FLG_1()));
				// 管理2入力フラグ
				map.put("KNR_FLG_2", String.valueOf(hkmMst.getKNR_FLG_2()));
				// 管理3入力フラグ
				map.put("KNR_FLG_3", String.valueOf(hkmMst.getKNR_FLG_3()));
				// 管理4入力フラグ
				map.put("KNR_FLG_4", String.valueOf(hkmMst.getKNR_FLG_4()));
				// 管理5入力フラグ
				map.put("KNR_FLG_5", String.valueOf(hkmMst.getKNR_FLG_5()));
				// 管理6入力フラグ
				map.put("KNR_FLG_6", String.valueOf(hkmMst.getKNR_FLG_6()));
				// 非会計明細1入力フラグ
				map.put("HM_FLG_1", String.valueOf(hkmMst.getHM_FLG_1()));
				// 非会計明細2入力フラグ
				map.put("HM_FLG_2", String.valueOf(hkmMst.getHM_FLG_2()));
				// 非会計明細3入力フラグ
				map.put("HM_FLG_3", String.valueOf(hkmMst.getHM_FLG_3()));
				// 発生日入力フラグ
				map.put("HAS_FLG", String.valueOf(hkmMst.getHAS_FLG()));
				// 多通貨入力フラグ
				map.put("MCR_FLG", String.valueOf(hkmMst.getMCR_FLG()));
				// 社員コード
				map.put("EMP_CODE", inputParameter.getEmployeeCode());
			} else {
				map.put("existFlag", "0");
				// 科目略称
				map.put("HKM_NAME_S", "");
			}

			return map;
		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * 補助科目一覧検索
	 * 
	 * @param inputParameter パラメータ
	 * @return 補助科目データ
	 */
	public List getSubItemInfoAll(AccountItemInputParameter inputParameter) throws TException {
		try {
			// 伝票日付
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// 伝票日付
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// 伝票日付
				inputParameter.setDateSlipDate(null);
			}
			// データを取得する。
			return hkmMstDao.findListByParameter(inputParameter);

		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * 内訳科目コード選択時表示データ取得。
	 * 
	 * @param inputParameter パラメーターMap
	 * @return map データマップ
	 * @throws TException
	 */
	public Map<String, Object> getBreakDownItemInfo(AccountItemInputParameter inputParameter) throws TException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			// 伝票日付
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// 伝票日付
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// 伝票日付
				inputParameter.setDateSlipDate(null);
			}

			UKM_MST ukmMst = ukmMstDao.findByParameter(inputParameter);

			if (ukmMst != null) {
				map.put("existFlag", "1");
				// 科目略称
				map.put("UKM_NAME_S", String.valueOf(ukmMst.getUKM_NAME_S()));
				// 売上課税入力フラグ
				map.put("URI_ZEI_FLG", String.valueOf(ukmMst.getURI_ZEI_FLG()));
				// 仕入課税入力フラグ
				map.put("SIR_ZEI_FLG", String.valueOf(ukmMst.getSIR_ZEI_FLG()));
				// 消費税コード
				map.put("ZEI_CODE", ukmMst.getZEI_CODE());
				// 消費税コード
				map.putAll(getSzeiMst(inputParameter.getCompanyCode(), ukmMst.getZEI_CODE()));
				// 社員略称
				map.put("EMP_NAME_S", getEmpName(inputParameter.getCompanyCode(), inputParameter.getEmployeeCode()));
				// 取引先入力フラグ
				map.put("TRI_CODE_FLG", String.valueOf(ukmMst.getTRI_CODE_FLG()));
				// 社員入力フラグ
				map.put("EMP_CODE_FLG", String.valueOf(ukmMst.getEMP_CODE_FLG()));
				// 管理1入力フラグ
				map.put("KNR_FLG_1", String.valueOf(ukmMst.getKNR_FLG_1()));
				// 管理2入力フラグ
				map.put("KNR_FLG_2", String.valueOf(ukmMst.getKNR_FLG_2()));
				// 管理3入力フラグ
				map.put("KNR_FLG_3", String.valueOf(ukmMst.getKNR_FLG_3()));
				// 管理4入力フラグ
				map.put("KNR_FLG_4", String.valueOf(ukmMst.getKNR_FLG_4()));
				// 管理5入力フラグ
				map.put("KNR_FLG_5", String.valueOf(ukmMst.getKNR_FLG_5()));
				// 管理6入力フラグ
				map.put("KNR_FLG_6", String.valueOf(ukmMst.getKNR_FLG_6()));
				// 非会計明細1入力フラグ
				map.put("HM_FLG_1", String.valueOf(ukmMst.getHM_FLG_1()));
				// 非会計明細2入力フラグ
				map.put("HM_FLG_2", String.valueOf(ukmMst.getHM_FLG_2()));
				// 非会計明細3入力フラグ
				map.put("HM_FLG_3", String.valueOf(ukmMst.getHM_FLG_3()));
				// 発生日入力フラグ
				map.put("HAS_FLG", String.valueOf(ukmMst.getHAS_FLG()));
				// 多通貨入力フラグ
				map.put("MCR_FLG", String.valueOf(ukmMst.getMCR_FLG()));
				// 社員コード
				map.put("EMP_CODE", inputParameter.getEmployeeCode());
			} else {
				map.put("existFlag", "0");
				// 科目略称
				map.put("UKM_NAME_S", "");
			}

			return map;

		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * 内訳科目一覧検索
	 * 
	 * @param inputParameter パラメータ
	 * @return 内訳科目データ
	 * @throws TException
	 */
	public List getBreakDownItemInfoAll(AccountItemInputParameter inputParameter) throws TException {
		try {
			// 伝票日付
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// 伝票日付
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// 伝票日付
				inputParameter.setDateSlipDate(null);
			}

			// データを取得する。
			return ukmMstDao.findListByParameter(inputParameter);

		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * 社員略称を取得する。
	 * 
	 * @param kaiCode 会社コード
	 * @param empCode 社員コード
	 * @return ZeiName 社員略称
	 */
	protected String getEmpName(String kaiCode, String empCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		EMP_MSTDao emp = (jp.co.ais.trans.master.dao.EMP_MSTDao) container
			.getComponent(jp.co.ais.trans.master.dao.EMP_MSTDao.class);

		EMP_MST empMst = emp.getEMP_MSTByKaicodeEmpcode(kaiCode, empCode);
		if (empMst != null) {
			return empMst.getEMP_NAME_S();
		}

		return "";
	}

	/**
	 * 消費税情報を取得
	 * 
	 * @param kaiCode 会社コード
	 * @param zeiCode 消費税コード
	 * @return ZeiName 消費税略称
	 */
	protected Map<String, Object> getSzeiMst(String kaiCode, String zeiCode) {

		if (Util.isNullOrEmpty(zeiCode)) {
			return new HashMap<String, Object>(0);
		}

		Map<String, Object> map = new HashMap<String, Object>();

		S2Container container = SingletonS2ContainerFactory.getContainer();
		SZEI_MSTDao szei = (jp.co.ais.trans.master.dao.SZEI_MSTDao) container
			.getComponent(jp.co.ais.trans.master.dao.SZEI_MSTDao.class);

		SZEI_MST szeiMst = szei.getSZEI_MSTByKaicodeSzeicode(kaiCode, zeiCode);

		if (szeiMst != null) {
			map.put("ZEI_NAME_S", szeiMst.getZEI_NAME_S());

			// 仕入か売上か非課税か
			String szeiKeiKbn = "0";

			if (szeiMst.getUS_KBN() != 0 && szeiMst.getZEI_RATE() != 0) {
				szeiKeiKbn = Integer.toString(szeiMst.getUS_KBN());
			}

			map.put("SZEI_KEI_KBN", szeiKeiKbn);

		}

		return map;
	}
}

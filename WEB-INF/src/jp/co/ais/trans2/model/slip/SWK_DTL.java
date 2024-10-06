package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.management.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.tax.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * 仕訳明細
 */
public class SWK_DTL extends TransferBase implements Cloneable {

	/** 自動仕訳区分定数 */
	public static final class AUTO_KBN {

		/** 0:通常伝票 */
		public static final int NOMAL = 0;

		/** 1:自動仕訳伝票 */
		public static final int AUTO = 1;

		/** 2:赤伝 = 逆仕訳) */
		public static final int REVERSE = 2;
	}

	/** 税区分定数 */
	public static final class ZEI_KBN {

		/** 0:外税 */
		public static final int TAX_OUT = 0;

		/** 1:内税 */
		public static final int TAX_IN = 1;

		/** 2:非課税 */
		public static final int TAX_NONE = 2;
	}

	/** 消込区分定数 */
	public static final class KESI_KBN {

		/** 0:通常 */
		public static final int NOMAL = 0;

		/** 1:消込元仕訳行 */
		public static final int BASE = 1;

		/** 2:消込先仕訳行 */
		public static final int FORWARD = 2;
	}

	/** AP/AR消込区分定数 */
	public static final class APAR_KESI_KBN {

		/** 0:通常 */
		public static final int NOMAL = 0;

		/** 1:AP消込元仕訳行 */
		public static final int AP_BASE = 1;

		/** 2:AP消込先仕訳行 */
		public static final int AP_FORWARD = 2;

		/** 3:AR消込元仕訳行 */
		public static final int AR_BASE = 3;

		/** 4:AR消込先仕訳行 */
		public static final int AR_FORWARD = 4;
	}

	/** 自由区分定数 */
	public static final class FREE_KBN {

		/** 0:未使用 */
		public static final int NOMAL = 0;

		/** 1:経過措置明細 */
		public static final int KEKA_SOTI = 1;

		// 以外拡張待ち
	}

	/** 会社コード */
	protected String kAI_CODE;

	/** 伝票日付 */
	protected Date sWK_DEN_DATE;

	/** 伝票番号 */
	protected String sWK_DEN_NO;

	/** BOOK No.(1:通常仕訳 2:機能通貨仕訳) */
	protected int sWK_BOOK_NO = CurrencyType.KEY.value;

	/** 行番号 */
	protected int sWK_GYO_NO;

	/** IFRS調整区分(0:両方 1:自国のみ 2:IFRSのみ) */
	protected int sWK_ADJ_KBN = AccountBook.BOTH.value;

	/** 年度 */
	protected int sWK_NENDO;

	/** 月度 */
	protected int sWK_TUKIDO;

	/** 証憑番号 */
	protected String sWK_SEI_NO;

	/** 貸借区分 */
	protected int sWK_DC_KBN;

	/** 科目コード */
	protected String sWK_KMK_CODE;

	/** 科目名称 */
	protected String sWK_KMK_NAME;

	/** 科目略称 */
	protected String sWK_KMK_NAME_S;

	/** 補助科目コード */
	protected String sWK_HKM_CODE;

	/** 補助科目名称 */
	protected String sWK_HKM_NAME;

	/** 補助科目略称 */
	protected String sWK_HKM_NAME_S;

	/** 内訳科目コード */
	protected String sWK_UKM_CODE;

	/** 内訳科目名称 */
	protected String sWK_UKM_NAME;

	/** 内訳科目略称 */
	protected String sWK_UKM_NAME_S;

	/** 計上部門コード */
	protected String sWK_DEP_CODE;

	/** 計上部門名称 */
	protected String sWK_DEP_NAME;

	/** 計上部門略称 */
	protected String sWK_DEP_NAME_S;

	/** 税区分 */
	protected int sWK_ZEI_KBN;

	/** 邦貨金額 */
	protected BigDecimal sWK_KIN;

	/** 消費税額 */
	protected BigDecimal sWK_ZEI_KIN;

	/** 消費税コード */
	protected String sWK_ZEI_CODE;

	/** 消費税名称 */
	protected String sWK_ZEI_NAME;

	/** 消費税略称 */
	protected String sWK_ZEI_NAME_S;

	/** 税率 */
	protected BigDecimal sWK_ZEI_RATE;

	/** 行摘要コード */
	protected String sWK_GYO_TEK_CODE;

	/** 行摘要 */
	protected String sWK_GYO_TEK;

	/** 取引先コード */
	protected String sWK_TRI_CODE;

	/** 取引先名称 */
	protected String sWK_TRI_NAME;

	/** 取引先略称 */
	protected String sWK_TRI_NAME_S;

	/** 集計取引先コード */
	protected String sUM_TRI_CODE;

	/** 集計取引先略称 */
	protected String sUM_TRI_NAME_S;

	/** 社員コード */
	protected String sWK_EMP_CODE;

	/** 社員名称 */
	protected String sWK_EMP_NAME;

	/** 社員略称 */
	protected String sWK_EMP_NAME_S;

	/** 管理１コード */
	protected String sWK_KNR_CODE_1;

	/** 管理１名称 */
	protected String sWK_KNR_NAME_1;

	/** 管理１略称 */
	protected String sWK_KNR_NAME_S_1;

	/** 管理２コード */
	protected String sWK_KNR_CODE_2;

	/** 管理２名称 */
	protected String sWK_KNR_NAME_2;

	/** 管理２略称 */
	protected String sWK_KNR_NAME_S_2;

	/** 管理３コード */
	protected String sWK_KNR_CODE_3;

	/** 管理３名称 */
	protected String sWK_KNR_NAME_3;

	/** 管理３略称 */
	protected String sWK_KNR_NAME_S_3;

	/** 管理４コード */
	protected String sWK_KNR_CODE_4;

	/** 管理４名称 */
	protected String sWK_KNR_NAME_4;

	/** 管理４略称 */
	protected String sWK_KNR_NAME_S_4;

	/** 管理５コード */
	protected String sWK_KNR_CODE_5;

	/** 管理５名称 */
	protected String sWK_KNR_NAME_5;

	/** 管理５略称 */
	protected String sWK_KNR_NAME_S_5;

	/** 管理６コード */
	protected String sWK_KNR_CODE_6;

	/** 管理６名称 */
	protected String sWK_KNR_NAME_6;

	/** 管理６略称 */
	protected String sWK_KNR_NAME_S_6;

	/** 非会計明細１ */
	protected String sWK_HM_1;

	/** 非会計明細２ */
	protected String sWK_HM_2;

	/** 非会計明細３ */
	protected String sWK_HM_3;

	/** 自動仕訳区分 */
	protected int sWK_AUTO_KBN;

	/** データ区分 */
	protected String sWK_DATA_KBN;

	/** 更新区分 */
	protected int sWK_UPD_KBN;

	/** 決算区分 */
	protected int sWK_KSN_KBN;

	/** 相手科目コード */
	protected String sWK_AT_KMK_CODE;

	/** 相手補助科目コード */
	protected String sWK_AT_HKM_CODE;

	/** 相手内訳科目コード */
	protected String sWK_AT_UKM_CODE;

	/** 相手部門コード */
	protected String sWK_AT_DEP_CODE;

	/** 登録日付 */
	protected Date iNP_DATE;

	/** 更新日付 */
	protected Date uPD_DATE;

	/** プログラムＩＤ */
	protected String pRG_ID;

	/** ユーザーＩＤ */
	protected String uSR_ID;

	/** 計上会社ｺｰﾄﾞ */
	protected String sWK_K_KAI_CODE;

	/** 計上会社名 */
	protected String sWK_K_KAI_NAME;

	/** 計上会社略称 */
	protected String sWK_K_KAI_NAME_S;

	/** 通貨ｺｰﾄﾞ */
	protected String sWK_CUR_CODE;

	/** ﾚｰﾄ */
	protected BigDecimal sWK_CUR_RATE;

	/** 入力金額 */
	protected BigDecimal sWK_IN_KIN;

	/** 会社間付替伝票区分 */
	protected int sWK_TUKE_KBN;

	/** 入力消費税額 */
	protected BigDecimal sWK_IN_ZEI_KIN;

	/** 消込区分 */
	protected int sWK_KESI_KBN;

	/** 消込伝票日付 */
	protected Date sWK_KESI_DATE;

	/** 消込伝票番号 */
	protected String sWK_KESI_DEN_NO;

	/** 相殺伝票日付 */
	protected Date sWK_SOUSAI_DATE;

	/** 相殺伝票番号 */
	protected String sWK_SOUSAI_DEN_NO;

	/** 相殺行番号 */
	protected Integer sWK_SOUSAI_GYO_NO = null;

	/** 発生日 */
	protected Date hAS_DATE;

	/** 伝票種別ｺｰﾄﾞ */
	protected String dEN_SYU_CODE;

	// パターン用 --

	/** 伝票修正回数 */
	protected int sWK_UPD_CNT;

	// 追加 --

	/** 通貨 少数点桁数 */
	protected int cUR_DEC_KETA;

	/** 通貨 レート係数 */
	protected int cUR_RATE_POW;

	/** 基軸通貨コード */
	protected String kEY_CUR_CODE;

	/** 基軸通貨小数点桁数 */
	protected int kEY_CUR_DEC_KETA;

	/** 機能通貨コード */
	protected String fUNC_CUR_CODE;

	/** 機能通貨小数点桁数 */
	protected int fUNC_CUR_DEC_KETA;

	/** 消費税仕訳かどうか */
	protected boolean taxJornal;

	/** 計上会社 */
	protected Company appropriateCompany;

	/** 計上部門 */
	protected Department department;

	/** 科目 */
	protected Item item;

	/** 行摘要 */
	protected Remark remark;

	/** 消費税 */
	protected ConsumptionTax tax;

	/** 通貨 */
	protected Currency currency;

	/** 社員 */
	protected Employee employee;

	/** 取引先 */
	protected Customer customer;

	/** 管理1 */
	protected Management1 management1;

	/** 管理2 */
	protected Management2 management2;

	/** 管理3 */
	protected Management3 management3;

	/** 管理4 */
	protected Management4 management4;

	/** 管理5 */
	protected Management5 management5;

	/** 管理6 */
	protected Management6 management6;

	/** 債務残高(消込用) */
	protected AP_ZAN apBalance;

	/** 債権残高(消込用) */
	protected AR_ZAN arBalance;

	/** 決算仕訳かどうか */
	protected boolean settlementJornal;

	/** BS勘定消込仕訳 */
	protected SWK_DTL bsDetail = null;

	/** 港費概算伝票番号 */
	protected String sWK_EST_DEN_NO;

	/** 港費実算伝票番号 */
	protected String sWK_STL_DEN_NO;

	/** 港費前渡金伝票番号 */
	protected String sWK_MAE_DEN_NO;

	/** 港費前渡金行番号 */
	protected Integer sWK_MAE_GYO_NO;

	/** 科目発生日使用 */
	protected boolean useItemOccurDate = false;

	/** AP/AR消込区分 */
	protected int sWK_APAR_KESI_KBN = 0;

	/** AP/AR消込伝票番号 */
	protected String sWK_APAR_DEN_NO = null;

	/** AP/AR消込行番号 */
	protected Integer sWK_APAR_GYO_NO = null;

	/** 仕訳明細用OPアイテム(注意：DB関係なくOP処理用のみ) */
	protected OPItem journalOPItem = null;

	/** 伝票追加情報(注意：DB関係なくOP処理用のみ) */
	protected SlipDetailAddonInfo addonInfo = new SlipDetailAddonInfo();

	/** 自由区分 */
	protected int SWK_FREE_KBN = 0;

	/** 経過措置の元行番号 */
	protected Integer SWK_ORI_GYO_NO = null;

	/** 経過措置仕訳であるかどうか true：経過措置仕訳 */
	protected boolean isKekaDtl = false;

	/** 経過措置控除可能率(1%～99%) */
	protected BigDecimal KEKA_SOTI_RATE = null;

	/**
	 * オブジェクトのClone
	 */
	@Override
	public SWK_DTL clone() {
		try {
			SWK_DTL clone = (SWK_DTL) super.clone();
			clone.apBalance = this.apBalance;
			clone.arBalance = this.arBalance;
			clone.bsDetail = this.bsDetail;

			return clone;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 伝票種別ｺｰﾄﾞ
	 * 
	 * @return 伝票種別ｺｰﾄﾞ
	 */
	public String getDEN_SYU_CODE() {
		return dEN_SYU_CODE;
	}

	/**
	 * 伝票種別ｺｰﾄﾞ
	 * 
	 * @param den_syu_code 伝票種別ｺｰﾄﾞ
	 */
	public void setDEN_SYU_CODE(String den_syu_code) {
		dEN_SYU_CODE = den_syu_code;
	}

	/**
	 * 発生日
	 * 
	 * @return 発生日
	 */
	public Date getHAS_DATE() {
		return hAS_DATE;
	}

	/**
	 * 発生日
	 * 
	 * @param has_date 発生日
	 */
	public void setHAS_DATE(Date has_date) {
		hAS_DATE = has_date;
	}

	/**
	 * 登録日付
	 * 
	 * @return 登録日付
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * 登録日付
	 * 
	 * @param inp_date 登録日付
	 */
	public void setINP_DATE(Date inp_date) {
		iNP_DATE = inp_date;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * 会社コード
	 * 
	 * @param kai_code 会社コード
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * プログラムＩＤ
	 * 
	 * @return プログラムＩＤ
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * プログラムＩＤ
	 * 
	 * @param prg_id プログラムＩＤ
	 */
	public void setPRG_ID(String prg_id) {
		pRG_ID = prg_id;
	}

	/**
	 * 相手部門コード
	 * 
	 * @return 相手部門コード
	 */
	public String getSWK_AT_DEP_CODE() {
		return sWK_AT_DEP_CODE;
	}

	/**
	 * 相手部門コード
	 * 
	 * @param swk_at_dep_code 相手部門コード
	 */
	public void setSWK_AT_DEP_CODE(String swk_at_dep_code) {
		sWK_AT_DEP_CODE = swk_at_dep_code;
	}

	/**
	 * 相手補助科目コード
	 * 
	 * @return 相手補助科目コード
	 */
	public String getSWK_AT_HKM_CODE() {
		return sWK_AT_HKM_CODE;
	}

	/**
	 * 相手補助科目コード
	 * 
	 * @param swk_at_hkm_code 相手補助科目コード
	 */
	public void setSWK_AT_HKM_CODE(String swk_at_hkm_code) {
		sWK_AT_HKM_CODE = swk_at_hkm_code;
	}

	/**
	 * 相手科目コード
	 * 
	 * @return 相手科目コード
	 */
	public String getSWK_AT_KMK_CODE() {
		return sWK_AT_KMK_CODE;
	}

	/**
	 * 相手科目コード
	 * 
	 * @param swk_at_kmk_code 相手科目コード
	 */
	public void setSWK_AT_KMK_CODE(String swk_at_kmk_code) {
		sWK_AT_KMK_CODE = swk_at_kmk_code;
	}

	/**
	 * 相手内訳科目コード
	 * 
	 * @return 相手内訳科目コード
	 */
	public String getSWK_AT_UKM_CODE() {
		return sWK_AT_UKM_CODE;
	}

	/**
	 * 相手内訳科目コード
	 * 
	 * @param swk_at_ukm_code 相手内訳科目コード
	 */
	public void setSWK_AT_UKM_CODE(String swk_at_ukm_code) {
		sWK_AT_UKM_CODE = swk_at_ukm_code;
	}

	/**
	 * 自動仕訳区分
	 * 
	 * @return 自動仕訳区分
	 */
	public int getSWK_AUTO_KBN() {
		return sWK_AUTO_KBN;
	}

	/**
	 * 自動仕訳かどうか
	 * 
	 * @return sWK_AUTO_KBN true:自動仕訳
	 */
	public boolean isAutoDetail() {
		return BooleanUtil.toBoolean(sWK_AUTO_KBN);
	}

	/**
	 * 自動仕訳区分
	 * 
	 * @param swk_auto_kbn 自動仕訳区分
	 */
	public void setSWK_AUTO_KBN(int swk_auto_kbn) {
		sWK_AUTO_KBN = swk_auto_kbn;
	}

	/**
	 * BOOK No.
	 * 
	 * @return BOOK No.
	 */
	public int getSWK_BOOK_NO() {
		return sWK_BOOK_NO;
	}

	/**
	 * BOOK No.
	 * 
	 * @param swk_book_no BOOK No.
	 */
	public void setSWK_BOOK_NO(int swk_book_no) {
		sWK_BOOK_NO = swk_book_no;
	}

	/**
	 * Bookタイプ
	 * 
	 * @param type Bookタイプ
	 */
	public void setCurrencyType(CurrencyType type) {
		sWK_BOOK_NO = type.value;
	}

	/**
	 * Bookタイプ
	 * 
	 * @return Bookタイプ
	 */
	public CurrencyType getCurrencyType() {
		return CurrencyType.get(sWK_BOOK_NO);
	}

	/**
	 * 通貨ｺｰﾄﾞ
	 * 
	 * @return 通貨ｺｰﾄﾞ
	 */
	public String getSWK_CUR_CODE() {
		return sWK_CUR_CODE;
	}

	/**
	 * 通貨ｺｰﾄﾞ
	 * 
	 * @param swk_cur_code 通貨ｺｰﾄﾞ
	 */
	public void setSWK_CUR_CODE(String swk_cur_code) {
		sWK_CUR_CODE = swk_cur_code;
	}

	/**
	 * ﾚｰﾄ
	 * 
	 * @return ﾚｰﾄ
	 */
	public BigDecimal getSWK_CUR_RATE() {
		return sWK_CUR_RATE;
	}

	/**
	 * ﾚｰﾄ
	 * 
	 * @param swk_cur_rate ﾚｰﾄ
	 */
	public void setSWK_CUR_RATE(BigDecimal swk_cur_rate) {
		sWK_CUR_RATE = swk_cur_rate;
	}

	/**
	 * データ区分
	 * 
	 * @return データ区分
	 */
	public String getSWK_DATA_KBN() {
		return sWK_DATA_KBN;
	}

	/**
	 * データ区分
	 * 
	 * @param swk_data_kbn データ区分
	 */
	public void setSWK_DATA_KBN(String swk_data_kbn) {
		sWK_DATA_KBN = swk_data_kbn;
	}

	/**
	 * 貸借区分
	 * 
	 * @return 貸借区分
	 */
	public int getSWK_DC_KBN() {
		return sWK_DC_KBN;
	}

	/**
	 * 貸借区分
	 * 
	 * @param swk_dc_kbn 貸借区分
	 */
	public void setSWK_DC_KBN(int swk_dc_kbn) {
		sWK_DC_KBN = swk_dc_kbn;
	}

	/**
	 * 貸借
	 * 
	 * @param dc 貸借
	 */
	public void setDC(Dc dc) {
		sWK_DC_KBN = dc.value;
	}

	/**
	 * 貸借
	 * 
	 * @return 貸借
	 */
	public Dc getDC() {
		return Dc.getDc(sWK_DC_KBN);
	}

	/**
	 * 伝票日付
	 * 
	 * @return 伝票日付
	 */
	public Date getSWK_DEN_DATE() {
		return sWK_DEN_DATE;
	}

	/**
	 * 伝票日付
	 * 
	 * @param swk_den_date 伝票日付
	 */
	public void setSWK_DEN_DATE(Date swk_den_date) {
		sWK_DEN_DATE = swk_den_date;
	}

	/**
	 * 伝票番号
	 * 
	 * @return 伝票番号
	 */
	public String getSWK_DEN_NO() {
		return sWK_DEN_NO;
	}

	/**
	 * 伝票番号
	 * 
	 * @param swk_den_no 伝票番号
	 */
	public void setSWK_DEN_NO(String swk_den_no) {
		sWK_DEN_NO = swk_den_no;
	}

	/**
	 * 計上部門コード
	 * 
	 * @return 計上部門コード
	 */
	public String getSWK_DEP_CODE() {
		return sWK_DEP_CODE;
	}

	/**
	 * 計上部門コード
	 * 
	 * @param swk_dep_code 計上部門コード
	 */
	public void setSWK_DEP_CODE(String swk_dep_code) {
		sWK_DEP_CODE = swk_dep_code;
	}

	/**
	 * 社員コード
	 * 
	 * @return 社員コード
	 */
	public String getSWK_EMP_CODE() {
		return sWK_EMP_CODE;
	}

	/**
	 * 社員コード
	 * 
	 * @param swk_emp_code 社員コード
	 */
	public void setSWK_EMP_CODE(String swk_emp_code) {
		sWK_EMP_CODE = swk_emp_code;
	}

	/**
	 * 行番号
	 * 
	 * @return 行番号
	 */
	public int getSWK_GYO_NO() {
		return sWK_GYO_NO;
	}

	/**
	 * 行番号
	 * 
	 * @param swk_gyo_no 行番号
	 */
	public void setSWK_GYO_NO(int swk_gyo_no) {
		sWK_GYO_NO = swk_gyo_no;
	}

	/**
	 * 行摘要
	 * 
	 * @return 行摘要
	 */
	public String getSWK_GYO_TEK() {
		return sWK_GYO_TEK;
	}

	/**
	 * 行摘要
	 * 
	 * @param swk_gyo_tek 行摘要
	 */
	public void setSWK_GYO_TEK(String swk_gyo_tek) {
		sWK_GYO_TEK = swk_gyo_tek;
	}

	/**
	 * 行摘要コード
	 * 
	 * @return 行摘要コード
	 */
	public String getSWK_GYO_TEK_CODE() {
		return sWK_GYO_TEK_CODE;
	}

	/**
	 * 行摘要コード
	 * 
	 * @param swk_gyo_tek_code 行摘要コード
	 */
	public void setSWK_GYO_TEK_CODE(String swk_gyo_tek_code) {
		sWK_GYO_TEK_CODE = swk_gyo_tek_code;
	}

	/**
	 * 補助科目コード
	 * 
	 * @return 補助科目コード
	 */
	public String getSWK_HKM_CODE() {
		return sWK_HKM_CODE;
	}

	/**
	 * 補助科目コード
	 * 
	 * @param swk_hkm_code 補助科目コード
	 */
	public void setSWK_HKM_CODE(String swk_hkm_code) {
		sWK_HKM_CODE = swk_hkm_code;
	}

	/**
	 * 非会計明細１
	 * 
	 * @return 非会計明細１
	 */
	public String getSWK_HM_1() {
		return sWK_HM_1;
	}

	/**
	 * 非会計明細１
	 * 
	 * @param swk_hm_1 非会計明細１
	 */
	public void setSWK_HM_1(String swk_hm_1) {
		sWK_HM_1 = swk_hm_1;
	}

	/**
	 * 非会計明細２
	 * 
	 * @return 非会計明細２
	 */
	public String getSWK_HM_2() {
		return sWK_HM_2;
	}

	/**
	 * 非会計明細２
	 * 
	 * @param swk_hm_2 非会計明細２
	 */
	public void setSWK_HM_2(String swk_hm_2) {
		sWK_HM_2 = swk_hm_2;
	}

	/**
	 * 非会計明細３
	 * 
	 * @return 非会計明細３
	 */
	public String getSWK_HM_3() {
		return sWK_HM_3;
	}

	/**
	 * 非会計明細３
	 * 
	 * @param swk_hm_3 非会計明細３
	 */
	public void setSWK_HM_3(String swk_hm_3) {
		sWK_HM_3 = swk_hm_3;
	}

	/**
	 * IFRS調整区分
	 * 
	 * @return IFRS調整区分
	 */
	public int getSWK_ADJ_KBN() {
		return sWK_ADJ_KBN;
	}

	/**
	 * IFRS調整区分
	 * 
	 * @param swk_ifrs_flg IFRS調整区分
	 */
	public void setSWK_ADJ_KBN(int swk_ifrs_flg) {
		sWK_ADJ_KBN = swk_ifrs_flg;
	}

	/**
	 * 会計帳簿
	 * 
	 * @param type タイプ
	 */
	public void setAccountBook(AccountBook type) {
		sWK_ADJ_KBN = type.value;
	}

	/**
	 * 会計帳簿
	 * 
	 * @return 会計帳簿
	 */
	public AccountBook getAccountBook() {
		return AccountBook.get(sWK_ADJ_KBN);
	}

	/**
	 * 入力金額
	 * 
	 * @return sWK_IN_KIN 入力金額
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * 入力金額
	 * 
	 * @param swk_in_kin 入力金額
	 */
	public void setSWK_IN_KIN(BigDecimal swk_in_kin) {
		sWK_IN_KIN = swk_in_kin;
	}

	/**
	 * 入力消費税額
	 * 
	 * @return 入力消費税額
	 */
	public BigDecimal getSWK_IN_ZEI_KIN() {
		return sWK_IN_ZEI_KIN;
	}

	/**
	 * 入力消費税額
	 * 
	 * @param swk_in_zei_kin 入力消費税額
	 */
	public void setSWK_IN_ZEI_KIN(BigDecimal swk_in_zei_kin) {
		sWK_IN_ZEI_KIN = swk_in_zei_kin;
	}

	/**
	 * 計上会社ｺｰﾄﾞ
	 * 
	 * @return 計上会社ｺｰﾄﾞ
	 */
	public String getSWK_K_KAI_CODE() {
		return sWK_K_KAI_CODE;
	}

	/**
	 * 計上会社ｺｰﾄﾞ
	 * 
	 * @param swk_k_kai_code 計上会社ｺｰﾄﾞ
	 */
	public void setSWK_K_KAI_CODE(String swk_k_kai_code) {
		sWK_K_KAI_CODE = swk_k_kai_code;
	}

	/**
	 * 消込伝票日付
	 * 
	 * @return 消込伝票日付
	 */
	public Date getSWK_KESI_DATE() {
		return sWK_KESI_DATE;
	}

	/**
	 * 消込伝票日付
	 * 
	 * @param swk_kesi_date 消込伝票日付
	 */
	public void setSWK_KESI_DATE(Date swk_kesi_date) {
		sWK_KESI_DATE = swk_kesi_date;
	}

	/**
	 * 消込伝票番号
	 * 
	 * @return 消込伝票番号
	 */
	public String getSWK_KESI_DEN_NO() {
		return sWK_KESI_DEN_NO;
	}

	/**
	 * 消込伝票番号
	 * 
	 * @param swk_kesi_den_no 消込伝票番号
	 */
	public void setSWK_KESI_DEN_NO(String swk_kesi_den_no) {
		sWK_KESI_DEN_NO = swk_kesi_den_no;
	}

	/**
	 * 消込区分
	 * 
	 * @return 消込区分
	 */
	public int getSWK_KESI_KBN() {
		return sWK_KESI_KBN;
	}

	/**
	 * 消込区分
	 * 
	 * @param swk_kesi_kbn 消込区分
	 */
	public void setSWK_KESI_KBN(int swk_kesi_kbn) {
		sWK_KESI_KBN = swk_kesi_kbn;
	}

	/**
	 * 邦貨金額
	 * 
	 * @return 邦貨金額
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * 邦貨金額
	 * 
	 * @param swk_kin 邦貨金額
	 */
	public void setSWK_KIN(BigDecimal swk_kin) {
		sWK_KIN = swk_kin;
	}

	/**
	 * 科目コード
	 * 
	 * @return 科目コード
	 */
	public String getSWK_KMK_CODE() {
		return sWK_KMK_CODE;
	}

	/**
	 * 科目コード
	 * 
	 * @param swk_kmk_code 科目コード
	 */
	public void setSWK_KMK_CODE(String swk_kmk_code) {
		sWK_KMK_CODE = swk_kmk_code;
	}

	/**
	 * 管理１コード
	 * 
	 * @return 管理１コード
	 */
	public String getSWK_KNR_CODE_1() {
		return sWK_KNR_CODE_1;
	}

	/**
	 * 管理１コード
	 * 
	 * @param swk_knr_code_1 管理１コード
	 */
	public void setSWK_KNR_CODE_1(String swk_knr_code_1) {
		sWK_KNR_CODE_1 = swk_knr_code_1;
	}

	/**
	 * 管理２コード
	 * 
	 * @return 管理２コード
	 */
	public String getSWK_KNR_CODE_2() {
		return sWK_KNR_CODE_2;
	}

	/**
	 * 管理２コード
	 * 
	 * @param swk_knr_code_2 管理２コード
	 */
	public void setSWK_KNR_CODE_2(String swk_knr_code_2) {
		sWK_KNR_CODE_2 = swk_knr_code_2;
	}

	/**
	 * 管理３コード
	 * 
	 * @return 管理３コード
	 */
	public String getSWK_KNR_CODE_3() {
		return sWK_KNR_CODE_3;
	}

	/**
	 * 管理３コード
	 * 
	 * @param swk_knr_code_3 管理３コード
	 */
	public void setSWK_KNR_CODE_3(String swk_knr_code_3) {
		sWK_KNR_CODE_3 = swk_knr_code_3;
	}

	/**
	 * 管理４コード
	 * 
	 * @return 管理４コード
	 */
	public String getSWK_KNR_CODE_4() {
		return sWK_KNR_CODE_4;
	}

	/**
	 * 管理４コード
	 * 
	 * @param swk_knr_code_4 管理４コード
	 */
	public void setSWK_KNR_CODE_4(String swk_knr_code_4) {
		sWK_KNR_CODE_4 = swk_knr_code_4;
	}

	/**
	 * 管理５コード
	 * 
	 * @return 管理５コード
	 */
	public String getSWK_KNR_CODE_5() {
		return sWK_KNR_CODE_5;
	}

	/**
	 * 管理５コード
	 * 
	 * @param swk_knr_code_5 管理５コード
	 */
	public void setSWK_KNR_CODE_5(String swk_knr_code_5) {
		sWK_KNR_CODE_5 = swk_knr_code_5;
	}

	/**
	 * 管理６コード
	 * 
	 * @return 管理６コード
	 */
	public String getSWK_KNR_CODE_6() {
		return sWK_KNR_CODE_6;
	}

	/**
	 * 管理６コード
	 * 
	 * @param swk_knr_code_6 管理６コード
	 */
	public void setSWK_KNR_CODE_6(String swk_knr_code_6) {
		sWK_KNR_CODE_6 = swk_knr_code_6;
	}

	/**
	 * 決算区分
	 * 
	 * @return 決算区分
	 */
	public int getSWK_KSN_KBN() {
		return sWK_KSN_KBN;
	}

	/**
	 * 決算区分
	 * 
	 * @param swk_ksn_kbn 決算区分
	 */
	public void setSWK_KSN_KBN(int swk_ksn_kbn) {
		sWK_KSN_KBN = swk_ksn_kbn;
	}

	/**
	 * 年度
	 * 
	 * @return 年度
	 */
	public int getSWK_NENDO() {
		return sWK_NENDO;
	}

	/**
	 * 年度
	 * 
	 * @param swk_nendo 年度
	 */
	public void setSWK_NENDO(int swk_nendo) {
		sWK_NENDO = swk_nendo;
	}

	/**
	 * 証憑番号
	 * 
	 * @return 証憑番号
	 */
	public String getSWK_SEI_NO() {
		return sWK_SEI_NO;
	}

	/**
	 * 証憑番号
	 * 
	 * @param swk_sei_no 証憑番号
	 */
	public void setSWK_SEI_NO(String swk_sei_no) {
		sWK_SEI_NO = swk_sei_no;
	}

	/**
	 * 相殺伝票日付
	 * 
	 * @return 相殺伝票日付
	 */
	public Date getSWK_SOUSAI_DATE() {
		return sWK_SOUSAI_DATE;
	}

	/**
	 * 相殺伝票日付
	 * 
	 * @param swk_sousai_date 相殺伝票日付
	 */
	public void setSWK_SOUSAI_DATE(Date swk_sousai_date) {
		sWK_SOUSAI_DATE = swk_sousai_date;
	}

	/**
	 * 相殺伝票番号
	 * 
	 * @return 相殺伝票番号
	 */
	public String getSWK_SOUSAI_DEN_NO() {
		return sWK_SOUSAI_DEN_NO;
	}

	/**
	 * 相殺伝票番号
	 * 
	 * @param swk_sousai_den_no 相殺伝票番号
	 */
	public void setSWK_SOUSAI_DEN_NO(String swk_sousai_den_no) {
		sWK_SOUSAI_DEN_NO = swk_sousai_den_no;
	}

	/**
	 * 相殺行番号の取得
	 * 
	 * @return sWK_SOUSAI_GYO_NO 相殺行番号
	 */
	public Integer getSWK_SOUSAI_GYO_NO() {
		return sWK_SOUSAI_GYO_NO;
	}

	/**
	 * 相殺行番号の設定
	 * 
	 * @param sWK_SOUSAI_GYO_NO 相殺行番号
	 */
	public void setSWK_SOUSAI_GYO_NO(Integer sWK_SOUSAI_GYO_NO) {
		this.sWK_SOUSAI_GYO_NO = sWK_SOUSAI_GYO_NO;
	}

	/**
	 * 取引先コード
	 * 
	 * @return 取引先コード
	 */
	public String getSWK_TRI_CODE() {
		return sWK_TRI_CODE;
	}

	/**
	 * 取引先コード
	 * 
	 * @param swk_tri_code 取引先コード
	 */
	public void setSWK_TRI_CODE(String swk_tri_code) {
		sWK_TRI_CODE = swk_tri_code;
	}

	/**
	 * 会社間付替伝票区分
	 * 
	 * @return 会社間付替伝票区分
	 */
	public int getSWK_TUKE_KBN() {
		return sWK_TUKE_KBN;
	}

	/**
	 * 会社間付替伝票区分
	 * 
	 * @param swk_tuke_kbn 会社間付替伝票区分
	 */
	public void setSWK_TUKE_KBN(int swk_tuke_kbn) {
		sWK_TUKE_KBN = swk_tuke_kbn;
	}

	/**
	 * 月度
	 * 
	 * @return 月度
	 */
	public int getSWK_TUKIDO() {
		return sWK_TUKIDO;
	}

	/**
	 * 月度
	 * 
	 * @param swk_tukido 月度
	 */
	public void setSWK_TUKIDO(int swk_tukido) {
		sWK_TUKIDO = swk_tukido;
	}

	/**
	 * 内訳科目コード
	 * 
	 * @return 内訳科目コード
	 */
	public String getSWK_UKM_CODE() {
		return sWK_UKM_CODE;
	}

	/**
	 * 内訳科目コード
	 * 
	 * @param swk_ukm_code 内訳科目コード
	 */
	public void setSWK_UKM_CODE(String swk_ukm_code) {
		sWK_UKM_CODE = swk_ukm_code;
	}

	/**
	 * 更新区分
	 * 
	 * @return 更新区分
	 */
	public int getSWK_UPD_KBN() {
		return sWK_UPD_KBN;
	}

	/**
	 * 更新区分
	 * 
	 * @param swk_upd_kbn 更新区分
	 */
	public void setSWK_UPD_KBN(int swk_upd_kbn) {
		sWK_UPD_KBN = swk_upd_kbn;
	}

	/**
	 * 消費税コード
	 * 
	 * @return 消費税コード
	 */
	public String getSWK_ZEI_CODE() {
		return sWK_ZEI_CODE;
	}

	/**
	 * 消費税コード
	 * 
	 * @param swk_zei_code 消費税コード
	 */
	public void setSWK_ZEI_CODE(String swk_zei_code) {
		sWK_ZEI_CODE = swk_zei_code;
	}

	/**
	 * 税区分
	 * 
	 * @return 税区分
	 */
	public int getSWK_ZEI_KBN() {
		return sWK_ZEI_KBN;
	}

	/**
	 * 税区分
	 * 
	 * @param swk_zei_kbn 税区分
	 */
	public void setSWK_ZEI_KBN(int swk_zei_kbn) {
		sWK_ZEI_KBN = swk_zei_kbn;
	}

	/**
	 * 税区分
	 * 
	 * @param type 税区分
	 */
	public void setTaxCalcType(TaxCalcType type) {
		this.sWK_ZEI_KBN = type.value;
	}

	/**
	 * 消費税額
	 * 
	 * @return 消費税額
	 */
	public BigDecimal getSWK_ZEI_KIN() {
		return sWK_ZEI_KIN;
	}

	/**
	 * 消費税額
	 * 
	 * @param swk_zei_kin 消費税額
	 */
	public void setSWK_ZEI_KIN(BigDecimal swk_zei_kin) {
		sWK_ZEI_KIN = swk_zei_kin;
	}

	/**
	 * 税率
	 * 
	 * @return 税率
	 */
	public BigDecimal getSWK_ZEI_RATE() {
		return sWK_ZEI_RATE;
	}

	/**
	 * 税率
	 * 
	 * @param swk_zei_rate 税率
	 */
	public void setSWK_ZEI_RATE(BigDecimal swk_zei_rate) {
		sWK_ZEI_RATE = swk_zei_rate;
	}

	/**
	 * 更新日付
	 * 
	 * @return 更新日付
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * 更新日付
	 * 
	 * @param upd_date 更新日付
	 */
	public void setUPD_DATE(Date upd_date) {
		uPD_DATE = upd_date;
	}

	/**
	 * ユーザーＩＤ
	 * 
	 * @return ユーザーＩＤ
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ユーザーＩＤ
	 * 
	 * @param usr_id ユーザーＩＤ
	 */
	public void setUSR_ID(String usr_id) {
		uSR_ID = usr_id;
	}

	/**
	 * 計上部門名称
	 * 
	 * @return 計上部門名称
	 */
	public String getSWK_DEP_NAME() {
		return sWK_DEP_NAME;
	}

	/**
	 * 計上部門名称
	 * 
	 * @param swk_dep_name 計上部門名称
	 */
	public void setSWK_DEP_NAME(String swk_dep_name) {
		sWK_DEP_NAME = swk_dep_name;
	}

	/**
	 * 計上部門略称
	 * 
	 * @return 計上部門略称
	 */
	public String getSWK_DEP_NAME_S() {
		return sWK_DEP_NAME_S;
	}

	/**
	 * 計上部門略称
	 * 
	 * @param swk_dep_name_s 計上部門略称
	 */
	public void setSWK_DEP_NAME_S(String swk_dep_name_s) {
		sWK_DEP_NAME_S = swk_dep_name_s;
	}

	/**
	 * 社員名称
	 * 
	 * @return 社員名称
	 */
	public String getSWK_EMP_NAME() {
		return sWK_EMP_NAME;
	}

	/**
	 * 社員名称
	 * 
	 * @param swk_emp_name 社員名称
	 */
	public void setSWK_EMP_NAME(String swk_emp_name) {
		sWK_EMP_NAME = swk_emp_name;
	}

	/**
	 * 社員略称
	 * 
	 * @return 社員略称
	 */
	public String getSWK_EMP_NAME_S() {
		return sWK_EMP_NAME_S;
	}

	/**
	 * 社員略称
	 * 
	 * @param swk_emp_name_s 社員略称
	 */
	public void setSWK_EMP_NAME_S(String swk_emp_name_s) {
		sWK_EMP_NAME_S = swk_emp_name_s;
	}

	/**
	 * 補助科目名称
	 * 
	 * @return 補助科目名称
	 */
	public String getSWK_HKM_NAME() {
		return sWK_HKM_NAME;
	}

	/**
	 * 補助科目名称
	 * 
	 * @param swk_hkm_name 補助科目名称
	 */
	public void setSWK_HKM_NAME(String swk_hkm_name) {
		sWK_HKM_NAME = swk_hkm_name;
	}

	/**
	 * 補助科目略称
	 * 
	 * @return 補助科目略称
	 */
	public String getSWK_HKM_NAME_S() {
		return sWK_HKM_NAME_S;
	}

	/**
	 * 補助科目略称
	 * 
	 * @param swk_hkm_name_s 補助科目略称
	 */
	public void setSWK_HKM_NAME_S(String swk_hkm_name_s) {
		sWK_HKM_NAME_S = swk_hkm_name_s;
	}

	/**
	 * 科目名称
	 * 
	 * @return 科目名称
	 */
	public String getSWK_KMK_NAME() {
		return sWK_KMK_NAME;
	}

	/**
	 * 科目名称
	 * 
	 * @param swk_kmk_name 科目名称
	 */
	public void setSWK_KMK_NAME(String swk_kmk_name) {
		sWK_KMK_NAME = swk_kmk_name;
	}

	/**
	 * 科目略称
	 * 
	 * @return 科目略称
	 */
	public String getSWK_KMK_NAME_S() {
		return sWK_KMK_NAME_S;
	}

	/**
	 * 科目略称
	 * 
	 * @param swk_kmk_name_s 科目略称
	 */
	public void setSWK_KMK_NAME_S(String swk_kmk_name_s) {
		sWK_KMK_NAME_S = swk_kmk_name_s;
	}

	/**
	 * 管理１名称
	 * 
	 * @return 管理１名称
	 */
	public String getSWK_KNR_NAME_1() {
		return sWK_KNR_NAME_1;
	}

	/**
	 * 管理１名称
	 * 
	 * @param swk_knr_name_1 管理１名称
	 */
	public void setSWK_KNR_NAME_1(String swk_knr_name_1) {
		sWK_KNR_NAME_1 = swk_knr_name_1;
	}

	/**
	 * 管理２名称
	 * 
	 * @return 管理２名称
	 */
	public String getSWK_KNR_NAME_2() {
		return sWK_KNR_NAME_2;
	}

	/**
	 * 管理２名称
	 * 
	 * @param swk_knr_name_2 管理２名称
	 */
	public void setSWK_KNR_NAME_2(String swk_knr_name_2) {
		sWK_KNR_NAME_2 = swk_knr_name_2;
	}

	/**
	 * 管理３名称
	 * 
	 * @return 管理３名称
	 */
	public String getSWK_KNR_NAME_3() {
		return sWK_KNR_NAME_3;
	}

	/**
	 * 管理３名称
	 * 
	 * @param swk_knr_name_3 管理３名称
	 */
	public void setSWK_KNR_NAME_3(String swk_knr_name_3) {
		sWK_KNR_NAME_3 = swk_knr_name_3;
	}

	/**
	 * 管理４名称
	 * 
	 * @return 管理４名称
	 */
	public String getSWK_KNR_NAME_4() {
		return sWK_KNR_NAME_4;
	}

	/**
	 * 管理４名称
	 * 
	 * @param swk_knr_name_4 管理４名称
	 */
	public void setSWK_KNR_NAME_4(String swk_knr_name_4) {
		sWK_KNR_NAME_4 = swk_knr_name_4;
	}

	/**
	 * 管理５名称
	 * 
	 * @return 管理５名称
	 */
	public String getSWK_KNR_NAME_5() {
		return sWK_KNR_NAME_5;
	}

	/**
	 * 管理５名称
	 * 
	 * @param swk_knr_name_5 管理５名称
	 */
	public void setSWK_KNR_NAME_5(String swk_knr_name_5) {
		sWK_KNR_NAME_5 = swk_knr_name_5;
	}

	/**
	 * 管理６名称
	 * 
	 * @return 管理６名称
	 */
	public String getSWK_KNR_NAME_6() {
		return sWK_KNR_NAME_6;
	}

	/**
	 * 管理６名称
	 * 
	 * @param swk_knr_name_6 管理６名称
	 */
	public void setSWK_KNR_NAME_6(String swk_knr_name_6) {
		sWK_KNR_NAME_6 = swk_knr_name_6;
	}

	/**
	 * 管理１略称
	 * 
	 * @return 管理１略称
	 */
	public String getSWK_KNR_NAME_S_1() {
		return sWK_KNR_NAME_S_1;
	}

	/**
	 * 管理１略称
	 * 
	 * @param swk_knr_name_s_1 管理１略称
	 */
	public void setSWK_KNR_NAME_S_1(String swk_knr_name_s_1) {
		sWK_KNR_NAME_S_1 = swk_knr_name_s_1;
	}

	/**
	 * 管理２略称
	 * 
	 * @return 管理２略称
	 */
	public String getSWK_KNR_NAME_S_2() {
		return sWK_KNR_NAME_S_2;
	}

	/**
	 * 管理２略称
	 * 
	 * @param swk_knr_name_s_2 管理２略称
	 */
	public void setSWK_KNR_NAME_S_2(String swk_knr_name_s_2) {
		sWK_KNR_NAME_S_2 = swk_knr_name_s_2;
	}

	/**
	 * 管理３略称
	 * 
	 * @return 管理３略称
	 */
	public String getSWK_KNR_NAME_S_3() {
		return sWK_KNR_NAME_S_3;
	}

	/**
	 * 管理３略称
	 * 
	 * @param swk_knr_name_s_3 管理３略称
	 */
	public void setSWK_KNR_NAME_S_3(String swk_knr_name_s_3) {
		sWK_KNR_NAME_S_3 = swk_knr_name_s_3;
	}

	/**
	 * 管理４略称
	 * 
	 * @return 管理４略称
	 */
	public String getSWK_KNR_NAME_S_4() {
		return sWK_KNR_NAME_S_4;
	}

	/**
	 * 管理４略称
	 * 
	 * @param swk_knr_name_s_4 管理４略称
	 */
	public void setSWK_KNR_NAME_S_4(String swk_knr_name_s_4) {
		sWK_KNR_NAME_S_4 = swk_knr_name_s_4;
	}

	/**
	 * 管理５略称
	 * 
	 * @return 管理５略称
	 */
	public String getSWK_KNR_NAME_S_5() {
		return sWK_KNR_NAME_S_5;
	}

	/**
	 * 管理５略称
	 * 
	 * @param swk_knr_name_s_5 管理５略称
	 */
	public void setSWK_KNR_NAME_S_5(String swk_knr_name_s_5) {
		sWK_KNR_NAME_S_5 = swk_knr_name_s_5;
	}

	/**
	 * 管理６略称
	 * 
	 * @return 管理６略称
	 */
	public String getSWK_KNR_NAME_S_6() {
		return sWK_KNR_NAME_S_6;
	}

	/**
	 * 管理６略称
	 * 
	 * @param swk_knr_name_s_6 管理６略称
	 */
	public void setSWK_KNR_NAME_S_6(String swk_knr_name_s_6) {
		sWK_KNR_NAME_S_6 = swk_knr_name_s_6;
	}

	/**
	 * 取引先名称
	 * 
	 * @return 取引先名称
	 */
	public String getSWK_TRI_NAME() {
		return sWK_TRI_NAME;
	}

	/**
	 * 取引先名称
	 * 
	 * @param swk_tri_name 取引先名称
	 */
	public void setSWK_TRI_NAME(String swk_tri_name) {
		sWK_TRI_NAME = swk_tri_name;
	}

	/**
	 * 取引先略称
	 * 
	 * @return 取引先略称
	 */
	public String getSWK_TRI_NAME_S() {
		return sWK_TRI_NAME_S;
	}

	/**
	 * 取引先略称
	 * 
	 * @param swk_tri_name_s 取引先略称
	 */
	public void setSWK_TRI_NAME_S(String swk_tri_name_s) {
		sWK_TRI_NAME_S = swk_tri_name_s;
	}

	/**
	 * 内訳科目名称
	 * 
	 * @return 内訳科目名称
	 */
	public String getSWK_UKM_NAME() {
		return sWK_UKM_NAME;
	}

	/**
	 * 内訳科目名称
	 * 
	 * @param swk_ukm_name 内訳科目名称
	 */
	public void setSWK_UKM_NAME(String swk_ukm_name) {
		sWK_UKM_NAME = swk_ukm_name;
	}

	/**
	 * 内訳科目略称
	 * 
	 * @return 内訳科目略称
	 */
	public String getSWK_UKM_NAME_S() {
		return sWK_UKM_NAME_S;
	}

	/**
	 * 内訳科目略称
	 * 
	 * @param swk_ukm_name_s 内訳科目略称
	 */
	public void setSWK_UKM_NAME_S(String swk_ukm_name_s) {
		sWK_UKM_NAME_S = swk_ukm_name_s;
	}

	/**
	 * 伝票修正回数
	 * 
	 * @return 伝票修正回数
	 */
	public int getSWK_UPD_CNT() {
		return sWK_UPD_CNT;
	}

	/**
	 * 伝票修正回数
	 * 
	 * @param swk_upd_cnt 伝票修正回数
	 */
	public void setSWK_UPD_CNT(int swk_upd_cnt) {
		sWK_UPD_CNT = swk_upd_cnt;
	}

	/**
	 * 消費税名称
	 * 
	 * @return 消費税名称
	 */
	public String getSWK_ZEI_NAME() {
		return sWK_ZEI_NAME;
	}

	/**
	 * 消費税名称
	 * 
	 * @param swk_zei_name 消費税名称
	 */
	public void setSWK_ZEI_NAME(String swk_zei_name) {
		sWK_ZEI_NAME = swk_zei_name;
	}

	/**
	 * 消費税略称
	 * 
	 * @return 消費税略称
	 */
	public String getSWK_ZEI_NAME_S() {
		return sWK_ZEI_NAME_S;
	}

	/**
	 * 消費税略称
	 * 
	 * @param swk_zei_name_s 消費税略称
	 */
	public void setSWK_ZEI_NAME_S(String swk_zei_name_s) {
		sWK_ZEI_NAME_S = swk_zei_name_s;
	}

	/**
	 * 必須項目にNULLが含まれていないかどうかをチェック
	 * 
	 * @return true:null、またはブランクが含まれる
	 */
	public String isRequiredItemNULL() {

		// 会社コード KAI_CODE
		// 伝票日付 SWK_DEN_DATE
		// 伝票番号 SWK_DEN_NO
		// BOOK No. SWK_BOOK_NO
		// 行番号 SWK_GYO_NO
		// IFRS調整区分 SWKADJ_KBN
		// 年度 SWK_NENDO
		// 月度 SWK_TUKIDO
		// 貸借区分 SWK_DC_KBN
		// 科目コード SWK_KMK_CODE
		// 計上部門コード SWK_DEP_CODE
		// 自動仕訳区分 SWK_AUTO_KBN
		// データ区分 SWK_DATA_KBN
		// 更新区分 SWK_UPD_KBN
		// 決算区分 SWK_KSN_KBN
		// 伝票種別ｺｰﾄﾞ DEN_SYU_CODE
		Map<String, Object> notNullList = new TreeMap<String, Object>();
		// notNullList.put("KAI_CODE", kAI_CODE);
		// notNullList.put("SWK_DEN_DATE", sWK_DEN_DATE);
		// notNullList.put("SWK_DEN_NO", sWK_DEN_NO);
		notNullList.put("SWK_KMK_CODE", sWK_KMK_CODE);
		notNullList.put("SWK_DEP_CODE", sWK_DEP_CODE);
		notNullList.put("DEN_SYU_CODE", dEN_SYU_CODE);

		for (Map.Entry<String, Object> entry : notNullList.entrySet()) {
			if (Util.isNullOrEmpty(entry.getValue())) {
				return entry.getKey();
			}
		}

		return "";
	}

	/**
	 * 非課税かどうか
	 * 
	 * @return true:非課税
	 */
	public boolean isNonTax() {
		return sWK_ZEI_KBN == ZEI_KBN.TAX_NONE;
	}

	/**
	 * 内税かどうか
	 * 
	 * @return true:内税
	 */
	public boolean isTaxInclusive() {
		return sWK_ZEI_KBN == ZEI_KBN.TAX_IN;
	}

	/**
	 * 外税かどうか
	 * 
	 * @return true:外税
	 */
	public boolean isTaxExcluded() {
		return sWK_ZEI_KBN == ZEI_KBN.TAX_OUT;
	}

	/**
	 * 借方の基軸通貨金額
	 * 
	 * @return 合計金額
	 */
	public BigDecimal getDebitKeyAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT ? DecimalUtil.avoidNull(sWK_KIN) : BigDecimal.ZERO;
	}

	/**
	 * 貸方の基軸通貨金額
	 * 
	 * @return 合計金額
	 */
	public BigDecimal getCreditKeyAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.CREDIT ? DecimalUtil.avoidNull(sWK_KIN) : BigDecimal.ZERO;
	}

	/**
	 * 借方の入力金額
	 * 
	 * @return 合計金額
	 */
	public BigDecimal getDebitInputAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT ? DecimalUtil.avoidNull(sWK_IN_KIN) : BigDecimal.ZERO;
	}

	/**
	 * 貸方の入力金額
	 * 
	 * @return 合計金額
	 */
	public BigDecimal getCreditInputAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.CREDIT ? DecimalUtil.avoidNull(sWK_IN_KIN) : BigDecimal.ZERO;
	}

	/**
	 * 借方の消費税金額
	 * 
	 * @return 合計金額
	 */
	public BigDecimal getDebitTaxAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT ? DecimalUtil.avoidNull(sWK_ZEI_KIN) : BigDecimal.ZERO;
	}

	/**
	 * 貸方の消費税金額
	 * 
	 * @return 合計金額
	 */
	public BigDecimal getCreditTaxAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.CREDIT ? DecimalUtil.avoidNull(sWK_ZEI_KIN) : BigDecimal.ZERO;
	}

	/**
	 * 借方の消費税金額
	 * 
	 * @return 合計金額
	 */
	public BigDecimal getDebitTaxInputAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT ? DecimalUtil.avoidNull(sWK_IN_ZEI_KIN) : BigDecimal.ZERO;
	}

	/**
	 * 貸方の消費税金額
	 * 
	 * @return 合計金額
	 */
	public BigDecimal getCreditTaxInputAmount() {
		return Dc.getDc(sWK_DC_KBN) == Dc.CREDIT ? DecimalUtil.avoidNull(sWK_IN_ZEI_KIN) : BigDecimal.ZERO;
	}

	/**
	 * 機能通貨仕訳かどうか
	 * 
	 * @return true:機能通貨仕訳
	 */
	public boolean isFunctionalCurrency() {
		return CurrencyType.get(sWK_BOOK_NO) == CurrencyType.FUNCTIONAL;
	}

	/**
	 * 借方かどうか
	 * 
	 * @return true:借方
	 */
	public boolean isDR() {
		return Dc.getDc(sWK_DC_KBN) == Dc.DEBIT;
	}

	/**
	 * 消費税仕訳かどうか
	 * 
	 * @param taxJornal true:消費税仕訳
	 */
	public void setTaxJornal(boolean taxJornal) {
		this.taxJornal = taxJornal;
	}

	/**
	 * 消費税仕訳かどうか
	 * 
	 * @return true:消費税仕訳
	 */
	public boolean isTaxJornal() {
		return this.taxJornal;
	}

	/**
	 * 通貨 少数点桁数
	 * 
	 * @return 通貨 少数点桁数
	 */
	public int getCUR_DEC_KETA() {
		return cUR_DEC_KETA;
	}

	/**
	 * 通貨 少数点桁数
	 * 
	 * @param cur_dec_keta 通貨 少数点桁数
	 */
	public void setCUR_DEC_KETA(int cur_dec_keta) {
		cUR_DEC_KETA = cur_dec_keta;
	}

	/**
	 * 通貨 レート係数
	 * 
	 * @return cUR_RATE_POW 通貨 レート係数
	 */
	public int getCUR_RATE_POW() {
		return cUR_RATE_POW;
	}

	/**
	 * 通貨 レート係数
	 * 
	 * @param cur_rate_pow 通貨 レート係数
	 */
	public void setCUR_RATE_POW(int cur_rate_pow) {
		cUR_RATE_POW = cur_rate_pow;
	}

	/**
	 * 計上会社名
	 * 
	 * @return 計上会社名
	 */
	public String getSWK_K_KAI_NAME() {
		return sWK_K_KAI_NAME;
	}

	/**
	 * 計上会社名
	 * 
	 * @param swk_k_kai_name 計上会社名
	 */
	public void setSWK_K_KAI_NAME(String swk_k_kai_name) {
		sWK_K_KAI_NAME = swk_k_kai_name;
	}

	/**
	 * 計上会社略称
	 * 
	 * @return 計上会社略称
	 */
	public String getSWK_K_KAI_NAME_S() {
		return sWK_K_KAI_NAME_S;
	}

	/**
	 * 計上会社略称
	 * 
	 * @param swk_k_kai_name_s 計上会社略称
	 */
	public void setSWK_K_KAI_NAME_S(String swk_k_kai_name_s) {
		sWK_K_KAI_NAME_S = swk_k_kai_name_s;
	}

	/**
	 * 機能通貨コード
	 * 
	 * @return 機能通貨コード
	 */
	public String getFUNC_CUR_CODE() {
		return fUNC_CUR_CODE;
	}

	/**
	 * 機能通貨コード
	 * 
	 * @param func_cur_code 機能通貨コード
	 */
	public void setFUNC_CUR_CODE(String func_cur_code) {
		fUNC_CUR_CODE = func_cur_code;
	}

	/**
	 * 機能通貨小数点桁数
	 * 
	 * @return 機能通貨小数点桁数
	 */
	public int getFUNC_CUR_DEC_KETA() {
		return fUNC_CUR_DEC_KETA;
	}

	/**
	 * 機能通貨小数点桁数
	 * 
	 * @param func_cur_dec_keta 機能通貨小数点桁数
	 */
	public void setFUNC_CUR_DEC_KETA(int func_cur_dec_keta) {
		fUNC_CUR_DEC_KETA = func_cur_dec_keta;
	}

	/**
	 * 基軸通貨コード
	 * 
	 * @return 基軸通貨コード
	 */
	public String getKEY_CUR_CODE() {
		return kEY_CUR_CODE;
	}

	/**
	 * 基軸通貨コード
	 * 
	 * @param key_cur_code 基軸通貨コード
	 */
	public void setKEY_CUR_CODE(String key_cur_code) {
		kEY_CUR_CODE = key_cur_code;
	}

	/**
	 * 基軸通貨小数点桁数
	 * 
	 * @return kEY_CUR_DEC_KETA
	 */
	public int getKEY_CUR_DEC_KETA() {
		return kEY_CUR_DEC_KETA;
	}

	/**
	 * 基軸通貨小数点桁数
	 * 
	 * @param key_cur_dec_keta 基軸通貨小数点桁数
	 */
	public void setKEY_CUR_DEC_KETA(int key_cur_dec_keta) {
		kEY_CUR_DEC_KETA = key_cur_dec_keta;
	}

	/**
	 * 補助科目があるかどうか
	 * 
	 * @return true:ある
	 */
	public boolean hasSubItem() {
		return !Util.isNullOrEmpty(sWK_HKM_CODE);
	}

	/**
	 * 内訳科目があるかどうか
	 * 
	 * @return true:ある
	 */
	public boolean hasDetailItem() {
		return !Util.isNullOrEmpty(sWK_UKM_CODE);
	}

	/**
	 * 債務残高
	 * 
	 * @return 債務残高
	 */
	public AP_ZAN getAP_ZAN() {
		return apBalance;
	}

	/**
	 * 債務残高
	 * 
	 * @param apBalance 債務残高
	 */
	public void setAP_ZAN(AP_ZAN apBalance) {
		this.apBalance = apBalance;
	}

	/**
	 * 債権残高
	 * 
	 * @return 債務残高
	 */
	public AR_ZAN getAR_ZAN() {
		return arBalance;
	}

	/**
	 * 債権残高
	 * 
	 * @param arBalance 債権残高
	 */
	public void setAR_ZAN(AR_ZAN arBalance) {
		this.arBalance = arBalance;
	}

	/**
	 * 消込明細であるかどうか
	 * 
	 * @return true:消込明細
	 */
	public boolean isErasing() {
		return apBalance != null || arBalance != null || bsDetail != null;
	}

	/**
	 * AP消込明細であるかどうか
	 * 
	 * @return true:AP消込明細
	 */
	public boolean isAPErasing() {
		return apBalance != null;
	}

	/**
	 * AR消込明細であるかどうか
	 * 
	 * @return true:AR消込明細
	 */
	public boolean isARErasing() {
		return arBalance != null;
	}

	/**
	 * BS消込明細であるかどうか
	 * 
	 * @return true:BS消込明細
	 */
	public boolean isBSErasing() {
		return bsDetail != null;
	}

	/**
	 * 集計取引先コード
	 * 
	 * @return 集計取引先コード
	 */
	public String getSUM_TRI_CODE() {
		return sUM_TRI_CODE;
	}

	/**
	 * 集計取引先コード
	 * 
	 * @param sum_tri_code 集計取引先コード
	 */
	public void setSUM_TRI_CODE(String sum_tri_code) {
		sUM_TRI_CODE = sum_tri_code;
	}

	/**
	 * 集計取引先略称
	 * 
	 * @return 集計取引先略称
	 */
	public String getSUM_TRI_NAME_S() {
		return sUM_TRI_NAME_S;
	}

	/**
	 * 集計取引先略称
	 * 
	 * @param sum_tri_name_s 集計取引先略称
	 */
	public void setSUM_TRI_NAME_S(String sum_tri_name_s) {
		sUM_TRI_NAME_S = sum_tri_name_s;
	}

	/**
	 * 港費概算伝票番号を設定する
	 * 
	 * @param sWK_EST_DEN_NO
	 */
	public void setSWK_EST_DEN_NO(String sWK_EST_DEN_NO) {
		this.sWK_EST_DEN_NO = sWK_EST_DEN_NO;
	}

	/**
	 * 港費概算伝票番号を取得する
	 * 
	 * @return sWK_EST_DEN_NO
	 */
	public String getSWK_EST_DEN_NO() {
		return this.sWK_EST_DEN_NO;
	}

	/**
	 * 港費実算伝票番号を設定する
	 * 
	 * @param sWK_STL_DEN_NO
	 */
	public void setSWK_STL_DEN_NO(String sWK_STL_DEN_NO) {
		this.sWK_STL_DEN_NO = sWK_STL_DEN_NO;
	}

	/**
	 * 港費実算伝票番号を取得する
	 * 
	 * @return sWK_STL_DEN_NO
	 */
	public String getSWK_STL_DEN_NO() {
		return this.sWK_STL_DEN_NO;
	}

	/**
	 * 港費前渡金伝票番号を設定する
	 * 
	 * @param sWK_MAE_DEN_NO
	 */
	public void setSWK_MAE_DEN_NO(String sWK_MAE_DEN_NO) {
		this.sWK_MAE_DEN_NO = sWK_MAE_DEN_NO;
	}

	/**
	 * 港費前渡金伝票番号を取得する
	 * 
	 * @return sWK_MAE_DEN_NO
	 */
	public String getSWK_MAE_DEN_NO() {
		return this.sWK_MAE_DEN_NO;
	}

	/**
	 * 港費前渡金行番号を設定する
	 * 
	 * @param sWK_MAE_GYO_NO
	 */
	public void setSWK_MAE_GYO_NO(Integer sWK_MAE_GYO_NO) {
		this.sWK_MAE_GYO_NO = sWK_MAE_GYO_NO;
	}

	/**
	 * 港費前渡金行番号を取得する
	 * 
	 * @return sWK_MAE_GYO_NO
	 */
	public Integer getSWK_MAE_GYO_NO() {
		return this.sWK_MAE_GYO_NO;
	}

	/**
	 * 科目発生日使用の取得
	 * 
	 * @return useItemOccurDate 科目発生日使用
	 */
	public boolean isUseItemOccurDate() {
		return useItemOccurDate;
	}

	/**
	 * 科目発生日使用の設定
	 * 
	 * @param useItemOccurDate 科目発生日使用
	 */
	public void setUseItemOccurDate(boolean useItemOccurDate) {
		this.useItemOccurDate = useItemOccurDate;
	}

	/**
	 * 計上会社の設定
	 * 
	 * @param comapny 計上会社
	 */
	public void setAppropriateCompany(Company comapny) {
		appropriateCompany = comapny;

		if (comapny == null) {
			sWK_K_KAI_CODE = null;
			sWK_K_KAI_NAME = null;
			sWK_K_KAI_NAME_S = null;

		} else {
			sWK_K_KAI_CODE = comapny.getCode();
			sWK_K_KAI_NAME = comapny.getName();
			sWK_K_KAI_NAME_S = comapny.getNames();
		}
	}

	/**
	 * 計上会社の取得
	 * 
	 * @return 計上会社
	 */
	public Company getAppropriateCompany() {
		return appropriateCompany;
	}

	/**
	 * 計上部門の設定
	 * 
	 * @param dept 部門
	 */
	public void setDepartment(Department dept) {
		department = dept;

		if (dept == null) {
			sWK_DEP_CODE = null;
			sWK_DEP_NAME = null;
			sWK_DEP_NAME_S = null;

		} else {
			sWK_DEP_CODE = dept.getCode();
			sWK_DEP_NAME = dept.getName();
			sWK_DEP_NAME_S = dept.getNames();
		}
	}

	/**
	 * 計上部門の取得
	 * 
	 * @return 部門
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * 科目の設定
	 * 
	 * @param item 科目
	 */
	public void setItem(Item item) {
		this.item = item;

		if (item == null) {
			sWK_KMK_CODE = null;
			sWK_KMK_NAME = null;
			sWK_KMK_NAME_S = null;
			sWK_HKM_CODE = null;
			sWK_HKM_NAME = null;
			sWK_HKM_NAME_S = null;
			sWK_UKM_CODE = null;
			sWK_UKM_NAME = null;
			sWK_UKM_NAME_S = null;

			// 発生日フラグ
			useItemOccurDate = false;

		} else {
			sWK_KMK_CODE = item.getCode();
			sWK_KMK_NAME = item.getName();
			sWK_KMK_NAME_S = item.getNames();
			sWK_HKM_CODE = item.getSubItemCode();
			sWK_HKM_NAME = item.getSubItemName();
			sWK_HKM_NAME_S = item.getSubItemNames();
			sWK_UKM_CODE = item.getDetailItemCode();
			sWK_UKM_NAME = item.getDetailItemName();
			sWK_UKM_NAME_S = item.getDetailItemNames();

			// 発生日フラグ
			useItemOccurDate = item.getPreferedItem().isUseOccurDate();
		}
	}

	/**
	 * 科目の取得
	 * 
	 * @return 科目
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * 行摘要
	 * 
	 * @param remark 行摘要
	 */
	public void setRemark(Remark remark) {
		this.remark = remark;

		if (remark == null) {
			sWK_GYO_TEK = null;
			sWK_GYO_TEK_CODE = null;

		} else {
			sWK_GYO_TEK_CODE = remark.getCode();
			sWK_GYO_TEK = remark.getName();
		}
	}

	/**
	 * 行摘要
	 * 
	 * @return 行摘要
	 */
	public Remark getRemark() {
		return remark;
	}

	/**
	 * 消費税
	 * 
	 * @param tax 消費税
	 */
	public void setTax(ConsumptionTax tax) {
		this.tax = tax;

		if (tax == null) {
			sWK_ZEI_CODE = null;
			sWK_ZEI_NAME = null;
			sWK_ZEI_NAME_S = null;
			sWK_ZEI_RATE = null;

		} else {
			sWK_ZEI_CODE = tax.getCode();
			sWK_ZEI_NAME = tax.getName();
			sWK_ZEI_NAME_S = tax.getNames();
			sWK_ZEI_RATE = tax.getRate();
		}
	}

	/**
	 * 消費税
	 * 
	 * @return 消費税
	 */
	public ConsumptionTax getTax() {
		return this.tax;
	}

	/**
	 * 通貨
	 * 
	 * @return 通貨
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * 通貨
	 * 
	 * @param currency 通貨
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;

		if (currency == null) {
			sWK_CUR_CODE = null;
			cUR_DEC_KETA = 0;
		} else {
			sWK_CUR_CODE = currency.getCode();
			cUR_DEC_KETA = currency.getDecimalPoint();
		}
	}

	/**
	 * 社員
	 * 
	 * @param employee 社員
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;

		if (employee == null) {
			sWK_EMP_CODE = null;
			sWK_EMP_NAME = null;
			sWK_EMP_NAME_S = null;

		} else {
			sWK_EMP_CODE = employee.getCode();
			sWK_EMP_NAME = employee.getName();
			sWK_EMP_NAME_S = employee.getNames();
		}
	}

	/**
	 * 社員
	 * 
	 * @return 社員
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * 取引先
	 * 
	 * @param customer 取引先
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;

		if (customer == null) {
			sWK_TRI_CODE = null;
			sWK_TRI_NAME = null;
			sWK_TRI_NAME_S = null;

		} else {
			sWK_TRI_CODE = customer.getCode();
			sWK_TRI_NAME = customer.getName();
			sWK_TRI_NAME_S = customer.getNames();
		}
	}

	/**
	 * 取引先
	 * 
	 * @return 取引先
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * 管理1
	 * 
	 * @param mng1 管理1
	 */
	public void setManagement1(Management1 mng1) {
		this.management1 = mng1;

		if (management1 == null) {
			sWK_KNR_CODE_1 = null;
			sWK_KNR_NAME_1 = null;
			sWK_KNR_NAME_S_1 = null;

		} else {
			sWK_KNR_CODE_1 = management1.getCode();
			sWK_KNR_NAME_1 = management1.getName();
			sWK_KNR_NAME_S_1 = management1.getNames();
		}
	}

	/**
	 * 管理1
	 * 
	 * @return 管理1
	 */
	public Management1 getManagement1() {
		return management1;
	}

	/**
	 * 管理2
	 * 
	 * @param mng2 管理2
	 */
	public void setManagement2(Management2 mng2) {
		this.management2 = mng2;

		if (management2 == null) {
			sWK_KNR_CODE_2 = null;
			sWK_KNR_NAME_2 = null;
			sWK_KNR_NAME_S_2 = null;

		} else {
			sWK_KNR_CODE_2 = management2.getCode();
			sWK_KNR_NAME_2 = management2.getName();
			sWK_KNR_NAME_S_2 = management2.getNames();
		}
	}

	/**
	 * 管理2
	 * 
	 * @return 管理2
	 */
	public Management2 getManagement2() {
		return management2;
	}

	/**
	 * 管理3
	 * 
	 * @param mng3 管理3
	 */
	public void setManagement3(Management3 mng3) {
		this.management3 = mng3;

		if (management3 == null) {
			sWK_KNR_CODE_3 = null;
			sWK_KNR_NAME_3 = null;
			sWK_KNR_NAME_S_3 = null;

		} else {
			sWK_KNR_CODE_3 = management3.getCode();
			sWK_KNR_NAME_3 = management3.getName();
			sWK_KNR_NAME_S_3 = management3.getNames();
		}
	}

	/**
	 * 管理3
	 * 
	 * @return 管理3
	 */
	public Management3 getManagement3() {
		return management3;
	}

	/**
	 * 管理4
	 * 
	 * @param mng4 管理4
	 */
	public void setManagement4(Management4 mng4) {
		this.management4 = mng4;

		if (management4 == null) {
			sWK_KNR_CODE_4 = null;
			sWK_KNR_NAME_4 = null;
			sWK_KNR_NAME_S_4 = null;

		} else {
			sWK_KNR_CODE_4 = management4.getCode();
			sWK_KNR_NAME_4 = management4.getName();
			sWK_KNR_NAME_S_4 = management4.getNames();
		}
	}

	/**
	 * 管理4
	 * 
	 * @return 管理4
	 */
	public Management4 getManagement4() {
		return management4;
	}

	/**
	 * 管理5
	 * 
	 * @param mng5 管理5
	 */
	public void setManagement5(Management5 mng5) {
		this.management5 = mng5;

		if (management5 == null) {
			sWK_KNR_CODE_5 = null;
			sWK_KNR_NAME_5 = null;
			sWK_KNR_NAME_S_5 = null;

		} else {
			sWK_KNR_CODE_5 = management5.getCode();
			sWK_KNR_NAME_5 = management5.getName();
			sWK_KNR_NAME_S_5 = management5.getNames();
		}
	}

	/**
	 * 管理5
	 * 
	 * @return 管理5
	 */
	public Management5 getManagement5() {
		return management5;
	}

	/**
	 * 管理6
	 * 
	 * @param mng6 管理6
	 */
	public void setManagement6(Management6 mng6) {
		this.management6 = mng6;

		if (management6 == null) {
			sWK_KNR_CODE_6 = null;
			sWK_KNR_NAME_6 = null;
			sWK_KNR_NAME_S_6 = null;

		} else {
			sWK_KNR_CODE_6 = management6.getCode();
			sWK_KNR_NAME_6 = management6.getName();
			sWK_KNR_NAME_S_6 = management6.getNames();
		}
	}

	/**
	 * 管理6
	 * 
	 * @return 管理6
	 */
	public Management6 getManagement6() {
		return management6;
	}

	/**
	 * 決算仕訳かどうか
	 * 
	 * @return true:決算仕訳
	 */
	public boolean isSettlementJornal() {
		return settlementJornal;
	}

	/**
	 * 決算仕訳かどうか
	 * 
	 * @param settlementJornal true:決算仕訳
	 */
	public void setSettlementJornal(boolean settlementJornal) {
		this.settlementJornal = settlementJornal;
	}

	/**
	 * BS勘定消込仕訳の取得
	 * 
	 * @return bsDetail BS勘定消込仕訳
	 */
	public SWK_DTL getBsDetail() {
		return bsDetail;
	}

	/**
	 * BS勘定消込仕訳の設定
	 * 
	 * @param bsDetail BS勘定消込仕訳
	 */
	public void setBsDetail(SWK_DTL bsDetail) {
		this.bsDetail = bsDetail;
	}

	/**
	 * 貸借逆転
	 */
	public void reverseDC() {
		setDC(isDR() ? Dc.CREDIT : Dc.DEBIT);
	}

	/**
	 * getDC()を利用すること
	 * 
	 * @deprecated
	 * @return 貸借
	 */
	@Deprecated
	public Dc getDc() {
		return Dc.getDc(sWK_DC_KBN);
	}

	/**
	 * AP/AR消込区分を設定する
	 * 
	 * @param sWK_APAR_KESI_KBN
	 */
	public void setSWK_APAR_KESI_KBN(int sWK_APAR_KESI_KBN) {
		this.sWK_APAR_KESI_KBN = sWK_APAR_KESI_KBN;
	}

	/**
	 * AP/AR消込区分を取得する
	 * 
	 * @return sWK_APAR_KESI_KBN
	 */
	public int getSWK_APAR_KESI_KBN() {
		return this.sWK_APAR_KESI_KBN;
	}

	/**
	 * AP/AR消込伝票番号を設定する
	 * 
	 * @param sWK_APAR_DEN_NO
	 */
	public void setSWK_APAR_DEN_NO(String sWK_APAR_DEN_NO) {
		this.sWK_APAR_DEN_NO = sWK_APAR_DEN_NO;
	}

	/**
	 * AP/AR消込伝票番号を取得する
	 * 
	 * @return sWK_APAR_DEN_NO
	 */
	public String getSWK_APAR_DEN_NO() {
		return this.sWK_APAR_DEN_NO;
	}

	/**
	 * AP/AR消込行番号を設定する
	 * 
	 * @param sWK_APAR_GYO_NO
	 */
	public void setSWK_APAR_GYO_NO(Integer sWK_APAR_GYO_NO) {
		this.sWK_APAR_GYO_NO = sWK_APAR_GYO_NO;
	}

	/**
	 * AP/AR消込行番号を取得する
	 * 
	 * @return sWK_APAR_GYO_NO
	 */
	public Integer getSWK_APAR_GYO_NO() {
		return this.sWK_APAR_GYO_NO;
	}

	/**
	 * 仕訳明細用OPアイテム(注意：DB関係なくOP処理用のみ)の取得
	 * 
	 * @return journalOPItem 仕訳明細用OPアイテム(注意：DB関係なくOP処理用のみ)
	 */
	public OPItem getJournalOPItem() {
		return journalOPItem;
	}

	/**
	 * 仕訳明細用OPアイテム(注意：DB関係なくOP処理用のみ)の設定
	 * 
	 * @param journalOPItem 仕訳明細用OPアイテム(注意：DB関係なくOP処理用のみ)
	 */
	public void setJournalOPItem(OPItem journalOPItem) {
		this.journalOPItem = journalOPItem;
	}

	/**
	 * 伝票追加情報(注意：DB関係なくOP処理用のみ)の取得
	 * 
	 * @return addonInfo 伝票追加情報(注意：DB関係なくOP処理用のみ)
	 */
	public SlipDetailAddonInfo getAddonInfo() {
		return addonInfo;
	}

	/**
	 * 伝票追加情報(注意：DB関係なくOP処理用のみ)の設定
	 * 
	 * @param addonInfo 伝票追加情報(注意：DB関係なくOP処理用のみ)
	 */
	public void setAddonInfo(SlipDetailAddonInfo addonInfo) {
		this.addonInfo = addonInfo;
	}

	/**
	 * 自由区分の取得
	 * 
	 * @return SWK_FREE_KBN 自由区分
	 */
	public int getSWK_FREE_KBN() {
		return SWK_FREE_KBN;
	}

	/**
	 * 自由区分の設定
	 * 
	 * @param SWK_FREE_KBN 自由区分
	 */
	public void setSWK_FREE_KBN(int SWK_FREE_KBN) {
		this.SWK_FREE_KBN = SWK_FREE_KBN;
	}

	/**
	 * 経過措置の元行番号の取得
	 * 
	 * @return SWK_ORI_GYO_NO 経過措置の元行番号
	 */
	public Integer getSWK_ORI_GYO_NO() {
		return SWK_ORI_GYO_NO;
	}

	/**
	 * 経過措置の元行番号の設定
	 * 
	 * @param SWK_ORI_GYO_NO 経過措置の元行番号
	 */
	public void setSWK_ORI_GYO_NO(Integer SWK_ORI_GYO_NO) {
		this.SWK_ORI_GYO_NO = SWK_ORI_GYO_NO;
	}

	/**
	 * 経過措置仕訳であるかどうかの取得
	 * 
	 * @return isKekaDtl 経過措置仕訳であるかどうか true：経過措置仕訳
	 */
	public boolean isKekaDtl() {
		return isKekaDtl;
	}

	/**
	 * 経過措置仕訳であるかどうかの設定
	 * 
	 * @param isKekaDtl 経過措置仕訳であるかどうか true：経過措置仕訳
	 */
	public void setKekaDtl(boolean isKekaDtl) {
		this.isKekaDtl = isKekaDtl;
	}

	/**
	 * 経過措置控除可能率(1%～99%)の取得
	 * 
	 * @return KEKA_SOTI_RATE 経過措置控除可能率(1%～99%)
	 */
	public BigDecimal getKEKA_SOTI_RATE() {
		return KEKA_SOTI_RATE;
	}

	/**
	 * 経過措置控除可能率(1%～99%)の設定
	 * 
	 * @param KEKA_SOTI_RATE 経過措置控除可能率(1%～99%)
	 */
	public void setKEKA_SOTI_RATE(BigDecimal KEKA_SOTI_RATE) {
		this.KEKA_SOTI_RATE = KEKA_SOTI_RATE;
	}

}

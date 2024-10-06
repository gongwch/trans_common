package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.bill.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.remark.*;

/**
 * 伝票ヘッダー<br>
 * 全ての伝票ヘッダーは当該クラスを継承する
 */
public class SWK_HDR extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String kAI_CODE;

	/** 会社名 */
	protected String kAI_NAME = null;

	/** 会社略称 */
	protected String kAI_NAME_S = null;

	/** 伝票日付 */
	protected Date sWK_DEN_DATE;

	/** 伝票番号 */
	protected String sWK_DEN_NO;

	/** IFRS調整区分 */
	protected int sWK_ADJ_KBN = AccountBook.BOTH.value;

	/** 証憑番号 */
	protected String sWK_SEI_NO;

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

	/** 取引先コード */
	protected String sWK_TRI_CODE;

	/** 取引先名称 */
	protected String sWK_TRI_NAME;

	/** 取引先略称 */
	protected String sWK_TRI_NAME_S;

	/** 社員コード */
	protected String sWK_EMP_CODE;

	/** 社員名称 */
	protected String sWK_EMP_NAME;

	/** 社員略称 */
	protected String sWK_EMP_NAME_S;

	/** 入出金邦貨金額 */
	protected BigDecimal sWK_KIN;

	/** データ区分 */
	protected String sWK_DATA_KBN;

	/** 受付部門コード */
	protected String sWK_UKE_DEP_CODE;

	/** 受付部門名称 */
	protected String sWK_UKE_DEP_NAME;

	/** 受付部門略称 */
	protected String sWK_UKE_DEP_NAME_S;

	/** 伝票摘要コード */
	protected String sWK_TEK_CODE;

	/** 伝票摘要 */
	protected String sWK_TEK;

	/** 訂正前伝票番号 */
	protected String sWK_BEFORE_DEN_NO;

	/** 更新区分 */
	protected int sWK_UPD_KBN;

	/** 更新前の更新区分 */
	protected int sWK_BEFORE_UPD_KBN;

	/** 承認者 */
	protected String sWK_SYO_EMP_CODE;

	/** 承認者名称 */
	protected String sWK_SYO_EMP_NAME;

	/** 承認者略称 */
	protected String sWK_SYO_EMP_NAME_S;

	/** 承認権限グループコード */
	protected String SWK_APRV_GRP_CODE;

	/** 承認日 */
	protected Date sWK_SYO_DATE;

	/** 依頼者 */
	protected String sWK_IRAI_EMP_CODE;

	/** 依頼者名称 */
	protected String sWK_IRAI_EMP_NAME;

	/** 依頼者略称 */
	protected String sWK_IRAI_EMP_NAME_S;

	/** 依頼部門コード */
	protected String sWK_IRAI_DEP_CODE;

	/** 依頼部門名称 */
	protected String sWK_IRAI_DEP_NAME;

	/** 依頼部門略称 */
	protected String sWK_IRAI_DEP_NAME_S;

	/** 依頼日 */
	protected Date sWK_IRAI_DATE;

	/** 排他フラグ */
	protected int sWK_SHR_KBN;

	/** 決算区分 */
	protected int sWK_KSN_KBN;

	/** 登録日付 */
	protected Date iNP_DATE;

	/** 更新日付 */
	protected Date uPD_DATE;

	/** プログラムＩＤ */
	protected String pRG_ID;

	/** ユーザーＩＤ */
	protected String uSR_ID;

	/** 通貨ｺｰﾄﾞ */
	protected String sWK_CUR_CODE;

	/** 通貨小数点桁数 */
	protected int sWK_CUR_DEC_KETA;

	/** ﾚｰﾄ */
	protected BigDecimal sWK_CUR_RATE;

	/** 入出金入力金額 */
	protected BigDecimal sWK_IN_KIN;

	/** ｼｽﾃﾑ区分 */
	protected String sWK_SYS_KBN;

	/** 伝票種別 */
	protected String sWK_DEN_SYU;

	/** 伝票種別名称 */
	protected String sWK_DEN_SYU_NAME;

	/** 伝票種別略称 */
	protected String sWK_DEN_SYU_NAME_S;

	/** 伝票種別検索名称 */
	protected String sWK_DEN_SYU_NAME_K;

	/** 会社間付替伝票区分 */
	protected int sWK_TUKE_KBN;

	/** 伝票修正回数 */
	protected int sWK_UPD_CNT;

	// AP不足分--

	/** 支払区分 */
	protected String sWK_SIHA_KBN;

	/** 支払日 */
	protected Date sWK_SIHA_DATE;

	/** 支払方法 */
	protected String sWK_HOH_CODE;

	/** 支払方法名称 */
	protected String sWK_HOH_NAME;

	/** 支払内部コード */
	protected String sWK_HOH_NAI_CODE;

	/** 保留区分 */
	protected int sWK_HORYU_KBN;

	/** 仮払金額 */
	protected BigDecimal sWK_KARI_KIN;

	/** 経費金額 */
	protected BigDecimal sWK_KEIHI_KIN;

	/** 支払金額(邦貨) */
	protected BigDecimal sWK_SIHA_KIN;

	/** 仮払申請伝票番号 */
	protected String sWK_KARI_DR_DEN_NO;

	/** 仮払精算伝票番号 */
	protected String sWK_KARI_CR_DEN_NO;

	/** 支払決済区分 */
	protected int sWK_KESAI_KBN;

	/** 仮払計上部門コード */
	protected String sWK_KARI_DEP_CODE;

	/** 仮払計上部門名称 */
	protected String sWK_KARI_DEP_NAME;

	/** 仮払計上部門略称 */
	protected String sWK_KARI_DEP_NAME_S;

	/** 登録日付 */
	protected Date sWK_INP_DATE;

	/** 取引先条件ｺｰﾄﾞ */
	protected String sWK_TJK_CODE;

	/** 取引先条件マスタの被仕向口座預金種目 */
	protected int sWK_TJK_YKN_KBN;

	/** 取引先条件マスタの被仕向口座番号 */
	protected String sWK_TJK_YKN_NO;

	/** 取引先条件マスタの被仕向口座名義カナ */
	protected String sWK_TJK_YKN_KANA;

	/** 英文銀行名 */
	protected String sWK_TJK_GS_BNK_NAME;

	/** 英文支店名 */
	protected String sWK_TJK_GS_STN_NAME;

	/** 英文銀行住所 */
	protected String sWK_TJK_GS_BNK_ADR;

	/** 取引先条件銀行名(支払銀行名) */
	protected String sWK_TJK_BNK_NAME_S;

	/** 取引先条件銀行口座名(支払銀行支店名) */
	protected String sWK_TJK_BNK_STN_NAME_S;

	/** 入力仮払金額 */
	protected BigDecimal sWK_IN_KARI_KIN;

	/** 入力経費金額 */
	protected BigDecimal sWK_IN_KEIHI_KIN;

	/** 入力支払金額 */
	protected BigDecimal sWK_IN_SIHA_KIN;

	/** 請求書通貨ｺｰﾄﾞ */
	protected String sWK_INV_CUR_CODE;

	/** 請求書通貨ﾚｰﾄ */
	protected BigDecimal sWK_INV_CUR_RATE;

	/** 請求書通貨金額 */
	protected BigDecimal sWK_INV_KIN;

	/** 銀行口座ｺｰﾄﾞ */
	protected String sWK_CBK_CODE;

	/** 銀行口座名称 */
	protected String sWK_CBK_NAME;

	/** 預金種目 */
	protected int sWK_CBK_YKN_KBN;

	/** 口座番号 */
	protected String sWK_CBK_YKN_NO;

	/** 支店コード */
	protected String sWK_CBK_STN_CODE;

	/** 銀行名 */
	protected String sWK_BNK_NAME_S;

	/** 支店名 */
	protected String sWK_BNK_STN_NAME_S;

	/** 精算予定日 */
	protected Date sWK_SSY_DATE;

	/** 受付No. */
	protected String sWK_UTK_NO;

	/** 仮払通貨ｺｰﾄﾞ */
	protected String sWK_KARI_CUR_CODE;

	/** 仮払通貨ﾚｰﾄ */
	protected BigDecimal sWK_KARI_CUR_RATE;

	// AR不足分--

	/** 請求区分 */
	protected String sWK_SEI_KBN;

	/** 入金予定日 */
	protected Date sWK_AR_DATE;

	// 追加 --

	/** 基軸通貨コード */
	protected String kEY_CUR_CODE;

	/** 基軸通貨小数点桁数 */
	protected int kEY_CUR_DEC_KETA;

	/** 機能通貨コード */
	protected String fUNC_CUR_CODE;

	/** 機能通貨小数点桁数 */
	protected int fUNC_CUR_DEC_KETA;

	// PE不足分--

	/** 港費概算取消伝票番号 */
	protected String sWK_EST_CANCEL_DEN_NO;

	/** 港費元概算伝票番号 */
	protected String sWK_BASE_EST_DEN_NO;

	// エンティティ系 --

	/** 部門マスタ */
	protected Department department = null;

	/** 科目マスタ */
	protected Item item = null;

	/** 通貨マスタ */
	protected Currency currency = null;

	/** 取引先マスタ */
	protected Customer customer = null;

	/** 支払方法マスタ */
	protected PaymentMethod paymentMethod = null;

	/** 取引先条件マスタ */
	protected PaymentSetting paymentSetting = null;

	/** 銀行口座マスタ */
	protected BankAccount bankAccount = null;

	/** 請求区分マスタ */
	protected BillType billType = null;

	/** 社員 */
	protected Employee employee = null;

	/** 摘要 */
	protected Remark remark = null;

	/** 添付 */
	protected List<SWK_ATTACH> attachments = null;

	/** 添付情報があるかどうか true：ある */
	protected boolean isExistAttachment = false;

	/** 承認権限ロールグループ */
	protected AprvRoleGroup aprRoleGroup = null;

	/** ワークフロー承認コントロール */
	protected SWK_SYO_CTL syoCtl = null;

	/**
	 * コンストラクタ
	 */
	public SWK_HDR() {
		super();
	}

	/**
	 * 必須項目にNULLが含まれていないかどうかをチェック
	 * 
	 * @return true:null、またはブランクが含まれる
	 */
	public String isReuiredItemNULL() {

		// 会社コード KAI_CODE
		// 伝票日付 SWK_DEN_DATE
		// 伝票番号 SWK_DEN_NO
		// データ区分 SWK_DATA_KBN
		// 伝票摘要 SWK_TEK
		// 更新区分 SWK_UPD_KBN
		// 決算区分 SWK_KSN_KBN
		Map<String, Object> notNullList = new TreeMap<String, Object>();
		notNullList.put("KAI_CODE", kAI_CODE);
		notNullList.put("SWK_DEN_DATE", sWK_DEN_DATE);
		notNullList.put("SWK_DEN_NO", sWK_DEN_NO);
		notNullList.put("SWK_TEK", sWK_TEK);
		notNullList.put("SWK_DATA_KBN", sWK_DATA_KBN);
		notNullList.put("SWK_DEN_SYU", sWK_DEN_SYU);

		for (Map.Entry<String, Object> entry : notNullList.entrySet()) {
			if (Util.isNullOrEmpty(entry.getValue())) {
				return entry.getKey();
			}
		}

		return "";
	}

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SWK_HDR clone() {
		try {
			SWK_HDR hdr = (SWK_HDR) super.clone();
			hdr.attachments = this.attachments;
			return hdr;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * 会社名の取得
	 * 
	 * @return kAI_NAME 会社名
	 */
	public String getKAI_NAME() {
		return kAI_NAME;
	}

	/**
	 * 会社名の設定
	 * 
	 * @param kAI_NAME 会社名
	 */
	public void setKAI_NAME(String kAI_NAME) {
		this.kAI_NAME = kAI_NAME;
	}

	/**
	 * 会社略称の取得
	 * 
	 * @return kAI_NAME_S 会社略称
	 */
	public String getKAI_NAME_S() {
		return kAI_NAME_S;
	}

	/**
	 * 会社略称の設定
	 * 
	 * @param kAI_NAME_S 会社略称
	 */
	public void setKAI_NAME_S(String kAI_NAME_S) {
		this.kAI_NAME_S = kAI_NAME_S;
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
	 * 訂正前伝票番号
	 * 
	 * @return 訂正前伝票番号
	 */
	public String getSWK_BEFORE_DEN_NO() {
		return sWK_BEFORE_DEN_NO;
	}

	/**
	 * 訂正前伝票番号
	 * 
	 * @param swk_before_den_no 訂正前伝票番号
	 */
	public void setSWK_BEFORE_DEN_NO(String swk_before_den_no) {
		sWK_BEFORE_DEN_NO = swk_before_den_no;
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
	 * 伝票の種類
	 * 
	 * @return 伝票の種類
	 */
	public SlipKind getSlipKind() {
		return SlipKind.get(sWK_DATA_KBN);
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
	 * 伝票種別
	 * 
	 * @return 伝票種別
	 */
	public String getSWK_DEN_SYU() {
		return sWK_DEN_SYU;
	}

	/**
	 * 伝票種別
	 * 
	 * @param swk_den_syu 伝票種別
	 */
	public void setSWK_DEN_SYU(String swk_den_syu) {
		sWK_DEN_SYU = swk_den_syu;
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
	 * 入出金入力金額
	 * 
	 * @return 入出金入力金額
	 */
	public BigDecimal getSWK_IN_KIN() {
		return sWK_IN_KIN;
	}

	/**
	 * 入出金入力金額
	 * 
	 * @param swk_in_kin 入出金入力金額
	 */
	public void setSWK_IN_KIN(BigDecimal swk_in_kin) {
		sWK_IN_KIN = swk_in_kin;
	}

	/**
	 * 依頼日
	 * 
	 * @return 依頼日
	 */
	public Date getSWK_IRAI_DATE() {
		return sWK_IRAI_DATE;
	}

	/**
	 * 依頼日
	 * 
	 * @param swk_irai_date 依頼日
	 */
	public void setSWK_IRAI_DATE(Date swk_irai_date) {
		sWK_IRAI_DATE = swk_irai_date;
	}

	/**
	 * 依頼部門コード
	 * 
	 * @return 依頼部門コード
	 */
	public String getSWK_IRAI_DEP_CODE() {
		return sWK_IRAI_DEP_CODE;
	}

	/**
	 * 依頼部門コード
	 * 
	 * @param swk_irai_dep_code 依頼部門コード
	 */
	public void setSWK_IRAI_DEP_CODE(String swk_irai_dep_code) {
		sWK_IRAI_DEP_CODE = swk_irai_dep_code;
	}

	/**
	 * 依頼者
	 * 
	 * @return 依頼者
	 */
	public String getSWK_IRAI_EMP_CODE() {
		return sWK_IRAI_EMP_CODE;
	}

	/**
	 * 依頼者
	 * 
	 * @param swk_irai_emp_code 依頼者
	 */
	public void setSWK_IRAI_EMP_CODE(String swk_irai_emp_code) {
		sWK_IRAI_EMP_CODE = swk_irai_emp_code;
	}

	/**
	 * 入出金邦貨金額
	 * 
	 * @return 入出金邦貨金額
	 */
	public BigDecimal getSWK_KIN() {
		return sWK_KIN;
	}

	/**
	 * 入出金邦貨金額
	 * 
	 * @param swk_kin 入出金邦貨金額
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
	 * 排他フラグ
	 * 
	 * @return 排他フラグ
	 */
	public int getSWK_SHR_KBN() {
		return sWK_SHR_KBN;
	}

	/**
	 * 排他フラグ
	 * 
	 * @param swk_shr_kbn 排他フラグ
	 */
	public void setSWK_SHR_KBN(int swk_shr_kbn) {
		sWK_SHR_KBN = swk_shr_kbn;
	}

	/**
	 * 承認日
	 * 
	 * @return 承認日
	 */
	public Date getSWK_SYO_DATE() {
		return sWK_SYO_DATE;
	}

	/**
	 * 承認日
	 * 
	 * @param swk_syo_date 承認日
	 */
	public void setSWK_SYO_DATE(Date swk_syo_date) {
		sWK_SYO_DATE = swk_syo_date;
	}

	/**
	 * 承認者
	 * 
	 * @return 承認者
	 */
	public String getSWK_SYO_EMP_CODE() {
		return sWK_SYO_EMP_CODE;
	}

	/**
	 * 承認者
	 * 
	 * @param swk_syo_emp_code 承認者
	 */
	public void setSWK_SYO_EMP_CODE(String swk_syo_emp_code) {
		sWK_SYO_EMP_CODE = swk_syo_emp_code;
	}

	/**
	 * ｼｽﾃﾑ区分
	 * 
	 * @return ｼｽﾃﾑ区分
	 */
	public String getSWK_SYS_KBN() {
		return sWK_SYS_KBN;
	}

	/**
	 * ｼｽﾃﾑ区分
	 * 
	 * @param swk_sys_kbn ｼｽﾃﾑ区分
	 */
	public void setSWK_SYS_KBN(String swk_sys_kbn) {
		sWK_SYS_KBN = swk_sys_kbn;
	}

	/**
	 * 伝票摘要
	 * 
	 * @return 伝票摘要
	 */
	public String getSWK_TEK() {
		return sWK_TEK;
	}

	/**
	 * 伝票摘要
	 * 
	 * @param swk_tek 伝票摘要
	 */
	public void setSWK_TEK(String swk_tek) {
		sWK_TEK = swk_tek;
	}

	/**
	 * 伝票摘要コード
	 * 
	 * @return 伝票摘要コード
	 */
	public String getSWK_TEK_CODE() {
		return sWK_TEK_CODE;
	}

	/**
	 * 伝票摘要コード
	 * 
	 * @param swk_tek_code 伝票摘要コード
	 */
	public void setSWK_TEK_CODE(String swk_tek_code) {
		sWK_TEK_CODE = swk_tek_code;
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
	 * 受付部門コード
	 * 
	 * @return 受付部門コード
	 */
	public String getSWK_UKE_DEP_CODE() {
		return sWK_UKE_DEP_CODE;
	}

	/**
	 * 受付部門コード
	 * 
	 * @param swk_uke_dep_code 受付部門コード
	 */
	public void setSWK_UKE_DEP_CODE(String swk_uke_dep_code) {
		sWK_UKE_DEP_CODE = swk_uke_dep_code;
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
	 * 更新区分を取得する
	 * 
	 * @return 更新区分Enum
	 */
	public SlipState getSlipState() {
		return SlipState.getSlipState(sWK_UPD_KBN);
	}

	/**
	 * 更新区分を設定する
	 * 
	 * @param state 更新区分Enum
	 */
	public void setSlipState(SlipState state) {
		this.sWK_UPD_KBN = state.value;
	}

	/**
	 * 更新変更前の更新区分取得
	 * 
	 * @return 更新区分
	 */
	public int getSWK_BEFORE_UPD_KBN() {
		return sWK_BEFORE_UPD_KBN;
	}

	/**
	 * 更新変更前の更新区分設定
	 * 
	 * @param swk_upd_kbn_before 更新区分
	 */
	public void setSWK_BEFORE_UPD_KBN(int swk_upd_kbn_before) {
		sWK_BEFORE_UPD_KBN = swk_upd_kbn_before;
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
	 * 入金予定日
	 * 
	 * @return 入金予定日
	 */
	public Date getSWK_AR_DATE() {
		return sWK_AR_DATE;
	}

	/**
	 * 入金予定日
	 * 
	 * @param swk_ar_date 入金予定日
	 */
	public void setSWK_AR_DATE(Date swk_ar_date) {
		sWK_AR_DATE = swk_ar_date;
	}

	/**
	 * 銀行口座ｺｰﾄﾞ
	 * 
	 * @return 銀行口座ｺｰﾄﾞ
	 */
	public String getSWK_CBK_CODE() {
		return sWK_CBK_CODE;
	}

	/**
	 * 銀行口座ｺｰﾄﾞ
	 * 
	 * @param swk_cbk_code 銀行口座ｺｰﾄﾞ
	 */
	public void setSWK_CBK_CODE(String swk_cbk_code) {
		sWK_CBK_CODE = swk_cbk_code;
	}

	/**
	 * 支払方法
	 * 
	 * @return 支払方法
	 */
	public String getSWK_HOH_CODE() {
		return sWK_HOH_CODE;
	}

	/**
	 * 支払方法
	 * 
	 * @param swk_hoh_code 支払方法
	 */
	public void setSWK_HOH_CODE(String swk_hoh_code) {
		sWK_HOH_CODE = swk_hoh_code;
	}

	/**
	 * 保留区分
	 * 
	 * @return 保留区分
	 */
	public int getSWK_HORYU_KBN() {
		return sWK_HORYU_KBN;
	}

	/**
	 * 保留区分
	 * 
	 * @param swk_horyu_kbn 保留区分
	 */
	public void setSWK_HORYU_KBN(int swk_horyu_kbn) {
		sWK_HORYU_KBN = swk_horyu_kbn;
	}

	/**
	 * 入力仮払金額
	 * 
	 * @return 入力仮払金額
	 */
	public BigDecimal getSWK_IN_KARI_KIN() {
		return sWK_IN_KARI_KIN;
	}

	/**
	 * 入力仮払金額
	 * 
	 * @param swk_in_kari_kin 入力仮払金額
	 */
	public void setSWK_IN_KARI_KIN(BigDecimal swk_in_kari_kin) {
		sWK_IN_KARI_KIN = swk_in_kari_kin;
	}

	/**
	 * 入力経費金額
	 * 
	 * @return 入力経費金額
	 */
	public BigDecimal getSWK_IN_KEIHI_KIN() {
		return sWK_IN_KEIHI_KIN;
	}

	/**
	 * 入力経費金額
	 * 
	 * @param swk_in_keihi_kin 入力経費金額
	 */
	public void setSWK_IN_KEIHI_KIN(BigDecimal swk_in_keihi_kin) {
		sWK_IN_KEIHI_KIN = swk_in_keihi_kin;
	}

	/**
	 * 入力支払金額
	 * 
	 * @return 入力支払金額
	 */
	public BigDecimal getSWK_IN_SIHA_KIN() {
		return sWK_IN_SIHA_KIN;
	}

	/**
	 * 入力支払金額
	 * 
	 * @param swk_in_siha_kin 入力支払金額
	 */
	public void setSWK_IN_SIHA_KIN(BigDecimal swk_in_siha_kin) {
		sWK_IN_SIHA_KIN = swk_in_siha_kin;
	}

	/**
	 * 登録日付
	 * 
	 * @return 登録日付
	 */
	public Date getSWK_INP_DATE() {
		return sWK_INP_DATE;
	}

	/**
	 * 登録日付
	 * 
	 * @param swk_inp_date 登録日付
	 */
	public void setSWK_INP_DATE(Date swk_inp_date) {
		sWK_INP_DATE = swk_inp_date;
	}

	/**
	 * 請求書通貨ｺｰﾄﾞ
	 * 
	 * @return 請求書通貨ｺｰﾄﾞ
	 */
	public String getSWK_INV_CUR_CODE() {
		return sWK_INV_CUR_CODE;
	}

	/**
	 * 請求書通貨ｺｰﾄﾞ
	 * 
	 * @param swk_inv_cur_code 請求書通貨ｺｰﾄﾞ
	 */
	public void setSWK_INV_CUR_CODE(String swk_inv_cur_code) {
		sWK_INV_CUR_CODE = swk_inv_cur_code;
	}

	/**
	 * 請求書通貨ﾚｰﾄ
	 * 
	 * @return 請求書通貨ﾚｰﾄ
	 */
	public BigDecimal getSWK_INV_CUR_RATE() {
		return sWK_INV_CUR_RATE;
	}

	/**
	 * 請求書通貨ﾚｰﾄ
	 * 
	 * @param swk_inv_cur_rate 請求書通貨ﾚｰﾄ
	 */
	public void setSWK_INV_CUR_RATE(BigDecimal swk_inv_cur_rate) {
		sWK_INV_CUR_RATE = swk_inv_cur_rate;
	}

	/**
	 * 請求書通貨金額
	 * 
	 * @return 請求書通貨金額
	 */
	public BigDecimal getSWK_INV_KIN() {
		return sWK_INV_KIN;
	}

	/**
	 * 請求書通貨金額
	 * 
	 * @param swk_inv_kin 請求書通貨金額
	 */
	public void setSWK_INV_KIN(BigDecimal swk_inv_kin) {
		sWK_INV_KIN = swk_inv_kin;
	}

	/**
	 * 仮払精算伝票番号
	 * 
	 * @return 仮払精算伝票番号
	 */
	public String getSWK_KARI_CR_DEN_NO() {
		return sWK_KARI_CR_DEN_NO;
	}

	/**
	 * 仮払精算伝票番号
	 * 
	 * @param swk_kari_cr_den_no 仮払精算伝票番号
	 */
	public void setSWK_KARI_CR_DEN_NO(String swk_kari_cr_den_no) {
		sWK_KARI_CR_DEN_NO = swk_kari_cr_den_no;
	}

	/**
	 * 仮払通貨ｺｰﾄﾞ
	 * 
	 * @return 仮払通貨ｺｰﾄﾞ
	 */
	public String getSWK_KARI_CUR_CODE() {
		return sWK_KARI_CUR_CODE;
	}

	/**
	 * 仮払通貨ｺｰﾄﾞ
	 * 
	 * @param swk_kari_cur_code 仮払通貨ｺｰﾄﾞ
	 */
	public void setSWK_KARI_CUR_CODE(String swk_kari_cur_code) {
		sWK_KARI_CUR_CODE = swk_kari_cur_code;
	}

	/**
	 * 仮払通貨ﾚｰﾄ
	 * 
	 * @return 仮払通貨ﾚｰﾄ
	 */
	public BigDecimal getSWK_KARI_CUR_RATE() {
		return sWK_KARI_CUR_RATE;
	}

	/**
	 * 仮払通貨ﾚｰﾄ
	 * 
	 * @param swk_kari_cur_rate 仮払通貨ﾚｰﾄ
	 */
	public void setSWK_KARI_CUR_RATE(BigDecimal swk_kari_cur_rate) {
		sWK_KARI_CUR_RATE = swk_kari_cur_rate;
	}

	/**
	 * 仮払計上部門コード
	 * 
	 * @return 仮払計上部門コード
	 */
	public String getSWK_KARI_DEP_CODE() {
		return sWK_KARI_DEP_CODE;
	}

	/**
	 * 仮払計上部門コード
	 * 
	 * @param swk_kari_dep_code 仮払計上部門コード
	 */
	public void setSWK_KARI_DEP_CODE(String swk_kari_dep_code) {
		sWK_KARI_DEP_CODE = swk_kari_dep_code;
	}

	/**
	 * 仮払申請伝票番号
	 * 
	 * @return 仮払申請伝票番号
	 */
	public String getSWK_KARI_DR_DEN_NO() {
		return sWK_KARI_DR_DEN_NO;
	}

	/**
	 * 仮払申請伝票番号
	 * 
	 * @param swk_kari_dr_den_no 仮払申請伝票番号
	 */
	public void setSWK_KARI_DR_DEN_NO(String swk_kari_dr_den_no) {
		sWK_KARI_DR_DEN_NO = swk_kari_dr_den_no;
	}

	/**
	 * 仮払金額
	 * 
	 * @return 仮払金額
	 */
	public BigDecimal getSWK_KARI_KIN() {
		return sWK_KARI_KIN;
	}

	/**
	 * 仮払金額
	 * 
	 * @param swk_kari_kin 仮払金額
	 */
	public void setSWK_KARI_KIN(BigDecimal swk_kari_kin) {
		sWK_KARI_KIN = swk_kari_kin;
	}

	/**
	 * 経費金額
	 * 
	 * @return 経費金額
	 */
	public BigDecimal getSWK_KEIHI_KIN() {
		return sWK_KEIHI_KIN;
	}

	/**
	 * 経費金額
	 * 
	 * @param swk_keihi_kin 経費金額
	 */
	public void setSWK_KEIHI_KIN(BigDecimal swk_keihi_kin) {
		sWK_KEIHI_KIN = swk_keihi_kin;
	}

	/**
	 * 支払決済区分
	 * 
	 * @return 支払決済区分
	 */
	public int getSWK_KESAI_KBN() {
		return sWK_KESAI_KBN;
	}

	/**
	 * 支払決済区分
	 * 
	 * @param swk_kesai_kbn 支払決済区分
	 */
	public void setSWK_KESAI_KBN(int swk_kesai_kbn) {
		sWK_KESAI_KBN = swk_kesai_kbn;
	}

	/**
	 * 請求区分
	 * 
	 * @return 請求区分
	 */
	public String getSWK_SEI_KBN() {
		return sWK_SEI_KBN;
	}

	/**
	 * 請求区分
	 * 
	 * @param swk_sei_kbn 請求区分
	 */
	public void setSWK_SEI_KBN(String swk_sei_kbn) {
		sWK_SEI_KBN = swk_sei_kbn;
	}

	/**
	 * 支払日
	 * 
	 * @return 支払日
	 */
	public Date getSWK_SIHA_DATE() {
		return sWK_SIHA_DATE;
	}

	/**
	 * 支払日
	 * 
	 * @param swk_siha_date 支払日
	 */
	public void setSWK_SIHA_DATE(Date swk_siha_date) {
		sWK_SIHA_DATE = swk_siha_date;
	}

	/**
	 * 支払区分
	 * 
	 * @return 支払区分
	 */
	public String getSWK_SIHA_KBN() {
		return sWK_SIHA_KBN;
	}

	/**
	 * 支払区分
	 * 
	 * @param swk_siha_kbn 支払区分
	 */
	public void setSWK_SIHA_KBN(String swk_siha_kbn) {
		sWK_SIHA_KBN = swk_siha_kbn;
	}

	/**
	 * 支払金額(邦貨)
	 * 
	 * @return 支払金額(邦貨)
	 */
	public BigDecimal getSWK_SIHA_KIN() {
		return sWK_SIHA_KIN;
	}

	/**
	 * 支払金額(邦貨)
	 * 
	 * @param swk_siha_kin 支払金額(邦貨)
	 */
	public void setSWK_SIHA_KIN(BigDecimal swk_siha_kin) {
		sWK_SIHA_KIN = swk_siha_kin;
	}

	/**
	 * 精算予定日
	 * 
	 * @return 精算予定日
	 */
	public Date getSWK_SSY_DATE() {
		return sWK_SSY_DATE;
	}

	/**
	 * 精算予定日
	 * 
	 * @param swk_ssy_date 精算予定日
	 */
	public void setSWK_SSY_DATE(Date swk_ssy_date) {
		sWK_SSY_DATE = swk_ssy_date;
	}

	/**
	 * 取引先条件ｺｰﾄﾞ
	 * 
	 * @return 取引先条件ｺｰﾄﾞ
	 */
	public String getSWK_TJK_CODE() {
		return sWK_TJK_CODE;
	}

	/**
	 * 取引先条件ｺｰﾄﾞ
	 * 
	 * @param swk_tjk_code 取引先条件ｺｰﾄﾞ
	 */
	public void setSWK_TJK_CODE(String swk_tjk_code) {
		sWK_TJK_CODE = swk_tjk_code;
	}

	/**
	 * 受付No.
	 * 
	 * @return 受付No.
	 */
	public String getSWK_UTK_NO() {
		return sWK_UTK_NO;
	}

	/**
	 * 受付No.
	 * 
	 * @param swk_utk_no 受付No.
	 */
	public void setSWK_UTK_NO(String swk_utk_no) {
		sWK_UTK_NO = swk_utk_no;
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
	 * 依頼部門名称
	 * 
	 * @return 依頼部門名称
	 */
	public String getSWK_IRAI_DEP_NAME() {
		return sWK_IRAI_DEP_NAME;
	}

	/**
	 * 依頼部門名称
	 * 
	 * @param swk_irai_dep_name 依頼部門名称
	 */
	public void setSWK_IRAI_DEP_NAME(String swk_irai_dep_name) {
		sWK_IRAI_DEP_NAME = swk_irai_dep_name;
	}

	/**
	 * 依頼部門略称
	 * 
	 * @return 依頼部門略称
	 */
	public String getSWK_IRAI_DEP_NAME_S() {
		return sWK_IRAI_DEP_NAME_S;
	}

	/**
	 * 依頼部門略称
	 * 
	 * @param swk_irai_dep_name_s 依頼部門略称
	 */
	public void setSWK_IRAI_DEP_NAME_S(String swk_irai_dep_name_s) {
		sWK_IRAI_DEP_NAME_S = swk_irai_dep_name_s;
	}

	/**
	 * 依頼者名称
	 * 
	 * @return 依頼者名称
	 */
	public String getSWK_IRAI_EMP_NAME() {
		return sWK_IRAI_EMP_NAME;
	}

	/**
	 * 依頼者名称
	 * 
	 * @param swk_irai_emp_name 依頼者名称
	 */
	public void setSWK_IRAI_EMP_NAME(String swk_irai_emp_name) {
		sWK_IRAI_EMP_NAME = swk_irai_emp_name;
	}

	/**
	 * 依頼者略称
	 * 
	 * @return 依頼者略称
	 */
	public String getSWK_IRAI_EMP_NAME_S() {
		return sWK_IRAI_EMP_NAME_S;
	}

	/**
	 * 依頼者略称
	 * 
	 * @param swk_irai_emp_name_s 依頼者略称
	 */
	public void setSWK_IRAI_EMP_NAME_S(String swk_irai_emp_name_s) {
		sWK_IRAI_EMP_NAME_S = swk_irai_emp_name_s;
	}

	/**
	 * 仮払計上部門名称
	 * 
	 * @return 仮払計上部門名称
	 */
	public String getSWK_KARI_DEP_NAME() {
		return sWK_KARI_DEP_NAME;
	}

	/**
	 * 仮払計上部門名称
	 * 
	 * @param swk_kari_dep_name 仮払計上部門名称
	 */
	public void setSWK_KARI_DEP_NAME(String swk_kari_dep_name) {
		sWK_KARI_DEP_NAME = swk_kari_dep_name;
	}

	/**
	 * 仮払計上部門略称
	 * 
	 * @return 仮払計上部門略称
	 */
	public String getSWK_KARI_DEP_NAME_S() {
		return sWK_KARI_DEP_NAME_S;
	}

	/**
	 * 仮払計上部門略称
	 * 
	 * @param swk_kari_dep_name_s 仮払計上部門略称
	 */
	public void setSWK_KARI_DEP_NAME_S(String swk_kari_dep_name_s) {
		sWK_KARI_DEP_NAME_S = swk_kari_dep_name_s;
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
	 * 承認者名称
	 * 
	 * @return 承認者名称
	 */
	public String getSWK_SYO_EMP_NAME() {
		return sWK_SYO_EMP_NAME;
	}

	/**
	 * 承認者名称
	 * 
	 * @param swk_syo_emp_name 承認者名称
	 */
	public void setSWK_SYO_EMP_NAME(String swk_syo_emp_name) {
		sWK_SYO_EMP_NAME = swk_syo_emp_name;
	}

	/**
	 * 承認者略称
	 * 
	 * @return 承認者略称
	 */
	public String getSWK_SYO_EMP_NAME_S() {
		return sWK_SYO_EMP_NAME_S;
	}

	/**
	 * 承認者略称
	 * 
	 * @param swk_syo_emp_name_s 承認者略称
	 */
	public void setSWK_SYO_EMP_NAME_S(String swk_syo_emp_name_s) {
		sWK_SYO_EMP_NAME_S = swk_syo_emp_name_s;
	}

	/**
	 * 承認権限グループコードを取得
	 * 
	 * @return sWK_APRV_GRP_CODE
	 */
	public String getSWK_APRV_GRP_CODE() {
		return SWK_APRV_GRP_CODE;
	}

	/**
	 * 承認権限グループコードをセットする
	 * 
	 * @param sWK_APRV_GRP_CODE 承認権限グループコード
	 */
	public void setSWK_APRV_GRP_CODE(String sWK_APRV_GRP_CODE) {
		SWK_APRV_GRP_CODE = sWK_APRV_GRP_CODE;
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
	 * 銀行口座名称
	 * 
	 * @return 銀行口座名称
	 */
	public String getSWK_CBK_NAME() {
		return sWK_CBK_NAME;
	}

	/**
	 * 銀行口座名称
	 * 
	 * @param swk_cbk_name 銀行口座名称
	 */
	public void setSWK_CBK_NAME(String swk_cbk_name) {
		sWK_CBK_NAME = swk_cbk_name;
	}

	/**
	 * 預金種目
	 * 
	 * @return 預金種目
	 */
	public int getSWK_CBK_YKN_KBN() {
		return sWK_CBK_YKN_KBN;
	}

	/**
	 * 預金種目
	 * 
	 * @param swk_cbk_ykn_kbn 預金種目
	 */
	public void setSWK_CBK_YKN_KBN(int swk_cbk_ykn_kbn) {
		sWK_CBK_YKN_KBN = swk_cbk_ykn_kbn;
	}

	/**
	 * 口座番号
	 * 
	 * @return 口座番号
	 */
	public String getSWK_CBK_YKN_NO() {
		return sWK_CBK_YKN_NO;
	}

	/**
	 * 口座番号
	 * 
	 * @param swk_cbk_ykn_no 口座番号
	 */
	public void setSWK_CBK_YKN_NO(String swk_cbk_ykn_no) {
		sWK_CBK_YKN_NO = swk_cbk_ykn_no;
	}

	/**
	 * 支店コード
	 * 
	 * @return 支店コード
	 */
	public String getSWK_CBK_STN_CODE() {
		return sWK_CBK_STN_CODE;
	}

	/**
	 * 支店コード
	 * 
	 * @param swk_cbk_stn_code 支店コード
	 */
	public void setSWK_CBK_STN_CODE(String swk_cbk_stn_code) {
		sWK_CBK_STN_CODE = swk_cbk_stn_code;
	}

	/**
	 * 受付部門名称
	 * 
	 * @return 受付部門名称
	 */
	public String getSWK_UKE_DEP_NAME() {
		return sWK_UKE_DEP_NAME;
	}

	/**
	 * 受付部門名称
	 * 
	 * @param swk_uke_dep_name 受付部門名称
	 */
	public void setSWK_UKE_DEP_NAME(String swk_uke_dep_name) {
		sWK_UKE_DEP_NAME = swk_uke_dep_name;
	}

	/**
	 * 受付部門略称
	 * 
	 * @return 受付部門略称
	 */
	public String getSWK_UKE_DEP_NAME_S() {
		return sWK_UKE_DEP_NAME_S;
	}

	/**
	 * 受付部門略称
	 * 
	 * @param swk_uke_dep_name_s 受付部門略称
	 */
	public void setSWK_UKE_DEP_NAME_S(String swk_uke_dep_name_s) {
		sWK_UKE_DEP_NAME_S = swk_uke_dep_name_s;
	}

	/**
	 * 伝票種別名称
	 * 
	 * @return 伝票種別名称
	 */
	public String getSWK_DEN_SYU_NAME() {
		return sWK_DEN_SYU_NAME;
	}

	/**
	 * 伝票種別名称
	 * 
	 * @param swk_den_syu_name 伝票種別名称
	 */
	public void setSWK_DEN_SYU_NAME(String swk_den_syu_name) {
		sWK_DEN_SYU_NAME = swk_den_syu_name;
	}

	/**
	 * 伝票種別略称
	 * 
	 * @return 伝票種別略称
	 */
	public String getSWK_DEN_SYU_NAME_S() {
		return sWK_DEN_SYU_NAME_S;
	}

	/**
	 * 伝票種別略称
	 * 
	 * @param swk_den_syu_name_s 伝票種別略称
	 */
	public void setSWK_DEN_SYU_NAME_S(String swk_den_syu_name_s) {
		sWK_DEN_SYU_NAME_S = swk_den_syu_name_s;
	}

	/**
	 * 支払方法名称
	 * 
	 * @return 支払方法名称
	 */
	public String getSWK_HOH_NAME() {
		return sWK_HOH_NAME;
	}

	/**
	 * 支払方法名称
	 * 
	 * @param swk_hoh_name 支払方法名称
	 */
	public void setSWK_HOH_NAME(String swk_hoh_name) {
		sWK_HOH_NAME = swk_hoh_name;
	}

	/**
	 * 支払内部コードの取得
	 * 
	 * @return sWK_HOH_NAI_CODE 支払内部コード
	 */
	public String getSWK_HOH_NAI_CODE() {
		return sWK_HOH_NAI_CODE;
	}

	/**
	 * 支払内部コードの設定
	 * 
	 * @param sWK_HOH_NAI_CODE 支払内部コード
	 */
	public void setSWK_HOH_NAI_CODE(String sWK_HOH_NAI_CODE) {
		this.sWK_HOH_NAI_CODE = sWK_HOH_NAI_CODE;
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
	 * @param swk_adj_kbn IFRS調整区分
	 */
	public void setSWK_ADJ_KBN(int swk_adj_kbn) {
		sWK_ADJ_KBN = swk_adj_kbn;
	}

	/**
	 * 銀行名
	 * 
	 * @return 銀行名
	 */
	public String getSWK_BNK_NAME_S() {
		return sWK_BNK_NAME_S;
	}

	/**
	 * 銀行名
	 * 
	 * @param swk_bnk_name_s 銀行名
	 */
	public void setSWK_BNK_NAME_S(String swk_bnk_name_s) {
		sWK_BNK_NAME_S = swk_bnk_name_s;
	}

	/**
	 * 支店名を取得する。
	 * 
	 * @return 支店名
	 */
	public String getSWK_BNK_STN_NAME_S() {
		return sWK_BNK_STN_NAME_S;
	}

	/**
	 * 支店名を設定する。
	 * 
	 * @param swk_bnk_stn_name_s 支店名
	 */
	public void setSWK_BNK_STN_NAME_S(String swk_bnk_stn_name_s) {
		sWK_BNK_STN_NAME_S = swk_bnk_stn_name_s;
	}

	/**
	 * 通貨小数点桁数を取得する。
	 * 
	 * @return 通貨小数点桁数
	 */
	public int getSWK_CUR_DEC_KETA() {
		return sWK_CUR_DEC_KETA;
	}

	/**
	 * 通貨小数点桁数を設定する。
	 * 
	 * @param swk_cur_dec_keta 通貨小数点桁数
	 */
	public void setSWK_CUR_DEC_KETA(int swk_cur_dec_keta) {
		sWK_CUR_DEC_KETA = swk_cur_dec_keta;
	}

	/**
	 * 英文銀行名の取得
	 * 
	 * @return sWK_TJK_GS_BNK_NAME 英文銀行名
	 */
	public String getSWK_TJK_GS_BNK_NAME() {
		return sWK_TJK_GS_BNK_NAME;
	}

	/**
	 * 英文銀行名の設定
	 * 
	 * @param sWK_TJK_GS_BNK_NAME 英文銀行名
	 */
	public void setSWK_TJK_GS_BNK_NAME(String sWK_TJK_GS_BNK_NAME) {
		this.sWK_TJK_GS_BNK_NAME = sWK_TJK_GS_BNK_NAME;
	}

	/**
	 * 英文支店名の取得
	 * 
	 * @return sWK_TJK_GS_STN_NAME 英文支店名
	 */
	public String getSWK_TJK_GS_STN_NAME() {
		return sWK_TJK_GS_STN_NAME;
	}

	/**
	 * 英文支店名の設定
	 * 
	 * @param sWK_TJK_GS_STN_NAME 英文支店名
	 */
	public void setSWK_TJK_GS_STN_NAME(String sWK_TJK_GS_STN_NAME) {
		this.sWK_TJK_GS_STN_NAME = sWK_TJK_GS_STN_NAME;
	}

	/**
	 * 取引先条件銀行名(支払銀行名)
	 * 
	 * @return 取引先条件銀行名(支払銀行名)
	 */
	public String getSWK_TJK_BNK_NAME_S() {
		return sWK_TJK_BNK_NAME_S;
	}

	/**
	 * 取引先条件銀行名(支払銀行名)
	 * 
	 * @param swk_tjk_bnk_name_s 取引先条件銀行名(支払銀行名)
	 */
	public void setSWK_TJK_BNK_NAME_S(String swk_tjk_bnk_name_s) {
		sWK_TJK_BNK_NAME_S = swk_tjk_bnk_name_s;
	}

	/**
	 * 取引先条件銀行口座名(支払銀行支店名)
	 * 
	 * @return 取引先条件銀行口座名(支払銀行支店名)
	 */
	public String getSWK_TJK_BNK_STN_NAME_S() {
		return sWK_TJK_BNK_STN_NAME_S;
	}

	/**
	 * 取引先条件銀行口座名(支払銀行支店名)
	 * 
	 * @param swk_tjk_bnk_stn_name_s 取引先条件銀行口座名(支払銀行支店名)
	 */
	public void setSWK_TJK_BNK_STN_NAME_S(String swk_tjk_bnk_stn_name_s) {
		sWK_TJK_BNK_STN_NAME_S = swk_tjk_bnk_stn_name_s;
	}

	/**
	 * 伝票種別検索名称
	 * 
	 * @return 伝票種別検索名称
	 */
	public String getSWK_DEN_SYU_NAME_K() {
		return sWK_DEN_SYU_NAME_K;
	}

	/**
	 * 伝票種別検索名称
	 * 
	 * @param swk_den_syu_name_k 伝票種別検索名称
	 */
	public void setSWK_DEN_SYU_NAME_K(String swk_den_syu_name_k) {
		sWK_DEN_SYU_NAME_K = swk_den_syu_name_k;
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
	 * @return 基軸通貨小数点桁数
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
	 * 保留区分
	 * 
	 * @return true:保留
	 */
	public boolean isSuspended() {
		return BooleanUtil.toBoolean(sWK_HORYU_KBN);
	}

	/**
	 * 保留区分
	 * 
	 * @param b true:保留
	 */
	public void setSuspended(boolean b) {
		sWK_HORYU_KBN = BooleanUtil.toInt(b);
	}

	/**
	 * currencyを取得する。
	 * 
	 * @return Currency currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * currencyを設定する。
	 * 
	 * @param currency
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;

		if (currency == null) {
			sWK_CUR_CODE = null;
			sWK_CUR_DEC_KETA = 0;

		} else {
			sWK_CUR_CODE = currency.getCode();
			sWK_CUR_DEC_KETA = currency.getDecimalPoint();
		}
	}

	/**
	 * customerを取得する。
	 * 
	 * @return Customer customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * customerを設定する。
	 * 
	 * @param customer
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
	 * paymentMethodを取得する。
	 * 
	 * @return PaymentMethod paymentMethod
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * paymentMethodを設定する。
	 * 
	 * @param paymentMethod
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;

		if (paymentMethod == null) {
			sWK_HOH_CODE = null;
			sWK_HOH_NAME = null;
			sWK_HOH_NAI_CODE = null;

		} else {
			sWK_HOH_CODE = paymentMethod.getCode();
			sWK_HOH_NAME = paymentMethod.getName();
			sWK_HOH_NAI_CODE = paymentMethod.getPaymentKind() == null ? null : paymentMethod.getPaymentKind().value;
		}
	}

	/**
	 * paymentSettingを取得する。
	 * 
	 * @return PaymentSetting paymentSetting
	 */
	public PaymentSetting getPaymentSetting() {
		return paymentSetting;
	}

	/**
	 * paymentSettingを設定する。
	 * 
	 * @param paymentSetting
	 */
	public void setPaymentSetting(PaymentSetting paymentSetting) {
		this.paymentSetting = paymentSetting;

		if (paymentSetting == null) {
			sWK_TJK_CODE = null;
			sWK_TJK_GS_BNK_NAME = null;
			sWK_TJK_GS_STN_NAME = null;
			sWK_TJK_BNK_NAME_S = null;
			sWK_TJK_BNK_STN_NAME_S = null;

		} else {
			sWK_TJK_CODE = paymentSetting.getCode();
			sWK_TJK_GS_BNK_NAME = paymentSetting.getSendBankName(); // 被仕向け・英文銀行
			sWK_TJK_GS_STN_NAME = paymentSetting.getSendBranchName(); // 被仕向け・英文支店
			sWK_TJK_BNK_NAME_S = paymentSetting.getBankName();
			sWK_TJK_BNK_STN_NAME_S = paymentSetting.getBranchName();
		}
	}

	/**
	 * bankAccountを取得する。
	 * 
	 * @return BankAccount bankAccount
	 */
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	/**
	 * bankAccountを設定する。
	 * 
	 * @param bankAccount
	 */
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;

		if (bankAccount == null) {
			sWK_CBK_CODE = null;
			sWK_CBK_NAME = null;
			sWK_CBK_STN_CODE = null;
			sWK_BNK_NAME_S = null;
			sWK_BNK_STN_NAME_S = null;

		} else {
			sWK_CBK_CODE = bankAccount.getCode();
			sWK_CBK_NAME = bankAccount.getName(); // 銀行口座名称
			sWK_CBK_STN_CODE = bankAccount.getBranchCode(); // 支店コード
			sWK_BNK_NAME_S = bankAccount.getBankName(); // 銀行名
			sWK_BNK_STN_NAME_S = bankAccount.getBranchName(); // 支店名
		}
	}

	/**
	 * 部門
	 * 
	 * @return 部門
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * 部門
	 * 
	 * @param department 部門
	 */
	public void setDepartment(Department department) {
		this.department = department;

		if (department == null) {
			sWK_DEP_CODE = null;
			sWK_DEP_NAME = null;
			sWK_DEP_NAME_S = null;

		} else {
			sWK_DEP_CODE = department.getCode();
			sWK_DEP_NAME = department.getName();
			sWK_DEP_NAME_S = department.getNames();
		}
	}

	/**
	 * 科目
	 * 
	 * @return 科目
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * 科目
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
		}
	}

	/**
	 * 支払区分
	 * 
	 * @return 支払区分
	 */
	public PaymentDateType getPaymentType() {
		return PaymentDateType.getPaymentDateType(sWK_SIHA_KBN);
	}

	/**
	 * 支払区分
	 * 
	 * @param paymentDateType 支払区分
	 */
	public void setPaymentType(PaymentDateType paymentDateType) {
		sWK_SIHA_KBN = paymentDateType.value;
	}

	/**
	 * billTypeを取得する。
	 * 
	 * @return BillType billType
	 */
	public BillType getBillType() {
		return billType;
	}

	/**
	 * billTypeを設定する。
	 * 
	 * @param billType
	 */
	public void setBillType(BillType billType) {
		this.billType = billType;

		if (billType == null) {
			this.sWK_SEI_KBN = null;

		} else {
			this.sWK_SEI_KBN = billType.getCode();
		}
	}

	/**
	 * 帳簿区分を設定する.
	 * 
	 * @param accountBook 帳簿区分
	 */
	public void setAccountBook(AccountBook accountBook) {
		sWK_ADJ_KBN = accountBook.value;
	}

	/**
	 * 取引先条件マスタの被仕向口座預金種目
	 * 
	 * @return 取引先条件マスタの被仕向口座預金種目
	 */
	public int getSWK_TJK_YKN_KBN() {
		return sWK_TJK_YKN_KBN;
	}

	/**
	 * 取引先条件マスタの被仕向口座預金種目
	 * 
	 * @param swk_tjk_ykn_kbn 取引先条件マスタの被仕向口座預金種目
	 */
	public void setSWK_TJK_YKN_KBN(int swk_tjk_ykn_kbn) {
		sWK_TJK_YKN_KBN = swk_tjk_ykn_kbn;
	}

	/**
	 * 取引先条件マスタの被仕向口座番号
	 * 
	 * @return 取引先条件マスタの被仕向口座番号
	 */
	public String getSWK_TJK_YKN_NO() {
		return sWK_TJK_YKN_NO;
	}

	/**
	 * 取引先条件マスタの被仕向口座番号
	 * 
	 * @param swk_tjk_ykn_no 取引先条件マスタの被仕向口座番号
	 */
	public void setSWK_TJK_YKN_NO(String swk_tjk_ykn_no) {
		sWK_TJK_YKN_NO = swk_tjk_ykn_no;
	}

	/**
	 * 取引先条件マスタの被仕向口座名義カナ
	 * 
	 * @return sWK_TJK_YKN_KANA
	 */
	public String getSWK_TJK_YKN_KANA() {
		return sWK_TJK_YKN_KANA;
	}

	/**
	 * @param swk_tjk_ykn_kana
	 */
	public void setSWK_TJK_YKN_KANA(String swk_tjk_ykn_kana) {
		sWK_TJK_YKN_KANA = swk_tjk_ykn_kana;
	}

	/**
	 * 港費概算取消伝票番号を設定する
	 * 
	 * @param sWK_EST_CANCEL_DEN_NO
	 */
	public void setSWK_EST_CANCEL_DEN_NO(String sWK_EST_CANCEL_DEN_NO) {
		this.sWK_EST_CANCEL_DEN_NO = sWK_EST_CANCEL_DEN_NO;
	}

	/**
	 * 港費概算取消伝票番号を取得する
	 * 
	 * @return sWK_EST_CANCEL_DEN_NO
	 */
	public String getSWK_EST_CANCEL_DEN_NO() {
		return this.sWK_EST_CANCEL_DEN_NO;
	}

	/**
	 * 港費元概算伝票番号を設定する
	 * 
	 * @param sWK_BASE_EST_DEN_NO
	 */
	public void setSWK_BASE_EST_DEN_NO(String sWK_BASE_EST_DEN_NO) {
		this.sWK_BASE_EST_DEN_NO = sWK_BASE_EST_DEN_NO;
	}

	/**
	 * 港費元概算伝票番号を取得する
	 * 
	 * @return sWK_BASE_EST_DEN_NO
	 */
	public String getSWK_BASE_EST_DEN_NO() {
		return this.sWK_BASE_EST_DEN_NO;
	}

	/**
	 * 伝票種別のセット
	 * 
	 * @param slipType 伝票種別
	 */
	public void setSlipType(SlipType slipType) {

		if (slipType == null) {
			this.sWK_SYS_KBN = null;
			this.sWK_DEN_SYU = null;
			this.sWK_DEN_SYU_NAME = null;
			this.sWK_DEN_SYU_NAME_S = null;
			this.sWK_DEN_SYU_NAME_K = null;
			this.sWK_DATA_KBN = null;

		} else {
			this.sWK_SYS_KBN = slipType.getSystemDiv();
			this.sWK_DEN_SYU = slipType.getCode();
			this.sWK_DEN_SYU_NAME = slipType.getName();
			this.sWK_DEN_SYU_NAME_S = slipType.getNames();
			this.sWK_DEN_SYU_NAME_K = slipType.getNamek();
			this.sWK_DATA_KBN = slipType.getDataType();
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
	 * 社員
	 * 
	 * @param employee 社員
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;

		if (employee == null) {
			this.sWK_EMP_CODE = null;
			this.sWK_EMP_NAME = null;
			this.sWK_EMP_NAME_S = null;

		} else {
			this.sWK_EMP_CODE = employee.getCode();
			this.sWK_EMP_NAME = employee.getName();
			this.sWK_EMP_NAME_S = employee.getNames();
		}
	}

	/**
	 * 摘要
	 * 
	 * @return 摘要
	 */
	public Remark getRemark() {
		return remark;
	}

	/**
	 * 摘要
	 * 
	 * @param remark 摘要
	 */
	public void setRemark(Remark remark) {
		this.remark = remark;

		if (remark == null) {
			this.sWK_TEK_CODE = null;

		} else {
			this.sWK_TEK_CODE = remark.getCode();
		}
	}

	/**
	 * 添付の取得
	 * 
	 * @return attachments 添付
	 */
	public List<SWK_ATTACH> getAttachments() {
		return attachments;
	}

	/**
	 * 添付の設定
	 * 
	 * @param attachments 添付
	 */
	public void setAttachments(List<SWK_ATTACH> attachments) {
		this.attachments = attachments;
	}

	/**
	 * 添付情報があるかどうかの取得
	 * 
	 * @return isExistAttachment 添付情報があるかどうか true：ある
	 */
	public boolean isExistAttachment() {
		return isExistAttachment;
	}

	/**
	 * 添付情報があるかどうかの設定
	 * 
	 * @param isExistAttachment 添付情報があるかどうか true：ある
	 */
	public void setExistAttachment(boolean isExistAttachment) {
		this.isExistAttachment = isExistAttachment;
	}

	/**
	 * 英文銀行住所の取得
	 * 
	 * @return sWK_TJK_GS_BNK_ADR 英文銀行住所
	 */
	public String getSWK_TJK_GS_BNK_ADR() {
		return sWK_TJK_GS_BNK_ADR;
	}

	/**
	 * 英文銀行住所の設定
	 * 
	 * @param sWK_TJK_GS_BNK_ADR 英文銀行住所
	 */
	public void setSWK_TJK_GS_BNK_ADR(String sWK_TJK_GS_BNK_ADR) {
		this.sWK_TJK_GS_BNK_ADR = sWK_TJK_GS_BNK_ADR;
	}

	/** 付箋 */
	protected List<SWK_TAG> swkTags = null;

	/**
	 * 付箋 の取得
	 * 
	 * @return tag 付箋
	 */
	public List<SWK_TAG> getSwkTags() {
		return swkTags;
	}

	/**
	 * 付箋 の設定
	 * 
	 * @param swkTags 付箋
	 */
	public void setSwkTags(List<SWK_TAG> swkTags) {
		this.swkTags = swkTags;
	}

	/**
	 * ワークフロー承認コントロールを取得
	 * 
	 * @return syoCtl
	 */
	public SWK_SYO_CTL getSyoCtl() {
		return syoCtl;
	}

	/**
	 * ワークフロー承認コントロールをセットする
	 * 
	 * @param syoCtl syoCtl
	 */
	public void setSyoCtl(SWK_SYO_CTL syoCtl) {
		this.syoCtl = syoCtl;
	}

	/**
	 * 承認権限ロールグループ を取得
	 * 
	 * @return 承認権限ロールグループ
	 */
	public AprvRoleGroup getAprRoleGroup() {
		return aprRoleGroup;
	}

	/**
	 * 承認権限ロールグループ をセットする
	 * 
	 * @param aprRoleGroup 承認権限ロールグループ
	 */
	public void setAprRoleGroup(AprvRoleGroup aprRoleGroup) {
		this.aprRoleGroup = aprRoleGroup;
	}

}

package jp.co.ais.trans2.model.payment;

import java.math.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 支払方針情報
 */
public class PaymentPolicy extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 定時支払（１日） */
	protected int sHH_SIHA_1 = 0;

	/** 定時支払（５日） */
	protected int sHH_SIHA_5 = 0;

	/** 定時支払（10日） */
	protected int sHH_SIHA_10 = 0;

	/** 定時支払（15日） */
	protected int sHH_SIHA_15 = 0;

	/** 定時支払（20日） */
	protected int sHH_SIHA_20 = 0;

	/** 定時支払（25日） */
	protected int sHH_SIHA_25 = 0;

	/** 定時支払（末日） */
	protected int sHH_SIHA_30 = 0;

	/** 銀行休日区分（1日） */
	protected int sHH_BNK_KBN_1 = 0;

	/** 銀行休日区分（5日） */
	protected int sHH_BNK_KBN_5 = 0;

	/** 銀行休日区分（10日） */
	protected int sHH_BNK_KBN_10 = 0;

	/** 銀行休日区分（15日） */
	protected int sHH_BNK_KBN_15 = 0;

	/** 銀行休日区分（20日） */
	protected int sHH_BNK_KBN_20 = 0;

	/** 銀行休日区分（25日） */
	protected int sHH_BNK_KBN_25 = 0;

	/** 銀行休日区分（末日） */
	protected int sHH_BNK_KBN_30 = 0;

	/** 支払下限額 */
	protected BigDecimal sHH_SIHA_MIN = null;

	/** 振込手数料下限額 */
	protected BigDecimal sHH_FURI_MIN = null;

	/** 手数料科目コード */
	protected String sHH_TESU_KMK_CODE = null;

	/** 手数料補助科目コード */
	protected String sHH_TESU_HKM_CODE = null;

	/** 手数料内訳科目コード */
	protected String sHH_TESU_UKM_CODE = null;

	/** 手数料計上部門コード */
	protected String sHH_TESU_DEP_CODE = null;

	/** 手数料消費税コード */
	protected String sHH_TESU_ZEI_CODE = null;

	/** 外国送金作成フラグ */
	protected String sHH_GS_PRINT_KBN = null;

	/** 請求書番号フラグ */
	protected String sHH_SEI_NO_KBN = null;

	/** 社外支払FBデータ出力先 */
	protected String fbOutputPath;

	/** 外国送金依頼データ出力先 */
	protected String remmitanceOutputPath;

	/** 社員支払FBデータ出力先 */
	protected String employeeFbOutputPath;

	/**
	 * 社員支払FBデータ出力先
	 * 
	 * @return fb FBデータ出力先を戻します。
	 */
	public String getEmployeeFbOutputPath() {
		return this.employeeFbOutputPath;
	}

	/**
	 * 社員支払FBデータ出力先
	 * 
	 * @param path FBデータ出力先を設定します。
	 */
	public void setEmployeeFbOutputPath(String path) {
		this.employeeFbOutputPath = path;
	}

	/**
	 * @return companyCode 会社コードを戻します。
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @param companyCode 会社コードを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 社外支払FBデータ出力先
	 * 
	 * @return 社外支払FBデータ出力先
	 */
	public String getFbOutputPath() {
		return this.fbOutputPath;
	}

	/**
	 * 社外支払FBデータ出力先
	 * 
	 * @param fbOutputPath 社外支払FBデータ出力先
	 */
	public void setFbOutputPath(String fbOutputPath) {
		this.fbOutputPath = fbOutputPath;
	}

	/**
	 * 外国送金依頼データ出力先
	 * 
	 * @return 外国送金依頼データ出力先
	 */
	public String getRemmitanceOutputPath() {
		return this.remmitanceOutputPath;
	}

	/**
	 * 外国送金依頼データ出力先
	 * 
	 * @param remmitanceOutputPath 外国送金依頼データ出力先
	 */
	public void setRemmitanceOutputPath(String remmitanceOutputPath) {
		this.remmitanceOutputPath = remmitanceOutputPath;
	}

	/**
	 * 定時支払（１日）の取得
	 * 
	 * @return sHH_SIHA_1 定時支払（１日）
	 */
	public int getSHH_SIHA_1() {
		return sHH_SIHA_1;
	}

	/**
	 * 定時支払（１日）の設定
	 * 
	 * @param sHH_SIHA_1 定時支払（１日）
	 */
	public void setSHH_SIHA_1(int sHH_SIHA_1) {
		this.sHH_SIHA_1 = sHH_SIHA_1;
	}

	/**
	 * 定時支払（５日）の取得
	 * 
	 * @return sHH_SIHA_5 定時支払（５日）
	 */
	public int getSHH_SIHA_5() {
		return sHH_SIHA_5;
	}

	/**
	 * 定時支払（５日）の設定
	 * 
	 * @param sHH_SIHA_5 定時支払（５日）
	 */
	public void setSHH_SIHA_5(int sHH_SIHA_5) {
		this.sHH_SIHA_5 = sHH_SIHA_5;
	}

	/**
	 * 定時支払（10日）の取得
	 * 
	 * @return sHH_SIHA_10 定時支払（10日）
	 */
	public int getSHH_SIHA_10() {
		return sHH_SIHA_10;
	}

	/**
	 * 定時支払（10日）の設定
	 * 
	 * @param sHH_SIHA_10 定時支払（10日）
	 */
	public void setSHH_SIHA_10(int sHH_SIHA_10) {
		this.sHH_SIHA_10 = sHH_SIHA_10;
	}

	/**
	 * 定時支払（15日）の取得
	 * 
	 * @return sHH_SIHA_15 定時支払（15日）
	 */
	public int getSHH_SIHA_15() {
		return sHH_SIHA_15;
	}

	/**
	 * 定時支払（15日）の設定
	 * 
	 * @param sHH_SIHA_15 定時支払（15日）
	 */
	public void setSHH_SIHA_15(int sHH_SIHA_15) {
		this.sHH_SIHA_15 = sHH_SIHA_15;
	}

	/**
	 * 定時支払（20日）の取得
	 * 
	 * @return sHH_SIHA_20 定時支払（20日）
	 */
	public int getSHH_SIHA_20() {
		return sHH_SIHA_20;
	}

	/**
	 * 定時支払（20日）の設定
	 * 
	 * @param sHH_SIHA_20 定時支払（20日）
	 */
	public void setSHH_SIHA_20(int sHH_SIHA_20) {
		this.sHH_SIHA_20 = sHH_SIHA_20;
	}

	/**
	 * 定時支払（25日）の取得
	 * 
	 * @return sHH_SIHA_25 定時支払（25日）
	 */
	public int getSHH_SIHA_25() {
		return sHH_SIHA_25;
	}

	/**
	 * 定時支払（25日）の設定
	 * 
	 * @param sHH_SIHA_25 定時支払（25日）
	 */
	public void setSHH_SIHA_25(int sHH_SIHA_25) {
		this.sHH_SIHA_25 = sHH_SIHA_25;
	}

	/**
	 * 定時支払（末日）の取得
	 * 
	 * @return sHH_SIHA_30 定時支払（末日）
	 */
	public int getSHH_SIHA_30() {
		return sHH_SIHA_30;
	}

	/**
	 * 定時支払（末日）の設定
	 * 
	 * @param sHH_SIHA_30 定時支払（末日）
	 */
	public void setSHH_SIHA_30(int sHH_SIHA_30) {
		this.sHH_SIHA_30 = sHH_SIHA_30;
	}

	/**
	 * 銀行休日区分（1日）の取得
	 * 
	 * @return sHH_BNK_KBN_1 銀行休日区分（1日）
	 */
	public int getSHH_BNK_KBN_1() {
		return sHH_BNK_KBN_1;
	}

	/**
	 * 銀行休日区分（1日）の設定
	 * 
	 * @param sHH_BNK_KBN_1 銀行休日区分（1日）
	 */
	public void setSHH_BNK_KBN_1(int sHH_BNK_KBN_1) {
		this.sHH_BNK_KBN_1 = sHH_BNK_KBN_1;
	}

	/**
	 * 銀行休日区分（5日）の取得
	 * 
	 * @return sHH_BNK_KBN_5 銀行休日区分（5日）
	 */
	public int getSHH_BNK_KBN_5() {
		return sHH_BNK_KBN_5;
	}

	/**
	 * 銀行休日区分（5日）の設定
	 * 
	 * @param sHH_BNK_KBN_5 銀行休日区分（5日）
	 */
	public void setSHH_BNK_KBN_5(int sHH_BNK_KBN_5) {
		this.sHH_BNK_KBN_5 = sHH_BNK_KBN_5;
	}

	/**
	 * 銀行休日区分（10日）の取得
	 * 
	 * @return sHH_BNK_KBN_10 銀行休日区分（10日）
	 */
	public int getSHH_BNK_KBN_10() {
		return sHH_BNK_KBN_10;
	}

	/**
	 * 銀行休日区分（10日）の設定
	 * 
	 * @param sHH_BNK_KBN_10 銀行休日区分（10日）
	 */
	public void setSHH_BNK_KBN_10(int sHH_BNK_KBN_10) {
		this.sHH_BNK_KBN_10 = sHH_BNK_KBN_10;
	}

	/**
	 * 銀行休日区分（15日）の取得
	 * 
	 * @return sHH_BNK_KBN_15 銀行休日区分（15日）
	 */
	public int getSHH_BNK_KBN_15() {
		return sHH_BNK_KBN_15;
	}

	/**
	 * 銀行休日区分（15日）の設定
	 * 
	 * @param sHH_BNK_KBN_15 銀行休日区分（15日）
	 */
	public void setSHH_BNK_KBN_15(int sHH_BNK_KBN_15) {
		this.sHH_BNK_KBN_15 = sHH_BNK_KBN_15;
	}

	/**
	 * 銀行休日区分（20日）の取得
	 * 
	 * @return sHH_BNK_KBN_20 銀行休日区分（20日）
	 */
	public int getSHH_BNK_KBN_20() {
		return sHH_BNK_KBN_20;
	}

	/**
	 * 銀行休日区分（20日）の設定
	 * 
	 * @param sHH_BNK_KBN_20 銀行休日区分（20日）
	 */
	public void setSHH_BNK_KBN_20(int sHH_BNK_KBN_20) {
		this.sHH_BNK_KBN_20 = sHH_BNK_KBN_20;
	}

	/**
	 * 銀行休日区分（25日）の取得
	 * 
	 * @return sHH_BNK_KBN_25 銀行休日区分（25日）
	 */
	public int getSHH_BNK_KBN_25() {
		return sHH_BNK_KBN_25;
	}

	/**
	 * 銀行休日区分（25日）の設定
	 * 
	 * @param sHH_BNK_KBN_25 銀行休日区分（25日）
	 */
	public void setSHH_BNK_KBN_25(int sHH_BNK_KBN_25) {
		this.sHH_BNK_KBN_25 = sHH_BNK_KBN_25;
	}

	/**
	 * 銀行休日区分（末日）の取得
	 * 
	 * @return sHH_BNK_KBN_30 銀行休日区分（末日）
	 */
	public int getSHH_BNK_KBN_30() {
		return sHH_BNK_KBN_30;
	}

	/**
	 * 銀行休日区分（末日）の設定
	 * 
	 * @param sHH_BNK_KBN_30 銀行休日区分（末日）
	 */
	public void setSHH_BNK_KBN_30(int sHH_BNK_KBN_30) {
		this.sHH_BNK_KBN_30 = sHH_BNK_KBN_30;
	}

	/**
	 * 支払下限額の取得
	 * 
	 * @return sHH_SIHA_MIN 支払下限額
	 */
	public BigDecimal getSHH_SIHA_MIN() {
		return sHH_SIHA_MIN;
	}

	/**
	 * 支払下限額の設定
	 * 
	 * @param sHH_SIHA_MIN 支払下限額
	 */
	public void setSHH_SIHA_MIN(BigDecimal sHH_SIHA_MIN) {
		this.sHH_SIHA_MIN = sHH_SIHA_MIN;
	}

	/**
	 * 振込手数料下限額の取得
	 * 
	 * @return sHH_FURI_MIN 振込手数料下限額
	 */
	public BigDecimal getSHH_FURI_MIN() {
		return sHH_FURI_MIN;
	}

	/**
	 * 振込手数料下限額の設定
	 * 
	 * @param sHH_FURI_MIN 振込手数料下限額
	 */
	public void setSHH_FURI_MIN(BigDecimal sHH_FURI_MIN) {
		this.sHH_FURI_MIN = sHH_FURI_MIN;
	}

	/**
	 * 手数料科目コードの取得
	 * 
	 * @return sHH_TESU_KMK_CODE 手数料科目コード
	 */
	public String getSHH_TESU_KMK_CODE() {
		return sHH_TESU_KMK_CODE;
	}

	/**
	 * 手数料科目コードの設定
	 * 
	 * @param sHH_TESU_KMK_CODE 手数料科目コード
	 */
	public void setSHH_TESU_KMK_CODE(String sHH_TESU_KMK_CODE) {
		this.sHH_TESU_KMK_CODE = sHH_TESU_KMK_CODE;
	}

	/**
	 * 手数料補助科目コードの取得
	 * 
	 * @return sHH_TESU_HKM_CODE 手数料補助科目コード
	 */
	public String getSHH_TESU_HKM_CODE() {
		return sHH_TESU_HKM_CODE;
	}

	/**
	 * 手数料補助科目コードの設定
	 * 
	 * @param sHH_TESU_HKM_CODE 手数料補助科目コード
	 */
	public void setSHH_TESU_HKM_CODE(String sHH_TESU_HKM_CODE) {
		this.sHH_TESU_HKM_CODE = sHH_TESU_HKM_CODE;
	}

	/**
	 * 手数料内訳科目コードの取得
	 * 
	 * @return sHH_TESU_UKM_CODE 手数料内訳科目コード
	 */
	public String getSHH_TESU_UKM_CODE() {
		return sHH_TESU_UKM_CODE;
	}

	/**
	 * 手数料内訳科目コードの設定
	 * 
	 * @param sHH_TESU_UKM_CODE 手数料内訳科目コード
	 */
	public void setSHH_TESU_UKM_CODE(String sHH_TESU_UKM_CODE) {
		this.sHH_TESU_UKM_CODE = sHH_TESU_UKM_CODE;
	}

	/**
	 * 手数料計上部門コードの取得
	 * 
	 * @return sHH_TESU_DEP_CODE 手数料計上部門コード
	 */
	public String getSHH_TESU_DEP_CODE() {
		return sHH_TESU_DEP_CODE;
	}

	/**
	 * 手数料計上部門コードの設定
	 * 
	 * @param sHH_TESU_DEP_CODE 手数料計上部門コード
	 */
	public void setSHH_TESU_DEP_CODE(String sHH_TESU_DEP_CODE) {
		this.sHH_TESU_DEP_CODE = sHH_TESU_DEP_CODE;
	}

	/**
	 * 手数料消費税コードの取得
	 * 
	 * @return sHH_TESU_ZEI_CODE 手数料消費税コード
	 */
	public String getSHH_TESU_ZEI_CODE() {
		return sHH_TESU_ZEI_CODE;
	}

	/**
	 * 手数料消費税コードの設定
	 * 
	 * @param sHH_TESU_ZEI_CODE 手数料消費税コード
	 */
	public void setSHH_TESU_ZEI_CODE(String sHH_TESU_ZEI_CODE) {
		this.sHH_TESU_ZEI_CODE = sHH_TESU_ZEI_CODE;
	}

	/**
	 * 外国送金作成フラグの取得
	 * 
	 * @return sHH_GS_PRINT_KBN 外国送金作成フラグ
	 */
	public String getSHH_GS_PRINT_KBN() {
		return sHH_GS_PRINT_KBN;
	}

	/**
	 * 外国送金作成フラグの設定
	 * 
	 * @param sHH_GS_PRINT_KBN 外国送金作成フラグ
	 */
	public void setSHH_GS_PRINT_KBN(String sHH_GS_PRINT_KBN) {
		this.sHH_GS_PRINT_KBN = sHH_GS_PRINT_KBN;
	}

	/**
	 * 請求書番号フラグの取得
	 * 
	 * @return sHH_SEI_NO_KBN 請求書番号フラグ
	 */
	public String getSHH_SEI_NO_KBN() {
		return sHH_SEI_NO_KBN;
	}

	/**
	 * 請求書番号フラグの設定
	 * 
	 * @param sHH_SEI_NO_KBN 請求書番号フラグ
	 */
	public void setSHH_SEI_NO_KBN(String sHH_SEI_NO_KBN) {
		this.sHH_SEI_NO_KBN = sHH_SEI_NO_KBN;
	}

}
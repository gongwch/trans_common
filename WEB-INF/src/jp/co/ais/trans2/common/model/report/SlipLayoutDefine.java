package jp.co.ais.trans2.common.model.report;

import jp.co.ais.trans2.define.*;

/**
 * 伝票レイアウトの定義
 * 
 * @author AIS
 */
public class SlipLayoutDefine {

	/** 伝票種別コード */
	protected String slipType = null;

	/** 伝票の種類（データ区分） */
	protected SlipKind slipKind = null;

	/** データ区分のコード */
	protected String dataDivision = null;

	/** ヘッダー単語定義 */

	/** ヘッダー：伝票日付 */
	protected String headerSlipDateWord = "C00599";

	/** ヘッダー：入力部門 */
	protected String headerDepartmentNameWord = "C01280";

	/** ヘッダー：入力者 */
	protected String headerEmployeeNameWord = "C01278";

	/** ヘッダー：伝票番号 */
	protected String headerSlipNoWord = "C00605";

	/** ヘッダー：修正 */
	protected String headerUpdateWord = "C01760";

	/** ヘッダー：回 */
	protected String headerUpdateCountWord = "C01761";

	/** ヘッダー：証憑番号 */
	protected String headerVoucherNoWord = "C01178";

	/** ヘッダー：伝票摘要 */
	protected String headerSlipRemarkWord = "C00569";

	/** ヘッダー：基軸通貨 */
	protected String headerKeyCurrencyWord = "C00907";

	/** ヘッダー：帳簿区分 */
	protected String headerAccountBookNameWord = "C10961";

	/** ヘッダー：承認印1 */
	protected String headerAcceptStamp1Word = "C01762";

	/** ヘッダー：承認印2 */
	protected String headerAcceptStamp2Word = "C11436";

	/** ヘッダー：承認印3 */
	protected String headerAcceptStamp3Word = "C11437";

	/** 明細単語定義 */

	/** 明細：借方金額 */
	protected String detailDrAmountWord = "C01557";

	/** 明細：貸方金額 */
	protected String detailCrAmountWord = "C01559";

	/** 明細：行摘要 */
	protected String detailRowRemarkWord = "C00119";

	/** 明細：計上部門/取引先/社員 */
	protected String detailCustomerWord = "C11071";

	/** 明細：通貨 */
	protected String detailCurrencyWord = "C00371";

	/** 明細：発生日/税 */
	protected String detailTaxWord = "C11435";

	/** 明細：管理1/2/3 */
	protected String detailManagement1To3Word = "C11072";

	/** 明細：管理4/5/6 */
	protected String detailManagement4To6Word = "C11073";

	/** 明細：非会計明細1/2/3 */
	protected String detailNonAccountWord = "C01763";

	/** 明細：合計 */
	protected String detailSumWord = "C00165";

	/** 明細：外貨計1 */
	protected String detailForeign1Word = "C01764";

	/** 明細：外貨計2 */
	protected String detailForeign2Word = "C01765";

	/** 明細：外貨計3 */
	protected String detailForeign3Word = "C11608";

	/** 明細：外貨計4 */
	protected String detailForeign4Word = "C11609";

	/** 列幅定義 */

	/** 行高さ：変更によるレイアウト調整必要 */
	protected double rowHeight = 0.50d;

	/** ヘッダータイトル幅 */
	protected double headerTitleWidth = 2.5d;

	/** ヘッダー部門幅 */
	protected double headerDepartmentWidth = 9.0d;

	/** 空行幅 */
	protected double detailRestWidth = 5.85d;

	/** 行番号幅 */
	protected double detailRowNoWidth = 0.7d;

	/** 科目幅 */
	protected double detailItemWidth = 4.6d;

	/** 金額幅 */
	protected double detailAmountWidth = 3.10d;

	/** 通貨幅 */
	protected double detailCurrencyWidth = 1.8d;

	/** 摘要幅 */
	protected double detailRemarkWidth = 4.5d;

	/** 取引先幅 */
	protected double detailCustomerWidth = 3.6d;

	/** 管理1～3幅 */
	protected double detailManagement1To3Width = 2.6d;

	/** 管理4～6幅 */
	protected double detailManagement4To6Width = 2.6d;

	/** 非会計明細幅 */
	protected double detailNonAccountWidth = 2.25d;

	/** 桁数定義 */

	/** 帳簿区分桁数 */
	protected int headerAccountBookNameLength = 12;

	/** 修正回数桁数 */
	protected int headerUpdateCountLength = 3;

	/** 承認印1桁数 */
	protected int headerAcceptStamp1Length = 6;

	/** 承認印2桁数 */
	protected int headerAcceptStamp2Length = 6;

	/** 承認印3桁数 */
	protected int headerAcceptStamp3Length = 6;

	/** 科目桁数 */
	protected int detailItemLength = 28;

	/** 消費税桁数 */
	protected int detailTaxLength = 10;

	/** 摘要桁数 */
	protected int detailRemarkLength = 27;

	/** 取引先桁数 */
	protected int detailCustomerLength = 21;

	/** 管理1～3桁数 */
	protected int detailManagement1To3Length = 15;

	/** 管理4～6桁数 */
	protected int detailManagement4To6Length = 15;

	/** 非会計明細桁数 */
	protected int detailNonAccountLength = 16;

	/** ヘッダー：入力部門桁数 */
	protected int headerDepartmentNameLength = 55;

	/** ヘッダー追加分幅定義 */
	/** ヘッダー：入出金伝票のレート幅 */
	protected double headerRateWidth = 2.80d;

	/** ヘッダー：入出金伝票の金額幅 */
	protected double headerAmountWidth = 3.10d;

	/** ヘッダー：入出金伝票の通貨幅 */
	protected double headerCurrencyWidth = 1.8d;

	/** ヘッダー：債務計上のレート幅 */
	protected double headerPayRateWidth = 2.80d;

	/** ヘッダー：債務計上の金額幅 */
	protected double headerPayAmountWidth = 3.10d;

	/** ヘッダー：債務計上の通貨幅 */
	protected double headerPayCurrencyWidth = 1.8d;

	/** ヘッダー：債務計上の支払日幅 */
	protected double headerPayDateWidth = 2.00d;

	/** ヘッダー：債務計上の支払区分幅 */
	protected double headerPayDateAndTypeWidth = 1.20d;

	/** ヘッダー：債権計上のレート幅 */
	protected double headerSaleRateWidth = 2.80d;

	/** ヘッダー：債権計上の金額幅 */
	protected double headerSaleAmountWidth = 3.10d;

	/** ヘッダー：債権計上の通貨幅 */
	protected double headerSaleCurrencyWidth = 1.8d;

	/** ヘッダー：経費精算のレート幅 */
	protected double headerSettlementRateWidth = 2.30d;

	/** ヘッダー：経費精算の金額幅 */
	protected double headerSettlementAmountWidth = 3.00d;

	/** ヘッダー：経費精算の通貨幅 */
	protected double headerSettlementCurrencyWidth = 0.70d;

	/** ヘッダー：社員仮払のレート幅 */
	protected double headerTempRateWidth = 2.30d;

	/** ヘッダー：社員仮払の金額幅 */
	protected double headerTempAmountWidth = 3.00d;

	/** ヘッダー：社員仮払の通貨幅 */
	protected double headerTempCurrencyWidth = 0.70d;

	/** ヘッダー：入金伝票の入金金額 */
	protected String headerInputCashflowAmountWord = "C00666";

	/** ヘッダー：出金伝票の出金金額 */
	protected String headerOutputCashflowAmountWord = "C02387";

	/** ヘッダー：債務計上の支払先 */
	protected String headerPayCustomerWord = "C01690";

	/** ヘッダー：債務計上の支払日/支払方法 */
	protected String headerPayDateAndTypeWord = "C10893";

	/** ヘッダー：債務計上の支払口座 */
	protected String headerPayAccountWord = "C01639";

	/** ヘッダー：債務計上の振出銀行 */
	protected String headerPayOwnBankWord = "C01634";

	/** ヘッダー：債務計上の支払金額 */
	protected String headerPayAmountWord = "C00229";

	/** ヘッダー：社員仮払の社員 */
	protected String headerTempEmployeeWord = "C00246";

	/** ヘッダー：社員仮払の仮払金額 */
	protected String headerTempAmountWord = "C04239";

	/** ヘッダー：社員仮払の支払日 */
	protected String headerTempPaymentDateWord = "C01098";

	/** ヘッダー：社員仮払の支払方法 */
	protected String headerTempPaymentMethodWord = "C00233";

	/** ヘッダー：社員仮払の振出銀行 */
	protected String headerTempBankWord = "C01634";

	/** ヘッダー：社員仮払の精算予定日 */
	protected String headerTempDueDateWord = "C04235";

	/** ヘッダー：債権計上の取引先 */
	protected String headerSaleCustomerWord = "C00408";

	/** ヘッダー：債権計上の入金予定日 */
	protected String headerSaleDateWord = "C01273";

	/** ヘッダー：債権計上の振込口座 */
	protected String headerSaleBankAccountWord = "C00683";

	/** ヘッダー：債権計上の請求金額 */
	protected String headerSaleAmountWord = "C01689";

	/** ヘッダー：経費精算の社員 */
	protected String headerSettlementEmployeeWord = "C00246";

	/** ヘッダー：経費精算の経費等合計 */
	protected String headerSettlementAmountSumWord = "C04282";

	/** ヘッダー：経費精算の支払日 */
	protected String headerSettlementPaymentDateWord = "C01098";

	/** ヘッダー：経費精算の仮払金額 */
	protected String headerSettlementPrevAmountWord = "C04239";

	/** ヘッダー：経費精算の支払方法 */
	protected String headerSettlementPayMethodWord = "C00233";

	/** ヘッダー：経費精算の精算金額 */
	protected String headerSettlementAmountWord = "C01707";

	/** ヘッダー：経費精算の振出銀行 */
	protected String headerSettlementBankWord = "C01634";

	/** ヘッダー伝票摘要コード非表示 */
	protected boolean notShowHeaderSlipRemarkCode = false;

	/** ヘッダー入力部門コード非表示 */
	protected boolean notShowHeaderDepartmentCode = false;

	/** ヘッダー入力社員コード非表示 */
	protected boolean notShowHeaderEmployeeCode = false;

	/** 部門コード非表示 */
	protected boolean notShowDepartmentCode = false;

	/** 科目コード非表示 */
	protected boolean notShowItemCode = false;

	/** 補助科目コード非表示 */
	protected boolean notShowSubItemCode = false;

	/** 内訳科目コード非表示 */
	protected boolean notShowDetailItemCode = false;

	/** 取引先コード非表示 */
	protected boolean notShowCustomerCode = false;

	/** 社員コード非表示 */
	protected boolean notShowEmployeeCode = false;

	/** 消費税コード非表示 */
	protected boolean notShowTaxCode = false;

	/** 管理1コード非表示 */
	protected boolean notShowManagementCode1 = false;

	/** 管理2コード非表示 */
	protected boolean notShowManagementCode2 = false;

	/** 管理3コード非表示 */
	protected boolean notShowManagementCode3 = false;

	/** 管理4コード非表示 */
	protected boolean notShowManagementCode4 = false;

	/** 管理5コード非表示 */
	protected boolean notShowManagementCode5 = false;

	/** 管理6コード非表示 */
	protected boolean notShowManagementCode6 = false;

	/**
	 * 伝票種別コードの取得
	 * 
	 * @return slipType 伝票種別コード
	 */
	public String getSlipType() {
		return slipType;
	}

	/**
	 * 伝票種別コードの設定
	 * 
	 * @param slipType 伝票種別コード
	 */
	public void setSlipType(String slipType) {
		this.slipType = slipType;
	}

	/**
	 * 伝票の種類（データ区分）の取得
	 * 
	 * @return slipKind 伝票の種類（データ区分）
	 */
	public SlipKind getSlipKind() {
		return slipKind;
	}

	/**
	 * 伝票の種類（データ区分）の設定
	 * 
	 * @param slipKind 伝票の種類（データ区分）
	 */
	public void setSlipKind(SlipKind slipKind) {
		this.slipKind = slipKind;
	}

	/**
	 * データ区分のコードの取得
	 * 
	 * @return dataDivision データ区分のコード
	 */
	public String getDataDivision() {
		return dataDivision;
	}

	/**
	 * データ区分のコードの設定
	 * 
	 * @param dataDivision データ区分のコード
	 */
	public void setDataDivision(String dataDivision) {
		this.dataDivision = dataDivision;
	}

	/**
	 * ヘッダー：伝票日付の取得
	 * 
	 * @return headerSlipDateWord ヘッダー：伝票日付
	 */
	public String getHeaderSlipDateWord() {
		return headerSlipDateWord;
	}

	/**
	 * ヘッダー：伝票日付の設定
	 * 
	 * @param headerSlipDateWord ヘッダー：伝票日付
	 */
	public void setHeaderSlipDateWord(String headerSlipDateWord) {
		this.headerSlipDateWord = headerSlipDateWord;
	}

	/**
	 * ヘッダー：入力部門の取得
	 * 
	 * @return headerDepartmentNameWord ヘッダー：入力部門
	 */
	public String getHeaderDepartmentNameWord() {
		return headerDepartmentNameWord;
	}

	/**
	 * ヘッダー：入力部門の設定
	 * 
	 * @param headerDepartmentNameWord ヘッダー：入力部門
	 */
	public void setHeaderDepartmentNameWord(String headerDepartmentNameWord) {
		this.headerDepartmentNameWord = headerDepartmentNameWord;
	}

	/**
	 * ヘッダー：入力者の取得
	 * 
	 * @return headerEmployeeNameWord ヘッダー：入力者
	 */
	public String getHeaderEmployeeNameWord() {
		return headerEmployeeNameWord;
	}

	/**
	 * ヘッダー：入力者の設定
	 * 
	 * @param headerEmployeeNameWord ヘッダー：入力者
	 */
	public void setHeaderEmployeeNameWord(String headerEmployeeNameWord) {
		this.headerEmployeeNameWord = headerEmployeeNameWord;
	}

	/**
	 * ヘッダー：伝票番号の取得
	 * 
	 * @return headerSlipNoWord ヘッダー：伝票番号
	 */
	public String getHeaderSlipNoWord() {
		return headerSlipNoWord;
	}

	/**
	 * ヘッダー：伝票番号の設定
	 * 
	 * @param headerSlipNoWord ヘッダー：伝票番号
	 */
	public void setHeaderSlipNoWord(String headerSlipNoWord) {
		this.headerSlipNoWord = headerSlipNoWord;
	}

	/**
	 * ヘッダー：修正の取得
	 * 
	 * @return headerUpdateWord ヘッダー：修正
	 */
	public String getHeaderUpdateWord() {
		return headerUpdateWord;
	}

	/**
	 * ヘッダー：修正の設定
	 * 
	 * @param headerUpdateWord ヘッダー：修正
	 */
	public void setHeaderUpdateWord(String headerUpdateWord) {
		this.headerUpdateWord = headerUpdateWord;
	}

	/**
	 * ヘッダー：回の取得
	 * 
	 * @return headerUpdateCountWord ヘッダー：回
	 */
	public String getHeaderUpdateCountWord() {
		return headerUpdateCountWord;
	}

	/**
	 * ヘッダー：回の設定
	 * 
	 * @param headerUpdateCountWord ヘッダー：回
	 */
	public void setHeaderUpdateCountWord(String headerUpdateCountWord) {
		this.headerUpdateCountWord = headerUpdateCountWord;
	}

	/**
	 * ヘッダー：証憑番号の取得
	 * 
	 * @return headerVoucherNoWord ヘッダー：証憑番号
	 */
	public String getHeaderVoucherNoWord() {
		return headerVoucherNoWord;
	}

	/**
	 * ヘッダー：証憑番号の設定
	 * 
	 * @param headerVoucherNoWord ヘッダー：証憑番号
	 */
	public void setHeaderVoucherNoWord(String headerVoucherNoWord) {
		this.headerVoucherNoWord = headerVoucherNoWord;
	}

	/**
	 * ヘッダー：伝票摘要の取得
	 * 
	 * @return headerSlipRemarkWord ヘッダー：伝票摘要
	 */
	public String getHeaderSlipRemarkWord() {
		return headerSlipRemarkWord;
	}

	/**
	 * ヘッダー：伝票摘要の設定
	 * 
	 * @param headerSlipRemarkWord ヘッダー：伝票摘要
	 */
	public void setHeaderSlipRemarkWord(String headerSlipRemarkWord) {
		this.headerSlipRemarkWord = headerSlipRemarkWord;
	}

	/**
	 * ヘッダー：基軸通貨の取得
	 * 
	 * @return headerKeyCurrencyWord ヘッダー：基軸通貨
	 */
	public String getHeaderKeyCurrencyWord() {
		return headerKeyCurrencyWord;
	}

	/**
	 * ヘッダー：基軸通貨の設定
	 * 
	 * @param headerKeyCurrencyWord ヘッダー：基軸通貨
	 */
	public void setHeaderKeyCurrencyWord(String headerKeyCurrencyWord) {
		this.headerKeyCurrencyWord = headerKeyCurrencyWord;
	}

	/**
	 * ヘッダー：帳簿区分の取得
	 * 
	 * @return headerAccountBookNameWord ヘッダー：帳簿区分
	 */
	public String getHeaderAccountBookNameWord() {
		return headerAccountBookNameWord;
	}

	/**
	 * ヘッダー：帳簿区分の設定
	 * 
	 * @param headerAccountBookNameWord ヘッダー：帳簿区分
	 */
	public void setHeaderAccountBookNameWord(String headerAccountBookNameWord) {
		this.headerAccountBookNameWord = headerAccountBookNameWord;
	}

	/**
	 * ヘッダー：承認印1の取得
	 * 
	 * @return headerAcceptStamp1Word ヘッダー：承認印1
	 */
	public String getHeaderAcceptStamp1Word() {
		return headerAcceptStamp1Word;
	}

	/**
	 * ヘッダー：承認印1の設定
	 * 
	 * @param headerAcceptStamp1Word ヘッダー：承認印1
	 */
	public void setHeaderAcceptStamp1Word(String headerAcceptStamp1Word) {
		this.headerAcceptStamp1Word = headerAcceptStamp1Word;
	}

	/**
	 * ヘッダー：承認印2の取得
	 * 
	 * @return headerAcceptStamp2Word ヘッダー：承認印2
	 */
	public String getHeaderAcceptStamp2Word() {
		return headerAcceptStamp2Word;
	}

	/**
	 * ヘッダー：承認印2の設定
	 * 
	 * @param headerAcceptStamp2Word ヘッダー：承認印2
	 */
	public void setHeaderAcceptStamp2Word(String headerAcceptStamp2Word) {
		this.headerAcceptStamp2Word = headerAcceptStamp2Word;
	}

	/**
	 * ヘッダー：承認印3の取得
	 * 
	 * @return headerAcceptStamp3Word ヘッダー：承認印3
	 */
	public String getHeaderAcceptStamp3Word() {
		return headerAcceptStamp3Word;
	}

	/**
	 * ヘッダー：承認印3の設定
	 * 
	 * @param headerAcceptStamp3Word ヘッダー：承認印3
	 */
	public void setHeaderAcceptStamp3Word(String headerAcceptStamp3Word) {
		this.headerAcceptStamp3Word = headerAcceptStamp3Word;
	}

	/**
	 * 明細：借方金額の取得
	 * 
	 * @return detailDrAmountWord 明細：借方金額
	 */
	public String getDetailDrAmountWord() {
		return detailDrAmountWord;
	}

	/**
	 * 明細：借方金額の設定
	 * 
	 * @param detailDrAmountWord 明細：借方金額
	 */
	public void setDetailDrAmountWord(String detailDrAmountWord) {
		this.detailDrAmountWord = detailDrAmountWord;
	}

	/**
	 * 明細：貸方金額の取得
	 * 
	 * @return detailCrAmountWord 明細：貸方金額
	 */
	public String getDetailCrAmountWord() {
		return detailCrAmountWord;
	}

	/**
	 * 明細：貸方金額の設定
	 * 
	 * @param detailCrAmountWord 明細：貸方金額
	 */
	public void setDetailCrAmountWord(String detailCrAmountWord) {
		this.detailCrAmountWord = detailCrAmountWord;
	}

	/**
	 * 明細：行摘要の取得
	 * 
	 * @return detailRowRemarkWord 明細：行摘要
	 */
	public String getDetailRowRemarkWord() {
		return detailRowRemarkWord;
	}

	/**
	 * 明細：行摘要の設定
	 * 
	 * @param detailRowRemarkWord 明細：行摘要
	 */
	public void setDetailRowRemarkWord(String detailRowRemarkWord) {
		this.detailRowRemarkWord = detailRowRemarkWord;
	}

	/**
	 * 明細：計上部門/取引先/社員の取得
	 * 
	 * @return detailCustomerWord 明細：計上部門/取引先/社員
	 */
	public String getDetailCustomerWord() {
		return detailCustomerWord;
	}

	/**
	 * 明細：計上部門/取引先/社員の設定
	 * 
	 * @param detailCustomerWord 明細：計上部門/取引先/社員
	 */
	public void setDetailCustomerWord(String detailCustomerWord) {
		this.detailCustomerWord = detailCustomerWord;
	}

	/**
	 * 明細：通貨の取得
	 * 
	 * @return detailCurrencyWord 明細：通貨
	 */
	public String getDetailCurrencyWord() {
		return detailCurrencyWord;
	}

	/**
	 * 明細：通貨の設定
	 * 
	 * @param detailCurrencyWord 明細：通貨
	 */
	public void setDetailCurrencyWord(String detailCurrencyWord) {
		this.detailCurrencyWord = detailCurrencyWord;
	}

	/**
	 * 明細：発生日/税の取得
	 * 
	 * @return detailTaxWord 明細：発生日/税
	 */
	public String getDetailTaxWord() {
		return detailTaxWord;
	}

	/**
	 * 明細：発生日/税の設定
	 * 
	 * @param detailTaxWord 明細：発生日/税
	 */
	public void setDetailTaxWord(String detailTaxWord) {
		this.detailTaxWord = detailTaxWord;
	}

	/**
	 * 明細：管理1/2/3の取得
	 * 
	 * @return detailManagement1To3Word 明細：管理1/2/3
	 */
	public String getDetailManagement1To3Word() {
		return detailManagement1To3Word;
	}

	/**
	 * 明細：管理1/2/3の設定
	 * 
	 * @param detailManagement1To3Word 明細：管理1/2/3
	 */
	public void setDetailManagement1To3Word(String detailManagement1To3Word) {
		this.detailManagement1To3Word = detailManagement1To3Word;
	}

	/**
	 * 明細：管理4/5/6の取得
	 * 
	 * @return detailManagement4To6Word 明細：管理4/5/6
	 */
	public String getDetailManagement4To6Word() {
		return detailManagement4To6Word;
	}

	/**
	 * 明細：管理4/5/6の設定
	 * 
	 * @param detailManagement4To6Word 明細：管理4/5/6
	 */
	public void setDetailManagement4To6Word(String detailManagement4To6Word) {
		this.detailManagement4To6Word = detailManagement4To6Word;
	}

	/**
	 * 明細：非会計明細1/2/3の取得
	 * 
	 * @return detailNonAccountWord 明細：非会計明細1/2/3
	 */
	public String getDetailNonAccountWord() {
		return detailNonAccountWord;
	}

	/**
	 * 明細：非会計明細1/2/3の設定
	 * 
	 * @param detailNonAccountWord 明細：非会計明細1/2/3
	 */
	public void setDetailNonAccountWord(String detailNonAccountWord) {
		this.detailNonAccountWord = detailNonAccountWord;
	}

	/**
	 * 明細：合計の取得
	 * 
	 * @return detailSumWord 明細：合計
	 */
	public String getDetailSumWord() {
		return detailSumWord;
	}

	/**
	 * 明細：合計の設定
	 * 
	 * @param detailSumWord 明細：合計
	 */
	public void setDetailSumWord(String detailSumWord) {
		this.detailSumWord = detailSumWord;
	}

	/**
	 * 明細：外貨計1の取得
	 * 
	 * @return detailForeign1Word 明細：外貨計1
	 */
	public String getDetailForeign1Word() {
		return detailForeign1Word;
	}

	/**
	 * 明細：外貨計1の設定
	 * 
	 * @param detailForeign1Word 明細：外貨計1
	 */
	public void setDetailForeign1Word(String detailForeign1Word) {
		this.detailForeign1Word = detailForeign1Word;
	}

	/**
	 * 明細：外貨計2の取得
	 * 
	 * @return detailForeign2Word 明細：外貨計2
	 */
	public String getDetailForeign2Word() {
		return detailForeign2Word;
	}

	/**
	 * 明細：外貨計2の設定
	 * 
	 * @param detailForeign2Word 明細：外貨計2
	 */
	public void setDetailForeign2Word(String detailForeign2Word) {
		this.detailForeign2Word = detailForeign2Word;
	}

	/**
	 * 明細：外貨計3の取得
	 * 
	 * @return detailForeign3Word 明細：外貨計3
	 */
	public String getDetailForeign3Word() {
		return detailForeign3Word;
	}

	/**
	 * 明細：外貨計3の設定
	 * 
	 * @param detailForeign3Word 明細：外貨計3
	 */
	public void setDetailForeign3Word(String detailForeign3Word) {
		this.detailForeign3Word = detailForeign3Word;
	}

	/**
	 * 明細：外貨計4の取得
	 * 
	 * @return detailForeign4Word 明細：外貨計4
	 */
	public String getDetailForeign4Word() {
		return detailForeign4Word;
	}

	/**
	 * 明細：外貨計4の設定
	 * 
	 * @param detailForeign4Word 明細：外貨計4
	 */
	public void setDetailForeign4Word(String detailForeign4Word) {
		this.detailForeign4Word = detailForeign4Word;
	}

	/**
	 * 行高さの取得<br>
	 * 変更によるレイアウト調整必要
	 * 
	 * @return rowHeight 行高さ
	 */
	public double getRowHeight() {
		return rowHeight;
	}

	/**
	 * 行高さの設定<br>
	 * 変更によるレイアウト調整必要
	 * 
	 * @param rowHeight 行高さ
	 */
	public void setRowHeight(double rowHeight) {
		this.rowHeight = rowHeight;
	}

	/**
	 * ヘッダータイトル幅の取得
	 * 
	 * @return headerTitleWidth ヘッダータイトル幅
	 */
	public double getHeaderTitleWidth() {
		return headerTitleWidth;
	}

	/**
	 * ヘッダータイトル幅の設定
	 * 
	 * @param headerTitleWidth ヘッダータイトル幅
	 */
	public void setHeaderTitleWidth(double headerTitleWidth) {
		this.headerTitleWidth = headerTitleWidth;
	}

	/**
	 * ヘッダー部門幅の取得
	 * 
	 * @return headerDepartmentWidth ヘッダー部門幅
	 */
	public double getHeaderDepartmentWidth() {
		return headerDepartmentWidth;
	}

	/**
	 * ヘッダー部門幅の設定
	 * 
	 * @param headerDepartmentWidth ヘッダー部門幅
	 */
	public void setHeaderDepartmentWidth(double headerDepartmentWidth) {
		this.headerDepartmentWidth = headerDepartmentWidth;
	}

	/**
	 * 空行幅の取得
	 * 
	 * @return detailRestWidth 空行幅
	 */
	public double getDetailRestWidth() {
		return detailRestWidth;
	}

	/**
	 * 空行幅の設定
	 * 
	 * @param detailRestWidth 空行幅
	 */
	public void setDetailRestWidth(double detailRestWidth) {
		this.detailRestWidth = detailRestWidth;
	}

	/**
	 * 行番号幅の取得
	 * 
	 * @return detailRowNoWidth 行番号幅
	 */
	public double getDetailRowNoWidth() {
		return detailRowNoWidth;
	}

	/**
	 * 行番号幅の設定
	 * 
	 * @param detailRowNoWidth 行番号幅
	 */
	public void setDetailRowNoWidth(double detailRowNoWidth) {
		this.detailRowNoWidth = detailRowNoWidth;
	}

	/**
	 * 科目幅の取得
	 * 
	 * @return detailItemWidth 科目幅
	 */
	public double getDetailItemWidth() {
		return detailItemWidth;
	}

	/**
	 * 科目幅の設定
	 * 
	 * @param detailItemWidth 科目幅
	 */
	public void setDetailItemWidth(double detailItemWidth) {
		this.detailItemWidth = detailItemWidth;
	}

	/**
	 * 金額幅の取得
	 * 
	 * @return detailAmountWidth 金額幅
	 */
	public double getDetailAmountWidth() {
		return detailAmountWidth;
	}

	/**
	 * 金額幅の設定
	 * 
	 * @param detailAmountWidth 金額幅
	 */
	public void setDetailAmountWidth(double detailAmountWidth) {
		this.detailAmountWidth = detailAmountWidth;
	}

	/**
	 * 通貨幅の取得
	 * 
	 * @return detailCurrencyWidth 通貨幅
	 */
	public double getDetailCurrencyWidth() {
		return detailCurrencyWidth;
	}

	/**
	 * 通貨幅の設定
	 * 
	 * @param detailCurrencyWidth 通貨幅
	 */
	public void setDetailCurrencyWidth(double detailCurrencyWidth) {
		this.detailCurrencyWidth = detailCurrencyWidth;
	}

	/**
	 * 摘要幅の取得
	 * 
	 * @return detailRemarkWidth 摘要幅
	 */
	public double getDetailRemarkWidth() {
		return detailRemarkWidth;
	}

	/**
	 * 摘要幅の設定
	 * 
	 * @param detailRemarkWidth 摘要幅
	 */
	public void setDetailRemarkWidth(double detailRemarkWidth) {
		this.detailRemarkWidth = detailRemarkWidth;
	}

	/**
	 * 取引先幅の取得
	 * 
	 * @return detailCustomerWidth 取引先幅
	 */
	public double getDetailCustomerWidth() {
		return detailCustomerWidth;
	}

	/**
	 * 取引先幅の設定
	 * 
	 * @param detailCustomerWidth 取引先幅
	 */
	public void setDetailCustomerWidth(double detailCustomerWidth) {
		this.detailCustomerWidth = detailCustomerWidth;
	}

	/**
	 * 管理1～3幅の取得
	 * 
	 * @return detailManagement1To3Width 管理1～3幅
	 */
	public double getDetailManagement1To3Width() {
		return detailManagement1To3Width;
	}

	/**
	 * 管理1～3幅の設定
	 * 
	 * @param detailManagement1To3Width 管理1～3幅
	 */
	public void setDetailManagement1To3Width(double detailManagement1To3Width) {
		this.detailManagement1To3Width = detailManagement1To3Width;
	}

	/**
	 * 管理4～6幅の取得
	 * 
	 * @return detailManagement4To6Width 管理4～6幅
	 */
	public double getDetailManagement4To6Width() {
		return detailManagement4To6Width;
	}

	/**
	 * 管理4～6幅の設定
	 * 
	 * @param detailManagement4To6Width 管理4～6幅
	 */
	public void setDetailManagement4To6Width(double detailManagement4To6Width) {
		this.detailManagement4To6Width = detailManagement4To6Width;
	}

	/**
	 * 非会計明細幅の取得
	 * 
	 * @return detailNonAccountWidth 非会計明細幅
	 */
	public double getDetailNonAccountWidth() {
		return detailNonAccountWidth;
	}

	/**
	 * 非会計明細幅の設定
	 * 
	 * @param detailNonAccountWidth 非会計明細幅
	 */
	public void setDetailNonAccountWidth(double detailNonAccountWidth) {
		this.detailNonAccountWidth = detailNonAccountWidth;
	}

	/**
	 * 帳簿区分桁数の取得
	 * 
	 * @return headerAccountBookNameLength 帳簿区分桁数
	 */
	public int getHeaderAccountBookNameLength() {
		return headerAccountBookNameLength;
	}

	/**
	 * 帳簿区分桁数の設定
	 * 
	 * @param headerAccountBookNameLength 帳簿区分桁数
	 */
	public void setHeaderAccountBookNameLength(int headerAccountBookNameLength) {
		this.headerAccountBookNameLength = headerAccountBookNameLength;
	}

	/**
	 * 修正回数桁数の取得
	 * 
	 * @return headerUpdateCountLength 修正回数桁数
	 */
	public int getHeaderUpdateCountLength() {
		return headerUpdateCountLength;
	}

	/**
	 * 修正回数桁数の設定
	 * 
	 * @param headerUpdateCountLength 修正回数桁数
	 */
	public void setHeaderUpdateCountLength(int headerUpdateCountLength) {
		this.headerUpdateCountLength = headerUpdateCountLength;
	}

	/**
	 * 承認印1桁数の取得
	 * 
	 * @return headerAcceptStamp1Length 承認印1桁数
	 */
	public int getHeaderAcceptStamp1Length() {
		return headerAcceptStamp1Length;
	}

	/**
	 * 承認印1桁数の設定
	 * 
	 * @param headerAcceptStamp1Length 承認印1桁数
	 */
	public void setHeaderAcceptStamp1Length(int headerAcceptStamp1Length) {
		this.headerAcceptStamp1Length = headerAcceptStamp1Length;
	}

	/**
	 * 承認印2桁数の取得
	 * 
	 * @return headerAcceptStamp2Length 承認印2桁数
	 */
	public int getHeaderAcceptStamp2Length() {
		return headerAcceptStamp2Length;
	}

	/**
	 * 承認印2桁数の設定
	 * 
	 * @param headerAcceptStamp2Length 承認印2桁数
	 */
	public void setHeaderAcceptStamp2Length(int headerAcceptStamp2Length) {
		this.headerAcceptStamp2Length = headerAcceptStamp2Length;
	}

	/**
	 * 承認印3桁数の取得
	 * 
	 * @return headerAcceptStamp3Length 承認印3桁数
	 */
	public int getHeaderAcceptStamp3Length() {
		return headerAcceptStamp3Length;
	}

	/**
	 * 承認印3桁数の設定
	 * 
	 * @param headerAcceptStamp3Length 承認印3桁数
	 */
	public void setHeaderAcceptStamp3Length(int headerAcceptStamp3Length) {
		this.headerAcceptStamp3Length = headerAcceptStamp3Length;
	}

	/**
	 * 科目桁数の取得
	 * 
	 * @return detailItemLength 科目桁数
	 */
	public int getDetailItemLength() {
		return detailItemLength;
	}

	/**
	 * 科目桁数の設定
	 * 
	 * @param detailItemLength 科目桁数
	 */
	public void setDetailItemLength(int detailItemLength) {
		this.detailItemLength = detailItemLength;
	}

	/**
	 * 消費税桁数の取得
	 * 
	 * @return detailTaxLength 消費税桁数
	 */
	public int getDetailTaxLength() {
		return detailTaxLength;
	}

	/**
	 * 消費税桁数の設定
	 * 
	 * @param detailTaxLength 消費税桁数
	 */
	public void setDetailTaxLength(int detailTaxLength) {
		this.detailTaxLength = detailTaxLength;
	}

	/**
	 * 摘要桁数の取得
	 * 
	 * @return detailRemarkLength 摘要桁数
	 */
	public int getDetailRemarkLength() {
		return detailRemarkLength;
	}

	/**
	 * 摘要桁数の設定
	 * 
	 * @param detailRemarkLength 摘要桁数
	 */
	public void setDetailRemarkLength(int detailRemarkLength) {
		this.detailRemarkLength = detailRemarkLength;
	}

	/**
	 * 取引先桁数の取得
	 * 
	 * @return detailCustomerLength 取引先桁数
	 */
	public int getDetailCustomerLength() {
		return detailCustomerLength;
	}

	/**
	 * 取引先桁数の設定
	 * 
	 * @param detailCustomerLength 取引先桁数
	 */
	public void setDetailCustomerLength(int detailCustomerLength) {
		this.detailCustomerLength = detailCustomerLength;
	}

	/**
	 * 管理1～3桁数の取得
	 * 
	 * @return detailManagement1To3Length 管理1～3桁数
	 */
	public int getDetailManagement1To3Length() {
		return detailManagement1To3Length;
	}

	/**
	 * 管理1～3桁数の設定
	 * 
	 * @param detailManagement1To3Length 管理1～3桁数
	 */
	public void setDetailManagement1To3Length(int detailManagement1To3Length) {
		this.detailManagement1To3Length = detailManagement1To3Length;
	}

	/**
	 * 管理4～6桁数の取得
	 * 
	 * @return detailManagement4To6Length 管理4～6桁数
	 */
	public int getDetailManagement4To6Length() {
		return detailManagement4To6Length;
	}

	/**
	 * 管理4～6桁数の設定
	 * 
	 * @param detailManagement4To6Length 管理4～6桁数
	 */
	public void setDetailManagement4To6Length(int detailManagement4To6Length) {
		this.detailManagement4To6Length = detailManagement4To6Length;
	}

	/**
	 * 非会計明細桁数の取得
	 * 
	 * @return detailNonAccountLength 非会計明細桁数
	 */
	public int getDetailNonAccountLength() {
		return detailNonAccountLength;
	}

	/**
	 * 非会計明細桁数の設定
	 * 
	 * @param detailNonAccountLength 非会計明細桁数
	 */
	public void setDetailNonAccountLength(int detailNonAccountLength) {
		this.detailNonAccountLength = detailNonAccountLength;
	}

	/**
	 * ヘッダー：入力部門桁数の取得
	 * 
	 * @return headerDepartmentNameLength ヘッダー：入力部門桁数
	 */
	public int getHeaderDepartmentNameLength() {
		return headerDepartmentNameLength;
	}

	/**
	 * ヘッダー：入力部門桁数の設定
	 * 
	 * @param headerDepartmentNameLength ヘッダー：入力部門桁数
	 */
	public void setHeaderDepartmentNameLength(int headerDepartmentNameLength) {
		this.headerDepartmentNameLength = headerDepartmentNameLength;
	}

	/**
	 * ヘッダー：入出金伝票のレート幅の取得
	 * 
	 * @return headerRateWidth ヘッダー：入出金伝票のレート幅
	 */
	public double getHeaderRateWidth() {
		return headerRateWidth;
	}

	/**
	 * ヘッダー：入出金伝票のレート幅の設定
	 * 
	 * @param headerRateWidth ヘッダー：入出金伝票のレート幅
	 */
	public void setHeaderRateWidth(double headerRateWidth) {
		this.headerRateWidth = headerRateWidth;
	}

	/**
	 * ヘッダー：入出金伝票の金額幅の取得
	 * 
	 * @return headerAmountWidth ヘッダー：入出金伝票の金額幅
	 */
	public double getHeaderAmountWidth() {
		return headerAmountWidth;
	}

	/**
	 * ヘッダー：入出金伝票の金額幅の設定
	 * 
	 * @param headerAmountWidth ヘッダー：入出金伝票の金額幅
	 */
	public void setHeaderAmountWidth(double headerAmountWidth) {
		this.headerAmountWidth = headerAmountWidth;
	}

	/**
	 * ヘッダー：入出金伝票の通貨幅の取得
	 * 
	 * @return headerCurrencyWidth ヘッダー：入出金伝票の通貨幅
	 */
	public double getHeaderCurrencyWidth() {
		return headerCurrencyWidth;
	}

	/**
	 * ヘッダー：入出金伝票の通貨幅の設定
	 * 
	 * @param headerCurrencyWidth ヘッダー：入出金伝票の通貨幅
	 */
	public void setHeaderCurrencyWidth(double headerCurrencyWidth) {
		this.headerCurrencyWidth = headerCurrencyWidth;
	}

	/**
	 * ヘッダー：債務計上のレート幅の取得
	 * 
	 * @return headerPayRateWidth ヘッダー：債務計上のレート幅
	 */
	public double getHeaderPayRateWidth() {
		return headerPayRateWidth;
	}

	/**
	 * ヘッダー：債務計上のレート幅の設定
	 * 
	 * @param headerPayRateWidth ヘッダー：債務計上のレート幅
	 */
	public void setHeaderPayRateWidth(double headerPayRateWidth) {
		this.headerPayRateWidth = headerPayRateWidth;
	}

	/**
	 * ヘッダー：債務計上の金額幅の取得
	 * 
	 * @return headerPayAmountWidth ヘッダー：債務計上の金額幅
	 */
	public double getHeaderPayAmountWidth() {
		return headerPayAmountWidth;
	}

	/**
	 * ヘッダー：債務計上の金額幅の設定
	 * 
	 * @param headerPayAmountWidth ヘッダー：債務計上の金額幅
	 */
	public void setHeaderPayAmountWidth(double headerPayAmountWidth) {
		this.headerPayAmountWidth = headerPayAmountWidth;
	}

	/**
	 * ヘッダー：債務計上の通貨幅の取得
	 * 
	 * @return headerPayCurrencyWidth ヘッダー：債務計上の通貨幅
	 */
	public double getHeaderPayCurrencyWidth() {
		return headerPayCurrencyWidth;
	}

	/**
	 * ヘッダー：債務計上の通貨幅の設定
	 * 
	 * @param headerPayCurrencyWidth ヘッダー：債務計上の通貨幅
	 */
	public void setHeaderPayCurrencyWidth(double headerPayCurrencyWidth) {
		this.headerPayCurrencyWidth = headerPayCurrencyWidth;
	}

	/**
	 * ヘッダー：債務計上の支払日幅の取得
	 * 
	 * @return headerPayDateWidth ヘッダー：債務計上の支払日幅
	 */
	public double getHeaderPayDateWidth() {
		return headerPayDateWidth;
	}

	/**
	 * ヘッダー：債務計上の支払日幅の設定
	 * 
	 * @param headerPayDateWidth ヘッダー：債務計上の支払日幅
	 */
	public void setHeaderPayDateWidth(double headerPayDateWidth) {
		this.headerPayDateWidth = headerPayDateWidth;
	}

	/**
	 * ヘッダー：債務計上の支払区分幅の取得
	 * 
	 * @return headerPayDateAndTypeWidth ヘッダー：債務計上の支払区分幅
	 */
	public double getHeaderPayDateAndTypeWidth() {
		return headerPayDateAndTypeWidth;
	}

	/**
	 * ヘッダー：債務計上の支払区分幅の設定
	 * 
	 * @param headerPayDateAndTypeWidth ヘッダー：債務計上の支払区分幅
	 */
	public void setHeaderPayDateAndTypeWidth(double headerPayDateAndTypeWidth) {
		this.headerPayDateAndTypeWidth = headerPayDateAndTypeWidth;
	}

	/**
	 * ヘッダー：債権計上のレート幅の取得
	 * 
	 * @return headerSaleRateWidth ヘッダー：債権計上のレート幅
	 */
	public double getHeaderSaleRateWidth() {
		return headerSaleRateWidth;
	}

	/**
	 * ヘッダー：債権計上のレート幅の設定
	 * 
	 * @param headerSaleRateWidth ヘッダー：債権計上のレート幅
	 */
	public void setHeaderSaleRateWidth(double headerSaleRateWidth) {
		this.headerSaleRateWidth = headerSaleRateWidth;
	}

	/**
	 * ヘッダー：債権計上の金額幅の取得
	 * 
	 * @return headerSaleAmountWidth ヘッダー：債権計上の金額幅
	 */
	public double getHeaderSaleAmountWidth() {
		return headerSaleAmountWidth;
	}

	/**
	 * ヘッダー：債権計上の金額幅の設定
	 * 
	 * @param headerSaleAmountWidth ヘッダー：債権計上の金額幅
	 */
	public void setHeaderSaleAmountWidth(double headerSaleAmountWidth) {
		this.headerSaleAmountWidth = headerSaleAmountWidth;
	}

	/**
	 * ヘッダー：債権計上の通貨幅の取得
	 * 
	 * @return headerSaleCurrencyWidth ヘッダー：債権計上の通貨幅
	 */
	public double getHeaderSaleCurrencyWidth() {
		return headerSaleCurrencyWidth;
	}

	/**
	 * ヘッダー：債権計上の通貨幅の設定
	 * 
	 * @param headerSaleCurrencyWidth ヘッダー：債権計上の通貨幅
	 */
	public void setHeaderSaleCurrencyWidth(double headerSaleCurrencyWidth) {
		this.headerSaleCurrencyWidth = headerSaleCurrencyWidth;
	}

	/**
	 * ヘッダー：経費精算のレート幅の取得
	 * 
	 * @return headerSettlementRateWidth ヘッダー：経費精算のレート幅
	 */
	public double getHeaderSettlementRateWidth() {
		return headerSettlementRateWidth;
	}

	/**
	 * ヘッダー：経費精算のレート幅の設定
	 * 
	 * @param headerSettlementRateWidth ヘッダー：経費精算のレート幅
	 */
	public void setHeaderSettlementRateWidth(double headerSettlementRateWidth) {
		this.headerSettlementRateWidth = headerSettlementRateWidth;
	}

	/**
	 * ヘッダー：経費精算の金額幅の取得
	 * 
	 * @return headerSettlementAmountWidth ヘッダー：経費精算の金額幅
	 */
	public double getHeaderSettlementAmountWidth() {
		return headerSettlementAmountWidth;
	}

	/**
	 * ヘッダー：経費精算の金額幅の設定
	 * 
	 * @param headerSettlementAmountWidth ヘッダー：経費精算の金額幅
	 */
	public void setHeaderSettlementAmountWidth(double headerSettlementAmountWidth) {
		this.headerSettlementAmountWidth = headerSettlementAmountWidth;
	}

	/**
	 * ヘッダー：経費精算の通貨幅の取得
	 * 
	 * @return headerSettlementCurrencyWidth ヘッダー：経費精算の通貨幅
	 */
	public double getHeaderSettlementCurrencyWidth() {
		return headerSettlementCurrencyWidth;
	}

	/**
	 * ヘッダー：経費精算の通貨幅の設定
	 * 
	 * @param headerSettlementCurrencyWidth ヘッダー：経費精算の通貨幅
	 */
	public void setHeaderSettlementCurrencyWidth(double headerSettlementCurrencyWidth) {
		this.headerSettlementCurrencyWidth = headerSettlementCurrencyWidth;
	}

	/**
	 * ヘッダー：社員仮払のレート幅の取得
	 * 
	 * @return headerTempRateWidth ヘッダー：社員仮払のレート幅
	 */
	public double getHeaderTempRateWidth() {
		return headerTempRateWidth;
	}

	/**
	 * ヘッダー：社員仮払のレート幅の設定
	 * 
	 * @param headerTempRateWidth ヘッダー：社員仮払のレート幅
	 */
	public void setHeaderTempRateWidth(double headerTempRateWidth) {
		this.headerTempRateWidth = headerTempRateWidth;
	}

	/**
	 * ヘッダー：社員仮払の金額幅の取得
	 * 
	 * @return headerTempAmountWidth ヘッダー：社員仮払の金額幅
	 */
	public double getHeaderTempAmountWidth() {
		return headerTempAmountWidth;
	}

	/**
	 * ヘッダー：社員仮払の金額幅の設定
	 * 
	 * @param headerTempAmountWidth ヘッダー：社員仮払の金額幅
	 */
	public void setHeaderTempAmountWidth(double headerTempAmountWidth) {
		this.headerTempAmountWidth = headerTempAmountWidth;
	}

	/**
	 * ヘッダー：社員仮払の通貨幅の取得
	 * 
	 * @return headerTempCurrencyWidth ヘッダー：社員仮払の通貨幅
	 */
	public double getHeaderTempCurrencyWidth() {
		return headerTempCurrencyWidth;
	}

	/**
	 * ヘッダー：社員仮払の通貨幅の設定
	 * 
	 * @param headerTempCurrencyWidth ヘッダー：社員仮払の通貨幅
	 */
	public void setHeaderTempCurrencyWidth(double headerTempCurrencyWidth) {
		this.headerTempCurrencyWidth = headerTempCurrencyWidth;
	}

	/**
	 * ヘッダー：入金伝票の入金金額の取得
	 * 
	 * @return headerInputCashflowAmountWord ヘッダー：入金伝票の入金金額
	 */
	public String getHeaderInputCashflowAmountWord() {
		return headerInputCashflowAmountWord;
	}

	/**
	 * ヘッダー：入金伝票の入金金額の設定
	 * 
	 * @param headerInputCashflowAmountWord ヘッダー：入金伝票の入金金額
	 */
	public void setHeaderInputCashflowAmountWord(String headerInputCashflowAmountWord) {
		this.headerInputCashflowAmountWord = headerInputCashflowAmountWord;
	}

	/**
	 * ヘッダー：出金伝票の出金金額の取得
	 * 
	 * @return headerOutputCashflowAmountWord ヘッダー：出金伝票の出金金額
	 */
	public String getHeaderOutputCashflowAmountWord() {
		return headerOutputCashflowAmountWord;
	}

	/**
	 * ヘッダー：出金伝票の出金金額の設定
	 * 
	 * @param headerOutputCashflowAmountWord ヘッダー：出金伝票の出金金額
	 */
	public void setHeaderOutputCashflowAmountWord(String headerOutputCashflowAmountWord) {
		this.headerOutputCashflowAmountWord = headerOutputCashflowAmountWord;
	}

	/**
	 * ヘッダー：債務計上の支払先の取得
	 * 
	 * @return headerPayCustomerWord ヘッダー：債務計上の支払先
	 */
	public String getHeaderPayCustomerWord() {
		return headerPayCustomerWord;
	}

	/**
	 * ヘッダー：債務計上の支払先の設定
	 * 
	 * @param headerPayCustomerWord ヘッダー：債務計上の支払先
	 */
	public void setHeaderPayCustomerWord(String headerPayCustomerWord) {
		this.headerPayCustomerWord = headerPayCustomerWord;
	}

	/**
	 * ヘッダー：債務計上の支払日/支払方法の取得
	 * 
	 * @return headerPayDateAndTypeWord ヘッダー：債務計上の支払日/支払方法
	 */
	public String getHeaderPayDateAndTypeWord() {
		return headerPayDateAndTypeWord;
	}

	/**
	 * ヘッダー：債務計上の支払日/支払方法の設定
	 * 
	 * @param headerPayDateAndTypeWord ヘッダー：債務計上の支払日/支払方法
	 */
	public void setHeaderPayDateAndTypeWord(String headerPayDateAndTypeWord) {
		this.headerPayDateAndTypeWord = headerPayDateAndTypeWord;
	}

	/**
	 * ヘッダー：債務計上の支払口座の取得
	 * 
	 * @return headerPayAccountWord ヘッダー：債務計上の支払口座
	 */
	public String getHeaderPayAccountWord() {
		return headerPayAccountWord;
	}

	/**
	 * ヘッダー：債務計上の支払口座の設定
	 * 
	 * @param headerPayAccountWord ヘッダー：債務計上の支払口座
	 */
	public void setHeaderPayAccountWord(String headerPayAccountWord) {
		this.headerPayAccountWord = headerPayAccountWord;
	}

	/**
	 * ヘッダー：債務計上の振出銀行の取得
	 * 
	 * @return headerPayOwnBankWord ヘッダー：債務計上の振出銀行
	 */
	public String getHeaderPayOwnBankWord() {
		return headerPayOwnBankWord;
	}

	/**
	 * ヘッダー：債務計上の振出銀行の設定
	 * 
	 * @param headerPayOwnBankWord ヘッダー：債務計上の振出銀行
	 */
	public void setHeaderPayOwnBankWord(String headerPayOwnBankWord) {
		this.headerPayOwnBankWord = headerPayOwnBankWord;
	}

	/**
	 * ヘッダー：債務計上の支払金額の取得
	 * 
	 * @return headerPayAmountWord ヘッダー：債務計上の支払金額
	 */
	public String getHeaderPayAmountWord() {
		return headerPayAmountWord;
	}

	/**
	 * ヘッダー：債務計上の支払金額の設定
	 * 
	 * @param headerPayAmountWord ヘッダー：債務計上の支払金額
	 */
	public void setHeaderPayAmountWord(String headerPayAmountWord) {
		this.headerPayAmountWord = headerPayAmountWord;
	}

	/**
	 * ヘッダー：社員仮払の社員の取得
	 * 
	 * @return headerTempEmployeeWord ヘッダー：社員仮払の社員
	 */
	public String getHeaderTempEmployeeWord() {
		return headerTempEmployeeWord;
	}

	/**
	 * ヘッダー：社員仮払の社員の設定
	 * 
	 * @param headerTempEmployeeWord ヘッダー：社員仮払の社員
	 */
	public void setHeaderTempEmployeeWord(String headerTempEmployeeWord) {
		this.headerTempEmployeeWord = headerTempEmployeeWord;
	}

	/**
	 * ヘッダー：社員仮払の仮払金額の取得
	 * 
	 * @return headerTempAmountWord ヘッダー：社員仮払の仮払金額
	 */
	public String getHeaderTempAmountWord() {
		return headerTempAmountWord;
	}

	/**
	 * ヘッダー：社員仮払の仮払金額の設定
	 * 
	 * @param headerTempAmountWord ヘッダー：社員仮払の仮払金額
	 */
	public void setHeaderTempAmountWord(String headerTempAmountWord) {
		this.headerTempAmountWord = headerTempAmountWord;
	}

	/**
	 * ヘッダー：社員仮払の支払日の取得
	 * 
	 * @return headerTempPaymentDateWord ヘッダー：社員仮払の支払日
	 */
	public String getHeaderTempPaymentDateWord() {
		return headerTempPaymentDateWord;
	}

	/**
	 * ヘッダー：社員仮払の支払日の設定
	 * 
	 * @param headerTempPaymentDateWord ヘッダー：社員仮払の支払日
	 */
	public void setHeaderTempPaymentDateWord(String headerTempPaymentDateWord) {
		this.headerTempPaymentDateWord = headerTempPaymentDateWord;
	}

	/**
	 * ヘッダー：社員仮払の支払方法の取得
	 * 
	 * @return headerTempPaymentMethodWord ヘッダー：社員仮払の支払方法
	 */
	public String getHeaderTempPaymentMethodWord() {
		return headerTempPaymentMethodWord;
	}

	/**
	 * ヘッダー：社員仮払の支払方法の設定
	 * 
	 * @param headerTempPaymentMethodWord ヘッダー：社員仮払の支払方法
	 */
	public void setHeaderTempPaymentMethodWord(String headerTempPaymentMethodWord) {
		this.headerTempPaymentMethodWord = headerTempPaymentMethodWord;
	}

	/**
	 * ヘッダー：社員仮払の振出銀行の取得
	 * 
	 * @return headerTempBankWord ヘッダー：社員仮払の振出銀行
	 */
	public String getHeaderTempBankWord() {
		return headerTempBankWord;
	}

	/**
	 * ヘッダー：社員仮払の振出銀行の設定
	 * 
	 * @param headerTempBankWord ヘッダー：社員仮払の振出銀行
	 */
	public void setHeaderTempBankWord(String headerTempBankWord) {
		this.headerTempBankWord = headerTempBankWord;
	}

	/**
	 * ヘッダー：社員仮払の精算予定日の取得
	 * 
	 * @return headerTempDueDateWord ヘッダー：社員仮払の精算予定日
	 */
	public String getHeaderTempDueDateWord() {
		return headerTempDueDateWord;
	}

	/**
	 * ヘッダー：社員仮払の精算予定日の設定
	 * 
	 * @param headerTempDueDateWord ヘッダー：社員仮払の精算予定日
	 */
	public void setHeaderTempDueDateWord(String headerTempDueDateWord) {
		this.headerTempDueDateWord = headerTempDueDateWord;
	}

	/**
	 * ヘッダー：債権計上の取引先の取得
	 * 
	 * @return headerSaleCustomerWord ヘッダー：債権計上の取引先
	 */
	public String getHeaderSaleCustomerWord() {
		return headerSaleCustomerWord;
	}

	/**
	 * ヘッダー：債権計上の取引先の設定
	 * 
	 * @param headerSaleCustomerWord ヘッダー：債権計上の取引先
	 */
	public void setHeaderSaleCustomerWord(String headerSaleCustomerWord) {
		this.headerSaleCustomerWord = headerSaleCustomerWord;
	}

	/**
	 * ヘッダー：債権計上の入金予定日の取得
	 * 
	 * @return headerSaleDateWord ヘッダー：債権計上の入金予定日
	 */
	public String getHeaderSaleDateWord() {
		return headerSaleDateWord;
	}

	/**
	 * ヘッダー：債権計上の入金予定日の設定
	 * 
	 * @param headerSaleDateWord ヘッダー：債権計上の入金予定日
	 */
	public void setHeaderSaleDateWord(String headerSaleDateWord) {
		this.headerSaleDateWord = headerSaleDateWord;
	}

	/**
	 * ヘッダー：債権計上の振込口座の取得
	 * 
	 * @return headerSaleBankAccountWord ヘッダー：債権計上の振込口座
	 */
	public String getHeaderSaleBankAccountWord() {
		return headerSaleBankAccountWord;
	}

	/**
	 * ヘッダー：債権計上の振込口座の設定
	 * 
	 * @param headerSaleBankAccountWord ヘッダー：債権計上の振込口座
	 */
	public void setHeaderSaleBankAccountWord(String headerSaleBankAccountWord) {
		this.headerSaleBankAccountWord = headerSaleBankAccountWord;
	}

	/**
	 * ヘッダー：債権計上の請求金額の取得
	 * 
	 * @return headerSaleAmountWord ヘッダー：債権計上の請求金額
	 */
	public String getHeaderSaleAmountWord() {
		return headerSaleAmountWord;
	}

	/**
	 * ヘッダー：債権計上の請求金額の設定
	 * 
	 * @param headerSaleAmountWord ヘッダー：債権計上の請求金額
	 */
	public void setHeaderSaleAmountWord(String headerSaleAmountWord) {
		this.headerSaleAmountWord = headerSaleAmountWord;
	}

	/**
	 * ヘッダー：経費精算の社員の取得
	 * 
	 * @return headerSettlementEmployeeWord ヘッダー：経費精算の社員
	 */
	public String getHeaderSettlementEmployeeWord() {
		return headerSettlementEmployeeWord;
	}

	/**
	 * ヘッダー：経費精算の社員の設定
	 * 
	 * @param headerSettlementEmployeeWord ヘッダー：経費精算の社員
	 */
	public void setHeaderSettlementEmployeeWord(String headerSettlementEmployeeWord) {
		this.headerSettlementEmployeeWord = headerSettlementEmployeeWord;
	}

	/**
	 * ヘッダー：経費精算の経費等合計の取得
	 * 
	 * @return headerSettlementAmountSumWord ヘッダー：経費精算の経費等合計
	 */
	public String getHeaderSettlementAmountSumWord() {
		return headerSettlementAmountSumWord;
	}

	/**
	 * ヘッダー：経費精算の経費等合計の設定
	 * 
	 * @param headerSettlementAmountSumWord ヘッダー：経費精算の経費等合計
	 */
	public void setHeaderSettlementAmountSumWord(String headerSettlementAmountSumWord) {
		this.headerSettlementAmountSumWord = headerSettlementAmountSumWord;
	}

	/**
	 * ヘッダー：経費精算の支払日の取得
	 * 
	 * @return headerSettlementPaymentDateWord ヘッダー：経費精算の支払日
	 */
	public String getHeaderSettlementPaymentDateWord() {
		return headerSettlementPaymentDateWord;
	}

	/**
	 * ヘッダー：経費精算の支払日の設定
	 * 
	 * @param headerSettlementPaymentDateWord ヘッダー：経費精算の支払日
	 */
	public void setHeaderSettlementPaymentDateWord(String headerSettlementPaymentDateWord) {
		this.headerSettlementPaymentDateWord = headerSettlementPaymentDateWord;
	}

	/**
	 * ヘッダー：経費精算の仮払金額の取得
	 * 
	 * @return headerSettlementPrevAmountWord ヘッダー：経費精算の仮払金額
	 */
	public String getHeaderSettlementPrevAmountWord() {
		return headerSettlementPrevAmountWord;
	}

	/**
	 * ヘッダー：経費精算の仮払金額の設定
	 * 
	 * @param headerSettlementPrevAmountWord ヘッダー：経費精算の仮払金額
	 */
	public void setHeaderSettlementPrevAmountWord(String headerSettlementPrevAmountWord) {
		this.headerSettlementPrevAmountWord = headerSettlementPrevAmountWord;
	}

	/**
	 * ヘッダー：経費精算の支払方法の取得
	 * 
	 * @return headerSettlementPayMethodWord ヘッダー：経費精算の支払方法
	 */
	public String getHeaderSettlementPayMethodWord() {
		return headerSettlementPayMethodWord;
	}

	/**
	 * ヘッダー：経費精算の支払方法の設定
	 * 
	 * @param headerSettlementPayMethodWord ヘッダー：経費精算の支払方法
	 */
	public void setHeaderSettlementPayMethodWord(String headerSettlementPayMethodWord) {
		this.headerSettlementPayMethodWord = headerSettlementPayMethodWord;
	}

	/**
	 * ヘッダー：経費精算の精算金額の取得
	 * 
	 * @return headerSettlementAmountWord ヘッダー：経費精算の精算金額
	 */
	public String getHeaderSettlementAmountWord() {
		return headerSettlementAmountWord;
	}

	/**
	 * ヘッダー：経費精算の精算金額の設定
	 * 
	 * @param headerSettlementAmountWord ヘッダー：経費精算の精算金額
	 */
	public void setHeaderSettlementAmountWord(String headerSettlementAmountWord) {
		this.headerSettlementAmountWord = headerSettlementAmountWord;
	}

	/**
	 * ヘッダー：経費精算の振出銀行の取得
	 * 
	 * @return headerSettlementBankWord ヘッダー：経費精算の振出銀行
	 */
	public String getHeaderSettlementBankWord() {
		return headerSettlementBankWord;
	}

	/**
	 * ヘッダー：経費精算の振出銀行の設定
	 * 
	 * @param headerSettlementBankWord ヘッダー：経費精算の振出銀行
	 */
	public void setHeaderSettlementBankWord(String headerSettlementBankWord) {
		this.headerSettlementBankWord = headerSettlementBankWord;
	}

	/**
	 * ヘッダー伝票摘要コード非表示の取得
	 * 
	 * @return notShowHeaderSlipRemarkCode ヘッダー伝票摘要コード非表示
	 */
	public boolean isNotShowHeaderSlipRemarkCode() {
		return notShowHeaderSlipRemarkCode;
	}

	/**
	 * ヘッダー伝票摘要コード非表示の設定
	 * 
	 * @param notShowHeaderSlipRemarkCode ヘッダー伝票摘要コード非表示
	 */
	public void setNotShowHeaderSlipRemarkCode(boolean notShowHeaderSlipRemarkCode) {
		this.notShowHeaderSlipRemarkCode = notShowHeaderSlipRemarkCode;
	}

	/**
	 * ヘッダー入力部門コード非表示の取得
	 * 
	 * @return notShowHeaderDepartmentCode ヘッダー入力部門コード非表示
	 */
	public boolean isNotShowHeaderDepartmentCode() {
		return notShowHeaderDepartmentCode;
	}

	/**
	 * ヘッダー入力部門コード非表示の設定
	 * 
	 * @param notShowHeaderDepartmentCode ヘッダー入力部門コード非表示
	 */
	public void setNotShowHeaderDepartmentCode(boolean notShowHeaderDepartmentCode) {
		this.notShowHeaderDepartmentCode = notShowHeaderDepartmentCode;
	}

	/**
	 * ヘッダー入力社員コード非表示の取得
	 * 
	 * @return notShowHeaderEmployeeCode ヘッダー入力社員コード非表示
	 */
	public boolean isNotShowHeaderEmployeeCode() {
		return notShowHeaderEmployeeCode;
	}

	/**
	 * ヘッダー入力社員コード非表示の設定
	 * 
	 * @param notShowHeaderEmployeeCode ヘッダー入力社員コード非表示
	 */
	public void setNotShowHeaderEmployeeCode(boolean notShowHeaderEmployeeCode) {
		this.notShowHeaderEmployeeCode = notShowHeaderEmployeeCode;
	}

	/**
	 * 部門コード非表示の取得
	 * 
	 * @return notShowDepartmentCode 部門コード非表示
	 */
	public boolean isNotShowDepartmentCode() {
		return notShowDepartmentCode;
	}

	/**
	 * 部門コード非表示の設定
	 * 
	 * @param notShowDepartmentCode 部門コード非表示
	 */
	public void setNotShowDepartmentCode(boolean notShowDepartmentCode) {
		this.notShowDepartmentCode = notShowDepartmentCode;
	}

	/**
	 * 科目コード非表示の取得
	 * 
	 * @return notShowItemCode 科目コード非表示
	 */
	public boolean isNotShowItemCode() {
		return notShowItemCode;
	}

	/**
	 * 科目コード非表示の設定
	 * 
	 * @param notShowItemCode 科目コード非表示
	 */
	public void setNotShowItemCode(boolean notShowItemCode) {
		this.notShowItemCode = notShowItemCode;
	}

	/**
	 * 補助科目コード非表示の取得
	 * 
	 * @return notShowSubItemCode 補助科目コード非表示
	 */
	public boolean isNotShowSubItemCode() {
		return notShowSubItemCode;
	}

	/**
	 * 補助科目コード非表示の設定
	 * 
	 * @param notShowSubItemCode 補助科目コード非表示
	 */
	public void setNotShowSubItemCode(boolean notShowSubItemCode) {
		this.notShowSubItemCode = notShowSubItemCode;
	}

	/**
	 * 内訳科目コード非表示の取得
	 * 
	 * @return notShowDetailItemCode 内訳科目コード非表示
	 */
	public boolean isNotShowDetailItemCode() {
		return notShowDetailItemCode;
	}

	/**
	 * 内訳科目コード非表示の設定
	 * 
	 * @param notShowDetailItemCode 内訳科目コード非表示
	 */
	public void setNotShowDetailItemCode(boolean notShowDetailItemCode) {
		this.notShowDetailItemCode = notShowDetailItemCode;
	}

	/**
	 * 取引先コード非表示の取得
	 * 
	 * @return notShowCustomerCode 取引先コード非表示
	 */
	public boolean isNotShowCustomerCode() {
		return notShowCustomerCode;
	}

	/**
	 * 取引先コード非表示の設定
	 * 
	 * @param notShowCustomerCode 取引先コード非表示
	 */
	public void setNotShowCustomerCode(boolean notShowCustomerCode) {
		this.notShowCustomerCode = notShowCustomerCode;
	}

	/**
	 * 社員コード非表示の取得
	 * 
	 * @return notShowEmployeeCode 社員コード非表示
	 */
	public boolean isNotShowEmployeeCode() {
		return notShowEmployeeCode;
	}

	/**
	 * 社員コード非表示の設定
	 * 
	 * @param notShowEmployeeCode 社員コード非表示
	 */
	public void setNotShowEmployeeCode(boolean notShowEmployeeCode) {
		this.notShowEmployeeCode = notShowEmployeeCode;
	}

	/**
	 * 消費税コード非表示の取得
	 * 
	 * @return notShowTaxCode 消費税コード非表示
	 */
	public boolean isNotShowTaxCode() {
		return notShowTaxCode;
	}

	/**
	 * 消費税コード非表示の設定
	 * 
	 * @param notShowTaxCode 消費税コード非表示
	 */
	public void setNotShowTaxCode(boolean notShowTaxCode) {
		this.notShowTaxCode = notShowTaxCode;
	}

	/**
	 * 管理1コード非表示の取得
	 * 
	 * @return notShowManagementCode1 管理1コード非表示
	 */
	public boolean isNotShowManagementCode1() {
		return notShowManagementCode1;
	}

	/**
	 * 管理1コード非表示の設定
	 * 
	 * @param notShowManagementCode1 管理1コード非表示
	 */
	public void setNotShowManagementCode1(boolean notShowManagementCode1) {
		this.notShowManagementCode1 = notShowManagementCode1;
	}

	/**
	 * 管理2コード非表示の取得
	 * 
	 * @return notShowManagementCode2 管理2コード非表示
	 */
	public boolean isNotShowManagementCode2() {
		return notShowManagementCode2;
	}

	/**
	 * 管理2コード非表示の設定
	 * 
	 * @param notShowManagementCode2 管理2コード非表示
	 */
	public void setNotShowManagementCode2(boolean notShowManagementCode2) {
		this.notShowManagementCode2 = notShowManagementCode2;
	}

	/**
	 * 管理3コード非表示の取得
	 * 
	 * @return notShowManagementCode3 管理3コード非表示
	 */
	public boolean isNotShowManagementCode3() {
		return notShowManagementCode3;
	}

	/**
	 * 管理3コード非表示の設定
	 * 
	 * @param notShowManagementCode3 管理3コード非表示
	 */
	public void setNotShowManagementCode3(boolean notShowManagementCode3) {
		this.notShowManagementCode3 = notShowManagementCode3;
	}

	/**
	 * 管理4コード非表示の取得
	 * 
	 * @return notShowManagementCode4 管理4コード非表示
	 */
	public boolean isNotShowManagementCode4() {
		return notShowManagementCode4;
	}

	/**
	 * 管理4コード非表示の設定
	 * 
	 * @param notShowManagementCode4 管理4コード非表示
	 */
	public void setNotShowManagementCode4(boolean notShowManagementCode4) {
		this.notShowManagementCode4 = notShowManagementCode4;
	}

	/**
	 * 管理5コード非表示の取得
	 * 
	 * @return notShowManagementCode5 管理5コード非表示
	 */
	public boolean isNotShowManagementCode5() {
		return notShowManagementCode5;
	}

	/**
	 * 管理5コード非表示の設定
	 * 
	 * @param notShowManagementCode5 管理5コード非表示
	 */
	public void setNotShowManagementCode5(boolean notShowManagementCode5) {
		this.notShowManagementCode5 = notShowManagementCode5;
	}

	/**
	 * 管理6コード非表示の取得
	 * 
	 * @return notShowManagementCode6 管理6コード非表示
	 */
	public boolean isNotShowManagementCode6() {
		return notShowManagementCode6;
	}

	/**
	 * 管理6コード非表示の設定
	 * 
	 * @param notShowManagementCode6 管理6コード非表示
	 */
	public void setNotShowManagementCode6(boolean notShowManagementCode6) {
		this.notShowManagementCode6 = notShowManagementCode6;
	}

}

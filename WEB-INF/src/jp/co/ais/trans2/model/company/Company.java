package jp.co.ais.trans2.model.company;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 会社情報
 * 
 * @author AIS
 */
public class Company extends TransferBase implements TReferable {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(code).append(" ").append(name);
		sb.append(",conf=").append(accountConfig);
		sb.append(",fp=").append(fiscalPeriod);
		return sb.toString();
	}

	/** 会社コード */
	protected String code = null;

	/** 会社名称 */
	protected String name = null;

	/** 会社略称 */
	protected String names = null;

	/** 住所1 */
	protected String address1 = null;

	/** 住所2 */
	protected String address2 = null;

	/** 住所カナ */
	protected String addressKana = null;

	/** 郵便番号 */
	protected String zip = null;

	/** 電話番号 */
	protected String phoneNo = null;

	/** FAX番号 */
	protected String faxNo = null;

	/** 適格請求書発行事業者登録番号番号 */
	protected String invRegNo = null;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** 会社背景色 */
	protected Color color = null;

	/** 会計設定情報 */
	protected AccountConfig accountConfig = null;

	/** 会計期間情報 */
	protected FiscalPeriod fiscalPeriod = null;

	/** 連結用会社コード */
	protected String connCompanyCode = null;

	/** サイン者・役職名 */
	protected String signerPosition = null;

	/** 外国送金担当者 */
	protected String remitterName = null;

	/** 連絡用電話番号 */
	protected String connPhoneNo = null;

	/** DEBITNOTE住所1 */
	protected String debitNoteAddress1 = null;

	/** DEBITNOTE住所2 */
	protected String debitNoteAddress2 = null;

	/** DEBITNOTE住所3 */
	protected String debitNoteAddress3 = null;

	/** DEBITNOTEフッタ情報 */
	protected String debitNoteInfo = null;

	/** 円通貨 */
	protected Currency jPY = null;

	/** ドル通貨 */
	protected Currency uSD = null;

	/** 会社名称(英字) */
	protected String KAI_NAME_ENG = null;

	/** 会社略称(英字) */
	protected String KAI_NAME_S_ENG = null;

	/** 会社住所1(英字) */
	protected String JYU_1_ENG = null;

	/** 会社住所2(英字) */
	protected String JYU_2_ENG = null;

	/** 会社住所3(英字) */
	protected String JYU_3_ENG = null;

	/** 英文請求書SIGNER欄（債権管理） */
	protected String AR_SIGN_NAME = null;

	/** インボイス制度チェックフラグ */
	protected boolean CMP_INV_CHK_FLG = false;

	/**
	 * コンストラクタ.
	 */
	public Company() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param code 会社コード
	 */
	public Company(String code) {
		this();
		this.code = code;
	}

	/**
	 * @return fiscalPeriodを戻します。
	 */
	public FiscalPeriod getFiscalPeriod() {
		return fiscalPeriod;
	}

	/**
	 * @param fiscalPeriod fiscalPeriodを設定します。
	 */
	public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	/**
	 * @return address1を戻します。
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 address1を設定します。
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return address2を戻します。
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 address2を設定します。
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return addressKanaを戻します。
	 */
	public String getAddressKana() {
		return addressKana;
	}

	/**
	 * @param addressKana addressKanaを設定します。
	 */
	public void setAddressKana(String addressKana) {
		this.addressKana = addressKana;
	}

	/**
	 * @return faxNoを戻します。
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * @param faxNo faxNoを設定します。
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * @return invRegNoを戻します。
	 */
	public String getInvRegNo() {
		return invRegNo;
	}

	/**
	 * @param invRegNo invRegNoを設定します。
	 */
	public void setInvRegNo(String invRegNo) {
		this.invRegNo = invRegNo;
	}

	/**
	 * @return phoneNoを戻します。
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo phoneNoを設定します。
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return zipを戻します。
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip zipを設定します。
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return dateFromを戻します。
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom dateFromを設定します。
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return dateToを戻します。
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo dateToを設定します。
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return codeを戻します。
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code codeを設定します。
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 会計設定情報を戻します。
	 */
	public AccountConfig getAccountConfig() {
		return accountConfig;
	}

	/**
	 * @param accountConfig 会計設定情報を設定します。
	 */
	public void setAccountConfig(AccountConfig accountConfig) {
		this.accountConfig = accountConfig;
	}

	/**
	 * @return nameを戻します。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name nameを設定します。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return namesを戻します。
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names namesを設定します。
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * @return colorを戻します。
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color colorを設定します。
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * 検索名称
	 * 
	 * @return 検索名称
	 */
	public String getNamek() {
		return null;
	}

	/**
	 * 連結用会社コードの取得
	 * 
	 * @return connCompanyCode 連結用会社コード
	 */
	public String getConnCompanyCode() {
		return connCompanyCode;
	}

	/**
	 * 連結用会社コードの設定
	 * 
	 * @param connCompanyCode 連結用会社コード
	 */
	public void setConnCompanyCode(String connCompanyCode) {
		this.connCompanyCode = connCompanyCode;
	}

	/**
	 * サイン者・役職名の取得
	 * 
	 * @return signerPosition サイン者・役職名
	 */
	public String getSignerPosition() {
		return signerPosition;
	}

	/**
	 * サイン者・役職名の設定
	 * 
	 * @param signerPosition サイン者・役職名
	 */
	public void setSignerPosition(String signerPosition) {
		this.signerPosition = signerPosition;
	}

	/**
	 * 外国送金担当者の取得
	 * 
	 * @return remitterName 外国送金担当者
	 */
	public String getRemitterName() {
		return remitterName;
	}

	/**
	 * 外国送金担当者の設定
	 * 
	 * @param remitterName 外国送金担当者
	 */
	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	/**
	 * 連絡用電話番号の取得
	 * 
	 * @return connPhoneNo 連絡用電話番号
	 */
	public String getConnPhoneNo() {
		return connPhoneNo;
	}

	/**
	 * 連絡用電話番号の設定
	 * 
	 * @param connPhoneNo 連絡用電話番号
	 */
	public void setConnPhoneNo(String connPhoneNo) {
		this.connPhoneNo = connPhoneNo;
	}

	/**
	 * DEBITNOTE住所1の取得
	 * 
	 * @return DebitNoteAddress1 DEBITNOTE住所1
	 */
	public String getDebitNoteAddress1() {
		return debitNoteAddress1;
	}

	/**
	 * DEBITNOTE住所1の設定
	 * 
	 * @param debitNoteAddress1 DEBITNOTE住所1
	 */
	public void setDebitNoteAddress1(String debitNoteAddress1) {
		this.debitNoteAddress1 = debitNoteAddress1;
	}

	/**
	 * DEBITNOTE住所2の取得
	 * 
	 * @return debitNoteAddress2 DEBITNOTE住所2
	 */
	public String getDebitNoteAddress2() {
		return debitNoteAddress2;
	}

	/**
	 * DEBITNOTE住所2の設定
	 * 
	 * @param debitNoteAddress2 DEBITNOTE住所2
	 */
	public void setDebitNoteAddress2(String debitNoteAddress2) {
		this.debitNoteAddress2 = debitNoteAddress2;
	}

	/**
	 * DEBITNOTE住所3の取得
	 * 
	 * @return debitNoteAddress3 DEBITNOTE住所3
	 */
	public String getDebitNoteAddress3() {
		return debitNoteAddress3;
	}

	/**
	 * DEBITNOTE住所3の設定
	 * 
	 * @param debitNoteAddress3 DEBITNOTE住所3
	 */
	public void setDebitNoteAddress3(String debitNoteAddress3) {
		this.debitNoteAddress3 = debitNoteAddress3;
	}

	/**
	 * DEBITNOTEフッタ情報の取得
	 * 
	 * @return debitNoteInfo DEBITNOTEフッタ情報
	 */
	public String getDebitNoteInfo() {
		return debitNoteInfo;
	}

	/**
	 * DEBITNOTEフッタ情報の設定
	 * 
	 * @param debitNoteInfo DEBITNOTEフッタ情報
	 */
	public void setDebitNoteInfo(String debitNoteInfo) {
		this.debitNoteInfo = debitNoteInfo;
	}

	/**
	 * 円通貨の取得
	 * 
	 * @return jPY 円通貨
	 */
	public Currency getJPY() {
		return jPY;
	}

	/**
	 * 円通貨の設定
	 * 
	 * @param jPY 円通貨
	 */
	public void setJPY(Currency jPY) {
		this.jPY = jPY;
	}

	/**
	 * ドル通貨の取得
	 * 
	 * @return uSD ドル通貨
	 */
	public Currency getUSD() {
		return uSD;
	}

	/**
	 * ドル通貨の設定
	 * 
	 * @param uSD ドル通貨
	 */
	public void setUSD(Currency uSD) {
		this.uSD = uSD;
	}

	/**
	 * 会社名称(英字)の取得
	 * 
	 * @return KAI_NAME_ENG 会社名称(英字)
	 */
	public String getKAI_NAME_ENG() {
		return KAI_NAME_ENG;
	}

	/**
	 * 会社名称(英字)の設定
	 * 
	 * @param KAI_NAME_ENG 会社名称(英字)
	 */
	public void setKAI_NAME_ENG(String KAI_NAME_ENG) {
		this.KAI_NAME_ENG = KAI_NAME_ENG;
	}

	/**
	 * 会社略称(英字)の取得
	 * 
	 * @return KAI_NAME_S_ENG 会社略称(英字)
	 */
	public String getKAI_NAME_S_ENG() {
		return KAI_NAME_S_ENG;
	}

	/**
	 * 会社略称(英字)の設定
	 * 
	 * @param KAI_NAME_S_ENG 会社略称(英字)
	 */
	public void setKAI_NAME_S_ENG(String KAI_NAME_S_ENG) {
		this.KAI_NAME_S_ENG = KAI_NAME_S_ENG;
	}

	/**
	 * 会社住所1(英字)の取得
	 * 
	 * @return JYU_1_ENG 会社住所1(英字)
	 */
	public String getJYU_1_ENG() {
		return JYU_1_ENG;
	}

	/**
	 * 会社住所1(英字)の設定
	 * 
	 * @param JYU_1_ENG 会社住所1(英字)
	 */
	public void setJYU_1_ENG(String JYU_1_ENG) {
		this.JYU_1_ENG = JYU_1_ENG;
	}

	/**
	 * 会社住所2(英字)の取得
	 * 
	 * @return JYU_2_ENG 会社住所2(英字)
	 */
	public String getJYU_2_ENG() {
		return JYU_2_ENG;
	}

	/**
	 * 会社住所2(英字)の設定
	 * 
	 * @param JYU_2_ENG 会社住所2(英字)
	 */
	public void setJYU_2_ENG(String JYU_2_ENG) {
		this.JYU_2_ENG = JYU_2_ENG;
	}

	/**
	 * 会社住所3(英字)の取得
	 * 
	 * @return JYU_3_ENG 会社住所3(英字)
	 */
	public String getJYU_3_ENG() {
		return JYU_3_ENG;
	}

	/**
	 * 会社住所3(英字)の設定
	 * 
	 * @param JYU_3_ENG 会社住所3(英字)
	 */
	public void setJYU_3_ENG(String JYU_3_ENG) {
		this.JYU_3_ENG = JYU_3_ENG;
	}

	/**
	 * 英文請求書SIGNER欄（債権管理） の取得
	 * 
	 * @return AR_SIGN_NAME 英文請求書SIGNER欄（債権管理）
	 */
	public String getAR_SIGN_NAME() {
		return AR_SIGN_NAME;
	}

	/**
	 * 英文請求書SIGNER欄（債権管理） の設定
	 * 
	 * @param AR_SIGN_NAME 英文請求書SIGNER欄（債権管理）
	 */
	public void setAR_SIGN_NAME(String AR_SIGN_NAME) {
		this.AR_SIGN_NAME = AR_SIGN_NAME;
	}
	
	/**
	 * インボイス制度チェックフラグの取得
	 * 
	 * @return CMP_INV_CHK_FLG インボイス制度チェックフラグ
	 */ 
	public boolean isCMP_INV_CHK_FLG() { 
	     return CMP_INV_CHK_FLG;
	}
	/**
	 * インボイス制度チェックフラグの設定
	 * 
	 * @param CMP_INV_CHK_FLG インボイス制度チェックフラグ
	 */
	public void setCMP_INV_CHK_FLG(boolean CMP_INV_CHK_FLG) {
	     this.CMP_INV_CHK_FLG = CMP_INV_CHK_FLG;
	}

}

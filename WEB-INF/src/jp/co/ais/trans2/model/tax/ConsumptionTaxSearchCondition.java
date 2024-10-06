package jp.co.ais.trans2.model.tax;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * 消費税マスタ検索条件
 */
public class ConsumptionTaxSearchCondition extends TransferBase implements Cloneable, FilterableCondition,
	OPLoginCondition {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** 売上課税を含めるか */
	protected boolean hasSales;

	/** 仕入課税を含めるか */
	protected boolean hasPurcharse;

	/** 仕入消費税のみ取得(インボイス取引先用) */
	protected boolean purcharseOnliy = false;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** 現在件数 */
	protected int nowCount = 0;

	/** 非適格請求書発行事業者フラグ */
	protected boolean noInvRegFlg = false;

	/** インボイス制度フラグ */
	protected boolean invoiceFlg = false;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConsumptionTaxSearchCondition clone() {
		try {
			return (ConsumptionTaxSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * @return codeFromを戻します。
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFromを設定します。
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeToを戻します。
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeToを設定します。
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return companyCodeを戻します。
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCodeを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return validTermを戻します。
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm validTermを設定します。
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * 売上課税を含めるか
	 * 
	 * @param hasSales true:含める
	 */
	public void setHasSales(boolean hasSales) {
		this.hasSales = hasSales;
	}

	/**
	 * 売上課税を含めるか
	 * 
	 * @return true:含める
	 */
	public boolean isHasSales() {
		return hasSales;
	}

	/**
	 * 仕入課税を含めるか
	 * 
	 * @param hasPurcharse true:含める
	 */
	public void setHasPurcharse(boolean hasPurcharse) {
		this.hasPurcharse = hasPurcharse;
	}

	/**
	 * 仕入課税を含めるか
	 * 
	 * @return true:含める
	 */
	public boolean isHasPurcharse() {
		return hasPurcharse;
	}

	/**
	 * 仕入消費税のみ取得(インボイス取引先用)の取得
	 * 
	 * @return purcharseOnliy 仕入消費税のみ取得(インボイス取引先用)
	 */ 
	public boolean isPurcharseOnliy() { 
	     return purcharseOnliy;
	}
	/**
	 * 仕入消費税のみ取得(インボイス取引先用)の設定
	 * 
	 * @param purcharseOnliy 仕入消費税のみ取得(インボイス取引先用)
	 */
	public void setPurcharseOnliy(boolean purcharseOnliy) {
	     this.purcharseOnliy = purcharseOnliy;
	}


	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getCodeList()
	 */
	public List<String> getCodeList() {
		return null;
	}

	/**
	 * 最終更新日時の取得
	 * 
	 * @return lastUpdateDate 最終更新日時
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * 最終更新日時の設定
	 * 
	 * @param lastUpdateDate 最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * 現在件数の取得
	 * 
	 * @return nowCount 現在件数
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * 現在件数の設定
	 * 
	 * @param nowCount 現在件数
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

	/**
	 * 非適格請求書発行事業者フラグの取得
	 * 
	 * @return noInvRegFlg 非適格請求書発行事業者フラグ
	 */
	public boolean isNoInvRegFlg() {
		return noInvRegFlg;
	}

	/**
	 * 非適格請求書発行事業者フラグの設定
	 * 
	 * @param noInvRegFlg 非適格請求書発行事業者フラグ
	 */
	public void setNoInvRegFlg(boolean noInvRegFlg) {
		this.noInvRegFlg = noInvRegFlg;
	}

	/**
	 * インボイス制度フラグの取得
	 * 
	 * @return invoiceFlg インボイス制度フラグ
	 */
	public boolean isInvoiceFlg() {
		return invoiceFlg;
	}

	/**
	 * インボイス制度フラグの設定
	 * 
	 * @param invoiceFlg インボイス制度フラグ
	 */
	public void setInvoiceFlg(boolean invoiceFlg) {
		this.invoiceFlg = invoiceFlg;
	}

}

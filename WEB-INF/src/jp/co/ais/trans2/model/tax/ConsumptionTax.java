package jp.co.ais.trans2.model.tax;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * 消費税情報
 */
public class ConsumptionTax extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

	@Override
	public String toString() {
		return getCode() + " " + getName();
	}

	/**
	 * @return 表示名
	 */
	public String getDisplayText() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCode());
		sb.append(" / ");
		sb.append(Util.avoidNull(getName()));
		sb.append(" / ");
		sb.append(Util.avoidNull(getNames()));

		return sb.toString();
	}

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** 略称 */
	protected String names = null;

	/** 検索名称 */
	protected String namek = null;

	/** 消費税レート */
	protected BigDecimal rate;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** 税区分 */
	protected TaxType taxType = null;

	/** 消費税計算書か 追加 */
	protected boolean zeiTaxConsumption = false;

	/** 出力順序 追加 */
	protected String odr = null;

	/** 非適格請求書発行事業者フラグ */
	protected boolean NO_INV_REG_FLG = false;

	/** 経過措置控除可能率(1%～99%) */
	protected BigDecimal KEKA_SOTI_RATE = null;

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
	 * @return namekを戻します。
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek namekを設定します。
	 */
	public void setNamek(String namek) {
		this.namek = namek;
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
	 * 消費税レート
	 * 
	 * @return 消費税レート
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * 消費税レート
	 * 
	 * @param rate 消費税レート
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * 消費税計算書か
	 * 
	 * @return true:消費税計算書 追加
	 */
	public boolean isTaxConsumption() {
		return zeiTaxConsumption;
	}

	/**
	 * 消費税計算書か
	 * 
	 * @param zieTaxConsumption true:消費税計算書 追加
	 */
	public void setTaxConsumption(boolean zieTaxConsumption) {
		this.zeiTaxConsumption = zieTaxConsumption;
	}

	/**
	 * 税区分
	 * 
	 * @return taxType 税区分
	 */
	public TaxType getTaxType() {
		return taxType;
	}

	/**
	 * 税区分
	 * 
	 * @param taxType 税区分
	 */
	public void setTaxType(TaxType taxType) {
		this.taxType = taxType;
	}

	/**
	 * 出力順序を取得 追加
	 * 
	 * @return odr
	 */
	public String getOdr() {
		return odr;
	}

	/**
	 * 出力順序をセット 追加
	 * 
	 * @param odr
	 */
	public void setOdr(String odr) {
		this.odr = odr;
	}

	/**
	 * 非適格請求書発行事業者フラグの取得
	 * 
	 * @return NO_INV_REG_FLG 非適格請求書発行事業者フラグ
	 */
	public boolean isNO_INV_REG_FLG() {
		return NO_INV_REG_FLG;
	}

	/**
	 * 非適格請求書発行事業者フラグの設定
	 * 
	 * @param NO_INV_REG_FLG 非適格請求書発行事業者フラグ
	 */
	public void setNO_INV_REG_FLG(boolean NO_INV_REG_FLG) {
		this.NO_INV_REG_FLG = NO_INV_REG_FLG;
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

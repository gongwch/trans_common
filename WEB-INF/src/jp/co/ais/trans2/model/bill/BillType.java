package jp.co.ais.trans2.model.bill;

import java.util.Date;
import jp.co.ais.trans.common.dt.TransferBase;

/**
 * 請求区分マスタ情報
 * 
 * @author AIS
 */
public class BillType extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 1201636486328477363L;

	/** 会社コード */
	protected String companyCode = null;

	/** 請求区分 */
	protected String code = null;

	/** 請求書フォーマット */
	protected String format = null;

	/** 名称 */
	protected String name = null;

	/** 検索名称 */
	protected String namek = null;

	/** 明細件数 */
	protected int detailCount = 1;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** 登録日付 */
	protected Date inpDate;

	/** 更新日付 */
	protected Date updDate;

	/** プログラムID */
	protected String prgId;

	/** ユーザーID */
	protected String usrId;

	/**
	 * codeを取得する。
	 * 
	 * @return String code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * codeを設定する。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * companyCodeを取得する。
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCodeを設定する。
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * dateFromを取得する。
	 * 
	 * @return Date dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * dateFromを設定する。
	 * 
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * dateToを取得する。
	 * 
	 * @return Date dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * dateToを設定する。
	 * 
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * formを取得する。
	 * 
	 * @return String form
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * formを設定する。
	 * 
	 * @param form
	 */
	public void setFormat(String form) {
		this.format = form;
	}

	/**
	 * inpDateを取得する。
	 * 
	 * @return Date inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * inpDateを設定する。
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * nameを取得する。
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * nameを設定する。
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * namekを取得する。
	 * 
	 * @return String namek
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * namekを設定する。
	 * 
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * prgIdを取得する。
	 * 
	 * @return String prgId
	 */
	public String getPrgId() {
		return prgId;
	}

	/**
	 * prgIdを設定する。
	 * 
	 * @param prgId
	 */
	public void setPrgId(String prgId) {
		this.prgId = prgId;
	}

	/**
	 * updDateを取得する。
	 * 
	 * @return Date updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * updDateを設定する。
	 * 
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * usrIdを取得する。
	 * 
	 * @return String usrId
	 */
	public String getUsrId() {
		return usrId;
	}

	/**
	 * usrIdを設定する。
	 * 
	 * @param usrId
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	/**
	 * 明細件数の取得
	 * 
	 * @return detailCount 明細件数
	 */
	public int getDetailCount() {
		return detailCount;
	}

	/**
	 * 明細件数の設定
	 * 
	 * @param detailCount 明細件数
	 */
	public void setDetailCount(int detailCount) {
		this.detailCount = detailCount;
	}

}

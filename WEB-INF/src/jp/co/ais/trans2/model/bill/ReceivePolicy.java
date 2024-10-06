package jp.co.ais.trans2.model.bill;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 入金方針マスタ情報
 * 
 * @author AIS
 */
public class ReceivePolicy extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 入力フォルダ名 */
	protected String inFolder = null;

	/** 退避フォルダ名 */
	protected String outFolder = null;

	/** レコード長 */
	protected int length = 0;

	/** CR/LF区分 */
	protected String lineType = null;

	/** 手数料範囲開始金額 */
	protected BigDecimal feeFrom = null;

	/** 手数料範囲終了金額 */
	protected BigDecimal feeTo = null;

	/** 手数料科目コード */
	protected String itemCode = null;

	/** 手数料補助科目コード */
	protected String subItemCode = null;

	/** 手数料内訳科目コード */
	protected String detailCode = null;

	/** 手数料計上部門コード */
	protected String departmentCode = null;

	/** 手数料消費税コード */
	protected String taxCode = null;

	/** 請求書番号入力ﾌﾗｸﾞ */
	protected boolean compulsoryInputBillNo = false;

	/** 請求書作成ﾌﾗｸﾞ */
	protected boolean checkBillMake = false;

	/** 振込入金通知フォーマットタイプ */
	protected String format = null;

	/** 登録日付 */
	protected Date inpDate = null;

	/** 更新日付 */
	protected Date updDate = null;

	/** プログラムID */
	protected String prgId = null;

	/** ユーザーID */
	protected String usrId = null;

	/** 消費税情報 */
	protected ConsumptionTax tax = null;

	/** 請求書番号 自動採番を使用するか */
	protected boolean useInvNumbering = false;

	/** 自動採番部桁数 */
	protected int invNoDigit = 0;

	/** 自動採番項目1 */
	protected InvoiceNoAdopt invNoAdopt1 = null;

	/** 自動採番項目2 */
	protected InvoiceNoAdopt invNoAdopt2 = null;

	/** 自動採番項目3 */
	protected InvoiceNoAdopt invNoAdopt3 = null;

	/** 請求書項目1名称 */
	protected String invNo1Name = null;

	/** 請求書項目2名称 */
	protected String invNo2Name = null;

	/** 請求書項目3名称 */
	protected String invNo3Name = null;

	/**
	 * checkBillMakeを取得する。
	 * 
	 * @return boolean checkBillMake
	 */
	public boolean isCheckBillMake() {
		return checkBillMake;
	}

	/**
	 * checkBillMakeを設定する。
	 * 
	 * @param checkBillMake
	 */
	public void setCheckBillMake(boolean checkBillMake) {
		this.checkBillMake = checkBillMake;
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
	 * departmentCodeを取得する。
	 * 
	 * @return String departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * departmentCodeを設定する。
	 * 
	 * @param departmentCode
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * detailCodeを取得する。
	 * 
	 * @return String detailCode
	 */
	public String getDetailCode() {
		return detailCode;
	}

	/**
	 * detailCodeを設定する。
	 * 
	 * @param detailCode
	 */
	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}

	/**
	 * feeFromを取得する。
	 * 
	 * @return BigDecimal feeFrom
	 */
	public BigDecimal getFeeFrom() {
		return feeFrom;
	}

	/**
	 * feeFromを設定する。
	 * 
	 * @param feeFrom
	 */
	public void setFeeFrom(BigDecimal feeFrom) {
		this.feeFrom = feeFrom;
	}

	/**
	 * feeToを取得する。
	 * 
	 * @return BigDecimal feeTo
	 */
	public BigDecimal getFeeTo() {
		return feeTo;
	}

	/**
	 * feeToを設定する。
	 * 
	 * @param feeTo
	 */
	public void setFeeTo(BigDecimal feeTo) {
		this.feeTo = feeTo;
	}

	/**
	 * formatを取得する。
	 * 
	 * @return String format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * formatを設定する。
	 * 
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * inFolderを取得する。
	 * 
	 * @return String inFolder
	 */
	public String getInFolder() {
		return inFolder;
	}

	/**
	 * inFolderを設定する。
	 * 
	 * @param inFolder
	 */
	public void setInFolder(String inFolder) {
		this.inFolder = inFolder;
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
	 * compulsoryInputBillNoを取得する。
	 * 
	 * @return boolean compulsoryInputBillNo
	 */
	public boolean isCompulsoryInputBillNo() {
		return compulsoryInputBillNo;
	}

	/**
	 * compulsoryInputBillNoを設定する。
	 * 
	 * @param compulsoryInputBillNo
	 */
	public void setCompulsoryInputBillNo(boolean compulsoryInputBillNo) {
		this.compulsoryInputBillNo = compulsoryInputBillNo;
	}

	/**
	 * itemCodeを取得する。
	 * 
	 * @return String itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * itemCodeを設定する。
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * lengthを取得する。
	 * 
	 * @return String length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * lengthを設定する。
	 * 
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * lineTypeを取得する。
	 * 
	 * @return String lineType
	 */
	public String getLineType() {
		return lineType;
	}

	/**
	 * lineTypeを設定する。
	 * 
	 * @param lineType
	 */
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	/**
	 * outFolderを取得する。
	 * 
	 * @return String outFolder
	 */
	public String getOutFolder() {
		return outFolder;
	}

	/**
	 * outFolderを設定する。
	 * 
	 * @param outFolder
	 */
	public void setOutFolder(String outFolder) {
		this.outFolder = outFolder;
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
	 * subItemCodeを取得する。
	 * 
	 * @return String subItemCode
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * subItemCodeを設定する。
	 * 
	 * @param subItemCode
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * taxCodeを取得する。
	 * 
	 * @return String taxCode
	 */
	public String getTaxCode() {
		return taxCode;
	}

	/**
	 * taxCodeを設定する。
	 * 
	 * @param taxCode
	 */
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
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
	 * taxを取得する。
	 * 
	 * @return ConsumptionTax tax
	 */
	public ConsumptionTax getTax() {
		return tax;
	}

	/**
	 * taxを設定する。
	 * 
	 * @param tax
	 */
	public void setTax(ConsumptionTax tax) {
		this.tax = tax;
	}

	/**
	 * @return useInvNumberingを戻します。
	 */
	public boolean isInvNumbering() {
		return useInvNumbering;
	}

	/**
	 * @param useInvNumbering useInvNumberingを設定します。
	 */
	public void setInvNumbering(boolean useInvNumbering) {
		this.useInvNumbering = useInvNumbering;
	}

	/**
	 * @param invNoDigit invNoDigitを設定します。
	 */
	public void setInvNoDigit(int invNoDigit) {
		this.invNoDigit = invNoDigit;
	}

	/**
	 * @return invNoDigitを戻します。
	 */
	public int getInvNoDigit() {
		return invNoDigit;
	}

	/**
	 * 請求書項目1名称
	 * 
	 * @return 請求書項目1名称
	 */
	public String getInvNo1Name() {
		return invNo1Name;
	}

	/**
	 * 請求書項目1名称
	 * 
	 * @param invNo1Name 請求書項目 1名称
	 */
	public void setInvNo1Name(String invNo1Name) {
		this.invNo1Name = invNo1Name;
	}

	/**
	 * 請求書項目2名称
	 * 
	 * @return 請求書項目2名称
	 */
	public String getInvNo2Name() {
		return invNo2Name;
	}

	/**
	 * 請求書項目2名称
	 * 
	 * @param invNo2Name 請求書項目 2名称
	 */
	public void setInvNo2Name(String invNo2Name) {
		this.invNo2Name = invNo2Name;
	}

	/**
	 * 請求書項目3名称
	 * 
	 * @return 請求書項目3名称
	 */
	public String getInvNo3Name() {
		return invNo3Name;
	}

	/**
	 * 請求書項目3名称
	 * 
	 * @param invNo3Name 請求書項目 3名称
	 */
	public void setInvNo3Name(String invNo3Name) {
		this.invNo3Name = invNo3Name;
	}

	/**
	 * 自動採番項目1
	 * 
	 * @return 自動採番項目1
	 */
	public InvoiceNoAdopt getInvoiceNoAdopt1() {
		return invNoAdopt1;
	}

	/**
	 * 自動採番項目1
	 * 
	 * @param invNoAdopt1 自動採番項目1
	 */
	public void setInvoiceNoAdopt1(InvoiceNoAdopt invNoAdopt1) {
		this.invNoAdopt1 = invNoAdopt1;
	}

	/**
	 * 自動採番項目2
	 * 
	 * @return 自動採番項目2
	 */
	public InvoiceNoAdopt getInvoiceNoAdopt2() {
		return invNoAdopt2;
	}

	/**
	 * 自動採番項目2
	 * 
	 * @param invNoAdopt2 自動採番項目2
	 */
	public void setInvoiceNoAdopt2(InvoiceNoAdopt invNoAdopt2) {
		this.invNoAdopt2 = invNoAdopt2;
	}

	/**
	 * 自動採番項目3
	 * 
	 * @return 自動採番項目3
	 */
	public InvoiceNoAdopt getInvoiceNoAdopt3() {
		return invNoAdopt3;
	}

	/**
	 * 自動採番項目3
	 * 
	 * @param invNoAdopt3 自動採番項目3
	 */
	public void setInvoiceNoAdopt3(InvoiceNoAdopt invNoAdopt3) {
		this.invNoAdopt3 = invNoAdopt3;
	}
}

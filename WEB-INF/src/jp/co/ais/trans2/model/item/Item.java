package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 科目情報
 */
public class Item extends TransferBase implements Cloneable, TReferable {

	/** serialVersionUID */
	private static final long serialVersionUID = 6334305355484348314L;

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

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** 固定部門コード */
	protected String fixedDepartmentCode = null;

	/** 固定部門名称 */
	protected String fixedDepartmentName = null;

	/** 補助科目を持つか */
	protected boolean hasSubItem = false;

	/** 補助科目 */
	protected SubItem subItem = null;

	/** 取引先区分 */
	protected CustomerType clientType = null;

	/** 社員入力フラグ */
	protected boolean useEmployee = false;

	/** 管理１入力フラグ */
	protected boolean useManagement1 = false;

	/** 管理２入力フラグ */
	protected boolean useManagement2 = false;

	/** 管理３入力フラグ */
	protected boolean useManagement3 = false;

	/** 管理４入力フラグ */
	protected boolean useManagement4 = false;

	/** 管理５入力フラグ */
	protected boolean useManagement5 = false;

	/** 管理６入力フラグ */
	protected boolean useManagement6 = false;

	/** 非会計1入力フラグ */
	protected boolean useNonAccount1 = false;

	/** 非会計2入力フラグ */
	protected boolean useNonAccount2 = false;

	/** 非会計3入力フラグ */
	protected boolean useNonAccount3 = false;

	/** 売上課税入力フラグ */
	protected boolean useSalesTaxation = false;

	/** 仕入課税入力フラグ */
	protected boolean usePurchaseTaxation = false;

	/** 多通貨入力フラグ */
	protected boolean useForeignCurrency = false;

	/** 評価替対象フラグ */
	protected boolean doesCurrencyEvaluate = false;

	/** 消費税 */
	protected ConsumptionTax consumptionTax = null;

	/** 集計区分 */
	protected ItemSumType itemSumType = null;

	/** 貸借区分 */
	protected Dc dc = null;

	/** 科目種別 */
	protected ItemType itemType = null;

	/** ＧＬ科目制御区分 */
	protected GLType glType = null;

	/** ＡＰ科目制御区分 */
	protected APType apType = null;

	/** ＡＲ科目制御区分 */
	protected ARType arType = null;

	/** ＢＧ科目制御区分 */
	protected BGType bgType = null;

	/** 相殺科目制御区分 */
	protected boolean doesOffsetItem = false;

	/** BS勘定消込区分 */
	protected boolean doesBsOffset = false;

	/** 評価替対象フラグ */
	protected EvaluationMethod evaluationMethod = null;

	/** 入金伝票入力フラグ */
	protected boolean useInputCashFlowSlip = false;

	/** 出金伝票入力フラグ */
	protected boolean useOutputCashFlowSlip = false;

	/** 振替伝票入力フラグ */
	protected boolean useTransferSlip = false;

	/** 経費精算伝票入力フラグ */
	protected boolean useExpenseSettlementSlip = false;

	/** 債務計上伝票入力フラグ */
	protected boolean usePaymentAppropriateSlip = false;

	/** 債権計上伝票入力フラグ */
	protected boolean useReceivableAppropriateSlip = false;

	/** 債権消込伝票入力フラグ */
	protected boolean useReceivableErasingSlip = false;

	/** 資産計上伝票入力フラグ */
	protected boolean useAssetsEntrySlip = false;

	/** 支払依頼伝票入力フラグ */
	protected boolean usePaymentRequestSlip = false;

	/** 航海収支計算対象かどうか */
	protected boolean useVoyageCalculation = false;

	/** 発生日使用 */
	protected boolean useOccurDate = false;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Item clone() {
		try {
			Item clone = (Item) super.clone();

			if (hasSubItem && this.subItem != null) {
				clone.setSubItem(this.subItem.clone());
			}

			return clone;

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
	 * 補助科目
	 * 
	 * @return 補助科目
	 */
	public SubItem getSubItem() {
		return subItem;
	}

	/**
	 * 補助科目
	 * 
	 * @param subItem 補助科目
	 */
	public void setSubItem(SubItem subItem) {
		this.subItem = subItem;

		this.hasSubItem = subItem != null;
	}

	/**
	 * 補助科目が存在するか
	 * 
	 * @return true:存在
	 */
	public boolean hasSubItem() {
		return hasSubItem;
	}

	/**
	 * 補助科目が存在するか
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setSubItem(boolean hasSubItem) {
		this.hasSubItem = hasSubItem;
	}

	/**
	 * 補助科目コードを返す
	 * 
	 * @return 補助科目コード
	 */
	public String getSubItemCode() {
		if (getSubItem() != null) {
			return getSubItem().getCode();
		}
		return null;
	}

	/**
	 * 補助科目名称を返す
	 * 
	 * @return 補助科目名称
	 */
	public String getSubItemName() {
		if (getSubItem() != null) {
			return getSubItem().getName();
		}
		return null;
	}

	/**
	 * 補助科目略称を返す
	 * 
	 * @return 補助科目略称
	 */
	public String getSubItemNames() {
		if (getSubItem() != null) {
			return getSubItem().getNames();
		}
		return null;
	}

	/**
	 * 補助科目検索名称を返す
	 * 
	 * @return 補助科目検索名称
	 */
	public String getSubItemNamek() {
		if (getSubItem() != null) {
			return getSubItem().getNamek();
		}
		return null;
	}

	/**
	 * 内訳科目を返す
	 * 
	 * @return 内訳科目
	 */
	public DetailItem getDetailItem() {
		if (getSubItem() != null) {
			return getSubItem().getDetailItem();
		}
		return null;
	}

	/**
	 * 内訳科目コードを返す
	 * 
	 * @return 内訳科目コード
	 */
	public String getDetailItemCode() {
		if (getDetailItem() != null) {
			return getDetailItem().getCode();
		}
		return null;
	}

	/**
	 * 内訳科目名称を返す
	 * 
	 * @return 内訳科目名称
	 */
	public String getDetailItemName() {
		if (getDetailItem() != null) {
			return getDetailItem().getName();
		}
		return null;
	}

	/**
	 * 内訳科目略称を返す
	 * 
	 * @return 内訳科目略称
	 */
	public String getDetailItemNames() {
		if (getDetailItem() != null) {
			return getDetailItem().getNames();
		}
		return null;
	}

	/**
	 * 内訳科目検索名称を返す
	 * 
	 * @return 内訳科目検索名称
	 */
	public String getDetailItemNamek() {
		if (getDetailItem() != null) {
			return getDetailItem().getNamek();
		}
		return null;
	}

	/**
	 * 多通貨を認めるかどうか
	 * 
	 * @return true:認める
	 */
	public boolean isUseForeignCurrency() {
		return useForeignCurrency;
	}

	/**
	 * 多通貨を認めるかどうか
	 * 
	 * @param useForeignCurrency true:認める
	 */
	public void setUseForeignCurrency(boolean useForeignCurrency) {
		this.useForeignCurrency = useForeignCurrency;
	}

	/**
	 * 仕入課税を認めるか
	 * 
	 * @return true:認める
	 */
	public boolean isUsePurchaseTaxation() {
		return usePurchaseTaxation;
	}

	/**
	 * 仕入課税を認めるか
	 * 
	 * @param usePurchaseTaxation true:認める
	 */
	public void setUsePurchaseTaxation(boolean usePurchaseTaxation) {
		this.usePurchaseTaxation = usePurchaseTaxation;
	}

	/**
	 * 売上課税を認めるか
	 * 
	 * @return true:認める
	 */
	public boolean isUseSalesTaxation() {
		return useSalesTaxation;
	}

	/**
	 * 売上課税を認めるか
	 * 
	 * @param useSalesTaxation true:認める
	 */
	public void setUseSalesTaxation(boolean useSalesTaxation) {
		this.useSalesTaxation = useSalesTaxation;
	}

	/**
	 * 消費税
	 * 
	 * @return 消費税を戻します。
	 */
	public ConsumptionTax getConsumptionTax() {
		return consumptionTax;
	}

	/**
	 * 消費税
	 * 
	 * @param consumptionTax 消費税を設定します。
	 */
	public void setConsumptionTax(ConsumptionTax consumptionTax) {
		this.consumptionTax = consumptionTax;
	}

	/**
	 * 固定部門コード
	 * 
	 * @return 固定部門コードを戻します。
	 */
	public String getFixedDepartmentCode() {
		return fixedDepartmentCode;
	}

	/**
	 * 固定部門コード
	 * 
	 * @param fixedDepartmentCode 固定部門コードを設定します。
	 */
	public void setFixedDepartmentCode(String fixedDepartmentCode) {
		this.fixedDepartmentCode = fixedDepartmentCode;
	}

	/**
	 * 評価替え対象か
	 * 
	 * @return true:対象
	 */
	public boolean isDoesCurrencyEvaluate() {
		return doesCurrencyEvaluate;
	}

	/**
	 * 評価替え対象か
	 * 
	 * @param doseCurrencyEvaluate true:対象
	 */
	public void setDoesCurrencyEvaluate(boolean doseCurrencyEvaluate) {
		this.doesCurrencyEvaluate = doseCurrencyEvaluate;
	}

	/**
	 * 社員入力
	 * 
	 * @return 社員入力を戻します。
	 */
	public boolean isUseEmployee() {
		return useEmployee;
	}

	/**
	 * 社員入力
	 * 
	 * @param useEmployee 社員入力を設定します。
	 */
	public void setUseEmployee(boolean useEmployee) {
		this.useEmployee = useEmployee;
	}

	/**
	 * 取引先
	 * 
	 * @return 取引先を戻します。
	 */
	public CustomerType getClientType() {
		return clientType;
	}

	/**
	 * 取引先
	 * 
	 * @param clientType 取引先を設定します。
	 */
	public void setClientType(CustomerType clientType) {
		this.clientType = clientType;
	}

	/**
	 * 管理1入力
	 * 
	 * @return 管理1入力を戻します。
	 */
	public boolean isUseManagement1() {
		return useManagement1;
	}

	/**
	 * 管理1入力
	 * 
	 * @param useManagement1 管理1入力を設定します。
	 */
	public void setUseManagement1(boolean useManagement1) {
		this.useManagement1 = useManagement1;
	}

	/**
	 * 管理2入力
	 * 
	 * @return 管理2入力を戻します。
	 */
	public boolean isUseManagement2() {
		return useManagement2;
	}

	/**
	 * 管理2入力
	 * 
	 * @param useManagement2 管理2入力を設定します。
	 */
	public void setUseManagement2(boolean useManagement2) {
		this.useManagement2 = useManagement2;
	}

	/**
	 * 管理3入力
	 * 
	 * @return 管理3入力を戻します。
	 */
	public boolean isUseManagement3() {
		return useManagement3;
	}

	/**
	 * 管理3入力
	 * 
	 * @param useManagement3 管理3入力を設定します。
	 */
	public void setUseManagement3(boolean useManagement3) {
		this.useManagement3 = useManagement3;
	}

	/**
	 * 管理4入力
	 * 
	 * @return 管理4入力を戻します。
	 */
	public boolean isUseManagement4() {
		return useManagement4;
	}

	/**
	 * 管理4入力
	 * 
	 * @param useManagement4 管理4入力を設定します。
	 */
	public void setUseManagement4(boolean useManagement4) {
		this.useManagement4 = useManagement4;
	}

	/**
	 * 管理5入力
	 * 
	 * @return 管理5入力を戻します。
	 */
	public boolean isUseManagement5() {
		return useManagement5;
	}

	/**
	 * 管理5入力
	 * 
	 * @param useManagement5 管理5入力を設定します。
	 */
	public void setUseManagement5(boolean useManagement5) {
		this.useManagement5 = useManagement5;
	}

	/**
	 * 管理6入力
	 * 
	 * @return 管理6入力を戻します。
	 */
	public boolean isUseManagement6() {
		return useManagement6;
	}

	/**
	 * 管理6入力
	 * 
	 * @param useManagement6 管理6入力を設定します。
	 */
	public void setUseManagement6(boolean useManagement6) {
		this.useManagement6 = useManagement6;
	}

	/**
	 * 非会計明細1を利用するか
	 * 
	 * @return true:利用
	 */
	public boolean isUseNonAccount1() {
		return useNonAccount1;
	}

	/**
	 * 非会計明細1を利用するか
	 * 
	 * @param useNonAccount1 true:利用
	 */
	public void setUseNonAccount1(boolean useNonAccount1) {
		this.useNonAccount1 = useNonAccount1;
	}

	/**
	 * 会計明細2を利用するか
	 * 
	 * @return true:利用
	 */
	public boolean isUseNonAccount2() {
		return useNonAccount2;
	}

	/**
	 * 会計明細2を利用するか
	 * 
	 * @param useNonAccount2 true:利用
	 */
	public void setUseNonAccount2(boolean useNonAccount2) {
		this.useNonAccount2 = useNonAccount2;
	}

	/**
	 * 会計明細3を利用するか
	 * 
	 * @return true:利用
	 */
	public boolean isUseNonAccount3() {
		return useNonAccount3;
	}

	/**
	 * 会計明細3を利用するか
	 * 
	 * @param useNonAccount3 true:利用
	 */
	public void setUseNonAccount3(boolean useNonAccount3) {
		this.useNonAccount3 = useNonAccount3;
	}

	/**
	 * 取引先を利用するか
	 * 
	 * @return true:利用
	 */
	public boolean isUseCustomer() {
		CustomerType customerType = getClientType();
		return customerType != CustomerType.NONE;
	}

	/**
	 * 入金伝票入力フラグ
	 * 
	 * @return 入金伝票入力フラグを戻します。
	 */
	public boolean isUseInputCashFlowSlip() {
		return useInputCashFlowSlip;
	}

	/**
	 * 入金伝票入力フラグ
	 * 
	 * @param depositSlip 入金伝票入力フラグフラグを設定します。
	 */
	public void setUseInputCashFlowSlip(boolean depositSlip) {
		this.useInputCashFlowSlip = depositSlip;
	}

	/**
	 * 出金伝票入力フラグ
	 * 
	 * @return 出金伝票入力フラグを戻します。
	 */
	public boolean isUseOutputCashFlowSlip() {
		return useOutputCashFlowSlip;
	}

	/**
	 * 出金伝票入力フラグ
	 * 
	 * @param paymentSlip 出金伝票入力フラグを設定します。
	 */
	public void setUseOutputCashFlowSlip(boolean paymentSlip) {
		this.useOutputCashFlowSlip = paymentSlip;
	}

	/**
	 * 振替伝票入力フラグ
	 * 
	 * @return 振替伝票入力フラグを戻します。
	 */
	public boolean isUseTransferSlip() {
		return useTransferSlip;
	}

	/**
	 * 振替伝票入力フラグ
	 * 
	 * @param transferSlip 振替伝票入力フラグを設定します。
	 */
	public void setUseTransferSlip(boolean transferSlip) {
		this.useTransferSlip = transferSlip;
	}

	/**
	 * 経費精算伝票入力フラグ
	 * 
	 * @return 経費精算伝票入力フラグを戻します。
	 */
	public boolean isUseExpenseSettlementSlip() {
		return useExpenseSettlementSlip;
	}

	/**
	 * 経費精算伝票入力フラグ
	 * 
	 * @param billSlip 経費精算伝票入力フラグを設定します。
	 */
	public void setUseExpenseSettlementSlip(boolean billSlip) {
		this.useExpenseSettlementSlip = billSlip;
	}

	/**
	 * 債務計上伝票入力フラグ
	 * 
	 * @return 債務計上伝票入力フラグを戻します。
	 */
	public boolean isUsePaymentAppropriateSlip() {
		return usePaymentAppropriateSlip;
	}

	/**
	 * 債務計上伝票入力フラグ
	 * 
	 * @param debtUpSlip 債務計上伝票入力フラグを設定します。
	 */
	public void setUsePaymentAppropriateSlip(boolean debtUpSlip) {
		this.usePaymentAppropriateSlip = debtUpSlip;
	}

	/**
	 * 債権計上伝票入力フラグ
	 * 
	 * @return 債権計上伝票入力フラグを戻します。
	 */
	public boolean isUseReceivableAppropriateSlip() {
		return useReceivableAppropriateSlip;
	}

	/**
	 * 債権計上伝票入力フラグ
	 * 
	 * @param claimUpSlip 債権計上伝票入力フラグを設定します。
	 */
	public void setUseReceivableAppropriateSlip(boolean claimUpSlip) {
		this.useReceivableAppropriateSlip = claimUpSlip;
	}

	/**
	 * 債権消込伝票入力フラグ
	 * 
	 * @return 債権消込伝票入力フラグを戻します。
	 */
	public boolean isUseReceivableErasingSlip() {
		return useReceivableErasingSlip;
	}

	/**
	 * 債権消込伝票入力フラグ
	 * 
	 * @param claimKesiSlip 債権消込伝票入力フラグを設定します。
	 */
	public void setUseReceivableErasingSlip(boolean claimKesiSlip) {
		this.useReceivableErasingSlip = claimKesiSlip;
	}

	/**
	 * 資産計上伝票入力フラグ
	 * 
	 * @return 資産計上伝票入力フラグを戻します。
	 */
	public boolean isUseAssetsEntrySlip() {
		return useAssetsEntrySlip;
	}

	/**
	 * 資産計上伝票入力フラグ
	 * 
	 * @param propertySlip 資産計上伝票入力フラグを設定します。
	 */
	public void setUseAssetsEntrySlip(boolean propertySlip) {
		this.useAssetsEntrySlip = propertySlip;
	}

	/**
	 * 支払依頼伝票入力フラグ
	 * 
	 * @return 支払依頼伝票入力フラグを戻します。
	 */
	public boolean isUsePaymentRequestSlip() {
		return usePaymentRequestSlip;
	}

	/**
	 * 支払依頼伝票入力フラグ
	 * 
	 * @param paymentRequestSlip 支払依頼伝票入力フラグを設定します。
	 */
	public void setUsePaymentRequestSlip(boolean paymentRequestSlip) {
		this.usePaymentRequestSlip = paymentRequestSlip;
	}

	/**
	 * 航海収支計算対象かどうかの取得
	 * 
	 * @return useVoyageCalculation 航海収支計算対象かどうか
	 */
	public boolean isUseVoyageCalculation() {
		return useVoyageCalculation;
	}

	/**
	 * 航海収支計算対象かどうかの設定
	 * 
	 * @param useVoyageCalculation 航海収支計算対象かどうか
	 */
	public void setUseVoyageCalculation(boolean useVoyageCalculation) {
		this.useVoyageCalculation = useVoyageCalculation;
	}

	/**
	 * 固定部門名称
	 * 
	 * @return fixedDepartmentName 固定部門名称を戻します。
	 */
	public String getFixedDepartmentName() {
		return fixedDepartmentName;
	}

	/**
	 * 固定部門名称
	 * 
	 * @param fixedDepartmentName 固定部門名称を設定します。
	 */
	public void setFixedDepartmentName(String fixedDepartmentName) {
		this.fixedDepartmentName = fixedDepartmentName;
	}

	/**
	 * 集計区分
	 * 
	 * @return 集計区分
	 */
	public ItemSumType getItemSumType() {
		return this.itemSumType;
	}

	/**
	 * 集計区分
	 * 
	 * @param itemSumType 集計区分
	 */
	public void setItemSumType(ItemSumType itemSumType) {
		this.itemSumType = itemSumType;
	}

	/**
	 * 貸借区分
	 * 
	 * @return 貸借区分
	 */
	public Dc getDc() {
		return this.dc;
	}

	/**
	 * 貸借区分
	 * 
	 * @param dc 貸借区分
	 */
	public void setDc(Dc dc) {
		this.dc = dc;
	}

	/**
	 * 科目種別
	 * 
	 * @return 科目種別
	 */
	public ItemType getItemType() {
		return this.itemType;
	}

	/**
	 * 科目種別
	 * 
	 * @param itemType 科目種別
	 */
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	/**
	 * GL科目制御区分
	 * 
	 * @return GL科目制御区分
	 */
	public GLType getGlType() {
		return this.glType;
	}

	/**
	 * GL科目制御区分
	 * 
	 * @param glType GL科目制御区分
	 */
	public void setGlType(GLType glType) {
		this.glType = glType;
	}

	/**
	 * AP科目制御区分
	 * 
	 * @return AP科目制御区分
	 */
	public APType getApType() {
		return this.apType;
	}

	/**
	 * AP科目制御区分
	 * 
	 * @param apType AP科目制御区分
	 */
	public void setApType(APType apType) {
		this.apType = apType;
	}

	/**
	 * AR科目制御区分
	 * 
	 * @return AR科目制御区分
	 */
	public ARType getArType() {
		return this.arType;
	}

	/**
	 * AR科目制御区分
	 * 
	 * @param arType AR科目制御区分
	 */
	public void setArType(ARType arType) {
		this.arType = arType;
	}

	/**
	 * BG科目制御区分
	 * 
	 * @return BG科目制御区分
	 */
	public BGType getBgType() {
		return this.bgType;
	}

	/**
	 * BG科目制御区分
	 * 
	 * @param bgType BG科目制御区分
	 */
	public void setBgType(BGType bgType) {
		this.bgType = bgType;
	}

	/**
	 * 相殺科目制御区分
	 * 
	 * @return true:する、false:しない
	 */
	public boolean isDoesOffsetItem() {
		return this.doesOffsetItem;
	}

	/**
	 * 相殺科目制御区分
	 * 
	 * @param offsetItem true:する、false:しない
	 */
	public void setDoesOffsetItem(boolean offsetItem) {
		this.doesOffsetItem = offsetItem;
	}

	/**
	 * BS勘定消込区分
	 * 
	 * @return true:する、false:しない
	 */
	public boolean isDoesBsOffset() {
		return this.doesBsOffset;
	}

	/**
	 * BS勘定消込区分
	 * 
	 * @param bsAcOffset true:する、false:しない
	 */
	public void setDoesBsOffset(boolean bsAcOffset) {
		this.doesBsOffset = bsAcOffset;
	}

	/**
	 * 評価替方式
	 * 
	 * @return 評価替方式
	 */
	public EvaluationMethod getEvaluationMethod() {
		return this.evaluationMethod;
	}

	/**
	 * 評価替方式
	 * 
	 * @param evaluationMethod 評価替方式
	 */
	public void setEvaluationMethod(EvaluationMethod evaluationMethod) {
		this.evaluationMethod = evaluationMethod;
	}

	/**
	 * 発生日使用の取得
	 * 
	 * @return useOccurDate 発生日使用
	 */
	public boolean isUseOccurDate() {
		return useOccurDate;
	}

	/**
	 * 発生日使用の設定
	 * 
	 * @param useOccurDate 発生日使用
	 */
	public void setUseOccurDate(boolean useOccurDate) {
		this.useOccurDate = useOccurDate;
	}

	/**
	 * 同一科目かどうかを判定(内訳まで)
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == null || !(obj instanceof Item)) {
			return false;
		}

		Item item = (Item) obj;

		// インスタンス一緒
		if (item == this) {
			return true;
		}

		// 会社
		if (!this.companyCode.equals(item.getCompanyCode())) {
			return false;
		}

		// 科目
		if (!this.code.equals(item.getCode())) {
			return false;
		}

		// 補助
		if (!Util.avoidNull(this.getSubItemCode()).equals(Util.avoidNull(item.getSubItemCode()))) {
			return false;
		}

		// 内訳
		if (!Util.avoidNull(this.getDetailItemCode()).equals(Util.avoidNull(item.getDetailItemCode()))) {
			return false;
		}

		return true;
	}

	/**
	 * 適した科目情報を返す
	 * 
	 * @return 科目
	 */
	public Item getPreferedItem() {
		Item item = this;

		if (hasSubItem) {
			SubItem sub = this.getSubItem();
			if (sub != null && sub.hasDetailItem) {
				item = getDetailItem();

			} else {
				item = sub;
			}
		}

		return item != null ? item : this;
	}
}

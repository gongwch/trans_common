package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 科目マスタ検索条件
 */
public class ItemSearchCondition extends TransferBase implements Cloneable {

	/** 伝票入力種類 */
	public enum SlipInputType {
	/** 振替伝票入力 */
	TransferSlip,

	/** 入金伝票入力 */
	InputCashFlowSlip,

	/** 出金伝票入力 */
	OutputCashFlowSlip,

	/** 振替伝票入力 */
	ReversingSlip,

	/** 経費精算伝票入力 */
	ExpenseSettlementSlip,

	/** 請求書伝票入力(債務計上) */
	PaymentAppropriateSlip,

	/** 債権計上伝票入力 */
	ReceivableAppropriateSlip,

	/** 債権消込伝票入力 */
	ReceivableErasingSlip,

	/** 資産計上伝票入力 */
	AssetsEntrySlip,

	/** 支払依頼伝票入力 */
	PaymentRequestSlip;
	}

	/** AR科目制御区分 */
	public enum ARControlType {
		/** 通常 */
		Nomal,

		/** 債権管理科目 */
		ARAccount,

		/** 債権回収仮勘定 */
		ARTempolaryAccount
	}

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** 集計科目を含むか */
	protected boolean sumItem = false;

	/** 見出し科目を含むか */
	protected boolean titleItem = false;

	/** 補助科目を含むか */
	protected boolean subItem = false;

	/** 固定部門コード */
	protected String departmentCode = null;

	/** 伝票入力種類 */
	protected SlipInputType slipInputType = null;

	/** 評価替科目だけを対象にするか */
	protected boolean evaluation = false;

	/** BS勘定消込科目だけを対象にするか */
	protected boolean bsCalculateErase = false;

	/** 資金科目だけを対象にするか */
	protected boolean cash = false;

	/** 債務科目だけを対象にするか */
	protected boolean paymentItem = false;

	/** 債権科目だけを対象にするか */
	protected boolean receiveItem = false;

	/** AR科目制御区分 */
	protected List<ARControlType> arControlTypes = new ArrayList<ARControlType>();

	/** 科目体系コード */
	protected String itemOrganizationCode = null;

	/** 管理1選択 */
	public boolean useManagement1 = false;

	/** 管理2選択 */
	public boolean useManagement2 = false;

	/** 管理3選択 */
	public boolean useManagement3 = false;

	/** 管理4選択 */
	public boolean useManagement4 = false;

	/** 管理5選択 */
	public boolean useManagement5 = false;

	/** 管理6選択 */
	public boolean useManagement6 = false;

	/** 管理1非選択 */
	public boolean notUseManagement1 = false;

	/** 管理2非選択 */
	public boolean notUseManagement2 = false;

	/** 管理3非選択 */
	public boolean notUseManagement3 = false;

	/** 管理4非選択 */
	public boolean notUseManagement4 = false;

	/** 管理5非選択 */
	public boolean notUseManagement5 = false;

	/** 管理6非選択 */
	public boolean notUseManagement6 = false;

	/** 多通貨 */
	public boolean useForeignCurrency = false;

	/** 社員フラグ */
	public boolean useEmployee = false;

	/** 非社員フラグ */
	public boolean noUseEmployee = false;

	/** 消費税コードフラグ */
	public boolean flgZeiCode = false;

	/** 追加検索条件 */
	protected String addonWhereSql = null;

	/** PK指定リスト */
	protected List<ItemSearchPrimaryKey> pkList = null;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ItemSearchCondition clone() {
		try {
			ItemSearchCondition con = (ItemSearchCondition) super.clone();
			if (pkList != null) {
				List<ItemSearchPrimaryKey> list = new ArrayList();
				for (ItemSearchPrimaryKey key : pkList) {
					list.add(key.clone());
				}
				con.setPkList(list);
			}
			return con;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 消費税コードフラグを設定する
	 * 
	 * @param flgZeiCode
	 */
	public void setFlgZeiCode(boolean flgZeiCode) {
		this.flgZeiCode = flgZeiCode;
	}

	/**
	 * 消費税コードが入力されているか
	 * 
	 * @return flgZeiCode
	 */
	public boolean isFlgZeiCode() {
		return this.flgZeiCode;
	}

	/**
	 * 社員入力が可能かを設定する
	 * 
	 * @param useEmployee
	 */
	public void setUseEmployee(boolean useEmployee) {
		this.useEmployee = useEmployee;
	}

	/**
	 * 社員入力が可能か
	 * 
	 * @return useEmployee
	 */
	public boolean isUseEmployee() {
		return this.useEmployee;
	}

	/**
	 * 非社員入力を設定する
	 * 
	 * @param noUseEmployee
	 */
	public void setNoUseEmployee(boolean noUseEmployee) {
		this.noUseEmployee = noUseEmployee;
	}

	/**
	 * 非社員入力判定
	 * 
	 * @return noUseEmployee
	 */
	public boolean isNoUseEmployee() {
		return this.noUseEmployee;
	}

	/**
	 * 多通貨入力が可能かを設定する
	 * 
	 * @param useForeignCurrency
	 */
	public void setUseForeignCurrency(boolean useForeignCurrency) {
		this.useForeignCurrency = useForeignCurrency;
	}

	/**
	 * 多通貨入力が可能か
	 * 
	 * @return useForeignCurrency
	 */
	public boolean isUseForeignCurrency() {
		return this.useForeignCurrency;
	}

	/**
	 * 管理1非選択を設定する
	 * 
	 * @param notUseManagement1
	 */
	public void setNotUseManagement1(boolean notUseManagement1) {
		this.notUseManagement1 = notUseManagement1;
	}

	/**
	 * 管理1非選択判定
	 * 
	 * @return notUseManagement1
	 */
	public boolean isNotUseManagement1() {
		return this.notUseManagement1;
	}

	/**
	 * 管理2非選択を設定する
	 * 
	 * @param notUseManagement2
	 */
	public void setNotUseManagement2(boolean notUseManagement2) {
		this.notUseManagement2 = notUseManagement2;
	}

	/**
	 * 管理2非選択判定
	 * 
	 * @return notUseManagement2
	 */
	public boolean isNotUseManagement2() {
		return this.notUseManagement2;
	}

	/**
	 * 管理3非選択を設定する
	 * 
	 * @param notUseManagement3
	 */
	public void setNotUseManagement3(boolean notUseManagement3) {
		this.notUseManagement3 = notUseManagement3;
	}

	/**
	 * 管理3非選択判定
	 * 
	 * @return notUseManagement3
	 */
	public boolean isNotUseManagement3() {
		return this.notUseManagement3;
	}

	/**
	 * 管理4非選択を設定する
	 * 
	 * @param notUseManagement4
	 */
	public void setNotUseManagement4(boolean notUseManagement4) {
		this.notUseManagement4 = notUseManagement4;
	}

	/**
	 * 管理4非選択判定
	 * 
	 * @return notUseManagement4
	 */
	public boolean isNotUseManagement4() {
		return this.notUseManagement4;
	}

	/**
	 * 管理5非選択を設定する
	 * 
	 * @param notUseManagement5
	 */
	public void setNotUseManagement5(boolean notUseManagement5) {
		this.notUseManagement5 = notUseManagement5;
	}

	/**
	 * 管理5非選択判定
	 * 
	 * @return notUseManagement5
	 */
	public boolean isNotUseManagement5() {
		return this.notUseManagement5;
	}

	/**
	 * 管理6非選択を設定する
	 * 
	 * @param notUseManagement6
	 */
	public void setNotUseManagement6(boolean notUseManagement6) {
		this.notUseManagement6 = notUseManagement6;
	}

	/**
	 * 管理6非選択判定
	 * 
	 * @return notUseManagement6
	 */
	public boolean isNotUseManagement6() {
		return this.notUseManagement6;
	}

	/**
	 * 管理1選択を設定する
	 * 
	 * @param useManagement1
	 */
	public void setUseManagement1(boolean useManagement1) {
		this.useManagement1 = useManagement1;
	}

	/**
	 * 管理1選択判定
	 * 
	 * @return useManagement1
	 */
	public boolean isUseManagement1() {
		return this.useManagement1;
	}

	/**
	 * 管理2選択を設定する
	 * 
	 * @param useManagement2
	 */
	public void setUseManagement2(boolean useManagement2) {
		this.useManagement2 = useManagement2;
	}

	/**
	 * 管理2選択判定
	 * 
	 * @return useManagement2
	 */
	public boolean isUseManagement2() {
		return this.useManagement2;
	}

	/**
	 * 管理3選択を設定する
	 * 
	 * @param useManagement3
	 */
	public void setUseManagement3(boolean useManagement3) {
		this.useManagement3 = useManagement3;
	}

	/**
	 * 管理3選択判定
	 * 
	 * @return useManagement3
	 */
	public boolean isUseManagement3() {
		return this.useManagement3;
	}

	/**
	 * 管理4選択を設定する
	 * 
	 * @param useManagement4
	 */
	public void setUseManagement4(boolean useManagement4) {
		this.useManagement4 = useManagement4;
	}

	/**
	 * 管理4選択判定
	 * 
	 * @return useManagement4
	 */
	public boolean isUseManagement4() {
		return this.useManagement4;
	}

	/**
	 * 管理5選択を設定する
	 * 
	 * @param useManagement5
	 */
	public void setUseManagement5(boolean useManagement5) {
		this.useManagement5 = useManagement5;
	}

	/**
	 * 管理5選択判定
	 * 
	 * @return useManagement5
	 */
	public boolean isUseManagement5() {
		return this.useManagement5;
	}

	/**
	 * 管理6選択を設定する
	 * 
	 * @param useManagement6
	 */
	public void setUseManagement6(boolean useManagement6) {
		this.useManagement6 = useManagement6;
	}

	/**
	 * 管理6選択判定
	 * 
	 * @return useManagement6
	 */
	public boolean isUseManagement6() {
		return this.useManagement6;
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

	/**
	 * @return codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * @return namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * @return namesLike
	 */
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
	 * @return isSumItem
	 */
	public boolean isSumItem() {
		return sumItem;
	}

	/**
	 * @param sumItem
	 */
	public void setSumItem(boolean sumItem) {
		this.sumItem = sumItem;
	}

	/**
	 * @return isTitleItem
	 */
	public boolean isTitleItem() {
		return titleItem;
	}

	/**
	 * @param titleItem
	 */
	public void setTitleItem(boolean titleItem) {
		this.titleItem = titleItem;
	}

	/**
	 * 固定部門コード
	 * 
	 * @param code 部門コード
	 */
	public void setDepartmentCode(String code) {
		this.departmentCode = code;
	}

	/**
	 * 固定部門コード
	 * 
	 * @return 部門コード
	 */
	public String getDepartmentCode() {
		return this.departmentCode;
	}

	/**
	 * 伝票入力種類
	 * 
	 * @return 伝票入力種類
	 */
	public SlipInputType getSlipInputType() {
		return slipInputType;
	}

	/**
	 * 伝票入力種類
	 * 
	 * @param slipInputType 伝票入力種類
	 */
	public void setSlipInputType(SlipInputType slipInputType) {
		this.slipInputType = slipInputType;
	}

	/**
	 * @return evaluation
	 */
	public boolean isEvaluation() {
		return evaluation;
	}

	/**
	 * @param evaluation
	 */
	public void setEvaluation(boolean evaluation) {
		this.evaluation = evaluation;
	}

	/**
	 * bsCalculateEraseを取得する。
	 * 
	 * @return boolean bsCalculateErase
	 */
	public boolean isBsCalculateErase() {
		return bsCalculateErase;
	}

	/**
	 * bsCalculateEraseを設定する。
	 * 
	 * @param bsCalculateErase
	 */
	public void setBsCalculateErase(boolean bsCalculateErase) {
		this.bsCalculateErase = bsCalculateErase;
	}

	/**
	 * AR科目制御区分
	 * 
	 * @return AR科目制御区分
	 */
	public List<ARControlType> getArControlTypes() {
		return arControlTypes;
	}

	/**
	 * AR科目制御区分
	 * 
	 * @param arControlType AR科目制御区分
	 */
	public void setArControlTypes(ARControlType... arControlType) {
		for (ARControlType type : arControlType) {
			this.arControlTypes.add(type);
		}
	}

	/**
	 * 資金科目だけを対象にするか
	 * 
	 * @return 資金科目だけを対象にするか
	 */
	public boolean isCash() {
		return cash;
	}

	/**
	 * 資金科目だけを対象にするか
	 * 
	 * @param cash
	 */
	public void setCash(boolean cash) {
		this.cash = cash;
	}

	/**
	 * 科目体系コードgetter
	 * 
	 * @return 科目体系コード
	 */
	public String getItemOrganizationCode() {
		return itemOrganizationCode;
	}

	/**
	 * 科目体系コードsetter
	 * 
	 * @param itemOrganizationCode 科目体系コード
	 */
	public void setItemOrganizationCode(String itemOrganizationCode) {
		this.itemOrganizationCode = itemOrganizationCode;
	}

	/**
	 * paymentItemを取得する。
	 * 
	 * @return boolean paymentItem
	 */
	public boolean isPaymentItem() {
		return paymentItem;
	}

	/**
	 * paymentItemを設定する。
	 * 
	 * @param paymentItem
	 */
	public void setPaymentItem(boolean paymentItem) {
		this.paymentItem = paymentItem;
	}

	/**
	 * receiveItemを取得する。
	 * 
	 * @return boolean receiveItem
	 */
	public boolean isReceiveItem() {
		return receiveItem;
	}

	/**
	 * receiveItemを設定する。
	 * 
	 * @param receiveItem
	 */
	public void setReceiveItem(boolean receiveItem) {
		this.receiveItem = receiveItem;
	}

	/**
	 * 補助科目を含むか
	 * 
	 * @return subItem
	 */
	public boolean isSubItem() {
		return subItem;
	}

	/**
	 * 補助科目を含むか
	 * 
	 * @param subItem
	 */
	public void setSubItem(boolean subItem) {
		this.subItem = subItem;
	}

	/**
	 * 追加検索条件の取得
	 * 
	 * @return addonWhereSql 追加検索条件
	 */
	public String getAddonWhereSql() {
		return addonWhereSql;
	}

	/**
	 * 追加検索条件の設定
	 * 
	 * @param addonWhereSql 追加検索条件
	 */
	public void setAddonWhereSql(String addonWhereSql) {
		this.addonWhereSql = addonWhereSql;
	}

	/**
	 * PK指定リスト
	 * 
	 * @return PK指定リスト
	 */
	public List<ItemSearchPrimaryKey> getPkList() {
		return pkList;
	}

	/**
	 * PK指定リスト
	 * 
	 * @param pkList
	 */
	public void setPkList(List<ItemSearchPrimaryKey> pkList) {
		this.pkList = pkList;
	}

	/**
	 * PK指定リストに追加
	 * 
	 * @param pk
	 */
	public void addPkList(ItemSearchPrimaryKey pk) {
		if (this.pkList == null) {
			pkList = new ArrayList();
		}
		this.pkList.add(pk);
	}

	/**
	 * PK指定リスト
	 * 
	 * @param pkList
	 */
	public void addPkList(String kaiCode, String kmkCode, String hkmCode, String ukmCode) {
		if (this.pkList == null) {
			pkList = new ArrayList();
		}
		this.pkList.add(new ItemSearchPrimaryKey(kaiCode, kmkCode, hkmCode, ukmCode));
	}

}

package jp.co.ais.trans2.model.company;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * 会計の設定情報
 * 
 * @author AIS
 */
public class AccountConfig extends TransferBase {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("key=").append(keyCurrency);
		sb.append(",func=").append(functionalCurrency);
		return sb.toString();
	}

	/** 科目名称 */
	protected String itemName = "";

	/** 補助科目名称 */
	protected String subItemName = "";

	/** 内訳科目を使用するか */
	protected boolean useDetailItem = false;

	/** 内訳科目名称 */
	protected String detailItemName = "";

	/** 管理1を使用するか */
	protected boolean useManagement1 = false;

	/** 管理2を使用するか */
	protected boolean useManagement2 = false;

	/** 管理3を使用するか */
	protected boolean useManagement3 = false;

	/** 管理4を使用するか */
	protected boolean useManagement4 = false;

	/** 管理5を使用するか */
	protected boolean useManagement5 = false;

	/** 管理6を使用するか */
	protected boolean useManagement6 = false;

	/** 管理1名称 */
	protected String management1Name = null;

	/** 管理2名称 */
	protected String management2Name = null;

	/** 管理3名称 */
	protected String management3Name = null;

	/** 管理4名称 */
	protected String management4Name = null;

	/** 管理5名称 */
	protected String management5Name = null;

	/** 管理6名称 */
	protected String management6Name = null;

	/** IFRS機能を使用するか */
	protected boolean useIfrs = false;

	/** 基軸通貨 */
	protected Currency keyCurrency = null;

	/** 機能通貨 */
	protected Currency functionalCurrency = null;

	/** 非会計明細1区分(0は未使用) */
	protected int nonAccounting1 = 0;

	/** 非会計明細2区分(0は未使用) */
	protected int nonAccounting2 = 0;

	/** 非会計明細3区分(0は未使用) */
	protected int nonAccounting3 = 0;

	/** 非会計明細1名称 */
	protected String nonAccounting1Name = null;

	/** 非会計明細2名称 */
	protected String nonAccounting2Name = null;

	/** 非会計明細3名称 */
	protected String nonAccounting3Name = null;

	/** ワークフロー承認を利用するか */
	protected boolean useWorkflowApprove = false;

	/** ワークフロー承認での承認取消・否認時、最初のステータスに戻すか */
	protected boolean isBackFirstWhenWorkflowDeny = false;

	/** 現場承認を使用するか */
	protected boolean useFieldApprove = false;

	/** 経理承認を使用するか */
	protected boolean useApprove = false;

	/** グループ会計を使用するか */
	protected boolean useGroupAccount = false;

	/** 発生日チェックを使用するか */
	protected boolean useHasDateCheck = false;

	/** 換算端数処理方式 */
	protected ExchangeFraction exchangeFraction;

	/** 仮払消費税端数処理 */
	protected ExchangeFraction paymentFunction;

	/** 仮受消費税端数処理 */
	protected ExchangeFraction receivingFunction;

	/** 伝票印刷するかどうか(trueする) */
	protected boolean slipPrint;

	/** 伝票印刷の初期値(true:印刷する) */
	protected boolean slipPrintDefault;

	/** 伝票有効期限チェック有無 */
	protected boolean slipTermCheck;

	/** 換算区分 */
	protected ConvertType convertType = null;

	/** 評価替レート区分 */
	protected EvaluationRateDate evaluationRateDate = null;

	/** 自動採番部桁数 */
	protected int slipNoDigit = 0;

	/** 自動採番項目1 */
	protected SlipNoAdopt slipNoAdopt1 = null;

	/** 自動採番項目2 */
	protected SlipNoAdopt slipNoAdopt2 = null;

	/** 自動採番項目3 */
	protected SlipNoAdopt slipNoAdopt3 = null;

	/** 中間決算繰越区分 */
	protected boolean carryJournalOfMidtermClosingForward = true;

	/**
	 * コンストラクタ
	 */
	public AccountConfig() {
		super();
	}

	/**
	 * @param slipNoDigit slipNoDigitを設定します。
	 */
	public void setSlipNoDigit(int slipNoDigit) {
		this.slipNoDigit = slipNoDigit;
	}

	/**
	 * @return slipNoDigitを戻します。
	 */
	public int getSlipNoDigit() {
		return slipNoDigit;
	}

	/**
	 * @return useIfrsを戻します。
	 */
	public boolean isUseIfrs() {
		return useIfrs;
	}

	/**
	 * @param useIfrs useIfrsを設定します。
	 */
	public void setUseIfrs(boolean useIfrs) {
		this.useIfrs = useIfrs;
	}

	/**
	 * @return management1Nameを戻します。
	 */
	public String getManagement1Name() {
		return management1Name;
	}

	/**
	 * @param management1Name management1Nameを設定します。
	 */
	public void setManagement1Name(String management1Name) {
		this.management1Name = management1Name;
	}

	/**
	 * @return management2Nameを戻します。
	 */
	public String getManagement2Name() {
		return management2Name;
	}

	/**
	 * @param management2Name management2Nameを設定します。
	 */
	public void setManagement2Name(String management2Name) {
		this.management2Name = management2Name;
	}

	/**
	 * @return management3Nameを戻します。
	 */
	public String getManagement3Name() {
		return management3Name;
	}

	/**
	 * @param management3Name management3Nameを設定します。
	 */
	public void setManagement3Name(String management3Name) {
		this.management3Name = management3Name;
	}

	/**
	 * @return management4Nameを戻します。
	 */
	public String getManagement4Name() {
		return management4Name;
	}

	/**
	 * @param management4Name management4Nameを設定します。
	 */
	public void setManagement4Name(String management4Name) {
		this.management4Name = management4Name;
	}

	/**
	 * @return management5Nameを戻します。
	 */
	public String getManagement5Name() {
		return management5Name;
	}

	/**
	 * @param management5Name management5Nameを設定します。
	 */
	public void setManagement5Name(String management5Name) {
		this.management5Name = management5Name;
	}

	/**
	 * @return management6Nameを戻します。
	 */
	public String getManagement6Name() {
		return management6Name;
	}

	/**
	 * @param management6Name management6Nameを設定します。
	 */
	public void setManagement6Name(String management6Name) {
		this.management6Name = management6Name;
	}

	/**
	 * @return useManagement1を戻します。
	 */
	public boolean isUseManagement1() {
		return useManagement1;
	}

	/**
	 * @param useManagement1 useManagement1を設定します。
	 */
	public void setUseManagement1(boolean useManagement1) {
		this.useManagement1 = useManagement1;
	}

	/**
	 * @return useManagement2を戻します。
	 */
	public boolean isUseManagement2() {
		return useManagement2;
	}

	/**
	 * @param useManagement2 useManagement2を設定します。
	 */
	public void setUseManagement2(boolean useManagement2) {
		this.useManagement2 = useManagement2;
	}

	/**
	 * @return useManagement3を戻します。
	 */
	public boolean isUseManagement3() {
		return useManagement3;
	}

	/**
	 * @param useManagement3 useManagement3を設定します。
	 */
	public void setUseManagement3(boolean useManagement3) {
		this.useManagement3 = useManagement3;
	}

	/**
	 * @return useManagement4を戻します。
	 */
	public boolean isUseManagement4() {
		return useManagement4;
	}

	/**
	 * @param useManagement4 useManagement4を設定します。
	 */
	public void setUseManagement4(boolean useManagement4) {
		this.useManagement4 = useManagement4;
	}

	/**
	 * @return useManagement5を戻します。
	 */
	public boolean isUseManagement5() {
		return useManagement5;
	}

	/**
	 * @param useManagement5 useManagement5を設定します。
	 */
	public void setUseManagement5(boolean useManagement5) {
		this.useManagement5 = useManagement5;
	}

	/**
	 * @return useManagement6を戻します。
	 */
	public boolean isUseManagement6() {
		return useManagement6;
	}

	/**
	 * @param useManagement6 useManagement6を設定します。
	 */
	public void setUseManagement6(boolean useManagement6) {
		this.useManagement6 = useManagement6;
	}

	/**
	 * @return itemNameを戻します。
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName itemNameを設定します。
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return subItemNameを戻します。
	 */
	public String getSubItemName() {
		return subItemName;
	}

	/**
	 * @param subItemName subItemNameを設定します。
	 */
	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}

	/**
	 * @return detailItemNameを戻します。
	 */
	public String getDetailItemName() {
		return detailItemName;
	}

	/**
	 * @param detailItemName detailItemNameを設定します。
	 */
	public void setDetailItemName(String detailItemName) {
		this.detailItemName = detailItemName;
	}

	/**
	 * @return useDetailItemを戻します。
	 */
	public boolean isUseDetailItem() {
		return useDetailItem;
	}

	/**
	 * @param useDetailItem useDetailItemを設定します。
	 */
	public void setUseDetailItem(boolean useDetailItem) {
		this.useDetailItem = useDetailItem;
	}

	/**
	 * 機能通貨
	 * 
	 * @return 機能通貨
	 */
	public Currency getFunctionalCurrency() {
		return functionalCurrency;
	}

	/**
	 * 機能通貨
	 * 
	 * @param functionalCurrency 機能通貨
	 */
	public void setFunctionalCurrency(Currency functionalCurrency) {
		this.functionalCurrency = functionalCurrency;
	}

	/**
	 * 基軸通貨
	 * 
	 * @return 基軸通貨
	 */
	public Currency getKeyCurrency() {
		return keyCurrency;
	}

	/**
	 * 基軸通貨
	 * 
	 * @param keyCurrency 基軸通貨
	 */
	public void setKeyCurrency(Currency keyCurrency) {
		this.keyCurrency = keyCurrency;
	}

	/**
	 * 非会計明細区分1
	 * 
	 * @return 非会計明細区分1
	 */
	public NonAccountingDivision getNonAccounting1() {
		return NonAccountingDivision.get(nonAccounting1);
	}

	/**
	 * 非会計明細区分1
	 * 
	 * @param useNotAccounting1 非会計明細区分1
	 */
	public void setNonAccounting1(int useNotAccounting1) {
		this.nonAccounting1 = useNotAccounting1;
	}

	/**
	 * 非会計明細区分2
	 * 
	 * @return 非会計明細区分2
	 */
	public NonAccountingDivision getNonAccounting2() {
		return NonAccountingDivision.get(nonAccounting2);
	}

	/**
	 * 非会計明細区分2
	 * 
	 * @param useNotAccounting2 非会計明細区分2
	 */
	public void setNonAccounting2(int useNotAccounting2) {
		this.nonAccounting2 = useNotAccounting2;
	}

	/**
	 * 非会計明細区分3
	 * 
	 * @return 非会計明細区分3
	 */
	public NonAccountingDivision getNonAccounting3() {
		return NonAccountingDivision.get(nonAccounting3);
	}

	/**
	 * 非会計明細区分3
	 * 
	 * @param useNotAccounting3 非会計明細区分3
	 */
	public void setNonAccounting3(int useNotAccounting3) {
		this.nonAccounting3 = useNotAccounting3;
	}

	/**
	 * 非会計明細1名称
	 * 
	 * @return 非会計明細1名称
	 */
	public String getNonAccounting1Name() {
		return nonAccounting1Name;
	}

	/**
	 * 非会計明細1名称
	 * 
	 * @param notAccounting1Name 非会計明細1名称
	 */
	public void setNonAccounting1Name(String notAccounting1Name) {
		this.nonAccounting1Name = notAccounting1Name;
	}

	/**
	 * 非会計明細2名称
	 * 
	 * @return 非会計明細2名称
	 */
	public String getNonAccounting2Name() {
		return nonAccounting2Name;
	}

	/**
	 * 非会計明細2名称
	 * 
	 * @param notAccounting2Name 非会計明細2名称
	 */
	public void setNonAccounting2Name(String notAccounting2Name) {
		this.nonAccounting2Name = notAccounting2Name;
	}

	/**
	 * 非会計明細2名称
	 * 
	 * @return 非会計明細2名称
	 */
	public String getNonAccounting3Name() {
		return nonAccounting3Name;
	}

	/**
	 * 非会計明細2名称
	 * 
	 * @param notAccounting3Name 非会計明細2名称
	 */
	public void setNonAccounting3Name(String notAccounting3Name) {
		this.nonAccounting3Name = notAccounting3Name;
	}

	/**
	 * 非会計明細1を使うか
	 * 
	 * @return 非会計明細1を使うか
	 */
	public boolean isUseNotAccounting1() {
		return (NonAccountingDivision.NONE != getNonAccounting1());
	}

	/**
	 * 非会計明細2を使うか
	 * 
	 * @return 非会計明細2を使うか
	 */
	public boolean isUseNotAccounting2() {
		return (NonAccountingDivision.NONE != getNonAccounting2());
	}

	/**
	 * 非会計明細3を使うか
	 * 
	 * @return 非会計明細3を使うか
	 */
	public boolean isUseNotAccounting3() {
		return (NonAccountingDivision.NONE != getNonAccounting3());
	}

	/**
	 * ワークフロー承認を利用するかを取得
	 * 
	 * @return useWorkflowApprove
	 */
	public boolean isUseWorkflowApprove() {
		return useWorkflowApprove;
	}

	/**
	 * ワークフロー承認を利用するかをセットする
	 * 
	 * @param useWorkflowApprove useWorkflowApprove
	 */
	public void setUseWorkflowApprove(boolean useWorkflowApprove) {
		this.useWorkflowApprove = useWorkflowApprove;
	}

	/**
	 * ワークフロー承認での承認取消・否認時、最初のステータスに戻すかを取得
	 * 
	 * @return isBackFirstWhenWorkflowDeny
	 */
	public boolean isBackFirstWhenWorkflowDeny() {
		return isBackFirstWhenWorkflowDeny;
	}

	/**
	 * ワークフロー承認での承認取消・否認時、最初のステータスに戻すかをセットする
	 * 
	 * @param isBackFirstWhenWorkflowDeny isBackFirstWhenWorkflowDeny
	 */
	public void setBackFirstWhenWorkflowDeny(boolean isBackFirstWhenWorkflowDeny) {
		this.isBackFirstWhenWorkflowDeny = isBackFirstWhenWorkflowDeny;
	}

	/**
	 * 現場承認を使用するか
	 * 
	 * @return 現場承認を使用するか
	 */
	public boolean isUseFieldApprove() {
		return useFieldApprove;
	}

	/**
	 * 現場承認を使用するか設定する
	 * 
	 * @param useFieldApprove 現場承認を使用するか
	 */
	public void setUseFieldApprove(boolean useFieldApprove) {
		this.useFieldApprove = useFieldApprove;
	}

	/**
	 * 伝票印刷するかどうか
	 * 
	 * @return true:印刷する
	 */
	public boolean isSlipPrint() {
		return slipPrint;
	}

	/**
	 * 伝票印刷するかどうか
	 * 
	 * @param slipPrint true:印刷する
	 */
	public void setSlipPrint(boolean slipPrint) {
		this.slipPrint = slipPrint;
	}

	/**
	 * 伝票印刷停止の初期値
	 * 
	 * @return true:印刷する
	 */
	public boolean getSlipPrintDefault() {
		return slipPrintDefault;
	}

	/**
	 * 伝票印刷停止の初期値
	 * 
	 * @param slipPrintStopDefault true:印刷する
	 */
	public void setSlipPrintDefault(boolean slipPrintStopDefault) {
		this.slipPrintDefault = slipPrintStopDefault;
	}

	/**
	 * useGroupAccountを取得する。
	 * 
	 * @return boolean useGroupAccount
	 */
	public boolean isUseGroupAccount() {
		return useGroupAccount;
	}

	/**
	 * useGroupAccountを設定する。
	 * 
	 * @param useGroupAccount
	 */
	public void setUseGroupAccount(boolean useGroupAccount) {
		this.useGroupAccount = useGroupAccount;
	}

	/**
	 * useHasDateCheckを取得する
	 * 
	 * @return useHasDateCheck
	 */
	public boolean isUseHasDateCheck() {
		return useHasDateCheck;
	}

	/**
	 * useHasDateCheckを設定する
	 * 
	 * @param useHasDateCheck
	 */
	public void setUseHasDateCheck(boolean useHasDateCheck) {
		this.useHasDateCheck = useHasDateCheck;
	}

	/**
	 * 換算端数処理方式
	 * 
	 * @return 換算端数処理方式
	 */
	public ExchangeFraction getExchangeFraction() {
		return exchangeFraction;
	}

	/**
	 * 換算端数処理方式
	 * 
	 * @param exchangeFraction 換算端数処理方式
	 */
	public void setExchangeFraction(ExchangeFraction exchangeFraction) {
		this.exchangeFraction = exchangeFraction;
	}

	/**
	 * 仮払消費税端数処理
	 * 
	 * @return 仮払消費税端数処理
	 */
	public ExchangeFraction getPaymentFunction() {
		return paymentFunction;
	}

	/**
	 * 仮払消費税端数処理
	 * 
	 * @param paymentFunction 仮払消費税端数処理
	 */
	public void setPaymentFunction(ExchangeFraction paymentFunction) {
		this.paymentFunction = paymentFunction;
	}

	/**
	 * 仮受消費税端数処理
	 * 
	 * @return 仮受消費税端数処理
	 */
	public ExchangeFraction getReceivingFunction() {
		return receivingFunction;
	}

	/**
	 * 仮受消費税端数処理
	 * 
	 * @param receivingFunction 仮受消費税端数処理
	 */
	public void setReceivingFunction(ExchangeFraction receivingFunction) {
		this.receivingFunction = receivingFunction;
	}

	/**
	 * 伝票有効期限チェック有無
	 * 
	 * @param slipTermCheck true:有
	 */
	public void setSlipTermCheck(boolean slipTermCheck) {
		this.slipTermCheck = slipTermCheck;
	}

	/**
	 * 伝票有効期限チェック有無
	 * 
	 * @return true:有
	 */
	public boolean isSlipTermCheck() {
		return slipTermCheck;
	}

	/**
	 * 換算区分
	 * 
	 * @return convertType 換算区分
	 */
	public ConvertType getConvertType() {
		return convertType;
	}

	/**
	 * 換算区分
	 * 
	 * @param convertType 換算区分
	 */
	public void setConvertType(ConvertType convertType) {
		this.convertType = convertType;
	}

	/**
	 * 経理承認を使用するか
	 * 
	 * @return 経理承認を使用するか
	 */
	public boolean isUseApprove() {
		return useApprove;
	}

	/**
	 * 経理承認を使用するかをセットする
	 * 
	 * @param useApprove 経理承認を使用するか
	 */
	public void setUseApprove(boolean useApprove) {
		this.useApprove = useApprove;
	}

	/**
	 * 通貨タイプに紐付く通貨を返す
	 * 
	 * @param currencyType 通貨タイプ
	 * @return 通貨タイプに紐付く通貨
	 */
	public Currency getCurrency(CurrencyType currencyType) {
		if (CurrencyType.FUNCTIONAL == currencyType) {
			return getFunctionalCurrency();
		}
		return getKeyCurrency();
	}

	/**
	 * 評価替レート区分を返す
	 * 
	 * @return 評価替レート区分
	 */
	public EvaluationRateDate getEvaluationRateDate() {
		return evaluationRateDate;
	}

	/**
	 * 評価替レート区分をセットする
	 * 
	 * @param evaluationRateDate 評価替レート区分
	 */
	public void setEvaluationRateDate(EvaluationRateDate evaluationRateDate) {
		this.evaluationRateDate = evaluationRateDate;
	}

	/**
	 * 自動設定項目1を返す
	 * 
	 * @return slipNoAdopt1
	 */
	public SlipNoAdopt getSlipNoAdopt1() {
		return slipNoAdopt1;
	}

	/**
	 * 自動設定項目1をセットする
	 * 
	 * @param slipNoAdopt1
	 */
	public void setSlipNoAdopt1(SlipNoAdopt slipNoAdopt1) {
		this.slipNoAdopt1 = slipNoAdopt1;
	}

	/**
	 * 自動設定項目2を返す
	 * 
	 * @return slipNoAdopt2
	 */
	public SlipNoAdopt getSlipNoAdopt2() {
		return slipNoAdopt2;
	}

	/**
	 * 自動設定項目2をセットする
	 * 
	 * @param slipNoAdopt2
	 */
	public void setSlipNoAdopt2(SlipNoAdopt slipNoAdopt2) {
		this.slipNoAdopt2 = slipNoAdopt2;
	}

	/**
	 * 自動設定項目3を返す
	 * 
	 * @return slipNoAdopt3
	 */
	public SlipNoAdopt getSlipNoAdopt3() {
		return slipNoAdopt3;
	}

	/**
	 * 自動設定項目3をセットする
	 * 
	 * @param slipNoAdopt3
	 */
	public void setSlipNoAdopt3(SlipNoAdopt slipNoAdopt3) {
		this.slipNoAdopt3 = slipNoAdopt3;
	}

	/**
	 * 中間決算仕訳を繰り越すか
	 * 
	 * @return true:中間決算仕訳を繰り越す
	 */
	public boolean isCarryJournalOfMidtermClosingForward() {
		return carryJournalOfMidtermClosingForward;
	}

	/**
	 * 中間決算仕訳を繰り越すかを設定する。
	 * 
	 * @param carryJournalOfMidtermClosingForward true:中間決算仕訳を繰り越す
	 */

	public void setCarryJournalOfMidtermClosingForward(boolean carryJournalOfMidtermClosingForward) {
		this.carryJournalOfMidtermClosingForward = carryJournalOfMidtermClosingForward;
	}

}

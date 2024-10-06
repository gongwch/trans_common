package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.program.SystemDivision;

/**
 * 伝票種別
 * 
 * @author AIS
 */
public class SlipType extends TransferBase {

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

	/** データ区分 */
	protected String dataType = null;

	/** システム区分 */
	protected SystemDivision systemDivision = null;

	/** システム区分コード */
	protected String systemDiv = "";

	/** 起票時の更新区分 */
	protected SlipState slipState = null;

	/** 仕訳インターフェース区分 */
	protected SlipState journalIfDivision = SlipState.ENTRY;

	/** 消費税計算区分(内税か) */
	protected boolean innerConsumptionTaxCalculation = true;

	/** 受入単位 */
	protected AcceptUnit acceptUnit = AcceptUnit.SLIPTYPE;

	/** 他システム区分 */
	protected boolean anotherSystemDivision = false;

	/** 他システムデータ受入時に採番するか */
	protected boolean takeNewSlipNo = false;

	/** 振戻取消の伝票種別コード */
	protected String reversingCode;

	/** 振戻取消の伝票種別 */
	protected SlipType reversingSlipType = null;

	/** インボイス制度チェック */
	protected boolean INV_SYS_FLG = false;

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
	 * データ区分
	 * 
	 * @return データ区分
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * データ区分
	 * 
	 * @param dataType データ区分
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * システム区分コード
	 * 
	 * @return システム区分コード
	 */
	public String getSystemDiv() {
		return systemDiv;
	}

	/**
	 * システム区分コード
	 * 
	 * @param systemDiv システム区分コード
	 */
	public void setSystemDiv(String systemDiv) {
		this.systemDiv = systemDiv;
	}

	/**
	 * 消費税計算区分(内税か)
	 * 
	 * @return true:内税
	 */
	public boolean isInnerConsumptionTaxCalculation() {
		return innerConsumptionTaxCalculation;
	}

	/**
	 * 消費税計算区分(内税か)
	 * 
	 * @param innerConsumptionTaxCalculation true:内税
	 */
	public void setInnerConsumptionTaxCalculation(boolean innerConsumptionTaxCalculation) {
		this.innerConsumptionTaxCalculation = innerConsumptionTaxCalculation;
	}

	/**
	 * 更新区分getter
	 * 
	 * @return 更新区分
	 */
	public SlipState getSlipState() {
		return slipState;
	}

	/**
	 * 更新区分setter
	 * 
	 * @param slipState
	 */
	public void setSlipState(SlipState slipState) {
		this.slipState = slipState;
	}

	/**
	 * @return takeNewSlipNo
	 */
	public boolean isTakeNewSlipNo() {
		return takeNewSlipNo;
	}

	/**
	 * @param takeNewSlipNo
	 */
	public void setTakeNewSlipNo(boolean takeNewSlipNo) {
		this.takeNewSlipNo = takeNewSlipNo;
	}

	/**
	 * @return 伝票番号を採番し直す・直さない
	 */
	public String getSlipIndexDivisionName() {

		if (takeNewSlipNo == false) {
			return "C02099";// しない
		} else {
			return "C02100";// する
		}

	}

	/**
	 * @return anotherSystemType
	 */
	public boolean isAnotherSystemDivision() {
		return anotherSystemDivision;
	}

	/**
	 * @param anotherSystemType
	 */
	public void setAnotherSystemDivision(boolean anotherSystemType) {
		this.anotherSystemDivision = anotherSystemType;
	}

	/**
	 * @return String
	 */
	public String getAnotherSystemDivisionName() {

		if (anotherSystemDivision == false) {
			return "C02097";// 他シス受入対象外
		} else {
			return "C02098";// 他シス受入対象
		}
	}

	/**
	 * @return acceptUnit
	 */
	public AcceptUnit isAcceptUnit() {
		return acceptUnit;
	}

	/**
	 * @param acceptUnit
	 */
	public void setAcceptUnit(AcceptUnit acceptUnit) {
		this.acceptUnit = acceptUnit;
	}

	/**
	 * @return journalIfDivision
	 */
	public SlipState getJounalIfDivision() {
		return journalIfDivision;
	}

	/**
	 * @param journalIfDivision
	 */
	public void setJounalIfDivision(SlipState journalIfDivision) {
		this.journalIfDivision = journalIfDivision;
	}

	/**
	 * システム区分を取得
	 * 
	 * @return システム区分
	 */
	public SystemDivision getSystemDivision() {
		return systemDivision;
	}

	/**
	 * システム区分を設定
	 * 
	 * @param systemDivision
	 */
	public void setSystemDivision(SystemDivision systemDivision) {
		this.systemDivision = systemDivision;
	}

	/**
	 * 振戻取消の伝票種別を取得
	 * 
	 * @return 振戻取消の伝票種別
	 */
	public SlipType getReversingSlipType() {
		return reversingSlipType;
	}

	/**
	 * 振戻取消の伝票種別を設定
	 * 
	 * @param reversingSlipType
	 */
	public void setReversingSlipType(SlipType reversingSlipType) {
		this.reversingSlipType = reversingSlipType;

		if (reversingSlipType != null) {
			this.reversingCode = reversingSlipType.getCode();
		} else {
			this.reversingCode = null;
		}
	}

	/**
	 * 振戻取消の伝票種別
	 * 
	 * @return 振戻取消の伝票種別
	 */
	public String getReversingCode() {
		return this.reversingCode;
	}

	/**
	 * 振戻伝票種別コード
	 * 
	 * @param reversingCode 振戻伝票種別コード
	 */
	public void setReversingCode(String reversingCode) {
		this.reversingCode = reversingCode;
	}
	
	/**
	 * インボイス制度チェックの取得
	 * 
	 * @return INV_SYS_FLG インボイス制度チェック
	 */ 
	public boolean isINV_SYS_FLG() { 
	     return INV_SYS_FLG;
	}
	/**
	 * インボイス制度チェックの設定
	 * 
	 * @param INV_SYS_FLG インボイス制度チェック
	 */
	public void setINV_SYS_FLG(boolean INV_SYS_FLG) {
	     this.INV_SYS_FLG = INV_SYS_FLG;
	}


}

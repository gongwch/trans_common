package jp.co.ais.trans2.model.program;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;

/**
 * システム区分
 * 
 * @author AIS
 */
public class SystemDivision extends TransferBase {

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

	/** 外部システム区分 */
	protected OuterSystemType outerSystemType = null;

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
	 * 外部システム区分の取得
	 * 
	 * @return foreignSystemType 外部システム区分
	 */
	public OuterSystemType getOuterSystemType() {
		return outerSystemType;
	}

	/**
	 * 外部システム区分の設定
	 * 
	 * @param foreignSystemType 外部システム区分
	 */
	public void setOuterSystemType(OuterSystemType foreignSystemType) {
		this.outerSystemType = foreignSystemType;
	}

}

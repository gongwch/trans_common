package jp.co.ais.trans2.model.language;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * 	言語コードの検索条件
 * @author AIS
 *
 */
public class LanguageSearchCondition extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = -5782442959967008907L;

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

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

}

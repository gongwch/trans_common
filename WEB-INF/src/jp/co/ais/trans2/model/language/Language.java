package jp.co.ais.trans2.model.language;

import jp.co.ais.trans.common.dt.*;

/**
 * 言語Entity
 * 
 * @author AIS
 */
public class Language extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = -6136170995614029871L;

	/** 言語コード */
	protected String code;

	/** 言語名 */
	protected String name;

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
	 * @return codeを戻します。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name codeを設定します。
	 */
	public void setName(String name) {
		this.name = name;
	}

}

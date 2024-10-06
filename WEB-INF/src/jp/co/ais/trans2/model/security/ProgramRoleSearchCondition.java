package jp.co.ais.trans2.model.security;

import java.util.Date;
import jp.co.ais.trans.common.dt.TransferBase;
import jp.co.ais.trans.common.except.*;

/**
 * プログラムロールマスタの検索条件
 * 
 * @author AIS
 */
public class ProgramRoleSearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = -8730963568795758545L;

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

	/** 言語コード */
	protected String lang = "";

	/**
	 * @return codeFromを戻します。
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ProgramRoleSearchCondition clone() {
		try {
			return (ProgramRoleSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * codeLikeを取得する。
	 * 
	 * @return String codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * codeLikeを設定する。
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * namekLikeを取得する。
	 * 
	 * @return String namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * namekLikeを設定する。
	 * 
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * namesLikeを取得する。
	 * 
	 * @return String namesLike
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * namesLikeを設定する。
	 * 
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * 言語コードを取得する。
	 * 
	 * @return 言語コード
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * 言語コードを設定する。
	 * 
	 * @param lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

}

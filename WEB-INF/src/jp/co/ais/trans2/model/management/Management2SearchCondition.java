package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 管理2の検索条件
 * 
 * @author AIS
 */
public class Management2SearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 3233838287647082966L;

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** コード(複数) */
	protected List<String> codeList = new LinkedList<String>();

	/** 対象会社リスト(getRefの場合のみ使用) */
	protected List<String> companyCodeList = null;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Management2SearchCondition clone() {
		try {
			return (Management2SearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	public String getCodeLike() {
		return codeLike;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public String getNamekLike() {
		return namekLike;
	}

	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	public String getNamesLike() {
		return namesLike;
	}

	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
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
	 * コード(複数)の取得
	 * 
	 * @return コード(複数)
	 */
	public List<String> getCodeList() {
		return this.codeList;
	}

	/**
	 * コード(複数)の取得
	 * 
	 * @return コード(複数)
	 */
	public String[] getManagement2CodeList() {
		if (codeList.isEmpty()) {
			return null;
		}

		return codeList.toArray(new String[codeList.size()]);
	}

	/**
	 * コード(複数)の設定
	 * 
	 * @param management2CodeList コード(複数)
	 */
	public void setManagement2CodeList(String[] management2CodeList) {
		addManagement2Code(management2CodeList);
	}

	/**
	 * コード(複数)の設定
	 * 
	 * @param list コード(複数)
	 */
	public void addManagement2Code(String... list) {
		for (String management2Code : list) {
			this.codeList.add(management2Code);
		}
	}

	/**
	 * コード(複数)のクリア
	 */
	public void clearManagement2CodeList() {
		codeList.clear();
	}

	/**
	 * 対象会社リスト(getRefの場合のみ使用)の取得
	 * 
	 * @return companyCodeList 対象会社リスト(getRefの場合のみ使用)
	 */
	public List<String> getCompanyCodeList() {
		return companyCodeList;
	}

	/**
	 * 対象会社リスト(getRefの場合のみ使用)の設定
	 * 
	 * @param companyCodeList 対象会社リスト(getRefの場合のみ使用)
	 */
	public void setCompanyCodeList(List<String> companyCodeList) {
		this.companyCodeList = companyCodeList;
	}
}

package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 管理3の検索条件
 * 
 * @author AIS
 */
public class Management3SearchCondition extends TransferBase implements Cloneable {

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

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** コード(複数) */
	protected List<String> codeList = new LinkedList<String>();

	/** 対象会社リスト(getRefの場合のみ使用) */
	protected List<String> companyCodeList = null;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Management3SearchCondition clone() {
		try {
			return (Management3SearchCondition) super.clone();

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
	 * 最終更新日時の取得
	 * 
	 * @return lastUpdateDate 最終更新日時
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * 最終更新日時の設定
	 * 
	 * @param lastUpdateDate 最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
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
	public String[] getManagement3CodeList() {
		if (codeList.isEmpty()) {
			return null;
		}

		return codeList.toArray(new String[codeList.size()]);
	}

	/**
	 * コード(複数)の設定
	 * 
	 * @param Management3CodeList コード(複数)
	 */
	public void setManagement3CodeList(String[] Management3CodeList) {
		addManagement3Code(Management3CodeList);
	}

	/**
	 * コード(複数)の設定
	 * 
	 * @param list コード(複数)
	 */
	public void addManagement3Code(String... list) {
		for (String Management3Code : list) {
			this.codeList.add(Management3Code);
		}
	}

	/**
	 * コード(複数)のクリア
	 */
	public void clearManagement3CodeList() {
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

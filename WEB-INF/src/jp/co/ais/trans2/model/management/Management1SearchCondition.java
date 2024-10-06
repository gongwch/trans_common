package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 管理1の検索条件
 * 
 * @author AIS
 */
public class Management1SearchCondition extends TransferBase implements Cloneable {

	/** 会社 */
	protected Company company = null;

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
	@Override
	public Management1SearchCondition clone() {
		try {
			return (Management1SearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * コード前方一致
	 * 
	 * @return コード前方一致
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * コード前方一致
	 * 
	 * @param codeLike コード前方一致
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * 検索名称like
	 * 
	 * @return 検索名称like
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * 検索名称like
	 * 
	 * @param namekLike 検索名称like
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * 略称like
	 * 
	 * @return 略称like
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * 略称like
	 * 
	 * @param namesLike 略称like
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * 会社
	 * 
	 * @return 会社
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * 会社
	 * 
	 * @param company 会社
	 */
	public void setCompany(Company company) {
		this.company = company;

		if (company == null) {
			setCompanyCode(null);

		} else {
			setCompanyCode(company.getCode());
		}
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
	public String[] getManagement1CodeList() {
		if (codeList.isEmpty()) {
			return null;
		}

		return codeList.toArray(new String[codeList.size()]);
	}

	/**
	 * コード(複数)の設定
	 * 
	 * @param management1CodeList コード(複数)
	 */
	public void setManagement1CodeList(String[] management1CodeList) {
		addManagement1Code(management1CodeList);
	}

	/**
	 * コード(複数)の設定
	 * 
	 * @param list コード(複数)
	 */
	public void addManagement1Code(String... list) {
		for (String management1Code : list) {
			this.codeList.add(management1Code);
		}
	}

	/**
	 * コード(複数)のクリア
	 */
	public void clearManagement1CodeList() {
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

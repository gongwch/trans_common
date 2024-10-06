package jp.co.ais.trans2.model.item.summary;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * MG0070ItemSummaryMaster - 科目集計マスタ - SearchCondition Class
 * 
 * @author AIS
 */
public class ItemSummarySearchCondition extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode;

	/** 科目体系コード */
	protected String kmtCode;

	/** 科目コード */
	protected String kmkCode;

	/** コード前方一致 */
	protected String codeLike;

	/** 略称あいまい */
	protected String namesLike;

	/** 検索名称あいまい */
	protected String namekLike;

	/** 開始コード */
	protected String codeFrom;

	/** 終了コード */
	protected String codeTo;

	/** 有効期間 */
	protected Date validTerm;

	/**
	 * * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ItemSummarySearchCondition clone() {
		try {
			return (ItemSummarySearchCondition) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 会社コード
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 科目体系コード
	 * @return kmtCode
	 */
	public String getKmtCode() {
		return kmtCode;
	}

	/**
	 * 科目体系コード
	 * @param kmtCode 設定する kmtCode
	 */
	public void setKmtCode(String kmtCode) {
		this.kmtCode = kmtCode;
	}

	/**
	 * 科目コード
	 * @return kmkCode
	 */
	public String getKmkCode() {
		return kmkCode;
	}

	/**
	 * 科目コード
	 * @param kmkCode 設定する kmkCode
	 */
	public void setKmkCode(String kmkCode) {
		this.kmkCode = kmkCode;
	}

	/**
	 * コード前方一致
	 * @return codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * コード前方一致
	 * @param codeLike 設定する codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * 略称あいまい検索
	 * @return namesLike
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * 略称あいまい検索
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * 検索名称あいまい検索
	 * @return namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * 検索名称あいまい検索
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * コード開始
	 * @return codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * コード開始
	 * @param codeFrom 設定する codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * コード終了
	 * @return codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * コード終了
	 * @param codeTo 設定する codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * 有効期限切れ
	 * @return validTermを戻します。
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * 有効期限切れ
	 * @param validTerm validTermを設定します。
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}
}
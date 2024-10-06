package jp.co.ais.trans2.model.slip;

import java.util.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 伝票種別の検索条件
 * 
 * @author AIS
 */
public class SlipTypeSearchCondition extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected List<String> codeList = new LinkedList<String>();

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

	/** 他シス区分参照フラグ */
	protected boolean isReferOtherSystemDivision = false;

	/** インボイス制度フラグ */
	protected boolean invoiceFlg = false;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SlipTypeSearchCondition clone() {
		try {
			return (SlipTypeSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * isReferOtherSystemDivisionを取得する。
	 * 
	 * @return boolean isReferOtherSystemDivision
	 */
	public boolean isReferOtherSystemDivision() {
		return isReferOtherSystemDivision;
	}

	/**
	 * isReferOtherSystemDivisionを設定する。
	 * 
	 * @param isReferOtherSystemDivision
	 */
	public void setReferOtherSystemDivision(boolean isReferOtherSystemDivision) {
		this.isReferOtherSystemDivision = isReferOtherSystemDivision;
	}

	/**
	 * コード
	 * 
	 * @return コード
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * コード
	 * 
	 * @param codes コード
	 */
	public void addCode(String... codes) {
		for (String code : codes) {
			codeList.add(code);
		}
	}

	/**
	 * コード
	 * 
	 * @param codes コード
	 */
	public void setCode(String... codes) {
		codeList.clear();

		for (String code : codes) {
			codeList.add(code);
		}
	}

	/**
	 * 開始コード
	 * 
	 * @return 開始コード
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * 開始コード
	 * 
	 * @param codeFrom 開始コード
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
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
	 * 終了コード
	 * 
	 * @return 終了コード
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * 終了コード
	 * 
	 * @param codeTo 終了コード
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * インボイス制度フラグの取得
	 * 
	 * @return invoiceFlg インボイス制度フラグ
	 */
	public boolean isInvoiceFlg() {
		return invoiceFlg;
	}

	/**
	 * インボイス制度フラグの設定
	 * 
	 * @param invoiceFlg インボイス制度フラグ
	 */
	public void setInvoiceFlg(boolean invoiceFlg) {
		this.invoiceFlg = invoiceFlg;
	}

}

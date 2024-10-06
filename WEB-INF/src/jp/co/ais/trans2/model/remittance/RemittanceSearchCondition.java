package jp.co.ais.trans2.model.remittance;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 送金目的の検索条件
 */
public class RemittanceSearchCondition extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** 送金目的コード */
	protected String code = null;

	/** 送金目的名称 */
	protected String name = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 送金目的名カナ */
	protected String kana = null;

	/** カナlike */
	protected String kanaLike = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** 国際収支コード */
	protected String balanceCode = null;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RemittanceSearchCondition clone() {
		try {
			return (RemittanceSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return 会社コードを戻す
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode 会社コードを設定する
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return 送金目的コードを戻す
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code 送金目的コードを設定する
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 送金目的名称を戻す
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 送金目的名称を設定する
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return codeLikeを戻す
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike codeLikeを設定する
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * @return 送金目的名カナを戻す
	 */
	public String getKana() {
		return kana;
	}

	/**
	 * @param kana 送金目的名カナを設定する
	 */
	public void setKana(String kana) {
		this.kana = kana;
	}

	/**
	 * @return kanaLikeを戻す
	 */
	public String getNameLike() {
		return kanaLike;
	}

	/**
	 * @param kanaLike kanaLikeを設定する
	 */
	public void setKanaLike(String kanaLike) {
		this.kanaLike = kanaLike;
	}

	/**
	 * @return codeFromを戻す
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFromを設定する
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeToを戻す
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeToを設定する
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return 名称あいまいを戻す
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike 名称あいまいを設定する
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * @return 国際収支コードを戻す
	 */
	public String getBalanceCode() {
		return balanceCode;
	}

	/**
	 * @param balanceCode 国際収支コードを設定する
	 */
	public void setBalanceCode(String balanceCode) {
		this.balanceCode = balanceCode;
	}
}

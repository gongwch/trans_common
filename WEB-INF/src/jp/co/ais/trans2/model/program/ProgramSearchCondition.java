package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * プログラムの検索条件
 * 
 * @author AIS
 */
public class ProgramSearchCondition extends TransferBase implements Cloneable {

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

	/** システム区分 */
	protected String systemCode = null;

	/** 実行可能なプログラム */
	protected boolean executable = true;

	/** メニュー用プログラム */
	protected boolean notExecutable = true;

	/** プログラムロール */
	protected String programRoleCode = null;

	/** 言語コード */
	protected String lang = "";

	/** メニュー表示以外のみ */
	protected boolean withoutMenu = false;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ProgramSearchCondition clone() {
		try {
			return (ProgramSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 言語コードの取得する
	 * 
	 * @return String
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * 言語コードの設定する
	 * 
	 * @param lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * メニュー用プログラムの取得
	 * 
	 * @return true:メニュー用プログラム
	 */
	public boolean isNotExecutable() {
		return notExecutable;
	}

	/**
	 * メニュー用プログラムの設定
	 * 
	 * @param notExecutable true:メニュー用プログラム
	 */
	public void setNotExecutable(boolean notExecutable) {
		this.notExecutable = notExecutable;
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
	 * コード前方一致の取得
	 * 
	 * @return codeLike コード前方一致
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * コード前方一致の設定
	 * 
	 * @param codeLike コード前方一致
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * 略称likeの取得
	 * 
	 * @return namesLike 略称like
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * 略称likeの設定
	 * 
	 * @param namesLike 略称like
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * 検索名称likeの取得
	 * 
	 * @return namekLike 検索名称like
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * 検索名称likeの設定
	 * 
	 * @param namekLike 検索名称like
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * システム区分の取得
	 * 
	 * @return systemCode システム区分
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * システム区分の設定
	 * 
	 * @param systemCode システム区分
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * 実行可能なプログラムの取得
	 * 
	 * @return executable 実行可能なプログラム
	 */
	public boolean isExecutable() {
		return executable;
	}

	/**
	 * 実行可能なプログラムの設定
	 * 
	 * @param executable 実行可能なプログラム
	 */
	public void setExecutable(boolean executable) {
		this.executable = executable;
	}

	/**
	 * プログラムロールの取得
	 * 
	 * @return programRoleCode プログラムロール
	 */
	public String getProgramRoleCode() {
		return programRoleCode;
	}

	/**
	 * プログラムロールの設定
	 * 
	 * @param programRoleCode プログラムロール
	 */
	public void setProgramRoleCode(String programRoleCode) {
		this.programRoleCode = programRoleCode;
	}

	/**
	 * メニュー表示以外のみの取得
	 * 
	 * @return withoutMenu メニュー表示以外のみ
	 */
	public boolean isWithoutMenu() {
		return withoutMenu;
	}

	/**
	 * メニュー表示以外のみの設定
	 * 
	 * @param withoutMenu メニュー表示以外のみ
	 */
	public void setWithoutMenu(boolean withoutMenu) {
		this.withoutMenu = withoutMenu;
	}

}

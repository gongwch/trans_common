package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * ユーザーの検索条件
 * 
 * @author AIS
 */
public class UserSearchCondition extends TransferBase implements Cloneable {

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

	/** パスワード */
	protected String password = null;

	/** 社員コード */
	protected String employeeCode = null;

	/** システム区分リスト */
	protected List<String> sysKbnList = null;

	/** 言語コード */
	protected String lang = "";

	/** アクセス権限フラグ１ */
	protected int AccessPermissionFlag1 = -1;

	/** アクセス権限フラグ２ */
	protected int AccessPermissionFlag2 = -1;

	/** アクセス権限フラグ３ */
	protected int AccessPermissionFlag3 = -1;

	/** アクセス権限フラグ４ */
	protected int AccessPermissionFlag4 = -1;

	/** アクセス権限フラグ５ */
	protected int AccessPermissionFlag5 = -1;

	/** アクセス権限フラグ６ */
	protected int AccessPermissionFlag6 = -1;

	/** アクセス権限フラグ７ */
	protected int AccessPermissionFlag7 = -1;

	/** アクセス権限フラグ８ */
	protected int AccessPermissionFlag8 = -1;

	/** アクセス権限フラグ９ */
	protected int AccessPermissionFlag9 = -1;

	/** アクセス権限フラグ１０ */
	protected int AccessPermissionFlag10 = -1;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public UserSearchCondition clone() {
		try {
			return (UserSearchCondition) super.clone();

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
	 * @return passwordを戻します。
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password passwordを設定します。
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * 社員コード
	 * 
	 * @return 社員コード
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * 社員コード
	 * 
	 * @param employeeCode 社員コード
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
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

	/**
	 * アクセス権限フラグ１の取得
	 * 
	 * @return AccessPermissionFlag1 アクセス権限フラグ１
	 */
	public int getAccessPermissionFlag1() {
		return AccessPermissionFlag1;
	}

	/**
	 * アクセス権限フラグ１の設定
	 * 
	 * @param AccessPermissionFlag1 アクセス権限フラグ１
	 */
	public void setAccessPermissionFlag1(int AccessPermissionFlag1) {
		this.AccessPermissionFlag1 = AccessPermissionFlag1;
	}

	/**
	 * アクセス権限フラグ２の取得
	 * 
	 * @return AccessPermissionFlag2 アクセス権限フラグ２
	 */
	public int getAccessPermissionFlag2() {
		return AccessPermissionFlag2;
	}

	/**
	 * アクセス権限フラグ２の設定
	 * 
	 * @param AccessPermissionFlag2 アクセス権限フラグ２
	 */
	public void setAccessPermissionFlag2(int AccessPermissionFlag2) {
		this.AccessPermissionFlag2 = AccessPermissionFlag2;
	}

	/**
	 * アクセス権限フラグ３の取得
	 * 
	 * @return AccessPermissionFlag3 アクセス権限フラグ３
	 */
	public int getAccessPermissionFlag3() {
		return AccessPermissionFlag3;
	}

	/**
	 * アクセス権限フラグ３の設定
	 * 
	 * @param AccessPermissionFlag3 アクセス権限フラグ３
	 */
	public void setAccessPermissionFlag3(int AccessPermissionFlag3) {
		this.AccessPermissionFlag3 = AccessPermissionFlag3;
	}

	/**
	 * アクセス権限フラグ４の取得
	 * 
	 * @return AccessPermissionFlag4 アクセス権限フラグ４
	 */
	public int getAccessPermissionFlag4() {
		return AccessPermissionFlag4;
	}

	/**
	 * アクセス権限フラグ４の設定
	 * 
	 * @param AccessPermissionFlag4 アクセス権限フラグ４
	 */
	public void setAccessPermissionFlag4(int AccessPermissionFlag4) {
		this.AccessPermissionFlag4 = AccessPermissionFlag4;
	}

	/**
	 * アクセス権限フラグ５の取得
	 * 
	 * @return AccessPermissionFlag5 アクセス権限フラグ５
	 */
	public int getAccessPermissionFlag5() {
		return AccessPermissionFlag5;
	}

	/**
	 * アクセス権限フラグ５の設定
	 * 
	 * @param AccessPermissionFlag5 アクセス権限フラグ５
	 */
	public void setAccessPermissionFlag5(int AccessPermissionFlag5) {
		this.AccessPermissionFlag5 = AccessPermissionFlag5;
	}

	/**
	 * アクセス権限フラグ６の取得
	 * 
	 * @return AccessPermissionFlag6 アクセス権限フラグ６
	 */
	public int getAccessPermissionFlag6() {
		return AccessPermissionFlag6;
	}

	/**
	 * アクセス権限フラグ６の設定
	 * 
	 * @param AccessPermissionFlag6 アクセス権限フラグ６
	 */
	public void setAccessPermissionFlag6(int AccessPermissionFlag6) {
		this.AccessPermissionFlag6 = AccessPermissionFlag6;
	}

	/**
	 * アクセス権限フラグ７の取得
	 * 
	 * @return AccessPermissionFlag7 アクセス権限フラグ７
	 */
	public int getAccessPermissionFlag7() {
		return AccessPermissionFlag7;
	}

	/**
	 * アクセス権限フラグ７の設定
	 * 
	 * @param AccessPermissionFlag7 アクセス権限フラグ７
	 */
	public void setAccessPermissionFlag7(int AccessPermissionFlag7) {
		this.AccessPermissionFlag7 = AccessPermissionFlag7;
	}

	/**
	 * アクセス権限フラグ８の取得
	 * 
	 * @return AccessPermissionFlag8 アクセス権限フラグ８
	 */
	public int getAccessPermissionFlag8() {
		return AccessPermissionFlag8;
	}

	/**
	 * アクセス権限フラグ８の設定
	 * 
	 * @param AccessPermissionFlag8 アクセス権限フラグ８
	 */
	public void setAccessPermissionFlag8(int AccessPermissionFlag8) {
		this.AccessPermissionFlag8 = AccessPermissionFlag8;
	}

	/**
	 * アクセス権限フラグ９の取得
	 * 
	 * @return AccessPermissionFlag9 アクセス権限フラグ９
	 */
	public int getAccessPermissionFlag9() {
		return AccessPermissionFlag9;
	}

	/**
	 * アクセス権限フラグ９の設定
	 * 
	 * @param AccessPermissionFlag9 アクセス権限フラグ９
	 */
	public void setAccessPermissionFlag9(int AccessPermissionFlag9) {
		this.AccessPermissionFlag9 = AccessPermissionFlag9;
	}

	/**
	 * アクセス権限フラグ１０の取得
	 * 
	 * @return AccessPermissionFlag10 アクセス権限フラグ１０
	 */
	public int getAccessPermissionFlag10() {
		return AccessPermissionFlag10;
	}

	/**
	 * アクセス権限フラグ１０の設定
	 * 
	 * @param AccessPermissionFlag10 アクセス権限フラグ１０
	 */
	public void setAccessPermissionFlag10(int AccessPermissionFlag10) {
		this.AccessPermissionFlag10 = AccessPermissionFlag10;
	}

	/**
	 * システム区分リストの取得
	 * 
	 * @return sysKbnList システム区分リスト
	 */
	public List<String> getSysKbnList() {
		return sysKbnList;
	}

	/**
	 * システム区分リストの設定
	 * 
	 * @param sysKbnList システム区分リスト
	 */
	public void setSysKbnList(List<String> sysKbnList) {
		this.sysKbnList = sysKbnList;
	}

}

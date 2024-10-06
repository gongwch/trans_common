package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.security.*;

/**
 * ユーザー
 * 
 * @author AIS
 */
public class User extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

	/** アクセス権限フラグカウント */
	public static final int ACCESS_FLAG_COUNT = 10;

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** 略称 */
	protected String names = null;

	/** 検索名称 */
	protected String namek = null;

	/** パスワード */
	protected String password = null;

	/** 所属部門 */
	protected Department department = null;

	/** 社員 */
	protected Employee employee = null;

	/** 言語コード */
	protected String language = null;

	/** 言語名称 */
	protected String languageName = null;

	/** プログラムロール */
	protected ProgramRole programRole = null;

	/** 開示ロール */
	protected UserRole userRole = null;

	/** 承認権限グループ */
	protected AprvRoleGroup aprvRoleGroup = null;

	/** INV. SIGNER DEPT */
	protected String signerDept = null;

	/** INV. SIGNER TITLE */
	protected String signerTitle = null;

	/** INV. SIGNER NAME */
	protected String signerName = null;

	/** INV. SIGN FILE NAME */
	protected String sineFileName = null;

	/** INV. SIGN FILE */
	protected USR_SIGN sineFile = null;

	/** E-MAIL */
	protected String eMailAddress = null;

	/** 伝票更新権限 */
	protected SlipRole slipRole = null;

	/** 経理部権限 */
	protected UserAccountRole userAccountRole = null;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** LF名 */
	protected String lfName = "";

	/** 色 */
	protected String lfColorType = "";

	/** LFのTabbedPanelのType */
	protected MenuType menuType;

	/** クライアントプリンタ名称 */
	protected String printerName = null;

	/** USR_DASH_CTL list */
	protected List<USR_DASH_CTL> USR_DASH_CTLList = null;

	/** アクセス権限フラグ１ */
	protected int AccessPermissionFlag1 = 0;

	/** アクセス権限フラグ２ */
	protected int AccessPermissionFlag2 = 0;

	/** アクセス権限フラグ３ */
	protected int AccessPermissionFlag3 = 0;

	/** アクセス権限フラグ４ */
	protected int AccessPermissionFlag4 = 0;

	/** アクセス権限フラグ５ */
	protected int AccessPermissionFlag5 = 0;

	/** アクセス権限フラグ６ */
	protected int AccessPermissionFlag6 = 0;

	/** アクセス権限フラグ７ */
	protected int AccessPermissionFlag7 = 0;

	/** アクセス権限フラグ８ */
	protected int AccessPermissionFlag8 = 0;

	/** アクセス権限フラグ９ */
	protected int AccessPermissionFlag9 = 0;

	/** アクセス権限フラグ１０ */
	protected int AccessPermissionFlag10 = 0;

	/**
	 * コンストラクタ.
	 */
	public User() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param code ユーザコード
	 */
	public User(String code) {
		this();
		this.code = code;
	}

	/**
	 * @return languageを戻します。
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language languageを設定します。
	 */
	public void setLanguage(String language) {
		this.language = language;
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
	 * @return dateFromを戻します。
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom dateFromを設定します。
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return dateToを戻します。
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo dateToを設定します。
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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
	 * @return nameを戻します。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name nameを設定します。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return namekを戻します。
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek namekを設定します。
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * @return namesを戻します。
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names namesを設定します。
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * @return departmentを戻します。
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department departmentを設定します。
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return signerDeptを戻します。
	 */
	public String getSignerDept() {
		return signerDept;
	}

	/**
	 * @param signerDept signerDeptを設定します。
	 */
	public void setSignerDept(String signerDept) {
		this.signerDept = signerDept;
	}

	/**
	 * @return signerTitleを戻します。
	 */
	public String getSignerTitle() {
		return signerTitle;
	}

	/**
	 * @param signerTitle signerTitleを設定します。
	 */
	public void setSignerTitle(String signerTitle) {
		this.signerTitle = signerTitle;
	}

	/**
	 * @return signerNameを戻します。
	 */
	public String getSignerName() {
		return signerName;
	}

	/**
	 * @param signerName signerNameを設定します。
	 */
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	/**
	 * @return sineFileNameを戻します。
	 */
	public String getSignFileName() {
		return sineFileName;
	}

	/**
	 * @param sineFileName signerNameを設定します。
	 */
	public void setSignFileName(String sineFileName) {
		this.sineFileName = sineFileName;
	}

	/**
	 * @return SignFileを戻します。
	 */
	public USR_SIGN getSignFile() {
		return sineFile;
	}

	/**
	 * @param sineFile SignFileを設定します。
	 */
	public void setSignFile(USR_SIGN sineFile) {
		this.sineFile = sineFile;
	}

	/**
	 * @return eMailAddressを戻します。
	 */
	public String getEMailAddress() {
		return eMailAddress;
	}

	/**
	 * @param eMailAddress eMailAddressを設定します。
	 */
	public void setEMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	/**
	 * 伝票更新権限
	 * 
	 * @return 伝票更新権限
	 */
	public SlipRole getSlipRole() {
		return slipRole;
	}

	/**
	 * 伝票更新権限
	 * 
	 * @param slipRole 伝票更新権限
	 */
	public void setSlipRole(SlipRole slipRole) {
		this.slipRole = slipRole;
	}

	/**
	 * 経理部権限
	 * 
	 * @return 経理部権限
	 */
	public UserAccountRole getUserAccountRole() {
		return userAccountRole;
	}

	/**
	 * 経理部権限
	 * 
	 * @param accountRole 経理部権限
	 */
	public void setUserAccountRole(UserAccountRole accountRole) {
		this.userAccountRole = accountRole;
	}

	/**
	 * 経理担当者か否かを返す
	 * 
	 * @return 経理担当者か否か
	 */
	public boolean isAccountant() {
		return (UserAccountRole.ACCOUNT == getUserAccountRole());
	}

	/**
	 * 社員
	 * 
	 * @return 社員
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * 社員
	 * 
	 * @param employee 社員
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return programRole
	 */
	public ProgramRole getProgramRole() {
		return programRole;
	}

	/**
	 * @param programRole
	 */
	public void setProgramRole(ProgramRole programRole) {
		this.programRole = programRole;
	}

	/**
	 * @return userRole
	 */
	public UserRole getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole
	 */
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	/**
	 * 承認権限グループを取得
	 * 
	 * @return aprvRoleGroup
	 */
	public AprvRoleGroup getAprvRoleGroup() {
		return aprvRoleGroup;
	}

	/**
	 * 承認権限グループコードを取得
	 * 
	 * @return aprvRoleGroup
	 */
	public String getAprvRoleGroupCode() {
		return aprvRoleGroup == null ? null : aprvRoleGroup.getAPRV_ROLE_GRP_CODE();
	}


	/**
	 * 承認権限グループをセットする
	 * 
	 * @param aprvRoleGroup aprvRoleGroup
	 */
	public void setAprvRoleGroup(AprvRoleGroup aprvRoleGroup) {
		this.aprvRoleGroup = aprvRoleGroup;
	}

	/**
	 * @return languageName
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * @param languageName
	 */
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	/**
	 * lfNameを取得する。
	 * 
	 * @return String lfName
	 */
	public String getLfName() {
		return lfName;
	}

	/**
	 * lfNameを設定する。
	 * 
	 * @param lfName
	 */
	public void setLfName(String lfName) {
		this.lfName = lfName;
	}

	/**
	 * lfColorTypeを取得する。
	 * 
	 * @return lfColorType
	 */
	public String getLfColorType() {
		return lfColorType;
	}

	/**
	 * lfColorTypeを設定する。
	 * 
	 * @param lfColorType
	 */
	public void setLfColorType(String lfColorType) {
		this.lfColorType = lfColorType;
	}

	/**
	 * メニュータイプ
	 * 
	 * @param type タイプ
	 */
	public void setMenuType(MenuType type) {
		this.menuType = type;
	}

	/**
	 * メニュータイプ1
	 * 
	 * @return true:メニュータイプ1
	 */
	public MenuType getMenuType() {
		return menuType;
	}

	/**
	 * ユーザの言語コードが英語かどうか
	 * 
	 * @return true:英語
	 */
	public boolean isEnglish() {
		return "en".equals(language);
	}

	/**
	 * クライアントプリンタ名称を戻します。
	 * 
	 * @return printerName
	 */
	public String getPrinterName() {
		return printerName;
	}

	/**
	 * @param printerName クライアントプリンタ名称を設定します。
	 */
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
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
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	/**
	 * USR_DASH_CTL listの取得
	 * 
	 * @return USR_DASH_CTLList USR_DASH_CTL list
	 */
	public List<USR_DASH_CTL> getUSR_DASH_CTLList() {
		return USR_DASH_CTLList;
	}

	/**
	 * USR_DASH_CTL listの設定
	 * 
	 * @param USR_DASH_CTLList USR_DASH_CTL list
	 */
	public void setUSR_DASH_CTLList(List<USR_DASH_CTL> USR_DASH_CTLList) {
		this.USR_DASH_CTLList = USR_DASH_CTLList;
	}

}

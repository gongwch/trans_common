package jp.co.ais.trans2.model.program;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;

/**
 * プログラムの検索条件
 * 
 * @author AIS
 */
public class MenuSearchCondition extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** グループコード */
	protected String grpCode = null;

	/** 親プログラムコード */
	protected String parentCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** メニュー区分 */
	protected MenuDivision menuDivision = null;

	/** プログラムロール */
	protected String programRoleCode = null;

	/** プログラムロール権限制御不要、true:制御不要 */
	protected boolean withoutProgramRole = false;

	/** 子ノードがないタブ取得、true:取得 */
	protected boolean withBlank = false;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public MenuSearchCondition clone() {
		try {
			return (MenuSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 会社コードを取得する。
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードを設定する。
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * グループコードを取得する。
	 * 
	 * @return グループコード
	 */
	public String getGrpCode() {
		return grpCode;
	}

	/**
	 * グループコードを設定する。
	 * 
	 * @param grpCode
	 */
	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	/**
	 * 親プログラムコードを取得する。
	 * 
	 * @return 親プログラムコード
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * 親プログラムコードを設定する。
	 * 
	 * @param parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * メニューコードを取得する。
	 * 
	 * @return メニューコード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * メニューコードを設定する。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * メニュー名称を取得する。
	 * 
	 * @return メニュー名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * メニュー名称を設定する。
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * メニュー区分を取得する。
	 * 
	 * @return メニュー区分
	 */
	public MenuDivision getMenuDivision() {
		return menuDivision;
	}

	/**
	 * メニュー区分を設定する。
	 * 
	 * @param menuDivision
	 */
	public void setMenuDivision(MenuDivision menuDivision) {
		this.menuDivision = menuDivision;
	}

	/**
	 * プログラムロールを取得する。
	 * 
	 * @return プログラムロール
	 */
	public String getProgramRoleCode() {
		return programRoleCode;
	}

	/**
	 * プログラムロールを設定する。
	 * 
	 * @param programRoleCode
	 */
	public void setProgramRoleCode(String programRoleCode) {
		this.programRoleCode = programRoleCode;
	}

	/**
	 * プログラムロール権限制御不要の取得
	 * 
	 * @return withoutProgramRole プログラムロール権限制御不要、true:制御不要
	 */
	public boolean isWithoutProgramRole() {
		return withoutProgramRole;
	}

	/**
	 * プログラムロール権限制御不要の設定
	 * 
	 * @param withoutProgramRole プログラムロール権限制御不要、true:制御不要
	 */
	public void setWithoutProgramRole(boolean withoutProgramRole) {
		this.withoutProgramRole = withoutProgramRole;
	}

	/**
	 * 子ノードがないタブ取得、true:取得の取得
	 * 
	 * @return withBlank 子ノードがないタブ取得、true:取得
	 */
	public boolean isWithBlank() {
		return withBlank;
	}

	/**
	 * 子ノードがないタブ取得、true:取得の設定
	 * 
	 * @param withBlank 子ノードがないタブ取得、true:取得
	 */
	public void setWithBlank(boolean withBlank) {
		this.withBlank = withBlank;
	}

}

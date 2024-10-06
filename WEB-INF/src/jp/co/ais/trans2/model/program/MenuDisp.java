package jp.co.ais.trans2.model.program;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;

/**
 * メニュー表示Entity
 * 
 * @author AIS
 */
public class MenuDisp extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** グループ情報 */
	protected SystemClassification systemClassification = null;

	/** 親プログラムコード */
	protected String parentCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** メニュー区分 */
	protected MenuDivision menuDivision = null;

	/** 表示順序 */
	protected int dispIndex = 0;

	/** プログラムグループに属するプログラム群 */
	protected Program program = null;

	/**
	 * ノードに表示する名称を取得する
	 * 
	 * @return ノードに表示する名称
	 */
	public String getViewName() {
		return this.toString();
	}

	/**
	 * オブジェクトのオリジナル文字表現を返す.
	 * 
	 * @return 名称
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * エンティティの比較に使用する。
	 * 
	 * @param obj 比較
	 * @return boolean
	 */
	public boolean equals(MenuDisp obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			return this.code.equals(obj.getCode());
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
	 * グループ情報を取得する。
	 * 
	 * @return グループ情報
	 */
	public SystemClassification getSystemClassification() {
		return systemClassification;
	}

	/**
	 * グループ情報を設定する。
	 * 
	 * @param systemClassification
	 */
	public void setSystemClassification(SystemClassification systemClassification) {
		this.systemClassification = systemClassification;
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
	 * メニューかどうかを判定する
	 * 
	 * @return true:メニュー、false:ノード
	 */
	public boolean isMenu() {
		return MenuDivision.MENU.equals(menuDivision);
	}

	/**
	 * 表示順序を取得する。
	 * 
	 * @return 表示順序
	 */
	public int getDispIndex() {
		return dispIndex;
	}

	/**
	 * 表示順序を設定する。
	 * 
	 * @param dispIndex
	 */
	public void setDispIndex(int dispIndex) {
		this.dispIndex = dispIndex;
	}

	/**
	 * プログラムグループ情報を取得する。
	 * 
	 * @return プログラムグループ
	 */
	public Program getProgram() {
		return program;
	}

	/**
	 * プログラムグループを設定する。
	 * 
	 * @param program
	 */
	public void setProgram(Program program) {
		this.program = program;
	}

}

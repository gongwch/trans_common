package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 部門階層Entity
 * 
 * @author AIS
 */
public class DepartmentDisp extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 親プログラムコード */
	protected String parentCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** 部門グループに属するプログラム群 */
	protected Department department = null;

	/** 登録日付 */
	protected Date inpDate;

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
	 * @return コード
	 */
	public String toStringCode() {
		return this.code;
	}

	/**
	 * オブジェクトのオリジナル文字表現を返す.
	 * 
	 * @return 名称
	 */
	@Override
	public String toString() {
		return this.code + " " + this.name;
	}

	/**
	 * エンティティの比較に使用する。
	 * 
	 * @param obj 比較
	 * @return boolean
	 */
	public boolean equals(DepartmentDisp obj) {
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
	 * 部門階層コードを取得する。
	 * 
	 * @return メニューコード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 部門階層コードを設定する。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 部門階層名称を取得する。
	 * 
	 * @return メニュー名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 部門階層名称を設定する。
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 部門グループ情報を取得する。
	 * 
	 * @return 部門グループ
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * 部門グループを設定する。
	 * 
	 * @param department
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * 登録日付を取得する。
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * * 登録日付を設定する。
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

}

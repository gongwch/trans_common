package jp.co.ais.trans2.model.program;

import java.awt.*;

import jp.co.ais.trans.common.dt.*;

/**
 * システム
 * 
 * @author AIS
 */
public class SystemClassification extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 5555796352356967433L;

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

	/** 表示順序 */
	protected int dispIndex = 0;

	/** プログラムグループのカラー */
	protected Color menuColor = null;

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
	 * コードを取得する。
	 * 
	 * @return コード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * コードを設定する。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称を取得する。
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称を設定する。
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 略称を取得する。
	 * 
	 * @return 略称
	 */
	public String getNames() {
		return names;
	}

	/**
	 * 略称を設定する。
	 * 
	 * @param names
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * 検索名称を取得する。
	 * 
	 * @return 検索名称
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * 検索名称を設定する。
	 * 
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
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
	 * プログラムグループのカラーを取得する。
	 * 
	 * @return プログラムグループのカラー
	 */
	public Color getMenuColor() {
		return menuColor;
	}

	/**
	 * プログラムグループのカラーを設定する。
	 * 
	 * @param menuColor
	 */
	public void setMenuColor(Color menuColor) {
		this.menuColor = menuColor;
	}

}

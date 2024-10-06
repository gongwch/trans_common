package jp.co.ais.trans2.model.program;

import java.awt.*;
import java.util.List;

import jp.co.ais.trans.common.dt.*;

/**
 * 体系化されたプログラム群<br>
 * 画面上の並び等に使用する
 * 
 * @author AIS
 */
public class SystemizedProgram extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 5153074987035799051L;

	/** 会社コード */
	protected String companyCode = null;

	/** プログラムグループコード */
	protected String programGroupCode = null;

	/** プログラムグループ名称 */
	protected String programGroupName = null;

	/** プログラムグループに属するプログラム群 */
	protected List<MenuDisp> menuDisp = null;

	// TODO:プログラムマスタによるメニュー構築⇒削除予定
	/** プログラムグループに属するプログラム群 */
	protected List<Program> programs = null;

	/** プログラムグループのカラー */
	protected Color menuColor = null;

	/** 表示順序 */
	protected int dispIndex = 0;

	/**
	 * @return menuColor プログラムグループのカラーを設定します。
	 */
	public Color getMenuColor() {
		return menuColor;
	}

	/**
	 * @param menuColor プログラムグループのカラーを戻します。
	 */
	public void setMenuColor(Color menuColor) {
		this.menuColor = menuColor;
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
	 * @return programGroupCodeを戻します。
	 */
	public String getProgramGroupCode() {
		return programGroupCode;
	}

	/**
	 * @param programGroupCode programGroupCodeを設定します。
	 */
	public void setProgramGroupCode(String programGroupCode) {
		this.programGroupCode = programGroupCode;
	}

	/**
	 * @return programGroupNameを戻します。
	 */
	public String getProgramGroupName() {
		return programGroupName;
	}

	/**
	 * @param programGroupName programGroupNameを設定します。
	 */
	public void setProgramGroupName(String programGroupName) {
		this.programGroupName = programGroupName;
	}

	/**
	 * @return programsを戻します。
	 */
	public List<MenuDisp> getMenuDisp() {
		return menuDisp;
	}

	/**
	 * @param menuDisp menuDispを設定します。
	 */
	public void setMenuDisp(List<MenuDisp> menuDisp) {
		this.menuDisp = menuDisp;
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
	 * @return programsを戻します。
	 */
	public List<Program> getPrograms() {
		// TODO:プログラムマスタによるメニュー構築⇒削除予定
		return programs;
	}

	/**
	 * @param programs programsを設定します。
	 */
	public void setPrograms(List<Program> programs) {
		// TODO:プログラムマスタによるメニュー構築⇒削除予定
		this.programs = programs;
	}
}

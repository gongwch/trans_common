package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * プログラムEntity
 * 
 * @author AIS
 */
public class Program extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 6908147419251825923L;

	/** 会社コード */
	protected String companyCode = null;

	/** システム区分 */
	protected SystemClassification systemClassification = null;

	/** システム区分コード */
	protected String sysCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** 略称 */
	protected String names = null;

	/** 検索名称 */
	protected String namek = null;

	/** コメント */
	protected String comment = null;

	/** ロードクラス名称 */
	protected String loadClassName = null;

	/** 有効期間開始 */
	protected Date termFrom = null;

	/** 有効期間終了 */
	protected Date termTo = null;

	/** 処理権限区分 */
	protected int procAuthKbn = -1;

	// TODO:プログラムマスタによるメニュー構築⇒削除予定
	/** 親プログラムコード */
	protected String parentPrgCode = null;

	// TODO:プログラムマスタによるメニュー構築⇒削除予定
	/** メニュー区分 */
	protected int menuKbn = 0;

	// TODO:プログラムマスタによるメニュー構築⇒削除予定
	/** 表示順 */
	protected int displayIndex = 0;

	/**
	 * システム区分コードの取得
	 * 
	 * @return sysCode システム区分コード
	 */
	public String getSysCode() {
		return sysCode;
	}

	/**
	 * システム区分コードの設定
	 * 
	 * @param sysCode システム区分コード
	 */
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
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
	 * @return commentを戻します。
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment commentを設定します。
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * @return parentPrgCodeを戻します。
	 */
	public String getParentPrgCode() {
		return parentPrgCode;
	}

	/**
	 * @param parentPrgCode parentPrgCodeを設定します。
	 */
	public void setParentPrgCode(String parentPrgCode) {
		this.parentPrgCode = parentPrgCode;
	}

	/**
	 * @return menuKbnを戻します。
	 */
	public int getMenuKbn() {
		return menuKbn;
	}

	/**
	 * @param menuKbn menuKbnを設定します。
	 */
	public void setMenuKbn(int menuKbn) {
		this.menuKbn = menuKbn;
	}

	/**
	 * メニューかどうかを判定する
	 * 
	 * @return true:メニュー、false:ノード
	 */
	public boolean isMenu() {
		return !BooleanUtil.toBoolean(this.menuKbn);
	}

	/**
	 * @return displayIndexを戻します。
	 */
	public int getDisplayIndex() {
		return displayIndex;
	}

	/**
	 * @param displayIndex displayIndexを設定します。
	 */
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

	/**
	 * @return loadClassNameを戻します。
	 */
	public String getLoadClassName() {
		return loadClassName;
	}

	/**
	 * @param loadClassName loadClassNameを設定します。
	 */
	public void setLoadClassName(String loadClassName) {
		this.loadClassName = loadClassName;
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
	 * @return termFromを戻します。
	 */
	public Date getTermFrom() {
		return termFrom;
	}

	/**
	 * @param termFrom termFromを設定します。
	 */
	public void setTermFrom(Date termFrom) {
		this.termFrom = termFrom;
	}

	/**
	 * @return termToを戻します。
	 */
	public Date getTermTo() {
		return termTo;
	}

	/**
	 * @param termTo termToを設定します。
	 */
	public void setTermTo(Date termTo) {
		this.termTo = termTo;
	}

	/**
	 * 処理権限区分を設定する。
	 * 
	 * @param procAuthKbn 処理権限区分
	 */
	public void setProcAuthKbn(int procAuthKbn) {
		this.procAuthKbn = procAuthKbn;
	}

	/**
	 * 処理権限区分を取得する。
	 * 
	 * @return int procAuthKbn 処理権限区分
	 */
	public int getProcAuthKbn() {
		return procAuthKbn;
	}

	/**
	 * @return systemClassificationを戻します。
	 */
	public SystemClassification getSystemClassification() {
		return systemClassification;
	}

	/**
	 * @param systemClassification systemClassificationを設定します。
	 */
	public void setSystemClassification(SystemClassification systemClassification) {
		this.systemClassification = systemClassification;
	}

}

package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * ユーザーロールマスタ
 * 
 * @author AIS
 */
public class UserRole extends TransferBase {

	/** 会社コード */
	protected String companyCode;

	/** ロールコード */
	protected String code;

	/** ロール名称 */
	protected String name;

	/** 略称 */
	protected String names = null;

	/** 検索名称 */
	protected String namek = null;

	/** 有効期間開始 */
	protected Date termFrom = null;

	/** 有効期間終了 */
	protected Date termTo = null;

	/** 登録日付 */
	protected Date inpDate;

	/** 更新日付 */
	protected Date updDate;

	/** プログラムID */
	protected String prgId;

	/** ユーザーID */
	protected String usrId;

	/** 部門開示レベル */
	protected List<RoleDepartmentLevel> depLvlList = null;

	/** 科目開示レベル */
	protected List<RoleItemLevel> itemLvlList = null;

	/**
	 * depLvlListを取得する。
	 * 
	 * @return List<RoleDepartmentLevel> depLvlList
	 */
	public List<RoleDepartmentLevel> getDepLvlList() {
		return depLvlList;
	}

	/**
	 * depLvlListを設定する。
	 * 
	 * @param depLvlList
	 */
	public void setDepLvlList(List<RoleDepartmentLevel> depLvlList) {
		this.depLvlList = depLvlList;
	}

	/**
	 * depLvlListを追加する。
	 * 
	 * @param bean
	 */
	public void addDepLvlList(RoleDepartmentLevel bean) {

		if (Util.isNullOrEmpty(this.depLvlList)) {
			this.depLvlList = new LinkedList<RoleDepartmentLevel>();
		}

		this.depLvlList.add(bean);
	}

	/**
	 * itemLvlListを取得する。
	 * 
	 * @return List<RoleItemLevel> itemLvlList
	 */
	public List<RoleItemLevel> getItemLvlList() {
		return itemLvlList;
	}

	/**
	 * itemLvlListを設定する。
	 * 
	 * @param itemLvlList
	 */
	public void setItemLvlList(List<RoleItemLevel> itemLvlList) {
		this.itemLvlList = itemLvlList;
	}

	/**
	 * itemLvlListを追加する。
	 * 
	 * @param bean
	 */
	public void addItemLvlList(RoleItemLevel bean) {

		if (Util.isNullOrEmpty(this.itemLvlList)) {
			this.itemLvlList = new LinkedList<RoleItemLevel>();
		}

		this.itemLvlList.add(bean);
	}

	/**
	 * codeを取得する。
	 * 
	 * @return String code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * codeを設定する。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * companyCodeを取得する。
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCodeを設定する。
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * inpDateを取得する。
	 * 
	 * @return Date inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * inpDateを設定する。
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * nameを取得する。
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * nameを設定する。
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * namekを取得する。
	 * 
	 * @return String namek
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * namekを設定する。
	 * 
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * namesを取得する。
	 * 
	 * @return String names
	 */
	public String getNames() {
		return names;
	}

	/**
	 * namesを設定する。
	 * 
	 * @param names
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * prgIdを取得する。
	 * 
	 * @return String prgId
	 */
	public String getPrgId() {
		return prgId;
	}

	/**
	 * prgIdを設定する。
	 * 
	 * @param prgId
	 */
	public void setPrgId(String prgId) {
		this.prgId = prgId;
	}

	/**
	 * termFromを取得する。
	 * 
	 * @return Date termFrom
	 */
	public Date getTermFrom() {
		return termFrom;
	}

	/**
	 * termFromを設定する。
	 * 
	 * @param termFrom
	 */
	public void setTermFrom(Date termFrom) {
		this.termFrom = termFrom;
	}

	/**
	 * termToを取得する。
	 * 
	 * @return Date termTo
	 */
	public Date getTermTo() {
		return termTo;
	}

	/**
	 * termToを設定する。
	 * 
	 * @param termTo
	 */
	public void setTermTo(Date termTo) {
		this.termTo = termTo;
	}

	/**
	 * updDateを取得する。
	 * 
	 * @return Date updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * updDateを設定する。
	 * 
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * usrIdを取得する。
	 * 
	 * @return String usrId
	 */
	public String getUsrId() {
		return usrId;
	}

	/**
	 * usrIdを設定する。
	 * 
	 * @param usrId
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

}

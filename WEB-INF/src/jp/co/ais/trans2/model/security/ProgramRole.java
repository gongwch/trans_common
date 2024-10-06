package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.program.*;

/**
 * プログラムロールEntity
 * 
 * @author AIS
 */
public class ProgramRole extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 6908147419251825923L;

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

	/** 有効期間開始 */
	protected Date termFrom = null;

	/** 有効期間終了 */
	protected Date termTo = null;

	/** プログラム略称 */
	protected String prgNameS = null;

	/** システム区分 */
	protected String sysKdn = null;

	/** プログラムコード */
	protected String prgCode = null;

	/** 登録日付 */
	protected Date inpDate;

	/** 更新日付 */
	protected Date updDate;

	/** プログラムID */
	protected String prgId;

	/** ユーザーID */
	protected String usrId;

	/** 処理権限区分 */
	protected int procAuthKbn;

	/** プログラムコードリスト */
	protected List<Program> programList = null;

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
	 * programCodeListを取得する。
	 * 
	 * @return List<String> programCodeList
	 */
	public List<Program> getProgramList() {
		return programList;
	}

	/**
	 * programListを設定する。
	 * 
	 * @param programList
	 */
	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}

	/**
	 * programCodeを追加する。
	 * 
	 * @param bean
	 */
	public void addProgramCodeList(Program bean) {

		if (Util.isNullOrEmpty(this.programList)) {
			this.programList = new LinkedList<Program>();
		}

		this.programList.add(bean);
	}

	/**
	 * prgNamekを取得する。
	 * 
	 * @return String prgNamek
	 */
	public String getPrgNameS() {
		return prgNameS;
	}

	/**
	 * prgNamekを設定する。
	 * 
	 * @param prgNamek
	 */
	public void setPrgNameS(String prgNamek) {
		this.prgNameS = prgNamek;
	}

	/**
	 * sysKdnを取得する。
	 * 
	 * @return String sysKdn
	 */
	public String getSysKdn() {
		return sysKdn;
	}

	/**
	 * sysKdnを設定する。
	 * 
	 * @param sysKdn
	 */
	public void setSysKdn(String sysKdn) {
		this.sysKdn = sysKdn;
	}

	/**
	 * prgCodeを取得する。
	 * 
	 * @return String prgCode
	 */
	public String getPrgCode() {
		return prgCode;
	}

	/**
	 * prgCodeを設定する。
	 * 
	 * @param prgCode
	 */
	public void setPrgCode(String prgCode) {
		this.prgCode = prgCode;
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

	/**
	 * 処理権限区分を取得する。
	 * 
	 * @return int procAuthKbn 処理権限区分
	 */
	public int getProcAuthKbn() {
		return procAuthKbn;
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
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ProgramRole clone() {
		try {
			return (ProgramRole) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

}

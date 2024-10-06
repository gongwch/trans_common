package jp.co.ais.trans2.model.port;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * Port
 */
public class Port extends TransferBase implements Cloneable, AutoCompletable, FilterableEntity {

	/** クローン */
	@Override
	public Port clone() {
		try {
			return (Port) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return 表示名
	 */
	public String getDisplayText() {
		StringBuilder sb = new StringBuilder();

		sb.append(Util.avoidNull(getName()));
		sb.append("/");
		sb.append(Util.avoidNull(COU_NAME));
		if (!Util.isNullOrEmpty(UNLOCODE)) {
			sb.append("/");
			sb.append(Util.avoidNull(UNLOCODE));
		}

		return sb.toString();
	}

	/** 会社コード */
	protected String companyCode = null;

	/** Portコード */
	protected String code = null;

	/** Port名称 */
	protected String name = null;

	/** Port名称（内航用） */
	protected String name_N = null;

	/** Port略称 */
	protected String names = null;

	/** Port検索略称 */
	protected String namek = null;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** Update Date */
	protected Date UPD_DATE = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** Remarks */
	protected String REMARKS = null;

	/** REGION_CODE */
	protected String REGION_CODE = null;

	/** REGION_CODE */
	protected String REGION_NAME = null;

	/** MLIT_REGION_CODE */
	protected String MLIT_REGION_CODE = null;

	/** MLIT_COUNTRY_CODE */
	protected String MLIT_COUNTRY_CODE = null;

	/** MLIT_REGION_NAME */
	protected String MLIT_REGION_NAME = null;

	/** MLIT_COUNTRY_NAME */
	protected String MLIT_COUNTRY_NAME = null;

	/** LINER_REMARKS */
	protected String LINER_REMARKS;

	/** 国際コード */
	protected String UNLOCODE = null;

	/** COUNTRY CODE */
	protected String COU_CODE = null;

	/** LAT */
	protected BigDecimal LAT = null;

	/** LNG */
	protected BigDecimal LNG = null;

	/** 時差 */
	protected BigDecimal GMT_TIMEZONE = null;

	/** 国名 */
	protected String COU_NAME = null;

	/** S_ECA_FLG */
	protected int S_ECA_FLG = -1;

	/** IPP_FLG */
	protected int IPP_FLG = -1;

	/** ユーザーID */
	protected String USR_ID = null;

	/** Port Liner Charge */
	protected List<PortLinerCharge> linerChargeList = new ArrayList<PortLinerCharge>();

	/** T/S港 日産専用船カスタマイズ */
	protected String TS_PORT_CODE;

	/** T/S港 日産専用船カスタマイズ */
	protected String TS_PORT_NAME;

	/** 標準港代理店コード */
	protected String STD_PORT_AGENT_CODE = null;

	/** 代理店名称 */
	protected String STD_PORT_AGENT_NAME = null;

	/** 標準港通貨コード */
	protected String PCG_EST_CUR_CODE = null;

	/** 標準港費金額 */
	protected BigDecimal PCG_EST_AMT = null;

	/**
	 * 会社コードを取得します。
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードを設定します。
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Portコードを取得します。
	 * 
	 * @return Portコード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Portコードを設定します。
	 * 
	 * @param code Portコード
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Port名称を取得します。
	 * 
	 * @return Port名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Port名称を設定します。
	 * 
	 * @param name Port名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Port名称（内航用）を取得します。
	 * 
	 * @return Port名称（内航用）
	 */
	public String getName_N() {
		return name_N;
	}

	/**
	 * Port名称（内航用）を設定します。
	 * 
	 * @param name_N Port名称（内航用）
	 */
	public void setName_N(String name_N) {
		this.name_N = name_N;
	}

	/**
	 * Port略称を取得します。
	 * 
	 * @return Port略称
	 */
	public String getNames() {
		return names;
	}

	/**
	 * Port略称を設定します。
	 * 
	 * @param names Port略称
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * Port検索略称を取得します。
	 * 
	 * @return Port検索略称
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * Port検索略称を設定します。
	 * 
	 * @param namek Port検索略称
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * Port検索略称を設定します。
	 * 
	 * @param MLIT_REGION_CODE Port検索略称
	 */
	public void setMLIT_REGION_CODE(String MLIT_REGION_CODE) {
		this.MLIT_REGION_CODE = MLIT_REGION_CODE;
	}

	/**
	 * Port検索略称を取得します。
	 * 
	 * @return MLIT Region Code
	 */
	public String getMLIT_REGION_CODE() {
		return MLIT_REGION_CODE;
	}

	/**
	 * Port検索略称を設定します。
	 * 
	 * @param MLIT_REGION_NAME Port検索略称
	 */
	public void setMLIT_REGION_NAME(String MLIT_REGION_NAME) {
		this.MLIT_REGION_NAME = MLIT_REGION_NAME;
	}

	/**
	 * Port検索略称を取得します。
	 * 
	 * @return MLIT Region Code
	 */
	public String getMLIT_REGION_NAME() {
		return MLIT_REGION_NAME;
	}

	/**
	 * Port検索略称を設定します。
	 * 
	 * @param MLIT_COUNTRY_CODE Port検索略称
	 */
	public void setMLIT_COUNTRY_CODE(String MLIT_COUNTRY_CODE) {
		this.MLIT_COUNTRY_CODE = MLIT_COUNTRY_CODE;
	}

	/**
	 * Port検索略称を取得します。
	 * 
	 * @return MLIT Region Code
	 */
	public String getMLIT_COUNTRY_CODE() {
		return MLIT_COUNTRY_CODE;
	}

	/**
	 * Port検索略称を設定します。
	 * 
	 * @param MLIT_COUNTRY_NAME Port検索略称
	 */
	public void setMLIT_COUNTRY_NAME(String MLIT_COUNTRY_NAME) {
		this.MLIT_COUNTRY_NAME = MLIT_COUNTRY_NAME;
	}

	/**
	 * Port検索略称を取得します。
	 * 
	 * @return MLIT Region Code
	 */
	public String getMLIT_COUNTRY_NAME() {
		return MLIT_COUNTRY_NAME;
	}

	/**
	 * Port検索略称を設定します。
	 * 
	 * @param LINER_REMARKS Port検索略称
	 */
	public void setLINER_REMARKS(String LINER_REMARKS) {
		this.LINER_REMARKS = LINER_REMARKS;
	}

	/**
	 * @return REGION_CODE
	 */
	public String getREGION_CODE() {
		return REGION_CODE;
	}

	/**
	 * @param REGION_CODE
	 */
	public void setREGION_CODE(String REGION_CODE) {
		this.REGION_CODE = REGION_CODE;
	}

	/**
	 * @return REGION_NAME
	 */
	public String getREGION_NAME() {
		return REGION_NAME;
	}

	/**
	 * @param rEGION_NAME
	 */
	public void setREGION_NAME(String rEGION_NAME) {
		REGION_NAME = rEGION_NAME;
	}

	/**
	 * Port検索略称を取得します。
	 * 
	 * @return Liner Remarks
	 */
	public String getLINER_REMARKS() {
		return LINER_REMARKS;
	}

	/**
	 * @return IPP_FLG
	 */
	public int getIPP_FLG() {
		return IPP_FLG;
	}

	/**
	 * @param IPP_FLG
	 */
	public void setIPP_FLG(int IPP_FLG) {
		this.IPP_FLG = IPP_FLG;
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
	 * Remarksを取得します。
	 * 
	 * @return REMARKS
	 */
	public String getREMARKS() {
		return REMARKS;
	}

	/**
	 * remarksを設定します。
	 * 
	 * @param REMARKS
	 */
	public void setREMARKS(String REMARKS) {
		this.REMARKS = REMARKS;
	}

	/**
	 * Remarksを取得します。
	 * 
	 * @return REMARKS
	 */
	public String getRemarks() {
		return REMARKS;
	}

	/**
	 * remarksを設定します。
	 * 
	 * @param REMARKS
	 */
	public void setRemarks(String REMARKS) {
		this.REMARKS = REMARKS;
	}

	/**
	 * 国際コードの取得
	 * 
	 * @return UNLOCODE 国際コード
	 */
	public String getUNLOCODE() {
		return UNLOCODE;
	}

	/**
	 * 国際コードの設定
	 * 
	 * @param UNLOCODE 国際コード
	 */
	public void setUNLOCODE(String UNLOCODE) {
		this.UNLOCODE = UNLOCODE;
	}

	/**
	 * GET COUNTRY CODE
	 * 
	 * @return Portコード
	 */
	public String getCOU_CODE() {
		return COU_CODE;
	}

	/**
	 * @param COU_CODE
	 */
	public void setCOU_CODE(String COU_CODE) {
		this.COU_CODE = COU_CODE;
	}

	/**
	 * @return the gMT_TIMEZONE
	 */
	public BigDecimal getGMT_TIMEZONE() {
		return GMT_TIMEZONE;
	}

	/**
	 * @param gMT_TIMEZONE the gMT_TIMEZONE to set
	 */
	public void setGMT_TIMEZONE(BigDecimal gMT_TIMEZONE) {
		GMT_TIMEZONE = gMT_TIMEZONE;
	}

	/**
	 * @return COU_NAME
	 */
	public String getCOU_NAME() {
		return COU_NAME;
	}

	/**
	 * @param cOU_NAME
	 */
	public void setCOU_NAME(String cOU_NAME) {
		COU_NAME = cOU_NAME;
	}

	/**
	 * @return LAT
	 */
	public BigDecimal getLAT() {
		return LAT;
	}

	/**
	 * @param lAT
	 */

	public void setLAT(BigDecimal lAT) {
		LAT = lAT;
	}

	/**
	 * @return LNG
	 */
	public BigDecimal getLNG() {
		return LNG;
	}

	/**
	 * @param lNG
	 */
	public void setLNG(BigDecimal lNG) {
		LNG = lNG;
	}

	/**
	 * @return S_ECA_FLG
	 */
	public int getS_ECA_FLG() {
		return S_ECA_FLG;
	}

	/**
	 * @param s_ECA_FLG
	 */
	public void setS_ECA_FLG(int s_ECA_FLG) {
		S_ECA_FLG = s_ECA_FLG;
	}

	/**
	 * ユーザー編集を取得します。
	 * 
	 * @return USR_ID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザー編集を設定します。
	 * 
	 * @param USR_ID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * @return updDateを戻します。
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * @param UPD_DATE UPD_DATEを設定します。
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * linerChargeListの取得.
	 * 
	 * @return linerChargeList
	 */
	public List<PortLinerCharge> getChargeList() {
		return linerChargeList;
	}

	/**
	 * linerChargeListの設定.
	 * 
	 * @param linerChargeList
	 */
	public void setChargeList(List<PortLinerCharge> linerChargeList) {
		this.linerChargeList = linerChargeList;
	}

	/**
	 * T/S港 を取得します。
	 * 
	 * @return TS_PORT_CODE T/S港
	 */
	public String getTS_PORT_CODE() {
		return TS_PORT_CODE;
	}

	/**
	 * T/S港 を設定します。
	 * 
	 * @param TS_PORT_CODE T/S港
	 */
	public void setTS_PORT_CODE(String TS_PORT_CODE) {
		this.TS_PORT_CODE = TS_PORT_CODE;
	}

	/**
	 * T/S港名称 を取得します。
	 * 
	 * @return TS_PORT_NAME T/S港名称
	 */
	public String getTS_PORT_NAME() {
		return TS_PORT_NAME;
	}

	/**
	 * T/S港名称 を設定します。
	 * 
	 * @param TS_PORT_NAME T/S港名称
	 */
	public void setTS_PORT_NAME(String TS_PORT_NAME) {
		this.TS_PORT_NAME = TS_PORT_NAME;
	}

	/**
	 * 標準港代理店コード
	 * 
	 * @return sTD_PORT_AGENT_CODE
	 */
	public String getSTD_PORT_AGENT_CODE() {
		return STD_PORT_AGENT_CODE;
	}

	/**
	 * 標準港代理店コード
	 * 
	 * @param sTD_PORT_AGENT_CODE セットする sTD_PORT_AGENT_CODE
	 */
	public void setSTD_PORT_AGENT_CODE(String sTD_PORT_AGENT_CODE) {
		STD_PORT_AGENT_CODE = sTD_PORT_AGENT_CODE;
	}

	/**
	 * 標準港代理店名称を取得
	 * 
	 * @return 標準港代理店名称
	 */
	public String getSTD_PORT_AGENT_NAME() {
		return STD_PORT_AGENT_NAME;
	}

	/**
	 * 標準港代理店名称をセットする
	 * 
	 * @param sTD_PORT_AGENT_NAME 標準港代理店名称
	 */
	public void setSTD_PORT_AGENT_NAME(String sTD_PORT_AGENT_NAME) {
		STD_PORT_AGENT_NAME = sTD_PORT_AGENT_NAME;
	}

	/**
	 * 標準港通貨コード
	 * 
	 * @return pCG_EST_CUR_CODE
	 */
	public String getPCG_EST_CUR_CODE() {
		return PCG_EST_CUR_CODE;
	}

	/**
	 * 標準港通貨コード
	 * 
	 * @param pCG_EST_CUR_CODE セットする pCG_EST_CUR_CODE
	 */
	public void setPCG_EST_CUR_CODE(String pCG_EST_CUR_CODE) {
		PCG_EST_CUR_CODE = pCG_EST_CUR_CODE;
	}

	/**
	 * 標準港費金額
	 * 
	 * @return pCG_EST_AMT
	 */
	public BigDecimal getPCG_EST_AMT() {
		return PCG_EST_AMT;
	}

	/**
	 * 標準港費金額
	 * 
	 * @param pCG_EST_AMT セットする pCG_EST_AMT
	 */
	public void setPCG_EST_AMT(BigDecimal pCG_EST_AMT) {
		PCG_EST_AMT = pCG_EST_AMT;
	}

}

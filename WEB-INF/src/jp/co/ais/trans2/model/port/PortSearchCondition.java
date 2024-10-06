package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * Portの検索条件
 * 
 * @author AIS
 */
public class PortSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** 会社コード */
	protected String companyCode = null;

	/** Portコード */
	protected String code = null;

	/** Portコード前方一致 */
	protected String codeLike = null;

	/** PortコードFrom */
	protected String codeFrom = null;

	/** PortコードTo */
	protected String codeTo = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** UNLOCODE */
	protected String UNLOCODE = null;

	/** COU_CODE */
	protected String COU_CODE = null;

	/** Port Name */
	protected String nameLike = null;

	/** COU_NAME */
	protected String couName = null;

	/** (S)ECAフラグ */
	protected int S_ECA_FLG = -1;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** コードリスト */
	protected List<String> codeList;

	/** 現在件数 */
	protected int nowCount = 0;

	/** 年(サマータイム用) */
	protected int YEAR = -1;

	/** REGIONコード */
	protected String REGION_CODE = null;

	/** SUMMER_TIME基準日時(LCT) */
	protected Date summerTimeBaseDate = null;

	/** true:存在しているSUMMER_TIMEデータのみ */
	protected boolean onlySummerData = false;

	/** true:内航 */
	protected boolean local = false;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PortSearchCondition clone() {
		try {
			return (PortSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * PortコードFromを取得します。
	 * 
	 * @return PortコードFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * PortコードFromを設定します。
	 * 
	 * @param codeFrom PortコードFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * PortコードToを取得します。
	 * 
	 * @return PortコードTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * PortコードToを設定します。
	 * 
	 * @param codeTo PortコードTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return namesLikeを戻します。
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike namesLikeを設定します。
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * @return 会社コード前方一致を戻します。
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike 会社コード前方一致を設定します。
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * 検索名称likeを取得します。
	 * 
	 * @return 検索名称like
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * 検索名称likeを設定します。
	 * 
	 * @param namekLike 検索名称like
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * 有効期限を取得します。
	 * 
	 * @return validTerm 有効期限を戻します。
	 */
	public Date getValidTerm() {
		return this.validTerm;
	}

	/**
	 * 有効期限を設定します。
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getCodeList()
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * @param codeList
	 */
	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

	/**
	 * @return UNLOCODE
	 */
	public String getUNLOCODE() {
		return UNLOCODE;
	}

	/**
	 * @param UNLOCODE
	 */
	public void setUNLOCODE(String UNLOCODE) {
		this.UNLOCODE = UNLOCODE;
	}

	/**
	 * @return COU_CODE
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
	 * @return Port Name
	 */
	public String getNameLike() {
		return nameLike;
	}

	/**
	 * @param nameLike
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	/**
	 * @return couName
	 */
	public String getCouName() {
		return couName;
	}

	/**
	 * @param couName
	 */
	public void setCouName(String couName) {
		this.couName = couName;
	}

	/**
	 * (S)ECAフラグの取得
	 * 
	 * @return S_ECA_FLG (S)ECAフラグ
	 */
	public int getS_ECA_FLG() {
		return S_ECA_FLG;
	}

	/**
	 * (S)ECAフラグの設定
	 * 
	 * @param S_ECA_FLG (S)ECAフラグ
	 */
	public void setS_ECA_FLG(int S_ECA_FLG) {
		this.S_ECA_FLG = S_ECA_FLG;
	}

	/**
	 * 最終更新日時の取得
	 * 
	 * @return lastUpdateDate 最終更新日時
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * 最終更新日時の設定
	 * 
	 * @param lastUpdateDate 最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * 現在件数の取得
	 * 
	 * @return nowCount 現在件数
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * 現在件数の設定
	 * 
	 * @param nowCount 現在件数
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

	/**
	 * 年(サマータイム用)の取得
	 * 
	 * @return YEAR 年(サマータイム用)
	 */
	public int getYEAR() {
		return YEAR;
	}

	/**
	 * 年(サマータイム用)の設定
	 * 
	 * @param YEAR 年(サマータイム用)
	 */
	public void setYEAR(int YEAR) {
		this.YEAR = YEAR;
	}

	/**
	 * REGIONコードの取得
	 * 
	 * @return REGION_CODE REGIONコード
	 */
	public String getREGION_CODE() {
		return REGION_CODE;
	}

	/**
	 * REGIONコードの設定
	 * 
	 * @param REGION_CODE REGIONコード
	 */
	public void setREGION_CODE(String REGION_CODE) {
		this.REGION_CODE = REGION_CODE;
	}

	/**
	 * SUMMER_TIME基準日時(LCT)の取得
	 * 
	 * @return summerTimeBaseDate SUMMER_TIME基準日時(LCT)
	 */
	public Date getSummerTimeBaseDate() {
		return summerTimeBaseDate;
	}

	/**
	 * SUMMER_TIME基準日時(LCT)の設定
	 * 
	 * @param summerTimeBaseDate SUMMER_TIME基準日時(LCT)
	 */
	public void setSummerTimeBaseDate(Date summerTimeBaseDate) {
		this.summerTimeBaseDate = summerTimeBaseDate;
	}

	/**
	 * true:存在しているSUMMER_TIMEデータのみの取得
	 * 
	 * @return onlySummerData true:存在しているSUMMER_TIMEデータのみ
	 */
	public boolean isOnlySummerData() {
		return onlySummerData;
	}

	/**
	 * true:存在しているSUMMER_TIMEデータのみの設定
	 * 
	 * @param onlySummerData true:存在しているSUMMER_TIMEデータのみ
	 */
	public void setOnlySummerData(boolean onlySummerData) {
		this.onlySummerData = onlySummerData;
	}

	/**
	 * true:内航の取得
	 * 
	 * @return local true:内航
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * true:内航の設定
	 * 
	 * @param local true:内航
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

}

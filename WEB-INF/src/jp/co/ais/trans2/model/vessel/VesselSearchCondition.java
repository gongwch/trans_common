package jp.co.ais.trans2.model.vessel;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * Vesselの検索条件
 * 
 * @author AIS
 */
public class VesselSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** 会社コード */
	protected String companyCode = null;

	/** Vesselコード */
	protected String code = null;

	/** Vesselコード前方一致 */
	protected String codeLike = null;

	/** VesselコードFrom */
	protected String codeFrom = null;

	/** VesselコードTo */
	protected String codeTo = null;

	/** ユーザーコード */
	protected String fleetUsrCode = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** The owner name like. */
	protected String ownerNameLike = null;

	/** The flag if include suspended. */
	protected boolean includeSuspended = false;

	/** The ship type code. */
	protected String shipTypeCode = null;

	/** The ship form code. */
	protected String shipFormCode = null;

	/** The ship owner code. */
	protected String shipOwnerCode = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** 造船契約登録済み対象か */
	protected boolean shipBuild = false;

	/** 燃料管理用 */
	protected boolean forBM = false;

	/** コードリスト */
	protected List<String> codeList;

	/** 詳細情報を含む(スピード、港燃料消費、海上燃料消費) */
	protected boolean includeDetail = false;

	/** RELET船を含まれるか */
	protected boolean includeRelet = true;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** CALL_SIGN */
	protected String callsign = null;

	/** 現在件数 */
	protected int nowCount = 0;

	/** true:油種情報を含む */
	protected boolean includeBunkerType = false;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public VesselSearchCondition clone() {
		try {
			return (VesselSearchCondition) super.clone();

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
	 * VesselコードFromを取得します。
	 * 
	 * @return VesselコードFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * VesselコードFromを設定します。
	 * 
	 * @param codeFrom VesselコードFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * VesselコードToを取得します。
	 * 
	 * @return VesselコードTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * VesselコードToを設定します。
	 * 
	 * @param codeTo VesselコードTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * ユーザーコード
	 * 
	 * @return ユーザーコード
	 */
	public String getFleetUsrCode() {
		return fleetUsrCode;
	}

	/**
	 * ユーザーコード
	 * 
	 * @param vesselUsrCode ユーザーコード
	 */
	public void setFleetUsrCode(String vesselUsrCode) {
		this.fleetUsrCode = vesselUsrCode;
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
		return validTerm;
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
	 * 造船契約登録済みのみ対象にするか
	 * 
	 * @return shipBuild true:造船契約登録済みのみ対象
	 */
	public boolean isShipBuild() {
		return shipBuild;
	}

	/**
	 * 造船契約登録済み対象かを設定する
	 * 
	 * @param shipBuild 造船契約登録済みフラグ true:造船契約登録済みのみ対象
	 */
	public void setShipBuild(boolean shipBuild) {
		this.shipBuild = shipBuild;
	}

	/**
	 * Gets the owner name like.
	 * 
	 * @return the owner name like
	 */
	public String getOwnerNameLike() {
		return ownerNameLike;
	}

	/**
	 * Sets the owner name like.
	 * 
	 * @param ownerNameLike the new owner name like
	 */
	public void setOwnerNameLike(String ownerNameLike) {
		this.ownerNameLike = ownerNameLike;
	}

	/**
	 * Gets the ship type code.
	 * 
	 * @return the type code
	 */
	public String getShipTypeCode() {
		return shipTypeCode;
	}

	/**
	 * Sets the ship type code.
	 * 
	 * @param shipTypeCode the new type code
	 */
	public void setShipTypeCode(String shipTypeCode) {
		this.shipTypeCode = shipTypeCode;
	}

	/**
	 * Gets the ship form code.
	 * 
	 * @return the ship form code
	 */
	public String getShipFormCode() {
		return shipFormCode;
	}

	/**
	 * Sets the ship form code.
	 * 
	 * @param shipFormCode the new size code
	 */
	public void setShipForm(String shipFormCode) {
		this.shipFormCode = shipFormCode;
	}

	/**
	 * Gets the ship owner code.
	 * 
	 * @return the ship owner code
	 */
	public String getShipOwnerCode() {
		return shipOwnerCode;
	}

	/**
	 * Sets the ship owner code.
	 * 
	 * @param shipOwnerCode the new ship owner code
	 */
	public void setShipOwnerCode(String shipOwnerCode) {
		this.shipOwnerCode = shipOwnerCode;
	}

	/**
	 * Checks if is include suspended.
	 * 
	 * @return true, if is include suspended
	 */
	public boolean isIncludeSuspended() {
		return includeSuspended;
	}

	/**
	 * Sets the include suspended.
	 * 
	 * @param includeSuspended the new include suspended
	 */
	public void setIncludeSuspended(boolean includeSuspended) {
		this.includeSuspended = includeSuspended;
	}

	/**
	 * 燃料管理用の取得
	 * 
	 * @return forBM 燃料管理用
	 */
	public boolean isForBM() {
		return forBM;
	}

	/**
	 * 燃料管理用の設定
	 * 
	 * @param forBM 燃料管理用
	 */
	public void setForBM(boolean forBM) {
		this.forBM = forBM;
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
	 * 詳細情報を含む(スピード、港燃料消費、海上燃料消費)の取得
	 * 
	 * @return includeDetail 詳細情報を含む(スピード、港燃料消費、海上燃料消費)
	 */
	public boolean isIncludeDetail() {
		return includeDetail;
	}

	/**
	 * 詳細情報を含む(スピード、港燃料消費、海上燃料消費)の設定
	 * 
	 * @param includeDetail 詳細情報を含む(スピード、港燃料消費、海上燃料消費)
	 */
	public void setIncludeDetail(boolean includeDetail) {
		this.includeDetail = includeDetail;
	}

	/**
	 * RELET船を含まれるかの取得
	 * 
	 * @return includeRelet RELET船を含まれるか
	 */
	public boolean isIncludeRelet() {
		return includeRelet;
	}

	/**
	 * RELET船を含まれるかの設定
	 * 
	 * @param includeRelet RELET船を含まれるか
	 */
	public void setIncludeRelet(boolean includeRelet) {
		this.includeRelet = includeRelet;
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
	 * CALL_SIGNの取得
	 * 
	 * @return callsign CALL_SIGN
	 */
	public String getCallsign() {
		return callsign;
	}

	/**
	 * CALL_SIGNの設定
	 * 
	 * @param callsign CALL_SIGN
	 */
	public void setCallsign(String callsign) {
		this.callsign = callsign;
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
	 * true:油種情報を含むの取得
	 * 
	 * @return includeBunkerType true:油種情報を含む
	 */
	public boolean isIncludeBunkerType() {
		return includeBunkerType;
	}

	/**
	 * true:油種情報を含むの設定
	 * 
	 * @param includeBunkerType true:油種情報を含む
	 */
	public void setIncludeBunkerType(boolean includeBunkerType) {
		this.includeBunkerType = includeBunkerType;
	}

}

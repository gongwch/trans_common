package jp.co.ais.trans2.op.model.item;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * OPアイテム検索条件
 */
public class OPItemSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** 会社コード */
	protected String companyCode = null;

	/** Itemコード */
	protected String code = null;

	/** Itemコード前方一致 */
	protected String codeLike = null;

	/** ItemコードFrom */
	protected String codeFrom = null;

	/** ItemコードTo */
	protected String codeTo = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** 契約タイプ */
	protected OPContractType contractType = null;

	/** アイテム制御区分 */
	protected OPItemControlDivision itemControlDivision = null;

	/** アイテムSUB区分 */
	protected OPItemSubDivision itemSubDivision = null;

	/** アイテムSUBコード（複数） */
	protected List<OPItemSubDivision> itemSubDivisions = null;

	/** コードリスト */
	protected List<String> codeList = null;

	/** BUNKER_TYPE_CODE */
	protected String brkrTypeCode = null;

	/** OWNR_SHIP_CODE */
	protected String ownrShipCode = null;

	/** 貸借区分 */
	protected Dc dc = null;

	/** SA区分 */
	protected OPSaKbn sa = null;

	/** 科目-補助-内訳の名称表示 */
	protected boolean showAccountName = false;

	/** コミッション区分 */
	protected int ADCOM_KBN = -1;

	/** Brokarage区分 */
	protected int BRKR_KBN = -1;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** 主科目の名称のみ表示 */
	protected boolean onlyShowAccountName = false;

	/** 現在件数 */
	protected int nowCount = 0;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public OPItemSearchCondition clone() {
		try {
			return (OPItemSearchCondition) super.clone();

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
	 * ItemコードFromを取得します。
	 * 
	 * @return ItemコードFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * ItemコードFromを設定します。
	 * 
	 * @param codeFrom ItemコードFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * ItemコードToを取得します。
	 * 
	 * @return ItemコードTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * ItemコードToを設定します。
	 * 
	 * @param codeTo SAItemコードTo
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
	 * 契約タイプの取得
	 * 
	 * @return contractType 契約タイプ
	 */
	public OPContractType getContractType() {
		return contractType;
	}

	/**
	 * 契約タイプの設定
	 * 
	 * @param opContractType 契約タイプ
	 */
	public void setContractType(OPContractType opContractType) {
		this.contractType = opContractType;
	}

	/**
	 * アイテム制御区分の取得
	 * 
	 * @return itemControlDivision アイテム制御区分
	 */
	public OPItemControlDivision getItemControlDivision() {
		return itemControlDivision;
	}

	/**
	 * アイテム制御区分の設定
	 * 
	 * @param opItemControlDivision アイテム制御区分
	 */
	public void setItemControlDivision(OPItemControlDivision opItemControlDivision) {
		this.itemControlDivision = opItemControlDivision;
	}

	/**
	 * アイテムSUB区分の取得
	 * 
	 * @return itemSubDivision アイテムSUB区分
	 */
	public OPItemSubDivision getItemSubDivision() {
		return itemSubDivision;
	}

	/**
	 * アイテムSUB区分の設定
	 * 
	 * @param opItemSubDivision アイテムSUB区分
	 */
	public void setItemSubDivision(OPItemSubDivision opItemSubDivision) {
		this.itemSubDivision = opItemSubDivision;
	}

	/**
	 * アイテムSUBコード（複数）の取得
	 * 
	 * @return itemSubDivisions アイテムSUBコード（複数）
	 */
	public List<OPItemSubDivision> getItemSubDivisions() {
		return itemSubDivisions;
	}

	/**
	 * アイテムSUBコード（複数）の設定
	 * 
	 * @param itemSubDivisions アイテムSUBコード（複数）
	 */
	public void setItemSubDivisions(List<OPItemSubDivision> itemSubDivisions) {
		this.itemSubDivisions = itemSubDivisions;
	}

	/**
	 * 貸借区分の取得
	 * 
	 * @return dc 貸借区分
	 */
	public Dc getDc() {
		return dc;
	}

	/**
	 * 貸借区分の設定
	 * 
	 * @param dc 貸借区分
	 */
	public void setDc(Dc dc) {
		this.dc = dc;
	}

	/**
	 * SA区分の取得
	 * 
	 * @return sa SA区分
	 */
	public OPSaKbn getSa() {
		return sa;
	}

	/**
	 * SA区分の設定
	 * 
	 * @param sa SA区分
	 */
	public void setSa(OPSaKbn sa) {
		this.sa = sa;
	}

	/**
	 * 科目-補助-内訳の名称表示の取得
	 * 
	 * @return showAccountName 科目-補助-内訳の名称表示
	 */
	public boolean isShowAccountName() {
		return showAccountName;
	}

	/**
	 * 科目-補助-内訳の名称表示の設定
	 * 
	 * @param showAccountName 科目-補助-内訳の名称表示
	 */
	public void setShowAccountName(boolean showAccountName) {
		this.showAccountName = showAccountName;
	}

	/**
	 * @return brkrTypeCodeを戻します。
	 */
	public String getBrkrTypeCode() {
		return brkrTypeCode;
	}

	/**
	 * @param brkrTypeCode を設定します。
	 */
	public void setBrkrTypeCode(String brkrTypeCode) {
		this.brkrTypeCode = brkrTypeCode;
	}

	/**
	 * コミッション区分の取得
	 * 
	 * @return ADCOM_KBN コミッション区分
	 */
	public int getADCOM_KBN() {
		return ADCOM_KBN;
	}

	/**
	 * コミッション区分の設定
	 * 
	 * @param ADCOM_KBN コミッション区分
	 */
	public void setADCOM_KBN(int ADCOM_KBN) {
		this.ADCOM_KBN = ADCOM_KBN;
	}

	/**
	 * Brokerage区分の取得
	 * 
	 * @return BRKR_KBN Brokerage区分
	 */
	public int getBRKR_KBN() {
		return BRKR_KBN;
	}

	/**
	 * Brokerage区分の設定
	 * 
	 * @param BRKR_KBN Brokerage区分
	 */
	public void setBRKR_KBN(int BRKR_KBN) {
		this.BRKR_KBN = BRKR_KBN;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getValidTerm()
	 */
	public Date getValidTerm() {
		return null;
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
	 * 主科目の名称のみ表示の取得
	 * 
	 * @return onlyShowAccountName 主科目の名称のみ表示
	 */
	public boolean isOnlyShowAccountName() {
		return onlyShowAccountName;
	}

	/**
	 * 主科目の名称のみ表示の設定
	 * 
	 * @param onlyShowAccountName 主科目の名称のみ表示
	 */
	public void setOnlyShowAccountName(boolean onlyShowAccountName) {
		this.onlyShowAccountName = onlyShowAccountName;
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
	 * OWNR_SHIP_CODE の取得
	 * 
	 * @return ownrShipCode OWNR_SHIP_CODE
	 */
	public String getOwnrShipCode() {
		return ownrShipCode;
	}

	/**
	 * OWNR_SHIP_CODE の設定
	 * 
	 * @param ownrShipCode OWNR_SHIP_CODE
	 */
	public void setOwnrShipCode(String ownrShipCode) {
		this.ownrShipCode = ownrShipCode;
	}

	/**
	 * コードリストの取得
	 * 
	 * @return codeList コードリスト
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * コードリストの設定
	 * 
	 * @param codeList コードリスト
	 */
	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

}

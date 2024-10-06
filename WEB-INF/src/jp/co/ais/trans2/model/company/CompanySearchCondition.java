package jp.co.ais.trans2.model.company;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 会社の検索条件
 * 
 * @author AIS
 */
public class CompanySearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = -4640605841564467077L;

	/** 会社コード開始 */
	protected String codeFrom = null;

	/** 会社コード終了 */
	protected String codeTo = null;

	/** 会社コード */
	protected String code = null;

	/** 会社コード前方一致 */
	protected String codeLike = null;

	/** 略称like */
	protected String namesLike = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** グループ会計区分 */
	protected int groupAccountDivision = -1;

	/** 基軸通貨 一致 */
	protected String keyCurrencyCode;

	/** 会社コード(複数指定用) */
	protected Set<String> codeList = new LinkedHashSet<String>();

	/** 当該決算段階以外の会社 */
	protected int settlementStageOtherThan = -1;

	/** 機能通貨 */
	protected String fncCurrencyCode;

	/** 指定会社コード以外の場合 */
	protected String excludeCompanyCode;

	/** 基軸通貨コードリスト */
	protected List<String> keyCurrencyCodeList = null;

	/** 追加検索条件SQL */
	protected String addonWhereSql = null;

	/** SPC情報非表示 */
	protected boolean notShowSpc = false;

	/** INVOICE使用(会社基礎情報英語版)を表示するか */
	protected boolean showInvoice = false;

	/** 階層 */
	protected int level = -1;

	/** 上位会社コード */
	protected String superiorCompanyCode = null;

	/** 会社組織コード */
	protected String organizationCode = null;

	/** 配下組織を含むか */
	protected boolean includeUnder = false;

	/** 適格請求書発行事業者登録番号 */
	protected String invRegNo = null;

	/** AR：英分請求書SIGNER表示可否 */
	protected boolean isShowARSignerEng = false;

	/** インボイス制度フラグ */
	protected boolean isInvoiceFlg = false;

	/**
	 * 指定会社コード以外を取得する
	 * 
	 * @return String
	 */
	public String getExcludeCompanyCode() {
		return excludeCompanyCode;
	}

	/**
	 * 指定会社コード以外の設定する
	 * 
	 * @param excludeKaiCode
	 */
	public void setExcludeCompanyCode(String excludeKaiCode) {
		this.excludeCompanyCode = excludeKaiCode;
	}

	/**
	 * 機能通貨を取得する
	 * 
	 * @return String
	 */
	public String getFncCurrencyCode() {
		return fncCurrencyCode;
	}

	/**
	 * 機能通貨の設定する
	 * 
	 * @param fncCurrencyCode
	 */
	public void setFncCurrencyCode(String fncCurrencyCode) {
		this.fncCurrencyCode = fncCurrencyCode;
	}

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CompanySearchCondition clone() {
		try {
			return (CompanySearchCondition) super.clone();

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
	 * @return codeFromを戻します。
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFromを設定します。
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeToを戻します。
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeToを設定します。
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return validTermを戻します。
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm validTermを設定します。
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
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
	 * グループ会社のみ
	 */
	public void setGroupAccountOnly() {
		this.groupAccountDivision = 1; // 使用する

	}

	/**
	 * グループ会計区分
	 * 
	 * @return グループ会計区分
	 */
	public int getGroupAccountDivision() {
		return groupAccountDivision;
	}

	/**
	 * グループ会計区分
	 * 
	 * @param groupAccountDivision グループ会計区分
	 */
	public void setGroupAccountDivision(int groupAccountDivision) {
		this.groupAccountDivision = groupAccountDivision;
	}

	/**
	 * 基軸通貨(一致)
	 * 
	 * @return 基軸通貨(一致)
	 */
	public String getKeyCurrencyCode() {
		return keyCurrencyCode;
	}

	/**
	 * 基軸通貨(一致)
	 * 
	 * @param keyCurrencyCode 基軸通貨(一致)
	 */
	public void setKeyCurrencyCode(String keyCurrencyCode) {
		this.keyCurrencyCode = keyCurrencyCode;
	}

	/**
	 * 会社コード(複数指定用)
	 * 
	 * @return 会社コード(複数指定用)
	 */
	public Set<String> getCodeList() {
		return codeList;
	}

	/**
	 * 会社コード(複数指定用)
	 * 
	 * @param codeList 会社コード(複数指定用)
	 */
	public void setCodeList(Set<String> codeList) {
		this.codeList = codeList;
	}

	/**
	 * 会社コード
	 * 
	 * @param code_ 会社コード
	 */
	public void addCode(String code_) {
		this.codeList.add(code_);
	}

	/**
	 * 当該決算段階以外の会社
	 * 
	 * @return 当該決算段階以外の会社
	 */
	public int getSettlementStageOtherThan() {
		return settlementStageOtherThan;
	}

	/**
	 * 当該決算段階以外の会社
	 * 
	 * @param settlementStageOtherThan
	 */
	public void setSettlementStageOtherThan(int settlementStageOtherThan) {
		this.settlementStageOtherThan = settlementStageOtherThan;
	}

	/**
	 * 基軸通貨コードリストの取得
	 * 
	 * @return keyCurrencyCodeList 基軸通貨コードリスト
	 */
	public List<String> getKeyCurrencyCodeList() {
		return keyCurrencyCodeList;
	}

	/**
	 * 基軸通貨コードリストの設定
	 * 
	 * @param keyCurrencyCodeList 基軸通貨コードリスト
	 */
	public void setKeyCurrencyCodeList(List<String> keyCurrencyCodeList) {
		this.keyCurrencyCodeList = keyCurrencyCodeList;
	}

	/**
	 * 追加検索条件SQLの取得
	 * 
	 * @return addonWhereSql 追加検索条件SQL
	 */
	public String getAddonWhereSql() {
		return addonWhereSql;
	}

	/**
	 * 追加検索条件SQLの設定
	 * 
	 * @param addonWhereSql 追加検索条件SQL
	 */
	public void setAddonWhereSql(String addonWhereSql) {
		this.addonWhereSql = addonWhereSql;
	}

	/**
	 * SPC情報非表示の取得
	 * 
	 * @return notShowSpc SPC情報非表示
	 */
	public boolean isNotShowSpc() {
		return notShowSpc;
	}

	/**
	 * SPC情報非表示の設定
	 * 
	 * @param notShowSpc SPC情報非表示
	 */
	public void setNotShowSpc(boolean notShowSpc) {
		this.notShowSpc = notShowSpc;
	}

	/**
	 * INVOICE使用(会社基礎情報英語版)表示するか
	 * 
	 * @return showInvoice INVOICE使用(会社基礎情報英語版)表示するか
	 */
	public boolean isShowInvoice() {
		return showInvoice;
	}

	/**
	 * INVOICE使用(会社基礎情報英語版)表示するかの設定
	 * 
	 * @param showInvoice INVOICE使用(会社基礎情報英語版)表示するか
	 */
	public void setShowInvoice(boolean showInvoice) {
		this.showInvoice = showInvoice;
	}

	/**
	 * 階層の取得
	 * 
	 * @return level 階層
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 階層の設定
	 * 
	 * @param level 階層
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * 上位会社コードの取得
	 * 
	 * @return superiorCompanyCode 上位会社コード
	 */
	public String getSuperiorCompanyCode() {
		return superiorCompanyCode;
	}

	/**
	 * 上位会社コードの設定
	 * 
	 * @param superiorCompanyCode 上位会社コード
	 */
	public void setSuperiorCompanyCode(String superiorCompanyCode) {
		this.superiorCompanyCode = superiorCompanyCode;
	}

	/**
	 * 会社組織コードの取得
	 * 
	 * @return organizationCode 会社組織コード
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * 会社組織コードの設定
	 * 
	 * @param organizationCode 会社組織コード
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * @return 配下組織を含むか
	 */
	public boolean isIncludeUnder() {
		return includeUnder;
	}

	/**
	 * 配下組織を含むか
	 * 
	 * @param includeUnder
	 */
	public void setIncludeUnder(boolean includeUnder) {
		this.includeUnder = includeUnder;
	}

	/**
	 * @return invRegNoを戻します。
	 */
	public String getInvRegNo() {
		return invRegNo;
	}

	/**
	 * @param invRegNo invRegNoを設定します。
	 */
	public void setInvRegNo(String invRegNo) {
		this.invRegNo = invRegNo;
	}

	/**
	 * @return isShowARSignerEng AR：英分請求書SIGNER表示可否
	 */
	public boolean isShowARSignerEng() {
		return isShowARSignerEng;
	}

	/**
	 * AR：英分請求書SIGNER表示可否
	 * 
	 * @param isShowARSignerEng
	 */
	public void setShowARSignerEng(boolean isShowARSignerEng) {
		this.isShowARSignerEng = isShowARSignerEng;
	}

	/**
	 * インボイス制度フラグの取得
	 * 
	 * @return isInvoiceFlg インボイス制度フラグ
	 */
	public boolean isInvoiceFlg() {
		return isInvoiceFlg;
	}

	/**
	 * インボイス制度フラグの設定
	 * 
	 * @param isInvoiceFlg インボイス制度フラグ
	 */
	public void setInvoiceFlg(boolean isInvoiceFlg) {
		this.isInvoiceFlg = isInvoiceFlg;
	}

}

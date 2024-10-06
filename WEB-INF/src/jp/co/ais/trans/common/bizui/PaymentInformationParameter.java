package jp.co.ais.trans.common.bizui;

/**
 * 支払先条件に紐付く情報
 */
public class PaymentInformationParameter {
	/** 支払方法コード */
	private String sihaHouCode = "";

	/** 支払方法名称 */
	private String sihaHouName = "";

	/** 振出銀行コード */
	private String huriCode = "";

	/** 振出銀行名称 */
	private String huriName = "";

	/** 支払区分 */
	private String sihaKbn = "";

	/** 支払日 */
	private String sihaDate = "";
	
	/** 支払内部コード */
	private String sihaNaiCode = "";
	
	/** 判定フラグ  */
	private boolean flag = true;

	/**
	 * 支払方法コードを設定する。<BR>
	 * 
	 * @param sihaHouCode 支払方法コード
	 * 
	 */
	public void setSihaHouCode(String sihaHouCode) {
		this.sihaHouCode = sihaHouCode;
	}

	/**
	 * 支払方法コードを返す。<BR>
	 * 
	 * @return sihaHouCode 支払方法コード
	 * 
	 */
	public String getSihaHouCode() {
		return sihaHouCode;
	}

	/**
	 * 支払方法名称を設定する。<BR>
	 * 
	 * @param sihaHouName 支払方法名称
	 * 
	 */
	public void setSihaHouName(String sihaHouName) {
		this.sihaHouName = sihaHouName;
	}

	/**
	 * 支払方法名称を返す。<BR>
	 * 
	 * @return sihaHouName 支払方法名称
	 * 
	 */
	public String getSihaHouName() {
		return sihaHouName;
	}

	/**
	 * 振出銀行コードを設定する。<BR>
	 * 
	 * @param huriCode 振出銀行コード
	 * 
	 */
	public void setHuriCode(String huriCode) {
		this.huriCode = huriCode;
	}

	/**
	 * 振出銀行コードを返す。<BR>
	 * 
	 * @return huriCode 振出銀行コード
	 * 
	 */
	public String getHuriCode() {
		return huriCode;
	}

	/**
	 * 振出銀行名称を設定する。<BR>
	 * 
	 * @param huriName 振出銀行名称
	 * 
	 */
	public void setHuriName(String huriName) {
		this.huriName = huriName;
	}

	/**
	 * 振出銀行名称を返す。<BR>
	 * 
	 * @return huriName 振出銀行名称
	 * 
	 */
	public String getHuriName() {
		return huriName;
	}

	/**
	 * 支払区分を設定する。<BR>
	 * 
	 * @param sihaKbn 支払区分
	 * 
	 */
	public void setSihaKbn(String sihaKbn) {
		this.sihaKbn = sihaKbn;
	}

	/**
	 * 支払区分を返す。<BR>
	 * 
	 * @return sihaKbn 支払区分
	 * 
	 */
	public String getSihaKbn() {
		return sihaKbn;
	}

	/**
	 * 支払日を設定する。<BR>
	 * 
	 * @param sihaDate 支払日
	 * 
	 */
	public void setSihaDate(String sihaDate) {
		this.sihaDate = sihaDate;
	}

	/**
	 * 支払日を返す。<BR>
	 * 
	 * @return sihaDate 支払日
	 * 
	 */
	public String getSihaDate() {
		return sihaDate;
	}
	
	/**
	 * 支払内部コードを設定する。<BR>
	 * 
	 * @param sihaNaiCode 支払内部コード
	 * 
	 */
	public void setSihaNaiCode(String sihaNaiCode) {
		this.sihaNaiCode = sihaNaiCode;
	}

	/**
	 * 設定があったかどうかを返す。<BR>
	 * 
	 * @return sihaNaiCode 支払内部コード
	 * 
	 */
	public String getSihaNaiCode() {
		return sihaNaiCode;
	}
	
	
	/**
	 * 判定フラグを設定する。<BR>
	 * 
	 * @param flag 判定フラグ true:データ有り false:データなし
	 * 
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * 判定フラグを返す。<BR>
	 * 
	 * @return flag 判定フラグ true:データ有り false:データなし
	 * 
	 */
	public boolean getFlag() {
		return flag;
	}

	/**
	 * 値を初期値に戻す
	 * 
	 */
	public void clear() {
		sihaHouCode = "";
		sihaHouName = "";
		huriCode = "";
		huriName = "";
		sihaKbn = "";
		sihaDate = "";
		sihaNaiCode = "";
	}
}

package jp.co.ais.trans2.model.code;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * コードマスタ検索条件
 */
public class CodeSearchCondition extends TransferBase implements OPLoginCondition {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** コード区分 */
	protected String CODE_DIV = null;

	/** コード */
	protected String CODE = null;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** 現在件数 */
	protected int nowCount = 0;

	/** true:内航モード、false:外航モード */
	protected Boolean local = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return KAI_CODE 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param KAI_CODE 会社コード
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		setKAI_CODE(companyCode);
	}

	/**
	 * コード区分の取得
	 * 
	 * @return CODE_DIV コード区分
	 */
	public String getCODE_DIV() {
		return CODE_DIV;
	}

	/**
	 * コード区分の設定
	 * 
	 * @param CODE_DIV コード区分
	 */
	public void setCODE_DIV(String CODE_DIV) {
		this.CODE_DIV = CODE_DIV;
	}

	/**
	 * コードの取得
	 * 
	 * @return CODE コード
	 */
	public String getCODE() {
		return CODE;
	}

	/**
	 * コードの設定
	 * 
	 * @param CODE コード
	 */
	public void setCODE(String CODE) {
		this.CODE = CODE;
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
	 * true:内航モード、false:外航モードの取得
	 * 
	 * @return local true:内航モード、false:外航モード
	 */
	public Boolean getLocal() {
		return local;
	}

	/**
	 * true:内航モード、false:外航モードの取得
	 * 
	 * @return local true:内航モード、false:外航モード
	 */
	public Boolean isLocal() {
		return local;
	}

	/**
	 * true:内航モード、false:外航モードの設定
	 * 
	 * @param local true:内航モード、false:外航モード
	 */
	public void setLocal(Boolean local) {
		this.local = local;
	}

}

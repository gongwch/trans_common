package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.*;

/**
 * ユーザ認証管理マスタ
 */
public class UserAuthUpdate extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** ロックアウト到達回数 */
	protected int rockcnt;

	/** ロックアウト開放時間 */
	protected String rockcnttime = null;

	/** 最低パスワード長 */
	protected int minpwd;

	/** パスワード有効期間 */
	protected String pwdterm = null;

	/** 複雑レベル */
	protected int diffilev;

	/** 履歴保持数 */
	protected int histcnt;

	/** 登録日付 */
	protected Date insdate = null;

	/** 更新日付 */
	protected Date upddate = null;

	/** プログラムID */
	protected String prgId = null;

	/** ユーザID */
	protected String userId = null;

	/** パスワード期限切れ通知日数 */
	protected String pwdexp = null;

	/**
	 * @return ロックアウト到達回数を戻します。
	 */
	public int getRockcnt() {
		return rockcnt;
	}

	/**
	 * @param ロックアウト到達回数を設定します。
	 */
	public void setRockcnt(int rockcnt) {
		this.rockcnt = rockcnt;
	}

	/**
	 * @return 会社コードを戻します。
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param 会社コードを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return ロックアウト開放時間を戻します。
	 */
	public String getRockcnttime() {
		return rockcnttime;
	}

	/**
	 * @param ロックアウト開放時間を設定します。
	 */
	public void setRockcnttime(String rockcnttime) {
		this.rockcnttime = rockcnttime;
	}

	/**
	 * @return 最低パスワード長を戻します。
	 */
	public int getMinpwd() {
		return minpwd;
	}

	/**
	 * @param 最低パスワード長を設定します。
	 */
	public void setMinpwd(int minpwd) {
		this.minpwd = minpwd;
	}

	/**
	 * @return パスワード有効期間を戻します。
	 */
	public String getPwdterm() {
		return pwdterm;
	}

	/**
	 * @param パスワード有効期間を設定します。
	 */
	public void setPwdterm(String pwdterm) {
		this.pwdterm = pwdterm;
	}

	/**
	 * @return 複雑レベルを戻します。
	 */
	public int getDiffilev() {
		return diffilev;
	}

	/**
	 * @param 複雑レベルを設定します。
	 */
	public void setDiffilev(int diffilev) {
		this.diffilev = diffilev;
	}

	/**
	 * @return 履歴保持数を戻します。
	 */
	public int getHistcnt() {
		return histcnt;
	}

	/**
	 * @param 履歴保持数を設定します。
	 */
	public void setHistcnt(int histcnt) {
		this.histcnt = histcnt;
	}

	/**
	 * 登録日付を戻します
	 * 
	 * @return true:存在
	 */
	public Date getInsdate() {
		return insdate;
	}

	/**
	 * 登録日付を設定します
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setInsdate(Date insdate) {
		this.insdate = insdate;
	}

	/**
	 * 更新日付を戻します
	 * 
	 * @return true:存在
	 */
	public Date getUpddate() {
		return upddate;
	}

	/**
	 * 更新日付を設定します
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}

	/**
	 * プログラムIDを戻します
	 * 
	 * @return true:存在
	 */
	public String getPrgid() {
		return prgId;
	}

	/**
	 * プログラムIDを設定します
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setPrgid(String prgId) {
		this.prgId = prgId;
	}

	/**
	 * ユーザIDを戻します
	 * 
	 * @return true:存在
	 */
	public String getUserid() {
		return userId;
	}

	/**
	 * ユーザIDを設定します
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setUserid(String userId) {
		this.userId = userId;
	}

	/**
	 * パスワード期限切れ通知日数を戻します
	 * 
	 * @return true:存在
	 */
	public String getPwdexp() {
		return pwdexp;
	}

	/**
	 * パスワード期限切れ通知日数を設定します
	 * 
	 * @param hasSubItem true:存在
	 */
	public void setPwdexp(String pwdexp) {
		this.pwdexp = pwdexp;
	}

}

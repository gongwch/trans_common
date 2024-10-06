package jp.co.ais.trans2.model.aprvrole;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.user.*;

/**
 * 承認権限ロールマスタ
 */
public class AprvRole extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 承認権限ロールコード */
	protected String APRV_ROLE_CODE = null;

	/** 承認権限ロール名称 */
	protected String APRV_ROLE_NAME = null;

	/** 承認権限ロール略称 */
	protected String APRV_ROLE_NAME_S = null;

	/** 承認権限ロール検索名称 */
	protected String APRV_ROLE_NAME_K = null;

	/** ユーザーコード */
	protected String USR_CODE = null;

	/** ユーザー略称 */
	protected String USR_NAME_S = null;

	/** 部門略称 */
	protected String DEP_NAME_S = null;

	/** 開始年月日 */
	protected Date STR_DATE = null;

	/** 終了年月日 */
	protected Date END_DATE = null;

	/** 登録日付 */
	protected Date INP_DATE = null;

	/** 更新日付 */
	protected Date UPD_DATE = null;

	/** プログラムＩＤ */
	protected String PRG_ID = null;

	/** ユーザーＩＤ */
	protected String USR_ID = null;

	/** 権限保持ユーザーリスト */
	protected List<User> userList;

	/**
	 * 会社コードの取得
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 承認権限ロールコードの取得
	 * 
	 * @return APRV_ROLE_CODE 承認権限ロールコード
	 */
	public String getAPRV_ROLE_CODE() {
		return APRV_ROLE_CODE;
	}

	/**
	 * 承認権限ロールコードの設定
	 * 
	 * @param APRV_ROLE_CODE 承認権限ロールコード
	 */
	public void setAPRV_ROLE_CODE(String APRV_ROLE_CODE) {
		this.APRV_ROLE_CODE = APRV_ROLE_CODE;
	}

	/**
	 * 承認権限ロール名称の取得
	 * 
	 * @return APRV_ROLE_NAME 承認権限ロール名称
	 */
	public String getAPRV_ROLE_NAME() {
		return APRV_ROLE_NAME;
	}

	/**
	 * 承認権限ロール名称の設定
	 * 
	 * @param APRV_ROLE_NAME 承認権限ロール名称
	 */
	public void setAPRV_ROLE_NAME(String APRV_ROLE_NAME) {
		this.APRV_ROLE_NAME = APRV_ROLE_NAME;
	}

	/**
	 * 承認権限ロール略称の取得
	 * 
	 * @return APRV_ROLE_NAME_S 承認権限ロール略称
	 */
	public String getAPRV_ROLE_NAME_S() {
		return APRV_ROLE_NAME_S;
	}

	/**
	 * 承認権限ロール略称の設定
	 * 
	 * @param APRV_ROLE_NAME_S 承認権限ロール略称
	 */
	public void setAPRV_ROLE_NAME_S(String APRV_ROLE_NAME_S) {
		this.APRV_ROLE_NAME_S = APRV_ROLE_NAME_S;
	}

	/**
	 * 承認権限ロール検索名称の取得
	 * 
	 * @return APRV_ROLE_NAME_K 承認権限ロール検索名称
	 */
	public String getAPRV_ROLE_NAME_K() {
		return APRV_ROLE_NAME_K;
	}

	/**
	 * 承認権限ロール検索名称の設定
	 * 
	 * @param APRV_ROLE_NAME_K 承認権限ロール検索名称
	 */
	public void setAPRV_ROLE_NAME_K(String APRV_ROLE_NAME_K) {
		this.APRV_ROLE_NAME_K = APRV_ROLE_NAME_K;
	}

	/**
	 * ユーザーコードの取得
	 * 
	 * @return USR_CODE ユーザーコード
	 */
	public String getUSR_CODE() {
		return USR_CODE;
	}

	/**
	 * ユーザーコードの設定
	 * 
	 * @param USR_CODE ユーザーコード
	 */
	public void setUSR_CODE(String USR_CODE) {
		this.USR_CODE = USR_CODE;
	}

	/**
	 * ユーザー略称の取得
	 * 
	 * @return USR_NAME_S ユーザー略称
	 */
	public String getUSR_NAME_S() {
		return USR_NAME_S;
	}

	/**
	 * ユーザー略称の設定
	 * 
	 * @param USR_NAME_S ユーザー略称
	 */
	public void setUSR_NAME_S(String USR_NAME_S) {
		this.USR_NAME_S = USR_NAME_S;
	}

	/**
	 * 部門略称の取得
	 * 
	 * @return DEP_NAME_S 部門略称
	 */
	public String getDEP_NAME_S() {
		return DEP_NAME_S;
	}

	/**
	 * 部門略称の設定
	 * 
	 * @param DEP_NAME_S 部門略称
	 */
	public void setDEP_NAME_S(String DEP_NAME_S) {
		this.DEP_NAME_S = DEP_NAME_S;
	}

	/**
	 * 開始年月日の取得
	 * 
	 * @return STR_DATE 開始年月日
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * 開始年月日の設定
	 * 
	 * @param STR_DATE 開始年月日
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * 終了年月日の取得
	 * 
	 * @return END_DATE 終了年月日
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * 終了年月日の設定
	 * 
	 * @param END_DATE 終了年月日
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * 登録日付の取得
	 * 
	 * @return INP_DATE 登録日付
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録日付の設定
	 * 
	 * @param INP_DATE 登録日付
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * 更新日付の取得
	 * 
	 * @return UPD_DATE 更新日付
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * 更新日付の設定
	 * 
	 * @param UPD_DATE 更新日付
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * プログラムＩＤの取得
	 * 
	 * @return PRG_ID プログラムＩＤ
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * プログラムＩＤの設定
	 * 
	 * @param PRG_ID プログラムＩＤ
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ユーザーＩＤの取得
	 * 
	 * @return USR_ID ユーザーＩＤ
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザーＩＤの設定
	 * 
	 * @param USR_ID ユーザーＩＤ
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * 権限保持ユーザーリストを取得
	 * 
	 * @return userList
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * 権限保持ユーザーリストをセットする
	 * 
	 * @param userList userList
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * 権限保持ユーザーリストに追加
	 * 
	 * @param usr
	 */
	public void addUser(User usr) {
		if (this.userList == null) {
			userList = new ArrayList();
		}
		this.userList.add(usr);
	}

}

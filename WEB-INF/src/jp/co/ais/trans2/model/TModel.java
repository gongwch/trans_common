package jp.co.ais.trans2.model;

import java.util.*;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.fw.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ビジネスロジック上位クラス
 */
public class TModel {

	/** 現在時刻 */
	private Date now;

	/** 会社情報 */
	private Company company;

	/** ユーザ情報 */
	private User user;

	/** プログラムコード */
	private String programCode;

	/** サーバー側アプリ識別子名称 */
	private String serverInstanceName = null;

	/** 画面識別子 */
	private String realUID = null;

	/** ログ用追加情報 */
	private String realInfo = null;

	/**
	 * 会社情報
	 * 
	 * @param company 会社情報
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * ユーザ情報
	 * 
	 * @param user ユーザ情報
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * プログラムコードセット
	 * 
	 * @param code コード
	 */
	public void setProgramCode(String code) {
		this.programCode = code;
	}

	/**
	 * 会社情報
	 * 
	 * @return 会社情報
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * ユーザ情報
	 * 
	 * @return ユーザ情報
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		if (company == null) {
			return null;
		}
		return company.getCode();
	}

	/**
	 * ユーザコード
	 * 
	 * @return ユーザコード
	 */
	public String getUserCode() {
		if (user == null) {
			return null;
		}
		return user.getCode();
	}

	/**
	 * プログラムコード
	 * 
	 * @return プログラムコード
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * 現在時刻
	 * 
	 * @return now 現在時刻
	 */
	public Date getNow() {
		return now;
	}

	/**
	 * 現在日(時刻は00:00)
	 * 
	 * @return now 現在日
	 */
	public Date getNowYMD() {
		return DateUtil.toYMDDate(now);
	}

	/**
	 * 現在時刻
	 * 
	 * @param now 現在時刻
	 */
	public void setNow(Date now) {
		this.now = now;
	}

	/**
	 * コンポーネント取得.<br>
	 * TModelの継承クラスの場合は、ログイン情報を引き継ぐ
	 * 
	 * @param clazz キー
	 * @return コンポーネント
	 */
	public <T> T get(Class<T> clazz) {
		Object ret = TContainerFactory.getContainer().getComponent(clazz);

		if (ret instanceof TModel) {
			TModel model = (TModel) ret;
			model.setCompany(company);
			model.setUser(user);
			model.setProgramCode(programCode);
			model.setNow(now);

			model.setRealUID(realUID);
			model.setRealInfo(realInfo);
			model.setServerInstanceName(serverInstanceName);
		}

		return (T) ret;
	}

	/**
	 * コンポーネント取得.<br>
	 * TModelの継承クラスの場合は、ログイン情報を引き継ぐ
	 * 
	 * @param key キー
	 * @return コンポーネント
	 */
	public Object getComponent(Object key) {
		Object ret = TContainerFactory.getContainer().getComponent(key);

		if (ret instanceof TModel) {
			TModel model = (TModel) ret;
			model.setCompany(company);
			model.setUser(user);
			model.setProgramCode(programCode);
			model.setNow(now);

			model.setRealUID(realUID);
			model.setRealInfo(realInfo);
			model.setServerInstanceName(serverInstanceName);
		}

		return ret;
	}

	/**
	 * IDからメッセージ取得
	 * 
	 * @param messageID メッセージID
	 * @param bindIds 単語ID、または、単語のリスト
	 * @return メッセージ
	 */
	protected String getMessage(String messageID, Object... bindIds) {
		if (user != null) {
			String lang = user.getLanguage();
			return MessageUtil.convertMessage(lang, messageID, bindIds);
		}

		return messageID;
	}

	/**
	 * IDから単語取得
	 * 
	 * @param wordID 単語ID
	 * @return 単語
	 */
	protected String getWord(String wordID) {
		if (user != null) {
			String lang = user.getLanguage();
			return MessageUtil.getWord(lang, wordID);
		}

		return wordID;
	}

	/**
	 * メッセージ(略称)取得
	 * 
	 * @param messageId
	 * @return メッセージ(略称)
	 */
	public String getShortWord(String messageId) {
		String lang = user.getLanguage();
		return MessageUtil.getShortWord(lang, messageId);

	}

	/**
	 * サーバー側アプリ識別子名称の取得
	 * 
	 * @return serverInstanceName サーバー側アプリ識別子名称
	 */
	public String getServerInstanceName() {
		return serverInstanceName;
	}

	/**
	 * サーバー側アプリ識別子名称の設定
	 * 
	 * @param serverInstanceName サーバー側アプリ識別子名称
	 */
	public void setServerInstanceName(String serverInstanceName) {
		this.serverInstanceName = serverInstanceName;
	}

	/**
	 * 画面識別子の取得
	 * 
	 * @return realUID 画面識別子
	 */
	public String getRealUID() {
		return realUID;
	}

	/**
	 * 画面識別子の設定
	 * 
	 * @param realUID 画面識別子
	 */
	public void setRealUID(String realUID) {
		this.realUID = realUID;
	}

	/**
	 * ログ用追加情報の取得
	 * 
	 * @return realInfo ログ用追加情報
	 */
	public String getRealInfo() {
		return realInfo;
	}

	/**
	 * ログ用追加情報の設定
	 * 
	 * @param realInfo ログ用追加情報
	 */
	public void setRealInfo(String realInfo) {
		this.realInfo = realInfo;
	}
}

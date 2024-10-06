package jp.co.ais.trans.common.info;

import java.awt.im.*;
import java.io.*;
import java.lang.Character.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * サーバ利用者情報.
 */
public final class TUserInfo implements Serializable {

	/** シリアルUID */
	private static final long serialVersionUID = 6559556905634237059L;

	/** 日本語サブセット */
	private static final Subset[] SUBSET_JP = new Subset[] { InputSubset.KANJI };

	/** 韓国語サブセット */
	private static final Subset[] SUBSET_KO = new Subset[] { InputSubset.HANJA };

	/** 中国語サブセット */
	private static final Subset[] SUBSET_ZH = new Subset[] { InputSubset.SIMPLIFIED_HANZI };

	/** login user Code */
	private String usrCode = "";

	/** ログイン利用者氏名 */
	private String usrName = "";

	/** 所属社員コード */
	private String empCode = "";

	/** 所属社員略称コード */
	private String empNameS = "";

	/** 所属部門コード */
	private String depCode = "";

	/** 所属部門名略称 */
	private String depNameS = "";

	/** 処理権限レベル */
	private int prcKen = 9;

	/** 更新権限レベル */
	private int updKen = 9;

	/** 利用者使用言語 */
	private String usrLang = "";

	/** 経理担当者区分 */
	private boolean isAccountChargePerson;

	/** ロケール */
	private Locale userLocale = Locale.getDefault();

	/** 全角キャラクターセット(IME制御用) */
	private transient Subset[] charSubsets = null;

	/** その他データ(カスタマイズ用) */
	private Map<String, String> otherData = new HashMap<String, String>(0);

	/** 会社情報 */
	private TCompanyInfo compInfo = new TCompanyInfo();

	/**
	 * 会社コード取得setter
	 * 
	 * @param kaiCode 会社コード
	 */
	public void setCompanyCode(String kaiCode) {
		this.compInfo.setCompanyCode(kaiCode);
	}

	/**
	 * 会社コード取得
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return this.compInfo.getCompanyCode();
	}

	/**
	 * ユーザーコード取得setter
	 * 
	 * @param usrCode ユーザーコード
	 */
	public void setUserCode(String usrCode) {
		this.usrCode = usrCode;
	}

	/**
	 * ユーザーコード取得
	 * 
	 * @return ユーザーコード
	 */
	public String getUserCode() {
		return this.usrCode;
	}

	/**
	 * ユーザー名称取得setter
	 * 
	 * @param usrName ユーザー名称
	 */
	public void setUserName(String usrName) {
		this.usrName = usrName;
	}

	/**
	 * ユーザー名称取得
	 * 
	 * @return usrName ユーザー名称
	 */
	public String getUserName() {
		return this.usrName;
	}

	/**
	 * 所属部門コードsetter
	 * 
	 * @param depCode 所属部門コード
	 */
	public void setDepartmentCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * 所属部門コード
	 * 
	 * @return 所属部門コード
	 */
	public String getDepartmentCode() {
		return this.depCode;
	}

	/**
	 * 処理権限レベル
	 * 
	 * @param prcKen 処理権限レベル
	 */
	public void setProcessLevel(int prcKen) {
		this.prcKen = prcKen;
	}

	/**
	 * 処理権限レベル
	 * 
	 * @return 処理権限レベル
	 */
	public int getProcessLevel() {
		return this.prcKen;
	}

	/**
	 * 更新権限レベル
	 * 
	 * @param updKen 更新権限レベル
	 */
	public void setUpdateLevel(int updKen) {
		this.updKen = updKen;
	}

	/**
	 * 更新権限レベル
	 * 
	 * @return 更新権限レベル
	 */
	public int getUpdateLevel() {
		return this.updKen;
	}

	/**
	 * 社員コードsetter
	 * 
	 * @param empCode 社員コード
	 */
	public void setEmployerCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * 社員コード
	 * 
	 * @return 社員コード
	 */
	public String getEmployerCode() {
		return this.empCode;
	}

	/**
	 * 社員略称setter
	 * 
	 * @param empNameS 社員略称
	 */
	public void setEmployerShortName(String empNameS) {
		this.empNameS = empNameS;
	}

	/**
	 * 社員略称
	 * 
	 * @return 社員略称
	 */
	public String getEmployerShortName() {
		return this.empNameS;
	}

	/**
	 * 所属部門略称setter
	 * 
	 * @param depNameS 所属部門略称
	 */
	public void setDepartmentShortName(String depNameS) {
		this.depNameS = depNameS;
	}

	/**
	 * 所属部門名略称
	 * 
	 * @return depName
	 */
	public String getDepartmentShortName() {
		return this.depNameS;
	}

	/**
	 * 経理区分setter
	 * 
	 * @param isAccountChargePerson 経理区分
	 */
	public void setAccountChargePerson(boolean isAccountChargePerson) {
		this.isAccountChargePerson = isAccountChargePerson;
	}

	/**
	 * 経理区分getter
	 * 
	 * @return true:経理担当者 false:その他
	 */
	public boolean isAccountChargePerson() {
		return this.isAccountChargePerson;
	}

	/**
	 * 利用者使用言語コードsetter
	 * 
	 * @param usrLang 利用者使用言語コード
	 */
	public void setUserLanguage(String usrLang) {
		this.usrLang = usrLang;

		// キャラクターセット(IME制御)の設定
		// ロケール設定
		if ("ja".equals(usrLang)) {
			this.charSubsets = SUBSET_JP;
			this.userLocale = Locale.JAPANESE;

		} else if ("zh".equals(usrLang)) {
			this.charSubsets = SUBSET_ZH;
			this.userLocale = Locale.SIMPLIFIED_CHINESE;

		} else if ("ko".equals(usrLang)) {
			this.charSubsets = SUBSET_KO;
			this.userLocale = Locale.KOREAN;

		} else {
			this.charSubsets = null;
			this.userLocale = Locale.ENGLISH;
		}
	}

	/**
	 * 利用者使用言語コード
	 * 
	 * @return 言語コード
	 */
	public String getUserLanguage() {
		return this.usrLang;
	}

	/**
	 * 利用者使用言語コードに紐づくロケールを返す
	 * 
	 * @return ロケール
	 */
	public Locale getUserLocale() {
		return this.userLocale;
	}

	/**
	 * 利用者使用言語に紐づくキャラクターセットを返す
	 * 
	 * @return キャラクターセット
	 */
	public Subset[] getCharacterSubsets() {
		return this.charSubsets;
	}

	/**
	 * ユーザに紐づく会社情報を設定する
	 * 
	 * @param compInfo 会社情報
	 */
	public void setCompanyInfo(TCompanyInfo compInfo) {
		this.compInfo = compInfo;
	}

	/**
	 * ユーザに紐づく会社情報を取得する
	 * 
	 * @return 会社情報
	 */
	public TCompanyInfo getCompanyInfo() {
		return this.compInfo;
	}

	/**
	 * その他データの追加(カスタマイズ用)
	 * 
	 * @param key キー
	 * @param value 値
	 */
	public void addData(String key, String value) {
		this.otherData.put(key, value);
	}

	/**
	 * 文字列変換
	 * 
	 * @return 値の文字列表現
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder("[");
		buff.append(Util.safeNull(usrCode)).append("/");
		buff.append(Util.safeNull(usrName)).append("/");
		buff.append(Util.safeNull(empCode)).append("/");
		buff.append(Util.safeNull(depCode)).append("/");
		buff.append(Util.safeNull(depNameS)).append("/");
		buff.append(Util.safeNull(prcKen)).append("/");
		buff.append(Util.safeNull(updKen)).append("/");
		buff.append(Util.safeNull(usrLang));
		buff.append("]");

		return buff.toString();
	}

	/**
	 * データをStringのMap形式に変換
	 * 
	 * @return Map
	 */
	public Map<String, String> toStringMap() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("usrCode", this.usrCode); // ユーザーコード
		map.put("usrName", this.usrName); // ユーザー名
		map.put("empCode", this.empCode); // 社員コード
		map.put("empNameS", this.empNameS); // 社員略称
		map.put("prcKen", String.valueOf(this.prcKen)); // 処理権限レベル
		map.put("updKen", String.valueOf(this.updKen)); // 更新権限レベル
		map.put("lngCode", this.usrLang); // 言語コード
		map.put("depCode", this.depCode); // 所属部門コード
		map.put("depNameS", this.depNameS); // 所属部門名略称
		map.put("isAccountChargePerson", BooleanUtil.toString(this.isAccountChargePerson)); // 経理担当者区分

		// その他データ
		map.putAll(otherData);

		return map;
	}

	/**
	 * データを反映する.
	 * 
	 * @param map データ
	 */
	public void reflect(Map<String, String> map) {
		this.otherData = map;

		this.usrCode = map.get("usrCode"); // ユーザーコード
		this.usrName = (map.get("usrName")); // ユーザー名
		this.empCode = (map.get("empCode")); // 社員コード

		// 処理権限レベル
		try {
			this.prcKen = Util.isNullOrEmpty(map.get("prcKen")) ? 9 : Integer.parseInt(map.get("prcKen"));
		} catch (NumberFormatException e) {
			ServerErrorHandler.handledException(e);
		}

		// 更新権限レベル
		try {
			this.updKen = Util.isNullOrEmpty(map.get("updKen")) ? 9 : Integer.parseInt(map.get("updKen"));
		} catch (NumberFormatException e) {
			ServerErrorHandler.handledException(e);
		}

		setUserLanguage(map.get("lngCode")); // 言語コード
		this.depCode = map.get("depCode"); // 所属部門コード
		this.depNameS = map.get("depNameS"); // 所属部門名略称
		this.isAccountChargePerson = BooleanUtil.toBoolean(map.get("isAccountChargePerson")); // 経理担当者区分
		this.empNameS = map.get("empNameS"); // 社員略称
	}

	/**
	 * 指定キーに対する会社情報を返す.(カスタマイズ用)
	 * 
	 * @param key キー
	 * @return 会社情報
	 */
	public String getData(String key) {
		return this.otherData.get(key);
	}
}

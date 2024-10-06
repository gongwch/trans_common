package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * 貸借
 * 
 * @author AIS
 */
public enum Dc {

	/** 借方 */
	DEBIT(0),

	/** 貸方 */
	CREDIT(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private Dc(int value) {
		this.value = value;
	}

	/**
	 * 貸借を返す
	 * 
	 * @param dc 値
	 * @return 貸借
	 */
	public static Dc getDc(int dc) {
		for (Dc em : values()) {
			if (em.value == dc) {
				return em;
			}
		}

		return null;
	}
	
	/**
	 * 借方であるか
	 * 
	 * @return 借方:true 貸方:false
	 */
	public boolean isDebit() {
		return DEBIT == this;
	}
	
	/**
	 * 貸方であるか
	 * 
	 * @return 借方:false 貸方:true
	 */
	public boolean isCredit() {
		return CREDIT == this;
	}

	/**
	 * テキスト
	 * 
	 * @return 貸借テキスト
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * テキスト
	 * 
	 * @param dc タイプ
	 * @return 貸借テキスト
	 */
	public static String getName(Dc dc) {

		switch (dc) {
			case DEBIT:
				return "C00080";// 借方

			default:
				return "C00068";// 貸方
		}
	}

	/**
	 * 貸借区分のEnum名称取得<br>
	 * コード：名称、コード：名称形式
	 * 
	 * @param lang 言語コード
	 * @return 貸借区分のEnum名称
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (Dc dc : Dc.values()) {
			sb.append(dc.value);
			sb.append("：");
			sb.append(MessageUtil.getWord(lang, dc.getName()));
			sb.append("、");
		}

		return sb.substring(0, sb.length() - 1);

	}
}

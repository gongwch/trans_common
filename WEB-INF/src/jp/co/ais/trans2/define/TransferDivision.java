package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * tÖæª
 * 
 * @author AIS
 */
public enum TransferDivision {

	/** Êí */
	NORMAL(0),

	/** tÖ */
	TRANSFER(1);

	/** l */
	public int value;

	/**
	 * RXgN^.
	 * 
	 * @param value l
	 */
	private TransferDivision(int value) {
		this.value = value;
	}

	/**
	 * tÖæªðÔ·
	 * 
	 * @param type tÖæª
	 * @return tÖæª
	 */
	public static TransferDivision get(int type) {
		for (TransferDivision em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}

	/**
	 * tÖæª¼ðÔ·
	 * 
	 * @return tÖæª¼
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * tÖæª¼ðÔ·
	 * 
	 * @param type tÖæª
	 * @return tÖæª¼
	 */
	public static String getName(TransferDivision type) {

		switch (type) {
			case NORMAL: // Êí
				return "C00372";

			case TRANSFER: // tÖ
				return "C00375";

			default:
				return null;
		}
	}

	/**
	 * tÖæªÌEnum¼Ìæ¾<br>
	 * R[hF¼ÌAR[hF¼Ì`®
	 * 
	 * @param lang ¾êR[h
	 * @return tÖæªÌEnum¼Ì
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (TransferDivision type : TransferDivision.values()) {
			sb.append(type.value);
			sb.append("F");
			sb.append(MessageUtil.getWord(lang, type.getName()));
			sb.append("A");
		}

		return sb.substring(0, sb.length() - 1);

	}

}

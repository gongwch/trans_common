package jp.co.ais.trans2.define;

/**
 * 敬称リスト
 * 
 * @author AIS
 */
public enum HonorType {

	/** 設定無 */
	NONE(0),

	/** 様 */
	MR_JP(1),

	/** 御中 */
	YOUR_JP(2),

	/** 宛 */
	DEAR_JP(3),

	/** 殿 */
	PER_JP(4),

	/** 宛先 */
	AD1_JP(5),

	/** 宛先： */
	AD2_JP(6),

	/** 先生 */
	TCH_JP(7),

	/** Mr. */
	MR_EN(8),

	/** Ms. */
	MS_EN(9),

	/** Mrs. */
	MRS_EN(10),

	/** Messrs. */
	MERS_EN(11),

	/** To. */
	TO_EN(12);

	/** 値 */
	public int value;

	/**
	 * @param value
	 */
	private HonorType(int value) {
		this.value = value;
	}

	/**
	 * 敬称リスト を返す
	 * 
	 * @param honorType 敬称リスト
	 * @return 敬称リスト
	 */
	public static HonorType getHonorType(int honorType) {

		for (HonorType em : values()) {
			if (em.value == honorType) {
				return em;
			}
		}

		return null;
	}

	/**
	 * 敬称リスト を返す
	 * 
	 * @param honorType 敬称リスト
	 * @return 敬称リスト
	 */
	public static String getName(HonorType honorType) {

		if (honorType == null) {
			return null;
		}

		switch (honorType) {
			case MR_JP:
				// 様
				return "C04241";

			case YOUR_JP:
				// 御中
				return "C02541";

			case DEAR_JP:
				// 宛
				return "C12176";

			case PER_JP:
				// 殿
				return "C12177";

			case AD1_JP:
				// 宛先
				return "C12178";

			case AD2_JP:
				// 宛先：
				return "C12179";

			case TCH_JP:
				// 先生
				return "C12180";

			case MR_EN:
				// Mr.
				return "C12181";

			case MS_EN:
				// Ms.
				return "C12182";

			case MRS_EN:
				// Mrs.
				return "C12183";

			case MERS_EN:
				// Messrs.
				return "CTC0102";

			case TO_EN:
				// To.
				return "C12189";

			default:
				return null;

		}
	}

	/**
	 * 敬称リストの前付、後付 を返す
	 * 
	 * @param honorType 敬称リスト
	 * @return true:前 false:後
	 */
	public static boolean isLeadHonor(HonorType honorType) {

		if (honorType == null) {
			return false;
		}

		switch (honorType) {
			case MR_JP:
				// 様
			case YOUR_JP:
				// 御中
			case DEAR_JP:
				// 宛
			case PER_JP:
				// 殿
			case TCH_JP:
				// 先生
				return false;

			case AD1_JP:
				// 宛先
			case AD2_JP:
				// 宛先：
			case MR_EN:
				// Mr.
			case MS_EN:
				// Ms.
			case MRS_EN:
				// Mrs.
			case MERS_EN:
				// Messrs.
			case TO_EN:
				// To.
				return true;

			default:
				return false;

		}
	}

}

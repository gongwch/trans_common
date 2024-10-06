package jp.co.ais.trans2.define;

/**
 * ƒf[ƒ^‹æ•ª
 */
public enum DataDivision {

	/** 11:“ü‹à“`•[ */
	INPUT(0),

	/** 12:o‹à“`•[ */
	OUTPUT(1),

	/** 13:U‘Ö“`•[ */
	TRANSFER(2),

	/** 14:Œ©Ï“`•[ */
	ESTIMATE(3),

	/** 15:Œ©Ïíœ“`•[ */
	ESTIMATE_DEL(4),

	/** 16:IFRS’ •ë’²®“`•[ */
	LEDGER_IFRS(5),

	/** 17:©‘’ •ë’²®“`•[ */
	LEDGER_SELF(6),

	/** 21:Ğˆõ‰¼•¥ */
	EMP_KARI(7),

	/** 22:Œo”ï¸Z */
	EXPENCE(8),

	/** 23:¿‹‘ */
	ACCOUNT(9),

	/** 24:x•¥“`•[(Ğˆõ) */
	PAYMENT_EMP(10),

	/** 27:ŠO‘‘—‹à’²® */
	ABROAD(11),

	/** 28:x•¥“`•[ */
	PAYMENT_COM(12),

	/** 31:ÂŒ Œvã */
	CREDIT_SLIP(13),

	/** 32:ÂŒ Á */
	CREDIT_DEL(14);

	/** ’l */
	public int value;

	/**
	 * ƒRƒ“ƒXƒgƒ‰ƒNƒ^[
	 * 
	 * @param value
	 */
	private DataDivision(int value) {
		this.value = value;
	}

	/**
	 * ƒf[ƒ^‹æ•ª–¼Ì‚ğ•Ô‚·
	 * 
	 * @return ƒf[ƒ^‹æ•ª–¼Ì
	 */
	public String getName() {
		return getDataDivisionName(this);
	}

	/**
	 * ƒf[ƒ^‹æ•ª–¼Ì‚ğ•Ô‚·
	 * 
	 * @param dataKbn
	 * @return ƒf[ƒ^‹æ•ª–¼Ì
	 */
	public static String getDataDivisionName(DataDivision dataKbn) {

		if (dataKbn == null) {
			return null;
		}

		switch (dataKbn) {
			case INPUT:
				return "C00419";// “ü‹à“`•[

			case OUTPUT:
				return "C00264";// o‹à“`•[

			case TRANSFER:
				return "C00469";// U‘Ö“`•[

			case ESTIMATE:
				return "C02079";// Œ©Ï“`•[

			case ESTIMATE_DEL:
				return "C02080";// Œ©ÏÁ‹“`•[

			case LEDGER_IFRS:
				return "C11237";// IFRS’ •ë’²®“`•[

			case LEDGER_SELF:
				return "C11238";// ©‘’ •ë’²®“`•[

			case EMP_KARI:
				return "C02081";// Ğˆõ‰¼•¥

			case EXPENCE:
				return "C02082";// Œo”ï¸Z

			case ACCOUNT:
				return "C00314";// ¿‹‘

			case PAYMENT_EMP:
				return "C02083";// x•¥“`•[(Ğˆõ)

			case ABROAD:
				return "C02086";// ŠO‘‘—‹à’²®

			case PAYMENT_COM:
				return "C02546";// x•¥“`•[

			case CREDIT_SLIP:
				return "C01978";// ÂŒ Œvã

			case CREDIT_DEL:
				return "C01979";// ÂŒ Á

			default:
				return null;

		}
	}

	/**
	 * ƒf[ƒ^‹æ•ª‚Ìenum‚ğ•Ô‚·
	 * 
	 * @param dataDiv
	 * @return ƒf[ƒ^‹æ•ª–¼Ì
	 */
	public static DataDivision getDataDivision(String dataDiv) {

		if (dataDiv == null) {
			return null;
		}

		if ("11".equals(dataDiv)) {
			return INPUT;
		} else if ("12".equals(dataDiv)) {
			return OUTPUT;
		} else if ("13".equals(dataDiv)) {
			return TRANSFER;
		} else if ("14".equals(dataDiv)) {
			return ESTIMATE;
		} else if ("15".equals(dataDiv)) {
			return ESTIMATE_DEL;
		} else if ("16".equals(dataDiv)) {
			return LEDGER_IFRS;
		} else if ("17".equals(dataDiv)) {
			return LEDGER_SELF;
		} else if ("21".equals(dataDiv)) {
			return EMP_KARI;
		} else if ("22".equals(dataDiv)) {
			return EXPENCE;
		} else if ("23".equals(dataDiv)) {
			return ACCOUNT;
		} else if ("24".equals(dataDiv)) {
			return PAYMENT_EMP;
		} else if ("27".equals(dataDiv)) {
			return ABROAD;
		} else if ("28".equals(dataDiv)) {
			return PAYMENT_COM;
		} else if ("31".equals(dataDiv)) {
			return CREDIT_SLIP;
		} else if ("32".equals(dataDiv)) {
			return CREDIT_DEL;
		}
		return null;
	}

	/**
	 * ƒf[ƒ^‹æ•ª–¼Ì‚ğ•Ô‚·
	 * 
	 * @return ƒf[ƒ^‹æ•ª–¼Ì
	 */
	public String getCode() {
		return getDataDivisionCode(this);
	}

	/**
	 * ƒf[ƒ^‹æ•ª–¼Ì‚ğ•Ô‚·
	 * 
	 * @param dataKbn
	 * @return ƒf[ƒ^‹æ•ª–¼Ì
	 */
	public static String getDataDivisionCode(DataDivision dataKbn) {

		if (dataKbn == null) {
			return null;
		}

		if (dataKbn == INPUT) {
			return "11";
		} else if (dataKbn == OUTPUT) {
			return "12";
		} else if (dataKbn == TRANSFER) {
			return "13";
		} else if (dataKbn == ESTIMATE) {
			return "14";
		} else if (dataKbn == ESTIMATE_DEL) {
			return "15";
		} else if (dataKbn == LEDGER_IFRS) {
			return "16";
		} else if (dataKbn == LEDGER_SELF) {
			return "17";
		} else if (dataKbn == EMP_KARI) {
			return "21";
		} else if (dataKbn == EXPENCE) {
			return "22";
		} else if (dataKbn == ACCOUNT) {
			return "23";
		} else if (dataKbn == PAYMENT_EMP) {
			return "24";
		} else if (dataKbn == ABROAD) {
			return "27";
		} else if (dataKbn == PAYMENT_COM) {
			return "28";
		} else if (dataKbn == CREDIT_SLIP) {
			return "31";
		} else if (dataKbn == CREDIT_DEL) {
			return "32";
		}
		return null;
	}

}

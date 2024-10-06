package jp.co.ais.trans2.define;


/**
 * 受入単位
 */
public enum AcceptUnit {

	/** 伝票種別単位にエラーチェック */
	SLIPTYPE(0),
	
	/** 伝票番号単位にエラーチェック */
	SLIPNO(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private AcceptUnit(int value) {
		this.value = value;
	}

	/**
	 * 受入単位名称を返す
	 * 
	 * @param acceptUnit
	 * @return 受入単位名称
	 */
	public static String getAcceptUnitName(AcceptUnit acceptUnit) {

		if (acceptUnit == null) {
			return null;
		}

		switch (acceptUnit) {
			case SLIPTYPE:
				// 伝票種別単位にエラーチェック
				return "C02101";

			case SLIPNO:
				// 伝票番号単位にエラーチェック
				return "C02102";

			default:
				return null;

		}
	}

	/**
	 * 受入単位のenumを返す
	 * 
	 * @param acceptUnit
	 * @return データ区分名称
	 */
	public static AcceptUnit getAcceptUnit(int acceptUnit) {

		if(acceptUnit == 0){

			return SLIPTYPE;
		}else if(acceptUnit == 1){

			return SLIPNO;
		}

		return null;

	}

	/**
	 * 受入単位のenumを返す
	 * 
	 * @param acceptUnit
	 * @return データ区分名称
	 */
	public static int getAcceptUnitCode(AcceptUnit acceptUnit) {

		if(acceptUnit == SLIPTYPE){

			return 0;
		}else

			return 1;
		}

	}


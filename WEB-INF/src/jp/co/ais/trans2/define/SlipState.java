package jp.co.ais.trans2.define;

/**
 * 伝票の更新区分
 * 
 * @author AIS
 */
public enum SlipState  {
	
	/** 登録 */
	ENTRY(1),

	/** 現場承認 */
	FIELD_APPROVE(2),
	
	/** 経理承認 */
	APPROVE(3),
	
	/** 更新 */
	UPDATE(4),
	
	/** 現場否認 */
	FIELD_DENY(11),
	
	/** 経理否認 */
	DENY(12);
	
	
	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private SlipState(int value) {
		this.value = value;
	}

	/**
	 * 更新区分を取得する
	 * 
	 * @return 更新区分
	 */
	public int getSlipState() {
		return value;
	}
	

	/**
	 * 更新区分名称を返す
	 * 
	 * @return 更新区分名称
	 */
	public String getName() {
		return getSlipStateName(this);
	}
	
	/**
	 * 更新区分を返す
	 * 
	 * @param slipState 伝票の状態
	 * @return 更新区分名
	 */
	public static SlipState getSlipState(int slipState) {
		for (SlipState em : values()) {
			if (em.value == slipState) {
				return em;
			}
		}
		
		return null;
	}

	/**
	 * 更新区分名称を返す
	 * 
	 * @param slipState 伝票の状態
	 * @return 更新区分名称
	 */
	public static String getSlipStateName(SlipState slipState) {

		if (slipState == null) {
			return null;
		}

		switch (slipState) {
			case ENTRY:
				return "C01258";// 登録

			case FIELD_APPROVE:
				return "C00157";// 現場承認
				
			case APPROVE:
				return "C01616";// 経理承認
				
			case UPDATE:
				return "C00169";// 更新
				
			case FIELD_DENY:
				return "C01617";// 現場否認

			case DENY:
				return "C01615";// 経理否認
		
			default:
				return null;
		}
	}

	/**
	 * 仕訳インターフェース区分名称を返す(伝票種別マスタで使用)
	 * 
	 * @param slipState 伝票の状態
	 * @return 更新区分名称
	 */
	public static SlipState getJnlIfDivName(int slipState) {

		if(slipState == 0){
			return ENTRY;
		}else{
			return APPROVE;
		}
	}

	/**
	 * 仕訳インターフェース区分コードを返す(伝票種別マスタで使用)
	 * 
	 * @param slipState 伝票の状態
	 * @return 更新区分名称
	 */
	public static int getJnlIfDivCode(SlipState slipState) {

		if(slipState == ENTRY){
			return 0;
		}else{
			return 1;
		}
	}

	/**
	 * 更新区分名称を返す
	 * 
	 * @param slipState 伝票の状態
	 * @return 更新区分名称
	 */
	public static String getSlipStateName(int slipState) {
		return getSlipStateName(getSlipState(slipState));
	}
	
}

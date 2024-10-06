package jp.co.ais.trans2.define;

/**
 * 部門区分
 * @author AIS
 *
 */
public enum DepartmentSumType {
	/** 入力 */
	INPUT(0),

	/** 集計 */
	SUM(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private DepartmentSumType(int value) {
		this.value = value;
	}

	/**
	 * 区分を返す
	 * 
	 * @param value 値
	 * @return 区分
	 */
	public static DepartmentSumType get(int value) {
		for (DepartmentSumType em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		return null;
	}

	 /**
	  * 区分名称を返す
	  * 
	  * @param sumType 区分
	  * @return 区分名称
	  */
	 public static String getName(DepartmentSumType sumType) {

		 switch (sumType) {
			 case INPUT:
				 return "C01275";//入力
			 case SUM:
				 return "C00255";//集計
			 default:
				 return null;
		 }
	 }
}

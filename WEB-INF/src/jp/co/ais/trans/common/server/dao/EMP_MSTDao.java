package jp.co.ais.trans.common.server.dao;

/**
 * 社員マスタDao(旧バージョン).<br>
 * KTの方を利用すること。
 */
public interface EMP_MSTDao {

	/** Entity */
	public static final Class BEAN = EMP_MST.class;

	/** getEMP_MST ARGS */
	public static final String getEMP_MST_ARGS = "kai_code, emp_code";

	/**
	 * 社員情報
	 * 
	 * @param kai_code 会社コード
	 * @param emp_code 社員コード
	 * @return 社員情報
	 */
	public EMP_MST getEMP_MST(String kai_code, String emp_code);
}

package jp.co.ais.trans2.model.slip;

/**
 * 仕訳明細パターンDao
 */
public interface SWK_PTN_DTLDao extends SWK_DTLDao {

	/** Entity */
	public Class BEAN = SWK_PTN_DTL.class;

	/**
	 * 仕訳パターン
	 */
	public class SWK_PTN_DTL extends SWK_DTL {

		/** テーブル名 */
		public static final String TABLE = "SWK_PTN_DTL";

		@Override
		public String toString() {
			return super.toString();
		}
	}
}
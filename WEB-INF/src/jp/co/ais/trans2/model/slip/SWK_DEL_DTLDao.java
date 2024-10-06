package jp.co.ais.trans2.model.slip;

/**
 * 削除仕訳明細Dao
 */
public interface SWK_DEL_DTLDao extends SWK_DTLDao {

	/** Entity */
	public Class BEAN = SWK_DEL_DTL.class;

	/**
	 * 削除仕訳明細
	 */
	public class SWK_DEL_DTL extends SWK_DTL {

		/** テーブル名 */
		public static final String TABLE = "SWK_DEL_DTL";

		@Override
		public String toString() {
			return super.toString();
		}
	}
}
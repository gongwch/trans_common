package jp.co.ais.trans2.model.slip;

/**
 * �폜�d�󖾍�Dao
 */
public interface SWK_DEL_DTLDao extends SWK_DTLDao {

	/** Entity */
	public Class BEAN = SWK_DEL_DTL.class;

	/**
	 * �폜�d�󖾍�
	 */
	public class SWK_DEL_DTL extends SWK_DTL {

		/** �e�[�u���� */
		public static final String TABLE = "SWK_DEL_DTL";

		@Override
		public String toString() {
			return super.toString();
		}
	}
}
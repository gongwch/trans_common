package jp.co.ais.trans2.model.slip;

/**
 * �d�󖾍׃p�^�[��Dao
 */
public interface SWK_PTN_DTLDao extends SWK_DTLDao {

	/** Entity */
	public Class BEAN = SWK_PTN_DTL.class;

	/**
	 * �d��p�^�[��
	 */
	public class SWK_PTN_DTL extends SWK_DTL {

		/** �e�[�u���� */
		public static final String TABLE = "SWK_PTN_DTL";

		@Override
		public String toString() {
			return super.toString();
		}
	}
}
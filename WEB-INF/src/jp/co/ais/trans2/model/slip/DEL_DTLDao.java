package jp.co.ais.trans2.model.slip;

/**
 * íœ“`•[ƒf[ƒ^Dao
 */
public interface DEL_DTLDao {

	/** Entity */
	public Class BEAN = DEL_DTL.class;

	/**
	 * “o˜^‚·‚é
	 * 
	 * @param dto
	 */
	public void insert(DEL_DTL dto);

	/**
	 * XV‚·‚é
	 * 
	 * @param dto
	 */
	public void update(DEL_DTL dto);

	/**
	 * íœ‚·‚é
	 * 
	 * @param dto
	 */
	public void delete(DEL_DTL dto);
}

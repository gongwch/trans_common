package jp.co.ais.trans2.model.slip;

/**
 * �폜�`�[�f�[�^Dao
 */
public interface DEL_DTLDao {

	/** Entity */
	public Class BEAN = DEL_DTL.class;

	/**
	 * �o�^����
	 * 
	 * @param dto
	 */
	public void insert(DEL_DTL dto);

	/**
	 * �X�V����
	 * 
	 * @param dto
	 */
	public void update(DEL_DTL dto);

	/**
	 * �폜����
	 * 
	 * @param dto
	 */
	public void delete(DEL_DTL dto);
}

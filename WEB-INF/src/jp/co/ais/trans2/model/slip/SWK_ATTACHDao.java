package jp.co.ais.trans2.model.slip;

/**
 * “`•[“Y•tDao<br>
 * SQL”p~
 */
@Deprecated
public interface SWK_ATTACHDao {

	/** BEAN */
	public Class BEAN = SWK_ATTACH.class;

	/**
	 * V‹Kì¬
	 * 
	 * @param entity
	 * @return Œ”
	 */
	public int insert(SWK_ATTACH entity);

	/** ˆø” */
	public String delete_ARGS = "kAI_CODE, sWK_DEN_NO, sEQ";

	/**
	 * ŒŸõ
	 * 
	 * @param kAI_CODE ‰ïĞƒR[ƒh
	 * @param sWK_DEN_NO “`•[”Ô†
	 * @param sEQ s”Ô†
	 * @return Œ”
	 */
	public int delete(String kAI_CODE, String sWK_DEN_NO, Integer sEQ);

	/** ˆø” */
	public String updateSeq_ARGS = "kAI_CODE, sWK_DEN_NO";

	/**
	 * SEQXV
	 * 
	 * @param kAI_CODE ‰ïĞƒR[ƒh
	 * @param sWK_DEN_NO “`•[”Ô†
	 * @return Œ”
	 */
	public int updateSeq(String kAI_CODE, String sWK_DEN_NO);
}

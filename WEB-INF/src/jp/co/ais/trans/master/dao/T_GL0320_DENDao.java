package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface T_GL0320_DENDao {

	/**  */
	public Class BEAN = T_GL0320_DEN.class;

	/**
	 * @return List
	 */
	public List getAllT_GL0320_DEN();

	/**
	 * @param dto
	 */
	public void insert(T_GL0320_DEN dto);

	/**
	 * @param dto
	 */
	public void update(T_GL0320_DEN dto);

	/**
	 * @param dto
	 */
	public void delete(T_GL0320_DEN dto);
}

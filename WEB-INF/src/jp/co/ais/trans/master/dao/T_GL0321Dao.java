package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface T_GL0321Dao {

	/**  */
	public Class BEAN = T_GL0321.class;

	/**
	 * @return List
	 */
	public List getAllT_GL0321();

	/**
	 * @param dto
	 */
	public void insert(T_GL0321 dto);

	/**
	 * @param dto
	 */
	public void update(T_GL0321 dto);

	/**
	 * @param dto
	 */
	public void delete(T_GL0321 dto);
}

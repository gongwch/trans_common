package jp.co.ais.trans2.common.db;

import java.sql.ResultSet;

/**
 * �}�b�s���O��`
 * 
 * @author AIS
 */
public interface ORMapper {

	/**
	 * @param rs
	 * @return Object
	 * @throws Exception
	 */
	public Object mapping(ResultSet rs) throws Exception;

}

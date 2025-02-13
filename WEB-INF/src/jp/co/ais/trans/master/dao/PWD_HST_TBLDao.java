package jp.co.ais.trans.master.dao;

import java.io.*;
import java.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ðÇ}X^DAO
 * 
 * @author roh
 */
public interface PWD_HST_TBLDao extends Serializable {

	/** ðÇ}X^Bean */
	public Class BEAN = PWD_HST_TBL.class;

	/**
	 * o^
	 * 
	 * @param dto
	 */
	public void insert(PWD_HST_TBL dto);

	/**
	 * XV
	 * 
	 * @param dto
	 */
	public void update(PWD_HST_TBL dto);

	/**
	 * í
	 * 
	 * @param dto
	 */
	public void delete(PWD_HST_TBL dto);

	/** findPassword SQL */
	public String findPassword_QUERY = "KAI_CODE = ? AND USR_CODE = ? ORDER BY INP_DATE";

	/**
	 * ðîñðK¾
	 * 
	 * @param kaiCode
	 * @param usrCode
	 * @return ðè·Æ
	 */
	public List<PWD_HST_TBL> findPassword(String kaiCode, String usrCode);

	/** OC`FbNíSQL */
	public String deleteByPK_SQL = "DELETE FROM PWD_HST_TBL WHERE KAI_CODE = ? AND USR_CODE = ?";

	/**
	 * OC`FbNí
	 * 
	 * @param kaiCode ïÐR[h
	 * @param usrCode [UR[h
	 */
	public void deleteByPK(String kaiCode, String usrCode);

}

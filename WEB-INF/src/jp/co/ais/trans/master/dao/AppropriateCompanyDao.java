package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �v���Ѓt�B�[���hDao
 * 
 * @author zhangbo
 */
public interface AppropriateCompanyDao {

	/** Bean��` */
	public static Class BEAN = AppropriateCompany.class;

	/**
	 * �w�肳�ꂽ�f�[�^�̎擾
	 */
	public String conditionSearch_ARGS = "param";

	/**
	 * @param param �v���Ѓt�B�[���h�G���e�B�e�B
	 * @return List �v���Ѓt�B�[���h�G���e�B�e�B
	 */
	public List<AppropriateCompany> conditionSearch(AppropriateCompany param);

}

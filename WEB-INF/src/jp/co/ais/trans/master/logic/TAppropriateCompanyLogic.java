package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �v���Ѓt�B�[���h���W�b�N
 * 
 * @author zhangbo
 */
public interface TAppropriateCompanyLogic {

	/**
	 * ����
	 * 
	 * @param param �v���Ѓt�B�[���h�G���e�B�e�B
	 * @return List �v���Ѓt�B�[���h�G���e�B�e�B
	 */
	public List<AppropriateCompany> conditionSearch(AppropriateCompany param);

}

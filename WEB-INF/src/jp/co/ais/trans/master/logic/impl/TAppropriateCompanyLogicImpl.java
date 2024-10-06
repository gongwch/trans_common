package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �v���Ѓt�B�[���hImpl
 * 
 * @author zhangbo
 */
public class TAppropriateCompanyLogicImpl implements TAppropriateCompanyLogic {

	/** �v���Ѓt�B�[���hDao */
	private AppropriateCompanyDao dao;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao AppropriateCompanyDao
	 */
	public TAppropriateCompanyLogicImpl(AppropriateCompanyDao dao) {
		// �v���Ѓt�B�[���hDao��Ԃ�
		this.dao = dao;
	}

	/**
	 * ����
	 * 
	 * @param param �v���Ѓt�B�[���h�G���e�B�e�B
	 * @return List �v���Ѓt�B�[���h�G���e�B�e�B
	 */
	public List<AppropriateCompany> conditionSearch(AppropriateCompany param) {

		return dao.conditionSearch(param);
	}
}

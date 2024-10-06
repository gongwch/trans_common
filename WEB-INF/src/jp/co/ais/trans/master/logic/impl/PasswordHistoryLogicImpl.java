package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * パスワード履歴ビジネスロジック
 * 
 * @author roh
 */
public class PasswordHistoryLogicImpl implements PasswordHistoryLogic {

	/** パスワード管理マスタDao */
	private PWD_HST_TBLDao dao;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao PWD_HST_TBLDao
	 */
	public PasswordHistoryLogicImpl(PWD_HST_TBLDao dao) {
		this.dao = dao;
	}

	/**
	 * 履歴データ検索.<br>
	 * 
	 * @param kaiCode 会社コード
	 * @param usrCode ユーザコード
	 * @return 履歴データ
	 */
	public List<PWD_HST_TBL> findPassword(String kaiCode, String usrCode) {

		return dao.findPassword(kaiCode, usrCode);
	}

	/**
	 * 履歴新規
	 * 
	 * @param pwdDto
	 */
	public void insert(PWD_HST_TBL pwdDto) {
		dao.insert(pwdDto);
	}

}

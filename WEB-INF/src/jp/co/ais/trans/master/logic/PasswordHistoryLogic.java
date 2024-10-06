package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * パスワード履歴ビジネスロジック
 * 
 * @author roh
 */
public interface PasswordHistoryLogic {

	/**
	 * 履歴データ検索.<br>
	 * 
	 * @param kaiCode 会社コード
	 * @param usrCode ユーザコード
	 * @return 履歴データ
	 */
	public List<PWD_HST_TBL> findPassword(String kaiCode, String usrCode);

	/**
	 * 履歴新規
	 * 
	 * @param pwdDto
	 */
	public void insert(PWD_HST_TBL pwdDto);

}

package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ユーザマスタビジネスロジック
 * 
 * @author roh
 */
public interface UserLogic extends CommonLogic {

	/**
	 * vertifier対応 ユーザ登録確認と部門情報習得
	 * 
	 * @param keys
	 * @return List
	 */
	public List getREFItems(Map keys);

	/**
	 * 会社コードでユーザリストを習得
	 * 
	 * @param kaiCode 会社コード
	 * @return List<USR_MST> ユーザlist
	 */
	public List<USR_MST> searchUsrList(String kaiCode);

	/**
	 * 更新ユーザマスタ
	 * 
	 * @param entity ユーザマスタ
	 * @param oldKaiCode old会社コード
	 */
	public void updateUsrMst(USR_MST entity,String oldKaiCode);

}

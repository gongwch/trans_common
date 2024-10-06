package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 会社階層マスタビジネスロジック
 */
public interface CompanyHierarchicalMasterLogic {

	/**
	 * データ登録
	 * 
	 * @param dto
	 * @throws TException
	 */
	public void insert(EVK_MST dto) throws TException;

	/**
	 * データ削除
	 * 
	 * @param dto
	 * @throws TException
	 */
	public void delete(EVK_MST dto) throws TException;

	/**
	 * データ削除
	 * 
	 * @param kaiCode
	 * @param sskCode
	 */
	public void delete(String kaiCode, String sskCode);

	/**
	 * データ編集
	 * 
	 * @param list
	 * @throws TException
	 */
	public void update(List list) throws TException;

	/**
	 * @param kaiCode
	 * @param sskCode
	 * @return List<Object
	 */
	public List<Object> getWithOutCom(String kaiCode, String sskCode);

	/**
	 * @param kaiCode
	 * @param sskCode
	 * @return List<Object
	 */
	public List<Object> getComLvl(String kaiCode, String sskCode);

	/**
	 * 会社コントロールデータ取得
	 * 
	 * @param string 会社コード
	 * @return 会社コントロールデータ
	 */
	public ENV_MST getENV_MST(String string);

	/**
	 * @param kaiCode
	 * @return List<Object>
	 */
	public List<Object> getOrgCode(String kaiCode);

	/**
	 * @param kaiCode
	 * @return List<Object>
	 */
	public List<Object> getENVListNewSsk(String kaiCode);

	/**
	 * @param kaiCode
	 * @param sskCode
	 * @throws TException
	 */
	public void getSskCode(String kaiCode, String sskCode) throws TException;
}

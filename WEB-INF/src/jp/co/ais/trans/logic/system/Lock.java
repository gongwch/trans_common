package jp.co.ais.trans.logic.system;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 排他制御ロジック
 */
public interface Lock extends Serializable {

	/**
	 * コード設定
	 * 
	 * @param userCode ユーザコード
	 * @param compCode 会社コード
	 */
	public void setCode(String compCode, String userCode);

	/**
	 * コード設定（個別排他解除）
	 * 
	 * @param compCode 会社コード
	 * @param userCode ユーザコード
	 * @param prgCode プログラムコード
	 */
	public void setCode(String compCode, String userCode, String prgCode);

	/**
	 * 機能排他 強制解除処理
	 */
	public void deleteForced();

	/**
	 * コード排他(HAITA_CTL) 強制解除処理
	 */
	public void deleteHaitaForced();

	/**
	 * 全強制排他解除
	 */
	public void unlockAll();

	/**
	 * 指定の伝票リストを排他解除する。
	 * 
	 * @param slipList
	 */
	public void unlockSlip(List<SlipUnlockObject> slipList);

	/**
	 * バッチ排他 個別排他解除
	 */
	public void batctlDeleteByUsrPrg();

	/**
	 * コード排他 個別排他解除
	 */
	public void haitactlDeleteByUsrPrg();

	/**
	 * バッチ, コード 個別排他解除
	 */
	public void unlockPrg();

	/**
	 * 指定会社コードで排他伝票リストを取得
	 * 
	 * @return 排他伝票リスト
	 */
	public List<SlipUnlockObject> getExclusiveSlip();

	/**
	 * 会社コード取得
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode();

	/**
	 * ユーザコード取得
	 * 
	 * @return ユーザコード
	 */
	public String getUserCode();

	/**
	 * プログラムコード取得
	 * 
	 * @return プログラムコード
	 */
	public String getPrgCode();
	
	/**
	 * 排他解除伝票リスト取得
	 * 
	 * @return 排他解除伝票リスト
	 */
	public List<SlipUnlockObject> getUnlockSlipList();

	/**
	 * 排他伝票リストを追加
	 * 
	 * @param exclusiveSlipList
	 */
	public void addSliplist(List<SlipUnlockObject> exclusiveSlipList);
}

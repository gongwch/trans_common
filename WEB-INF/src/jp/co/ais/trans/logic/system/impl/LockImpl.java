package jp.co.ais.trans.logic.system.impl;

import java.util.*;

import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 排他制御ロジック
 */
public class LockImpl implements Lock {

	/** BATコントロールDao */
	protected BAT_CTLDao batDao;

	/** 排他制御DAO */
	protected HAITA_CTLDao haitaDao;

	/** 会社コード */
	protected String companyCode;

	/** ユーザコード */
	protected String userCode;

	/** プログラムコード */
	protected String prgCode;

	/** 排他解除伝票リスト */
	protected List<SlipUnlockObject> slipList;

	/** 排他伝票リスト */
	protected List<SlipUnlockObject> returnSlipList;

	/**
	 * コンストラクタ
	 */
	public LockImpl() {
		slipList = new LinkedList<SlipUnlockObject>();
		returnSlipList = new LinkedList<SlipUnlockObject>();
	}

	/**
	 * @param batDao
	 */
	public void setBatDao(BAT_CTLDao batDao) {
		this.batDao = batDao;
	}

	/**
	 * @param haitaDao
	 */
	public void setHaitaDao(HAITA_CTLDao haitaDao) {
		this.haitaDao = haitaDao;
	}

	/**
	 * コード設定
	 * 
	 * @param userCode ユーザコード
	 * @param compCode 会社コード
	 */
	public void setCode(String compCode, String userCode) {
		this.companyCode = compCode;
		this.userCode = userCode;
	}

	/**
	 * コード設定（個別排他解除）
	 * 
	 * @param userCode ユーザコード
	 * @param compCode 会社コード
	 */
	public void setCode(String compCode, String userCode, String prgCode) {
		this.companyCode = compCode;
		this.userCode = userCode;
		this.prgCode = prgCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getCompanyCode()
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getUserCode()
	 */
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getUserCode()
	 */
	public String getPrgCode() {
		return this.prgCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#unlockAll()
	 */
	public void unlockAll() {
		// 機能排他 強制解除
		this.deleteForced();

		// コード排他 強制解除
		this.deleteHaitaForced();
	}

	/**
	 * 機能排他 強制解除処理
	 */
	public void deleteForced() {

		// 強制デリート
		batDao.deleteForced(companyCode, userCode);
	}

	/**
	 * コード排他 強制解除処理
	 */
	public void deleteHaitaForced() {

		// データを削除
		haitaDao.deleteLockUser(companyCode, userCode);
	}

	/**
	 * バッチ, コード 個別排他解除
	 */
	public void unlockPrg() {
		// バッチ排他 個別排他解除
		this.batctlDeleteByUsrPrg();

		// コード排他 個別排他解除
		this.haitactlDeleteByUsrPrg();
	}

	/**
	 * バッチ排他 個別排他解除
	 */
	public void batctlDeleteByUsrPrg() {
		batDao.deleteByUsrPrg(companyCode, prgCode, userCode);
	}

	/**
	 * コード排他 個別排他解除
	 */
	public void haitactlDeleteByUsrPrg() {
		haitaDao.deleteByUsrPrg(companyCode, userCode, prgCode);
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#unlockSlip(java.util.List)
	 */
	public void unlockSlip(List<SlipUnlockObject> sliplist) {
		this.slipList = sliplist;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getExclusiveSlip()
	 */
	public List<SlipUnlockObject> getExclusiveSlip() {
		returnSlipList = new LinkedList<SlipUnlockObject>();
		return this.returnSlipList;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getUnlockSlipList()
	 */
	public List<SlipUnlockObject> getUnlockSlipList() {
		return this.slipList;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#addSliplist(java.util.List)
	 */
	public void addSliplist(List<SlipUnlockObject> exclusiveSlipList) {
		this.returnSlipList.addAll(exclusiveSlipList);
	}

}

package jp.co.ais.trans.logic.system.impl;

import org.seasar.framework.container.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ロックアウトロジック
 */
public class UserLockoutImpl implements UserLockout {

	/** コンテナ */
	private S2Container container;

	/** 会社コード */
	private String companyCode;

	/** ユーザコード */
	private String userCode;

	/** ユーザ認証マスタDAO */
	private USR_AUTH_MSTDao usrAuthDao;

	/** ロックアウトマスタDAO */
	private LOCK_OUT_TBLDao lockDao;

	/** ロックアウトデータ */
	private LOCK_OUT_TBL lockOutTbl;

	/** ユーザ認証データ */
	private USR_AUTH_MST usrAuthMst;

	/**
	 * コンストラクタ
	 * 
	 * @param container コンテナ
	 */
	public UserLockoutImpl(S2Container container) {
		this.container = container;
	}

	/**
	 * @param usrAuthDao
	 */
	public void setUsrAuthDao(USR_AUTH_MSTDao usrAuthDao) {
		this.usrAuthDao = usrAuthDao;
	}

	/**
	 * @param lockdao
	 */
	public void setLockDao(LOCK_OUT_TBLDao lockdao) {
		this.lockDao = lockdao;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param compCode 会社コード
	 */
	public void setCode(String compCode, String userCode) {
		this.companyCode = compCode;
		this.userCode = userCode;

		this.lockOutTbl = lockDao.findWithPK(compCode, userCode);
		this.usrAuthMst = usrAuthDao.findByKaiCode(compCode);
	}

	/**
	 * ロックアウト管理を行うかどうか
	 * 
	 * @return true:ロックアウト管理する
	 */
	public boolean isLockoutManaged() {
		return usrAuthMst != null;
	}

	/**
	 * 該当ユーザがロックアウトかどうかを判別<br>
	 * 
	 * @return boolean true:ロックアウト状態
	 */
	public boolean isLockoutStatus() {

		// 認証設定が存在しない(チェックしない)
		if (usrAuthMst == null) {
			return false;
		}

		// 認証設定が0の場合チェックなし(チェックしない)
		if (usrAuthMst.getLOCK_OUT_ARR_CNT() == 0) {
			return false;
		}

		// ロックアウト情報が存在しない
		if (lockOutTbl == null) {
			return false;
		}

		// ロックアウト回数と認証回数の比較
		if (lockOutTbl.getFAIL_COUNT() < usrAuthMst.getLOCK_OUT_ARR_CNT()) {
			return false;
		}

		// ロックアウト自動開放

		// それぞれのDATE値をmileSecondに変える。
		long releaseTime = usrAuthMst.getLOCK_OUT_RELEASE_TIME();
		long sysDate = (Util.getCurrentDate().getTime()) / 60000;
		long failDate = (lockOutTbl.getFAIL_DATE().getTime()) / 60000;

		// 開放時間設定0の時は自動開放なし。
		if (releaseTime != 0 && (sysDate - failDate) > releaseTime) {
			return false;
		}

		// ロックアウト状態
		return true;
	}

	/**
	 * ロックアウト数１増加 <br>
	 * 新規の場合は新規記録 更新の場合カウント数１増加
	 */
	public void countUp() {

		// 認証設定が存在しないとカウントアップなし
		if (usrAuthMst == null) {
			return;
		}

		// カウントアップ設定が0だったらカウントアップしない
		if (usrAuthMst.getLOCK_OUT_ARR_CNT() == 0) {
			return;
		}

		if (lockOutTbl == null) {
			// データない場合インサート
			lockOutTbl = (LOCK_OUT_TBL) container.getComponent("LOCK_OUT_TBL");
			lockOutTbl.setKAI_CODE(companyCode);
			lockOutTbl.setUSR_CODE(userCode);
			lockOutTbl.setFAIL_COUNT(1);
			lockOutTbl.setFAIL_DATE(Util.getCurrentDate());
			lockOutTbl.setINP_DATE(Util.getCurrentDate());

			lockDao.insert(lockOutTbl);

		} else {
			// カウントアップ
			lockOutTbl.setFAIL_COUNT(lockOutTbl.getFAIL_COUNT() + 1);
			lockOutTbl.setFAIL_DATE(Util.getCurrentDate());

			lockDao.update(lockOutTbl);
		}
	}

	/**
	 * ロックアウト解除
	 */
	public void clearLockout() {

		if (lockOutTbl != null) {
			lockDao.delete(lockOutTbl);
		}

		lockOutTbl = null;
	}

}

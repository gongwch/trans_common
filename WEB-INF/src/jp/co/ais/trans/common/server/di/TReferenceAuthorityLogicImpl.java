package jp.co.ais.trans.common.server.di;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * マスタ参照ロジック
 */
public class TReferenceAuthorityLogicImpl implements TReferenceAuthorityLogic {

	/** 部門マスタ一覧 */
	private BMN_MSTDao bmnMstDao_;

	/** ユーザーマスタ */
	private USR_MSTDao USR_MSTDao_;

	/** 社員マスタ */
	private EMP_MSTDao EMP_MSTDao_;

	/**
	 * 入力部門略称取得<BR>
	 * 
	 * @param kaiCode 会社コード
	 * @param depCode テキストフィールドに文字列
	 * @return BMN_MST 部門マスタbean
	 */
	public BMN_MST getBMN_MSTByKaicodeDepcode(String kaiCode, String depCode) {
		// 部門マスタbean
		BMN_MST bmnMstBean = null;
		if (!"".equals(depCode)) {
			bmnMstBean = bmnMstDao_.getBmnMstDataByKaiCodeDepCode(kaiCode, depCode);
		}
		return bmnMstBean;
	}

	/**
	 * ログインユーザーの社員コードを取得<BR>
	 * 
	 * @param kaiCode 会社コード
	 * @param usrCode テキストフィールドに文字列
	 * @return USR_MST ユーザーマスタbean
	 */
	public USR_MST getUSR_MSTByKaicodeUsercode(String kaiCode, String usrCode) {
		// ユーザーマスタbean
		USR_MST usrMst = null;
		if (!"".equals(usrCode)) {
			usrMst = USR_MSTDao_.getUSR_MSTByKaicodeUsrcode(kaiCode, usrCode);
		}
		return usrMst;
	}

	/**
	 * ログインユーザーの社員略称を取得<BR>
	 * 
	 * @param kaiCode 会社コード
	 * @param empCode テキストフィールドに文字列
	 * @param depCode 所属部門コード
	 * @return EMP_MST ユーザーマスタ
	 */
	public EMP_MST getEMP_MSTByKaiCodeEmpCode(String kaiCode, String empCode, String depCode) {
		// 社員マスタbean
		EMP_MST empMst = null;
		if (!"".equals(empCode)) {
			empMst = EMP_MSTDao_.searchEmpNamesByUser(kaiCode, empCode, depCode);
		}
		return empMst;
	}

	/**
	 * setBmnMstDao_
	 * 
	 * @param bmnMstDao_
	 */
	public void setBmnMstDao_(BMN_MSTDao bmnMstDao_) {
		this.bmnMstDao_ = bmnMstDao_;
	}

	/**
	 * setUSR_MSTDao_
	 * 
	 * @param dao_
	 */
	public void setUSR_MSTDao_(USR_MSTDao dao_) {
		USR_MSTDao_ = dao_;
	}

	/**
	 * setEMP_MSTDao_
	 * 
	 * @param dao_
	 */
	public void setEMP_MSTDao_(EMP_MSTDao dao_) {
		EMP_MSTDao_ = dao_;
	}
}

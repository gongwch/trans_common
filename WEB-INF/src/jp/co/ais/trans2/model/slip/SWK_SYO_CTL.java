package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 仕訳承認状況テーブル
 */
public class SWK_SYO_CTL extends TransferBase {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 伝票日付 */
	protected Date SWK_DEN_DATE = null;

	/** 伝票番号 */
	protected String SWK_DEN_NO = null;

	/** SEQ */
	protected int SEQ = -1;

	/** 更新区分 */
	protected int UPD_KBN = -1;

	/** 前回更新区分 */
	protected int PREV_UPD_KBN = -1;

	/** 承認ロールグループコード */
	protected String APRV_ROLE_GRP_CODE = null;

	/** 承認ロールグループ名称 */
	protected String aprvRoleGroupName = null;

	/** 承認ロールコード */
	protected String APRV_ROLE_CODE = null;

	/** 承認ロール名 */
	protected String aprvRoleName = null;

	/** 承認ロールがスキップ可能ﾌﾗｸﾞか */
	protected boolean isAprvRoleSkippable = false;

	/** 次回承認ロールコード */
	protected String NEXT_APRV_ROLE_CODE = null;

	/** 次回承認ロール名称 */
	protected String nextAprvRoleName = null;

	/** 次回承認ロールがスキップ可能ﾌﾗｸﾞか */
	protected boolean isNextAprvRoleSkippable = false;

	/** 登録日付 */
	protected Date INP_DATE = null;

	/** プログラムＩＤ */
	protected String PRG_ID = null;

	/** ユーザーＩＤ */
	protected String USR_ID = null;

	/**
	 * 会社コードを取得する
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードを設定する
	 * 
	 * @param KAI_CODE 会社コード
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * 伝票日付を取得する
	 * 
	 * @return 伝票日付
	 */
	public Date getSWK_DEN_DATE() {
		return SWK_DEN_DATE;
	}

	/**
	 * 伝票日付を設定する
	 * 
	 * @param SWK_DEN_DATE 伝票日付
	 */
	public void setSWK_DEN_DATE(Date SWK_DEN_DATE) {
		this.SWK_DEN_DATE = SWK_DEN_DATE;
	}

	/**
	 * 伝票番号を取得する
	 * 
	 * @return 伝票番号
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * 伝票番号を設定する
	 * 
	 * @param SWK_DEN_NO 伝票番号
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

	/**
	 * SEQを取得する
	 * 
	 * @return SEQ
	 */
	public int getSEQ() {
		return SEQ;
	}

	/**
	 * SEQを設定する
	 * 
	 * @param SEQ SEQ
	 */
	public void setSEQ(int SEQ) {
		this.SEQ = SEQ;
	}

	/**
	 * 更新区分を取得する
	 * 
	 * @return 更新区分
	 */
	public int getUPD_KBN() {
		return UPD_KBN;
	}

	/**
	 * 更新区分を設定する
	 * 
	 * @param UPD_KBN 更新区分
	 */
	public void setUPD_KBN(int UPD_KBN) {
		this.UPD_KBN = UPD_KBN;
	}

	/**
	 * 前回更新区分を取得する
	 * 
	 * @return 前回更新区分
	 */
	public int getPREV_UPD_KBN() {
		return PREV_UPD_KBN;
	}

	/**
	 * 前回更新区分を設定する
	 * 
	 * @param PREV_UPD_KBN 前回更新区分
	 */
	public void setPREV_UPD_KBN(int PREV_UPD_KBN) {
		this.PREV_UPD_KBN = PREV_UPD_KBN;
	}

	/**
	 * 承認ロールグループコードを取得
	 * 
	 * @return aPRV_ROLE_GRP_CODE
	 */
	public String getAPRV_ROLE_GRP_CODE() {
		return APRV_ROLE_GRP_CODE;
	}

	/**
	 * 承認ロールグループコードをセットする
	 * 
	 * @param aPRV_ROLE_GRP_CODE aPRV_ROLE_GRP_CODE
	 */
	public void setAPRV_ROLE_GRP_CODE(String aPRV_ROLE_GRP_CODE) {
		APRV_ROLE_GRP_CODE = aPRV_ROLE_GRP_CODE;
	}

	/**
	 * 承認ロールコードを取得する
	 * 
	 * @return 承認ロールコード
	 */
	public String getAPRV_ROLE_CODE() {
		return APRV_ROLE_CODE;
	}

	/**
	 * 承認ロールコードを設定する
	 * 
	 * @param APRV_ROLE_CODE 承認ロールコード
	 */
	public void setAPRV_ROLE_CODE(String APRV_ROLE_CODE) {
		this.APRV_ROLE_CODE = APRV_ROLE_CODE;
	}

	/**
	 * 次回承認ロールコードを取得する
	 * 
	 * @return 次回承認ロールコード
	 */
	public String getNEXT_APRV_ROLE_CODE() {
		return NEXT_APRV_ROLE_CODE;
	}

	/**
	 * 次回承認ロールコードを設定する
	 * 
	 * @param NEXT_APRV_ROLE_CODE 次回承認ロールコード
	 */
	public void setNEXT_APRV_ROLE_CODE(String NEXT_APRV_ROLE_CODE) {
		this.NEXT_APRV_ROLE_CODE = NEXT_APRV_ROLE_CODE;
	}

	/**
	 * 登録日付を取得する
	 * 
	 * @return 登録日付
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録日付を設定する
	 * 
	 * @param INP_DATE 登録日付
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * プログラムＩＤを取得する
	 * 
	 * @return プログラムＩＤ
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * プログラムＩＤを設定する
	 * 
	 * @param PRG_ID プログラムＩＤ
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ユーザーＩＤを取得する
	 * 
	 * @return ユーザーＩＤ
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ユーザーＩＤを設定する
	 * 
	 * @param USR_ID ユーザーＩＤ
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * 承認ロールグループ名称を取得
	 * 
	 * @return aprvRoleGroupName
	 */
	public String getAprvRoleGroupName() {
		return aprvRoleGroupName;
	}

	/**
	 * 承認ロールグループ名称をセットする
	 * 
	 * @param aprvRoleGroupName aprvRoleGroupName
	 */
	public void setAprvRoleGroupName(String aprvRoleGroupName) {
		this.aprvRoleGroupName = aprvRoleGroupName;
	}

	/**
	 * 承認ロール名を取得
	 * 
	 * @return aprvRoleName
	 */
	public String getAprvRoleName() {
		return aprvRoleName;
	}

	/**
	 * 承認ロール名をセットする
	 * 
	 * @param aprvRoleName aprvRoleName
	 */
	public void setAprvRoleName(String aprvRoleName) {
		this.aprvRoleName = aprvRoleName;
	}

	/**
	 * 次回承認ロール名称を取得
	 * 
	 * @return nextAprvRoleName
	 */
	public String getNextAprvRoleName() {
		return nextAprvRoleName;
	}

	/**
	 * 次回承認ロール名称をセットする
	 * 
	 * @param nextAprvRoleName nextAprvRoleName
	 */
	public void setNextAprvRoleName(String nextAprvRoleName) {
		this.nextAprvRoleName = nextAprvRoleName;
	}

	/**
	 * ロールがスキップ可能ﾌﾗｸﾞかを取得
	 * 
	 * @return isAprvRoleSkippable
	 */
	public boolean isAprvRoleSkippable() {
		return isAprvRoleSkippable;
	}

	/**
	 * ロールがスキップ可能ﾌﾗｸﾞかをセットする
	 * 
	 * @param isAprvRoleSkippable isAprvRoleSkippable
	 */
	public void setAprvRoleSkippable(boolean isAprvRoleSkippable) {
		this.isAprvRoleSkippable = isAprvRoleSkippable;
	}

	/**
	 * 次回承認ロールがスキップ可能ﾌﾗｸﾞかを取得
	 * 
	 * @return isNextAprvRoleSkippable
	 */
	public boolean isNextAprvRoleSkippable() {
		return isNextAprvRoleSkippable;
	}

	/**
	 * 次回承認ロールがスキップ可能ﾌﾗｸﾞかをセットする
	 * 
	 * @param isNextAprvRoleSkippable isNextAprvRoleSkippable
	 */
	public void setNextAprvRoleSkippable(boolean isNextAprvRoleSkippable) {
		this.isNextAprvRoleSkippable = isNextAprvRoleSkippable;
	}

}

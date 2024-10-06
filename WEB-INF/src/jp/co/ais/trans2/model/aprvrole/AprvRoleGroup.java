package jp.co.ais.trans2.model.aprvrole;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 承認権限ロールグループエンティティ
 */
public class AprvRoleGroup extends TransferBase {

	/** 会社コード */
	protected String KAI_CODE = null;

	/** ロールグループコード */
	protected String APRV_ROLE_GRP_CODE = null;

	/** ロールグループ名称 */
	protected String APRV_ROLE_GRP_NAME = null;

	/** ロールグループ略称 */
	protected String APRV_ROLE_GRP_NAME_S = null;

	/** ロールグループ検索名称 */
	protected String APRV_ROLE_GRP_NAME_K = null;

	/** 開始年月日 */
	protected Date STR_DATE = null;

	/** 終了年月日 */
	protected Date END_DATE = null;

	/** 登録日付 */
	protected Date INP_DATE = null;

	/** 更新日付 */
	protected Date UPD_DATE = null;

	/** プログラムＩＤ */
	protected String PRG_ID = null;

	/** ユーザーＩＤ */
	protected String USR_ID = null;

	/** 明細リスト */
	protected List<AprvRoleGroupDetail> detailList = null;

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
	 * ロールグループコードを取得する
	 * 
	 * @return ロールグループコード
	 */
	public String getAPRV_ROLE_GRP_CODE() {
		return APRV_ROLE_GRP_CODE;
	}

	/**
	 * ロールグループコードを設定する
	 * 
	 * @param APRV_ROLE_GRP_CODE ロールグループコード
	 */
	public void setAPRV_ROLE_GRP_CODE(String APRV_ROLE_GRP_CODE) {
		this.APRV_ROLE_GRP_CODE = APRV_ROLE_GRP_CODE;
	}

	/**
	 * ロールグループ名称を取得する
	 * 
	 * @return ロールグループ名称
	 */
	public String getAPRV_ROLE_GRP_NAME() {
		return APRV_ROLE_GRP_NAME;
	}

	/**
	 * ロールグループ名称を設定する
	 * 
	 * @param APRV_ROLE_GRP_NAME ロールグループ名称
	 */
	public void setAPRV_ROLE_GRP_NAME(String APRV_ROLE_GRP_NAME) {
		this.APRV_ROLE_GRP_NAME = APRV_ROLE_GRP_NAME;
	}

	/**
	 * ロールグループ略称を取得する
	 * 
	 * @return ロールグループ略称
	 */
	public String getAPRV_ROLE_GRP_NAME_S() {
		return APRV_ROLE_GRP_NAME_S;
	}

	/**
	 * ロールグループ略称を設定する
	 * 
	 * @param APRV_ROLE_GRP_NAME_S ロールグループ略称
	 */
	public void setAPRV_ROLE_GRP_NAME_S(String APRV_ROLE_GRP_NAME_S) {
		this.APRV_ROLE_GRP_NAME_S = APRV_ROLE_GRP_NAME_S;
	}

	/**
	 * ロールグループ検索名称を取得する
	 * 
	 * @return ロールグループ検索名称
	 */
	public String getAPRV_ROLE_GRP_NAME_K() {
		return APRV_ROLE_GRP_NAME_K;
	}

	/**
	 * ロールグループ検索名称を設定する
	 * 
	 * @param APRV_ROLE_GRP_NAME_K ロールグループ検索名称
	 */
	public void setAPRV_ROLE_GRP_NAME_K(String APRV_ROLE_GRP_NAME_K) {
		this.APRV_ROLE_GRP_NAME_K = APRV_ROLE_GRP_NAME_K;
	}

	/**
	 * 開始年月日を取得する
	 * 
	 * @return 開始年月日
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * 開始年月日を設定する
	 * 
	 * @param STR_DATE 開始年月日
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * 終了年月日を取得する
	 * 
	 * @return 終了年月日
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * 終了年月日を設定する
	 * 
	 * @param END_DATE 終了年月日
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
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
	 * 更新日付を取得する
	 * 
	 * @return 更新日付
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * 更新日付を設定する
	 * 
	 * @param UPD_DATE 更新日付
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
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
	 * 明細リストを取得
	 * 
	 * @return detailList
	 */
	public List<AprvRoleGroupDetail> getDetailList() {
		return detailList;
	}

	/**
	 * 明細リストをセットする
	 * 
	 * @param detailList detailList
	 */
	public void setDetailList(List<AprvRoleGroupDetail> detailList) {
		this.detailList = detailList;
	}

	/**
	 * 明細リストに明細を追加
	 * 
	 * @param detail
	 */
	public void addDetailList(AprvRoleGroupDetail detail) {
		if (detailList == null) {
			detailList = new ArrayList();
		}
		detailList.add(detail);
	}

	/**
	 * 先頭の承認ロールコード取得
	 * 
	 * @return 先頭の承認ロールコード取得
	 */
	public String getFirstRoleCode() {
		if (detailList == null || detailList.isEmpty()) {
			return null;
		}
		String code = null;
		int minLevel = 9;
		for (AprvRoleGroupDetail d : detailList) {
			if (code == null) {
				code = d.getAPRV_ROLE_CODE();
				minLevel = d.getAPRV_LEVEL();
			}
			if (minLevel > d.getAPRV_LEVEL()) {
				code = d.getAPRV_ROLE_CODE();
				minLevel = d.getAPRV_LEVEL();
			}
		}
		return code;
	}

	/**
	 * 最終の承認ロールコード取得
	 * 
	 * @return 最終の承認ロールコード取得
	 */
	public String getLastRoleCode() {
		if (detailList == null || detailList.isEmpty()) {
			return null;
		}
		String code = null;
		int maxLevel = 0;
		for (AprvRoleGroupDetail d : detailList) {
			if (code == null) {
				code = d.getAPRV_ROLE_CODE();
				maxLevel = d.getAPRV_LEVEL();
			}
			if (maxLevel < d.getAPRV_LEVEL()) {
				code = d.getAPRV_ROLE_CODE();
				maxLevel = d.getAPRV_LEVEL();
			}
		}
		return code;
	}
}

package jp.co.ais.trans2.model.aprvrole;

/**
 * 承認グループ明細<br>
 * ロール情報までを保持し、ぶら下がる所属ユーザーについては扱わない
 */
public class AprvRoleGroupDetail extends AprvRole {

	/** 承認権限レベル */
	protected int APRV_LEVEL = -1;

	/**
	 * 承認権限レベルを取得
	 * 
	 * @return 承認権限レベル
	 */
	public int getAPRV_LEVEL() {
		return APRV_LEVEL;
	}

	/**
	 * 承認権限レベルをセットする
	 * 
	 * @param aPRV_LEVEL 承認権限レベル
	 */
	public void setAPRV_LEVEL(int aPRV_LEVEL) {
		APRV_LEVEL = aPRV_LEVEL;
	}

}

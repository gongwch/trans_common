package jp.co.ais.trans2.model.history;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;

/**
 * 承認履歴
 */
public class ApproveHistory extends TransferBase {

	/**
	 * 承認フラグ
	 */
	public static class SYO_FLG {

		/** 0:承認 */
		public static final int APPROVE = 0;

		/** 1:取消 */
		public static final int CANCEL = 1;

		/** 2:否認 */
		public static final int DENY = 2;

		/** 3:登録 */
		public static final int ENTRY = 3;

		/** 4:修正 */
		public static final int MODIFY = 4;

		/** 5:更新 */
		public static final int UPDATE = 5;
	}

	/** 会社コード */
	protected String KAI_CODE = null;

	/** 伝票日付 */
	protected Date SWK_DEN_DATE = null;

	/** 伝票番号 */
	protected String SWK_DEN_NO = null;

	/** 更新区分 */
	protected int SWK_UPD_KBN = SlipState.ENTRY.value;

	/** 承認者 */
	protected String EMP_CODE = null;

	/** 承認者名称 */
	protected String EMP_NAME = null;

	/** 承認者略称 */
	protected String EMP_NAME_S = null;

	/** 承認フラグ(0:承認、1:キャンセル、2:否認、3:登録、4:修正、5:更新) */
	protected int sYO_FLG = 0;

	/** 登録日時(承認日時) */
	protected Date INP_DATE = null;

	/** 前の更新区分 */
	protected int PREV_SWK_UPD_KBN = SlipState.ENTRY.value;

	/** 今の承認者権限ロールコード */
	protected String APRV_ROLE_CODE = null;

	/** 次の承認者権限ロールコード */
	protected String NEXT_APRV_ROLE_CODE = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return KAI_CODE 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param KAI_CODE 会社コード
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * 伝票日付の取得
	 * 
	 * @return SWK_DEN_DATE 伝票日付
	 */
	public Date getSWK_DEN_DATE() {
		return SWK_DEN_DATE;
	}

	/**
	 * 伝票日付の設定
	 * 
	 * @param SWK_DEN_DATE 伝票日付
	 */
	public void setSWK_DEN_DATE(Date SWK_DEN_DATE) {
		this.SWK_DEN_DATE = SWK_DEN_DATE;
	}

	/**
	 * 伝票番号の取得
	 * 
	 * @return SWK_DEN_NO 伝票番号
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * 伝票番号の設定
	 * 
	 * @param SWK_DEN_NO 伝票番号
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

	/**
	 * 更新区分の取得
	 * 
	 * @return SWK_UPD_KBN 更新区分
	 */
	public int getSWK_UPD_KBN() {
		return SWK_UPD_KBN;
	}

	/**
	 * 更新区分の設定
	 * 
	 * @param SWK_UPD_KBN 更新区分
	 */
	public void setSWK_UPD_KBN(int SWK_UPD_KBN) {
		this.SWK_UPD_KBN = SWK_UPD_KBN;
	}

	/**
	 * 承認者の取得
	 * 
	 * @return EMP_CODE 承認者
	 */
	public String getEMP_CODE() {
		return EMP_CODE;
	}

	/**
	 * 承認者の設定
	 * 
	 * @param EMP_CODE 承認者
	 */
	public void setEMP_CODE(String EMP_CODE) {
		this.EMP_CODE = EMP_CODE;
	}

	/**
	 * 承認者名称の取得
	 * 
	 * @return EMP_NAME 承認者名称
	 */
	public String getEMP_NAME() {
		return EMP_NAME;
	}

	/**
	 * 承認者名称の設定
	 * 
	 * @param EMP_NAME 承認者名称
	 */
	public void setEMP_NAME(String EMP_NAME) {
		this.EMP_NAME = EMP_NAME;
	}

	/**
	 * 承認者略称の取得
	 * 
	 * @return EMP_NAME_S 承認者略称
	 */
	public String getEMP_NAME_S() {
		return EMP_NAME_S;
	}

	/**
	 * 承認者略称の設定
	 * 
	 * @param EMP_NAME_S 承認者略称
	 */
	public void setEMP_NAME_S(String EMP_NAME_S) {
		this.EMP_NAME_S = EMP_NAME_S;
	}

	/**
	 * 登録日時(承認日時)の取得
	 * 
	 * @return INP_DATE 登録日時(承認日時)
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * 登録日時(承認日時)の設定
	 * 
	 * @param INP_DATE 登録日時(承認日時)
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * 承認フラグ(0:承認、1:キャンセル、2:否認、3:登録、4:修正、5:更新)の取得
	 * 
	 * @return SYO_FLG 承認フラグ(0:承認、1:キャンセル、2:否認、3:登録、4:修正、5:更新)
	 */
	public int getSYO_FLG() {
		return sYO_FLG;
	}

	/**
	 * 承認フラグ(0:承認、1:キャンセル、2:否認、3:登録、4:修正、5:更新)の設定
	 * 
	 * @param SYO_FLG 承認フラグ(0:承認、1:キャンセル、2:否認、3:登録、4:修正、5:更新)
	 */
	public void setSYO_FLG(int SYO_FLG) {
		this.sYO_FLG = SYO_FLG;
	}

	/**
	 * 前の更新区分の取得
	 * 
	 * @return PREV_SWK_UPD_KBN 前の更新区分
	 */
	public int getPREV_SWK_UPD_KBN() {
		return PREV_SWK_UPD_KBN;
	}

	/**
	 * 前の更新区分の設定
	 * 
	 * @param PREV_SWK_UPD_KBN 前の更新区分
	 */
	public void setPREV_SWK_UPD_KBN(int PREV_SWK_UPD_KBN) {
		this.PREV_SWK_UPD_KBN = PREV_SWK_UPD_KBN;
	}

	/**
	 * 今の承認者権限ロールコードの取得
	 * 
	 * @return 今の承認者権限ロール
	 */
	public String getAPRV_ROLE_CODE() {
		return APRV_ROLE_CODE;
	}

	/**
	 * 今の承認者権限ロールコードの設定
	 * 
	 * @param APRV_ROLE_CODE
	 */
	public void setAPRV_ROLE_CODE(String APRV_ROLE_CODE) {
		this.APRV_ROLE_CODE = APRV_ROLE_CODE;
	}

	/**
	 * 次の承認者権限ロールコードの取得
	 * 
	 * @return 次の承認者権限ロール
	 */
	public String getNEXT_APRV_ROLE_CODE() {
		return NEXT_APRV_ROLE_CODE;
	}

	/**
	 * 次の承認者権限ロールコードの設定
	 * 
	 * @param NEXT_APRV_ROLE_CODE
	 */
	public void setNEXT_APRV_ROLE_CODE(String NEXT_APRV_ROLE_CODE) {
		this.NEXT_APRV_ROLE_CODE = NEXT_APRV_ROLE_CODE;
	}
}

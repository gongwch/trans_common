package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 月次更新<br>
 * 燃料締め情報Bean
 */
public class BMCloseInfo extends TransferBase {

	/** 船コード */
	protected String VESSEL_CODE;

	/** 船名称 */
	protected String VESSEL_NAME;

	/** 油種区分 */
	protected String OIL_TYPE_KBN;

	/** 油種区分名称 */
	protected String OIL_TYPE_NAME;

	/** 伝票日付 */
	protected Date DEN_DATE;

	/**
	 * 船コード取得
	 * 
	 * @return 船コード
	 */
	public String getVESSEL_CODE() {
		return this.VESSEL_CODE;
	}

	/**
	 * 船コード設定
	 * 
	 * @param vESSEL_CODE
	 */
	public void setVESSEL_CODE(String vESSEL_CODE) {
		VESSEL_CODE = vESSEL_CODE;
	}

	/**
	 * 船名称取得
	 * 
	 * @return 船名称
	 */
	public String getVESSEL_NAME() {
		return this.VESSEL_NAME;
	}

	/**
	 * 船名称設定
	 * 
	 * @param vESSEL_NAME
	 */
	public void setVESSEL_NAME(String vESSEL_NAME) {
		VESSEL_NAME = vESSEL_NAME;
	}


	/**
	 * 油種区分取得
	 * 
	 * @return 油種区分
	 */
	public String getOIL_TYPE_KBN() {
		return this.OIL_TYPE_KBN;
	}

	/**
	 * 油種区分設定
	 * 
	 * @param oIL_TYPE_KBN
	 */
	public void setOIL_TYPE_KBN(String oIL_TYPE_KBN) {
		OIL_TYPE_KBN = oIL_TYPE_KBN;
	}
/**
	 * 油種区分名称取得
	 * 
	 * @return 油種区分名称
	 */
	public String getOIL_TYPE_NAME() {
		return this.OIL_TYPE_NAME;
	}

	/**
	 * 油種区分名称設定
	 * 
	 * @param oIL_TYPE_NAME
	 */
	public void setOIL_TYPE_NAME(String oIL_TYPE_NAME) {
		OIL_TYPE_NAME = oIL_TYPE_NAME;
	}

	/**
	 * 伝票日付取得
	 * 
	 * @return 伝票日付
	 */
	public Date getDEN_DATE() {
		return this.DEN_DATE;
	}

	/**
	 * 伝票日付設定
	 * 
	 * @param dEN_DATE
	 */
	public void setDEN_DATE(Date dEN_DATE) {
		DEN_DATE = dEN_DATE;
	}
}

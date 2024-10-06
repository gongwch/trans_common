package jp.co.ais.trans2.model.attach.verify;

import jp.co.ais.trans.common.dt.*;

/**
 * 添付ファイル検証結果
 */
public class AttachmentVerifyResult extends TransferBase {

	/** 会社コード */
	protected String KAI_CODE;

	/** 検証対象種別 */
	protected VerifyResultType TYPE;

	/** キー情報1 仕訳：伝票番号 OP：OP_ATT_UID */
	protected String KEY1;

	/** キー情報2 仕訳：伝票日付 OP：(VOY:VOY_UID TCC:TCC_UID VCC:VCC_UID) */
	protected String KEY2;

	/** キー情報3 仕訳：伝票種別名 OP：(VOY:vessel + voy TCC:TC_CTRT_NO VCC:VCC_CTRT_NO) */
	protected String KEY3;

	/** キー情報4 仕訳：なし OP：(VOY:なし TCC:vessel VCC:vessel + voy) */
	protected String KEY4;

	/** ファイル名 */
	protected String FILE_NAME;

	/** サーバーファイル名 */
	protected String SRV_FILE_NAME;

	/** メッセージ */
	protected String MESSAGE;

	/** 検証対象種別 */
	public enum VerifyResultType {
		/** 仕訳 */
		SWK_ATTACH,
		/** 船費 */
		SC_ATTACH,
		/** OP */
		OP_ATTACH,
		/** BL */
		BL_ATTACH;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コード
	 * 
	 * @param kAI_CODE
	 */
	public void setKAI_CODE(String kAI_CODE) {
		KAI_CODE = kAI_CODE;
	}

	/**
	 * 検証対象種別
	 * 
	 * @return 検証対象種別
	 */
	public VerifyResultType getTYPE() {
		return TYPE;
	}

	/**
	 * 検証対象種別
	 *
	 * @param tYPE
	 */
	public void setTYPE(VerifyResultType tYPE) {
		TYPE = tYPE;
	}

	/**
	 * キー情報1 仕訳：伝票番号 OP：OP_ATT_UID
	 * 
	 * @return キー情報1 仕訳：伝票番号 OP：OP_ATT_UID
	 */
	public String getKEY1() {
		return KEY1;
	}

	/**
	 * キー情報1 仕訳：伝票番号 OP：OP_ATT_UID
	 * 
	 * @param kEY1
	 */
	public void setKEY1(String kEY1) {
		KEY1 = kEY1;
	}

	/**
	 * キー情報2 仕訳：伝票日付 OP：(VOY:VOY_UID TCC:TCC_UID VCC:VCC_UID)
	 *
	 * @return キー情報2 仕訳：伝票日付 OP：(VOY:VOY_UID TCC:TCC_UID VCC:VCC_UID)
	 */
	public String getKEY2() {
		return KEY2;
	}

	/**
	 * キー情報2 仕訳：伝票日付 OP：(VOY:VOY_UID TCC:TCC_UID VCC:VCC_UID)
	 * 
	 * @param kEY2
	 */
	public void setKEY2(String kEY2) {
		KEY2 = kEY2;
	}

	/**
	 * キー情報3 仕訳：伝票種別名 OP：(VOY:vessel + voy TCC:TC_CTRT_NO VCC:VCC_CTRT_NO)
	 * 
	 * @return キー情報3 仕訳：伝票種別名 OP：(VOY:vessel + voy TCC:TC_CTRT_NO VCC:VCC_CTRT_NO)
	 */
	public String getKEY3() {
		return KEY3;
	}

	/**
	 * キー情報3 仕訳：伝票種別名 OP：(VOY:vessel + voy TCC:TC_CTRT_NO VCC:VCC_CTRT_NO)
	 * 
	 * @param kEY3
	 */
	public void setKEY3(String kEY3) {
		KEY3 = kEY3;
	}

	/**
	 * キー情報4 仕訳：なし OP：(VOY:なし TCC:vessel VCC:vessel + voy)
	 * 
	 * @return キー情報4 仕訳：なし OP：(VOY:なし TCC:vessel VCC:vessel + voy)
	 */
	public String getKEY4() {
		return KEY4;
	}

	/**
	 * キー情報4 仕訳：なし OP：(VOY:なし TCC:vessel VCC:vessel + voy)
	 * 
	 * @param kEY4
	 */
	public void setKEY4(String kEY4) {
		KEY4 = kEY4;
	}

	/**
	 * ファイル名
	 * 
	 * @return ファイル名
	 */
	public String getFILE_NAME() {
		return FILE_NAME;
	}

	/**
	 * ファイル名
	 * 
	 * @param fILE_NAME
	 */
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	/**
	 * サーバーファイル名
	 *
	 * @return サーバーファイル名
	 */
	public String getSRV_FILE_NAME() {
		return SRV_FILE_NAME;
	}

	/**
	 * サーバーファイル名
	 * 
	 * @param sRV_FILE_NAME
	 */
	public void setSRV_FILE_NAME(String sRV_FILE_NAME) {
		SRV_FILE_NAME = sRV_FILE_NAME;
	}

	/**
	 * メッセージ
	 * 
	 * @return メッセージ
	 */
	public String getMESSAGE() {
		return MESSAGE;
	}

	/**
	 * メッセージ
	 *
	 * @param mESSAGE
	 */
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

}

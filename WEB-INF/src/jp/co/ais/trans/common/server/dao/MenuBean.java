package jp.co.ais.trans.common.server.dao;

import java.util.*;

import jp.co.ais.trans.common.server.*;

/**
 * メニュー用Bean 転送用に、PRG_MSTからカラムを抜粋
 */
public class MenuBean implements TInterfaceHasToObjectArray {

	/** テーブル名 */
	public static final String TABLE = "PRG_MST";

	/** システムコード */
	private String sYS_CODE = "";

	/** プログラムコード */
	private String pRG_CODE = "";

	/** プログラム名称 */
	private String pRG_NAME;

	/** プログラム略称 */
	private String pRG_NAME_S = "";

	/** 権限レベル */
	private Integer kEN = 9;

	/** コメント */
	private String cOM;

	/** ロードモジュール */
	private String lD_NAME;

	/** 親プログラムコード */
	private String pARENT_PRG_CODE = "";

	/** メニュー区分 */
	private boolean mENU_KBN;

	/** 表示順序 */
	private int dISP_INDEX;

	/**
	 * システムコード取得
	 * 
	 * @return システムコード
	 */
	public String getSYS_CODE() {
		return sYS_CODE;
	}

	/**
	 * システムコード設定
	 * 
	 * @param sYS_CODE システムコード
	 */
	public void setSYS_CODE(String sYS_CODE) {
		this.sYS_CODE = sYS_CODE;
	}

	/**
	 * プログラムコード取得
	 * 
	 * @return プログラムコード
	 */
	public String getPRG_CODE() {
		return pRG_CODE;
	}

	/**
	 * プログラムコード設定
	 * 
	 * @param pRG_CODE プログラムコード
	 */
	public void setPRG_CODE(String pRG_CODE) {
		this.pRG_CODE = pRG_CODE;
	}

	/**
	 * プログラム名称取得
	 * 
	 * @return プログラム名称
	 */
	public String getPRG_NAME() {
		return pRG_NAME;
	}

	/**
	 * プログラム名称設定
	 * 
	 * @param pRG_NAME プログラム名称
	 */
	public void setPRG_NAME(String pRG_NAME) {
		this.pRG_NAME = pRG_NAME;
	}

	/**
	 * プログラム略称
	 * 
	 * @return プログラム略称
	 */
	public String getPRG_NAME_S() {
		return pRG_NAME_S;
	}

	/**
	 * プログラム略称設定
	 * 
	 * @param pRG_NAME_S プログラム略称
	 */
	public void setPRG_NAME_S(String pRG_NAME_S) {
		this.pRG_NAME_S = pRG_NAME_S;
	}

	/**
	 * 権限レベル取得
	 * 
	 * @return 権限レベル
	 */
	public Integer getKEN() {
		return kEN;
	}

	/**
	 * 権限レベル設定.
	 * 
	 * @param kEN 権限レベル
	 */
	public void setKEN(Integer kEN) {
		this.kEN = (kEN == null) ? 9 : kEN.intValue();
	}

	/**
	 * コメント取得
	 * 
	 * @return コメント
	 */
	public String getCOM() {
		return cOM;
	}

	/**
	 * コメント設定
	 * 
	 * @param cOM コメント
	 */
	public void setCOM(String cOM) {
		this.cOM = cOM;
	}

	/**
	 * ロードモジュール名取得
	 * 
	 * @return ロードモジュール名
	 */
	public String getLD_NAME() {
		return lD_NAME;
	}

	/**
	 * ロードモジュール名設定
	 * 
	 * @param lD_NAME ロードモジュール名
	 */
	public void setLD_NAME(String lD_NAME) {
		this.lD_NAME = lD_NAME;
	}

	/**
	 * 親コード取得
	 * 
	 * @return 親コード
	 */
	public String getPARENT_PRG_CODE() {
		return pARENT_PRG_CODE;
	}

	/**
	 * 親コード設定
	 * 
	 * @param pARENT_PRG_CODE 親コード
	 */
	public void setPARENT_PRG_CODE(String pARENT_PRG_CODE) {
		this.pARENT_PRG_CODE = pARENT_PRG_CODE;
	}

	/**
	 * メニュー区分設定
	 * 
	 * @param isMenu true:メニュー, false:画面
	 */
	public void setMENU_KBN(boolean isMenu) {
		this.mENU_KBN = isMenu;
	}

	/**
	 * メニュー区分判定
	 * 
	 * @return true:メニュー, false:画面
	 */
	public boolean isMENU_KBN() {
		return this.mENU_KBN;
	}

	/**
	 * 表示順序取得
	 * 
	 * @return 表示順序
	 */
	public int getDISP_INDEX() {
		return dISP_INDEX;
	}

	/**
	 * 表示順序設定
	 * 
	 * @param dISP_INDEX 表示順序
	 */
	public void setDISP_INDEX(int dISP_INDEX) {
		this.dISP_INDEX = dISP_INDEX;
	}

	/**
	 * Bean内データをリスト形式で取得.
	 * 
	 * @return リスト形式データ
	 */
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(sYS_CODE);
		list.add(pRG_CODE);
		list.add(pRG_NAME);
		list.add(pRG_NAME_S);
		list.add(kEN);
		list.add(cOM);
		list.add(lD_NAME);
		list.add(pARENT_PRG_CODE);
		list.add(mENU_KBN);
		list.add(dISP_INDEX);

		return list;
	}

	/**
	 * 文字列変換
	 * 
	 * @return 文字列
	 */
	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/sYS_CODE=").append(sYS_CODE);
		buff.append("/pRG_CODE=").append(pRG_CODE);
		buff.append("/pRG_NAME=").append(pRG_NAME);
		buff.append("/pRG_NAME_S=").append(pRG_NAME_S);
		buff.append("/kEN=").append(kEN);
		buff.append("/cOM=").append(cOM);
		buff.append("/lD_NAME=").append(lD_NAME);
		buff.append("/pARENT_PRG_CODE=").append(pARENT_PRG_CODE);
		buff.append("/mENU_KBN=").append(isMENU_KBN());
		buff.append("/dISP_INDEX=").append(dISP_INDEX);
		buff.append("]");
		return buff.toString();
	}

	// 不要カラム定義(Daoの都合上定義)
	private String kAI_CODE = "";

	private String pRG_NAME_K;

	private Date sTR_DATE;

	private Date eND_DATE;

	private Date iNP_DATE;

	private Date uPD_DATE;

	private String pRG_ID;

	private String uSR_ID;

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * 会社コード
	 * 
	 * @param kAI_CODE 会社コード
	 */
	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	/**
	 * プログラム検索名称
	 * 
	 * @return プログラム検索名称
	 */
	public String getPRG_NAME_K() {
		return pRG_NAME_K;
	}

	/**
	 * プログラム検索名称
	 * 
	 * @param pRG_NAME_K プログラム検索名称
	 */
	public void setPRG_NAME_K(String pRG_NAME_K) {
		this.pRG_NAME_K = pRG_NAME_K;
	}

	/**
	 * 開始日
	 * 
	 * @return 開始日
	 */
	public Date getSTR_DATE() {
		return sTR_DATE;
	}

	/**
	 * 開始日
	 * 
	 * @param sTR_DATE 開始日
	 */
	public void setSTR_DATE(Date sTR_DATE) {
		this.sTR_DATE = sTR_DATE;
	}

	/**
	 * 終了日
	 * 
	 * @return 終了日
	 */
	public Date getEND_DATE() {
		return eND_DATE;
	}

	/**
	 * 終了日
	 * 
	 * @param eND_DATE 終了日
	 */
	public void setEND_DATE(Date eND_DATE) {
		this.eND_DATE = eND_DATE;
	}

	/**
	 * 登録日時
	 * 
	 * @return 登録日時
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * 登録日時
	 * 
	 * @param iNP_DATE 登録日時
	 */
	public void setINP_DATE(Date iNP_DATE) {
		this.iNP_DATE = iNP_DATE;
	}

	/**
	 * 更新日時
	 * 
	 * @return 更新日時
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * 更新日時
	 * 
	 * @param uPD_DATE 更新日時
	 */
	public void setUPD_DATE(Date uPD_DATE) {
		this.uPD_DATE = uPD_DATE;
	}

	/**
	 * プログラムID
	 * 
	 * @return プログラムID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * プログラムID
	 * 
	 * @param pRG_ID プログラムID
	 */
	public void setPRG_ID(String pRG_ID) {
		this.pRG_ID = pRG_ID;
	}

	/**
	 * ユーザID
	 * 
	 * @return ユーザID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ユーザID
	 * 
	 * @param uSR_ID ユーザID
	 */
	public void setUSR_ID(String uSR_ID) {
		this.uSR_ID = uSR_ID;
	}
}

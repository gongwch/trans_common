package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ログインチェックDao
 */
public interface PCI_CHK_CTLDao {

	/** Bean定義 */
	public static final Class BEAN = PCI_CHK_CTL.class;

	/**
	 * 全データ取得
	 * 
	 * @return データリスト
	 */
	public List getAllPCI_CHK_CTL();

	/** 指定されたデータの取得 */
	public static final String getPCI_CHK_CTLByKaicodeUsrid_ARGS = "KAI_CODE, USR_ID";

	/**
	 * 会社コード、ユーザIDを基にデータ取得
	 * 
	 * @param kaiCode 会社コード
	 * @param usrID ユーザID
	 * @return データ
	 */
	public PCI_CHK_CTL getPCI_CHK_CTLByKaicodeUsrid(String kaiCode, String usrID);

	/** クエリ属性 */
	public static final String getPCIListByCompanyCode_ARGS = "companyCode";

	/**
	 * 会社コードでユーザ排他リストを取得
	 * 
	 * @param companyCode
	 * @return ユーザ排他リスト
	 */
	public List<PCI_CHK_CTL> getPCIListByCompanyCode(String companyCode);

	/** ログインユーザー数取得SQL */
	public static final String getCount_SQL = "SELECT COUNT(*) FROM PCI_CHK_CTL WHERE KAI_CODE = ?";

	/**
	 * ログインユーザー数の取得
	 * 
	 * @param kaiCode 会社コード
	 * @return ログインユーザー数
	 */
	public int getCount(String kaiCode);

	/**
	 * 新規追加
	 * 
	 * @param dto
	 */
	public void insert(PCI_CHK_CTL dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(PCI_CHK_CTL dto);

	/** ログインチェック削除SQL */
	public String delete_SQL = "DELETE FROM PCI_CHK_CTL WHERE KAI_CODE = ? AND USR_ID = ?";

	/**
	 * ログインチェック削除
	 * 
	 * @param kaiCode 会社コード
	 * @param usrCode ユーザコード
	 */
	public void delete(String kaiCode, String usrCode);

}

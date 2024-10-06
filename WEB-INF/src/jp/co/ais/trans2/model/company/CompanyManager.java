package jp.co.ais.trans2.model.company;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 会社管理インターフェース。
 * 
 * @author AIS
 */
public interface CompanyManager {

	/**
	 * 指定条件に該当する会社情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社情報
	 * @throws TException
	 */
	public List<Company> get(CompanySearchCondition condition) throws TException;

	/**
	 * 指定コードに紐付く会社情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @return 指定コードに紐付く会社情報
	 * @throws TException
	 */
	public Company get(String companyCode) throws TException;

	/**
	 * 会社を登録する。
	 * 
	 * @param company 会社
	 * @throws TException
	 */
	public void entry(Company company) throws TException;

	/**
	 * 会社を修正する。
	 * 
	 * @param company 会社
	 * @throws TException
	 */
	public void modify(Company company) throws TException;

	/**
	 * 会社を削除する。
	 * 
	 * @param company 会社
	 * @throws TException
	 */
	public void delete(Company company) throws TException;

	/**
	 * 付替情報の取得
	 * 
	 * @param fromCompanyCode 付替元会社コード
	 * @param toCompanyCode 付替先会社コード
	 * @return 各会社付替情報
	 * @throws TException
	 */
	public List<TransferConfig> getTransferConfig(String fromCompanyCode, String toCompanyCode) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(CompanySearchCondition condition) throws TException;

	/**
	 * @return サーバーシステム日時
	 * @throws TException
	 */
	public Date getSystemDate() throws TException;

	/**
	 * 指定条件に該当する会社組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社組織情報
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganization(CompanyOrganizationSearchCondition condition)
		throws TException;

	/**
	 * 指定条件に該当する会社組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社組織情報(会社階層マスタ用)
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganizationData(CompanyOrganizationSearchCondition condition)
		throws TException;

	/**
	 * 会社階層マスタに登録されていない会社リスト取得
	 * 
	 * @param companyCode
	 * @param condition
	 * @return 会社階層マスタに登録されていない会社リスト
	 * @throws TException
	 */
	public List<String> getNotExistCompanyList(String companyCode, CompanyOutputCondition condition) throws TException;
}

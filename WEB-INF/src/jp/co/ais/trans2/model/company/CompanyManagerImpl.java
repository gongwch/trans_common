package jp.co.ais.trans2.model.company;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 会社管理の実装クラスです。
 * 
 * @author AIS
 */
public class CompanyManagerImpl extends TModel implements CompanyManager {

	/**
	 * 指定コードに紐付く会社情報を返します。
	 * 
	 * @param companyCode 会社コード
	 * @return 指定コードに紐付く会社情報
	 * @throws TException
	 */
	public Company get(String companyCode) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.get(companyCode);
	}

	/**
	 * 指定条件に該当する会社情報を返します。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社情報
	 * @throws TException
	 */
	public List<Company> get(CompanySearchCondition condition) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.get(condition);
	}

	/**
	 * 会社を登録する。
	 * 
	 * @param company 会社
	 * @throws TException
	 */
	public void entry(Company company) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		dao.entry(company);
	}

	/**
	 * 会社を修正する。
	 * 
	 * @param company 会社
	 * @throws TException
	 */
	public void modify(Company company) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		dao.modify(company);
	}

	/**
	 * 会社を削除する。
	 * 
	 * @param company 会社
	 * @throws TException
	 */
	public void delete(Company company) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		dao.delete(company);
	}

	/**
	 * 付替情報の取得
	 * 
	 * @param fromCompanyCode 付替元会社コード
	 * @param toCompanyCode 付替先会社コード
	 * @return 各会社付替情報
	 * @throws TException
	 */
	public List<TransferConfig> getTransferConfig(String fromCompanyCode, String toCompanyCode) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.getTransferConfig(fromCompanyCode, toCompanyCode);
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(CompanySearchCondition condition) throws TException {

		try {

			// 会社データを抽出
			List<Company> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			CompanyExcel exl = new CompanyExcel(getUser().getLanguage(), condition);
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * @return サーバーシステム日時
	 * @throws TException
	 */
	public Date getSystemDate() throws TException {
		return getNow();
	}

	/**
	 * 指定条件に該当する会社組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社組織情報
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganization(CompanyOrganizationSearchCondition condition)
		throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.getCompanyOrganization(condition);
	}

	/**
	 * 指定条件に該当する会社組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社組織情報(会社階層マスタ用)
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganizationData(CompanyOrganizationSearchCondition condition)
		throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.getCompanyOrganizationData(condition);
	}

	/**
	 * 会社階層マスタに登録されていない会社リスト取得
	 * 
	 * @param companyCode
	 * @param condition
	 * @return 会社階層マスタに登録されていない会社リスト
	 * @throws TException
	 */
	public List<String> getNotExistCompanyList(String companyCode, CompanyOutputCondition condition) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.getNotExistCompanyList(companyCode, condition);
	}
}

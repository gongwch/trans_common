package jp.co.ais.trans2.model.customer;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.check.*;

/**
 * 取引先情報マネージャ実装クラス
 * 
 * @author AIS
 */
public class CustomerManagerImpl extends TModel implements CustomerManager {

	/**
	 * Daoファクトリ
	 * 
	 * @return Dao
	 */
	protected CustomerDao getDao() {
		CustomerDao dao = (CustomerDao) getComponent(CustomerDao.class);
		return dao;
	}

	public List<Customer> get(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.get(condition);
	}

	/**
	 * 指定条件に該当する取引先情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する取引先情報
	 * @throws TException
	 */
	public List<Customer> getRef(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.getRef(condition);
	}

	/**
	 * 指定条件に該当する取引先情報を返す<br>
	 * インクリメントサーチ用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する取引先情報
	 * @throws TException
	 */
	public List<Customer> getCustomerForSearch(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.getCustomerForSearch(condition);
	}

	public void entry(Customer customer) throws TException {
		CustomerDao dao = getDao();
		dao.entry(customer);
	}

	public void modify(Customer customer) throws TException {
		CustomerDao dao = getDao();
		dao.modify(customer);
	}

	public void delete(Customer customer) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.CUSTOMER);
		condition.setCompanyCode(customer.getCompanyCode());
		condition.setCustomerCode(customer.getCode());

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		CustomerDao dao = getDao();
		dao.delete(customer);
	}

	/**
	 * 取引先の振込依頼人情報を修正する。
	 * 
	 * @param companyCode 会社コード
	 * @param customerCode 取引先コード
	 * @param clientName 振込依頼人名
	 * @throws TException
	 */
	public void modifyBankAccountClientName(String companyCode, String customerCode, String clientName)
		throws TException {

		CustomerDao dao = getDao();
		dao.modifyBankAccountClientName(companyCode, customerCode, clientName);

	}

	/**
	 * 取引先マスタ一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の取引先マスタ一覧
	 * @throws TException
	 */
	public byte[] getExcel(CustomerSearchCondition condition) throws TException {

		List<Customer> customers = get(condition);
		if (customers == null || customers.isEmpty()) {
			return null;
		}

		CustomerExcel layout = getLayout(getUser().getLanguage(), condition);
		byte[] data = layout.getExcel(customers);

		return data;

	}

	/**
	 * EXCELレイアウトファクトリ
	 * 
	 * @param language
	 * @param condition
	 * @return EXCELレイアウトファクトリ
	 */
	protected CustomerExcel getLayout(String language, CustomerSearchCondition condition) {
		return new CustomerExcel(language, condition);
	}

	/**
	 * EXCELレイアウトファクトリ(担当者一覧出力用)
	 * 
	 * @param language
	 * @return EXCELレイアウトファクトリ
	 */
	protected CustomerUsrExcel getUsrLayout(String language) {
		return new CustomerUsrExcel(language);
	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.hasDelete(condition);
	}

	/**
	 * TRI_USR_MSTの検索
	 * 
	 * @param triCode
	 * @return TRI_USR_MST情報
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MST(String triCode) throws TException {
		CustomerDao dao = getDao();
		return dao.getTRI_USR_MST(triCode);
	}

	/**
	 * TRI_USR_MSTの登録
	 * 
	 * @param list
	 * @throws TException
	 */
	public void entryTRI_USR_MST(List<CustomerUser> list) throws TException {
		CustomerDao dao = getDao();
		dao.entryTRI_USR_MST(list);
	}

	/**
	 * TRI_USR_MSTの検索（一覧出力用）
	 * 
	 * @param condition
	 * @return TRI_USR_MST情報（一覧出力用）
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MSTExcel(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.getTRI_USR_MSTExcel(condition);
	}

	/**
	 * 取引先担当者マスタ一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の取引先担当者マスタ一覧
	 * @throws TException
	 */
	public byte[] getUsrExcel(CustomerSearchCondition condition) throws TException {

		List<CustomerUser> customers = getTRI_USR_MSTExcel(condition);
		if (customers == null || customers.isEmpty()) {
			return null;
		}

		CustomerUsrExcel layout = getUsrLayout(getUser().getLanguage());
		byte[] data = layout.getExcel(customers);

		return data;

	}

}

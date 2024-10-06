package jp.co.ais.trans2.model.customer;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 取引先情報インターフェース
 * 
 * @author AIS
 */
public interface CustomerDao {

	/**
	 * 指定条件に該当する取引先情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する取引先情報
	 * @throws TException
	 */
	public List<Customer> get(CustomerSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する取引先情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する取引先情報
	 * @throws TException
	 */
	public List<Customer> getRef(CustomerSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する取引先情報を返す<br>
	 * インクリメントサーチ用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する取引先情報
	 * @throws TException
	 */
	public List<Customer> getCustomerForSearch(CustomerSearchCondition condition) throws TException;

	/**
	 * 取引先を登録する。
	 * 
	 * @param customer 取引先
	 * @throws TException
	 */
	public void entry(Customer customer) throws TException;

	/**
	 * 取引先を修正する。
	 * 
	 * @param customer 取引先
	 * @throws TException
	 */
	public void modify(Customer customer) throws TException;

	/**
	 * 取引先を削除する。
	 * 
	 * @param customer 取引先
	 * @throws TException
	 */
	public void delete(Customer customer) throws TException;

	/**
	 * 取引先の振込依頼人情報を修正する。
	 * 
	 * @param companyCode 会社コード
	 * @param customerCode 取引先コード
	 * @param clientName 振込依頼人名
	 * @throws TException
	 */
	public void modifyBankAccountClientName(String companyCode, String customerCode, String clientName)
		throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(CustomerSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する取引先担当者情報を返す
	 * 
	 * @param triCode 取引先コード
	 * @return 指定条件に該当する取引先担当者情報
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MST(String triCode) throws TException;

	/**
	 * 取引先担当者設定を登録する。
	 * 
	 * @param customerUser 取引先担当者設定
	 * @throws TException
	 */
	public void entryTRI_USR_MST(List<CustomerUser> customerUser) throws TException;

	/**
	 * 指定条件に該当する取引先担当者情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する取引先担当者情報
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MSTExcel(CustomerSearchCondition condition) throws TException;

}

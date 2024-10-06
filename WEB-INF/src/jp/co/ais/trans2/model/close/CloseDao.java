package jp.co.ais.trans2.model.close;

import jp.co.ais.trans.common.except.TException;

/**
 * 締め関連のDao
 * @author AIS
 *
 */
public interface CloseDao {

	/**
	 * 指定会社の会計締め情報を返す
	 * @param companyCode 会社コード
	 * @return 指定会社の会計締め情報
	 */
	public FiscalPeriod getFiscalPeriod(String companyCode) throws TException;

	/**
	 * 指定会社の会計締め情報を更新する
	 * @param companyCode 会社コード
	 * @param fp 会計締め情報
	 * @throws TException
	 */
	public void update(String companyCode, FiscalPeriod fp) throws TException;

}

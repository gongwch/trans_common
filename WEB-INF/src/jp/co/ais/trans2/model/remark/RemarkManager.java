package jp.co.ais.trans2.model.remark;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 摘要インターフェース。
 * 
 * @author AIS
 */
public interface RemarkManager {

	/**
	 * 指定条件に該当する摘要情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する摘要情報
	 * @throws TException
	 */
	public List<Remark> get(RemarkSearchCondition condition) throws TException;

	/**
	 * 摘要情報を登録する。
	 * 
	 * @param remark
	 * @throws TException
	 */
	public void entry(Remark remark) throws TException;

	/**
	 * 摘要情報を修正する。
	 * 
	 * @param remark
	 * @throws TException
	 */
	public void modify(Remark remark) throws TException;

	/**
	 * 摘要情報を削除する。
	 * 
	 * @param remark
	 * @throws TException
	 */
	public void delete(Remark remark) throws TException;

	/**
	 * 摘要情報(エクセル)を返す
	 * 
	 * @param condition
	 * @return 摘要情報
	 * @throws TException
	 */
	public byte[] getExcel(RemarkSearchCondition condition) throws TException;

}

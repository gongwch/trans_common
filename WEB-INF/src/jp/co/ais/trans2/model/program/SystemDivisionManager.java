package jp.co.ais.trans2.model.program;

import java.util.*;
import jp.co.ais.trans.common.except.*;

/**
 * システム区分インターフェース。
 * 
 * @author AIS
 */
public interface SystemDivisionManager {

	/**
	 * 特定のシステム区分情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @param code システム区分コード
	 * @return 特定のシステム区分情報
	 * @throws TException
	 */
	public SystemDivision get(String companyCode, String code) throws TException;

	/**
	 * 指定条件に該当するシステム区分情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するシステム区分
	 * @throws TException
	 */
	public List<SystemDivision> get(SystemDivisionSearchCondition condition) throws TException;

	/**
	 * システム区分情報を追加する。
	 * 
	 * @param bean 登録データ
	 * @throws TException
	 */
	public void entry(SystemDivision bean) throws TException;

	/**
	 * システム区分情報を更新する。
	 * 
	 * @param bean 修正データ
	 * @throws TException
	 */
	public void modify(SystemDivision bean) throws TException;

	/**
	 * システム区分情報を削除する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(SystemDivision bean) throws TException;

	/**
	 * システム区分一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式のシステム区分一覧
	 * @throws TException
	 */
	public byte[] getExcel(SystemDivisionSearchCondition condition) throws TException;

}

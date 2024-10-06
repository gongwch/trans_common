package jp.co.ais.trans2.model.security;

import java.util.List;
import jp.co.ais.trans.common.except.TException;

/**
 * プログラムロールマネージャ
 * 
 * @author AIS
 */
public interface ProgramRoleManager {

	/**
	 * 指定条件に該当するデータを返す
	 * 
	 * @param condition 検索条件
	 * @return 対象データリスト
	 * @throws TException
	 */
	public List<ProgramRole> get(ProgramRoleSearchCondition condition) throws TException;

	/**
	 * 登録する
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(ProgramRole bean) throws TException;

	/**
	 * 修正する
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(ProgramRole bean) throws TException;

	/**
	 * 削除する
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(ProgramRole bean) throws TException;

	/**
	 * プログラムロール(エクセル)を返す
	 * 
	 * @param condition 検索条件
	 * @return 対象データリスト
	 * @throws TException
	 */
	public byte[] getExcel(ProgramRoleSearchCondition condition) throws TException;

}

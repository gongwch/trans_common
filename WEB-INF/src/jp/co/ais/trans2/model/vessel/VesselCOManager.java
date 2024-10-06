package jp.co.ais.trans2.model.vessel;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 内航外航区分マネージャ
 * 
 * @author AIS
 */
public interface VesselCOManager {

	/**
	 * 指定条件に該当するデータを返す
	 * 
	 * @param isExcel
	 * @return 対象データリスト
	 * @throws TException
	 */
	public List<VesselCO> get(boolean isExcel) throws TException;

	/**
	 * 登録する
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(List<VesselCO> bean) throws TException;

	/**
	 * 削除する
	 * 
	 * @throws TException
	 */
	public void delete() throws TException;

	/**
	 * 内航外航区分(エクセル)を返す
	 * 
	 * @return 対象データリスト
	 * @throws TException
	 */
	public byte[] getExcel() throws TException;

}

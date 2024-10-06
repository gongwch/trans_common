package jp.co.ais.trans2.model.cargo;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * Cargo共通マネージャ
 */
public interface CargoManager {

	/**
	 * Cargoリストの取得
	 * 
	 * @param condition
	 * @return Cargoリスト
	 * @throws TException
	 */
	public List<Cargo> get(CargoSearchCondition condition) throws TException;

	/**
	 * Entry cargo master.
	 * 
	 * @param bean the bean
	 * @param oldBean the old bean
	 * @return the cargo master
	 * @throws TException
	 */
	public Cargo entry(Cargo bean, Cargo oldBean) throws TException;

	/**
	 * Checks if is exist cargo master.
	 * 
	 * @param kaiCODE the company code
	 * @param crgCode the cargo code
	 * @return true, if is exist cargo master
	 * @throws TException
	 */
	public boolean isExistCargoMaster(String kaiCODE, String crgCode) throws TException;

	/**
	 * Insert
	 * 
	 * @param bean the bean
	 * @throws TException the exception
	 */
	public void insert(Cargo bean) throws TException;

	/**
	 * Modify
	 * 
	 * @param bean the bean
	 * @throws TException the exception
	 */
	public void modify(Cargo bean) throws TException;

	/**
	 * Delete cargo master.
	 * 
	 * @param kaiCODE company code
	 * @param crgCd cargo code
	 * @throws TException
	 */
	public void delete(String kaiCODE, String crgCd) throws TException;

	/**
	 * Gets the excel.
	 * 
	 * @param condition the condition
	 * @return the excel
	 * @throws TException
	 */
	public byte[] getExcel(CargoSearchCondition condition) throws TException;

	/**
	 * Get by code.
	 * 
	 * @param kaiCODE company code
	 * @param crgCd cargo code
	 * @return cargo
	 * @throws TException
	 */
	public Cargo getByCode(String kaiCODE, String crgCd) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(CargoSearchCondition condition) throws TException;
}

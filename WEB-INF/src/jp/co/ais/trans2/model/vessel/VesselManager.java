package jp.co.ais.trans2.model.vessel;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * Vessel管理インターフェース
 * 
 * @author AIS
 */
public interface VesselManager {

	/**
	 * 指定条件に該当するVessel情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するVessel情報
	 * @throws TException
	 */
	public List<Vessel> get(VesselSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当するVessel情報を返す。(注意：インクリメントサーチ用情報のみ)
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するVessel情報
	 * @throws TException
	 */
	public List<Vessel> getVesselForSearch(VesselSearchCondition condition) throws TException;

	/**
	 * 指定コードに紐付くVessel情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param beanCode Vesselコード
	 * @return 指定コードに紐付くVessel情報
	 * @throws TException
	 */
	public Vessel get(String companyCode, String beanCode) throws TException;

	/**
	 * 指定条件に該当するVessel情報を返す 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するVessel
	 * @throws TException
	 */
	public List<Vessel> getRef(VesselSearchCondition condition) throws TException;

	/**
	 * Register Vessel.
	 * 
	 * @param bean
	 * @param oldBean
	 * @return the {@link Vessel}
	 * @throws TException
	 */
	public Vessel entry(Vessel bean, Vessel oldBean) throws TException;

	/**
	 * Insert vessel.
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void insert(Vessel bean) throws TException;

	/**
	 * Vesselを修正する。
	 * 
	 * @param bean Vessel
	 * @throws TException
	 */
	public void modify(Vessel bean) throws TException;

	/**
	 * Vesselを削除する。
	 * 
	 * @param bean Vessel
	 * @throws TException
	 */
	public void delete(Vessel bean) throws TException;

	/**
	 * 燃料管理テーブルを使うかどうか
	 * 
	 * @return true:存在するfalse:存在しない
	 * @throws TException
	 */
	public boolean isUseBM() throws TException;

	/**
	 * VESSELスピードマスタの取得
	 * 
	 * @param condition
	 * @return VESSELスピードマスタ
	 * @throws TException
	 */
	public List<VesselSpeed> getSpeedList(VesselSearchCondition condition) throws TException;

	/**
	 * PORT燃料消費量マスタの取得
	 * 
	 * @param condition
	 * @return PORT燃料消費量マスタ
	 * @throws TException
	 */
	public List<VesselPortCons> getPortConsList(VesselSearchCondition condition) throws TException;

	/**
	 * 海上燃料消費量マスタの取得
	 * 
	 * @param condition
	 * @return 海上燃料消費量マスタ
	 * @throws TException
	 */
	public List<VesselSeaCons> getSeaConsList(VesselSearchCondition condition) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(VesselSearchCondition condition) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(VesselSearchCondition condition) throws TException;
}

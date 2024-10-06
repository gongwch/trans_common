package jp.co.ais.trans2.model.company;

import java.util.List;
import jp.co.ais.trans.common.except.TException;

/**
 * 付替会社マネージャ
 * 
 * @author AIS
 */
public interface InterCompanyTransferManager {

	/**
	 * 指定条件に該当する部門情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する部門情報
	 * @throws TException
	 */
	public List<InterCompanyTransfer> get(InterCompanyTransferSearchCondition condition) throws TException;

	/**
	 * データを一覧に登録する。
	 * 
	 * @param interCompanyTransfer
	 * @throws TException
	 */
	public void entry(InterCompanyTransfer interCompanyTransfer) throws TException;

	/**
	 * 一覧中の選択行のデータを修正する。
	 * 
	 * @param interCompanyTransfer
	 * @throws TException
	 */
	public void modify(InterCompanyTransfer interCompanyTransfer) throws TException;

	/**
	 * 一覧中の選択行のデータを削除する。
	 * 
	 * @param interCompanyTransfer
	 * @throws TException
	 */
	public void delete(InterCompanyTransfer interCompanyTransfer) throws TException;

	/**
	 * @param transferCompanyCode
	 * @return 会社間付替マスタのデータ
	 * @throws TException
	 */
	public InterCompanyTransfer getOne(String companyCode, String transferCompanyCode) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(InterCompanyTransferSearchCondition condition) throws TException;

}

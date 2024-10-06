package jp.co.ais.trans2.model.slip;

import java.util.*;
import jp.co.ais.trans.common.except.*;

/**
 * 伝票種別インターフェース。
 * 
 * @author AIS
 */
public interface SlipTypeManager {

	/**
	 * 特定の伝票種別情報を返す
	 * @param companyCode 会社コード
	 * @param slipTypeCode 伝票種別コード
	 * @return 特定の伝票種別情報
	 * @throws TException
	 */
	public SlipType get(String companyCode, String slipTypeCode) throws TException;

	/**
	 * 指定条件に該当する伝票種別情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する伝票種別
	 * @throws TException
	 */
	public List<SlipType> get(SlipTypeSearchCondition condition) throws TException;

	/**
	 * 
	 * @param sliptype 検索条件
	 * @throws TException
	 */
	public void entry(SlipType sliptype) throws TException;

	/**
	 * 
	 * @param sliptype 検索条件
	 * @throws TException
	 */
	public void modify(SlipType sliptype) throws TException;

	/**
	 * 伝票種別情報を削除する。
	 * @param sliptype 
	 * @throws TException 
	 */
	public void delete(SlipType sliptype) throws TException;


	/**
	 * 伝票種別マスタ一覧をエクセル形式で返す
	 * @param condition 検索条件
	 * @return エクセル形式の伝票種別マスタ一覧
	 * @throws TException
	 */
	public byte[] getExcel(SlipTypeSearchCondition condition) throws TException;
}

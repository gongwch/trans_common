package jp.co.ais.trans2.model.save;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * オブジェクト保存用
 * 
 * @author AIS
 */
public interface SaveManager {

	/**
	 * 削除処理
	 * 
	 * @param base 削除対象
	 * @throws Exception
	 */
	public void deleteObject(OBJ_SAVE base) throws Exception;

	/**
	 * オブジェクト保存
	 * 
	 * @param list オブジェクトリスト
	 * @throws Exception
	 */
	public void saveObject(List<OBJ_SAVE> list) throws Exception;

	/**
	 * 保存オブジェクト呼び出し
	 * 
	 * @param compCode 会社コード
	 * @param userID ユーザID
	 * @param prgID プログラムID
	 * @param seq シーケンスNo.
	 * @return オブジェクトリスト
	 * @throws Exception
	 */
	public List<OBJ_SAVE> readObject(String compCode, String userID, String prgID, Integer seq) throws Exception;

	/**
	 * マニュアルの取得
	 * 
	 * @return マニュアルのリスト
	 * @throws TException
	 */
	public List<MANUAL_ATTACH> getManual() throws TException;

	/**
	 * マニュアル登録
	 * 
	 * @param list
	 * @throws TException
	 * @return マニュアルのリスト
	 */
	public List<MANUAL_ATTACH> entryManual(List<MANUAL_ATTACH> list) throws TException;

	/**
	 * マニュアル削除
	 * 
	 * @param list
	 * @throws TException
	 */
	public void deleteManual(List<MANUAL_ATTACH> list) throws TException;

	/**
	 * マニュアル照会
	 * 
	 * @param bean
	 * @return マニュアル
	 * @throws TException
	 */
	public byte[] drilldownManual(MANUAL_ATTACH bean) throws TException;

}

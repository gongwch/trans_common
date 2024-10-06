package jp.co.ais.trans2.model.attach.verify;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 添付ファイル検証マネージャ
 */
public interface AttachmentVerifyManager {

	/**
	 * エラーリストを取得する
	 * 
	 * @return エラーリスト
	 * @throws TException
	 */
	public List get() throws TException;

	/**
	 * エラーリストエクセルを取得する
	 * 
	 * @return エラーリストエクセル
	 * @throws TException
	 */
	public byte[] getExcel() throws TException;

}

package jp.co.ais.trans.common.client;

/**
 * 非同期通信時の処理通知用リスナー
 */
public interface TAsyncListener {

	/**
	 * 通信後呼び出し
	 * 
	 * @param isSuccess 通信処理結果 true:成功
	 */
	void after(boolean isSuccess);
}

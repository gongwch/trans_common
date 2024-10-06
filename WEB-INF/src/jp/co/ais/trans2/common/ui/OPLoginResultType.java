package jp.co.ais.trans2.common.ui;

/**
 * OPキャッシュ情報取得後のタイプ
 */
public enum OPLoginResultType {

	/** 全て更新必要 */
	NEED_UPDATE,

	/** 差分マージ必要 */
	NEED_MERGE,

	/** クリア */
	CLEAR_ALL,

	/** 処理なし */
	NO_OPERATION
}

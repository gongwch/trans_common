package jp.co.ais.trans.common.server;

import java.io.*;

/**
 * 認証状態を管理する.
 * 
 * @author ais-y numazawa
 */
public class TServerCertification extends Object implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 認証状態 true:認証 false:認証されていない
	 */
	private boolean certify = false;

	/**
	 * 認証の設定.
	 * 
	 * @param valid
	 */
	public void setCertified(boolean valid) {
		this.certify = valid;
	}

	/**
	 * 認証状態参照.
	 * 
	 * @return true:承認
	 */
	public boolean isCertified() {
		return this.certify;
	}

	// ******************************************
	// impliments sirealizatable
	// ******************************************

	/**
	 * 直列化 (reflection method)
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {

		// デフォルトの直列化プロセス
		out.defaultWriteObject();

		// 追加の直列化処理
		out.writeBoolean(this.certify);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

		// デフォルトの直列化プロセス
		in.defaultReadObject();

		// 追加の直列化処理
		this.certify = in.readBoolean();
	}
}

package jp.co.ais.trans.common.server;

import java.util.*;

/**
 * Bean通信用インターフェース(旧バージョン).<br>
 * 新しく作成するBeanは、TransferBaseまたはSerializableを利用すること
 */
public interface TInterfaceHasToObjectArray {

	/**
	 * 値をリスト表現で返す
	 * 
	 * @return リスト表現
	 */
	public List toObjectArray();
}

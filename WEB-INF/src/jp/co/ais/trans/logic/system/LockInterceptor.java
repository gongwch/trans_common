package jp.co.ais.trans.logic.system;

import java.io.*;

import org.aopalliance.intercept.*;
import org.seasar.framework.aop.interceptors.*;
import org.seasar.framework.container.*;

/**
 * 排他Interceptorの上位クラス
 */
public abstract class LockInterceptor extends AbstractInterceptor implements Serializable {

	/**
	 * ログインユーザの会社コードを取得
	 * 
	 * @param invocation Invocation
	 * @return 会社コード
	 */
	public String getCompanyCode(MethodInvocation invocation) {
		Lock lock = (Lock) invocation.getThis();
		return lock.getCompanyCode();
	}

	/**
	 * ログインユーザコードを取得
	 * 
	 * @param invocation Invocation
	 * @return ユーザコード
	 */
	public String getUserCode(MethodInvocation invocation) {
		Lock lock = (Lock) invocation.getThis();
		return lock.getUserCode();
	}

	/**
	 * 対象プログラムコードを取得
	 * 
	 * @param invocation Invocation
	 * @return プログラムコードコード
	 */
	public String getPrgCode(MethodInvocation invocation) {
		Lock lock = (Lock) invocation.getThis();
		return lock.getPrgCode();
	}

	/**
	 * コンテナを取得
	 * 
	 * @param invocation Invocation
	 * @return コンテナ
	 */
	public S2Container getContainer(MethodInvocation invocation) {
		return super.getComponentDef(invocation).getContainer();
	}
}

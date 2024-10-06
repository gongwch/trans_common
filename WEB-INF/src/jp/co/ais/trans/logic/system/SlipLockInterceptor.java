package jp.co.ais.trans.logic.system;

import java.util.*;

import org.aopalliance.intercept.*;
import org.seasar.framework.aop.interceptors.*;
import org.seasar.framework.container.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 排他Interceptorの上位クラス
 */
public abstract class SlipLockInterceptor extends AbstractInterceptor {

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
	 * 排他解除対象の伝票リストを取得
	 * 
	 * @param invocation
	 * @return 排他解除対象の伝票リスト
	 */
	public List<SlipUnlockObject> getUnlockSlipList(MethodInvocation invocation) {
		Lock lock = (Lock) invocation.getThis();
		return lock.getUnlockSlipList();
	}

	/**
	 * 排他伝票リストを追加
	 * 
	 * @param invocation
	 * @param list
	 */
	public void addSliplist(MethodInvocation invocation, List<SlipUnlockObject> list) {
		Lock lock = (Lock) invocation.getThis();
		lock.addSliplist(list);
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

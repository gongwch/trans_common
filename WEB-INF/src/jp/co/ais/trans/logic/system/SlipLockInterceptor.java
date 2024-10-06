package jp.co.ais.trans.logic.system;

import java.util.*;

import org.aopalliance.intercept.*;
import org.seasar.framework.aop.interceptors.*;
import org.seasar.framework.container.*;

import jp.co.ais.trans.master.entity.*;

/**
 * �r��Interceptor�̏�ʃN���X
 */
public abstract class SlipLockInterceptor extends AbstractInterceptor {

	/**
	 * ���O�C�����[�U�̉�ЃR�[�h���擾
	 * 
	 * @param invocation Invocation
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode(MethodInvocation invocation) {
		Lock lock = (Lock) invocation.getThis();
		return lock.getCompanyCode();
	}

	/**
	 * �r�������Ώۂ̓`�[���X�g���擾
	 * 
	 * @param invocation
	 * @return �r�������Ώۂ̓`�[���X�g
	 */
	public List<SlipUnlockObject> getUnlockSlipList(MethodInvocation invocation) {
		Lock lock = (Lock) invocation.getThis();
		return lock.getUnlockSlipList();
	}

	/**
	 * �r���`�[���X�g��ǉ�
	 * 
	 * @param invocation
	 * @param list
	 */
	public void addSliplist(MethodInvocation invocation, List<SlipUnlockObject> list) {
		Lock lock = (Lock) invocation.getThis();
		lock.addSliplist(list);
	}

	/**
	 * �R���e�i���擾
	 * 
	 * @param invocation Invocation
	 * @return �R���e�i
	 */
	public S2Container getContainer(MethodInvocation invocation) {
		return super.getComponentDef(invocation).getContainer();
	}
}

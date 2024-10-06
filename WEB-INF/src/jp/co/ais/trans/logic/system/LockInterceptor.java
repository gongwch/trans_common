package jp.co.ais.trans.logic.system;

import java.io.*;

import org.aopalliance.intercept.*;
import org.seasar.framework.aop.interceptors.*;
import org.seasar.framework.container.*;

/**
 * �r��Interceptor�̏�ʃN���X
 */
public abstract class LockInterceptor extends AbstractInterceptor implements Serializable {

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
	 * ���O�C�����[�U�R�[�h���擾
	 * 
	 * @param invocation Invocation
	 * @return ���[�U�R�[�h
	 */
	public String getUserCode(MethodInvocation invocation) {
		Lock lock = (Lock) invocation.getThis();
		return lock.getUserCode();
	}

	/**
	 * �Ώۃv���O�����R�[�h���擾
	 * 
	 * @param invocation Invocation
	 * @return �v���O�����R�[�h�R�[�h
	 */
	public String getPrgCode(MethodInvocation invocation) {
		Lock lock = (Lock) invocation.getThis();
		return lock.getPrgCode();
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

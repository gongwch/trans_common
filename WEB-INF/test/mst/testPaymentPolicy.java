package mst;

import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.payment.*;
import jp.co.ais.trans2.model.user.*;

/**
 * 
 */
public class testPaymentPolicy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SingletonS2ContainerFactory.setConfigPath("trans-commons2.dicon");
		SingletonS2ContainerFactory.init();

		PaymentPolicyManagerImpl manager = (PaymentPolicyManagerImpl) SingletonS2ContainerFactory.getContainer()
			.getComponent(PaymentPolicyManager.class);
		Company company =new Company();
		company.setCode("000");
		manager.setCompany(company);
		manager.setUser(new User());
		try {
			System.out.println(manager.get());
		} catch (TException e) {
			e.printStackTrace();
		}
	}

}

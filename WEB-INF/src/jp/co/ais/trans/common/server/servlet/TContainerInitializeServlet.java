package jp.co.ais.trans.common.server.servlet;

import jp.co.ais.trans.common.log.*;

import org.seasar.framework.beans.*;
import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.framework.exception.*;

/**
 * S2Container �����������Asingleton�𐶐�����N���X<br>
 * �������ŕK�v�ȏ������L�q����
 */
public class TContainerInitializeServlet extends S2ContainerServlet {

	/** �o�[�W����ID */
	private static final long serialVersionUID = -3760675496098560779L;

	/**
	 * constructor
	 */
	public TContainerInitializeServlet() {
		super();
	}

	/**
	 * initialization.
	 */
	public void init() {

		ServerLogger.info("*** " + this.getClass().getName() + "#init in ***");

		try {

			super.init();

		} catch (SAXRuntimeException ex) {
			// XML ��̓G���[
			ServerLogger.fatal("xml error", ex);

		} catch (IllegalPropertyRuntimeException ex) {
			// DI container component property �G���[
			ServerLogger.fatal("DI container component property error", ex);

			ServerLogger.fatal("property:" + ex.getPropertyName());

			Object[] args = ex.getArgs();
			if (args != null) {
				for (int cnt = 0; cnt < args.length; cnt++) {
					ServerLogger.fatal("args[" + cnt + "]:" + args[cnt].getClass().getName() + ","
							+ args[cnt].toString());
				}
			}

		} catch (Throwable ex) {
			// ���̑��̗�O
			ServerLogger.fatal("", ex);
			ex.printStackTrace();

		} finally {

			ServerLogger.info("*** " + this.getClass().getName() + "#init out ***");
		}
	}
}

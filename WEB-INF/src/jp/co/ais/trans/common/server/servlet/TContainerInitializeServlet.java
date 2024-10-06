package jp.co.ais.trans.common.server.servlet;

import jp.co.ais.trans.common.log.*;

import org.seasar.framework.beans.*;
import org.seasar.framework.container.servlet.S2ContainerServlet;
import org.seasar.framework.exception.*;

/**
 * S2Container を初期化し、singletonを生成するクラス<br>
 * 初期化で必要な処理を記述する
 */
public class TContainerInitializeServlet extends S2ContainerServlet {

	/** バージョンID */
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
			// XML 解析エラー
			ServerLogger.fatal("xml error", ex);

		} catch (IllegalPropertyRuntimeException ex) {
			// DI container component property エラー
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
			// その他の例外
			ServerLogger.fatal("", ex);
			ex.printStackTrace();

		} finally {

			ServerLogger.info("*** " + this.getClass().getName() + "#init out ***");
		}
	}
}

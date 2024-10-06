package jp.co.ais.trans2.common.server;

import java.util.*;

import org.aopalliance.intercept.*;
import org.seasar.framework.aop.interceptors.*;
import org.seasar.framework.log.*;

import jp.co.ais.trans.common.util.*;

/**
 * トレース表記クラス
 */
public class TTraceInterceptor extends TraceInterceptor {

	/** ログ */
	private static Logger logger_ = Logger.getLogger(TraceInterceptor.class);

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		StringBuffer buf = new StringBuffer(100);
		buf.append(getTargetClass(invocation).getName());
		buf.append("#");
		buf.append(invocation.getMethod().getName());
		buf.append("(");
		Object[] args = invocation.getArguments();
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; ++i) {
				addContent(buf, args[i]);
				buf.append(", ");
			}
			buf.setLength(buf.length() - 2);
		}
		buf.append(")");
		Object ret = null;
		Throwable cause = null;
		logger_.debug("BEGIN " + buf);
		try {
			ret = invocation.proceed(); // 処理実行
			buf.append(" : ");
			addContent(buf, ret);
		} catch (Throwable t) {
			buf.append(" Throwable:");
			buf.append(t);
			cause = t;
		}
		logger_.debug("END " + buf);
		if (cause == null) {
			return ret;
		}
		throw cause;
	}

	/**
	 * @param buf
	 * @param obj
	 */
	protected static void addContent(StringBuffer buf, Object obj) {
		if (obj != null) {
			if (obj instanceof List) {
				List list = (List) obj;
				buf.append("List:");
				buf.append(list.size());
				if (!list.isEmpty()) {
					Object first = list.get(0);
					buf.append("[");
					addContent(buf, first);
					buf.append("]");
				}

			} else if (obj instanceof Map) {
				Map map = (Map) obj;
				buf.append("Map:");
				buf.append(map.size());

			} else if (obj instanceof Object[]) {
				Object[] arr = (Object[]) obj;
				buf.append("Array:");
				buf.append(arr.length);
				if (arr.length > 0) {
					Object first = arr[0];
					buf.append("[");
					addContent(buf, first);
					buf.append("]");
				}

			} else if (obj instanceof Boolean || obj instanceof Number) {
				buf.append(obj);

			} else if (obj instanceof Date) {
				buf.append(DateUtil.toYMDHMSSSSString((Date) obj));

			} else {
				buf.append(obj.getClass().getSimpleName());
			}
		} else {
			buf.append(obj);
		}
	}
}

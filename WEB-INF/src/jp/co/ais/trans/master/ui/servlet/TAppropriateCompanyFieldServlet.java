package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 計上会社フィールドServlet
 * 
 * @author zhangbo
 */
public class TAppropriateCompanyFieldServlet extends TServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 219250234324041314L;

	/**
	 * doPost()メソッドでPOST形式で送信されたデータを処理
	 */
	public void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			// 判定用flag
			String flag = req.getParameter("flag");
			if (flag != null) {
				flag = flag.toLowerCase();
			}

			// 検索
			if ("find".equals(flag)) {
				find(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/**
	 * 検索
	 * 
	 * @param req
	 * @param resp
	 */
	private void find(HttpServletRequest req, HttpServletResponse resp) {
		// containerの初期化
		S2Container container = SingletonS2ContainerFactory.getContainer();

		AppropriateCompany param = (AppropriateCompany) super.getDtoParameter(req, AppropriateCompany.class);
		// logicの初期化
		TAppropriateCompanyLogic logic = (TAppropriateCompanyLogic) container
			.getComponent(TAppropriateCompanyLogic.class);

		// 実体の初期化
		List list = logic.conditionSearch(param);

		this.dispatchResultDtoList(req, resp, list);

	}
}

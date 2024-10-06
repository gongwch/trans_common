package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.TServletBase;

import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * リリースファイル一覧サーブレット
 */
public class MG0029ReleasedFileServlet extends TServletBase {

	/** UID */
	private static final long serialVersionUID = 3717257283354795101L;

	@Override
	/**
	 * リリースファイル一覧を取得
	 */
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();

			ReleasedFileLogic logic = (ReleasedFileLogic) container.getComponent(ReleasedFileLogic.class);
			TUserInfo userInfo = refTServerUserInfo(req);

			// サーブレットからルートフォルダを取得
			String rootPath = getServletContext().getRealPath("");

			// 実行ログリスト
			List dtoList = logic.getReleasedFileList(rootPath);

			if (dtoList == null || dtoList.isEmpty()) {
				// データがありません。
				throw new TException("W00100");
			}

			if (dtoList.size() >= 65535) {
				throw new TException("W00213");
			}

			// エクセルフォマット取得
			ReportExcelDefine red = new ReleasedFileReportExcelDefine(userInfo.getUserLanguage());

			// 会社コードをセット
			red.setKaiCode(userInfo.getCompanyCode());

			// 言語コードをセット
			red.setLangCode(userInfo.getUserLanguage());
			OutputUtil util = new OutputUtil(red, userInfo.getUserLanguage());

			// エクセル形式に変更
			util.createExcel(dtoList);
			byte[] bytes = util.getBinary();

			// バイト送信
			super.dispatchExcel(req, resp, red.getFileName(), bytes);
		} catch (Exception e) {
			handledException(e, req, resp);
		}

	}

}

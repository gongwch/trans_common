package jp.co.ais.trans.master.ui.servlet;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 会社階層マスタ画面Servlet
 * 
 * @author DXC
 */
public class OW0140CompanyHierarchicalMasterServlet extends TServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 2112305901187041314L;

	/**
	 * doPost()メソッドでPOST形式で送信されたデータを処理
	 */
	public void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {

			// 判定用flag
			String flag = req.getParameter("flag");

			// 検索1
			if ("findENVList".equals(flag)) {
				findENVList(req, resp);
			}
			// 検索2
			else if ("findEVKKaiCodeList".equals(flag)) {
				findEVKKaiCodeList(req, resp);
			}
			// 新規（複写）、編集
			else if ("insert".equals(flag)) {
				insert(req, resp);
			}
			// 変更
			else if ("update".equals(flag)) {
				update(req, resp);
			}
			// 削除
			else if ("delete".equals(flag)) {
				delete(req, resp);
			}
			// 削除
			else if ("deleteOrgCode".equals(flag)) {
				deleteOrgCode(req, resp);
			}
			// Excelリスト取得
			else if ("listOut".equals(flag)) {
				listOut(req, resp);
			}
			//
			else if ("getOrgCode".equals(flag)) {
				getOrgCode(req, resp);
			}
			//
			else if ("getSskCode".equals(flag)) {
				getSskCode(req, resp);
			}
			//
			else if ("findENVListNewSsk".equals(flag)) {
				findENVListNewSsk(req, resp);
			}
			// 会社名称取得
			else if ("findCompanyName".equals(flag)) {
				findCompanyName(req, resp);
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
	private void findENVList(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		String kaiCode = req.getParameter("kaiCode");
		String sskCode = req.getParameter("sskCode");

		dispatchResultListObject(req, resp, logic.getWithOutCom(kaiCode, sskCode));
	}

	/**
	 * @param req
	 * @param resp
	 */
	private void findEVKKaiCodeList(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		String kaiCode = req.getParameter("kaiCode");
		String sskCode = req.getParameter("sskCode");

		dispatchResultListObject(req, resp, logic.getComLvl(kaiCode, sskCode));
	}

	/**
	 * 新規
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	private void insert(HttpServletRequest req, HttpServletResponse resp) throws TException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		String prgId = req.getParameter("prgId");
		String usrId = req.getParameter("usrId");
		String kaiCode = req.getParameter("kaiCode");
		String sskCode = req.getParameter("sskCode");
		String dpkDepCode = req.getParameter("dpkDepCode");
		String dpkLvl0 = req.getParameter("dpkLvl0");

		EVK_MST dto = new EVK_MST();
		dto.setKAI_CODE(kaiCode);
		dto.setDPK_SSK(sskCode);
		dto.setDPK_DEP_CODE(dpkDepCode);
		dto.setDPK_LVL(0);
		dto.setDPK_LVL_0(dpkLvl0);
		dto.setINP_DATE(Util.getCurrentDate());
		dto.setPRG_ID(prgId);
		dto.setUSR_ID(usrId);

		logic.insert(dto);

		dispatchResultSuccess(req, resp);
	}

	/**
	 * 更新
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	private void update(HttpServletRequest req, HttpServletResponse resp) throws TException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		String[] values = req.getParameterValues("updateDate");
		String prgId = req.getParameter("prgId");
		String usrId = req.getParameter("usrId");

		logic.update(getUpdateList(values, prgId, usrId));

		dispatchResultSuccess(req, resp);
	}

	private List getUpdateList(String[] values, String prgId, String usrId) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		List list = new ArrayList();
		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			String[] array = StringUtil.toArrayFromHTMLEscapeString(value);

			EVK_MST entity = new EVK_MST();
			ENV_MST entity2 = new ENV_MST();

			if ((array[3].trim()).equals("0")) {
				entity2 = logic.getENV_MST(array[0].trim());
			}

			entity.setKAI_CODE(array[0].trim());
			entity.setDPK_SSK(array[1].trim());
			entity.setDPK_DEP_CODE(array[2].trim());
			entity.setDPK_LVL(Integer.parseInt(array[3].trim()));
			entity.setDPK_LVL_0(array[4].trim());
			entity.setDPK_LVL_1(array[5].trim());
			entity.setDPK_LVL_2(array[6].trim());
			entity.setDPK_LVL_3(array[7].trim());
			entity.setDPK_LVL_4(array[8].trim());
			entity.setDPK_LVL_5(array[9].trim());
			entity.setDPK_LVL_6(array[10].trim());
			entity.setDPK_LVL_7(array[11].trim());
			entity.setDPK_LVL_8(array[12].trim());
			entity.setDPK_LVL_9(array[13].trim());

			if (!(array[3].trim()).equals("0")) {
				try {
					entity.setSTR_DATE(DateUtil.toYMDDate(array[14].trim()));
				} catch (ParseException e) {
					entity.setSTR_DATE(null);
				}
				try {
					entity.setEND_DATE(DateUtil.toYMDDate(array[15].trim()));
				} catch (ParseException e) {
					entity.setEND_DATE(null);
				}

			} else {
				if (entity2 != null) {
					entity.setSTR_DATE(entity2.getSTR_DATE());
					entity.setEND_DATE(entity2.getEND_DATE());
				}
			}

			entity.setPRG_ID(prgId.trim());
			entity.setUSR_ID(usrId.trim());
			entity.setINP_DATE(Util.getCurrentDate());

			list.add(entity);

		}

		return list;
	}

	/**
	 * 削除
	 * 
	 * @param req
	 * @param resp
	 */
	private void deleteOrgCode(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		String kaiCode = req.getParameter("kaiCode");
		String sskCode = req.getParameter("sskCode");
		logic.delete(kaiCode, sskCode);
		dispatchResultSuccess(req, resp);
	}

	/**
	 * 削除
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws TException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);
		String kaiCode = req.getParameter("kaiCode"); // 会社コード
		String sskCode = req.getParameter("sskCode");
		String dpkDepCode = req.getParameter("dpkDepCode");

		EVK_MST dto = new EVK_MST();
		dto.setKAI_CODE(kaiCode);
		dto.setDPK_SSK(sskCode);
		dto.setDPK_DEP_CODE(dpkDepCode);

		logic.delete(dto);
		dispatchResultSuccess(req, resp);
	}

	/**
	 * Excelリスト作成
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void listOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String lang = refTServerUserInfo(req).getUserLanguage();
		String kaiCode = req.getParameter("kaiCode");
		String sskCode = req.getParameter("sskCode");

		CompanyHierarchicalMasterReport layout = new CompanyHierarchicalMasterReport(lang);

		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		List list = logic.getComLvl(kaiCode, sskCode);

		layout.createReport(list);

		byte[] bytes = layout.getBinary();

		super.dispatchExcel(req, resp, "OW0140", bytes);
	}

	/**
	 * @param req
	 * @param resp
	 */
	private void getOrgCode(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		String kaiCode = req.getParameter("kaiCode");

		dispatchResultListObject(req, resp, logic.getOrgCode(kaiCode));
	}

	/**
	 * @param req
	 * @param resp
	 * @throws TException
	 * @throws TException
	 */
	private void getSskCode(HttpServletRequest req, HttpServletResponse resp) throws TException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		String kaiCode = req.getParameter("kaiCode");
		String sskCode = req.getParameter("sskCode");

		logic.getSskCode(kaiCode, sskCode);

		dispatchResultSuccess(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 */
	private void findENVListNewSsk(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		String kaiCode = req.getParameter("kaiCode");

		dispatchResultListObject(req, resp, logic.getENVListNewSsk(kaiCode));
	}

	/**
	 * 会社名称検索
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	private void findCompanyName(HttpServletRequest req, HttpServletResponse resp) throws TException {

		String kaiCode = req.getParameter("kaiCode");

		S2Container container = SingletonS2ContainerFactory.getContainer();

		CompanyHierarchicalMasterLogic logic = (CompanyHierarchicalMasterLogic) container
			.getComponent(CompanyHierarchicalMasterLogic.class);

		ENV_MST entity = logic.getENV_MST(kaiCode);

		if (entity == null) {
			throw new TException("W00081", kaiCode);
		} else {
			dispatchResult(req, resp, "name", entity.getKAI_NAME_S());
		}
	}
}

package applet;

import java.awt.*;
import java.util.*;
import java.util.List;

import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.fw.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;

/**
 * テストアプレット
 * 
 * @author AIS
 */
public abstract class TTestApplet extends TApplet {

	/** 横 */
	protected int width = 800;

	/** 縦 */
	protected int height = 600;

	/**
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init() {
		try {
			TUIManager.setUI(this);
			EventQueue.invokeAndWait(new Runnable() {

				public void run() {
					TTestApplet.this.run();
				}
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 起動
	 */
	protected void run() {
		try {
			initContainer();
			initLogin();

			initComponents();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {

		try {
			setSize(width, height);

			TController ctrl = getController();

			ctrl.setProgramInfo(getProgramInfo());
			ctrl.start();

			getContentPane().add(ctrl.getPanel());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * TPanelCtrlBaseを返す
	 * 
	 * @return TPanelCtrlBase
	 */
	public TController getController() {
		return null;
	}

	/**
	 * コンテナ生成
	 */
	protected void initContainer() {

		try {
			String path = ResourceUtil.getResourcePath(getClass());
			path = path.replaceAll(".class", "");
			SingletonS2ContainerFactory.setConfigPath(path + ".dicon");
			SingletonS2ContainerFactory.init();

		} catch (Exception ex) {
			SingletonS2ContainerFactory.setConfigPath("trans2.dicon");
			SingletonS2ContainerFactory.init();
		}
	}

	/**
	 * 仮ログイン状態
	 * 
	 * @throws Exception
	 */
	protected void initLogin() throws Exception {
		String compCode = TClientLoginInfo.getInstance().getCompanyCode(); // 会社コード
		String userCode = TClientLoginInfo.getInstance().getUserCode(); // ユーザコード

		TLoginInfo.setCompany(getCompany(compCode));
		TLoginInfo.setUser(getUser(compCode, userCode));
	}

	/**
	 * プログラム情報
	 * 
	 * @return プログラム情報
	 */
	protected TClientProgramInfo getProgramInfo() {
		TClientProgramInfo prgInfoTmp = new TClientProgramInfo();
		prgInfoTmp.setProgramCode(getProgramID());
		prgInfoTmp.setProgramName(getProgramID());
		prgInfoTmp.setProcessLevel(0);

		return prgInfoTmp;
	}

	/**
	 * プログラムIDを返す
	 * 
	 * @return プログラムID
	 */
	protected String getProgramID() {
		return "";
	}

	/**
	 * プログラム名を返す
	 * 
	 * @return プログラム名
	 */
	protected String getProgramName() {
		return getProgramID();
	}

	/**
	 * 会社コードに紐付く会社情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @return 会社コードに紐付く会社情報
	 * @throws Exception
	 */
	protected Company getCompany(String companyCode) throws Exception {
		CompanySearchCondition condition = new CompanySearchCondition();
		condition.setCode(companyCode);
		List<Company> companyList = getCompany(condition);
		if (companyList == null || companyList.isEmpty()) {
			return null;
		}

		return companyList.get(0);
	}

	/**
	 * 検索条件に該当する会社リストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する会社リスト
	 * @throws Exception
	 */
	protected List<Company> getCompany(CompanySearchCondition condition) throws Exception {
		TContainer container = TContainerFactory.getContainer();
		CompanyManager cm = (CompanyManager) container.getComponent(CompanyManager.class);
		List<Company> list = cm.get(condition);
		List<Company> lists = new ArrayList<Company>();
		if (list != null) {
			for (Company company : list) {
				lists.add(company);
			}
		}

		return lists;
	}

	/**
	 * ユーザー情報を返す
	 * 
	 * @param companyCode
	 * @param userCode
	 * @return ユーザー情報
	 * @throws Exception
	 */
	protected User getUser(String companyCode, String userCode) throws Exception {
		TContainer container = TContainerFactory.getContainer();
		UserManager um = (UserManager) container.getComponent(UserManager.class);
		User user = um.get(companyCode, userCode);
		return user;
	}

}

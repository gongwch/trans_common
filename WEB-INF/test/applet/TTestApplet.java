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
 * �e�X�g�A�v���b�g
 * 
 * @author AIS
 */
public abstract class TTestApplet extends TApplet {

	/** �� */
	protected int width = 800;

	/** �c */
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
	 * �N��
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
	 * ��ʍ\�z
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
	 * TPanelCtrlBase��Ԃ�
	 * 
	 * @return TPanelCtrlBase
	 */
	public TController getController() {
		return null;
	}

	/**
	 * �R���e�i����
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
	 * �����O�C�����
	 * 
	 * @throws Exception
	 */
	protected void initLogin() throws Exception {
		String compCode = TClientLoginInfo.getInstance().getCompanyCode(); // ��ЃR�[�h
		String userCode = TClientLoginInfo.getInstance().getUserCode(); // ���[�U�R�[�h

		TLoginInfo.setCompany(getCompany(compCode));
		TLoginInfo.setUser(getUser(compCode, userCode));
	}

	/**
	 * �v���O�������
	 * 
	 * @return �v���O�������
	 */
	protected TClientProgramInfo getProgramInfo() {
		TClientProgramInfo prgInfoTmp = new TClientProgramInfo();
		prgInfoTmp.setProgramCode(getProgramID());
		prgInfoTmp.setProgramName(getProgramID());
		prgInfoTmp.setProcessLevel(0);

		return prgInfoTmp;
	}

	/**
	 * �v���O����ID��Ԃ�
	 * 
	 * @return �v���O����ID
	 */
	protected String getProgramID() {
		return "";
	}

	/**
	 * �v���O��������Ԃ�
	 * 
	 * @return �v���O������
	 */
	protected String getProgramName() {
		return getProgramID();
	}

	/**
	 * ��ЃR�[�h�ɕR�t����Џ���Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ��ЃR�[�h�ɕR�t����Џ��
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
	 * ���������ɊY�������Ѓ��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�������Ѓ��X�g
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
	 * ���[�U�[����Ԃ�
	 * 
	 * @param companyCode
	 * @param userCode
	 * @return ���[�U�[���
	 * @throws Exception
	 */
	protected User getUser(String companyCode, String userCode) throws Exception {
		TContainer container = TContainerFactory.getContainer();
		UserManager um = (UserManager) container.getComponent(UserManager.class);
		User user = um.get(companyCode, userCode);
		return user;
	}

}

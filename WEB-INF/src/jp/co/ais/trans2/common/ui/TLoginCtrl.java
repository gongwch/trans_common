package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���O�C���̃R���g���[���B<BR>
 * �A�v���P�[�V�����̋N���A���O�C����ʂ̐���A���O�C���̐��۔�����s���B
 * 
 * @author AIS
 */
public class TLoginCtrl extends TController {

	/** �j�����x�� */
	protected static final String[] CALENDAR_WEEK_IDS = { "C90001", "C90002", "C90003", "C90004", "C90005", "C90006",
			"C90007" };

	/** �e�L�X�g�t�B�[���h�|�b�v�A�b�v���j���[���x�� */
	protected static final String[] TXT_POPUP_IDS = { "C90008", "C90009", "C90010", "C90011" };

	/**
	 * �X�v���b�h�V�[�g�|�b�v�A�b�v���j���[���x�� C11278 �\���ݒ�N���A C11438 ����������(�w�b�_�[�ƒl) C11439 ����������(�w�b�_�[) C11440 ����������(�l)
	 */
	protected static final String[] TBL_POPUP_IDS = { "C11278", "C11438", "C11439", "C11440" };

	/** ���O�C����� */
	protected TLogin loginView = null;

	/** true:�O����͒l�\�� */
	protected static final boolean isLoadPrevInputted = ClientConfig.isFlagOn("trans.login.default.prev");

	/** true:�������b�Z�[�W���\�� */
	protected static final boolean isShowProgressBar = ClientConfig.isFlagOn("trans.login.show.progress.bar");

	/** ���O�C��Servlet�ւ̃A�N�Z�X���ǂ��� */
	protected boolean isLoginServlet;

	/** �A�C�R�� */
	protected static BufferedImage icon = null;

	/** PDF���ڈ�����邩�ǂ����Atrue:PDF���ڈ������ */
	protected static final boolean isDirectPrint = ClientConfig.isFlagOn("trans.pdf.direct.print");

	/** ���O�C���L�����Z�����p���邩�ǂ����Atrue:���O�C���L�����Z�����p */
	protected static final boolean isUseLoginCancel = ClientConfig.isFlagOn("trans.use.login.cancel");

	/** ��ЌŒ�y�є�\���Atrue:��ЌŒ�y�є�\�� */
	protected static final boolean isNotShowCompany = ClientConfig.isFlagOn("trans.not.show.company");

	/** �Œ��ЃR�[�h */
	protected String fixedCompanyCode;

	/** �������t���O */
	protected boolean processing = false;

	/**
	 * �v���O�����G���g���|�C���g<BR>
	 * �A�v���P�[�V�������N������B
	 */
	@Override
	public void start() {

		// UI�̐ݒ�
		try {
			// L&F ������
			TUIManager.setLookAndFeel(LookAndFeelType.WINDOWS.value);

			// ���O�C����ʐ���
			loginView = createLoginView();

			// ���O�C����ʏ�����
			initLoginView();

			TUIManager.setUI(loginView.btnLogin, LookAndFeelType.MINT, LookAndFeelColor.White);
			TUIManager.setUI(loginView.btnClear, LookAndFeelType.MINT, LookAndFeelColor.White);
			TUIManager.setUI(loginView.btnClose, LookAndFeelType.MINT, LookAndFeelColor.White);

			if (isShowProgressBar) {
				TUIManager.setUI(loginView.progressBar, LookAndFeelType.MINT, LookAndFeelColor.White);
			} else {
				loginView.remove(loginView.progressBar);
			}

			// ���O�C����ʕ\��
			loginView.setLocationRelativeTo(null);
			loginView.setVisible(true);

			// �f�t�H���g�l�ݒ�
			setDefaultLoginInfo();

		} catch (Exception e) {
			errorHandler(loginView, e);
		}

	}

	/**
	 * ���O�C����ʂ�����������
	 */
	protected void initLoginView() {

		if (Util.isNullOrEmpty(TController.jarVersion)) {
			TController.initClientVersion();
		}

		if (icon == null) {
			initIcon();
		}

		if (icon != null) {
			loginView.setIconImage(icon);
		}

		// ���O�C����ʏ�����
		loginView.init();

		if (isNotShowCompany) {
			loginView.txCompanyCode.setVisible(false);
		}

		// ���O�C����ʂ̃C�x���g��`
		initLoginViewEvent();

		// �^�C�g��
		loginView.setTitle(ClientConfig.getTitle());

		loginView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// GlassPane
		loginView.setGlassPane(new LockingGlassPane());
		loginView.getGlassPane().setVisible(false);
	}

	/**
	 * 
	 */
	protected void initIcon() {
		try {
			icon = ResourceUtil.getImage("images/" + ClientConfig.getProperty("favicon.file.name"));
		} catch (Throwable ex) {
			// �G���[�Ȃ�
		}
	}

	/**
	 * �f�t�H���g�l�ݒ�
	 */
	protected void setDefaultLoginInfo() {
		String companyCode = "";
		String userCode = "";

		if (isLoadPrevInputted) {
			// �O����͒l�\��
			User user = getLoginInfo();
			if (user != null) {
				companyCode = user.getCompanyCode();
				userCode = user.getCode();
			}
		} else {

			try {
				// �v���p�e�B�[�t�@�C���ݒ�l
				companyCode = ClientConfig.getProperty("trans.fixed.login.company.code");
			} catch (Exception e) {
				// �G���[�Ȃ�
			}
		}

		if (isNotShowCompany) {
			// ��ЃR�[�h��\��

			fixedCompanyCode = null;

			try {
				// �v���p�e�B�[�t�@�C���ݒ�l
				fixedCompanyCode = ClientConfig.getProperty("trans.fixed.login.company.code");
			} catch (Exception e) {
				// �G���[�Ȃ�
			}

			if (!Util.isNullOrEmpty(fixedCompanyCode)) {
				// �Œ��ЃR�[�h�w��ς�
				companyCode = fixedCompanyCode;
				loginView.txCompanyCode.setVisible(false);
			} else {
				loginView.txCompanyCode.setVisible(true);
			}
		}

		loginView.txCompanyCode.setValue(companyCode);
		loginView.txUserCode.setValue(userCode);

		// �t�H�[�J�X�ݒ�
		if (!Util.isNullOrEmpty(companyCode)) {
			if (!Util.isNullOrEmpty(userCode)) {
				loginView.txPassword.requestFocus();
			} else {
				loginView.txUserCode.requestFocus();
			}
		} else {
			loginView.txCompanyCode.requestFocus();
		}

		// �������
		if (ClientConfig.isFlagOn("trans.login.show.title")) {
			Color fontColor = loginView.lblRemark.getForeground();

			try {
				fontColor = Util.toColor(StringUtil.split(ClientConfig.getProperty("login.title.fontcolor"), ","));
			} catch (Exception ex) {
				//
			}

			loginView.lblRemark.setText(ClientConfig.getTitle());
			loginView.lblRemark.setForeground(fontColor);
			loginView.lblRemark.setVisible(true);
		} else {
			loginView.lblRemark.setVisible(false);
		}

	}

	/**
	 * ���O�C����ʂ̃C�x���g��`
	 */
	protected void initLoginViewEvent() {

		// ���O�C������
		loginView.btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				loginView.btnLogin.setEnabled(false);
				btnLogin_Click();
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				loginView.btnLogin.setEnabled(true);
			}
		});

		// �N���A����
		loginView.btnClear.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClear_Click();
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// ���鉟��
		loginView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * ���O�C����ʂ̃t�@�N�g��
	 * 
	 * @return ���O�C�����
	 */
	protected TLogin createLoginView() {
		return new TLogin();
	}

	/**
	 * [���O�C��]�{�^������
	 */
	protected void btnLogin_Click() {

		try {

			if (processing) {
				return;
			}

			processing = true;

			// ���̓`�F�b�N
			if (!isInputCorrect()) {
				processing = false;
				return;
			}

			// ���O�C������
			login();

		} catch (Exception e) {
			errorHandler(loginView, e);
		}
	}

	/**
	 * ���O�C������
	 */
	protected void login() {

		int processCount = 12;

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				boolean isStarted = false;

				try {

					// �����v
					loginView.getGlassPane().setVisible(true);

					if (isShowProgressBar) {
						loginView.progressBar.setVisible(true);
					}

					{// 1
						setProgressText("Session ID intial...");// setProgressValue(i++);

						// ���[�U�[�F��
						try {
							String sessionID = authenticateUser(loginView.txCompanyCode.getValue(),
								loginView.txUserCode.getValue(), loginView.txPassword.getValue());

							// ���O�A�E�g�ŃZ�b�V�����J���l��
							TClientLoginInfo.setSessionID(sessionID);

						} catch (TException e) {
							showMessage(loginView, e.getMessageID());
							return;
						}
					}

					User user = null;

					{// 2
						setProgressText("User initial...");// setProgressValue(i++);

						// ���[�U�[���擾
						user = getUser(loginView.txCompanyCode.getValue(), loginView.txUserCode.getValue());

						if (user == null) {
							showMessage(loginView, "Error:Can not find User");
							return;
						}
					}

					{// 3
						setProgressText("User info setting...");// setProgressValue(i++);

						if (isDirectPrint) {
							// ���ڈ���̏ꍇ�A�v�����^�̖��̎擾
							String printerName = getPrinterName(loginView.txCompanyCode.getValue(),
								loginView.txUserCode.getValue());
							user.setPrinterName(printerName);
						}

						TLoginInfo.setUser(user);
					}

					{// 4
						setProgressText("Language initial...");// setProgressValue(i++);

						// ������Ή�
						String lang = user.getLanguage();
						TClientLoginInfo.getInstance().setUserLanguage(lang);

						TUIManager.setUILocale(TClientLoginInfo.getInstance().getUserLocale());

						// �e���i���P��
						TCalendar.setDayOfWeekWords(getWordList(CALENDAR_WEEK_IDS));
						TGuiUtil.setLightPopupMenuWords(getWordList(TXT_POPUP_IDS));
						TTable.setLightPopupMenuWords(getWordList(TBL_POPUP_IDS));
					}

					Company company = null;

					{// 5
						setProgressText("Company initial...");// setProgressValue(i++);

						// ��Џ��擾
						company = getCompany(loginView.txCompanyCode.getValue());

						if (company == null) {
							showMessage(loginView, "Error:Can not find Company");
							return;
						}
						TLoginInfo.setCompany(company);
					}

					{ // 7
						setProgressText("Client info initial...");// setProgressValue(i++);

						// ���o�[�W�����p
						TClientLoginInfo.getInstance().setCompanyCode(company.getCode());
						TClientLoginInfo.getInstance().setUserCode(user.getCode());

						if (ClientConfig.isWeb()) {
							TClientLoginInfo.reflesh();
						}
					}

					{// 8
						setProgressText("Cancel exclude...");// setProgressValue(i++);

						// �p�X���[�h�L�����ԃ`�F�b�N
						if (!checkPassword(company, user)) {
							// �r�����������Ă���A�������f
							request(UserExclusiveManager.class, "cancelExclude", company.getCode(), user.getCode());
							return;
						}
					}

					// �X�^�C���K�p (�����Look and Feel�̐ݒ�)
					final LookAndFeelType type;
					final LookAndFeelColor color;

					{// 9
						setProgressText("Change user setting...");// setProgressValue(i++);

						if (Util.isNullOrEmpty(user.getLfName())) {
							// ���[�U�o�^����Ă��Ȃ��ꍇ�A�f�t�H���g�X�^�C���K�p
							String style = ClientConfig.getSystemStyle();
							type = LookAndFeelType.getStyle(style);
							color = LookAndFeelColor.White;

						} else {
							type = LookAndFeelType.get(user.getLfName());
							color = LookAndFeelColor.get(user.getLfColorType());
						}

						SwingUtilities.invokeLater(new Runnable() {

							public void run() {
								// �\���ؑ�
								try {
									TUIManager.setUI(loginView, type, color);
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						});
					}

					{// 10
						setProgressText("UI updating...");// setProgressValue(i++);

						// �\���ؑ�
						TUIManager.setMenuType(user.getMenuType());

						// �O����͒l�\�����[�h�̏ꍇ�A���O�C�����ۑ�
						if (isLoadPrevInputted) {
							saveLoginInfo(company.getCode(), user.getCode());
						}

					}

					{// 11
						// �֘A���̏�����
						initInvoice();
					}

					{// 12
						setProgressText("Login initial...");// setProgressValue(i++);

						// ����������
						doInit();
					}

					{// 13
						setProgressText("Loading...");// setProgressValue(i++);

						// �O�����h���j���[�N��
						TMainCtrl ctrl = createTMainCtrl();

						// �A�C�R���ݒ�
						ctrl.setIcon(icon);

						// �J�n
						ctrl.start();
					}

					// ���O�C����ʂ����
					loginView.setVisible(false);

					{// 14
						setProgressText("Login completed.");// setProgressValue(i++);

						// ���O�C�����O�L�^
						log(getCompany(), getUser(), null, "Login");
					}

					isStarted = true;
				} catch (Exception e) {
					errorHandler(e);
				} finally {

					processing = false;

					if (!isStarted) {
						// �N�����s�A���O�C����ʍĕ\��
						loginView.setVisible(true);
					}

					// �������f
					loginView.progressBar.setVisible(false);

					loginView.getGlassPane().setVisible(false);
				}
			}

		});

		loginView.progressBar.setMaximum(processCount);
		thread.start();

		if (!loginView.isVisible()) {
			loginView.dispose();
		} else {
			loginView.btnLogin.requestFocus();
		}
	}

	/**
	 * �v���O���X�o�[���ݒl�{�P�A�\�������ݒ�.
	 * 
	 * @param str �i���o�[�̌��ݕ\������
	 */
	public void setProgressText(String str) {
		loginView.progressBar.setValue(loginView.progressBar.getValue() + 1);
		loginView.progressBar.setString(str);
	}

	/**
	 * @throws Exception
	 */
	public static void doInit() throws Exception {
		// ���O�C�����ɏ�������
		List<Class> clzList = getLoginInitialClasses();
		if (!clzList.isEmpty()) {
			// ������������
			System.out.println("login initial");

			for (Class clz : clzList) {
				Object obj = clz.newInstance();
				if (obj instanceof TLoginInitialInterface) {
					TLoginInitialInterface clazz = (TLoginInitialInterface) obj;
					System.out.println(clazz.getName());

					// ����������
					clazz.init();
				}
			}
		}
	}

	/**
	 * @return ���O�C���������N���X�ꗗ�擾
	 */
	protected static List<Class> getLoginInitialClasses() {
		String k = "trans.login.init.class.";
		List<Class> list = getInitialClasses(k);
		return list;
	}

	/**
	 * ���O�C��/Main���
	 * 
	 * @param k �L�[
	 * @return �������N���X�ꗗ�擾
	 */
	public static List<Class> getInitialClasses(String k) {

		List<Class> list = new ArrayList<Class>();

		try {
			for (int i = 1; i <= 10; i++) {
				String key = k + i;
				String className = ClientConfig.getProperty(key);
				if (!Util.isNullOrEmpty(className)) {
					Class clazz = Class.forName(className);
					list.add(clazz);
				}
			}

		} catch (Throwable ex) {
			// �G���[�Ȃ��A�������f
		}

		return list;
	}

	/**
	 * ���C���R���g���[������
	 * 
	 * @return ���C���R���g���[��
	 */
	protected TMainCtrl createTMainCtrl() {
		if (ClientConfig.isFlagOn("trans.bi.use")) {
			return new TBIMainCtrl();
		}
		return new TMainCtrl();
	}

	/**
	 * [�N���A]�{�^������
	 */
	protected void btnClear_Click() {

		try {

			// �t�B�[���h���N���A����
			if (loginView.txCompanyCode.isVisible()) {
				loginView.txCompanyCode.setValue(null);
			} else {
				loginView.txCompanyCode.setValue(fixedCompanyCode);
			}
			loginView.txUserCode.setValue(null);
			loginView.txPassword.setValue(null);

		} catch (Exception e) {
			errorHandler(loginView, e);
		}

	}

	/**
	 * [����]�{�^������
	 */
	protected void btnClose_Click() {

		try {
			loginView.setVisible(false);
			loginView.dispose();
			System.exit(0);
		} catch (Exception e) {
			errorHandler(loginView, e);
		}

	}

	/**
	 * ���͂�����������Ԃ�
	 * 
	 * @return true(����) / false(�G���[)
	 */
	protected boolean isInputCorrect() {

		// ��ЃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(loginView.txCompanyCode.getValue())) {
			showMessage(loginView, "Please Input All Items");
			loginView.txCompanyCode.requestFocus();
			return false;
		}

		// ���[�U�[�R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(loginView.txUserCode.getValue())) {
			showMessage(loginView, "Please Input All Items");
			loginView.txUserCode.requestFocus();
			return false;
		}

		// �p�X���[�h�`�F�b�N
		if (Util.isNullOrEmpty(loginView.txPassword.getValue())) {
			showMessage(loginView, "Please Input All Items");
			loginView.txPassword.requestFocus();
			return false;
		}

		if (isUseLoginCancel && ClientConfig.isWeb()) {
			// ������������

			String companyCode = loginView.txCompanyCode.getValue();
			String userCode = loginView.txUserCode.getValue();
			String password = loginView.txPassword.getValue();

			try {
				UserExclusive userExclusive = (UserExclusive) request(null, "canEntry", companyCode, userCode, password);

				if (UserExclusive.Locked != userExclusive) {
					// �����Ȃ�
					return true;
				}

				// ���b�N���ꂽ�ꍇ�A���������m�F���b�Z�[�W�o��
				if (showConfirmMessage("Q00097")) {
					request(null, "cancelExclude", companyCode, userCode);
					return true;
				} else {
					return false;
				}

			} catch (TException e) {
				// �����Ȃ�
				return true;
			}
		}

		return true;

	}

	/**
	 * ���[�U�[�F��
	 * 
	 * @param companyCode
	 * @param userCode
	 * @param password
	 * @return �Z�b�V����ID
	 * @throws TWarningException
	 * @throws TException
	 */
	protected String authenticateUser(String companyCode, String userCode, String password) throws TWarningException,
		TException {

		if (ClientConfig.isWeb()) {
			return (String) this.request(null, "authenticateUser", companyCode, userCode, password);
		}

		this.request(UserAuthenticater.class, "authenticateUser", companyCode, userCode, password);

		return "";
	}

	/**
	 * ���O�C�����[�U�[����Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @return ���O�C�����[�U�[���
	 * @throws TException
	 */
	protected User getUser(String companyCode, String userCode) throws TException {
		return (User) request(UserManager.class, "get", companyCode, userCode);
	}

	/**
	 * �v�����^���̂�Ԃ�(�v�����^�ݒ肪����ꍇ)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @return �v�����^����
	 * @throws TException
	 */
	protected String getPrinterName(String companyCode, String userCode) throws TException {
		return (String) request(UserManager.class, "getPrinterName", companyCode, userCode);
	}

	/**
	 * ���O�C����Џ���Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ���O�C����Џ��
	 * @throws TException
	 */
	protected Company getCompany(String companyCode) throws TException {
		return (Company) super.request(CompanyManager.class, "get", companyCode);
	}

	/**
	 * @see TController#request(Class, String, Object[])
	 */
	@Override
	public Object request(Class cls, String methodName, Object... arg) throws TException {
		isLoginServlet = cls == null;
		return super.request(cls, methodName, arg);
	}

	/**
	 * �ʐM��Servlet��
	 * 
	 * @return Servlet��
	 */
	@Override
	protected String getServletName() {
		return isLoginServlet ? "TLoginServlet" : super.getServletName();
	}

	/**
	 * �p�X���[�h�`�F�b�N
	 * 
	 * @param company
	 * @param user
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean checkPassword(Company company, User user) throws TException {

		// �L�����ԃ`�F�b�N
		try {

			request(PasswordManager.class, "isInTerm", company, user);

		} catch (TPasswordTermComeThroughException e) {

			int remainDays = e.getDaysBeforePasswordTermComeThrough();

			// �����������̏ꍇ�A�m�F���b�Z�[�W
			if (remainDays > 0) {

				if (showConfirmMessage(loginView, "Q90003", Integer.toString(remainDays))) {

					// �p�X���[�h�X�V
					TPasswordCtrl ctrl = createPasswordCtrl();
					ctrl.start();

				}

				// ��������
			} else {

				showMessage(loginView, "I00026");

				// �p�X���[�h�X�V
				TPasswordCtrl ctrl = createPasswordCtrl();
				ctrl.start();

				if (!ctrl.isPasswordChanged()) {
					return false;
				}
			}

		} catch (TException e) {
			throw e;
		}

		return true;

	}

	/**
	 * �p�X���[�h�ύX�R���g���[������
	 * 
	 * @return �p�X���[�h�ύX�R���g���[��
	 */
	protected TPasswordCtrl createPasswordCtrl() {
		return new TPasswordCtrl(loginView);
	}

	/**
	 * ���O�C�����擾
	 * 
	 * @return ���O�C�����
	 */
	protected User getLoginInfo() {
		String path = SystemUtil.getAisSettingDir();
		User user = (User) FileUtil.getObject(path + File.separator + getSaveKey());
		return user;
	}

	/**
	 * ���O�C�����ۑ�
	 * 
	 * @param companyCode
	 * @param userCode
	 */
	protected void saveLoginInfo(String companyCode, String userCode) {

		User user = new User();
		user.setCompanyCode(companyCode);
		user.setCode(userCode);

		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(user, path + File.separator + getSaveKey());
	}

	/**
	 * �C���{�C�X���x�Ή��֘A���̏�����
	 * 
	 * @throws TException
	 */
	protected void initInvoice() throws TException {
		if (TLoginInfo.getCompany() == null) {
			return;
		}
		if (!TLoginInfo.getCompany().isCMP_INV_CHK_FLG()) {
			// �C���{�C�X���x���g�p
			return;
		}
		// ���O�C����Ђ̏��擾
		String companyCode = TLoginInfo.getCompany().getCode();
		{
			// �����d��Ȗ�
			int[] types = new int[] { AutoJornalAccountType.PAY_TAX.value, AutoJornalAccountType.RECEIVE_TAX.value };
			List<AutoJornalAccount> list = (List<AutoJornalAccount>) request(SlipManager.class,
				"getAutoJornalAccounts", companyCode, types);
			Map<Integer, AutoJornalAccount> map = new LinkedHashMap<Integer, AutoJornalAccount>();
			if (list != null) {
				for (AutoJornalAccount bean : list) {
					map.put(bean.getKind(), bean);
				}
			}
			TLoginInfo.setAutoJournalMap(map);
		}
	}

	/**
	 * �ۑ��L�[�̎擾
	 * 
	 * @return �ۑ��L�[
	 */
	protected String getSaveKey() {

		return "login_" + getClientSaveKey() + ".log";
	}

	/**
	 * @return �N���C�A���g�֕ێ����鎞�A���Y���i�̎��ʎq
	 */
	public static String getClientSaveKey() {
		String url = jp.co.ais.trans.common.config.ClientConfig.getBaseAddress();
		url = url.replaceAll("/", "_");
		url = url.replaceAll(":", "_");
		url = url.replaceAll("\\\\", "");

		if (Util.isNullOrEmpty(url)) {
			url = ClientConfig.getTitle();
		}

		return url;
	}

}

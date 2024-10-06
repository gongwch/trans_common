package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import sun.applet.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.client.http.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.server.dao.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���C���A�v���b�g�R���g���[��
 * 
 * @author mizoguchi
 */
public class TAppletMainCtrl extends TAppletClientBase implements TreeSelectionListener, ChangeListener {

	/** �}���`�E�B���h�E�N������� */
	private static final int MAX_WINDOW = ClientConfig.getMaxWindowCount();

	/** �j�����x�� */
	private static final String[] CALENDAR_WEEK_IDS = { "C90001", "C90002", "C90003", "C90004", "C90005", "C90006",
			"C90007" };

	/** �|�b�v�A�b�v���j���[���x�� */
	private static final String[] TXT_POPUP_IDS = { "C90008", "C90009", "C90010", "C90011" };

	/** �^�u�̕��я�(�V�X�e���敪�R�[�h�̃��X�g) */
	protected static List<String> menuTabOrderList = Arrays.asList(ClientConfig.getMenuTabOrder());

	/** �v���O�����؂�ւ��ʒm�p */
	protected TRequestProgram prgRequest = new TRequestProgram(this);

	/** ���C���A�v���b�g */
	protected TAppletMain appMain;

	/** �I�𒆂̃^�uNo */
	protected int tabNum = -1;

	/** �\�����Ɩ��p�l�� */
	protected JPanel currentPanel = null;

	/** �t���[���ŋN�����̃v���O�����R�[�h */
	protected String runFramePrgCode = "";

	/** �}���`�E�B���h�E���[�h���ǂ��� */
	private boolean isMultiWindowMode = MAX_WINDOW > 0;

	/** �E�B���h�E�ŋN�����̃v���O�����R�[�h */
	protected Map<String, Frame> runWindowPrograms = new TreeMap<String, Frame>();

	/** Tree�I�𒆂̃^�uNo */
	protected int runFrameTabNum;

	/** Tree�I�𒆂̃m�[�h */
	protected TreePath runFrameTreePath;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param appMain ���C��Applet
	 */
	public TAppletMainCtrl(TAppletMain appMain) {
		this.appMain = appMain;
	}

	/**
	 * �ʃE�B���h�E�̉�ʃT�C�Y
	 * 
	 * @return �T�C�Y
	 */
	protected Dimension getWindowSize() {
		// ���C����ʃT�C�Y����擾
		Dimension dim = appMain.getSize();
		return new Dimension(dim.width - appMain.menuWidthSize + 10, dim.height + 30);
	}

	/**
	 * ����������.
	 * 
	 * @param sid �Z�b�V����ID
	 * @param kaiCode ���O�C����ЃR�[�h
	 * @param usrCode ���O�C�����[�U�R�[�h
	 */
	public void init(String sid, String kaiCode, String usrCode) {
		try {

			// �J���p
			if (Util.isNullOrEmpty(kaiCode)) {
				kaiCode = TClientLoginInfo.getInstance().getCompanyCode();
			}
			if (Util.isNullOrEmpty(usrCode)) {
				usrCode = TClientLoginInfo.getInstance().getUserCode();
			}

			// ���[�U�[���̕ێ�
			TClientLoginInfo.setSessionID(sid);
			TClientLoginInfo.getInstance().setCompanyCode(kaiCode);
			TClientLoginInfo.getInstance().setUserCode(usrCode);

			TClientLoginInfo.reflesh();

			// ���O�C���̒ʒm(�Z�b�V������IP�A�h���X�ێ��ׁ̈A���[�U���\�z�̌�)
			super.addSendValues("flag", "noticeLogin");
			if (!super.request(ClientConfig.getRootDirectory() + "/login")) {
				errorHandler(appMain);
			}

			// ��ʃf�t�H���g�ݒ�
			TUIManager.setUILocale(TClientLoginInfo.getInstance().getUserLocale());
			JOptionPane.setRootFrame(this.appMain.getParentFrame());

			// �J�����_�[�̗j���ݒ�
			String lang = TClientLoginInfo.getInstance().getUserLanguage();
			TCalendar.setDayOfWeekWords(MessageUtil.getWordList(lang, CALENDAR_WEEK_IDS));
			TGuiUtil.setLightPopupMenuWords(MessageUtil.getWordList(lang, TXT_POPUP_IDS));

			// ���j���[��
			if (ClientConfig.isMenuSplit()) {
				this.appMain.initComponentsSplit(isMultiWindowMode);

			} else {
				this.appMain.initComponents(isMultiWindowMode);
			}

			this.makeMenuTree(appMain.tabbedPane);

			this.setIEFrameWindowListener();

			// �^�u����A�^�u�ړ����[�v��IE�ɐ����n���Ȃ��悤�ɂ��邽�߂ɕK�v�B
			this.appMain.setFocusCycleRoot(true);

		} catch (Exception e) {
			errorHandler(this.appMain, e, "E00009");
		}
	}

	/**
	 * �c���[�ɕK�v�ȃf�[�^�̎擾
	 * 
	 * @param tabbedPane ���f��̃^�u�p�l��
	 * @throws IOException �f�[�^�擾���s
	 */
	protected void makeMenuTree(JTabbedPane tabbedPane) throws IOException {

		// ���j���[���N�G�X�g
		TRequestMenuManager menuRequest = new TRequestMenuManager(this);

		TUserInfo userInfo = TClientLoginInfo.getInstance();
		if (!menuRequest.request(userInfo.getCompanyCode(), userInfo.getUserCode())) {
			errorHandler(appMain);
			return;
		}

		// �T�u�V�X�e�����ɕ�����
		Map<String, List> sysMap = new TreeMap<String, List>();

		List list = menuRequest.getMenuList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			MenuBean element = (MenuBean) iter.next();
			String sysCode = element.getSYS_CODE();
			if (!sysMap.containsKey(sysCode)) {
				sysMap.put(sysCode, new LinkedList<Object>());
			}

			sysMap.get(sysCode).add(element);
		}

		// �T�u�V�X�e���̃^�u���Ή�
		Object[] objs = new Object[menuTabOrderList.size()];

		// �T�u�V�X�e������Tree���\�z
		for (Iterator iter = sysMap.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			List menuList = sysMap.get(key);

			MenuTreeNode root = new MenuTreeNode();

			// ���[�g�R�[�h�𔻕�
			MenuBean[] parents = this.getRootMenus(menuList);

			// ���[�g�R�[�h�ȉ���Tree���\�z
			for (int i = 0; i < parents.length; i++) {
				MenuBean parent = parents[i];

				MenuTreeNode category = new MenuTreeNode(parent.getPRG_CODE(), parent.getPRG_NAME(), parent.getCOM());
				makeMenuNode(category, menuList);

				root.add(category);

				// �v���O�����̖������j���[���폜
				removeNotChildNode(root);
			}

			int index = menuTabOrderList.indexOf(key);

			if (index != -1) {
				objs[index] = new Object[] { key, root };
			}
		}

		// �^�u�����l����AddTab
		for (Object object : objs) {
			if (object == null) {
				continue;
			}
			Object[] keyRoot = (Object[]) object;

			TreeNode root = (TreeNode) keyRoot[1];

			if (root.getChildCount() != 0) {
				addTab(tabbedPane, (String) keyRoot[0], root);
			}
		}
	}

	/**
	 * Tree�\�z
	 * 
	 * @param parent �e�R�[�h
	 * @param menuList ���l�^
	 */
	protected void makeMenuNode(MenuTreeNode parent, List<MenuBean> menuList) {

		MenuBean[] children = getChildrenMenus(parent.getProgramCode(), menuList);
		for (int i = 0; i < children.length; i++) {
			MenuBean child = children[i];

			String panelName = child.getLD_NAME();

			MenuTreeNode item;
			if (Util.isNullOrEmpty(panelName)) {
				item = new MenuTreeNode(child.getPRG_CODE(), child.getPRG_NAME(), child.getCOM());
			} else {
				item = new MenuTreeNode(child.getPRG_CODE(), child.getPRG_NAME(), child.getCOM(), panelName, child
					.getKEN());
			}

			if (parent.isMenu) {
				makeMenuNode(item, menuList);
			}

			parent.add(item);
		}
	}

	/**
	 * ���j���[�Ŏq�m�[�h�������Ȃ��m�[�h���폜����
	 * 
	 * @param node �Ώۃm�[�h
	 */
	protected void removeNotChildNode(MenuTreeNode node) {

		int count = node.getChildCount();
		for (int i = count - 1; i >= 0; i--) {
			MenuTreeNode child = (MenuTreeNode) node.getChildAt(i);

			if (!child.isMenu) {
				continue;
			}

			removeNotChildNode(child);

			if (child.getChildCount() == 0) {
				node.remove(child);
			}
		}
	}

	/**
	 * ���X�g�ォ�烋�[�g�m�[�h�v�f�����𒊏o����
	 * 
	 * @param menuList �Ώۃ��X�g
	 * @return ���[�g�̃v���O�����R�[�h�z��
	 */
	protected MenuBean[] getRootMenus(List menuList) {
		List<MenuBean> parentList = new LinkedList<MenuBean>();

		for (Iterator iter = menuList.iterator(); iter.hasNext();) {
			MenuBean element = (MenuBean) iter.next();
			String parntCode = element.getPARENT_PRG_CODE();
			if (Util.isNullOrEmpty(parntCode)) {
				parentList.add(element);
			}
		}

		return parentList.toArray(new MenuBean[parentList.size()]);
	}

	/**
	 * ���X�g�ォ��A�w�肵���e�R�[�h�������j���[�����𒊏o����
	 * 
	 * @param parentCode �e�R�[�h
	 * @param menuList ���j���[���X�g
	 * @return �q���j���[���X�g
	 */
	protected MenuBean[] getChildrenMenus(String parentCode, List<MenuBean> menuList) {

		List<MenuBean> childList = new LinkedList<MenuBean>();

		for (Iterator iter = menuList.iterator(); iter.hasNext();) {
			MenuBean element = (MenuBean) iter.next();
			if (parentCode.equals(element.getPARENT_PRG_CODE())) {
				childList.add(element);
			}
		}

		return childList.toArray(new MenuBean[childList.size()]);
	}

	/**
	 * ���j���[�c���[�I���C�x���g. �p�l���̐؂�ւ��A�r������������s��
	 */
	public void valueChanged(TreeSelectionEvent e) {

		try {
			// �I������Ă���v�f���擾���܂��B
			JScrollPane scrollpane = (JScrollPane) appMain.tabbedPane.getSelectedComponent();

			JTree tree = (JTree) scrollpane.getViewport().getComponent(0);

			// �c���[��I�������Ƃ��̓��̓`�F�b�N���s��Ȃ��悤�ɐݒ肷��B
			tree.setVerifyInputWhenFocusTarget(false);
			final MenuTreeNode current = (MenuTreeNode) tree.getLastSelectedPathComponent();

			// �v���O�����m�[�h���烁�j���[�m�[�h��I������ƈ�x�I���������͂��ނ��߁Anull���Ԃ��Ă���
			// ���j���[�t�H���_�m�[�h���A�v���O�����m�[�h�����f
			if (current == null || current.isMenu()) {
				return;
			}

			String prgCode = current.getProgramCode();

			// ���[�U�[��񂩂猠�����x�������擾
			TUserInfo userInfo = TClientLoginInfo.getInstance();

			// ���[�U�[�����ƃv���O���������̔�r
			if (userInfo.getProcessLevel() > current.getProcessLevel()) {
				// �m�[�h���������Ă���������
				ClientLogger.debug("no authenticate:" + prgCode);

				setCurrentSelectionTree();
				return;
			}

			// Frame��v���O������d�N���h�~
			if (runFramePrgCode.equals(prgCode)) {
				ClientLogger.debug(prgCode + " has already been displaying in the frame.");
				return;
			}

			// Window�v���O������d�N���h�~
			if (runWindowPrograms.containsKey(prgCode)) {
				ClientLogger.debug(prgCode + " has already been displaying in the window.");

				Frame frame = runWindowPrograms.get(prgCode);
				if (frame.getExtendedState() == Frame.ICONIFIED) {
					frame.setExtendedState(Frame.NORMAL);
				}
				frame.toFront();

				setCurrentSelectionTree();
				return;
			}

			// �t�H�[�J�X��I���v���O�����ɓ��ĂĂ���A�N���X�����[�h
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					try {
						// WAIT�J�[�\���ݒ�
						appMain.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

						// �v���O�����I����ʒm(�O�̃v���O���������݂���ꍇ�j
						if (!isShowProgramForWindow() && !"".equals(runFramePrgCode)) {
							if (isMultiWindowMode) {
								prgRequest.endPrg(runFramePrgCode);

							} else {
								prgRequest.end(runFramePrgCode);
							}
						}

						// �\���v���O�����̃��[�h
						loadControl(current);

						// �v���O�����J�n�̒ʒm
						prgRequest.start(current.getProgramCode());

					} catch (Exception ex) {
						errorHandler(appMain, ex, "E00009");

					} finally {
						// �ʏ�J�[�\���ɖ߂�
						appMain.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}
			});

		} catch (Exception ex) {
			errorHandler(this.appMain, ex, "E00009");
		}
	}

	/**
	 * �N���X��ǂݍ��݁A���C���p�l���ɐݒ�
	 * 
	 * @param className �N���X��
	 */
	protected void loadControl(String className) {
		MenuTreeNode dummy = new MenuTreeNode();
		dummy.setPanelName(className);

		loadControl(dummy);
	}

	/**
	 * �N���X��ǂݍ��݁A���C���p�l���ɐݒ�
	 * 
	 * @param current �I��TreeNode
	 */
	@SuppressWarnings("deprecation")
	protected void loadControl(MenuTreeNode current) {

		// class�����[�h
		try {
			final String programCode = current.getProgramCode();

			// �C���X�^���X���̂��߂Ƀv���O��������ݒ�
			TClientProgramInfo prgInfoTmp = TClientProgramInfo.getInstance();
			prgInfoTmp.setProgramCode(programCode);
			prgInfoTmp.setProgramName(current.getProgramName());
			prgInfoTmp.setProgramName(current.getProgramShortName());
			prgInfoTmp.setProcessLevel(current.getProcessLevel());

			Class cl = this.getClass().getClassLoader().loadClass(current.getPanelName());

			TAppletClientBase ctrl = (TAppletClientBase) cl.newInstance();

			if (ctrl == null) {
				errorHandler(appMain.getParentFrame(), "panel control class not found.");
				return;
			}

			JPanel panel = ctrl.getPanel();
			if (panel == null) {
				errorHandler(appMain.getParentFrame(), "Panel  not found");
				return;
			}

			// �V�v���O�����N���O��GC
			System.gc();

			// �I�������v���O�����̏����A�I���v���O�������ێ��N���X�ɐݒ�
			if (ctrl instanceof TPanelCtrlBase) {
				// Dialog�ŗ��p�����̂�h���ׂ�PanelCtrlBase�Ɍ���
				((TPanelCtrlBase) ctrl).setProgramInfo(prgInfoTmp.clone());
			}

			// �v���O�����I�[�v��
			if (isShowProgramForWindow()) {
				// �E�C���h�E���[�h
				TAppletWindow windowPanel = createTAppletWindow();
				windowPanel.detailPanel.add(panel);

				// �v���O�������̕\��
				windowPanel.lblProgramName.setText(current.getProgramName());

				JFrame frame = new JFrame(current.getProgramName());
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

				frame.add(windowPanel);

				// ��ʃT�C�Y
				frame.setSize(getWindowSize()); // ���C����ʂ̃��j���[�����Ɠ����T�C�Y

				frame.addWindowListener(new WindowAdapter() {

					public void windowClosed(WindowEvent e) {
						doWindowEnd(programCode);

						if (currentPanel != null) {
							currentPanel.requestFocusInWindow();
						}
					}
				});

				doWindowStart(programCode, frame);

				frame.setVisible(true);

			} else {
				// �t���[��

				// �p�l���ǉ�
				panel.setVisible(false);
				panel.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
				GridBagConstraints gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.fill = GridBagConstraints.BOTH;
				gridBagConstraints.weightx = 1;
				gridBagConstraints.weighty = 1;
				this.appMain.mainPanel.add(panel, gridBagConstraints);
				panel.setVisible(true);

				if (panel instanceof TPanelBusiness) {
					((TPanelBusiness) panel).setTabPolicy();
					((TPanelBusiness) panel).transferFocusTopItem();
				}

				boolean isFirstPanel = (currentPanel == null);

				// ���O�p�l�����폜
				if (!isFirstPanel) {
					currentPanel.setVisible(false);
					this.appMain.mainPanel.remove(currentPanel);
				}

				// ���O�p�l���ɐݒ�
				currentPanel = panel;
				currentPanel.setFocusable(true);

				// �v���O�������̕\��
				appMain.lblProgramName.setText(current.getProgramName());

				// �ŏ��N�����A�}�E�X���X�i�[��o�^
				if (isFirstPanel) {

					MouseListener listener = new MouseAdapter() {

						public void mousePressed(MouseEvent e) {
							currentPanel.requestFocusInWindow();
						}
					};

					appMain.addMouseListener(listener);
					appMain.tabbedPane.addMouseListener(listener);
					appMain.mainPanel.addMouseListener(listener);
				}

				// �v���O�����̃R�[�h���N���d���`�F�b�N�p�ɕێ�
				runFramePrgCode = programCode;

				// Frame��Tree�I����Ԃ�ێ�����
				this.runFrameTabNum = tabNum;
				JScrollPane scrollpane = (JScrollPane) appMain.tabbedPane.getSelectedComponent();
				JTree tree = (JTree) scrollpane.getViewport().getComponent(0);
				this.runFrameTreePath = tree.getSelectionPath();
			}

		} catch (Exception e) {
			errorHandler(this.appMain, e, "E00009");

		} finally {
			setCurrentSelectionTree();
		}
	}

	/**
	 * ��Window�̃t���[���N���X��Ԃ�
	 * 
	 * @return ��WindowFrame
	 */
	protected TAppletWindow createTAppletWindow() {
		return new TAppletWindow();
	}

	/**
	 * Frame�ɕ\������Ă���TreeNode��I����Ԃɂ���
	 */
	protected void setCurrentSelectionTree() {

		if (runFrameTreePath == null) {
			return;
		}

		JScrollPane scrollpane = (JScrollPane) appMain.tabbedPane.getComponentAt(tabNum);
		JTree tree = (JTree) scrollpane.getViewport().getComponent(0);

		if (tabNum == runFrameTabNum) {
			tree.setSelectionPath(runFrameTreePath);
		} else {
			tree.clearSelection();
		}
	}

	/**
	 * �e�R���e�i�����ǂ�Ajava.awt.Window, java.awt.Applet��T��.
	 * 
	 * @param p ���R���e�i
	 * @return �e�R���e�i
	 */
	public Container getParentRoot(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof AppletViewer) {
				return p;
			}
		}
		return p;
	}

	/**
	 * �v���O�����̃I�[�v�����[�h���擾
	 * 
	 * @return true:�E�C���h�E false:�t���[��
	 */
	protected boolean isShowProgramForWindow() {

		// �t���[���Ƀv���O�������Ȃ���΋����I�Ƀt���[���ɕ\���B
		if (runFramePrgCode.equals("")) {
			return false;
		}

		// �E�C���h�E���I�����L��
		return appMain.rdoPrgWind.isSelected() && appMain.rdoPrgWind.isEnabled();
	}

	/**
	 * �E�C���h�E���J���Ă��鎞�͐���{�^���𖳌��ɂ���B
	 */
	protected void ctrlOpenModeEnabled() {
		if (runWindowPrograms.size() < MAX_WINDOW) {
			appMain.rdoPrgFrame.setEnabled(true);
			appMain.rdoPrgWind.setEnabled(true);

		} else {
			appMain.rdoPrgFrame.setEnabled(false);
			appMain.rdoPrgWind.setEnabled(false);
		}
	}

	/**
	 * �E�B���h�E�v���O�����̊J�n
	 * 
	 * @param prgCode �v���O�����R�[�h
	 * @param frame �N��Frame
	 */
	protected void doWindowStart(String prgCode, Frame frame) {

		// �v���O�����̃R�[�h���N���d���`�F�b�N�p�ɕێ�
		runWindowPrograms.put(prgCode, frame);

		ctrlOpenModeEnabled();
	}

	/**
	 * �E�B���h�E�v���O�����̏I��
	 * 
	 * @param prgCode �v���O�����R�[�h
	 */
	protected void doWindowEnd(String prgCode) {

		if (!runWindowPrograms.containsKey(prgCode)) {
			return;
		}

		try {
			prgRequest.endPrg(prgCode);

		} catch (IOException ex) {
			// �����Ȃ�
		}

		runWindowPrograms.remove(prgCode);

		ctrlOpenModeEnabled();
	}

	/**
	 * ���O�A�E�g�������s��JavaScript�̌Ăяo��
	 */
	public void logout() {
		try {
			String msgID = runWindowPrograms.isEmpty() ? "Q90001" : "Q90002";

			if (!showConfirmMessage(appMain, msgID)) {
				return;
			}

			// �E�B���h�E�v���O�����̏I���ʒm
			String[] prgCodes = runWindowPrograms.keySet().toArray(new String[runWindowPrograms.size()]);
			for (String prgCode : prgCodes) {
				doWindowEnd(prgCode);
			}

			// �t���[���v���O�����I���ʒm
			if (!"".equals(runFramePrgCode)) {
				prgRequest.end(runFramePrgCode);
			}

			// ���O�A�E�g�̒ʒm
			super.addSendValues("flag", "1");
			super.request(ClientConfig.getRootDirectory() + "/login");

			TAccessJScript jscript = new TAccessJScript();
			jscript.setApplet(appMain); // jscript access object �� applet�ݒ�
			jscript.executeJScriptFunctionOnThread("logoutprocess", new String[0]);

		} catch (Exception ex) {
			ClientLogger.error(ex.getMessage(), ex);
			errorHandler(appMain);
		}
	}

	/**
	 * �~�{�^�����O�A�E�g����
	 */
	public void logoutForClose() {

		try {
			// �E�B���h�E�v���O�����̏I���ʒm
			String[] prgCodes = runWindowPrograms.keySet().toArray(new String[runWindowPrograms.size()]);
			for (String prgCode : prgCodes) {
				doWindowEnd(prgCode);
			}

			// �t���[���v���O�����I���ʒm
			if (!"".equals(runFramePrgCode)) {
				prgRequest.end(runFramePrgCode);
			}

			// ���O�A�E�g�̒ʒm
			super.addSendValues("flag", "1");
			super.request(ClientConfig.getRootDirectory() + "/login");

		} catch (IOException ex) {
			ClientLogger.error(ex.getMessage(), ex);
		}
	}

	/**
	 * �p�X���[�h�ύX�_�C�A���O�̌Ăяo��
	 */
	public void openDialog() {

		PasswordUpdateDialogCtrl dialogCtrl = new PasswordUpdateDialogCtrl(appMain.getParentFrame());
		dialogCtrl.show();
	}

	/**
	 * tree���̓������^�u�� tabbedPane�ɒǉ�
	 * 
	 * @param tabbedPane ���f��̃^�u
	 * @param name �^�u����
	 * @param rootNode ���fTree
	 */
	protected void addTab(JTabbedPane tabbedPane, String name, TreeNode rootNode) {
		TTree tree = new TTree(rootNode) {

			public String getToolTipText(MouseEvent evt) {
				if (getRowForLocation(evt.getX(), evt.getY()) == -1) {
					return null;
				}

				TreePath curPath = getPathForLocation(evt.getX(), evt.getY());
				return ((MenuTreeNode) curPath.getLastPathComponent()).getToolTipText();
			}
		};

		ToolTipManager.sharedInstance().registerComponent(tree);

		// tree��scrollPane�ɐݒ�
		JScrollPane scrollPane = new JScrollPane(tree);

		// tree�ݒ�
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(this);
		tree.setShowsRootHandles(false);

		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setOpenIcon(ResourceUtil.getImage(TAppletMain.class, "images/sikaku07.gif"));
		renderer.setClosedIcon(ResourceUtil.getImage(TAppletMain.class, "images/sikaku07.gif"));
		renderer.setLeafIcon(ResourceUtil.getImage(TAppletMain.class, "images/sikaku01.gif"));

		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		try {
			Icon icon = ResourceUtil.getImage(TAppletMain.class, "images/" + name + ClientConfig.getImageSuffix()
				+ ".png");

			tabbedPane.addTab(null, icon, scrollPane);

		} catch (TRuntimeException ex) {
			ClientLogger.error(getMessage(ex.getMessageID(), ex.getMessageArgs()), ex);

			tabbedPane.addTab(name, null, scrollPane);
		}
	}

	/**
	 * �^�u�`�F���W�ł���܂őI�����Ă����^�u�̃c���[�I������������
	 */
	public void stateChanged(ChangeEvent e) {
		if (tabNum != -1) {
			JScrollPane scrollpane = (JScrollPane) appMain.tabbedPane.getComponentAt(tabNum);
			JTree tree = (JTree) scrollpane.getViewport().getComponent(0);
			tree.clearSelection();
		}

		tabNum = appMain.tabbedPane.getSelectedIndex();

		setCurrentSelectionTree();
	}

	/**
	 * �~�{�^��������
	 */
	protected void setIEFrameWindowListener() {

		Window window = (Frame) TGuiUtil.getParentFrameOrDialog(this.appMain);

		window.addWindowListener(new TWindowListener(null));
	}

	/**
	 * ���j���[�pNode
	 */
	public class MenuTreeNode extends DefaultMutableTreeNode {

		private String prgCode = "";

		private String prgName = "";

		private String panelName = "";

		private boolean isMenu = true;

		private int procLevel;

		private String comment;

		/**
		 * ���[�g�p
		 */
		public MenuTreeNode() {
			super("");
		}

		/**
		 * ���j���[�p
		 * 
		 * @param programCode �v���O�����R�[�h
		 * @param programName �\������
		 * @param comment �R�����g
		 */
		public MenuTreeNode(String programCode, String programName, String comment) {
			super(programName);

			this.prgCode = programCode;
			this.prgName = programName;
			this.comment = comment;

			// �����������ꍇ�́Anull�i�󔒂�����ƁA�ςȕ\�����o��ׁj
			if (Util.isNullOrEmpty(comment)) {
				this.comment = null;
			}
		}

		/**
		 * ��ʗp
		 * 
		 * @param programCode �v���O�����R�[�h
		 * @param programName �\������
		 * @param comment �R�����g
		 * @param panelName ���[�h�p�l����
		 * @param procLevel �v���O�������x��
		 */
		public MenuTreeNode(String programCode, String programName, String comment, String panelName, int procLevel) {
			this(programCode, programName, comment);

			if (ClientConfig.isShowPrgramCode()) {
				setUserObject(programCode + ":" + programName);
			}

			this.panelName = panelName;
			this.isMenu = false;
			this.procLevel = procLevel;
		}

		/**
		 * �v���O�����R�[�h�擾
		 * 
		 * @return �v���O�����R�[�h
		 */
		public String getProgramCode() {
			return this.prgCode;
		}

		/**
		 * �v���O�������̎擾
		 * 
		 * @return �v���O��������
		 */
		public String getProgramName() {
			return this.prgName;
		}

		/**
		 * �v���O�������̎擾
		 * 
		 * @return �v���O��������
		 */
		public String getProgramShortName() {
			return this.prgName;
		}

		/**
		 * ���[�h�p�l�����擾
		 * 
		 * @return ���[�h�p�l����
		 */
		public String getPanelName() {
			return this.panelName;
		}

		/**
		 * ���[�h�p�l�����擾
		 * 
		 * @param panelName ���[�h�p�l����
		 */
		public void setPanelName(String panelName) {
			this.panelName = panelName;
		}

		/**
		 * ���j���[����ʂ��𔻒f����.
		 * 
		 * @return true: ���j���[�Afalse: ���
		 */
		public boolean isMenu() {
			return this.isMenu;
		}

		/**
		 * �v���O�����ւ̃A�N�Z�X�������x��
		 * 
		 * @return �A�N�Z�X�������x��
		 */
		public int getProcessLevel() {
			return this.procLevel;
		}

		/**
		 * Tooltip�e�L�X�g�i�R�����g�j��Ԃ�.
		 * 
		 * @return Tooltip�e�L�X�g
		 */
		public String getToolTipText() {
			return this.comment;
		}
	}
}

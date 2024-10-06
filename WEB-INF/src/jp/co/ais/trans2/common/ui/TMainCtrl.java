package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.print.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

import javax.print.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.tree.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���C����ʂ̃R���g���[��
 * 
 * @author AIS
 */
public class TMainCtrl extends TController {

	/** true:���j���[�^�u�̏�����ۑ����� */
	protected static boolean IS_SAVE_MENU = ClientConfig.isFlagOn("trans.ui.menu.save.status");

	/** �C���X�^���X */
	protected static TMainCtrl instance = null;

	/** �ŏ����C���^�C�g���T�C�Y */
	public static int minMenuSize = 45;

	/** ���C����� */
	public TMain mainView;

	/** ���݋N�����̃v���O���� */
	public Map<String, TAppletClientBase> selectedProgram;

	/** ���݋N�����̃t���[���`���̃v���O���� */
	public Map<String, TPanelBusiness> frameProgram;

	/** ���݋N�����̃t���[�� */
	public Map<String, TFrame> frames;

	/** ���C���^�C�g���T�C�Y */
	protected int maxMenuTitleLength = 0;

	/** ���C���^�C�g���⑫�p�X�g�����O */
	protected String maxMenuTitleSpaces = "";

	/** ���j���[���ψ�ɂ��邩�ǂ���(�p��̂�)�Atrue:���ψ�ɂ��� */
	protected boolean isMenuWidthFixed = false;

	/** �v���O�����R�[�h��\�����邩�ǂ����Atrue:�\������ */
	protected boolean isShowProgramCode = false;

	/** ���j���[���[�h�`���Atrue:���j���[�}�X�^�Afalse:�v���O�����}�X�^ */
	public boolean isUseMenuMaster = false;

	/** �t���[���̃X�N���[���`���Atrue:�X�N���[���ǉ��Afalse:�X�N���[���Ȃ� */
	protected boolean isUseScroll = false;

	/** �J�X�^�}�C�Y�t���[���̕ۑ� */
	public List<TFrame> customerizeFrames = new ArrayList<TFrame>();

	/** �A�C�R�� */
	public BufferedImage icon = null;

	/** PDF���ڈ���ݒ肪���邩�ǂ����Atrue:PDF���ڈ������ */
	protected boolean isDirectPrint = false;

	/** �������pInstance���X�g */
	public List<TMainInitialInterface> instanceList;

	/** ���j���[TAB�������̘A�������}�b�v */
	public Map<String, List<TMainInitialInterface>> tabLinkInstanceMap;

	/** �e��ۑ��}�b�v */
	public Map<String, Object> saveMap;

	/**
	 * �����C����ʂ̃C���X�^���X�̎擾
	 * 
	 * @return �����C����ʂ̃C���X�^���X
	 */
	public static TMainCtrl getInstance() {
		return instance;
	}

	/**
	 * @param icon �A�C�R���w��
	 */
	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}

	/**
	 * �J�X�^�}�C�Y�t���[���̕ۑ�
	 * 
	 * @param frame
	 */
	public void addFrame(TFrame frame) {
		customerizeFrames.add(frame);
	}

	/**
	 * �J�X�^�}�C�Y�t���[���̕ۑ�
	 * 
	 * @param frame
	 */
	public void removeFrame(TFrame frame) {
		customerizeFrames.remove(frame);
	}

	/**
	 * �J�X�^�}�C�Y�t���[���̕ۑ�
	 */
	public void clearCustomerizeFrames() {
		customerizeFrames.clear();
	}

	/**
	 * �v���O�����̊J�n�B���C����ʂ𐶐�����B
	 */
	@Override
	public void start() {

		try {
			// ���C����ʏ������N���X�̏�������
			doMainInit();

			// ���C����ʂ𐶐����A����������
			mainView = createMainView();
			initMainView();

			selectedProgram = new TreeMap<String, TAppletClientBase>();
			frameProgram = new TreeMap<String, TPanelBusiness>();
			frames = new TreeMap<String, TFrame>();

			// �C���X�^���X����
			instance = this;

			// ���C����ʏ������N���X�̊J�n����
			doMainAfterCreate(instanceList);

			// ���C����ʂ�\��
			mainView.setExtendedState(Frame.MAXIMIZED_BOTH);
			mainView.setVisible(true);

		} catch (Exception e) {
			// �v���I�G���[�B�A�v���P�[�V���������
			e.printStackTrace();
		}

	}

	/**
	 * ���C����ʏ������N���X���X�g
	 * 
	 * @throws Exception
	 */
	protected void doMainInit() throws Exception {

		try {
			String fileName = getSaveFileKey();
			String path = SystemUtil.getAisSettingDir();
			Map<String, Object> map = (Map<String, Object>) FileUtil.getObject(path + File.separator + fileName);
			saveMap = map;

		} catch (Throwable ex) {
			// �G���[�Ȃ�
		}

		if (saveMap == null) {
			saveMap = new LinkedHashMap<String, Object>();
		}

		List<Class> clzList = getMainInitialClasses();
		instanceList = new ArrayList<TMainInitialInterface>();

		if (!clzList.isEmpty()) {
			// ������������
			System.out.println("main initial");

			for (Class clz : clzList) {
				Object obj = clz.newInstance();
				if (obj instanceof TMainInitialInterface) {
					TMainInitialInterface clazz = (TMainInitialInterface) obj;
					System.out.println(clazz.getName());
					instanceList.add(clazz);

					// ����������
					clazz.init();
				}
			}
		}

		// ������
		tabLinkInstanceMap = new LinkedHashMap<String, List<TMainInitialInterface>>();

	}

	/**
	 * ���C����ʕ\���㏉����
	 * 
	 * @param instanceList
	 */
	public static void doMainAfterCreate(List<TMainInitialInterface> instanceList) {
		// ���O�C�����ɏ�������

		if (!instanceList.isEmpty()) {
			// ������������

			for (TMainInitialInterface clazz : instanceList) {

				// ����������
				clazz.afterCreate();
			}
		}
	}

	/**
	 * @return ���C����ʏ������N���X�ꗗ�擾
	 */
	protected static List<Class> getMainInitialClasses() {
		String k = "trans.main.init.class.";
		return getMainInitialClasses(k);
	}

	/**
	 * @param key
	 * @return ���C����ʎw��L�[�̏������N���X�ꗗ�擾
	 */
	protected static List<Class> getMainInitialClasses(String key) {
		List<Class> list = TLoginCtrl.getInitialClasses(key);
		return list;
	}

	/**
	 * ���C����ʂ̃t�@�N�g��
	 * 
	 * @return ���C�����
	 */
	protected TMain createMainView() {
		return new TMain(instanceList);
	}

	/**
	 * ���C����ʂ̏�����
	 * 
	 * @throws Exception
	 */
	protected void initMainView() throws Exception {

		// �����ő剻�̂��߁A�p�b�N
		if (icon != null) {
			mainView.setIconImage(icon);
		}
		mainView.pack();

		mainView.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// �^�C�g��
		if (ClientConfig.isFlagOn("trans.title.not.show.company.code")) {
			mainView.setTitle(ClientConfig.getTitle());
		} else {
			mainView.setTitle(TLoginInfo.getCompany().getCode() + " " + ClientConfig.getTitle());
		}

		// �T�C�Y�w��
		int width = ClientConfig.getMainViewWidth();
		int height = ClientConfig.getMainViewHeight();
		mainView.setSize(width, height);

		// ���j���[���ψ�̃t���O�ݒ�
		isMenuWidthFixed = ClientConfig.isFlagOn("trans.menu.width.fixed") && getUser().isEnglish();

		// �v���O�����R�[�h��\�����邩�ǂ����Atrue:�\������
		isShowProgramCode = ClientConfig.isFlagOn("show.prg.code");

		// ���j���[���[�h�`���Atrue:���j���[�}�X�^�Afalse:�v���O�����}�X�^
		isUseMenuMaster = ClientConfig.isFlagOn("trans.menu.use");

		// �t���[���̃X�N���[���`���Atrue:�X�N���[���ǉ��Afalse:�X�N���[���Ȃ�
		isUseScroll = ClientConfig.isFlagOn("trans.mainframe.scroll.use");

		// PDF���ڈ���Atrue:����Afalse:���Ȃ�
		isDirectPrint = ClientConfig.isFlagOn("trans.pdf.direct.print");

		if (isUseScroll) {
			System.out.println("trans.mainframe.scroll.use is true");
		}

		// ���C����ʂ̃w�b�_�[������
		initMainViewHeader();

		// �v���O�����c���[����
		createMenuTree();

		// �C�x���g��`
		initMainViewEvent();

		// Windows���̏ꍇ�͔z�F�ύX�{�^���𖳌�
		if (LookAndFeelType.MINT != TUIManager.getLookAndFeelType()) {
			mainView.pnlLf.setVisible(false);
		}

		// GlassPane
		mainView.setGlassPane(new LockingGlassPane());
		mainView.getGlassPane().setVisible(false);

		// �p��̂݃��j���[�^�u�̕\����ؑ�
		if (getUser().isEnglish()) {
			mainView.menuTab.setTabPlacement(SwingConstants.NORTH);
			mainView.btnType1.setVisible(false);
			mainView.btnType2.setVisible(false);
			mainView.btnType3.setVisible(false);

			if (isMenuWidthFixed) {

				char[] chs = new char[maxMenuTitleLength];
				for (int i = 0; i < maxMenuTitleLength; i++) {
					chs[i] = ' ';
				}
				maxMenuTitleSpaces = new String(chs);

				for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {
					String title = mainView.menuTab.getTitleAt(i);
					title = StringUtil.leftBX(title + maxMenuTitleSpaces, maxMenuTitleLength);
					mainView.menuTab.setTitleAt(i, title);
				}
			} else {
				System.out.println("trans.menu.width.no.fixed is true");
			}
		}

		if (isDirectPrint) {
			String printerName = getUser().getPrinterName();

			for (PrintService service : PrinterJob.lookupPrintServices()) {
				if (service.getName().equals(printerName)) {
					TClientLoginInfo.setPrintService(service);
					break;
				}
			}
		}

		// �c�[���`�b�v�̕\���x��0.1�b
		ToolTipManager.sharedInstance().setInitialDelay(100);

		int seconds = 10;

		try {
			seconds = Util.avoidNullAsInt(ClientConfig.getProperty("trans.tooltip.dismiss.delay.seconds"));

		} catch (Exception ex) {
			// �G���[�Ȃ�
		}

		if (seconds <= 0) {
			seconds = 4; // Java�f�t�H���g��4�b
		}

		// �c�[���`�b�v�̕\�����ԁA�P�ʂ͕b
		ToolTipManager.sharedInstance().setDismissDelay(seconds * 1000);

		// ���j���[�\��������
		restoreMenuOrder();
	}

	/**
	 * ���C����ʂ̃C�x���g��`
	 */
	protected void initMainViewEvent() {

		// �v���O�����c���[����v���O�����I��
		for (int i = 0; i < mainView.menuTrees.size(); i++) {

			mainView.menuTrees.get(i).addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					menuTree_mouseClicked(e);
				}
			});
		}

		// �z�u�t�@�C����true�̏ꍇ�A�C�x���g��ǉ�����
		if (isMenuWidthFixed) {
			// ���j���[�T�C�Y�ύX
			mainView.menuTab.addComponentListener(new ComponentAdapter() {

				/**
				 * ���j���[�T�C�Y�ύX
				 */
				@Override
				public void componentResized(ComponentEvent arg0) {
					menuResized();
				}

			});
		}

		// L&F�{�^���iBlue�j����
		mainView.btnBlue.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Loo�� And Feel�̕ύX
				btnLF_Click(LookAndFeelColor.Blue);
			}
		});

		// L&F�{�^���iPink�j����
		mainView.btnPink.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Loo�� And Feel�̕ύX
				btnLF_Click(LookAndFeelColor.Pink);
			}
		});

		// L&F�{�^���iOrange�j����
		mainView.btnOrange.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Loo�� And Feel�̕ύX
				btnLF_Click(LookAndFeelColor.Orange);
			}
		});

		// L&F�{�^���iGreen�j����
		mainView.btnGreen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Loo�� And Feel�̕ύX
				btnLF_Click(LookAndFeelColor.Green);
			}
		});

		// L&F�{�^���iGray�j����
		mainView.btnGray.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Loo�� And Feel�̕ύX
				btnLF_Click(LookAndFeelColor.Gray);
			}
		});

		// L&F�{�^���iWhite�j����
		mainView.btnWhite.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Loo�� And Feel�̕ύX
				btnLF_Click(LookAndFeelColor.White);
			}
		});

		// L&F�{�^���iType1�j����
		mainView.btnType1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnLF_Click(MenuType.TYPE1);
			}
		});

		// L&F�{�^���iType2�j����
		mainView.btnType2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnLF_Click(MenuType.TYPE2);
			}
		});

		// L&F�{�^���iType3�j����
		mainView.btnType3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnLF_Click(MenuType.TYPE3);
			}
		});

		mainView.ctrlFontSize.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changedFontSizeComboBox();
				}
			}
		});

		// �v�����^�ݒ�{�^������
		mainView.btnPrintSetup.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnPrintSetup_Click();
			}
		});

		// �p�X���[�h�ύX�{�^������
		mainView.btnPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnPassword_Click();
			}
		});

		// �o�[�W�����{�^������
		mainView.btnVersion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnVersion_Click();
			}
		});

		// ���O�A�E�g�{�^������
		mainView.btnLogOut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnLogOut_Click();
			}
		});

		// ���鎞
		mainView.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				maybeExit();
			}

		});

		// ���j���[�^�u�ύX������
		mainView.menuTab.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				// ���j���[�^�u�X�e�[�^�X�ύX����
				changedMenuTabState();
			}
		});

		// �^�u������ꍇ�̏���
		mainView.mainTab.addCancelButtonCloseCallBackListener(new CallBackListener() {

			@Override
			public void after(Component component) {
				closeProgram(component);
			}
		});

		// �^�u���đI���������̃t�H�[�J�X�ݒ�
		mainView.mainTab.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				// ���C���^�u�X�e�[�^�X�ύX����
				changedMainTabState();
			}
		});

		// �^�u���đI���������̃t�H�[�J�X�ݒ�
		mainView.menuTab.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				// ���C���^�u�X�e�[�^�X�ύX����
				changedMenuTabState();
			}
		});

		mainView.mainTab.addMouseListener(new MouseAdapter() {

			/** ���j���[ */
			protected JPopupMenu popup = new JPopupMenu();

			/** �N���b�N�C�x���g */
			@Override
			public void mouseClicked(MouseEvent e) {

				Component comp = e.getComponent();

				if (comp == null || !comp.isEnabled()) {
					return;
				}

				Point tabPoint = e.getPoint();
				final int index = mainView.mainTab.indexAtLocation(tabPoint.x, tabPoint.y);

				if (index < 0) {
					return;
				}

				if (SwingUtilities.isMiddleMouseButton(e)) {
					// �z�C�[���N���b�N
					removeTabAt(index);

				} else if (SwingUtilities.isRightMouseButton(e)) {
					// �E�N���b�N
					popup.removeAll();

					JMenuItem closeAll = new JMenuItem(getWord("C10796"));// �S�Ẵ^�u�����
					closeAll.addActionListener(new ActionListener() {

						@SuppressWarnings("hiding")
						public void actionPerformed(ActionEvent e) {

							for (int i = mainView.mainTab.getTabCount(); 0 < i; i--) {
								removeTabAt(i - 1);
							}
						}
					});

					JMenuItem closeOther = new JMenuItem(getWord("C10797"));// ���̃^�u��S�ĕ���
					closeOther.addActionListener(new ActionListener() {

						@SuppressWarnings("hiding")
						public void actionPerformed(ActionEvent e) {

							for (int i = mainView.mainTab.getTabCount(); 0 < i; i--) {
								if (index == i - 1) {
									continue;
								}

								removeTabAt(i - 1);
							}
						}
					});

					JMenuItem closeOwn = new JMenuItem(getWord("C02374"));// ����
					closeOwn.addActionListener(new ActionListener() {

						@SuppressWarnings("hiding")
						public void actionPerformed(ActionEvent e) {
							removeTabAt(index);
						}
					});

					JMenuItem otherFrame = new JMenuItem(getWord("C11401"));// �؂藣��
					otherFrame.addActionListener(new ActionListener() {

						@SuppressWarnings("hiding")
						public void actionPerformed(ActionEvent e) {
							createNewFrame(index);
						}
					});

					popup.add(closeAll);
					popup.add(closeOther);
					popup.add(closeOwn);
					popup.add(otherFrame);

					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	/**
	 * ���j���[�^�u�̏�������
	 */
	protected void restoreMenuOrder() {
		if (!IS_SAVE_MENU) {
			return;
		}

		boolean isEnglish = getUser().isEnglish();
		String key = isEnglish ? "ui.menu.tab.list.en" : "ui.menu.tab.list";
		Object obj = getSaveValue(key);
		if (obj == null) {
			return;
		}

		if (!(obj instanceof List)) {
			return;
		}

		List<String> titleList = (List<String>) obj;

		List<String> nowTitleList = new ArrayList<String>();
		List<MenuTabData> nowMenuList = new ArrayList<MenuTabData>();

		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {
			String title = Util.avoidNullNT(mainView.menuTab.getTitleAt(i));
			nowTitleList.add(title);

			nowMenuList.add(new MenuTabData(mainView.menuTab, i));
		}

		mainView.menuTab.removeAll();

		for (int i = 0; i < titleList.size(); i++) {
			String title = titleList.get(i);
			if (i >= nowTitleList.size()) {
				// ���݃^�u�̌��������Ȃ��A�������f
				break;
			}

			if (nowTitleList.contains(title)) {
				// ���݃^�u�ɕۑ����ꂽ�^�u�����݂���
				int src = nowTitleList.indexOf(title); // �ړ���
				addMenuTab(nowMenuList.get(src));

				// �d���ǉ���h�~����
				nowTitleList.set(src, null);
			}
		}

		for (int i = 0; i < nowTitleList.size(); i++) {
			String title = nowTitleList.get(i);
			if (!Util.isNullOrEmpty(title)) {
				// ���������j���[�͂��̂܂ܒǉ�
				int src = i; // �ړ���
				addMenuTab(nowMenuList.get(src));
			}
		}
	}

	/**
	 * @param menuTabData
	 */
	protected void addMenuTab(MenuTabData menuTabData) {
		if (menuTabData == null) {
			// �����ς݃f�[�^�͏����Ȃ�
			return;
		}

		mainView.menuTab.addTab(menuTabData.label, menuTabData.iconSrc, menuTabData.comp, menuTabData.tooltip);

		int dst = mainView.menuTab.getTabCount() - 1;

		mainView.menuTab.setDisabledIconAt(dst, menuTabData.iconDis);
		mainView.menuTab.setEnabledAt(dst, menuTabData.enabled);
		mainView.menuTab.setMnemonicAt(dst, menuTabData.keycode);
		mainView.menuTab.setDisplayedMnemonicIndexAt(dst, menuTabData.mnemonicLoc);
		mainView.menuTab.setForegroundAt(dst, menuTabData.fg);
		mainView.menuTab.setBackgroundAt(dst, menuTabData.bg);

	}

	/**
	 * ���j���[�^�u�f�[�^
	 */
	protected class MenuTabData {

		/** comp */
		protected Component comp;

		/** label */
		protected String label;

		/** iconSrc */
		protected Icon iconSrc;

		/** iconDis */
		protected Icon iconDis;

		/** tooltip */
		protected String tooltip;

		/** enabled */
		protected boolean enabled;

		/** keycode */
		protected int keycode;

		/** mnemonicLoc */
		protected int mnemonicLoc;

		/** fg */
		protected Color fg;

		/** bg */
		protected Color bg;

		/**
		 * �R���X�g���N�^�[
		 * 
		 * @param pane
		 * @param src
		 */
		public MenuTabData(TLeftColorTabbedPane pane, int src) {
			this.comp = pane.getComponentAt(src);
			this.label = pane.getTitleAt(src);
			this.iconSrc = pane.getIconAt(src);
			this.iconDis = pane.getDisabledIconAt(src);
			this.tooltip = pane.getToolTipTextAt(src);
			this.enabled = pane.isEnabledAt(src);
			this.keycode = pane.getMnemonicAt(src);
			this.mnemonicLoc = pane.getDisplayedMnemonicIndexAt(src);
			this.fg = pane.getForegroundAt(src);
			this.bg = pane.getBackgroundAt(src);
		}
	}

	/**
	 * ���C���^�u�X�e�[�^�X�ύX����
	 */
	protected void changedMainTabState() {
		int index = mainView.mainTab.getSelectedIndex();

		if (0 <= index) {
			// �X�N���[���Ή�
			Component comp = mainView.mainTab.getComponentAt(index);
			TPanelBusiness panel = null;
			if (comp instanceof TPanelBusiness) {
				panel = (TPanelBusiness) comp;
			} else if (comp instanceof TScrollPane) {
				panel = ((TScrollPane) comp).getPanel();
			}

			if (panel != null) {
				panel.requestFocus();
				panel.transferFocusTopItem();

				if (panel instanceof TTabbedPanel) {
					((TTabbedPanel) panel).active();
				}
			}

			if (comp != null && comp instanceof TTabbedDialog) {
				((TTabbedDialog) comp).active();
			}
		}
	}

	/**
	 * ���j���[(����)�^�u�X�e�[�^�X�ύX����
	 */
	protected void changedMenuTabState() {
		if (!IS_SAVE_MENU) {
			return;
		}

		boolean isEnglish = getUser().isEnglish();
		List<String> titleList = new ArrayList<String>();

		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {
			String title = Util.avoidNullNT(mainView.menuTab.getTitleAt(i));
			titleList.add(title);
		}

		// �L������
		String key = isEnglish ? "ui.menu.tab.list.en" : "ui.menu.tab.list";
		setSaveValue(key, titleList);
		saveFile();

		int index = mainView.menuTab.getSelectedIndex();

		if (0 <= index) {
			// �X�N���[���Ή�
			TTree menu = getSelectedTree();

			if (menu != null && menu.getModel() != null && menu.getModel().getRoot() != null && menu.getModel().getRoot() instanceof TMenuTreeNode) {
				TMenuTreeNode parent = (TMenuTreeNode) menu.getModel().getRoot();

				if (parent.getPrgGroup() != null) {
					SystemizedProgram sp = parent.getPrgGroup();

					String sysCode = sp.getProgramGroupCode();

					List<TMainInitialInterface> clsList = tabLinkInstanceMap.get(sysCode);
					if (clsList != null && !clsList.isEmpty()) {
						// �N������

						for (TMainInitialInterface clazz : clsList) {
							try {

								// ����������
								clazz.afterCreate();

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}

		}
	}

	/**
	 * ���j���[�T�C�Y�ύX(���ψ�)
	 */
	protected void menuResized() {

		int len = mainView.menuTab.getWidth() - minMenuSize;
		int titleSize = len / 5 - 2;

		if (titleSize < maxMenuTitleLength) {
			titleSize = maxMenuTitleLength;
		}

		char[] chs = new char[titleSize];
		for (int i = 0; i < titleSize; i++) {
			chs[i] = ' ';
		}
		String addSpaces = new String(chs);

		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {
			String title = mainView.menuTab.getTitleAt(i);
			title += addSpaces;
			mainView.menuTab.setTitleAt(i, StringUtil.leftBX(title, titleSize));
		}
	}

	/**
	 * �w��^�u�����
	 * 
	 * @param tabIndex �^�uIndex
	 */
	public void removeTabAt(int tabIndex) {
		// ����C�x���g
		Component comp = mainView.mainTab.getComponentAt(tabIndex);
		closeProgram(comp);

		mainView.mainTab.remove(comp);
	}

	/**
	 * �w��^�u��؂藣��
	 * 
	 * @param tabIndex �^�uIndex
	 */
	public void createNewFrame(int tabIndex) {

		// �؂藣���C�x���g
		Component component = mainView.mainTab.getComponentAt(tabIndex);

		// �X�N���[���Ή�
		if (component == null || (!(component instanceof TPanelBusiness) && !(component instanceof TScrollPane))) {
			return;
		}

		final TPanelBusiness panel;
		if (component instanceof TPanelBusiness) {
			panel = (TPanelBusiness) component;
		} else {
			panel = ((TScrollPane) component).getPanel();
		}

		// ���C����ʂ̃^�u������
		mainView.mainTab.removeTabAt(tabIndex);

		// �w��p�l����Frame�ɕ\������
		showPanelWithFrame(panel);

	}

	/**
	 * �w��p�l����Frame�ɕ\������
	 * 
	 * @param panel TPanelBusiness
	 */
	public void showPanelWithFrame(final TPanelBusiness panel) {

		// �R���g���[��
		TAppletClientBase controller = null;
		String uid = null;

		if (panel instanceof TTabbedPanel) {
			uid = ((TTabbedPanel) panel).getDialog().getUID();
			controller = selectedProgram.get(uid);
		} else {
			controller = selectedProgram.get(panel.getProgramCode());
		}

		// �v���O�������
		TClientProgramInfo programInfo = null;

		// TRANS2.0�ȏ�̏ꍇ
		if (controller instanceof TController) {
			programInfo = ((TController) controller).getProgramInfo();
		}
		// TRANS1.0�̏ꍇ
		else if (controller instanceof TPanelCtrlBase) {
			programInfo = ((TPanelCtrlBase) controller).getProgramInfo();
		}
		// �ȊO
		else {
			return;
		}

		final String programCode = programInfo.getProgramCode();
		final String programName = programInfo.getProgramName();

		TFrame frame = createFrame(panel, programName);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// ��ʃT�C�Y
		int width = panel.getSize().width;
		int height = panel.getSize().height;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // �X�N���[���T�C�Y
		if (width == 0) {
			width = mainView.mainTab.getWidth();
		}
		if (height == 0) {
			height = mainView.mainTab.getHeight();
		}

		width = Math.min(width, dim.width);
		height = Math.min(height, dim.height);

		frame.setSize(width, height);

		// �X�N���[���Ή�

		if (isUseScroll) {
			TScrollPane jsp = createScrollPane(panel);
			jsp.setAutoscrolls(true);
			frame.add(jsp);
		} else {
			frame.add(panel);
		}

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent ev) {

				// �v���O���������
				closeProgram(panel);

				// �t���[���`���v���O�����������
				frameProgram.remove(programCode);
				frames.remove(programCode);
			}
		});

		// �t���[���`���v���O�����ɒǉ�����
		if (!Util.isNullOrEmpty(uid)) {
			frameProgram.put(uid, panel);
			frames.put(uid, frame);
		} else {
			frameProgram.put(programCode, panel);
			frames.put(programCode, frame);
		}

		// �t���[���\��
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * @param panel
	 * @param programName
	 * @return �t���[��
	 */
	public TFrame createFrame(final TPanelBusiness panel, final String programName) {
		TFrame frame = new TFrame(panel, programName);
		if (icon != null) {
			frame.setIconImage(icon);
			frame.pack();
		}
		return frame;
	}

	/**
	 * �I�����邩�̊m�F
	 */
	protected void maybeExit() {
		if (!showConfirmMessage(mainView, "Q00055")) {// �I�����܂����H
			return;
		}

		String tmpVersion = jarVersion; // ��U�ޔ�
		jarVersion = ""; // ���O�A�E�g�̓`�F�b�N�s�v

		try {

			// �r������
			for (int i = mainView.mainTab.getTabCount() - 1; i >= 0; i--) { // �t��
				closeProgramE(mainView.mainTab.getComponentAt(i));
			}

			// �t���[���`���̃v���O�������r������
			TPanelBusiness[] pnls = frameProgram.values().toArray(new TPanelBusiness[0]);
			for (int i = pnls.length - 1; i >= 0; i--) { // �t��
				TPanelBusiness panel = pnls[i];
				closeProgramE(panel);
			}

			// ���O�A�E�g���O�L�^
			logE(getCompany(), getUser(), null, "OUT");

			// �I��
			request(UserExclusiveManager.class, "cancelExclude");

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		} finally {
			mainView.setVisible(false);
			mainView.dispose();
			jarVersion = tmpVersion;
		}

		System.exit(0);
	}

	/**
	 * �v���O�����c���[����v���O�����I��
	 * 
	 * @param e
	 */
	public void menuTree_mouseClicked(MouseEvent e) {

		// ���N���b�N�ŋN��
		if (SwingUtilities.isLeftMouseButton(e)) {

			// �I���^�u�ɑ΂���c���[�𐳊m�Ɏ擾����
			TTree tree = getSelectedTree();
			if (tree == null) {
				return;
			}

			// �N���b�N���ꂽ�ꏊ����TreePath ���擾����
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			if (path == null) {
				return;
			}

			TMenuTreeNode node = (TMenuTreeNode) tree.getLastSelectedPathComponent();

			if (node == null) {
				return;
			}

			TTreeItem item = (TTreeItem) node.getUserObject();

			// true:Frame�֕\��
			boolean isFrame = e.isAltDown();

			if (isUseMenuMaster) {

				MenuDisp program = (MenuDisp) item.getObj();

				if (program.getProgram() == null || Util.isNullOrEmpty(program.getProgram().getLoadClassName())) {
					return;
				}

				startProgram(program.getCode(), program.getName(), program.getProgram().getLoadClassName(), isFrame);

			} else {

				// TODO:�v���O�����}�X�^�ɂ�郁�j���[�\�z�ˍ폜�\��

				Program program = (Program) item.getObj();

				if (program == null || Util.isNullOrEmpty(program.getLoadClassName())) {
					return;
				}

				startProgram(program.getCode(), program.getName(), program.getLoadClassName(), isFrame);
			}
		}
	}

	/**
	 * �I���^�u�ɑ΂���c���[�𐳊m�Ɏ擾����
	 * 
	 * @return �I���^�u
	 */
	protected TTree getSelectedTree() {
		if (!(mainView.menuTab.getSelectedComponent() instanceof TScrollPane)) {
			return null;
		}
		TScrollPane sp = (TScrollPane) mainView.menuTab.getSelectedComponent();
		if (sp.getViewport().getComponentCount() == 0 || !(sp.getViewport().getComponent(0) instanceof TPanel)) {
			return null;
		}
		TPanel pnl = (TPanel) sp.getViewport().getComponent(0);
		if (pnl.getComponentCount() == 0 || !(pnl.getComponent(0) instanceof TTree)) {
			return null;
		}
		TTree tree = (TTree) pnl.getComponent(0);
		return tree;
	}

	/**
	 * �ҏW�_�C�A���O��TAB�@�\�ŊJ��
	 * 
	 * @param view
	 */
	@SuppressWarnings("deprecation")
	public void startTabbedProgram(TTabbedDialog view) {
		startTabbedProgram(view, false);
	}

	/**
	 * �ҏW�_�C�A���O��TAB�@�\�ŊJ��
	 * 
	 * @param view
	 * @param isFrame true:�t���[�����[�h
	 */
	@SuppressWarnings("deprecation")
	public void startTabbedProgram(TTabbedDialog view, boolean isFrame) {

		String uid = view.getUID();
		String prgName = view.getProgramName();

		// ���ɑI������Ă���ꍇ�A���̃^�u�Ɉړ�
		if (isStartedProgram(uid)) {

			boolean hasTab = false;

			for (int tabCount = 0; tabCount < mainView.mainTab.getTabCount(); tabCount++) {

				// �X�N���[���Ή�
				Component comp = mainView.mainTab.getComponentAt(tabCount);
				TDialog dialog = null;
				String currentUID = null;

				if (comp instanceof TTabbedPanel) {
					currentUID = ((TTabbedPanel) comp).getDialog().getUID();

				} else if (comp instanceof TPanelBusiness) {
					// �����Ȃ�

				} else if (comp instanceof TScrollPane) {
					dialog = ((TScrollPane) comp).getDialog();
				}

				if (dialog != null && dialog instanceof TTabbedDialog) {
					currentUID = ((TTabbedDialog) dialog).getUID();
				}

				Container cont = (Container) comp;

				if (uid.equals(currentUID)) {
					TInputVerifier.setActiveChildren(cont, false);
					mainView.mainTab.setSelectedIndex(tabCount);
					TInputVerifier.setActiveChildren(cont, true);
					hasTab = true;
					break;
				}
			}

			// �t���[��������΁A�d���N���s�v
			if (!hasTab && frameProgram.get(uid) != null) {
				TPanelBusiness panel = frameProgram.get(uid);
				// �������t�H�[�J�X�ݒ�
				if (panel.getParentFrame().getExtendedState() == 7) {
					// �ő剻�ˍŏ���
					panel.getParentFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
				}
				if (panel.getParentFrame().getExtendedState() == Frame.ICONIFIED) {
					// ���ʁˍŏ���
					panel.getParentFrame().setExtendedState(Frame.NORMAL);
				}
				panel.transferFocusTopItem();
			}

			return;
		}

		// �v���O�������N�����A�w����ʂ����C���̈�ɃZ�b�g
		try {

			String prgCode = view.getProgramCode();

			// �����v
			mainView.getGlassPane().setVisible(true);

			if (Util.isNullOrEmpty(prgCode)) {
				return;
			}

			TClientProgramInfo prgInfo_ = TClientProgramInfo.getInstance();
			prgInfo_.setProgramCode(prgCode);
			prgInfo_.setProgramName(prgName);

			TController controller = view.getController();
			String realUID = controller.getRealUID(); // ��ʎ��ʎq
			String realInfo = controller.getRealInfo(); // ���l

			// Trans2.0�̏ꍇ

			controller.setProgramInfo(prgInfo_.clone());

			// �r������
			if (controller instanceof TExclusive) {
				TExclusiveControlMethod ec = ((TExclusive) controller).getExclusiveControlMethod();
				try {
					ec.exclude();

				} catch (Exception e) {
					errorHandler(mainView, e);
					return;
				}

			}

			controller.start();
			selectedProgram.put(uid, controller);

			TTabbedPanel pnl = view.getDialogBasePanel();
			TDialog dialog = view.getDialog();
			if (isUseScroll) {
				TScrollPane jsp = createScrollPane((TTabbedDialog) dialog);
				jsp.setAutoscrolls(true);
				if (jsp.getViewPanel() != null) {
					TGuiUtil.setComponentSize(jsp.getViewPanel(), 800, 600);
				}
				if (!isFrame) {
					mainView.mainTab.addTab(prgName, jsp, true);
				} else {
					showPanelWithFrame(pnl);
				}
			} else {
				if (!isFrame) {
					mainView.mainTab.addTab(prgName, pnl, true);
				} else {
					showPanelWithFrame(pnl);
				}
			}

			TInputVerifier.setActiveChildren(dialog, false);
			mainView.mainTab.setSelectedIndex(mainView.mainTab.getTabCount() - 1);
			TInputVerifier.setActiveChildren(dialog, true);

			if (!Util.isNullOrEmpty(realUID)) {
				// ��ʎ��ʎq�w�肠�聨�v���O�����N���L�^
				logE(getCompany(), getUser(), realUID, "IN", realInfo);
			}

		} catch (Exception ex) {
			errorHandler(mainView, ex);
			selectedProgram.remove(uid); // �G���[���������ꍇ�A�I���ς݃v���O��������O��

		} finally {
			mainView.getGlassPane().setVisible(false);
		}
	}

	/**
	 * TAB�^�C�g���ύX
	 * 
	 * @param ori
	 * @param title
	 */
	public void changedTabTitle(TTabbedPanel ori, String title) {
		if (ori == null) {
			return;
		}

		int tabIndex = mainView.mainTab.indexOfComponent(ori);

		if (tabIndex >= 0 && tabIndex < mainView.mainTab.getTabCount()) {
			// �L����TAB

			TTabbedTitle pnl = mainView.mainTab.getTab(tabIndex);
			if (pnl != null) {
				pnl.setTitle(title);
			}

			mainView.mainTab.repaint();
		}

		if (ori.getDialog() != null && !Util.isNullOrEmpty(ori.getDialog().getUID())) {
			TFrame frame = frames.get(ori.getDialog().getUID());
			if (frame != null) {
				frame.setTitle(title);
			}
		}
	}

	/**
	 * �v���O�������J�n����
	 * 
	 * @param prgCode �v���O�����R�[�h
	 * @param prgName �v���O��������
	 * @param ldName ���[�h�N���X��
	 */
	public void startProgram(String prgCode, String prgName, String ldName) {
		startProgram(prgCode, prgName, ldName, false);
	}

	/**
	 * �v���O�������J�n����
	 * 
	 * @param prgCode �v���O�����R�[�h
	 * @param prgName �v���O��������
	 * @param ldName ���[�h�N���X��
	 * @param isFrame true:Frame�֕\��
	 */
	@SuppressWarnings("deprecation")
	public void startProgram(String prgCode, String prgName, String ldName, boolean isFrame) {

		// ���ɑI������Ă���ꍇ�A���̃^�u�Ɉړ�
		if (isStartedProgram(prgCode)) {

			boolean hasTab = false;

			for (int tabCount = 0; tabCount < mainView.mainTab.getTabCount(); tabCount++) {

				// �X�N���[���Ή�
				Component comp = mainView.mainTab.getComponentAt(tabCount);
				TPanelBusiness panel = null;
				String currentProgramCode = null;

				if (comp instanceof TTabbedPanel) {
					currentProgramCode = ((TTabbedPanel) comp).getProgramCode();

				} else if (comp instanceof TPanelBusiness) {
					panel = (TPanelBusiness) comp;
					currentProgramCode = panel.getProgramCode();

				} else if (comp instanceof TScrollPane) {
					panel = ((TScrollPane) comp).getPanel();
					currentProgramCode = panel.getProgramCode();
				}

				if (prgCode.equals(currentProgramCode)) {
					TInputVerifier.setActiveChildren(panel, false);
					mainView.mainTab.setSelectedIndex(tabCount);
					panel.transferFocusTopItem();
					TInputVerifier.setActiveChildren(panel, true);
					hasTab = true;
					break;
				}
			}

			// �t���[��������΁A�d���N���s�v
			if (!hasTab && frameProgram.get(prgCode) != null) {
				TPanelBusiness panel = frameProgram.get(prgCode);
				// �������t�H�[�J�X�ݒ�
				if (panel.getParentFrame().getExtendedState() == 7) {
					// �ő剻�ˍŏ���
					panel.getParentFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
				}
				if (panel.getParentFrame().getExtendedState() == Frame.ICONIFIED) {
					// ���ʁˍŏ���
					panel.getParentFrame().setExtendedState(Frame.NORMAL);
				}
				panel.transferFocusTopItem();
			}

			return;
		}

		// �v���O�������N�����A�w����ʂ����C���̈�ɃZ�b�g
		try {
			// �����v
			mainView.getGlassPane().setVisible(true);

			if (Util.isNullOrEmpty(ldName)) {
				return;
			}

			// ����T�u�V�X�e�����p���[�U���`�F�b�N
			if (isOverSystemRegulatedNumber(getCompany(), getUser(), prgCode)) {
				return;
			}

			Class cls = Class.forName(ldName);

			TClientProgramInfo prgInfo_ = TClientProgramInfo.getInstance();
			prgInfo_.setProgramCode(prgCode);
			prgInfo_.setProgramName(prgName);

			TPanelBusiness panel;

			Object ctlInstance = cls.newInstance();

			// Trans2.0�̏ꍇ
			if (ctlInstance instanceof TController) {

				TController controller = (TController) ctlInstance;
				controller.setProgramInfo(prgInfo_.clone());

				// �r������
				if (controller instanceof TExclusive) {
					TExclusiveControlMethod ec = ((TExclusive) controller).getExclusiveControlMethod();
					try {
						ec.exclude();

					} catch (Exception e) {
						errorHandler(mainView, e);
						return;
					}

				}

				controller.start();
				selectedProgram.put(prgCode, controller);

				panel = controller.getPanel();
				panel.setProgramCode(prgCode);

				TGradationPanel dummy = createGradationPanel(panel);

				if (isUseScroll) {
					TScrollPane jsp = createScrollPane(dummy);
					jsp.setAutoscrolls(true);
					if (!isFrame) {
						mainView.mainTab.addTab(prgName, jsp, true);
					} else {
						showPanelWithFrame(panel);
					}
				} else {
					if (!isFrame) {
						mainView.mainTab.addTab(prgName, dummy, true);
					} else {
						showPanelWithFrame(panel);
					}
				}

				int tabIndex[] = new int[1];
				tabIndex[0] = mainView.mainTab.getTabCount();

			}
			// Trans1.0�̏ꍇ
			else {

				TAppletClientBase controller = (TAppletClientBase) ctlInstance;
				selectedProgram.put(prgCode, controller);

				if (controller instanceof TPanelCtrlBase) {
					((TPanelCtrlBase) controller).setProgramInfo(prgInfo_.clone());
				}

				panel = (TPanelBusiness) controller.getPanel();
				panel.setProgramCode(prgCode);

				// �O���f�[�V�����p�Ƀ_�~�[�p�l���ɏ悹��
				panel.setOpaque(false);
				TGradationPanel dummy = createGradationPanel(panel);

				// �X�N���[���Ή�
				if (isUseScroll) {
					TScrollPane jsp = createScrollPane(dummy);
					jsp.setAutoscrolls(true);
					if (!isFrame) {
						mainView.mainTab.addTab(prgName, jsp, true);
					} else {
						showPanelWithFrame(panel);
					}
				} else {
					if (!isFrame) {
						mainView.mainTab.addTab(prgName, dummy, true);
					} else {
						showPanelWithFrame(panel);
					}
				}

			}

			panel.setTabPolicy();
			TInputVerifier.setActiveChildren(panel, false);
			if (!isFrame) {
				mainView.mainTab.setSelectedIndex(mainView.mainTab.getTabCount() - 1);
			}
			panel.transferFocusTopItem();
			TInputVerifier.setActiveChildren(panel, true);

			// �v���O�����N���L�^
			logE(getCompany(), getUser(), prgCode, "IN");

		} catch (Exception ex) {
			errorHandler(mainView, ex);
			selectedProgram.remove(prgCode); // �G���[���������ꍇ�A�I���ς݃v���O��������O��

		} finally {
			mainView.getGlassPane().setVisible(false);
		}
	}

	/**
	 * @param panel
	 * @return TRANS1�v���O�����p�_�~�[�w�i�̃O���f�[�V�����p�l��
	 */
	protected TGradationPanel createGradationPanel(TPanelBusiness panel) {
		return new TGradationPanel(panel);
	}

	/**
	 * @param dialog
	 * @return �X�N���[���p�l��
	 */
	protected TScrollPane createScrollPane(TTabbedDialog dialog) {
		return new TScrollPane(dialog);
	}

	/**
	 * @param panel
	 * @return �X�N���[���p�l��
	 */
	protected TScrollPane createScrollPane(TPanel panel) {
		return new TScrollPane(panel);
	}

	/**
	 * @param panel
	 * @return �X�N���[���p�l��
	 */
	protected TScrollPane createScrollPane(TPanelBusiness panel) {
		return new TScrollPane(panel);
	}

	/**
	 * ����T�u�V�X�e�����p���[�U���`�F�b�N
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @return true:�K��T�u�V�X�e�����p���[�U����������
	 */
	protected boolean isOverSystemRegulatedNumber(Company company, User user, String programCode) {
		try {

			// ���O����
			Log log = new Log();

			log.setDate(Util.getCurrentDate());
			log.setCompany(company);
			log.setUser(user);
			Program program = new Program();
			program.setCode(programCode);
			log.setProgram(program);
			try {
				log.setIp(InetAddress.getLocalHost().getHostAddress());
			} catch (Exception e) {
				log.setIp("Unknown");
			}

			boolean result = (Boolean) request(SystemManager.class, "canOpenProgram", log);
			if (!result) {
				// W01155 �K��̃T�u���W���[���̃��C�Z���X������܂���B
				// You need more licenses to use this module.
				showMessage("W01155");
				return true;
			}

		} catch (TException ex) {
			errorHandler(ex);
			return true;
		}
		return false;
	}

	/**
	 * TRANS1�v���O�����p�_�~�[�w�i�̃O���f�[�V�����p�l��
	 */
	static class TGradationPanel extends TPanelBusiness {

		/** �O���f�[�V�����n�_�F */
		protected Color startColor = Color.white;

		/** �O���f�[�V�����n�_�F */
		protected Color endColor;

		/** ���p�l�� */
		protected TPanelBusiness panel;

		/**
		 * �R���X�g���N�^.
		 * 
		 * @param panel ���p�l��
		 */
		public TGradationPanel(TPanelBusiness panel) {
			this.panel = panel;

			setLayout(new BorderLayout());
			add(panel, BorderLayout.CENTER);
		}

		@Override
		public String getProgramCode() {
			return panel.getProgramCode();
		}

		@Override
		public void requestFocus() {
			panel.requestFocus();
		}

		@Override
		public void transferFocusTopItem() {
			panel.transferFocusTopItem();
		}

		@Override
		public void setTabPolicy() {
			panel.setTabPolicy();
		}

		@Override
		public void paintComponent(Graphics g) {

			endColor = getBackground();

			Graphics2D g2 = (Graphics2D) g;

			// ��(��)�ˉ�(�F)
			GradientPaint gradient = new GradientPaint(getWidth() / 4, 0.0f, startColor, getWidth() / 4,
				getHeight() / 4, endColor);
			g2.setPaint(gradient);

			g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
		}
	}

	/**
	 * �v���O���������ɋN������Ă��Ȃ����`�F�b�N����
	 * 
	 * @param prgCode �v���O�����R�[�h
	 * @return true:�N��
	 */
	public boolean isStartedProgram(String prgCode) {

		if (Util.isNullOrEmpty(prgCode)) {
			return false;
		}

		return selectedProgram.containsKey(prgCode);
	}

	/**
	 * �v���O�����̏I���{�^��������
	 * 
	 * @param component
	 */
	public void closeProgram(Component component) {
		try {
			closeProgramE(component, false);
		} catch (TException e) {
			// �G���[�Ȃ�
		}
	}

	/**
	 * �v���O�����̏I���{�^��������
	 * 
	 * @param component
	 * @throws TException
	 */
	public void closeProgramE(Component component) throws TException {
		closeProgramE(component, true);
	}

	/**
	 * �v���O�����̏I���{�^��������
	 * 
	 * @param component
	 * @param doThrow
	 * @throws TException
	 */
	public void closeProgramE(Component component, boolean doThrow) throws TException {

		if (component == null) {
			return;
		}

		TPanelBusiness panel = null;
		TDialog dialog = null;
		String prgCode = null;

		// �X�N���[���Ή�
		if (component instanceof TScrollPane) {
			panel = ((TScrollPane) component).getPanel();

			if (panel == null) {
				dialog = ((TScrollPane) component).getDialog();
			}

		} else if (component instanceof TTabbedPanel) {
			prgCode = ((TTabbedPanel) component).getDialog().getUID();

		} else if (component instanceof TPanelBusiness) {
			panel = (TPanelBusiness) component;
		}

		if (panel != null) {
			if (panel instanceof TTabbedPanel) {
				prgCode = ((TTabbedPanel) panel).getDialog().getUID();
			} else {
				prgCode = panel.getProgramCode();
			}
		} else if (dialog != null && dialog instanceof TTabbedDialog) {
			prgCode = ((TTabbedDialog) dialog).getUID();
		}

		// �r������
		TAppletClientBase controller = selectedProgram.get(prgCode);
		if (controller instanceof TExclusive) {
			TExclusiveControlMethod ec = ((TExclusive) controller).getExclusiveControlMethod();
			try {
				ec.cancelExclude();
			} catch (TException e) {
				errorHandler(mainView, e);
				return;
			}
		}

		if (controller instanceof IManagementCtrl) {
			// �Ǘ����ڂ���
			((IManagementCtrl) controller).saveManagementSetting();
			// �o�͒P�ʂ���
			((IManagementCtrl) controller).saveDepartmentSetting();
		}

		if (panel != null) {
			// �v���O�����A�E�g���O
			if (doThrow) {
				logE(getCompany(), getUser(), prgCode, "OUT");
			} else {
				log(getCompany(), getUser(), prgCode, "OUT");
			}
		}

		if (component instanceof TTabbedPanel) {
			// �Ώۃp�l������^�u��������ꍇ�ɏ������s��
			{

				String realUID = null;
				String realInfo = null;

				TTabbedPanel pnl = (TTabbedPanel) component;

				if (pnl.getDialog() != null && pnl.getDialog().getController() != null) {
					realUID = pnl.getDialog().getController().getRealUID();
					realInfo = pnl.getDialog().getController().getRealInfo();
				}

				if (!Util.isNullOrEmpty(realUID)) {

					// �v���O�����A�E�g���O
					if (doThrow) {
						logE(getCompany(), getUser(), realUID, "OUT", realInfo);
					} else {
						log(getCompany(), getUser(), realUID, "OUT", realInfo);
					}
				}
			}

			for (int i = 0; i < mainView.mainTab.getTabCount(); i++) {

				Component comp = mainView.mainTab.getComponentAt(i);

				if (comp == null) {
					continue;
				}

				if (comp instanceof TScrollPane) {
					TTabbedPanel basePanel = ((TScrollPane) comp).getDialog().getDialogBasePanel();
					if (component.equals(basePanel)) {
						mainView.mainTab.removeTabAt(i);
						break;
					}
				} else if (comp instanceof TTabbedPanel) {
					if (comp.equals(component)) {
						mainView.mainTab.removeTabAt(i);
						break;
					}
				}
			}

			if (frames.get(prgCode) != null) {
				// frame�ɂ�蔻��
				TFrame frame = frames.get(prgCode);
				frame.setVisible(false);

				frameProgram.remove(prgCode);
				frames.remove(prgCode);
			}
		}

		// �N���v���O�������X�g����폜
		selectedProgram.remove(prgCode);

		System.gc();
	}

	/**
	 * L&F�{�^������
	 * 
	 * @param color
	 */
	@SuppressWarnings("deprecation")
	public void btnLF_Click(LookAndFeelColor color) {

		try {

			// Look and Feel�̐ݒ�
			LookAndFeelType type = TUIManager.getLookAndFeelType();
			TUIManager.setUI(mainView, type, color);

			// �I������L&F��ۑ�
			User user = TLoginInfo.getUser();
			user.setLfName(type.value);
			user.setLfColorType(color.name());

			// �t���[���`���̃v���O����L&F���ݒ�
			for (TPanelBusiness panel : frameProgram.values()) {
				TUIManager.setUI(panel, type, color);
			}

			// 1.0�̃X�v���b�h�V�[�g�̐F��߂�
			for (TAppletClientBase ctrl : selectedProgram.values()) {
				resetColor(ctrl.getPanel());
			}

			request(UserManager.class, "entryLookandFeel", user);

			drawImages(mainView);

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		}
	}

	/**
	 * �R���|�[�l���g�`����ĕ\������
	 * 
	 * @param container
	 */
	protected void drawImages(Container container) {
		for (Component c : container.getComponents()) {
			if (c instanceof TTitlePanel) {
				if (((TTitlePanel) c).isTitleVisible) {

					// �}�̍ĕ\��
					((TTitlePanel) c).setImage();
				}
			}
			if (c instanceof Container) {
				drawImages((Container) c);
			}
		}
	}

	/**
	 * �J���[���Z�b�g
	 * 
	 * @param cont �R���e�i.
	 */
	protected void resetColor(Container cont) {
		if (cont == null) {
			return;
		}

		if (cont instanceof TTable) {
			((TTable) cont).resetColor();
		}

		if (cont instanceof TTextField) {
			TTextField txt = (TTextField) cont;
			if (txt.isFocusOwner()) {
				txt.focusGainedBackColor();
			}
		}

		if (cont instanceof TTextArea) {
			TTextArea txt = (TTextArea) cont;
			if (txt.isFocusOwner()) {
				txt.focusGainedBackColor();
			}
		}

		if (cont instanceof TPasswordField) {
			TPasswordField txt = (TPasswordField) cont;
			if (txt.isFocusOwner()) {
				txt.focusGainedBackColor();
			}
		}

		// �q�̃R���|�[�l���g������.
		Component[] arrComp = cont.getComponents();
		if (arrComp == null) {
			return;
		}

		// ���ׂĂ̎q�ɂ��āA�ċA�I�Ɍ�������.
		for (Component comp : arrComp) {
			if (comp instanceof Container) {
				resetColor((Container) comp);
			}
		}
	}

	/**
	 * L&F menuType �{�^������
	 * 
	 * @param menuType �^�u�^�C�v
	 */
	@SuppressWarnings("deprecation")
	public void btnLF_Click(MenuType menuType) {

		try {
			// �^�C�v�ؑ�
			TUIManager.setMenuType(menuType);

			// �I������L&F���擾����
			User user = TLoginInfo.getUser();
			// �I������L&F Type��ۑ�
			user.setMenuType(menuType);

			// Look and Feel�̐ݒ�
			LookAndFeelType type = TUIManager.getLookAndFeelType();
			TUIManager.setUI(mainView, type, LookAndFeelColor.get(user.getLfColorType()));

			// �t���[���`���̃v���O����L&F���ݒ�
			for (TPanelBusiness panel : frameProgram.values()) {
				TUIManager.setUI(panel, type, LookAndFeelColor.get(user.getLfColorType()));
			}

			// 1.0�̃X�v���b�h�V�[�g�̐F��߂�
			for (TAppletClientBase ctrl : selectedProgram.values()) {
				resetColor(ctrl.getPanel());
			}

			request(UserManager.class, "entryLookandFeel", user);

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		}
	}

	/**
	 * �t�H���g�T�C�Y�R���{�ύX����
	 */
	public void changedFontSizeComboBox() {
		changedFontSizeComboBox(true);
	}

	/**
	 * �t�H���g�T�C�Y�R���{�ύX����
	 * 
	 * @param doSave true:�ۑ��������s��
	 */
	public void changedFontSizeComboBox(boolean doSave) {

		try {

			int fontSize = Util.avoidNullAsInt(mainView.ctrlFontSize.getSelectedItemValue());

			TUIManager.setLookAndFeel(LookAndFeelType.MINT.value);

			// fontMap������
			TUIManager.initDefaultFontMap(fontSize);

			// Look and Feel�̐ݒ�
			LookAndFeelType type = TUIManager.getLookAndFeelType();
			TUIManager.setUI(mainView, type, LookAndFeelColor.get(TLoginInfo.getUser().getLfColorType()));

			if (doSave) {
				// �����Ȃ�A�ۑ�
				setSaveValue("ui.font.size", fontSize);
				saveFile();
			}

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		}

	}

	/**
	 * �v�����^�[�ݒ�{�^������
	 */
	public void btnPrintSetup_Click() {
		selectPrinter();
	}

	/**
	 * �v�����^�I��
	 */
	public void selectPrinter() {

		try {
			PrinterJob printJob = PrinterJob.getPrinterJob();

			PrintService service = TClientLoginInfo.getPrintService();
			if (service != null) {
				printJob.setPrintService(service);
			}

			if (printJob.printDialog()) {

				PrintService selectService = printJob.getPrintService();

				TClientLoginInfo.setPrintService(selectService);

				if (service != null && service.equals(selectService)) {
					return;
				}

				String printName = selectService.getName();
				User user = new User();
				user.setCompanyCode(getCompanyCode());
				user.setCode(getUserCode());
				user.setPrinterName(printName);

				// �o�^
				request(getPrinterModelClass(), "updatePrinter", user);

				// ���O�C�����X�V
				TLoginInfo.getUser().setPrinterName(printName);

			}

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return UserManager
	 */
	protected Class getPrinterModelClass() {
		return UserManager.class;
	}

	/**
	 * �p�X���[�h�ύX�{�^������
	 */
	public void btnPassword_Click() {

		TPasswordCtrl ctrl = createPasswordCtrl();
		ctrl.start();

	}

	/**
	 * �o�[�W�����{�^������
	 */
	public void btnVersion_Click() {
		TVersionDialog dialog = new TVersionDialog(mainView);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	/**
	 * ���O�A�E�g�{�^������
	 */
	public void btnLogOut_Click() {
		// �m�F���b�Z�[�W
		if (!showConfirmMessage(mainView, "Q90001")) {
			return;
		}

		String tmpVersion = jarVersion; // ��U�ޔ�
		jarVersion = ""; // ���O�A�E�g�̓`�F�b�N�s�v

		try {

			// �r������
			for (int i = mainView.mainTab.getTabCount() - 1; i >= 0; i--) { // �t��
				closeProgramE(mainView.mainTab.getComponentAt(i));
			}

			// �t���[���`���̃v���O�������r������
			TPanelBusiness[] pnls = frameProgram.values().toArray(new TPanelBusiness[0]);
			for (int i = pnls.length - 1; i >= 0; i--) { // �t��
				TPanelBusiness panel = pnls[i];
				closeProgramE(panel);
			}

			// �t���[����\��
			for (TFrame frame : frames.values()) {
				frame.setVisible(false);
			}

			// �J�X�^�}�C�Y�t���[����\��
			for (TFrame frame : customerizeFrames) {
				frame.setVisible(false);
			}

			// ���O�A�E�g���O
			logE(getCompany(), getUser(), null, "OUT");

			// ���O�C������
			request(UserExclusiveManager.class, "cancelExclude");

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		} finally {

			// ���O�C�����N���A
			TLoginInfo.setCompany(null);
			TLoginInfo.setUser(null);
			TClientLoginInfo.getInstance().setUserLanguage(System.getProperty("user.language"));

			// ����
			mainView.setVisible(false);
			mainView.dispose();
			jarVersion = tmpVersion;

			// ���O�C���R���g���[���N��
			TLoginCtrl ctrl = createLoginController();

			// ���O�C���J�n
			ctrl.start();
		}
	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	public TLoginCtrl createLoginController() {
		return new TLoginCtrl();
	}

	/**
	 * �p�X���[�h�ύX�R���g���[������
	 * 
	 * @return �p�X���[�h�ύX�R���g���[��
	 */
	protected TPasswordCtrl createPasswordCtrl() {
		return new TPasswordCtrl(mainView);
	}

	/**
	 * ���C����ʂ̃w�b�_�[������
	 */
	protected void initMainViewHeader() {

		// ���S
		mainView.logo.setIcon(getCompanyLogo());

		if (!isDirectPrint) {
			mainView.btnPrintSetup.setVisible(false);
		}

		changeTime();
		createWatchThread();

		// ���O�C�����̍X�V
		setupLoginInfo();
	}

	/**
	 * ��Ђ̃A�C�R����Ԃ�
	 * 
	 * @return ��Ђ̃A�C�R��
	 */
	protected Icon getCompanyLogo() {

		String logoFilePath = ClientConfig.getProperty("logo.file.name");
		Icon logo = ResourceUtil.getImage(this.getClass(), "images/" + logoFilePath);
		return logo;

	}

	/**
	 * ���O�C�����̍X�V
	 */
	public void setupLoginInfo() {

		// ��Ж�
		mainView.lblCompanyName.setText(TLoginInfo.getCompany().getName());

		// ���O�C�����[�U�[�̏�������ƃ��[�U�[��
		mainView.lblUserName.setText(TLoginInfo.getUser().getDepartment().getName() + "    "
			+ TLoginInfo.getUser().getName());

		// ��ЃJ���[
		if (TLoginInfo.getCompany().getColor() != null) {
			mainView.pnlCompanyColor.setStartColor(TLoginInfo.getCompany().getColor());
		}

		Color companyColor = getCompanyColor();
		if (companyColor != null) {
			mainView.pnlCompanyColor.setStartColor(companyColor);
		}

		Object obj = getSaveValue("ui.font.size");
		if (obj != null) {
			if (obj instanceof Integer) {
				mainView.ctrlFontSize.setSelectedItemValue(obj);
				changedFontSizeComboBox(false);
			}
		}
	}

	/**
	 * @return �ۑ��t�@�C���L�[
	 */
	protected String getSaveFileKey() {
		return "ui_map_" + TLoginCtrl.getClientSaveKey() + ".log";
	}

	/**
	 * �e��ۑ�
	 */
	protected void saveFile() {
		String fileName = getSaveFileKey();
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(saveMap, path + File.separator + fileName);
	}

	/**
	 * @param key
	 * @return �ۑ��l�̎擾
	 */
	public Object getSaveValue(String key) {
		if (saveMap == null) {
			return null;
		}
		return saveMap.get(key);
	}

	/**
	 * @param key �L�[
	 * @param value �ۑ��l
	 */
	public void setSaveValue(String key, Object value) {
		if (saveMap == null) {
			saveMap = new LinkedHashMap<String, Object>();
		}
		saveMap.put(key, value);
	}

	/**
	 * ��ЃJ���[�擾
	 * 
	 * @return ��ЃJ���[
	 */
	public static Color getCompanyColor() {
		try {
			String[] rgb = StringUtil.split(ClientConfig.getProperty("trans.main.company.color"), ",");

			int r = Integer.parseInt(rgb[0]);
			int g = Integer.parseInt(rgb[1]);
			int b = Integer.parseInt(rgb[2]);

			return new Color(r, g, b);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * �v���O�����c���[����
	 * 
	 * @throws Exception
	 */
	protected void createMenuTree() throws Exception {

		List<SystemizedProgram> prgGroupList = null;
		if (isUseMenuMaster) {
			prgGroupList = getMenuPrograms(TLoginInfo.getCompany(), TLoginInfo.getUser());
		} else {

			// TODO:�v���O�����}�X�^�ɂ�郁�j���[�\�z�ˍ폜�\��
			prgGroupList = getMenuProgramsOld(TLoginInfo.getCompany(), TLoginInfo.getUser());
		}

		if (prgGroupList == null || prgGroupList.isEmpty()) {
			return;
		}

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.insets = new Insets(5, 5, 5, 0);

		for (SystemizedProgram prgGroup : prgGroupList) {

			// �^�u�ɑ�����v���O������z�u
			TMenuTreeNode root = new TMenuTreeNode();
			root.setPrgGroup(prgGroup);

			if (prgGroup != null) {
				String sysCode = prgGroup.getProgramGroupCode();

				List<Class> clsList = getMainInitialClasses("trans.menu.tab.class." + sysCode + ".");
				List<TMainInitialInterface> initList = new ArrayList<TMainInitialInterface>();
				if (clsList != null) {
					for (Class clazz : clsList) {
						try {
							Object obj = clazz.newInstance();
							if (obj instanceof TMainInitialInterface) {
								// ����������
								TMainInitialInterface clz = (TMainInitialInterface) obj;
								clz.init();

								initList.add(clz);
							}
						} catch (Exception e) {
							// �G���[�Ȃ�
							e.printStackTrace();
						}
					}
				}
				tabLinkInstanceMap.put(sysCode, initList);
			}

			if (isUseMenuMaster) {
				// ���j���[���X�g
				List<MenuDisp> menuList = prgGroup.getMenuDisp();

				// ���[�g�ƂȂ郌�R�[�h���擾
				List<MenuDisp> rootMenuList = this.getRootMenus(menuList);

				// ���[�g��������폜����
				for (MenuDisp prg : rootMenuList) {
					menuList.remove(prg);
				}

				// ���[�g�ȉ���Tree���\�z
				for (MenuDisp parent : rootMenuList) {
					TTreeItem item = createTreeItem(parent);
					TMenuTreeNode treeNode = new TMenuTreeNode(item);

					// �e�R�[�h�ƕR�t���Ă���q��ݒ�
					if (makeMenuNode(menuList, treeNode, parent.getCode())) {
						root.add(treeNode);
					}

				}

				// �e���Ȃ����j���[��\��
				for (MenuDisp prg : menuList) {

					// �u�����N�^�u�̏ꍇ�A�\�z�s�v
					if (prg.getName() == null) {
						continue;
					}

					TTreeItem item = createTreeItem(prg);
					TMenuTreeNode treeNode = new TMenuTreeNode(item);
					root.add(treeNode);
				}

			} else {

				// TODO:�v���O�����}�X�^�ɂ�郁�j���[�\�z�ˍ폜�\��

				// ���j���[���X�g
				List<Program> menuList = prgGroup.getPrograms();

				// ���[�g�ƂȂ郌�R�[�h���擾
				List<Program> rootMenuList = this.getRootMenusOld(menuList);

				// ���[�g��������폜����
				for (Program prg : rootMenuList) {
					menuList.remove(prg);
				}

				// ���[�g�ȉ���Tree���\�z
				for (Program parent : rootMenuList) {
					TTreeItem item = createTreeItemOld(parent);
					TMenuTreeNode treeNode = new TMenuTreeNode(item);

					// �e�R�[�h�ƕR�t���Ă���q��ݒ�
					if (makeMenuNodeOld(menuList, treeNode, parent.getCode())) {
						root.add(treeNode);
					}
				}

				// �e���Ȃ����j���[��\��
				for (Program prg : menuList) {
					TTreeItem item = createTreeItemOld(prg);
					TMenuTreeNode treeNode = new TMenuTreeNode(item);
					root.add(treeNode);
				}
			}

			// ���Y���[�g�̎q�m�[�h�����݂��ĂȂ��ꍇ�A�^�u�̒ǉ��s�v
			if (root.getChildCount() > 0) {
				// �v���O�����O���[�v�^�u�ǉ�
				TPanel pnl = new TPanel();
				pnl.setLayout(new GridBagLayout());
				pnl.setBackground(Color.white);
				TScrollPane sp = createScrollPane(pnl);
				sp.getVerticalScrollBar().setUnitIncrement(40);

				mainView.menuTab.addTab(getTabChar(prgGroup.getProgramGroupName()), sp);
				mainView.menuTab.setBackgroundAt(mainView.menuTab.getTabCount() - 1, prgGroup.getMenuColor());

				TTree tree = createTree(root);
				if (ClientConfig.isFlagOn("trans.menu.default.expand.all")) {
					tree.expandAll();
				}
				tree.setRootVisible(false);
				mainView.menuTrees.add(tree);
				pnl.add(tree, gc);
			}
		}
	}

	/**
	 * �c���[�̍쐬
	 * 
	 * @param root
	 * @return �c���[
	 */
	protected TTree createTree(TMenuTreeNode root) {
		return new TToolTipTree(root);
	}

	/**
	 * Tree�\�z
	 * 
	 * @param prgList ���l�^
	 * @param parent �e�t�H���_��
	 * @param code �e�R�[�h
	 * @return true:�q���ڂ���Afalse:�q���ڂȂ�
	 */
	protected boolean makeMenuNode(List<MenuDisp> prgList, TMenuTreeNode parent, String code) {

		List<MenuDisp> children = this.getChildrenMenus(prgList, code);

		// �q�b�g�����q�v�f��������폜����
		for (MenuDisp prg : children) {
			prgList.remove(prg);
		}

		// �q�v�f�̊K�w�\�z
		for (MenuDisp child : children) {

			TTreeItem item = createTreeItem(child);
			TMenuTreeNode itemNew = new TMenuTreeNode(item);

			// ���j���[�̏ꍇ�A����ɂ��̉��̊K�w���\�z
			if (child.isMenu()) {
				if (!makeMenuNode(prgList, itemNew, child.getCode())) {
					continue;
				}
			}

			parent.add(itemNew);
		}

		if (parent.getChildCount() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * �c���[���ڂ̍쐬
	 * 
	 * @param prg
	 * @return �c���[����
	 */
	protected TTreeItem createTreeItem(MenuDisp prg) {

		// ���j���[�\�����͐ݒ�t�@�C���ɏ]��
		String name = prg.getName();
		if (isShowProgramCode && !prg.isMenu()) {
			name = prg.getCode() + " " + prg.getName();
		}

		TTreeItem item = new TTreeItem(name, prg);
		return item;
	}

	/**
	 * ���[�g�m�[�h�v�f�����𒊏o����
	 * 
	 * @param prgList �Ώۃ��X�g
	 * @return ���[�g�̃v���O�����R�[�h�z��
	 */
	protected List<MenuDisp> getRootMenus(List<MenuDisp> prgList) {
		List<MenuDisp> parentList = new LinkedList<MenuDisp>();

		for (MenuDisp prg : prgList) {
			String parntCode = prg.getParentCode();

			// �e�v���O�����R�[�h���ݒ肳��Ă��Ȃ��ꍇ�A���[�g�Ƃ���
			if (Util.isNullOrEmpty(parntCode) && prg.isMenu()) {
				parentList.add(prg);
			}
		}

		return parentList;
	}

	/**
	 * �w�肵���e�R�[�h�������j���[�����𒊏o����
	 * 
	 * @param prgList ���j���[���X�g
	 * @param parentCode �e�R�[�h
	 * @return �q���j���[���X�g
	 */
	protected List<MenuDisp> getChildrenMenus(List<MenuDisp> prgList, String parentCode) {
		List<MenuDisp> childList = new LinkedList<MenuDisp>();

		for (MenuDisp prg : prgList) {
			// �e�R�[�h�Ǝq�̐e�v���O�����R�[�h����v����ꍇ
			if (parentCode.equals(prg.getParentCode())) {
				childList.add(prg);
			}
		}

		return childList;
	}

	/**
	 * �����X���b�h�𐶐�
	 */
	public void createWatchThread() {

		Thread t = new Thread(new Runnable() {

			public void run() {
				try {
					while (true) {
						Thread.sleep(1000);
						changeTime();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		t.start();

	}

	/**
	 * �w�b�_�[�̎������ŐV�ɕύX����
	 */
	public void changeTime() {

		String date = DateUtil.toYMDHMString(new Date());
		mainView.lblTime.setText(date);

	}

	/**
	 * �w���ЁA���[�U�[���g�p�\�ȃv���O�����̌n��Ԃ�
	 * 
	 * @param company ���
	 * @param user ���[�U�[
	 * @return �w���ЁA���[�U�[���g�p�\�ȃv���O�����̌n
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	public List<SystemizedProgram> getMenuPrograms(Company company, User user) throws TException {

		MenuSearchCondition condition = new MenuSearchCondition();
		condition.setCompanyCode(company.getCode());
		condition.setProgramRoleCode(user.getProgramRole().getCode());

		List<SystemizedProgram> list = (List<SystemizedProgram>) request(MenuManager.class, "getSystemizedProgram",
			condition);

		return list;

	}

	/**
	 * Tree�\�z
	 * 
	 * @param prgList ���l�^
	 * @param parent �e�t�H���_��
	 * @param code �e�R�[�h
	 * @return true:�q���ڂ���Afalse:�q���ڂȂ�
	 */
	protected boolean makeMenuNodeOld(List<Program> prgList, TMenuTreeNode parent, String code) {

		List<Program> children = this.getChildrenMenusOld(prgList, code);

		// �q�b�g�����q�v�f��������폜����
		for (Program prg : children) {
			prgList.remove(prg);
		}

		// �q�v�f�̊K�w�\�z
		for (Program child : children) {

			TTreeItem item = createTreeItemOld(child);
			TMenuTreeNode itemNew = new TMenuTreeNode(item);

			// ���j���[�̏ꍇ�A����ɂ��̉��̊K�w���\�z
			if (child.isMenu()) {
				if (!makeMenuNodeOld(prgList, itemNew, child.getCode())) {
					continue;
				}
			}

			parent.add(itemNew);
		}

		if (parent.getChildCount() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * �c���[���ڂ̍쐬
	 * 
	 * @param prg
	 * @return �c���[����
	 */
	protected TTreeItem createTreeItemOld(Program prg) {

		// ���j���[�\�����͐ݒ�t�@�C���ɏ]��
		String name = prg.getName();
		if (isShowProgramCode && !prg.isMenu()) {
			name = prg.getCode() + " " + prg.getName();
		}

		TTreeItem item = new TTreeItem(name, prg);
		return item;
	}

	/**
	 * ���[�g�m�[�h�v�f�����𒊏o����
	 * 
	 * @param prgList �Ώۃ��X�g
	 * @return ���[�g�̃v���O�����R�[�h�z��
	 */
	protected List<Program> getRootMenusOld(List<Program> prgList) {
		List<Program> parentList = new LinkedList<Program>();

		for (Program prg : prgList) {
			String parntCode = prg.getParentPrgCode();

			// �e�v���O�����R�[�h���ݒ肳��Ă��Ȃ��ꍇ�A���[�g�Ƃ���
			if (Util.isNullOrEmpty(parntCode) && prg.isMenu()) {
				parentList.add(prg);
			}
		}

		return parentList;
	}

	/**
	 * �w�肵���e�R�[�h�������j���[�����𒊏o����
	 * 
	 * @param prgList ���j���[���X�g
	 * @param parentCode �e�R�[�h
	 * @return �q���j���[���X�g
	 */
	protected List<Program> getChildrenMenusOld(List<Program> prgList, String parentCode) {
		List<Program> childList = new LinkedList<Program>();

		for (Program prg : prgList) {
			// �e�R�[�h�Ǝq�̐e�v���O�����R�[�h����v����ꍇ
			if (parentCode.equals(prg.getParentPrgCode())) {
				childList.add(prg);
			}
		}

		return childList;
	}

	/**
	 * �w���ЁA���[�U�[���g�p�\�ȃv���O�����̌n��Ԃ�
	 * 
	 * @param company ���
	 * @param user ���[�U�[
	 * @return �w���ЁA���[�U�[���g�p�\�ȃv���O�����̌n
	 * @throws TException
	 */
	public List<SystemizedProgram> getMenuProgramsOld(Company company, User user) throws TException {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(company.getCode());
		condition.setProgramRoleCode(user.getProgramRole().getCode());

		List<SystemizedProgram> list = (List<SystemizedProgram>) request(ProgramManager.class, "getSystemizedProgram",
			condition);

		return list;

	}

	/**
	 * ���j���[�^�u�ɃZ�b�g���镶�����Ԃ�
	 * 
	 * @param title �^�C�g��
	 * @return ���j���[�^�u�ɃZ�b�g���镶����
	 */
	protected String getTabChar(String title) {
		// �p��͂��̂܂�
		if (getUser().isEnglish()) {

			// �ő�o�C�g�����擾
			if (title.getBytes().length > maxMenuTitleLength) {
				maxMenuTitleLength = title.getBytes().length;
			}
			return title;
		}

		String rt = "<html><br>";

		for (int i = 0; i < title.length(); i++) {
			rt = rt + "<center>" + title.charAt(i) + "</center>";
		}
		rt = rt + "<br>";

		return rt;
	}
}

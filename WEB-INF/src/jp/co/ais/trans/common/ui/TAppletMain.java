package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * TApplet���p�������Amain applet.
 */
public class TAppletMain extends TApplet implements Runnable {

	/** ���C��Applet */
	public static TAppletMain instance = null;

	/** �R���g���[���N���X */
	protected TAppletMainCtrl ctrl;

	/** �A�v���b�g�ɕ\������x�[�X�p�l�� */
	protected TPanel basePanel;

	/** �w�b�_�[�\���p�l�� */
	protected TPanel headerPanel;

	/** �����p�l�� */
	protected TPanel detailPanel;

	/** �ؑփp�l�� */
	protected TPanel rdoPanel;

	/** �e�v���O������ʂ�\�����郁�C���p�l�� */
	protected TPanel mainPanel;

	/** ��Аݒ�F �\�����C���p�l�� */
	protected TPanel footerPanel;

	/** ���j���[�ƃp�l���Ԃ̃X�v���b�hPane */
	protected JSplitPane spPanel;

	/** ���S���x�� */
	protected TLabel lblLogo;

	/** ��Џ�񃉃x�� */
	protected TLabel lblCompanyName;

	/** ���[�U��񃉃x�� */
	protected TLabel lblUserInfo;

	/** �v���O�������\�����x�� */
	protected TLabel lblProgramName;

	/** �����\�����x�� */
	protected TLabel lblTime;

	/** ���O�A�E�g�{�^�� */
	protected TButton btnLogout;

	/** �p�X���[�h�ύX�{�^�� */
	protected TButton btnPassword;

	/** ���j���[�\���^�u�y�C�� */
	protected JTabbedPane tabbedPane;

	/** �����\���X���b�h */
	private volatile Thread timethread;

	/** �����\���ؑ֗pString */
	private String strTime;

	/** �����\���ؑ֗pDate */
	private Date date;

	/** �V�X�e�����S */
	protected Icon logo;

	/** �v���O���� �\���ؑփO���[�v */
	protected ButtonGroup rdoPrgGroup;

	/** �v���O���� �t���[���\���{�^�� */
	protected JRadioButton rdoPrgFrame;

	/** �v���O���� �E�C���h�E�\���{�^�� */
	protected JRadioButton rdoPrgWind;

	/** �w�b�_�J���[ */
	protected Color headerColor = Color.WHITE;

	/** ��ʉ��T�C�Y */
	protected int width;

	/** ��ʏc�T�C�Y */
	protected int height;

	/** ���j���[�̉��T�C�Y */
	protected int menuWidthSize = 200;

	/** �v���O���� �\���ؑւ̏c�T�C�Y */
	private int prgHeightSize = 20;

	/** �v���O���� �\���ؑւ̏c�T�C�Y */
	private int prgWidthSize = menuWidthSize - 3;

	/**
	 * �����ݒ��Look & Feel�ݒ�
	 */
	public void init() {

		try {
			System.gc();

			// ��ʍ\�z
			TUIManager.setUI(this);

			// Applet�T�C�Y�w��(AppletViewer�p)
			this.width = ClientConfig.getAppletWidht();
			this.height = ClientConfig.getAppletHeight();

			// ���j���[�T�C�Y�w��
			String menuSize = getParameter("menuSize");
			if (!Util.isNullOrEmpty(menuSize)) {
				menuWidthSize = Integer.parseInt(menuSize);
				prgWidthSize = menuWidthSize - 3;
			}

			EventQueue.invokeAndWait(new Runnable() {

				public void run() {
					try {
						String sid = getParameter("trans.scd");
						String kaiCode = getParameter("trans.ccd");
						String usrCode = getParameter("trans.ucd");
						String url = getParameter("trans.url");

						ClientLogger.info(sid + ", " + kaiCode + ", " + usrCode);
						ClientLogger.info(url);

						ClientConfig.setBaseAddress(url);

						initControl(sid, kaiCode, usrCode);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			});

			showStatus("");

			instance = this;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �R���g���[���̌Ăяo��
	 * 
	 * @param sid �Z�b�V����ID
	 * @param kaiCode ���O�C����ЃR�[�h
	 * @param usrCode ���O�C�����[�U�R�[�h
	 */
	public void initControl(String sid, String kaiCode, String usrCode) {
		ctrl = new TAppletMainCtrl(this);
		ctrl.init(sid, kaiCode, usrCode);
	}

	/**
	 * �ʃX���b�h�Ŏ����\�����n��
	 */
	public void start() {
		timethread = new Thread(this);
		timethread.start();
	}

	/**
	 * �p�l�������ݒ�
	 * 
	 * @param isMultiWindowMode true:�}���`�E�B���h�E���[�h
	 */
	public void initComponents(boolean isMultiWindowMode) {

		this.setSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));

		// ��Ճp�l��
		basePanel = new TPanel();
		basePanel.setLayout(new BorderLayout(0, 0));
		basePanel.setBackground(new Color(240, 240, 240));

		// �w�b�_�p�l��
		headerPanel = new TPanel();
		headerPanel.setLayout(new BorderLayout(5, 0));
		headerPanel.setBackground(new Color(255, 255, 255));

		makeHeaderPanel(); // �ڍ׍\�z

		headerPanel.setSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setMinimumSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setMaximumSize(new Dimension(headerPanel.getWidth(), 43));

		GridBagConstraints gridBagConstraints;

		// �����p�l��
		detailPanel = new TPanel();
		detailPanel.setLayout(new GridBagLayout());

		// �ؑփp�l��
		rdoPanel = new TPanel();
		rdoPanel.setLayout(new GridBagLayout());
		rdoPanel.setMaximumSize(new Dimension(prgWidthSize, prgHeightSize));
		rdoPanel.setMinimumSize(new Dimension(prgWidthSize, prgHeightSize));
		rdoPanel.setPreferredSize(new Dimension(prgWidthSize, prgHeightSize));
		rdoPanel.setBorder(BorderFactory.createEtchedBorder());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(1, 1, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		detailPanel.add(rdoPanel, gridBagConstraints);

		// �v���O�����\���ؑ�
		rdoPrgFrame = new JRadioButton("Frame", true);
		rdoPrgWind = new JRadioButton("Window", false);
		rdoPrgGroup = new ButtonGroup();
		rdoPrgGroup.add(rdoPrgFrame);
		rdoPrgGroup.add(rdoPrgWind);

		// �v���O�����\�� �t���[��
		rdoPrgFrame.setMaximumSize(new Dimension(90, prgHeightSize - 4));
		rdoPrgFrame.setMinimumSize(new Dimension(90, prgHeightSize - 4));
		rdoPrgFrame.setPreferredSize(new Dimension(90, prgHeightSize - 4));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.WEST;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		rdoPanel.add(rdoPrgFrame, gridBagConstraints);

		// �v���O�����\�� �E�C���h�E
		rdoPrgWind.setMaximumSize(new Dimension(90, prgHeightSize - 4));
		rdoPrgWind.setMinimumSize(new Dimension(90, prgHeightSize - 4));
		rdoPrgWind.setPreferredSize(new Dimension(90, prgHeightSize - 4));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(0, -4, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.EAST;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		rdoPanel.add(rdoPrgWind, gridBagConstraints);

		// ���j���[�^�u�y�C��
		tabbedPane = new JTabbedPane();
		tabbedPane.setMaximumSize(new Dimension(menuWidthSize, tabbedPane.getHeight()));
		tabbedPane.setMinimumSize(new Dimension(menuWidthSize, tabbedPane.getHeight()));
		tabbedPane.setPreferredSize(new Dimension(menuWidthSize, tabbedPane.getHeight()));
		tabbedPane.addChangeListener(ctrl);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tabbedPane.setTabPlacement(SwingConstants.LEFT);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;

		if (isMultiWindowMode) {
			gridBagConstraints.insets = new Insets(prgHeightSize, 0, 0, 0);
		} else {
			rdoPanel.setVisible(false);
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		}

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 1;
		detailPanel.add(tabbedPane, gridBagConstraints);

		// �v���O�����p�l��
		mainPanel = new TPanel();
		mainPanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		detailPanel.add(mainPanel, gridBagConstraints);

		// �t�b�^
		footerPanel = new TPanel();
		footerPanel.setPreferredSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMinimumSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMaximumSize(new Dimension(footerPanel.getWidth(), 3));

		// ��Аݒ�F�\�����C���ݒ�
		int[] rgbCode = Util.toRGBColorCode(TClientLoginInfo.getInstance().getCompanyInfo().getForeColor());
		footerPanel.setBackground(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));

		basePanel.add(headerPanel, BorderLayout.NORTH);
		basePanel.add(detailPanel, BorderLayout.CENTER);
		basePanel.add(footerPanel, BorderLayout.SOUTH);

		this.getContentPane().add(basePanel);
	}

	/**
	 * �p�l�������ݒ�(�X�v���b�g�p�l����)
	 * 
	 * @param isMultiWindowMode true:�}���`�E�B���h�E���[�h
	 */
	public void initComponentsSplit(boolean isMultiWindowMode) {
		this.setSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));

		// ��Ճp�l��
		basePanel = new TPanel();
		basePanel.setLayout(new BorderLayout(0, 0));
		basePanel.setBackground(new Color(240, 240, 240));

		// �w�b�_�p�l��
		headerPanel = new TPanel();
		headerPanel.setLayout(new BorderLayout(5, 0));
		headerPanel.setBackground(new Color(255, 255, 255));

		makeHeaderPanel(); // �ڍ׍\�z
		headerPanel.setSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setMinimumSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setMaximumSize(new Dimension(headerPanel.getWidth(), 43));

		GridBagConstraints gridBagConstraints;

		// �����p�l��
		detailPanel = new TPanel();
		detailPanel.setLayout(new GridBagLayout());

		// �ؑփp�l��
		rdoPanel = new TPanel();
		rdoPanel.setLayout(new GridBagLayout());
		rdoPanel.setMaximumSize(new Dimension(menuWidthSize, 20));
		rdoPanel.setMinimumSize(new Dimension(0, 20));
		rdoPanel.setPreferredSize(new Dimension(menuWidthSize, 20));
		rdoPanel.setBorder(BorderFactory.createEtchedBorder());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		detailPanel.add(rdoPanel, gridBagConstraints);

		// �v���O�����\���ؑ�
		rdoPrgFrame = new JRadioButton("Frame", true);
		rdoPrgWind = new JRadioButton("Window", false);
		rdoPrgGroup = new ButtonGroup();
		rdoPrgGroup.add(rdoPrgFrame);
		rdoPrgGroup.add(rdoPrgWind);

		// �v���O�����\�� �t���[��
		rdoPrgFrame.setMaximumSize(new Dimension(80, prgHeightSize - 4));
		rdoPrgFrame.setMinimumSize(new Dimension(0, prgHeightSize - 4));
		rdoPrgFrame.setPreferredSize(new Dimension(80, prgHeightSize - 4));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;

		rdoPanel.add(rdoPrgFrame, gridBagConstraints);

		// �v���O�����\�� �E�C���h�E
		rdoPrgWind.setMaximumSize(new Dimension(80, prgHeightSize - 4));
		rdoPrgWind.setMinimumSize(new Dimension(0, prgHeightSize - 4));
		rdoPrgWind.setPreferredSize(new Dimension(80, prgHeightSize - 4));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;

		rdoPanel.add(rdoPrgWind, gridBagConstraints);

		// ���j���[�^�u�y�C��
		tabbedPane = new JTabbedPane();
		tabbedPane.setMaximumSize(new Dimension(menuWidthSize, tabbedPane.getHeight() - 5));
		tabbedPane.setMinimumSize(new Dimension(0, tabbedPane.getHeight() - 5));
		tabbedPane.setPreferredSize(new Dimension(menuWidthSize, tabbedPane.getHeight() - 5));
		tabbedPane.addChangeListener(ctrl);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tabbedPane.setTabPlacement(SwingConstants.LEFT);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;

		if (!isMultiWindowMode) {
			rdoPanel.setVisible(false);
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		}

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		detailPanel.add(tabbedPane, gridBagConstraints);

		// �v���O�����p�l��
		mainPanel = new TPanel();
		mainPanel.setPreferredSize(new Dimension(width - menuWidthSize - 110, prgHeightSize));
		mainPanel.setMaximumSize(new Dimension(width, prgHeightSize));
		mainPanel.setMinimumSize(new Dimension(0, prgHeightSize));
		mainPanel.setLayout(new GridBagLayout());

		spPanel = new JSplitPane();
		spPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailPanel, mainPanel);
		spPanel.setContinuousLayout(true);
		spPanel.setDividerSize(6);
		spPanel.setDividerLocation(menuWidthSize);
		InputMap im = spPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).getParent();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), null);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), null);

		// �t�b�^
		footerPanel = new TPanel();
		footerPanel.setPreferredSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMinimumSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMaximumSize(new Dimension(footerPanel.getWidth(), 3));

		// ��Аݒ�F�\�����C���ݒ�
		int[] rgbCode = Util.toRGBColorCode(TClientLoginInfo.getInstance().getCompanyInfo().getForeColor());
		footerPanel.setBackground(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));

		basePanel.add(headerPanel, BorderLayout.NORTH);
		basePanel.add(spPanel, BorderLayout.CENTER);
		basePanel.add(footerPanel, BorderLayout.SOUTH);

		this.getContentPane().add(basePanel);
	}

	/**
	 * �w�b�_�[�ݒ�
	 */
	protected void makeHeaderPanel() {

		// �w�b�_�[�p�l���w�i�F�ύX
		if (!Util.isNullOrEmpty(ClientConfig.getHeaderBackColor())) {
			String[] code = ClientConfig.getHeaderBackColor().split(",");
			headerColor = new Color(Integer.parseInt(code[0]), Integer.parseInt(code[1]), Integer.parseInt(code[2]));
		}

		TPanel panel = new TPanel();
		panel.setLayout(new BorderLayout(10, 5));
		panel.setBackground(headerColor);

		// �C���[�W���x���̐ݒ�
		lblLogo = new TLabel();
		logo = ResourceUtil.getImage(TAppletMain.class, "images/Series" + ClientConfig.getImageSuffix() + ".png");
		lblLogo.setIcon(logo);
		lblLogo.setPreferredSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));
		lblLogo.setMinimumSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));
		lblLogo.setMaximumSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));

		// ���p�l��
		TPanel infoPanel = new TPanel();
		infoPanel.setLayout(new GridLayout(2, 2));
		infoPanel.setBackground(headerColor);

		// ���[�U�[���̎擾
		TUserInfo userInfo = TClientLoginInfo.getInstance();

		// �v���O�������̕\��
		lblProgramName = new TLabel();
		lblProgramName.setHorizontalAlignment(SwingConstants.RIGHT);
		TUIManager.addFontSize(lblProgramName, 2);

		// ��Ж��̕\��
		lblCompanyName = new TLabel();
		lblCompanyName.setText(userInfo.getCompanyInfo().getCompanyName());
		lblCompanyName.setHorizontalAlignment(SwingConstants.LEFT);
		TUIManager.addFontSize(lblCompanyName, 2);

		// ���[�U�[���̕\��
		lblUserInfo = new TLabel();
		lblUserInfo.setText(userInfo.getDepartmentShortName() + "  /   " + userInfo.getUserName());
		lblUserInfo.setText(userInfo.getDepartmentShortName() + "  /   " + userInfo.getUserName());
		lblUserInfo.setHorizontalAlignment(SwingConstants.LEFT);
		TUIManager.addFontSize(lblUserInfo, 2);

		// ���ݎ����̕\��
		lblTime = new TLabel();
		lblTime.setText(DateUtil.toYMDHMString(new Date()));
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		TUIManager.addFontSize(lblTime, 2);

		infoPanel.add(lblCompanyName);
		infoPanel.add(lblProgramName);
		infoPanel.add(lblUserInfo);
		infoPanel.add(lblTime);

		// �{�^���p�l��
		TPanel buttonPanel = new TPanel();
		FlowLayout fLayout = new FlowLayout(5);
		fLayout.setAlignment(FlowLayout.RIGHT);
		buttonPanel.setLayout(fLayout);
		buttonPanel.setBackground(headerColor);

		buttonPanel.setSize(new Dimension(80, buttonPanel.getHeight()));
		buttonPanel.setPreferredSize(new Dimension(80, buttonPanel.getHeight()));
		buttonPanel.setMinimumSize(new Dimension(80, buttonPanel.getHeight()));
		buttonPanel.setMaximumSize(new Dimension(80, buttonPanel.getHeight()));

		// �p�X���[�h�ύX�{�^���̐ݒ�
		btnPassword = new TButton();
		btnPassword.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/pass.png"));
		btnPassword.setPreferredSize(new Dimension(30, 30));
		btnPassword.setMinimumSize(new Dimension(30, 30));
		btnPassword.setMaximumSize(new Dimension(30, 30));
		btnPassword.setBackground(headerColor);
		btnPassword.setBorderPainted(false);
		btnPassword.setOpaque(false);
		btnPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.openDialog();
			}
		});

		// ���O�A�E�g�{�^���̐ݒ�
		btnLogout = new TButton();
		btnLogout.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/logout.png"));
		btnLogout.setPreferredSize(new Dimension(30, 30));
		btnLogout.setMinimumSize(new Dimension(30, 30));
		btnLogout.setMaximumSize(new Dimension(30, 30));
		btnLogout.setBackground(headerColor);
		btnLogout.setBorderPainted(false);
		btnLogout.setOpaque(false);
		btnLogout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.logout();
			}
		});

		buttonPanel.add(btnPassword);
		buttonPanel.add(btnLogout);

		// ��Аݒ�F�\�����C���ݒ�
		TPanel pnlLine = new TPanel();
		pnlLine.setPreferredSize(new Dimension(pnlLine.getWidth(), 3));
		pnlLine.setMinimumSize(new Dimension(pnlLine.getWidth(), 3));
		pnlLine.setMaximumSize(new Dimension(pnlLine.getWidth(), 3));

		// �w�i�F�̎擾
		int[] RGBcode = Util.toRGBColorCode(userInfo.getCompanyInfo().getForeColor());
		pnlLine.setBackground(new Color(RGBcode[0], RGBcode[1], RGBcode[2]));

		// �w�b�_�[�p�l���̐ݒ�
		panel.add(lblLogo, BorderLayout.WEST);
		panel.add(infoPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.EAST);

		headerPanel.add(panel, BorderLayout.CENTER);
		headerPanel.add(pnlLine, BorderLayout.SOUTH);
	}

	/**
	 * Applet�����ۂɌĂ΂��.
	 * 
	 * @see java.applet.Applet#stop()
	 */
	@Override
	public void destroy() {
		super.destroy();

		ClientLogger.debug("Applet destroy");

		this.ctrl.logoutForClose();

		System.gc();
	}

	/**
	 * �����\���X���b�h���X�^�[�g����
	 */
	public void run() {
		Thread me = Thread.currentThread();
		while (timethread == me) {
			try {
				Thread.sleep(1000);

				changeTime();
			} catch (InterruptedException e) {
				// �ʒm����
			}
		}
	}

	/**
	 * TOP��ʃw�b�_�̎������ŐV�ɍX�V
	 */
	public void changeTime() {
		date = new Date();
		// Get the date to print at the bottom
		strTime = DateUtil.toYMDHMString(date);

		if (lblTime != null) {
			lblTime.setText(strTime);
		}
	}

	/**
	 * �e�t���[���̎擾
	 * 
	 * @return �e�t���[��
	 */
	public Frame getParentFrame() {
		for (Container p = this.getParent(); p != null; p = p.getParent()) {
			if (p instanceof Frame) {
				return (Frame) p;
			}
		}
		return null;
	}
}
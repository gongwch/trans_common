package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTabbedPane;

/**
 * ���C�����
 * 
 * @author AIS
 */
public class TMain extends TFrame {

	/** �w�b�_�[�̈� */
	public TPanel pnlHeader = null;

	/** ��Ђ�\���� */
	public TColorPanel pnlCompanyColor = null;

	/** ���S */
	public TLabel logo;

	/** LF&�����̈� */
	public TPanel pnlLfAndTime;

	/** ��Ж� */
	public TLabel lblCompanyName;

	/** ���O�C�����[�U�[�̏�������ƃ��[�U�[�� */
	public TLabel lblUserName;

	/** �����\�����x�� */
	public TLabel lblTime = null;

	/** LookAndFeel�p�l�� */
	public TPanel pnlLf = null;

	/** �v�����^�[�ݒ�{�^�� */
	protected TButton btnPrintSetup = null;

	/** �p�X���[�h�ύX�{�^�� */
	public TButton btnPassword = null;

	/** �o�[�W�����{�^�� */
	public TButton btnVersion = null;

	/** ���O�A�E�g�{�^�� */
	public TButton btnLogOut = null;

	/** L&F�{�^���iBlue�j */
	public TButton btnBlue = null;

	/** L&F�{�^���iPink�j */
	public TButton btnPink = null;

	/** L&F�{�^���iOrange�j */
	public TButton btnOrange = null;

	/** L&F�{�^���iGreen�j */
	public TButton btnGreen = null;

	/** L&F�{�^���iGray�j */
	public TButton btnGray = null;

	/** L&F�{�^���iWhite�j */
	public TButton btnWhite = null;

	/** L&F�{�^���iType1�j */
	public TButton btnType1 = null;

	/** L&F�{�^���iType2�j */
	public TButton btnType2 = null;

	/** L&F�{�^���iType3�j */
	public TButton btnType3 = null;

	/** �t�H���g�T�C�Y�w��R���{ */
	public TLabelComboBox ctrlFontSize = null;

	/** �{�f�B�̈� */
	public TPanel pnlBody = null;

	/** �t�b�^�[�̈� */
	public TPanel pnlFooter = null;

	/** �{�f�B�̈�̃��j���[�ƍ�Ɨ̈敪�� */
	public TSplitPane spBody = null;

	/** ���j���[�̈� */
	public TPanel pnlMenu = null;

	/** ���j���[�^�u */
	public TLeftColorTabbedPane menuTab = null;

	/** �T�u�V�X�e�����̃^�u�ɕ\������p�l�� */
	public List<TPanel> menuPanels = null;

	/** �T�u�V�X�e�����̃��j���[�c���[ */
	public List<TTree> menuTrees = null;

	/** ���C���̈� */
	public TPanel pnlMain = null;

	/** ���C���^�u(�v���O�����ʂ̃^�u) */
	public TTabbedPane mainTab = null;

	/** ���C����ʏ������pINSTANCE���X�g */
	public List<TMainInitialInterface> instanceList;

	/**
	 * �R���X�g���N�^�[
	 */
	public TMain() {

		// ������
		this(null);

	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param instanceList
	 */
	public TMain(List<TMainInitialInterface> instanceList) {
		this.instanceList = instanceList;

		// ������
		init();

	}

	/**
	 * ��������
	 */
	public void init() {

		try {
			// �w����ʐ���
			initComponents();

			allocateComponents();

			initFrame();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		pnlHeader = new TPanel();
		lblTime = new TLabel();
		btnPrintSetup = new TButton();
		btnPassword = new TButton();
		btnVersion = new TButton();
		btnLogOut = new TButton();
		pnlLf = new TPanel();
		btnBlue = new TButton();
		btnPink = new TButton();
		btnOrange = new TButton();
		btnGreen = new TButton();
		btnGray = new TButton();
		btnWhite = new TButton();
		btnType1 = new TButton();
		btnType2 = new TButton();
		btnType3 = new TButton();
		ctrlFontSize = new TLabelComboBox();

		pnlCompanyColor = new TColorPanel();
		pnlBody = new TPanel();
		pnlFooter = new TPanel();
		pnlMenu = new TPanel();
		pnlMain = new TPanel();
		spBody = new TSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlMenu, pnlMain);
		menuTab = new TLeftColorTabbedPane();

		menuPanels = new ArrayList<TPanel>();
		menuTrees = new ArrayList<TTree>();
		mainTab = new TTabbedPane();
	}

	/**
	 * �R���|�[�l���g��z�u
	 */
	public void allocateComponents() {

		setLayout(new GridBagLayout());

		// �w�b�_�[�z�u
		allocateHeader();

		// ��Ɨ̈�̔z�u
		allocateBody();

		// �t�b�^�[�̈�̔z�u
		allocateFooter();
	}

	/**
	 * �w�b�_�[�̈�̔z�u
	 */
	public void allocateHeader() {

		GridBagConstraints gc = new GridBagConstraints();
		pnlCompanyColor.setLayout(new GridBagLayout());
		pnlCompanyColor.setMinimumSize(new Dimension(0, 50));
		pnlCompanyColor.setPreferredSize(new Dimension(0, 50));
		pnlCompanyColor.setBackground(Color.white);
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlCompanyColor, gc);

		pnlHeader.setLayout(new GridBagLayout());
		pnlHeader.setOpaque(false);
		pnlHeader.setBackground(Color.white);
		pnlHeader.setPreferredSize(new Dimension(0, 50));
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlCompanyColor.add(pnlHeader, gc);

		pnlHeader.setLayout(new GridBagLayout());

		// ���S
		logo = new TLabel();

		gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.WEST;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 20, 0, 0);
		pnlHeader.add(logo, gc);

		// ��Ж�
		lblCompanyName = new TLabel();
		TUIManager.addFontSize(lblCompanyName, 2);
		lblCompanyName.setPreferredSize(new Dimension(400, 20));
		lblCompanyName.setForeground(Color.BLACK);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.insets = new Insets(10, 20, 2, 0);
		gc.anchor = GridBagConstraints.WEST;
		pnlHeader.add(lblCompanyName, gc);

		// ���O�C�����[�U�[�̏�������ƃ��[�U�[��
		lblUserName = new TLabel();
		TUIManager.addFontSize(lblUserName, 2);
		lblUserName.setPreferredSize(new Dimension(400, 20));
		lblUserName.setForeground(Color.BLACK);
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(2, 20, 2, 0);
		gc.anchor = GridBagConstraints.WEST;
		pnlHeader.add(lblUserName, gc);

		pnlLfAndTime = new TPanel();
		pnlLfAndTime.setOpaque(false);
		pnlLfAndTime.setBackground(Color.white);
		pnlLfAndTime.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 0;
		gc.gridheight = 2;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(pnlLfAndTime, gc);

		// �^�C��
		lblTime.setForeground(Color.BLACK);
		TUIManager.addFontSize(lblTime, 2);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(5, 0, 3, 0);
		pnlLfAndTime.add(lblTime, gc);

		pnlLf.setLayout(new GridBagLayout());
		pnlLf.setOpaque(false);
		pnlLf.setBackground(Color.white);
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLfAndTime.add(pnlLf, gc);

		// L&F�{�^���iBlue�j
		btnBlue.setPreferredSize(new Dimension(15, 15));
		btnBlue.setMinimumSize(new Dimension(20, 20));
		btnBlue.setMaximumSize(new Dimension(20, 20));
		btnBlue.setBorderPainted(false);
		btnBlue.setOpaque(false);
		btnBlue.setBackground(new Color(176, 196, 222));
		btnBlue.setToolTipText("blue");
		btnBlue.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(0, 0, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnBlue, gc);

		// L&F�{�^���iPink�j
		btnPink.setPreferredSize(new Dimension(15, 15));
		btnPink.setMinimumSize(new Dimension(20, 20));
		btnPink.setMaximumSize(new Dimension(20, 20));
		btnPink.setBorderPainted(false);
		btnPink.setOpaque(false);
		btnPink.setBackground(new Color(255, 192, 203));
		btnPink.setToolTipText("pink");
		btnPink.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnPink, gc);

		// L&F�{�^���iOrange�j
		btnOrange.setPreferredSize(new Dimension(15, 15));
		btnOrange.setMinimumSize(new Dimension(20, 20));
		btnOrange.setMaximumSize(new Dimension(20, 20));
		btnOrange.setBorderPainted(false);
		btnOrange.setOpaque(false);
		btnOrange.setBackground(new Color(255, 222, 173));
		btnOrange.setToolTipText("orange");
		btnOrange.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 2;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnOrange, gc);

		// L&F�{�^���iGreen�j
		btnGreen.setPreferredSize(new Dimension(15, 15));
		btnGreen.setMinimumSize(new Dimension(20, 20));
		btnGreen.setMaximumSize(new Dimension(20, 20));
		btnGreen.setBorderPainted(false);
		btnGreen.setOpaque(false);
		btnGreen.setBackground(new Color(152, 251, 152));
		btnGreen.setToolTipText("green");
		btnGreen.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 3;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnGreen, gc);

		// L&F�{�^���iGray�j
		btnGray.setPreferredSize(new Dimension(15, 15));
		btnGray.setMinimumSize(new Dimension(20, 20));
		btnGray.setMaximumSize(new Dimension(20, 20));
		btnGray.setBorderPainted(false);
		btnGray.setOpaque(false);
		btnGray.setBackground(new Color(211, 211, 211));
		btnGray.setToolTipText("gray");
		btnGray.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 4;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnGray, gc);

		// L&F�{�^���iWhite�j
		btnWhite.setPreferredSize(new Dimension(15, 15));
		btnWhite.setMinimumSize(new Dimension(20, 20));
		btnWhite.setMaximumSize(new Dimension(20, 20));
		btnWhite.setBorderPainted(false);
		btnWhite.setOpaque(false);
		btnWhite.setBackground(new Color(224, 255, 255));
		btnWhite.setToolTipText("white");
		btnWhite.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 5;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnWhite, gc);

		// L&F�{�^���i���j���[Type1�j
		btnType1.setIcon(ResourceUtil.getImage(this.getClass(), "images/menuType1.png"));
		btnType1.setPreferredSize(new Dimension(16, 16));
		btnType1.setMinimumSize(new Dimension(20, 20));
		btnType1.setMaximumSize(new Dimension(20, 20));
		btnType1.setBorderPainted(false);
		btnType1.setOpaque(false);
		btnType1.setBackground(new Color(224, 255, 255));
		btnType1.setToolTipText("type1");
		btnType1.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 6;
		gc.gridy = 0;
		gc.insets = new Insets(0, 15, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnType1, gc);

		// L&F�{�^���i���j���[Type2�j
		btnType2.setIcon(ResourceUtil.getImage(this.getClass(), "images/menuType2.png"));
		btnType2.setPreferredSize(new Dimension(16, 16));
		btnType2.setMinimumSize(new Dimension(20, 20));
		btnType2.setMaximumSize(new Dimension(20, 20));
		btnType2.setBorderPainted(false);
		btnType2.setOpaque(false);
		btnType2.setBackground(new Color(224, 255, 255));
		btnType2.setToolTipText("type2");
		btnType2.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 7;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnType2, gc);

		// L&F�{�^���i���j���[Type3�j
		btnType3.setIcon(ResourceUtil.getImage(this.getClass(), "images/menuType3.png"));
		btnType3.setPreferredSize(new Dimension(16, 16));
		btnType3.setMinimumSize(new Dimension(20, 20));
		btnType3.setMaximumSize(new Dimension(20, 20));
		btnType3.setBorderPainted(false);
		btnType3.setOpaque(false);
		btnType3.setBackground(new Color(224, 255, 255));
		btnType3.setToolTipText("type3");
		btnType3.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = 8;
		gc.gridy = 0;
		gc.insets = new Insets(0, 7, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlLf.add(btnType3, gc);

		int gridx = getIconStartIndex();

		{
			if (ClientConfig.isFlagOn("trans.ui.change.font.size.enabled")) {
				// Header UI Font Size

				ctrlFontSize.setLangMessageID("SIZE");
				ctrlFontSize.setEditable(false);
				ctrlFontSize.setLabelSize(35);
				ctrlFontSize.setComboSize(55);
				{
					// �ύX�\�T�C�Y
					ctrlFontSize.addTextValueItem(10, "10pt");
					ctrlFontSize.addTextValueItem(11, "11pt");
					ctrlFontSize.addTextValueItem(12, "12pt");
					ctrlFontSize.addTextValueItem(13, "13pt");
					ctrlFontSize.addTextValueItem(14, "14pt");
					ctrlFontSize.addTextValueItem(15, "15pt");
				}
				ctrlFontSize.setFocusable(false);
				ctrlFontSize.setToolTipText("UI FONT SIZE");
				for (int i = 0; i < ctrlFontSize.getComboBox().getMouseWheelListeners().length; i++) {
					MouseWheelListener l = ctrlFontSize.getComboBox().getMouseWheelListeners()[i];
					ctrlFontSize.getComboBox().removeMouseWheelListener(l);
				}

				int fontSize = getUIFontSize();
				ctrlFontSize.setSelectedItemValue(fontSize);

				gc = new GridBagConstraints();
				gc.gridx = gridx++;
				gc.gridheight = 2;
				gc.insets = new Insets(0, 10, 2, 0);
				gc.anchor = GridBagConstraints.SOUTHEAST;
				pnlHeader.add(ctrlFontSize, gc);
			}
		}

		// �v�����^�[�ݒ�{�^��
		btnPrintSetup.setIcon(ResourceUtil.getImage(this.getClass(), "images/printer.png"));
		btnPrintSetup.setPreferredSize(new Dimension(30, 30));
		btnPrintSetup.setMinimumSize(new Dimension(30, 30));
		btnPrintSetup.setMaximumSize(new Dimension(30, 30));
		btnPrintSetup.setBorderPainted(false);
		btnPrintSetup.setOpaque(false);
		btnPrintSetup.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = gridx++;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 10, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(btnPrintSetup, gc);

		{
			// �J�X�^�}�C�Y�p�{�^���ǉ�

			if (this.instanceList != null) {
				for (TMainInitialInterface clazz : this.instanceList) {
					List<TButton> btnList = clazz.getAddonButtonList();
					if (btnList == null || btnList.isEmpty()) {
						continue;
					}

					for (TButton btn : btnList) {

						gc = new GridBagConstraints();
						gc.gridx = gridx++;
						gc.gridheight = 2;
						gc.insets = new Insets(0, 5, 2, 0);
						gc.anchor = GridBagConstraints.SOUTHEAST;
						pnlHeader.add(btn, gc);

					}
				}
			}
		}

		// �p�X���[�h�ύX�{�^��
		btnPassword.setIcon(ResourceUtil.getImage(this.getClass(), "images/pass.png"));
		btnPassword.setPreferredSize(new Dimension(30, 30));
		btnPassword.setMinimumSize(new Dimension(30, 30));
		btnPassword.setMaximumSize(new Dimension(30, 30));
		btnPassword.setBorderPainted(false);
		btnPassword.setOpaque(false);
		btnPassword.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = gridx++;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 5, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(btnPassword, gc);

		// �p�X���[�h�ύX�{�^��
		btnVersion.setIcon(ResourceUtil.getImage(this.getClass(), "images/version.png"));
		btnVersion.setPreferredSize(new Dimension(30, 30));
		btnVersion.setMinimumSize(new Dimension(30, 30));
		btnVersion.setMaximumSize(new Dimension(30, 30));
		btnVersion.setBorderPainted(false);
		btnVersion.setOpaque(false);
		btnVersion.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = gridx++;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 5, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(btnVersion, gc);

		// ���O�A�E�g�{�^��
		btnLogOut.setIcon(ResourceUtil.getImage(this.getClass(), "images/logout.png"));
		btnLogOut.setPreferredSize(new Dimension(30, 30));
		btnLogOut.setMinimumSize(new Dimension(30, 30));
		btnLogOut.setMaximumSize(new Dimension(30, 30));
		btnLogOut.setBorderPainted(false);
		btnLogOut.setOpaque(false);
		btnLogOut.setFocusable(false);

		gc = new GridBagConstraints();
		gc.gridx = gridx++;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 5, 2, 20);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(btnLogOut, gc);
	}

	/**
	 * @return UI�f�t�H���g�t�H���g�T�C�Y
	 */
	protected int getUIFontSize() {
		return (int) ClientConfig.getClientFontSize();
	}

	/**
	 * @return �E��̃A�C�R���\��GridX
	 */
	protected int getIconStartIndex() {
		return 9;
	}

	/**
	 * ��Ɨ̈�̔z�u
	 */
	public void allocateBody() {

		// ��Ɨ̈�z�u
		pnlBody.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBody, gc);

		// ��Ɨ̈�����j���[�̈�ƃ��C���̈�ɕ���
		pnlMenu.setMinimumSize(new Dimension(0, 0));
		pnlMain.setMinimumSize(new Dimension(0, 0));
		spBody.setContinuousLayout(true);
		spBody.setDividerLocation(200);
		InputMap im = spBody.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).getParent();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), null);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), null);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(spBody, gc);

		// ���j���[�^�u�̕��ݒ�
		pnlMenu.setLayout(new GridBagLayout());

		menuTab.switchMode();

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 5, 0, 0);
		pnlMenu.add(menuTab, gc);

		// ���C���^�u�̕��ݒ�
		mainTab.switchMode();

		pnlMain.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlMain.add(mainTab, gc);
	}

	/**
	 * �t�b�^�[�̈�̔z�u
	 */
	public void allocateFooter() {

		GridBagConstraints gc = new GridBagConstraints();
		pnlFooter.setPreferredSize(new Dimension(0, 10));
		gc.gridy = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(pnlFooter, gc);

	}

}
package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * �v���O�����E�C���h�E <br>
 * �i���d���O�C����p�j
 * 
 * @author hosoya
 */
public class TAppletWindow extends TPanel implements Runnable {

	/** �t���[���ɕ\������x�[�X�p�l�� */
	protected TPanel basePanel;

	/** �w�b�_�[�\���p�l�� */
	protected TPanel headerPanel;

	/** �����p�l�� */
	public TPanel detailPanel;

	/** ��Аݒ�F �\�����C���p�l�� */
	protected TPanel footerPanel;

	/** �v���O�������\�����x�� */
	public TLabel lblProgramName;

	/** �����\�����x�� */
	TLabel lblTime;

	/** ���O�A�E�g�{�^�� */
	TButton btnLogout;

	/** �p�X���[�h�ύX�{�^�� */
	TButton btnPassword;

	/** ���j���[�\���^�u�y�C�� */
	JTabbedPane tabbedPane;

	/** �����\���X���b�h */
	volatile Thread timethread;

	/** �����\���ؑ֗pString */
	private String strTime;

	/** �����\���ؑ֗pDate */
	private Date date;

	/** �V�X�e�����S */
	Icon logo;

	/**
	 * �R���X�g���N�^
	 */
	public TAppletWindow() {

		// �p�l�������ݒ�
		initComponents();

		// ���Ԃ̕ύX
		timethread = new Thread(this);
		timethread.start();
	}

	/**
	 * �p�l�������ݒ�
	 */
	protected void initComponents() {

		this.setLayout(new GridLayout(1, 1));

		// ��Ճp�l��
		basePanel = new TPanel();
		basePanel.setLayout(new BorderLayout(0, 0));
		basePanel.setBackground(new Color(240, 240, 240));

		// �w�b�_�p�l��
		headerPanel = new TPanel();
		headerPanel.setLayout(new BorderLayout(5, 0));
		headerPanel.setBackground(new Color(255, 255, 255));

		makeHeaderPanel(); // �ڍ׍\�z

		headerPanel.setSize(new Dimension(headerPanel.getWidth(), logo.getIconHeight() + 10));
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), logo.getIconHeight() + 10));
		headerPanel.setMinimumSize(new Dimension(headerPanel.getWidth(), logo.getIconHeight() + 10));
		headerPanel.setMaximumSize(new Dimension(headerPanel.getWidth(), logo.getIconHeight() + 10));

		// �����p�l��
		detailPanel = new TPanel();
		detailPanel.setLayout(new BorderLayout(0, 0));
		detailPanel.setBackground(new Color(240, 240, 240));

		// �t�b�^
		footerPanel = new TPanel();
		footerPanel.setPreferredSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMinimumSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMaximumSize(new Dimension(footerPanel.getWidth(), 3));

		// �t�b�^ ��Аݒ�F�\�����C���ݒ�
		int[] rgbCode = Util.toRGBColorCode(TClientLoginInfo.getInstance().getCompanyInfo().getForeColor());
		footerPanel.setBackground(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));

		// �p�l���\��t��
		basePanel.add(headerPanel, BorderLayout.NORTH);
		basePanel.add(detailPanel, BorderLayout.CENTER);
		basePanel.add(footerPanel, BorderLayout.SOUTH);
		this.add(basePanel);
	}

	/**
	 * �w�b�_�[�ݒ�
	 */
	protected void makeHeaderPanel() {

		TPanel panel = new TPanel();
		panel.setLayout(new BorderLayout(10, 5));
		panel.setBackground(new Color(255, 255, 255));

		// �C���[�W���x���̐ݒ�
		TLabel lblLogo = new TLabel();
		logo = ResourceUtil.getImage(TAppletMain.class, "images/Series" + ClientConfig.getImageSuffix() + ".png");
		lblLogo.setIcon(logo);
		lblLogo.setPreferredSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));
		lblLogo.setMinimumSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));
		lblLogo.setMaximumSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));

		// ���p�l��
		TPanel infoPanel = new TPanel();
		infoPanel.setLayout(new GridLayout(2, 2));
		infoPanel.setBackground(new Color(255, 255, 255));

		// ���[�U�[���̎擾
		TUserInfo userInfo = TClientLoginInfo.getInstance();

		// �v���O�������̕\��
		lblProgramName = new TLabel();
		lblProgramName.setHorizontalAlignment(SwingConstants.RIGHT);
		TUIManager.addFontSize(lblProgramName, 2);

		// ��Ж��̕\��
		TLabel lblCompanyName = new TLabel();
		lblCompanyName.setText(userInfo.getCompanyInfo().getCompanyName());
		lblCompanyName.setHorizontalAlignment(SwingConstants.LEFT);
		TUIManager.addFontSize(lblCompanyName, 2);

		// ���[�U�[���̕\��
		TLabel lblUserInfo = new TLabel();
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
		buttonPanel.setBackground(new Color(255, 255, 255));

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
		btnPassword.setBackground(new Color(255, 255, 255));
		btnPassword.setBorderPainted(false);
		btnPassword.setOpaque(false);
		btnPassword.setVisible(false); // ���C�A�E�g������Ȃ��悤�Ƀ{�^���͉B���Ďc��

		// ���O�A�E�g�{�^���̐ݒ�
		btnLogout = new TButton();
		btnLogout.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/logout.png"));
		btnLogout.setPreferredSize(new Dimension(30, 30));
		btnLogout.setMinimumSize(new Dimension(30, 30));
		btnLogout.setMaximumSize(new Dimension(30, 30));
		btnLogout.setBackground(new Color(255, 255, 255));
		btnLogout.setBorderPainted(false);
		btnLogout.setOpaque(false);
		btnLogout.setVisible(false);// ���C�A�E�g������Ȃ��悤�Ƀ{�^���͉B���Ďc��

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
	private void changeTime() {
		date = new Date();
		// Get the date to print at the bottom
		strTime = DateUtil.toYMDHMString(date);

		if (lblTime != null) {
			lblTime.setText(strTime);
		}
	}

}

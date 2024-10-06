package jp.co.ais.trans2.common.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;

/**
 * ���O�C�����
 * 
 * @author AIS
 */
public class TLogin extends TUndecoratedFrame {

	/** �w�i���x�� */
	public JLabel backGroundImageLabel;

	/** ��� */
	public TLabel lblRemark;

	/** ��ЃR�[�h */
	public TLabelField txCompanyCode;

	/** ���[�U�[�R�[�h */
	public TLabelField txUserCode;

	/** �p�X���[�h */
	public TLabelPasswordField txPassword;

	/** �v���O���X�o�[ */
	public JProgressBar progressBar;

	/** ���O�C���{�^�� */
	public TButton btnLogin;

	/** �N���A�{�^�� */
	public TButton btnClear;

	/** ����{�^�� */
	public TButton btnClose;

	/**
	 * ��������
	 */
	public void init() {

		// �R���|�[�l���g������
		initComponents();

		// �R���|�[�l���g�z�u
		allocateComponents();

		// �^�u���ݒ�
		setTabIndex();

		// �t�H���g�F�ݒ�
		initFontColor();

		initFrame();

	}

	/**
	 * �R���|�[�l���g������������
	 */
	public void initComponents() {
		backGroundImageLabel = new JLabel(getBackGroundImage());
		lblRemark = new TLabel();
		txCompanyCode = new TLabelField();
		txUserCode = new TLabelField();
		txPassword = new TLabelPasswordField();
		progressBar = new JProgressBar();
		btnLogin = new TButton();
		btnClear = new TButton();
		btnClose = new TButton();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	public void allocateComponents() {

		setLayout(new GridBagLayout());

		// �w�i
		backGroundImageLabel.setLayout(null);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		add(backGroundImageLabel, gc);

		int x = 5;
		int y = 0;

		// �������
		lblRemark.setSize(getWidth(), 20);
		lblRemark.setLocation(x, y);
		backGroundImageLabel.add(lblRemark);

		int labelWidth = getIntProperty("login.input.label.width", 200);
		int startInputX = getIntProperty("login.input.start.x", 180);
		int startInputY = getIntProperty("login.input.start.y", 110);
		int startButtonX = getIntProperty("login.button.start.x", 155);
		int startButtonY = getIntProperty("login.button.start.y", 210);

		String lblCompanyWord = getStringProperty("login.input.company.label", "Company Code");
		String lblUserWord = getStringProperty("login.input.user.label", "User Code");
		String lblPasswordWord = getStringProperty("login.input.pw.label", "Password");

		String btnLoginWord = getStringProperty("login.button.login.label", "LOGIN");
		String btnClearWord = getStringProperty("login.button.clear.label", "CLEAR");
		String btnCloseWord = getStringProperty("login.button.close.label", "CLOSE");

		x = startInputX;
		y = startInputY;

		// ��ЃR�[�h
		txCompanyCode.getLabel().setForeground(Color.white);
		txCompanyCode.getLabel().setFont(txCompanyCode.getLabel().getFont().deriveFont(Font.BOLD));
		txCompanyCode.setOpaque(false);
		txCompanyCode.getLabel().setText(lblCompanyWord);
		txCompanyCode.setImeMode(false);
		txCompanyCode.setMaxLength(10);
		txCompanyCode.setSize(labelWidth, 20);
		txCompanyCode.setFieldSize(80);
		txCompanyCode.setLocation(x, y);
		backGroundImageLabel.add(txCompanyCode);

		y += 30;

		// ���[�U�[�R�[�h
		txUserCode.getLabel().setForeground(Color.white);
		txUserCode.getLabel().setFont(txUserCode.getLabel().getFont().deriveFont(Font.BOLD));
		txUserCode.setOpaque(false);
		txUserCode.getLabel().setText(lblUserWord);
		txUserCode.setImeMode(false);
		txUserCode.setMaxLength(10);
		txUserCode.setSize(labelWidth, 20);
		txUserCode.setFieldSize(80);
		txUserCode.setLocation(x, y);
		backGroundImageLabel.add(txUserCode);

		y += 30;

		// �p�X���[�h
		txPassword.getLabel().setForeground(Color.white);
		txPassword.getLabel().setFont(txPassword.getLabel().getFont().deriveFont(Font.BOLD));
		txPassword.setOpaque(false);
		txPassword.getLabel().setText(lblPasswordWord);
		txPassword.setSize(labelWidth, 20);
		txPassword.setFieldSize(80);
		txPassword.setLocation(x, y);
		backGroundImageLabel.add(txPassword);

		x = startButtonX;
		y = startButtonY;

		// ���O�C���{�^��
		btnLogin.setOpaque(false);
		btnLogin.setText(btnLoginWord);
		btnLogin.setSize(20, 80);
		btnLogin.setLocation(x, y);
		backGroundImageLabel.add(btnLogin);

		x += btnLogin.getWidth() + 10;

		// �N���A�{�^��
		btnClear.setOpaque(false);
		btnClear.setText(btnClearWord);
		btnClear.setSize(20, 80);
		btnClear.setLocation(x, y);
		backGroundImageLabel.add(btnClear);

		x += btnClear.getWidth() + 10;

		// ����{�^��
		btnClose.setOpaque(false);
		btnClose.setText(btnCloseWord);
		btnClose.setSize(20, 80);
		btnClose.setLocation(x, y);
		backGroundImageLabel.add(btnClose);

		x = btnLogin.getX();
		y += 25;
		int w = btnClose.getX() + btnClose.getWidth() - x;

		TGuiUtil.setComponentSize(progressBar, w, 20);
		progressBar.setVisible(false);
		progressBar.setStringPainted(true);
		progressBar.setLocation(x, y);
		backGroundImageLabel.add(progressBar);

	}

	/**
	 * tab����ݒ肷��
	 */
	public void setTabIndex() {
		int i = 0;
		txCompanyCode.setTabControlNo(i++);
		txUserCode.setTabControlNo(i++);
		txPassword.setTabControlNo(i++);
		btnLogin.setTabControlNo(i++);
		btnLogin.setEnterFocusable(true);
		btnClear.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

	/**
	 * �w�i�摜���擾����
	 * 
	 * @return �w�i�摜
	 */
	protected Icon getBackGroundImage() {
		Icon logo = ResourceUtil.getImage(this.getClass(), getImagePath());
		return logo;
	}

	/**
	 * �w�i�摜���擾����
	 * 
	 * @return �w�i�摜
	 */
	@Override
	protected String getImagePath() {
		String backGroundImageFilePath = "login.jpg";

		try {
			backGroundImageFilePath = ClientConfig.getProperty("login.background.image");
		} catch (Throwable ex) {
			// �����Ȃ�
		}
		return "images/" + backGroundImageFilePath;
	}

	/**
	 * ���x���t�H���g�F�ݒ�
	 */
	protected void initFontColor() {
		try {
			String[] forecolor = ClientConfig.getProperty("login.label.forecolor").split(",");
			if (!Util.isNullOrEmpty(forecolor)) {
				Color color = Util.toColor(forecolor);
				txCompanyCode.getLabel().setForeground(color);
				txUserCode.getLabel().setForeground(color);
				txPassword.getLabel().setForeground(color);
			}
		} catch (Throwable ex) {
			// �����Ȃ�
		}

	}

	/**
	 * �ݒ�l�擾
	 * 
	 * @param key
	 * @param defaultValue
	 * @return �ݒ�l
	 */
	protected String getStringProperty(String key, String defaultValue) {
		String value = defaultValue;
		try {
			String valueProperty = ClientConfig.getProperty(key);
			if (!Util.isNullOrEmpty(valueProperty)) {
				value = valueProperty;
			}
		} catch (Throwable ex) {
			// �����Ȃ�
		}
		return value;
	}

	/**
	 * �ݒ�l�擾
	 * 
	 * @param key
	 * @param defaultValue
	 * @return �ݒ�l
	 */
	protected int getIntProperty(String key, int defaultValue) {
		int value = defaultValue;
		try {
			String valueProperty = ClientConfig.getProperty(key);
			if (!Util.isNullOrEmpty(valueProperty)) {
				value = Integer.parseInt(valueProperty);
			}
		} catch (Throwable ex) {
			// �����Ȃ�
		}
		return value;
	}

}

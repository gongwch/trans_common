package jp.co.ais.trans.common.config;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * �N���C�A���g�T�C�h�ݒ���
 */
public final class ClientConfig {

	/** �Œ�J���R�[�h(����) */
	public static final String DEV_CODE = "TRANS";

	/** �f�t�H���g�t�H���g�T�C�Y */
	public static final int DEFAULT_FONT_SIZE = 12;

	/** �N���C�A���g�T�C�h�ݒ� */
	public static ResourceBundle bundle = ResourceBundle.getBundle("client");

	/** �����O�C����Ж�(�J���p) */
	public static String kariComp;

	/** �����O�C�����[�U�[��(�J���p) */
	public static String kariUser;

	/** AppletViewer���ݒ�(�J���p) */
	public static int appletWidht;

	/** �ڑ��v���g�R��(�J���p) */
	public static String protocol;

	/** �ڑ���(�J���p) */
	public static String host;

	/** �ڑ��|�[�g(�J���p) */
	public static String port;

	/** �v���W�F�N�g��(�J���p) */
	public static String project;

	/** AppletViewer�����ݒ�(�J���p) */
	public static int appletHeight;

	/** �v���O�����R�[�h�\���L��(�J���p) */
	public static boolean isShowPrgCode = false;

	/** URL���[�g�f�B���N�g�� */
	public static String rootDirectory;

	/** �V�X�e������ */
	public static String systemName;

	/** �摜�t�@�C���T�t�B�b�N�X */
	public static String imageSuffix = "";

	/** �N���C�A���g��ʃt�H���g */
	public static String clientFont;

	/** �N���C�A���g��ʃt�H���g�T�C�Y */
	public static float clientFontSize;

	/** TTable�J���[�ݒ� */
	public static String[] labelback;

	/** TTable�J���[�ݒ� */
	public static String[] labelfont;

	/** TTable�J���[�ݒ� */
	public static String[] cellback1;

	/** TTable�J���[�ݒ� */
	public static String[] cellback2;

	/** ���C���p�l���F�w�b�_�[���J���[�ݒ� */
	public static String headerBackColor;

	/** �}���`�E�B���h�E�N������� */
	public static int maxWindowCount = 0;

	/** ���j���[�T�C�Y���ςɂ��邩�ǂ��� */
	public static boolean isMenuSplit;

	/** �ڑ���A�h���X */
	public static String baseAddress;

	static {

		// �J���p�ݒ� ---

		try {
			// �����O�C�����
			kariComp = bundle.getString("login.comp.code");
			kariUser = bundle.getString("login.user.code");
		} catch (MissingResourceException e) {
			//
		}

		try {
			// �v���O�����R�[�h�\��
			isShowPrgCode = Boolean.valueOf(bundle.getString("show.prg.code"));
		} catch (MissingResourceException e) {
			//
		}

		try {
			// �v���O�����R�[�h�\��
			appletWidht = Integer.parseInt(bundle.getString("applet.widht"));
			appletHeight = Integer.parseInt(bundle.getString("applet.height"));

		} catch (Exception e) {
			appletWidht = 1010;
			appletHeight = 644;
		}

		// �ڑ���ݒ� ---

		try {
			// �v���g�R���ݒ�
			protocol = bundle.getString("http.protocol");

		} catch (MissingResourceException e) {
			// �v���g�R���ݒ肪�����ꍇ�Ahttp���f�t�H���g
			protocol = "http";
		}

		try {
			host = bundle.getString("http.host");
			port = bundle.getString("http.port");
			project = bundle.getString("http.project");

		} catch (MissingResourceException e) {
			// Applet�p
		}

		// ���[�g�f�B���N�g��
		rootDirectory = bundle.getString("root.url.directory");

		// �V�X�e���֘A�ݒ� ---

		try {
			// �V�X�e������
			systemName = bundle.getString("trans.system.name");

		} catch (MissingResourceException e) {
			systemName = DEV_CODE;
		}

		// �V�X�e�����̂��J���R�[�h���̂ƈقȂ�ꍇ�A�摜����������ύX����.
		if (!DEV_CODE.equals(systemName)) {
			imageSuffix = "_" + systemName;
		}

		try {
			// �}���`�v���O�����E�C���h�E
			String multilogin = bundle.getString("trans.system.multilogin");

			if (Util.isNumber(multilogin)) {
				maxWindowCount = Integer.parseInt(multilogin);

			} else if (BooleanUtil.toBoolean(multilogin)) {
				// true�̏ꍇ�́A1���
				maxWindowCount = 1;
			}

		} catch (MissingResourceException e) {
			maxWindowCount = 0;
		}

		try {
			// ���j���[�T�C�Y��
			isMenuSplit = BooleanUtil.toBoolean(ClientConfig.getProperty("menu.split"));

		} catch (Exception ex) {
			isMenuSplit = false;
		}

		// �t�H���g
		clientFont = bundle.containsKey("client.font") ? bundle.getString("client.font") : "Dialog";
		clientFontSize = bundle.containsKey("client.font.size") ? Float
			.parseFloat(bundle.getString("client.font.size")) : DEFAULT_FONT_SIZE;

		// �X�v���b�h�J���[�R�[�h
		labelback = bundle.getString("table.label.backcolor").split(",");
		labelfont = bundle.getString("table.label.fontcolor").split(",");
		cellback1 = bundle.getString("table.cell.backcolor1").split(",");
		cellback2 = bundle.getString("table.cell.backcolor2").split(",");

		try {
			// �w�b�_�[�w�i�F
			headerBackColor = bundle.getString("trans.header.backcolor");

		} catch (MissingResourceException e) {
			headerBackColor = "";
		}
	}

	/**
	 * Client�v���p�e�B����l���擾
	 * 
	 * @param key �L�[
	 * @return �ݒ�l
	 */
	public static String getProperty(String key) {
		return jp.co.ais.trans2.common.config.ClientConfig.getProperty(key);
	}

	/**
	 * Client�v���p�e�B����l(����)���擾<br>
	 * �J���}��؂�Őݒ肵�Ă����邱�Ƃ��O��
	 * 
	 * @param key �L�[
	 * @return �ݒ�l���X�g
	 */
	public static String[] getProperties(String key) {

		String[] values = StringUtil.split(getProperty(key), ",");

		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].trim();
		}

		return values;
	}

	/**
	 * �F�ݒ�̎擾
	 * 
	 * @param key
	 * @return �F�ݒ�
	 */
	public static Color getColor(String key) {
		try {
			String[] rgb = getProperties(key);
			return Util.toColor(rgb);
		} catch (Throwable ex) {
			return null;
		}
	}

	/**
	 * �F�ݒ�̎擾<br>
	 * null�̏ꍇ�A�f�t�H���g�l��Ԃ�
	 * 
	 * @param key
	 * @param defaultValue �f�t�H���g�l
	 * @return �F�ݒ�
	 */
	public static Color getColor(String key, Color defaultValue) {
		Color color = getColor(key);
		if (color == null) {
			return defaultValue;
		} else {
			return color;
		}
	}

	/**
	 * �����O�C����Ж�
	 * 
	 * @return kariComp
	 */
	public static String getDummyComp() {

		return kariComp;
	}

	/**
	 * �����O�C�����[�U�[��
	 * 
	 * @return kariUser
	 */
	public static String getDummyUser() {

		return kariUser;
	}

	/**
	 * �v���O�����R�[�h�\���L��(�J���p)
	 * 
	 * @return �\������ꍇtrue
	 */
	public static boolean isShowPrgramCode() {
		return isShowPrgCode;
	}

	/**
	 * AppletViewer���擾(�J���p)
	 * 
	 * @return ���T�C�Y
	 */
	public static int getAppletHeight() {
		return appletHeight;
	}

	/**
	 * AppletViewer�����ݒ�(�J���p)
	 * 
	 * @return �����T�C�Y
	 */
	public static int getAppletWidht() {
		return appletWidht;
	}

	/**
	 * �v���g�R��
	 * 
	 * @return �v���g�R��
	 */
	public static String getProtocol() {
		return protocol;
	}

	/**
	 * �z�X�g��
	 * 
	 * @return host
	 */
	public static String getHost() {
		return host;
	}

	/**
	 * �|�[�g
	 * 
	 * @return port
	 */
	public static String getPort() {
		return port;
	}

	/**
	 * �v���W�F�N�g
	 * 
	 * @return project
	 */
	public static String getProject() {
		return project;
	}

	/**
	 * ��{URL�A�h���X
	 * 
	 * @param url �A�h���X
	 */
	public static void setBaseAddress(String url) {
		baseAddress = url;
	}

	/**
	 * ��{URL�A�h���X
	 * 
	 * @return �A�h���X
	 */
	public static String getBaseAddress() {
		if (!Util.isNullOrEmpty(baseAddress)) {
			return baseAddress;
		}

		return protocol + "://" + host + ":" + port + "/" + project + "/";
	}

	/**
	 * URL���[�g�f�B���N�g��
	 * 
	 * @return ���[�g�f�B���N�g��
	 */
	public static String getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * �V�X�e�����̂̎擾
	 * 
	 * @return �V�X�e������
	 */
	public static String getSystemName() {
		return systemName;
	}

	/**
	 * ��ʃt�H���g�w��
	 * 
	 * @return �t�H���g
	 */
	public static String getClientFont() {
		return clientFont;
	}

	/**
	 * ��ʃt�H���g�T�C�Y�w��
	 * 
	 * @return �t�H���g�T�C�Y
	 */
	public static float getClientFontSize() {
		return clientFontSize;
	}

	/**
	 * �摜�t�@�C���T�t�B�b�N�X�i��������j�̎擾
	 * 
	 * @return �摜�̌������
	 */
	public static String getImageSuffix() {
		return imageSuffix;
	}

	/**
	 * �}���`�E�B���h�E�N�������
	 * 
	 * @return �����
	 */
	public static int getMaxWindowCount() {
		return maxWindowCount;
	}

	/**
	 * �w�b�_�[�J���[���擾
	 * 
	 * @return �ݒ�l
	 */
	public static String getHeaderBackColor() {
		return headerBackColor;
	}

	/**
	 * �R���|�[�l���g���␳�l���擾����
	 * 
	 * @param type �R���|�[�l���g�^�C�v
	 * @return �␳�l
	 */
	public static double getDeffWidth(String type) {
		try {
			String deff = getProperty("deff.width." + type);
			return Double.valueOf(deff);

		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * �R���|�[�l���g�����␳�l���擾����
	 * 
	 * @param type �R���|�[�l���g�^�C�v
	 * @return �␳�l
	 */
	public static double getDeffHeight(String type) {
		try {
			String deff = getProperty("deff.height." + type);
			return Double.valueOf(deff);

		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * ���b�Z�[�W�_�C�A���O��OK�{�^������
	 * 
	 * @return OK�{�^������
	 */
	public static String getOkButtonText() {
		try {
			return getProperty("dialog.ok.text");

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * �m�F�_�C�A���O��Yes/No�{�^������
	 * 
	 * @return Yes/No�{�^������
	 */
	public static String[] getYesNoButtonWords() {
		try {
			return StringUtil.toArrayByComma(getProperty("dialog.yesno.text"));

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * ���j���[�T�C�Y���ςɂ��邩�ǂ���
	 * 
	 * @return true:��
	 */
	public static boolean isMenuSplit() {
		return isMenuSplit;
	}

	/**
	 * ���j���[�^�u�̕\������(�V�X�e���敪)
	 * 
	 * @return ���j���[�^�u�̕\������
	 */
	public static String[] getMenuTabOrder() {
		try {

			return StringUtil.toArrayByComma(getProperty("menu.systab.order"));

		} catch (Exception ex) {
			return new String[] { "AG", "OW", "AC", "KT" };
		}
	}

	/**
	 * �X�v���b�h�V�[�g��Enter�L�[�������̑J�ڐ���w�肷��
	 * 
	 * @return �^�C�v
	 */
	public static String getTableEnterTraverse() {
		try {
			return getProperty("table.enter.traverse");

		} catch (Exception ex) {
			return "TRAVERSE_DOWN";
		}
	}

	/**
	 * TTable�J���[�ݒ�
	 * 
	 * @return ���x���F
	 */
	public static Color getTableLabelColor() {

		return toColor(labelback);
	}

	/**
	 * TTable�J���[�ݒ�
	 * 
	 * @return ���x���t�H���g�F
	 */
	public static Color getTableLabelFontColor() {

		return toColor(labelfont);
	}

	/**
	 * TTable�J���[�ݒ�
	 * 
	 * @return �f�t�H���g�F
	 */
	public static Color getTableCellBackColor1() {

		return toColor(cellback1);
	}

	/**
	 * TTable�J���[�ݒ�
	 * 
	 * @return �J��Ԃ��I��F
	 */
	public static Color getTableCellBackColor2() {

		return toColor(cellback2);
	}

	/**
	 * TTable�J���[�ݒ�
	 * 
	 * @return �I�����J���[
	 */
	public static Color getTableSelectColor() {
		try {
			return toColor(StringUtil.split(getProperty("table.cell.selectcolor"), ","));

		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * TTable�J���[�ݒ�
	 * 
	 * @return �I�����J���[
	 */
	public static Color getTableSelectCellFontColor() {
		try {
			return toColor(StringUtil.split(getProperty("table.cell.selectfontcolor"), ","));

		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * TTable�J���[�ݒ�
	 * 
	 * @return �I�����J���[
	 */
	public static Color getTableCellFontColor() {
		try {
			return toColor(StringUtil.split(getProperty("table.cell.fontcolor"), ","));

		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * TTable�����ݒ�
	 * 
	 * @return ����
	 */
	public static int getTableCellHeight() {
		try {
			return Integer.parseInt(getProperty("table.cell.height"));

		} catch (MissingResourceException e) {
			return 0;
		}
	}

	/**
	 * �F�ϊ�
	 * 
	 * @param abg RBG
	 * @return �F
	 */
	public static Color toColor(String[] abg) {
		int color0 = Integer.parseInt(abg[0]);
		int color1 = Integer.parseInt(abg[1]);
		int color2 = Integer.parseInt(abg[2]);

		return new Color(color0, color1, color2);
	}

	/**
	 * �t���OON/OFF
	 * 
	 * @param key �L�[
	 * @return true:ON�Afalse:OFF
	 */
	public static boolean isFlagOn(String key) {

		try {
			String prop = getProperty(key);
			return BooleanUtil.toBoolean(prop);

		} catch (MissingResourceException e) {
			return false;
		}
	}
}

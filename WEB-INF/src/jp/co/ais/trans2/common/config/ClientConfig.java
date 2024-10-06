package jp.co.ais.trans2.common.config;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * �N���C�A���g���̐ݒ�Ǘ��N���X�B<br>
 * �N���C�A���g���̐ݒ���͓��Y�N���X��ʂ��Ď擾���邱�ƁB
 * 
 * @author AIS
 */
public class ClientConfig {

	/** JNLP�v���p�e�B�擪 */
	public static String jnlpHeader = "jnlp.p.";

	/** �N���C�A���g�ݒ��� */
	protected static ResourceBundle bundle = ResourceBundle.getBundle("client");

	/**
	 * Web�o�R�ŃA�N�Z�X����ꍇtrue�B<br>
	 * �J������false�ɐݒ肷�邱�Ƃ�APServer���N�����邱�Ɩ����J���ł���B<br>
	 */
	protected static boolean web = true;

	/** ���C����ʂ̍��� */
	protected static int mainViewHeight;

	/** ���C����ʂ̕� */
	protected static int mainViewWidth;

	/** �o�[�W���� */
	protected static String systemVersion;

	/** �C�ӎw��̃v���p�e�B�[ */
	protected static Map<String, String> properties = null;

	static {

		// ������
		properties = new HashMap<String, String>();

		for (Map.Entry entry : System.getProperties().entrySet()) {
			String key = Util.avoidNull(entry.getKey());

			// jnlp�擪���菈���ǉ�
			if (key.startsWith(jnlpHeader)) {
				key = key.replace(jnlpHeader, "");
			}
			setProperty(key, Util.avoidNull(entry.getValue()));
		}

		// Web�o�R�ŃA�N�Z�X���邩
		web = Boolean.parseBoolean(getProperty("system.type.web"));

		// ���C����ʂ̃T�C�Y
		mainViewWidth = 800;
		String width = getProperty("applet.width");
		if (!Util.isNullOrEmpty(width)) {
			mainViewWidth = Integer.parseInt(width);
		}

		mainViewHeight = 600;
		String height = getProperty("applet.height");
		if (!Util.isNullOrEmpty(height)) {
			mainViewHeight = Integer.parseInt(height);
		}

		// �o�[�W����
		try {
			systemVersion = getProperty("trans.version");
		} catch (Exception e) {
			//
		}
	}

	/**
	 * @param key
	 * @param value
	 */
	public static void initConfigOld(String key, String value) {
		try {

			boolean isOldKeyValue = true;

			if ("client.font".equals(key)) {
				jp.co.ais.trans.common.config.ClientConfig.clientFont = value;

			} else if ("client.font.size".equals(key)) {
				// �t�H���g
				jp.co.ais.trans.common.config.ClientConfig.clientFontSize = Float.parseFloat(value);

			} else if ("login.comp.code".equals(key)) {
				// �����O�C�����
				jp.co.ais.trans.common.config.ClientConfig.kariComp = value;

			} else if ("login.user.code".equals(key)) {
				// �����O�C�����
				jp.co.ais.trans.common.config.ClientConfig.kariUser = value;

			} else if ("show.prg.code".equals(key)) {
				// PRG�R�[�h�\��
				jp.co.ais.trans.common.config.ClientConfig.isShowPrgCode = Boolean.valueOf(value);

			} else if ("applet.widht".equals(key)) {
				// �v���O�����R�[�h�\��
				jp.co.ais.trans.common.config.ClientConfig.appletWidht = Integer.parseInt(value);

			} else if ("applet.height".equals(key)) {
				// �v���O�����R�[�h�\��
				jp.co.ais.trans.common.config.ClientConfig.appletHeight = Integer.parseInt(value);

			} else if ("base.address".equals(key)) {
				// �ڑ���ݒ�
				jp.co.ais.trans.common.config.ClientConfig.baseAddress = value;

			} else if ("http.protocol".equals(key)) {
				// �ڑ���ݒ�
				jp.co.ais.trans.common.config.ClientConfig.protocol = value;

			} else if ("http.host".equals(key)) {
				// �ڑ���ݒ�
				jp.co.ais.trans.common.config.ClientConfig.host = value;

			} else if ("http.port".equals(key)) {
				// �ڑ���ݒ�
				jp.co.ais.trans.common.config.ClientConfig.port = value;

			} else if ("http.project".equals(key)) {
				// �ڑ���ݒ�
				jp.co.ais.trans.common.config.ClientConfig.project = value;

			} else if ("root.url.directory".equals(key)) {
				// ���[�g�f�B���N�g��
				jp.co.ais.trans.common.config.ClientConfig.rootDirectory = value;

			} else if ("trans.system.name".equals(key)) {
				// �V�X�e������
				jp.co.ais.trans.common.config.ClientConfig.systemName = value;

				// �V�X�e�����̂��J���R�[�h���̂ƈقȂ�ꍇ�A�摜����������ύX����.
				if (!jp.co.ais.trans.common.config.ClientConfig.DEV_CODE.equals(value)) {
					jp.co.ais.trans.common.config.ClientConfig.imageSuffix = "_" + value;
				}

			} else if ("trans.system.multilogin".equals(key)) {
				// ���[�g�f�B���N�g��
				String multilogin = value;
				if (Util.isNumber(multilogin)) {
					jp.co.ais.trans.common.config.ClientConfig.maxWindowCount = Integer.parseInt(multilogin);

				} else if (BooleanUtil.toBoolean(multilogin)) {
					// true�̏ꍇ�́A1���
					jp.co.ais.trans.common.config.ClientConfig.maxWindowCount = 1;
				}

			} else if ("menu.split".equals(key)) {
				// ���j���[�T�C�Y��
				jp.co.ais.trans.common.config.ClientConfig.isMenuSplit = Boolean.valueOf(value);

			} else if ("table.label.backcolor".equals(key)) {
				// �X�v���b�h�J���[�R�[�h
				jp.co.ais.trans.common.config.ClientConfig.labelback = value.split(",");

			} else if ("table.label.fontcolor".equals(key)) {
				// �X�v���b�h�J���[�R�[�h
				jp.co.ais.trans.common.config.ClientConfig.labelfont = value.split(",");

			} else if ("table.cell.backcolor1".equals(key)) {
				// �X�v���b�h�J���[�R�[�h
				jp.co.ais.trans.common.config.ClientConfig.cellback1 = value.split(",");

			} else if ("table.cell.backcolor2".equals(key)) {
				// �X�v���b�h�J���[�R�[�h
				jp.co.ais.trans.common.config.ClientConfig.cellback2 = value.split(",");

			} else if ("trans.header.backcolor".equals(key)) {
				// �w�b�_�[�w�i�F
				jp.co.ais.trans.common.config.ClientConfig.headerBackColor = value;

			} else {
				isOldKeyValue = false;
			}

			if (isOldKeyValue) {
				ClientLogger.debug("is old key and value. [key=" + key + ", value=" + value);
			}

		} catch (Exception e) {
			ClientLogger.error("is error key and value. [key=" + key + ", value=" + value);
		}
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
	 * Client�v���p�e�B����l���擾����B
	 * 
	 * @param key �L�[
	 * @return �ݒ�l
	 */
	public static String getProperty(String key) {

		if (properties.containsKey(key)) {
			return properties.get(key);
		} else {
			String value = Util.avoidNull(bundle.getString(key));
			properties.put(key, value);
			return value;
		}

	}

	/**
	 * �v���p�e�B�[�̎擾
	 * 
	 * @return �v���p�e�B�[�}�b�v
	 */
	public static Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * �C�Ӄv���p�e�B�̐ݒ�
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		properties.put(key, value);

		// ��Config���T�|�[�g����
		initConfigOld(key, value);
	}

	/**
	 * @return Web�o�R�ŃA�N�Z�X���邩��Ԃ��B
	 */
	public static boolean isWeb() {
		return web;
	}

	/**
	 * @param flag true:WEB���[�h
	 */
	public static void setWeb(boolean flag) {
		web = flag;
	}

	/**
	 * @return ���C����ʂ̍�����Ԃ��B
	 */
	public static int getMainViewHeight() {
		return mainViewHeight;
	}

	/**
	 * @return ���C����ʂ̕���Ԃ��B
	 */
	public static int getMainViewWidth() {
		return mainViewWidth;
	}

	/**
	 * �^�C�g��
	 * 
	 * @return �^�C�g��
	 */
	public static String getTitle() {

		String title;
		try {
			title = getProperty("system.name");

		} catch (MissingResourceException e) {
			title = "TRANS";
		}

		if (!Util.isNullOrEmpty(systemVersion)) {
			title += " Ver" + systemVersion;
		}

		return title;
	}

	/**
	 * �V�X�e���X�^�C��
	 * 
	 * @return �V�X�e���X�^�C��
	 */
	public static String getSystemStyle() {
		try {
			return getProperty("system.style");

		} catch (MissingResourceException e) {
			return null;
		}
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

	/**
	 * �ݒ�v���p�e�B����F���擾����
	 * 
	 * @param key
	 * @return �F
	 */
	public static Color getColor(String key) {

		try {
			String prop = getProperty(key);
			String[] arr = prop.split(",");

			int color0 = Integer.parseInt(arr[0]);
			int color1 = Integer.parseInt(arr[1]);
			int color2 = Integer.parseInt(arr[2]);

			return new Color(color0, color1, color2);

		} catch (Throwable e) {
			return null;
		}
	}
}

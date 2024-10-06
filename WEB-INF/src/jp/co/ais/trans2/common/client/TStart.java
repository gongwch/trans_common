package jp.co.ais.trans2.common.client;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * �v���O�����N���N���X�B<br>
 * �A�v���P�[�V�����͓��Y�N���X��main���\�b�h���N�_�Ƃ���B
 * 
 * @author AIS
 */
public class TStart {

	/** �f�t�H���g���[�J���^�C���]�[�� */
	public static TimeZone localTimeZone;

	/** DEBUG���[�h */
	public static boolean isDebugMode = false;

	static {

		try {
			Class clazz = Class.forName("javax.jnlp.ServiceManager");
			Method method = clazz.getDeclaredMethod("lookup", String.class);
			Object objBS = method.invoke(clazz, "javax.jnlp.BasicService");

			Method methodBS = objBS.getClass().getDeclaredMethod("getCodeBase");
			String codebase = Util.avoidNull(methodBS.invoke(objBS));

			// URL�ݒ�
			// BasicService bs = (BasicService) clazz.newInstance().lookup("javax.jnlp.BasicService");
			// String codebase = bs.getCodeBase().toString();
			jp.co.ais.trans.common.config.ClientConfig.setBaseAddress(codebase);
			System.out.println("#codebase=" + codebase);

		} catch (RuntimeException ex) {
			// �G���[�Ȃ�
			ClientLogger.info("local mode 1");
		} catch (Exception e) {
			// �G���[�Ȃ�
			ClientLogger.info("local mode 2");
		}

		// �e�N���C�A���g�p�����[�^�̏�����
		showInitialParamaters();

		localTimeZone = TimeZone.getDefault();

		try {

			// Java 9 ����G���[�̂��߁A�R�����g�A�E�g����
			// // �t�H�[�J�X
			// AppContext.getAppContext().put(KeyboardFocusManager.class, new TKeyboardFocusManager());

			// �^�C���]�[���ݒ�
			String timeZone = ClientConfig.getProperty("trans.time.zone");
			TimeZone.setDefault(TimeZone.getTimeZone(timeZone));

			ClientLogger.info("#set timezone=" + timeZone);

		} catch (MissingResourceException e) {
			// �����Ȃ�
		}
	}

	/**
	 * �v���O�����G���g���|�C���g<BR>
	 * �A�v���P�[�V�������N������B
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		println("new TStart.");

		// �A�v�������ɂ���ăv���p�e�B������
		initArgumentProperties(args);

		// ���O�C���R���g���[���N��
		TLoginCtrl ctrl = new TLoginCtrl();

		// ���O�C���J�n
		ctrl.start();
	}

	/**
	 * �����p�����[�^�̏�����
	 */
	public static void showInitialParamaters() {

		// ���O�o�͂��ǂ����ŗD��
		try {
			String key = "debug.mode";
			String value = Util.avoidNull(System.getProperty(ClientConfig.jnlpHeader + key));
			ClientConfig.setProperty(key, value);

			isDebugMode = Boolean.valueOf(ClientConfig.getProperty(key));

			if (isDebugMode) {
				for (Map.Entry<String, String> entry : ClientConfig.getProperties().entrySet())
					ClientLogger.info(entry.getKey() + "=" + entry.getValue());
			}
		} catch (Exception e) {
			//
		}
	}

	/**
	 * �A�v�������ɂ���ăv���p�e�B������
	 * 
	 * @param args
	 */
	public static void initArgumentProperties(String[] args) {
		try {
			for (int i = 0; i < args.length - 1; i += 2) {
				String key = args[i];
				if (i + 1 >= args.length) {
					break;
				}
				String value = args[i + 1];

				// �t�@�C���ł���΁A�t�@�C����ǂݍ���
				if (key.equalsIgnoreCase("-config")) {

					System.out.println("has config file.");

					if (ClientConfig.isWeb()) {
						// �������̂��߁A���A�N�Z�X����
					}

					//
					FileReader fr = null;
					BufferedReader br = null;

					try {

						fr = new FileReader(value);
						br = new BufferedReader(fr);
						String line = null;

						println("#config to properties init. [" + value + "]");

						for (int lin = 1; (line = br.readLine()) != null; lin++) {
							String[] arr = line.split("=");
							if (arr.length > 1) {
								String subKey = arr[0];
								String subValue = arr[1];

								// jnlp�擪���菈���ǉ�
								if (subKey.startsWith(ClientConfig.jnlpHeader)) {
									subKey = subKey.replace(ClientConfig.jnlpHeader, "");
								}

								println("#config key=[" + subKey + "], value=[" + subValue + "] at line:" + lin);
								System.setProperty(subKey, subValue);
								ClientConfig.setProperty(subKey, subValue);
							}
						}

					} catch (Throwable ex) {
						println("#config to properties error! [" + value + "]");
						println(ex);
					} finally {
						if (br != null) {
							br.close();
						}
						if (fr != null) {
							fr.close();
						}
					}
				} else {
					// �ʏ�ݒ�

					// jnlp�擪���菈���ǉ�
					if (key.startsWith(ClientConfig.jnlpHeader)) {
						key = key.replace(ClientConfig.jnlpHeader, "");
					}
					ClientConfig.setProperty(key, value);
				}
			}
		} catch (Exception e) {
			println("#args to properties error.");
		}
	}

	/**
	 * DEBUG
	 * 
	 * @param str
	 */
	protected static void println(String str) {
		ClientLogger.info(str);
	}

	/**
	 * DEBUG
	 * 
	 * @param ex
	 */
	protected static void println(Throwable ex) {
		ex.printStackTrace(System.out);
	}

}

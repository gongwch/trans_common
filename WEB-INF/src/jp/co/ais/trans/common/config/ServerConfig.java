package jp.co.ais.trans.common.config;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �T�[�o�T�C�h�ݒ���
 */
public class ServerConfig {

	/** �V�X�e���R�[�h(�T�u�V�X�e������) */
	private static String[] sysCodes = null;

	/** �K�胍�O�C���� */
	private static int regulatedNumber = -1;

	/** �T�[�o�T�C�h�ݒ� */
	private static ResourceBundle bundle = ResourceBundle.getBundle("server");

	/** �V�X�e������R�[�h */
	private static String lang;

	/** �V�X�e���X�L�[�} */
	private static String schemaName;

	/** �\�̈於 */
	private static String tablespaceName;

	/** �Z�b�V�����F�؃��[�h */
	private static boolean isSessionMode;

	/** ���O�C���Ǘ����[�h */
	private static boolean isLoginManagedMode;

	/** ���s���O�t�@�C����DB�ɓo�^���邩�ǂ��� */
	private static boolean isExeLogDBMode;

	/** WEB���[�g�f�B���N�g���p�X */
	private static String rootPath = "";

	/** �`�[�̗L���������`�F�b�N���邩 */
	private static boolean isSlipTermCheck;

	/** �K��T�u�V�X�e�����p���[�U�����`�F�b�N���邩 */
	private static boolean isSystemRegulatedNumberCheck;

	/** �K��T�u�V�X�e�����p���[�U�� */
	private static Map<String, Integer> regulatedNumberMap = null;

	/** �K�藘�p���[�U�̉�ЃO���[�v<��ЃR�[�h, �����O���[�v> */
	private static Map<String, Integer> regulatedGroupMap = null;

	/** �K�藘�p���[�U�̉�ЃO���[�v<index, ��ЃR�[�h���X�g> */
	private static Map<Integer, List<String>> regulatedGroupListMap = null;

	static {
		// �Í������ꂽ�K�萔���V�X�e���R�[�h
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(ResourceUtil.getResourceAsFile("keyfile/TSCode.dll"));

			// ���������t�@�C�������[�h�����v���p�e�B�̎擾
			Properties properties = EncryptUtil.decryptProperty(fis);

			String sysCodesStr = properties.getProperty("system.code");
			String regulNumStr = properties.getProperty("login.regulate.number");

			// �ݒ薳���̓V�X�e���G���[
			if (Util.isNullOrEmpty(sysCodesStr)) {
				throw new TEnvironmentException("S00001", "system.code not found.");
			}

			if (Util.isNullOrEmpty(regulNumStr)) {
				throw new TEnvironmentException("S00001", "login.regulate.number not found.");
			}

			// �g�p�T�u�V�X�e���F�擾����sysCode���J���}��؂��String[]�ɐݒ�
			sysCodes = StringUtil.split(sysCodesStr, ",");

			// �K�胍�O�C����
			regulatedNumber = Integer.parseInt(regulNumStr);

			// �T�u�V�X�e�����Ƀ`�F�b�N���s�����ǂ���
			try {
				String prop = properties.getProperty("system.use.regulate.check");
				isSystemRegulatedNumberCheck = BooleanUtil.toBoolean(prop);

			} catch (MissingResourceException e) {
				isSystemRegulatedNumberCheck = false;
			}

			regulatedNumberMap = new LinkedHashMap<String, Integer>();
			regulatedGroupMap = new LinkedHashMap<String, Integer>();
			regulatedGroupListMap = new LinkedHashMap<Integer, List<String>>();

			// �T�u�V�X�e�����Ɋ��胍�O�C������ݒ�
			if (isSystemRegulatedNumberCheck) {
				for (String sysCode : sysCodes) {
					if (Util.isNullOrEmpty(sysCode)) {
						continue;
					}

					// �T�u�V�X�e���P��
					try {
						String key = "system.regulate.number." + sysCode;
						String numStr = properties.getProperty(key);
						int num = regulatedNumber;

						if (!Util.isNullOrEmpty(numStr) && Util.isNumber(numStr)) {
							num = Integer.parseInt(numStr);
						}

						regulatedNumberMap.put(sysCode, num);

					} catch (Throwable ex) {
						// �G���[�Ȃ�

						regulatedNumberMap.put(sysCode, regulatedNumber);
					}

					// �O���[�vIndex�P��
					{
						int num = 0;

						{
							String numStr = properties.getProperty("system.regulate.group.count");

							if (!Util.isNullOrEmpty(numStr) && Util.isNumber(numStr)) {
								num = Integer.parseInt(numStr);
							}
						}

						if (num > 0) {
							// �O���[�v�ݒ肠��
							for (int i = 1; i <= num; i++) {
								String key = "system.regulate.group." + i;
								String[] values = StringUtil.split(properties.getProperty(key), ",");
								List<String> companyCodeList = new ArrayList<String>();

								for (String str : values) {
									companyCodeList.add(str);
									regulatedGroupMap.put(str, i);
								}

								regulatedGroupListMap.put(i, companyCodeList);
							}
						}

						if (!regulatedGroupListMap.isEmpty()) {
							// �O���[�v����
							for (Integer i : regulatedGroupListMap.keySet()) {
								String key = "system.regulate.number." + i + "." + sysCode;
								String numStr = properties.getProperty(key);
								int n = regulatedNumber;

								if (!Util.isNullOrEmpty(numStr) && Util.isNumber(numStr)) {
									n = Integer.parseInt(numStr);
								}

								regulatedNumberMap.put(sysCode + i, n);
							}
						}
					}
				}

			}

		} catch (FileNotFoundException e) {
			ServerErrorHandler.handledException(e);
			throw new TEnvironmentException(e, "TSCode file not found.");

		} catch (TEnvironmentException e) {
			ServerErrorHandler.handledException(e);
			throw e;

		} catch (TException e) {
			ServerErrorHandler.handledException(e);
			throw new TEnvironmentException(e, "SystemProperty decrypt failed.");

		} finally {
			ResourceUtil.closeInputStream(fis);
		}

		// system�v���p�e�B

		try {
			// �T�[�o�T�C�h�f�t�H���g����
			lang = getProperty("trans.system.lang");

		} catch (MissingResourceException e) {
			lang = Locale.getDefault().getLanguage();
		}

		try {
			// �X�L�[�}��
			schemaName = getProperty("trans.system.schema") + ".";

		} catch (MissingResourceException e) {
			schemaName = "";
		}

		try {
			// Function��
			tablespaceName = getProperty("trans.system.tablespace") + ".";

		} catch (MissingResourceException e) {
			tablespaceName = "";
		}

		try {
			// �Z�b�V�����Ǘ������t���O�i�J���p�j
			String noSessionMode = getProperty("trans.no.session.mode");

			isSessionMode = !Boolean.valueOf(noSessionMode);

		} catch (MissingResourceException e) {
			isSessionMode = true;
		}

		try {
			// ���O�C���Ǘ������t���O�i�J���p�j
			String noLoginManage = getProperty("trans.no.loginmanaged.mode");

			isLoginManagedMode = !Boolean.valueOf(noLoginManage);

		} catch (MissingResourceException e) {
			isLoginManagedMode = true;
		}

		try {
			// �T�[�o�v���p�e�B�擾
			String strDBLogMode = ServerConfig.getProperty("trans.exelog.isdbmode");

			isExeLogDBMode = Boolean.valueOf(strDBLogMode);

		} catch (MissingResourceException e) {
			isExeLogDBMode = false;
		}

		try {
			// �`�[�L�������`�F�b�N
			isSlipTermCheck = BooleanUtil.toBoolean(ServerConfig.getProperty("trans.slip.term.check"));

		} catch (MissingResourceException e) {
			isSlipTermCheck = false;
		}
	}

	/**
	 * ���p�V�X�e���R�[�h�̎擾
	 * 
	 * @return String[] sysCode
	 */
	public static String[] getSysCodes() {

		return sysCodes;
	}

	/**
	 * �K�胍�O�C�����̎擾
	 * 
	 * @return int �K�胍�O�C����
	 */
	public static int getRegulatedNumber() {

		return regulatedNumber;
	}

	/**
	 * �K��T�u�V�X�e�����p���[�U���̎擾
	 * 
	 * @param sysCode �T�u�V�X�e���R�[�h
	 * @param companyCode ��ЃR�[�h�w��
	 * @return int �K��T�u�V�X�e�����p���[�U��
	 */
	public static int getSystemRegulatedNumber(String sysCode, String companyCode) {
		int groupIndex = getSystemRegulatedGroupIndex(companyCode);
		return getSystemRegulatedNumber(sysCode, groupIndex);
	}

	/**
	 * �K��T�u�V�X�e�����p���[�U���̎擾
	 * 
	 * @param sysCode �T�u�V�X�e���R�[�h
	 * @param groupIndex
	 * @return int �K��T�u�V�X�e�����p���[�U��
	 */
	public static int getSystemRegulatedNumber(String sysCode, int groupIndex) {
		if (regulatedNumberMap.containsKey(sysCode + groupIndex)) {
			// �O���[�v�w�肠��A�O���[�v���C�Z���X��Ԃ�
			return regulatedNumberMap.get(sysCode + groupIndex);

		} else if (regulatedNumberMap.containsKey(sysCode)) {
			// �f�t�H���g�T�u�V�X�e���P�ʂ̃��C�Z���X��Ԃ�
			return regulatedNumberMap.get(sysCode);
		}

		// �f�t�H���g�V�X�e���P�ʂ̃��C�Z���X��Ԃ�
		return regulatedNumber;
	}

	/**
	 * �w���ЃR�[�h�̃O���[�vIndex��Ԃ�
	 * 
	 * @param companyCode
	 * @return �w���ЃR�[�h�̃O���[�vIndex
	 */
	public static int getSystemRegulatedGroupIndex(String companyCode) {
		if (!Util.isNullOrEmpty(companyCode) && regulatedGroupMap.containsKey(companyCode)) {
			return Util.avoidNullAsInt(regulatedGroupMap.get(companyCode));
		}

		return -1;
	}

	/**
	 * �w���ЃR�[�h�̓���O���[�vIndex�̉�ЃR�[�h���X�g��Ԃ�
	 * 
	 * @param companyCode
	 * @return �w���ЃR�[�h�̓���O���[�vIndex�̉�ЃR�[�h���X�g
	 */
	public static List<String> getSystemRegulatedGroupList(String companyCode) {
		int groupIndex = getSystemRegulatedGroupIndex(companyCode);
		List<String> list = getSystemRegulatedGroupList(groupIndex, companyCode);
		return list;
	}

	/**
	 * �w��O���[�vIndex�̉�ЃR�[�h���X�g��Ԃ�
	 * 
	 * @param groupIndex
	 * @param companyCode
	 * @return �w��O���[�vIndex�̉�ЃR�[�h���X�g
	 */
	public static List<String> getSystemRegulatedGroupList(int groupIndex, String companyCode) {
		List<String> list = null;

		if (groupIndex != -1) {
			list = regulatedGroupListMap.get(groupIndex);
		}

		if (list == null) {
			list = new ArrayList<String>();
		}

		if (list.isEmpty()) {
			list.add(companyCode);
		}

		return list;
	}

	/**
	 * �V�X�e������R�[�h
	 * 
	 * @return ����R�[�h
	 */
	public static String getSystemLanguageCode() {
		return lang;
	}

	/**
	 * �V�X�e���X�L�[�}��.<br>
	 * �ݒ肪�����ꍇ�́A�󕶎���Ԃ�.
	 * 
	 * @return �X�L�[�}��
	 */
	public static String getSchemaName() {
		return schemaName;
	}

	/**
	 * �\�̈於.<br>
	 * �ݒ肪�����ꍇ�́A�󕶎���Ԃ�.
	 * 
	 * @return �\�̈於
	 */
	public static String getTableSpaceName() {
		return tablespaceName;
	}

	/**
	 * �Z�b�V�����F�؃��[�h
	 * 
	 * @return �F�؂���ꍇ��true
	 */
	public static boolean isSessionMode() {
		return isSessionMode;
	}

	/**
	 * ���O�C���Ǘ����[�h
	 * 
	 * @return �Ǘ�����ꍇ��true
	 */
	public static boolean isLoginManagedMode() {
		return isLoginManagedMode;
	}

	/**
	 * ���O�C���Ǘ����[�h�̎w��
	 * 
	 * @param isLoginManagedMode
	 */
	public static void setLoginManagedMode(boolean isLoginManagedMode) {
		ServerConfig.isLoginManagedMode = isLoginManagedMode;
	}

	/**
	 * ���s���O�t�@�C����DB�ɓo�^���邩�ǂ���
	 * 
	 * @return DB�ɓo�^����ꍇ��true
	 */
	public static boolean isExeLogDBMode() {
		return isExeLogDBMode;
	}

	/**
	 * �`�[�̗L���������`�F�b�N���邩
	 * 
	 * @return true:�`�F�b�N����
	 */
	public static boolean isSlipTermCheck() {
		return isSlipTermCheck;
	}

	/**
	 * �K��T�u�V�X�e�����p���[�U�����`�F�b�N���邩
	 * 
	 * @return true:�`�F�b�N����
	 */
	public static boolean isSystemRegulatedNumberCheck() {
		return isSystemRegulatedNumberCheck;
	}

	/**
	 * Server�v���p�e�B����l���擾
	 * 
	 * @param key �L�[
	 * @return �ݒ�l
	 */
	public static String getProperty(String key) {

		return Util.avoidNull(bundle.getString(key));
	}

	/**
	 * Server�v���p�e�B����l(����)���擾<br>
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
	 * �T�[�o�T�C�h���[�g�f�B���N�g����ݒ�
	 * 
	 * @param dir �T�[�o�T�C�h���[�g�f�B���N�g��
	 */
	public static void setRootPath(String dir) {
		rootPath = dir;
	}

	/**
	 * �T�[�o�T�C�h���[�g�f�B���N�g�����擾
	 * 
	 * @return �T�[�o�T�C�h���[�g�f�B���N�g��
	 */
	public static String getRootPath() {
		if (Util.isNullOrEmpty(rootPath)) {
			return System.getProperty("user.dir");
		}

		return rootPath;
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
	 * �w��ݒ肪���邩�ǂ���
	 * 
	 * @param key �L�[
	 * @return true:����
	 */
	public static boolean contains(String key) {
		return bundle.containsKey(key);
	}
}

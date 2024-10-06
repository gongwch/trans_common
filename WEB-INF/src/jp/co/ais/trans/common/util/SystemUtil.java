package jp.co.ais.trans.common.util;

import java.io.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;

/**
 * �V�X�e���n���[�e�B���e�B
 */
public final class SystemUtil {

	/** ���s���� */
	public static String LINE_SEP = System.getProperty("line.separator");

	/** �ϊ����X�g�u^�v�̓G�X�P�[�v�̂��߁A��ԍŏ��ɂ���K�v */
	public static char[] ESCAPE_LIST = ("^" + " &()[]{}=;!'+,`~�@").toCharArray();

	/**
	 * �ꎞ�t�@�C���ۑ��f�B���N�g���p�X���擾����
	 * 
	 * @return �f�B���N�g���p�X
	 */
	public static String getTemporaryDir() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * @return AIS�p�e��(���O�C�����/�X�v���b�h�������)�̕ۑ��t�H���_
	 */
	public static String getAisSettingDir() {
		String dir = getUserHomeDir() + "\\.ais\\setting\\";
		File file = new File(dir);
		file.mkdirs();
		return dir;
	}

	/**
	 * @return AIS�p�t�H���_
	 */
	public static String getAisDir() {
		String dir = getUserHomeDir() + "\\.ais\\";
		File file = new File(dir);
		file.mkdirs();
		return dir;
	}

	/**
	 * ���[�U�z�[���f�B���N�g���p�X���擾����
	 * 
	 * @return �f�B���N�g���p�X
	 */
	public static String getUserHomeDir() {
		return System.getProperty("user.home");
	}

	/**
	 * @param fileName
	 * @return �t�@�C�����G�X�P�[�v
	 */
	public static String escapeFileName(String fileName) {
		String name = fileName;
		for (char str : ESCAPE_LIST) {
			name = name.replaceAll("\\" + str, "^" + str);
		}
		return name;
	}

	/**
	 * �t�@�C���N��
	 * 
	 * @param fileName �N���t�@�C����
	 * @return ���s�v���Z�X
	 * @throws TException �N�����s
	 */
	public static Process executeFile(String fileName) throws TException {
		String name = escapeFileName(fileName);
		String cmd = "cmd /c " + "\"" + name + "\"";

		return exec(cmd);
	}

	/**
	 * PDF�t�@�C�����
	 * 
	 * @param fileName ����t�@�C����
	 * @return ���s�v���Z�X
	 * @throws TException ������s
	 */
	public static Process printPDF(String fileName) throws TException {
		startPDF();

		String cmd = "cmd /c start acrord32.exe /p /h " + "\"" + fileName + "\"";

		return exec(cmd);
	}

	/**
	 * PDF�t�@�C�����(Wait�o�[�W����)
	 * 
	 * @param fileName ����t�@�C����
	 * @return ���s�v���Z�X
	 * @throws TException ������s
	 */
	public static Process printPDFForWait(String fileName) throws TException {

		String cmd = "cmd /c start /WAIT acrord32.exe /p /h " + "\"" + fileName + "\"";

		try {
			startPDF();

			Process process = exec(cmd);
			process.waitFor();

			return process;

		} catch (InterruptedException ex) {
			throw new TException(ex, "E00019", cmd);
		}
	}

	/**
	 * PDF�t�@�C�����
	 * 
	 * @param fileName ����t�@�C����
	 * @param printerName �v�����^��
	 * @return ���s�v���Z�X
	 * @throws TException ������s
	 */
	public static Process printPDF(String fileName, String printerName) throws TException {
		startPDF();

		String cmd = "cmd /c start acrord32.exe /t /h " + "\"" + fileName + "\" " + "\"" + printerName + "\"";

		return exec(cmd);
	}

	/**
	 * �������Y����h���ׁA�ŏ���Acrobat���N��������
	 */
	public static void startPDF() {
		try {
			String cmd = "cmd /c start acrord32.exe";

			exec(cmd);

			// �N���������҂�
			Thread.sleep(1000);
		} catch (Exception ex) {
			// �Ȃ�
		}
	}

	/**
	 * Runtime�R�}���h���s
	 * 
	 * @param cmd �R�}���h
	 * @return ���s�v���Z�X
	 * @throws TException
	 */
	public static Process exec(String cmd) throws TException {

		Process process = null;

		try {
			process = Runtime.getRuntime().exec(cmd);

			ClientLogger.debug(cmd);

		} catch (IOException ex) {
			throw new TException(ex, "E00019", cmd);
		}

		return process;
	}

	/**
	 * ���s����PDF�A�v���������I�ɕ���
	 * 
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	public void closePDFApp() throws TException {
		String cmd = "cmd /c taskkill.exe /IM acrord32.exe /f /FI \"STATUS eq RUNNING\"";

		try {
			Runtime.getRuntime().exec(cmd);

			ClientLogger.debug(cmd);

		} catch (IOException ex) {
			throw new TException(ex, "E00019", cmd);
		}
	}
}

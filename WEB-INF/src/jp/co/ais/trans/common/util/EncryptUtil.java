package jp.co.ais.trans.common.util;

import java.io.*;
import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import jp.co.ais.trans.common.except.*;

/**
 * �Í������郆�[�e�B���e�B
 */
public final class EncryptUtil {

	/** �Í������� */
	private static final String METHOD = "Blowfish";

	/** �W���L�[ */
	private static final String KEY = "ais.trans";

	/**
	 * �t�@�C�����Í�������
	 * 
	 * @param inputPath �Ώۃt�@�C��
	 * @param outputPath �Í�����t�@�C��
	 * @param key �Í����L�[
	 * @throws TException
	 */
	public static void encryptProperty(String inputPath, String outputPath, String key) throws TException {

		FileInputStream fi = null;
		CipherInputStream co = null;
		FileOutputStream fo = null;

		try {

			// ���̓X�g���[���̐ݒ�
			fi = new FileInputStream(inputPath);

			// ���ʌ��̐ݒ�
			SecretKeySpec ks = new SecretKeySpec(key.getBytes(), METHOD);

			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.ENCRYPT_MODE, ks);

			// �t�@�C���̈Í����X�g���[���ݒ�
			co = new CipherInputStream(fi, cipher);

			// �o�̓X�g���[���̐ݒ�
			fo = new FileOutputStream(outputPath);

			byte[] m = new byte[8];
			int i = co.read(m);

			while (i != -1) {
				// �t�@�C���̓ǂݍ���
				fo.write(m, 0, i);

				// �t�@�C���̏o��
				i = co.read(m);
			}

		} catch (Exception ex) {
			throw new TException(ex);

		} finally {

			// �X�g���[���̏I��
			ResourceUtil.closeInputStream(co);
			ResourceUtil.closeInputStream(fi);
			ResourceUtil.closeOutputStream(fo);
		}
	}

	/**
	 * �t�@�C���𕜍����AProperties�ɑ}��
	 * 
	 * @param fis �ΏۃX�g���[��
	 * @return Properties properties
	 * @throws TException
	 */
	public static Properties decryptProperty(FileInputStream fis) throws TException {
		CipherInputStream cis = null;

		try {
			Properties properties = new Properties();

			// ���ʌ��̐ݒ�
			SecretKeySpec ks = new SecretKeySpec(new byte[] { 116, 114, 97, 110, 115, 67, 111, 100, 101 }, METHOD);

			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.DECRYPT_MODE, ks);

			// �t�@�C���̕����X�g���[���ݒ�
			cis = new CipherInputStream(fis, cipher);

			// ���������t�@�C����properties�œǂݍ���
			properties.load(cis);

			return properties;

		} catch (Exception ex) {
			throw new TException(ex);

		} finally {
			// �X�g���[�������
			ResourceUtil.closeInputStream(cis);
			ResourceUtil.closeInputStream(fis);
		}
	}

	/**
	 * �W���Í���(�P��)
	 * 
	 * @param str �Í����Ώە�����
	 * @return �Í���������
	 */
	public static String encrypt(String str) {
		return encrypt(KEY, str);
	}

	/**
	 * �W��������(�P��)
	 * 
	 * @param str �������Ώە�����
	 * @return ������������
	 */
	public static String decrypt(String str) {
		return decrypt(KEY, str);
	}

	/**
	 * �W���Í���(�P��)
	 * 
	 * @param key �L�[
	 * @param str �Í����Ώە�����
	 * @return �Í���������
	 */
	public static String encrypt(String key, String str) {
		try {
			// ���ʌ��̐ݒ�
			SecretKeySpec ks = new SecretKeySpec(key.getBytes(), METHOD);
			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.ENCRYPT_MODE, ks);

			byte encrypted[] = cipher.doFinal(str.getBytes()); // �����ňÍ���

			return new String(encrypted);

		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * �W��������(�P��)
	 * 
	 * @param key �L�[
	 * @param str �������Ώە�����
	 * @return ������������
	 */
	public static String decrypt(String key, String str) {
		try {
			// ���ʌ��̐ݒ�
			SecretKeySpec ks = new SecretKeySpec(key.getBytes(), METHOD);

			Cipher cipher = Cipher.getInstance(METHOD);
			cipher.init(Cipher.DECRYPT_MODE, ks);

			byte decrypted[] = cipher.doFinal(str.getBytes()); // �����ŕ�����

			return new String(decrypted);

		} catch (Exception ex) {
			throw new TRuntimeException(ex);
		}
	}

}

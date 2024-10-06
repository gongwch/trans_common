package jp.co.ais.trans2.common.file;

import java.io.*;
import java.nio.charset.*;

import org.mozilla.universalchardet.*;

/**
 * 
 */
public class FileCharsetDetector {

	/**
	 * �L�����N�^�Z�b�g���擾����
	 * @param file
	 * @return �L�����N�^�Z�b�g
	 * @throws IOException
	 */
	public static Charset getCharset(File file) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return getCharset(is);
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}

	}

	/**
	 * �L�����N�^�Z�b�g���擾����
	 * @param is
	 * @return �L�����N�^�Z�b�g
	 * @throws IOException
	 */
	public static Charset getCharset(InputStream is) throws IOException {
		//4kb�̃������o�b�t�@���m�ۂ���
		byte[] buf = new byte[4096];
		UniversalDetector detector = new UniversalDetector(null);

		//�����R�[�h�̐������ʂ�������܂�InputStream��ǂݐi�߂�
		int nread;
		while ((nread = is.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}

		//�������ʂ��擾����
		detector.dataEnd();
		final String detectedCharset = detector.getDetectedCharset();

		detector.reset();

		if (detectedCharset != null) {
			return Charset.forName(detectedCharset);
		}
		//�����R�[�h���擾�ł��Ȃ������ꍇ�A���̃f�t�H���g���g�p����
		return Charset.forName(System.getProperty("file.encoding"));
	}
}

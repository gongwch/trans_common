package jp.co.ais.trans2.common.file;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * CSV�t�@�C���Ǎ��̂��߂̃N���X <br>
 * BufferedReader�ɁAFile���璼�ɃC���X�^���X�����R���X�g���N�^TCsvReader(File)�� �Ǎ�method readLineToColumnList()��ǉ������N���X�ł��B
 */

public class TCsvFileReader extends BufferedReader {

	/** DELIMITER */
	static final String DELIMITER = ",";

	/**
	 * constructor.
	 * 
	 * @param in �t�@�C�����[�_
	 */
	public TCsvFileReader(Reader in) {
		super(in);
	}

	/**
	 * constructor. <br>
	 * �ʏ�A���̃R���X�g���N�^���g���B
	 * 
	 * @param fi CSV�t�@�C��
	 * @throws IOException 
	 */
	public TCsvFileReader(File fi) throws IOException {
		super(new InputStreamReader(new FileInputStream(fi), FileCharsetDetector.getCharset(fi)));
	}

	/**
	 * �Ǎ��񂾍s���J���}(,)��؂�̃J�����Ɣ��f���A���X�g�ɂ��ĕԂ�
	 * 
	 * @return �s���X�g
	 * @throws IOException
	 */
	public List<String> readLineToColumnList() throws IOException {

		// �P�s�Ǎ�
		String lin = this.readLine();

		if (lin == null) {
			return null;
		}

		List<String> list = TCsvTokenizer.readLine(lin);

		return list;
	}

	/**
	 * TCsvReader��close()����
	 * 
	 * @param in TCsvReader
	 */
	public static void close(TCsvFileReader in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			SystemErrorHandler.handledException(ex);
		}
	}
}

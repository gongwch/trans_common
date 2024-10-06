package jp.co.ais.trans.common.file;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * CSV�t�@�C���Ǎ��̂��߂̃N���X <br>
 * BufferedReader�ɁAFile���璼�ɃC���X�^���X�����R���X�g���N�^TCsvReader(File)�� �Ǎ�method readLineToColumnList()��ǉ������N���X�ł��B <br>
 * �Â��o�[�W����<br>
 * �V�����o�[�W����:{@link jp.co.ais.trans2.common.file.TCsvFileReader}
 */
public class TCsvReader extends BufferedReader {

	static final String DELIMITER = ",";

	/**
	 * constructor.
	 * 
	 * @param in �t�@�C�����[�_
	 */
	public TCsvReader(Reader in) {
		super(in);
	}

	/**
	 * constructor. <br>
	 * �ʏ�A���̃R���X�g���N�^���g���B
	 * 
	 * @param fi CSV�t�@�C��
	 * @throws FileNotFoundException
	 */
	public TCsvReader(File fi) throws FileNotFoundException {

		super(new FileReader(fi));
	}

	/**
	 * �Ǎ��񂾍s���J���}(,)��؂�̃J�����Ɣ��f���A���X�g�ɂ��ĕԂ�
	 * 
	 * @return �s���X�g
	 * @throws IOException
	 */
	public List<String> readLineToColumnList() throws IOException {

		List<String> list = null;

		// �P�s�Ǎ�
		String lin = this.readLine();

		if (lin == null) {
			return null;
		}

		// ���X�g����
		list = new LinkedList<String>();

		Enumeration column = new CSVTokenizer(lin);

		while (column.hasMoreElements()) {
			list.add((String) column.nextElement());
		}

		return list;
	}

	/**
	 * TCsvReader��close()����
	 * 
	 * @param in TCsvReader
	 */
	public static void close(TCsvReader in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			SystemErrorHandler.handledException(ex);
		}
	}
}

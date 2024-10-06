package jp.co.ais.trans2.common.file;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �t�@�C���̃��[�e�B���e�B
 * 
 * @author AIS
 */
public class FileUtil {

	/**
	 * �I�u�W�F�N�g�̕ۑ�<br>
	 * AIS�ݒ�t�H���_�ɕύX�����B(Windows10�ȍ~��TEMP�t�H���_�͕s����̂���)
	 * 
	 * @param obj �I�u�W�F�N�g
	 * @param name �t�@�C������(�t�@�C�����̂�)
	 */
	public static void saveTemporaryObject(Object obj, String name) {
		String path = SystemUtil.getAisSettingDir();
		saveObject(obj, path + name);
	}

	/**
	 * �I�u�W�F�N�g�̕ۑ�
	 * 
	 * @param obj �I�u�W�F�N�g
	 * @param fileName �t�@�C������(�t���p�X)
	 */
	public static void saveObject(Object obj, String fileName) {

		try {

			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, 8192);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			bos.close();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �I�u�W�F�N�g�̕���<br>
	 * AIS�ݒ�t�H���_�ɕύX�����B(Windows10�ȍ~��TEMP�t�H���_�͕s����̂���)
	 * 
	 * @param name �t�@�C������(�t�@�C�����̂�)
	 * @return �I�u�W�F�N�g
	 */
	public static Object getTemporaryObject(String name) {
		String path = SystemUtil.getAisSettingDir();
		return getObject(path + name);
	}

	/**
	 * �I�u�W�F�N�g�̕���
	 * 
	 * @param fileName �t�@�C����
	 * @return �I�u�W�F�N�g
	 */
	public static Object getObject(String fileName) {

		try {
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis, 8192);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object obj = ois.readObject();

			ois.close();
			bis.close();
			fis.close();

			return obj;

		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * PDF�̕ۑ�
	 * 
	 * @param obj
	 * @param ledgerName
	 */
	public static void savePdf(Object obj, String ledgerName) {

		try {

			File file = new File(ledgerName + ".pdf");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, 8192);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);

			oos.flush();
			oos.close();
			bos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * PDF���J��
	 * 
	 * @param obj
	 * @param ledgerName
	 */
	public static void openPdf(Object obj, String ledgerName) {

		try {
			savePdf(obj, ledgerName);
			File f = new File(ledgerName + ".pdf");
			Desktop desktop = Desktop.getDesktop();
			desktop.open(f);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �t�@�C�����폜����
	 * 
	 * @param filePath �폜�t�@�C���p�X(�t���p�X)
	 */
	public static void removeFile(String filePath) {

		try {
			File file = new File(filePath);

			if (file.exists()) {
				file.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �t�@�C�����폜����<br>
	 * AIS�ݒ�t�H���_�ɕύX�����B(Windows10�ȍ~��TEMP�t�H���_�͕s����̂���)
	 * 
	 * @param fileName �폜�t�@�C����
	 */
	public static void removeTempolaryFile(String fileName) {
		String path = SystemUtil.getAisSettingDir();
		removeFile(path + fileName);
	}

	/**
	 * �t�@�C�����R�s�[����B<br>
	 * �R�s�[��t�H���^�����݂��Ȃ��ꍇ�쐬����B
	 * 
	 * @param oldFilePath �R�s�[��
	 * @param newFilePath �R�s�[��
	 * @throws TException
	 */
	public static void copyFile(String oldFilePath, String newFilePath) throws TException {

		FileInputStream fis = null;
		FileOutputStream fos = null;

		FileChannel oldChannel = null;
		FileChannel newChannel = null;

		try {
			// ������
			File oldFile = new File(oldFilePath);
			File newFile = new File(newFilePath);

			// �R�s�[���t�@�C���̑��݃`�F�b�N
			if (!oldFile.exists() || !oldFile.isFile()) {
				// �����Ώۃt�@�C�����擾���t�H���_�ɑ��݂��܂���B�ݒ�t�@�C�����m�F���Ă��������B\n{0}
				throw new TException("I00253", oldFilePath);
			}

			// �R�s�[��p�X�̑��݃`�F�b�N
			if (!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}

			// �X�V����
			long lastModified = oldFile.lastModified();

			fis = new FileInputStream(oldFilePath);
			fos = new FileOutputStream(newFilePath);

			oldChannel = fis.getChannel();
			newChannel = fos.getChannel();

			// �R�s�[����
			oldChannel.transferTo(0, oldChannel.size(), newChannel);

			close(oldChannel);
			close(newChannel);

			// �X�V����
			newFile.setLastModified(lastModified);

		} catch (IOException ex) {
			throw new TException(ex);

		} finally {
			close(oldChannel);
			close(newChannel);

			try {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				//
			}
		}
	}

	/**
	 * FileChannel��close
	 * 
	 * @param channel
	 */
	public static void close(FileChannel channel) {
		try {
			if (channel != null) {
				channel.close();
			}
		} catch (IOException ex) {
			//
		}
	}

	/**
	 * �t�@�C�����ړ�����<br>
	 * �ړ���t�H���^�����݂��Ȃ��ꍇ�A�����쐬����B
	 * 
	 * @param oldFilePath �ړ���
	 * @param newFilePath �ړ���
	 * @throws TException
	 */
	public static void moveFile(String oldFilePath, String newFilePath) throws TException {

		// �t�@�C�����R�s�[����B
		copyFile(oldFilePath, newFilePath);

		// �ړ���t�@�C���̑��݊m�F���s�Ȃ��Ă��猳���폜
		if (new File(newFilePath).exists()) {
			removeFile(oldFilePath);
		}
	}

	/**
	 * �p�X�փA�N�Z�X�`�F�b�N
	 * 
	 * @param path �p�X
	 * @return ���b�Z�[�W
	 */
	public static boolean canAccess(String path) {

		return canAccess(new File(path));
	}

	/**
	 * �p�X�փA�N�Z�X�`�F�b�N
	 * 
	 * @param file �Ώۃt�@�C��
	 * @return ���b�Z�[�W
	 */
	public static boolean canAccess(File file) {

		// �p�X�փA�N�Z�X�m�F
		if (!file.exists() || !file.canRead() || file.listFiles() == null) {
			return false;
		}

		return true;
	}

	/**
	 * �t�@�C�����̋֎~�����ϊ�
	 * 
	 * @param fileName �t�@�C����
	 * @return �֎~�������u�����N�ɒu����������
	 */
	public static String toNonProhibitedName(String fileName) {

		// TODO �p�^�[������͂��đS�Ėԗ�
		return fileName.replaceAll("/", "");
	}

	/**
	 * �t�@�C�����ɕ�����ǉ�����
	 * 
	 * @param fileName �t�@�C����
	 * @param add �ǉ�����
	 * @return �ǉ��㕶��
	 */
	public static String addName(String fileName, String add) {
		int point = fileName.lastIndexOf('.');

		if (point == -1) {
			return fileName + add;
		}

		return fileName.substring(0, point) + add + fileName.substring(point, fileName.length());
	}

	/**
	 * �g���q�����O
	 * 
	 * @param fileName �t�@�C����
	 * @return �g���q�����̖���
	 */
	public static String removeExtension(String fileName) {
		int point = fileName.lastIndexOf('.');

		if (point <= 0) {
			return fileName;
		}

		return fileName.substring(0, point);
	}

	/**
	 * URL����t�@�C���ɕۑ�����
	 * 
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	public static void copyURLToFile(URL source, File destination) throws IOException {
		if ((destination.getParentFile() != null) && (!(destination.getParentFile().exists()))) {
			destination.getParentFile().mkdirs();
		}

		if ((destination.exists()) && (!(destination.canWrite()))) {
			String message = "Unable to open file " + destination + " for writing.";

			throw new IOException(message);
		}

		InputStream input = source.openStream();
		try {
			FileOutputStream output = new FileOutputStream(destination);
			try {
				copy(input, output);
			} finally {
				ResourceUtil.closeOutputStream(output);
			}
		} finally {
			ResourceUtil.closeInputStream(input);
		}
	}

	/**
	 * �C���v�b�g����A�E�g�v�b�g�֕ϊ�
	 * 
	 * @param input
	 * @param output
	 * @return �J�E���g
	 * @throws IOException
	 */
	public static int copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[4096];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}

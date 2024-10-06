package jp.co.ais.trans.common.util;

import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

import javax.imageio.*;
import javax.swing.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���\�[�X�n���[�e�B���e�B
 */
public final class ResourceUtil {

	/** �o�b�t�@�[�T�C�Y�i�o�C�g���j4KB */
	public static final int BUFFER_SIZE = 4096;

	/**
	 * ���\�[�X�p�X
	 * 
	 * @param clazz �N���X
	 * @return ���\�[�X�p�X
	 */
	public static String getResourcePath(Class clazz) {
		return org.seasar.framework.util.ResourceUtil.getResourcePath(clazz);
	}

	/**
	 * �N���X�̔z�u�ꏊ����Ƀt�@�C���p�X���擾.
	 * 
	 * @param cls ��N���X
	 * @param path �p�X
	 * @return �t�@�C���p�X
	 */
	public static String convertPath(Class cls, String path) {
		if (org.seasar.framework.util.ResourceUtil.getResourceNoException(path) != null) {
			return path;
		}
		String prefix = cls.getName().replace('.', '/').replaceFirst("/[^/]+$", "");
		return prefix + "/" + path;
	}

	/**
	 * �N���X�̔z�u�ꏊ����Ƀ��\�[�X���擾.
	 * 
	 * @param cls ��N���X
	 * @param path �p�X
	 * @return ���\�[�X
	 */
	public static File getResourceAsFile(Class cls, String path) {
		return org.seasar.framework.util.ResourceUtil.getResourceAsFile(convertPath(cls, path));
	}

	/**
	 * �w�肳�ꂽ�p�X��̃��\�[�X���擾.
	 * 
	 * @param path �p�X
	 * @return ���\�[�X
	 */
	public static File getResourceAsFile(String path) {
		return org.seasar.framework.util.ResourceUtil.getResourceAsFile(path);
	}

	/**
	 * Icon�̎擾
	 * 
	 * @param cls ��N���X
	 * @param path �p�X
	 * @return Icon
	 */
	public static ImageIcon getImage(Class cls, String path) {

		return new ImageIcon(getResource(cls, path));
	}

	/**
	 * Icon�̎擾
	 * 
	 * @param path �p�X
	 * @return Icon
	 */
	public static BufferedImage getImage(String path) {
		try {
			return ImageIO.read(getResource(path));

		} catch (IOException ex) {
			throw new TRuntimeException(ex, "E00008");
		}
	}

	/**
	 * Icon�̎擾
	 * 
	 * @param path �p�X
	 * @return Icon
	 */
	public static Icon getIcon(String path) {
		return new ImageIcon(getResource(path));
	}

	/**
	 * URL�̎擾
	 * 
	 * @param path �p�X
	 * @return URL
	 */
	public static URL getResource(String path) {
		try {
			return org.seasar.framework.util.ResourceUtil.getResource(path);

		} catch (RuntimeException ex) {
			throw new TRuntimeException(ex, "E00008");
		}
	}

	/**
	 * URL�̎擾
	 * 
	 * @param cls ��N���X
	 * @param path �p�X
	 * @return URL
	 */
	public static URL getResource(Class cls, String path) {
		return getResource(convertPath(cls, path));
	}

	/**
	 * InputStream�̎擾
	 * 
	 * @param path �p�X
	 * @return InputStream
	 */
	public static InputStream getResourceAsStream(String path) {

		return org.seasar.framework.util.ResourceUtil.getResourceAsStream(path, null);
	}

	/**
	 * InputStream�̎擾
	 * 
	 * @param cls �N���X
	 * @param path �p�X
	 * @return InputStream
	 */
	public static InputStream getResourceAsStream(Class cls, String path) {

		return org.seasar.framework.util.ResourceUtil.getResourceAsStream(convertPath(cls, path), null);
	}

	/**
	 * �o�C�g����摜�̎擾
	 * 
	 * @param bytes �o�C�i��
	 * @return �摜
	 */
	public static BufferedImage toImage(byte[] bytes) {
		try {
			if (bytes == null) {
				return null;
			}

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			return ImageIO.read(bais);

		} catch (IOException ex) {
			return null;
		}
	}

	/**
	 * �w�肳�ꂽ�t�@�C�����������݉\��Ԃ��ǂ�����Ԃ�
	 * 
	 * @param fileName �t�@�C����
	 * @return �������݉\�ȏꍇtrue
	 */
	public static boolean canWrite(String fileName) {

		if (!new File(fileName).exists()) {
			return true;
		}

		FileOutputStream fo = null;

		try {
			fo = new FileOutputStream(fileName);

			return true;

		} catch (IOException ex) {
			return false;
		} finally {
			closeOutputStream(fo);
		}
	}

	/**
	 * �o�C�i���f�[�^���t�@�C���ɏ�������
	 * 
	 * @param fileName �t�@�C����
	 * @param bytes �o�C�i���f�[�^
	 * @throws IOException �������ݎ��s
	 */
	public static void writeBinary(String fileName, byte[] bytes) throws IOException {
		FileOutputStream fo = null;

		try {
			fo = new FileOutputStream(fileName);

			fo.write(bytes);
			fo.flush();

		} finally {
			closeOutputStream(fo);
		}
	}

	/**
	 * InputStream��close
	 * 
	 * @param stream InputStream
	 */
	public static void closeInputStream(InputStream stream) {

		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException ex) {
			SystemErrorHandler.handledException(ex);
		}
	}

	/**
	 * OutputStream��close
	 * 
	 * @param stream OutputStream
	 */
	public static void closeOutputStream(OutputStream stream) {

		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException ex) {
			SystemErrorHandler.handledException(ex);
		}
	}

	/**
	 * PrintWriter��close
	 * 
	 * @param bw PrintWriter
	 */
	public static void closePrintWriter(PrintWriter bw) {
		if (bw != null) {
			bw.close();
		}
	}

	/**
	 * Zip���k.�w��t�@�C����ZIP�Ɋi�[���ăo�C�i����.<br>
	 * 1�t�@�C���݂̂�Ώ�
	 * 
	 * @param fileName �i�[�t�@�C����
	 * @param bytes �i�[�Ώۃt�@�C���o�C�i��
	 * @return ZIP�o�C�i��
	 * @throws IOException
	 */
	public static byte[] zipBinary(String fileName, byte[] bytes) throws IOException {

		ByteArrayOutputStream bos = null;
		ZipOutputStream zos = null;

		try {
			// �t�@�C�����k
			bos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(bos);

			ZipEntry zent = new ZipEntry(fileName);
			zos.putNextEntry(zent);

			// ZIP�ɒǉ�����[�t�@�C��1]��ǂݎ����̓X�g���[���𐶐�����
			BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(bytes));

			// ���̓X�g���[������ZIP�t�@�C���ɒǉ�����t�@�C���̓��e��ǂݎ��
			int count = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((count = in.read(buffer)) > 0) {

				if (count != BUFFER_SIZE) {
					buffer = Arrays.copyOf(buffer, count);
				}
				// ZIP�t�@�C�����o�͂���X�g���[���ɑ΂���write()����B
				zos.write(buffer);
			}

			// ���̓X�g���[�������
			in.close();
			in = null;

			// �G���g�������
			zos.closeEntry();

			return bos.toByteArray();

		} finally {
			ResourceUtil.closeOutputStream(bos);
			ResourceUtil.closeOutputStream(zos);
		}
	}

	/**
	 * Zip��.Zip�`���t�@�C������t�@�C�������o���ď�������.<br>
	 * Zip���ɂ�1�t�@�C���݂̂�z��
	 * 
	 * @param fileName �o�̓t�@�C����
	 * @param bytes Zip�`���t�@�C���o�C�i��
	 * @throws IOException
	 */
	public static void writeBinaryInZip(String fileName, byte[] bytes) throws IOException {

		BufferedOutputStream out = null; // �𓀃t�@�C���o�͗p�X�g���[��
		ZipInputStream in = null; // Zip�t�@�C���Ǎ��p�X�g���[��

		try {
			in = new ZipInputStream(new ByteArrayInputStream(bytes));

			@SuppressWarnings("unused")
			ZipEntry zipEntry = null;
			boolean hasData = false;

			// ZIP�t�@�C���̎��̃G���g���̐擪�ɃX�g���[�����ړ�����
			while ((zipEntry = in.getNextEntry()) != null) {

				// ZIP�t�@�C�����̃G���g�����t�@�C���ɏo�͂���X�g���[���𐶐�����
				out = new BufferedOutputStream(new FileOutputStream(fileName));

				// 1�o�C�g���A�G���g������ǂݍ���ŁA�W�J��t�@�C���ɏo�͂���
				int count = 0;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((count = in.read(buffer)) > 0) {

					if (count != BUFFER_SIZE) {
						buffer = Arrays.copyOf(buffer, count);
					}
					out.write(buffer);
					hasData = true;
				}

				// ���݂�ZIP�G���g�������
				in.closeEntry();

				// �o�̓X�g���[�������
				out.close();
				out = null;
			}

			if (!hasData) {
				// ��t�@�C�������
				writeBinary(fileName, new byte[0]);
			}

		} finally {
			ResourceUtil.closeInputStream(in);
			ResourceUtil.closeOutputStream(out);
		}
	}

	/**
	 * Zip��.Zip�`���t�@�C������t�@�C�������o���ăo�C�i���ɖ߂�.<br>
	 * Zip���ɂ�1�t�@�C���݂̂�z��
	 * 
	 * @param bytes Zip�`���t�@�C���o�C�i��
	 * @return �𓀌�t�@�C���o�C�i��
	 * @throws IOException
	 */
	public static byte[] toBinaryInZip(byte[] bytes) throws IOException {

		BufferedOutputStream out = null; // �𓀃t�@�C���o�͗p�X�g���[��
		ZipInputStream in = null; // Zip�t�@�C���Ǎ��p�X�g���[��
		ByteArrayOutputStream baos = null;

		try {
			in = new ZipInputStream(new ByteArrayInputStream(bytes));

			baos = new ByteArrayOutputStream();

			@SuppressWarnings("unused")
			ZipEntry zipEntry = null;

			// ZIP�t�@�C���̎��̃G���g���̐擪�ɃX�g���[�����ړ�����
			while ((zipEntry = in.getNextEntry()) != null) {

				// ZIP�t�@�C�����̃G���g�����t�@�C���ɏo�͂���X�g���[���𐶐�����
				out = new BufferedOutputStream(baos);

				// 1�o�C�g���A�G���g������ǂݍ���ŁA�W�J��t�@�C���ɏo�͂���
				int count = 0;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((count = in.read(buffer)) > 0) {

					if (count != BUFFER_SIZE) {
						buffer = Arrays.copyOf(buffer, count);
					}
					out.write(buffer);
				}

				// ���݂�ZIP�G���g�������
				in.closeEntry();

				// �o�̓X�g���[�������
				out.close();
				out = null;
			}

			return baos.toByteArray();

		} finally {
			ResourceUtil.closeOutputStream(baos);
			ResourceUtil.closeInputStream(in);
			ResourceUtil.closeOutputStream(out);
		}
	}

	/**
	 * �w��t�@�C�����t�H���_�����݂��邩�ǂ������f����
	 * 
	 * @param filePath �w��t�@�C�����t�H���_�̃p�X
	 * @return �w��t�@�C�����t�H���_�����݂��邩�ǂ���
	 */
	public static boolean isFilePathExists(String filePath) {
		// �w��t�@�C�����t�H���_
		File file = new File(filePath);

		// ���ݔ��f
		return file.exists();
	}

	/**
	 * �t�@�C�����̂���g���q���擾
	 * 
	 * @param f �t�@�C��
	 * @return �g���q
	 */
	public static String getExtension(File f) {
		String ext = "";
		String filename = f.getName();
		int dotIndex = filename.lastIndexOf('.');

		if ((dotIndex > 0) && (dotIndex < filename.length() - 1)) {
			ext = filename.substring(dotIndex + 1).toLowerCase();
		}

		return ext;
	}

	/**
	 * �w��t�@�C�����o�C�i���`���ɕϊ�����.
	 * 
	 * @param fileName �t�@�C����
	 * @return �o�C�i��
	 * @throws IOException
	 */
	public static byte[] readBinarry(String fileName) throws IOException {
		FileInputStream in = null;
		ByteArrayOutputStream bo = null;

		try {
			in = new FileInputStream(fileName);
			bo = new ByteArrayOutputStream();

			int count = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((count = in.read(buffer)) > 0) {

				if (count != BUFFER_SIZE) {
					buffer = Arrays.copyOf(buffer, count);
				}
				bo.write(buffer);
			}

			return bo.toByteArray();

		} finally {
			closeOutputStream(bo);
			closeInputStream(in);
		}
	}

	/**
	 * �w�蕶���Ńe�L�X�g�t�@�C�����쐬����
	 * 
	 * @param fileName �t�@�C����
	 * @param txt �e�L�X�g
	 * @throws IOException
	 */
	public static void makeTextFile(String fileName, String txt) throws IOException {

		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(txt);

			bw.close();
			osw.close();

		} finally {
			closeOutputStream(fos);
		}
	}

	/**
	 * �o�C�i���̃I�u�W�F�N�g�ϊ�
	 * 
	 * @param bytes �o�C�i��
	 * @return �I�u�W�F�N�g
	 */
	public static Object toObject(byte[] bytes) {
		ByteArrayInputStream input = null;
		ObjectInputStream ois = null;

		try {
			input = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(input);

			return ois.readObject();

		} catch (Exception ex) {
			throw new TRuntimeException(ex);

		} finally {
			ResourceUtil.closeInputStream(input);
			ResourceUtil.closeInputStream(ois);
		}
	}

	/**
	 * �I�u�W�F�N�g�̃o�C�i���ϊ�
	 * 
	 * @param obj �I�u�W�F�N�g
	 * @return �o�C�i��
	 */
	public static byte[] toBinarry(Object obj) {
		ByteArrayOutputStream out = null;
		ObjectOutputStream oos = null;

		try {
			out = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(out);
			oos.writeObject(obj);
			oos.flush();

			return out.toByteArray();

		} catch (Exception ex) {
			throw new TRuntimeException(ex);

		} finally {
			ResourceUtil.closeOutputStream(out);
			ResourceUtil.closeOutputStream(oos);
		}
	}

	/**
	 * �g���q���擾
	 * 
	 * @param fileName �t�@�C����
	 * @return �g���q
	 */
	public static String getExtension(String fileName) {
		if (Util.isNullOrEmpty(fileName)) {
			return "";
		}

		String[] names = StringUtil.split(fileName, ".");
		if (names.length == 0) {
			return "";
		}

		return names[names.length - 1];
	}
}

package jp.co.ais.trans2.common.file;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.print.*;

/**
 * �t�@�C������MUtil
 */
public class FileTransferUtil {

	// ///////////////////////�N���C�A���g�T�C�g/////////////////////////

	/**
	 * WEB���M�Ώۃ��X�g�𐮔�
	 * 
	 * @param objectList
	 * @param fileList
	 * @param fileIndexs
	 * @param arg
	 * @throws Exception
	 */
	public static void reflectWebObjectList(List<Object> objectList, List<File> fileList, List<Integer> fileIndexs, Object... arg) throws Exception {
		reflectWebObjectList(true, objectList, fileList, fileIndexs, arg);
	}

	/**
	 * WEB���M�Ώۃ��X�g�𐮔�
	 * 
	 * @param isMappingObjectList true:���M�Ώۂ𐮔�
	 * @param objectList
	 * @param fileList
	 * @param fileIndexs
	 * @param arg
	 * @throws Exception
	 */
	public static void reflectWebObjectList(boolean isMappingObjectList, List<Object> objectList, List<File> fileList, List<Integer> fileIndexs, Object... arg) throws Exception {

		if (isMappingObjectList) {
			// ���ڂ͑Ώۃ��X�g�쐬���t�@�C������M�ɕϊ�
			for (Object obj : arg) {

				if (obj == null) {
					objectList.add(obj);
					continue;
				}

				if (obj instanceof File) {
					TFileTransfer bean = new TFileAdpater();
					objectList.add(bean);
					bean.setFile((File) obj);
					continue;
				}

				objectList.add(obj);
			}
		}

		// ���ڂ͑��M�t�@�C����Z�߂�
		for (Object obj : objectList) {
			reflectWebFileList(obj, fileList, fileIndexs);
		}
	}

	/**
	 * WEB���M�t�@�C���Ώۃ��X�g�𐮔�
	 * 
	 * @param obj
	 * @param fileList
	 * @param fileIndexs
	 * @throws Exception
	 */
	protected static void reflectWebFileList(Object obj, List<File> fileList, List<Integer> fileIndexs) throws Exception {
		if (obj == null) {
			return;
		}

		if (!(obj instanceof TFileTransfer || obj instanceof List || obj instanceof TransferBase)) {
			return;
		}

		if (obj instanceof TFileTransfer) {
			TFileTransfer bean = (TFileTransfer) obj;

			if (bean.getFile() == null) {
				return;
			}

			int fileIndex = fileIndexs.size();
			bean.setFileIndex(fileIndex);

			fileList.add(bean.getFile());
			fileIndexs.add(fileIndex);
			return;
		}

		// ���܂Ńt�@�C������M���̂�T���đ��M���X�g�ɐ�������
		if (obj instanceof List) {
			for (Object obj2 : (List) obj) {
				reflectWebFileList(obj2, fileList, fileIndexs);
			}
			return;
		}

		if (obj instanceof TransferBase) {
			// �S�t�B�[���h�̒l�����X�g�֊i�[����
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);

				if (field.isSynthetic() || field.isEnumConstant()) {
					// ����Field�͏��O
					continue;
				}

				Object obj2 = field.get(obj);

				reflectWebFileList(obj2, fileList, fileIndexs);
			}
		}
	}

	// ///////////////////////�T�[�o�T�C�g/////////////////////////

	/**
	 * WEB��M�Ώۃ��X�g�𐮔�
	 * 
	 * @param objectList
	 * @param fileList
	 * @throws Exception
	 */
	public static void dispatchWebObjectList(List<Object> objectList, List<File> fileList) throws Exception {
		if (objectList == null) {
			return;
		}

		for (int i = 0; i < objectList.size(); i++) {
			Object obj = objectList.get(i);

			if (obj == null) {
				continue;
			}

			if (obj instanceof TFileAdpater) {
				// �t�@�C��Adapter�̏ꍇ��File�ɍ����ւ���

				TFileAdpater bean = (TFileAdpater) obj;
				objectList.set(i, fileList.get(bean.getFileIndex()));
				continue;
			}

			// �S�t�B�[���h�̒l�����X�g�֊i�[����
			dispatchWebFileList(obj, fileList);
		}
	}

	/**
	 * WEB��M�t�@�C���Ώۃ��X�g�𐮔�
	 * 
	 * @param obj
	 * @param fileList
	 * @throws Exception
	 */
	protected static void dispatchWebFileList(Object obj, List<File> fileList) throws Exception {
		if (obj == null) {
			return;
		}

		if (!(obj instanceof TFileTransfer || obj instanceof List || obj instanceof TransferBase)) {
			return;
		}

		if (obj instanceof TFileTransfer) {
			TFileTransfer bean = (TFileTransfer) obj;

			if (bean.getFileIndex() == -1) {
				bean.setFile(null);
				return;
			}

			File file = fileList.get(bean.getFileIndex());
			bean.setFile(file);

			return;
		}

		// ���܂Ńt�@�C������M���̂�T���đ��M���X�g�ɐ�������
		if (obj instanceof List) {
			for (Object obj2 : (List) obj) {
				dispatchWebFileList(obj2, fileList);
			}
			return;
		}

		if (obj instanceof TransferBase) {
			// �S�t�B�[���h�̒l�����X�g�֊i�[����
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);

				if (field.isSynthetic() || field.isEnumConstant()) {
					// ����Field�͏��O
					continue;
				}

				Object obj2 = field.get(obj);

				dispatchWebFileList(obj2, fileList);
			}
		}
	}

	/**
	 * �t�@�C�����w��T�[�o�t�H���_�Ƀt�@�C�������
	 * 
	 * @param file
	 * @return �T�[�o�T�C�g�t�@�C����
	 */
	public static String createServerFile(File file) {
		return createServerFile(file, null);
	}

	/**
	 * �t�@�C�����w��T�[�o�t�H���_�Ƀt�@�C�������
	 * 
	 * @param file
	 * @param defaultFileName �f�t�H���g�t�@�C����
	 * @return �T�[�o�T�C�g�t�@�C����
	 */
	public static String createServerFile(File file, String defaultFileName) {
		try {
			String folder = ServerConfig.getProperty("trans.attachment.folder");
			File dir = new File(folder);
			if (dir.isDirectory() && dir.exists()) {

				String fileName = !Util.isNullOrEmpty(defaultFileName) ? defaultFileName : StringUtil.createUID();

				TPrinter printer = new TPrinter(false);
				byte[] data = ResourceUtil.readBinarry(file.getPath());
				printer.createResultFile(folder, fileName, data);

				return fileName;

			} else {
				ServerLogger.error(" incorrect attachment folder setting. [" + folder + "]");
			}

		} catch (Exception ex) {
			ServerLogger.error("can't find attachment folder setting.");
		}

		return null;
	}

	/**
	 * �w��T�[�o�t�H���_�̃t�@�C�����o�C�i���ɕϊ�
	 * 
	 * @param fileName
	 * @param serverFileName
	 * @return �t�@�C���o�C�i��
	 */
	public static byte[] getServerFile(String fileName, String serverFileName) {

		if (Util.isNullOrEmpty(fileName) || Util.isNullOrEmpty(serverFileName)) {
			return null;
		}

		try {
			String folder = ServerConfig.getProperty("trans.attachment.folder");

			File dir = new File(folder);
			if (dir.isDirectory() && dir.exists()) {

				byte[] data = ResourceUtil.zipBinary(fileName, ResourceUtil.readBinarry(folder + File.separator + serverFileName));
				return data;

			} else {
				ServerLogger.error(" incorrect attachment folder setting. [" + folder + "]");
			}

		} catch (Exception ex) {
			ServerLogger.error("can't find attachment folder setting.");
		}

		return null;
	}

	/**
	 * �w��T�[�o�t�H���_�̃t�@�C�����o�C�i���ɕϊ�<br>
	 * ZIP�Ȃ�
	 * 
	 * @param fileName
	 * @param serverFileName
	 * @return �t�@�C���o�C�i��
	 */
	public static byte[] getServerFileNoZip(String fileName, String serverFileName) {

		if (Util.isNullOrEmpty(fileName) || Util.isNullOrEmpty(serverFileName)) {
			return null;
		}

		String fileURI = "";

		try {
			String folder = ServerConfig.getProperty("trans.attachment.folder");
			fileURI = folder + File.separator + serverFileName;

			File dir = new File(folder);
			if (dir.isDirectory() && dir.exists()) {

				byte[] data = ResourceUtil.readBinarry(fileURI);
				return data;

			} else {
				ServerLogger.error(" incorrect attachment folder setting. [" + folder + "]");
			}

		} catch (Exception ex) {
			ServerLogger.error("can't find attachment folder setting.[" + fileURI + "]", ex);
		}

		return null;
	}

	/**
	 * �w��T�[�o�t�H���_�̃t�@�C�����폜����
	 * 
	 * @param serverFileName
	 */
	public static void deleteServerFile(String serverFileName) {

		if (Util.isNullOrEmpty(serverFileName)) {
			return;
		}

		String fileName = serverFileName;

		try {
			String folder = ServerConfig.getProperty("trans.attachment.folder");
			fileName = folder + File.separator + serverFileName;

			File file = new File(fileName);
			if (file.exists()) {
				ServerLogger.debug("delete server file. [" + fileName + "]");
				file.delete();
			}

		} catch (Exception ex) {
			ServerLogger.error("can 't delete file. [" + fileName + "]");
		}

	}
}

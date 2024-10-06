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
 * ファイル送受信Util
 */
public class FileTransferUtil {

	// ///////////////////////クライアントサイト/////////////////////////

	/**
	 * WEB送信対象リストを整備
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
	 * WEB送信対象リストを整備
	 * 
	 * @param isMappingObjectList true:送信対象を整備
	 * @param objectList
	 * @param fileList
	 * @param fileIndexs
	 * @param arg
	 * @throws Exception
	 */
	public static void reflectWebObjectList(boolean isMappingObjectList, List<Object> objectList, List<File> fileList, List<Integer> fileIndexs, Object... arg) throws Exception {

		if (isMappingObjectList) {
			// 一回目は対象リスト作成＆ファイル送受信に変換
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

		// 二回目は送信ファイルを纏める
		for (Object obj : objectList) {
			reflectWebFileList(obj, fileList, fileIndexs);
		}
	}

	/**
	 * WEB送信ファイル対象リストを整備
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

		// 奥までファイル送受信ものを探して送信リストに整備する
		if (obj instanceof List) {
			for (Object obj2 : (List) obj) {
				reflectWebFileList(obj2, fileList, fileIndexs);
			}
			return;
		}

		if (obj instanceof TransferBase) {
			// 全フィールドの値をリストへ格納する
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);

				if (field.isSynthetic() || field.isEnumConstant()) {
					// 特殊Fieldは除外
					continue;
				}

				Object obj2 = field.get(obj);

				reflectWebFileList(obj2, fileList, fileIndexs);
			}
		}
	}

	// ///////////////////////サーバサイト/////////////////////////

	/**
	 * WEB受信対象リストを整備
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
				// ファイルAdapterの場合→Fileに差し替える

				TFileAdpater bean = (TFileAdpater) obj;
				objectList.set(i, fileList.get(bean.getFileIndex()));
				continue;
			}

			// 全フィールドの値をリストへ格納する
			dispatchWebFileList(obj, fileList);
		}
	}

	/**
	 * WEB受信ファイル対象リストを整備
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

		// 奥までファイル送受信ものを探して送信リストに整備する
		if (obj instanceof List) {
			for (Object obj2 : (List) obj) {
				dispatchWebFileList(obj2, fileList);
			}
			return;
		}

		if (obj instanceof TransferBase) {
			// 全フィールドの値をリストへ格納する
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);

				if (field.isSynthetic() || field.isEnumConstant()) {
					// 特殊Fieldは除外
					continue;
				}

				Object obj2 = field.get(obj);

				dispatchWebFileList(obj2, fileList);
			}
		}
	}

	/**
	 * ファイルを指定サーバフォルダにファイルを作る
	 * 
	 * @param file
	 * @return サーバサイトファイル名
	 */
	public static String createServerFile(File file) {
		return createServerFile(file, null);
	}

	/**
	 * ファイルを指定サーバフォルダにファイルを作る
	 * 
	 * @param file
	 * @param defaultFileName デフォルトファイル名
	 * @return サーバサイトファイル名
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
	 * 指定サーバフォルダのファイルをバイナリに変換
	 * 
	 * @param fileName
	 * @param serverFileName
	 * @return ファイルバイナリ
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
	 * 指定サーバフォルダのファイルをバイナリに変換<br>
	 * ZIPなし
	 * 
	 * @param fileName
	 * @param serverFileName
	 * @return ファイルバイナリ
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
	 * 指定サーバフォルダのファイルを削除する
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

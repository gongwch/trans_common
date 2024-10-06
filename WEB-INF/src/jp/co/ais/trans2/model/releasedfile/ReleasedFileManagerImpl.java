package jp.co.ais.trans2.model.releasedfile;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * MG0029ReleasedFileMaster - リリースファイル一覧 - Model Class
 * 
 * @author AIS
 */
public class ReleasedFileManagerImpl extends TModel implements ReleasedFileManager {

	/**
	 * 情報検索-Excel出力
	 * 
	 * @return リリースファイル一覧
	 * @throws TException
	 */
	public byte[] getExcel() throws TException {

		try {
			List<ReleasedFile> releaseList = new ArrayList<ReleasedFile>();

			String rootPath = ServerConfig.getRootPath();

			List<ReleasedFile> excelList = findFiles(rootPath, releaseList);

			if (excelList == null || excelList.isEmpty()) {
				return null;
			}

			ReleasedFileExcel exl = new ReleasedFileExcel(getUser().getLanguage());

			return exl.getExcel(excelList);
		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * 指定のフォルダに存在するすべてのファイルをファイルリストに追加する。
	 * 
	 * @param rootPath ルートフォルダ
	 * @param releaseList
	 * @return releaseList
	 */
	public List<ReleasedFile> findFiles(String rootPath, List<ReleasedFile> releaseList) {
		File pathName = new File(rootPath);

		// ファイル名の配列を生成
		String[] fileNames = pathName.list();

		// 下位ファイルが存在しないと処理なし
		if (fileNames.length == 0) {
			return null;
		}

		// 下位ファイルにフォルダ区分子を追加する。
		String lastChar = rootPath.substring(rootPath.length() - 1, rootPath.length());
		if (lastChar.equals(File.separator) == false) {
			rootPath = rootPath + File.separator;
		}

		// 下位ファイルを生成しファイルリストに追加
		for (int i = 0; i < fileNames.length; i++) {
			File tf = new File(rootPath, fileNames[i]);

			// フォルダの場合メソッド再帰呼び
			if (tf.isDirectory()) {

				// ログファイルは検索しない
				if (!fileNames[i].equals("log")) {
					findFiles(tf.getPath(), releaseList);
				}

				// ファイルだと情報を取り出しファイルリストに追加
			} else if (tf.isFile()) {
				ReleasedFile releaseFile = new ReleasedFile();
				releaseFile.setFolder(tf.getParent());
				releaseFile.setFile(tf.getName());
				Date date = new Date(tf.lastModified());
				releaseFile.setUpdate(date);
				releaseFile.setSize(tf.length());
				releaseList.add(releaseFile);
			}
		}
		return releaseList;

	}

}

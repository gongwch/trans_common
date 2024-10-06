package jp.co.ais.trans.logic.system.impl;

import java.io.*;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.entity.*;

/**
 * リリースファイル一覧ロジック
 */
public class ReleasedFileLogicImpl implements ReleasedFileLogic {

	/** ファイルリスト */
	private List<ReleasedFileObject> releaseList = new ArrayList<ReleasedFileObject>();

	/**
	 * リリースファイルリストを取得
	 * 
	 * @param rootPath ファイルPATH
	 * @return List リリースファイルリスト
	 */
	public List<ReleasedFileObject> getReleasedFileList(String rootPath) {
		// ファイル検索及びリスト追加
		findFiles(rootPath);
		return releaseList;
	}

	/**
	 * 指定のフォルダに存在するすべてのファイルをファイルリストに追加する。
	 * 
	 * @param path ルートフォルダ
	 */
	private void findFiles(String path) {
		File pathName = new File(path);

		// ファイル名の配列を生成
		String[] fileNames = pathName.list();

		// 下位ファイルが存在しないと処理なし
		if (fileNames.length == 0) {
			return;
		}

		// 下位ファイルにフォルダ区分子を追加する。
		String lastChar = path.substring(path.length() - 1, path.length());
		if (lastChar.equals(File.separator) == false) {
			path = path + File.separator;
		}

		// 下位ファイルを生成しファイルリストに追加
		for (int i = 0; i < fileNames.length; i++) {
			File tf = new File(path, fileNames[i]);

			// フォルダの場合メソッド再帰呼び
			if (tf.isDirectory()) {

				// ログファイルは検索しない
				if (!fileNames[i].equals("log")) {
					findFiles(tf.getPath());
				}

				// ファイルだと情報を取り出しファイルリストに追加
			} else if (tf.isFile()) {
				ReleasedFileObject releaseFile = new ReleasedFileObject();
				releaseFile.setPATH_NAME(tf.getParent());
				releaseFile.setFILE_NAME(tf.getName());
				releaseFile.setUPDATE_DATE(DateUtil.getYMDString((tf.lastModified())));
				releaseFile.setLENGTH(tf.length());

				releaseList.add(releaseFile);
			}
		}

	}
}

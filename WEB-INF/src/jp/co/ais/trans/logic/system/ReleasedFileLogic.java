package jp.co.ais.trans.logic.system;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * リリースファイル一覧ビジネスロジック
 * 
 * @author roh
 */
public interface ReleasedFileLogic {

	/**
	 * リリースファイルリストを取得
	 * 
	 * @param rootPath ファイルPATH
	 * @return List リリースファイルリスト
	 */
	List<ReleasedFileObject> getReleasedFileList(String rootPath);

}

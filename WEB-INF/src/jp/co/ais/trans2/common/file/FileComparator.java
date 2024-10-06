package jp.co.ais.trans2.common.file;

import java.io.*;
import java.util.*;

/**
 * ファイル/フォルダのComparator
 */
public class FileComparator implements Comparator {

	/** 比較タイプ */
	public enum TYPE {
		/** 名称 */
		NAME,

		/** 更新日時 */
		UPDATE_TIME
	}

	/** 比較タイプ */
	protected TYPE type;

	/**
	 * コンストラクタ.
	 */
	public FileComparator() {
		this(TYPE.NAME);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param type 比較タイプ
	 */
	public FileComparator(TYPE type) {
		this.type = type;
	}

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) {
		File f1 = (File) o1;
		File f2 = (File) o2;

		switch (type) {
			case NAME: // 名称
				return f1.getName().compareTo(f2.getName());

			case UPDATE_TIME: // 更新日時
				if (f1.lastModified() == f2.lastModified()) {
					return 0;
				}

				return f1.lastModified() < f2.lastModified() ? -1 : 1;

			default:
				return 0;
		}
	}
}

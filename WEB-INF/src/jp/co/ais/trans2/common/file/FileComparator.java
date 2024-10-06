package jp.co.ais.trans2.common.file;

import java.io.*;
import java.util.*;

/**
 * �t�@�C��/�t�H���_��Comparator
 */
public class FileComparator implements Comparator {

	/** ��r�^�C�v */
	public enum TYPE {
		/** ���� */
		NAME,

		/** �X�V���� */
		UPDATE_TIME
	}

	/** ��r�^�C�v */
	protected TYPE type;

	/**
	 * �R���X�g���N�^.
	 */
	public FileComparator() {
		this(TYPE.NAME);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param type ��r�^�C�v
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
			case NAME: // ����
				return f1.getName().compareTo(f2.getName());

			case UPDATE_TIME: // �X�V����
				if (f1.lastModified() == f2.lastModified()) {
					return 0;
				}

				return f1.lastModified() < f2.lastModified() ? -1 : 1;

			default:
				return 0;
		}
	}
}

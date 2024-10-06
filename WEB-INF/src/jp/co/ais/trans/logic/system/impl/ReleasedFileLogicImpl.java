package jp.co.ais.trans.logic.system.impl;

import java.io.*;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �����[�X�t�@�C���ꗗ���W�b�N
 */
public class ReleasedFileLogicImpl implements ReleasedFileLogic {

	/** �t�@�C�����X�g */
	private List<ReleasedFileObject> releaseList = new ArrayList<ReleasedFileObject>();

	/**
	 * �����[�X�t�@�C�����X�g���擾
	 * 
	 * @param rootPath �t�@�C��PATH
	 * @return List �����[�X�t�@�C�����X�g
	 */
	public List<ReleasedFileObject> getReleasedFileList(String rootPath) {
		// �t�@�C�������y�у��X�g�ǉ�
		findFiles(rootPath);
		return releaseList;
	}

	/**
	 * �w��̃t�H���_�ɑ��݂��邷�ׂẴt�@�C�����t�@�C�����X�g�ɒǉ�����B
	 * 
	 * @param path ���[�g�t�H���_
	 */
	private void findFiles(String path) {
		File pathName = new File(path);

		// �t�@�C�����̔z��𐶐�
		String[] fileNames = pathName.list();

		// ���ʃt�@�C�������݂��Ȃ��Ə����Ȃ�
		if (fileNames.length == 0) {
			return;
		}

		// ���ʃt�@�C���Ƀt�H���_�敪�q��ǉ�����B
		String lastChar = path.substring(path.length() - 1, path.length());
		if (lastChar.equals(File.separator) == false) {
			path = path + File.separator;
		}

		// ���ʃt�@�C���𐶐����t�@�C�����X�g�ɒǉ�
		for (int i = 0; i < fileNames.length; i++) {
			File tf = new File(path, fileNames[i]);

			// �t�H���_�̏ꍇ���\�b�h�ċA�Ă�
			if (tf.isDirectory()) {

				// ���O�t�@�C���͌������Ȃ�
				if (!fileNames[i].equals("log")) {
					findFiles(tf.getPath());
				}

				// �t�@�C�����Ə������o���t�@�C�����X�g�ɒǉ�
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

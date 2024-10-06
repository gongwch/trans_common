package jp.co.ais.trans2.model.releasedfile;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * MG0029ReleasedFileMaster - �����[�X�t�@�C���ꗗ - Model Class
 * 
 * @author AIS
 */
public class ReleasedFileManagerImpl extends TModel implements ReleasedFileManager {

	/**
	 * ��񌟍�-Excel�o��
	 * 
	 * @return �����[�X�t�@�C���ꗗ
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
	 * �w��̃t�H���_�ɑ��݂��邷�ׂẴt�@�C�����t�@�C�����X�g�ɒǉ�����B
	 * 
	 * @param rootPath ���[�g�t�H���_
	 * @param releaseList
	 * @return releaseList
	 */
	public List<ReleasedFile> findFiles(String rootPath, List<ReleasedFile> releaseList) {
		File pathName = new File(rootPath);

		// �t�@�C�����̔z��𐶐�
		String[] fileNames = pathName.list();

		// ���ʃt�@�C�������݂��Ȃ��Ə����Ȃ�
		if (fileNames.length == 0) {
			return null;
		}

		// ���ʃt�@�C���Ƀt�H���_�敪�q��ǉ�����B
		String lastChar = rootPath.substring(rootPath.length() - 1, rootPath.length());
		if (lastChar.equals(File.separator) == false) {
			rootPath = rootPath + File.separator;
		}

		// ���ʃt�@�C���𐶐����t�@�C�����X�g�ɒǉ�
		for (int i = 0; i < fileNames.length; i++) {
			File tf = new File(rootPath, fileNames[i]);

			// �t�H���_�̏ꍇ���\�b�h�ċA�Ă�
			if (tf.isDirectory()) {

				// ���O�t�@�C���͌������Ȃ�
				if (!fileNames[i].equals("log")) {
					findFiles(tf.getPath(), releaseList);
				}

				// �t�@�C�����Ə������o���t�@�C�����X�g�ɒǉ�
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

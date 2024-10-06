package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.io.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * file chooser
 * 
 * @author AIS
 */
public class TFileChooser extends JFileChooser {

	/**
	 * �R���X�g���N�^
	 */
	public TFileChooser() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 * @param extentions �\���g���q
	 */
	public TFileChooser(String title, ExtensionType... extentions) {

		super();

		// �t�B���^�[�Z�b�g
		TFileFilter filter = new TFileFilter(title, extentions);
		resetChoosableFileFilters();
		setFileFilter(filter);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 * @param extentions �\���g���q
	 */
	public TFileChooser(String title, String... extentions) {

		super();

		// �t�B���^�[�Z�b�g
		TFileFilter filter = new TFileFilter(title, extentions);
		resetChoosableFileFilters();
		setFileFilter(filter);
	}

	/**
	 * �\������
	 * 
	 * @param parent
	 * @return ����
	 */
	public FileStatus show(Component parent) {
		int status = showOpenDialog(parent);
		return FileStatus.get(status);
	}

	/**
	 * �I�����ꂽ�t�@�C����Ԃ�
	 * 
	 * @return �I�����ꂽ�t�@�C��
	 */
	public TFile getSelectedTFile() {
		File file = getSelectedFile();
		TFile tFile = new TFile();
		tFile.setFile(file);
		tFile.setCompanyCode(TLoginInfo.getCompany().getCode());
		tFile.setFileName(getName(file));
		tFile.setInputDate(Util.getCurrentDate());
		tFile.setUserCode(TLoginInfo.getUser().getCode());
		return tFile;
	}

	/**
	 * �I�����ꂽ�t�@�C����Ԃ�
	 * 
	 * @return �I�����ꂽ�t�@�C��
	 */
	public TFile[] getSelectedTFiles() {
		File[] files = getSelectedFiles();
		if (files == null || files.length == 0) {
			return new TFile[0];
		}

		TFile[] tFiles = new TFile[files.length];
		for (int i = 0; i < files.length; i++) {
			TFile tFile = new TFile();
			tFile.setFile(files[i]);
			tFile.setCompanyCode(TLoginInfo.getCompany().getCode());
			tFile.setFileName(getName(files[i]));
			tFile.setInputDate(Util.getCurrentDate());
			tFile.setUserCode(TLoginInfo.getUser().getCode());
			tFiles[i] = tFile;
		}
		return tFiles;
	}

	/**
	 * ���݃f�B���N�g���ݒ�
	 * 
	 * @param path �f�B���N�g���p�X
	 */
	public void setCurrentDirectory(String path) {
		super.setCurrentDirectory(new File(path));
	}

	/**
	 * �I���t���p�X���擾
	 * 
	 * @return �t���p�X
	 */
	public String getFileFillPath() {
		return getCurrentDirectory().getPath() + File.separator + getSelectedTFile().getFileName();
	}

	/**
	 * �t�@�C���I����
	 */
	public enum FileStatus {

		/** �I�� */
		Selected(JFileChooser.APPROVE_OPTION),

		/** �L�����Z�� */
		Canceled(JFileChooser.CANCEL_OPTION);

		/**
		 *
		 */
		public int value;

		/**
		 * �R���X�g���N�^.
		 * 
		 * @param value
		 */
		private FileStatus(int value) {
			this.value = value;
		}

		/**
		 * @param status
		 * @return FileStatus
		 */
		public static FileStatus get(int status) {
			for (FileStatus em : values()) {
				if (em.value == status) {
					return em;
				}
			}
			return null;
		}
	}
}
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
	 * コンストラクタ
	 */
	public TFileChooser() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 * @param extentions 表示拡張子
	 */
	public TFileChooser(String title, ExtensionType... extentions) {

		super();

		// フィルターセット
		TFileFilter filter = new TFileFilter(title, extentions);
		resetChoosableFileFilters();
		setFileFilter(filter);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 * @param extentions 表示拡張子
	 */
	public TFileChooser(String title, String... extentions) {

		super();

		// フィルターセット
		TFileFilter filter = new TFileFilter(title, extentions);
		resetChoosableFileFilters();
		setFileFilter(filter);
	}

	/**
	 * 表示する
	 * 
	 * @param parent
	 * @return 結果
	 */
	public FileStatus show(Component parent) {
		int status = showOpenDialog(parent);
		return FileStatus.get(status);
	}

	/**
	 * 選択されたファイルを返す
	 * 
	 * @return 選択されたファイル
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
	 * 選択されたファイルを返す
	 * 
	 * @return 選択されたファイル
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
	 * 現在ディレクトリ設定
	 * 
	 * @param path ディレクトリパス
	 */
	public void setCurrentDirectory(String path) {
		super.setCurrentDirectory(new File(path));
	}

	/**
	 * 選択フルパスを取得
	 * 
	 * @return フルパス
	 */
	public String getFileFillPath() {
		return getCurrentDirectory().getPath() + File.separator + getSelectedTFile().getFileName();
	}

	/**
	 * ファイル選択状況
	 */
	public enum FileStatus {

		/** 選択 */
		Selected(JFileChooser.APPROVE_OPTION),

		/** キャンセル */
		Canceled(JFileChooser.CANCEL_OPTION);

		/**
		 *
		 */
		public int value;

		/**
		 * コンストラクタ.
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
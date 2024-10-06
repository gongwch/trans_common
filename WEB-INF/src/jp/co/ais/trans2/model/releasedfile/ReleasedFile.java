package jp.co.ais.trans2.model.releasedfile;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * MG0029ReleasedFile - リリースファイル一覧
 * 
 * @author AIS
 */
public class ReleasedFile extends TransferBase {

	/** フォルダ名 */
	protected String folder = null;

	/** ファイル名 */
	protected String file = null;

	/** 更新日付 */
	protected Date update = null;

	/** サイズ */
	protected long size;

	/**
	 * フォルダ名の取得
	 * 
	 * @return folder フォルダ名
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * フォルダ名の設定
	 * 
	 * @param folder フォルダ名
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}

	/**
	 * ファイル名の取得
	 * 
	 * @return file ファイル名
	 */
	public String getFile() {
		return file;
	}

	/**
	 * ファイル名の設定
	 * 
	 * @param file ファイル名
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * 更新日付の取得
	 * 
	 * @return update 更新日付
	 */
	public Date getUpdate() {
		return update;
	}

	/**
	 * 更新日付の設定
	 * 
	 * @param update 更新日付
	 */
	public void setUpdate(Date update) {
		this.update = update;
	}

	/**
	 * サイズの取得
	 * 
	 * @return size サイズ
	 */
	public long getSize() {
		return size;
	}

	/**
	 * サイズの設定
	 * 
	 * @param size サイズ
	 */
	public void setSize(long size) {
		this.size = size;
	}
}

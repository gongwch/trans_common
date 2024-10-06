package jp.co.ais.trans2.common.model.ui.slip;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 添付ボタン
 */
public class TAttachButton extends TImageButton {

	/** 親パネル */
	public TPanel parent = null;

	/** 親パネル */
	public TDialog dialog = null;

	/** コントローラ */
	public TAttachButtonCtrl controller = null;

	/**
	 * コンストラクター
	 * 
	 * @param parent 親パネル
	 */
	public TAttachButton(TPanel parent) {

		this.parent = parent;
		controller = createController();

		allocateComponents();
	}

	/**
	 * コンストラクター
	 * 
	 * @param dialog 親ダイアログ
	 */
	public TAttachButton(TDialog dialog) {

		this.dialog = dialog;
		controller = createController();

		allocateComponents();
	}

	/**
	 * コンポーネント初期化
	 */
	protected void allocateComponents() {
		setSize(16, 16);
		setFocusable(false);
	}

	/**
	 * コントローラの作成
	 * 
	 * @return コントローラ
	 */
	protected TAttachButtonCtrl createController() {
		return new TAttachButtonCtrl(this);
	}

	/**
	 * コントローラの取得
	 * 
	 * @return コントローラ
	 */
	public TAttachButtonCtrl getController() {
		return controller;
	}

	/**
	 * @param icon
	 */
	@Override
	public void setIconType(IconType icon) {
		super.setIconType(icon);
		this.repaint();
	}

	/**
	 * 添付ファイルの取得
	 * 
	 * @return files 添付ファイル
	 */
	public List<SWK_ATTACH> getAttachments() {
		return controller.getAttachments();
	}

	/**
	 * 添付ファイルの設定
	 * 
	 * @param attachments 添付ファイル情報
	 */
	public void setAttachments(List<SWK_ATTACH> attachments) {
		controller.setAttachments(attachments);
	}

	/**
	 * 添付ファイルの削除
	 * 
	 * @param list 添付ファイル情報
	 */
	public void removeAttachment(List<SWK_ATTACH> list) {
		controller.removeAttachment(list);
	}

	/**
	 * ファイル追加
	 * 
	 * @param filename キー
	 * @param file バイナリ
	 */
	public void addFile(String filename, File file) {
		controller.addFile(filename, file);
	}

	/**
	 * ファイル追加<br>
	 * File直接送信を使ってください
	 * 
	 * @param filename キー
	 * @param data バイナリ
	 */
	@Deprecated
	public void addFile(String filename, byte[] data) {
		controller.addFile(filename, data);
	}

	/**
	 * クリア処理
	 */
	public void clear() {
		controller.clear();
	}

}

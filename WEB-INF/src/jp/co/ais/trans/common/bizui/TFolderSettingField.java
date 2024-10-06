package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * フォルダ参照
 * 
 * @author nagahashi
 */
public class TFolderSettingField extends TButtonField {

	/** ファイルチューザー */
	protected JFileChooser openChooser;

	/** フォルダ選択後のイベントリスナ */
	protected FolderSelectListener folderSelectListener = null;

	/**
	 * /** コンストラクタ
	 */
	public TFolderSettingField() {
		this(new JFileChooser());
	}

	/**
	 * コンストラクタ(外部指定用)
	 * 
	 * @param chooser ファイルチューザー
	 */
	public TFolderSettingField(JFileChooser chooser) {
		super();

		this.openChooser = chooser;

		initComponents();
	}

	/**
	 * 構築
	 */
	protected void initComponents() {

		// チューザーの設定
		refleshChooser();

		// サイズ初期値
		this.setButtonSize(100);
		this.setFieldSize(400);

		this.setLangMessageID("C10298");

		// 制御
		this.setNoticeVisible(false);
		this.getField().setEditable(false);
		this.getField().setAllowedSpace(true);
		this.getField().setImeMode(true);

		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnActionPerformed();
			}
		});
	}

	/**
	 * Chooser設定
	 */
	protected void refleshChooser() {
		openChooser.setMultiSelectionEnabled(false);
		openChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		openChooser.setAcceptAllFileFilterUsed(false);
	}

	/**
	 * ボタン押下時の処理
	 */
	protected void btnActionPerformed() {
		if (!new File(this.getValue()).exists()) {
			refleshChooser();
		}

		// 前回指定したディレクトリを設定する
		openChooser.setCurrentDirectory(new File(this.getValue()));

		int status = openChooser.showOpenDialog(TGuiUtil.getParentFrameOrDialog(this));

		if (status == JFileChooser.APPROVE_OPTION) {
			String folderName = openChooser.getSelectedFile().getAbsolutePath();
			this.setValue(folderName);
		}

		if (folderSelectListener != null) {
			folderSelectListener.folderSelected(status);
		}

	}

	/**
	 * 選択ファイル取得
	 * 
	 * @return ファイル
	 */
	public File getSelectedFile() {
		if (isEmpty()) {
			return null;
		}

		return new File(getValue());
	}

	/**
	 * ファイルチューザーを設定
	 * 
	 * @param openChooser
	 */
	public void setOpenChooser(JFileChooser openChooser) {
		this.openChooser = openChooser;
	}

	/**
	 * ファイルチューザーを返す
	 * 
	 * @return openChooser
	 */
	public JFileChooser getOpenChooser() {
		return this.openChooser;
	}

	/**
	 * フォルダ選択後のイベントリスナのgetter
	 * 
	 * @return フォルダ選択後イベントリスナ
	 */
	public FolderSelectListener getFolderSelectListener() {
		return folderSelectListener;
	}

	/**
	 * フォルダ選択後のイベントリスナのsetter
	 * 
	 * @param folderSelectListener フォルダ選択後イベントリスナ
	 */
	public void setFolderSelectListener(FolderSelectListener folderSelectListener) {
		this.folderSelectListener = folderSelectListener;
	}

}

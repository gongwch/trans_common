package jp.co.ais.trans.common.bizui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ファイル参照
 */
public class TFileSettingField extends TFolderSettingField {

	/**
	 * @see jp.co.ais.trans.common.bizui.TFolderSettingField#initComponents()
	 */
	@Override
	protected void initComponents() {
		super.initComponents();

		setButtonSize(100);
		setLangMessageID("C01988");//ファイル

		TGuiUtil.setComponentWidth(this, 510);
	}

	/**
	 * Chooser設定
	 */
	@Override
	protected void refleshChooser() {
		openChooser.setMultiSelectionEnabled(false);
		openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
	}
}

package jp.co.ais.trans.common.bizui;

/**
 * TFolderSettingFieldのフォルダ選択時のリスナー
 * @author AIS
 *
 */
public class FolderSelectListener {

	/**
	 * フォルダ選択イベント後にCallされる。
	 * @param status JFileChooserのshowOpenDialogメソッドの戻り値。<br>
	 * 当値によりフォルダが選択されたかキャンセルされたかを判断する。<br>
	 * for override
	 */
	public void folderSelected(int status) {	}

}

package jp.co.ais.trans2.master.ui;

import java.awt.event.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * MG0029ReleasedFileMaster - リリースファイル一覧 - Main Panel Class
 * 
 * @author AIS
 */
public class MG0029ReleasedFilePanel extends TMainPanel {

	/** エクセルボタン */
	public TImageButton btnExcel;

	@Override
	public void initComponents() {

		btnExcel = new TImageButton(IconType.EXCEL);

	}

	@Override
	public void allocateComponents() {

		// エクセルボタン
		btnExcel.setSize(25, 130);
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setLocation(HEADER_LEFT_X, HEADER_Y);
		pnlHeader.add(btnExcel);

	}

	@Override
	// Tab順定義
	public void setTabIndex() {
		int i = 1;
		btnExcel.setTabControlNo(i++);
	}
}

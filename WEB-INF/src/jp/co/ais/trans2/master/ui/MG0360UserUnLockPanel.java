package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

//　作成　福井

/**
 * ログイン解除の指示画面
 * 
 * @author AIS
 */
public class MG0360UserUnLockPanel extends TMainPanel {

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 解除ボタン */
	public TButton btnDelete;

	/** 排他一覧 */
	public TCheckBox checkBox;

	/** ログイン解除マスタ一覧 */
	public TTable tbList;

	/** 一覧のカラム定義 */
	public enum SC {

		/** テーブルフラグ */
		bean,

		/** チェックボックス */
		CHECK_BOX,

		/** ユーザーコード */
		USR_ID,

		/** ユーザー名称 */
		USR_NAME,

		/** ログイン日時 */
		PCI_CHECK_IN_TIME,
	}

	@Override
	public void initComponents() {

		btnSearch = new TImageButton(IconType.SEARCH);
		btnDelete = new TImageButton();
		checkBox = new TCheckBox();

		tbList = new TTable();
		tbList.addColumn(SC.bean, "BEAN", -1);
		tbList.addColumn(SC.CHECK_BOX, "", 40, checkBox);
		tbList.addColumn(SC.USR_ID, getWord("C00589"), 100, SwingConstants.LEFT);
		tbList.addColumn(SC.USR_NAME, getWord("C00691"), 200, SwingConstants.LEFT);
		tbList.addColumn(SC.PCI_CHECK_IN_TIME, getWord("C03454"), 200, SwingConstants.CENTER);

	}

	@Override
	public void allocateComponents() {

		// 検索ボタン
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(HEADER_LEFT_X, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 解除ボタン
		btnDelete.setLangMessageID("C00056");
		btnDelete.setShortcutKey(KeyEvent.VK_F7);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(150, HEADER_Y);
		pnlHeader.add(btnDelete);

		// 一覧
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbList, gc);
	}

	@Override
	// Tab順定義
	public void setTabIndex() {
		int i = 0;
		btnSearch.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		tbList.setTabControlNo(i++);

	}
}
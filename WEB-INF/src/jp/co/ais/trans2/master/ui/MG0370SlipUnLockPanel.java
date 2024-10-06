package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans.common.gui.*;

/**
 * ユーザーマスタの指示画面レイアウト
 * 
 * @author AIS
 */
public class MG0370SlipUnLockPanel extends TMainPanel {

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 解除ボタン */
	public TImageButton btnRelieve;

	/** 排他一覧 */
	public TTable tbl;

	/** 排他一覧 */
	public TCheckBox checkBox;

	/**
	 * 一覧のカラム定義
	 */
	public enum SC {

		/** テーブルフラグ */
		bean,
		/** チェックボックス */
		CHECK_BOX,
		/** システム区分 */
		SWK_SYS_KBN,
		/** 伝票番号 */
		SWK_DEN_NO,
		/** 伝票種別 */
		SWK_DEN_SYU,
		/** 伝票日付 */
		SWK_DEN_DATE,
		/** 伝票摘要 */
		SWK_TEK,
		/** 排他ユーザ */
		USR_ID,
		/** 排他ユーザ名称 */
		USR_NAME,
		/** 排他実行日時 */
		UPD_DATE

	}

	@Override
	public void initComponents() {

		btnSearch = new TImageButton(IconType.SEARCH);
		btnRelieve = new TImageButton();
		checkBox = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.bean, "BEAN", -1);
		tbl.addColumn(SC.CHECK_BOX, "", 40, checkBox);
		tbl.addColumn(SC.SWK_SYS_KBN, "C00980", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.SWK_DEN_NO, "C00605", 130, SwingConstants.LEFT);
		tbl.addColumn(SC.SWK_DEN_SYU, "C00917", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.SWK_DEN_DATE, getWord("C00390") + getWord("C02906"), 80, SwingConstants.CENTER);
		tbl.addColumn(SC.SWK_TEK, "C00569", 200, SwingConstants.LEFT);
		tbl.addColumn(SC.USR_ID, "C03423", 130, SwingConstants.LEFT);
		tbl.addColumn(SC.USR_NAME, "C03424", 140, SwingConstants.LEFT);
		tbl.addColumn(SC.UPD_DATE, "C03425", -1, SwingConstants.CENTER);

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
		btnRelieve.setLangMessageID("C00056");
		btnRelieve.setShortcutKey(KeyEvent.VK_F7);
		btnRelieve.setSize(25, 110);
		btnRelieve.setLocation(150, HEADER_Y);
		pnlHeader.add(btnRelieve);

		// 一覧
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 0;
		btnSearch.setTabControlNo(i++);
		btnRelieve.setTabControlNo(i++);
		tbl.setTabControlNo(i++);

	}

}

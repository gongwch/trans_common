package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 添付検証画面
 */
public class MG0460AttachmentCheckPanel extends TMainPanel {

	// メインボタン部

	/** 検索ボタン */
	protected TImageButton btnSearch;

	/** エクセル */
	protected TImageButton btnExcel;

	/** 結果一覧 */
	protected TTable tblResult;

	/** 一覧列定義 */
	protected enum SC {
		/** 会社コード */
		KAI_CODE,
		/** データ区分(伝票・船費・OPなど) */
		DATA_TYPE,
		/** キー情報1 */
		KEY1,
		/** キー情報2 */
		KEY2,
		/** キー情報3 */
		KEY3,
		/** キー情報4 */
		KEY4,
		/** ファイル名 */
		FILE_NAME,
		/** サーバー上ファイル名 */
		SRV_FILE_NAME,
		/** メッセージ */
		MESSAGE
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {
		btnSearch = new TImageButton(IconType.SEARCH);
		btnExcel = new TImageButton(IconType.EXCEL);

		initTable();
	}

	/**
	 * 結果一覧初期化
	 */
	protected void initTable() {
		tblResult = new TTable();

		tblResult.addColumn(SC.KAI_CODE, "会社コード", 80, SwingConstants.LEFT);
		tblResult.addColumn(SC.DATA_TYPE, "データ種別", 80, SwingConstants.CENTER);
		tblResult.addColumn(SC.KEY1, "キー情報1", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.KEY2, "キー情報2", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.KEY3, "キー情報3", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.KEY4, "キー情報4", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.FILE_NAME, "ファイル名", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.SRV_FILE_NAME, "サーバーファイル名", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.MESSAGE, "メッセージ", 120, SwingConstants.LEFT);
	}

	@Override
	public void allocateComponents() {
		// メインボタン部初期化
		allocateMainButton();
		// ボディ部初期化
		allocateBody();
	}

	/**
	 * メインボタン部配置
	 */
	protected void allocateMainButton() {
		// 検索ボタン
		int x = HEADER_LEFT_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// エクセルボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

	}

	/**
	 * ボディ部配置
	 */
	protected void allocateBody() {
		pnlBody.setLayout(new GridBagLayout());

		// 一覧
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tblResult, gc);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		btnSearch.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		tblResult.setTabControlNo(i++);
	}

}

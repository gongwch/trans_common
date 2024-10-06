package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 添付一覧画面
 * 
 * @author AIS
 */
public class ManualAttachListDialog extends TDialog {

	/** 追加ボタン */
	public TButton btnAdd;

	/** 照会ボタン */
	public TButton btnDrillDown;

	/** 削除ボタン */
	public TButton btnDelete;

	/** 閉じるボタン */
	public TButton btnClose;

	/** スプレッド */
	public TTable tbl;

	/**
	 * コンストラクタ.
	 * 
	 * @param company 会社情報
	 * @param parent 親フレーム
	 */
	public ManualAttachListDialog(Company company, Frame parent) {
		super(company, parent, true);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param company 会社情報
	 * @param parent 親フレーム
	 */
	public ManualAttachListDialog(Company company, Dialog parent) {
		super(company, parent, true);
	}

	@Override
	public void initComponents() {
		btnAdd = new TImageButton(IconType.NEW);
		btnDrillDown = new TImageButton(IconType.PREVIEW);
		btnDelete = new TImageButton(IconType.DELETE);
		btnClose = new TButton();

		tbl = new TTable();
	}

	@Override
	public void allocateComponents() {

		setResizable(true);

		setPreferredSize(new Dimension(850, 665));

		setTitle(getWord("マニュアル一覧")); // マニュアル一覧

		int count = 4; // ボタン個数
		int x = getPreferredSize().width - 110 * count - HEADER_MARGIN_X * count - 95;
		int y = 10;

		// 追加ボタン
		btnAdd.setLangMessageID("C03263"); // 追加
		btnAdd.setShortcutKey(KeyEvent.VK_F2);
		btnAdd.setSize(25, 110);
		btnAdd.setLocation(x, y);
		pnlHeader.add(btnAdd);

		x += btnAdd.getWidth() + HEADER_MARGIN_X;

		// 照会ボタン
		btnDrillDown.setLangMessageID("C03661"); // 照会
		btnDrillDown.setShortcutKey(KeyEvent.VK_F3);
		btnDrillDown.setSize(25, 110);
		btnDrillDown.setLocation(x, y);
		pnlHeader.add(btnDrillDown);

		x += btnDrillDown.getWidth() + HEADER_MARGIN_X + 30;

		// 削除ボタン
		btnDelete.setLangMessageID("C01544"); // 削除
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, y);
		pnlHeader.add(btnDelete);

		x += btnDelete.getWidth() + HEADER_MARGIN_X + 30;

		// 閉じるボタン
		btnClose.setLangMessageID("C02374"); // 閉じる
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, y);
		pnlHeader.add(btnClose);

		// 一覧
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 0, 10);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlBody.add(tbl, gc);

		// スプレッドの初期化
		initSpread();

		pack();

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		btnAdd.setTabControlNo(i++);
		btnDrillDown.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
	}

	/**
	 * スプレッド初期化
	 */
	public void initSpread() {
		tbl.addColumn(MANUAL_SC.bean, "", -1); // Bean
		tbl.addColumn(MANUAL_SC.inputDate, getWord("日時"), 120); // 日時
		tbl.addColumn(MANUAL_SC.filename, getWord("C10018"), 650); // ファイル名
	}

	/**
	 * テーブル列名列挙体
	 */
	public enum MANUAL_SC {
		/** エンティティ */
		bean,

		/** 日時 */
		inputDate,

		/** ファイル名 */
		filename

	}
}

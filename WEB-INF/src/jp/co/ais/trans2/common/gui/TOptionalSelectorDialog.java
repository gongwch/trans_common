package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 任意選択(個別指定)コンポーネントのダイアログ
 * 
 * @author AIS
 */
public abstract class TOptionalSelectorDialog extends jp.co.ais.trans.common.gui.TDialog {

	/** 選択候補リスト */
	public TTable tblList;

	/** 選択されたリスト */
	public TTable tblSelectedList;

	/** 中央パネル */
	public TPanel pnlCenter;

	/** テーブル選択ボタン */
	public TButton btnTableSelect;

	/** テーブルキャンセルボタン */
	public TButton btnTableCancel;

	/** 検索パネル */
	public TPanel pnlSearch;

	/** 条件 コード */
	public TTextField txtSearchCode;
	/** 条件 略称 */
	public TTextField txtSearchName;

	/** 下部パネル */
	public TPanel pnlBottom;

	/** 確定ボタン */
	public TButton btnSettle;

	/** 戻るボタン */
	public TButton btnBack;

	/**
	 * コンストラクタ.
	 * 
	 * @param parent
	 * @param mordal
	 */
	public TOptionalSelectorDialog(Frame parent, boolean mordal) {

		super(parent, mordal);

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		initDialog();

	}

	/**
	 * コンポーネントの初期設定
	 */
	public void initComponents() {
		tblList = new TTable();
		pnlCenter = new TPanel();
		btnTableSelect = new TButton();
		btnTableCancel = new TButton();
		tblSelectedList = new TTable();
		pnlSearch = new TPanel();
		txtSearchCode = new TTextField();
		txtSearchName = new TTextField();
		pnlBottom = new TPanel();
		btnSettle = new TButton();
		btnBack = new TButton();
	}

	/**
	 * コンポーネントの配置を行います。
	 */
	public void allocateComponents() {

		setSize(840, 600);
		this.setResizable(true);

		setLayout(new GridBagLayout());

		// 選択候補一欄
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 0.45d;
		gc.weighty = 0.95d;
		gc.insets = new Insets(10, 10, 10, 0);
		gc.fill = GridBagConstraints.BOTH;
		add(tblList, gc);

		// 中央パネル
		pnlCenter.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.weightx = 0.10d;
		gc.weighty = 0.95d;
		gc.gridx = 1;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlCenter, gc);

		// 選択ボタン
		btnTableSelect.setPreferredSize(new Dimension(60, 20));
		btnTableSelect.setText(getWord("C10139")); // →
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 30, 0);
		pnlCenter.add(btnTableSelect, gc);

		// 選択取消ボタン
		btnTableCancel.setPreferredSize(new Dimension(60, 20));
		btnTableCancel.setText(getWord("C10140")); // ←
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.insets = new Insets(30, 0, 0, 0);
		pnlCenter.add(btnTableCancel, gc);

		// 選択された一覧
		gc = new GridBagConstraints();
		gc.weightx = 0.45d;
		gc.weighty = 0.95d;
		gc.gridx = 2;
		gc.insets = new Insets(10, 0, 10, 10);
		gc.fill = GridBagConstraints.BOTH;
		add(tblSelectedList, gc);

		// 検索パネル
		pnlSearch.setLayout(new GridBagLayout());
		pnlSearch.setPreferredSize(new Dimension(0, 20));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		gc.gridwidth = 3;
		add(pnlSearch, gc);

		// コード
		int codeLength = 100;
		txtSearchCode.setMinimumSize(new Dimension(codeLength, 20));
		txtSearchCode.setPreferredSize(new Dimension(codeLength, 20));
		txtSearchCode.setImeMode(false);
		int marginX = tblList.getRowColumnWidth() + 10;
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, marginX, 0, 0);
		
		pnlSearch.add(txtSearchCode, gc);

		// 略称
		int namesLength = 200;
		txtSearchName.setMinimumSize(new Dimension(namesLength, 20));
		txtSearchName.setPreferredSize(new Dimension(namesLength, 20));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);

		gc.weightx = 0.1d;
		gc.weighty = 0.1d;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlSearch.add(txtSearchName, gc);

		// 下部パネル
		pnlBottom.setLayout(new GridBagLayout());
		pnlBottom.setPreferredSize(new Dimension(0, 30));
		gc = new GridBagConstraints();
		gc.gridy = 2;
		gc.gridwidth = 3;
		gc.weighty = 0.05d;
		gc.fill = GridBagConstraints.BOTH;
		add(pnlBottom, gc);

		// 確定ボタン
		btnSettle.setPreferredSize(new Dimension(120, 25));
		btnSettle.setLangMessageID("C01019"); // 確定
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 10);
		pnlBottom.add(btnSettle, gc);

		// 戻るボタン
		btnBack.setPreferredSize(new Dimension(120, 25));
		btnBack.setLangMessageID("C01747"); // 戻る
		btnBack.setShortcutKey(KeyEvent.VK_F12);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 10, 0, 0);
		pnlBottom.add(btnBack, gc);

		tblList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblSelectedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		tblList.addSpreadSheetSelectChange(btnTableSelect);
		tblSelectedList.addSpreadSheetSelectChange(btnTableCancel);

	}

}

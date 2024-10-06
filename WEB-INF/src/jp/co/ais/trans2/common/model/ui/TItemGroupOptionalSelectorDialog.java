package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * 科目+補助科目+内訳科目の任意選択コンポーネントダイアログ
 * 
 * @author AIS
 */
public class TItemGroupOptionalSelectorDialog extends TOptionalSelectorDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = -3832189335879758074L;

	/** 科目選択 */
	public TItemLevelChooser ctrlItemLevelChooser;

	/** 科目選択パネル */
	public TPanel pnlChooser;

	/**
	 * テーブルのカラム
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** コード */
		code,
		/** 略称 */
		names,
		/** エンティティ */
		entity
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param parent
	 * @param mordal
	 */
	public TItemGroupOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	/**
	 * コンポーネントの初期設定
	 */
	@Override
	public void initComponents() {
		super.initComponents();
		ctrlItemLevelChooser = new TItemLevelChooser(SwingConstants.HORIZONTAL);
		pnlChooser = new TPanel();
	}

	/**
	 * コンポーネントの配置を行います。
	 */
	@Override
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

		// 科目の表示レベル
		TGuiUtil.setComponentSize(pnlChooser, 310, 50);
		pnlChooser.setLayout(null);

		ctrlItemLevelChooser.setLocation(0, 0);
		pnlChooser.add(ctrlItemLevelChooser);

		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 10, 0);
		gc.anchor = GridBagConstraints.WEST;
		pnlBottom.add(pnlChooser, gc);

		// 確定ボタン
		btnSettle.setMinimumSize(new Dimension(120, 25));
		btnSettle.setPreferredSize(new Dimension(120, 25));
		btnSettle.setLangMessageID("C01019"); // 確定
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 50, 0, 10);
		pnlBottom.add(btnSettle, gc);

		// 戻るボタン
		btnBack.setMinimumSize(new Dimension(120, 25));
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

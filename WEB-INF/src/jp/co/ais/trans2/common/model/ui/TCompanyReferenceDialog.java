package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;

/**
 * 会社用検索ダイアログの基底 検索名称を削除
 * 
 * @author AIS
 */
public class TCompanyReferenceDialog extends TReferenceDialog {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -2047862879654382708L;

	/**
	 * @param controller
	 */
	public TCompanyReferenceDialog(TReferenceController controller) {
		super(controller);		
	}

	/**
	 * 検索ダイアログの列番号定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** bean */
		bean,
		/** コード */
		code,
		/** 略称 */
		names
	}

	/**
	 * コンポーネント初期化
	 */
	@Override
	protected void initComponents() {
		tbl = new TTable();
		String caption = controller.getDialogCaption();
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.code, getWord(caption) + getWord("C00174"), 100); // XXコード
		tbl.addColumn(SC.names, getWord(caption) + getWord("C00548"), 160); // 略称
		code = new TTextField();
		names = new TTextField();
		namek = new TTextField();
		namek.setVisible(false);
		btnSearch = new TButton();
		btnSettle = new TButton();
		btnBack = new TButton();
	}
	
	/**
	 * コンポーネント配置
	 */
	@Override
	protected void allocateComponents() {

		setLayout(new GridBagLayout());
		setResizable(true);

		setPreferredSize(new Dimension(650, 520));

		setTitle(controller.getDialogTitle()); // XX一覧

		// 一覧
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 10, 0, 10);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		add(tbl, gc);

		// 検索条件指定フィールド
		TPanel pnlSearch = new TPanel();
		pnlSearch.setLayout(new GridBagLayout());
		pnlSearch.setMinimumSize(new Dimension(0, 20));
		pnlSearch.setPreferredSize(new Dimension(0, 20));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 1;
		add(pnlSearch, gc);

		// コード検索
		TTableInformation ti = tbl.getTableInformation();
		int codeLength = ti.getWidthList().get(SC.code.ordinal());
		code.setMinimumSize(new Dimension(codeLength, 20));
		code.setPreferredSize(new Dimension(codeLength, 20));
		code.setMaxLength(TransUtil.COMPANY_CODE_LENGTH);
		code.setImeMode(false);
		int marginX = tbl.getRowColumnWidth() + 10;
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, marginX, 0, 0);
		pnlSearch.add(code, gc);

		// 略称検索
		int namesLength = ti.getWidthList().get(SC.names.ordinal());
		names.setMinimumSize(new Dimension(namesLength, 20));
		names.setPreferredSize(new Dimension(namesLength, 20));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlSearch.add(names, gc);

		// ボタンフィールド
		TPanel pnlButton = new TPanel();
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMinimumSize(new Dimension(0, 40));
		pnlButton.setPreferredSize(new Dimension(0, 40));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy = 2;
		add(pnlButton, gc);

		// 検索ボタン
		btnSearch.setPreferredSize(new Dimension(120, 25));
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		pnlButton.add(btnSearch);
		gc = new GridBagConstraints();
		pnlButton.add(btnSearch, gc);

		// 確定ボタン
		btnSettle.setPreferredSize(new Dimension(120, 25));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		pnlButton.add(btnSettle, gc);
		tbl.addSpreadSheetSelectChange(btnSettle);

		// 戻るボタン
		btnBack.setPreferredSize(new Dimension(120, 25));
		btnBack.setLangMessageID("C01747");
		btnBack.setShortcutKey(KeyEvent.VK_F12);
		gc = new GridBagConstraints();
		pnlButton.add(btnBack, gc);

		pack();

	}

	/**
	 * Tab順セット
	 */
	@Override
	protected void setTabIndex() {
		int i = 0;
		code.setTabControlNo(i++);
		names.setTabControlNo(i++);
		if (ClientConfig.isFlagOn("trans.ref.table.focusable")) {
			tbl.setTabControlNo(i++);
			tbl.setEnterToButton(true);
		}
		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnBack.setTabControlNo(i++);
	}

}

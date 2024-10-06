package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 内訳科目マスタの指示画面
 */
public class MG0100DetailItemMasterPanel extends TMainPanel {

	/** 新規ボタン */
	public TImageButton btnNew;

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 編集ボタン */
	public TImageButton btnModify;

	/** 複写ボタン */
	public TImageButton btnCopy;

	/** 削除ボタン */
	public TImageButton btnDelete;

	/** エクセルボタン */
	public TImageButton btnExcel;

	/** 範囲検索コンポーネント */
	public TDetailItemRange ctrlSerch;

	/** 有効期限切れチェックボックス */
	public TCheckBox chkOutputTermEnd;

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 科目マスタ一覧 */
	public TTable tbl;

	/**
	 * 一覧のカラム定義
	 */
	public enum SC {
		/** 科目コード */
		kmkcode,
		/** 科目名称 */
		kmkname,
		/** 補助科目コード */
		hkmcode,
		/** 補助科目名称 */
		hkmname,
		/** 内訳科目コード */
		code,
		/** 内訳科目名称 */
		name,
		/** 内訳科目略称 */
		names,
		/** 内訳科目検索名称 */
		namek,
		/** 消費税コード */
		zeicode,
		/** 取引先入力フラグ */
		tricodeflg,
		/** 発生日入力フラグ */
		hasseiflag,
		/** 評価替対象フラグ */
		excflg,
		/** 入金伝票入力フラグ */
		glflg1,
		/** 出金伝票入力フラグ */
		glflg2,
		/** 振替伝票入力フラグ */
		glflg3,
		/** 経費精算伝票入力フラグ */
		apflg1,
		/** 債務計上伝票入力フラグ */
		apflg2,
		/** 債権計上伝票入力フラグ */
		arflg1,
		/** 債権消込伝票入力フラグ */
		arflg2,
		/** 資産計上伝票入力フラグ */
		faflg1,
		/** 支払依頼伝票入力フラグ */
		faflg2,
		/** 多通貨入力フラグ */
		mcrflg,
		/** 社員入力フラグ */
		empcodeflg,
		/** 管理１入力フラグ */
		knrflg1,
		/** 管理２入力フラグ */
		knrflg2,
		/** 管理３入力フラグ */
		knrflg3,
		/** 管理４入力フラグ */
		knrflg4,
		/** 管理５入力フラグ */
		knrflg5,
		/** 管理６入力フラグ */
		knrflg6,
		/** 非会計１入力フラグ */
		hmflg1,
		/** 非会計２入力フラグ */
		hmflg2,
		/** 非会計３入力フラグ */
		hmflg3,
		/** 売上課税入力フラグ */
		urizeiflg,
		/** 仕入課税入力フラグ */
		sirzeiflg,
		/** 発生日フラグ */
		occurDateflg,
		/** 開始年月日 */
		dateFrom,
		/** 終了年月日 */
		dateTo
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 */
	public MG0100DetailItemMasterPanel(Company company) {
		super(company);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		// 新規ボタン
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 検索ボタン
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 編集ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// 複写ボタン
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// エクセルボタン
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 上部
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 145));
		pnlBodyTop.setMinimumSize(new Dimension(0, 145));
		pnlBodyTop.setPreferredSize(new Dimension(0, 145));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 135);
		pnlBodyTop.add(pnlSearchCondition);

		// 有効期間切れ表示
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setLocation(360, 100);
		chkOutputTermEnd.setSize(140, 20);
		pnlSearchCondition.add(chkOutputTermEnd);

		// 範囲検索
		ctrlSerch.setLocation(20, 20);
		TGuiUtil.setComponentSize(ctrlSerch, 500, 400);
		pnlSearchCondition.add(ctrlSerch);

		// 下部
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// 一覧
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl, gc);
	}

	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		chkOutputTermEnd = new TCheckBox();
		ctrlSerch = new TDetailItemRange();
		tbl = new TTable();
		tbl.addColumn(SC.kmkcode, "C00572", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.kmkname, "C00700", 200, SwingConstants.LEFT);
		tbl.addColumn(SC.hkmcode, "C00602", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.hkmname, "C00701", 200, SwingConstants.LEFT);
		tbl.addColumn(SC.code, "C00603", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.name, "C00702", 200, SwingConstants.LEFT);
		tbl.addColumn(SC.names, "C01594", 150, SwingConstants.LEFT);
		tbl.addColumn(SC.namek, "C01593", 200, SwingConstants.LEFT);
		tbl.addColumn(SC.zeicode, "C00573", 150, SwingConstants.LEFT);
		tbl.addColumn(SC.glflg1, "C02067", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.glflg2, "C02068", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.glflg3, "C02321", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.apflg1, "C01049", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.apflg2, "C01083", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.arflg1, "C02071", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.arflg2, "C02072", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.faflg1, "C02073", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.faflg2, "C02074", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.tricodeflg, "C01134", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.empcodeflg, "C01120", 150, SwingConstants.CENTER);
		if (company.getAccountConfig().getManagement1Name() != null) {
			tbl.addColumn(SC.knrflg1, company.getAccountConfig().getManagement1Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg1, "C01026", 100, SwingConstants.CENTER);
		}

		if (company.getAccountConfig().getManagement2Name() != null) {
			tbl.addColumn(SC.knrflg2, company.getAccountConfig().getManagement2Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg2, "C01028", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getManagement3Name() != null) {
			tbl.addColumn(SC.knrflg3, company.getAccountConfig().getManagement3Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg3, "C01030", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getManagement4Name() != null) {
			tbl.addColumn(SC.knrflg4, company.getAccountConfig().getManagement4Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg4, "C01032", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getManagement5Name() != null) {
			tbl.addColumn(SC.knrflg5, company.getAccountConfig().getManagement5Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg5, "C01034", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getManagement6Name() != null) {
			tbl.addColumn(SC.knrflg6, company.getAccountConfig().getManagement6Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.knrflg6, "C01036", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getNonAccounting1Name() != null) {
			tbl.addColumn(SC.hmflg1, company.getAccountConfig().getNonAccounting1Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.hmflg1, "C01288", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getNonAccounting2Name() != null) {
			tbl.addColumn(SC.hmflg2, company.getAccountConfig().getNonAccounting2Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.hmflg2, "C01289", 100, SwingConstants.CENTER);
		}
		if (company.getAccountConfig().getNonAccounting3Name() != null) {
			tbl.addColumn(SC.hmflg3, company.getAccountConfig().getNonAccounting3Name() + getWord("C02386"), 100,
				SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.hmflg3, "C01290", 100, SwingConstants.CENTER);
		}
		tbl.addColumn(SC.urizeiflg, "C01282", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.sirzeiflg, "C01088", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.mcrflg, "C01223", 150, SwingConstants.CENTER);
		tbl.addColumn(SC.excflg, "C01301", 150, SwingConstants.CENTER);

		if (ClientConfig.isFlagOn("trans.MG0080.use.occurdate")) {
			tbl.addColumn(SC.occurDateflg, "C11619", 100, SwingConstants.CENTER);
		} else {
			tbl.addColumn(SC.occurDateflg, "C11619", -1, SwingConstants.CENTER);
		}

		tbl.addColumn(SC.dateFrom, "C00055", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.dateTo, "C00261", 100, SwingConstants.CENTER);
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlSerch.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
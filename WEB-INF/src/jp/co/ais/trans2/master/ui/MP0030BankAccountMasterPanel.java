package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 銀行口座マスタ
 *
 * @author AIS
 */
public class MP0030BankAccountMasterPanel extends TMainPanel {

	/** 複写ボタン */
	public TImageButton btnCopy;

	/** 削除ボタン */
	public TImageButton btnDelete;

	/** 編集ボタン */
	public TImageButton btnModify;

	/** リスト出力ボタン */
	public TImageButton btnListOutput;

	/** 新規ボタン */
	public TImageButton btnNew;

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** 銀行口座範囲 */
	public TBankAccountReferenceRange ctrlRange;

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 一覧 */
	public TTable tbl;

	/** 有効期間切れを表示するか */
	public TCheckBox chkOutputTermEnd;

	/** 一覧のカラム定義 */
	public enum SC {
		/** 銀行口座コード */
		code,
		/** 銀行口座名称 */
		name,
		/** 通貨コード */
		curCode,
		/** 銀行コード */
		bankCode,
		/** 銀行名称 */
		bankName,
		/** 支店コード */
		stnCode,
		/** 支店名称 */
		stnName,
		/** 振込依頼人コード */
		iraiCode,
		/** 振込依頼人名（ｶﾅ） */
		iraiName,
		/** 振込依頼人名（漢字） */
		iraiName_j,
		/** 振込依頼人名（英字） */
		iraiName_e,
		/** 預金種目 */
		yokinKbn,
		/** 口座番号 */
		kouzaNo,
		/** 社員FB区分 */
		empFB,
		/** 社外FB区分 */
		syagaiFB,
		/** 計上部門コード */
		depCode,
		/** 計上部門略称 */
		depNames,
		/** 科目コード */
		itemCode,
		/** 科目略称 */
		itemNames,
		/** 補助科目コード */
		subItemCode,
		/** 補助科目略称 */
		subItemNames,
		/** 内訳科目コード */
		detailItemCode,
		/** 内訳科目略称 */
		detailItemNames,
		/** 開始日 */
		dateFrom,
		/** 終了日 */
		dateTo

	}

	/**
	 * コンストラクタ.
	 */
	public MP0030BankAccountMasterPanel() {
		super();
	}

	/**
	 * パネルでボタンの状態を設定する
	 *
	 * @param bool true或false
	 */
	public void setBtnLock(Boolean bool) {
		this.btnModify.setEnabled(bool);
		this.btnCopy.setEnabled(bool);
		this.btnDelete.setEnabled(bool);
	}

	/**
	 * 画面コンポーネントの構築
	 */
	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		ctrlRange = new TBankAccountReferenceRange(true);
		chkOutputTermEnd = new TCheckBox();

		pnlSearchCondition = new TPanel();
		tbl = new TTable();
		tbl.addColumn(SC.code, getWord("C01879"), 100);
		tbl.addColumn(SC.name, getWord("C02145"), 200);
		tbl.addColumn(SC.curCode, getWord("C00665"), 80, SwingConstants.CENTER);
		tbl.addColumn(SC.bankCode, getWord("C00779"), 100);
		tbl.addColumn(SC.bankName, getWord("C00781"), 200);
		tbl.addColumn(SC.stnCode, getWord("C02055"), 100);
		tbl.addColumn(SC.stnName, getWord("C02060"), 200);
		tbl.addColumn(SC.iraiCode, getWord("C00858"), 110);
		tbl.addColumn(SC.iraiName, getWord("C00859"), 200);
		tbl.addColumn(SC.iraiName_j, getWord("C00860"), 200);
		tbl.addColumn(SC.iraiName_e, getWord("C00861"), 200);
		tbl.addColumn(SC.yokinKbn, getWord("C01326"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.kouzaNo, getWord("C00794"), 100);
		tbl.addColumn(SC.empFB, getWord("C01117"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.syagaiFB, getWord("C01122"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.depCode, getWord("C00571"), 100);
		tbl.addColumn(SC.depNames, getWord("C00724"), 200);
		tbl.addColumn(SC.itemCode, getWord("C00572"), 100);
		tbl.addColumn(SC.itemNames, getWord("C00730"), 200);
		tbl.addColumn(SC.subItemCode, getWord("C00602"), 100);
		tbl.addColumn(SC.subItemNames, getWord("C00739"), 200);
		tbl.addColumn(SC.detailItemCode, getWord("C00603"), 100);
		tbl.addColumn(SC.detailItemNames, getWord("C01594"), 200);
		tbl.addColumn(SC.dateFrom, getWord("C00055"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.dateTo, getWord("C00261"), 100, SwingConstants.CENTER);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		int x = HEADER_LEFT_X;

		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		x = x + btnNew.getWidth() + HEADER_MARGIN_X;

		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;

		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		tbl.addSpreadSheetSelectChange(btnModify);
		pnlHeader.add(btnModify);

		x = x + btnModify.getWidth() + HEADER_MARGIN_X;

		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;

		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;

		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setSize(25, 130);
		btnListOutput.setLocation(x, HEADER_Y);
		pnlHeader.add(btnListOutput);

		// 上部
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// 範囲検索
		ctrlRange.setLocation(20, 20);
		pnlSearchCondition.add(ctrlRange);

		// 有効期間切れ表示
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(450, 40);
		pnlSearchCondition.add(chkOutputTermEnd);

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

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnListOutput.setTabControlNo(i++);
	}

}

package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 伝票種別マスタ 指示画面
 */
public class MG0330SlipTypeMasterPanel extends TMainPanel {

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

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 伝票種別範囲検索 */
	public TSlipTypeReferenceRange TSlipTypeReferenceRange;

	/** 一覧 */
	public TTable tbl;

	/**
	 * 一覧のカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** 伝票種別コード */
		denSyuCode,
		/** システム区分 */
		sysKbn,
		/** 伝票種別名称 */
		denSyuName,
		/** 伝票種別略称 */
		denSyuNames,
		/** 帳票タイトル */
		reportTitle,
		/** データ区分 */
		dataKbn,
		/** 他システム区分 */
		anotherSysKbn,
		/** 伝票番号採番フラグ */
		denNoFlg,
		/** 入力単位 */
		acceptUnit,
		/** 消費税区分 */
		TaxCulKbn,
		/** 仕訳インターフェース区分 */
		swkIfKbn,
		/** 振戻伝票種別コード */
		revDenSyuCode,
		/** 振戻伝票種別名称 */
		revDenSyuName,
		/** インボイス制度チェック */
		invoiceCheck
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
		TSlipTypeReferenceRange = new TSlipTypeReferenceRange();

		tbl = new TTable();
		tbl.addColumn(SC.denSyuCode, getWord("C00837"), 100);// 伝票種別コード
		tbl.addColumn(SC.sysKbn, getWord("C00980"), 100);// システム区分
		tbl.addColumn(SC.denSyuName, getWord("C00838"), 150);// 伝票種別名称
		tbl.addColumn(SC.denSyuNames, getWord("C00839"), 150);// 伝票種別略称
		tbl.addColumn(SC.reportTitle, getWord("C02757"), 200);// 帳票タイトル
		tbl.addColumn(SC.dataKbn, getWord("C00567"), 150);// データ区分
		tbl.addColumn(SC.anotherSysKbn, getWord("C01221"), 150);// 他システム区分
		tbl.addColumn(SC.denNoFlg, getWord("C11168"), 150);// 伝票番号採番フラグ
		tbl.addColumn(SC.acceptUnit, getWord("C11169"), 200);// 入力単位
		tbl.addColumn(SC.TaxCulKbn, getWord("C01642"), 100);// 消費税区分
		tbl.addColumn(SC.swkIfKbn, getWord("C00299"), 200);// 仕訳インターフェース区分
		tbl.addColumn(SC.revDenSyuCode, getWord("C11280"), 130);// 振戻伝票種別コード
		tbl.addColumn(SC.revDenSyuName, getWord("C11281"), 130);// 振戻伝票種別名称

		int invoiceWith = MG0330SlipTypeMasterPanelCtrl.isInvoice ? 130 : -1;
		tbl.addColumn(SC.invoiceCheck, getWord("C12199"), invoiceWith);// インボイス制度チェック

	}

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
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C01060")));
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(360, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// 取引先検索範囲
		TSlipTypeReferenceRange.setLocation(20, 20);
		pnlSearchCondition.add(TSlipTypeReferenceRange);

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
	public void setTabIndex() {
		int i = 1;
		TSlipTypeReferenceRange.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}

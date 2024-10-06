package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.payment.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 支払方法マスタの指示画面
 * 
 * @author AIS
 */
public class MP0040PaymentMethodMasterPanel extends TMainPanel {

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

	/** 上部 */
	public TPanel pnlBodyTop;

	/** 検索条件パネル */
	public TPanel pnlSearchCondition;

	/** 支払対象区分パネル */
	public TPanel pnlPaymentDivision;

	/** 社員支払 */
	public TCheckBox chkEmployeePayment;

	/** 社外支払 */
	public TCheckBox chkExternalPayment;

	/** 支払方法検索範囲 */
	public TPaymentMethodReferenceRange ctrlPayRefRan;

	/** 有効期限切れチェックボックス */
	public TCheckBox chkOutputTermEnd;

	/** 下部 */
	public TPanel pnlBodyBottom;

	/** 支払方法一覧 */
	public TTable tbList;

	/** 一覧のカラム定義 */
	public enum SC {

		/** 支払方法コード */
		HOH_HOH_CODE,

		/** 支払方法名称 */
		HOH_HOH_NAME,

		/** 支払方法検索名称 */
		HOH_HOH_NAME_K,

		/** 科目 */
		HOH_KMK_CODE,

		/** 補助 */
		HOH_HKM_CODE,

		/** 内訳 */
		HOH_UKM_CODE,

		/** 計上部門コード */
		HOH_DEP_CODE,

		/** 支払対象区分 */
		HOH_SHI_KBN,

		/** 支払内部コード */
		HOH_NAI_CODE,

		/** 開始年月日 */
		STR_DATE,

		/** 終了年月日 */
		END_DATE,

		/** Entity */
		ENTITY
	}

	@SuppressWarnings("static-access")
	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlBodyTop = new TPanel();
		pnlSearchCondition = new TPanel();
		pnlPaymentDivision = new TPanel();
		chkEmployeePayment = new TCheckBox();
		chkExternalPayment = new TCheckBox();
		ctrlPayRefRan = new TPaymentMethodReferenceRange();
		chkOutputTermEnd = new TCheckBox();
		pnlBodyBottom = new TPanel();

		tbList = new TTable();
		tbList.addColumn(SC.HOH_HOH_CODE, getWord("C00864"), 150, SwingConstants.LEFT);
		tbList.addColumn(SC.HOH_HOH_NAME, getWord("C00865"), 240, SwingConstants.LEFT);
		tbList.addColumn(SC.HOH_HOH_NAME_K, getWord("C00866"), 240, SwingConstants.LEFT);
		tbList.addColumn(SC.HOH_KMK_CODE, getWord("C00077"), 120, SwingConstants.LEFT);
		tbList.addColumn(SC.HOH_HKM_CODE, getWord("C00488"), 120, SwingConstants.LEFT);
		tbList.addColumn(SC.HOH_UKM_CODE, getWord("C00025"), 120, SwingConstants.LEFT);
		tbList.addColumn(SC.HOH_DEP_CODE, getWord("C00571"), 120, SwingConstants.LEFT);
		tbList.addColumn(SC.HOH_SHI_KBN, getWord("C01096"), 120, SwingConstants.CENTER);
		tbList.addColumn(SC.HOH_NAI_CODE, getWord("C01097"), 120, SwingConstants.CENTER);
		tbList.addColumn(SC.STR_DATE, getWord("COP706"), 120, SwingConstants.CENTER);
		tbList.addColumn(SC.END_DATE, getWord("COP707"), 120, SwingConstants.CENTER);
		tbList.addColumn(SC.ENTITY, "", -1, false);
	}

	@Override
	public void allocateComponents() {

		// 新規ボタン
		int x = HEADER_LEFT_X;
		btnNew.setSize(25, 110);
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// 検索ボタン
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setSize(25, 110);
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// 編集ボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setSize(25, 110);
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbList.addSpreadSheetSelectChange(btnModify);

		// 複写ボタン
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setSize(25, 110);
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// 削除ボタン
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setSize(25, 110);
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// エクセルボタン
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setSize(25, 130);
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 上部
		pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 180));
		pnlBodyTop.setMinimumSize(new Dimension(0, 180));
		pnlBodyTop.setPreferredSize(new Dimension(0, 180));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 検索条件パネル
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(480, 130);
		pnlBodyTop.add(pnlSearchCondition);

		// 支払対象区分パネル
		pnlPaymentDivision.setLayout(null);
		pnlPaymentDivision.setLangMessageID("C01096");
		pnlPaymentDivision.setLocation(20, 20);
		pnlPaymentDivision.setSize(280, 50);
		pnlSearchCondition.add(pnlPaymentDivision);

		// 社員支払
		chkEmployeePayment.setLangMessageID("C01119");
		chkEmployeePayment.setSize(100, 20);
		chkEmployeePayment.setLocation(20, 20);
		chkEmployeePayment.setSelected(true);
		chkEmployeePayment.setIndex(0);
		pnlPaymentDivision.add(chkEmployeePayment);

		// 社外支払
		chkExternalPayment.setLangMessageID("C01124");
		chkExternalPayment.setSize(100, 20);
		chkExternalPayment.setLocation(150, 20);
		chkExternalPayment.setSelected(true);
		chkExternalPayment.setIndex(1);
		pnlPaymentDivision.add(chkExternalPayment);

		// 支払方法（範囲）（開始、終了）
		ctrlPayRefRan.setLocation(20, 75);
		pnlSearchCondition.add(ctrlPayRefRan);

		// 有効期間切れ表示
		chkOutputTermEnd.setLangMessageID("C11089"); // 有効期間切れ表示
		chkOutputTermEnd.setLocation(340, 95);
		chkOutputTermEnd.setSize(140, 20);
		pnlSearchCondition.add(chkOutputTermEnd);

		// 下部
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(15, 30, 10, 30);
		pnlBody.add(tbList, gc);
	}

	@Override
	// Tab順定義
	public void setTabIndex() {
		int i = 0;
		chkEmployeePayment.setTabControlNo(i++);
		chkExternalPayment.setTabControlNo(i++);
		ctrlPayRefRan.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		tbList.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
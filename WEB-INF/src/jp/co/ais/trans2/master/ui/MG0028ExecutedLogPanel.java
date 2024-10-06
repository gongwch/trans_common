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
 * 実行ログ参照の画面レイアウト
 * 
 * @author AIS
 */
public class MG0028ExecutedLogPanel extends TMainPanel {

	/** 検索ボタン */
	public TImageButton btnSearch;

	/** エクセルボタン */
	public TImageButton btnExcel;

	/** 上部 */
	public TPanel pnlBodyTop;

	/** 対象年月日（開始） */
	public TLabelPopupCalendar ctrlDateFrom;

	/** 対象年月日（終了） */
	public TLabelPopupCalendar ctrlDateTo;

	/** ユーザ検索パネル */
	public TPanel pnlUser;

	/** ユーザー範囲検索 */
	public TUserReferenceRange ctrlUserRange;

	/** ユーザ開始ラベル */
	public TLabel lblStart;

	/** ユーザ終了ラベル */
	public TLabel lblEnd;

	/** プログラム検索パネル */
	public TPanel pnlProgram;

	/** プログラム範囲検索 */
	public TProgramReferenceRange ctrlProgramRange;

	/** プログラム開始ラベル */
	public TLabel lblProStart;

	/** プログラム終了ラベル */
	public TLabel lblProEnd;

	/** ログイン参照チェックボックス */
	public TCheckBox checkProgram;

	/** 並び順コンボボックス */
	public TLabelComboBox ctrlSort;

	/** 下部 */
	public TPanel pnlBodyBottom;

	/** ロックアウト管理マスタ一覧 */
	public TTable tbl;

	/** 一覧のカラム定義 */
	public enum SC {
		/** Entity **/
		ENTITY

		/** 実行日時 */
		, EXE_DATE

		/** 実行ユーザーコード */
		, USR_CODE

		/** 実行ユーザー名称 */
		, USR_NAME

		/** IPアドレス */
		, IP_ADDRESS

		/** プログラムコード */
		, PR_CODE

		/** プログラム名称 */
		, PR_NAME

		/** ステータス */
		, STATE

	}

	@Override
	public void initComponents() {
		btnSearch = new TImageButton(IconType.SEARCH);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlBodyTop = new TPanel();
		ctrlDateFrom = new TLabelPopupCalendar();
		ctrlDateTo = new TLabelPopupCalendar();
		pnlUser = new TPanel();
		ctrlUserRange = new TUserReferenceRange();
		lblStart = new TLabel();
		lblEnd = new TLabel();
		pnlProgram = new TPanel();
		ctrlProgramRange = new TProgramReferenceRange();
		lblProStart = new TLabel();
		lblProEnd = new TLabel();
		checkProgram = new TCheckBox();
		ctrlSort = new TLabelComboBox();
		pnlBodyBottom = new TPanel();

		tbl = new TTable();
		tbl.addColumn(SC.ENTITY, "", -1);
		tbl.addColumn(SC.EXE_DATE, getWord("C00218") + getWord("C02906"), 140, SwingConstants.CENTER);
		tbl.addColumn(SC.USR_CODE, getWord("C00218") + getWord("C00589"), 140);
		tbl.addColumn(SC.USR_NAME, getWord("C00218") + getWord("C00691"), 140);
		tbl.addColumn(SC.IP_ADDRESS, getWord("C02907"), 120);
		tbl.addColumn(SC.PR_CODE, getWord("C00818"), 140);
		tbl.addColumn(SC.PR_NAME, getWord("C00819"), 140);
		tbl.addColumn(SC.STATE, getWord("C02908"), 120);

	}

	@Override
	public void allocateComponents() {

		// 検索ボタン
		int x = HEADER_LEFT_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F1);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// エクセルボタン
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnExcel.setSize(25, 130);
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F9);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// 上部
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 150));
		pnlBodyTop.setMinimumSize(new Dimension(0, 150));
		pnlBodyTop.setPreferredSize(new Dimension(0, 150));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// 対象年月日
		int Y = 20;
		ctrlDateFrom.setLabelText(getWord("C00347") + getWord("C02909"));// 対象＋年月日
		ctrlDateFrom.getCalendar().setCalendarType(TPopupCalendar.TYPE_YMD);
		ctrlDateFrom.setLabelSize(100);
		ctrlDateFrom.setLocation(25, Y);
		pnlBodyTop.add(ctrlDateFrom);

		// ～
		ctrlDateTo.setLangMessageID("C01333");
		ctrlDateTo.getCalendar().setCalendarType(TPopupCalendar.TYPE_YMD);
		ctrlDateTo.setLabelSize(100);
		ctrlDateTo.setLocation(130, Y);
		pnlBodyTop.add(ctrlDateTo);

		// ユーザー検索パネル
		pnlUser.setLayout(null);
		pnlUser.setSize(400, 75);
		pnlUser.setLangMessageID(getWord("C00347") + getWord("C00528"));// 対象+ユーザー
		pnlUser.setLocation(30, Y + ctrlDateFrom.getHeight() + 5);
		pnlBodyTop.add(pnlUser);

		// ユーザ範囲検索
		ctrlUserRange.ctrlUserReferenceFrom.btn.setLangMessageID("C00528");// ユーザー
		ctrlUserRange.ctrlUserReferenceTo.btn.setLangMessageID("C00528");
		ctrlUserRange.setLocation(70, Y);

		pnlUser.add(ctrlUserRange);

		// ユーザ検索開始ラベル
		lblStart.setLangMessageID("C01012");// 開始
		lblStart.setSize(60, 20);
		lblStart.setLocation(20, Y);
		pnlUser.add(lblStart);

		// ユーザ検索終了ラベル
		lblEnd.setLangMessageID("C01143");// 終了
		lblEnd.setSize(60, 20);
		lblEnd.setLocation(20, Y + 20);
		pnlUser.add(lblEnd);

		// プログラム検索パネル
		pnlProgram.setLayout(null);
		pnlProgram.setSize(410, 100);
		pnlProgram.setLangMessageID(getWord("C00347") + getWord("C00477"));// 対象＋プログラム
		pnlProgram.setLocation(pnlUser.getX() + pnlUser.getWidth() + 10, pnlUser.getY());
		pnlBodyTop.add(pnlProgram);

		// ログイン・ログアウトを対象チェックボックス
		checkProgram.setLangMessageID("C02910");
		checkProgram.setSize(200, 20);
		checkProgram.setLocation(60, Y);
		pnlProgram.add(checkProgram);

		// プログラム範囲検索
		ctrlProgramRange.ctrlProgramReferenceFrom.btn.setLangMessageID("C00477");// プログラム
		ctrlProgramRange.ctrlProgramReferenceTo.btn.setLangMessageID("C00477");
		ctrlProgramRange.setLocation(checkProgram.getX(), checkProgram.getY() + 25);
		pnlProgram.add(ctrlProgramRange);

		// プログラム開始ラベル
		lblProStart.setLangMessageID("C01012");
		lblProStart.setSize(60, 20);
		lblProStart.setLocation(10, ctrlProgramRange.getY());
		pnlProgram.add(lblProStart);

		// プログラム終了ラベル
		lblProEnd.setLangMessageID("C01143");// 終了
		lblProEnd.setSize(60, 20);
		lblProEnd.setLocation(10, lblProStart.getY() + 20);
		pnlProgram.add(lblProEnd);

		// 並び順コンボボックス
		ctrlSort.setLangMessageID("C02839");// 並び順
		ctrlSort.setLabelSize(100);
		ctrlSort.setComboSize(110);
		ctrlSort.setSize(230, 20);
		ctrlSort.setLocation(40, pnlUser.getY() + 80);
		initComboBox(ctrlSort.getComboBox());
		pnlBodyTop.add(ctrlSort);

		// 下部
		pnlBodyBottom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyBottom, gc);

		// 一覧
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyBottom.add(tbl, gc);

	}

	/**
	 * コンボボックス中身定義
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void initComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(1, getWord("C00218") + getWord("C02906"));// 実行＋日時
		comboBox.addTextValueItem(2, getWord("C00528"));
		comboBox.addTextValueItem(3, getWord("C00477"));

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlDateFrom.setTabControlNo(i++);
		ctrlDateTo.setTabControlNo(i++);
		ctrlUserRange.setTabControlNo(i++);
		checkProgram.setTabControlNo(i++);
		ctrlProgramRange.setTabControlNo(i++);
		ctrlSort.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);

	}
}

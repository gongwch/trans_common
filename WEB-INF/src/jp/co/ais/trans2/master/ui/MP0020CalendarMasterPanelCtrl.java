package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.calendar.*;

/**
 * カレンダーマスタ
 */
public class MP0020CalendarMasterPanelCtrl extends TController {

	/** 該当月の日数 */
	protected int intLastDay = 0;

	/** 何曜日（対象年月の初日の曜日） */
	protected int intDayOfWeek = 0;

	/** 確定ボタン押下可かどうかチェック区分 */
	protected boolean settleFlg = true;

	/** 指示画面 */
	protected MP0020CalendarMasterPanel mainView = null;

	/** 日付ﾌｫｰﾏｯﾄ */
	public static final DateFormat ddFormat = new SimpleDateFormat("dd");

	@Override
	public void start() {

		try {
			// 指示画面生成
			createMainView();

			// 指示画面を初期化する
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {

		// 該当日の初期化
		Date dtDate = Util.getCurrentYMDDate();

		// 年の設定
		int year = DateUtil.getYear(dtDate);
		// 月の設定
		int month = DateUtil.getMonth(dtDate);
		// 日付の設定
		mainView.txtObjectYears.setValue(dtDate);

		// データの取得
		fillData(year, month);
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MP0020CalendarMasterPanel(this);

		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義
	 */
	protected void addMainViewEvent() {

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			// @Override
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

		});
		// 「エクセル取込」ボタン押下
		mainView.btnImportExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnImportExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// 「エクセル出力」ボタン押下
		mainView.btnExportExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExportExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [確定]ボタン押下
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// バウンドプロパティーの変更時
		mainView.txtObjectYears.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent ev) {

				if (ev.getPropertyName().equals("value")) {
					mainView.btnSettle.setEnabled(false);
				}
			}
		});

		// 年月テキストのロスとフォーカス移動時
		mainView.txtObjectYears.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				// チェックを行った
				if (settleFlg) {
					// 年月が変更の場合
					if (mainView.txtObjectYears.isValueChanged()) {
						mainView.btnSettle.setEnabled(false);
						settleFlg = false;
					} else {
						mainView.btnSettle.setEnabled(true);
					}
				}
				return true;
			}
		});
	}

	/**
	 * パネル取得
	 * 
	 * @return カレンダーマスタパネル
	 */
	@Override
	public TPanelBusiness getPanel() {
		// パネルを返す
		return mainView;
	}

	/**
	 * @return boolean
	 */
	public boolean disposeDialog() {
		boolean isSettle = false;
		// 確定ボタン押下 チェックOKなら閉じる
		if (this.showConfirmMessage(mainView.getParentFrame(), "Q00005", "")) {
			isSettle = true;
		}
		return isSettle;
	}

	/**
	 * 指示画面[検索]ボタン押下
	 */
	protected void btnSearch_Click() {
		try {
			// 確定ボタンを押下不可能にする
			mainView.btnSettle.setEnabled(false);

			// 該当日の初期化
			Date dtDate = DateUtil.toYMDDate(mainView.txtObjectYears.getValue());

			// 年の設定
			int year = DateUtil.getYear(dtDate);
			// 月の設定
			int month = DateUtil.getMonth(dtDate);
			// データの取得
			fillData(year, month);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 検索条件に該当する会社のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する会社のリスト
	 * @throws Exception
	 */
	protected List<CalendarEntity> getCalendar(CalendarSearchCondition condition) throws Exception {

		List<CalendarEntity> list = (List<CalendarEntity>) request(getModelClass(), "getCalendar", condition);
		return list;
	}

	/**
	 * 「エクセル取込」ボタン押下時の処理
	 */
	protected void btnImportExcel_Click() {
		try {
			// ファイル選択ダイアログを開く
			TFileChooser fc = new TFileChooser();

			File dir = getPreviousFolder(".chooser");

			// 前回のフォルダを復元
			if (dir != null) {
				fc.setCurrentDirectory(dir);
			}

			// テキストファイル(CSV形式)フィルター
			TFileFilter filter = new TFileFilter(getWord("C00085") + getWord("C00500"), ExtensionType.XLS,
				ExtensionType.XLSX);

			fc.setFileFilter(filter);
			if (TFileChooser.FileStatus.Selected != fc.show(mainView)) {
				return;
			}

			// 同一ファイルが過去に取り込まれている場合警告メッセージを表示する。
			TFile file = fc.getSelectedTFile();
			file.setKey("MP0020");

			// 最後の取込フォルダ保存
			saveFolder(fc.getCurrentDirectory(), ".chooser");

			// エクセル取込を行う
			request(getModelClass(), "importExcel", file.getFile());

			// 完了メッセージ
			showMessage(mainView, "I00013");

			// 画面を再検索する
			btnSearch_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 前回保存したフォルダ情報を返す。
	 * 
	 * @param type 種類
	 * @return 前回保存したフォルダ情報
	 */
	protected File getPreviousFolder(String type) {

		String path = SystemUtil.getAisSettingDir();
		File dir = (File) FileUtil.getObject(path + File.separator + getFolderKeyName() + type);

		return dir;
	}

	/**
	 * 指定のフォルダ情報を保存する
	 * 
	 * @param type 種類
	 * @param dir フォルダ情報
	 */
	protected void saveFolder(File dir, String type) {
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(dir, path + File.separator + getFolderKeyName() + type);
	}

	/**
	 * @return FolderKeyNameを戻します。
	 */
	protected String getFolderKeyName() {
		return "MP0020";
	}

	/**
	 * 「エクセル出力」ボタン押下時の処理
	 */
	protected void btnExportExcel_Click() {

		try {
			CalendarSearchCondition condition = getCalendarSearchCondition();
			Date calDate = DateUtil.toYMDDate(mainView.txtObjectYears.getValue());

			condition.setCompanyCode(getCompanyCode());
			condition.setDateFrom(DateUtil.getFirstDate(calDate));
			condition.setDateTo(DateUtil.getLastDate(calDate));

			// エクセル出力を行う
			byte[] data = (byte[]) request(getModelClass(), "exportExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00085") + getWord("C00500") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * モデルインターフェースを返す
	 * 
	 * @return モデルインターフェース
	 */
	protected Class getModelClass() {
		return CalendarManager.class;
	}

	/**
	 * 検索条件の取得
	 * 
	 * @return 検索条件
	 */
	protected CalendarSearchCondition getCalendarSearchCondition() {
		return new CalendarSearchCondition();
	}

	/**
	 * 表示データの取得
	 * 
	 * @param dteFrom 開始日付, dteTo 終了日付
	 * @param dteTo
	 */
	public void reflesh(Date dteFrom, Date dteTo) {
		try {
			// 会社コードの取得
			String kaiCode = getCompanyCode();
			// カレンダー情報を取得
			CalendarSearchCondition condition = getCalendarSearchCondition();
			condition.setCompanyCode(kaiCode);
			condition.setDateFrom(dteFrom);
			condition.setDateTo(dteTo);

			List<CalendarEntity> list = getCalendar(condition);

			// 結果の設定
			setData(list);

			// 確定ボタン押下可能
			mainView.btnSettle.setEnabled(true);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(e);
		}
	}

	/**
	 * 新規処理
	 * 
	 * @param list Calendarリスト
	 * @param condition
	 */
	public void insert(List<CalendarEntity> list, CalendarSearchCondition condition) {
		try {
			// 登録
			request(getModelClass(), "entry", list, condition);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(e);
		}
	}

	/**
	 * 画面項目のクリア
	 */
	public void clearMainView() {
		for (int i = 0; i < 37; i++) {
			// 日付ラベルの表示
			mainView.lbl[i].setVisible(true);
			// 日付ラベルのテキストクリア
			mainView.lbl[i].setText("");
			// 社員支払対象日ﾁｪｯｸﾎﾞｯｸｽの表示
			mainView.chkEmp[i].setVisible(true);
			// 銀行休業日ﾁｪｯｸﾎﾞｯｸｽの表示
			mainView.chkBak[i].setVisible(true);
			// 臨時支払対象日ﾁｪｯｸﾎﾞｯｸｽの表示
			mainView.chkTep[i].setVisible(true);
			// 土日を判定
			boolean isHoliday = i == 0 || i == 6 || i == 7 || i == 13 || i == 14 || i == 20 || i == 21 || i == 27
				|| i == 28 || i == 34 || i == 35 ? true : false;

			// 社員支払対象日の全クリア
			mainView.chkEmp[i].setSelected(false);
			// 銀行休業日のセット
			mainView.chkBak[i].setSelected(isHoliday);
			// 臨時支払対象日の全セット
			mainView.chkTep[i].setSelected(!isHoliday);
		}
		// 画面項目の非表示（前月日分）
		for (int i = 0; i < intDayOfWeek; i++) {
			// 日付ラベルの非表示
			mainView.lbl[i].setVisible(false);
			// 社員支払対象日ﾁｪｯｸﾎﾞｯｸｽの非表示
			mainView.chkEmp[i].setVisible(false);
			// 銀行休業日ﾁｪｯｸﾎﾞｯｸｽの非表示
			mainView.chkBak[i].setVisible(false);
			// 臨時支払対象日ﾁｪｯｸﾎﾞｯｸｽの非表示
			mainView.chkTep[i].setVisible(false);
		}
		// 画面項目の非表示（来月日分）
		for (int i = intLastDay + intDayOfWeek; i < 37; i++) {
			// 日付ラベルの非表示
			mainView.lbl[i].setVisible(false);
			// 社員支払対象日ﾁｪｯｸﾎﾞｯｸｽの非表示
			mainView.chkEmp[i].setVisible(false);
			// 銀行休業日ﾁｪｯｸﾎﾞｯｸｽの非表示
			mainView.chkBak[i].setVisible(false);
			// 臨時支払対象日ﾁｪｯｸﾎﾞｯｸｽの非表示
			mainView.chkTep[i].setVisible(false);
		}

	}

	/**
	 * 確定ボタンの処理
	 */
	protected void btnSettle_Click() {
		try {
			// 確認メッセージ表示
			if (!disposeDialog()) {
				return;
			}
			// 会社コードの取得
			String kaiCode = getCompanyCode();
			// 年月の取得
			Date dtDate = DateUtil.toYMDDate(mainView.txtObjectYears.getValue());

			// 日付の初期化
			Date calDate;
			// 社員支払対象日の初期化
			String calSha = "";
			// 銀行休業日の初期化
			String calBank = "";
			// 臨時支払対象日の初期化
			String calRinji = "";

			// 開始日付の取得
			Date dteFrom = DateUtil.getFirstDate(dtDate);
			// 終了日付の取得
			Date dteTo = DateUtil.getLastDate(dtDate);

			CalendarSearchCondition condition = new CalendarSearchCondition();
			condition.setCompanyCode(kaiCode);
			condition.setDateFrom(dteFrom);
			condition.setDateTo(dteTo);

			List<CalendarEntity> list = new ArrayList<CalendarEntity>();
			for (int i = 0; i < intLastDay; i++) {
				// 日付の設定
				calDate = DateUtil.addDay(dteFrom, i);
				// 社員支払対象日の設定
				calSha = String.valueOf(BooleanUtil.toInt(mainView.chkEmp[i + intDayOfWeek].isSelected()));
				// 銀行休業日の設定
				calBank = String.valueOf(BooleanUtil.toInt(mainView.chkBak[i + intDayOfWeek].isSelected()));
				// 臨時支払対象日の設定
				calRinji = String.valueOf(BooleanUtil.toInt(mainView.chkTep[i + intDayOfWeek].isSelected()));

				// Calendarリスト作成
				CalendarEntity rs = new CalendarEntity();
				rs.setCompanyCode(kaiCode);
				rs.setCalDate(calDate);
				rs.setCalSha(calSha);
				rs.setCalBank(calBank);
				rs.setCalRinji(calRinji);
				list.add(rs);

			}
			// 登録処理
			insert(list, condition);
			
			// 表示更新
			switchModify();
			
			// 完了メッセージ表示
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * データの取得
	 * 
	 * @param year
	 * @param month
	 */
	public void fillData(int year, int month) {
		try {
			// 日付の初期化
			Calendar cal = Calendar.getInstance();
			// 年の設定
			cal.set(Calendar.YEAR, year);
			// 月の設定
			cal.set(Calendar.MONTH, month - 1);
			// 日の設定
			cal.set(Calendar.DATE, 1);
			// 日数の設定
			intLastDay = cal.getActualMaximum(Calendar.DATE);
			// 曜日の設定
			intDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
			// 日曜日の設定
			if (intDayOfWeek < 0) {
				intDayOfWeek = 0;
			}

			// 画面項目のクリア
			clearMainView();

			// 日の表示
			for (int i = intDayOfWeek; i < intLastDay + intDayOfWeek; i++) {
				// 日付ラベルの表示
				mainView.lbl[i].setText(String.valueOf(i + 1 - intDayOfWeek));
			}
			Date calDate = cal.getTime();
			// 開始日付の取得
			Date dateFrom = DateUtil.getFirstDate(calDate);
			// 終了日付の取得
			Date dateTo = DateUtil.getLastDate(calDate);

			// 表示データの取得
			reflesh(dateFrom, dateTo);

			// 確定ボタンの有効化
			settleFlg = true;
			mainView.btnSettle.setEnabled(true);

		} catch (Exception ex) {
			// 正常に処理されませんでした
			errorHandler(ex);
		}
	}

	/**
	 * データの設定
	 * 
	 * @param lisRt
	 */
	public void setData(List<CalendarEntity> lisRt) {

		boolean isExists = false;

		// 画面項目の設定
		for (int i = 0; i < lisRt.size(); i++) {
			if (!isExists) isExists = true;
			
			// 該当データの取得
			CalendarEntity lisRow = lisRt.get(i);

			String strDate = ddFormat.format(lisRow.getCalDate());

			// 日の取得
			int intCel = Integer.parseInt(strDate);
			// 社員支払対象日ﾁｪｯｸﾎﾞｯｸｽの設定
			mainView.chkEmp[intCel + intDayOfWeek - 1].setSelected("1".equals(lisRow.getCalSha()));
			// 銀行休業日ﾁｪｯｸﾎﾞｯｸｽの設定
			mainView.chkBak[intCel + intDayOfWeek - 1].setSelected("1".equals(lisRow.getCalBank()));
			// 臨時支払対象日ﾁｪｯｸﾎﾞｯｸｽの設定
			mainView.chkTep[intCel + intDayOfWeek - 1].setSelected("1".equals(lisRow.getCalRinji()));

		}
		
		// 表示更新
		if (isExists) {
			switchModify();
		} else {
			switchNew();
		}
			
	}

	/**
	 * 新規モード切替
	 */
	public void switchNew() {
		mainView.lblState.setBackground(new Color(0, 240, 255));
		mainView.lblState.setText(getShortWord("C00303"));
	}

	/**
	 * 編集モード切替
	 */
	public void switchModify() {
		mainView.lblState.setBackground(new Color(255, 255, 50));
		mainView.lblState.setText(getShortWord("C00169"));
	}
}

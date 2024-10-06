package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.executedlog.*;

/**
 * 実行ログ参照のコントローラ
 * 
 * @author AIS
 */
public class MG0028ExecutedLogPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0028ExecutedLogPanel mainView = null;

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

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0028ExecutedLogPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {
		mainView.ctrlDateFrom.setValue(Util.getCurrentDate());
		mainView.ctrlDateFrom.setAllowableBlank(false);
		mainView.ctrlDateTo.setValue(Util.getCurrentDate());
		mainView.ctrlDateTo.setAllowableBlank(false);
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [エクセル]ボタン押下
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 指示画面[検索]ボタン押下
	 */
	protected void btnSearch_Click() {
		try {

			// 開始年月日<=終了年月日にしてください。
			if (Util.isSmallerThen(DateUtil.toYMDString(mainView.ctrlDateFrom.getValue()),
				DateUtil.toYMDString(mainView.ctrlDateTo.getValue())) == false) {
				showMessage(mainView, "I00067");
				mainView.ctrlDateFrom.requestFocus();
				return;
			}

			// 検索条件
			ExecutedLogSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック(ユーザー)
			if (Util.isSmallerThen(condition.getUserFrom(), condition.getUserTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.requestFocus();
				return;
			}

			// 開始 <= 終了チェック(プログラム)
			if (Util.isSmallerThen(condition.getProgramFrom(), condition.getProgramTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.requestFocus();
				return;
			}

			// 一覧をクリア
			mainView.tbl.removeRow();

			List<ExecutedLog> list = getList(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");// 対象となるデータが見つかりません。
				return;
			}

			for (ExecutedLog log : list) {
				if (condition.isIsLogin() == true) {
					if (ExecutedLogger.LOGIN.equals(log.getProCode())) {
						log.setProName(getWord("C03187")); // ログイン

					} else if (ExecutedLogger.LOGOUT.equals(log.getProCode())) {
						log.setProName(getWord("C03188")); // ログアウト
					}
				}

				// 検索したデータを一覧に表示する
				mainView.tbl.addRow(getRowData(log));
			}

			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面[エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// 開始年月日<=終了年月日にしてください。
			if (Util.isSmallerThen(DateUtil.toYMDString(mainView.ctrlDateFrom.getValue()),
				DateUtil.toYMDString(mainView.ctrlDateTo.getValue())) == false) {
				showMessage(mainView, "I00067");
				mainView.ctrlDateFrom.requestFocus();
				return;
			}

			// データ抽出
			ExecutedLogSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック(ユーザー)
			if (Util.isSmallerThen(condition.getUserFrom(), condition.getUserTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.requestFocus();
				return;
			}

			// 開始 <= 終了チェック(プログラム)
			if (Util.isSmallerThen(condition.getProgramFrom(), condition.getProgramTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.requestFocus();
				return;
			}

			byte[] data = (byte[]) request(getManagerClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView.getParentFrame(), "I00022");// 対象となるデータが見つかりません。
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02911") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * Managerクラスを返す
	 * 
	 * @return ExecutedLogManager
	 */
	protected Class getManagerClass() {
		return ExecutedLogManager.class;
	}

	/**
	 * 検索条件取得
	 * 
	 * @return 検索条件
	 */
	protected ExecutedLogSearchCondition getSearchCondition() {

		ExecutedLogSearchCondition condition = new ExecutedLogSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setDateFrom(mainView.ctrlDateFrom.getValue());
		condition.setDateTo(mainView.ctrlDateTo.getValue());
		condition.setUserFrom(mainView.ctrlUserRange.getCodeFrom());
		condition.setUserTo(mainView.ctrlUserRange.getCodeTo());
		condition.setProgramFrom(mainView.ctrlProgramRange.getCodeFrom());
		condition.setProgramTo(mainView.ctrlProgramRange.getCodeTo());
		if (mainView.checkProgram.isSelected()) {

			condition.setIsLogin(true);
		} else {
			condition.setIsLogin(false);
		}

		condition.setSort((Integer) mainView.ctrlSort.getSelectedItemValue());

		return condition;
	}

	/**
	 * 実行ログ参照データを返す
	 * 
	 * @param condition
	 * @return List
	 * @throws Exception
	 */
	protected List<ExecutedLog> getList(ExecutedLogSearchCondition condition) throws Exception {
		List<ExecutedLog> list = (List<ExecutedLog>) request(getManagerClass(), "get", condition);
		return list;
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(ExecutedLog bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean);
		list.add(DateUtil.toYMDHMSString(bean.getExcDate()));
		list.add(bean.getExcCode());
		list.add(bean.getExcNames());
		list.add(bean.getIpAddress());
		list.add(bean.getProCode());
		list.add(bean.getProName());
		list.add(bean.getStste());
		return list;
	}


}

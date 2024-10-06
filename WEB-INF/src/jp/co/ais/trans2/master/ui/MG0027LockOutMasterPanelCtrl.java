package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.MG0027LockOutMasterPanel.SC;
import jp.co.ais.trans2.model.lockout.*;

/**
 * ロックアウト管理マスタのコントローラ
 * 
 * @author AIS
 */
public class MG0027LockOutMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0027LockOutMasterPanel mainView = null;

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
	 * 指示画面を初期化する
	 * 
	 * @throws Exception
	 */
	protected void initMainView() throws Exception {
		mainView.tbl.removeRow();
		List<LockOut> list = getList();

		// ロックアウト管理データを一覧に表示する
		for (LockOut lock : list) {
			mainView.tbl.addRow(getData(lock));
		}

	}

	/**
	 * 指示画面のファクトリ。新規に画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0027LockOutMasterPanel();
		addMainViewEvent();
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

		// [消去]ボタン押下
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 指示画面[検索]ボタン押下
	 */
	protected void btnSearch_Click() {

		try {
			// 一覧に最新データ反映
			initMainView();
			if (mainView.tbl.getRowCount() == 0) {
				showMessage(mainView.getParentFrame(), "I00022");// 対象となるデータが見つかりません。
				return;
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [消去]ボタン押下
	 */
	protected void btnDelete_Click() {

		try {

			// 確認メッセージ表示
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 選択された行データ取得・消去する
			List<LockOut> lockoutList = getSelected();
			if (lockoutList.isEmpty()) {
				// 一覧からデータを選択してください。
				showMessage("I00043");
				return;
			}
			request(getModelClass(), "delete", lockoutList);

			// 処理後の一覧作成
			initMainView();

			// 完了メッセージ
			showMessage(mainView.getParentFrame(), "I00013");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return LockOutManager
	 */
	protected Class getModelClass() {
		return LockOutManager.class;
	}

	/**
	 * 一覧で選択したデータを取得
	 * 
	 * @return bean
	 */
	protected List<LockOut> getSelected() {
		List<LockOut> dataList = new ArrayList<LockOut>();
		for (int row = 0; row < mainView.tbl.getRowCount(); row++) { // テーブルの行数分for

			boolean isChecked = (Boolean) mainView.tbl.getRowValueAt(row, SC.UNLOCK);
			if (isChecked) {
				LockOut data = (LockOut) mainView.tbl.getRowValueAt(row, SC.ENTITY); // 以下、trueのdataをセットする
				dataList.add(data);
			}

		}
		return dataList;

	}

	/**
	 * ロックアウト管理データを返す
	 * 
	 * @return List
	 * @throws Exception
	 */
	protected List<LockOut> getList() throws Exception {
		List<LockOut> list = (List<LockOut>) request(getModelClass(), "get");
		return list;
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getData(LockOut bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean);
		list.add(false);
		list.add(bean.getUserCode());
		list.add(bean.getUserNames());
		list.add(DateUtil.toYMDHMSString(bean.getLogFailure()));
		return list;
	}

}

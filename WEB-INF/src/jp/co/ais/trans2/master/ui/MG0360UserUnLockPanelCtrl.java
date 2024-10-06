package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.userunlock.*;

/**
 * MG0360UserUnLockPaneMaster - ログイン解除マスタ - Main Controller
 * 
 * @author AIS
 */
public class MG0360UserUnLockPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0360UserUnLockPanel mainView = null;

	/** 起動時 */
	protected boolean isStart = false;

	@Override
	public void start() {
		try {

			isStart = true;

			// 指示画面生成
			createMainView();

			// 指示画面初期化
			initMainView();

			// 画面を開いた際に検索
			btnSearch_Click();

			isStart = false;

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面生成, イベント定義
	 */
	protected void createMainView() {
		mainView = new MG0360UserUnLockPanel();
		addMainViewEvent();
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln Boolean
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * 指示画面のイベント定義
	 */
	protected void addMainViewEvent() {
		// [検索]ボタン
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [解除]ボタン
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {
		// メインボタンを押下不可能にする
		setMainButtonEnabled(false);

	}

	/**
	 * 指示画面[検索]ボタン押下
	 */
	protected void btnSearch_Click() {
		try {

			// 一覧をクリア
			mainView.tbList.removeRow();

			// メインボタン制御
			setMainButtonEnabled(false);

			// データ取得
			List<UserUnLock> list = getList();

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				if (!isStart) {
					showMessage(mainView.getParentFrame(), "I00022");
				}
				return;
			}

			// 検索したデータを一覧に表示する
			for (UserUnLock bean : list) {
				mainView.tbList.addRow(getRowData(bean));
			}

			// メインボタンを押下可能にし、1行目を選択する
			setMainButtonEnabled(true);
			mainView.tbList.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [解除]ボタン押下
	 */
	protected void btnDelete_Click() {
		try {

			// 削除対象のデータ取得
			List<UserUnLock> list = getSelected();

			if (list == null) {
				return;
			}

			// 実行確認ﾒｯｾｰｼﾞ
			if (!showConfirmMessage(mainView, "Q00016")) {
				return;
			}
			// 削除実行
			delete(list);

			// 処理完了のメッセージ
			showMessage(mainView, "I00008");

			// 再検索
			btnSearch_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(UserUnLock bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean);
		list.add(false);// チェックボックス
		list.add(bean.getUSR_ID()); // ユーザーコード
		list.add(bean.getUSR_NAME()); // ユーザー名称
		list.add(DateUtil.toYMDHMSString(bean.getPCI_CHECK_IN_TIME())); // ログイン日時

		return list;
	}

	/**
	 * 一覧で選択したデータを取得
	 * 
	 * @return bean
	 */
	protected List<UserUnLock> getSelected() {

		List<UserUnLock> list = new ArrayList<UserUnLock>();

		for (int row = 0; row < mainView.tbList.getRowCount(); row++) {// スプレッドの行数文回転
			boolean isChecked = ((Boolean) mainView.tbList.getRowValueAt(row, MG0360UserUnLockPanel.SC.CHECK_BOX));// 選択されている対象
			if (isChecked) {
				UserUnLock data = (UserUnLock) mainView.tbList.getRowValueAt(row, MG0360UserUnLockPanel.SC.bean);
				list.add(data);

			}
		}

		if (list.isEmpty()) {
			showMessage(mainView, "W00195"); // 一覧に1行以上選択してください。
			return null;
		}

		return list;
	}

	/**
	 * 検索条件に該当するデータを返す
	 * 
	 * @return List
	 * @throws Exception
	 */
	protected List<UserUnLock> getList() throws Exception {

		List<UserUnLock> list = (List<UserUnLock>) request(getModelClass(), "get");
		return list;
	}

	/**
	 * 指定行データを削除する
	 * 
	 * @param list
	 * @throws Exception
	 */
	protected void delete(List<UserUnLock> list) throws Exception {
		request(getModelClass(), "delete", list);
	}

	/**
	 * モデルを返す
	 * 
	 * @return モデル
	 */
	protected Class getModelClass() {
		return UserUnLockManager.class;
	}
}

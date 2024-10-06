package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票排他解除マスタのコントローラ
 * 
 * @author AIS
 */
public class MG0370SlipUnLockPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0370SlipUnLockPanel mainView = null;

	/** 起動時 */
	protected boolean isStart = false;

	/**
	 * @see jp.co.ais.trans2.common.client.TController#start()
	 */
	@Override
	public void start() {
		try {

			isStart = true;

			// 指示画面生成
			createMainView();

			// 指示画面を初期化する
			initMainView();

			// 画面を開いた際に検索
			btnSearch_Click();

			isStart = false;

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * パネル取得
	 * 
	 * @return パネル
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;

	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0370SlipUnLockPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面ボタン押下時のイベント
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

		// [解除]ボタン押下
		mainView.btnRelieve.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnRelieve_Click();
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

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);
			// 検索処理
			List<SlipUnLock> list = get();

			// 検索条件に該当するリストが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				if (!isStart) {
					showMessage(mainView.getParentFrame(), "I00022");
				}
				return;
			}

			// 排他情報を一覧に表示する
			for (SlipUnLock bean : list) {
				mainView.tbl.addRow(getRowData(bean));
			}

			// メインボタンを押下可能にし、1行目を選択する
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[更新]ボタン押下
	 */
	protected void btnRelieve_Click() {

		try {

			// 編集対象の排他データを取得する
			List<SlipUnLock> list = getSelected();

			// 戻り値を判定
			if (list == null) {
				return;
			}

			// 更新処理
			update(list);

			// 処理完了のメッセージ
			showMessage(mainView, "I00008");

			// 再検索
			btnSearch_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 排他情報を一覧に表示する形式に変換し返す
	 * 
	 * @param bean 排他情報
	 * @return 一覧に表示する形式に変換された排他情報
	 */
	protected List<Object> getRowData(SlipUnLock bean) {
		List<Object> list = new ArrayList<Object>();

		list.add(bean);
		list.add(false);// チェックボックス
		list.add(bean.getSYS_NAME()); // システム区分名称
		list.add(bean.getSWK_DEN_NO()); // 伝票番号
		list.add(bean.getDEN_SYU_NAME()); // 伝票種別名称
		list.add(DateUtil.toYMDString(bean.getSWK_DEN_DATE())); // 伝票日時
		list.add(bean.getSWK_TEK()); // 伝票摘要
		list.add(bean.getUSR_ID()); // 排他ユーザID
		list.add(bean.getUSR_NAME()); // 排他ユーザ名称
		list.add(DateUtil.toYMDHMSString(bean.getUPD_DATE())); // 排他実行日時

		return list;
	}

	/**
	 * 更新条件に該当する排他データのリストを返す
	 * 
	 * @param list 更新条件
	 * @throws Exception
	 */
	protected void update(List<SlipUnLock> list) throws Exception {

		request(getModelClass(), "update", list);
	}

	/**
	 * 検索条件に該当する排他のリストを返す
	 * 
	 * @return 検索条件に該当する排他のリスト
	 * @throws Exception
	 */
	protected List<SlipUnLock> get() throws Exception {

		List<SlipUnLock> list = (List<SlipUnLock>) request(getModelClass(), "get");
		return list;
	}

	/**
	 * 一覧で選択されている排他を返す
	 * 
	 * @return 一覧で選択されている排他
	 */
	protected List<SlipUnLock> getSelected() {

		List<SlipUnLock> list = new ArrayList<SlipUnLock>();

		for (int row = 0; row < mainView.tbl.getRowCount(); row++) {// スプレッドの行数文回転
			boolean isChecked = ((Boolean) mainView.tbl.getRowValueAt(row, MG0370SlipUnLockPanel.SC.CHECK_BOX));// 選択されている対象
			if (isChecked) {
				SlipUnLock bean = (SlipUnLock) mainView.tbl.getRowValueAt(row, MG0370SlipUnLockPanel.SC.bean);
				list.add(bean);

			}
		}

		if (list.isEmpty()) {
			showMessage(mainView, "W00195"); // 一覧に1行以上選択してください。
			return null;
		}

		return list;
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param mainEnabled enabled
	 */
	protected void setMainButtonEnabled(boolean mainEnabled) {
		mainView.btnRelieve.setEnabled(mainEnabled);
	}

	/**
	 * インタフェースクラスを返す
	 * 
	 * @return SlipUnLockManager
	 */
	protected Class getModelClass() {
		return SlipUnLockManager.class;
	}

}

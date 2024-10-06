package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.exclusive.*;

/**
 * 排他明細一覧のコントローラ
 * 
 * @author AIS
 */
public class MG0470ExclusiveDetailPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0470ExclusiveDetailPanel mainView = null;

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
		mainView = new MG0470ExclusiveDetailPanel();
		addMainViewEvent();

		btnSearch_Click();
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

		// [エクセル]ボタン押下
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
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

			// 一覧をクリアする
			mainView.tbl.removeRow();

			// 検索処理
			List<ExclusiveDetail> list = get();

			// 排他明細が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				if (!isStart) {
					showMessage(mainView.getParentFrame(), "I00022");
				}
				return;
			}

			// 排他情報を一覧に表示する
			for (ExclusiveDetail bean : list) {
				mainView.tbl.addRow(getRowData(bean));
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {
		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// 検索処理
			List<ExclusiveDetail> list = get();

			// 排他明細が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// エクセルデータの取得
			byte[] data = (byte[]) request(getModelClass(), "getExcel");

			// エクセルタイトルセット
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11951") + ".xls");

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
	protected List<Object> getRowData(ExclusiveDetail bean) {
		List<Object> list = new ArrayList<Object>();

		list.add(bean.getKAI_CODE()); // 会社コード
		list.add(bean.getSHORI_KBN()); // 処理区分
		list.add(bean.getHAITA_KEY()); // 排他キー
		list.add(bean.getGYO_NO()); // 行番号
		list.add(DateUtil.toYMDHMString(bean.getINP_DATE())); // 処理日時
		list.add(bean.getPRG_ID()); // プログラムID
		list.add(bean.getPRG_NAME()); // プログラム名称
		list.add(bean.getUSR_ID()); // 排他ユーザID
		list.add(bean.getUSR_NAME()); // 排他ユーザ名称

		return list;
	}

	/**
	 * 検索条件に該当する排他のリストを返す
	 * 
	 * @return 検索条件に該当する排他のリスト
	 * @throws Exception
	 */
	protected List<ExclusiveDetail> get() throws Exception {
		List<ExclusiveDetail> list = (List<ExclusiveDetail>) request(getModelClass(), "get");
		return list;
	}

	/**
	 * インタフェースクラスを返す
	 * 
	 * @return ExclusiveDetailManager
	 */
	protected Class getModelClass() {
		return ExclusiveDetailManager.class;
	}

}

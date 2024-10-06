package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * 内航外航区分マスタコントロール
 * 
 * @author AIS
 */
public class MG0501VesselCOMasterPanelCtrl extends TController {

	/**
	 * 指示画面
	 */
	protected MG0501VesselCOMasterPanel mainView = null;

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
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0501VesselCOMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [確定]ボタン押下
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
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

		// [←(内航船追加)]ボタン押下
		mainView.btnCoastalAddVessel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCoastalAddVessel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [→(内航船削除)]ボタン押下
		mainView.btnCoastalDeleteVessel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCoastalDeleteVessel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [←(外航船追加)]ボタン押下
		mainView.btnOceanAddVessel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnOceanAddVessel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [→(外航船削除)]ボタン押下
		mainView.btnOceanDeleteVessel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnOceanDeleteVessel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {

		// 検索押下時と同様の制御
		btnSearch_Click();

	}

	/**
	 * 指示画面[検索]ボタン押下
	 */
	protected void btnSearch_Click() {

		try {

			// 一覧をクリア
			mainView.tblRight.removeRow();
			mainView.tblUpper.removeRow();
			mainView.tblLower.removeRow();

			// データ取得
			List<VesselCO> list = (List<VesselCO>) request(getModelClass(), "get", false);

			// 検索条件に該当する船情報が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			for (VesselCO VesselCO : list) {
				if (VesselCO.getCOKbn().equals("1")) {

					// 左上リストを一覧に表示する
					mainView.tblUpper.addRow(addSelectedCoastl(VesselCO));

				} else if (VesselCO.getCOKbn().equals("2")) {

					// 左下リストを一覧に表示する
					mainView.tblLower.addRow(addSelectedOcean(VesselCO));
				} else {

					// 右リストを一覧に表示する
					mainView.tblRight.addRow(addCandidateVessel(VesselCO));
				}
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

			// データ抽出
			byte[] data = (byte[]) request(getModelClass(), "getExcel");

			// データが無かった場合、エラーメッセージ出力
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11985") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [←(内航船追加)]ボタン押下
	 */
	protected void btnCoastalAddVessel_Click() {

		try {

			// 右リストから選択されたデータを取得
			List<VesselCO> list = getSelectedVesselList();

			// 左上リストを再設定
			for (VesselCO VesselCO : list) {
				mainView.tblUpper.addRow(addSelectedCoastl(VesselCO));
			}

			// 右リストの選択行を削除
			deleteVesselRow();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [→(内航船除去)]ボタン押下
	 */
	protected void btnCoastalDeleteVessel_Click() {

		try {

			// 左上リストから選択されたデータを取得
			List<VesselCO> list = getSelectedCoastlList();

			// 右リストを再設定
			for (VesselCO VesselCO : list) {
				mainView.tblRight.addRow(addCandidateVessel(VesselCO));
			}

			// 左上リストの選択行を削除
			deleteCoastlRow();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [←(外航船追加)]ボタン押下
	 */
	protected void btnOceanAddVessel_Click() {

		try {

			// 右リストから選択されたデータを取得
			List<VesselCO> list = getSelectedVesselList();

			// 左下リストを再設定
			for (VesselCO VesselCO : list) {
				mainView.tblLower.addRow(addSelectedOcean(VesselCO));
			}

			// 右リストの選択行を削除
			deleteVesselRow();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [→(外航船除去)]ボタン押下
	 */
	protected void btnOceanDeleteVessel_Click() {

		try {

			// 左下リストから選択されたデータを取得
			List<VesselCO> list = getSelectedOceanList();

			// 右リストを再設定
			for (VesselCO VesselCO : list) {
				mainView.tblRight.addRow(addSelectedOcean(VesselCO));
			}

			// 左下リストの選択行を削除
			deleteOceanRow();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 左上リストから選択されたデータを取得
	 * 
	 * @return 選択行リスト
	 */
	protected List<VesselCO> getSelectedCoastlList() {

		List<VesselCO> list = new ArrayList<VesselCO>();

		int[] rows = mainView.tblUpper.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO.setVesselCode((String) mainView.tblUpper.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setVesselNames((String) mainView.tblUpper.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselNames));
			list.add(vesselCO);
		}
		return list;
	}

	/**
	 * 左下リストから選択されたデータを取得
	 * 
	 * @return 選択行リスト
	 */
	protected List<VesselCO> getSelectedOceanList() {

		List<VesselCO> list = new ArrayList<VesselCO>();

		int[] rows = mainView.tblLower.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO.setVesselCode((String) mainView.tblLower.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setVesselNames((String) mainView.tblLower.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselNames));
			list.add(vesselCO);
		}
		return list;
	}

	/**
	 * 右リストから選択されたデータを取得
	 * 
	 * @return 選択行リスト
	 */
	protected List<VesselCO> getSelectedVesselList() {

		List<VesselCO> list = new ArrayList<VesselCO>();

		int[] rows = mainView.tblRight.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO.setVesselCode((String) mainView.tblRight.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setVesselNames((String) mainView.tblRight.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselNames));
			list.add(vesselCO);
		}
		return list;
	}

	/**
	 * 左上リストから選択されたデータを削除
	 */
	protected void deleteCoastlRow() {

		int[] rows = mainView.tblUpper.getSelectedRows();
		for (int i = rows.length - 1; i >= 0; i--) {
			mainView.tblUpper.removeRow(rows[i]);
		}
	}

	/**
	 * 左下リストから選択されたデータを削除
	 */
	protected void deleteOceanRow() {

		int[] rows = mainView.tblLower.getSelectedRows();
		for (int i = rows.length - 1; i >= 0; i--) {
			mainView.tblLower.removeRow(rows[i]);
		}
	}

	/**
	 * 右リストから選択されたデータを削除
	 */
	protected void deleteVesselRow() {

		int[] rows = mainView.tblRight.getSelectedRows();
		for (int i = rows.length - 1; i >= 0; i--) {
			mainView.tblRight.removeRow(rows[i]);
		}
	}

	/**
	 * 左上リストにデータを追加する
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> addSelectedCoastl(VesselCO bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean.getVesselCode());
		list.add(bean.getVesselNames());

		return list;
	}

	/**
	 * 左下リストにデータを追加する
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> addSelectedOcean(VesselCO bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean.getVesselCode());
		list.add(bean.getVesselNames());

		return list;
	}

	/**
	 * 右リストにデータを追加する
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> addCandidateVessel(VesselCO bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean.getVesselCode());
		list.add(bean.getVesselNames());

		return list;
	}

	/**
	 * 指示画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			// 左上下リスト一覧の取得
			List<VesselCO> list = getSelectedVesselCOList();

			// DB一括削除
			request(getModelClass(), "delete");

			// 新規登録
			request(getModelClass(), "entry", list);

			// 完了メッセージ
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 左上下リスト一覧の取得
	 * 
	 * @return 選択行リスト
	 */
	protected List<VesselCO> getSelectedVesselCOList() {

		List<VesselCO> list = new ArrayList<VesselCO>();

		// 左上リスト一覧の取得
		int rows = mainView.tblUpper.getRowCount();
		for (int i = 0; i < rows; i++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO
				.setVesselCode((String) mainView.tblUpper.getRowValueAt(i, MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setCOKbn("1");
			list.add(vesselCO);
		}

		// 左下リスト一覧の取得
		int rows_ = mainView.tblLower.getRowCount();
		for (int j = 0; j < rows_; j++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO
				.setVesselCode((String) mainView.tblLower.getRowValueAt(j, MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setCOKbn("2");
			list.add(vesselCO);
		}
		return list;
	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getModelClass() {
		return VesselCOManager.class;
	}
}

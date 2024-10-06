package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.ui.MG0450BankMergePanel.IN;
import jp.co.ais.trans2.model.bank.*;
import jp.co.ais.trans2.model.bank.BankMergeError.ErrorContent;

/**
 * 銀行統廃合のコントロール
 */
public class MG0450BankMergePanelCtrl extends TController {

	/** 指示画面 */
	protected MG0450BankMergePanel mainView;

	/**
	 * コンストラクタ
	 */
	@Override
	public void start() {
		try {
			// 指示画面生成
			createMainView();

			// 指示画面を初期化
			initMainView();

			// 指示画面表示
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @see jp.co.ais.trans2.common.client.TController#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {

		return mainView;
	}

	/**
	 * 新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0450BankMergePanel();
		addMainViewEvent();
	}

	/**
	 * ボタンのイベント発生
	 */
	protected void addMainViewEvent() {

		// エクセル取込
		mainView.btnImportExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doImportExcel();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 実行ボタン
		mainView.btnExecute.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doExecute();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 指示画面初期化
	 */
	protected void initMainView() {

		mainView.btnExecute.setEnabled(false);

	}

	/**
	 * エクセル取込ボタン押下
	 */
	protected void doImportExcel() {

		try {

			TFileChooser fc = new TFileChooser();

			TFileFilter filter = new TFileFilter(getWord("C11432"), ExtensionType.XLS, ExtensionType.XLSX); // MS-Excelファイル

			fc.setFileFilter(filter);
			fc.setMultiSelectionEnabled(true);

			if (TFileChooser.FileStatus.Selected != fc.show(mainView)) {
				return;
			}

			// File形式で取得
			File[] file = fc.getSelectedFiles();

			BankMergeErrorCheck check = new BankMergeErrorCheck();

			for (File checkFile : file) {

				try {
					List<BankMerge> bank = check.toBankMergeData(checkFile);

					for (int i = 0; i < bank.size(); i++) {
						BankMerge bankDate = new BankMerge();

						int j = 0;
						if (i == 0) {
							j = 0;
						} else {
							j = 1;
						}
						bankDate.setOldBankCode(bank.get(i).getOldBankCode());
						bankDate.setOldBankName(bank.get(i).getOldBankName());
						bankDate.setOldBankOffCode(bank.get(i).getOldBankOffCode());
						bankDate.setNewBankCode(bank.get(i).getNewBankCode());
						bankDate.setNewBankKana(bank.get(i).getNewBankKana());
						bankDate.setNewBankKanaFb(bank.get(i).getNewBankKanaFb());
						bankDate.setNewBankName(bank.get(i).getNewBankName());
						bankDate.setNewBankOffCode(bank.get(i).getNewBankOffCode());
						bankDate.setNewBankOffKana(bank.get(i).getNewBankOffKana());
						bankDate.setNewBankOffKanaFb(bank.get(i).getNewBankOffKanaFb());
						bankDate.setNewBankOffName(bank.get(i).getNewBankOffName());
						bankDate.setDateFrom(bank.get(i).getDateFrom());
						bankDate.setDateTo(bank.get(i).getDateTo());

						request(getManagerClass(), "entry", bankDate, j);
					}

					// エラーがない場合、更新対象データを表示
					List<BankMerge> list = getBankData();

					if (list == null || list.isEmpty()) {
						return;
					} else {
						mainView.btnExecute.setEnabled(true);
					}

					// 更新銀行一覧にデータ表示
					mainView.tblErrList.removeRow();
					setImportList(list);

				} catch (BankMergeError e) {

					mainView.tblImportList.removeRow();
					mainView.tblErrList.removeRow();
					setErrorContents(e.getErrorList());
					return;

				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return list
	 * @throws TException
	 */
	protected List<BankMerge> getBankData() throws TException {

		List<BankMerge> list = (List<BankMerge>) request(getManagerClass(), "get");
		return list;
	}

	/**
	 * 実行ボタン押下
	 */
	protected void doExecute() {

		try {

			// 対象銀行一覧から何も選択されてないばあい。
			if (mainView.tblImportList.getCheckedRowCount(IN.CHK_BOX) == 0) {
				// I00596 対象銀行一覧からデータを選択してください。
				showMessage(mainView.getParentFrame(), "I00596");
				return;
			}

			// 複数行選択の場合
			else if (mainView.tblImportList.getCheckedRowCount(IN.CHK_BOX) > 1) {
				// 一行のデータを選んでください
				showMessage(mainView.getParentFrame(), "I00044");
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// 新銀行、支店コードを基準に銀行マスタ内の重なるデータを削除

			List<BankMerge> countList = new ArrayList<BankMerge>();
			int checkIndex = mainView.tblImportList.getFirstCheckedRowIndex(MG0450BankMergePanel.IN.CHK_BOX);

			List<BankMerge> bankMergeList = (List<BankMerge>) mainView.tblImportList.getRowValueAt(checkIndex,
				MG0450BankMergePanel.IN.ENTITY);

			countList = (List<BankMerge>) request(getManagerClass(), "seachcount", bankMergeList);
			request(getManagerClass(), "updata", bankMergeList);

			// 完了メッセージを表示
			showMessage(mainView.getParentFrame(), "I00013");

			// 更新完了内容を表示。
			CompList(countList);

		} catch (Exception e) {

			errorHandler(e);
		}

	}

	/**
	 * @param countList
	 */
	protected void CompList(List<BankMerge> countList) {

		try {
			mainView.tblImportList.removeRow();

			// エラーがない場合、更新対象データを表示

			List<BankMerge> list = getBankData();

			if (list == null || list.isEmpty()) {
				mainView.btnExecute.setEnabled(false);
			}

			// 更新銀行一覧にデータ表示
			setImportList(list);

			// 一覧表示
			for (int i = 0; i < 8; i++) {

				mainView.tblCompList.addRow(getExRowData(countList, i));
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * @param countList
	 * @param i
	 * @return rowData;
	 */
	protected List<Object> getExRowData(List<BankMerge> countList, int i) {

		List<Object> rowData = new ArrayList<Object>();

		String[] master = { getWord("C02323"),// 銀行マスタ
				getWord("C02322"),// 銀行口座マスタ
				getWord("C00913"),// 社員マスタ
				getWord("C02325"),// 取引先支払条件マスタ
				getWord("C11844"),// 社外振込データ
				getWord("C11845"),// 支払集計データ
				getWord("C11846"),// 支払手形データ
				getWord("C11847") // 支払振込データ
		};

		if (i == 0) {
			mainView.tblCompList.removeRow();
		}
		rowData.add(master[i]);
		rowData.add(countList.get(i).getAdd());
		rowData.add(countList.get(i).getRenew());
		rowData.add(countList);

		return rowData;
	}

	/**
	 * @return BankMergeManager.class;
	 */
	protected Class getManagerClass() {
		return BankMergeManager.class;
	}

	/**
	 * 対象銀行一覧
	 * 
	 * @param list
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected void setImportList(List<BankMerge> list) throws TException {

		mainView.tblImportList.removeRow();

		List<BankMerge> bankDataList = new ArrayList<BankMerge>();
		BankMerge bankData = list.get(0);

		for (BankMerge bankMerge : list) {
			if (!Util.avoidNull(bankMerge.getNewBankName()).equals(Util.avoidNull(bankData.getNewBankName()))) {
				mainView.tblImportList.addRow(getRowData(bankDataList));
				bankDataList = new ArrayList<BankMerge>();
			}
			bankDataList.add(bankMerge);
			bankData = bankMerge;
		}

		mainView.tblImportList.addRow(getRowData(bankDataList));

	}

	/**
	 * @param bankDataList
	 * @return rowData
	 */
	protected List<Object> getRowData(List<BankMerge> bankDataList) {

		List<Object> rowData = new ArrayList<Object>();
		rowData.add(false);
		rowData.add(bankDataList.get(0).getNewBankName());
		rowData.add(bankDataList.get(0).getOldBankName());
		rowData.add(bankDataList.size());
		rowData.add(bankDataList);

		return rowData;
	}

	/**
	 * 再検索
	 */
	protected void listSeach() {

		try {
			mainView.tblImportList.removeRow();

			// エラーがない場合、更新対象データを表示

			List<BankMerge> list = getBankData();

			if (list == null || list.isEmpty()) {
				return;
			} else {
				mainView.btnExecute.setEnabled(true);
			}

			// 一覧にデータ表示
			setImportList(list);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * エラーリストを表示
	 * 
	 * @param list
	 */
	protected void setErrorContents(List<ErrorContent> list) {

		for (ErrorContent content : list) {
			List<Object> objList = new ArrayList<Object>();
			objList.add(content.getLine());
			objList.add(content.getError());

			mainView.tblErrList.addRow(objList);
		}
	}

}

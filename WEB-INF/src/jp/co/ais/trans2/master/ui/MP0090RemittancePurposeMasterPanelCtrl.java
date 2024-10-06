package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.remittance.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * MP0090RemittancePurposeMaster - 送金目的マスタ - Main Controller
 * 
 * @author AIS
 */
public class MP0090RemittancePurposeMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MP0090RemittancePurposeMasterPanel mainView = null;

	/** 編集画面 */
	protected MP0090RemittancePurposeMasterDialog editView = null;

	/** 現在操作中のモードを把握するために使用する */
	protected Mode mode = null;

	/** 操作モード */
	protected enum Mode {

		/** 新規 */
		NEW,
		/** 修正 */
		MODIFY,
		/** 複写 */
		COPY

	}

	@Override
	public void start() {
		try {

			// 指示画面生成
			createMainView();

			// 指示画面初期化
			initMainView();

			// 指示画面表示
			mainView.setVisible(true);

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
		mainView = new MP0090RemittancePurposeMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面初期化
	 */
	protected void initMainView() {
		setMainButtonEnabled(false);
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln Boolean
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * 指示画面のイベント定義
	 */
	protected void addMainViewEvent() {
		// [新規]ボタン
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [検索]ボタン
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [編集]ボタン
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [複写]ボタン
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [削除]ボタン
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [エクセル]ボタン
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 指示画面[新規]ボタン押下
	 */
	protected void btnNew_Click() {
		try {

			// 編集画面生成
			createEditView();

			// 編集画面初期化
			initEditView(Mode.NEW, null);

			// 編集画面を表示
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面[検索]ボタン押下
	 */
	protected void btnSearch_Click() {
		try {

			// 検索条件取得
			RemittanceSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlRemittancePurposeRefRan.requestFocus();
				return;
			}

			// 一覧をクリア
			mainView.tbList.removeRow();

			// メインボタン制御
			setMainButtonEnabled(false);

			// 送金目的情報を取得
			List<Remittance> list = getList(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (Remittance cst : list) {
				mainView.tbList.addRow(getRowData(cst));
			}

			// メインボタン制御
			setMainButtonEnabled(true);

			mainView.tbList.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [編集]ボタン押下
	 */
	protected void btnModify_Click() {
		try {

			if (!checkMainView()) {
				return;
			}

			// 編集対象のデータ取得
			Remittance bean = getSelected();

			if (bean == null) {
				return;
			}

			// 編集画面生成
			createEditView();

			// 編集画面に選択データをセット
			initEditView(Mode.MODIFY, bean);

			// 編集画面表示
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [複写]ボタン押下
	 */
	protected void btnCopy_Click() {
		try {

			if (!checkMainView()) {
				return;
			}

			// 複写対象のデータ取得
			Remittance bean = getSelected();

			if (bean == null) {
				return;
			}

			// 複写画面生成
			createEditView();

			// 編集画面に選択データをセット
			initEditView(Mode.COPY, bean);

			// 複写画面表示
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [削除]ボタン押下
	 */
	protected void btnDelete_Click() {
		try {

			if (!checkMainView()) {
				return;
			}

			// 確認メッセージ表示
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象のデータ取得
			Remittance bean = getSelected();

			if (bean == null) {
				return;
			}

			// 削除実行
			doDelete(bean);

			// 削除した行を一覧から削除
			mainView.tbList.removeSelectedRow();

			// 削除した後、一覧のレコードが0件の場合、メインボタン制御
			if (mainView.tbList.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

			// 完了メッセージ
			showMessage(mainView.getParentFrame(), "I00013");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 選択行のチェック
	 * 
	 * @return true:エラーなし
	 */
	protected boolean checkMainView() {

		if (mainView.tbList.getSelectedRow() == -1) {
			// 一覧からデータを選択してください。
			showMessage("I00043");
			return false;
		}

		return true;
	}

	/**
	 * [エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {
		try {

			// 検索条件取得
			RemittanceSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlRemittancePurposeRefRan.requestFocus();
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			byte[] data = (byte[]) request(getModelClass(), "getExcelRemittancePurpose", condition);

			// データが無かった場合、エラーメッセージ出力
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// エクセルタイトルセット
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11843") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面作成
	 */
	protected void createEditView() {
		editView = new MP0090RemittancePurposeMasterDialog(mainView.getParentFrame(), true);
		addEditViewEvent();
	}

	/**
	 * 編集画面のイベント定義
	 */
	protected void addEditViewEvent() {
		// [確定]ボタン押下
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [戻る]ボタン押下
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {
		try {

			// 入力データチェック
			if (!isInputCorrect()) {
				return;
			}

			// 入力データ取得
			Remittance bean = getInputtedRemittance();

			// 新規登録の場合
			switch (mode) {

			case NEW:
			case COPY:

				// 新規登録 - 複写登録
				request(getModelClass(), "entry", bean);

				// 追加分を一覧に反映
				mainView.tbList.addRow(getRowData(bean));

				// メインボタン制御
				setMainButtonEnabled(true);

				break;

			case MODIFY:

				// 修正
				request(getModelClass(), "modify", bean);

				// 修正分を一覧に反映
				mainView.tbList.modifySelectedRow(getRowData(bean));

				break;
			}

			btnClose_Click();
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面[戻る]ボタン押下
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * 編集画面初期化
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, Remittance bean) {

		this.mode = editMode;

		switch (editMode) {

		case NEW:
			editView.setTitle(getWord("C01698"));

			break;

		case MODIFY:
		case COPY:

			if (Mode.MODIFY == editMode) {

				editView.setTitle(getWord("C00977"));
				editView.ctrlRemittancePurposeCode.setEditable(false);
				editView.setEditMode();

			} else {

				editView.setTitle(getWord("C01738"));

			}

			editView.ctrlRemittancePurposeCode.setValue(bean.getCode());
			editView.ctrlRemittancePurpose.setValue(bean.getName());

			break;
		}
	}

	/**
	 * 編集画面の入力値取得
	 * 
	 * @return Summary
	 */
	protected Remittance getInputtedRemittance() {

		Remittance bean = new Remittance();

		bean.setCompanyCode(getCompanyCode());
		bean.setCode(editView.ctrlRemittancePurposeCode.getValue());
		bean.setName(editView.ctrlRemittancePurpose.getValue());

		return bean;
	}

	/**
	 * 検索条件取得
	 * 
	 * @return SummarySearchCondition 検索条件
	 */
	protected RemittanceSearchCondition getSearchCondition() {

		RemittanceSearchCondition condition = new RemittanceSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlRemittancePurposeRefRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlRemittancePurposeRefRan.getCodeTo());

		return condition;
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(Remittance bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getCode()); // 送金目的コード
		list.add(bean.getName()); // 送金目的名カナ

		list.add(bean);

		return list;
	}

	/**
	 * @return 検索条件
	 */
	protected ConsumptionTaxSearchCondition createCondition() {
		return new ConsumptionTaxSearchCondition();
	}

	/**
	 * 一覧で選択したデータを取得
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected Remittance getSelected() throws Exception {

		Remittance bean = (Remittance) mainView.tbList
				.getSelectedRowValueAt(MP0090RemittancePurposeMasterPanel.SC.ENTITY);

		RemittanceSearchCondition condition = new RemittanceSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<Remittance> list = getList(condition);

		// メッセージ追加必要 ##########
		if (list == null || list.isEmpty()) {
			showMessage("I00193");// 選択されたデータは他ユーザーにより削除されました。
			return null;
		}
		return list.get(0);
	}

	/**
	 * 検索条件に該当するデータを返す
	 * 
	 * @param condition
	 * @return List
	 * @throws Exception
	 */
	protected List<Remittance> getList(RemittanceSearchCondition condition) throws Exception {
		List<Remittance> list = (List<Remittance>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * 指定行データを削除する
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(Remittance bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return モデル
	 */
	protected Class getModelClass() {
		return RemittanceManager.class;
	}

	/**
	 * 編集画面入力値の妥当性をチェック *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {

		if (Util.isNullOrEmpty(editView.ctrlRemittancePurposeCode.getValue())) {
			showMessage(editView, "I00037", "C00793"); // 送金目的コード
			editView.ctrlRemittancePurposeCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlRemittancePurpose.getValue())) {
			showMessage(editView, "I00037", "C00947"); // 送金目的
			editView.ctrlRemittancePurpose.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一会社コード&&同一送金目的コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			RemittanceSearchCondition condition = new RemittanceSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlRemittancePurposeCode.getValue());

			List<Remittance> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00697");
				editView.ctrlRemittancePurposeCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}
}
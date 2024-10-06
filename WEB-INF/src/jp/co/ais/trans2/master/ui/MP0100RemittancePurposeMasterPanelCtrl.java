package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * 新送金目的マスタコントローラ
 */
public class MP0100RemittancePurposeMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MP0100RemittancePurposeMasterPanel mainView = null;

	/** 編集画面 */
	protected MP0100RemittancePurposeMasterDialog editView = null;

	/** 登録日付 */
	protected Date inpDate = null;

	/**
	 * 操作モード。現在操作中のモードを把握するために使用する。
	 */
	protected Mode mode = null;

	/**
	 * 操作モード
	 */
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
		mainView = new MP0100RemittancePurposeMasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * 指示画面のボタン押下時のイベント
	 */
	protected void addMainViewEvent() {
		// [新規]ボタン押下
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [編集]ボタン押下
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [複写]ボタン押下
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [削除]ボタン押下
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
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
	 * 指示画面を初期化する
	 */
	protected void initMainView() {

		// メインボタンを押下不可能にする
		setMainButtonEnabled(false);
	}

	/**
	 * 指示画面[新規]ボタン押下
	 */
	protected void btnNew_Click() {

		try {

			// 編集画面を生成
			createEditView();

			// 編集画面を初期化する
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

			// 開始コード <= 終了コードにしてください。
			if (Util.isSmallerThen(mainView.ctrlSearch.getCodeFrom(), mainView.ctrlSearch.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlSearch.getFieldFrom().requestFocus();
				return;

			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 送金目的情報を取得
			RemittanceSearchCondition condition = getSearchCondition();
			List<Remittance> list = getRemittancePurpose(condition);

			// 検索条件に該当する送金目的が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage("I00022");
				return;
			}

			// 送金目的情報を一覧に表示する
			for (Remittance bean : list) {
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
	 * [編集]ボタン押下
	 */
	protected void btnModify_Click() {

		try {

			// 選択行のチェック
			if (!checkMainView()) {
				return;
			}

			// 編集対象の送金目的を取得する
			Remittance bean = getSelectedRemittancePurpose();
			inpDate = bean.getInpDate();

			// 編集画面を生成し、編集対象の送金目的情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, bean);

			// 編集画面を表示する
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

			// 選択行のチェック
			if (!checkMainView()) {
				return;
			}

			// 複写対象の送金目的を取得する
			Remittance bean = getSelectedRemittancePurpose();

			// 複写画面を生成し、複写対象の送金目的情報をセットする
			createEditView();
			initEditView(Mode.COPY, bean);

			// 複写画面を表示する
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

			// 選択行のチェック
			if (!checkMainView()) {
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {
				return;
			}

			// 送金目的情報を取得
			Remittance bean = getSelectedRemittancePurpose();

			// 削除
			request(getModelClass(), "deleteRemittance", bean);

			// 削除した送金目的を一覧から削除
			mainView.tbl.removeSelectedRow();

			// 完了メッセージ
			showMessage(mainView, "I00013");

			// 削除した結果0件になったらメインボタンを押下不可能にする
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

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

		if (mainView.tbl.getSelectedRow() == -1) {
			// 一覧からデータを選択してください。
			showMessage("I00043");
			return false;
		}

		return true;
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

			// データ抽出
			RemittanceSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcelRemittance", condition);

			if (data == null || data.length == 0) {
				showMessage("I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11843") + ".xls"); // 送金目的マスタ

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MP0100RemittancePurposeMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

	}

	/**
	 * 編集画面のイベント定義。
	 */
	protected void addEditViewEvent() {

		// [確定]ボタン押下
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 国際収支コード確定時
		editView.ctrlBalanceCode.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}

				ctrlRemmitanceReference_after();
			}
		});
	}

	/**
	 * 国際収支名称が送金目的名称の初期値としてセットされる（送金目的名称変更可能）
	 */
	protected void ctrlRemmitanceReference_after() {
		editView.ctrlPurposeName.setValue(editView.ctrlBalanceCode.getNames());
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード
	 * @param bean 送金目的。修正、複写の場合は当該送金目的情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, Remittance bean) {

		this.mode = mode_;

		if (Mode.NEW == mode) {
			// 新規
			editView.setTitle(getWord("C01698")); // 新規画面
		}

		else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			if (Mode.MODIFY == mode) {
				// 編集
				editView.setTitle(getWord("C00977")); // 編集画面
				editView.ctrlPurposeCode.setEditable(false);
				editView.setEditMode();

			} else {
				// 複写
				editView.setTitle(getWord("C01738")); // 複写画面
			}

			editView.ctrlPurposeCode.setValue(bean.getCode());
			editView.ctrlBalanceCode.code.setValue(bean.getBalanceCode());
			editView.ctrlBalanceCode.name.setValue(bean.getBalanceName());
			editView.ctrlPurposeName.setValue(bean.getName());
		}
	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 入力チェックを行う
			if (!isDialogInputCheck()) {
				return;
			}

			// 入力された送金目的情報を取得
			Remittance bean = getInputedRemittancePurpose();

			if (Mode.NEW == mode || Mode.COPY == mode) {
				// 新規登録または複写

				request(getModelClass(), "entryRemittance", bean);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(bean));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);
				mainView.tbl.setRowSelection(0);

			} else if (Mode.MODIFY == mode) {
				// 修正

				request(getModelClass(), "modifyRemittance", bean);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(bean));
			}

			// 編集画面を閉じる
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
	 * Servletクラスを返す
	 * 
	 * @return RemittancePurposeManager
	 */
	protected Class getModelClass() {
		return RemittanceManager.class;
	}

	/**
	 * 編集画面で入力された送金目的を返す
	 * 
	 * @return 編集画面で入力された送金目的
	 */
	protected Remittance getInputedRemittancePurpose() {

		Remittance bean = new Remittance();

		bean.setCompanyCode(super.getCompanyCode());
		bean.setCode(editView.ctrlPurposeCode.getValue());
		bean.setBalanceCode(editView.ctrlBalanceCode.getCode());
		bean.setName(editView.ctrlPurposeName.getValue());
		bean.setInpDate(inpDate);

		return bean;
	}

	/**
	 * @param bean
	 * @return データ取得
	 */
	protected List<Object> getRowData(Remittance bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode()); // 送金目的コード
		list.add(bean.getBalanceCode()); // 国際収支コード
		list.add(bean.getName()); // 送金目的名称
		list.add(bean);

		return list;
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param mainEnabled
	 */
	protected void setMainButtonEnabled(boolean mainEnabled) {
		mainView.btnModify.setEnabled(mainEnabled);
		mainView.btnCopy.setEnabled(mainEnabled);
		mainView.btnDelete.setEnabled(mainEnabled);
	}

	/**
	 * 検索条件に該当する送金目的のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する送金目的のリスト
	 * @throws Exception
	 */
	protected List<Remittance> getRemittancePurpose(RemittanceSearchCondition condition) throws Exception {
		condition.setCompanyCode(getCompanyCode());
		List<Remittance> list = (List<Remittance>) request(getModelClass(), "getRemittance", condition);

		return list;
	}

	/**
	 * 指示画面で入力された送金目的の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected RemittanceSearchCondition getSearchCondition() {

		RemittanceSearchCondition condition = createSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlSearch.getCodeFrom());
		condition.setCodeTo(mainView.ctrlSearch.getCodeTo());

		return condition;
	}

	/**
	 * @return 送金目的情報の検索条件
	 */
	protected RemittanceSearchCondition createSearchCondition() {
		return new RemittanceSearchCondition();
	}

	/**
	 * 一覧で選択されている送金目的を返す
	 * 
	 * @return 一覧で選択されている送金目的
	 * @throws Exception
	 */
	protected Remittance getSelectedRemittancePurpose() throws Exception {

		RemittanceSearchCondition condition = createSearchCondition();
		condition.setCode((String) mainView.tbl
			.getSelectedRowValueAt(MP0100RemittancePurposeMasterPanel.SC.purposeCode));
		condition.setCompanyCode(getCompany().getCode());

		List<Remittance> list = getRemittancePurpose(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * 編集画面で入力した値が妥当か
	 * 
	 * @return true：正常、false：エラーあり
	 * @throws Exception
	 */
	protected boolean isDialogInputCheck() throws Exception {

		// 送金目的コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlPurposeCode.getValue())) {
			showMessage(editView, "I00037", "C00793"); // 送金目的コードを入力してください。
			editView.ctrlPurposeCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合、同一送金目的コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			RemittanceSearchCondition condition = createSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlPurposeCode.getValue());

			List<Remittance> list = getRemittancePurpose(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00793"); // 指定の送金目的コードは既に存在します。
				editView.ctrlPurposeCode.requestTextFocus();
				return false;
			}
		}

		// 国際収支コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlBalanceCode.getCode())) {
			showMessage(editView, "I00037", "C11839"); // 国際収支コードを入力してください。
			editView.ctrlBalanceCode.btn.requestFocus();
			return false;
		}

		// 送金目的名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlPurposeName.getValue())) {
			showMessage(editView, "I00037", "C11840"); // 送金目的名称を入力してください。
			editView.ctrlPurposeName.requestFocus();
			return false;
		}

		return true;
	}
}

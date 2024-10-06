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
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.remark.*;

/**
 * MG0120RemarkMaster - 摘要マスタ - Main Controller
 * 
 * @author AIS
 */
public class MG0120RemarkMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0120RemarkMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0120RemarkMasterDialog editView = null;

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
		mainView = new MG0120RemarkMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面初期化
	 */
	protected void initMainView() {
		setMainButtonEnabled(false);
		mainView.ctrlRemRefRan.ctrlRemReferenceFrom.name.setEditable(false);
		mainView.ctrlRemRefRan.ctrlRemReferenceTo.name.setEditable(false);
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
			RemarkSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlRemRefRan.requestFocus();
				return;
			}

			// 一覧をクリア
			mainView.tbList.removeRow();

			// メインボタン制御
			setMainButtonEnabled(false);

			// データ取得
			List<Remark> list = getRemark(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (Remark smr : list) {
				mainView.tbList.addRow(getRowData(smr));
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
			Remark bean = getSelected();

			// 戻り値がNULLの場合
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
			Remark bean = getSelected();

			// 戻り値がNULLの場合
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
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {
				return;
			}

			// 削除対象のデータ取得
			Remark bean = getSelected();

			// 戻り値がNULLの場合
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

			showMessage(mainView.getParentFrame(), "I00013");
			return;

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
			RemarkSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlRemRefRan.requestFocus();
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			// データが無かった場合、エラーメッセージ出力
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// エクセルタイトルセット
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02349") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面作成
	 */
	protected void createEditView() {
		editView = new MG0120RemarkMasterDialog(mainView.getParentFrame(), true);
		addEditViewEvent();
	}

	/**
	 * 編集画面のイベント定義
	 */
	protected void addEditViewEvent() {
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
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

			if (!isInputCorrect()) {
				return;
			}

			Remark bean = getInputtedRemark();

			switch (mode) {

				case NEW:
				case COPY:

					request(getModelClass(), "entry", bean);

					mainView.tbList.addRow(getRowData(bean));

					setMainButtonEnabled(true);

					break;

				case MODIFY:

					request(getModelClass(), "modify", bean);

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
	protected void initEditView(Mode editMode, Remark bean) {

		this.mode = editMode;

		switch (editMode) {

			case NEW:
				editView.setTitle(getWord("C01698"));
				editView.ctrlStrDate.setValue(editView.ctrlStrDate.getCalendar().getMinimumDate());
				editView.ctrlEndDate.setValue(editView.ctrlEndDate.getCalendar().getMaximumDate());

				break;

			case MODIFY:
			case COPY:

				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));
					editView.rdoSlipRemark.setEnabled(false);
					editView.rdoSlipRowRemark.setEnabled(false);

					editView.ctrlTekCode.setEditable(false);
					editView.setEditMode();

				} else {

					editView.setTitle(getWord("C01738"));

				}

				editView.rdoSlipRemark.setSelected(bean.isSlipRemark());
				editView.rdoSlipRowRemark.setSelected(!bean.isSlipRemark());
				editView.ctrlDataKbn.setSelectedItemValue(DataDivision.getDataDivision(bean.getDataType()));
				editView.ctrlTekCode.setValue(bean.getCode());
				editView.ctrlTekName.setValue(bean.getName());
				editView.ctrlTekNameK.setValue(bean.getNamek());
				editView.ctrlStrDate.setValue(bean.getDateFrom());
				editView.ctrlEndDate.setValue(bean.getDateTo());

				break;
		}

	}

	/**
	 * 編集画面の入力値取得
	 * 
	 * @return Remark
	 */
	protected Remark getInputtedRemark() {

		Remark bean = new Remark();

		bean.setCompanyCode(getCompanyCode());

		if (editView.rdoSlipRemark.isSelected()) {
			bean.setSlipRemark(true);
		} else {
			bean.setSlipRemark(false);
		}

		bean.setDataType(DataDivision.getDataDivisionCode((DataDivision) editView.ctrlDataKbn.getSelectedItemValue()));

		bean.setCode(editView.ctrlTekCode.getValue());
		bean.setName(editView.ctrlTekName.getValue());
		bean.setNamek(editView.ctrlTekNameK.getValue());
		bean.setDateFrom(editView.ctrlStrDate.getValue());
		bean.setDateTo(editView.ctrlEndDate.getValue());

		return bean;
	}

	/**
	 * 検索条件取得
	 * 
	 * @return RemarkSearchCondition 検索条件
	 */
	protected RemarkSearchCondition getSearchCondition() {

		RemarkSearchCondition condition = new RemarkSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlRemRefRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlRemRefRan.getCodeTo());

		// 摘要区分による検索チェック
		if (mainView.rdoDnp.isSelected()) {
			condition.setSlipRemark(true);
			condition.setSlipRowRemark(false);
		} else if (mainView.rdoGyo.isSelected()) {
			condition.setSlipRemark(false);
			condition.setSlipRowRemark(true);
		} else {
			condition.setSlipRemark(true);
			condition.setSlipRowRemark(true);
		}

		// 有効期限による検索チェック
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}
		return condition;

	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(Remark bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.isSlipRemark() ? getWord("C00569") : getWord("C00119"));
		list.add(getWord(DataDivision.getDataDivisionName(DataDivision.getDataDivision(bean.getDataType()))));
		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNamek());
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean);

		return list;
	}

	/**
	 * 一覧で選択したデータを取得
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected Remark getSelected() throws Exception {

		Remark bean = (Remark) mainView.tbList.getSelectedRowValueAt(MG0120RemarkMasterPanel.SC.ENTITY);

		RemarkSearchCondition condition = new RemarkSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());
		if (bean.isSlipRemark()) {
			condition.setSlipRowRemark(false);
		} else {
			condition.setSlipRemark(false);
		}

		List<Remark> list = getRemark(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			showMessage("I00193");
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
	protected List<Remark> getRemark(RemarkSearchCondition condition) throws Exception {
		List<Remark> list = (List<Remark>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * 指定行データを削除する
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(Remark bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return モデル
	 */
	protected Class getModelClass() {
		return RemarkManager.class;
	}

	/**
	 * 編集画面入力値の妥当性をチェック *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {

		if (Util.isNullOrEmpty(editView.ctrlDataKbn.getSelectedItemValue())) {
			showMessage(editView, "I00037", "C00567");
			editView.ctrlDataKbn.requestTextFocus();
			return false;
		}
		
		if (Util.isNullOrEmpty(editView.ctrlTekCode.getValue())) {
			showMessage(editView, "I00037", "C00564");
			editView.ctrlTekCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlTekName.getValue())) {
			showMessage(editView, "I00037", "C00565");
			editView.ctrlTekName.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlTekNameK.getValue())) {
			showMessage(editView, "I00037", "C00566");
			editView.ctrlTekNameK.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlStrDate.getValue())) {
			showMessage(editView, "I00037", "COP706");
			editView.ctrlStrDate.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", "COP707");
			editView.ctrlEndDate.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if ((!Util.isNullOrEmpty(editView.ctrlStrDate.getValue()) && !Util.isNullOrEmpty(editView.ctrlEndDate
			.getValue())) && !Util.isSmallerThenByYMD(editView.ctrlStrDate.getValue(), editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.ctrlStrDate.requestFocus();
			return false;
		}

		// 新規、複写の場合は同一会社コード && 同一摘要区分 && 同一摘要コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			RemarkSearchCondition condition = new RemarkSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlTekCode.getValue());
			if (editView.rdoSlipRemark.isSelected()) {
				condition.setSlipRowRemark(false);
			} else {
				condition.setSlipRemark(false);
			}

			List<Remark> list = getRemark(condition);
			
			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00384");
				editView.ctrlTekCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}

}
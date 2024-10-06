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
import jp.co.ais.trans2.model.payment.*;

/**
 * MP0040PaymentMethodMaster - 支払方法マスタ - Main Controller
 * 
 * @author AIS
 */
public class MP0040PaymentMethodMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MP0040PaymentMethodMasterPanel mainView = null;

	/** 編集画面 */
	protected MP0040PaymentMethodMasterDialog editView = null;

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
		mainView = new MP0040PaymentMethodMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面初期化
	 */
	protected void initMainView() {
		setMainButtonEnabled(false);
		mainView.ctrlPayRefRan.ctrlPayReferenceFrom.name.setEditable(false);
		mainView.ctrlPayRefRan.ctrlPayReferenceTo.name.setEditable(false);

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
			PaymentMethodSearchCondition condition = getSearchCondition();

			// 支払対象区分未チェック
			if (!mainView.chkEmployeePayment.isSelected() && !mainView.chkExternalPayment.isSelected()) {
				showMessage(mainView, "I00041", "C01096");
				mainView.chkEmployeePayment.requestFocus();
				return;
			}

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlPayRefRan.requestFocus();
				return;
			}

			// 一覧をクリア
			mainView.tbList.removeRow();

			// メインボタン制御
			setMainButtonEnabled(false);

			// データ取得
			List<PaymentMethod> list = getPaymentMethod(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (PaymentMethod smr : list) {
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
			PaymentMethod bean = getSelected();

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
			PaymentMethod bean = getSelected();

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
			PaymentMethod bean = getSelected();

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
			PaymentMethodSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlPayRefRan.requestFocus();
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
			printer.preview(data, getWord("C02350") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面作成
	 */
	protected void createEditView() {
		// 編集画面を生成
		editView = new MP0040PaymentMethodMasterDialog(mainView.getParentFrame(), true);

		// 編集画面のイベント定義
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

		editView.ctrlPaymentInternalCode.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {
					cboPaymentKind_Change();
				}
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

			PaymentMethod bean = getInputtedPaymentMethod();

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
	 * 現金(社員)・社員未収・現金・消込・相殺・その他 以外をコンボボックスで選択→入力不可＋値消去
	 */
	protected void cboPaymentKind_Change() {

		boolean isEdit = false;

		Object obj = editView.ctrlPaymentInternalCode.getSelectedItemValue();
		if (!Util.isNullOrEmpty(obj)) {
			PaymentKind kind = (PaymentKind) obj;
			isEdit = kind == PaymentKind.EMP_PAY_CASH || kind == PaymentKind.EMP_PAY_UNPAID
				|| kind == PaymentKind.EMP_PAY_ACCRUED || kind == PaymentKind.EX_PAY_CASH
				|| kind == PaymentKind.EX_PAY_ERASE || kind == PaymentKind.EX_PAY_OFFSET || kind == PaymentKind.OTHER;
		}

		if (isEdit) {
			editView.ctrlItemGroup.ctrlItemReference.setEditable(true);
			editView.ctrlDepartment.setEditable(true);
		} else {
			editView.ctrlItemGroup.ctrlItemReference.setEditable(false);
			editView.ctrlItemGroup.ctrlItemReference.clear();
			editView.ctrlItemGroup.ctrlSubItemReference.setEditable(false);
			editView.ctrlItemGroup.ctrlSubItemReference.clear();
			editView.ctrlItemGroup.ctrlDetailItemReference.setEditable(false);
			editView.ctrlItemGroup.ctrlDetailItemReference.clear();
			editView.ctrlDepartment.setEditable(false);
			editView.ctrlDepartment.clear();
		}

	}

	/**
	 * 編集画面初期化
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, PaymentMethod bean) {

		this.mode = editMode;

		switch (editMode) {

			case NEW:
				editView.setTitle(getWord("C01698"));
				editView.ctrlStrDate.setValue(editView.ctrlStrDate.getCalendar().getMinimumDate());
				editView.ctrlEndDate.setValue(editView.ctrlEndDate.getCalendar().getMaximumDate());
				cboPaymentKind_Change(); // 初期セット

				break;

			case MODIFY:
			case COPY:

				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));

					editView.ctrlPayCode.setEditable(false);
					editView.setEditMode();

				} else {

					editView.setTitle(getWord("C01738"));

				}

				editView.ctrlPayCode.setValue(bean.getCode());
				editView.ctrlPayName.setValue(bean.getName());
				editView.ctrlPayNameK.setValue(bean.getNamek());

				// 科目コード
				editView.ctrlItemGroup.ctrlItemReference.setCode(bean.getItemCode());
				// 補助コード
				editView.ctrlItemGroup.ctrlSubItemReference.setCode(bean.getSubItemCode());
				// 内訳コード
				editView.ctrlItemGroup.ctrlDetailItemReference.setCode(bean.getDetailItemCode());
				// 科目、補助、内訳の略称
				editView.ctrlItemGroup.refleshGroupEntity();

				// 計上部門
				editView.ctrlDepartment.setCode(bean.getDepartmentCode());
				editView.ctrlDepartment.refleshEntity();

				editView.rdoEmployeePayment.setSelected(bean.isUseEmployeePayment());
				editView.rdoExternalPayment.setSelected(bean.isUseExPayment());

				editView.ctrlPaymentInternalCode.setSelectedItemValue(bean.getPaymentKind());

				editView.ctrlStrDate.setValue(bean.getDateFrom());
				editView.ctrlEndDate.setValue(bean.getDateTo());

				break;
		}

	}

	/**
	 * 編集画面の入力値取得
	 * 
	 * @return PaymentMethod
	 */
	protected PaymentMethod getInputtedPaymentMethod() {

		PaymentMethod bean = new PaymentMethod();

		bean.setCompanyCode(getCompanyCode());
		bean.setCode(editView.ctrlPayCode.getValue());
		bean.setName(editView.ctrlPayName.getValue());
		bean.setNamek(editView.ctrlPayNameK.getValue());

		bean.setItemCode(editView.ctrlItemGroup.ctrlItemReference.getCode());

		bean.setSubItemCode(editView.ctrlItemGroup.ctrlSubItemReference.getCode());

		bean.setDetailItemCode(editView.ctrlItemGroup.ctrlDetailItemReference.getCode());

		bean.setDepartmentCode(editView.ctrlDepartment.getCode());

		bean.setPaymentDivision(editView.rdoEmployeePayment.isSelected() ? 0 : 1);
		bean.setPaymentKind((PaymentKind) editView.ctrlPaymentInternalCode.getSelectedItemValue());

		bean.setDateFrom(editView.ctrlStrDate.getValue());
		bean.setDateTo(editView.ctrlEndDate.getValue());

		return bean;
	}

	/**
	 * 検索条件取得
	 * 
	 * @return PaymentMethodSearchCondition 検索条件
	 */
	protected PaymentMethodSearchCondition getSearchCondition() {

		PaymentMethodSearchCondition condition = new PaymentMethodSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlPayRefRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlPayRefRan.getCodeTo());

		// 支払対象区分による検索チェック
		if (mainView.chkEmployeePayment.isSelected() && !mainView.chkExternalPayment.isSelected()) {
			condition.setUseEmployeePayment(true);
			condition.setUseExPayment(false);
		} else if (!mainView.chkEmployeePayment.isSelected() && mainView.chkExternalPayment.isSelected()) {
			condition.setUseEmployeePayment(false);
			condition.setUseExPayment(true);
		} else {
			condition.setUseEmployeePayment(false);
			condition.setUseExPayment(false);
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
	protected List<Object> getRowData(PaymentMethod bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNamek());

		list.add(bean.getItemCode()); // 科目コード

		list.add(bean.getSubItemCode()); // 補助科目コード

		list.add(bean.getDetailItemCode()); // 内訳科目コード

		list.add(bean.getDepartmentCode()); // 計上部門コード

		list.add(bean.isUseEmployeePayment() ? getWord("C01119") : getWord("C01124"));

		list.add(getWord(PaymentKind.getPaymentKindName(bean.getPaymentKind())));
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
	protected PaymentMethod getSelected() throws Exception {

		PaymentMethod bean = (PaymentMethod) mainView.tbList
			.getSelectedRowValueAt(MP0040PaymentMethodMasterPanel.SC.ENTITY);

		PaymentMethodSearchCondition condition = new PaymentMethodSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<PaymentMethod> list = getPaymentMethod(condition);

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
	protected List<PaymentMethod> getPaymentMethod(PaymentMethodSearchCondition condition) throws Exception {
		List<PaymentMethod> list = (List<PaymentMethod>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * 指定行データを削除する
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(PaymentMethod bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return モデル
	 */
	protected Class getModelClass() {
		return PaymentMethodManager.class;
	}

	/**
	 * 編集画面入力値の妥当性をチェック *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {

		// 支払方法コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlPayCode.getValue())) {
			showMessage(editView, "I00037", "C00864");
			editView.ctrlPayCode.requestTextFocus();
			return false;
		}

		// 支払方法名称コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlPayName.getValue())) {
			showMessage(editView, "I00037", "C00865");
			editView.ctrlPayName.requestTextFocus();
			return false;
		}

		// 支払方法検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlPayNameK.getValue())) {
			showMessage(editView, "I00037", "C00866");
			editView.ctrlPayNameK.requestTextFocus();
			return false;
		}

		// 科目コードが未入力の場合エラー
		if (editView.ctrlItemGroup.ctrlItemReference.code.isEditable()
			&& Util.isNullOrEmpty(editView.ctrlItemGroup.ctrlItemReference.getCode())) {
			showMessage(editView, "I00037", "C00077");
			editView.ctrlItemGroup.ctrlItemReference.requestTextFocus();
			return false;
		}

		// 補助科目コードが未入力の場合エラー
		if (editView.ctrlItemGroup.ctrlSubItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlItemGroup.ctrlSubItemReference.getCode())) {
				showMessage(editView, "I00037", "C00488");
				editView.ctrlItemGroup.ctrlSubItemReference.requestTextFocus();
				return false;
			}
		}

		// 内訳科目コードが未入力の場合エラー
		if (editView.ctrlItemGroup.ctrlDetailItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlItemGroup.ctrlDetailItemReference.getCode())) {
				showMessage(editView, "I00037", "C00025");
				editView.ctrlItemGroup.ctrlDetailItemReference.requestTextFocus();
				return false;
			}
		}

		// 計上部門コードが未入力の場合エラー
		if (editView.ctrlDepartment.code.isEditable() && Util.isNullOrEmpty(editView.ctrlDepartment.getCode())) {
			showMessage(editView, "I00037", "C00571");
			editView.ctrlDepartment.requestTextFocus();
			return false;
		}

		// 支払内部コードの設定（社外支払いの場合、内部コード<=10だとエラー）
		if (editView.rdoExternalPayment.isSelected()) {
			// 支払い内部コードが10以下のときはエラー
			if (editView.ctrlPaymentInternalCode.getComboBox().getSelectedIndex() < 5) {
				// 警告メッセージ表示
				showMessage(editView, "W00208");
				editView.ctrlPaymentInternalCode.getComboBox().requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 支払内部コードの設定（社員支払いの場合、内部コード>10だとエラー）
		if (editView.rdoEmployeePayment.isSelected()) {
			// 支払い内部コードが10以下のときはエラー
			if (editView.ctrlPaymentInternalCode.getComboBox().getSelectedIndex() > 4) {
				// 警告メッセージ表示
				showMessage(editView, "W00209");
				editView.ctrlPaymentInternalCode.getComboBox().requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlStrDate.getValue())) {
			showMessage(editView, "I00037", "COP706");
			editView.ctrlStrDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", "COP707");
			editView.ctrlEndDate.requestTextFocus();
			return false;
		}

		// 開始年月日 <= 終了年月日チェック
		if ((!Util.isNullOrEmpty(editView.ctrlStrDate.getValue()) && !Util.isNullOrEmpty(editView.ctrlEndDate
			.getValue())) && !Util.isSmallerThenByYMD(editView.ctrlStrDate.getValue(), editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.ctrlStrDate.requestFocus();
			return false;
		}

		// 新規、複写の場合は同一会社コード && 同一支払方法コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			PaymentMethodSearchCondition condition = new PaymentMethodSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlPayCode.getValue());

			List<PaymentMethod> list = getPaymentMethod(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00233");
				editView.ctrlPayCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}

}
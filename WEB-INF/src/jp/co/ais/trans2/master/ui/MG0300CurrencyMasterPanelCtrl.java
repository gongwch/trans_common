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
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 通貨マスタ 画面コントロール
 */
public class MG0300CurrencyMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0300CurrencyMasterPanel mainView;

	/** 編集画面 */
	protected MG0300CurrencyMasterDialog editView = null;

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

	/**
	 * 操作モード。
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
		mainView = new MG0300CurrencyMasterPanel();
		addMainViewEvent();
	}

	/**
	 * メイン画面ボタン押下時のイベント
	 */
	protected void addMainViewEvent() {
		// [新規]ボタン押下
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [編集]ボタン押下
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [複写]ボタン押下
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [削除]ボタン押下
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
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

	}

	/**
	 * メイン画面の初期化
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
			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.currencyRange.getCodeFrom(), mainView.currencyRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.currencyRange.getFieldFrom().requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 通貨情報を取得
			CurrencySearchCondition condition = getSearchCondition();
			List<Currency> list = getCurrency(condition);

			// 検索条件に該当する通貨が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {

				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 通貨情報を一覧に表示する
			for (Currency currency : list) {
				mainView.tbl.addRow(getRowData(currency));
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

			if (!checkMainView()) {
				return;
			}
			// 編集対象の通貨を取得する
			Currency currency = getSelectedCurrency();

			// 戻り値がNULLの場合
			if (currency == null) {
				return;
			}

			// 編集画面を生成し、編集対象の通貨情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, currency);

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

			if (!checkMainView()) {
				return;
			}

			// 複写対象の通貨を取得する
			Currency currency = getSelectedCurrency();

			// 戻り値がNULLの場合
			if (currency == null) {
				return;
			}

			// 複写画面を生成し、複写対象の通貨情報をセットする
			createEditView();
			initEditView(Mode.COPY, currency);

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

			if (!checkMainView()) {
				return;
			}

			// 削除対象の通貨を取得する
			Currency currency = getSelectedCurrency();

			// 戻り値がNULLの場合
			if (currency == null) {
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除する
			deleteCurrency(currency);

			// 削除した通貨を一覧から削除
			mainView.tbl.removeSelectedRow();

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
			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.currencyRange.getCodeFrom(), mainView.currencyRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.currencyRange.getFieldFrom().requestFocus();
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// データ抽出
			CurrencySearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C01985") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0300CurrencyMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();
	}

	/**
	 * 編集画面のイベント定義。
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
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param bean  通貨。修正、複写の場合は当該通貨情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, Currency bean) {

		this.mode = mode_;
		// 新規
		switch (mode) {
		// 新規
		case NEW:
			editView.setTitle(getWord("C01698"));
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editView.ctrlCurrencyCode.setRegex("[A-Z,0-9]");

			break;

		case COPY:
		case MODIFY:
			// 編集
			if (Mode.MODIFY == mode_) {
				editView.setTitle(getWord("C00977"));
				editView.ctrlCurrencyCode.setEditable(false);
				editView.setEditMode();
				// 複写
			} else {
				editView.setTitle(getWord("C01738"));
			}

			editView.ctrlCurrencyCode.setRegex("[A-Z,0-9]");
			editView.ctrlCurrencyCode.setValue(bean.getCode());
			editView.ctrlCurrencyName.setValue(bean.getName());
			editView.ctrlCurrencyNames.setValue(bean.getNames());
			editView.ctrlCurrencyNamek.setValue(bean.getNamek());
			editView.ctrlCurrencyRate.setNumber(bean.getRatePow());
			editView.ctrlCurrencyDecimalPoint.setNumber(bean.getDecimalPoint());
			editView.dtBeginDate.setValue(bean.getDateFrom());
			editView.dtEndDate.setValue(bean.getDateTo());
		}
	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {
			// 入力チェックを行う。
			if (!isInputRight()) {
				return;
			}

			// 入力された通貨情報を取得
			Currency currency = getInputedCurrency();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", currency);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(currency));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", currency);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(currency));

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
	 * @return DepartmentManager
	 */
	protected Class getModelClass() {
		return CurrencyManager.class;
	}

	/**
	 * 編集画面で入力された通貨を返す
	 * 
	 * @return 編集画面で入力された通貨
	 */
	protected Currency getInputedCurrency() {

		Currency currency = createCurrency();
		currency.setCompanyCode(getCompany().getCode());
		currency.setCode(editView.ctrlCurrencyCode.getValue());
		currency.setName(editView.ctrlCurrencyName.getValue());
		currency.setNames(editView.ctrlCurrencyNames.getValue());
		currency.setNamek(editView.ctrlCurrencyNamek.getValue());
		currency.setRatePow(editView.ctrlCurrencyRate.getIntValue());
		currency.setDecimalPoint(editView.ctrlCurrencyDecimalPoint.getIntValue());
		currency.setDateFrom(editView.dtBeginDate.getValue());
		currency.setDateTo(editView.dtEndDate.getValue());

		return currency;

	}

	/**
	 * @return 通貨
	 */
	protected Currency createCurrency() {
		return new Currency();
	}

	/**
	 * 通貨情報を一覧に表示する形式に変換し返す
	 * 
	 * @param currency 通貨情報
	 * @return 一覧に表示する形式に変換された通貨情報
	 */
	protected List<Object> getRowData(Currency currency) {
		List<Object> list = new ArrayList<Object>();

		list.add(currency.getCode()); // 通貨コード
		list.add(currency.getName()); // 通貨名称
		list.add(currency.getNames()); // 通貨略称
		list.add(currency.getNamek()); // 通貨検索名称
		list.add(NumberFormatUtil.formatNumber(currency.getRatePow())); // レート係数
		list.add(NumberFormatUtil.formatNumber(currency.getDecimalPoint())); // 小数点以下桁数
		list.add(DateUtil.toYMDString(currency.getDateFrom())); // 開始年月日
		list.add(DateUtil.toYMDString(currency.getDateTo())); // 終了年月日

		return list;
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param mainEnabled enabled
	 */
	protected void setMainButtonEnabled(boolean mainEnabled) {
		mainView.btnModify.setEnabled(mainEnabled);
		mainView.btnCopy.setEnabled(mainEnabled);
		mainView.btnDelete.setEnabled(mainEnabled);
	}

	/**
	 * 検索条件に該当する通貨のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する通貨のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Currency> getCurrency(CurrencySearchCondition condition) throws Exception {

		List<Currency> list = (List<Currency>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * 指示画面で入力された通貨の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected CurrencySearchCondition getSearchCondition() {

		CurrencySearchCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.currencyRange.getCodeFrom());
		condition.setCodeTo(mainView.currencyRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * @return 検索条件
	 */
	protected CurrencySearchCondition createCondition() {
		return new CurrencySearchCondition();
	}

	/**
	 * 一覧で選択されている通貨を返す
	 * 
	 * @return 一覧で選択されている通貨
	 * @throws Exception
	 */
	protected Currency getSelectedCurrency() throws Exception {

		CurrencySearchCondition condition = createCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0300CurrencyMasterPanel.SC.code));

		List<Currency> list = getCurrency(condition);

		if (list == null || list.isEmpty()) {
			showMessage("I00193");// 選択されたデータは他ユーザーにより削除されました。
			return null;
		}
		return list.get(0);

	}

	/**
	 * 指定の通貨を削除する
	 * 
	 * @param currency 通貨
	 * @throws TException
	 */
	protected void deleteCurrency(Currency currency) throws TException {
		request(getModelClass(), "delete", currency);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// 通貨コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCurrencyCode.getValue())) {
			showMessage(editView, "I00037", "C00665"); // 通貨コードを入力してください。
			editView.ctrlCurrencyCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一通貨が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			CurrencySearchCondition condition = createCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlCurrencyCode.getValue());

			List<Currency> list = getCurrency(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00371"); // 指定の通貨は既に存在します。
				editView.ctrlCurrencyCode.requestTextFocus();
				return false;
			}
		}

		// 通貨名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCurrencyName.getValue())) {
			showMessage(editView, "I00037", "C00880"); // 通貨名称を入力してください。
			editView.ctrlCurrencyName.requestTextFocus();
			return false;
		}

		// 通貨略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCurrencyNames.getValue())) {
			showMessage(editView, "I00037", "C00881"); // 通貨略称を入力してください。
			editView.ctrlCurrencyNames.requestTextFocus();
			return false;
		}

		// 通貨検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCurrencyNamek.getValue())) {
			showMessage(editView, "I00037", "C00882"); // 通貨検索名称を入力してください。
			editView.ctrlCurrencyNamek.requestTextFocus();
			return false;
		}

		// レート係数が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCurrencyRate.getValue())) {
			showMessage(editView, "I00037", "C00896"); // レート係数を入力してください。
			editView.ctrlCurrencyRate.requestTextFocus();
			return false;
		}

		// 小数点以下桁数が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCurrencyDecimalPoint.getValue())) {
			showMessage(editView, "I00037", "C00884"); // 小数点以下桁数を入力してください。
			editView.ctrlCurrencyDecimalPoint.requestTextFocus();
			return false;
		}

		// 小数点以下桁数に0～4以外が入力された場合エラー
		if (editView.ctrlCurrencyDecimalPoint.isEditable() && editView.ctrlCurrencyDecimalPoint.getIntValue() >= 5
				|| (editView.ctrlCurrencyDecimalPoint.isEditable()
						&& editView.ctrlCurrencyDecimalPoint.getIntValue() <= -1)) {
			showMessage(editView, "I00247", "C00884", "0", "4"); // 小数点以下桁数は0～4の範囲で指定してください。
			editView.ctrlCurrencyDecimalPoint.requestTextFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055"); // 開始年月日を入力してください。
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261"); // 終了年月日を入力してください。
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}
		return true;

	}
}

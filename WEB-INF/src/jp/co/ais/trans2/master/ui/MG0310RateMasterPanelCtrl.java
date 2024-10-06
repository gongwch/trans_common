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
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * レートマスタのコントローラー
 * 
 * @author AIS
 */
public class MG0310RateMasterPanelCtrl extends TController {

	/**
	 * 指示画面
	 */
	protected MG0310RateMasterPanel mainView = null;

	/**
	 * 編集画面
	 */
	protected MG0310RateMasterDialog editView = null;

	/**
	 * 操作モード。現在操作中のモードを把握するために使用する。
	 */
	protected Mode mode = null;

	/**
	 * 操作モード。
	 * 
	 * @author AIS
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
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0310RateMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
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
	 * 指示画面を初期化する
	 */
	protected void initMainView() {

		// メインボタンを押下不可能にする
		setMainButtonEnabled(false);

		// 社定レート、決算日レート,、社定レート（機能通貨）、決算日レート（機能通貨）すべてにチェック
		mainView.chkCompanyRate.setSelected(true);
		mainView.chkSettlementRate.setSelected(true);
		mainView.chkFuncCompanyRate.setSelected(true);
		mainView.chkFuncSettlementRate.setSelected(true);

		// 適用開始日付は空白
		mainView.dateFrom.setValue(null);
		mainView.dateTo.setValue(null);

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

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 入力チェック
			if (!isInputCorrectWhenSearch()) {
				return;
			}

			// 検索条件を取得
			RateSearchCondition condition = getSearchCondition();
			List<Rate> list = getList(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (Rate rate : list) {
				mainView.tbl.addRow(getRowData(rate));
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

			// 編集対象のデータを取得する
			Rate rate = getSelectedRate();

			// 編集画面を生成し、編集対象のデータをセットする
			createEditView();
			initEditView(Mode.MODIFY, rate);

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

			// 複写対象のレートを取得する
			Rate rate = getSelectedRate();

			// 複写画面を生成し、複写対象のデータをセットする
			createEditView();
			initEditView(Mode.COPY, rate);

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

			// 確認メッセージ
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象のデータを取得する
			Rate rate = getSelectedRate();

			// 削除する
			doDelete(rate);

			// 削除した行を一覧から削除
			mainView.tbl.removeSelectedRow();

			// 削除した結果0件になったらメインボタンを押下不可能にする
			if (mainView.tbl.getRowCount() == 0) {
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

		if (mainView.tbl.getSelectedRow() == -1) {
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

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			RateSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			// 通貨レートマスタ
			printer.preview(data, getWord("C11158") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0310RateMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param editMode 操作モード。
	 * @param rate データ。修正、複写の場合は当該データを編集画面にセットする。
	 */
	protected void initEditView(Mode editMode, Rate rate) {

		this.mode = editMode;

		// 新規の場合
		if (Mode.NEW == editMode) {

			// 新規画面
			editView.setTitle(getWord("C01698"));
			return;

			// 編集、複写の場合
		} else if (Mode.MODIFY == editMode || Mode.COPY == editMode) {

			// 編集
			if (Mode.MODIFY == editMode) {
				// 編集画面
				editView.setTitle(getWord("C00977"));
				editView.cboRateType.setEnabled(false);
				editView.ctrlCurrency.setEditable(false);
				editView.dtBeginDate.setEditable(false);
				editView.setEditMode();
				// 複写
			} else {
				// 複写画面
				editView.setTitle(getWord("C01738"));
			}

			if (RateType.COMPANY == rate.getRateType()) {
				editView.cboRateType.setSelectedIndex(0);
			} else if (RateType.SETTLEMENT == rate.getRateType()) {
				editView.cboRateType.setSelectedIndex(1);
			} else if (RateType.FNC_COMPANY == rate.getRateType()) {
				editView.cboRateType.setSelectedIndex(2);
			} else if (RateType.FNC_SETTLEMENT == rate.getRateType()) {
				editView.cboRateType.setSelectedIndex(3);
			}
			editView.ctrlCurrency.setEntity(rate.getCurrency());
			editView.dtBeginDate.setValue(rate.getTermFrom());
			editView.ctrlRate.setNumber(rate.getCurrencyRate());

		}

	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 入力チェックを行う。
			if (!isInputCorrect()) {
				return;
			}

			// 入力されたデータを取得
			Rate rate = getInputtedRate();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", rate);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(rate));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", rate);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(rate));

			}

			// 編集画面を閉じる
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面で入力されたデータを返す
	 * 
	 * @return 編集画面で入力されたデータ
	 */
	protected Rate getInputtedRate() {

		Rate rate = new Rate();

		rate.setCompanyCode(getCompanyCode());
		rate.setRateType(getSelectedRateType());
		rate.setCurrency(editView.ctrlCurrency.getEntity());
		rate.setTermFrom(editView.dtBeginDate.getValue());
		rate.setCurrencyRate(editView.ctrlRate.getBigDecimal());

		return rate;
	}

	/**
	 * 編集画面[戻る]ボタン押下
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * 指示画面で入力されたデータの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected RateSearchCondition getSearchCondition() {

		RateSearchCondition condition = new RateSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCurrencyCodeFrom(mainView.ctrlCurrencyReferenceRange.getCodeFrom());
		condition.setCurrencyCodeTo(mainView.ctrlCurrencyReferenceRange.getCodeTo());
		condition.setCompanyRate(mainView.chkCompanyRate.isSelected());
		condition.setSettlementRate(mainView.chkSettlementRate.isSelected());
		condition.setFuncCompanyRate(mainView.chkFuncCompanyRate.isSelected());
		condition.setFuncSettlementRate(mainView.chkFuncSettlementRate.isSelected());
		condition.setDateFrom(mainView.dateFrom.getValue());
		condition.setDateTo(mainView.dateTo.getValue());

		return condition;

	}

	/**
	 * モデルを返す
	 * 
	 * @return モデル
	 */
	protected Class getModelClass() {
		return RateManager.class;
	}

	/**
	 * エンティティを一覧に表示する形式に変換し返す
	 * 
	 * @param rate データ
	 * @return 一覧に表示する形式に変換されたデータ
	 */
	protected List<Object> getRowData(Rate rate) {

		List<Object> list = new ArrayList<Object>();
		list.add(getWord(RateType.getName(rate.getRateType())));
		list.add(rate.getCurrency().getCode());
		list.add(DateUtil.toYMDString(rate.getTermFrom()));
		list.add(NumberFormatUtil.formatNumber(rate.getCurrencyRate(), RateFormat.getRateDecimalPoint()));
		list.add(rate);

		return list;

	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * 検索条件に該当するリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Rate> getList(RateSearchCondition condition) throws Exception {

		List<Rate> list = (List<Rate>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * 一覧で選択されているデータを返す
	 * 
	 * @return 一覧で選択されているデータ
	 * @throws Exception
	 */
	protected Rate getSelectedRate() throws Exception {

		Rate rate = (Rate) mainView.tbl.getSelectedRowValueAt(MG0310RateMasterPanel.SC.entity);

		RateSearchCondition condition = new RateSearchCondition();
		condition.setRateType(rate.getRateType());
		condition.setCompanyCode(rate.getCompanyCode());
		condition.setCurrencyCode(rate.getCurrency().getCode());
		condition.setTermFrom(rate.getTermFrom());

		List<Rate> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			throw new TException(getMessage("I00193"));
		}
		return list.get(0);

	}

	/**
	 * 指定の行データを削除する
	 * 
	 * @param rate データ
	 * @throws Exception
	 */
	protected void doDelete(Rate rate) throws Exception {
		request(getModelClass(), "delete", rate);
	}

	/**
	 * 検索時の入力チェック
	 * 
	 * @return 検索時の入力チェック。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isInputCorrectWhenSearch() {

		// 開始コード<=終了コードにしてください。
		if (Util.isSmallerThen(mainView.ctrlCurrencyReferenceRange.getCodeFrom(),
			mainView.ctrlCurrencyReferenceRange.getCodeTo()) == false) {
			showMessage(editView, "I00068");
			mainView.ctrlCurrencyReferenceRange.getFieldFrom().requestFocus();
			return false;
		}

		// レート区分未選択の場合エラー
		if (!mainView.chkCompanyRate.isSelected() && !mainView.chkSettlementRate.isSelected()
			&& !mainView.chkFuncCompanyRate.isSelected() && !mainView.chkFuncSettlementRate.isSelected()) {
			// レート区分を選択してください。
			showMessage(editView, "I00096", "C00883");
			mainView.chkCompanyRate.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// 通貨が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCurrency.getEntity())) {
			// 通貨を入力してください。
			showMessage(editView, "I00037", "C00371");
			editView.ctrlCurrency.requestTextFocus();
			return false;
		}

		// 適用開始日付が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			// 適用開始日を入力してください。
			showMessage(editView, "I00037", "C03741");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// レートが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlRate.getValue())) {
			// レートを入力してください。
			showMessage(editView, "I00037", "C00556");
			editView.ctrlRate.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一会社コード&&同一レート区分&&同一通貨コード&&同一適用開始日付が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {

			RateSearchCondition condition = new RateSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setRateType(getSelectedRateType());
			condition.setCurrencyCode(editView.ctrlCurrency.getCode());
			condition.setTermFrom(editView.dtBeginDate.getValue());

			List<Rate> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				// 指定のデータは既に存在します。
				showMessage(editView, "I00262");
				editView.cboRateType.requestTextFocus();
				return false;
			}
		}

		return true;

	}

	/**
	 * 選択されたRateTypeを返す
	 * 
	 * @return 選択されたRateType
	 */
	protected RateType getSelectedRateType() {

		int selectedIndex = editView.cboRateType.getSelectedIndex();
		if (selectedIndex == 0) {
			return RateType.COMPANY;
		} else if (selectedIndex == 1) {
			return RateType.SETTLEMENT;
		} else if (selectedIndex == 2) {
			return RateType.FNC_COMPANY;
		} else if (selectedIndex == 3) {
			return RateType.FNC_SETTLEMENT;
		}
		return null;
	}
}

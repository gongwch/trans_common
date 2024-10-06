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
import jp.co.ais.trans2.model.item.summary.*;

/**
 * MG0070ItemSummaryMaster - 科目集計マスタ - Controller Class
 * 
 * @author AIS
 */
public class MG0070ItemSummaryMasterPanelCtrl extends TController {

	/** 新規画面 */
	protected MG0070ItemSummaryMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0070ItemSummaryMasterDialog editView = null;

	/** 現在操作中のモードを把握するために使用する */
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
		mainView = new MG0070ItemSummaryMasterPanel();
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

			// 編集編集画面初期化
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
			ItemSummarySearchCondition condition = getSearchCondition();

			// 開始 <= 終了 チェック
			if (!Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo())) {
				showMessage(mainView, "I00068");
				mainView.ctrlAccSumRef.requestFocus();
				return;
			}

			// 一覧をクリア
			mainView.tbl.removeRow();

			// メインボタン制御
			setMainButtonEnabled(false);

			// データ取得
			List<ItemSummary> list = getList(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (ItemSummary bean : list) {
				mainView.tbl.addRow(getRowData(bean));
			}

			// メインボタン制御
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

			// 編集対象のデータ取得
			ItemSummary bean = getSelected();

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

			// 複写対象のデータ取得
			ItemSummary bean = getSelected();

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

			// 確認メッセージ表示
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象のデータ取得
			ItemSummary bean = getSelected();

			// 削除実行
			doDelete(bean);

			// 削除した行を一覧から削除
			mainView.tbl.removeSelectedRow();

			// 削除した後、一覧のレコードが0件の場合、メインボタン制御
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
	 * [エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {
		try {

			// 開始 <= 終了 チェック
			if (Util.isSmallerThen(mainView.ctrlAccSumRef.getCodeFrom(), mainView.ctrlAccSumRef.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlAccSumRef.requestFocus();
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// 検索条件取得
			ItemSummarySearchCondition condition = getSearchCondition();

			// エクセルデータの取得
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			// データが無かった場合、エラーメッセージ出力
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// エクセルタイトルセット
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02019") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 編集画面作成
	 */
	protected void createEditView() {
		editView = new MG0070ItemSummaryMasterDialog(mainView.getParentFrame(), true);
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
			ItemSummary bean = getInputtedAccountSummary();

			// 新規登録の場合
			switch (mode) {
				case NEW:
				case COPY:

					// 新規登録 - 複写登録
					request(getModelClass(), "entry", bean);

					// 追加分を一覧に反映
					mainView.tbl.addRow(getRowData(bean));

					// メインボタン制御
					setMainButtonEnabled(true);

					break;

				case MODIFY:

					// 修正
					request(getModelClass(), "modify", bean);

					// 修正分を一覧に反映
					mainView.tbl.modifySelectedRow(getRowData(bean));

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
	protected void initEditView(Mode editMode, ItemSummary bean) {

		editView.ctrlSumCode.getSearchCondition().setTitleItem(true);
		editView.ctrlSumCode.getSearchCondition().setSumItem(true);
		editView.ctrlKmkCode.getSearchCondition().setSumItem(true);
		editView.ctrlKmkCode.getSearchCondition().setTitleItem(true);

		this.mode = editMode;

		switch (editMode) {
			case NEW:
				// 編集画面タイトルセット
				editView.setTitle(getWord("C01698"));
				break;
			case MODIFY:
			case COPY:
				// 編集
				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));
					editView.ctrlKmtCode.setEditable(false);
					editView.ctrlKmkCode.setEditable(false);
					editView.setEditMode();

					// 複写
				} else {
					editView.setTitle(getWord("C01738"));
				}

				editView.ctrlKmtCode.code.setValue(bean.getKmtCode());
				editView.ctrlKmtCode.name.setValue(bean.getKmtName());
				editView.ctrlKmkCode.code.setValue(bean.getKmkCode());
				editView.ctrlKmkCode.name.setValue(bean.getKmkName());
				editView.ctrlKokName.setValue(bean.getKokName());
				editView.ctrlSumCode.code.setValue(bean.getSumCode());
				editView.ctrlSumCode.name.setValue(bean.getSumName());
				editView.ctrlOdr.setValue(bean.getOdr());
				if (bean.getHyjKbn() == 1) {
					editView.ctrlHyjKbnVisible.setSelected(true);
				} else {
					editView.ctrlHyjKbnInvisible.setSelected(true);
				}
				editView.ctrlBeginDate.setValue(bean.getDateFrom());
				editView.ctrlEndDate.setValue(bean.getDateTo());
				break;
		}
	}

	/**
	 * 編集画面の入力値取得
	 * 
	 * @return AccountSummary
	 */
	protected ItemSummary getInputtedAccountSummary() {

		ItemSummary bean = new ItemSummary();

		bean.setKmtCode(editView.ctrlKmtCode.getCode());
		bean.setKmtName(editView.ctrlKmtCode.getNames());
		bean.setKmkCode(editView.ctrlKmkCode.getCode());
		bean.setKmkName(editView.ctrlKmkCode.getNames());
		bean.setKokName(editView.ctrlKokName.getValue());
		bean.setSumCode(editView.ctrlSumCode.getCode());
		bean.setSumName(editView.ctrlSumCode.getNames());
		bean.setOdr(editView.ctrlOdr.getValue());

		if (editView.ctrlHyjKbnVisible.isSelected()) {
			bean.setHyjKbn(1);
		} else {
			bean.setHyjKbn(0);
		}

		bean.setDateFrom(editView.ctrlBeginDate.getValue());
		bean.setDateTo(editView.ctrlEndDate.getValue());

		return bean;
	}

	/**
	 * 検索条件取得
	 * 
	 * @return AccountSummarySearchCondition 検索条件
	 */
	protected ItemSummarySearchCondition getSearchCondition() {

		ItemSummarySearchCondition condition = new ItemSummarySearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setKmtCode(mainView.ctrlAccSumRef.getCodeUp());
		condition.setCodeFrom(mainView.ctrlAccSumRef.getCodeFrom());
		condition.setCodeTo(mainView.ctrlAccSumRef.getCodeTo());

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
	protected List<Object> getRowData(ItemSummary bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getKmtCode());
		list.add(bean.getKmtName());
		list.add(bean.getKmkCode());
		list.add(bean.getKmkName());
		list.add(bean.getKokName());
		list.add(bean.getSumCode());
		list.add(bean.getSumName());
		list.add(bean.getOdr());
		list.add(bean.getHyjKbn() == 1 ? getWord("C00432") : getWord("C01297"));
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
	protected ItemSummary getSelected() throws Exception {

		ItemSummary bean = (ItemSummary) mainView.tbl.getSelectedRowValueAt(MG0070ItemSummaryMasterPanel.SC.ENTITY);

		ItemSummarySearchCondition condition = new ItemSummarySearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setKmtCode(bean.getKmtCode());
		condition.setKmkCode(bean.getKmkCode());

		List<ItemSummary> list = getList(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * 検索条件に該当するデータを返す
	 * 
	 * @param condition
	 * @return list
	 * @throws Exception
	 */
	protected List<ItemSummary> getList(ItemSummarySearchCondition condition) throws Exception {
		List<ItemSummary> list = (List<ItemSummary>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * 指定行データを削除する
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(ItemSummary bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * インターフェースを返す
	 * 
	 * @return インターフェース
	 */
	protected Class getModelClass() {
		return ItemSummaryManager.class;
	}

	/**
	 * 編集画面入力値の妥当性をチェック *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */
	protected boolean isInputCorrect() throws Exception {
		// 科目体系コード未入力
		if (Util.isNullOrEmpty(editView.ctrlKmtCode.getCode())) {
			showMessage(editView, "I00037", "C00617");
			editView.ctrlKmtCode.requestTextFocus();
			return false;
		}
		// 科目コード未入力
		if (Util.isNullOrEmpty(editView.ctrlKmkCode.getCode())) {
			showMessage(editView, "I00037", "C00572");
			editView.ctrlKmkCode.requestTextFocus();
			return false;
		}
		// 公表科目名称未入力
		if (Util.isNullOrEmpty(editView.ctrlKokName.getValue())) {
			showMessage(editView, "I00037", "C00624");
			editView.ctrlKokName.requestTextFocus();
			return false;
		}

		// 出力順序未入力
		if (Util.isNullOrEmpty(editView.ctrlOdr.getValue())) {
			showMessage(editView, "I00037", "C00776");
			editView.ctrlOdr.requestTextFocus();
			return false;
		}
		// 開始年月日未入力
		if (Util.isNullOrEmpty(editView.ctrlBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.ctrlBeginDate.requestTextFocus();
			return false;
		}
		//集計相手先科目＝科目かどうか、チェック
		if(Util.equals(editView.ctrlSumCode.getCode(), editView.ctrlKmkCode.getCode())) {
			showMessage(editView, "I00893","C00625","C00572");
			editView.ctrlSumCode.requestTextFocus();
			return false;
		}
		// 終了年月日未入力
		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");
			editView.ctrlEndDate.requestTextFocus();
			return false;
		}
		// 開始 <= 終了
		if (Util.isSmallerThenByYMD(editView.ctrlEndDate.getValue(), editView.ctrlBeginDate.getValue())) {
			showMessage(editView, "I00068");
			editView.ctrlBeginDate.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一会社コード&&同一科目体系コード&&同一科目コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			ItemSummarySearchCondition condition = new ItemSummarySearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setKmtCode(editView.ctrlKmtCode.getCode());
			condition.setKmkCode(editView.ctrlKmkCode.getCode());

			List<ItemSummary> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "C00077");
				editView.ctrlKmtCode.requestTextFocus();
				return false;
			}
		}

		return true;
	}
}
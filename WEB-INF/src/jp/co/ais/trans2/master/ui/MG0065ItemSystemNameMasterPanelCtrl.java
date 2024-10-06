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
import jp.co.ais.trans2.model.item.*;

/**
 * MG0065ItemSystemNameMaster - 科目体系名称マスタ - Main Controller
 * 
 * @author AIS
 */
public class MG0065ItemSystemNameMasterPanelCtrl extends TController {

	/** 新規画面 */
	protected MG0065ItemSystemNameMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0065ItemSystemNameMasterDialog editView = null;

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
		mainView = new MG0065ItemSystemNameMasterPanel();
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
			ItemOrganizationSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlItemOrgRan.requestFocus();
				return;
			}

			// 一覧をクリア
			mainView.tbList.removeRow();

			// メインボタン制御
			setMainButtonEnabled(false);

			// データ取得
			List<ItemOrganization> list = getList(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (ItemOrganization item : list) {
				mainView.tbList.addRow(getRowData(item));
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
			ItemOrganization bean = getSelected();

			// 戻り値を判定
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
			ItemOrganization bean = getSelected();

			// 戻り値を判定
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
			ItemOrganization bean = getSelected();

			// 戻り値を判定
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

			// 検索条件の取得
			ItemOrganizationSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlItemOrgRan.requestFocus();
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			byte[] data = (byte[]) request(getModelClass(), "getExcelItemOrganization", condition);

			// データが無かった場合、エラーメッセージ出力
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// エクセルタイトルセット
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00618") + getWord("C00500") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面作成
	 */
	protected void createEditView() {
		editView = new MG0065ItemSystemNameMasterDialog(mainView.getParentFrame(), true);
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

			// 入力チェックを行う。
			if (!isInputCorrect()) {
				return;
			}

			// 入力された科目体系名称マスタ情報を取得
			ItemOrganization bean = getInputtedItemOrganization();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entryItemOrganization", bean);

				// 追加分を一覧に反映する
				mainView.tbList.addRow(getRowData(bean));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modifyItemOrganization", bean);

				// 修正内容を一覧に反映する
				mainView.tbList.modifySelectedRow(getRowData(bean));

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
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param bean 修正、複写の場合は当該情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, ItemOrganization bean) {

		this.mode = mode_;

		// 新規の場合
		if (Mode.NEW == mode_) {
			// 新規画面
			editView.setTitle(getWord("C01698"));
			return;
		}

		// 編集
		if (Mode.MODIFY == mode_) {
			// 編集画面
			editView.setTitle(getWord("C00977"));
			editView.ctrlCode.setEditable(false);
			editView.setEditMode();
		}
		// 複写
		else {
			// 複写画面
			editView.setTitle(getWord("C01738"));
		}

		// 編集画面の初期値
		editView.ctrlCode.setValue(bean.getCode());
		editView.ctrlName.setValue(bean.getName());
		editView.ctrlNameS.setValue(bean.getNames());
		editView.ctrlNameK.setValue(bean.getNamek());

	}

	/**
	 * 編集画面の入力値取得
	 * 
	 * @return bean
	 */
	protected ItemOrganization getInputtedItemOrganization() {

		ItemOrganization bean = new ItemOrganization();

		bean.setCode(editView.ctrlCode.getValue());
		bean.setName(editView.ctrlName.getValue());
		bean.setNames(editView.ctrlNameS.getValue());
		bean.setNamek(editView.ctrlNameK.getValue());

		return bean;
	}

	/**
	 * 検索条件取得
	 * 
	 * @return ItemOrganizationSearchCondition 検索条件
	 */
	protected ItemOrganizationSearchCondition getSearchCondition() {

		ItemOrganizationSearchCondition condition = new ItemOrganizationSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlItemOrgRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlItemOrgRan.getCodeTo());

		return condition;
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(ItemOrganization bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean);
		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());

		return list;
	}

	/**
	 * 一覧で選択したデータを取得
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected ItemOrganization getSelected() throws Exception {

		ItemOrganization bean = (ItemOrganization) mainView.tbList
			.getSelectedRowValueAt(MG0065ItemSystemNameMasterPanel.SC.ENTITY);

		ItemOrganizationSearchCondition condition = new ItemOrganizationSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<ItemOrganization> list = getList(condition);

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
	protected List<ItemOrganization> getList(ItemOrganizationSearchCondition condition) throws Exception {
		List<ItemOrganization> list = (List<ItemOrganization>) request(getModelClass(), "getItemOrganization",
			condition);
		return list;
	}

	/**
	 * 指定行データを削除する
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(ItemOrganization bean) throws Exception {
		request(getModelClass(), "deleteItemOrganization", bean);
	}

	/**
	 * @return モデル
	 */
	protected Class getModelClass() {
		return ItemManager.class;
	}

	/**
	 * 編集画面入力値の妥当性をチェック *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C00617");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C00618");
			editView.ctrlName.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlNameS.getValue())) {
			showMessage(editView, "I00037", "C00619");
			editView.ctrlNameS.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlNameK.getValue())) {
			showMessage(editView, "I00037", "C00620");
			editView.ctrlNameK.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一会社コード&&同一科目体系コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			ItemOrganizationSearchCondition condition = new ItemOrganizationSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<ItemOrganization> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00617");
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}
}
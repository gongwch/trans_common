package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 会社間付替のコントローラクラス。
 * 
 * @author AIS
 */
public class MG0350InterCompanyTransferMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0350InterCompanyTransferMasterPanel mainView = null;

	/** 指示画面 */
	protected MG0350InterCompanyTransferMasterDialog editView = null;

	/**
	 * 操作モード。現在操作中のモードを把握するために使用する。
	 */
	protected Mode mode = null;

	/**
	 * 操作モード
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

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面のファクトリ
	 */
	protected void createMainView() {

		mainView = new MG0350InterCompanyTransferMasterPanel();
		addMainViewEvent();

	}

	/**
	 * パネル取得
	 * 
	 * @return パネル
	 */
	@Override
	public TPanelBusiness getPanel() {
		return this.mainView;
	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {
		// メインボタンを押下不可能にする
		setMainButtonEnabled(false);
	}

	/**
	 * 指示画面のイベント定義
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
			InterCompanyTransferSearchCondition condition = getSearchCondition();
			List<InterCompanyTransfer> list = getList(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (InterCompanyTransfer bean : list) {
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

			// 編集対象のデータを取得する
			InterCompanyTransfer bean = getSelectedRowData();

			// 編集画面を生成し、編集対象のデータをセットする
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

			// 複写対象のデータを取得する
			InterCompanyTransfer bean = getSelectedRowData();

			// 複写画面を生成し、複写対象のデータをセットする
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

			// 確認メッセージ
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象のデータを取得する
			InterCompanyTransfer bean = getSelectedRowData();

			// 削除する
			doDelete(bean);

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
	 * [エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {

		try {

			// 入力チェック
			if (!isInputCorrectWhenSearch()) {
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			InterCompanyTransferSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			// 会社間付替マスタ
			printer.preview(data, getWord("C02341") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 検索時入力した値が妥当かをチェックする
	 * 
	 * @return 検索時入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isInputCorrectWhenSearch() {

		// 開始コード<=終了コードにしてください。
		if (Util.isSmallerThen(mainView.ctrlRange.getCodeFrom(), mainView.ctrlRange.getCodeTo()) == false) {
			showMessage(editView, "I00068");
			mainView.ctrlRange.getFieldFrom().requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0350InterCompanyTransferMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

		// 科目連動処理
		editView.ctrlItem.addCallBackListener(new TCallBackListener() {

			@Override
			public boolean afterVerify(boolean flag) {
				if (!flag) {
					return false;
				}
				return changedItem();
			}
		});

	}

	/**
	 * 科目変更処理
	 * 
	 * @return false:NG
	 */
	protected boolean changedItem() {
		Item item = editView.ctrlItem.getEntity();

		if (item == null) {
			clearInputForItem();
			return true;
		}

		// 補助、内訳がある場合は、そっちを反映
		item = item.getPreferedItem();

		// 取引先
		CustomerType customerType = item.getClientType();
		boolean needCustomer = customerType != null && customerType != CustomerType.NONE;

		editView.ctrlCustomer.setEditable(needCustomer);
		editView.ctrlCustomer.getSearchCondition().setType(customerType);

		if (needCustomer) {
			editView.ctrlCustomer.refleshEntity();
		} else {
			editView.ctrlCustomer.clear();
		}

		return true;
	}

	/**
	 * 科目関連入力部のみ初期状態
	 */
	public void clearInputForItem() {
		// クリア
		editView.ctrlCustomer.clear();
		editView.ctrlCustomer.setEditable(false);
	}

	/**
	 * /** 編集画面を初期化する
	 * 
	 * @param editMode 操作モード。
	 * @param bean データ。修正、複写の場合は当該データを編集画面にセットする。
	 */
	protected void initEditView(Mode editMode, InterCompanyTransfer bean) {

		this.mode = editMode;

		// 計上会社 条件
		editView.ctrlCompany.getSearchCondition().setGroupAccountOnly(); // グループ会社のみ

		// 科目連動更新
		clearInputForItem();

		// 新規の場合
		if (Mode.NEW == editMode) {
			// 新規画面
			editView.setTitle(getWord("C01698"));

			// 編集、複写の場合
		} else if (Mode.MODIFY == editMode || Mode.COPY == editMode) {

			// 編集
			if (Mode.MODIFY == editMode) {
				// 編集画面
				editView.setTitle(getWord("C00977"));
				editView.ctrlCompany.setEditable(false);
				editView.setEditMode();
				// 複写
			} else {
				// 複写画面
				editView.setTitle(getWord("C01738"));
			}

			// データを貼り付ける
			editView.ctrlCompany.setEntity(bean.getTransferCompany());
			editView.ctrlDepartment.setEntity(bean.getDepartment());
			editView.ctrlItem.setEntity(bean.getItem());

			// 科目連動
			changedItem();

			if (editView.ctrlCustomer.isEditable()) {
				editView.ctrlCustomer.setEntity(bean.getCustomer());
			}
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
			InterCompanyTransfer bean = getInputedData();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", bean);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(bean));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", bean);

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
	 * 指示画面で入力されたデータの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected InterCompanyTransferSearchCondition getSearchCondition() {

		InterCompanyTransferSearchCondition condition = new InterCompanyTransferSearchCondition();
		condition.setCompany(getCompany());
		condition.setTransferCompanyFrom(mainView.ctrlRange.getCodeFrom());
		condition.setTransferCompanyTo(mainView.ctrlRange.getCodeTo());

		return condition;

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getModelClass() {
		return InterCompanyTransferManager.class;
	}

	/**
	 * 編集画面で入力されたデータを返す
	 * 
	 * @return 編集画面で入力されたデータ
	 */
	protected InterCompanyTransfer getInputedData() {

		InterCompanyTransfer bean = new InterCompanyTransfer();
		bean.setCompanyCode(TLoginInfo.getCompany().getCode());
		bean.setTransferCompany(editView.ctrlCompany.getEntity());
		bean.setDepartment(editView.ctrlDepartment.getEntity());
		bean.setItem(editView.ctrlItem.getEntity());

		Customer customer = editView.ctrlCustomer.getEntity();
		if (customer == null) {
			customer = new Customer();
		}

		bean.setCustomer(customer);

		return bean;

	}

	/**
	 * エンティティを一覧に表示する形式に変換し返す
	 * 
	 * @param bean データ
	 * @return 一覧に表示する形式に変換されたデータ
	 */
	protected String[] getRowData(InterCompanyTransfer bean) {

		return new String[] { bean.getTransferCompany().getCode(), //
				bean.getTransferCompany().getNames(),//
				bean.getDepartment().getCode(), //
				bean.getDepartment().getNames(),//
				bean.getItem().getCode(),//
				bean.getItem().getNames(), //
				bean.getItem().getSubItemCode(),//
				bean.getItem().getSubItemNames(),//
				bean.getItem().getDetailItemCode(),//
				bean.getItem().getDetailItemNames(), //
				bean.getCustomer().getCode(),//
				bean.getCustomer().getNames() };//

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
	protected List<InterCompanyTransfer> getList(InterCompanyTransferSearchCondition condition) throws Exception {

		List<InterCompanyTransfer> list = (List<InterCompanyTransfer>) request(getModelClass(), "get", condition);
		return list;

	}

	/**
	 * 一覧で選択されているデータを返す
	 * 
	 * @return 一覧で選択されているデータ
	 * @throws Exception
	 */
	protected InterCompanyTransfer getSelectedRowData() throws Exception {

		String transferCompanyCode = (String) mainView.tbl
			.getSelectedRowValueAt(MG0350InterCompanyTransferMasterPanel.SC.KTK_KAI_CODE);

		InterCompanyTransfer bean = (InterCompanyTransfer) request(getModelClass(), "getOne", getCompanyCode(),
			transferCompanyCode);

		if (bean == null) {
			// 選択されたデータは他ユーザーにより削除されました。
			throw new TException(getMessage("I00193"));
		}
		return bean;

	}

	/**
	 * 指定のデータを削除する
	 * 
	 * @param bean マスタ
	 * @throws Exception
	 */
	protected void doDelete(InterCompanyTransfer bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCompany.getCode())) {
			// 会社コード
			showMessage(editView, "I00037", "C00596");
			editView.ctrlCompany.requestFocus();
			return false;
		}

		if (Mode.NEW == mode || Mode.COPY == mode) {

			// 新規、複写の場合は同一データが既に存在したらエラー
			InterCompanyTransferSearchCondition condition = new InterCompanyTransferSearchCondition();
			condition.setCompany(getCompany());
			condition.setTransferCompanyCode(editView.ctrlCompany.getCode());

			List<InterCompanyTransfer> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				// 付替会社コード
				showMessage(editView, "I00055", "C02050");
				editView.ctrlCompany.requestTextFocus();
				return false;
			}

			// 入力コードとログイン会社コードが同じ場合エラー
			if (editView.ctrlCompany.getCode().equals(getCompanyCode())) {
				// 自社の付替は設定できません。
				showMessage(editView, "I00263");
				editView.ctrlCompany.requestTextFocus();
				return false;
			}
		}

		// 付替計上部門が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartment.getCode())) {
			// 付替計上部門
			showMessage(editView, "I00037", "C00847");
			editView.ctrlDepartment.requestTextFocus();
			return false;
		}

		// 付替科目が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlItem.ctrlItemReference.getCode())) {
			// 付替
			showMessage(editView, "I00037", getWord("C00375") + getCompany().getAccountConfig().getItemName());
			editView.ctrlItem.ctrlItemReference.requestTextFocus();
			return false;
		}

		// 付替補助科目が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlItem.ctrlSubItemReference.getCode())
			&& editView.ctrlItem.ctrlSubItemReference.code.isEditable()) {
			// 付替
			showMessage(editView, "I00037", getWord("C00375") + getCompany().getAccountConfig().getSubItemName());
			editView.ctrlItem.ctrlSubItemReference.requestTextFocus();
			return false;
		}

		// 付替内訳科目が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlItem.ctrlDetailItemReference.getCode())
			&& editView.ctrlItem.ctrlDetailItemReference.code.isEditable()) {
			// 付替
			showMessage(editView, "I00037", getWord("C00375") + getCompany().getAccountConfig().getDetailItemName());
			editView.ctrlItem.ctrlDetailItemReference.requestTextFocus();
			return false;
		}

		// 取引先が未入力の場合エラー
		if (editView.ctrlCustomer.isEditable() && Util.isNullOrEmpty(editView.ctrlCustomer.getCode())) {
			// 取引先
			showMessage(editView, "I00037", "C00408");
			editView.ctrlCustomer.requestTextFocus();
			return false;
		}

		return true;

	}

}

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
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 部門マスタ 画面コントロール
 */
public class MG0431AutomaticJournalizingItemMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0431AutomaticJournalizingItemMasterPanel mainView;

	/** 編集画面 */
	protected MG0431AutomaticJournalizingItemMasterDialog editView = null;

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
		mainView = new MG0431AutomaticJournalizingItemMasterPanel();
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
			if (Util.isSmallerThen(mainView.AutoJnlItemRange.getCodeFrom(), mainView.AutoJnlItemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.AutoJnlItemRange.getFieldFrom().requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 科目制御区分情報を取得
			AutomaticJournalItemSearchCondition condition = getSearchCondition();
			List<AutomaticJournalItem> list = getAutomaticJournalItem(condition);

			// 検索条件に該当する部門が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {

				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 科目制御区分情報を一覧に表示する
			for (AutomaticJournalItem bean : list) {
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

			if (!checkMainView()) {
				return;
			}
			// 編集対象の科目制御区分を取得する
			AutomaticJournalItem bean = getSelectedAutomaticJournalItem();

			// 編集画面を生成し、編集対象の科目制御区分情報をセットする
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

			if (!checkMainView()) {
				return;
			}

			// 複写対象の科目制御区分を取得する
			AutomaticJournalItem bean = getSelectedAutomaticJournalItem();

			// 複写画面を生成し、複写対象の科目制御区分情報をセットする
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

			if (!checkMainView()) {
				return;
			}

			// 削除対象の科目制御区分を取得する
			AutomaticJournalItem bean = getSelectedAutomaticJournalItem();

			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除する
			deleteAutomaticJournalItem(bean);

			// 削除した科目制御区分を一覧から削除
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
			if (Util.isSmallerThen(mainView.AutoJnlItemRange.getCodeFrom(), mainView.AutoJnlItemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.AutoJnlItemRange.getFieldFrom().requestFocus();
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// データ抽出
			AutomaticJournalItemSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00911") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0431AutomaticJournalizingItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param bean 部門。修正、複写の場合は当該部門情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, AutomaticJournalItem bean) {

		AccountConfig ac = getCompany().getAccountConfig();

		// 科目
		editView.ctrlTItemGroup.ctrlItemReference.btn.setText(ac.getItemName());
		editView.ctrlTItemGroup.ctrlSubItemReference.btn.setText(ac.getSubItemName());
		editView.ctrlTItemGroup.ctrlDetailItemReference.btn.setText(ac.getDetailItemName());

		if (!ac.isUseDetailItem()) {
			editView.ctrlTItemGroup.ctrlDetailItemReference.setVisible(false);
		}
		this.mode = mode_;
		// 新規
		switch (mode) {
		// 新規
			case NEW:
				editView.setTitle(getWord("C01698"));

				break;

			case COPY:
			case MODIFY:
				// 編集
				if (Mode.MODIFY == mode_) {
					editView.setTitle(getWord("C00977"));
					editView.ctrlAutoJnlItemCode.setEditable(false);
					editView.setEditMode();
					// 複写
				} else {
					editView.setTitle(getWord("C01738"));
				}

				editView.ctrlTItemGroup.setEntity(bean.getItem());
				editView.ctrlAutoJnlItemCode.setNumber(DecimalUtil.toInt(bean.getKMK_CNT()));
				editView.ctrlAutoJnlItemName.setValue(bean.getKMK_CNT_NAME());
				editView.ctrlDepartment.code.setText(bean.getDEP_CODE());
				editView.ctrlDepartment.name.setText(bean.getDEP_NAMES());
				editView.ctrlTItemGroup.ctrlItemReference.code.setText(bean.getKMK_CODE());
				editView.ctrlTItemGroup.ctrlItemReference.name.setText(bean.getKMK_NAMES());
				if (!Util.isNullOrEmpty(bean.getHKM_CODE())) {
					editView.ctrlTItemGroup.ctrlSubItemReference.code.setText(bean.getHKM_CODE());
					editView.ctrlTItemGroup.ctrlSubItemReference.name.setText(bean.getHKM_NAMES());
					editView.ctrlTItemGroup.ctrlSubItemReference.btn.setEnabled(true);
					editView.ctrlTItemGroup.ctrlSubItemReference.code.setEditable(true);
				}
				if (!Util.isNullOrEmpty(bean.getUKM_CODE())) {
					editView.ctrlTItemGroup.ctrlDetailItemReference.code.setText(bean.getUKM_CODE());
					editView.ctrlTItemGroup.ctrlDetailItemReference.name.setText(bean.getUKM_NAMES());
					editView.ctrlTItemGroup.ctrlDetailItemReference.btn.setEnabled(true);
					editView.ctrlTItemGroup.ctrlDetailItemReference.code.setEditable(true);
				}
				
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

			// 入力された科目制御区分情報を取得
			AutomaticJournalItem bean = getInputedAutomaticJournalItem();

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
	 * Servletクラスを返す
	 * 
	 * @return AutomaticJournalItemManager
	 */
	protected Class getModelClass() {
		return AutomaticJournalItemManager.class;
	}

	/**
	 * 編集画面で入力された科目制御区分を返す
	 * 
	 * @return 編集画面で入力された科目制御区分
	 */
	protected AutomaticJournalItem getInputedAutomaticJournalItem() {

		AutomaticJournalItem bean = createAutomaticJournalItem();

		bean.setKAI_CODE(getCompany().getCode());
		bean.setKMK_CNT(editView.ctrlAutoJnlItemCode.getValue());
		bean.setKMK_CNT_NAME(editView.ctrlAutoJnlItemName.getValue());
		bean.setDEP_CODE(editView.ctrlDepartment.getCode());
		bean.setDEP_NAMES(editView.ctrlDepartment.getNames());
		bean.setKMK_CODE(editView.ctrlTItemGroup.ctrlItemReference.getCode());
		bean.setKMK_NAMES(editView.ctrlTItemGroup.ctrlItemReference.getNames());
		bean.setHKM_CODE(editView.ctrlTItemGroup.ctrlSubItemReference.getCode());
		bean.setHKM_NAMES(editView.ctrlTItemGroup.ctrlSubItemReference.getNames());
		bean.setUKM_CODE(editView.ctrlTItemGroup.ctrlDetailItemReference.getCode());
		bean.setUKM_NAMES(editView.ctrlTItemGroup.ctrlDetailItemReference.getNames());

		return bean;

	}

	/**
	 * @return 科目制御区分
	 */
	protected AutomaticJournalItem createAutomaticJournalItem() {
		return new AutomaticJournalItem();
	}

	/**
	 * 科目制御区分情報を一覧に表示する形式に変換し返す
	 * 
	 * @param bean 科目制御区分情報
	 * @return 一覧に表示する形式に変換された科目制御区分情報
	 */
	protected List<Object> getRowData(AutomaticJournalItem bean) {
		List<Object> list = new ArrayList<Object>();

		list.add(bean.getKMK_CNT()); // 科目制御区分
		list.add(bean.getKMK_CNT_NAME()); // 科目制御区分名称
		list.add(bean.getDEP_CODE()); // 部門コード
		list.add(bean.getDEP_NAMES()); // 部門名称
		list.add(bean.getKMK_CODE()); // 科目コード
		list.add(bean.getKMK_NAMES()); // 科目名称
		list.add(bean.getHKM_CODE()); // 補助コード
		list.add(bean.getHKM_NAMES()); // 補助名称
		list.add(bean.getUKM_CODE()); // 内訳コード
		list.add(bean.getUKM_NAMES()); // 内訳名称

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
	 * 検索条件に該当する科目制御区分のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する科目制御区分のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<AutomaticJournalItem> getAutomaticJournalItem(AutomaticJournalItemSearchCondition condition)
		throws Exception {

		List<AutomaticJournalItem> list = (List<AutomaticJournalItem>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * 指示画面で入力された科目制御区分の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected AutomaticJournalItemSearchCondition getSearchCondition() {

		AutomaticJournalItemSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.AutoJnlItemRange.getCodeFrom());
		condition.setCodeTo(mainView.AutoJnlItemRange.getCodeTo());

		return condition;

	}

	/**
	 * @return 検索条件
	 */
	protected AutomaticJournalItemSearchCondition createCondition() {
		return new AutomaticJournalItemSearchCondition();
	}

	/**
	 * 一覧で選択されている科目制御区分を返す
	 * 
	 * @return 一覧で選択されている科目制御区分
	 * @throws Exception
	 */
	protected AutomaticJournalItem getSelectedAutomaticJournalItem() throws Exception {

		AutomaticJournalItemSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(mainView.tbl
			.getSelectedRowValueAt(MG0431AutomaticJournalizingItemMasterPanel.SC.autojnlitemkbn).toString());

		List<AutomaticJournalItem> list = getAutomaticJournalItem(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193"); // 選択されたデータは他ユーザーにより削除されました。
		}
		return list.get(0);
	}

	/**
	 * 指定の科目制御区分を削除する
	 * 
	 * @param AutomaticJournalItem 科目制御区分
	 * @throws TException
	 */
	protected void deleteAutomaticJournalItem(AutomaticJournalItem AutomaticJournalItem) throws TException {
		request(getModelClass(), "delete", AutomaticJournalItem);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// 科目制御区分が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlAutoJnlItemCode.getValue())) {
			showMessage(editView, "I00037", "C01008"); // 科目制御区分を入力してください。
			editView.ctrlAutoJnlItemCode.requestTextFocus();
			return false;
		}

		// 科目制御区分名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlAutoJnlItemName.getValue())) {
			showMessage(editView, "I00037", "C00518"); // 名称を入力してください。
			editView.ctrlAutoJnlItemName.requestTextFocus();
			return false;
		}

		// 部門が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartment.code.getText())) {
			showMessage(editView, "I00037", "C00698"); //  部門を入力してください。
			editView.ctrlAutoJnlItemName.requestTextFocus();
			return false;
		}

		// 科目が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlTItemGroup.ctrlItemReference.code.getText())) {
			showMessage(editView, "I00037", "C00572"); // 科目コードを入力してください。
			editView.ctrlAutoJnlItemName.requestTextFocus();
			return false;
		}

		// 補助コードが使用可で未入力の場合エラー
		if (editView.ctrlTItemGroup.ctrlSubItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlTItemGroup.ctrlSubItemReference.code.getText())) {
				showMessage(editView, "I00037", "C00602"); // 補助科目コードを入力してください。
				editView.ctrlTItemGroup.ctrlSubItemReference.requestTextFocus();
				return false;
			}
		}
		// 内訳コードが使用可で未入力の場合エラー
		if (editView.ctrlTItemGroup.ctrlDetailItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlTItemGroup.ctrlDetailItemReference.code.getText())) {
				showMessage(editView, "I00037", "C00603"); // 内訳科目コードを入力してください。
				editView.ctrlTItemGroup.ctrlDetailItemReference.requestTextFocus();
				return false;
			}
		}
		
		// 新規、複写の場合は同一科目制御区分が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			AutomaticJournalItemSearchCondition condition = createCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlAutoJnlItemCode.getValue());

			List<AutomaticJournalItem> list = getAutomaticJournalItem(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C01008"); // 指定の部門は既に存在します。
				editView.ctrlAutoJnlItemCode.requestTextFocus();
				return false;
			}
		}

		// 前期繰越利益 = 1 の場合チェック 
		if (editView.ctrlAutoJnlItemCode.getIntValue() == 1) {
			// チェックデータ取得
			AutomaticJournalItem AutomaticJournalItem = new AutomaticJournalItem();
			AutomaticJournalItem.setKMK_CODE(editView.ctrlTItemGroup.ctrlItemReference.getCode());
			int dckbn = (Integer) request(getModelClass(), "check", AutomaticJournalItem);

			// 貸借区分が前期繰越利益の場合、借方だったらエラー
			if (dckbn == 0) {
				showMessage(editView, "I00258", "C01125", editView.ctrlTItemGroup.ctrlItemReference.getNames()); // 前期繰越利益が貸方科目ではありません
				editView.ctrlTItemGroup.ctrlItemReference.requestTextFocus();
				return false;
			}
		}

		return true;

	}
}

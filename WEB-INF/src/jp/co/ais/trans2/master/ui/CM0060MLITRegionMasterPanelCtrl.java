package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.master.ui.CM0060MLITRegionMasterPanel.SC;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * 輸送実績国マスタ画面コントロール
 * 
 * @author AIS
 */
public class CM0060MLITRegionMasterPanelCtrl extends TController {

	/** 操作モード */
	protected enum Mode {
		/** 新規 */
		NEW,
		/** 修正 */
		MODIFY,
		/** 複写 */
		COPY
	}

	/** パネル */
	protected CM0060MLITRegionMasterPanel mainView;

	/** 編集画面 */
	protected CM0060MLITRegionMasterDialog editView;

	/** 修正前のデータ */
	protected YJRegion editBean = null;

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

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
	 * パネル取得
	 * 
	 * @return 輸送実績国マスタパネル
	 */
	@Override
	public TPanelBusiness getPanel() {
		// パネルを返す
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new CM0060MLITRegionMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		mainView.btnNew.addActionListener(new ActionListener() {

			// 新規ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnSearch.addActionListener(new ActionListener() {

			// 検索ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnModify.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnCopy.addActionListener(new ActionListener() {

			// 複写ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnDelete.addActionListener(new ActionListener() {

			// 削除ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnListOutput.addActionListener(new ActionListener() {

			// リスト出力ボタンを押下
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
	 * 新規登録処理
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
	 * 検索処理
	 */
	protected void btnSearch_Click() {

		try {
			// 検索条件チェック
			if (!checkMainView()) {
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// YJRegion情報を取得
			YJRegionSearchCondition condition = getSearchCondition();
			List<YJRegion> list = getList(condition);

			// 検索条件に該当するYJRegionが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView, "I00022");
				return;
			}

			// YJRegion情報を一覧に表示する
			for (YJRegion bean : list) {
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
			if (!checkSelectedRow()) {
				return;
			}

			// 編集対象のYJRegionを取得する
			YJRegion bean = getSelectedRowData();

			// 編集画面を生成し、編集対象のYJRegion情報をセットする
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
			if (!checkSelectedRow()) {
				return;
			}

			// 複写対象のYJRegionを取得する
			YJRegion bean = getSelectedRowData();

			// 編集前データを削除
			editBean = null;

			// 複写画面を生成し、複写対象のYJRegion情報をセットする
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
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new CM0060MLITRegionMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();
	}

	/**
	 * [削除]ボタン押下
	 */
	protected void btnDelete_Click() {

		try {
			if (!checkSelectedRow()) {
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00007")) {
				return;
			}

			// 削除対象のYJRegionを取得する
			YJRegion bean = getSelectedRowData();

			// 削除する
			request(getModelClass(), "deleteRegion", bean);

			// 削除した行を一覧から削除
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
	protected boolean checkSelectedRow() {

		if (mainView.tbl.getSelectedRow() == -1) {
			// 一覧からデータを選択してください。
			showMessage("I00043");
			return false;
		}
		return true;
	}

	/**
	 * エクセルリスト出力
	 */
	protected void btnExcel_Click() {

		try {
			// 検索条件チェック
			if (!checkMainView()) {
				return;
			}

			// エクセルデータの取得
			YJRegionSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcelRegion", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("YJ Region Master") + ".xls"); // YJRegionマスタ.xls

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * メイン画面チェック
	 * 
	 * @return true:問題なし
	 */
	protected boolean checkMainView() {

		// 開始コード<=終了コードにしてください。
		if (!mainView.ctrlRange.isSmallerFrom()) {
			showMessage(mainView, "I00068");
			mainView.ctrlRange.getFieldFrom().code.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * 編集画面のイベント定義。
	 */
	protected void addEditViewEvent() {

		// [確定]ボタン押下
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditView_Settle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditView_Close_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnEditView_Settle_Click() {

		try {
			// 入力チェック
			if (!isInputRight()) {
				return;
			}

			// 入力されたYJRegion情報を取得
			reflectEditBean();

			// 削除→登録
			editBean = (YJRegion) request(getModelClass(), "entryRegionMaster", editBean);

			if (Mode.NEW == mode || Mode.COPY == mode) {
				// 新規登録の場合

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(editBean));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

			} else if (Mode.MODIFY == mode) {
				// 修正の場合

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(editBean));

			}
			// 編集画面を閉じる
			btnEditView_Close_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// コードチェック
		if (Util.isNullOrEmpty(editView.ctrlItemCode.getValue())) {
			// エラーメッセージ出力
			showMessage(editView, "I00002", "Code"); // 国コード
			editView.ctrlItemCode.requestFocus();
			return false;
		}

		// 新規、複写の場合は同一YJRegionコードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {

			YJRegionSearchCondition condition = createCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setRegionCode(editView.ctrlItemCode.getValue());

			List<YJRegion> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "Code"); // 国コード
				editView.ctrlItemCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}

	/**
	 * 編集画面で入力されたYJRegionを返す
	 */
	protected void reflectEditBean() {

		if (editBean == null) {
			editBean = createEntity();
		}

		editBean.setKAI_CODE(getCompanyCode()); // 会社コード
		editBean.setREGION_CODE(editView.ctrlItemCode.getValue()); // コード
		editBean.setREGION_NAME(editView.ctrlItemName.getValue()); // 名称
		editBean.setREGION_REMARK(editView.ctrlRemark.getValue()); // Remark
	}

	/**
	 * 編集画面[戻る]ボタン押下
	 */
	protected void btnEditView_Close_Click() {
		editView.setVisible(false);
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param editMode 操作モード
	 * @param bean
	 */
	protected void initEditView(Mode editMode, YJRegion bean) {
		this.mode = editMode;

		switch (editMode) {
			case NEW:
				// 新規
				editView.setTitle(getWord("Region") + " " + getWord("C01698")); // 新規画面

				editBean = null;// 編集前データを削除
				break;

			case MODIFY:
				// 編集
				editView.setTitle(getWord("Region") + " " + getWord("C00977")); // 編集画面
				editView.ctrlItemCode.setEditable(false);
				editView.setEditMode();
				editBean = bean;// 編集前データを保持

				break;

			case COPY:
				// 複写
				editView.setTitle(getWord("Region") + " " + getWord("C01738")); // 複写画面
				editBean = null;// 編集前データをクリア
				break;
		}
		setEditDialog(bean);
	}

	/**
	 * 画面にYJRegion情報をセットする
	 * 
	 * @param bean
	 */
	protected void setEditDialog(YJRegion bean) {

		if (bean == null) {
			return;
		}

		editView.ctrlItemCode.setValue(bean.getREGION_CODE()); // コード
		editView.ctrlItemName.setValue(bean.getREGION_NAME()); // 名称
		editView.ctrlRemark.setValue(bean.getREGION_REMARK()); // Remark
	}

	/**
	 * 一覧で選択されているYJRegionを返す
	 * 
	 * @return 一覧で選択されているYJRegion
	 * @throws Exception
	 */
	protected YJRegion getSelectedRowData() throws Exception {

		YJRegionSearchCondition condition = createCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setRegionCode((String) mainView.tbl.getSelectedRowValueAt(SC.code));

		List<YJRegion> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * ボタン(新規、削除、編集、複写、リスト出力)ロック
	 * 
	 * @param boo
	 */
	protected void lockBtn(boolean boo) {
		mainView.btnDelete.setEnabled(boo);
		mainView.btnModify.setEnabled(boo);
		mainView.btnCopy.setEnabled(boo);
		mainView.btnListOutput.setEnabled(boo);
	}

	/**
	 * 指示画面で入力されたYJRegionの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected YJRegionSearchCondition getSearchCondition() {
		YJRegionSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setRegionCodeFrom(mainView.ctrlRange.getCodeFrom());
		condition.setRegionCodeTo(mainView.ctrlRange.getCodeTo());

		return condition;
	}

	/**
	 * @return YJRegion
	 */
	protected YJRegion createEntity() {
		return new YJRegion();
	}

	/**
	 * @return 検索条件
	 */
	protected YJRegionSearchCondition createCondition() {
		return new YJRegionSearchCondition();
	}

	/**
	 * 検索条件に該当するリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するリスト
	 * @throws Exception
	 */
	protected List<YJRegion> getList(YJRegionSearchCondition condition) throws Exception {
		List<YJRegion> list = (List<YJRegion>) request(getModelClass(), "getRegion", condition);
		return list;
	}

	/**
	 * Managerクラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getModelClass() {
		return YJRegionManager.class;
	}

	/**
	 * エンティティを一覧に表示する形式に変換し返す
	 * 
	 * @param bean データ
	 * @return 一覧に表示する形式に変換されたYJRegion
	 */
	protected List<String> getRowData(YJRegion bean) {
		List<String> list = new ArrayList<String>();

		list.add(bean.getREGION_CODE()); // コード
		list.add(bean.getREGION_NAME()); // 名称
		list.add(bean.getREGION_REMARK()); // Remark

		return list;
	}
}

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
import jp.co.ais.trans2.master.ui.MG0320SystemDivisionMasterPanel.SC;
import jp.co.ais.trans2.model.program.*;

/**
 * システム区分マスタ コントローラー
 */
public class MG0320SystemDivisionMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0320SystemDivisionMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0320SystemDivisionMasterDialog editView = null;

	/** 現在操作中のモードを把握するために使用 */
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
		mainView = new MG0320SystemDivisionMasterPanel();
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

			// 検索条件
			SystemDivisionSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				// 警告メッセージ表示
				showMessage(mainView, "I00449");// 開始＜＝終了にしてください。
				mainView.TSystemDivisionReferenceRange.ctrlSysDivReferenceFrom.code.requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// システム区分情報を取得
			List<SystemDivision> list = getSystemDivision(condition);

			// 検索条件に該当するシステム区分が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// システム区分情報を一覧に表示する
			for (SystemDivision SystemDivision : list) {
				mainView.tbl.addRow(getRowData(SystemDivision));
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

			// 編集対象のシステム区分情報を取得する
			SystemDivision bean = getSelectedSystemDivision();

			// 戻り値を判定
			if (bean == null) {
				return;
			}

			// 編集画面を生成し、編集対象のシステム区分情報をセットする
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

			// 複写対象のシステム区分を取得する
			SystemDivision bean = getSelectedSystemDivision();

			// 戻り値を判定
			if (bean == null) {
				return;
			}

			// 複写画面を生成し、複写対象のシステム区分情報をセットする
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

			// 確認メッセージ
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象のシステム区分を取得する
			SystemDivision bean = getSelectedSystemDivision();

			// 戻り値を判定
			if (bean == null) {
				return;
			}

			// 削除する
			deleteSystemDivision(bean);

			// 削除したシステム区分を一覧から削除
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
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			SystemDivisionSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			// システム区分マスタ
			printer.preview(data, getWord("C02354") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面作成。編集画面を新規に生成し、イベントを定義する。
	 */
	protected void createEditView() {
		editView = new MG0320SystemDivisionMasterDialog(mainView.getParentFrame(), true);
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
	 * @param bean 修正、複写の場合は当該情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, SystemDivision bean) {

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
			editView.ctrlKbn.setEditable(false);
			editView.setEditMode();
		}
		// 複写
		else {
			// 複写画面
			editView.setTitle(getWord("C01738"));
		}

		// 編集画面の初期値
		editView.ctrlKbn.setValue(bean.getCode());
		editView.ctrlKbnName.setValue(bean.getName());
		editView.ctrlKbnNameS.setValue(bean.getNames());
		editView.ctrlKbnNameK.setValue(bean.getNamek());
		editView.ctrlExternalKbn.setSelectedItemValue(bean.getOuterSystemType());

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

			// 入力されたシステム区分情報を取得
			SystemDivision bean = getInputedSystemDivision();

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
	 * 指示画面で入力されたシステム区分の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected SystemDivisionSearchCondition getSearchCondition() {

		SystemDivisionSearchCondition condition = new SystemDivisionSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.TSystemDivisionReferenceRange.getCodeFrom());
		condition.setCodeTo(mainView.TSystemDivisionReferenceRange.getCodeTo());

		return condition;

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return CustomerManager
	 */
	protected Class getModelClass() {
		return SystemDivisionManager.class;
	}

	/**
	 * 編集画面で入力された情報を返す
	 * 
	 * @return 編集画面で入力されたシステム区分
	 */
	protected SystemDivision getInputedSystemDivision() {

		SystemDivision bean = new SystemDivision();
		bean.setCompanyCode(getCompanyCode());
		bean.setCode(editView.ctrlKbn.getValue());
		bean.setName(editView.ctrlKbnName.getValue());
		bean.setNames(editView.ctrlKbnNameS.getValue());
		bean.setNamek(editView.ctrlKbnNameK.getValue());
		bean.setOuterSystemType((OuterSystemType) editView.ctrlExternalKbn.getSelectedItemValue());
		return bean;

	}

	/**
	 * システム区分情報を一覧に表示する形式に変換し返す
	 * 
	 * @param bean システム区分
	 * @return 一覧に表示する形式に変換されたシステム区分
	 */
	protected List<Object> getRowData(SystemDivision bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean);
		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());
		list.add(getWord(OuterSystemType.getName(bean.getOuterSystemType())));
		return list;
	}

	/**
	 * 一覧で選択されているシステム区分を返す
	 * 
	 * @return 一覧で選択されているシステム区分
	 * @throws Exception
	 */
	protected SystemDivision getSelectedSystemDivision() throws Exception {
		SystemDivision bean = (SystemDivision) mainView.tbl.getSelectedRowValueAt(SC.ENTITY);

		SystemDivisionSearchCondition condition = new SystemDivisionSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(bean.getCode());

		List<SystemDivision> list = getSystemDivision(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			showMessage("I00193");
			return null;

		}
		return list.get(0);
	}

	/**
	 * 検索条件に該当するシステム区分のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するシステム区分のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<SystemDivision> getSystemDivision(SystemDivisionSearchCondition condition) throws Exception {

		List<SystemDivision> list = (List<SystemDivision>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * 指定のシステム区分を削除する
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void deleteSystemDivision(SystemDivision bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// システム区分が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlKbn.getValue())) {
			// システム区分を入力してください。
			showMessage(editView, "I00037", "C00980");
			editView.ctrlKbn.requestTextFocus();
			return false;
		}

		// システム区分名称が未入力の場合エラー if
		if (Util.isNullOrEmpty(editView.ctrlKbnName.getValue())) {
			// システム区分名称を入力してください。
			showMessage(editView, "I00037", "C00832");
			editView.ctrlKbnName.requestTextFocus();
			return false;
		}

		// システム区分略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlKbnNameS.getValue())) {
			// システム区分略称を入力してください。"
			showMessage(editView, "I00037", "C00833");
			editView.ctrlKbnNameS.requestTextFocus();
			return false;
		}

		// システム区分検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlKbnNameK.getValue())) {
			// システム区分検索名称を入力してください。
			showMessage(editView, "I00037", "C00834");
			editView.ctrlKbnNameK.requestTextFocus();
			return false;
		}

		// 外部システム区分が未選択の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlExternalKbn.getSelectedItemValue())) {
			// 外部システム区分を入力してください。
			showMessage(editView, "I00037", "C01018");
			editView.ctrlExternalKbn.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一のシステム区分が存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			SystemDivisionSearchCondition condition = new SystemDivisionSearchCondition();
			condition.setCode(editView.ctrlKbn.getValue());
			condition.setCompanyCode(getCompanyCode());
			List<SystemDivision> list = getSystemDivision(condition);
			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00980");
				editView.ctrlKbn.requestTextFocus();
				return false;
			}
		}

		return true;
	}

}

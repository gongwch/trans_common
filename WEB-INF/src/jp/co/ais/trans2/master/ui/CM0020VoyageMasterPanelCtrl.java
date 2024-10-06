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
import jp.co.ais.trans2.model.voyage.*;

/**
 * 次航マスタのコントローラ
 * 
 * @author AIS
 */
public class CM0020VoyageMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected CM0020VoyageMasterPanel mainView = null;

	/** 編集画面 */
	protected CM0020VoyageMasterDialog editView = null;

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
		mainView = new CM0020VoyageMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [新規]ボタン押下
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [編集]ボタン押下
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [複写]ボタン押下
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [削除]ボタン押下
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [エクセル]ボタン押下
		mainView.btnExcel.addActionListener(new ActionListener() {

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

		// ログイン会社コードをセットする
		mainView.ctrlVoyageRange.ctrlVoyageReferenceFrom.setCompanyCode(getCompany().getCode());
		mainView.ctrlVoyageRange.ctrlVoyageReferenceTo.setCompanyCode(getCompany().getCode());

		// 次航コード開始にフォーカス
		mainView.ctrlVoyageRange.ctrlVoyageReferenceFrom.code.requestFocus();

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
			if (Util.isSmallerThen(mainView.ctrlVoyageRange.getCodeFrom(), mainView.ctrlVoyageRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlVoyageRange.getFieldFrom().requestFocus();
				return;

			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 次航情報を取得
			VoyageSearchCondition condition = getSearchCondition();
			List<Voyage> list = getVoyage(condition);

			// 検索条件に該当する次航情報が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 次航情報を一覧に表示する
			for (Voyage voyage : list) {
				mainView.tbl.addRow(getRowData(voyage));
			}

			// メインボタンを押下可能にし、1行目を選択する
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[編集]ボタン押下
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 編集対象の次航情報を取得する
			Voyage voyage = getSelectedVoyage();

			// 編集画面を生成し、編集対象の次航情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, voyage);

			// 編集画面を表示する
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[複写]ボタン押下
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 複写対象の次航を取得する
			Voyage voyage = getSelectedVoyage();

			// 複写画面を生成し、複写対象の次航情報をセットする
			createEditView();
			initEditView(Mode.COPY, voyage);

			// 複写画面を表示する
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[削除]ボタン押下
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象の次航情報を取得する
			Voyage voyage = getSelectedVoyage();

			// 削除する
			deleteVoyage(voyage);

			// 削除した次航情報を一覧から削除
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
	 * 指示画面[エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// データ抽出

			VoyageSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11779") + ".xls");// 次航マスタ

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new CM0020VoyageMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

	}

	/**
	 * 編集画面のイベント定義。
	 */
	protected void addEditViewEvent() {

		// [確定]ボタン押下
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode 操作モード。
	 * @param bean 次航情報。修正、複写の場合は当該次航情報を編集画面にセットする。
	 */
	protected void initEditView(@SuppressWarnings("hiding") Mode mode, Voyage bean) {

		this.mode = mode;

		// 新規の場合
		if (Mode.NEW == mode) {

			editView.setTitle(getWord("C01698"));// 新規画面

			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

			return;

			// 編集、複写の場合
		} else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			// 編集
			if (Mode.MODIFY == mode) {
				editView.setTitle(getWord("C00977"));// 編集画面
				editView.ctrlCode.setEditable(false);
				editView.setEditMode();
				// 複写
			} else {
				editView.setTitle(getWord("C01738"));// 複写画面
			}

			// 次航情報
			editView.ctrlCode.setValue(bean.getCode());
			editView.ctrlName.setValue(bean.getName());
			editView.ctrlNames.setValue(bean.getNames());
			editView.ctrlNamek.setValue(bean.getNamek());
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

			// 入力された次航情報を取得
			Voyage voyage = getInputedVoyage();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録

				request(getModelClass(), "entry", voyage);
				// 編集画面を閉じる
				btnClose_Click();

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(voyage));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", voyage);
				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(voyage));

				// 編集画面を閉じる
				btnClose_Click();

			}

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
	 * 指示画面で入力された次航情報の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected VoyageSearchCondition getSearchCondition() {

		VoyageSearchCondition condition = createVoyageSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlVoyageRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlVoyageRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * モデルインターフェースを返す
	 * 
	 * @return モデルインターフェース
	 */
	protected Class getModelClass() {
		return VoyageManager.class;
	}

	/**
	 * 編集画面で入力された次航情報を返す
	 * 
	 * @return 編集画面で入力された次航情報
	 */
	protected Voyage getInputedVoyage() {

		// 次航情報
		Voyage voyage = createVoyage();
		voyage.setCompanyCode(getCompany().getCode());
		voyage.setCode(editView.ctrlCode.getValue());
		voyage.setName(editView.ctrlName.getValue());
		voyage.setNames(editView.ctrlNames.getValue());
		voyage.setNamek(editView.ctrlNamek.getValue());
		voyage.setDateFrom(editView.dtBeginDate.getValue());
		voyage.setDateTo(editView.dtEndDate.getValue());

		return voyage;

	}

	/**
	 * @return 次航情報の検索条件
	 */
	protected VoyageSearchCondition createVoyageSearchCondition() {
		return new VoyageSearchCondition();
	}

	/**
	 * @return 次航情報
	 */
	protected Voyage createVoyage() {
		return new Voyage();
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(Voyage bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean);
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
	 * 検索条件に該当する次航のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する次航情報のリスト
	 * @throws Exception
	 */
	protected List<Voyage> getVoyage(VoyageSearchCondition condition) throws Exception {

		List<Voyage> list = (List<Voyage>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * 一覧で選択されている次航データを返す
	 * 
	 * @return 一覧で選択されている次航情報
	 * @throws Exception
	 */
	protected Voyage getSelectedVoyage() throws Exception {

		VoyageSearchCondition condition = createVoyageSearchCondition();
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(CM0020VoyageMasterPanel.SC.code));

		List<Voyage> list = getVoyage(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * 指定の次航情報を削除する
	 * 
	 * @param voyage 次航情報
	 * @throws Exception
	 */
	protected void deleteVoyage(Voyage voyage) throws Exception {

		request(getModelClass(), "delete", voyage);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {
		int tabIndex = 0;
		// -- 基本タブ --
		// 次航コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C03003");// 次航コード
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一次航コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			VoyageSearchCondition condition = createVoyageSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<Voyage> list = getVoyage(condition);

			if (list != null && !list.isEmpty()) {
				// 指定の次航コードは既に存在します
				showMessage(editView, "I00148", "C03003");
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}

		// 次航名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C11780");// 次航名称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// 次航略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlNames.getValue())) {
			showMessage(editView, "I00037", "C11781");// 次航略称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNames.requestTextFocus();
			return false;
		}

		// 次航検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlNamek.getValue())) {
			showMessage(editView, "I00037", "C11782");// 次航検索名称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNamek.requestTextFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");// 開始年月日
			editView.tab.setSelectedIndex(tabIndex);
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");// 終了年月日
			editView.tab.setSelectedIndex(tabIndex);
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

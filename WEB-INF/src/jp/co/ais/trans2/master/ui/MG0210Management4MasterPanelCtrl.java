package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理4マスタのコントローラー
 * 
 * @author AIS
 */
public class MG0210Management4MasterPanelCtrl extends TController {

	/**
	 * 指示画面
	 */
	protected MG0210Management4MasterPanel mainView = null;

	/**
	 * 編集画面
	 */
	protected MG0210Management4MasterDialog editView = null;

	/**
	 * 操作モード。現在操作中のモードを把握するために使用する。
	 */
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
		mainView = new MG0210Management4MasterPanel(getCompany());
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

			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.management4Range.getCodeFrom(),
					mainView.management4Range.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.management4Range.getFieldFrom().requestFocus();
				return;
			}

			// 管理4情報を取得
			Management4SearchCondition condition = getSearchCondition();
			List<Management4> list = getManagement4(condition);

			// 検索条件に該当する管理4が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 管理4情報を一覧に表示する
			for (Management4 management : list) {
				mainView.tbl.addRow(getRowData(management));
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

			// 編集対象の管理4を取得する
			Management4 management4 = getSelectedManagement4();

			// 戻り値がNULLの場合
			if (management4 == null) {
				return;
			}

			// 編集画面を生成し、編集対象の管理4情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, management4);

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

			// 複写対象の管理4を取得する
			Management4 management4 = getSelectedManagement4();

			// 戻り値がNULLの場合
			if (management4 == null) {
				return;
			}

			// 複写画面を生成し、複写対象の管理4情報をセットする
			createEditView();
			initEditView(Mode.COPY, management4);

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

			// 削除対象の管理4を取得する
			Management4 management4 = getSelectedManagement4();

			// 戻り値がNULLの場合
			if (management4 == null) {
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除する
			deleteManagement4(management4);

			// 削除した管理4を一覧から削除
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
	 * [エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// データ抽出
			Management4SearchCondition condition = getSearchCondition();

			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();

			String fileName = "";
			if (getCompany().getAccountConfig().getManagement4Name() == null) {
				fileName = getShortWord("C01031") + getWord("C00500") + ".xls";
			} else {
				fileName = getCompany().getAccountConfig().getManagement4Name() + getWord("C00500") + ".xls";
			}

			printer.preview(data, fileName);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0210Management4MasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param bean  管理4。修正、複写の場合は当該管理4情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, Management4 bean) {

		this.mode = mode_;

		// 新規の場合
		if (Mode.NEW == mode_) {

			// 新規画面
			editView.setTitle(getWord("C01698"));

			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

			return;

			// 編集、複写の場合
		} else if (Mode.MODIFY == mode_ || Mode.COPY == mode_) {

			// 編集
			if (Mode.MODIFY == mode_) {
				// 編集画面
				editView.setTitle(getWord("C00977"));
				editView.ctrlManagementCode.setEditable(false);
				editView.setEditMode();
				// 複写
			} else {
				// 複写画面
				editView.setTitle(getWord("C01738"));
			}

			editView.ctrlManagementCode.setValue(bean.getCode());
			editView.ctrlManagementName.setValue(bean.getName());
			editView.ctrlManagementNames.setValue(bean.getNames());
			editView.ctrlManagementNamek.setValue(bean.getNamek());
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

			// 入力された管理4情報を取得
			Management4 management4 = getInputedManagement4();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", management4);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(management4));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", management4);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(management4));

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
	 * 指示画面で入力された管理4の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected Management4SearchCondition getSearchCondition() {

		Management4SearchCondition condition = new Management4SearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.management4Range.getCodeFrom());
		condition.setCodeTo(mainView.management4Range.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return Management4Manager.class;
	}

	/**
	 * 編集画面で入力された管理4を返す
	 * 
	 * @return 編集画面で入力された管理4
	 */
	protected Management4 getInputedManagement4() {

		Management4 management4 = new Management4();
		management4.setCompanyCode(getCompany().getCode());
		management4.setCode(editView.ctrlManagementCode.getValue());
		management4.setName(editView.ctrlManagementName.getValue());
		management4.setNames(editView.ctrlManagementNames.getValue());
		management4.setNamek(editView.ctrlManagementNamek.getValue());
		management4.setDateFrom(editView.dtBeginDate.getValue());
		management4.setDateTo(editView.dtEndDate.getValue());

		return management4;

	}

	/**
	 * 管理4情報を一覧に表示する形式に変換し返す
	 * 
	 * @param management 管理4情報
	 * @return 一覧に表示する形式に変換された管理4情報
	 */
	protected String[] getRowData(Management4 management) {
		return new String[] { management.getCompanyCode(), management.getCode(), management.getName(),
				management.getNames(), management.getNamek(), DateUtil.toYMDString(management.getDateFrom()),
				DateUtil.toYMDString(management.getDateTo()) };
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
	 * 検索条件に該当する管理4のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する管理4のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Management4> getManagement4(Management4SearchCondition condition) throws Exception {

		List<Management4> list = (List<Management4>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * 一覧で選択されている管理4を返す
	 * 
	 * @return 一覧で選択されている管理4
	 * @throws Exception
	 */
	protected Management4 getSelectedManagement4() throws Exception {

		Management4SearchCondition condition = new Management4SearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0210Management4MasterPanel.SC.code));

		List<Management4> list = getManagement4(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			showMessage("I00193");
			return null;
		}
		return list.get(0);

	}

	/**
	 * 指定の管理4を削除する
	 * 
	 * @param management4 管理4
	 * @throws Exception
	 */
	protected void deleteManagement4(Management4 management4) throws Exception {
		request(getModelClass(), "delete", management4);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		String caption = "";
		if (getCompany().getAccountConfig().getManagement4Name() == null) {
			caption = getShortWord("C01031");
		} else {
			caption = getCompany().getAccountConfig().getManagement4Name();
		}

		// 管理4コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlManagementCode.getValue())) {
			// コードを入力してください。
			showMessage(editView, "I00037", caption + getWord("C00174"));
			editView.ctrlManagementCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一管理4が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			Management4SearchCondition condition = new Management4SearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlManagementCode.getValue());

			List<Management4> list = getManagement4(condition);

			if (list != null && !list.isEmpty()) {
				// "指定の" + caption + "は既に存在します。"
				showMessage(editView, "I00148", caption + getWord("C00174"));
				editView.ctrlManagementCode.requestTextFocus();
				return false;
			}
		}

		// 管理4名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlManagementName.getValue())) {
			// 名称を入力してください。
			showMessage(editView, "I00037", caption + getWord("C00518"));
			editView.ctrlManagementName.requestTextFocus();
			return false;
		}

		// 管理4略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlManagementNames.getValue())) {
			// 略称を入力してください。
			showMessage(editView, "I00037", caption + getWord("C00548"));
			editView.ctrlManagementNames.requestTextFocus();
			return false;
		}

		// 管理4検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlManagementNamek.getValue())) {
			// 検索名称を入力してください。
			showMessage(editView, "I00037", caption + getWord("C00160"));
			editView.ctrlManagementNamek.requestTextFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			// 開始年月日を入力してください。
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			// 終了年月日を入力してください。
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if ((!Util.isNullOrEmpty(editView.dtBeginDate.getValue()) && !Util.isNullOrEmpty(editView.dtEndDate.getValue()))
				&& !Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}
		return true;

	}

}

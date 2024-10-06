package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.program.*;

/**
 * プログラムマスタのコントローラ
 * 
 * @author AIS
 */
public class MG0240ProgramMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0240ProgramMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0240ProgramMasterDialog editView = null;

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
		mainView = new MG0240ProgramMasterPanel();
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
	 * 指示画面[エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {
		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// プログラム情報を取得
			ProgramSearchCondition condition = getSearchCondition();
			condition.setLang(super.getUser().getLanguage());

			// データ抽出
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			// プログラムマスタ
			printer.preview(data, getWord("C02147") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

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
			if (Util.isSmallerThen(mainView.ctrlProgramRange.getCodeFrom(), mainView.ctrlProgramRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlProgramRange.getFieldFrom().requestFocus();
				return;
			}

			// プログラム情報を取得
			ProgramSearchCondition condition = getSearchCondition();
			List<Program> list = getProgram(condition);

			// 検索条件に該当するプログラムが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// プログラム情報を一覧に表示する
			for (Program program : list) {
				mainView.tbl.addRow(getRowData(program));
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

			// 編集対象のプログラムを取得する
			Program program = getSelectedProgram();

			// 編集画面を生成し、編集対象のプログラム情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, program);

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

			// 複写対象のプログラムを取得する
			Program program = getSelectedProgram();

			// 複写画面を生成し、複写対象のプログラム情報をセットする
			createEditView();
			initEditView(Mode.COPY, program);

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

			// 削除対象のプログラムを取得する
			Program program = getSelectedProgram();

			// 削除する
			deleteProgram(program);

			// 削除したプログラムを一覧から削除
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
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0240ProgramMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param program プログラム。修正、複写の場合は当該プログラム情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, Program program) {

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
				editView.ctrlProgramCode.setEditable(false);
				editView.ctrlSystem.setEditable(false);
				editView.setEditMode();
				// 複写
			} else {
				// 複写画面
				editView.setTitle(getWord("C01738"));
			}

			editView.ctrlSystem.setEntity(program.getSystemClassification());
			editView.ctrlProgramCode.setValue(program.getCode());
			editView.ctrlProgramName.setValue(program.getName());
			editView.ctrlProgramNames.setValue(program.getNames());
			editView.ctrlProgramNamek.setValue(program.getNamek());
			editView.ctrlComment.setValue(program.getComment());
			editView.ctrlModuleName.setValue(program.getLoadClassName());
			editView.dtBeginDate.setValue(program.getTermFrom());
			editView.dtEndDate.setValue(program.getTermTo());

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

			// 入力されたプログラム情報を取得
			Program program = getInputedProgram();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", program);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(program));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

			}
			// 修正の場合
			else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", program);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(program));
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
	 * 指示画面で入力されたプログラムの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected ProgramSearchCondition getSearchCondition() {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setSystemCode(mainView.ctrlSystem.getCode());
		condition.setCodeFrom(mainView.ctrlProgramRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlProgramRange.getCodeTo());
		condition.setExecutable(false);
		condition.setNotExecutable(false);

		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * モデルクラスを返す
	 * 
	 * @return モデルクラス
	 */
	protected Class getModelClass() {
		return ProgramManager.class;
	}

	/**
	 * 編集画面で入力されたプログラムを返す
	 * 
	 * @return 編集画面で入力されたプログラム
	 */
	protected Program getInputedProgram() {

		Program program = new Program();
		program.setCompanyCode(TLoginInfo.getCompany().getCode());
		SystemClassification sysBean = new SystemClassification();
		sysBean.setCode(editView.ctrlSystem.getCode());
		sysBean.setName(editView.ctrlSystem.getName());
		program.setSystemClassification(sysBean);
		program.setCode(editView.ctrlProgramCode.getValue());
		program.setName(editView.ctrlProgramName.getValue());
		program.setNames(editView.ctrlProgramNames.getValue());
		program.setNamek(editView.ctrlProgramNamek.getValue());
		program.setComment(editView.ctrlComment.getValue());
		program.setLoadClassName(editView.ctrlModuleName.getValue());
		program.setTermFrom(editView.dtBeginDate.getValue());
		program.setTermTo(editView.dtEndDate.getValue());

		return program;

	}

	/**
	 * プログラム情報を一覧に表示する形式に変換し返す
	 * 
	 * @param program プログラム
	 * @return 一覧に表示する形式に変換されたプログラム
	 */
	protected String[] getRowData(Program program) {
		return new String[] { program.getSystemClassification().getCode(), program.getCode(), program.getName(),
				program.getNames(), program.getNamek(), program.getComment(), program.getLoadClassName(),
				DateUtil.toYMDString(program.getTermFrom()), DateUtil.toYMDString(program.getTermTo()) };
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
	 * 検索条件に該当するプログラムのリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するプログラムのリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Program> getProgram(ProgramSearchCondition condition) throws Exception {

		List<Program> list = (List<Program>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * 一覧で選択されているプログラムを返す
	 * 
	 * @return 一覧で選択されているプログラム
	 * @throws Exception
	 */
	protected Program getSelectedProgram() throws Exception {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0240ProgramMasterPanel.SC.code));

		condition.setSystemCode((String) mainView.tbl.getSelectedRowValueAt(MG0240ProgramMasterPanel.SC.systemCode));

		List<Program> list = getProgram(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * 指定のプログラムを削除する
	 * 
	 * @param program プログラム
	 * @throws Exception
	 */
	protected void deleteProgram(Program program) throws Exception {
		request(getModelClass(), "delete", program);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// システム区分が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlSystem.getCode())) {
			showMessage(editView, "I00037", "C00980");
			editView.ctrlSystem.code.requestFocus();
			return false;
		}

		// プログラムコードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramCode.getValue())) {
			showMessage(editView, "I00037", "C00818");
			editView.ctrlProgramCode.requestFocus();
			return false;
		}

		// 新規、複写の場合は同一プログラムが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {

			ProgramSearchCondition condition = new ProgramSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlProgramCode.getValue());

			List<Program> list = getProgram(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "C00818");
				editView.ctrlProgramCode.requestTextFocus();
				return false;
			}
		}

		// プログラム名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramName.getValue())) {
			showMessage(editView, "I00037", "C00819");
			editView.ctrlProgramName.requestFocus();
			return false;
		}

		// プログラム略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramNames.getValue())) {
			showMessage(editView, "I00037", "C00820");
			editView.ctrlProgramNames.requestFocus();
			return false;
		}

		// プログラム検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramNamek.getValue())) {
			showMessage(editView, "I00037", "C00821");
			editView.ctrlProgramNamek.requestFocus();
			return false;
		}

		// ロードモジュールファイル名が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlModuleName.getValue())) {
			showMessage(editView, "I00037", "C00823");
			editView.ctrlModuleName.requestFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}
		return true;

	}

}

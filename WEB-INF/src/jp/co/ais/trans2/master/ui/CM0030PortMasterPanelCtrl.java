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
import jp.co.ais.trans2.model.port.*;

/**
 * 港マスタのコントローラ
 * 
 * @author AIS
 */
public class CM0030PortMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected CM0030PortMasterPanel mainView = null;

	/** 編集画面 */
	protected CM0030PortMasterDialog editView = null;

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
		mainView = new CM0030PortMasterPanel();
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
		mainView.ctrlPortRange.ctrlPortReferenceFrom.setCompanyCode(getCompany().getCode());
		mainView.ctrlPortRange.ctrlPortReferenceTo.setCompanyCode(getCompany().getCode());

		// 港コード開始にフォーカス
		mainView.ctrlPortRange.ctrlPortReferenceFrom.code.requestFocus();

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
			if (Util.isSmallerThen(mainView.ctrlPortRange.getCodeFrom(), mainView.ctrlPortRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlPortRange.getFieldFrom().requestFocus();
				return;

			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 港情報を取得
			PortSearchCondition condition = getSearchCondition();
			List<Port> list = getPort(condition);

			// 検索条件に該当する港情報が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 港情報を一覧に表示する
			for (Port port : list) {
				mainView.tbl.addRow(getRowData(port));
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

			// 編集対象の港情報を取得する
			Port port = getSelectedPort();

			// 編集画面を生成し、編集対象の港情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, port);

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

			// 複写対象の港を取得する
			Port port = getSelectedPort();

			// 複写画面を生成し、複写対象の港情報をセットする
			createEditView();
			initEditView(Mode.COPY, port);

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

			// 削除対象の港情報を取得する
			Port port = getSelectedPort();

			// 削除する
			deletePort(port);

			// 削除した港情報を一覧から削除
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

			PortSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11771") + ".xls");// 港マスタ

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new CM0030PortMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param bean 港情報。修正、複写の場合は当該港情報を編集画面にセットする。
	 */
	protected void initEditView(@SuppressWarnings("hiding") Mode mode, Port bean) {

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

			// 港情報
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

			// 入力された港情報を取得
			Port port = getInputedPort();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録

				request(getModelClass(), "entry", port);
				// 編集画面を閉じる
				btnClose_Click();

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(port));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", port);
				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(port));

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
	 * 指示画面で入力された港情報の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected PortSearchCondition getSearchCondition() {

		PortSearchCondition condition = createPortSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlPortRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlPortRange.getCodeTo());
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
		return PortManager.class;
	}

	/**
	 * 編集画面で入力された港情報を返す
	 * 
	 * @return 編集画面で入力された港情報
	 */
	protected Port getInputedPort() {

		// 港情報
		Port port = createPort();
		port.setCompanyCode(getCompany().getCode());
		port.setCode(editView.ctrlCode.getValue());
		port.setName(editView.ctrlName.getValue());
		port.setNames(editView.ctrlNames.getValue());
		port.setNamek(editView.ctrlNamek.getValue());
		port.setDateFrom(editView.dtBeginDate.getValue());
		port.setDateTo(editView.dtEndDate.getValue());

		return port;

	}

	/**
	 * @return 港情報の検索条件
	 */
	protected PortSearchCondition createPortSearchCondition() {
		return new PortSearchCondition();
	}

	/**
	 * @return 港情報
	 */
	protected Port createPort() {
		return new Port();
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(Port bean) {
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
	 * 検索条件に該当する港のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する港情報のリスト
	 * @throws Exception
	 */
	protected List<Port> getPort(PortSearchCondition condition) throws Exception {

		List<Port> list = (List<Port>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * 一覧で選択されている港データを返す
	 * 
	 * @return 一覧で選択されている港情報
	 * @throws Exception
	 */
	protected Port getSelectedPort() throws Exception {

		PortSearchCondition condition = createPortSearchCondition();
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(CM0030PortMasterPanel.SC.code));

		List<Port> list = getPort(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * 指定の港情報を削除する
	 * 
	 * @param port 港情報
	 * @throws Exception
	 */
	protected void deletePort(Port port) throws Exception {

		request(getModelClass(), "delete", port);
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
		// 港コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C00584");// 港コード
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一港コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			PortSearchCondition condition = createPortSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<Port> list = getPort(condition);

			if (list != null && !list.isEmpty()) {
				// 指定の港コードは既に存在します
				showMessage(editView, "I00148", "C00584");
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}

		// 港名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C00585");// 港名称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// 港略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlNames.getValue())) {
			showMessage(editView, "I00037", "C00586");// 港略称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNames.requestTextFocus();
			return false;
		}

		// 港検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlNamek.getValue())) {
			showMessage(editView, "I00037", "C00587");// 港検索名称
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

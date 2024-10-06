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
import jp.co.ais.trans2.master.ui.CM0010VesselMasterPanel.SC;
import jp.co.ais.trans2.model.vessel.*;

/**
 * 船マスタのコントローラ
 * 
 * @author AIS
 */
public class CM0010VesselMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected CM0010VesselMasterPanel mainView = null;

	/** 編集画面 */
	protected CM0010VesselMasterDialog editView = null;

	/** 燃料管理を使用するか true:使用する */
	protected boolean isUseBM = false;

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
		mainView = new CM0010VesselMasterPanel();
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

		this.isUseBM = isUseBM();

		// メインボタンを押下不可能にする
		setMainButtonEnabled(false);

		// ログイン会社コードをセットする
		mainView.ctrlVesselRange.ctrlVesselReferenceFrom.setCompanyCode(getCompany().getCode());
		mainView.ctrlVesselRange.ctrlVesselReferenceTo.setCompanyCode(getCompany().getCode());

		if (isUseBM) {

			// 燃料管理コントロールマスタあり
			mainView.tbl.addColumn(SC.stock, getWord("C11775"), 130); // 貯蔵品部門コード
			mainView.tbl.addColumn(SC.stockName, getWord("C11776"), 200); // 貯蔵品部門名称
			mainView.tbl.addColumn(SC.fuel, getWord("C11777"), 130); // 燃料費部門コード
			mainView.tbl.addColumn(SC.fuelName, getWord("C11778"), 200); // 燃料費部門名称
		}

		// 船コード開始にフォーカス
		mainView.ctrlVesselRange.ctrlVesselReferenceFrom.code.requestFocus();

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

			if (!isUseBM) {

				// 燃料管理コントロールマスタなし
				editView.tab.setEnabledAt(0, false);

			} else {

				// 燃料管理コントロールマスタあり
				editView.tab.setEnabledAt(0, true);

			}

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
			if (Util.isSmallerThen(mainView.ctrlVesselRange.getCodeFrom(), mainView.ctrlVesselRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlVesselRange.getFieldFrom().requestFocus();
				return;

			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 船情報を取得
			VesselSearchCondition condition = getSearchCondition();
			List<Vessel> list = getVessel(condition);

			// 検索条件に該当する船情報が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 船情報を一覧に表示する
			for (Vessel vessel : list) {
				mainView.tbl.addRow(getRowData(vessel));
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

			// 編集対象の船情報を取得する
			Vessel vessel = getSelectedVessel();

			// 編集画面を生成し、編集対象の船情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, vessel);

			if (!isUseBM) {

				// 燃料管理コントロールマスタなし
				editView.tab.setEnabledAt(0, false);

			} else {

				// 燃料管理コントロールマスタあり
				editView.tab.setEnabledAt(0, true);

			}

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

			// 複写対象の船を取得する
			Vessel vessel = getSelectedVessel();

			// 複写画面を生成し、複写対象の船情報をセットする
			createEditView();
			initEditView(Mode.COPY, vessel);

			if (!isUseBM) {

				// 燃料管理コントロールマスタなし
				editView.tab.setEnabledAt(0, false);

			} else {

				// 燃料管理コントロールマスタあり
				editView.tab.setEnabledAt(0, true);

			}

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

			// 削除対象の船情報を取得する
			Vessel vessel = getSelectedVessel();

			// 削除する
			deleteVessel(vessel);

			// 削除した船情報を一覧から削除
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

			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.ctrlVesselRange.getCodeFrom(), mainView.ctrlVesselRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlVesselRange.getFieldFrom().requestFocus();
				return;

			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// データ抽出

			VesselSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11772") + ".xls");// 船マスタ

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new CM0010VesselMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param mode_ 操作モード。
	 * @param bean 船情報。修正、複写の場合は当該船情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, Vessel bean) {

		mode = mode_;

		// 新規の場合
		if (Mode.NEW == mode) {

			editView.setTitle(getWord("C01698"));// 新規画面

			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

			if (!isUseBM) {

				editView.tab.setVisible(false);
				editView.setSize(editView.getWidth(), editView.getHeight() - 130);
			}

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

			if (!isUseBM) {

				editView.tab.setVisible(false);
				editView.setSize(editView.getWidth(), editView.getHeight() - 130);
			}

			// 船情報
			editView.ctrlCode.setValue(bean.getCode());
			editView.ctrlName.setValue(bean.getName());
			editView.ctrlNames.setValue(bean.getNames());
			editView.ctrlNamek.setValue(bean.getNamek());
			editView.dtBeginDate.setValue(bean.getDateFrom());
			editView.dtEndDate.setValue(bean.getDateTo());
			editView.ctrlStockReference.code.setValue(bean.getStockCode());
			editView.ctrlStockReference.name.setValue(bean.getStockName());
			editView.ctrlFuelReference.code.setValue(bean.getFuelCode());
			editView.ctrlFuelReference.name.setValue(bean.getFuelName());
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

			// 入力された船情報を取得
			Vessel vessel = getInputedVessel();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録

				request(getModelClass(), "insert", vessel);
				// 編集画面を閉じる
				btnClose_Click();

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(vessel));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", vessel);
				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(vessel));

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
	 * 指示画面で入力された船情報の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected VesselSearchCondition getSearchCondition() {

		VesselSearchCondition condition = createVesselSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlVesselRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlVesselRange.getCodeTo());
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
		return VesselManager.class;
	}

	/**
	 * 編集画面で入力された船情報を返す
	 * 
	 * @return 編集画面で入力された船情報
	 */
	protected Vessel getInputedVessel() {

		// 船情報
		Vessel vessel = createVessel();
		vessel.setCompanyCode(getCompany().getCode());
		vessel.setCode(editView.ctrlCode.getValue());
		vessel.setName(editView.ctrlName.getValue());
		vessel.setNames(editView.ctrlNames.getValue());
		vessel.setNamek(editView.ctrlNamek.getValue());
		vessel.setDateFrom(editView.dtBeginDate.getValue());
		vessel.setDateTo(editView.dtEndDate.getValue());
		vessel.setStockCode(editView.ctrlStockReference.getCode());
		vessel.setStockName(editView.ctrlStockReference.getNames());
		vessel.setFuelCode(editView.ctrlFuelReference.getCode());
		vessel.setFuelName(editView.ctrlFuelReference.getNames());

		return vessel;

	}

	/**
	 * @return 船情報の検索条件
	 */
	protected VesselSearchCondition createVesselSearchCondition() {
		return new VesselSearchCondition();
	}

	/**
	 * @return 船情報
	 */
	protected Vessel createVessel() {
		return new Vessel();
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(Vessel bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean.getStockCode());
		list.add(bean.getStockName());
		list.add(bean.getFuelCode());
		list.add(bean.getFuelName());
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
	 * 検索条件に該当する船のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する船情報のリスト
	 * @throws Exception
	 */
	protected List<Vessel> getVessel(VesselSearchCondition condition) throws Exception {

		List<Vessel> list = (List<Vessel>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * 一覧で選択されている船データを返す
	 * 
	 * @return 一覧で選択されている船情報
	 * @throws Exception
	 */
	protected Vessel getSelectedVessel() throws Exception {

		VesselSearchCondition condition = createVesselSearchCondition();
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(CM0010VesselMasterPanel.SC.code));
		condition.setCompanyCode(getCompany().getCode());

		List<Vessel> list = getVessel(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * 指定の船情報を削除する
	 * 
	 * @param vessel 船情報
	 * @throws Exception
	 */
	protected void deleteVessel(Vessel vessel) throws Exception {

		request(getModelClass(), "delete", vessel);
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
		// 船コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C11758");// 船コード
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一船コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			VesselSearchCondition condition = createVesselSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<Vessel> list = getVessel(condition);

			if (list != null && !list.isEmpty()) {
				// 指定の船コードは既に存在します
				showMessage(editView, "I00148", "C11758");
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}

		// 船名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C11773");// 船名称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// 船略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlNames.getValue())) {
			showMessage(editView, "I00037", "C11759");// 船略称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNames.requestTextFocus();
			return false;
		}

		// 船検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlNamek.getValue())) {
			showMessage(editView, "I00037", "C11774");// 船検索名称
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

		if (isUseBM) {
			// 貯蔵品部門コードが未入力の場合エラー
			if (Util.isNullOrEmpty(editView.ctrlStockReference.getCode())) {
				showMessage(editView, "I00037", "C11775");// 貯蔵品部門コード
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlStockReference.requestTextFocus();
				return false;

			}
			// 燃料費部門コードが未入力の場合エラー
			if (Util.isNullOrEmpty(editView.ctrlFuelReference.getCode())) {
				showMessage(editView, "I00037", "C11777");// 燃料費部門コード
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlFuelReference.requestTextFocus();
				return false;
			}
		}

		return true;

	}

	/**
	 * Entityが存在するかどうかをチェックする
	 * 
	 * @return true:存在する false:存在しない
	 */
	protected boolean isUseBM() {
		try {
			if ((Boolean) request(getModelClass(), "isUseBM")) {

				return true;
			}
		} catch (Exception e) {
			errorHandler(e);
		}
		return false;
	}

}

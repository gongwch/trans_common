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
import jp.co.ais.trans2.model.tag.*;

/**
 * MG0460TagMaster - マスタ - Main Controller
 * 
 * @author AIS
 */
public class MG0460TagMasterPanelCtrl extends TController {

	/** 一覧画面 */
	protected MG0460TagMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0460TagMasterDialog editView = null;

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

	/** デフォルト白色 */
	protected static Color DEFAULT_COLOR = Color.WHITE;

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
		mainView = new MG0460TagMasterPanel();
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

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [検索]ボタン
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [編集]ボタン
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [複写]ボタン
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [削除]ボタン
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
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
			TagSearchCondition condition = getSearchCondition();

			// 一覧をクリア
			mainView.tbList.removeRow();

			// メインボタン制御
			setMainButtonEnabled(false);

			// データ取得
			List<Tag> list = getList(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (Tag tag : list) {
				mainView.tbList.addRow(getRowData(tag));
			}

			// メインボタン制御
			setMainButtonEnabled(true);

			mainView.tbList.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 検索条件取得
	 * 
	 * @return TagSearchCondition 検索条件
	 */
	protected TagSearchCondition getSearchCondition() {

		TagSearchCondition condition = new TagSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		return condition;
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
			Tag bean = getSelected();

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
			Tag bean = getSelected();

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

			// 確認メッセージ表示:消去しますか？
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {
				return;
			}

			// 削除対象のデータ取得
			Tag bean = getSelected();

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
	 * /** 編集画面作成
	 */
	protected void createEditView() {
		editView = new MG0460TagMasterDialog(mainView.getParentFrame(), true);
		addEditViewEvent();
	}

	/**
	 * 編集画面のイベント定義
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
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {
		try {

			// 入力データチェック
			if (!isInputCorrect()) {
				return;
			}

			// 入力データ取得
			Tag bean = getInputtedTagMaster();

			// 新規登録の場合
			switch (mode) {

				case NEW:

					// 新規登録 - 複写登録
					request(getModelClass(), "entry", bean);

					// 追加分を一覧に反映
					mainView.tbList.addRow(getRowData(bean));

					// メインボタン制御
					setMainButtonEnabled(true);

					break;

				case COPY:

					// 新規登録 - 複写登録
					request(getModelClass(), "entry", bean);

					// 追加分を一覧に反映
					mainView.tbList.addRow(getRowData(bean));

					// メインボタン制御
					setMainButtonEnabled(true);

					break;

				case MODIFY:

					// 修正
					request(getModelClass(), "modify", bean);

					// 修正分を一覧に反映
					mainView.tbList.modifySelectedRow(getRowData(bean));

					break;
			}

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
	 * 編集画面初期化
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, Tag bean) {

		this.mode = editMode;
		switch (editMode) {

			case NEW:

				editView.setTitle(getWord("C01698"));
				editView.ctrlCompanyColor.setColor(DEFAULT_COLOR);

				break;

			case MODIFY:
			case COPY:

				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));
					editView.ctrlCode.setEditable(false);

				} else {

					editView.setTitle(getWord("C01738"));

				}

				editView.ctrlCode.setValue(bean.getCode());
				editView.ctrlCompanyColor.setColor(bean.getColor());
				editView.ctrlTitle.setValue(bean.getTitle());
				break;
		}
	}

	/**
	 * 編集画面の入力値取得
	 * 
	 * @return Employee
	 */
	protected Tag getInputtedTagMaster() {

		Tag bean = new Tag();

		bean.setCode(editView.ctrlCode.getValue());
		bean.setColor(editView.ctrlCompanyColor.getColor());
		bean.setTitle(editView.ctrlTitle.getValue());
		return bean;
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List getRowData(Tag bean) {

		List list = new ArrayList();

		list.add(bean);
		list.add(bean.getCode());
		list.add("");
		list.add(bean.getTitle());

		return list;
	}

	/**
	 * 一覧で選択したデータを取得
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected Tag getSelected() throws Exception {

		Tag bean = (Tag) mainView.tbList.getSelectedRowValueAt(MG0460TagMasterPanel.SC.bean);

		TagSearchCondition condition = new TagSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setTagCode(bean.getCode());

		List<Tag> list = getList(condition);

		// メッセージ追加必要
		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			throw new TException(getMessage("I00193"));
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
	protected List<Tag> getList(TagSearchCondition condition) throws Exception {
		List<Tag> list = (List<Tag>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * 指定行データを削除する
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(Tag bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return モデル
	 */
	protected Class getModelClass() {
		return TagManager.class;
	}

	/**
	 * 編集画面入力値の妥当性をチェック *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "CM0081");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一会社コード&&同一社員コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			TagSearchCondition condition = new TagSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setTagCode(editView.ctrlCode.getValue());

			List<Tag> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00697");
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}
		return true;

	}
}
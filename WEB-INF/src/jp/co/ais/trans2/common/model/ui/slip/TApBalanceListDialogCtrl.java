package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.slip.TBalanceListDialog.SC;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * 債務残高消込用ダイアログコントローラ
 */
public class TApBalanceListDialogCtrl extends TController {

	/** 「Cancel」が選択された場合にクラスメソッドから返される値 */
	public static final int CANCEL_OPTION = 0;

	/** 「確定」が選択された場合にクラスメソッドから返される値 */
	public static final int OK_OPTION = 1;

	/** 指示画面 */
	public TBalanceListDialog dialog;

	/** 親Container */
	protected Container parent;

	/** 固定通貨コード */
	protected String currencyCode = "";

	/** 基軸通貨コード */
	protected String keyCurrency = getCompany().getAccountConfig().getKeyCurrency().getCode();

	/** 基軸通貨 小数点桁数 */
	protected int keyDigit = getCompany().getAccountConfig().getKeyCurrency().getDecimalPoint();

	/** 結果 */
	protected int option = CANCEL_OPTION;

	/** 現在編集中の伝票番号 */
	protected String nowSlipNo;

	/** 既に消込選択しているデータ */
	protected List<AP_ZAN> nowEraseList = new LinkedList<AP_ZAN>();

	/** true:選択はチェックボックスを使用＜Client＞ */
	public static boolean isUseChk = ClientConfig.isFlagOn("trans.use.zan.dialog.select.check");

	/**
	 * コンストラクタ.
	 * 
	 * @param parent 親コンポーネント
	 */
	public TApBalanceListDialogCtrl(Container parent) {
		this.parent = parent;
		this.dialog = createView();

		// 指示画面を初期化する
		initView();

		// イベント設定
		addDialogEvent();
	}

	/**
	 * ダイアログ生成
	 * 
	 * @return ダイアログ
	 */
	protected TBalanceListDialog createView() {
		if (parent instanceof TPanel) {
			return new TBalanceListDialog(((TPanel) parent).getParentFrame());
		} else if (parent instanceof TDialog) {
			return new TBalanceListDialog(((TDialog) parent));
		} else if (parent instanceof TFrame) {
			return new TBalanceListDialog(((TFrame) parent));
		}

		return null;
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getView()
	 */
	@Override
	public TBalanceListDialog getView() {
		return dialog;
	}

	/**
	 * 画面を初期化する
	 */
	protected void initView() {
		// タイトルの設定
		dialog.setTitle(getWord("C10334"));// 債務残高一覧

		dialog.tbl.setTableKeyName(this.getClass().toString());

		// スプレッドの設定
		dialog.tbl.addColumn(TBalanceListDialog.SC.BEAN, "", -1);
		dialog.tbl.addColumn(TBalanceListDialog.SC.CHECK, "C00324", isUseChk ? 30 : -1, dialog.chk); // 選択
		dialog.tbl.addColumn(TBalanceListDialog.SC.CUSTOMER_NAME, getWord("C01690"), 200);// 支払先
		dialog.tbl.addColumn(TBalanceListDialog.SC.CLAlM_DATE, getWord("C00598"), 75, SwingConstants.CENTER);// 計上日
		dialog.tbl.addColumn(TBalanceListDialog.SC.SLIP_NO, getWord("C10384"), 130);// 計上伝票番号
		dialog.tbl.addColumn(TBalanceListDialog.SC.CLAlM_NO, getWord("C01708"), 130);// 請求書番号
		dialog.tbl.addColumn(TBalanceListDialog.SC.CLAlM_DATE, getWord("C01100"), 75, SwingConstants.CENTER);// 支払予定日
		dialog.tbl.addColumn(TBalanceListDialog.SC.CUR_CODE, getWord("C00371"), 40, SwingConstants.CENTER);// 通貨
		dialog.tbl
			.addColumn(TBalanceListDialog.SC.NON_ERASE_INPUT_AMOUNT, getWord("C10380"), 110, SwingConstants.RIGHT);// 未消込金額(外貨)
		dialog.tbl.addColumn(TBalanceListDialog.SC.NON_ERASE_AMOUNT, getWord("C10381"), 110, SwingConstants.RIGHT);// 未消込金額
		dialog.tbl.addColumn(TBalanceListDialog.SC.DEPARTMENT_NAME, getWord("C00863"), 150);// 計上部門
		dialog.tbl.addColumn(TBalanceListDialog.SC.REMARKS, getWord("C00384"), 300);// 摘要

		dialog.tbl.restoreComponent();// カラム状態の復元

		// 債務残高一覧の場合は非表示
		dialog.ctrlCustomerKind.setVisible(false);
		
		// 計上部門、伝票摘要検索欄の位置調整
		int width = dialog.getWidth();
		int adjSize = 40 + 110 * 2; // 通貨・金額×6
		
		int x = dialog.dtExpectedDay.getX() + dialog.dtExpectedDay.getWidth();
		int y = dialog.dtExpectedDay.getY();

		int txtDep = dialog.txtDep.getWidth();
		int txtTek = dialog.txtTek.getWidth();

		// 配置してダイアログサイズを超える場合は収まるように調整
		if (width > (x + adjSize + txtDep + txtTek)) {
			x += adjSize;
		} else {
			x = width - txtDep - txtTek - 20;
		}		
		dialog.txtDep.setLocation(x, y);
		x += dialog.txtDep.getWidth();
		dialog.txtTek.setLocation(x, y);
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addDialogEvent() {

		// 検索
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				doSearch();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

		});

		// 確定
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doSettle();
			}
		});

		// 閉じる
		dialog.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doClose();
			}

		});

		// 行選択時イベント
		dialog.tbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				if (!isUseChk) {
					selectedTableRow();
				}
			}
		});

		dialog.chk.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// 選択行に応じて、合計行を再計算する。
				summary();
			}

		});
		// 一覧入力
		dialog.tbl.setAdapter(new TTableAdapter() {

			/**
			 * ヘッダーをクリック後
			 * 
			 * @param column 列
			 */
			@Override
			public void afterHeaderClicked(int column) {
				if (dialog.tbl.getRowCount() == 0 || column < 0) {
					return;
				}

				if (SC.CHECK.ordinal() == column) {
					// 選択行に応じて、合計行を再計算する。
					summary();

					// クリックした列のフォーカスを当てる
					table.setColumnSelectionInterval(column, column);
				}
			}
		});
	}

	/**
	 * クラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getManagerClass() {
		return BalanceManager.class;
	}

	/**
	 * 既に消込選択しているデータをセット
	 * 
	 * @param list 消込行情報
	 */
	public void setNowEraseList(List<AP_ZAN> list) {
		this.nowEraseList = list;
	}

	/**
	 * 検索対象に含める消込伝票番号
	 * 
	 * @param slipNo 伝票番号
	 */
	public void setNowSlipNo(String slipNo) {
		this.nowSlipNo = slipNo;
	}

	/**
	 * 表示
	 * 
	 * @return 結果
	 */
	public int show() {

		// 表示
		dialog.setVisible(true);

		return option;
	}

	/**
	 * 検索処理
	 */
	protected void doSearch() {
		try {
			if (!checkInput()) {
				return;
			}

			// テーブル初期化
			dialog.tbl.removeRow();

			// データ抽出
			BalanceCondition condition = getCondition();

			List<AP_ZAN> list = (List<AP_ZAN>) request(getManagerClass(), "getApBalance", condition);

			if (list == null || list.isEmpty()) {
				showMessage(dialog, "I00022");
				dialog.txtCustomer.requestFocus();
				return;
			}

			// データが存在する場合、スプレッドに取得データを設定
			for (AP_ZAN bean : list) {
				// 既に選んでいる場合は除外
				if (nowEraseList.contains(bean)) {
					continue;
				}

				dialog.tbl.addRow(getRow(bean));
			}

			if (dialog.tbl.getRowCount() == 0) {
				showMessage(dialog, "I00022");
				dialog.txtCustomer.requestFocus();

			} else {
				// 行選択
				dialog.tbl.requestFocus();
				dialog.tbl.setRowSelection(0);
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:NG
	 */
	protected boolean checkInput() {
		if (!dialog.ctrlDepartmentRange.isSmallerFrom()) {
			showMessage("I00152", "C00467");// {0}は開始コード＜＝終了コードで指定してください。部門
			dialog.ctrlDepartmentRange.getFieldFrom().code.requestFocus();
			return false;
		}

		if (!dialog.ctrlClientRange.isSmallerFrom()) {
			showMessage("I00152", "C00408");// {0}は開始コード＜＝終了コードで指定してください。 取引先
			dialog.ctrlClientRange.getFieldFrom().code.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * テーブルに設定する配列データを取得する
	 * 
	 * @param bean
	 * @return 行データ
	 */
	protected List<Object> getRow(AP_ZAN bean) {
		// 小数点桁数
		int digit = bean.getZAN_CUR_DEC_KETA();

		List<Object> list = new ArrayList(SC.values().length);
		list.add(bean);
		list.add(false);
		list.add(bean.getZAN_SIHA_NAME_S()); // 支払先
		list.add(DateUtil.toYMDString(bean.getZAN_DEN_DATE())); // 計上日
		list.add(bean.getZAN_DEN_NO()); // 計上伝票番号
		list.add(bean.getZAN_SEI_NO()); // 請求書番号
		list.add(DateUtil.toYMDString(bean.getZAN_SIHA_DATE())); // 支払予定日
		list.add(keyCurrency.equals(bean.getZAN_CUR_CODE()) ? "" : bean.getZAN_CUR_CODE()); // 通貨
		list.add(NumberFormatUtil.formatNumber(bean.getZAN_IN_SIHA_KIN(), digit)); // 未消込金額(外貨)
		list.add(NumberFormatUtil.formatNumber(bean.getZAN_KIN(), keyDigit)); // 未消込金額
		list.add(bean.getZAN_DEP_NAME_S()); // 計上部門
		list.add(bean.getZAN_TEK()); // 摘要

		return list;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected BalanceCondition getCondition() {
		BalanceCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode()); // 会社コード
		condition.setCustomerNamesLike(dialog.txtCustomer.getText()); // 支払先
		condition.setSlipDate(dialog.dtPaymentDay.getValue()); // 計上日
		condition.setSlipNoLike(dialog.txtSlipNo.getText()); // 計上伝票番号
		condition.setBillNoLike(dialog.txtBillNo.getText()); // 請求書番号
		condition.setExpectedDay(dialog.dtExpectedDay.getValue()); // 支払予定日
		condition.setDepartmentNameLike(dialog.txtDep.getText()); // 計上部門
		condition.setSlipTekLike(dialog.txtTek.getText()); // 伝票摘要
		condition.setClientCodeFrom(dialog.ctrlClientRange.getCodeFrom()); // 開始取引先
		condition.setClientCodeTo(dialog.ctrlClientRange.getCodeTo());// 終了取引先
		condition.setDepartmentCodeFrom(dialog.ctrlDepartmentRange.getCodeFrom());// 開始部門
		condition.setDepartmentCodeTo(dialog.ctrlDepartmentRange.getCodeTo());// 終了部門
		condition.setNotIncledEraseSlipNo(this.nowSlipNo); // 編集中の消込伝票番号
		condition.setCurrencyCode(currencyCode); // 通貨コード
		return condition;
	}

	/**
	 * 検索条件生成
	 * 
	 * @return condition
	 */
	protected BalanceCondition createCondition() {
		BalanceCondition condition = new BalanceCondition();
		return condition;
	}

	/**
	 * 確定処理
	 */
	protected void doSettle() {
		try {
			// 排他
			List<AP_ZAN> list = getSelectedList();
			List<BalanceCondition> lockList = new ArrayList<BalanceCondition>();

			String compCode = getCompanyCode();

			for (AP_ZAN zan : list) {
				BalanceCondition condition = new BalanceCondition();
				condition.setCompanyCode(compCode);
				condition.setSlipNo(zan.getZAN_DEN_NO());
				condition.setSlipLineNo(zan.getZAN_GYO_NO());
				condition.setNotIncledEraseSlipNo(this.nowSlipNo); // 編集中の消込伝票番号
				lockList.add(condition);
			}

			request(getManagerClass(), "lockApBalance", lockList);

			this.option = OK_OPTION;

			dialog.dispose();

		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 取消ボタン押下
	 */
	protected void doClose() {
		this.option = CANCEL_OPTION;

		dialog.dispose();
	}

	/**
	 * 選択行の変更
	 */
	protected void selectedTableRow() {
		// 確定ボタン制御
		dialog.btnSettle.setEnabled(dialog.tbl.getSelectedRows().length != 0);

		// 選択行に応じて、合計行を再計算する。
		summary();
	}

	/**
	 * 合計を計算する
	 */
	protected void summary() {

		// 変数初期化
		BigDecimal total = BigDecimal.ZERO;// 合計

		int keta = 0;

		// 選択行の取得
		boolean isSelected = false;
		for (AP_ZAN zan : getSelectedList()) {
			// 表示桁
			keta = keta < zan.getZAN_CUR_DEC_KETA() ? zan.getZAN_CUR_DEC_KETA() : keta;

			total = total.add(zan.getZAN_IN_SIHA_KIN());
	
			isSelected = true;
		}
		// 確定ボタン制御
		dialog.btnSettle.setEnabled(isSelected);

		// 合計値の設定
		dialog.ctrlTotal.setDecimalPoint(keta);
		dialog.ctrlTotal.setNumber(total);
	}

	/**
	 * 選択行データの取得
	 * 
	 * @return 残高リスト
	 */
	public List<AP_ZAN> getSelectedList() {
		
		List<AP_ZAN> list = null;

		if (isUseChk) {
			list = new ArrayList<AP_ZAN>();
			for (int i = 0; i < dialog.tbl.getRowCount(); i++) {
				Boolean isChecked = (Boolean) dialog.tbl.getRowValueAt(i, SC.CHECK);

				if (isChecked) {
					list.add((AP_ZAN) dialog.tbl.getRowValueAt(i, SC.BEAN));
				}
			}
		} else {
			int[] rows = dialog.tbl.getSelectedRows();
			list = new ArrayList<AP_ZAN>(rows.length);

			for (int i = 0; i < rows.length; i++) {
				list.add((AP_ZAN) dialog.tbl.getRowValueAt(rows[i], SC.BEAN));
			}
		}

		return list;
	}

	/**
	 * 一覧の選択形式を設定する
	 * 
	 * @param model
	 */
	public void setSelectionMode(int model) {
		dialog.tbl.setSelectionMode(model);
	}

	/**
	 * 取引先を設定する。（固定表示）
	 * 
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		dialog.ctrlClientRange.getFieldFrom().setEntity(customer);
		dialog.ctrlClientRange.getFieldTo().setEntity(customer);
		dialog.ctrlClientRange.getFieldFrom().setEditable(false);
		dialog.ctrlClientRange.getFieldTo().setEditable(false);
	}

	/**
	 * 固定通貨コードを設定する。
	 * 
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}

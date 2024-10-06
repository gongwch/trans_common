package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 銀行口座任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TBankAccountOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected BankAccountSearchCondition condition;

	/**
	 * @param field
	 */
	public TBankAccountOptionalSelectorController(TBankAccountOptionalSelector field) {
		super(field);
	}

	/**
	 * 初期化
	 */
	@Override
	protected void init() {

		super.init();

		// 検索条件初期化
		initSearchCondition();

	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected BankAccountSearchCondition createSearchCondition() {
		return new BankAccountSearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	@Override
	protected void initTable() {

		// 銀行口座コード
		dialog.tblList.addColumn(TBankAccountOptionalSelectorDialog.SC.code, getWord("C01879"), 100);
		// 銀行口座名称
		dialog.tblList.addColumn(TBankAccountOptionalSelectorDialog.SC.names, getWord("C02145"), 200);
		dialog.tblList.addColumn(TBankAccountOptionalSelectorDialog.SC.entity, "", -1);

		// 銀行口座コード
		dialog.tblSelectedList.addColumn(TBankAccountOptionalSelectorDialog.SC.code, getWord("C01879"), 100);
		// 銀行口座名称
		dialog.tblSelectedList.addColumn(TBankAccountOptionalSelectorDialog.SC.names, getWord("C02145"), 200);
		dialog.tblSelectedList.addColumn(TBankAccountOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// 銀行口座選択
		return getWord("C11102");
	}

	/**
	 * 選択候補テーブルの一覧をリフレッシュ(再取得)
	 */
	@Override
	public void refresh() {

		try {

			// 確定ボタンを押下不可にする
			dialog.btnSettle.setEnabled(false);

			// 一覧をクリアする
			dialog.tblSelectedList.removeRow();
			dialog.tblList.removeRow();
			field.cbo.removeAllItems();
			field.cbo.setEnabled(false);

			// データを抽出する
			List<BankAccount> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (BankAccount bean : list) {
				dialog.tblList.addRow(new Object[] { bean.getCode(), bean.getName(), bean });
			}

			// 確定ボタンを押下可能にする
			dialog.btnSettle.setEnabled(true);

			// 1行目を選択
			dialog.tblList.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * @param condition1
	 * @return 銀行口座情報
	 */
	@SuppressWarnings("unchecked")
	protected List<BankAccount> getList(BankAccountSearchCondition condition1) {

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";
			}

			List<BankAccount> list = (List<BankAccount>) request(getModelClass(), method, condition1);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servletのclassを返す
	 * 
	 * @return class
	 */
	protected Class getModelClass() {
		return BankAccountManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public BankAccountSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(BankAccountSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [一覧から選択]ボタン押下
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.names);
			BankAccount cus = (BankAccount) dialog.tblList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, cus });
		}

		// 選択元から削除
		dialog.tblList.removeSelectedRows();

	}

	/**
	 * [一覧から選択を取消]ボタン押下
	 */
	@Override
	protected void btnTableCancel_Click() {

		int selectedRows[] = dialog.tblSelectedList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.names);
			BankAccount bankAccount = (BankAccount) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TBankAccountOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, bankAccount });
		}

		// 選択先から削除
		dialog.tblSelectedList.removeSelectedRows();

	}

	/**
	 * [確定]ボタン押下
	 */
	@Override
	protected void btnSettle_Click() {

		field.cbo.removeAllItems();

		// 選択された銀行口座一覧を取得
		List<BankAccount> list = new ArrayList<BankAccount>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			BankAccount bankAccount = new BankAccount();
			bankAccount.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TBankAccountOptionalSelector.SC.code));
			bankAccount
				.setName((String) dialog.tblSelectedList.getRowValueAt(i, TBankAccountOptionalSelector.SC.names));
			list.add(bankAccount);
		}

		// 呼び出し元にセット
		for (BankAccount bankAccount : list) {
			field.cbo.addItem(bankAccount.getCode() + " " + bankAccount.getName());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TBankAccountOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 選択された銀行口座Entity一覧を返す
	 * 
	 * @return 選択された銀行口座Entity一覧
	 */
	public List<BankAccount> getEntities() {

		List<BankAccount> list = new ArrayList<BankAccount>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				BankAccount bankAccount = (BankAccount) dialog.tblSelectedList.getRowValueAt(i,
					TBankAccountOptionalSelectorDialog.SC.entity);
				list.add(bankAccount);
			}
		}

		return list;
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TBankAccountOptionalSelector getField() {
		return (TBankAccountOptionalSelector) field;
	}

	/**
	 * 選択されたコードリストを返す
	 * 
	 * @return 選択されたコードリスト
	 */
	@Override
	public List<String> getCodeList() {

		List<String> list = new ArrayList<String>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				BankAccount bankAccount = (BankAccount) dialog.tblSelectedList.getRowValueAt(i,
					TBankAccountOptionalSelectorDialog.SC.entity);
				list.add(bankAccount.getCode());
			}
		}

		return list;

	}

}

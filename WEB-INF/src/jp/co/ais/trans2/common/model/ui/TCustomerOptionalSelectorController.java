package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * 取引先任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TCustomerOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected CustomerSearchCondition condition;

	/**
	 * @param field
	 */
	public TCustomerOptionalSelectorController(TCustomerOptionalSelector field) {
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
	protected CustomerSearchCondition createSearchCondition() {
		return new CustomerSearchCondition();
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

		// 取引先コード
		dialog.tblList.addColumn(TCustomerOptionalSelectorDialog.SC.code, getWord("C00786"), 100);
		// 取引先略称
		dialog.tblList.addColumn(TCustomerOptionalSelectorDialog.SC.names, getWord("C00787"), 200);
		dialog.tblList.addColumn(TCustomerOptionalSelectorDialog.SC.entity, "", -1);

		// 取引先コード
		dialog.tblSelectedList.addColumn(TCustomerOptionalSelectorDialog.SC.code, getWord("C00786"), 100);
		// 取引先略称
		dialog.tblSelectedList.addColumn(TCustomerOptionalSelectorDialog.SC.names, getWord("C00787"), 200);
		dialog.tblSelectedList.addColumn(TCustomerOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// 取引先選択
		return getWord("C02672");
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
			List<Customer> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (Customer bean : list) {
				dialog.tblList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
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
	 * @return 取引先情報
	 */
	@SuppressWarnings("unchecked")
	protected List<Customer> getList(CustomerSearchCondition condition1) {

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";

				// 会社コードを取得
				List<String> companyCodeList = TCompanyClientUtil.getCodeList(this, getField().getCompanyOrgUnit());

				if (companyCodeList == null || companyCodeList.isEmpty()) {
					// 会社が取得出来ない
					return null;
				}

				condition1.setCompanyCodeList(companyCodeList);
			} else {
				condition1.setCompanyCodeList(null);
			}

			List<Customer> list = (List<Customer>) request(getModelClass(), method, condition1);
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
		return CustomerManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public CustomerSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(CustomerSearchCondition condition) {
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
				TCustomerOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TCustomerOptionalSelectorDialog.SC.names);
			Customer cus = (Customer) dialog.tblList.getRowValueAt(selectedRows[i],
				TCustomerOptionalSelectorDialog.SC.entity);

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
				TCustomerOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TCustomerOptionalSelectorDialog.SC.names);
			Customer cus = (Customer) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TCustomerOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, cus });
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

		// 選択された取引先一覧を取得
		List<Customer> list = new ArrayList<Customer>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Customer customer = new Customer();
			customer.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TCustomerOptionalSelector.SC.code));
			customer.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TCustomerOptionalSelector.SC.names));
			list.add(customer);
		}

		// 呼び出し元にセット
		for (Customer customer : list) {
			field.cbo.addItem(customer.getCode() + " " + customer.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TCustomerOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 選択された取引先Entity一覧を返す
	 * 
	 * @return 選択された取引先Entity一覧
	 */
	public List<Customer> getEntities() {

		List<Customer> list = new ArrayList<Customer>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Customer customer = (Customer) dialog.tblSelectedList.getRowValueAt(i,
					TCustomerOptionalSelectorDialog.SC.entity);
				list.add(customer);
			}
		}

		return list;
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TCustomerOptionalSelector getField() {
		return (TCustomerOptionalSelector) field;
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
				Customer customer = (Customer) dialog.tblSelectedList.getRowValueAt(i,
					TCustomerOptionalSelectorDialog.SC.entity);
				list.add(customer.getCode());
			}
		}

		return list;

	}

	/**
	 * 検索条件のコードリストをセット（初期値）
	 * 
	 * @param codeList
	 */
	@Override
	public void setCodeList(List<String> codeList) {
		// 個別選択ダイアログ取得
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}
		if (codeList == null || codeList.isEmpty()) {
			return;
		}
		condition.setCustomerCodeList(codeList.toArray(new String[0]));
		List<Customer> list = null;
		try {
			list = (List<Customer>) request(getModelClass(), "get", condition);
		} catch (TException e) {
			//
		}
		if (list == null || list.isEmpty()) {
			return;
		}

		// 一覧にセット
		for (Customer bean : list) {
			dialog.tblSelectedList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
		}

		// 疑似的に確定
		btnSettle_Click();
		// Vector退避処理
		saveSelectedListData();
	}

}

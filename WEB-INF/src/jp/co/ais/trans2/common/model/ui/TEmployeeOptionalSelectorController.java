package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * 社員任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TEmployeeOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected EmployeeSearchCondition condition;

	/**
	 * @param field
	 */
	public TEmployeeOptionalSelectorController(TEmployeeOptionalSelector field) {
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
	protected EmployeeSearchCondition createSearchCondition() {
		return new EmployeeSearchCondition();
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

		// 社員コード
		dialog.tblList.addColumn(TEmployeeOptionalSelectorDialog.SC.code, getWord("C00697"), 100);
		// 社員略称
		dialog.tblList.addColumn(TEmployeeOptionalSelectorDialog.SC.names, getWord("C00808"), 200);
		dialog.tblList.addColumn(TEmployeeOptionalSelectorDialog.SC.entity, "", -1);

		// 社員コード
		dialog.tblSelectedList.addColumn(TEmployeeOptionalSelectorDialog.SC.code, getWord("C00697"), 100);
		// 社員略称
		dialog.tblSelectedList.addColumn(TEmployeeOptionalSelectorDialog.SC.names, getWord("C00808"), 200);
		dialog.tblSelectedList.addColumn(TEmployeeOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// 社員選択
		return getWord("C11130");
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
			List<Employee> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (Employee bean : list) {
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
	 * @return 社員情報
	 */
	@SuppressWarnings("unchecked")
	protected List<Employee> getList(EmployeeSearchCondition condition1) {

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

			List<Employee> list = (List<Employee>) request(getModelClass(), method, condition1);
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
		return EmployeeManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public EmployeeSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(EmployeeSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 一覧から選択時
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.names);
			Employee emp = (Employee) dialog.tblList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, emp });
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
				TEmployeeOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.names);
			Employee emp = (Employee) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TEmployeeOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, emp });
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

		// 選択された社員一覧を取得
		List<Employee> list = new ArrayList<Employee>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Employee employee = new Employee();
			employee.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TEmployeeOptionalSelector.SC.code));
			employee.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TEmployeeOptionalSelector.SC.names));
			list.add(employee);
		}

		// 呼び出し元にセット
		for (Employee employee : list) {
			field.cbo.addItem(employee.getCode() + " " + employee.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TEmployeeOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 選択された社員Entity一覧を返す
	 * 
	 * @return 選択された社員Entity一覧
	 */
	public List<Employee> getEntities() {

		List<Employee> list = new ArrayList<Employee>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Employee emp = (Employee) dialog.tblSelectedList.getRowValueAt(i,
					TEmployeeOptionalSelectorDialog.SC.entity);
				list.add(emp);
			}
		}

		return list;
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TEmployeeOptionalSelector getField() {
		return (TEmployeeOptionalSelector) field;
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
				Employee emp = (Employee) dialog.tblSelectedList.getRowValueAt(i,
					TEmployeeOptionalSelectorDialog.SC.entity);
				list.add(emp.getCode());
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
		condition.setEmployeeCodeList(codeList.toArray(new String[0]));
		List<Employee> list = null;
		try {
			list = (List<Employee>) request(getModelClass(), "get", condition);
		} catch (TException e) {
			//
		}
		if (list == null || list.isEmpty()) {
			return;
		}

		// 一覧にセット
		for (Employee bean : list) {
			dialog.tblSelectedList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
		}

		// 疑似的に確定
		btnSettle_Click();
		// Vector退避処理
		saveSelectedListData();
	}
}

package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TDepartmentOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected DepartmentSearchCondition condition;

	/**
	 * @param field
	 */
	public TDepartmentOptionalSelectorController(TDepartmentOptionalSelector field) {
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
	protected DepartmentSearchCondition createSearchCondition() {
		return new DepartmentSearchCondition();
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

		dialog.tblList.addColumn(TDepartmentOptionalSelectorDialog.SC.code, getWord("C00698"), 100); // 部門コード
		dialog.tblList.addColumn(TDepartmentOptionalSelectorDialog.SC.names, getWord("C00724"), 200); // 部門略称
		dialog.tblList.addColumn(TDepartmentOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TDepartmentOptionalSelectorDialog.SC.code, getWord("C00698"), 100); // 部門コード
		dialog.tblSelectedList.addColumn(TDepartmentOptionalSelectorDialog.SC.names, getWord("C00724"), 200); // 部門略称
		dialog.tblSelectedList.addColumn(TDepartmentOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		return "C10978"; // 部門選択
	}

	/**
	 * 選択候補テーブルの一覧セット
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
			List<Department> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (Department bean : list) {
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
	 * @param param
	 * @return 部門情報
	 */
	protected List<Department> getList(DepartmentSearchCondition param) {

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

				param.setCompanyCodeList(companyCodeList);
			} else {
				param.setCompanyCodeList(null);
			}

			List<Department> list = (List<Department>) request(getModelClass(), method, param);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servletのclassを返す
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public DepartmentSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(DepartmentSearchCondition condition) {
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
				TDepartmentOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.names);
			Department dep = (Department) dialog.tblList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, dep });
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
				TDepartmentOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.names);
			Department dep = (Department) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TDepartmentOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, dep });
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

		// 選択された部門一覧を取得
		List<Department> list = new ArrayList<Department>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Department department = new Department();
			department.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TDepartmentOptionalSelector.SC.code));
			department.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TDepartmentOptionalSelector.SC.names));
			list.add(department);
		}

		// 呼び出し元にセット
		for (Department department : list) {
			field.cbo.addItem(department.getCode() + " " + department.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TDepartmentOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 選択された部門Entity一覧を返す
	 * 
	 * @return 選択された部門Entity一覧
	 */
	public List<Department> getDeptratmentEntities() {

		List<Department> list = new ArrayList<Department>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Department dep = (Department) dialog.tblSelectedList.getRowValueAt(i,
					TDepartmentOptionalSelectorDialog.SC.entity);
				list.add(dep);
			}
		}

		return list;
	}

	/**
	 * 部門Entity一覧を設定する
	 * 
	 * @param list 部門Entity一覧
	 */
	public void setDeptratmentEntities(List<Department> list) {

		// 個別選択ダイアログ取得
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}
		for (Department department : list) {
			dialog.tblSelectedList.addRow(new Object[] { department.getCode(), department.getNames(), department });
		}
		// エンティティセットして疑似確定
		btnSettle_Click();
		// Vector退避処理
		saveSelectedListData();
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TDepartmentOptionalSelector getField() {
		return (TDepartmentOptionalSelector) field;
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
				Department dep = (Department) dialog.tblSelectedList.getRowValueAt(i,
					TDepartmentOptionalSelectorDialog.SC.entity);
				list.add(dep.getCode());
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
		condition.setDepartmentCodeList(codeList.toArray(new String[0]));
		List<Department> list = null;
		try {
			list = (List<Department>) request(getModelClass(), "get", condition);
		} catch (TException e) {
			//
		}
		if (list == null || list.isEmpty()) {
			return;
		}

		// 一覧にセット
		for (Department bean : list) {
			dialog.tblSelectedList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
		}

		// 疑似的に確定
		btnSettle_Click();
		// Vector退避処理
		saveSelectedListData();
	}

}

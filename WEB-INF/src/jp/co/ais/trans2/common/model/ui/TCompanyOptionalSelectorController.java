package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.TCompanyOptionalSelectorDialog.CMP_SEL_SC;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TCompanyOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected CompanySearchCondition condition;

	/**
	 * @param field
	 */
	public TCompanyOptionalSelectorController(TCompanyOptionalSelector field) {
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
	protected CompanySearchCondition createSearchCondition() {
		return new CompanySearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
	}

	@Override
	protected void initTable() {

		dialog.tblList.addColumn(CMP_SEL_SC.entity, "", -1);
		dialog.tblList.addColumn(CMP_SEL_SC.code, getWord("C00596"), 100); // 会社コード
		dialog.tblList.addColumn(CMP_SEL_SC.name, getWord("C00685"), 200); // 会社名称

		dialog.tblSelectedList.addColumn(CMP_SEL_SC.entity, "", -1);
		dialog.tblSelectedList.addColumn(CMP_SEL_SC.code, getWord("C00596"), 100); // 会社コード
		dialog.tblSelectedList.addColumn(CMP_SEL_SC.name, getWord("C00685"), 200); // 会社名称

	}

	@Override
	protected String getDialogCaption() {
		return "C11365"; // 会社選択
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
			List<Company> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (Company bean : list) {
				dialog.tblList.addRow(new Object[] { bean, bean.getCode(), bean.getName() });
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
	 * @return 会社情報
	 */
	protected List<Company> getList(CompanySearchCondition param) {

		try {
			List<Company> list = (List<Company>) request(getModelClass(), "get", param);
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
		return CompanyManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public CompanySearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(CompanySearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [一覧から選択]ボタン押下
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			Company bean = (Company) dialog.tblList.getRowValueAt(selectedRows[i], CMP_SEL_SC.entity);
			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], CMP_SEL_SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i], CMP_SEL_SC.name);

			dialog.tblSelectedList.addRow(new Object[] { bean, code, names });
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

			Company bean = (Company) dialog.tblSelectedList.getRowValueAt(selectedRows[i], CMP_SEL_SC.entity);
			String code = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i], CMP_SEL_SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i], CMP_SEL_SC.name);

			dialog.tblList.addRow(new Object[] { bean, code, names });
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

		// 選択された会社一覧を取得
		List<Company> list = new ArrayList<Company>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Company bean = new Company();
			bean.setCode((String) dialog.tblSelectedList.getRowValueAt(i, CMP_SEL_SC.code));
			bean.setNames((String) dialog.tblSelectedList.getRowValueAt(i, CMP_SEL_SC.name));
			list.add(bean);
		}

		// 呼び出し元にセット
		for (Company bean : list) {
			field.cbo.addItem(bean.getCode() + " " + bean.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TCompanyOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 選択された会社Entity一覧を返す
	 * 
	 * @return 選択された会社Entity一覧
	 */
	public List<Company> getEntities() {

		List<Company> list = new ArrayList<Company>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Company dep = (Company) dialog.tblSelectedList.getRowValueAt(i, CMP_SEL_SC.entity);
				list.add(dep);
			}
		}

		return list;
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
				Company cmp = (Company) dialog.tblSelectedList.getRowValueAt(i, CMP_SEL_SC.entity);
				list.add(cmp.getCode());
			}
		}

		return list;

	}

}

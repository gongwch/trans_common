package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理4の任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TManagement4OptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected Management4SearchCondition condition;

	/**
	 * @param field
	 */
	public TManagement4OptionalSelectorController(TManagement4OptionalSelector field) {
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
	protected Management4SearchCondition createSearchCondition() {
		return new Management4SearchCondition();
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

		// コード
		dialog.tblList.addColumn(TManagement4OptionalSelectorDialog.SC.code, getCompany().getAccountConfig()
			.getManagement4Name() + getWord("C00174"), 100);
		// 略称
		dialog.tblList.addColumn(TManagement4OptionalSelectorDialog.SC.names, getCompany().getAccountConfig()
			.getManagement4Name() + getWord("C00548"), 200);
		dialog.tblList.addColumn(TManagement4OptionalSelectorDialog.SC.entity, "", -1);

		// コード
		dialog.tblSelectedList.addColumn(TManagement4OptionalSelectorDialog.SC.code, getCompany().getAccountConfig()
			.getManagement4Name() + getWord("C00174"), 100);
		// 略称
		dialog.tblSelectedList.addColumn(TManagement4OptionalSelectorDialog.SC.names, getCompany().getAccountConfig()
			.getManagement4Name() + getWord("C00548"), 200);
		dialog.tblSelectedList.addColumn(TManagement4OptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// 選択
		return getCompany().getAccountConfig().getManagement4Name() + getWord("C00324");
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
			List<Management4> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (Management4 bean : list) {
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
	 * @return 管理４情報
	 */
	@SuppressWarnings("unchecked")
	protected List<Management4> getList(Management4SearchCondition condition1) {

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

			List<Management4> list = (List<Management4>) request(getModelClass(), method, condition1);
			return list;
		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * Servletのclassを返す
	 * 
	 * @return class
	 */
	protected Class getModelClass() {
		return Management4Manager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public Management4SearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(Management4SearchCondition condition) {
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
				TManagement4OptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TManagement4OptionalSelectorDialog.SC.names);
			Management4 ma = (Management4) dialog.tblList.getRowValueAt(selectedRows[i],
				TManagement4OptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, ma });
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
				TManagement4OptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TManagement4OptionalSelectorDialog.SC.names);
			Management4 ma = (Management4) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TManagement4OptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, ma });
		}

		// 選択先から削除
		dialog.tblSelectedList.removeSelectedRows();

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TManagement4OptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * [確定]ボタン押下
	 */
	@Override
	protected void btnSettle_Click() {

		field.cbo.removeAllItems();

		// 選択された管理4一覧を取得
		List<Management4> list = new ArrayList<Management4>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Management4 management4 = new Management4();
			management4.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TManagement4OptionalSelector.SC.code));
			management4.setNames((String) dialog.tblSelectedList
				.getRowValueAt(i, TManagement4OptionalSelector.SC.names));
			list.add(management4);
		}

		// 呼び出し元にセット
		for (Management4 management4 : list) {
			field.cbo.addItem(management4.getCode() + " " + management4.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	/**
	 * 選択された管理4Entity一覧を返す
	 * 
	 * @return 選択された管理4Entity一覧
	 */
	public List<Management4> getEntities() {

		List<Management4> list = new ArrayList<Management4>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Management4 ma = (Management4) dialog.tblSelectedList.getRowValueAt(i,
					TManagement4OptionalSelectorDialog.SC.entity);
				list.add(ma);
			}
		}

		return list;
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TManagement4OptionalSelector getField() {
		return (TManagement4OptionalSelector) field;
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
				Management4 ma = (Management4) dialog.tblSelectedList.getRowValueAt(i,
					TManagement4OptionalSelectorDialog.SC.entity);
				list.add(ma.getCode());
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
		condition.setManagement4CodeList(codeList.toArray(new String[0]));
		List<Management4> list = null;
		try {
			list = (List<Management4>) request(getModelClass(), "get", condition);
		} catch (TException e) {
			//
		}
		if (list == null || list.isEmpty()) {
			return;
		}

		// 一覧にセット
		for (Management4 bean : list) {
			dialog.tblSelectedList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
		}

		// 疑似的に確定
		btnSettle_Click();
		// Vector退避処理
		saveSelectedListData();
	}
}

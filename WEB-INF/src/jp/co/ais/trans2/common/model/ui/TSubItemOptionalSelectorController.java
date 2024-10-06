package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 補助科目任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TSubItemOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected SubItemSearchCondition condition;

	/**
	 * @param field
	 */
	public TSubItemOptionalSelectorController(TSubItemOptionalSelector field) {
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
	protected SubItemSearchCondition createSearchCondition() {
		return new SubItemSearchCondition();
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

		dialog.tblList.addColumn(TSubItemOptionalSelectorDialog.SC.code, getCompany().getAccountConfig()
			.getSubItemName() + getWord("C00174"), 100); // コード
		dialog.tblList.addColumn(TSubItemOptionalSelectorDialog.SC.names, getCompany().getAccountConfig()
			.getSubItemName() + getWord("C00548"), 200); // 略称
		dialog.tblList.addColumn(TSubItemOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TSubItemOptionalSelectorDialog.SC.code, getCompany().getAccountConfig()
			.getSubItemName() + getWord("C00174"), 100); // コード
		dialog.tblSelectedList.addColumn(TSubItemOptionalSelectorDialog.SC.names, getCompany().getAccountConfig()
			.getSubItemName() + getWord("C00548"), 200); // 略称
		dialog.tblSelectedList.addColumn(TSubItemOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		return getCompany().getAccountConfig().getSubItemName() + getWord("C00324"); // 選択
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
			dialog.tblList.removeRow();
			dialog.tblSelectedList.removeRow();
			field.cbo.removeAllItems();
			field.cbo.setEnabled(false);

			// データを抽出する
			List<Item> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (Item bean : list) {
				dialog.tblList.addRow(new Object[] { bean.getSubItemCode(), bean.getSubItemNames(), bean });
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
	 * @param con
	 * @return 補助科目情報
	 */
	protected List<Item> getList(SubItemSearchCondition con) {

		try {
			List<Item> list = (List<Item>) request(getModelClass(), "get", con);
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
		return ItemManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public SubItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(SubItemSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [一覧から選択]ボタン押下
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList
				.getRowValueAt(selectedRows[i], TSubItemOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TSubItemOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblList.getRowValueAt(selectedRows[i], TSubItemOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, item });
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
				TSubItemOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TSubItemOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TSubItemOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, item });
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

		// 選択された補助科目一覧を取得
		List<Item> list = new ArrayList<Item>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Item item = new Item();
			item.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TSubItemOptionalSelector.SC.code));
			item.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TSubItemOptionalSelector.SC.names));
			list.add(item);
		}

		// 呼び出し元にセット
		for (Item item : list) {
			field.cbo.addItem(item.getCode() + " " + item.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TSubItemOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 選択された補助科目Entity一覧を返す
	 * 
	 * @return 選択された補助科目Entity一覧
	 */
	public List<Item> getEntities() {

		List<Item> list = new ArrayList<Item>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TSubItemOptionalSelectorDialog.SC.entity);
				list.add(item);
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
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TSubItemOptionalSelectorDialog.SC.entity);
				list.add(item.getSubItemCode());
			}
		}

		return list;

	}

}

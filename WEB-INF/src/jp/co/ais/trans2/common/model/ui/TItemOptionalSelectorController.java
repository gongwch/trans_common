package jp.co.ais.trans2.common.model.ui;

import java.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TItemOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected ItemSearchCondition condition;

	/**
	 * @param field
	 */
	public TItemOptionalSelectorController(TItemOptionalSelector field) {
		super(field);
	}

	/**
	 * 初期化
	 */
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
	protected ItemSearchCondition createSearchCondition() {
		return new ItemSearchCondition();
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

		dialog.tblList.addColumn(TItemOptionalSelectorDialog.SC.code,
		// コード
			getCompany().getAccountConfig().getItemName() + getWord("C00174"), 100);
		dialog.tblList.addColumn(TItemOptionalSelectorDialog.SC.names,
		// 略称
			getCompany().getAccountConfig().getItemName() + getWord("C00548"), 200);
		dialog.tblList.addColumn(TItemOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TItemOptionalSelectorDialog.SC.code,
		// コード
			getCompany().getAccountConfig().getItemName() + getWord("C00174"), 100);
		dialog.tblSelectedList.addColumn(TItemOptionalSelectorDialog.SC.names,
		// 略称
			getCompany().getAccountConfig().getItemName() + getWord("C00548"), 200);
		dialog.tblSelectedList.addColumn(TItemOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// 選択
		return getCompany().getAccountConfig().getItemName() + getWord("C00324");
	}

	/**
	 * 選択候補テーブルの一覧をリフレッシュ(再取得)
	 */
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
	 * @param condition
	 * @return 科目情報
	 */
	@SuppressWarnings("unchecked")
	protected List<Item> getList(ItemSearchCondition condition) {

		try {
			List<Item> list = (List<Item>) request(getModelClass(), "get", condition);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servletのclassを返す
	 * 
	 * @return
	 */
	protected Class getModelClass() {
		return ItemManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public ItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(ItemSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [一覧から選択]ボタン押下
	 */
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], TItemOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i], TItemOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblList.getRowValueAt(selectedRows[i], TItemOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, item });
		}

		// 選択元から削除
		dialog.tblList.removeSelectedRows();

	}

	/**
	 * [一覧から選択を取消]ボタン押下
	 */
	protected void btnTableCancel_Click() {

		int selectedRows[] = dialog.tblSelectedList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TItemOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TItemOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TItemOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, item });
		}

		// 選択先から削除
		dialog.tblSelectedList.removeSelectedRows();

	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		field.cbo.removeAllItems();

		// 選択された科目一覧を取得
		List<Item> list = new ArrayList<Item>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Item item = new Item();
			item.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TItemOptionalSelector.SC.code));
			item.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TItemOptionalSelector.SC.names));
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
		return new TItemOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 選択された科目Entity一覧を返す
	 * 
	 * @return 選択された科目Entity一覧
	 */
	public List<Item> getEntities() {

		List<Item> list = new ArrayList<Item>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TItemOptionalSelectorDialog.SC.entity);
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
	public List<String> getCodeList() {

		List<String> list = new ArrayList<String>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TItemOptionalSelectorDialog.SC.entity);
				list.add(item.getCode());
			}
		}

		return list;

	}

}

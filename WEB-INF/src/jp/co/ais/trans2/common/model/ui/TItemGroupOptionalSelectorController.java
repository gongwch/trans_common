package jp.co.ais.trans2.common.model.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.Util;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.ItemLevel;
import jp.co.ais.trans2.model.company.AccountConfig;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目+補助科目+内訳科目任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TItemGroupOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected ItemSearchCondition condition;

	/** 補助科目検索条件 */
	protected SubItemSearchCondition subItemCondition;

	/** 内訳科目検索条件 */
	protected DetailItemSearchCondition detailItemCondition;

	/** 科目レベル */
	protected ItemLevel itemLevel = null;

	/**
	 * @param field
	 */
	public TItemGroupOptionalSelectorController(TItemGroupOptionalSelector field) {
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
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = new ItemSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		subItemCondition = new SubItemSearchCondition();
		subItemCondition.setCompanyCode(TLoginInfo.getCompany().getCode());
		detailItemCondition = new DetailItemSearchCondition();
		detailItemCondition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	@Override
	protected void initTable() {

		dialog.tblList.addColumn(TItemGroupOptionalSelectorDialog.SC.code, getWord("C00174"), 100); // コード
		dialog.tblList.addColumn(TItemGroupOptionalSelectorDialog.SC.names, getWord("C00548"), 200); // 略称
		dialog.tblList.addColumn(TItemGroupOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TItemGroupOptionalSelectorDialog.SC.code, getWord("C00174"), 100); // コード
		dialog.tblSelectedList.addColumn(TItemGroupOptionalSelectorDialog.SC.names, getWord("C00548"), 200); // 略称
		dialog.tblSelectedList.addColumn(TItemGroupOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		return getCompany().getAccountConfig().getItemName() + getWord("C00324"); // 選択
	}

	/**
	 * 選択候補テーブルの一覧をリフレッシュ(再取得)
	 */
	public void refresh() {

		try {

			// 確定ボタンを押下不可にする
			dialog.btnSettle.setEnabled(false);

			// 一覧をクリアする
			dialog.tblSelectedList.removeRow();
			dialog.tblList.removeRow();

			// データを抽出する
			List<Item> list = null;
			ItemLevel itemLevel = ((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.getItemLevel();

			// 科目レベルの場合
			if (ItemLevel.ITEM == itemLevel) {
				list = getList(getCondition());
			}

			// 補助科目レベルの場合
			else if (ItemLevel.SUBITEM == itemLevel) {
				list = getList(getSubItemCondition());
			}

			// 内訳科目レベルの場合
			else if (ItemLevel.DETAILITEM == itemLevel) {
				list = getList(getDetailItemCondition());
			}

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (Item bean : list) {

				// 科目レベルの場合
				if (ItemLevel.ITEM == itemLevel) {
					dialog.tblList.addRow(new Object[] { bean.getCode(), bean.getNames(), bean });
				}

				// 補助科目レベルの場合
				else if (ItemLevel.SUBITEM == itemLevel) {

					String code = bean.getCode();
					String names = bean.getNames();
					if (bean.getSubItem() != null && !Util.isNullOrEmpty(bean.getSubItem().getCode())) {
						code = code + " - " + bean.getSubItem().getCode();
						names = names + " - " + bean.getSubItem().getNames();
					}

					dialog.tblList.addRow(new Object[] { code, names, bean });
				}

				// 内訳科目レベルの場合
				else if (ItemLevel.DETAILITEM == itemLevel) {

					String code = bean.getCode();
					String names = bean.getNames();
					if (bean.getSubItem() != null && !Util.isNullOrEmpty(bean.getSubItem().getCode())) {
						code = code + " - " + bean.getSubItem().getCode();
						names = names + " - " + bean.getSubItem().getNames();
						if (bean.getSubItem().getDetailItem() != null && !Util.isNullOrEmpty(bean.getDetailItemCode())) {
							code = code + " - " + bean.getSubItem().getDetailItem().getCode();
							names = names + " - " + bean.getSubItem().getDetailItem().getNames();
						}
					}

					dialog.tblList.addRow(new Object[] { code, names, bean });
				}

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

	public SubItemSearchCondition getSubItemCondition() {
		return subItemCondition;
	}

	public DetailItemSearchCondition getDetailItemCondition() {
		return detailItemCondition;
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

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TItemGroupOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TItemGroupOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblList
				.getRowValueAt(selectedRows[i], TItemGroupOptionalSelectorDialog.SC.entity);
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
				TItemGroupOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TItemGroupOptionalSelectorDialog.SC.names);
			Item item = (Item) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TItemGroupOptionalSelectorDialog.SC.entity);
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
			item.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.code));
			item.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.names));
			list.add(item);
		}

		// 呼び出し元にセット
		for (Item item : list) {
			field.cbo.addItem(item.getCode() + " " + item.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		saveSelectedListData();

		dialog.setVisible(false);

	}

	@Override
	protected TItemGroupOptionalSelectorDialog getSelectorDialog() {
		TItemGroupOptionalSelectorDialog dialog = new TItemGroupOptionalSelectorDialog(field.getParentFrame(), true);
		addDialogEvent(dialog);

		return dialog;
	}

	/**
	 * ダイアログのイベント定義
	 * 
	 * @param dialog
	 */
	protected void addDialogEvent(TItemGroupOptionalSelectorDialog dialog) {

		dialog.ctrlItemLevelChooser.rdoItem.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoItem_Click();
			}
		});

		dialog.ctrlItemLevelChooser.rdoSubItem.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoSubItem_Click();
			}
		});

		dialog.ctrlItemLevelChooser.rdoDetailItem.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoSubItem_Click();
			}
		});

	}

	protected void rdoItem_Click() {
		dialog.tblSelectedList.removeRow();
		dialog.txtSearchCode.clear();
		dialog.txtSearchName.clear();
		refresh();
	}

	protected void rdoSubItem_Click() {
		dialog.tblSelectedList.removeRow();
		dialog.txtSearchCode.clear();
		dialog.txtSearchName.clear();
		refresh();
	}

	protected void rdoDetailItem_Click() {
		dialog.tblSelectedList.removeRow();
		dialog.txtSearchCode.clear();
		dialog.txtSearchName.clear();
		refresh();
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
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.entity);
				list.add(item);
			}
		}

		return list;

	}

	/**
	 * 科目一覧にEntityをセットする
	 */
	public void setEntities(List<Item> items) {

		// ダイアログ初期化
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}

		ItemLevel itemLevel = ItemLevel.ITEM;
		for (Item item : items) {
			if (item.getDetailItem() != null) {
				itemLevel = ItemLevel.DETAILITEM;
				break;
			}
			if (item.getSubItem() != null) {
				itemLevel = ItemLevel.SUBITEM;
			}
		}

		if (this.itemLevel != null) {
			itemLevel = this.itemLevel;
		}

		// 科目レベルではない場合リフレッシュ
		if (itemLevel != ItemLevel.ITEM) {
			((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.setItemLevel(itemLevel);
			refresh();
		}

		List<Integer> matchRowNo = new ArrayList<Integer>();
		for (Item item : items) {

			// 指定のコードがあれば
			for (int i = 0; i < dialog.tblList.getRowCount(); i++) {

				String code = (String) dialog.tblList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.code);
				String names = (String) dialog.tblList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.names);
				Item tItem = (Item) dialog.tblList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.entity);

				boolean match = false;

				// 科目レベルの場合、科目コードが一致すればマッチ
				if (itemLevel == ItemLevel.ITEM && item.getCode().equals(tItem.getCode())) {
					match = true;
				}

				// 補助レベルの場合、科目コード、補助科目コードが一致すればマッチ
				if (itemLevel == ItemLevel.SUBITEM) {
					String subItemCode = item.getSubItemCode();
					String tableSubItemCode = tItem.getSubItemCode();
					if (item.getCode().equals(tItem.getCode())
						&& Util.avoidNull(subItemCode).equals(Util.avoidNull(tableSubItemCode))) {
						match = true;
					}
				}

				// 内訳レベルの場合、科目コード、補助科目コード、内訳科目が一致すればマッチ
				if (itemLevel == ItemLevel.DETAILITEM) {
					String subItemCode = item.getSubItemCode();
					String detailItemCode = item.getDetailItemCode();
					String tableSubItemCode = tItem.getSubItemCode();
					String tableDetailItemCode = tItem.getDetailItemCode();

					if (item.getCode().equals(tItem.getCode())
						&& Util.avoidNull(subItemCode).equals(Util.avoidNull(tableSubItemCode))
						&& Util.avoidNull(detailItemCode).equals(Util.avoidNull(tableDetailItemCode))) {
						match = true;
					}
				}

				if (match) {
					dialog.tblSelectedList.addRow(new Object[] { code, names, tItem });
					matchRowNo.add(i);
				}

			}

		}

		// 選択された行を削除
		for (int i = matchRowNo.size() - 1; i >= 0; i--) {
			dialog.tblList.removeRow(matchRowNo.get(i));
		}

		btnSettle_Click(null);

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
				Item item = (Item) dialog.tblSelectedList.getRowValueAt(i, TItemGroupOptionalSelectorDialog.SC.entity);
				list.add(item.getCode());
			}
		}

		return list;

	}

	/**
	 * 補助、内訳別に出力した際に、補助、内訳が無いものでも出力するかを設定する。
	 * 
	 * @param bln
	 */
	public void setGetAllItems(boolean bln) {
		subItemCondition.setGetNotExistSubItem(bln);
		detailItemCondition.setGetNotExistDetailItem(bln);
	}

	/**
	 * 科目レベルを固定する。
	 * 
	 * @param itemLevel 科目レベル
	 */
	public void setItemLevel(ItemLevel itemLevel) {
		this.itemLevel = itemLevel;
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}
		((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.setItemLevel(itemLevel);
		((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.setEnabled(false);
		refresh();
	}

	/**
	 * 科目レベルを最大で固定する。<br>
	 * 内訳科目を使用する場合は内訳科目、使用しない場合は補助科目固定。
	 * 
	 * @param ac 会計設定
	 */
	public void setItemLevelMax(AccountConfig ac) {
		if (ac.isUseDetailItem()) {
			setItemLevel(ItemLevel.DETAILITEM);
		} else {
			setItemLevel(ItemLevel.SUBITEM);
		}
	}

	/**
	 * 検索リストの退避
	 */
	@Override
	public void saveListData() {

		// 科目レベルを退避
		itemLevel = ((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.getItemLevel();
		
//		// データを退避
//		DefaultTableModel tableModel = (DefaultTableModel) dialog.tblList.getModel();
//		dataWhenOpened = (Vector) tableModel.getDataVector().clone();
//
//		if (dialog.tblList.getRowCount() > 0) {
//			dialog.tblList.setRowSelectionInterval(0, 0);
//		}

	}

	/**
	 * 検索リストの退避
	 */
	@Override
	public void saveSelectedListData() {

		// 科目レベルを退避
		itemLevel = ((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.getItemLevel();
		
		// データを退避
		DefaultTableModel tableSelectedModel = (DefaultTableModel) dialog.tblSelectedList.getModel();
		dataSelectedWhenOpened = (Vector) tableSelectedModel.getDataVector().clone();

		if (dialog.tblSelectedList.getRowCount() > 0) {
			dialog.tblSelectedList.setRowSelectionInterval(0, 0);
		}

	}

	/**
	 * 科目レベルの戻し
	 */
	@Override
	public void loadItemLevel() {
		
		// 科目レベルを戻す
		((TItemGroupOptionalSelectorDialog) dialog).ctrlItemLevelChooser.setItemLevel(itemLevel);

	}
	
	/**
	 * 検索リストの戻し
	 */
	@Override
	public void loadListData() {

//		// 退避から戻す
//		dialog.tblList.removeRow();
//		dialog.tblSelectedList.removeRow();
//
//		DefaultTableModel tableModel = (DefaultTableModel) dialog.tblList.getModel();
//		if (dataWhenOpened != null) {
//			for (int i = 0; i < dataWhenOpened.size(); i++) {
//				tableModel.addRow((Vector) dataWhenOpened.get(i));
//			}
//		}
		refresh();

		DefaultTableModel tableSelectedModel = (DefaultTableModel) dialog.tblSelectedList.getModel();
		if (dataSelectedWhenOpened != null) {
			for (int i = 0; i < dataSelectedWhenOpened.size(); i++) {
				tableSelectedModel.addRow((Vector) dataSelectedWhenOpened.get(i));
			}
		}

	}
}

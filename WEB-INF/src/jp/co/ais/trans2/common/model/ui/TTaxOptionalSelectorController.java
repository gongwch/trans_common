package jp.co.ais.trans2.common.model.ui;

import java.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.tax.ConsumptionTax;
import jp.co.ais.trans2.model.tax.ConsumptionTaxManager;
import jp.co.ais.trans2.model.tax.ConsumptionTaxSearchCondition;

/**
 * 消費税任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TTaxOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected ConsumptionTaxSearchCondition condition;

	/**
	 * @param field
	 */
	public TTaxOptionalSelectorController(TTaxOptionalSelector field) {
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
	protected ConsumptionTaxSearchCondition createSearchCondition() {
		return new ConsumptionTaxSearchCondition();
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

		// 消費税コード
		dialog.tblList.addColumn(TTaxOptionalSelectorDialog.SC.code, getWord("C00573"), 100);
		// 消費税略称
		dialog.tblList.addColumn(TTaxOptionalSelectorDialog.SC.names, getWord("C00775"), 200);
		dialog.tblList.addColumn(TTaxOptionalSelectorDialog.SC.entity, "", -1);

		// 消費税コード
		dialog.tblSelectedList.addColumn(TTaxOptionalSelectorDialog.SC.code, getWord("C00573"), 100);
		// 消費税略称
		dialog.tblSelectedList.addColumn(TTaxOptionalSelectorDialog.SC.names, getWord("C00775"), 200);
		dialog.tblSelectedList.addColumn(TTaxOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		// 消費税選択
		return getWord("C11135");
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
			List<ConsumptionTax> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (ConsumptionTax bean : list) {
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
	protected List<ConsumptionTax> getList(ConsumptionTaxSearchCondition condition) {

		try {
			List<ConsumptionTax> list = (List<ConsumptionTax>) request(getModelClass(), "get", condition);
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
		return ConsumptionTaxManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public ConsumptionTaxSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(ConsumptionTaxSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [一覧から選択]ボタン押下
	 */
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], TTaxOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i], TTaxOptionalSelectorDialog.SC.names);
			ConsumptionTax tax = (ConsumptionTax) dialog.tblList.getRowValueAt(selectedRows[i],
				TTaxOptionalSelectorDialog.SC.entity);

			dialog.tblSelectedList.addRow(new Object[] { code, names, tax });
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
				TTaxOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TTaxOptionalSelectorDialog.SC.names);
			ConsumptionTax tax = (ConsumptionTax) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TTaxOptionalSelectorDialog.SC.entity);

			dialog.tblList.addRow(new Object[] { code, names, tax });
		}

		// 選択先から削除
		dialog.tblSelectedList.removeSelectedRows();

	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		field.cbo.removeAllItems();

		// 選択された消費税一覧を取得
		List<ConsumptionTax> list = new ArrayList<ConsumptionTax>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			ConsumptionTax tax = new ConsumptionTax();
			tax.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TTaxOptionalSelector.SC.code));
			tax.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TTaxOptionalSelector.SC.names));
			list.add(tax);
		}

		// 呼び出し元にセット
		for (ConsumptionTax tax : list) {
			field.cbo.addItem(tax.getCode() + " " + tax.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TTaxOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 選択された消費税Entity一覧を返す
	 * 
	 * @return 選択された消費税Entity一覧
	 */
	public List<ConsumptionTax> getEntities() {

		List<ConsumptionTax> list = new ArrayList<ConsumptionTax>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				ConsumptionTax tax = (ConsumptionTax) dialog.tblSelectedList.getRowValueAt(i,
					TTaxOptionalSelectorDialog.SC.entity);
				list.add(tax);
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
				ConsumptionTax tax = (ConsumptionTax) dialog.tblSelectedList.getRowValueAt(i,
					TTaxOptionalSelectorDialog.SC.entity);
				list.add(tax.getCode());
			}
		}

		return list;

	}

	/**
	 * 消費税一覧にEntityをセットする
	 */
	public void setEntities(List<ConsumptionTax> taxs) {

		// ダイアログ初期化
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}

		List<Integer> matchRowNo = new ArrayList<Integer>();
		for (ConsumptionTax tax : taxs) {

			// 指定のコードがあれば
			for (int i = 0; i < dialog.tblList.getRowCount(); i++) {

				String code = (String) dialog.tblList.getRowValueAt(i, TTaxOptionalSelectorDialog.SC.code);
				String names = (String) dialog.tblList.getRowValueAt(i, TTaxOptionalSelectorDialog.SC.names);
				ConsumptionTax tTax = (ConsumptionTax) dialog.tblList.getRowValueAt(i,
					TTaxOptionalSelectorDialog.SC.entity);

				// 消費税コードが一致すればマッチ
				if (tax.getCode().equals(tTax.getCode())) {
					dialog.tblSelectedList.addRow(new Object[] { code, names, tTax });
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

}

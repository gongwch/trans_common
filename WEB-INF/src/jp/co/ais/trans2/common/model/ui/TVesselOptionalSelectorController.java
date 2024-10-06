package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * 船任意選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TVesselOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected VesselSearchCondition condition;

	/**
	 * @param field
	 */
	public TVesselOptionalSelectorController(TVesselOptionalSelector field) {
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
	protected VesselSearchCondition createSearchCondition() {
		return new VesselSearchCondition();
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

		dialog.tblList.addColumn(TVesselOptionalSelectorDialog.SC.code, getWord("C11758"), 100); // 船コード
		dialog.tblList.addColumn(TVesselOptionalSelectorDialog.SC.names, getWord("C11759"), 200); // 船略称
		dialog.tblList.addColumn(TVesselOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TVesselOptionalSelectorDialog.SC.code, getWord("C11758"), 100); // 船コード
		dialog.tblSelectedList.addColumn(TVesselOptionalSelectorDialog.SC.names, getWord("C11759"), 200); // 船略称
		dialog.tblSelectedList.addColumn(TVesselOptionalSelectorDialog.SC.entity, "", -1);

	}

	@Override
	protected String getDialogCaption() {
		return getWord("C11760"); // 船選択
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
			List<Vessel> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (Vessel bean : list) {
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
	 * @return 船情報
	 */
	@SuppressWarnings("unchecked")
	protected List<Vessel> getList(VesselSearchCondition condition) {

		try {
			List<Vessel> list = (List<Vessel>) request(getModelClass(), "get", condition);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servletのclassを返す
	 * 
	 * @return VesselManager
	 */
	protected Class getModelClass() {
		return VesselManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public VesselSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(VesselSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [一覧から選択]ボタン押下
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], TVesselOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TVesselOptionalSelectorDialog.SC.names);
			Vessel dep = (Vessel) dialog.tblList
				.getRowValueAt(selectedRows[i], TVesselOptionalSelectorDialog.SC.entity);

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
				TVesselOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TVesselOptionalSelectorDialog.SC.names);
			Vessel dep = (Vessel) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TVesselOptionalSelectorDialog.SC.entity);

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

		// 選択された船一覧を取得
		List<Vessel> list = new ArrayList<Vessel>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Vessel vessel = new Vessel();
			vessel.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TVesselOptionalSelector.SC.code));
			vessel.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TVesselOptionalSelector.SC.names));
			list.add(vessel);
		}

		// 呼び出し元にセット
		for (Vessel vessel : list) {
			field.cbo.addItem(vessel.getCode() + " " + vessel.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TVesselOptionalSelectorDialog(field.getParentFrame(), true);
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 選択された船Entity一覧を返す
	 * 
	 * @return 選択された船Entity一覧
	 */
	public List<Vessel> getEntities() {

		List<Vessel> list = new ArrayList<Vessel>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Vessel dep = (Vessel) dialog.tblSelectedList.getRowValueAt(i, TVesselOptionalSelectorDialog.SC.entity);
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
				Vessel dep = (Vessel) dialog.tblSelectedList.getRowValueAt(i, TVesselOptionalSelectorDialog.SC.entity);
				list.add(dep.getCode());
			}
		}

		return list;

	}

}

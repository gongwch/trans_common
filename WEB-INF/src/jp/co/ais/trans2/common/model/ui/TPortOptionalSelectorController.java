package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.port.*;

/**
 * 港任意選択コンポーネントのコントローラ
 *
 * @author AIS
 */
public class TPortOptionalSelectorController extends TOptionalSelectorController {

	/** 検索条件 */
	protected PortSearchCondition condition;

	/**
	 * @param field
	 */
	public TPortOptionalSelectorController(TPortOptionalSelector field) {
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
	protected PortSearchCondition createSearchCondition() {
		return new PortSearchCondition();
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
		dialog.tblList.addColumn(TPortOptionalSelectorDialog.SC.code, getWord("C00584"), 100); // 港コード
		dialog.tblList.addColumn(TPortOptionalSelectorDialog.SC.names, getWord("C00586"), 200); // 港略称
		dialog.tblList.addColumn(TPortOptionalSelectorDialog.SC.entity, "", -1);

		dialog.tblSelectedList.addColumn(TPortOptionalSelectorDialog.SC.code, getWord("C00584"), 100); // 港コード
		dialog.tblSelectedList.addColumn(TPortOptionalSelectorDialog.SC.names, getWord("C00586"), 200); // 港略称
		dialog.tblSelectedList.addColumn(TPortOptionalSelectorDialog.SC.entity, "", -1);

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
			List<Port> list = getList(getCondition());

			if (list == null || list.isEmpty()) {
				return;
			}

			// 一覧にセット
			for (Port bean : list) {
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
	 * @param cond
	 * @return 船情報
	 */
	@SuppressWarnings("unchecked")
	protected List<Port> getList(PortSearchCondition cond) {

		try {
			List<Port> list = (List<Port>) request(getModelClass(), "get", cond);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Servletのclassを返す
	 *
	 * @return PortManager
	 */
	protected Class getModelClass() {
		return PortManager.class;
	}

	/**
	 * @return conditionを戻します。
	 */
	public PortSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition conditionを設定します。
	 */
	public void setCondition(PortSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * [一覧から選択]ボタン押下
	 */
	@Override
	protected void btnTableSelect_Click() {

		int selectedRows[] = dialog.tblList.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String code = (String) dialog.tblList.getRowValueAt(selectedRows[i], TPortOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblList.getRowValueAt(selectedRows[i],
				TPortOptionalSelectorDialog.SC.names);
			Port dep = (Port) dialog.tblList
				.getRowValueAt(selectedRows[i], TPortOptionalSelectorDialog.SC.entity);

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
				TPortOptionalSelectorDialog.SC.code);
			String names = (String) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TPortOptionalSelectorDialog.SC.names);
			Port dep = (Port) dialog.tblSelectedList.getRowValueAt(selectedRows[i],
				TPortOptionalSelectorDialog.SC.entity);

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
		List<Port> list = new ArrayList<Port>();

		for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
			Port port = new Port();
			port.setCode((String) dialog.tblSelectedList.getRowValueAt(i, TPortOptionalSelector.SC.code));
			port.setNames((String) dialog.tblSelectedList.getRowValueAt(i, TPortOptionalSelector.SC.names));
			list.add(port);
		}

		// 呼び出し元にセット
		for (Port port: list) {
			field.cbo.addItem(port.getCode() + " " + port.getNames());
		}

		field.cbo.setEnabled(field.cbo.getItemCount() > 0);

		dialog.setVisible(false);

	}

	@Override
	protected TOptionalSelectorDialog getSelectorDialog() {
		return new TPortOptionalSelectorDialog(field.getParentFrame(), true);
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
	public List<Port> getEntities() {

		List<Port> list = new ArrayList<Port>();

		if (dialog != null) {
			for (int i = 0; i < dialog.tblSelectedList.getRowCount(); i++) {
				Port dep = (Port) dialog.tblSelectedList.getRowValueAt(i, TPortOptionalSelectorDialog.SC.entity);
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
				Port dep = (Port) dialog.tblSelectedList.getRowValueAt(i, TPortOptionalSelectorDialog.SC.entity);
				list.add(dep.getCode());
			}
		}

		return list;

	}

}

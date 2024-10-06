package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.util.List;

import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票種別選択(テーブルチェック形式)コンポーネント
 * 
 * @author AIS
 */
public class TSlipTypeListSelector extends TPanel {

	/** 一覧 */
	protected TTable tbl;

	/** フィルター */
	protected TTextField ctrlFilter;

	/** テーブルソート */
	protected TableRowSorter<? extends TableModel> sorter = null;

	/** コントローラ */
	protected TSlipTypeListSelectorController controller;

	/**
	 * テーブル列名列挙体
	 */
	public enum SC {
		/** 選択 */
		CHECK,
		/** 伝票種別 表示名 */
		SLIPTYPE,
		/** 伝票種別 */
		SLIPTYPE_VALUE
	}

	/**
	 * 
	 *
	 */
	public TSlipTypeListSelector() {
		this(true);
	}

	/**
	 * 初期表示データのあり/なしを指定。無しの場合、呼び出し側で<br>
	 * 検索条件をセットし、refreshメソッドを実行することで個別の検索条件に<br>
	 * 基づく一覧の表示を行う
	 * 
	 * @param isRefresh true(初期表示データをデフォルトの検索条件で表示する)
	 */
	public TSlipTypeListSelector(boolean isRefresh) {

		initComponents();

		allocateComponents();

		// コントローラ生成
		controller = new TSlipTypeListSelectorController(this, isRefresh);

	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		tbl = new TTable();
		ctrlFilter = new TTextField();
		tbl.addColumn(SC.CHECK, "", 30, TCheckBox.class);
		tbl.addColumn(SC.SLIPTYPE, "C00917", 160);
		tbl.addColumn(SC.SLIPTYPE_VALUE, "", -1);
		tbl.setRowLabelNumber(false);
		tbl.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * コンポーネントの配置
	 */
	public void allocateComponents() {
		GridBagConstraints gc = new GridBagConstraints();

		setLayout(new GridBagLayout());
		setSize(210, 150);

		tbl.setSize(210, 150);
		gc.insets = new Insets(0, 0, 0, 0);
		gc.gridx = 0;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		add(tbl, gc);

		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 20);
		gc.gridx = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		ctrlFilter.setMaxLength(20);
		ctrlFilter.setMaximumSize(new Dimension(130, 20));
		ctrlFilter.setMinimumSize(new Dimension(130, 20));
		ctrlFilter.setPreferredSize(new Dimension(130, 20));

		add(ctrlFilter, gc);

	}

	/**
	 * チェックした行数を返す
	 * 
	 * @return チェック行数
	 */
	public int getCheckedRowCount() {
		return controller.getCheckedRowCount();
	}

	/**
	 * チェックした伝票種別を返す
	 * 
	 * @return List<伝票種別>
	 */
	public List<String> getCheckedSlipType() {
		return controller.getCheckedSlipType();
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public SlipTypeSearchCondition getCondition() {
		return controller.getCondition();
	}

	/**
	 * 設定された検索条件を元に伝票種別一覧を再読込する。
	 */
	public void refresh() {
		controller.refresh();
	}

	/**
	 * @param no
	 */
	public void setTabControlNo(int no) {
		tbl.table.setTabControlNo(no);
		ctrlFilter.setTabControlNo(no);
	}

	/**
	 * 全ての行を削除する
	 */
	public void removeRow() {
		tbl.removeRow();
	}

	/**
	 * @param row
	 */
	public void setShowRowHeaderStar(int row) {
		tbl.setShowRowHeaderStar(row);
	}

	/**
	 * 行追加 Object型の配列を追加
	 * 
	 * @param data データ
	 */
	public void addRow(Object[] data) {
		tbl.addRow(data);
	}

	/**
	 * 表示されている行数を取得する
	 * 
	 * @return 行数
	 */
	public int getRowCount() {
		return tbl.getRowCount();
	}

	/**
	 * 指定行の指定カラムの値を返します。
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public Object getRowValueAt(int row, Enum column) {
		return tbl.getRowValueAt(row, column);
	}

	/**
	 * @param obj
	 * @param row
	 * @param column
	 * @see jp.co.ais.trans2.common.gui.TTable#setRowValueAt(java.lang.Object, int, java.lang.Enum)
	 */
	public void setRowValueAt(Object obj, int row, Enum column) {
		tbl.setRowValueAt(obj, row, column);
	}

}

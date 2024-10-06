package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.TCheckBox;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;

/**
 * 伝票のステータス選択(テーブルチェック形式)コンポーネント
 * 
 * @author AIS
 */
public class TSlipStateListSelector extends TTable {

	/** serialVersionUID */
	private static final long serialVersionUID = 5229926607981299214L;

	/** コントローラ */
	protected TSlipStateListSelectorController controller;

	/**
	 * テーブル列名列挙体
	 */
	public enum SC {
		/** 選択 */
		CHECK,
		/** 更新区分名称 */
		SLIPSTATE,
		/** 更新区分 */
		SLIPSTATE_VALUE
	}

	/**
	 * コンストラクタ.
	 */
	public TSlipStateListSelector() {

		this(new SlipState[] {});
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param nonDisplayList 非表示区分
	 */
	public TSlipStateListSelector(SlipState... nonDisplayList) {

		initComponents();

		allocateComponents();

		// コントローラ生成
		controller = new TSlipStateListSelectorController(this, nonDisplayList);
	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		addColumn(SC.CHECK, "", 30, TCheckBox.class);
		addColumn(SC.SLIPSTATE, "C01069", 100);
		addColumn(SC.SLIPSTATE_VALUE, "", -1);
		setRowLabelNumber(false);
		getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * コンポーネントの配置
	 */
	public void allocateComponents() {
		setSize(135, 140);
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
	 * チェックした更新区分を返す
	 * 
	 * @return List<更新区分>
	 */
	public List<SlipState> getCheckedSlipState() {
		return controller.getCheckedSlipState();
	}

}

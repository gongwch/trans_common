package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;

/**
 * 伝票のステータス選択(テーブルチェック形式)コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TSlipStateListSelectorController extends TController {

	/** フィールド */
	protected TSlipStateListSelector field;

	/**
	 * コンストラクタ.
	 * 
	 * @param field
	 * @param nonDisplayList 非表示区分
	 */
	public TSlipStateListSelectorController(TSlipStateListSelector field, SlipState[] nonDisplayList) {

		this.field = field;

		init(nonDisplayList);
	}

	/**
	 * Start
	 */
	@Override
	public void start() {
		//
	}

	/**
	 * パネル取得
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 初期化
	 * 
	 * @param nonDisplayList 非表示区分
	 */
	protected void init(SlipState[] nonDisplayList) {

		boolean isChecked = ClientConfig.isFlagOn("trans.slipstate.init.checkall");

		// 登録
		if (isVisible(nonDisplayList, SlipState.ENTRY)) {
			field.addRow(new Object[] { isChecked, getWord("C01258"), SlipState.ENTRY });// 登録
		}

		// 現場承認
		if (isVisible(nonDisplayList, SlipState.FIELD_APPROVE) && getCompany().getAccountConfig().isUseFieldApprove()) {
			field.addRow(new Object[] { isChecked, getWord("C00157"), SlipState.FIELD_APPROVE });// 現場承認
		}

		// 経理承認
		if (isVisible(nonDisplayList, SlipState.APPROVE) && getCompany().getAccountConfig().isUseApprove()) {
			field.addRow(new Object[] { isChecked, getWord("C01616"), SlipState.APPROVE });// 経理承認
		}

		// 現場否認
		if (isVisible(nonDisplayList, SlipState.FIELD_DENY) && getCompany().getAccountConfig().isUseFieldApprove()) {
			field.addRow(new Object[] { isChecked, getWord("C01617"), SlipState.FIELD_DENY });// 現場否認
		}

		// 経理否認
		if (isVisible(nonDisplayList, SlipState.DENY) && getCompany().getAccountConfig().isUseApprove()) {
			field.addRow(new Object[] { isChecked, getWord("C01615"), SlipState.DENY });// 経理否認
		}

		// 更新
		if (isVisible(nonDisplayList, SlipState.UPDATE)) {
			field.addRow(new Object[] { isChecked, getWord("C00169"), SlipState.UPDATE });// 更新
		}

		field.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		field.setReorderingAllowed(false);

		if (field.getRowCount() > 4) {
			field.setSize(150, 140);
		}
	}

	/**
	 * 対象更新区分を表示するかどうか
	 * 
	 * @param stateArray 非表示リスト
	 * @param state 対象更新区分
	 * @return True：表示
	 */
	protected boolean isVisible(SlipState[] stateArray, SlipState state) {

		for (int i = 0; i < stateArray.length; i++) {

			// リストに一致する更新区分がある場合はFalseを返す
			if (stateArray[i].equals(state)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * チェックした行数を返す
	 * 
	 * @return チェック行数
	 */
	public int getCheckedRowCount() {

		// チェック行の取得
		int checkedCount = 0;
		for (int i = 0; i < field.getRowCount(); i++) {

			// チェック値の取得
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipStateListSelector.SC.CHECK);

			// チェックされている場合、カウントを増加
			if (isChecked) {
				checkedCount += 1;
			}

		}

		return checkedCount;
	}

	/**
	 * チェックした更新区分を返す
	 * 
	 * @return List<更新区分>
	 */
	public List<SlipState> getCheckedSlipState() {

		List<SlipState> list = new LinkedList<SlipState>();

		for (int i = 0; i < field.getRowCount(); i++) {

			// チェック値の取得
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipStateListSelector.SC.CHECK);

			// チェックされている場合、リストに対象更新を追加
			if (isChecked) {
				SlipState state = (SlipState) field.getRowValueAt(i, TSlipStateListSelector.SC.SLIPSTATE_VALUE);
				list.add(state);
			}
		}

		return list;
	}
}
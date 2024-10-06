package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票種別選択(テーブルチェック形式)コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TSlipTypeListSelectorController extends TController {

	/** フィールド */
	protected TSlipTypeListSelector field;

	/** 検索条件 */
	protected SlipTypeSearchCondition condition;

	/** 伝票種別スプレッド全データリスト */
	protected List<SlipType> slipList;

	/** 伝票種別スプレッド：map */
	protected Map<String, Boolean> map = new HashMap<String, Boolean>();

	/**
	 * コンストラクタ(一覧データを初期表示する)
	 * 
	 * @param field フィールド
	 */
	public TSlipTypeListSelectorController(TSlipTypeListSelector field) {
		this(field, true);
	}

	/**
	 * コンストラクタ(一覧データを初期表示しない)
	 * 
	 * @param field
	 * @param isRefresh
	 */
	public TSlipTypeListSelectorController(TSlipTypeListSelector field, boolean isRefresh) {

		try {

			this.field = field;

			initSearchCondition();

			if (isRefresh) {
				init(condition);
			}

		} catch (TException e) {
			errorHandler(e);
		}
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 検索条件初期化
	 */
	protected void initSearchCondition() {
		condition = new SlipTypeSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setReferOtherSystemDivision(true);
	}

	/**
	 * 設定された検索条件を元に伝票種別一覧を再読込する。
	 */
	protected void refresh() {
		try {
			init(condition);

		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * 初期化
	 * 
	 * @param condition1
	 * @throws TException
	 */
	protected void init(SlipTypeSearchCondition condition1) throws TException {

		field.removeRow();

		slipList = getSlipType(condition1);

		boolean isChecked = ClientConfig.isFlagOn("trans.sliptype.init.checkall");

		for (SlipType slipType : slipList) {
			field
				.addRow(new Object[] { isChecked, slipType.getCode() + " : " + slipType.getNames(), slipType.getCode() });
			map.put(slipType.getCode(), isChecked);

		}
		filterEvent();

	}

	/**
	 * フィルター入力イベント定義
	 */
	protected void filterEvent() {
		field.ctrlFilter.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				filter_Input(e);

			}

		});
	}

	/**
	 * 伝票種別一覧[フィルター入力]
	 * 
	 * @param e
	 */
	protected void filter_Input(KeyEvent e) {

		for (int i = 0; i < field.getRowCount(); i++) {

			// チェック値の取得
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipTypeListSelector.SC.CHECK);

			// チェックされている場合、リストに対象更新を追加
			String key = (String) field.getRowValueAt(i, TSlipTypeListSelector.SC.SLIPTYPE_VALUE);
			map.put(key, isChecked);

		}

		field.removeRow();

		for (SlipType slipType : slipList) {
			String name = slipType.getCode() + ":" + slipType.getNames();

			if (name.contains(field.ctrlFilter.getInputText())) {

				field.addRow(new Object[] { map.get(slipType.getCode()),
						slipType.getCode() + " : " + slipType.getNames(), slipType.getCode() });
			}

		}
	}

	/**
	 * 伝票種別を取得する
	 * 
	 * @param searchCondition
	 * @return List<伝票種別>
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	protected List<SlipType> getSlipType(SlipTypeSearchCondition searchCondition) throws TException {

		List<SlipType> list = (List<SlipType>) request(getSlipTypeManagerClass(), "get", searchCondition);

		return list;
	}

	/**
	 * 伝票種別抽出クラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getSlipTypeManagerClass() {
		return SlipTypeManager.class;
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

			// チェック値の取得及びmap更新
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipTypeListSelector.SC.CHECK);
			String value = (String) field.getRowValueAt(i, TSlipTypeListSelector.SC.SLIPTYPE_VALUE);
			map.put(value, isChecked);

		}
		for (Map.Entry<String, Boolean> entry : map.entrySet()) {
			if (entry.getValue() != null && entry.getValue()) {
				checkedCount++;
			}
		}

		return checkedCount;

	}

	/**
	 * チェックした伝票種別を返す
	 * 
	 * @return List<伝票種別>
	 */
	public List<String> getCheckedSlipType() {

		List<String> list = new LinkedList<String>();
		for (int i = 0; i < field.getRowCount(); i++) {

			// チェック値の取得及びmap更新
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TSlipTypeListSelector.SC.CHECK);
			String value = (String) field.getRowValueAt(i, TSlipTypeListSelector.SC.SLIPTYPE_VALUE);
			map.put(value, isChecked);

		}
		for (Map.Entry<String, Boolean> entry : map.entrySet()) {
			if (entry.getValue() != null && entry.getValue()) {
				list.add(entry.getKey());
			}
		}

		return list;
	}

	/**
	 * @return 検索条件
	 */
	public SlipTypeSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition 検索条件
	 */
	public void setCondition(SlipTypeSearchCondition condition) {
		this.condition = condition;
	}

}

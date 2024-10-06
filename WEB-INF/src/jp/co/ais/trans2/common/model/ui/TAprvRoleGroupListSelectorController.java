package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.model.ui.TAprvRoleGroupListSelector.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * 承認グループ選択(テーブルチェック形式)コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TAprvRoleGroupListSelectorController extends TController {

	/** フィールド */
	protected TAprvRoleGroupListSelector field;

	/** 検索条件 */
	protected AprvRoleGroupSearchCondition condition;

	/** 承認グループ全データリスト */
	protected List<AprvRoleGroup> grpList;

	/** 伝票種別スプレッド：map */
	protected Map<String, Boolean> map = new HashMap<String, Boolean>();

	/**
	 * コンストラクタ(一覧データを初期表示する)
	 * 
	 * @param field フィールド
	 */
	public TAprvRoleGroupListSelectorController(TAprvRoleGroupListSelector field) {
		this(field, true);
	}

	/**
	 * コンストラクタ(一覧データを初期表示しない)
	 * 
	 * @param field
	 * @param isRefresh
	 */
	public TAprvRoleGroupListSelectorController(TAprvRoleGroupListSelector field, boolean isRefresh) {

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
		condition = new AprvRoleGroupSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
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
	protected void init(AprvRoleGroupSearchCondition condition1) throws TException {

		field.removeRow();
		grpList = getRoleGroup(condition1);

		boolean isChecked = ClientConfig.isFlagOn("trans.aprvrole.grp.init.checkall");

		for (AprvRoleGroup role : grpList) {
			field.addRow(new Object[] { isChecked, role.getAPRV_ROLE_GRP_CODE() + " : " + role.getAPRV_ROLE_GRP_NAME(),
					role.getAPRV_ROLE_GRP_CODE() });
			map.put(role.getAPRV_ROLE_GRP_CODE(), isChecked);

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
	 * 承認ロール一覧[フィルター入力]
	 * 
	 * @param e
	 */
	protected void filter_Input(KeyEvent e) {

		for (int i = 0; i < field.getRowCount(); i++) {

			// チェック値の取得
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TAprvRoleListSelector.SC.CHECK);

			// チェックされている場合、リストに対象更新を追加
			String key = (String) field.getRowValueAt(i, TAprvRoleListSelector.SC.VALUE);
			map.put(key, isChecked);

		}

		field.removeRow();

		for (AprvRoleGroup role : grpList) {
			String name = role.getAPRV_ROLE_GRP_CODE() + ":" + role.getAPRV_ROLE_GRP_NAME();

			if (name.contains(field.ctrlFilter.getInputText())) {
				field.addRow(new Object[] { map.get(role.getAPRV_ROLE_GRP_CODE()),
						role.getAPRV_ROLE_GRP_CODE() + " : " + role.getAPRV_ROLE_GRP_NAME(), role.getAPRV_ROLE_GRP_CODE() });
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
	protected List<AprvRoleGroup> getRoleGroup(AprvRoleGroupSearchCondition searchCondition) throws TException {

		List<AprvRoleGroup> list = (List<AprvRoleGroup>) request(getManagerClass(), "get", searchCondition);

		return list;
	}

	/**
	 * 伝票種別抽出クラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getManagerClass() {
		return AprvRoleGroupManager.class;
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
			Boolean isChecked = (Boolean) field.getRowValueAt(i, TAprvRoleListSelector.SC.CHECK);
			String value = (String) field.getRowValueAt(i, TAprvRoleListSelector.SC.VALUE);
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
	 * チェックした承認権限コードを返す
	 * 
	 * @return List<伝票種別>
	 */
	public List<String> getCheckedAprvRoleCode() {

		List<String> list = new LinkedList<String>();
		for (int i = 0; i < field.getRowCount(); i++) {

			// チェック値の取得及びmap更新
			Boolean isChecked = (Boolean) field.getRowValueAt(i, SC.CHECK);
			String value = (String) field.getRowValueAt(i, SC.VALUE);
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
	public AprvRoleGroupSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition 検索条件
	 */
	public void setCondition(AprvRoleGroupSearchCondition condition) {
		this.condition = condition;
	}

}

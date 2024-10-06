package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TComboBox.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * 輸送実績大品目コンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TMlitItemComboBoxController extends TController {

	/** コンボボックス */
	protected TMlitItemComboBox comboBox;

	/** 検索条件 */
	protected YJItemSearchCondition condition;

	/**
	 * @param comboBox フィールド
	 */
	public TMlitItemComboBoxController(TMlitItemComboBox comboBox) {

		this.comboBox = comboBox;
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		// 検索条件初期化
		initSearchCondition();

		// コンボボックス生成
		getList(condition);

		// イベント定義
		addEvent();
	}

	/**
	 * イベントを定義
	 */
	protected void addEvent() {
		comboBox.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					setItemCondition();
				}
			}

		});
	}

	/**
	 * 選択内容変更時イベント<br>
	 * 対になるSubItemCombo設定時、Subコンボの検索条件を設定する
	 */
	protected void setItemCondition() {
		if (comboBox.subCombo == null) {
			return;
		}
		TMlitSubItemComboBox combo = comboBox.subCombo;
		boolean enable = !Util.isNullOrEmpty(comboBox.getSelectedCode());
		combo.setEnabled(enable);
		combo.getSearchCondition().setItemCode(getSelectedCode());
		combo.reflesh();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected YJItemSearchCondition createSearchCondition() {
		return new YJItemSearchCondition();
	}

	/**
	 * コンボボックスのリストを返す
	 */
	public void initList() {
		getList(condition);
	}

	/**
	 * コンボボックスのリストを返す
	 * 
	 * @param condition1
	 */
	public void getList(YJItemSearchCondition condition1) {

		try {
			comboBox.getComboBox().removeAllItems();

			List<YJItem> list = getDataList(condition1);

			comboBox.getComboBox().addTextValueItem(null, "");

			for (int i = 0; i < list.size(); i++) {
				comboBox.getComboBox().addTextValueItem(list.get(i).getITEM_CODE(), list.get(i).getITEM_NAME());
			}
		} catch (TException ex) {
			errorHandler(ex);
		}
	}

	/**
	 * @param condition1
	 * @return リストを返す
	 * @throws TException
	 */
	protected List<YJItem> getDataList(YJItemSearchCondition condition1) throws TException {
		List<YJItem> list = (List<YJItem>) request(getModelClass(), "getItems", condition1);

		if (comboBox.hasBlank) {
			YJItem blank = new YJItem();
			list.add(0, blank);
		}
		return list;
	}

	/**
	 * @return ServletのClassを返す
	 */
	protected Class getModelClass() {
		return YJItemManager.class;
	}

	/**
	 * @see jp.co.ais.trans2.common.client.TController#start()
	 */
	@Override
	public void start() {
		//
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 選択された品目コードを返す
	 * 
	 * @return 選択された品目コード
	 */
	public String getSelectedCode() {
		return (String) comboBox.getComboBox().getSelectedItemValue();
	}

	/**
	 * 品目コードを設定する
	 * 
	 * @param code 品目コード
	 */
	public void setSelectedCode(String code) {
		comboBox.setSelectedItemValue(code);
	}

	/**
	 * 選択された名称を取得する
	 * 
	 * @return 名称
	 */
	public String getSelectedName() {
		TTextValue val = comboBox.getComboBox().getSelectedTextValue();
		return val == null ? null : val.getText();
	}
}

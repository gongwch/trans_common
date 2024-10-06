package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * 輸送実績国コンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TMlitRegionComboBoxController extends TController {

	/** コンボボックス */
	protected TMlitRegionComboBox comboBox;

	/** 検索条件 */
	protected YJRegionSearchCondition condition;

	/**
	 * @param comboBox フィールド
	 */
	public TMlitRegionComboBoxController(TMlitRegionComboBox comboBox) {

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
					setCountryCondition();
				}
			}

		});
	}

	/**
	 * 選択内容変更時イベント<br>
	 * 対になるCoutryCombo設定時、Countryコンボの検索条件を設定する
	 */
	protected void setCountryCondition() {
		if (comboBox.couCombo == null) {
			return;
		}
		TMlitCountryComboBox combo = comboBox.couCombo;
		combo.getSearchCondition().setRegionCode(getSelectedRegionCode());
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
	protected YJRegionSearchCondition createSearchCondition() {
		return new YJRegionSearchCondition();
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
	public void getList(YJRegionSearchCondition condition1) {

		try {
			comboBox.getComboBox().removeAllItems();

			List<YJRegion> list = getDataList(condition1);

			comboBox.getComboBox().addTextValueItem(null, "");

			for (int i = 0; i < list.size(); i++) {
				comboBox.getComboBox().addTextValueItem(list.get(i).getREGION_CODE(), list.get(i).getREGION_NAME());
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
	protected List<YJRegion> getDataList(YJRegionSearchCondition condition1) throws TException {
		List<YJRegion> list = (List<YJRegion>) request(getModelClass(), "getRegion", condition1);

		if (comboBox.hasBlank) {
			YJRegion blank = new YJRegion();
			list.add(0, blank);
		}
		return list;
	}

	/**
	 * @return ServletのClassを返す
	 */
	protected Class getModelClass() {
		return YJRegionManager.class;
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
	 * 選択された国コードを返す
	 * 
	 * @return 選択された国コード
	 */
	public String getSelectedRegionCode() {
		return (String) comboBox.getComboBox().getSelectedItem();
	}

	/**
	 * 国コードを設定する
	 * 
	 * @param code 国コード
	 */
	public void setSelectedRegionCode(String code) {
		DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getComboBox().getModel();
		int index = model.getIndexOf(code);
		if (index != -1) {
			comboBox.setSelectedIndex(index);
		}
	}
}

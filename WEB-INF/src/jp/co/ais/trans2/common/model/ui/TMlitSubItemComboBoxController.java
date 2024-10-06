package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TComboBox.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * 輸送実績サブ品目コンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TMlitSubItemComboBoxController extends TController {

	/** コンボボックス */
	protected TMlitSubItemComboBox comboBox;

	/** 検索条件 */
	protected YJItemSearchCondition condition;

	/**
	 * @param comboBox フィールド
	 */
	public TMlitSubItemComboBoxController(TMlitSubItemComboBox comboBox) {

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
	 * @return 検索条件を返す
	 */
	public YJItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * コンボボックスのリストを返す
	 */
	public void initList() {
		YJItemSearchCondition searchCondition = condition.clone();
		getList(searchCondition);
	}

	/**
	 * コンボボックス内容をリフレッシュ
	 */
	public void refleshCombo() {
		String selected = getSelectedCode();
		getList(condition);
		comboBox.setSelectedIndex(0);
		if (selected != null) {
			setSelectedCode(selected);
		}
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
				comboBox.getComboBox().addTextValueItem(list.get(i).getITEM_SUB_CODE(), list.get(i).getITEM_SUB_NAME());
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
		List<YJItem> list = (List<YJItem>) request(getModelClass(), "getSubItems", condition1);

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
	 * 選択されたサブ品目コードを返す
	 * 
	 * @return 選択されたサブ品目コード
	 */
	public String getSelectedCode() {
		return (String) comboBox.getComboBox().getSelectedItemValue();
	}

	/**
	 * サブ品目コードを設定する
	 * 
	 * @param code サブ品目コード
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

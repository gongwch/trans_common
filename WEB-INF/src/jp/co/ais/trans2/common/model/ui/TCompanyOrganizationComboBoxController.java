package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社組織コンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TCompanyOrganizationComboBoxController extends TController {

	/** コンボボックス */
	protected TCompanyOrganizationComboBox comboBox;

	/** 検索条件 */
	protected CompanyOrganizationSearchCondition condition;

	/**
	 * @param comboBox フィールド
	 */
	public TCompanyOrganizationComboBoxController(TCompanyOrganizationComboBox comboBox) {

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
	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected CompanyOrganizationSearchCondition createSearchCondition() {
		return new CompanyOrganizationSearchCondition();
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
	public void getList(CompanyOrganizationSearchCondition condition1) {

		try {
			comboBox.getComboBox().removeAllItems();

			List<CompanyOrganization> list = getDataList(condition1);
			String[] code = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				switch (TCompanyOrganizationUnit.DISPLAY_NAME_FLG) {
					case 1:
						comboBox.getComboBox().addTextValueItem(list.get(i).getCode(), list.get(i).getName());
						break;
					case 2:
						comboBox.getComboBox().addTextValueItem(list.get(i).getCode(),
							list.get(i).getCode() + " " + list.get(i).getName());
						break;
					default:
						code[i] = list.get(i).getCode();
						break;
				}
			}
			if (0 < list.size()) {
				switch (TCompanyOrganizationUnit.DISPLAY_NAME_FLG) {
					case 0:
					case 3:
						comboBox.getComboBox().setModel(code);
						break;
					default:
						break;
				}
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
	protected List<CompanyOrganization> getDataList(CompanyOrganizationSearchCondition condition1) throws TException {
		List<CompanyOrganization> list = (List<CompanyOrganization>) request(getModelClass(), "getCompanyOrganization",
			condition1);

		if (comboBox.hasBlank) {
			CompanyOrganization blank = new CompanyOrganization();
			list.add(0, blank);
		}

		return list;
	}

	/**
	 * @return ServletのClassを返す
	 */
	protected Class getModelClass() {
		return CompanyOrganizationManager.class;
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
	 * 選択された組織コードを返す
	 * 
	 * @return 選択された組織コード
	 */
	public String getSelectedOrganizationCode() {

		switch (TCompanyOrganizationUnit.DISPLAY_NAME_FLG) {
			case 1:
			case 2:
				return (String) comboBox.getComboBox().getSelectedItemValue();
			default:
				return (String) comboBox.getComboBox().getSelectedItem();
		}
	}

	/**
	 * 組織コードを設定する
	 * 
	 * @param code 組織コード
	 */
	public void setSelectedOrganizationCode(String code) {
		DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getComboBox().getModel();
		int index = model.getIndexOf(code);
		if (index != -1) {
			comboBox.setSelectedIndex(index);
		}

	}

}

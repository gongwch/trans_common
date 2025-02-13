package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門組織コンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TDepartmentOrganizationComboBoxController extends TController {

	/** コンボボックス */
	protected TDepartmentOrganizationComboBox comboBox;

	/** 検索条件 */
	protected DepartmentOrganizationSearchCondition condition;

	/**
	 * @param comboBox フィールド
	 */
	public TDepartmentOrganizationComboBoxController(TDepartmentOrganizationComboBox comboBox) {

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
	protected DepartmentOrganizationSearchCondition createSearchCondition() {
		return new DepartmentOrganizationSearchCondition();
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
	public void getList(DepartmentOrganizationSearchCondition condition1) {

		try {

			comboBox.getComboBox().removeAllItems();
			List<DepartmentOrganization> list = getDataList(condition1);
			String[] code = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				switch (TDepartmentOrganizationUnit.DISPLAY_NAME_FLG) {
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
				switch (TDepartmentOrganizationUnit.DISPLAY_NAME_FLG) {
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
	protected List<DepartmentOrganization> getDataList(DepartmentOrganizationSearchCondition condition1)
		throws TException {
		List<DepartmentOrganization> list = (List<DepartmentOrganization>) request(getModelClass(),
			"getDepartmentOrganization", condition1);

		if (comboBox.hasBlank) {
			DepartmentOrganization blank = new DepartmentOrganization();
			list.add(0, blank);
		}

		return list;
	}

	/**
	 * @return ServletのClassを返す
	 */
	protected Class getModelClass() {
		return DepartmentOrganizationManager.class;
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

		switch (TDepartmentOrganizationUnit.DISPLAY_NAME_FLG) {
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

package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.language.*;

/**
 * 言語コードコンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TLanguageComboBoxController extends TController {

	/** コンボボックス */
	protected TLanguageComboBox comboBox;

	/** 検索条件 */
	protected LanguageSearchCondition condition;

	/**
	 * @param comboBox フィールド
	 */
	public TLanguageComboBoxController(TLanguageComboBox comboBox) {

		this.comboBox = comboBox;

		init();

	}

	/**
	 * 初期化
	 */
	private void init() {

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
	protected LanguageSearchCondition createSearchCondition() {
		return new LanguageSearchCondition();
	}

	/**
	 * コンボボックスのリストを返す
	 * 
	 * @param condition
	 */
	@SuppressWarnings("unchecked")
	private void getList(LanguageSearchCondition condition) {

		try {

			List<Language> list =
				(List<Language>) request(getModelClass(), "getLanguage", condition);

			for (Language lang : list) {
				comboBox.getComboBox().addTextValueItem(lang.getCode(), lang.getName());
			}

		} catch (TException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @return ServletのClassを返す
	 */
	protected Class getModelClass() {
		return LanguageManager.class;
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

}

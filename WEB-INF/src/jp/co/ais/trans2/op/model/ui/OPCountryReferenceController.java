package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.country.*;

/**
 * 国検索コンポーネントのコントローラ
 */
public class OPCountryReferenceController extends TCountryReferenceController {

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public OPCountryReferenceController(TReference field) {
		super(field);
	}

	/**
	 * 検索条件を取得する
	 * 
	 * @return 検索条件
	 */
	@Override
	protected CountrySearchCondition getCondition() {
		return condition;
	}

	/**
	 * Countryを取得する
	 * 
	 * @param condition_ 検索条件
	 * @return Country情報
	 */
	@Override
	protected List<Country> getList(CountrySearchCondition condition_) {
		try {
			List<Country> list = OPLoginUtil.getCountryList(condition_);
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}

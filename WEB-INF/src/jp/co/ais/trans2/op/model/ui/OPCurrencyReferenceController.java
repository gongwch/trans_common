package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 通貨検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class OPCurrencyReferenceController extends TCurrencyReferenceController {

	/** 基準日 */
	public Date baseDate;

	/** デフォルトリスト */
	public ArrayList defaultList;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public OPCurrencyReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return ダイアログ画面での検索条件を取得
	 */
	@Override
	protected CurrencySearchCondition getCondition() {
		return condition;
	}

	@Override
	public void init() {

		super.init();

		field.code.setRegex(null); // 小文字入力可能
	}

	/**
	 * フィールドからコードを直接入力した場合の検索
	 * 
	 * @return Entity
	 */
	@Override
	protected Currency getInputtedEntity() {

		field.code.setText(field.code.getText().toUpperCase()); // 大文字自動変換

		return super.getInputtedEntity();
	}

	/**
	 * @param condition_
	 * @return 通貨情報
	 */
	@Override
	protected List<Currency> getList(CurrencySearchCondition condition_) {

		try {
			List<Currency> list = OPLoginUtil.getCurrencyList(condition_);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	@Override
	public Object getUnspecifiedEntity() {
		Currency entity1 = new Currency();
		entity1.setCode(field.code.getText().toUpperCase()); // 大文字自動変換
		entity1.setNames(field.name.getText());
		return entity1;
	}

	/**
	 * 基準日設定
	 * 
	 * @param termDate
	 */
	public void setTermDate(Date termDate) {
		if (field.name.getAutoComplete() == null) {
			return;
		}

		Date dt = termDate;
		if (dt == null) {
			// 基準日未指定の場合はシステム日付
			dt = Util.getCurrentDate();
		}

		boolean isChanged = !equals(this.baseDate, dt);

		this.baseDate = dt;

		// デフォルトリストの保持
		if (defaultList == null) {
			if (field.name.getAutoComplete().getMatchDataList() != null) {
				defaultList = new ArrayList(field.name.getAutoComplete().getMatchDataList());
			}
		}

		if (isChanged) {

			// 1. インクリメントサーチのリスト更新

			List<Currency> list = null;
			CurrencySearchCondition param = condition.clone();
			param.setValidTerm(this.baseDate);

			if (defaultList != null) {
				list = OPLoginUtil.getFilterList(defaultList, param);
			} else {
				list = getList(param);
			}

			field.name.getAutoComplete().setMatchData(list);

			boolean hasData = false;
			if (list != null && !list.isEmpty()) {
				for (Currency bean : list) {
					if (equals(bean.getCode(), field.getCode())) {
						hasData = true;
						break;
					}
				}
			}

			if (!hasData && field.name.isEditable()) {
				// 期間切れた場合に船変更可能であればクリアする
				clear();
			}
		}
	}

}

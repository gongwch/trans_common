package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * 取引先検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class OPCustomerReferenceController extends TCustomerReferenceController {

	/** 基準日 */
	public Date baseDate;

	/** デフォルトリスト */
	public ArrayList defaultList;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public OPCustomerReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return ダイアログ画面での検索条件を取得
	 */
	@Override
	protected CustomerSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return 取引先Entity
	 */
	@Override
	protected List<Customer> getList(CustomerSearchCondition condition_) {
		try {
			List<Customer> list = OPLoginUtil.getCustomerList(condition_);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
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

			List<Customer> list = null;
			CustomerSearchCondition param = condition.clone();
			param.setValidTerm(this.baseDate);

			if (defaultList != null) {
				list = OPLoginUtil.getFilterList(defaultList, param);
			} else {
				list = getList(param);
			}

			field.name.getAutoComplete().setMatchData(list);

			boolean hasData = false;
			if (list != null && !list.isEmpty()) {
				for (Customer bean : list) {
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

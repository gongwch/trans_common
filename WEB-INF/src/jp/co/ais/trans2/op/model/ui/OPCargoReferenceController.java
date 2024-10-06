package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.cargo.*;

/**
 * Cargoの検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class OPCargoReferenceController extends TCargoReferenceController {

	/** 基準日 */
	public Date baseDate;

	/** デフォルトリスト */
	public ArrayList defaultList;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public OPCargoReferenceController(TReference field) {
		super(field);
	}

	/**
	 * 検索条件を取得する
	 * 
	 * @return 検索条件
	 */
	@Override
	protected CargoSearchCondition getCondition() {
		return condition;
	}

	/**
	 * Cargoを取得する
	 * 
	 * @param condition_ 検索条件
	 * @return Cargo情報
	 */
	@Override
	protected List<Cargo> getList(CargoSearchCondition condition_) {
		try {
			List<Cargo> list = OPLoginUtil.getCargoList(condition_);
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

			List<Cargo> list = null;
			CargoSearchCondition param = condition.clone();
			param.setValidTerm(this.baseDate);

			if (defaultList != null) {
				list = OPLoginUtil.getFilterList(defaultList, param);
			} else {
				list = getList(param);
			}

			field.name.getAutoComplete().setMatchData(list);

			boolean hasData = false;
			if (list != null && !list.isEmpty()) {
				for (Cargo bean : list) {
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

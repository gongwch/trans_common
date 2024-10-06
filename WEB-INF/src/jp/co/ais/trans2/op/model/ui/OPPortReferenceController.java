package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.port.*;

/**
 * Portの検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class OPPortReferenceController extends TPortReferenceController {

	/** 基準日 */
	public Date baseDate;

	/** デフォルトリスト */
	public ArrayList defaultList;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public OPPortReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return 検索条件を返す
	 */
	@Override
	protected PortSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return Port情報
	 */
	@Override
	protected List<Port> getList(PortSearchCondition condition_) {
		try {
			List<Port> list = OPLoginUtil.getPortList(condition_);
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

			List<Port> list = null;
			PortSearchCondition param = condition.clone();
			param.setValidTerm(this.baseDate);

			if (defaultList != null) {
				list = OPLoginUtil.getFilterList(defaultList, param);
			} else {
				list = getList(param);
			}

			field.name.getAutoComplete().setMatchData(list);

			boolean hasData = false;
			if (list != null && !list.isEmpty()) {
				for (Port bean : list) {
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

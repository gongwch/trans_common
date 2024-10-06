package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * Vesselの検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class OPVesselReferenceController extends TVesselReferenceController {

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public OPVesselReferenceController(TReference field) {
		super(field);
	}

	/**
	 * 検索条件を取得する
	 * 
	 * @return 検索条件
	 */
	@Override
	protected VesselSearchCondition getCondition() {
		return condition;
	}

	/**
	 * Vesselを取得する
	 * 
	 * @param condition_ 検索条件
	 * @return Vessel情報
	 */
	@Override
	protected List<Vessel> getList(VesselSearchCondition condition_) {
		try {
			List<Vessel> list = OPLoginUtil.getVesselList(condition_);
			return list;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}

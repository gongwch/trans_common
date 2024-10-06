package jp.co.ais.trans2.op.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ユーザ検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class OPUserReferenceController extends TUserReferenceController {

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public OPUserReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return 検索条件を返す
	 */
	@Override
	protected UserSearchCondition getCondition() {
		return condition;
	}

}

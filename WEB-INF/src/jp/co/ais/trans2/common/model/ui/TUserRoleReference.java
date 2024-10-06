package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ユーザーロールマスタの検索コンポーネント
 * 
 * @author AIS
 */
public class TUserRoleReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 6380044260658661264L;

	/** コントローラ */
	protected TUserRoleReferenceController controller;

	/**
	 * 
	 *
	 */
	public TUserRoleReference() {

		// コントローラ生成
		controller = new TUserRoleReferenceController(this);

	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public UserRole getEntity() {
		return controller.getEntity();
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public UserSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param userRole ユーザーロール
	 */
	public void setEntity(UserRole userRole) {
		controller.setEntity(userRole);
	}

}

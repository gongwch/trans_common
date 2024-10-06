package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ユーザーの検索コンポーネント
 * 
 * @author AIS
 */
public class TUserReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = -8122503842627737947L;

	/** コントローラ */
	protected TUserReferenceController controller;

	/**
	 * 
	 *
	 */
	public TUserReference() {

		// コントローラ生成
		controller = createController();

	}

	/**
	 * Controllerを生成
	 * 
	 * @return controller
	 */
	protected TUserReferenceController createController() {
		return new TUserReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public UserSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public void refleshAndShowEntity() {
		this.controller.refleshEntity();
		this.controller.setEntity(getEntity());
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public User getEntity() {
		return (User) getController().getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param user ユーザー
	 */
	public void setEntity(User user) {
		controller.setEntity(user);
	}
}

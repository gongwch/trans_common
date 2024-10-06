package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * 承認グループマスタの検索コンポーネント
 * 
 * @author AIS
 */
public class TAprvRoleGroupReference extends TReference {

	/** コントローラ */
	protected TAprvRoleGroupReferenceController controller;

	/**
	 * 
	 *
	 */
	public TAprvRoleGroupReference() {

		// コントローラ生成
		controller = new TAprvRoleGroupReferenceController(this);

	}

	@Override
	public TAprvRoleGroupReferenceController getController() {
		return controller;
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public AprvRoleGroup getEntity() {
		return controller.getEntity();
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public AprvRoleGroupSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param entity
	 */
	public void setEntity(AprvRoleGroup entity) {
		controller.setEntity(entity);
	}

}

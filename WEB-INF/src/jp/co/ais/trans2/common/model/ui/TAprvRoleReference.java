package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * 承認権限ロールマスタの検索コンポーネント
 * 
 * @author AIS
 */
public class TAprvRoleReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 6380044260658661264L;

	/** コントローラ */
	protected TAprvRoleReferenceController controller;

	/**
	 * 
	 *
	 */
	public TAprvRoleReference() {

		// コントローラ生成
		controller = new TAprvRoleReferenceController(this);

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
	public AprvRole getEntity() {
		return controller.getEntity();
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public AprvRoleSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * Entityをセットする
	 * @param aprvUser 
	 */
	public void setEntity(AprvRole aprvUser) {
		controller.setEntity(aprvUser);
	}

}

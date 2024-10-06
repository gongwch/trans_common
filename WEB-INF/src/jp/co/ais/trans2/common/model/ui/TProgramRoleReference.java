package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.security.*;

/**
 * プログラムロールマスタの検索コンポーネント
 * 
 * @author AIS
 */
public class TProgramRoleReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 6380044260658661264L;

	/** コントローラ */
	protected TProgramRoleReferenceController controller;

	/**
	 * 
	 *
	 */
	public TProgramRoleReference() {
		controller = new TProgramRoleReferenceController(this);
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
	public ProgramRoleSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public ProgramRole getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param programRole プログラムロール
	 */
	public void setEntity(ProgramRole programRole) {
		controller.setEntity(programRole);
	}

}

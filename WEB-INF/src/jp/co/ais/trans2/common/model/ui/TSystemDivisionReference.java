package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.TReferenceController;
import jp.co.ais.trans2.model.program.SystemDivision;
import jp.co.ais.trans2.model.program.SystemDivisionSearchCondition;


/**
 * システム区分検索コンポーネント
 */
public class TSystemDivisionReference extends TReference {

	/** コントローラ */
	public TSystemDivisionReferenceController controller;

	/**
	 * コンストラクタ
	 *
	 * @param title
	 */
	public TSystemDivisionReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 */
	public TSystemDivisionReference() {
		super();
		controller = createController();
	}

	/**
	 * コンストラクタ
	 *
	 * @param type タイプ
	 */
	public TSystemDivisionReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 *
	 * @param type タイプ
	 * @param title
	 */
	public TSystemDivisionReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * コントローラ生成
	 *
	 * @return コントローラ
	 */
	public TSystemDivisionReferenceController createController() {
		return new TSystemDivisionReferenceController(this);
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
	public SystemDivisionSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 *
	 * @return 選択されたEntity
	 */
	public SystemDivision getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 *
	 * @param entity エンティティ
	 */
	public void setEntity(SystemDivision entity) {
		controller.setEntity(entity);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}
}

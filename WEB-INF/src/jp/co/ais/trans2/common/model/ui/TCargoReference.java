package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.cargo.*;

/**
 * Cargo検索コンポーネント
 * 
 * @author AIS
 */
public class TCargoReference extends TReference {

	/** コントローラ */
	protected TCargoReferenceController controller;

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TCargoReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 */
	public TCargoReference() {
		super();
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TCargoReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public TCargoReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	protected TCargoReferenceController createController() {
		return new TCargoReferenceController(this);
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
	public CargoSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Cargo getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param bean エンティティ
	 */
	public void setEntity(Cargo bean) {
		controller.setEntity(bean);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}

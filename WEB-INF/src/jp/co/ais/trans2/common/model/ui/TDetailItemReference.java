package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 内訳科目の検索コンポーネント
 * 
 * @author AIS
 */
public class TDetailItemReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = -2442214843620387829L;

	/** コントローラ */
	protected TDetailItemReferenceController controller;

	/**
	 * コンストラクタ
	 */
	public TDetailItemReference() {

		// コントローラ生成
		controller = createController();

	}

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TDetailItemReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TDetailItemReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public TDetailItemReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return TDetailItemReferenceController
	 */
	public TDetailItemReferenceController createController() {
		return new TDetailItemReferenceController(this);
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
	public DetailItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Item getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセット
	 * 
	 * @param item Entity
	 */
	public void setEntity(Item item) {
		controller.setEntity(item);
	}
}

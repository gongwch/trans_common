package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 補助科目の検索コンポーネント
 * 
 * @author AIS
 */
public class TSubItemReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = -8638123416261093176L;

	/** コントローラ */
	protected TSubItemReferenceController controller;

	/**
	 * コンストラクタ
	 */
	public TSubItemReference() {
		// コントローラ生成
		controller = createController();

	}

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TSubItemReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TSubItemReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public TSubItemReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return TSubItemReferenceController
	 */
	public TSubItemReferenceController createController() {
		return new TSubItemReferenceController(this);
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
	public SubItemSearchCondition getSearchCondition() {
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

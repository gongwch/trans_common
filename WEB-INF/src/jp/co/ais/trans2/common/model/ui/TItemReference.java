package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目の検索コンポーネント
 * 
 * @author AIS
 */
public class TItemReference extends TReference {

	/** コントローラ */
	protected TItemReferenceController controller;

	/**
	 * コンストラクタ
	 */
	public TItemReference() {
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TItemReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TItemReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public TItemReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return コントローラ
	 */
	public TItemReferenceController createController() {
		return new TItemReferenceController(this);
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
	public ItemSearchCondition getSearchCondition() {
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

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}

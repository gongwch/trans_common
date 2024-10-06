package jp.co.ais.trans2.op.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.op.model.item.*;

/**
 * Item reference.
 * 
 * @author Ngoc Nguyen
 */
public class OPItemReference extends TReference {

	/** The controller */
	protected OPItemReferenceController controller;

	/**
	 * コンストラクタ
	 */
	public OPItemReference() {
		this(null);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public OPItemReference(String title) {
		super(title);

		this.controller = createController();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return OPItemReferenceController
	 */
	public OPItemReferenceController createController() {
		return new OPItemReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return this.controller;
	}

	/**
	 * OPアイテム検索条件の取得
	 * 
	 * @return the search condition
	 */
	public OPItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	public OPItem getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param bean エンティティ
	 */
	public void setEntity(OPItem bean) {
		controller.setEntity(bean);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}

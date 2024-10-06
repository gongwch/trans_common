package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.slip.*;


/**
 * 伝票種別検索コンポーネント
 */
public class TSlipTypeReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = -1369641370951036568L;

	/** コントローラ */
	protected TSlipTypeReferenceController controller;

	/**
	 *
	 */
	public TSlipTypeReference() {
		controller = new TSlipTypeReferenceController(this);
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public SlipTypeSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public SlipType getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param sliptype エンティティ
	 */
	public void setEntity(SlipType sliptype) {
		controller.setEntity(sliptype);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}

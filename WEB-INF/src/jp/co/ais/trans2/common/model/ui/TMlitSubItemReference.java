package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * 輸送実績サブアイテムマスタ検索コンポーネント
 * 
 * @author AIS
 */
public class TMlitSubItemReference extends TReference {

	/** コントローラ */
	protected TMlitSubItemReferenceController controller;

	/**
	 * コンストラクター
	 */
	public TMlitSubItemReference() {
		controller = createController();
	}

	/**
	 * @return コントローラ
	 */
	protected TMlitSubItemReferenceController createController() {
		return new TMlitSubItemReferenceController(this);
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
	public YJItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public YJItem getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param bean Vesselマスタ
	 */
	public void setEntity(YJItem bean) {
		controller.setEntity(bean);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}
}

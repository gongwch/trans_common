package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * 輸送実績国マスタ検索コンポーネント
 * 
 * @author AIS
 */
public class TMlitRegionReference extends TReference {

	/** コントローラ */
	protected TMlitRegionReferenceController controller;

	/**
	 * コンストラクター
	 */
	public TMlitRegionReference() {
		controller = createController();
	}

	/**
	 * @return コントローラ
	 */
	protected TMlitRegionReferenceController createController() {
		return new TMlitRegionReferenceController(this);
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
	public YJRegionSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public YJRegion getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param bean Vesselマスタ
	 */
	public void setEntity(YJRegion bean) {
		controller.setEntity(bean);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}
}

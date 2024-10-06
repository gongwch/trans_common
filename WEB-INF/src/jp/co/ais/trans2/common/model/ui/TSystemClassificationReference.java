package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.program.SystemClassification;
import jp.co.ais.trans2.model.program.SystemClassificationSearchCondition;

/**
 * システムの検索コンポーネント
 * @author AIS
 *
 */
public class TSystemClassificationReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 8817226532784567130L;

	/** コントローラ */
	protected TSystemClassificationReferenceController controller;

	/**
	 * 
	 *
	 */
	public TSystemClassificationReference() {

		// コントローラ生成
		controller = new TSystemClassificationReferenceController(this);
		
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * @return 検索条件
	 */
	public SystemClassificationSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * Entityをセットする
	 * @param bean Entity
	 */
	public void setEntity(SystemClassification bean) {
		controller.setEntity(bean);
	}

	/**
	 * Entityを返す
	 * @return Entity
	 */
	public SystemClassification getEntity() {
		return controller.getEntity();
	}

}

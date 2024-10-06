package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 消費税の検索コンポーネント
 * 
 * @author AIS
 */
public class TTaxReference extends TReference {

	/** コントローラ */
	protected TTaxReferenceController controller;

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TTaxReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 */
	public TTaxReference() {
		super();
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TTaxReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public TTaxReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	protected TTaxReferenceController createController() {
		return new TTaxReferenceController(this);
	}

	@Override
	public TTaxReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public ConsumptionTaxSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public ConsumptionTax getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param entity エンティティ
	 */
	public void setEntity(ConsumptionTax entity) {
		controller.setEntity(entity);
	}

	@Override
	protected int getCodeLength() {
		return TransUtil.TAX_CODE_LENGTH;
	}

	@Override
	protected void allocateComponents() {
		super.allocateComponents();
		code.setMaximumSize(new Dimension(30, 20));
		code.setMinimumSize(new Dimension(30, 20));
		code.setPreferredSize(new Dimension(30, 20));
		resize();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.<br>
	 * 表示を更新する
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}

}

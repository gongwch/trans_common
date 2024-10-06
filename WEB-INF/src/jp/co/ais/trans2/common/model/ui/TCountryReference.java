package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.country.*;

/**
 * 国検索コンポーネント
 */
public class TCountryReference extends TReference {

	/** コントローラ */
	protected TCountryReferenceController controller;

	/**
	 * コンストラクタ
	 */
	public TCountryReference() {
		this(TYPE.BUTTON, "");
	}

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TCountryReference(String title) {
		this(TYPE.BUTTON, title);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type
	 */
	public TCountryReference(TYPE type) {
		this(type, "");
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type
	 * @param title 外から設定したい場合のキャプション
	 */
	public TCountryReference(TYPE type, String title) {
		super(type, title);

		controller = createController();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	protected TCountryReferenceController createController() {
		return new TCountryReferenceController(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TReference#getController()
	 */
	@Override
	protected TCountryReferenceController getController() {
		return controller;
	}

	/**
	 * コンポーネントを配置する
	 */
	@Override
	protected void allocateComponents() {
		super.allocateComponents();

		TGuiUtil.setComponentSize(code, new Dimension(40, 20));
	}

	/**
	 * コードの長さを返す
	 * 
	 * @return コード長
	 */
	@Override
	protected int getCodeLength() {
		return 3;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public CountrySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Country getEntity() {
		return controller.getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param entity エンティティ
	 */
	public void setEntity(Country entity) {
		controller.setEntity(entity);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}
}

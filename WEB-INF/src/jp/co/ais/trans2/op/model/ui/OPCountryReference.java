package jp.co.ais.trans2.op.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.country.*;

/**
 * 国検索コンポーネント
 */
public class OPCountryReference extends TCountryReference {

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public OPCountryReference(String title) {
		super(title);
	}

	/**
	 * コンストラクタ
	 */
	public OPCountryReference() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public OPCountryReference(TYPE type) {
		super(type);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public OPCountryReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * コントローラ生成
	 */
	@Override
	protected TCountryReferenceController createController() {
		// コントローラ生成
		return new OPCountryReferenceController(this);
	}

	/**
	 * コンポーネントを初期化する<BR>
	 */
	@Override
	protected void initComponents() {

		// トラオペ共通対応
		if (isLabelMode()) {
			// 強制的にラベルにする
			this.type = TYPE.LABEL;
		}

		super.initComponents();
	}

	/**
	 * コンポーネントを配置する
	 */
	@Override
	protected void allocateComponents() {

		// トラオペ共通対応
		if (isLabelMode()) {
			OPGuiUtil.allocateComponents(this);
		} else {
			super.allocateComponents();
		}
	}

	/**
	 * @return true:ラベルモード
	 */
	protected boolean isLabelMode() {
		return OPGuiUtil.isLabelMode();
	}

	/**
	 * コントローラのファクトリ
	 */
	@Override
	public OPCountryReferenceController getController() {
		return (OPCountryReferenceController) controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	public CountrySearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	@Override
	public Country getEntity() {
		return getController().getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param vessel エンティティ
	 */
	@Override
	public void setEntity(Country vessel) {
		getController().setEntity(vessel);
	}
}

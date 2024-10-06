package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.cargo.*;

/**
 * Cargoの検索コンポーネント
 * 
 * @author AIS
 */
public class OPCargoReference extends TCargoReference {

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public OPCargoReference(String title) {
		super(title);
	}

	/**
	 * コンストラクタ
	 */
	public OPCargoReference() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public OPCargoReference(TYPE type) {
		super(type);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public OPCargoReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * コントローラ生成
	 */
	@Override
	protected TCargoReferenceController createController() {
		// コントローラ生成
		return new OPCargoReferenceController(this);
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
	public OPCargoReferenceController getController() {
		return (OPCargoReferenceController) controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	public CargoSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	@Override
	public Cargo getEntity() {
		return getController().getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param vessel エンティティ
	 */
	@Override
	public void setEntity(Cargo vessel) {
		getController().setEntity(vessel);
	}

	/**
	 * 基準日設定
	 * 
	 * @param termDate
	 */
	public void setTermDate(Date termDate) {
		getController().setTermDate(termDate);
	}
}

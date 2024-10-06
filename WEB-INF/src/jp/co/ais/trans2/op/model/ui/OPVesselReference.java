package jp.co.ais.trans2.op.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * Vesselの検索コンポーネント
 * 
 * @author AIS
 */
public class OPVesselReference extends TVesselReference {

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public OPVesselReference(String title) {
		super(title);
	}

	/**
	 * コンストラクタ
	 */
	public OPVesselReference() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public OPVesselReference(TYPE type) {
		super(type);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public OPVesselReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * コントローラ生成
	 */
	@Override
	protected void initController() {
		// コントローラ生成
		if (controller == null) {
			controller = new OPVesselReferenceController(this);
		}
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
	public OPVesselReferenceController getController() {
		return (OPVesselReferenceController) controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	public VesselSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	@Override
	public Vessel getEntity() {
		return getController().getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param vessel エンティティ
	 */
	@Override
	public void setEntity(Vessel vessel) {
		getController().setEntity(vessel);
	}
}

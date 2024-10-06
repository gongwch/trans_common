package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.port.*;

/**
 * Portの検索コンポーネント
 * 
 * @author AIS
 */
public class OPPortReference extends TPortReference {

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public OPPortReference(String title) {
		super(title);
	}

	/**
	 * コンストラクタ
	 */
	public OPPortReference() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public OPPortReference(TYPE type) {
		super(type);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public OPPortReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * コントローラ生成
	 */
	@Override
	protected void initController() {
		// コントローラ生成
		if (controller == null) {
			controller = new OPPortReferenceController(this);
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

	@Override
	public OPPortReferenceController getController() {
		return (OPPortReferenceController) controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	public PortSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * 選択されているエンティティ
	 * 
	 * @return エンティティ
	 */
	@Override
	public Port getEntity() {
		return getController().getEntity();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param port エンティティ
	 */
	@Override
	public void setEntity(Port port) {
		getController().setEntity(port);
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

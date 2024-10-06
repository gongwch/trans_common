package jp.co.ais.trans2.op.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.user.*;

/**
 * 取引先検索コンポーネント
 * 
 * @author AIS
 */
public class OPUserReference extends TUserReference {

	/**
	 * コンストラクタ
	 */
	public OPUserReference() {
		super();
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
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	@Override
	protected TUserReferenceController createController() {
		return new OPUserReferenceController(this);
	}

	@Override
	public OPUserReferenceController getController() {
		return (OPUserReferenceController) controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	public UserSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * エンティティをセット
	 * 
	 * @param bean エンティティ
	 */
	public void setEntity(User bean) {
		getController().setEntity(bean);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		getController().refleshEntity();
	}

}

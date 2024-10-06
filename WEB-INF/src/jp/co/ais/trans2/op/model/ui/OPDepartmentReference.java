package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門検索コンポーネント
 * 
 * @author AIS
 */
public class OPDepartmentReference extends TDepartmentReference {

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public OPDepartmentReference(String title) {
		super(title);
	}

	/**
	 * コンストラクタ
	 */
	public OPDepartmentReference() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public OPDepartmentReference(TYPE type) {
		super(type);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public OPDepartmentReference(TYPE type, String title) {
		super(type, title);
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
	protected OPDepartmentReferenceController createController() {
		return new OPDepartmentReferenceController(this);
	}

	@Override
	protected OPDepartmentReferenceController getController() {
		return (OPDepartmentReferenceController) controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	public DepartmentSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Department getEntity() {
		return getController().getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param department 部門
	 */
	@Override
	public void setEntity(Department department) {
		getController().setEntity(department);
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	@Override
	public void refleshEntity() {
		getController().refleshEntity();
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

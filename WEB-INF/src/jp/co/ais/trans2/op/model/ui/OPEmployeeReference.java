package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * 社員の検索コンポーネント
 * 
 * @author AIS
 */
public class OPEmployeeReference extends TEmployeeReference {

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public OPEmployeeReference(String title) {
		super(title);
	}

	/**
	 * コンストラクタ
	 */
	public OPEmployeeReference() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public OPEmployeeReference(TYPE type) {
		super(type);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public OPEmployeeReference(TYPE type, String title) {
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
	public OPEmployeeReferenceController createController() {
		return new OPEmployeeReferenceController(this);
	}

	@Override
	public OPEmployeeReferenceController getController() {
		return (OPEmployeeReferenceController) controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	@Override
	public EmployeeSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Employee getEntity() {
		return getController().getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param employee 社員
	 */
	@Override
	public void setEntity(Employee employee) {
		getController().setEntity(employee);
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

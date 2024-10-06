package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * 社員の検索コンポーネント
 * 
 * @author AIS
 */
public class TEmployeeReference extends TReference {

	/** コントローラ */
	protected TEmployeeReferenceController controller;

	/** 入力部門コード */
	protected String inputDepartmentCode;

	/** true:全SPCモード */
	protected boolean allCompanyMode = false;

	/**
	 * コンストラクタ
	 * 
	 * @param title
	 */
	public TEmployeeReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 */
	public TEmployeeReference() {
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 */
	public TEmployeeReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type タイプ
	 * @param title
	 */
	public TEmployeeReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	public TEmployeeReferenceController createController() {
		return new TEmployeeReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public EmployeeSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	public Employee getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param employee 社員
	 */
	public void setEntity(Employee employee) {
		controller.setEntity(employee);
	}

	/**
	 * 伝票の参照権限をセットする。<br>
	 * 権限によって社員フィールド制御する。
	 * 
	 * @param slipRole 伝票参照権限
	 * @param employee 社員
	 */
	public void setSlipRole(SlipRole slipRole, Employee employee) {
		controller.setSlipRole(slipRole, employee);
	}

	/**
	 * 入力部門をセットする
	 * 
	 * @param inputDepartmentCode 入力部門
	 */
	public void setInputDepartmentCode(String inputDepartmentCode) {
		this.inputDepartmentCode = inputDepartmentCode;
	}

	/**
	 * 入力部門を返す
	 * 
	 * @return inputDepartmentCode 入力部門
	 */
	public String getInputDepartmentCode() {
		return this.inputDepartmentCode;
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

	/**
	 * true:全SPCモードの取得
	 * 
	 * @return allCompanyMode true:全SPCモード
	 */
	public boolean isAllCompanyMode() {
		return allCompanyMode;
	}

	/**
	 * true:全SPCモードの設定
	 * 
	 * @param allCompanyMode true:全SPCモード
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {
		this.allCompanyMode = allCompanyMode;

		if (allCompanyMode) {
			getSearchCondition().setCompanyCode(null);
		} else {
			getSearchCondition().setCompanyCode(TLoginInfo.getCompany().getCode());
		}
	}

}

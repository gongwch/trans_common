package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.client.TController;

/**
 * 入力部門/入力者フィールドコンポーネントのコントローラ
 * @author AIS
 *
 */
public class TDepEmpReferenceUnitController extends TController {

	/** 入力部門/入力者コンポーネント */
	protected TDepEmpReferenceUnit field;

	/**
	 * コンストラクタ
	 * @param field フィールド
	 */
	public TDepEmpReferenceUnitController(TDepEmpReferenceUnit field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 *
	 */
	protected void init() {

		// 入力部門に伝票参照権限付与
		field.ctrlDepartmentReference.setSlipRole(
				getUser().getSlipRole(),
				getUser().getDepartment());

		// 入力者に伝票参照権限付与
		field.ctrlEmployeeReference.setSlipRole(
				getUser().getSlipRole(),
				getUser().getEmployee());

	}

}

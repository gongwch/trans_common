package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.department.Department;
import jp.co.ais.trans2.model.employee.Employee;

/**
 * 入力部門/入力者フィールドコンポーネント
 * 
 * @author AIS
 */
public class TDepEmpReferenceUnit extends TPanel {

	/** 部門フィールド */
	public TDepartmentReference ctrlDepartmentReference;

	/** 社員フィールド */
	public TEmployeeReference ctrlEmployeeReference;

	/** コントローラ */
	protected TDepEmpReferenceUnitController controller;

	/**
	 * コンストラクタ
	 */
	public TDepEmpReferenceUnit() {

		initComponents();

		allocateComponents();

		controller = new TDepEmpReferenceUnitController(this);
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {
		ctrlDepartmentReference = new TDepartmentReference();
		ctrlEmployeeReference = new TEmployeeReference();
	}

	/**
	 * コンポーネントの配置
	 */
	public void allocateComponents() {

		setLayout(null);

		// 入力部門
		ctrlDepartmentReference.btn.setLangMessageID("C01280");
		ctrlDepartmentReference.setLocation(0, 0);
		add(ctrlDepartmentReference);

		// 入力者
		ctrlEmployeeReference.btn.setLangMessageID("C01278");
		ctrlEmployeeReference.setLocation(0, ctrlDepartmentReference.getY() + ctrlDepartmentReference.getHeight() + 5);
		add(ctrlEmployeeReference);

		setSize(ctrlDepartmentReference.getWidth(), ctrlDepartmentReference.getHeight()
			+ ctrlEmployeeReference.getHeight() + 5);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabNo タブ順
	 */
	public void setTabControlNo(int tabNo) {
		ctrlDepartmentReference.setTabControlNo(tabNo);
		ctrlEmployeeReference.setTabControlNo(tabNo);
	}

	/**
	 * 部門コードを返す
	 * 
	 * @return 部門コード
	 */
	public String getDepartmentCode() {
		return ctrlDepartmentReference.getCode();
	}

	/**
	 * 社員コードを返す
	 * 
	 * @return 社員コード
	 */
	public String getEmployeeCode() {
		return ctrlEmployeeReference.getCode();
	}

	/**
	 * 部門を返す
	 * 
	 * @return 部門
	 */
	public Department getDepartment() {
		return ctrlDepartmentReference.getEntity();
	}

	/**
	 * 社員を返す
	 * 
	 * @return 社員
	 */
	public Employee getEmployee() {
		return ctrlEmployeeReference.getEntity();
	}

}

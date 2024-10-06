package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 管理選択：管理選択+範囲検索+任意選択：コントローラ （部門追加版）
 * 
 * @author AIS
 */
public class TManagementSelectUnitAddDepController extends TManagementSelectUnitController {

	/**
	 * @param field フィールド
	 */
	public TManagementSelectUnitAddDepController(TManagementSelectUnit field) {
		super(field);
	}

	/**
	 * コンボボックス、検索フィールドの初期化
	 * 
	 * @param company
	 */
	@Override
	protected void setModel(Company company) {

		AccountConfig config = company.getAccountConfig();

		// 条件なし
		field.cbo.addTextValueItem(ManagementAngle.NONE, getWord("C01748"));
		field.ctrlReferenceRangeUnits.add(new TNoneReferenceRangeUnit());

		// 取引先
		field.cbo.addTextValueItem(ManagementAngle.DEPARTMENT, getWord("C00467"));
		field.ctrlReferenceRangeUnits.add(new TDepartmentReferenceRangeUnit());

		// 取引先
		field.cbo.addTextValueItem(ManagementAngle.CUSTOMER, getWord("C00408"));
		field.ctrlReferenceRangeUnits.add(new TCustomerReferenceRangeUnit());

		if (ClientConfig.isFlagOn("trans.management.use.sum.tricode")) {
			// 集計取引先
			TCustomerReferenceRangeUnit sumRef = new TCustomerReferenceRangeUnit();
			sumRef.ctrlReferenceRange.ctrlCustomerReferenceFrom.getSearchCondition().setSearchSumCode(true);
			sumRef.ctrlReferenceRange.ctrlCustomerReferenceTo.getSearchCondition().setSearchSumCode(true);
			sumRef.ctrlOptionalSelector.getSearchCondition().setSearchSumCode(true);
			field.cbo.addTextValueItem(ManagementAngle.SUMCUSTOMER, getWord("C01149"));
			field.ctrlReferenceRangeUnits.add(sumRef);
		}
		// 社員
		field.cbo.addTextValueItem(ManagementAngle.EMPLOYEE, getWord("C00246"));
		field.ctrlReferenceRangeUnits.add(new TEmployeeReferenceRangeUnit());

		// 管理1
		if (config.isUseManagement1()) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT1, config.getManagement1Name());
			field.ctrlReferenceRangeUnits.add(new TManagement1ReferenceRangeUnit());
		}

		// 管理2
		if (config.isUseManagement2()) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT2, config.getManagement2Name());
			field.ctrlReferenceRangeUnits.add(new TManagement2ReferenceRangeUnit());
		}

		// 管理3
		if (config.isUseManagement3()) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT3, config.getManagement3Name());
			field.ctrlReferenceRangeUnits.add(new TManagement3ReferenceRangeUnit());
		}

		// 管理4
		if (config.isUseManagement4()) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT4, config.getManagement4Name());
			field.ctrlReferenceRangeUnits.add(new TManagement4ReferenceRangeUnit());
		}

		// 管理5
		if (config.isUseManagement5()) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT5, config.getManagement5Name());
			field.ctrlReferenceRangeUnits.add(new TManagement5ReferenceRangeUnit());
		}

		// 管理6
		if (config.isUseManagement6()) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT6, config.getManagement6Name());
			field.ctrlReferenceRangeUnits.add(new TManagement6ReferenceRangeUnit());
		}

		for (TReferenceRangeUnit rangeUnit : field.ctrlReferenceRangeUnits) {
			if (field.isBorder()) {
				rangeUnit.setLocation(15, 30);
			} else {
				rangeUnit.setLocation(0, 20);
			}
			field.add(rangeUnit);
		}

	}

}

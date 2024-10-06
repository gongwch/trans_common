package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理選択：管理選択+範囲検索+任意選択：コントローラ
 * 
 * @author AIS
 */
public class TManagementSelectUnitController extends TController {

	/** フィールド */
	protected TManagementSelectUnit field;

	/**
	 * @param field フィールド
	 */
	public TManagementSelectUnitController(TManagementSelectUnit field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		setModel(getCompany());
		addEvent();
		cbo_itemStateChanged();
	}

	/**
	 * コンボボックス、検索フィールドの初期化
	 * 
	 * @param company
	 */
	protected void setModel(Company company) {

		AccountConfig config = company.getAccountConfig();

		// 条件なし
		field.cbo.addTextValueItem(ManagementAngle.NONE, getWord("C01748"));
		field.ctrlReferenceRangeUnits.add(new TNoneReferenceRangeUnit());

		// 部門
		if (ClientConfig.isFlagOn("trans.management.use.department")) {
			field.cbo.addTextValueItem(ManagementAngle.DEPARTMENT, getWord("C00467"));
			field.ctrlReferenceRangeUnits.add(new TDepartmentReferenceRangeUnit());
		}

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
		if (config.isUseManagement1() && !ClientConfig.isFlagOn("trans.management.not.use.mng1")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT1, config.getManagement1Name());
			field.ctrlReferenceRangeUnits.add(new TManagement1ReferenceRangeUnit());
		}

		// 管理2
		if (config.isUseManagement2() && !ClientConfig.isFlagOn("trans.management.not.use.mng2")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT2, config.getManagement2Name());
			field.ctrlReferenceRangeUnits.add(new TManagement2ReferenceRangeUnit());
		}

		// 管理3
		if (config.isUseManagement3() && !ClientConfig.isFlagOn("trans.management.not.use.mng3")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT3, config.getManagement3Name());
			field.ctrlReferenceRangeUnits.add(new TManagement3ReferenceRangeUnit());
		}

		// 管理4
		if (config.isUseManagement4() && !ClientConfig.isFlagOn("trans.management.not.use.mng4")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT4, config.getManagement4Name());
			field.ctrlReferenceRangeUnits.add(new TManagement4ReferenceRangeUnit());
		}

		// 管理5
		if (config.isUseManagement5() && !ClientConfig.isFlagOn("trans.management.not.use.mng5")) {
			field.cbo.addTextValueItem(ManagementAngle.MANAGEMENT5, config.getManagement5Name());
			field.ctrlReferenceRangeUnits.add(new TManagement5ReferenceRangeUnit());
		}

		// 管理6
		if (config.isUseManagement6() && !ClientConfig.isFlagOn("trans.management.not.use.mng6")) {
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

	/**
	 * イベント追加
	 */
	public void addEvent() {

		field.cbo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					cbo_itemStateChanged();
				}
			}
		});

	}

	/**
	 * コンボボックスのアイテム変更
	 */
	protected void cbo_itemStateChanged() {
		for (TReferenceRangeUnit rangeUnit : field.ctrlReferenceRangeUnits) {
			rangeUnit.setVisible(false);
		}
		field.ctrlReferenceRangeUnits.get(field.cbo.getSelectedIndex()).setVisible(true);
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 選択されている管理を返す
	 * 
	 * @return 選択されている管理
	 */
	public ManagementAngle getManagementAngle() {
		return (ManagementAngle) field.cbo.getSelectedItemValue();
	}

	/**
	 * 選択されている管理
	 * 
	 * @param angle 選択されている管理
	 */
	public void setManagementAngle(ManagementAngle angle) {
		field.cbo.setSelectedItemValue(angle);
	}

	/**
	 * 指定された条件を纏めて返す。<br>
	 * ・管理として何が選択されているか<br>
	 * ・開始コード、終了コード<br>
	 * ・個別選択コード<br>
	 * を纏めて返す。
	 * 
	 * @return 指定された条件を纏めて返す
	 */
	public ManagementAngleSearchCondition getManagementAngleSearchCondition() {

		ManagementAngleSearchCondition condition = new ManagementAngleSearchCondition();

		// 何が選択されているか
		condition.setManagementAngle(getManagementAngle());

		if (ManagementAngle.NONE != condition.getManagementAngle()) {

			TReferenceRangeUnit unit = getReferenceRangeUnit();
			// 開始コード
			condition.setCodeFrom(unit.getReferenceRange().getCodeFrom());

			// 終了コード
			condition.setCodeTo(unit.getReferenceRange().getCodeTo());

			// 個別選択
			condition.setOptionalCodes(unit.getOptionalSelector().getCodeList());

		}

		return condition;

	}

	/**
	 * 選択されている範囲検索フィールドを返す。
	 * 
	 * @return 選択されている範囲検索フィールド
	 */
	public TReferenceRangeUnit getReferenceRangeUnit() {
		return field.ctrlReferenceRangeUnits.get(field.cbo.getSelectedIndex());
	}

	/**
	 * 指定された条件を設定する
	 * 
	 * @param condition 指定された条件
	 */
	public void setManagementAngleSearchCondition(ManagementAngleSearchCondition condition) {

		if (condition == null) {
			return;
		}

		try {

			// 何が選択されているか
			setManagementAngle(condition.getManagementAngle());

			if (ManagementAngle.NONE != condition.getManagementAngle()) {

				TReferenceRangeUnit unit = getReferenceRangeUnit();
				// 開始コード
				unit.getReferenceRange().setCodeFrom(condition.getCodeFrom());
				unit.getReferenceRange().refleshEntityFrom();

				// 終了コード
				unit.getReferenceRange().setCodeTo(condition.getCodeTo());
				unit.getReferenceRange().refleshEntityTo();

				// 個別選択
				// unit.getOptionalSelector().setEntities(condition.getOptionalCodes()));
				unit.getOptionalSelector().setCodeList(condition.getOptionalCodes());

			}

		} catch (Exception ex) {
			// エラーなし
		}
	}

}

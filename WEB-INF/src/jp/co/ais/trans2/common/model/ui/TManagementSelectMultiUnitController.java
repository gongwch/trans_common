package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理選択：管理選択+範囲検索+任意選択 * 3：コントローラ
 * 
 * @author AIS
 */
public class TManagementSelectMultiUnitController extends TController {

	/** true:記憶 */
	protected static boolean useSaveSetting = ClientConfig.isFlagOn("trans.report.management.unit.save");

	/** フィールド */
	protected TManagementSelectMultiUnit field;

	/**
	 * @param field フィールド
	 */
	public TManagementSelectMultiUnitController(TManagementSelectMultiUnit field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		//
	}

	/**
	 * @return true:記憶
	 */
	protected boolean isUseSaveSetting() {
		return useSaveSetting;
	}

	/**
	 * 管理項目単位条件保持の設定により復旧
	 */
	protected void restoreSetting() {
		if (!isUseSaveSetting()) {
			// 記憶しない場合、処理不要
			return;
		}

		// 保持キーを使って初期化する
		if (!Util.isNullOrEmpty(getSaveKey())) {
			for (int i = 0; i < field.ctrlManagementSelectUnits.size(); i++) {
				TManagementSelectUnit unit = field.ctrlManagementSelectUnits.get(i);
				unit.setManagementAngleSearchCondition(getSaveCondition(i));
			}
		}
	}

	/**
	 * 保持キーの取得
	 * 
	 * @return saveKey 保持キー
	 */
	public String getSaveKey() {
		return field.getSaveKey();
	}

	/**
	 * 保持キーの取得
	 * 
	 * @param i
	 * @return saveKey 保持キー
	 */
	protected ManagementAngleSearchCondition getSaveCondition(int i) {
		return (ManagementAngleSearchCondition) FileUtil.getTemporaryObject(getSaveKey(i));
	}

	/**
	 * 保持キーの取得
	 * 
	 * @param i
	 * @return saveKey 保持キー
	 */
	protected String getSaveKey(int i) {
		StringBuilder sb = new StringBuilder();
		sb.append(TLoginCtrl.getClientSaveKey());
		sb.append("_");
		sb.append(field.getSaveKey());
		sb.append("_");
		sb.append(i);
		return sb.toString();
	}

	/**
	 * 管理項目単位条件 設定保持
	 */
	public void saveSetting() {
		if (!isUseSaveSetting()) {
			// 記憶しない場合、処理不要
			return;
		}

		// 解消される時に、保持キーがあれば、当該条件をクライアントに持つ
		if (Util.isNullOrEmpty(getSaveKey())) {
			return;
		}

		int size = field.ctrlManagementSelectUnits.size();

		List<ManagementAngleSearchCondition> list = getManagementAngleSearchConditions();
		if (list == null || list.isEmpty()) {
			// 既存条件をクリア
			for (int i = 0; i < size; i++) {
				FileUtil.saveTemporaryObject(null, getSaveKey(i));
			}
		} else {
			for (int i = 0; i < size; i++) {
				ManagementAngleSearchCondition condition = list.get(i);
				FileUtil.saveTemporaryObject(condition, getSaveKey(i));
			}
		}

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
	 * 管理条件を纏めて返す
	 * 
	 * @return 管理条件を纏めて返す
	 */
	public List<ManagementAngleSearchCondition> getManagementAngleSearchConditions() {
		List<ManagementAngleSearchCondition> list = new ArrayList<ManagementAngleSearchCondition>();
		for (TManagementSelectUnit unit : field.ctrlManagementSelectUnits) {
			list.add(unit.getManagementAngleSearchCondition());
		}
		return list;
	}

	/**
	 * 管理条件を設定する
	 * 
	 * @param list 管理条件
	 */
	public void setManagementAngleSearchConditions(List<ManagementAngleSearchCondition> list) {
		if (list == null) {
			return;
		}

		// TODO
	}

	/**
	 * 大小チェック。開始コード > 終了コードのフィールドがあれば<br>
	 * そのインデックスを返す。無ければ負数を返す。
	 * 
	 * @return 開始コード > 終了コードのフィールドがあれば<br>
	 *         そのインデックスを返す。無ければ負数を返す。
	 */
	public int isSmallerFrom() {
		int i = 0;
		for (TManagementSelectUnit unit : field.ctrlManagementSelectUnits) {
			if (!unit.isSmallerFrom()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * 上から順に入力されているかを返す。
	 * 
	 * @return 上から順に入力されているか
	 */
	public boolean isEnteredAtTheTop() {

		boolean entered = false;
		for (int i = field.ctrlManagementSelectUnits.size() - 1; i >= 0; i--) {
			if (entered && ManagementAngle.NONE == field.ctrlManagementSelectUnits.get(i).getManagementAngle()) {
				return false;
			}
			if (ManagementAngle.NONE != field.ctrlManagementSelectUnits.get(i).getManagementAngle()) {
				entered = true;
			}
		}

		return true;
	}

	/**
	 * 管理指定に重複があれば、重複しているフィールドのインデックスを返す。<br>
	 * 負数の場合、重複はない
	 * 
	 * @return 負数。それ以外は重複があったフィールドの番号
	 */
	public int getSameManagementAngleIndex() {

		for (int i = 0; i < field.ctrlManagementSelectUnits.size(); i++) {

			TManagementSelectUnit unit = field.ctrlManagementSelectUnits.get(i);

			// 選択されている管理
			ManagementAngle managementAngle = unit.getManagementAngle();
			if (ManagementAngle.NONE != managementAngle) {
				int selectedCount = 0;
				for (TManagementSelectUnit unit2 : field.ctrlManagementSelectUnits) {
					if (unit2.getManagementAngle() == managementAngle) {
						selectedCount++;
					}
				}
				if (selectedCount != 1) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * true:全SPCモードの設定
	 * 
	 * @param allCompanyMode true:全SPCモード
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {

		for (int i = 0; i < field.ctrlManagementSelectUnits.size(); i++) {

			TManagementSelectUnit unit = field.ctrlManagementSelectUnits.get(i);

			for (TReferenceRangeUnit unit2 : unit.ctrlReferenceRangeUnits) {

				if (unit2 instanceof TNoneReferenceRangeUnit) {
					continue;
				}

				if (unit2 instanceof TDepartmentReferenceRangeUnit) {
					// 部門
					TDepartmentOptionalSelector selector = ((TDepartmentReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TDepartmentReferenceRange range = ((TDepartmentReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TCustomerReferenceRangeUnit) {
					// 取引先
					// 集計取引先
					TCustomerOptionalSelector selector = ((TCustomerReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TCustomerReferenceRange range = ((TCustomerReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TEmployeeReferenceRangeUnit) {
					// 社員
					TEmployeeOptionalSelector selector = ((TEmployeeReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TEmployeeReferenceRange range = ((TEmployeeReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement1ReferenceRangeUnit) {
					// 管理1
					TManagement1OptionalSelector selector = ((TManagement1ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement1ReferenceRange range = ((TManagement1ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement2ReferenceRangeUnit) {
					// 管理2
					TManagement2OptionalSelector selector = ((TManagement2ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement2ReferenceRange range = ((TManagement2ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement3ReferenceRangeUnit) {
					// 管理3
					TManagement3OptionalSelector selector = ((TManagement3ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement3ReferenceRange range = ((TManagement3ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement4ReferenceRangeUnit) {
					// 管理4
					TManagement4OptionalSelector selector = ((TManagement4ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement4ReferenceRange range = ((TManagement4ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement5ReferenceRangeUnit) {
					// 管理5
					TManagement5OptionalSelector selector = ((TManagement5ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement5ReferenceRange range = ((TManagement5ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);

				} else if (unit2 instanceof TManagement6ReferenceRangeUnit) {
					// 管理6
					TManagement6OptionalSelector selector = ((TManagement6ReferenceRangeUnit) unit2).ctrlOptionalSelector;
					TManagement6ReferenceRange range = ((TManagement6ReferenceRangeUnit) unit2).ctrlReferenceRange;
					selector.setAllCompanyMode(allCompanyMode);
					range.getFieldFrom().setAllCompanyMode(allCompanyMode);
					range.getFieldTo().setAllCompanyMode(allCompanyMode);
				}

			}
		}

	}

	/**
	 * 会社出力単位の設定
	 * 
	 * @param companyOrgUnit 会社出力単位
	 */
	public void setCompanyOrgUnit(TCompanyOrganizationUnit companyOrgUnit) {

		// 関連管理選択へすべて更新する
		for (int i = 0; i < field.ctrlManagementSelectUnits.size(); i++) {

			TManagementSelectUnit unit = field.ctrlManagementSelectUnits.get(i);

			for (TReferenceRangeUnit unit2 : unit.ctrlReferenceRangeUnits) {
				unit2.setCompanyOrgUnit(companyOrgUnit);
			}
		}
	}

}

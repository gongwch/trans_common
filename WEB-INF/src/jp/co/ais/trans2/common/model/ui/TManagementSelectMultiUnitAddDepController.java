package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理選択：管理選択+範囲検索+任意選択 * 3：コントローラ
 * 
 * @author AIS
 */
public class TManagementSelectMultiUnitAddDepController extends TManagementSelectMultiUnitController {

	/**
	 * @param field フィールド
	 */
	public TManagementSelectMultiUnitAddDepController(TManagementSelectMultiUnit field) {
		super(field);
	}

	/**
	 * @return field TManagementSelectMultiUnitAddDep
	 */
	public TManagementSelectMultiUnitAddDep getField() {
		return (TManagementSelectMultiUnitAddDep) field;
	}

	/**
	 * 管理条件を纏めて返す
	 * 
	 * @return 管理条件を纏めて返す
	 */
	@Override
	public List<ManagementAngleSearchCondition> getManagementAngleSearchConditions() {
		List<ManagementAngleSearchCondition> list = new ArrayList<ManagementAngleSearchCondition>();
		for (TManagementSelectUnit unit : getField().ctrlManagementSelectUnits) {
			list.add(unit.getManagementAngleSearchCondition());
		}
		return list;
	}

	/**
	 * 大小チェック。開始コード > 終了コードのフィールドがあれば<br>
	 * そのインデックスを返す。無ければ負数を返す。
	 * 
	 * @return 開始コード > 終了コードのフィールドがあれば<br>
	 *         そのインデックスを返す。無ければ負数を返す。
	 */
	@Override
	public int isSmallerFrom() {
		int i = 0;
		for (TManagementSelectUnit unit : getField().ctrlManagementSelectUnits) {
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
	@Override
	public boolean isEnteredAtTheTop() {

		boolean entered = false;
		for (int i = getField().ctrlManagementSelectUnits.size() - 1; i >= 0; i--) {
			if (entered && ManagementAngle.NONE == getField().ctrlManagementSelectUnits.get(i).getManagementAngle()) {
				return false;
			}
			if (ManagementAngle.NONE != getField().ctrlManagementSelectUnits.get(i).getManagementAngle()) {
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
	@Override
	public int getSameManagementAngleIndex() {

		for (int i = 0; i < getField().ctrlManagementSelectUnits.size(); i++) {

			TManagementSelectUnit unit = getField().ctrlManagementSelectUnits.get(i);

			// 選択されている管理
			ManagementAngle managementAngle = unit.getManagementAngle();
			if (ManagementAngle.NONE != managementAngle) {
				int selectedCount = 0;
				for (TManagementSelectUnit unit2 : getField().ctrlManagementSelectUnits) {
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
	 * 管理項目単位条件 設定保持
	 */
	@Override
	public void saveSetting() {
		if (!isUseSaveSetting()) {
			// 記憶しない場合、処理不要
			return;
		}

		// 解消される時に、保持キーがあれば、当該条件をクライアントに持つ
		if (Util.isNullOrEmpty(getSaveKey())) {
			return;
		}

		int size = getField().ctrlManagementSelectUnits.size();

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

	/**
	 * 管理項目単位条件保持の設定により復旧
	 */
	@Override
	protected void restoreSetting() {
		if (!isUseSaveSetting()) {
			// 記憶しない場合、処理不要
			return;
		}

		// 保持キーを使って初期化する
		if (!Util.isNullOrEmpty(getSaveKey())) {
			for (int i = 0; i < getField().ctrlManagementSelectUnits.size(); i++) {
				TManagementSelectUnit unit = getField().ctrlManagementSelectUnits.get(i);
				unit.setManagementAngleSearchCondition(getSaveCondition(i));
			}
		}
	}

}

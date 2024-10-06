package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TComboBox.TTextValue;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理選択：内訳表示：コントローラ
 * 
 * @author AIS
 */
public class TManagementComboMultiUnitController extends TController {

	/** フィールド */
	protected TManagementComboMultiUnit field;

	/**
	 * @param field フィールド
	 */
	public TManagementComboMultiUnitController(TManagementComboMultiUnit field) {
		this.field = field;
		init();
	}

	/**
	 * コンボボックスの初期化
	 */
	protected void init() {
		// 初期状態設定
		setSelectedAngles(field.angles);
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
	public List<ManagementAngle> getSelectedAngles() {
		List<ManagementAngle> list = new ArrayList<ManagementAngle>();
		for (TComboBox cbo : field.combos) {
			list.add((ManagementAngle) cbo.getSelectedItemValue());
		}
		return list;
	}

	/**
	 * @return true:使用可能の管理項目が選択されたこと
	 */
	public boolean isLastSelected() {
		for (TComboBox cbo : field.combos) {
			if (!cbo.isEnabled()) {
				continue;
			}

			ManagementAngle angle = (ManagementAngle) cbo.getSelectedItemValue();
			if (ManagementAngle.NONE == angle) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 使用可能の管理項目にフォーカスを当てる
	 */
	public void requestLastFocus() {
		for (TComboBox cbo : field.combos) {
			if (!cbo.isEnabled()) {
				continue;
			}

			cbo.requestFocus();
			return;
		}
	}

	/**
	 * 管理条件を設定する
	 * 
	 * @param list 管理条件
	 */
	public void setSelectedAngles(List<ManagementAngle> list) {
		if (list == null || list.isEmpty()) {
			return;
		}

		field.angles = list;

		boolean canDrillDown = false;

		for (int i = 0; i < Math.min(field.combos.size(), field.combos.size()); i++) {
			ManagementAngle angle = field.angles.get(i);
			TComboBox cbo = field.combos.get(i);

			if (ManagementAngle.NONE != angle) {
				// 選択なし以外は選択対象以外をクリア
				cbo.removeAllItems();
				cbo.addItem(createTTextValue(angle));
				cbo.setEnabled(false);
			} else {

				cbo.removeAllItems();
				initComboBox(cbo);

				canDrillDown = true;
			}
		}

		// ボタン制御
		field.btn.setEnabled(canDrillDown);
	}

	/**
	 * 管理条件を設定する
	 * 
	 * @param conditions 管理条件
	 */
	public void setSelectedAngleConditions(List<ManagementAngleSearchCondition> conditions) {
		List<ManagementAngle> list = new ArrayList<ManagementAngle>();
		for (ManagementAngleSearchCondition cond : conditions) {
			list.add(cond.getManagementAngle());
		}

		setSelectedAngles(list);
	}

	/**
	 * コンボボックス初期化
	 * 
	 * @param cbo
	 */
	protected void initComboBox(TComboBox cbo) {
		Company company = getCompany();
		AccountConfig config = company.getAccountConfig();

		// 条件なし
		cbo.addItem(createTTextValue(ManagementAngle.NONE));

		// 取引先
		if (!field.angles.contains(ManagementAngle.CUSTOMER)) {
			cbo.addItem(createTTextValue(ManagementAngle.CUSTOMER));
		}

		// 社員
		if (!field.angles.contains(ManagementAngle.EMPLOYEE)) {
			cbo.addItem(createTTextValue(ManagementAngle.EMPLOYEE));
		}

		// 管理1
		if (config.isUseManagement1() && !field.angles.contains(ManagementAngle.MANAGEMENT1)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT1));
		}

		// 管理2
		if (config.isUseManagement2() && !field.angles.contains(ManagementAngle.MANAGEMENT2)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT2));
		}

		// 管理3
		if (config.isUseManagement3() && !field.angles.contains(ManagementAngle.MANAGEMENT3)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT3));
		}

		// 管理4
		if (config.isUseManagement4() && !field.angles.contains(ManagementAngle.MANAGEMENT4)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT4));
		}

		// 管理5
		if (config.isUseManagement5() && !field.angles.contains(ManagementAngle.MANAGEMENT5)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT5));
		}

		// 管理6
		if (config.isUseManagement6() && !field.angles.contains(ManagementAngle.MANAGEMENT6)) {
			cbo.addItem(createTTextValue(ManagementAngle.MANAGEMENT6));
		}
	}

	/**
	 * @param angle
	 * @return コンボボックス選択肢
	 */
	protected TTextValue createTTextValue(ManagementAngle angle) {
		return new TTextValue(getAngleName(angle), angle);
	}

	/**
	 * @param angle
	 * @return 表示名
	 */
	public static String getAngleName(ManagementAngle angle) {

		if (angle == null) {
			return "";
		}

		Company company = TLoginInfo.getCompany();
		AccountConfig config = company.getAccountConfig();
		String lang = TClientLoginInfo.getInstance().getUserLanguage();

		switch (angle) {
			case NONE:
				return MessageUtil.getWord(lang, "C01748");
			case DEPARTMENT:
				return MessageUtil.getWord(lang, "C00467");
			case CUSTOMER:
				return MessageUtil.getWord(lang, "C00408");
			case SUMCUSTOMER:
				return MessageUtil.getWord(lang, "C01149");
			case EMPLOYEE:
				return MessageUtil.getWord(lang, "C00246");
			case MANAGEMENT1:
				return config.getManagement1Name();
			case MANAGEMENT2:
				return config.getManagement2Name();
			case MANAGEMENT3:
				return config.getManagement3Name();
			case MANAGEMENT4:
				return config.getManagement4Name();
			case MANAGEMENT5:
				return config.getManagement5Name();
			case MANAGEMENT6:
				return config.getManagement6Name();
		}
		return "";
	}

}

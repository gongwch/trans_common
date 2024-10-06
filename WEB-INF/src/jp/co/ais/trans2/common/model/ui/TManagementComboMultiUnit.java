package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理選択：内訳表示：メイン
 * 
 * @author AIS
 */
public class TManagementComboMultiUnit extends TPanel {

	/** 管理選択コンボボックス */
	public List<TComboBox> combos;

	/** 各コンボボックスの初期値 */
	public List<ManagementAngle> angles;

	/** 内訳表示 */
	public TButton btn;

	/** コントローラ */
	public TManagementComboMultiUnitController controller;

	/**
	 * コンストラクタ
	 */
	public TManagementComboMultiUnit() {
		this(null);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param angles
	 */
	public TManagementComboMultiUnit(List<ManagementAngle> angles) {

		if (angles == null) {
			this.angles = new ArrayList<ManagementAngle>();
			this.angles.add(ManagementAngle.NONE);
			this.angles.add(ManagementAngle.NONE);
			this.angles.add(ManagementAngle.NONE);
		} else {
			this.angles = angles;
		}

		// コンポーネント初期化
		initComponents();

		// コンポーネント配置
		allocateComponents();

		// コントローラ生成
		controller = createController();
	}

	/**
	 * コンポーネント初期化
	 */
	public void initComponents() {
		combos = new ArrayList<TComboBox>();
		for (int i = 0; i < angles.size(); i++) {
			combos.add(new TComboBox());
		}

		// 内訳表示
		btn = new TButton();
	}

	/**
	 * コンポーネント配置
	 */
	public void allocateComponents() {

		setLayout(null);

		int x = 0;
		for (TComboBox cbo : combos) {
			TGuiUtil.setComponentSize(cbo, 90, 20);
			cbo.setLocation(x, 5);
			add(cbo);
			x += cbo.getWidth() + 5;
		}

		btn.setLangMessageID("C11884"); // 内訳表示
		btn.setSize(20, 70);
		btn.setLocation(x, 5);
		add(btn);

		x += btn.getWidth();

		setSize(x + 5, 30);
	}

	/**
	 * コントローラ生成
	 * 
	 * @return controller
	 */
	protected TManagementComboMultiUnitController createController() {
		return new TManagementComboMultiUnitController(this);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TComboBox cbo : combos) {
			cbo.setTabControlNo(tabControlNo);
		}
		btn.setTabControlNo(tabControlNo);
	}

	/**
	 * 管理条件を纏めて返す
	 * 
	 * @return 管理条件を纏めて返す
	 */
	public List<ManagementAngle> getSelectedAngles() {
		return controller.getSelectedAngles();
	}

	/**
	 * 管理条件を設定する
	 * 
	 * @param list 管理条件
	 */
	public void setSelectedAngles(List<ManagementAngle> list) {
		controller.setSelectedAngles(list);
	}

	/**
	 * 管理条件を設定する
	 * 
	 * @param conditions 管理条件
	 */
	public void setSelectedAngleConditions(List<ManagementAngleSearchCondition> conditions) {
		controller.setSelectedAngleConditions(conditions);
	}

	/**
	 * @return true:使用可能の管理項目が選択されたこと
	 */
	public boolean isLastSelected() {
		return controller.isLastSelected();
	}

	/**
	 * 使用可能の管理項目にフォーカスを当てる
	 */
	public void requestLastFocus() {
		controller.requestLastFocus();
	}

}
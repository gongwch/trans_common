package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.management.*;

/**
 * 管理選択：管理選択+範囲検索+任意選択 * 3：メイン
 * 
 * @author AIS
 */
public class TManagementSelectMultiUnit extends TTitlePanel {

	/** 管理選択フィールド */
	public List<TManagementSelectUnit> ctrlManagementSelectUnits;

	/** コントローラ */
	protected TManagementSelectMultiUnitController controller;

	/** フィールド数の初期値 */
	protected static final int FIELD_COUNT = 3;

	/** 保持キー */
	protected String saveKey = null;

	/** 会社出力単位 */
	protected TCompanyOrganizationUnit companyOrgUnit = null;

	/**
	 * コンストラクタ
	 */
	public TManagementSelectMultiUnit() {
		this(FIELD_COUNT);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param fieldCount
	 */
	public TManagementSelectMultiUnit(int fieldCount) {

		// コンポーネント初期化
		initComponents(fieldCount);

		// コンポーネント配置
		allocateComponents();

		// コントローラ生成
		controller = createController();
	}

	/**
	 * コンポーネント初期化
	 * 
	 * @param fieldCount
	 */
	public void initComponents(int fieldCount) {
		ctrlManagementSelectUnits = new ArrayList<TManagementSelectUnit>();
		for (int i = 0; i < fieldCount; i++) {
			ctrlManagementSelectUnits.add(new TManagementSelectUnit());
		}
	}

	/**
	 * コンポーネント配置
	 */
	public void allocateComponents() {

		setLangMessageID("C10834"); // 管理選択

		int y = 5;
		for (TManagementSelectUnit unit : ctrlManagementSelectUnits) {
			unit.setLocation(15, y);
			add(unit);
			y = y + unit.getHeight() + 5;
		}

		setSize(360, y + 25);
	}

	/**
	 * コントローラ生成
	 * 
	 * @return controller
	 */
	protected TManagementSelectMultiUnitController createController() {
		return new TManagementSelectMultiUnitController(this);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TManagementSelectUnit unit : ctrlManagementSelectUnits) {
			unit.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * 管理条件を纏めて返す
	 * 
	 * @return 管理条件を纏めて返す
	 */
	public List<ManagementAngleSearchCondition> getManagementAngleSearchConditions() {
		return controller.getManagementAngleSearchConditions();
	}

	/**
	 * 管理条件を設定する
	 * 
	 * @param list 管理条件
	 */
	public void setManagementAngleSearchConditions(List<ManagementAngleSearchCondition> list) {
		controller.setManagementAngleSearchConditions(list);
	}

	/**
	 * 大小チェック。開始コード > 終了コードのフィールドがあれば<br>
	 * そのインデックスを返す。無ければ負数を返す。
	 * 
	 * @return 開始コード > 終了コードのフィールドがあれば<br>
	 *         そのインデックスを返す。無ければ負数を返す。
	 */
	public int isSmallerFrom() {
		return controller.isSmallerFrom();
	}

	/**
	 * 上から順に入力されているかを返す。
	 * 
	 * @return 上から順に入力されているか
	 */
	public boolean isEnteredAtTheTop() {
		return controller.isEnteredAtTheTop();
	}

	/**
	 * 管理指定に重複があれば、重複しているフィールドのインデックスを返す。<br>
	 * 負数の場合、重複はない
	 * 
	 * @return 負数。それ以外は重複があったフィールドの番号
	 */
	public int getSameManagementAngleIndex() {
		return controller.getSameManagementAngleIndex();
	}

	/**
	 * 保持キーの取得
	 * 
	 * @return saveKey 保持キー
	 */
	public String getSaveKey() {
		return saveKey;
	}

	/**
	 * 保持キーの設定
	 * 
	 * @param saveKey 保持キー
	 */
	public void setSaveKey(String saveKey) {
		this.saveKey = saveKey;
	}

	/**
	 * 管理項目単位条件保持の設定により復旧
	 */
	public void restoreManagementSetting() {
		controller.restoreSetting();
	}

	/**
	 * 管理項目単位条件 設定保持
	 */
	public void saveManagementSetting() {
		// 解消される時に、保持キーがあれば、当該条件をクライアントに持つ
		controller.saveSetting();
	}

	/**
	 * true:全SPCモードの設定
	 * 
	 * @param allCompanyMode true:全SPCモード
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {
		controller.setAllCompanyMode(allCompanyMode);
	}

	/**
	 * 会社出力単位の取得
	 * 
	 * @return companyOrgUnit 会社出力単位
	 */
	public TCompanyOrganizationUnit getCompanyOrgUnit() {
		return companyOrgUnit;
	}

	/**
	 * 会社出力単位の設定
	 * 
	 * @param companyOrgUnit 会社出力単位
	 */
	public void setCompanyOrgUnit(TCompanyOrganizationUnit companyOrgUnit) {
		this.companyOrgUnit = companyOrgUnit;

		controller.setCompanyOrgUnit(companyOrgUnit);
	}

}
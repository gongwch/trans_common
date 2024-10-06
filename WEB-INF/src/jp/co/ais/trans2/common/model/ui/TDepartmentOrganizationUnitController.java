package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 組織(部門階層)の出力単位ユニットコンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TDepartmentOrganizationUnitController extends TController {

	/** true:記憶 */
	protected static boolean useSaveSetting = ClientConfig.isFlagOn("trans.report.department.unit.save");

	/** フィールド */
	protected TDepartmentOrganizationUnit field;

	/**
	 * @param field フィールド
	 */
	public TDepartmentOrganizationUnitController(TDepartmentOrganizationUnit field) {
		this.field = field;
		init();
	}

	/**
	 * @return true:記憶
	 */
	protected boolean isUseSaveSetting() {
		return useSaveSetting;
	}

	/**
	 * 出力単位条件保持の設定により復旧
	 */
	protected void restoreSetting() {
		if (!isUseSaveSetting()) {
			// 記憶しない場合、処理不要
			return;
		}

		// 保持キーを使って初期化する
		if (!Util.isNullOrEmpty(getSaveKey())) {
			DepartmentOutputCondition condition = getSaveCondition();
			if (condition != null) {
				setDepartmentOutputCondition(getSaveCondition());
			}
		}
	}

	/**
	 * 保持キーの取得
	 * 
	 * @return saveKey 保持キー
	 */
	protected DepartmentOutputCondition getSaveCondition() {
		return (DepartmentOutputCondition) FileUtil.getTemporaryObject(getSaveKey());
	}

	/**
	 * 保持キーの取得
	 * 
	 * @return saveKey 保持キー
	 */
	protected String getSaveKey() {
		StringBuilder sb = new StringBuilder();
		sb.append(TLoginCtrl.getClientSaveKey());
		sb.append("_");
		sb.append(field.getSaveKey());
		return sb.toString();
	}

	/**
	 * 出力単位条件 設定保持
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

		FileUtil.saveTemporaryObject(getDepartmentOutputCondition(), getSaveKey());

	}

	/**
	 * 初期化
	 */
	protected void init() {
		// 階層レベル初期化
		initLevelCombobox();
		// 「含む」を選択
		field.rdoInclude.setSelected(true);
		// イベント定義
		addEvent();
		// 集計部門を含む
		field.ctrlSuperiorDepartment.getSearchCondition().setSumDepartment(true);
		field.ctrlSuperiorDepartment.setCheckExist(false);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setSumDepartment(true);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setSumDepartment(true);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.setCheckExist(false);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.setCheckExist(false);
		field.ctrlDepartment.ctrlOptionalSelector.getSearchCondition().setSumDepartment(true);
		clear();
	}

	/**
	 * イベント追加
	 */
	protected void addEvent() {

		// 組織変更
		field.cboDepartmentOrganization.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					cboDepartmentOrganization_itemStateChanged();
				}
			}
		});

		// 階層レベル変更
		field.cboLevel.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					cboLevel_itemStateChanged();
				}
			}
		});

		// 上位部門選択時
		field.ctrlSuperiorDepartment.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSuperiorDepartment_after();
			}
		});

	}

	/**
	 * 階層レベルの初期化
	 */
	protected void initLevelCombobox() {
		for (int i = 0; i <= 9; i++) {
			field.cboLevel.getComboBox().addItem(getWord("C01739") + Integer.toString(i));
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
	 * [組織]時の処理
	 */
	protected void cboDepartmentOrganization_itemStateChanged() {
		clear();
	}

	/**
	 * [レベル]変更時の処理
	 */
	protected void cboLevel_itemStateChanged() {

		// レベル0の場合、上位部門を選択不可能にする。
		field.ctrlSuperiorDepartment.setEditable(field.cboLevel.getSelectedIndex() != 0);
		field.ctrlDepartment.ctrlReferenceRange.setEditable(field.cboLevel.getSelectedIndex() == 0);

		// 部門情報クリア
		field.ctrlSuperiorDepartment.clear();
		field.ctrlDepartment.getReferenceRange().clear();
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setCodeFrom(null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setCodeTo(null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setCodeFrom(null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setCodeTo(null);

		// 上位部門はレベル-1を指定
		field.ctrlSuperiorDepartment.getSearchCondition().setLevel(field.cboLevel.getSelectedIndex() - 1);

		// 部門はレベルを指定
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setLevel(
			field.cboLevel.getSelectedIndex());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setLevel(
			field.cboLevel.getSelectedIndex());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition()
			.setSuperiorDepartmentCode(null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition()
			.setSuperiorDepartmentCode(null);

		// 個別選択はレベルを指定
		field.ctrlDepartment.ctrlOptionalSelector.getSearchCondition().setLevel(field.cboLevel.getSelectedIndex());
		field.ctrlDepartment.ctrlOptionalSelector.refresh();

	}

	/**
	 * レベル名称コンボボックス
	 */
	protected void initLevelNameComboBox() {
		field.cboLevel.getComboBox().removeAllItems();
		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCode(field.cboDepartmentOrganization.getSelectedOrganizationCode());
		try {
			DepartmentOrganization bean = (DepartmentOrganization) request(DepartmentOrganizationManager.class,
				"getDepartmentOrganizationName", condition);
			String[] codes = new String[10];

			if (bean == null) {
				bean = new DepartmentOrganization();
			}

			codes[0] = getLevelName(bean.getDPK_LVL_0_NAME(), getWord("C00722"));
			codes[1] = getLevelName(bean.getDPK_LVL_1_NAME(), getWord("C02126"));
			codes[2] = getLevelName(bean.getDPK_LVL_2_NAME(), getWord("C02127"));
			codes[3] = getLevelName(bean.getDPK_LVL_3_NAME(), getWord("C02128"));
			codes[4] = getLevelName(bean.getDPK_LVL_4_NAME(), getWord("C02129"));
			codes[5] = getLevelName(bean.getDPK_LVL_5_NAME(), getWord("C02130"));
			codes[6] = getLevelName(bean.getDPK_LVL_6_NAME(), getWord("C02131"));
			codes[7] = getLevelName(bean.getDPK_LVL_7_NAME(), getWord("C02132"));
			codes[8] = getLevelName(bean.getDPK_LVL_8_NAME(), getWord("C02133"));
			codes[9] = getLevelName(bean.getDPK_LVL_9_NAME(), getWord("C02134"));
			field.cboLevel.getComboBox().setModel(codes);

		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * @param name
	 * @param defaultName
	 * @return getLevelNames
	 */
	protected String getLevelName(String name, String defaultName) {
		if (Util.isNullOrEmpty(name)) {
			return defaultName;
		}
		return name;
	}

	/**
	 * フィールドをクリアする
	 */
	public void clear() {
		// 組織コード条件セット
		field.ctrlSuperiorDepartment.getSearchCondition().setOrganizationCode(
			field.cboDepartmentOrganization.getSelectedOrganizationCode());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setOrganizationCode(
			field.cboDepartmentOrganization.getSelectedOrganizationCode());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setOrganizationCode(
			field.cboDepartmentOrganization.getSelectedOrganizationCode());
		if (!Util.isNullOrEmpty(field.cboDepartmentOrganization.getSelectedOrganizationCode())) {
			// レベルは0を選択
			field.cboLevel.setSelectedIndex(0);
		}
		field.ctrlDepartment.ctrlOptionalSelector.getSearchCondition().setOrganizationCode(
			field.cboDepartmentOrganization.getSelectedOrganizationCode());
		if (TDepartmentOrganizationUnit.DISPLAY_NAME_FLG != 0) {
			initLevelNameComboBox();
		}
		cboLevel_itemStateChanged();
	}

	/**
	 * 上位部門選択時
	 */
	protected void ctrlSuperiorDepartment_after() {

		if (field.ctrlSuperiorDepartment.code.isValueChanged()) {
			field.ctrlDepartment.getReferenceRange().clear();
			field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setCodeFrom(null);
			field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition().setCodeTo(null);
			field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setCodeFrom(null);
			field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition().setCodeTo(null);
		}

		Department department = field.ctrlSuperiorDepartment.getEntity();

		if (department == null) {
			field.ctrlDepartment.ctrlReferenceRange.setEditable(false);
		} else {
			field.ctrlDepartment.ctrlReferenceRange.setEditable(true);
		}

		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getSearchCondition()
			.setSuperiorDepartmentCode(field.ctrlSuperiorDepartment.getCode());
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getSearchCondition()
			.setSuperiorDepartmentCode(field.ctrlSuperiorDepartment.getCode());

	}

	/**
	 * 出力単位を返す
	 * 
	 * @return 出力単位
	 */
	public DepartmentOutputCondition getDepartmentOutputCondition() {

		DepartmentOutputCondition condition = createCondition();
		// 会社
		condition.setCompanyCode(getCompany().getCode());
		// 組織コード
		condition.setDepartmentOrganizationCode(field.cboDepartmentOrganization.getSelectedOrganizationCode());
		// 階層レベル
		condition.setLevel(field.cboLevel.getSelectedIndex());
		// 配下部門を含むか
		condition.setIncludeUnderDepartment(field.rdoInclude.isSelected());
		// 上位部門
		condition.setSuperiorDepartment(field.ctrlSuperiorDepartment.getEntity());
		// 開始部門
		condition.setDepartmentFrom(field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.getEntity());
		// 終了部門
		condition.setDepartmentTo(field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.getEntity());
		// 個別選択部門
		condition.setOptionalDepartments(field.ctrlDepartment.ctrlOptionalSelector.getDepartmentEntities());

		return condition;

	}

	/**
	 * 出力単位を設定する
	 * 
	 * @param condition 出力単位
	 */
	public void setDepartmentOutputCondition(DepartmentOutputCondition condition) {
		// 組織コード
		field.cboDepartmentOrganization.setSelectedOrganizationCode(condition.getDepartmentOrganizationCode());
		// 階層レベル
		field.cboLevel.setSelectedIndex(condition.getLevel());
		// 配下部門を含むか
		field.rdoInclude.setSelected(condition.isIncludeUnderDepartment());
		// 上位部門
		field.ctrlSuperiorDepartment.setEntity(condition.getSuperiorDepartment());
		// 開始部門
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.setEntity(condition.getDepartmentFrom());
		// 終了部門
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo.setEntity(condition.getDepartmentTo());
		// 個別選択部門
		field.ctrlDepartment.ctrlOptionalSelector.setDepartmentEntities(condition.getOptionalDepartments());

		// 画面制御追加
		field.ctrlSuperiorDepartment.setEditable(condition.getSuperiorDepartment() != null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceFrom.setEditable(condition
			.getSuperiorDepartment() != null);
		field.ctrlDepartment.ctrlReferenceRange.ctrlDepartmentReferenceTo
			.setEditable(condition.getSuperiorDepartment() != null);
	}

	/**
	 * @return 検索条件作成
	 */
	protected DepartmentOutputCondition createCondition() {
		return new DepartmentOutputCondition();
	}

	/**
	 * 大小チェック
	 * 
	 * @return true(問題無し) / false(エラーあり)
	 */
	public boolean isDepartmentSmallerFrom() {
		return field.ctrlDepartment.ctrlReferenceRange.isSmallerFrom();
	}

}

package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 組織(会社階層)の出力単位ユニットコンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TCompanyOrganizationUnitController extends TController {

	/** フィールド */
	protected TCompanyOrganizationUnit field;

	/**
	 * @param field フィールド
	 */
	public TCompanyOrganizationUnitController(TCompanyOrganizationUnit field) {
		this.field = field;
		init();
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
		// 集計会社を含む
		field.ctrlSuperiorCompany.setCheckExist(false);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.setCheckExist(false);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.setCheckExist(false);
		clear();
	}

	/**
	 * イベント追加
	 */
	protected void addEvent() {

		// 組織変更
		field.cboCompanyOrganization.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					cboCompanyOrganization_itemStateChanged();
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

		// 上位会社選択時
		field.ctrlSuperiorCompany.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlSuperiorCompany_after();
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
	protected void cboCompanyOrganization_itemStateChanged() {
		clear();
	}

	/**
	 * [レベル]変更時の処理
	 */
	protected void cboLevel_itemStateChanged() {

		int level = field.cboLevel.getSelectedIndex();

		// レベル0の場合、上位会社を選択不可能にする。
		field.ctrlSuperiorCompany.setEditable(level != 0);
		field.ctrlCompany.ctrlReferenceRange.setEditable(level == 0);

		// 会社情報クリア
		field.ctrlSuperiorCompany.clear();
		field.ctrlCompany.getReferenceRange().clear();
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setCodeFrom(null);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setCodeTo(null);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setCodeFrom(null);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setCodeTo(null);

		// 上位会社はレベル-1を指定
		field.ctrlSuperiorCompany.getSearchCondition().setLevel(level - 1);

		// 会社はレベルを指定
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setLevel(level);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setLevel(level);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setSuperiorCompanyCode(null);
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setSuperiorCompanyCode(null);

		// 個別選択はレベルを指定
		field.ctrlCompany.ctrlOptionalSelector.getSearchCondition().setLevel(level);
		field.ctrlCompany.ctrlOptionalSelector.refresh();

	}

	/**
	 * フィールドをクリアする
	 */
	public void clear() {
		// 組織コード条件セット
		field.ctrlSuperiorCompany.getSearchCondition().setOrganizationCode(
			field.cboCompanyOrganization.getSelectedOrganizationCode());
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setOrganizationCode(
			field.cboCompanyOrganization.getSelectedOrganizationCode());
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setOrganizationCode(
			field.cboCompanyOrganization.getSelectedOrganizationCode());
		if (!Util.isNullOrEmpty(field.cboCompanyOrganization.getSelectedOrganizationCode())) {
			// レベルは0を選択
			field.cboLevel.setSelectedIndex(0);
		}
		field.ctrlCompany.ctrlOptionalSelector.getSearchCondition().setOrganizationCode(
			field.cboCompanyOrganization.getSelectedOrganizationCode());
		if (TCompanyOrganizationUnit.DISPLAY_NAME_FLG != 0) {
			initLevelNameComboBox();
		}
		cboLevel_itemStateChanged();
	}

	/**
	 * レベル名称コンボボックス
	 */
	protected void initLevelNameComboBox() {
		field.cboLevel.getComboBox().removeAllItems();
		CompanyOrganizationSearchCondition condition = new CompanyOrganizationSearchCondition();
		condition.setCode(field.cboCompanyOrganization.getSelectedOrganizationCode());
		try {
			CompanyOrganization bean = (CompanyOrganization) request(CompanyOrganizationManager.class,
				"getCompanyOrganizationName", condition);
			String[] codes = new String[10];

			if (bean == null) {
				bean = new CompanyOrganization();
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
	 * @return getLevelName
	 */
	protected String getLevelName(String name, String defaultName) {
		if (Util.isNullOrEmpty(name)) {
			return defaultName;
		}
		return name;
	}

	/**
	 * 上位会社選択時
	 */
	protected void ctrlSuperiorCompany_after() {

		if (field.ctrlSuperiorCompany.code.isValueChanged()) {
			field.ctrlCompany.getReferenceRange().clear();
			field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setCodeFrom(null);
			field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setCodeTo(null);
			field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setCodeFrom(null);
			field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setCodeTo(null);
		}

		Company company = field.ctrlSuperiorCompany.getEntity();

		if (company == null) {
			field.ctrlCompany.ctrlReferenceRange.setEditable(false);
		} else {
			field.ctrlCompany.ctrlReferenceRange.setEditable(true);
		}

		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getSearchCondition().setSuperiorCompanyCode(
			field.ctrlSuperiorCompany.getCode());
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getSearchCondition().setSuperiorCompanyCode(
			field.ctrlSuperiorCompany.getCode());

	}

	/**
	 * 出力単位を返す
	 * 
	 * @return 出力単位
	 */
	public CompanyOutputCondition getCompanyOutputCondition() {

		CompanyOutputCondition condition = createCondition();
		// 会社
		condition.setCompanyCode(getCompany().getCode());
		// 組織コード
		condition.setCompanyOrganizationCode(field.cboCompanyOrganization.getSelectedOrganizationCode());
		// 階層レベル
		condition.setLevel(field.cboLevel.getSelectedIndex());
		// 配下会社を含むか
		condition.setIncludeUnderCompany(field.rdoInclude.isSelected());
		// 上位会社
		condition.setSuperiorCompany(field.ctrlSuperiorCompany.getEntity());
		// 開始会社
		condition.setCompanyFrom(field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.getEntity());
		// 終了会社
		condition.setCompanyTo(field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.getEntity());
		// 個別選択会社
		condition.setOptionalCompanys(field.ctrlCompany.ctrlOptionalSelector.getEntities());

		return condition;

	}

	/**
	 * 出力単位を設定する
	 * 
	 * @param condition 出力単位
	 */
	public void setCompanyOutputCondition(CompanyOutputCondition condition) {
		// 組織コード
		field.cboCompanyOrganization.setSelectedOrganizationCode(condition.getCompanyOrganizationCode());
		// 階層レベル
		field.cboLevel.setSelectedIndex(condition.getLevel());
		// 配下会社を含むか
		field.rdoInclude.setSelected(condition.isIncludeUnderCompany());
		// 上位会社
		field.ctrlSuperiorCompany.setEntity(condition.getSuperiorCompany());
		// 開始会社
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceFrom.setEntity(condition.getCompanyFrom());
		// 終了会社
		field.ctrlCompany.ctrlReferenceRange.ctrlCompanyReferenceTo.setEntity(condition.getCompanyTo());
		// 個別選択会社
		field.ctrlCompany.ctrlOptionalSelector.setEntities(condition.getOptionalCompanys());
	}

	/**
	 * @return 検索条件作成
	 */
	protected CompanyOutputCondition createCondition() {
		return new CompanyOutputCondition();
	}

	/**
	 * 大小チェック
	 * 
	 * @return true(問題無し) / false(エラーあり)
	 */
	public boolean isCompanySmallerFrom() {
		return field.ctrlCompany.ctrlReferenceRange.isSmallerFrom();
	}

}
